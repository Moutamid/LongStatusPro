package in.whatsaga.whatsapplongerstatus.pro.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.whatsaga.whatsapplongerstatus.pro.R;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.ConversationActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.DeletedMediaActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.WebActivity;
import in.whatsaga.whatsapplongerstatus.pro.whatsaga.MainAppActivity;

public class PaidFragment extends Fragment implements View.OnClickListener {

    public PaidFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_paid, container, false);
        view.findViewById(R.id.conversation).setOnClickListener(this);
        view.findViewById(R.id.media).setOnClickListener(this);
        view.findViewById(R.id.web).setOnClickListener(this);
        view.findViewById(R.id.long_status).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.conversation:
                startActivity(new Intent(requireContext(), ConversationActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.media:
                startActivity(new Intent(requireContext(), DeletedMediaActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.web:
                startActivity(new Intent(requireContext(), WebActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.long_status:
                startActivity(new Intent(requireContext(), MainAppActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }
}