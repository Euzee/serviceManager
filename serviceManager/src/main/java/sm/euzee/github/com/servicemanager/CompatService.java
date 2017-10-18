package sm.euzee.github.com.servicemanager;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.JobIntentService;


public abstract class CompatService extends JobIntentService {

    private final IBinder mBinder = getBinder();

    protected IBinder getBinder() {
        return new LocalBinder();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void fakeHandleJob(Intent intent) {
        onHandleWork(intent);
    }

    class LocalBinder extends Binder {
        public CompatService getService() {
            return CompatService.this;
        }
    }
}
