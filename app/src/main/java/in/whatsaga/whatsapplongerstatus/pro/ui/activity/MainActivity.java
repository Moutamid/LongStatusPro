package in.whatsaga.whatsapplongerstatus.pro.ui.activity;

import static in.whatsaga.whatsapplongerstatus.pro.ui.activity.AllPermissionsActivity.ACTION_NOTIFICATION_LISTENER_SETTINGS;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import in.whatsaga.whatsapplongerstatus.pro.R;
import in.whatsaga.whatsapplongerstatus.pro.models.AppStatusModel;
import in.whatsaga.whatsapplongerstatus.pro.services.NLService;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.status.StatusMainActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.fragment.DefaultAppsFragment;
import in.whatsaga.whatsapplongerstatus.pro.ui.fragment.PaidFragment;
import in.whatsaga.whatsapplongerstatus.pro.ui.smartkit.EmojiActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.smartkit.StylishTextActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.smartkit.TextRepeaterActivity;
import in.whatsaga.whatsapplongerstatus.pro.utils.Common;
import in.whatsaga.whatsapplongerstatus.pro.utils.Constants;
import in.whatsaga.whatsapplongerstatus.pro.whatsaga.MainAppActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    Toolbar toolbar;
    private boolean isActivityLive = true;
    private AppStatusModel appStatusModel;
    private boolean isNewActivityLaunch = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Common.setLocale(this);
        setContentView(R.layout.activity_main);

        Constants.checkApp(this);

        //     getAppStatus();


        Common.createFolders();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        toggleNotificationListenerService();

        setupViewPager();

/*        findViewById(R.id.about).setOnClickListener(this);
        findViewById(R.id.conversation).setOnClickListener(this);
        findViewById(R.id.media).setOnClickListener(this);
        findViewById(R.id.web).setOnClickListener(this);
        findViewById(R.id.status_saver).setOnClickListener(this);
        findViewById(R.id.text_to_emoji).setOnClickListener(this);
        findViewById(R.id.text_repeat).setOnClickListener(this);
        findViewById(R.id.stylish).setOnClickListener(this);
        findViewById(R.id.direct_chat).setOnClickListener(this);
        findViewById(R.id.long_status).setOnClickListener(this);*/

    }


    private void toggleNotificationListenerService() {
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(this, NLService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        pm.setComponentEnabledSetting(new ComponentName(this, NLService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            ActivityCompat.finishAffinity(MainActivity.this);
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.instructions) {
            startActivity(new Intent(MainActivity.this, IntroActivity.class));
            finish();
        } else if (id == R.id.long_status) {
            startActivity(new Intent(MainActivity.this, MainAppActivity.class));
            finish();
        } else if (id == R.id.settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            finish();
        } else if (id == R.id.languages) {
            startActivity(new Intent(MainActivity.this, LanguageActivity.class));
            finish();
        } else if (id == R.id.restart) {
            startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
        } else if (id == R.id.feedback) {
            startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
        } else if (id == R.id.privacy) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://supertechapps.blogspot.com/2022/07/paid-privacy-policy.html")));
        } else if (id == R.id.share) {
            shareAppLink();
        } else if (id == R.id.rate) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
        } else if (id == R.id.quit) {
            finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void shareAppLink() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Long Status and RDM \n https://play.google.com/store/apps/details?id=" + getPackageName() + "");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "share via"));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onClick(View view) {
        /*switch (view.getId()) {
            case R.id.about:
                Toast.makeText(this, "what to do with this ?", Toast.LENGTH_SHORT).show();
                break;

            case R.id.conversation:


                startActivity(new Intent(MainActivity.this, ConversationActivity.class));
                break;

            case R.id.media:

                startActivity(new Intent(MainActivity.this, DeletedMediaActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.web:

                startActivity(new Intent(MainActivity.this, WebActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.status_saver:
                startActivity(new Intent(MainActivity.this, StatusMainActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.text_to_emoji:
                startActivity(new Intent(MainActivity.this, EmojiActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.text_repeat:
                startActivity(new Intent(MainActivity.this, TextRepeaterActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;

            case R.id.stylish:
                startActivity(new Intent(MainActivity.this, StylishTextActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.direct_chat:

                startActivity(new Intent(MainActivity.this, DirectActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                break;
            case R.id.long_status:

                startActivity(new Intent(MainActivity.this, MainAppActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;
        }*/
    }

    private void setupViewPager() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager());

        adapter.addFragment(new DefaultAppsFragment(), "Default");
        adapter.addFragment(new PaidFragment(), "Paid");

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return this.mFragmentList.get(arg0);
        }

        @Override
        public int getCount() {
            return this.mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return this.mFragmentTitleList.get(position);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Common.setLocale(this);

    }


    public void onDestroy() {
        super.onDestroy();

    }

}