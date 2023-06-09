package in.whatsaga.whatsapplongerstatus.status.uploader.whatsaga;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import in.whatsaga.whatsapplongerstatus.status.uploader.R;

public class PermissionActivity extends AppCompatActivity {

    private PermissionActivity mCurrentActivity;
    int RC_WRITE_PERMISSION = 2;
    int RC_READ_PERMISSION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_permission);

        mCurrentActivity = this;

        findViewById(R.id.done).setOnClickListener(view -> requestReadPermission());
        findViewById(R.id.skip).setOnClickListener(v -> {
            startActivity(new Intent(PermissionActivity.this, MainAppActivity.class));
            finish();
        });
    }

    private boolean hasReadPermission() {
        return ContextCompat.checkSelfPermission(mCurrentActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadPermission() {
        ActivityCompat.requestPermissions(mCurrentActivity,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                RC_READ_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_READ_PERMISSION) {
            if (!hasReadPermission()) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(mCurrentActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showPermissionDeniedDialog();
                } else {
                    showNeverAskDialog();
                }
            } else {
                startActivity(new Intent(PermissionActivity.this, MainAppActivity.class));
                Common.startAnimation(this);
                finish();
            }
        }
    }

    private void showPermissionDeniedDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        String message = "You have denied permission. Read storage permission needed to select pictures from storage.";
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestReadPermission();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialogBuilder.show();
    }

    private void showNeverAskDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        String message = "You have selected never ask permission. Allow permission manually from settings.";
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openSettings(mCurrentActivity);
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialogBuilder.show();
    }

    public static void openSettings(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }

    private void showConfirmationDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(mCurrentActivity).create();
        alertDialog.setTitle("Sure!");
        alertDialog.setMessage("The video making progress will lost. Are you sure?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        discard = true;
                        onBackPressed();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
