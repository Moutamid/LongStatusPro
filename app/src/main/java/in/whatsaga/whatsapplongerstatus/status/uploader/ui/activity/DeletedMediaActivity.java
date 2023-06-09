package in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import in.whatsaga.whatsapplongerstatus.status.uploader.R;
import in.whatsaga.whatsapplongerstatus.status.uploader.utils.Common;
import in.whatsaga.whatsapplongerstatus.status.uploader.utils.Constants;

public class DeletedMediaActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Common.setLocale(this);
        setContentView(R.layout.activity_deleted_media);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        findViewById(R.id.deleted_images).setOnClickListener(this);
        findViewById(R.id.deleted_videos).setOnClickListener(this);


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(DeletedMediaActivity.this, MediaActivity.class);
        switch (v.getId()) {
            case R.id.deleted_images:

                intent = new Intent(DeletedMediaActivity.this, MediaActivity.class);
                intent.putExtra(Constants.TYPE, Constants.DELETED_IMAGES);
                startActivity(intent);
                Common.startAnimation(DeletedMediaActivity.this);

                break;


            case R.id.deleted_videos:

                intent.putExtra(Constants.TYPE, Constants.DELETED_VIDEOS);
                startActivity(intent);
                Common.startAnimation(DeletedMediaActivity.this);


                break;

        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    public void onBackPressed() {

        super.onBackPressed();
    }


}