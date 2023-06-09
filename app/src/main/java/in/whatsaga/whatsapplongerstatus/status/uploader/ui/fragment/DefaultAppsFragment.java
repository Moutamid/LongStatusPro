package in.whatsaga.whatsapplongerstatus.status.uploader.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.PurchaseInfo;
import com.anjlab.android.iab.v3.SkuDetails;
import com.fxn.stash.Stash;
import com.google.android.ads.nativetemplates.TemplateView;

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
        TemplateView v1, v2, v3;
        v1 = view.findViewById(R.id.my_template);
        v2 = view.findViewById(R.id.my_template2);
        v3 = view.findViewById(R.id.my_template3);

        Ads.showNativeAd(requireContext(), v1);
        Ads.showNativeAd(requireContext(), v2);
        Ads.showNativeAd(requireContext(), v3);
        ImageView lock = view.findViewById(R.id.lock);
        ArrayList<String> ids = new ArrayList<>();
        ids.add(Constants.YEARLY_IN_WHATSAGA_WHATSAPPLONGERSTATUS_PRO);
        ids.add(Constants.MONTHLY_IN_WHATSAGA_WHATSAPPLONGERSTATUS_PRO);
        ids.add(Constants.WEEKLY_IN_WHATSAGA_WHATSAPPLONGERSTATUS_PRO);
        bp.getSubscriptionsListingDetailsAsync(ids, new BillingProcessor.ISkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(@Nullable List<SkuDetails> products) {
                Log.d("PURSS", "Size : " + products.size());
                for (int i = 0; i < products.size(); i++){
                    boolean isSub = products.get(i).isSubscription;
                    Stash.put(Constants.IS_PRO, isSub);
                    if (Stash.getBoolean(Constants.IS_PRO, false)){
                        lock.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onSkuDetailsError(String error) {

            }
        });

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
            if (Stash.getBoolean(Constants.IS_PRO, false)) {
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