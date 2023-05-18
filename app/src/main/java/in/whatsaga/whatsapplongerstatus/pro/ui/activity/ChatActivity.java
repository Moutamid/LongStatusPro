package in.whatsaga.whatsapplongerstatus.pro.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.mikhaellopez.circularimageview.CircularImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import in.whatsaga.whatsapplongerstatus.pro.R;
import in.whatsaga.whatsapplongerstatus.pro.adapters.DetailMessageAdapter;
import in.whatsaga.whatsapplongerstatus.pro.persistence.HiddenMessage;
import in.whatsaga.whatsapplongerstatus.pro.persistence.Repository;
import in.whatsaga.whatsapplongerstatus.pro.services.NLService;
import in.whatsaga.whatsapplongerstatus.pro.utils.Common;
import in.whatsaga.whatsapplongerstatus.pro.utils.StorageUtils;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MESSAGE_TITLE = "EXTRA_MESSAGE_TITLE";
    public static final String EXTRA_MESSAGE_PACK = "EXTRA_MESSAGE_PACK";
    public static final String EXTRA_MESSAGE_SHOW = "EXTRA_MESSAGE_SHOW";
    public static final String CHAT_NONE = "";
    public static final String PACK_NONE = "";
    public static String pack = PACK_NONE;
    private DetailMessageAdapter adapter;
    public static String title = CHAT_NONE;
    boolean isFromService;
    private EditText etReply;
    RecyclerView recyclerView;
    CircularImageView ivProfile;
    TextView textViewTitle;
    View canReply;



    public static Intent getStartIntent(Context context, String msgTitle, String pack, boolean isFromService) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_MESSAGE_TITLE, msgTitle);
        intent.putExtra(EXTRA_MESSAGE_PACK, pack);
        intent.putExtra(EXTRA_MESSAGE_SHOW, isFromService);
        return intent;
    }

    public static void start(Context context, String msgTitle, String pack) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_MESSAGE_TITLE, msgTitle);
        intent.putExtra(EXTRA_MESSAGE_PACK, pack);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Common.setLocale(this);
        setContentView(R.layout.activity_chat);




        this.isFromService = getIntent().getBooleanExtra(EXTRA_MESSAGE_SHOW, false);
        textViewTitle = findViewById(R.id.textViewTitle);
        etReply = findViewById(R.id.etReply);
        canReply = findViewById(R.id.viewReply);
        recyclerView = findViewById(R.id.recyclerView);
        ivProfile = findViewById(R.id.ivProfile);
        findViewById(R.id.ivReply).setOnClickListener(this);
        findViewById(R.id.ivBack).setOnClickListener(this);
        findViewById(R.id.ivProfile).setOnClickListener(this);


        initExtras();
        init();
        removeAnimation();
        StorageUtils.loadProfile(title, ivProfile);
        Repository.INSTANCE.markAsRead(title);
        registerObserver(title, pack);
        textViewTitle.setText(title);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onResume() {
        super.onResume();
        canReply();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onReplyClick() {
        String reply = etReply.getText().toString();
        if (validateEmpty(reply))
            return;
        if (NLService.reply(this, title, reply, pack)) {
            etReply.setText("");
        } else {
            Toast.makeText(this, getResources().getString(R.string.cant_send_message), Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void canReply() {
        if (NLService.canReply(title)) {
            canReply.setVisibility(View.VISIBLE);
        } else {
            canReply.setVisibility(View.GONE);
        }
    }


    private boolean validateEmpty(@NotNull String reply) {
        if (reply.isEmpty()) {
            Toast.makeText(this, getString(R.string.type_a_message), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    private String getExtraPack() {
        return getIntent().getStringExtra(EXTRA_MESSAGE_PACK);
    }

    private void registerObserver(String title, String pack) {
        Repository.INSTANCE.getAllSocialMessageLive(title, pack).observe(this, this::setData);
    }

    private String getExtraTitle() {
        return getIntent().getStringExtra(EXTRA_MESSAGE_TITLE);
    }

//    private void registerObserver(String title) {
//        Repository.INSTANCE.getAllSocialMessageLive(title).observe(this, this::setData);
//    }

    private void removeAnimation() {
        SimpleItemAnimator animator = (SimpleItemAnimator) recyclerView.getItemAnimator();
        if (animator != null) animator.setSupportsChangeAnimations(false);
    }

    private void initExtras() {
        title = getExtraTitle();
        pack = getExtraPack();

        Log.i("TAG", "title: " + title);
        Log.i("TAG", "pack: " + pack);
    }

    private void init() {
        adapter = new DetailMessageAdapter(this);
        recyclerView.setAdapter(adapter);
        setAdapterListener();
    }

    private void setAdapterListener() {
        adapter.setOnItemClickListener(new DetailMessageAdapter.OnItemClickListener() {
            @Override
            public void onSelectionStarted() {

            }

            @Override
            public void updateCount(int selectedCount) {

            }

            @Override
            public void onClick(int position) {

            }

            @Override
            public void onSelectionFinished() {

            }
        });
    }

    private void setData(List<HiddenMessage> hiddenMessages) {
        adapter.addAll(hiddenMessages);
    }

    @Override
    public void onBackPressed() {

        if (!isFromService)
            finish();
        else {
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivReply:

                    onReplyClick();

                break;

            case R.id.ivProfile:
            case R.id.ivBack:
                finish();
                break;
        }
    }




   

}
