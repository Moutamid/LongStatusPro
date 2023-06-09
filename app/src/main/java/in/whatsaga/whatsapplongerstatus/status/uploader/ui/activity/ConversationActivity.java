package in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import in.whatsaga.whatsapplongerstatus.status.uploader.R;
import in.whatsaga.whatsapplongerstatus.status.uploader.ui.fragment.BusinessChatFragment;
import in.whatsaga.whatsapplongerstatus.status.uploader.ui.fragment.ChatFragment;
import in.whatsaga.whatsapplongerstatus.status.uploader.utils.Common;

public class ConversationActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager mViewPager;


    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Common.setLocale(this);
        setContentView(R.layout.activity_conversation);


        tabLayout = findViewById(R.id.tabs);
        mViewPager = findViewById(R.id.container);
        initTabs();
    }

    private void initTabs() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.setCurrentItem(0);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new ChatFragment();
            } else return new BusinessChatFragment();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.whatsapp);
                case 1:

                    return getResources().getString(R.string.business);
                default:
                    return "";
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();

        Common.startAnimation(ConversationActivity.this);
    }
}
