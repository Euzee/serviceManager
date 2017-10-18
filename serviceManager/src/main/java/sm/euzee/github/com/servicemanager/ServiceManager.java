package sm.euzee.github.com.servicemanager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.v4.app.JobIntentService;

import java.util.HashMap;

public class ServiceManager {
    private static final String CALLBACK_ID = "CALLBACK_ID";
    private static final int LOCAL_JOB_ID = 1010;
    private static HashMap<String, ServiceCallback> callbacksStorage = new HashMap<>();
    private static Handler handler = getHandler();

    private static Handler getHandler() {
        if (handler == null) {
            HandlerThread thread = new HandlerThread("ServiceManagerThred");
            thread.start();
            handler = new Handler(thread.getLooper());
        }
        return handler;
    }


    public static void runService(Context context, Class<?> cls) {
        runService(context, cls, null);
    }

    public static void runService(Context context, Intent service) {
        runService(context, null, service);
    }

    public static void runService(Context context, Class<?> cls, Intent service) {
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                ServiceCallback cb = new ServiceCallback() {
                    @Override
                    public void onHandleWork() {
                        Intent bindableService = service == null ? new Intent(context, cls) : service;
                        context.getApplicationContext().bindService(bindableService, new SimpleServiceConnection(bindableService), Context.BIND_AUTO_CREATE);
                    }
                };
                callbacksStorage.put(cb.toString(), cb);
                Intent localService = new Intent(context, LocalService.class);
                localService.putExtra(CALLBACK_ID, cb.toString());
                JobIntentService.enqueueWork(context, LocalService.class, LOCAL_JOB_ID, localService);
            }
        });

    }

    //In case if you don't want to create service you can use this method and just add actions in callback
    public static void runService(Context context, ServiceCallback callback, boolean isShortActions) {
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                callbacksStorage.put(callback.toString(), callback);
                Intent service = new Intent(context, isShortActions ? LocalService.class : LongTermService.class);
                service.putExtra(CALLBACK_ID, callback.toString());
                if (isShortActions) {
                    JobIntentService.enqueueWork(context, LocalService.class, LOCAL_JOB_ID, service);
                } else {
                    context.getApplicationContext().bindService(service, new SimpleServiceConnection(service), Context.BIND_AUTO_CREATE);
                }
            }
        });
    }

    static void callDoneFor(Intent intent) {
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (intent.getExtras() != null && intent.getExtras().containsKey(CALLBACK_ID)) {
                    String callbackId = intent.getExtras().getString(CALLBACK_ID);
                    if (callbacksStorage.containsKey(callbackId)) {
                        callbacksStorage.get(callbackId).onHandleWork();
                    }
                }
            }
        });
    }

    private static class SimpleServiceConnection implements ServiceConnection {
        private final Intent service;

        SimpleServiceConnection(Intent service) {
            this.service = service;
        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    CompatService compatService = ((CompatService.LocalBinder) iBinder).getService();
                    compatService.fakeHandleJob(service);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    }
}
