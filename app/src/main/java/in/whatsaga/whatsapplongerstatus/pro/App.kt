package `in`.whatsaga.whatsapplongerstatus.pro

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.fxn.stash.Stash
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import `in`.whatsaga.whatsapplongerstatus.pro.persistence.Repository
import `in`.whatsaga.whatsapplongerstatus.pro.utils.Common
import `in`.whatsaga.whatsapplongerstatus.pro.utils.PrefsHelper
import `in`.whatsaga.whatsapplongerstatus.pro.utils.StorageUtils
import khangtran.preferenceshelper.PrefHelper
import org.jetbrains.annotations.Contract


class App : Application(){
    private var appOpenManager: AppOpenManager? = null

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        Stash.init(this)

        MobileAds.initialize(
            this
        ) { initializationStatus: InitializationStatus? -> }
        appOpenManager = AppOpenManager(this)

        Common.ImageBackupfolder.mkdirs()
        PrefsHelper.init(context)
        PrefHelper.initHelper(context)
        Repository.INSTANCE.init(context)
        StorageUtils.init(context)
    }

    companion object {
        @get:Contract(pure = true)
        var isForeground = false

        @JvmField
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null

    }
}