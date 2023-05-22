package `in`.whatsaga.whatsapplongerstatus.pro.ui.activity

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import `in`.whatsaga.whatsapplongerstatus.pro.R
import `in`.whatsaga.whatsapplongerstatus.pro.utils.Common
import `in`.whatsaga.whatsapplongerstatus.pro.utils.Constants
import `in`.whatsaga.whatsapplongerstatus.pro.utils.SharedPref
import khangtran.preferenceshelper.PrefHelper

class StartActivity : AppCompatActivity() {
    var isAdShown = false
    var progressBar: ProgressBar? = null
    var notification = false
    var sharedPref: SharedPref? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Common.setLocale(this)
        setContentView(R.layout.activity_start)
        Constants.checkApp(this)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT

        sharedPref = SharedPref(this)
        progressBar = findViewById<View>(R.id.progress) as ProgressBar
        progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
        progressAnimator!!.setDuration(2500)
        progressAnimator!!.setInterpolator(LinearInterpolator())
        progressAnimator!!.start()
        //        if (!isAdShown) {
//            App.mAppOpenManager.startScreenFetchAd(new AppOpenManagerNew.OnCloseListener() {
//                @Override
//                public void onExit() {
//                    // intention();
//                    progressAnimator.resume();
//                }
//
//                @Override
//                public void onLoad() {
//                    progressAnimator.pause();
//                }
//            });
//        }
        progressAnimator!!.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (!sharedPref!!.getAppLaunch()) {
                    startActivity(Intent(this@StartActivity, IntroScreenActivity::class.java))
                    finish()
                } else {
                    intention()
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        //  progressAnimator.pause();
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    private fun intention() {
        if (!isStoragePermission(this)) {
            PrefHelper.setVal("storage", false)
        } else {
            PrefHelper.setVal("storage", true)
        }

//        if (!PrefHelper.getBooleanVal("locale_set", false)) {
//            FirstTimeLanguageActivity.start(this);
//            finish();
//            return;
//        }
        if (!Common.isAllPermission()) {
            startActivity(Intent(this, AllPermissionsActivity::class.java))
        } else {
            if (!isNotificationServiceEnabled) {
                PrefHelper.setVal("notification", false)
                startActivity(Intent(this, AllPermissionsActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        finish()
    }

    val isNotificationServiceEnabled: Boolean
        get() {
            val pkgName = packageName
            val flat = Settings.Secure.getString(
                contentResolver,
                ENABLED_NOTIFICATION_LISTENERS
            )
            if (!TextUtils.isEmpty(flat)) {
                val names = flat.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (name in names) {
                    val cn = ComponentName.unflattenFromString(name)
                    if (cn != null) {
                        if (TextUtils.equals(pkgName, cn.packageName)) {
                            return true
                        }
                    }
                }
            }
            return false
        }

    override fun onResume() {
        super.onResume()

//        Bundle extras = getIntent().getExtras();
//        progressBar = (ProgressBar) findViewById(R.id.progress);
//        progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
//        progressAnimator.setDuration(4000);
//        progressAnimator.setInterpolator(new LinearInterpolator());
//        progressAnimator.start();

//        if (!isAdShown) {
//            App.mAppOpenManager.startScreenFetchAd(new AppOpenManager.OnCloseListener() {
//                @Override
//                public void onExit() {
//                    visibleViews();
//                }
//
//                @Override
//                public void onLoad() {
//                    progressAnimator.pause();
//                }
//            });
//        }

//        progressAnimator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                intention();
//            }
//        });
//
//
//        if (extras != null && extras.getString("application") != null) {
//            notification = true;
//            String value = extras.getString("application");
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + value));
//            startActivity(intent);
//            finishAffinity();
//        }
    } //    private void visibleViews() {

    //        isAdShown = true;
    //        spinKitView.setVisibility(View.GONE);
    //        pleaseWait.setVisibility(View.GONE);
    //        progress.setVisibility(View.VISIBLE);
    //        new Handler().postDelayed(new Runnable() {
    //            @Override
    //            public void run() {
    //                getStarted.setVisibility(View.VISIBLE);
    //                progress.setVisibility(View.GONE);
    //            }
    //        }, 1500);
    //    }
    companion object {
        const val ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
        var progressAnimator: ObjectAnimator? = null
        fun isStoragePermission(context: Context?): Boolean {
            return if (Build.VERSION.SDK_INT >= 23 && Build.VERSION.SDK_INT < 33) {
                ContextCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            } else if (Build.VERSION.SDK_INT == 33) {
                ContextCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        }
    }
}