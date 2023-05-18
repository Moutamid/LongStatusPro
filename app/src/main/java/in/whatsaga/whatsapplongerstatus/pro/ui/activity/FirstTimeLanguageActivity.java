package in.whatsaga.whatsapplongerstatus.pro.ui.activity;

import static in.whatsaga.whatsapplongerstatus.pro.ui.activity.StartActivity.ENABLED_NOTIFICATION_LISTENERS;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import in.whatsaga.whatsapplongerstatus.pro.R;
import in.whatsaga.whatsapplongerstatus.pro.utils.Common;
import khangtran.preferenceshelper.PrefHelper;

public class FirstTimeLanguageActivity extends AppCompatActivity {

   

    public static void start(Context context) {
        Intent starter = new Intent(context, FirstTimeLanguageActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Common.setLocale(this);
        setContentView(R.layout.activity_first_time_language);




        PrefHelper.setVal("locale_set", true);

        findViewById(R.id.hindiCard).setOnClickListener(v -> {
            PrefHelper.setVal("locale", "hi");
            updatingUi();
        });
        findViewById(R.id.englishCard).setOnClickListener(v -> {
            PrefHelper.setVal("locale", "en");
            updatingUi();
        });
        findViewById(R.id.germanCard).setOnClickListener(v -> {
            PrefHelper.setVal("locale", "de");
            updatingUi();
        });
        findViewById(R.id.frenchCard).setOnClickListener(v -> {
            PrefHelper.setVal("locale", "fr");
            updatingUi();
        });
        findViewById(R.id.spanishCard).setOnClickListener(v -> {
            PrefHelper.setVal("locale", "es");
            updatingUi();
        });
        findViewById(R.id.arabicCard).setOnClickListener(v -> {
            PrefHelper.setVal("locale", "ar");
            updatingUi();
        });
        findViewById(R.id.purtaguesCard).setOnClickListener(v -> {
            PrefHelper.setVal("locale", "pt");
            updatingUi();
        });
        findViewById(R.id.italianCard).setOnClickListener(v -> {
            PrefHelper.setVal("locale", "it");
            updatingUi();
        });
        findViewById(R.id.urduCard).setOnClickListener(v -> {
            PrefHelper.setVal("locale", "ur");
            updatingUi();
        });
        findViewById(R.id.japaneseCard).setOnClickListener(v -> {
            PrefHelper.setVal("locale", "ja");
            updatingUi();
        });
        findViewById(R.id.bengaliCard).setOnClickListener(v -> {
            PrefHelper.setVal("locale", "bn");
            updatingUi();
        });
        findViewById(R.id.turkishCard).setOnClickListener(v -> {
            PrefHelper.setVal("locale", "tr");
            updatingUi();
        });
        findViewById(R.id.indonesiaCard).setOnClickListener(v -> {
            PrefHelper.setVal("locale", "in");
            updatingUi();
        });
        findViewById(R.id.russianCard).setOnClickListener(v -> {
            PrefHelper.setVal("locale", "ru");
            updatingUi();
        });
        findViewById(R.id.swedenCard).setOnClickListener(v -> {
            PrefHelper.setVal("locale", "sv");
            updatingUi();
        });
        findViewById(R.id.done).setOnClickListener(v -> {


                intention();

        });

        updatingUi();
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        intention();
    }




    public boolean isNotificationServiceEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (String name : names) {
                final ComponentName cn = ComponentName.unflattenFromString(name);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void intention() {

            startActivity(new Intent(this, MainActivity.class));

    }

    private void updatingUi() {
        if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("hi")) {
            findViewById(R.id.hindiCheck).setVisibility(View.VISIBLE);
            findViewById(R.id.englishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.spanishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.frenchCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.germanCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.arabicCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.portaguesCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.italianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.urduCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.japaneseCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.bengaliCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.turkishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.indonesiaCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.russianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.swedenCheck).setVisibility(View.INVISIBLE);
        } else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("es")) {
            findViewById(R.id.hindiCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.englishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.spanishCheck).setVisibility(View.VISIBLE);
            findViewById(R.id.frenchCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.germanCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.arabicCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.portaguesCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.italianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.urduCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.japaneseCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.bengaliCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.turkishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.indonesiaCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.russianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.swedenCheck).setVisibility(View.INVISIBLE);
        } else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("fr")) {
            findViewById(R.id.hindiCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.englishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.spanishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.frenchCheck).setVisibility(View.VISIBLE);
            findViewById(R.id.germanCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.arabicCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.portaguesCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.italianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.urduCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.japaneseCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.bengaliCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.turkishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.indonesiaCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.russianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.swedenCheck).setVisibility(View.INVISIBLE);
        } else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("ar")) {
            findViewById(R.id.hindiCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.englishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.spanishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.frenchCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.germanCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.arabicCheck).setVisibility(View.VISIBLE);
            findViewById(R.id.portaguesCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.italianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.urduCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.japaneseCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.bengaliCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.turkishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.indonesiaCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.russianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.swedenCheck).setVisibility(View.INVISIBLE);
        } else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("pt")) {
            findViewById(R.id.hindiCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.englishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.spanishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.frenchCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.germanCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.arabicCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.portaguesCheck).setVisibility(View.VISIBLE);
            findViewById(R.id.italianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.urduCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.japaneseCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.bengaliCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.turkishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.indonesiaCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.russianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.swedenCheck).setVisibility(View.INVISIBLE);
        } else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("de")) {
            findViewById(R.id.hindiCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.englishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.spanishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.frenchCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.germanCheck).setVisibility(View.VISIBLE);
            findViewById(R.id.arabicCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.portaguesCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.italianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.urduCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.japaneseCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.bengaliCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.turkishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.indonesiaCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.russianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.swedenCheck).setVisibility(View.INVISIBLE);
        } else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("en")) {
            findViewById(R.id.hindiCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.englishCheck).setVisibility(View.VISIBLE);
            findViewById(R.id.spanishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.frenchCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.germanCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.arabicCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.portaguesCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.italianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.urduCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.japaneseCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.bengaliCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.turkishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.indonesiaCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.russianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.swedenCheck).setVisibility(View.INVISIBLE);
        } else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("it")) {
            findViewById(R.id.hindiCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.englishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.spanishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.frenchCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.germanCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.arabicCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.portaguesCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.italianCheck).setVisibility(View.VISIBLE);
            findViewById(R.id.urduCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.japaneseCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.bengaliCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.turkishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.indonesiaCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.russianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.swedenCheck).setVisibility(View.INVISIBLE);
        } else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("ur")) {
            findViewById(R.id.hindiCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.englishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.spanishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.frenchCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.germanCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.arabicCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.portaguesCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.italianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.urduCheck).setVisibility(View.VISIBLE);
            findViewById(R.id.japaneseCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.bengaliCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.turkishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.indonesiaCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.russianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.swedenCheck).setVisibility(View.INVISIBLE);
        } else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("ja")) {
            findViewById(R.id.hindiCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.englishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.spanishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.frenchCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.germanCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.arabicCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.portaguesCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.italianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.urduCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.japaneseCheck).setVisibility(View.VISIBLE);
            findViewById(R.id.bengaliCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.turkishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.indonesiaCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.russianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.swedenCheck).setVisibility(View.INVISIBLE);
        } else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("bn")) {
            findViewById(R.id.hindiCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.englishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.spanishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.frenchCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.germanCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.arabicCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.portaguesCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.italianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.urduCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.japaneseCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.bengaliCheck).setVisibility(View.VISIBLE);
            findViewById(R.id.turkishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.indonesiaCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.russianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.swedenCheck).setVisibility(View.INVISIBLE);
        } else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("tr")) {
            findViewById(R.id.hindiCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.englishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.spanishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.frenchCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.germanCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.arabicCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.portaguesCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.italianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.urduCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.japaneseCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.bengaliCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.turkishCheck).setVisibility(View.VISIBLE);
            findViewById(R.id.indonesiaCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.russianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.swedenCheck).setVisibility(View.INVISIBLE);
        } else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("in")) {
            findViewById(R.id.hindiCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.englishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.spanishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.frenchCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.germanCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.arabicCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.portaguesCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.italianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.urduCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.japaneseCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.bengaliCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.turkishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.indonesiaCheck).setVisibility(View.VISIBLE);
            findViewById(R.id.russianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.swedenCheck).setVisibility(View.INVISIBLE);
        } else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("ru")) {
            findViewById(R.id.hindiCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.englishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.spanishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.frenchCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.germanCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.arabicCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.portaguesCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.italianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.urduCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.japaneseCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.bengaliCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.turkishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.indonesiaCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.russianCheck).setVisibility(View.VISIBLE);
            findViewById(R.id.swedenCheck).setVisibility(View.INVISIBLE);
        } else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("sv")) {
            findViewById(R.id.hindiCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.englishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.spanishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.frenchCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.germanCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.arabicCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.portaguesCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.italianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.urduCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.japaneseCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.bengaliCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.turkishCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.indonesiaCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.russianCheck).setVisibility(View.INVISIBLE);
            findViewById(R.id.swedenCheck).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }




}