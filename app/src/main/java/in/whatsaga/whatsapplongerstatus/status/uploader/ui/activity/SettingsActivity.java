package in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity;

import static in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.AllPermissionsActivity.ACTION_NOTIFICATION_LISTENER_SETTINGS;

import android.Manifest;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.fxn.stash.Stash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import in.whatsaga.whatsapplongerstatus.status.uploader.R;
import in.whatsaga.whatsapplongerstatus.status.uploader.adsense.Ads;
import in.whatsaga.whatsapplongerstatus.status.uploader.utils.Common;
import in.whatsaga.whatsapplongerstatus.status.uploader.utils.Constants;
import in.whatsaga.whatsapplongerstatus.status.uploader.utils.SharedPref;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    SharedPref sharedPref;
    SwitchCompat notification;
    SwitchCompat deletedNotification;
    final private int POST_NOTIFICATION = 32;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Common.setLocale(this);
        setContentView(R.layout.activity_setting);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPref = new SharedPref(this);

        notification = findViewById(R.id.notification);
        notification.setChecked(sharedPref.getNotification());
        notification.setOnCheckedChangeListener(this);

        deletedNotification = findViewById(R.id.deleted_notification);
        deletedNotification.setChecked(sharedPref.getDeletedNotificationAlert());
        deletedNotification.setOnCheckedChangeListener(this);

        findViewById(R.id.share).setOnClickListener(this);
        findViewById(R.id.rate).setOnClickListener(this);
        findViewById(R.id.languages).setOnClickListener(this);
        findViewById(R.id.privacy).setOnClickListener(this);
        findViewById(R.id.restartService).setOnClickListener(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!permissionCheck(this)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, POST_NOTIFICATION);
            }
        }

    }

    public static boolean permissionCheck(Context context) {
        if (Build.VERSION.SDK_INT >= 33) {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.restartService:

                startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
                break;
            case R.id.languages:

                LanguageActivity.start(this);
                finish();
                break;

            case R.id.rate:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                break;

            case R.id.privacy:
                showPolicy();
                break;

            case R.id.share:
                shareAppLink();
                break;

        }
    }

    private void shareAppLink() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Long Status and RDM \n https://play.google.com/store/apps/details?id=" + getPackageName() + "");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "share via"));
    }

    private static void autoLaunchVivo(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"));
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
                context.startActivity(intent);
            } catch (Exception ex) {
                try {
                    Intent intent = new Intent();
                    intent.setClassName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager");
                    context.startActivity(intent);
                } catch (Exception exx) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void allowStartUpManger() {
        autoLaunchVivo(this);
        if (Build.MANUFACTURER.equalsIgnoreCase("oppo")) {
            try {
                Intent intent = new Intent();
                intent.setClassName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity");
                startActivity(intent);
            } catch (Exception e) {
                try {
                    Intent intent = new Intent();
                    intent.setClassName("com.oppo.safe", "com.oppo.safe.permission.startup.StartupAppListActivity");
                    startActivity(intent);

                } catch (Exception ex) {
                    try {
                        Intent intent = new Intent();
                        intent.setClassName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity");
                        startActivity(intent);
                    } catch (Exception exx) {

                    }
                }
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.alread_allowed), Toast.LENGTH_SHORT).show();
        }
    }

    void showPolicy() {
        try {
            final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.privacy_layout);
            dialog.setCancelable(true);
            Button gotit = dialog.findViewById(R.id.gotIt);

            TextView textView = dialog.findViewById(R.id.textView);
            textView.setText(Html.fromHtml(readFromAssets(this, "privacy.txt")));

            gotit.setOnClickListener(v -> dialog.cancel());
            dialog.show();
        } catch (Exception ignored) {
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.notification:
                sharedPref.setNotification(isChecked);
                break;

            case R.id.deleted_notification:
                sharedPref.setDeletedNotificationAlert(isChecked);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if (Stash.getBoolean(Constants.IS_PRO, false)) {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else {
            Ads.loadIntersAD(this, this, MainActivity.class);
        }
    }


}
