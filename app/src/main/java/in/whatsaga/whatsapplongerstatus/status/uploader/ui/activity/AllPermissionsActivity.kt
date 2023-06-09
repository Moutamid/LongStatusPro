package `in`.whatsaga.whatsapplongerstatus.status.uploader.ui.activity

import android.Manifest
import android.app.Dialog
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import `in`.whatsaga.whatsapplongerstatus.status.uploader.R

import `in`.whatsaga.whatsapplongerstatus.status.uploader.services.MyService
import `in`.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.StartActivity.Companion.ENABLED_NOTIFICATION_LISTENERS
import `in`.whatsaga.whatsapplongerstatus.status.uploader.utils.Common
import khangtran.preferenceshelper.PrefHelper

class AllPermissionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Common.setLocale(this)
        setContentView(R.layout.activity_all_permissions)
        if (Common.isAllPermission()) {
            startActivity(Intent(this@AllPermissionsActivity, MainActivity::class.java))
            finish()
        }
        if (PrefHelper.getBooleanVal("storage", false)) {
            findViewById<View>(R.id.storageAllowBtn).visibility = View.INVISIBLE
        }
        if (PrefHelper.getBooleanVal("notification", false)) {
            findViewById<View>(R.id.notificationAllowBtn).visibility = View.INVISIBLE
        }
        if (PrefHelper.getBooleanVal("battery_optimization", false)) {
            findViewById<View>(R.id.batteryOptimizationAllowBtn).visibility = View.INVISIBLE
        }
        findViewById<View>(R.id.storageAllowBtn).setOnClickListener { v: View? ->
            if (Build.VERSION.SDK_INT < 33) {
                ActivityCompat.requestPermissions(
                    this@AllPermissionsActivity,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    MY_STORAGE_PERMISSION
                )
            } else {
                ActivityCompat.requestPermissions(
                    this@AllPermissionsActivity,
                    arrayOf(
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_MEDIA_VIDEO
                    ),
                    MY_STORAGE_PERMISSION
                )
            }
        }
        findViewById<View>(R.id.notificationAllowBtn).setOnClickListener { v: View? ->
            startActivityForResult(
                Intent(
                    ACTION_NOTIFICATION_LISTENER_SETTINGS
                ),
                NOTIFICATION_PERMISSION
            )
        }
        findViewById<View>(R.id.batteryOptimizationAllowBtn).setOnClickListener { v: View? -> importantNotice() }
        findViewById<View>(R.id.next).setOnClickListener { v: View? ->
            if (Common.isAllPermission()) {
                if (!PrefHelper.getBooleanVal("locale_set", false)) {
                    FirstTimeLanguageActivity.start(
                        this
                    )
                    finish()
                    return@setOnClickListener
                }
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, R.string.allow_permission_first, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun importantNotice() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.my_dialog)
        dialog.findViewById<View>(R.id.allowBtn).setOnClickListener { v: View? ->
            PrefHelper.setVal("battery_optimization", true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(MyService.start(this))
            } else {
                startService(MyService.start(this))
            }
            findViewById<View>(R.id.batteryOptimizationAllowBtn).visibility = View.INVISIBLE
            dialog.dismiss()
        }
        dialog.findViewById<View>(R.id.cancelBtn)
            .setOnClickListener { v: View? -> dialog.dismiss() }
        dialog.setCancelable(false)
        val displayMetrics = Resources.getSystem().displayMetrics
        val widthLcl = (displayMetrics.widthPixels * 0.9f).toInt()
        val params =
            dialog.findViewById<View>(R.id.rootDialog).layoutParams as FrameLayout.LayoutParams
        params.width = widthLcl
        params.gravity = Gravity.CENTER
        dialog.show()
        dialog.findViewById<View>(R.id.rootDialog).layoutParams = params
        val window = dialog.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onResume() {
        super.onResume()
        if (isNotificationServiceEnabled) {
            findViewById<View>(R.id.notificationAllowBtn).visibility = View.INVISIBLE
            PrefHelper.setVal("notification", true)
        } else {
            findViewById<View>(R.id.notificationAllowBtn).visibility = View.VISIBLE
            PrefHelper.setVal("notification", false)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_STORAGE_PERMISSION) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                findViewById<View>(R.id.storageAllowBtn).visibility = View.INVISIBLE
                PrefHelper.setVal("storage", true)
                //                takeAction();
            } else {
                Toast.makeText(this, "Allow Permission", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val isNotificationServiceEnabled: Boolean
        private get() {
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

    companion object {
        private const val MY_STORAGE_PERMISSION = 32
        const val ACTION_NOTIFICATION_LISTENER_SETTINGS =
            "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
        private const val NOTIFICATION_PERMISSION = 123
    }
}