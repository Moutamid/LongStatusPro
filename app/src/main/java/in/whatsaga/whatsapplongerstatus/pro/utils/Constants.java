package in.whatsaga.whatsapplongerstatus.pro.utils;

import android.app.Activity;
import android.os.Build;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Constants {
    public static final String WHATSAPP = "WHATSAPP";
    public static final String BUSINESS = "BUSINESS";
    public static final String GALLERY = "GALLERY";
    public static final String STATUS = "STATUS";
    public static final String SAVED = "SAVED";
    public static final String TYPE = "TYPE";
    public static final String IMAGE_URI = "IMAGE_URI";
    public static final String DELETED_IMAGES = "DELETED_IMAGES";
    public static final String DELETED_VIDEOS = "DELETED_VIDEOS";
    public static final String DELETED_VOICE = "DELETED_VOICE";
    public static String TYPE_GALLERY = "WHATSAPP"; //default
    public static final String ARG_MEDIA_TYPE = "ARG_MEDIA_TYPE";
    public static final int MEDIA_TYPE_VIDEOS = 0;
    public static final int MEDIA_TYPE_IMAGES = 1;
    public static final int MEDIA_TYPE_VOICE = 2;
    public static final String WHATSAPP_STATE = "WhatsApp";
    public static final String MESSENGER_STATE = "Messenger";


    public static void checkApp(Activity activity) {
        String appName = "LongStatusPro";

        new Thread(() -> {
            URL google = null;
            try {
                google = new URL("https://raw.githubusercontent.com/Moutamid/Moutamid/main/apps.txt");
            } catch (final MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(google != null ? google.openStream() : null));
            } catch (final IOException e) {
                e.printStackTrace();
            }
            String input = null;
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if ((input = in != null ? in.readLine() : null) == null) break;
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                stringBuffer.append(input);
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
            String htmlData = stringBuffer.toString();

            try {
                JSONObject myAppObject = new JSONObject(htmlData).getJSONObject(appName);

                boolean value = myAppObject.getBoolean("value");
                String msg = myAppObject.getString("msg");

                if (value) {
                    activity.runOnUiThread(() -> {
                        new AlertDialog.Builder(activity)
                                .setMessage(msg)
                                .setCancelable(false)
                                .show();
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }).start();
    }

}
