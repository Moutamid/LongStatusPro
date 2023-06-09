package in.whatsaga.whatsapplongerstatus.status.uploader.whatsaga;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import in.whatsaga.whatsapplongerstatus.status.uploader.R;

public class SettingsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener{

    RadioGroup radioGroup;
    SharePref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_uploader);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        sharePref = new SharePref(this);
        radioGroup = findViewById(R.id.radio);
        radioGroup.setOnCheckedChangeListener(this);
        if (sharePref.getDuration() == 29000) {
            radioGroup.check(R.id.radio1);
        } else {
            radioGroup.check(R.id.radio2);
        }
        findViewById(R.id.rate).setOnClickListener(this);
        findViewById(R.id.share).setOnClickListener(this);
        findViewById(R.id.privacy).setOnClickListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio1:
                sharePref.setDuration(29000);
                break;

            case R.id.radio2:

                sharePref.setDuration(15000);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rate:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                break;

            case R.id.share:
                shareAppLink();
                break;

            case R.id.privacy:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.Privacy_url)));
                startActivity(intent);
                break;
        }
    }


    private void shareAppLink() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Upload full status video here \n https://play.google.com/store/apps/details?id=" + getPackageName() + "");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "share via"));
    }

    public String readFromAssets(Context context, String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(filename), Charset.forName("UTF-8")));
        StringBuilder sb = new StringBuilder();
        String mLine = reader.readLine();
        while (mLine != null) {
            sb.append(mLine); // process line
            mLine = reader.readLine();
        }
        reader.close();
        return sb.toString();
    }

    void showPolicy() {

        try {
            final Dialog dialog = new Dialog(SettingsActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.privacy_layout);
            dialog.setCancelable(true);
            Button gotit = (Button) dialog.findViewById(R.id.gotIt);

            TextView textView = (TextView) dialog.findViewById(R.id.textView);
            textView.setText(Html.fromHtml(readFromAssets(SettingsActivity.this, "privacy.txt")));

            gotit.setOnClickListener(v -> dialog.cancel());
            dialog.show();
        } catch (Exception e) {
        }
    }
    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


    //Back press method
    public void onBackPressed() {

        startActivity(new Intent(SettingsActivity.this, MainAppActivity.class));

    }
}