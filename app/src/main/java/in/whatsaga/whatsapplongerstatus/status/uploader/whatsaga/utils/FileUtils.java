package in.whatsaga.whatsapplongerstatus.status.uploader.whatsaga.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import in.whatsaga.whatsapplongerstatus.status.uploader.R;

public class FileUtils {
    public static final String ROOT_PATH = Environment.getExternalStorageDirectory().getPath();
    public static File whatsAppStatusFolder = new File(ROOT_PATH + "/WhatsApp/Media/.Statuses");
    public static File whatsAppBusinessStatusFolder = new File(ROOT_PATH + "/WhatsApp Business/Media/.Statuses");

    public static File saveFile(Context context, String app, Uri pathFrom, String extension) throws IOException {
        File file = getOutputMediaFile(context, app, extension);
        Uri pathTo = Uri.fromFile(file);
        try (InputStream in = context.getContentResolver().openInputStream(pathFrom)) {
            if (in == null) return null;
            try (OutputStream out = context.getContentResolver().openOutputStream(pathTo)) {
                if (out == null) return null;
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
        return file;
    }

    public static File getOutputMediaFile(Context context, String app, String extension) {
        File mediaStorageDir = getAppFolder(context, app);
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        File mediaFile;
        String mImageName = "status_saver" + System.currentTimeMillis() + extension;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public static File getAppFolder(Context context, String app) {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + "/" + context.getResources().getString(R.string.app_name) + "_" + app);
    }
}
