package in.whatsaga.whatsapplongerstatus.pro.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.PurchaseInfo;
import com.fxn.stash.Stash;

import in.whatsaga.whatsapplongerstatus.pro.R;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.ConversationActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.DeletedMediaActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.WebActivity;
import in.whatsaga.whatsapplongerstatus.pro.utils.Constants;
import in.whatsaga.whatsapplongerstatus.pro.whatsaga.MainAppActivity;

public class PaidFragment extends Fragment implements BillingProcessor.IBillingHandler {
    BillingProcessor bp;

    Button yearlySUB;
    Button monthlySUB;
    Button weeklySUb;

    public PaidFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paid, container, false);

        yearlySUB = view.findViewById(R.id.twofoursix);
        monthlySUB = view.findViewById(R.id.twotwofive);
        weeklySUb = view.findViewById(R.id.twohundred);

        if (Stash.getString(Constants.PRO).equals(Constants.WEEKLY)) {
            weeklySUb.setText("Subscribed");
            weeklySUb.setEnabled(false);
        } else if (Stash.getString(Constants.PRO).equals(Constants.MONTHLY)) {
            monthlySUB.setText("Subscribed");
            monthlySUB.setEnabled(false);
        } else if (Stash.getString(Constants.PRO).equals(Constants.YEARLY)) {
            yearlySUB.setText("Subscribed");
            yearlySUB.setEnabled(false);
        }

        yearlySUB.setOnClickListener(v -> {
            bp.purchase(requireActivity(), Constants.TWO_FORTY_SIX_DOLLAR_PRODUCT);
        });

        monthlySUB.setOnClickListener(v -> {
            bp.purchase(requireActivity(), Constants.TWO_TWENTY_FIVE_DOLLAR_PRODUCT);
        });

        weeklySUb.setOnClickListener(v -> {
            bp.purchase(requireActivity(), Constants.TWO_HUNDRED_DOLLAR_PRODUCT);
        });

        bp = BillingProcessor.newBillingProcessor(requireContext(), Constants.LICENSE_KEY, this);
        bp.initialize();

        return view;
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable PurchaseInfo details) {
        Toast.makeText(requireContext(), "Purchase successful", Toast.LENGTH_SHORT).show();

        if (productId.equals(Constants.TWO_HUNDRED_DOLLAR_PRODUCT)){
            weeklySUb.setText("Subscribed");
            weeklySUb.setEnabled(false);
            Stash.put(Constants.PRO, Constants.WEEKLY);
        }

        if (productId.equals(Constants.TWO_TWENTY_FIVE_DOLLAR_PRODUCT)){
            monthlySUB.setText("Subscribed");
            monthlySUB.setEnabled(false);
            Stash.put(Constants.PRO, Constants.MONTHLY);
        }

        if (productId.equals(Constants.TWO_FORTY_SIX_DOLLAR_PRODUCT)){
            yearlySUB.setText("Subscribed");
            yearlySUB.setEnabled(false);
            Stash.put(Constants.PRO, Constants.YEARLY);
        }
            Stash.put(Constants.IS_PRO, true);
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Toast.makeText(requireContext(), "onBillingError: code: " + errorCode + " \n" + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {

    }

}