package in.whatsaga.whatsapplongerstatus.status.uploader.adsense;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;

import in.whatsaga.whatsapplongerstatus.status.uploader.R;

public class Ads {
    private static InterstitialAd mInterstitialAd;
    public static AdRequest adRequest = new AdRequest.Builder().build();

    public static void calledIniti(Context context){
        MobileAds.initialize(context, initializationStatus -> {

        });
    }

    public static void showBannerAd(AdView mAdView){
        mAdView.loadAd(adRequest);
    }

    public static void loadIntersAD(Context context, Activity activity, Class intent) {

        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Ad is loading");
        dialog.show();

        InterstitialAd.load(context, context.getString(R.string.Interstial_ID), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(activity);
                            dialog.dismiss();
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.");
                        }

                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                context.startActivity(new Intent(context, intent));
                                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }
                        });

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                        context.startActivity(new Intent(context, intent));
                        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        dialog.dismiss();
                    }
                });
    }

    public static void showNativeAd(Context context, TemplateView myTemplate) {
        AdLoader adLoader = new AdLoader.Builder(context, context.getString(R.string.Native_ID))
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                        TemplateView template = myTemplate;
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();

        adLoader.loadAd(adRequest);
    }

}
