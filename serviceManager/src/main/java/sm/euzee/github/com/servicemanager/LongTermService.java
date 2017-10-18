package sm.euzee.github.com.servicemanager;

import android.content.Intent;
import android.support.annotation.NonNull;

public class LongTermService extends CompatService {
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        ServiceManager.callDoneFor(intent);
    }
}
