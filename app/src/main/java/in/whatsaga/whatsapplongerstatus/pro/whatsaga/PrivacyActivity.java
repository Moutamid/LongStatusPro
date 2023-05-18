package in.whatsaga.whatsapplongerstatus.pro.whatsaga;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import in.whatsaga.whatsapplongerstatus.pro.R;

public class PrivacyActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    SharePref sharedPref;
    CheckBox checkBox;
    Button goBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        sharedPref = new SharePref(this);
        TextView textView = findViewById(R.id.privacy);
        goBtn = findViewById(R.id.go);
        checkBox = findViewById(R.id.check);

        if (checkBox.isChecked()) {
            goBtn.setEnabled(true);
            goBtn.setBackground(getResources().getDrawable(R.drawable.button_click));
        } else {
            goBtn.setEnabled(false);
            goBtn.setBackground(getResources().getDrawable(R.drawable.button_off));
        }

        checkBox.setOnCheckedChangeListener(this);

        SpannableString ss = new SpannableString("We are committed to bringing users a better experience in all aspects and will update from time to time Terms of Use and Privacy Policy");
        ss.setSpan(new MyClickableSpan(), 104, 116, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new MyClickableSpanTwo(), 121, 135, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        findViewById(R.id.termOfUse).setOnClickListener(v -> {
            checkBox.setChecked(!checkBox.isChecked());
            if (checkBox.isChecked()) {
                goBtn.setEnabled(true);
                goBtn.setBackground(getResources().getDrawable(R.drawable.button_click));
            } else {
                goBtn.setEnabled(false);
                goBtn.setBackground(getResources().getDrawable(R.drawable.button_off));
            }
        });

        goBtn.setOnClickListener(v -> {
            sharedPref.setIntro(true);
            if (!PermissionUtils.hasReadStoragePermission(this)) {
                startActivity(new Intent(PrivacyActivity.this, PermissionActivity.class));
            } else {
                startActivity(new Intent(PrivacyActivity.this, MainAppActivity.class));
            }
            finish();
        });
    }

    void showPolicy() {

        try {
            final Dialog dialog = new Dialog(PrivacyActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.privacy_layout);
            dialog.setCancelable(true);
            Button gotit = (Button) dialog.findViewById(R.id.gotIt);

            TextView textView = (TextView) dialog.findViewById(R.id.textView);
            textView.setText(Html.fromHtml(readFromAssets(PrivacyActivity.this, "privacy.txt")));

            gotit.setOnClickListener(v -> dialog.cancel());
            dialog.show();
        } catch (Exception e) {
        }
    }

    void showTermOfUse() {
        try {
            final Dialog dialog = new Dialog(PrivacyActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.privacy_layout);
            dialog.setCancelable(true);
            Button gotit = (Button) dialog.findViewById(R.id.gotIt);

            TextView textView = (TextView) dialog.findViewById(R.id.textView);
            textView.setText(Html.fromHtml(readFromAssets(PrivacyActivity.this, "termOfUse.txt")));

            gotit.setOnClickListener(v -> dialog.cancel());
            dialog.show();
        } catch (Exception e) {
        }
    }

    public String readFromAssets(Context context, String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(filename), Charset.forName("UTF-8")));
        // do reading, usually loop until end of file reading
        StringBuilder sb = new StringBuilder();
        String mLine = reader.readLine();
        while (mLine != null) {
            sb.append(mLine); // process line
            mLine = reader.readLine();
        }
        reader.close();
        return sb.toString();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            goBtn.setBackground(getResources().getDrawable(R.drawable.button_click));
        } else {
            goBtn.setBackground(getResources().getDrawable(R.drawable.button_off));
        }
        goBtn.setEnabled(isChecked);
    }

    class MyClickableSpan extends ClickableSpan { //clickable span
        public void onClick(View textView) {
            showTermOfUse();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.colorPrimary));//set text color
            ds.setUnderlineText(true); // set to false to remove underline
        }
    }

    class MyClickableSpanTwo extends ClickableSpan { //clickable span
        public void onClick(View textView) {
            showPolicy();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.colorPrimary));//set text color
            ds.setUnderlineText(true); // set to false to remove underline
        }
    }
}