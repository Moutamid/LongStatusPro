package in.whatsaga.whatsapplongerstatus.pro.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

import in.whatsaga.whatsapplongerstatus.pro.R;
import khangtran.preferenceshelper.PrefHelper;

public class LanguageActivity extends AppCompatActivity  {
    MaterialCardView english, spanish, arabic, hindi, french, germen, portg, italian, urdu, japans, bengali, turkish, indona, russian, sweden;
    TextView englishText, spanishText, arabicText, hindiText, frenchText, germenText, portgText, italianText, urduText, japansText, bengaliText, turkishText, indonaText, russianText, swedenText;


    public static void start(Context context) {
        Intent starter = new Intent(context, LanguageActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        // Cards
        english = findViewById(R.id.englishCard);
        spanish = findViewById(R.id.spanishCard);
        arabic = findViewById(R.id.arabicCard);
        hindi = findViewById(R.id.hindiCard);
        french = findViewById(R.id.frenchCard);
        germen = findViewById(R.id.germanCard);
        portg = findViewById(R.id.purtaguesCard);
        italian = findViewById(R.id.italianCard);
        urdu = findViewById(R.id.urduCard);
        japans = findViewById(R.id.japaneseCard);
        bengali = findViewById(R.id.bengaliCard);
        turkish = findViewById(R.id.turkishCard);
        indona = findViewById(R.id.indonesiaCard);
        russian = findViewById(R.id.russianCard);
        sweden = findViewById(R.id.swedenCard);

        // Text Views
        englishText = findViewById(R.id.englishText);
        spanishText = findViewById(R.id.spanishText);
        arabicText = findViewById(R.id.arabicText);
        hindiText = findViewById(R.id.hindiText);
        frenchText = findViewById(R.id.frenchText);
        germenText = findViewById(R.id.germanText);
        portgText = findViewById(R.id.potguseText);
        italianText = findViewById(R.id.italianText);
        urduText = findViewById(R.id.urduText);
        japansText = findViewById(R.id.japanText);
        bengaliText = findViewById(R.id.bengaliText);
        turkishText = findViewById(R.id.turkText);
        indonaText = findViewById(R.id.indoText);
        russianText = findViewById(R.id.russianText);
        swedenText = findViewById(R.id.swedenText);

        hindi.setOnClickListener(v -> {
            PrefHelper.setVal("locale", "hi");
            updatingUi();
        });
        english.setOnClickListener(v -> {
            PrefHelper.setVal("locale", "en");
            updatingUi();
        });
        germen.setOnClickListener(v -> {
            PrefHelper.setVal("locale", "de");
            updatingUi();
        });
        french.setOnClickListener(v -> {
            PrefHelper.setVal("locale", "fr");
            updatingUi();
        });
        spanish.setOnClickListener(v -> {
            PrefHelper.setVal("locale", "es");
            updatingUi();
        });
        arabic.setOnClickListener(v -> {
            PrefHelper.setVal("locale", "ar");
            updatingUi();
        });
        portg.setOnClickListener(v -> {
            PrefHelper.setVal("locale", "pt");
            updatingUi();
        });
        italian.setOnClickListener(v -> {
            PrefHelper.setVal("locale", "it");
            updatingUi();
        });
        urdu.setOnClickListener(v -> {
            PrefHelper.setVal("locale", "ur");
            updatingUi();
        });
        japans.setOnClickListener(v -> {
            PrefHelper.setVal("locale", "ja");
            updatingUi();
        });
        bengali.setOnClickListener(v -> {
            PrefHelper.setVal("locale", "bn");
            updatingUi();
        });
        turkish.setOnClickListener(v -> {
            PrefHelper.setVal("locale", "tr");
            updatingUi();
        });
        indona.setOnClickListener(v -> {
            PrefHelper.setVal("locale", "in");
            updatingUi();
        });
        russian.setOnClickListener(v -> {
            PrefHelper.setVal("locale", "ru");
            updatingUi();
        });
        sweden.setOnClickListener(v -> {
            PrefHelper.setVal("locale", "sv");
            updatingUi();
        });

        findViewById(R.id.done).setOnClickListener(v -> {
                startActivity(new Intent(LanguageActivity.this, MainActivity.class));
                finish();
        });

        updatingUi();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LanguageActivity.this, MainActivity.class));
    }

    private void updatingUi() {
        // Hindi
        if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("hi")) {
            hindi.setCardBackgroundColor(getResources().getColor(R.color.orange));
            hindiText.setTextColor(getResources().getColor(R.color.white));

            english.setCardBackgroundColor(getResources().getColor(R.color.white));
            spanish.setCardBackgroundColor(getResources().getColor(R.color.white));
            french.setCardBackgroundColor(getResources().getColor(R.color.white));
            germen.setCardBackgroundColor(getResources().getColor(R.color.white));
            arabic.setCardBackgroundColor(getResources().getColor(R.color.white));
            portg.setCardBackgroundColor(getResources().getColor(R.color.white));
            italian.setCardBackgroundColor(getResources().getColor(R.color.white));
            urdu.setCardBackgroundColor(getResources().getColor(R.color.white));
            japans.setCardBackgroundColor(getResources().getColor(R.color.white));
            bengali.setCardBackgroundColor(getResources().getColor(R.color.white));
            turkish.setCardBackgroundColor(getResources().getColor(R.color.white));
            indona.setCardBackgroundColor(getResources().getColor(R.color.white));
            russian.setCardBackgroundColor(getResources().getColor(R.color.white));
            sweden.setCardBackgroundColor(getResources().getColor(R.color.white));

            englishText.setTextColor(getResources().getColor(R.color.black));
            spanishText.setTextColor(getResources().getColor(R.color.black));
            frenchText.setTextColor(getResources().getColor(R.color.black));
            germenText.setTextColor(getResources().getColor(R.color.black));
            arabicText.setTextColor(getResources().getColor(R.color.black));
            portgText.setTextColor(getResources().getColor(R.color.black));
            italianText.setTextColor(getResources().getColor(R.color.black));
            urduText.setTextColor(getResources().getColor(R.color.black));
            japansText.setTextColor(getResources().getColor(R.color.black));
            bengaliText.setTextColor(getResources().getColor(R.color.black));
            turkishText.setTextColor(getResources().getColor(R.color.black));
            indonaText.setTextColor(getResources().getColor(R.color.black));
            russianText.setTextColor(getResources().getColor(R.color.black));
            swedenText.setTextColor(getResources().getColor(R.color.black));

        }
        // Spanish
        else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("es")) {

            hindi.setCardBackgroundColor(getResources().getColor(R.color.white));
            hindiText.setTextColor(getResources().getColor(R.color.black));

            english.setCardBackgroundColor(getResources().getColor(R.color.white));
            englishText.setTextColor(getResources().getColor(R.color.black));

            spanish.setCardBackgroundColor(getResources().getColor(R.color.orange));
            spanishText.setTextColor(getResources().getColor(R.color.white));

            french.setCardBackgroundColor(getResources().getColor(R.color.white));
            frenchText.setTextColor(getResources().getColor(R.color.black));

            germen.setCardBackgroundColor(getResources().getColor(R.color.white));
            germenText.setTextColor(getResources().getColor(R.color.black));

            arabic.setCardBackgroundColor(getResources().getColor(R.color.white));
            arabicText.setTextColor(getResources().getColor(R.color.black));

            portg.setCardBackgroundColor(getResources().getColor(R.color.white));
            portgText.setTextColor(getResources().getColor(R.color.black));

            italian.setCardBackgroundColor(getResources().getColor(R.color.white));
            italianText.setTextColor(getResources().getColor(R.color.black));

            urdu.setCardBackgroundColor(getResources().getColor(R.color.white));
            urduText.setTextColor(getResources().getColor(R.color.black));

            japans.setCardBackgroundColor(getResources().getColor(R.color.white));
            japansText.setTextColor(getResources().getColor(R.color.black));

            bengali.setCardBackgroundColor(getResources().getColor(R.color.white));
            bengaliText.setTextColor(getResources().getColor(R.color.black));

            turkish.setCardBackgroundColor(getResources().getColor(R.color.white));
            turkishText.setTextColor(getResources().getColor(R.color.black));

            indona.setCardBackgroundColor(getResources().getColor(R.color.white));
            indonaText.setTextColor(getResources().getColor(R.color.black));

            russian.setCardBackgroundColor(getResources().getColor(R.color.white));
            russianText.setTextColor(getResources().getColor(R.color.black));

            sweden.setCardBackgroundColor(getResources().getColor(R.color.white));
            swedenText.setTextColor(getResources().getColor(R.color.black));

        }
        // French
        else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("fr")) {

            hindi.setCardBackgroundColor(getResources().getColor(R.color.white));
            hindiText.setTextColor(getResources().getColor(R.color.black));

            english.setCardBackgroundColor(getResources().getColor(R.color.white));
            englishText.setTextColor(getResources().getColor(R.color.black));

            spanish.setCardBackgroundColor(getResources().getColor(R.color.white));
            spanishText.setTextColor(getResources().getColor(R.color.black));

            french.setCardBackgroundColor(getResources().getColor(R.color.orange));
            frenchText.setTextColor(getResources().getColor(R.color.white));

            germen.setCardBackgroundColor(getResources().getColor(R.color.white));
            germenText.setTextColor(getResources().getColor(R.color.black));

            arabic.setCardBackgroundColor(getResources().getColor(R.color.white));
            arabicText.setTextColor(getResources().getColor(R.color.black));

            portg.setCardBackgroundColor(getResources().getColor(R.color.white));
            portgText.setTextColor(getResources().getColor(R.color.black));

            italian.setCardBackgroundColor(getResources().getColor(R.color.white));
            italianText.setTextColor(getResources().getColor(R.color.black));

            urdu.setCardBackgroundColor(getResources().getColor(R.color.white));
            urduText.setTextColor(getResources().getColor(R.color.black));

            japans.setCardBackgroundColor(getResources().getColor(R.color.white));
            japansText.setTextColor(getResources().getColor(R.color.black));

            bengali.setCardBackgroundColor(getResources().getColor(R.color.white));
            bengaliText.setTextColor(getResources().getColor(R.color.black));

            turkish.setCardBackgroundColor(getResources().getColor(R.color.white));
            turkishText.setTextColor(getResources().getColor(R.color.black));

            indona.setCardBackgroundColor(getResources().getColor(R.color.white));
            indonaText.setTextColor(getResources().getColor(R.color.black));

            russian.setCardBackgroundColor(getResources().getColor(R.color.white));
            russianText.setTextColor(getResources().getColor(R.color.black));

            sweden.setCardBackgroundColor(getResources().getColor(R.color.white));
            swedenText.setTextColor(getResources().getColor(R.color.black));

        }
        // Arabic
        else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("ar")) {
            hindi.setCardBackgroundColor(getResources().getColor(R.color.white));
            hindiText.setTextColor(getResources().getColor(R.color.black));

            english.setCardBackgroundColor(getResources().getColor(R.color.white));
            englishText.setTextColor(getResources().getColor(R.color.black));

            spanish.setCardBackgroundColor(getResources().getColor(R.color.white));
            spanishText.setTextColor(getResources().getColor(R.color.black));

            french.setCardBackgroundColor(getResources().getColor(R.color.white));
            frenchText.setTextColor(getResources().getColor(R.color.black));

            germen.setCardBackgroundColor(getResources().getColor(R.color.white));
            germenText.setTextColor(getResources().getColor(R.color.black));

            arabic.setCardBackgroundColor(getResources().getColor(R.color.orange));
            arabicText.setTextColor(getResources().getColor(R.color.white));

            portg.setCardBackgroundColor(getResources().getColor(R.color.white));
            portgText.setTextColor(getResources().getColor(R.color.black));

            italian.setCardBackgroundColor(getResources().getColor(R.color.white));
            italianText.setTextColor(getResources().getColor(R.color.black));

            urdu.setCardBackgroundColor(getResources().getColor(R.color.white));
            urduText.setTextColor(getResources().getColor(R.color.black));

            japans.setCardBackgroundColor(getResources().getColor(R.color.white));
            japansText.setTextColor(getResources().getColor(R.color.black));

            bengali.setCardBackgroundColor(getResources().getColor(R.color.white));
            bengaliText.setTextColor(getResources().getColor(R.color.black));

            turkish.setCardBackgroundColor(getResources().getColor(R.color.white));
            turkishText.setTextColor(getResources().getColor(R.color.black));

            indona.setCardBackgroundColor(getResources().getColor(R.color.white));
            indonaText.setTextColor(getResources().getColor(R.color.black));

            russian.setCardBackgroundColor(getResources().getColor(R.color.white));
            russianText.setTextColor(getResources().getColor(R.color.black));

            sweden.setCardBackgroundColor(getResources().getColor(R.color.white));
            swedenText.setTextColor(getResources().getColor(R.color.black));
        }
        // Portuguese
        else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("pt")) {
            hindi.setCardBackgroundColor(getResources().getColor(R.color.white));
            hindiText.setTextColor(getResources().getColor(R.color.black));

            english.setCardBackgroundColor(getResources().getColor(R.color.white));
            englishText.setTextColor(getResources().getColor(R.color.black));

            spanish.setCardBackgroundColor(getResources().getColor(R.color.white));
            spanishText.setTextColor(getResources().getColor(R.color.black));

            french.setCardBackgroundColor(getResources().getColor(R.color.white));
            frenchText.setTextColor(getResources().getColor(R.color.black));

            germen.setCardBackgroundColor(getResources().getColor(R.color.white));
            germenText.setTextColor(getResources().getColor(R.color.black));

            arabic.setCardBackgroundColor(getResources().getColor(R.color.white));
            arabicText.setTextColor(getResources().getColor(R.color.black));

            portg.setCardBackgroundColor(getResources().getColor(R.color.orange));
            portgText.setTextColor(getResources().getColor(R.color.white));

            italian.setCardBackgroundColor(getResources().getColor(R.color.white));
            italianText.setTextColor(getResources().getColor(R.color.black));

            urdu.setCardBackgroundColor(getResources().getColor(R.color.white));
            urduText.setTextColor(getResources().getColor(R.color.black));

            japans.setCardBackgroundColor(getResources().getColor(R.color.white));
            japansText.setTextColor(getResources().getColor(R.color.black));

            bengali.setCardBackgroundColor(getResources().getColor(R.color.white));
            bengaliText.setTextColor(getResources().getColor(R.color.black));

            turkish.setCardBackgroundColor(getResources().getColor(R.color.white));
            turkishText.setTextColor(getResources().getColor(R.color.black));

            indona.setCardBackgroundColor(getResources().getColor(R.color.white));
            indonaText.setTextColor(getResources().getColor(R.color.black));

            russian.setCardBackgroundColor(getResources().getColor(R.color.white));
            russianText.setTextColor(getResources().getColor(R.color.black));

            sweden.setCardBackgroundColor(getResources().getColor(R.color.white));
            swedenText.setTextColor(getResources().getColor(R.color.black));
        }
        // German
        else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("de")) {
            hindi.setCardBackgroundColor(getResources().getColor(R.color.white));
            hindiText.setTextColor(getResources().getColor(R.color.black));

            english.setCardBackgroundColor(getResources().getColor(R.color.white));
            englishText.setTextColor(getResources().getColor(R.color.black));

            spanish.setCardBackgroundColor(getResources().getColor(R.color.white));
            spanishText.setTextColor(getResources().getColor(R.color.black));

            french.setCardBackgroundColor(getResources().getColor(R.color.white));
            frenchText.setTextColor(getResources().getColor(R.color.black));

            germen.setCardBackgroundColor(getResources().getColor(R.color.orange));
            germenText.setTextColor(getResources().getColor(R.color.white));

            arabic.setCardBackgroundColor(getResources().getColor(R.color.white));
            arabicText.setTextColor(getResources().getColor(R.color.black));

            portg.setCardBackgroundColor(getResources().getColor(R.color.white));
            portgText.setTextColor(getResources().getColor(R.color.black));

            italian.setCardBackgroundColor(getResources().getColor(R.color.white));
            italianText.setTextColor(getResources().getColor(R.color.black));

            urdu.setCardBackgroundColor(getResources().getColor(R.color.white));
            urduText.setTextColor(getResources().getColor(R.color.black));

            japans.setCardBackgroundColor(getResources().getColor(R.color.white));
            japansText.setTextColor(getResources().getColor(R.color.black));

            bengali.setCardBackgroundColor(getResources().getColor(R.color.white));
            bengaliText.setTextColor(getResources().getColor(R.color.black));

            turkish.setCardBackgroundColor(getResources().getColor(R.color.white));
            turkishText.setTextColor(getResources().getColor(R.color.black));

            indona.setCardBackgroundColor(getResources().getColor(R.color.white));
            indonaText.setTextColor(getResources().getColor(R.color.black));

            russian.setCardBackgroundColor(getResources().getColor(R.color.white));
            russianText.setTextColor(getResources().getColor(R.color.black));

            sweden.setCardBackgroundColor(getResources().getColor(R.color.white));
            swedenText.setTextColor(getResources().getColor(R.color.black));
        }
        // English
        else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("en")) {
            hindi.setCardBackgroundColor(getResources().getColor(R.color.white));
            hindiText.setTextColor(getResources().getColor(R.color.black));

            english.setCardBackgroundColor(getResources().getColor(R.color.orange));
            englishText.setTextColor(getResources().getColor(R.color.white));

            spanish.setCardBackgroundColor(getResources().getColor(R.color.white));
            spanishText.setTextColor(getResources().getColor(R.color.black));

            french.setCardBackgroundColor(getResources().getColor(R.color.white));
            frenchText.setTextColor(getResources().getColor(R.color.black));

            germen.setCardBackgroundColor(getResources().getColor(R.color.white));
            germenText.setTextColor(getResources().getColor(R.color.black));

            arabic.setCardBackgroundColor(getResources().getColor(R.color.white));
            arabicText.setTextColor(getResources().getColor(R.color.black));

            portg.setCardBackgroundColor(getResources().getColor(R.color.white));
            portgText.setTextColor(getResources().getColor(R.color.black));

            italian.setCardBackgroundColor(getResources().getColor(R.color.white));
            italianText.setTextColor(getResources().getColor(R.color.black));

            urdu.setCardBackgroundColor(getResources().getColor(R.color.white));
            urduText.setTextColor(getResources().getColor(R.color.black));

            japans.setCardBackgroundColor(getResources().getColor(R.color.white));
            japansText.setTextColor(getResources().getColor(R.color.black));

            bengali.setCardBackgroundColor(getResources().getColor(R.color.white));
            bengaliText.setTextColor(getResources().getColor(R.color.black));

            turkish.setCardBackgroundColor(getResources().getColor(R.color.white));
            turkishText.setTextColor(getResources().getColor(R.color.black));

            indona.setCardBackgroundColor(getResources().getColor(R.color.white));
            indonaText.setTextColor(getResources().getColor(R.color.black));

            russian.setCardBackgroundColor(getResources().getColor(R.color.white));
            russianText.setTextColor(getResources().getColor(R.color.black));

            sweden.setCardBackgroundColor(getResources().getColor(R.color.white));
            swedenText.setTextColor(getResources().getColor(R.color.black));
        }
        // Italian
        else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("it")) {
            hindi.setCardBackgroundColor(getResources().getColor(R.color.white));
            hindiText.setTextColor(getResources().getColor(R.color.black));

            english.setCardBackgroundColor(getResources().getColor(R.color.white));
            englishText.setTextColor(getResources().getColor(R.color.black));

            spanish.setCardBackgroundColor(getResources().getColor(R.color.white));
            spanishText.setTextColor(getResources().getColor(R.color.black));

            french.setCardBackgroundColor(getResources().getColor(R.color.white));
            frenchText.setTextColor(getResources().getColor(R.color.black));

            germen.setCardBackgroundColor(getResources().getColor(R.color.white));
            germenText.setTextColor(getResources().getColor(R.color.black));

            arabic.setCardBackgroundColor(getResources().getColor(R.color.white));
            arabicText.setTextColor(getResources().getColor(R.color.black));

            portg.setCardBackgroundColor(getResources().getColor(R.color.white));
            portgText.setTextColor(getResources().getColor(R.color.black));

            italian.setCardBackgroundColor(getResources().getColor(R.color.orange));
            italianText.setTextColor(getResources().getColor(R.color.white));

            urdu.setCardBackgroundColor(getResources().getColor(R.color.white));
            urduText.setTextColor(getResources().getColor(R.color.black));

            japans.setCardBackgroundColor(getResources().getColor(R.color.white));
            japansText.setTextColor(getResources().getColor(R.color.black));

            bengali.setCardBackgroundColor(getResources().getColor(R.color.white));
            bengaliText.setTextColor(getResources().getColor(R.color.black));

            turkish.setCardBackgroundColor(getResources().getColor(R.color.white));
            turkishText.setTextColor(getResources().getColor(R.color.black));

            indona.setCardBackgroundColor(getResources().getColor(R.color.white));
            indonaText.setTextColor(getResources().getColor(R.color.black));

            russian.setCardBackgroundColor(getResources().getColor(R.color.white));
            russianText.setTextColor(getResources().getColor(R.color.black));

            sweden.setCardBackgroundColor(getResources().getColor(R.color.white));
            swedenText.setTextColor(getResources().getColor(R.color.black));
        }
        // Urdu
        else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("ur")) {
            hindi.setCardBackgroundColor(getResources().getColor(R.color.white));
            hindiText.setTextColor(getResources().getColor(R.color.black));

            english.setCardBackgroundColor(getResources().getColor(R.color.white));
            englishText.setTextColor(getResources().getColor(R.color.black));

            spanish.setCardBackgroundColor(getResources().getColor(R.color.white));
            spanishText.setTextColor(getResources().getColor(R.color.black));

            french.setCardBackgroundColor(getResources().getColor(R.color.white));
            frenchText.setTextColor(getResources().getColor(R.color.black));

            germen.setCardBackgroundColor(getResources().getColor(R.color.white));
            germenText.setTextColor(getResources().getColor(R.color.black));

            arabic.setCardBackgroundColor(getResources().getColor(R.color.white));
            arabicText.setTextColor(getResources().getColor(R.color.black));

            portg.setCardBackgroundColor(getResources().getColor(R.color.white));
            portgText.setTextColor(getResources().getColor(R.color.black));

            italian.setCardBackgroundColor(getResources().getColor(R.color.white));
            italianText.setTextColor(getResources().getColor(R.color.black));

            urdu.setCardBackgroundColor(getResources().getColor(R.color.orange));
            urduText.setTextColor(getResources().getColor(R.color.white));

            japans.setCardBackgroundColor(getResources().getColor(R.color.white));
            japansText.setTextColor(getResources().getColor(R.color.black));

            bengali.setCardBackgroundColor(getResources().getColor(R.color.white));
            bengaliText.setTextColor(getResources().getColor(R.color.black));

            turkish.setCardBackgroundColor(getResources().getColor(R.color.white));
            turkishText.setTextColor(getResources().getColor(R.color.black));

            indona.setCardBackgroundColor(getResources().getColor(R.color.white));
            indonaText.setTextColor(getResources().getColor(R.color.black));

            russian.setCardBackgroundColor(getResources().getColor(R.color.white));
            russianText.setTextColor(getResources().getColor(R.color.black));

            sweden.setCardBackgroundColor(getResources().getColor(R.color.white));
            swedenText.setTextColor(getResources().getColor(R.color.black));
        }
        // Japanese
        else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("ja")) {
            hindi.setCardBackgroundColor(getResources().getColor(R.color.white));
            hindiText.setTextColor(getResources().getColor(R.color.black));

            english.setCardBackgroundColor(getResources().getColor(R.color.white));
            englishText.setTextColor(getResources().getColor(R.color.black));

            spanish.setCardBackgroundColor(getResources().getColor(R.color.white));
            spanishText.setTextColor(getResources().getColor(R.color.black));

            french.setCardBackgroundColor(getResources().getColor(R.color.white));
            frenchText.setTextColor(getResources().getColor(R.color.black));

            germen.setCardBackgroundColor(getResources().getColor(R.color.white));
            germenText.setTextColor(getResources().getColor(R.color.black));

            arabic.setCardBackgroundColor(getResources().getColor(R.color.white));
            arabicText.setTextColor(getResources().getColor(R.color.black));

            portg.setCardBackgroundColor(getResources().getColor(R.color.white));
            portgText.setTextColor(getResources().getColor(R.color.black));

            italian.setCardBackgroundColor(getResources().getColor(R.color.white));
            italianText.setTextColor(getResources().getColor(R.color.black));

            urdu.setCardBackgroundColor(getResources().getColor(R.color.white));
            urduText.setTextColor(getResources().getColor(R.color.black));

            japans.setCardBackgroundColor(getResources().getColor(R.color.orange));
            japansText.setTextColor(getResources().getColor(R.color.white));

            bengali.setCardBackgroundColor(getResources().getColor(R.color.white));
            bengaliText.setTextColor(getResources().getColor(R.color.black));

            turkish.setCardBackgroundColor(getResources().getColor(R.color.white));
            turkishText.setTextColor(getResources().getColor(R.color.black));

            indona.setCardBackgroundColor(getResources().getColor(R.color.white));
            indonaText.setTextColor(getResources().getColor(R.color.black));

            russian.setCardBackgroundColor(getResources().getColor(R.color.white));
            russianText.setTextColor(getResources().getColor(R.color.black));

            sweden.setCardBackgroundColor(getResources().getColor(R.color.white));
            swedenText.setTextColor(getResources().getColor(R.color.black));
        }
        // Bengali
        else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("bn")) {
            hindi.setCardBackgroundColor(getResources().getColor(R.color.white));
            hindiText.setTextColor(getResources().getColor(R.color.black));

            english.setCardBackgroundColor(getResources().getColor(R.color.white));
            englishText.setTextColor(getResources().getColor(R.color.black));

            spanish.setCardBackgroundColor(getResources().getColor(R.color.white));
            spanishText.setTextColor(getResources().getColor(R.color.black));

            french.setCardBackgroundColor(getResources().getColor(R.color.white));
            frenchText.setTextColor(getResources().getColor(R.color.black));

            germen.setCardBackgroundColor(getResources().getColor(R.color.white));
            germenText.setTextColor(getResources().getColor(R.color.black));

            arabic.setCardBackgroundColor(getResources().getColor(R.color.white));
            arabicText.setTextColor(getResources().getColor(R.color.black));

            portg.setCardBackgroundColor(getResources().getColor(R.color.white));
            portgText.setTextColor(getResources().getColor(R.color.black));

            italian.setCardBackgroundColor(getResources().getColor(R.color.white));
            italianText.setTextColor(getResources().getColor(R.color.black));

            urdu.setCardBackgroundColor(getResources().getColor(R.color.white));
            urduText.setTextColor(getResources().getColor(R.color.black));

            japans.setCardBackgroundColor(getResources().getColor(R.color.white));
            japansText.setTextColor(getResources().getColor(R.color.black));

            bengali.setCardBackgroundColor(getResources().getColor(R.color.orange));
            bengaliText.setTextColor(getResources().getColor(R.color.white));

            turkish.setCardBackgroundColor(getResources().getColor(R.color.white));
            turkishText.setTextColor(getResources().getColor(R.color.black));

            indona.setCardBackgroundColor(getResources().getColor(R.color.white));
            indonaText.setTextColor(getResources().getColor(R.color.black));

            russian.setCardBackgroundColor(getResources().getColor(R.color.white));
            russianText.setTextColor(getResources().getColor(R.color.black));

            sweden.setCardBackgroundColor(getResources().getColor(R.color.white));
            swedenText.setTextColor(getResources().getColor(R.color.black));
        }
        // Turkish
        else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("tr")) {
            hindi.setCardBackgroundColor(getResources().getColor(R.color.white));
            hindiText.setTextColor(getResources().getColor(R.color.black));

            english.setCardBackgroundColor(getResources().getColor(R.color.white));
            englishText.setTextColor(getResources().getColor(R.color.black));

            spanish.setCardBackgroundColor(getResources().getColor(R.color.white));
            spanishText.setTextColor(getResources().getColor(R.color.black));

            french.setCardBackgroundColor(getResources().getColor(R.color.white));
            frenchText.setTextColor(getResources().getColor(R.color.black));

            germen.setCardBackgroundColor(getResources().getColor(R.color.white));
            germenText.setTextColor(getResources().getColor(R.color.black));

            arabic.setCardBackgroundColor(getResources().getColor(R.color.white));
            arabicText.setTextColor(getResources().getColor(R.color.black));

            portg.setCardBackgroundColor(getResources().getColor(R.color.white));
            portgText.setTextColor(getResources().getColor(R.color.black));

            italian.setCardBackgroundColor(getResources().getColor(R.color.white));
            italianText.setTextColor(getResources().getColor(R.color.black));

            urdu.setCardBackgroundColor(getResources().getColor(R.color.white));
            urduText.setTextColor(getResources().getColor(R.color.black));

            japans.setCardBackgroundColor(getResources().getColor(R.color.white));
            japansText.setTextColor(getResources().getColor(R.color.black));

            bengali.setCardBackgroundColor(getResources().getColor(R.color.white));
            bengaliText.setTextColor(getResources().getColor(R.color.black));

            turkish.setCardBackgroundColor(getResources().getColor(R.color.orange));
            turkishText.setTextColor(getResources().getColor(R.color.white));

            indona.setCardBackgroundColor(getResources().getColor(R.color.white));
            indonaText.setTextColor(getResources().getColor(R.color.black));

            russian.setCardBackgroundColor(getResources().getColor(R.color.white));
            russianText.setTextColor(getResources().getColor(R.color.black));

            sweden.setCardBackgroundColor(getResources().getColor(R.color.white));
            swedenText.setTextColor(getResources().getColor(R.color.black));
        }
        // Indonesian
        else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("in")) {
            hindi.setCardBackgroundColor(getResources().getColor(R.color.white));
            hindiText.setTextColor(getResources().getColor(R.color.black));

            english.setCardBackgroundColor(getResources().getColor(R.color.white));
            englishText.setTextColor(getResources().getColor(R.color.black));

            spanish.setCardBackgroundColor(getResources().getColor(R.color.white));
            spanishText.setTextColor(getResources().getColor(R.color.black));

            french.setCardBackgroundColor(getResources().getColor(R.color.white));
            frenchText.setTextColor(getResources().getColor(R.color.black));

            germen.setCardBackgroundColor(getResources().getColor(R.color.white));
            germenText.setTextColor(getResources().getColor(R.color.black));

            arabic.setCardBackgroundColor(getResources().getColor(R.color.white));
            arabicText.setTextColor(getResources().getColor(R.color.black));

            portg.setCardBackgroundColor(getResources().getColor(R.color.white));
            portgText.setTextColor(getResources().getColor(R.color.black));

            italian.setCardBackgroundColor(getResources().getColor(R.color.white));
            italianText.setTextColor(getResources().getColor(R.color.black));

            urdu.setCardBackgroundColor(getResources().getColor(R.color.white));
            urduText.setTextColor(getResources().getColor(R.color.black));

            japans.setCardBackgroundColor(getResources().getColor(R.color.white));
            japansText.setTextColor(getResources().getColor(R.color.black));

            bengali.setCardBackgroundColor(getResources().getColor(R.color.white));
            bengaliText.setTextColor(getResources().getColor(R.color.black));

            turkish.setCardBackgroundColor(getResources().getColor(R.color.white));
            turkishText.setTextColor(getResources().getColor(R.color.black));

            indona.setCardBackgroundColor(getResources().getColor(R.color.orange));
            indonaText.setTextColor(getResources().getColor(R.color.white));

            russian.setCardBackgroundColor(getResources().getColor(R.color.white));
            russianText.setTextColor(getResources().getColor(R.color.black));

            sweden.setCardBackgroundColor(getResources().getColor(R.color.white));
            swedenText.setTextColor(getResources().getColor(R.color.black));
        }
        // Russian
        else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("ru")) {
            hindi.setCardBackgroundColor(getResources().getColor(R.color.white));
            hindiText.setTextColor(getResources().getColor(R.color.black));

            english.setCardBackgroundColor(getResources().getColor(R.color.white));
            englishText.setTextColor(getResources().getColor(R.color.black));

            spanish.setCardBackgroundColor(getResources().getColor(R.color.white));
            spanishText.setTextColor(getResources().getColor(R.color.black));

            french.setCardBackgroundColor(getResources().getColor(R.color.white));
            frenchText.setTextColor(getResources().getColor(R.color.black));

            germen.setCardBackgroundColor(getResources().getColor(R.color.white));
            germenText.setTextColor(getResources().getColor(R.color.black));

            arabic.setCardBackgroundColor(getResources().getColor(R.color.white));
            arabicText.setTextColor(getResources().getColor(R.color.black));

            portg.setCardBackgroundColor(getResources().getColor(R.color.white));
            portgText.setTextColor(getResources().getColor(R.color.black));

            italian.setCardBackgroundColor(getResources().getColor(R.color.white));
            italianText.setTextColor(getResources().getColor(R.color.black));

            urdu.setCardBackgroundColor(getResources().getColor(R.color.white));
            urduText.setTextColor(getResources().getColor(R.color.black));

            japans.setCardBackgroundColor(getResources().getColor(R.color.white));
            japansText.setTextColor(getResources().getColor(R.color.black));

            bengali.setCardBackgroundColor(getResources().getColor(R.color.white));
            bengaliText.setTextColor(getResources().getColor(R.color.black));

            turkish.setCardBackgroundColor(getResources().getColor(R.color.white));
            turkishText.setTextColor(getResources().getColor(R.color.black));

            indona.setCardBackgroundColor(getResources().getColor(R.color.white));
            indonaText.setTextColor(getResources().getColor(R.color.black));

            russian.setCardBackgroundColor(getResources().getColor(R.color.orange));
            russianText.setTextColor(getResources().getColor(R.color.white));

            sweden.setCardBackgroundColor(getResources().getColor(R.color.white));
            swedenText.setTextColor(getResources().getColor(R.color.black));
        }
        // Sweden
        else if (PrefHelper.getStringVal("locale", "en").equalsIgnoreCase("sv")) {
            hindi.setCardBackgroundColor(getResources().getColor(R.color.white));
            hindiText.setTextColor(getResources().getColor(R.color.black));

            english.setCardBackgroundColor(getResources().getColor(R.color.white));
            englishText.setTextColor(getResources().getColor(R.color.black));

            spanish.setCardBackgroundColor(getResources().getColor(R.color.white));
            spanishText.setTextColor(getResources().getColor(R.color.black));

            french.setCardBackgroundColor(getResources().getColor(R.color.white));
            frenchText.setTextColor(getResources().getColor(R.color.black));

            germen.setCardBackgroundColor(getResources().getColor(R.color.white));
            germenText.setTextColor(getResources().getColor(R.color.black));

            arabic.setCardBackgroundColor(getResources().getColor(R.color.white));
            arabicText.setTextColor(getResources().getColor(R.color.black));

            portg.setCardBackgroundColor(getResources().getColor(R.color.white));
            portgText.setTextColor(getResources().getColor(R.color.black));

            italian.setCardBackgroundColor(getResources().getColor(R.color.white));
            italianText.setTextColor(getResources().getColor(R.color.black));

            urdu.setCardBackgroundColor(getResources().getColor(R.color.white));
            urduText.setTextColor(getResources().getColor(R.color.black));

            japans.setCardBackgroundColor(getResources().getColor(R.color.white));
            japansText.setTextColor(getResources().getColor(R.color.black));

            bengali.setCardBackgroundColor(getResources().getColor(R.color.white));
            bengaliText.setTextColor(getResources().getColor(R.color.black));

            turkish.setCardBackgroundColor(getResources().getColor(R.color.white));
            turkishText.setTextColor(getResources().getColor(R.color.black));

            indona.setCardBackgroundColor(getResources().getColor(R.color.white));
            indonaText.setTextColor(getResources().getColor(R.color.black));

            russian.setCardBackgroundColor(getResources().getColor(R.color.white));
            russianText.setTextColor(getResources().getColor(R.color.black));

            sweden.setCardBackgroundColor(getResources().getColor(R.color.orange));
            swedenText.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }



   

}