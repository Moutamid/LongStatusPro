package in.whatsaga.whatsapplongerstatus.status.uploader.whatsaga;


import static in.whatsaga.whatsapplongerstatus.status.uploader.whatsaga.Common.getPath;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import in.whatsaga.whatsapplongerstatus.status.uploader.R;
import in.whatsaga.whatsapplongerstatus.status.uploader.adsense.Ads;
import in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.MainActivity;


public class MainAppActivity extends AppCompatActivity implements View.OnClickListener {
    private RewardedInterstitialAd rewardedInterstitialAd;
    private static final int REQUEST_TAKE_GALLERY_VIDEO = 32;
    private static final int REQUEST_LOAD_VIDEOS = 100;
    public static int REQUEST_DOWNLOADS = 4;
    private ArrayList<Uri> videoPaths = new ArrayList<>();
    public static int REQUEST_FILES = 5;
    private final String TAG = "MainAppActivity1233";

    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_first);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Common.mkDirs();


        Ads.calledIniti(this);

        // showRewardAd();
        findViewById(R.id.upload).setOnClickListener(this);
        findViewById(R.id.settings).setOnClickListener(this);

    }

    boolean doubleBackToExitPressedOnce = false;


    private void requestReadPermission(int id) {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                id);
    }

    private boolean hasReadPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void showRewardAd() {

        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i(TAG, android_id);
        List<String> testDeviceIds = Arrays.asList(android_id);
        RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);
        MobileAds.initialize(this, initializationStatus -> {

        });
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload:


                if (Common.isRorAbove()) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    MainAppActivity.this.startActivityForResult(intent, REQUEST_LOAD_VIDEOS);
                } else {

                    upload();

                }

                break;


            case R.id.settings:

                startActivity(new Intent(MainAppActivity.this, SettingsActivity.class));


                break;


        }
    }


    private void upload() {
        String str8 = "Please select Video";
        String str9 = "MP4";
        String str10 = ".mp4";
        FilePickerBuilder.getInstance().setMaxCount(1).setSelectedFiles(this.videoPaths)
                .addFileSupport(str9, new String[]{str10})
                .enableVideoPicker(true).enableImagePicker(false)
                .enableCameraSupport(false).setActivityTitle(str8)
                .setActivityTheme(R.style.LibAppTheme)
                .enableDocSupport(false)
                .enableSelectAll(false)
                .pickPhoto(this, REQUEST_TAKE_GALLERY_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {

                FilePickerBuilder.getInstance().setMaxCount(1).setSelectedFiles(this.videoPaths)
                        .addFileSupport("MP4", new String[]{".mp4"})
                        .enableVideoPicker(true)
                        .enableImagePicker(false).enableCameraSupport(false)
                        .setActivityTitle("Please select device Video")
                        .setActivityTheme(R.style.LibAppTheme)
                        .enableDocSupport(false)
                        .enableSelectAll(false)
                        .pickPhoto(this, REQUEST_TAKE_GALLERY_VIDEO);
                try {
                    this.videoPaths = new ArrayList<>();
                    this.videoPaths = (ArrayList) data.getSerializableExtra(FilePickerConst.KEY_SELECTED_MEDIA);
                    Intent intent2 = new Intent(this, UploadActivity.class);
                    intent2.putExtra("path", this.videoPaths.get(0).toString());
                    startActivity(intent2);
                    Common.startAnimation(this);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Please retry to select video...!", Toast.LENGTH_LONG).show();
                }

            } else if (requestCode == REQUEST_LOAD_VIDEOS) {
                Uri selectedVideo = null;
                selectedVideo = data.getData();
                Log.i("TAG", "image load");
                if (selectedVideo == null) {
                    Toast.makeText(MainAppActivity.this, "Image not supported", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Intent intent = new Intent(MainAppActivity.this, UploadActivity.class);
                        String filePath = getPath(getApplicationContext(), selectedVideo);
                        intent.putExtra("path", filePath);
                        startActivity(intent);
                        Common.startAnimation(this);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Please retry to select video...!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_DOWNLOADS) {
            if (!hasReadPermission()) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showPermissionDeniedDialog(REQUEST_DOWNLOADS);
                } else {
                    showNeverAskDialog();
                }
            }  //                startActivity(new Intent(this, StatusMainActivity.class));

        } else if (requestCode == REQUEST_FILES) {
            if (!hasReadPermission()) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showPermissionDeniedDialog(REQUEST_DOWNLOADS);
                } else {
                    showNeverAskDialog();
                }
            } else {
                if (Common.isRorAbove()) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    MainAppActivity.this.startActivityForResult(intent, REQUEST_LOAD_VIDEOS);
                } else {
                    upload();
                }
            }
        }
    }

    public static void openSettings(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }

    private void showPermissionDeniedDialog(int id) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        String message = "You have denied permission. Read storage permission needed to recover Media into device storage.";
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Allow", (dialogInterface, i) -> requestReadPermission(id));

        alertDialogBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialogBuilder.show();
    }

    private void showNeverAskDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        String message = "You have selected never ask permission. Allow permission manually from settings.";
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Settings", (dialogInterface, i) -> openSettings(MainAppActivity.this));
        alertDialogBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialogBuilder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        in.whatsaga.whatsapplongerstatus.status.uploader.utils.Common.setLocale(this);

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    public void onBackPressed() {

        startActivity(new Intent(MainAppActivity.this, MainActivity.class));
    }
}