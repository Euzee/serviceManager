package sm.euzee.github.com.servicemanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

public class LocalService extends JobIntentService {
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        ServiceManager.callDoneFor(intent);
    }
}
