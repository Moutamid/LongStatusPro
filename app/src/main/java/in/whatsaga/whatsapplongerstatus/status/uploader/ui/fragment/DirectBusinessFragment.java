package in.whatsaga.whatsapplongerstatus.status.uploader.ui.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.hbb20.CountryCodePicker;

import in.whatsaga.whatsapplongerstatus.status.uploader.R;
import in.whatsaga.whatsapplongerstatus.status.uploader.utils.Common;

import java.net.URLEncoder;

public class DirectBusinessFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText edNumber, edMessage;
    CountryCodePicker ccp;
    String countryCode;
    String number;
    String numberWithCountryCode;

    public DirectBusinessFragment() {
        // Required empty public constructor
    }

    public static DirectBusinessFragment newInstance(String param1, String param2) {
        DirectBusinessFragment fragment = new DirectBusinessFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_direct_business, container, false);

        ccp = view.findViewById(R.id.ccp22);
        edNumber = view.findViewById(R.id.ed_number);
        edMessage = view.findViewById(R.id.ed_msg);

        view.findViewById(R.id.send).setOnClickListener(this);
        view.findViewById(R.id.share).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send:
                if(!Common.isAppInstalled(getContext(),"com.whatsapp.w4b")){
                    Toast.makeText(getContext(), "WA Whatsapp not installed", Toast.LENGTH_SHORT).show();
                    return;
                }
                countryCode = ccp.getSelectedCountryCode();
                number = edNumber.getText().toString();

                if (number.length() != 0) {
                    send("com.whatsapp.w4b");
                } else {
                    Toast.makeText(getContext(), "Enter Number", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.share:
                shareMessage(edMessage.getText().toString());
                break;
        }
    }

    private void shareMessage( String message) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "share via"));
    }

    private void send(String pack) {
        try {
            if (number.charAt(0) == 0) {
                numberWithCountryCode = number.replaceAll("(^|[^0-9])0+", countryCode);
            } else {
                numberWithCountryCode = countryCode + number;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Enter Number", Toast.LENGTH_SHORT).show();
            return;
        }

        PackageManager packageManager = getContext().getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);


        try {
            String url = "https://api.whatsapp.com/send?phone=" + numberWithCountryCode + "&text=" + URLEncoder.encode(edMessage.getText().toString(), "UTF-8");
            i.setPackage(pack);
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Invalid Number", Toast.LENGTH_SHORT).show();
        }
    }
}