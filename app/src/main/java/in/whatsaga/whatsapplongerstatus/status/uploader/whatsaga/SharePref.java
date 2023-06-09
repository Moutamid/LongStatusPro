package in.whatsaga.whatsapplongerstatus.status.uploader.whatsaga;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {
    private static final String PRIVATE = "PRIVATE";
    private static final String DURATION = "DURATION";
    private static final String INTRO = "INTRO";

    SharedPreferences sharedPreferences;
    Context context;

    public SharePref(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PRIVATE, Context.MODE_PRIVATE);
    }

    public void setDuration(int value) {
        sharedPreferences.edit().putInt(DURATION, value).apply();
    }

    public int getDuration() {
        return sharedPreferences.getInt(DURATION, 29000);
    }

    public void setIntro(boolean date) {
        sharedPreferences.edit().putBoolean(INTRO, date).apply();
    }

    public boolean getIntro() {
        return sharedPreferences.getBoolean(INTRO, false);
    }

}
