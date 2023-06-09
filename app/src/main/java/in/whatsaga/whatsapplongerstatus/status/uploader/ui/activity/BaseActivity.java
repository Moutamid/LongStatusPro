package in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import in.whatsaga.whatsapplongerstatus.status.uploader.utils.Common;

public abstract class BaseActivity extends AppCompatActivity {
    protected AppCompatActivity activity;
    protected Context context;
    protected Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Common.setLocale(this);
        activity = this;
        context = this;
        handler = new Handler();
        beforeSettingContent();
        super.onCreate(savedInstanceState);
        setContentView(getResId());
        onReady();
        onReady(savedInstanceState);
    }

    protected void enableToolbarBack(int resId) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
            supportActionBar.setHomeAsUpIndicator(resId);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected void beforeSettingContent() {
    }

    protected abstract void onReady();

    protected void onReady(@Nullable Bundle savedInstanceState) {
    }

    protected abstract int getResId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}