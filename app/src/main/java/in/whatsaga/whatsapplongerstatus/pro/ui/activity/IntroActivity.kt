package `in`.whatsaga.whatsapplongerstatus.pro.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment

import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType
import `in`.whatsaga.whatsapplongerstatus.pro.R
import `in`.whatsaga.whatsapplongerstatus.pro.utils.SharedPref
import java.util.concurrent.TimeUnit

class IntroActivity : AppIntro() {
    var sharedPref: SharedPref? = null
   
    private var retryAttempt = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = SharedPref(this)
        sharedPref!!.setAppLaunch(true)

        addSlide(
            AppIntroFragment.newInstance(
                getString(R.string.how_it_work), getString(R.string.slide_one),
                R.drawable.slide1, resources.getColor(R.color.colorPrimaryDark)
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                getString(R.string.required), getString(R.string.slide_two),
                R.drawable.slide2, resources.getColor(R.color.colorPrimaryDark)
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                getString(R.string.disclaimer), getString(R.string.slide_three),
                R.drawable.slide3, resources.getColor(R.color.colorPrimaryDark)
            )
        )


        // Fade Transition
        setTransformer(AppIntroPageTransformerType.Fade)

        // Show/hide status bar
        showStatusBar(true)

        //Speed up or down scrolling
        setScrollDurationFactor(2)

        //Enable the color "fade" animation between two slides (make sure the slide implements SlideBackgroundColorHolder)
        isColorTransitionsEnabled = true

        //Prevent the back button from exiting the slides
        isSystemBackButtonLocked = true


        //Activate wizard mode (Some aesthetic changes)
        isWizardMode = true

        //Enable immersive mode (no status and nav bar)
        setImmersiveMode()

        //Enable/disable page indicators
        isIndicatorEnabled = true

        //Dhow/hide ALL buttons
        isButtonsEnabled = true

        // Enable Vibration
        isVibrate = true
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {

        super.onDonePressed(currentFragment)
        startActivity(Intent(this@IntroActivity, AllPermissionsActivity::class.java))
        finish()
    }



    override fun onBackPressed() {
        super.onBackPressed()
    }
}