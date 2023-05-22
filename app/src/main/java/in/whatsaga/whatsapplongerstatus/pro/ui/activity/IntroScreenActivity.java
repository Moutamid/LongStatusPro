package in.whatsaga.whatsapplongerstatus.pro.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import in.whatsaga.whatsapplongerstatus.pro.R;
import in.whatsaga.whatsapplongerstatus.pro.adapters.IntroViewPagerAdapter;
import in.whatsaga.whatsapplongerstatus.pro.models.ScreenItems;
import in.whatsaga.whatsapplongerstatus.pro.utils.SharedPref;

public class IntroScreenActivity extends AppCompatActivity {
    List<ScreenItems> list;
    ViewPager screenPager;
    IntroViewPagerAdapter adapter;
    TabLayout tablayout;
    Button next;
    SharedPref sharedPref = null;
    int pos = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);
        getSupportActionBar().hide();

        sharedPref = new SharedPref(this);


        list = new ArrayList<>();

        list.add(new ScreenItems(getString(R.string.how_it_work), getString(R.string.slide_one), R.drawable.intro1));
        list.add(new ScreenItems( getString(R.string.required), getString(R.string.slide_two), R.drawable.intro2));
        list.add(new ScreenItems(  getString(R.string.disclaimer), getString(R.string.slide_three), R.drawable.nodata));

        screenPager = findViewById(R.id.viewPager);
        tablayout = findViewById(R.id.tablayout);
        next = findViewById(R.id.next);

        adapter = new IntroViewPagerAdapter(this, list);
        screenPager.setAdapter(adapter);

        screenPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == list.size()-1){
                    loadScreen();
                } else {
                    tablayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tablayout.setupWithViewPager(screenPager);

        next.setOnClickListener(v -> {
            pos = screenPager.getCurrentItem();
            if (pos < list.size()){
                pos++;
                if (pos == list.size()-1) {
                    loadScreen();
                }
                screenPager.setCurrentItem(pos);
            } if (pos >= list.size()) {
                if (sharedPref.getAppLaunch()){
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    sharedPref.setAppLaunch(true);
                    startActivity(new Intent(this, AllPermissionsActivity.class));
                    finish();
                }
            }
        });

    }

    private void loadScreen() {
        tablayout.setVisibility(View.GONE);
    }
}