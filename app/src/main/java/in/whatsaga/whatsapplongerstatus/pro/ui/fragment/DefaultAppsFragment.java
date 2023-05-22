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
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.DirectActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.SubscribeActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.WebActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.status.StatusMainActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.smartkit.EmojiActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.smartkit.StylishTextActivity;
import in.whatsaga.whatsapplongerstatus.pro.ui.smartkit.TextRepeaterActivity;
import in.whatsaga.whatsapplongerstatus.pro.whatsaga.MainAppActivity;

public class DefaultAppsFragment extends Fragment implements View.OnClickListener {


    public DefaultAppsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default_apps, container, false);

        view.findViewById(R.id.status_saver).setOnClickListener(this);
        view.findViewById(R.id.text_to_emoji).setOnClickListener(this);
        view.findViewById(R.id.text_repeat).setOnClickListener(this);
        view.findViewById(R.id.stylish).setOnClickListener(this);
        view.findViewById(R.id.direct_chat).setOnClickListener(this);
        view.findViewById(R.id.conversation).setOnClickListener(this);
        view.findViewById(R.id.media).setOnClickListener(this);
        view.findViewById(R.id.web).setOnClickListener(this);
        view.findViewById(R.id.long_status).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.status_saver:
                startActivity(new Intent(requireContext(), StatusMainActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.text_to_emoji:
                startActivity(new Intent(requireContext(), EmojiActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.text_repeat:
                startActivity(new Intent(requireContext(), TextRepeaterActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.stylish:
                startActivity(new Intent(requireContext(), StylishTextActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.direct_chat:
                startActivity(new Intent(requireContext(), DirectActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.conversation:
                startActivity(new Intent(requireContext(), ConversationActivity.class));
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.media:
                startActivity(new Intent(requireContext(), SubscribeActivity.class));
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