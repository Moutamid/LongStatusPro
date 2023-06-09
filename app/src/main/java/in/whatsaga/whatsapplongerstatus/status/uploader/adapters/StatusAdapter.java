package in.whatsaga.whatsapplongerstatus.status.uploader.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import in.whatsaga.whatsapplongerstatus.status.uploader.R;
import in.whatsaga.whatsapplongerstatus.status.uploader.models.MediaModel;
import in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.status.StatusViewPager;
import in.whatsaga.whatsapplongerstatus.status.uploader.utils.Common;
import in.whatsaga.whatsapplongerstatus.status.uploader.utils.Constants;

import java.io.File;
import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusHolder> {

    Context context;
    List<MediaModel> list;
    LayoutInflater inflater;
    OnItemClick listener;
    boolean isSelection;
    int count = 0;
    private MediaModel mediaModel;
    private boolean isSelectAll = false;
    String type;

    public StatusAdapter(Context context, List<MediaModel> list, OnItemClick listener, String type) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.type = type;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StatusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.status_item, parent, false);
        return new StatusHolder(view);
    }

    public void selectAll() {
        isSelectAll = true;
        count = list.size();
    }

    public void setSelectionValue() {
        isSelection = false;
    }

    public void unSelectAll() {
        isSelectAll = false;
        count = 0;
    }

    @Override
    public void onBindViewHolder(@NonNull StatusHolder holder, int position) {

        MediaModel mediaModel = list.get(position);
        File file = new File(mediaModel.getPath());

        if (isSelectAll) {
            mediaModel.setSelected(true);
            holder.selectView.setVisibility(View.VISIBLE);
        } else {
            mediaModel.setSelected(false);
            holder.selectView.setVisibility(View.GONE);
        }

        if (file.getName().contains(".mp4")) {
            holder.videoLayout.setVisibility(View.VISIBLE);
        } else {
            holder.videoLayout.setVisibility(View.GONE);
        }

        if (Build.VERSION.SDK_INT >= 30)
            Glide.with(context).load(list.get(position).getPath()).into(holder.image);
        else
            Glide.with(context).load(file.getPath()).into(holder.image);

        holder.image.setPadding(0, 0, 0, 0);

        try {
            String duration = Common.getDuration(file);
            holder.duration.setText(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class StatusHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        ImageView image;
        TextView duration;
        RelativeLayout videoLayout;
        private RelativeLayout selectView;

        public StatusHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            duration = itemView.findViewById(R.id.duration_text);
            videoLayout = itemView.findViewById(R.id.media_layout);
            selectView = itemView.findViewById(R.id.select_view);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            if (!isSelection) {
                count = 1;
                listener.onLongClick(getAdapterPosition(), count);
                vibrate();
                mediaModel = list.get(getAdapterPosition());
                mediaModel.setSelected(true);
                isSelection = true;
                selectView.setVisibility(View.VISIBLE);
            }
            return true;
        }

        @Override
        public void onClick(View v) {
            if (isSelection) {
                mediaModel = list.get(getAdapterPosition());
                mediaModel.setSelected(!mediaModel.isSelected());
                if (mediaModel.isSelected()) {
                    selectView.setVisibility(View.VISIBLE);
                    count++;
                } else {
                    count--;
                    selectView.setVisibility(View.GONE);
                    if (count == 0) {
                        isSelection = false;
                    }
                }
                listener.onItemClicked(getAdapterPosition(), count);

            } else {
                Intent intent = new Intent(context, StatusViewPager.class);
                intent.putExtra("position", getAdapterPosition());
                intent.putExtra(Constants.TYPE, type);
                context.startActivity(intent);
            }
        }
    }

    private void vibrate() {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            assert v != null;
            v.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            assert v != null;
            v.vibrate(500);
        }
    }

    public interface OnItemClick {
        void onItemClicked(int pos, int count);

        void onLongClick(int pos, int count);
    }
}
