package in.whatsaga.whatsapplongerstatus.pro.whatsaga;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import in.whatsaga.whatsapplongerstatus.pro.R;

public class ShareActivity extends AppCompatActivity implements View.OnClickListener {

    String appLink;
    String extraText;
    String path;
    ImageView icon, video_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        icon = findViewById(R.id.icon);
        video_icon = findViewById(R.id.video);

        appLink = "https://play.google.com/store/apps/details?id=" + getPackageName();
        extraText = "Upload full video status on a single click : \n " + appLink;
        path = getIntent().getStringExtra("path");

        assert path != null;
        if (path.contains(".jpg") || path.contains(".png")) {
            Glide.with(this).load(path).into(icon);
        } else if (path.contains(".mp4")) {
            Glide.with(this).load(path).into(icon);
            video_icon.setVisibility(View.VISIBLE);
        } else if (path.contains(".mp3")) {
            Glide.with(this).load(R.drawable.ic_audio_big).into(icon);
        } else {
            Glide.with(this).load(R.drawable.app_icon).into(icon);
        }

        findViewById(R.id.share).setOnClickListener(this);
        findViewById(R.id.whatsapp).setOnClickListener(this);
        findViewById(R.id.facebook).setOnClickListener(this);
        findViewById(R.id.copy).setOnClickListener(this);
        findViewById(R.id.instagram).setOnClickListener(this);
        findViewById(R.id.skype).setOnClickListener(this);
        findViewById(R.id.messenger).setOnClickListener(this);
        findViewById(R.id.mail).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.whatsapp:
                if (path.contains(".mp4") || path.contains(".mp3")) {
                    shareVideo("com.whatsapp", path);
                } else if (path.contains(".jpg") || path.contains(".png")) {
                    shareImage("com.whatsapp", path);
                } else
                    share("com.whatsapp");
                break;

            case R.id.facebook:
                if (path.contains(".mp4") || path.contains(".mp3")) {
                    shareVideo("com.facebook.katana", path);
                } else if (path.contains(".jpg") || path.contains(".png")) {
                    shareImage("com.facebook.katana", path);
                } else
                    share("com.facebook.katana");
                break;

            case R.id.skype:
                if (path.contains(".mp4") || path.contains(".mp3")) {
                    shareVideo("com.skype.raider", path);
                } else if (path.contains(".jpg") || path.contains(".png")) {
                    shareImage("com.skype.raider", path);
                } else
                    share("com.skype.raider");
                break;

            case R.id.copy:
                if (!path.contains(".mp4") || !path.contains(".jpg") || !path.contains(".png") || path.contains(".mp3")) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("app", extraText);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(this, "link added to clipboard", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "you can not copy image", Toast.LENGTH_SHORT).show();

                break;

            case R.id.share:
                if (path.contains(".mp4") || path.contains(".mp3")) {
                    Common.shareVideoFile(this, path, "Video");
                } else if (path.contains(".jpg") || path.contains(".png")) {
                    Common.shareImageFile(path, this);
                } else
                    shareAppLink();
                break;

            case R.id.messenger:
                if (path.contains(".mp4") || path.contains(".mp3")) {
                    shareVideo("com.facebook.orca", path);
                } else if (path.contains(".jpg") || path.contains(".png")) {
                    shareImage("com.facebook.orca", path);
                } else
                    share("com.facebook.orca");
                break;

            case R.id.mail:
                if (!path.contains(".mp4") || path.contains(".mp3") || !path.contains(".jpg") || !path.contains(".png")) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", "", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Unseen");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, extraText);
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                } else
                    Toast.makeText(this, "you can not send media file in mail", Toast.LENGTH_SHORT).show();

                break;

            case R.id.instagram:
                if (path.contains(".mp4")) {
                    shareVideo("com.instagram.android", path);
                } else if (path.contains(".jpg") || path.contains(".png")) {
                    shareImage("com.instagram.android", path);
                } else
                    share("com.instagram.android");
                break;

            case R.id.cancel:
                finish();
                break;
        }
    }

    private void shareAppLink() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, extraText);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "share via"));
    }

    public void share(String packge) {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage(packge);
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, extraText);
        try {
            startActivity(whatsappIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "app not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareImage(String packge, String path) {
        Uri uri = Uri.parse(path);
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("image/*");
        whatsappIntent.setPackage(packge);
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(whatsappIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "app not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareVideo(String packge, String path) {
        Uri uri = Uri.parse(path);
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("video/*");
        whatsappIntent.setPackage(packge);
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(whatsappIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "app not found", Toast.LENGTH_SHORT).show();
        }
    }
}
