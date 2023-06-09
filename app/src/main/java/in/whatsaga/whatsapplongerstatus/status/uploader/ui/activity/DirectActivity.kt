package `in`.whatsaga.whatsapplongerstatus.status.uploader.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import `in`.whatsaga.whatsapplongerstatus.status.uploader.R
import `in`.whatsaga.whatsapplongerstatus.status.uploader.ui.fragment.DirectBusinessFragment
import `in`.whatsaga.whatsapplongerstatus.status.uploader.ui.fragment.DirectWhatsappFragment
import `in`.whatsaga.whatsapplongerstatus.status.uploader.utils.Common

class DirectActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var mViewPager: ViewPager? = null
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
   
    private var retryAttempt = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Common.setLocale(this)
        setContentView(R.layout.activity_direct)

        tabLayout = findViewById<TabLayout>(R.id.tabs)
        mViewPager = findViewById<ViewPager>(R.id.container)
        initTabs()
    }

    private fun initTabs() {
        mSectionsPagerAdapter = SectionsPagerAdapter(
            supportFragmentManager
        )
        mViewPager!!.adapter = mSectionsPagerAdapter
        tabLayout!!.setupWithViewPager(mViewPager)
        mViewPager!!.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(mViewPager))
        mViewPager!!.currentItem = 0
    }

    inner class SectionsPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(
        fm!!
    ) {
        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                DirectWhatsappFragment()
            } else DirectBusinessFragment()
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> resources.getString(R.string.whatsapp)
                1 -> {

                    resources.getString(R.string.business)
                }
                else -> ""
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }

    override fun onBackPressed() {

        finish()
    }

    override fun onResume() {
        super.onResume()
    }


}