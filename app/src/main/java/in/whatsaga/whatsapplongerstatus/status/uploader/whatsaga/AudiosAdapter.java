package in.whatsaga.whatsapplongerstatus.status.uploader.whatsaga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import in.whatsaga.whatsapplongerstatus.status.uploader.R;

public class AudiosAdapter extends RecyclerView.Adapter {

    private static final String TAG = AudiosAdapter.class.getSimpleName();

    private Context context;
    private List<File> mediaInfoList;

    public AudiosAdapter(Context context, List<File> mediaInfoList) {
        this.context = context;
        this.mediaInfoList = mediaInfoList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_audio_item, parent, false);
        return new VideoViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        VideoViewHolder holder1 = (VideoViewHolder) holder;
        holder1.bindView(new File(mediaInfoList.get(position).getPath()));

    }

    @Override
    public int getItemCount() {
        return mediaInfoList.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        private ImageView imageViewPlay;
        private ImageView imageViewOptions;
        private TextView textViewDateCreated;
        private TextView textViewSize;

        VideoViewHolder(View itemView) {
            super(itemView);
            imageViewPlay = itemView.findViewById(R.id.imageViewPlay);
            imageViewOptions = itemView.findViewById(R.id.imageViewOptions);
            textViewDateCreated = itemView.findViewById(R.id.textViewDateCreated);
            textViewSize = itemView.findViewById(R.id.textViewSize);
            itemView.setOnClickListener(v -> playAudio());
            imageViewOptions.setOnClickListener(this);
        }

        public void bindView(File mediaFile) {
            textViewDateCreated.setText(mediaFile.getName());
            String size = "Size : " + (mediaFile.length() / 1024) + " kb";
            textViewSize.setText(size);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageViewOptions:
                    showPopup();
                    break;
            }
        }

        private void playAudio() {
            Common.playAudio(itemView.getContext(), mediaInfoList.get(getAdapterPosition()).getPath());
        }

        private void showPopup() {
            PopupMenu popup = new PopupMenu(itemView.getContext(), imageViewOptions);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.audio_actions, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    if (Common.deleteFile(mediaInfoList.get(getAdapterPosition()).getPath())) {
                        mediaInfoList.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    } else Toast.makeText(itemView.getContext(),
                            "Failed to delete file", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_share:
                    Common.shareAudioFile(itemView.getContext(),
                            mediaInfoList.get(getAdapterPosition()).getPath(),
                            "Share Audio");
                    break;
            }
            return false;
        }
    }
}
