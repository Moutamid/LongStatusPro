package in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.status;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.google.android.ads.nativetemplates.TemplateView;

import java.io.File;

import in.whatsaga.whatsapplongerstatus.status.uploader.R;
import in.whatsaga.whatsapplongerstatus.status.uploader.adsense.Ads;
import in.whatsaga.whatsapplongerstatus.status.uploader.utils.Common;
import in.whatsaga.whatsapplongerstatus.status.uploader.utils.Constants;
import khangtran.preferenceshelper.PrefHelper;

public class StatusMainActivity extends AppCompatActivity implements View.OnClickListener {

    private Dialog folderPermissionWhatsapp;
    private Dialog folderPermissionBusiness;

    Dialog dialog;
    int value = 0;

    private String TAG = "MainActivity";

 


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_main);
        Ads.calledIniti(this);

        TemplateView v1 = findViewById(R.id.my_template);
        if (!Stash.getBoolean(Constants.IS_PRO, false)) {
            Ads.showNativeAd(this, v1);
        } else {
            v1.setVisibility(View.GONE);
        }


        dialog = new Dialog(this);

        folderPermissionWhatsapp();
        folderPermissionBusiness();

        findViewById(R.id.whatsapp).setOnClickListener(this);
        findViewById(R.id.business).setOnClickListener(this);
        findViewById(R.id.downloads).setOnClickListener(this);
    }






    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.downloads:
                if (Stash.getBoolean(Constants.IS_PRO, false)) {
                    startActivity(new Intent(this, DownloadsActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else {
                    Ads.loadIntersAD(StatusMainActivity.this, StatusMainActivity.this, DownloadsActivity.class);
                }

                break;

            case R.id.whatsapp:
                    if (Common.isRorAbove()) {
                        if (!PrefHelper.getBooleanVal("Permission")) {
                            folderPermissionWhatsapp.show();
                        } else {
                            if (Stash.getBoolean(Constants.IS_PRO, false)) {
                                startActivity(new Intent(this, WhatsappActivity.class));
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            } else {
                                Ads.loadIntersAD(StatusMainActivity.this, StatusMainActivity.this, WhatsappActivity.class);
                            }
                        }
                    } else {
                        if (Stash.getBoolean(Constants.IS_PRO, false)) {
                            startActivity(new Intent(this, WhatsappActivity.class));
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        } else {
                            Ads.loadIntersAD(StatusMainActivity.this, StatusMainActivity.this, WhatsappActivity.class);
                        }
                    }

                break;

            case R.id.business:

                if (Common.isRorAbove()) {
                    if (!PrefHelper.getBooleanVal("PermissionBusiness")) {
                        folderPermissionBusiness.show();
                    } else {
                        startActivity(new Intent(StatusMainActivity.this, BusinessActivity.class));
                    }
                } else {
                    startActivity(new Intent(StatusMainActivity.this, BusinessActivity.class));
                }

                break;


        }
    }

    public void openDirectoryForPermissionBusiness() {
        String sb = Environment.getExternalStorageDirectory() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media";
        String str = new File(sb).exists() ? "Android%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp Business%2FMedia" : "WhatsApp Business%2FMedia";
        Intent createOpenDocumentTreeIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            createOpenDocumentTreeIntent = ((StorageManager) getSystemService(Context.STORAGE_SERVICE))
                    .getPrimaryStorageVolume().createOpenDocumentTreeIntent();
        }
        String uri2 = createOpenDocumentTreeIntent.getParcelableExtra("android.provider.extra.INITIAL_URI").toString();
        Log.d("TAG", "INITIAL_URI scheme: " + uri2);
        String replace = uri2.replace("/root/", "/document/");
        Uri parse = Uri.parse(replace + "%3A" + str);
        createOpenDocumentTreeIntent.putExtra("android.provider.extra.INITIAL_URI", parse);
        Log.d("TAG", "uri: " + parse.toString());
        try {
            startActivityForResult(createOpenDocumentTreeIntent, 10);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void openDirectoryForPermission() {
        String sb = Environment.getExternalStorageDirectory() + "/Android/media/com.whatsapp/WhatsApp/Media";
        String str = new File(sb).exists() ? "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia" : "WhatsApp%2FMedia";
        Intent createOpenDocumentTreeIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            createOpenDocumentTreeIntent = ((StorageManager) getSystemService(Context.STORAGE_SERVICE))
                    .getPrimaryStorageVolume().createOpenDocumentTreeIntent();
        }
        String uri2 = createOpenDocumentTreeIntent.getParcelableExtra("android.provider.extra.INITIAL_URI").toString();
        Log.d("TAG", "INITIAL_URI scheme: " + uri2);
        String replace = uri2.replace("/root/", "/document/");
        Uri parse = Uri.parse(replace + "%3A" + str);
        createOpenDocumentTreeIntent.putExtra("android.provider.extra.INITIAL_URI", parse);
        Log.d("TAG", "uri: " + parse.toString());
        try {
            startActivityForResult(createOpenDocumentTreeIntent, 6);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void folderPermissionWhatsapp() {
        folderPermissionWhatsapp = new Dialog(this);
        folderPermissionWhatsapp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        folderPermissionWhatsapp.setContentView(R.layout.folder_permission);
        folderPermissionWhatsapp.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        folderPermissionWhatsapp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        folderPermissionWhatsapp.getWindow().setGravity(Gravity.CENTER);
        folderPermissionWhatsapp.findViewById(R.id.yes).setOnClickListener(v -> {
            openDirectoryForPermission();
            folderPermissionWhatsapp.dismiss();

        });
        folderPermissionWhatsapp.findViewById(R.id.no).setOnClickListener(v -> {
            folderPermissionWhatsapp.dismiss();
        });
    }

    private void folderPermissionBusiness() {
        folderPermissionBusiness = new Dialog(this);
        folderPermissionBusiness.requestWindowFeature(Window.FEATURE_NO_TITLE);
        folderPermissionBusiness.setContentView(R.layout.folder_permission);
        folderPermissionBusiness.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        folderPermissionBusiness.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        folderPermissionBusiness.getWindow().setGravity(Gravity.CENTER);

        folderPermissionBusiness.findViewById(R.id.yes).setOnClickListener(v -> {
            openDirectoryForPermissionBusiness();
            folderPermissionBusiness.dismiss();

        });
        folderPermissionBusiness.findViewById(R.id.no).setOnClickListener(v -> {
            folderPermissionBusiness.dismiss();
        });
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 6) {
                Uri uri = data.getData();
                if (uri.getPath().endsWith("Media")) {
                    int takeFlags = data.getFlags()
                            & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                            | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    // Check for the freshest data.
                    getContentResolver().takePersistableUriPermission(uri, takeFlags);
                    PrefHelper.setVal("Permission", true);
                    PrefHelper.setVal("PersistentPath", uri.toString());
                    startActivity(new Intent(this, WhatsappActivity.class));
                    return;
                }
                Toast.makeText(this, "Please give right path", Toast.LENGTH_LONG).show();
            } else if (requestCode == 10) {
                Uri uri = data.getData();
                if (uri.getPath().endsWith("Media")) {
                    int takeFlags = data.getFlags()
                            & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                            | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    // Check for the freshest data.
                    getContentResolver().takePersistableUriPermission(uri, takeFlags);
                    PrefHelper.setVal("PermissionBusiness", true);
                    PrefHelper.setVal("PersistentPathBusiness", uri.toString());
                    startActivity(new Intent(this, BusinessActivity.class));
                    return;
                }
                Toast.makeText(this, "Please give right path", Toast.LENGTH_LONG).show();
            }
        }

    }


    public void onBackPressed() {
        super.onBackPressed();
    }

}
