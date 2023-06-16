package in.whatsaga.whatsapplongerstatus.status.uploader.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.PurchaseInfo;
import com.anjlab.android.iab.v3.SkuDetails;
import com.fxn.stash.Stash;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions;

import java.util.ArrayList;
import java.util.List;

import in.whatsaga.whatsapplongerstatus.status.uploader.R;
import in.whatsaga.whatsapplongerstatus.status.uploader.adsense.Ads;
import in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.ConversationActivity;
import in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.DeletedMediaActivity;
import in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.DirectActivity;
import in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.MainActivity;
import in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.SettingsActivity;
import in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.WebActivity;
import in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.status.StatusMainActivity;
import in.whatsaga.whatsapplongerstatus.status.uploader.ui.smartkit.EmojiActivity;
import in.whatsaga.whatsapplongerstatus.status.uploader.ui.smartkit.StylishTextActivity;
import in.whatsaga.whatsapplongerstatus.status.uploader.ui.smartkit.TextRepeaterActivity;
import in.whatsaga.whatsapplongerstatus.status.uploader.utils.Constants;
import in.whatsaga.whatsapplongerstatus.status.uploader.whatsaga.MainAppActivity;

public class DefaultAppsFragment extends Fragment implements View.OnClickListener, BillingProcessor.IBillingHandler {
    BillingProcessor bp;
    AdRequest adRequest;
    private RewardedAd rewardedAd;
    ArrayList<String> ids;
    private final String TAG = "RewardAd";

    public DefaultAppsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default_apps, container, false);

        MainActivity activity = (MainActivity) getActivity();

        bp = BillingProcessor.newBillingProcessor(view.getContext(), Constants.LICENSE_KEY, this);
        bp.initialize();

        Ads.calledIniti(requireContext());
        adRequest = new AdRequest.Builder().build();
        TemplateView v1, v2, v3;
        v1 = view.findViewById(R.id.my_template);
        v2 = view.findViewById(R.id.my_template2);
        v3 = view.findViewById(R.id.my_template3);

        ImageView lock = view.findViewById(R.id.lock);
        ids = new ArrayList<>();
        ids.add(Constants.YEARLY_IN_WHATSAGA_WHATSAPPLONGERSTATUS_PRO);
        ids.add(Constants.MONTHLY_IN_WHATSAGA_WHATSAPPLONGERSTATUS_PRO);
        ids.add(Constants.WEEKLY_IN_WHATSAGA_WHATSAPPLONGERSTATUS_PRO);
        bp.getSubscriptionsListingDetailsAsync(ids, new BillingProcessor.ISkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(@Nullable List<SkuDetails> products) {
                Log.d("PURSS", "Size : " + products.size());
                for (int i = 0; i < products.size(); i++) {
                    boolean isSub = products.get(i).isSubscription;
                    Stash.put(Constants.IS_PRO, isSub);
                    if (Stash.getBoolean(Constants.IS_PRO, false)) {
                        lock.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onSkuDetailsError(String error) {

            }
        });

        if (Stash.getBoolean(Constants.IS_PRO, false)) {
            v1.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
            v3.setVisibility(View.GONE);
        }

        if (bp.isSubscribed(ids.get(0)) || bp.isSubscribed(ids.get(1)) || bp.isSubscribed(ids.get(2))) {
            v1.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
            v3.setVisibility(View.GONE);
            Stash.put(Constants.IS_PRO, true);
        } else {
            Stash.put(Constants.IS_PRO, false);
            v1.setVisibility(View.VISIBLE);
            v2.setVisibility(View.VISIBLE);
            v3.setVisibility(View.VISIBLE);
            Ads.showNativeAd(requireContext(), v1);
            Ads.showNativeAd(requireContext(), v2);
            Ads.showNativeAd(requireContext(), v3);
        }

        if (bp.isSubscribed(ids.get(0)) || bp.isSubscribed(ids.get(1)) || bp.isSubscribed(ids.get(2))) {
            lock.setVisibility(View.GONE);
            Stash.put(Constants.IS_PRO, true);
        }

        if (Stash.getBoolean(Constants.IS_PRO, false)) {
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
            if (Stash.getBoolean(Constants.IS_PRO, false)) {
                startActivity(new Intent(requireContext(), DeletedMediaActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                Ads.loadIntersAD(requireContext(), requireActivity(), DeletedMediaActivity.class);
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
                if (Stash.getBoolean(Constants.IS_PRO, false)) {
                    startActivity(new Intent(requireContext(), DirectActivity.class));
                    requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else {
                    Ads.loadIntersAD(requireContext(), requireActivity(), DirectActivity.class);
                }

                break;
            case R.id.conversation:
                if (Stash.getBoolean(Constants.IS_PRO, false)) {
                    startActivity(new Intent(requireContext(), ConversationActivity.class));
                    requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else {
                    Ads.loadIntersAD(requireContext(), requireActivity(), ConversationActivity.class);
                }
                break;
            case R.id.web:
                if (Stash.getBoolean(Constants.IS_PRO, false)) {
                    startActivity(new Intent(requireContext(), WebActivity.class));
                    requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else {
                    Ads.loadIntersAD(requireContext(), requireActivity(), WebActivity.class);
                }
                break;

            case R.id.long_status:
                if (Stash.getBoolean(Constants.IS_PRO, false)) {
                    startActivity(new Intent(requireContext(), MainAppActivity.class));
                    requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else {
                    showAdDialog();
                }

                break;
        }
    }

    private void showAdDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_ad);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();

        Button watch = dialog.findViewById(R.id.watch);
        Button cancel = dialog.findViewById(R.id.cancel);

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        watch.setOnClickListener(v -> {
            dialog.dismiss();
            loadAd();
        });

    }

    private void loadAd() {
        RewardedAd.load(requireContext(), getResources().getString(R.string.Reward_ID),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, "Error : " + loadAdError.toString());
                        rewardedAd = null;
                        Toast.makeText(requireContext(), "Ad load failed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(requireContext(), MainAppActivity.class));
                        requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                        Log.d(TAG, "Ad was loaded.");
                        ServerSideVerificationOptions options = new ServerSideVerificationOptions
                                .Builder()
                                .setCustomData("SAMPLE_CUSTOM_DATA_STRING")
                                .build();
                        rewardedAd.setServerSideVerificationOptions(options);
                        showAd();
                    }
                });

    }

    private void showAd() {
        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.");
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad dismissed fullscreen content.");
                rewardedAd = null;
                startActivity(new Intent(requireContext(), MainAppActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.");
                rewardedAd = null;
            }

            @Override
            public void onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.");
            }
        });

        if (rewardedAd != null) {
            rewardedAd.show(requireActivity(), new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d(TAG, "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                }
            });
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.");
        }
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable PurchaseInfo details) {

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }
}