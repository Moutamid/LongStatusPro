package in.whatsaga.whatsapplongerstatus.pro.ui.activity;
import static in.whatsaga.whatsapplongerstatus.pro.utils.Common.getImageContentUri;
import static in.whatsaga.whatsapplongerstatus.pro.utils.Common.getVideoContentUri;

import android.app.Dialog;
import android.app.PendingIntent;
import android.app.RecoverableSecurityException;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.leinardi.android.speeddial.SpeedDialView;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;
import in.whatsaga.whatsapplongerstatus.pro.R;
import in.whatsaga.whatsapplongerstatus.pro.adapters.SlidingImageAdapter;
import in.whatsaga.whatsapplongerstatus.pro.utils.Common;
import in.whatsaga.whatsapplongerstatus.pro.utils.Constants;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ViewPagerActivity extends AppCompatActivity implements View.OnClickListener, SlidingImageAdapter.VideoListener {

    ViewPager viewPager;
    SlidingImageAdapter adapter;
    List<File> list;
    int pos;
    String type, deletedType;
    private Dialog deleteDialog;
    UniversalVideoView videoView;
    private FrameLayout videoLayout;
    ActivityResultLauncher<IntentSenderRequest> someActivityResultLauncher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);




        list = new ArrayList<>();
        pos = getIntent().getIntExtra("position", -1);
        type = getIntent().getStringExtra("type");
        deletedType = getIntent().getStringExtra("DELETED_TYPE");

        File file;
        assert type != null;
        if (type.contains("voice")) {
            file = Common.Voicebackupfolder;
        } else {
            file = Common.ImageRecovery;
//            if (deletedType.equals(Constants.DELETED_IMAGES)) {
//                file = Common.ImageRecovery;
//            } else {
//
//            }

        }

        File[] files = file.listFiles();
        assert files != null;
        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
        for (File mfile : files) {
            if (!mfile.getName().startsWith(".")) {
                if (!list.contains(mfile) && !mfile.isDirectory()) {
                    if (type.contains("voice")) {
                        list.add(mfile);
                    } else {
                        if (deletedType.equals(Constants.DELETED_IMAGES)) {
                            if (Common.isImageFile(mfile.getPath())) {
                                list.add(mfile);
                            }
                        } else if (deletedType.equals(Constants.DELETED_VIDEOS)) {
                            if (Common.isVideoFile(mfile.getPath())) {
                                list.add(mfile);
                            }
                        } else {
                            if (mfile.getPath().contains(".opus")) {
                                list.add(mfile);
                            }
                        }
                    }
                }
            }
        }
        viewPager = findViewById(R.id.view_pager);
        adapter = new SlidingImageAdapter(this, list, this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                try {
                    if (videoView.isPlaying()) {
                        videoView.stopPlayback();
                        videoLayout.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), result -> {

        });

        deleteDialog();

        SpeedDialView speedDialView = findViewById(R.id.speedDial);

        speedDialView.setOnActionSelectedListener(speedDialActionItem -> {
            switch (speedDialActionItem.getId()) {
                case R.id.delete:

                    deleteDialog.show();
                    return false;

                case R.id.share:

                    Intent intent = new Intent(ViewPagerActivity.this, ShareActivity.class);
                    intent.putExtra("path", list.get(viewPager.getCurrentItem()).getPath());
                    intent.putExtra(Constants.TYPE, type);
                    startActivity(intent);
                    return false;

                default:
                    return false;
            }
        });

        speedDialView.inflate(R.menu.menu_context);

    }



    private void deleteDialog() {
        deleteDialog = new Dialog(this, R.style.Theme_Dialog);
        deleteDialog.setContentView(R.layout.delete_layout);
        deleteDialog.findViewById(R.id.yes).setOnClickListener(this);
        deleteDialog.findViewById(R.id.no).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.yes:
                deleteDialog.dismiss();
                delete();
                Toast.makeText(this, getResources().getString(R.string.file_deleted), Toast.LENGTH_SHORT).show();
                break;

            case R.id.no:
                deleteDialog.dismiss();
                break;
        }

    }

    private void delete() {
        try {
            int itemPos = viewPager.getCurrentItem();
            Log.i("TAG", "itempos : " + itemPos);
            File file = list.get(itemPos);
            if (Common.isRorAbove()) {
                if (file.getName().endsWith(".png") || file.getName().endsWith(".jpg")) {
                    delete(someActivityResultLauncher, getImageContentUri(this, file));
                } else {
                    delete(someActivityResultLauncher, getVideoContentUri(this, file));
                }
            } else {
                file.delete();
            }

            list.remove(itemPos);
            adapter = new SlidingImageAdapter(ViewPagerActivity.this, list, this);
            viewPager.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            viewPager.setCurrentItem(itemPos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPlayButtonClick(int pos, UniversalVideoView view, FrameLayout video, UniversalMediaController controller) {
        this.videoLayout = video;
        videoView = view;
        videoView.setMediaController(controller);
        videoView.setVideoURI(Uri.parse(list.get(pos).getPath()));
        videoView.start();
    }

    public void delete(ActivityResultLauncher<IntentSenderRequest> launcher, Uri uri) {

        ContentResolver contentResolver = ViewPagerActivity.this.getContentResolver();

        try {

            //delete object using resolver
            contentResolver.delete(uri, null, null);

        } catch (SecurityException e) {

            PendingIntent pendingIntent = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                ArrayList<Uri> collection = new ArrayList<>();
                collection.add(uri);
                pendingIntent = MediaStore.createDeleteRequest(contentResolver, collection);

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                //if exception is recoverable then again send delete request using intent
                if (e instanceof RecoverableSecurityException) {
                    RecoverableSecurityException exception = (RecoverableSecurityException) e;
                    pendingIntent = exception.getUserAction().getActionIntent();
                }
            }

            if (pendingIntent != null) {
                IntentSender sender = pendingIntent.getIntentSender();
                IntentSenderRequest request = new IntentSenderRequest.Builder(sender).build();
                launcher.launch(request);
            }
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (!InterstitialHelperNew.isInterstitialLoad) {
//            InterstitialHelperNew.init();
//        }
//    }


   

    public void onBackPressed() {



        super.onBackPressed();
    }
}
