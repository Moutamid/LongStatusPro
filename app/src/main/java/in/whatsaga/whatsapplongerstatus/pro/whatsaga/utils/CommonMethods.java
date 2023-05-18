package in.whatsaga.whatsapplongerstatus.pro.whatsaga.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.content.FileProvider;
import in.whatsaga.whatsapplongerstatus.pro.BuildConfig;


import java.io.File;



public class CommonMethods {

    public static void shareApp(Context context, String message) {
        final String appLink = "\nhttps://play.google.com/store/apps/details?id=" + context.getPackageName();
        Intent sendInt = new Intent(Intent.ACTION_SEND);
        sendInt.putExtra(Intent.EXTRA_SUBJECT, message);
        sendInt.putExtra(Intent.EXTRA_TEXT, message + appLink);
        sendInt.setType("text/plain");
        context.startActivity(Intent.createChooser(sendInt, "Share"));
    }

    public static void rateApp(Context context) {
        final String appName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appName)));
        } catch (ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
        }
    }

    public static boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void shareFile(Context context, Uri uri, String mediaType) {
        Intent shareIntent = new Intent();
        shareIntent.setAction("android.intent.action.SEND");
        shareIntent.putExtra("android.intent.extra.STREAM", uri);
        shareIntent.setType(mediaType);
        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(shareIntent, "Share File"));
    }

    public static void shareFile(Context context, File file, String mediaType) {
        Intent shareIntent = new Intent();
        Uri uri = FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider", file);
        shareIntent.setAction("android.intent.action.SEND");
        shareIntent.putExtra("android.intent.extra.STREAM", uri);
        shareIntent.setType(mediaType);
        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(shareIntent, "Share File"));
    }
}