package sang.com.minitoolsimple;

import android.app.Application;
import android.os.Build;

import sang.com.minitools.MiniTools;

/**
 * 作者： ${PING} on 2018/6/11.
 */

public class ToolApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MiniTools.init(this, BuildConfig.DEBUG);
    }
}
