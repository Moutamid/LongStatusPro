package in.whatsaga.whatsapplongerstatus.pro.whatsaga;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

import in.whatsaga.whatsapplongerstatus.pro.R;

public class UploadActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, View.OnClickListener{
    private VideoView videoView;
    String path;
    private int splitFileCount = 0;
    private int stopPosition = 0;
    int start = -1;
    int end = -1;
    private static final String SEPARATOR = "/";
    private ProgressDialog progress;
    SharePref sharePref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        sharePref = new SharePref(this);
        videoView = findViewById(R.id.videoView);
        findViewById(R.id.upload).setOnClickListener(this);
        path = getIntent().getStringExtra("path");
        MediaController controller = new MediaController(this);
        end=sharePref.getDuration();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                int duration = videoView.getDuration() / 1000;
                int splitTime;
                if(sharePref.getDuration()==29000){
                    splitTime = 29;
                }else{
                    splitTime=15;
                }
                splitFileCount = duration / splitTime + 1;
            }
        });
        controller.setAnchorView(videoView);
        controller.setMediaPlayer(videoView);
        videoView.setMediaController(controller);
        videoView.setVideoPath(path);
        videoView.setOnCompletionListener(this);
        videoView.requestFocus();
        videoView.start();

    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            stopPosition = videoView.getCurrentPosition(); //stopPosition is an int
            videoView.pause();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            if (stopPosition == videoView.getDuration())
                stopPosition = 0;
            videoView.seekTo(stopPosition);
            videoView.start(); //Or use resume() if it doesn't work. I'm not sure
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        videoView.start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.upload) {

                        new SplitVideo().execute();


        }
    }

    private void splitVideo() {
        ArrayList<String> videoFiles = new ArrayList<>();
        for (int i = 0; i < splitFileCount; i++) {
            String outputPath = Common.splitVideoFolder.getPath() + SEPARATOR +
                    "video" + i + ".mp4";
            videoFiles.add(Common.splitVideoFolder.getPath() + SEPARATOR +
                    "video" + i + ".mp4");

            try {
                Log.i("TAG", "start : " + start);
                Log.i("TAG", "end : " + end);
                new Common().genVideoUsingMuxer(path, outputPath, start, end, true, true);
                Log.i("TAG", "video saved : " + i);
                start = end;
                end = end + sharePref.getDuration();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Common.shareMultipleVideo(UploadActivity.this, videoFiles, "Share Videos");
    }




    class SplitVideo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(UploadActivity.this);
            progress.setTitle("Splitting the video");
            progress.setMessage("Please wait...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            splitVideo();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
        }
    }

    private void dismissProgressDialog() {
        try {
            if (progress != null && progress.isShowing())
                progress.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {

        super.onBackPressed();


    }

}