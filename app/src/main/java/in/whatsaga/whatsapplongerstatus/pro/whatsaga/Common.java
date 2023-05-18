package in.whatsaga.whatsapplongerstatus.pro.whatsaga;

import static android.content.ContentValues.TAG;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaMuxer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import androidx.documentfile.provider.DocumentFile;
import static in.whatsaga.whatsapplongerstatus.pro.App.context;

import in.whatsaga.whatsapplongerstatus.pro.R;
import in.whatsaga.whatsapplongerstatus.pro.whatsaga.adapter.MediaModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import khangtran.preferenceshelper.PrefHelper;

public class Common {


    public static final int FILE_COPIED = 1;
    public static final int FILE_EXISTS = 2;
    public static final File folder = new File(
            context.getExternalFilesDir(""), "SuperTech Uploader"
    );
    private static final String SPLIT_VIDEO = "Split Video";
    public static final File splitVideoFolder = new File(folder, SPLIT_VIDEO);
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 1024;
    private static final String AUDIO = "audio";
    public static final File audioFolder = new File(folder, AUDIO);
    public static File whatsAppFolderStatus_B = new File(Environment.getExternalStorageDirectory().getPath() +
            "/WhatsApp Business/Media/.Statuses");
    public static File whatsAppFolderStatus_B11 = new File(Environment.getExternalStorageDirectory().getPath() +
            "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/.Statuses");
    public static File whatsAppFolderStatus = new File(Environment.getExternalStorageDirectory().getPath() +
            "/WhatsApp/Media/.Statuses");
    public static File whatsAppFolderStatus11 = new File(Environment.getExternalStorageDirectory().getPath() +
            "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses");
    public static File Statusbackupfolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/SuperTech Status/Media/Statuses");

    public static void mkDirs() {
        if (folder.mkdirs())
            Log.i(TAG, "Folder created");
        if (audioFolder.mkdirs())
            Log.i(TAG, "Audio folder created");
        if (splitVideoFolder.mkdirs())
            Log.i(TAG, "Split Video folder created");
        if (Statusbackupfolder.mkdirs())
            Log.i(TAG, "Folder created");
    }

    public static int copyImage(File file) {
        mkDirs();
        return copyFile(file, new File(Statusbackupfolder, file.getName()));
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

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.delete();
    }

    public static void shareAudioFile(Context context, String filePath, String title) {
        File fileWithinMyDir = new File(filePath);
        if (fileWithinMyDir.exists()) {
            Uri uri = Uri.parse(filePath);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("audio/*");
            share.putExtra(Intent.EXTRA_STREAM, uri);
            context.startActivity(Intent.createChooser(share, title));
        }
    }

    public static void playAudio(Context context, String mediaInfo) {
        Uri uri;
        // only for gingerbread and newer versions
        uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName(), new File(mediaInfo));

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "Player not found", Toast.LENGTH_SHORT).show();
        }
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

    public static void startAnimation(Activity context) {
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    private static int copyFile(File sourceFile, File destFile) {
        if (!Objects.requireNonNull(destFile.getParentFile()).exists())
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

    public static String getDuration(File file) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
        String durationStr = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        return Common.formateMilliSeccond(Long.parseLong(durationStr));
    }

    public static void shareMultipleVideo(Context context, ArrayList<String> videoFiles, String title) {
        ArrayList<Uri> uriArrayList = new ArrayList<>();
        for (String videoPath : videoFiles) {
            Uri uri;
            // only for gingerbread and newer versions
            uri = FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName(), new File(videoPath));
            uriArrayList.add(uri);
        }
        // Intent videoShare = new Intent(Intent.ACTION_SEND_MULTIPLE);
        Intent videoShare = new Intent(Intent.ACTION_SEND_MULTIPLE);
        videoShare.setPackage("com.whatsapp");
        videoShare.setType("video/*");
        videoShare.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriArrayList);
        videoShare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(videoShare, title));
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

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = true;

        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isRorAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

    public static List<MediaModel> fetchImages() {
        List<MediaModel> list = new ArrayList<>();

        try {
            if (Build.VERSION.SDK_INT >= 30) {
                DocumentFile[] listFiles = Objects.requireNonNull(DocumentFile.fromTreeUri(context, Uri.parse(PrefHelper.getStringVal("PersistentPath")))).listFiles();
                int i2 = 0;
                while (i2 < listFiles.length) {
                    DocumentFile documentFile = listFiles[i2];
                    if (documentFile.getUri().toString().endsWith(".Statuses")) {
                        DocumentFile[] listFiles2 = documentFile.listFiles();
                        int i3 = 0;
                        while (i3 < listFiles2.length) {
                            DocumentFile documentFile2 = listFiles2[i3];
                            if (Objects.requireNonNull(documentFile2.getName()).endsWith(".jpg")) {
                                MediaModel mediaModel = new MediaModel();
                                mediaModel.setPath(documentFile2.getUri().toString());
                                mediaModel.setSelected(false);
                                list.add(mediaModel);
                            } else if (documentFile2.getName().endsWith(".png") || documentFile2.getName().endsWith(".mp4") || documentFile2.getName().endsWith(".3gp") || documentFile2.getName().endsWith(".avi") || documentFile2.getName().endsWith(".flv")) {
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

    public static List<MediaModel> fetchImagesBusiness() {
        List<MediaModel> list = new ArrayList<>();

        try {
            if (Build.VERSION.SDK_INT >= 30) {
                String listPath = PrefHelper.getStringVal("PersistentPathBusiness");
                Log.i("fetchImagesBusiness: ", listPath);
                DocumentFile[] listFiles = Objects.requireNonNull(DocumentFile.fromTreeUri(context, Uri.parse(PrefHelper.getStringVal("PersistentPathBusiness")))).listFiles();
                int i2 = 0;
                while (i2 < listFiles.length) {
                    DocumentFile documentFile = listFiles[i2];
                    if (documentFile.getUri().toString().endsWith(".Statuses")) {
                        DocumentFile[] listFiles2 = documentFile.listFiles();
                        int i3 = 0;
                        while (i3 < listFiles2.length) {
                            DocumentFile documentFile2 = listFiles2[i3];
                            if (Objects.requireNonNull(documentFile2.getName()).endsWith(".jpg")) {
                                MediaModel mediaModel = new MediaModel();
                                mediaModel.setPath(documentFile2.getUri().toString());
                                mediaModel.setSelected(false);
                                list.add(mediaModel);
                            } else if (documentFile2.getName().endsWith(".png") || documentFile2.getName().endsWith(".mp4") || documentFile2.getName().endsWith(".3gp") || documentFile2.getName().endsWith(".avi") || documentFile2.getName().endsWith(".flv")) {
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

    @SuppressLint("WrongConstant")
    public void genVideoUsingMuxer(String srcPath, String dstPath, int startMs, int endMs, boolean useAudio, boolean useVideo) throws IOException {
        // Set up MediaExtractor to read from the source.
        MediaExtractor extractor = new MediaExtractor();
        extractor.setDataSource(srcPath);
        int trackCount = extractor.getTrackCount();
        // Set up MediaMuxer for the destination.
        MediaMuxer muxer;
        muxer = new MediaMuxer(dstPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
        // Set up the tracks and retrieve the max buffer size for selected
        // tracks.
        HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>(trackCount);
        int bufferSize = -1;
        for (int i = 0; i < trackCount; i++) {
            MediaFormat format = extractor.getTrackFormat(i);
            String mime = format.getString(MediaFormat.KEY_MIME);
            boolean selectCurrentTrack = false;
            if (mime.startsWith("audio/") && useAudio) {
                selectCurrentTrack = true;
            } else if (mime.startsWith("video/") && useVideo) {
                selectCurrentTrack = true;
            }
            if (selectCurrentTrack) {
                extractor.selectTrack(i);
                int dstIndex = muxer.addTrack(format);
                indexMap.put(i, dstIndex);
                if (format.containsKey(MediaFormat.KEY_MAX_INPUT_SIZE)) {
                    int newSize = format.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
                    bufferSize = Math.max(newSize, bufferSize);
                }
            }
        }
        if (bufferSize < 0) {
            bufferSize = DEFAULT_BUFFER_SIZE;
        }
        // Set up the orientation and starting time for extractor.
        MediaMetadataRetriever retrieverSrc = new MediaMetadataRetriever();
        retrieverSrc.setDataSource(srcPath);
        String degreesString = retrieverSrc.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
        if (degreesString != null) {
            int degrees = Integer.parseInt(degreesString);
            if (degrees >= 0) {
                muxer.setOrientationHint(degrees);
            }
        }
        if (startMs > 0) {
            extractor.seekTo(startMs * 1000L, MediaExtractor.SEEK_TO_CLOSEST_SYNC);
        }
        // Copy the samples from MediaExtractor to MediaMuxer. We will loop
        // for copying each sample and stop when we get to the end of the source
        // file or exceed the end time of the trimming.
        int offset = 0;
        int trackIndex = -1;
        ByteBuffer dstBuf = ByteBuffer.allocate(bufferSize);
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        muxer.start();
        while (true) {
            bufferInfo.offset = offset;
            bufferInfo.size = extractor.readSampleData(dstBuf, offset);
            if (bufferInfo.size < 0) {
                bufferInfo.size = 0;
                break;
            } else {
                bufferInfo.presentationTimeUs = extractor.getSampleTime();
                if (endMs > 0 && bufferInfo.presentationTimeUs > (endMs * 1000L)) {
                    break;
                } else {
                    bufferInfo.flags = extractor.getSampleFlags();
                    trackIndex = extractor.getSampleTrackIndex();
                    muxer.writeSampleData(indexMap.get(trackIndex), dstBuf, bufferInfo);
                    extractor.advance();
                }
            }
        }


        try {
            muxer.stop();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        muxer.release();
    }

    public static boolean appInstalledOrNot(String uri, Context context) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, 0);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return app_installed;
    }

    public static String getPackageName(String url) {
        return url.split("=")[1].toString();
    }

}
