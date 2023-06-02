package in.whatsaga.whatsapplongerstatus.pro.ui.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;

import java.util.Arrays;
import java.util.List;

import in.whatsaga.whatsapplongerstatus.pro.R;
import in.whatsaga.whatsapplongerstatus.pro.adsense.Ads;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.ConversationActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.DeletedMediaActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.DirectActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.MainActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.MediaActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.SettingsActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.SubscribeActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.WebActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.status.StatusMainActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.smartkit.EmojiActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.smartkit.StylishTextActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.smartkit.TextRepeaterActivity;
import in.whatsaga.whatsapplongerstatus.pro.utils.Constants;
import in.whatsaga.whatsapplongerstatus.pro.whatsaga.MainAppActivity;

public class DefaultAppsFragment extends Fragment implements View.OnClickListener {


    public DefaultAppsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default_apps, container, false);

        MainActivity activity = (MainActivity) getActivity();

        Ads.calledIniti(requireContext());
        TemplateView v1, v2, v3;
        v1 = view.findViewById(R.id.my_template);
        v2 = view.findViewById(R.id.my_template2);
        v3 = view.findViewById(R.id.my_template3);

        Ads.showNativeAd(requireContext(), v1);
        Ads.showNativeAd(requireContext(), v2);
        Ads.showNativeAd(requireContext(), v3);
        ImageView lock = view.findViewById(R.id.lock);
        if (Stash.getBoolean(Constants.IS_PRO, false)){
            lock.setVisibility(View.GONE);
        }

        view.findViewById(R.id.status_saver).setOnClickListener(this);
        view.findViewById(R.id.text_to_emoji).setOnClickListener(this);
        view.findViewById(R.id.text_repeat).setOnClickListener(this);
        view.findViewById(R.id.stylish).setOnClickListener(this);
        view.findViewById(R.id.direct_chat).setOnClickListener(this);
        view.findViewById(R.id.conversation).setOnClickListener(this);
        view.findViewById(R.id.setting).setOnClickListener(this);
        view.findViewById(R.id.media).setOnClickListener(v -> {
            if (Stash.getBoolean(Constants.IS_PRO, false)){
                Ads.loadIntersAD(requireContext(), requireActivity(), DeletedMediaActivity.class);
            } else {
                if (activity != null) {
                    ViewPager viewPager = activity.findViewById(R.id.viewPager);
                    viewPager.setCurrentItem(1);
                }
            }
        });
        view.findViewById(R.id.web).setOnClickListener(this);
        view.findViewById(R.id.long_status).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.status_saver:
                requireContext().startActivity(new Intent(requireContext(), StatusMainActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.text_to_emoji:
                requireContext().startActivity(new Intent(requireContext(), EmojiActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.setting:
                requireContext().startActivity(new Intent(requireContext(), SettingsActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.text_repeat:
                requireContext().startActivity(new Intent(requireContext(), TextRepeaterActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.stylish:
                requireContext().startActivity(new Intent(requireContext(), StylishTextActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.direct_chat:
                Ads.loadIntersAD(requireContext(), requireActivity(), DirectActivity.class);
                break;
            case R.id.conversation:
                Ads.loadIntersAD(requireContext(), requireActivity(), ConversationActivity.class);
                break;
            case R.id.web:
                Ads.loadIntersAD(requireContext(), requireActivity(), WebActivity.class);
                break;

            case R.id.long_status:
                startActivity(new Intent(requireContext(), MainAppActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }
}