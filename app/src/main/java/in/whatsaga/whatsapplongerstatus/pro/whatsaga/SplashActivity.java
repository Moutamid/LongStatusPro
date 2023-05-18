package in.whatsaga.whatsapplongerstatus.pro.whatsaga;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import in.whatsaga.whatsapplongerstatus.pro.R;

public class SplashActivity extends AppCompatActivity {
    SharePref sharePref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

            @Override
            public void run() {
                sharePref = new SharePref(SplashActivity.this);
                if(!sharePref.getIntro()){
                    startActivity(new Intent(SplashActivity.this, PrivacyActivity.class));
                }else if(!PermissionUtils.hasReadStoragePermission(SplashActivity.this)){
                    startActivity(new Intent(SplashActivity.this, MainAppActivity.class));
                }else {
                    Intent i = new Intent(SplashActivity.this, MainAppActivity.class);

                    startActivity(i);

                    // close this activity

                    finish();
                }

            }

        }, 800); // wait for 1 second
    }

}