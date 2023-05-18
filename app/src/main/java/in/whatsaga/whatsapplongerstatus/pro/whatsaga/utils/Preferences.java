package in.whatsaga.whatsapplongerstatus.pro.whatsaga.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class Preferences {
    private static final String APP_LANGUAGE = "app_language";
    private static final String WHATSAPP_STATUS_PERMISSION = "whatsapp_status_permission";
    private static final String WHATSAPP_STATUS_PATH = "whatsapp_status_path";
    private static final String BUSINESS_STATUS_PERMISSION = "business_status_permission";
    private static final String BUSINESS_STATUS_PATH = "business_status_path";
    private static SharedPreferences preferences;

    public static void init(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getLanguage() {
        return preferences.getString(APP_LANGUAGE, "en");
    }

    public static void setLanguage(String language) {
        preferences.edit().putString(APP_LANGUAGE, language).apply();
    }

    public static void setIsWhatsAppStatusPermissionAllowed(boolean permissionAllowed) {
        preferences.edit().putBoolean(WHATSAPP_STATUS_PERMISSION, permissionAllowed).apply();
    }

    public static boolean getIsWhatsAppStatusPermissionAllowed() {
        return preferences.getBoolean(WHATSAPP_STATUS_PERMISSION, false);
    }

    public static void setWhatsAppStatusPath(String statusPath) {
        preferences.edit().putString(WHATSAPP_STATUS_PATH, statusPath).apply();
    }

    public static String getWhatsAppStatusPath() {
        return preferences.getString(WHATSAPP_STATUS_PATH, "");
    }

    public static void setIsBusinessStatusPermissionAllowed(boolean permissionAllowed) {
        preferences.edit().putBoolean(BUSINESS_STATUS_PERMISSION, permissionAllowed).apply();
    }

    public static boolean getIsBusinessStatusPermissionAllowed() {
        return preferences.getBoolean(BUSINESS_STATUS_PERMISSION, false);
    }

    public static void setBusinessStatusPath(String statusPath) {
        preferences.edit().putString(BUSINESS_STATUS_PATH, statusPath).apply();
    }

    public static String getBusinessStatusPath() {
        return preferences.getString(BUSINESS_STATUS_PATH, "");
    }
}