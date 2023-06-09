package in.whatsaga.whatsapplongerstatus.status.uploader.utils;

import static android.os.Environment.DIRECTORY_DCIM;

import static in.whatsaga.whatsapplongerstatus.status.uploader.BuildConfig.APPLICATION_ID;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;
import androidx.documentfile.provider.DocumentFile;

import in.whatsaga.whatsapplongerstatus.status.uploader.App;
import in.whatsaga.whatsapplongerstatus.status.uploader.R;
import in.whatsaga.whatsapplongerstatus.status.uploader.models.MediaModel;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import khangtran.preferenceshelper.PrefHelper;


public class Common {

    static List<Character> circleList;
    static List<Character> turnedList;
    static List<Integer> filledCircleList;
    static List<Integer> negativeSquaredList;
    static List<Integer> notFilledSquaredList;
    static List<Integer> doubleStruckList;
    static List<Integer> scriptList;
    static List<Integer> boldScriptList;
    public static String STYLISH_TEXT = "STYLISH_TEXT";

    public static void setLocale(Context context) {
        Locale locale = new Locale(PrefHelper.getStringVal("locale", "en"));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }

    private static final String TAG = "TAG";
    public static File whatsAppFolderImage = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/WhatsApp Images");

    public static File whatsAppFolderDocs = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/WhatsApp Documents");

    public static File whatsAppFolderStatus = new File(Environment.getExternalStorageDirectory().getPath() +
            "/WhatsApp/Media/.Statuses");
    public static File Imagefolder = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/WhatsApp Images");
    public static File Imagefolder_private = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/WhatsApp Images/private");
    public static File Videofolder = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/WhatsApp Video");
    public static File Videofolder_private = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/WhatsApp Video/private");
    public static File Audiofolder = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/WhatsApp Audio");
    public static File Audiofolder_private = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/WhatsApp Audio/private");
    public static File Voicefolder = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/WhatsApp Voice Notes");
    public static File Docfolder = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/WhatsApp Documents");
    public static File Statusfolder = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/.Statuses");

    //android 11
    public static File whatsAppFolderStatus11 = new File(Environment.getExternalStorageDirectory().getPath() +
            "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses");
    public static File Imagefolder11 = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Images");
    public static File Videofolder11 = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Video");
    public static File Audiofolder11 = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Audio");
    public static File Voicefolder11 = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Voice Notes");


    ///BackUp target folders
    public static File VideoBackupfolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Whats Recovery/Media/WhatsRecovery Video");
    public static File ImageBackupfolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Whats Recovery/Media/WhatsRecovery Images");


    public static void createFolders() {
        Common.ImageBackupfolder.mkdirs();
        Common.Voicebackupfolder.mkdirs();
        Common.VideoBackupfolder.mkdirs();
        Common.ImageRecovery.mkdirs();
        Common.Statusbackupfolder.mkdirs();
    }


//    public static File ImageBackupfolder = new File(Environment.getExternalStorageDirectory().getPath() + "/.Whats Recovery/Media/WhatsRecovery Images");
//    public static File VideoBackupfolder = new File(Environment.getExternalStorageDirectory().getPath() + "/.Whats Recovery/Media/WhatsRecovery Video");


    public static File Voicebackupfolder = new File(Environment.getExternalStorageDirectory().getPath() + "/Whats Delete/Media/WhatsDelete Voice Notes");


//    public static File Statusbackupfolder = new File(Environment.getExternalStorageDirectory().getPath() + "/Whats Delete/Media/WhatsDelete Statuses");

    public static File Statusbackupfolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Whats Delete/Media/Status Saver");


    //    public static File ImageRecovery = new File(Environment.getExternalStorageDirectory().getPath() + "/Recovery/Media/.Recovery Images");
    public static File ImageRecovery = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM) + "/Recovery/Media/Recovery");

    //Business Whatsapp
    public static File Imagefolder_B = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp Business/Media/WhatsApp Business Images");
    public static File Imagefolder_private_B = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp Business/Media/WhatsApp Business Images/private");
    public static File Videofolder_B = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp Business/Media/WhatsApp Business Video");
    public static File Audiofolder_B = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp Business/Media/WhatsApp Business Audio");
    public static File Voicefolder_B = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp Business/Media/WhatsApp Business Voice Notes");
    public static File Docfolder_B = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp Business/Media/WhatsApp Business Documents");
    public static File Statusfolder_B = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp Business/Media/.Statuses");
    public static File whatsAppFolderStatus_B = new File(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp Business/Media/.Statuses");


    //android 11 business whatsapp
    public static File Imagefolder_B11 = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/WhatsApp Business Images");
    public static File Videofolder_B11 = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/WhatsApp Business Video");
    public static File Audiofolder_B11 = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/WhatsApp Business Audio");
    public static File Voicefolder_B11 = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/WhatsApp Business Voice Notes");
    public static File whatsAppFolderStatus_B11 = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/.Statuses");


    //Business Whatsapp Backup
    public static File ImageBackupfolder_B = new File(Environment.getExternalStorageDirectory().getPath() + "/Business Whats Delete/Media/WhatsDelete Images");
    public static File VideoBackupfolder_B = new File(Environment.getExternalStorageDirectory().getPath() + "/Business Whats Delete/Media/WhatsDelete Video");
    public static File AudiobackUpfolder_B = new File(Environment.getExternalStorageDirectory().getPath() + "/Business Whats Delete/Media/WhatsDelete Audio");
    public static File Voicebackupfolder_B = new File(Environment.getExternalStorageDirectory().getPath() + "/Business Whats Delete/Media/WhatsDelete Voice Notes");
    public static File Docbackupfolder_B = new File(Environment.getExternalStorageDirectory().getPath() + "/Business Whats Delete/Media/WhatsDelete Documents");
    public static File Statusbackupfolder_B = new File(Environment.getExternalStorageDirectory().getPath() + "/Business Whats Delete/Media/WhatsDelete Statuses");


//    public static File profilePath = new File(Environment.getExternalStorageDirectory().getPath()
//            + "/Android/data/Unseen/files/Profile Pics");

    public static File profilePath = new File(App.context.getFilesDir().getPath()
            + "/Profile Pics");

    static char[] letterList = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static String PATH = "PATH";
    public static int POSITION = -1;

    public static final int FILE_COPIED = 1;
    public static final int FILE_EXISTS = 2;
    public static final File folder = new File(
            Environment.getExternalStorageDirectory(), "Unseen WhatsApp Status"
    );
    private static List<String> voiceList;
    private static File[] files;

    public static void mkDirs() {
        if (folder.mkdirs())
            Log.i(TAG, "Folder created");

    }

    public static void mkDirs(File file) {
        if (file.mkdirs())
            Log.i(TAG, "Folder created");

    }

    public static int copyImage(File file) {
        mkDirs();
        return copyFile(file, new File(Statusbackupfolder, file.getName()));
    }

    public static int copyImage(File file, File dest) {
        mkDirs(dest);
        return copyFile(file, new File(dest, file.getName()));
    }

    public static List<Character> getCircleList() {
        circleList = new ArrayList<>();
        circleList.clear();
        circleList.add('\u24b6'); //a
        circleList.add('\u24b7'); //b
        circleList.add('\u24b8'); //c
        circleList.add('\u24b9'); //d
        circleList.add('\u24ba'); //e
        circleList.add('\u24bb'); //f
        circleList.add('\u24bc'); //g
        circleList.add('\u24bd'); //h
        circleList.add('\u24be'); //i
        circleList.add('\u24bf'); //j
        circleList.add('\u24c0'); //k
        circleList.add('\u24c1'); //l
        circleList.add('\u24c2'); //m
        circleList.add('\u24c3'); //n
        circleList.add('\u24c4'); //o
        circleList.add('\u24c5'); //p
        circleList.add('\u24c6'); //q
        circleList.add('\u24c7'); //r
        circleList.add('\u24c8'); //s
        circleList.add('\u24c9'); //t
        circleList.add('\u24ca'); //u
        circleList.add('\u24cb'); //v
        circleList.add('\u24cc'); //w
        circleList.add('\u24cd'); //x
        circleList.add('\u24ce'); //y
        circleList.add('\u24cf'); //z

        return circleList;
    }

    public static List<Character> getTurnedList() {
        turnedList = new ArrayList<>();
        turnedList.clear();
        turnedList.add('\u0250'); //a
        turnedList.add('\u0071'); //b
        turnedList.add('\u0254'); //c
        turnedList.add('\u0070'); //d
        turnedList.add('\u01DD'); //e
        turnedList.add('\u025F'); //f
        turnedList.add('\u0182'); //g
        turnedList.add('\u0265'); //h
        turnedList.add('\u1D09'); //i
        turnedList.add('\u027E'); //j
        turnedList.add('\u029E'); //k
        turnedList.add('\u0131'); //l
        turnedList.add('\u026F'); //m
        turnedList.add('\u0075'); //n
        turnedList.add('\u006F'); //o
        turnedList.add('\u0064'); //p
        turnedList.add('\u0062'); //q
        turnedList.add('\u0279'); //r
        turnedList.add('\u0073'); //s
        turnedList.add('\u0287'); //t
        turnedList.add('\u006E'); //u
        turnedList.add('\u028C'); //v
        turnedList.add('\u028D'); //w
        turnedList.add('\u0078'); //x
        turnedList.add('\u028E'); //y
        turnedList.add('\u007A'); //z

        return turnedList;
    }

    public static List<Integer> getNotFilledSquaredList() {
        notFilledSquaredList = new ArrayList<>();
        notFilledSquaredList.clear();
        notFilledSquaredList.add(0x1F130); //a
        notFilledSquaredList.add(0x1F131); //b
        notFilledSquaredList.add(0x1F132); //c
        notFilledSquaredList.add(0x1F133); //d
        notFilledSquaredList.add(0x1F134); //e
        notFilledSquaredList.add(0x1F135); //f
        notFilledSquaredList.add(0x1F136); //g
        notFilledSquaredList.add(0x1F137); //h
        notFilledSquaredList.add(0x1F138); //i
        notFilledSquaredList.add(0x1F139); //j
        notFilledSquaredList.add(0x1F13A); //k
        notFilledSquaredList.add(0x1F13B); //l
        notFilledSquaredList.add(0x1F13C); //m
        notFilledSquaredList.add(0x1F13D); //n
        notFilledSquaredList.add(0x1F13E); //o
        notFilledSquaredList.add(0x1F13F); //p
        notFilledSquaredList.add(0x1F140); //q
        notFilledSquaredList.add(0x1F141); //r
        notFilledSquaredList.add(0x1F142); //s
        notFilledSquaredList.add(0x1F143); //t
        notFilledSquaredList.add(0x1F144); //u
        notFilledSquaredList.add(0x1F145); //v
        notFilledSquaredList.add(0x1F146); //w
        notFilledSquaredList.add(0x1F147); //x
        notFilledSquaredList.add(0x1F148); //y
        notFilledSquaredList.add(0x1F149); //z

        return notFilledSquaredList;
    }

    public static List<Integer> getNegativeSquaredList() {
        negativeSquaredList = new ArrayList<>();
        negativeSquaredList.clear();
        negativeSquaredList.add(0x1F170); //a
        negativeSquaredList.add(0x1F171); //b
        negativeSquaredList.add(0x1F172); //c
        negativeSquaredList.add(0x1F173); //d
        negativeSquaredList.add(0x1F174); //e
        negativeSquaredList.add(0x1F175); //f
        negativeSquaredList.add(0x1F176); //g
        negativeSquaredList.add(0x1F177); //h
        negativeSquaredList.add(0x1F178); //i
        negativeSquaredList.add(0x1F179); //j
        negativeSquaredList.add(0x1F17A); //k
        negativeSquaredList.add(0x1F17B); //l
        negativeSquaredList.add(0x1F17C); //m
        negativeSquaredList.add(0x1F17D); //n
        negativeSquaredList.add(0x1F17E); //o
        negativeSquaredList.add(0x1F18A); //p
        negativeSquaredList.add(0x1F180); //q
        negativeSquaredList.add(0x1F181); //r
        negativeSquaredList.add(0x1F182); //s
        negativeSquaredList.add(0x1F183); //t
        negativeSquaredList.add(0x1F184); //u
        negativeSquaredList.add(0x1F185); //v
        negativeSquaredList.add(0x1F186); //w
        negativeSquaredList.add(0x1F187); //x
        negativeSquaredList.add(0x1F188); //y
        negativeSquaredList.add(0x1F189); //z

        return negativeSquaredList;
    }

    public static List<Integer> getFilledCircleList() {
        filledCircleList = new ArrayList<>();
        filledCircleList.clear();
        filledCircleList.add(0x1F150); //a
        filledCircleList.add(0x1F151); //b
        filledCircleList.add(0x1F152); //c
        filledCircleList.add(0x1F153); //d
        filledCircleList.add(0x1F154); //e
        filledCircleList.add(0x1F155); //f
        filledCircleList.add(0x1F156); //g
        filledCircleList.add(0x1F157); //h
        filledCircleList.add(0x1F158); //i
        filledCircleList.add(0x1F159); //j
        filledCircleList.add(0x1F15A); //k
        filledCircleList.add(0x1F15B); //l
        filledCircleList.add(0x1F15C); //m
        filledCircleList.add(0x1F15D); //n
        filledCircleList.add(0x1F15E); //o
        filledCircleList.add(0x1F15F); //p
        filledCircleList.add(0x1F160); //q
        filledCircleList.add(0x1F161); //r
        filledCircleList.add(0x1F162); //s
        filledCircleList.add(0x1F163); //t
        filledCircleList.add(0x1F164); //u
        filledCircleList.add(0x1F165); //v
        filledCircleList.add(0x1F166); //w
        filledCircleList.add(0x1F167); //x
        filledCircleList.add(0x1F168); //y
        filledCircleList.add(0x1F169); //z

        return filledCircleList;
    }

    public static List<Integer> getDoubleStuckList() {
        doubleStruckList = new ArrayList<>();
        doubleStruckList.clear();
        doubleStruckList.add(0x1D538); //a
        doubleStruckList.add(0x1D539); //b
        doubleStruckList.add(0x00002102); //c
        doubleStruckList.add(0x1D53B); //d
        doubleStruckList.add(0x1D53C); //e
        doubleStruckList.add(0x1D53D); //f
        doubleStruckList.add(0x1D53E); //g
        doubleStruckList.add(0x00000048); //h
        doubleStruckList.add(0x1D540); //i
        doubleStruckList.add(0x1D541); //j
        doubleStruckList.add(0x1D542); //k
        doubleStruckList.add(0x1D543); //l
        doubleStruckList.add(0x1D544); //m
        doubleStruckList.add(0x0000004E); //n
        doubleStruckList.add(0x1D546); //o
        doubleStruckList.add(0x00002119); //p
        doubleStruckList.add(0x0000211A); //q
        doubleStruckList.add(0x00000052); //r
        doubleStruckList.add(0x1D54A); //s
        doubleStruckList.add(0x1D54B); //t
        doubleStruckList.add(0x1D54C); //u
        doubleStruckList.add(0x1D54D); //v
        doubleStruckList.add(0x1D54E); //w
        doubleStruckList.add(0x1D54F); //x
        doubleStruckList.add(0x1D550); //y
        doubleStruckList.add(0x0000005A); //z

        return doubleStruckList;
    }

    public static List<Integer> getScriptList() {
        scriptList = new ArrayList<>();
        scriptList.clear();
        scriptList.add(0x1D49C); //a
        scriptList.add(0x0000212C); //b
        scriptList.add(0x0001D49E); //c
        scriptList.add(0x1D49F); //d
        scriptList.add(0x00002130); //e
        scriptList.add(0x00002131); //f
        scriptList.add(0x1D4A2); //g
        scriptList.add(0x0000210B); //h
        scriptList.add(0x00002110); //i
        scriptList.add(0x1D4A5); //j
        scriptList.add(0x1D4A6); //k
        scriptList.add(0x00002112); //l
        scriptList.add(0x00002133); //m
        scriptList.add(0x1D4A9); //n
        scriptList.add(0x1D4AA); //o
        scriptList.add(0x1D4AB); //p
        scriptList.add(0x1D4AC); //q
        scriptList.add(0x0000211B); //r
        scriptList.add(0x1D4AE); //s
        scriptList.add(0x1D4AF); //t
        scriptList.add(0x1D4B0); //u
        scriptList.add(0x1D4B1); //v
        scriptList.add(0x1D4B2); //w
        scriptList.add(0x1D4B3); //x
        scriptList.add(0x1D4B4); //y
        scriptList.add(0x1D4B5); //z

        return scriptList;
    }

    public static List<Integer> getBoldScriptList() {
        boldScriptList = new ArrayList<>();
        boldScriptList.clear();
        boldScriptList.add(0x1D4D0); //a
        boldScriptList.add(0x1D4D1); //b
        boldScriptList.add(0x1D4D2); //c
        boldScriptList.add(0x1D4D3); //d
        boldScriptList.add(0x1D4D4); //e
        boldScriptList.add(0x1D4D5); //f
        boldScriptList.add(0x1D4D6); //g
        boldScriptList.add(0x1D4D7); //h
        boldScriptList.add(0x1D4D8); //i
        boldScriptList.add(0x1D4D9); //j
        boldScriptList.add(0x1D4DA); //k
        boldScriptList.add(0x1D4DB); //l
        boldScriptList.add(0x1D4DC); //m
        boldScriptList.add(0x1D4DD); //n
        boldScriptList.add(0x1D4DE); //o
        boldScriptList.add(0x1D4DF); //p
        boldScriptList.add(0x1D4E0); //q
        boldScriptList.add(0x1D4E1); //r
        boldScriptList.add(0x1D4E2); //s
        boldScriptList.add(0x1D4E3); //t
        boldScriptList.add(0x1D4E4); //u
        boldScriptList.add(0x1D4E5); //v
        boldScriptList.add(0x1D4E6); //w
        boldScriptList.add(0x1D4E7); //x
        boldScriptList.add(0x1D4E8); //y
        boldScriptList.add(0x1D4E9); //z

        return boldScriptList;
    }

    public static char[] getLetterList() {
        return letterList;
    }


    public static void shareVideoFile(Context context, String filePath, String title) {
        File fileWithinMyDir = new File(filePath);
        if (fileWithinMyDir.exists()) {
            Uri uri = Uri.parse(filePath);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("video/*");
            share.putExtra(Intent.EXTRA_STREAM, uri);
            context.startActivity(Intent.createChooser(share, title));
        }
    }

    private static int copyFile(File sourceFile, File destFile) {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            try {
                destFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else return FILE_EXISTS;

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } catch (Exception ignored) {

        } finally {
            if (source != null) {
                try {
                    source.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (destination != null) {
                try {
                    destination.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return FILE_COPIED;
    }


    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    public static String getMimType(String filePath) {
        return URLConnection.guessContentTypeFromName(filePath);
    }

    public static boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }

    public static boolean isVoiceFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("voice");
    }

    public static void startAnimation(Activity context) {
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static boolean appInstalledOrNot(String uri, Context context) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, 0);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static String getPackageName(String url) {
        return url.split("=")[1].toString();
    }


    public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        try {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getDuration(File file) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
        String durationStr = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        return Common.formateMilliSeccond(Long.parseLong(durationStr));
    }

    public static String formateMilliSeccond(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        return finalTimerString;
    }

    public static void shareImageFile(String filePath, Context context) {
        File fileWithinMyDir = new File(filePath);
        if (fileWithinMyDir.exists()) {
            Uri uri = Uri.parse(filePath);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/*");
            share.putExtra(Intent.EXTRA_STREAM, uri);
            context.startActivity(Intent.createChooser(share, "Share Image"));
        }
    }

    public static List<String> getVoiceList() {
        voiceList = new ArrayList<>();

        files = Voicefolder.listFiles();
        if (files != null) {
            Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            for (File file : files) {
                if (file.getName().contains(".opus")) {
                    voiceList.add(file.getPath());
                } else {
                    File subFile = new File(file.getPath());
                    try {
                        for (File sub : subFile.listFiles()) {
                            if (sub.getName().contains(".opus")) {
                                voiceList.add(sub.getPath());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return voiceList;

    }


    public static boolean isRorAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

    public static List<MediaModel> fetchImagesBusiness() {
        List<MediaModel> list = new ArrayList<>();

        try {
            if (Build.VERSION.SDK_INT >= 30) {
                String listPath = PrefHelper.getStringVal("PersistentPathBusiness");
                Log.i("fetchImagesBusiness: ", listPath);
                DocumentFile[] listFiles = DocumentFile.fromTreeUri(App.context, Uri.parse(PrefHelper.getStringVal("PersistentPathBusiness"))).listFiles();
                int i2 = 0;
                while (i2 < listFiles.length) {
                    DocumentFile documentFile = listFiles[i2];
                    if (documentFile.getUri().toString().endsWith(".Statuses")) {
                        DocumentFile[] listFiles2 = documentFile.listFiles();
                        int i3 = 0;
                        while (i3 < listFiles2.length) {
                            DocumentFile documentFile2 = listFiles2[i3];
                            if (documentFile2.getName().endsWith(".jpg") ||
                                    documentFile2.getName().endsWith(".png") ||
                                    documentFile2.getName().endsWith(".mp4") ||
                                    documentFile2.getName().endsWith(".3gp") ||
                                    documentFile2.getName().endsWith(".avi") ||
                                    documentFile2.getName().endsWith(".flv")) {
                                MediaModel mediaModel = new MediaModel();
                                mediaModel.setPath(documentFile2.getUri().toString());
                                mediaModel.setSelected(false);
                                list.add(mediaModel);
                            }
                            i3++;
                        }
                    }
                    i2++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<MediaModel> fetchImages() {
        List<MediaModel> list = new ArrayList<>();

        try {
            if (Build.VERSION.SDK_INT >= 30) {
                DocumentFile[] listFiles = DocumentFile.fromTreeUri(App.context, Uri.parse(PrefHelper.getStringVal("PersistentPath"))).listFiles();
                int i2 = 0;
                while (i2 < listFiles.length) {
                    DocumentFile documentFile = listFiles[i2];
                    if (documentFile.getUri().toString().endsWith(".Statuses")) {
                        DocumentFile[] listFiles2 = documentFile.listFiles();
                        int i3 = 0;
                        while (i3 < listFiles2.length) {
                            DocumentFile documentFile2 = listFiles2[i3];
                            if (documentFile2.getName().endsWith(".jpg") ||
                                    documentFile2.getName().endsWith(".png") ||
                                    documentFile2.getName().endsWith(".mp4") ||
                                    documentFile2.getName().endsWith(".3gp") ||
                                    documentFile2.getName().endsWith(".avi") ||
                                    documentFile2.getName().endsWith(".flv")) {
                                MediaModel mediaModel = new MediaModel();
                                String pathToSet = documentFile2.getUri().toString();
                                mediaModel.setPath(pathToSet);
                                mediaModel.setSelected(false);
                                list.add(mediaModel);
                            }
                            i3++;
                        }
                    }
                    i2++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    public static Uri getVideoContentUri(Context context, File file) {
        String filePath = file.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Video.Media._ID},
                MediaStore.Video.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (file.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Video.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    public static boolean isAllPermission(){
        return PrefHelper.getBooleanVal("storage") && PrefHelper.getBooleanVal("notification") && PrefHelper.getBooleanVal("battery_optimization");
    }

    public static void shareFile(Context context, File file, String mediaType) {
        Intent shareIntent = new Intent();
        Uri uri = FileProvider.getUriForFile(context,
                APPLICATION_ID + ".provider", file); //issue
        shareIntent.setAction("android.intent.action.SEND");
        shareIntent.putExtra("android.intent.extra.STREAM", uri);
        shareIntent.setType(mediaType);
        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(shareIntent, "Share File"));

    }

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}