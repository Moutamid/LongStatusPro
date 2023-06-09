package in.whatsaga.whatsapplongerstatus.status.uploader.whatsaga.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class StorageUtils {
    public static final String storagePermission = isSDKGreaterThan29()
            ? Manifest.permission.READ_EXTERNAL_STORAGE
            : Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final int REQUEST_CODE_STORAGE_PERMISSION = 888;

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
    public static boolean isSDKGreaterOr29() {
        return Build.VERSION.SDK_INT >= 29;
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
    public static boolean isSDKGreaterThan29() {
        return Build.VERSION.SDK_INT > 29;
    }

    public static boolean hasStoragePermission(Context context) {
        return ContextCompat.checkSelfPermission(context, storagePermission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestStoragePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{storagePermission}, REQUEST_CODE_STORAGE_PERMISSION);
    }
}