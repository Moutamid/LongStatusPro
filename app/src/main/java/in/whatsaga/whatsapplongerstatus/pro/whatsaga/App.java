package in.whatsaga.whatsapplongerstatus.pro.whatsaga;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import in.whatsaga.whatsapplongerstatus.pro.whatsaga.utils.Preferences;
import khangtran.preferenceshelper.PrefHelper;

public class App extends Application {

    public static Context context;
    public static boolean IS_WEB = false;

    public static Context getContext() {
        return context;

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;



        PrefHelper.initHelper(context);
        Preferences.init(context);


    }

}
