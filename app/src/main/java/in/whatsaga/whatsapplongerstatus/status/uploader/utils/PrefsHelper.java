package in.whatsaga.whatsapplongerstatus.status.uploader.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.jetbrains.annotations.NotNull;

public class PrefsHelper {
    public static final boolean RECENT = false;
    public static final boolean VIEWED = true;
    private static final String KEY_NOTIFICATION = "KEY_NOTIFICATION";
    private static final String SWITCH_VALUE     = "SWITCH_VALUE";

    //    private static final int ONE_DAY = 24 * 60 * 60 * 1000;
    private static final int ONE_DAY = 120 * 1000;
    private static SharedPreferences preferences;

    public static void init(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean isViewed(@NotNull String nameKey) {
        return preferences.getBoolean(nameKey, RECENT);
    }

    public static void setViewed(@NotNull String nameKey) {
        preferences.edit().putBoolean(nameKey, VIEWED).apply();
    }

    public static boolean notificationEnabled() {
        return preferences.getBoolean(KEY_NOTIFICATION, true);
    }

    public static void setNotificationEnabled(boolean enabled) {
        preferences.edit().putBoolean(KEY_NOTIFICATION, enabled).apply();
    }

    // TODO : Text Repeater New Line
    public static void setSwitch(boolean SWITCH){
        preferences.edit().putBoolean(SWITCH_VALUE, SWITCH).apply();
    }

    public static boolean getSwitch(){
        return preferences.getBoolean(SWITCH_VALUE,false);
    }
}
