package in.whatsaga.whatsapplongerstatus.status.uploader.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import in.whatsaga.whatsapplongerstatus.status.uploader.R;
import in.whatsaga.whatsapplongerstatus.status.uploader.models.MediaModel;
import in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.ViewPagerActivity;
import in.whatsaga.whatsapplongerstatus.status.uploader.utils.Common;

import java.io.File;
import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MyHolder> {

    Context context;
    List<MediaModel> list;
    LayoutInflater inflater;
    boolean isSelection;
    MediaListener listener;
    int count = 0;
    Handler mainHandler = new Handler(Looper.getMainLooper());
    private MediaModel mediaModel;
    private boolean isSelectAll = false;
    String type;

    public MediaAdapter(Context context, List<MediaModel> list, MediaListener listener, String type) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.type = type;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.status_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
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
                    forGlide(file.getPath(), holder.image);
//                    Glide.with(context).load(file.getPath()).into(holder.image);
                    holder.image.setPadding(0, 0, 0, 0);
                    holder.videoLayout.setVisibility(View.VISIBLE);
                }
                else if (file.getPath().contains(".aac") || file.getPath().contains(".opus")) {
                    forGlide(file.getPath(), holder.image);
//                    Glide.with(context).load(R.drawable.ic_voice_big).into(holder.image);
                    holder.image.setPadding(120, 120, 120, 120);
                    holder.videoLayout.setVisibility(View.VISIBLE);
                }
//                else if (file.getPath().contains(".mp3")) {
//                    holder.videoLayout.setVisibility(View.VISIBLE);
//                    Glide.with(context).load(R.drawable.ic_audio_big).into(holder.image);
//                    holder.image.setPadding(120, 120, 120, 120);
//                }
                else {
                holder.videoLayout.setVisibility(View.GONE);
                forGlide(file.getPath(),holder.image);
//                    Glide.with(context).load(file.getPath()).into(holder.image);
                holder.image.setPadding(0, 0, 0, 0);
                }

                try {
                    String duration = Common.getDuration(file);
                    holder.duration.setText(duration);
                } catch (Exception e) {
                    e.printStackTrace();
                }



    }

    void runWithThread(String file,ImageView imageView){
        new Thread(new Runnable() {
            @Override
            public void run() {
                forGlide(file, imageView);
            }
        }).start();
    }

    void forGlide(String file, ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(file)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        //Play with bitmap
                        super.setResource(resource);
                    }
                });
    }


    @Override
    public int getItemCount() {
        return list.size();
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

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView image;
        TextView duration;
        RelativeLayout videoLayout;
        private RelativeLayout selectView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            duration = itemView.findViewById(R.id.duration_text);
            videoLayout = itemView.findViewById(R.id.media_layout);
            selectView = itemView.findViewById(R.id.select_view);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (isSelection) {
                mediaModel = list.get(getAdapterPosition());
                mediaModel.setSelected(!mediaModel.isSelected());
                Log.i("TAG", "check :" + mediaModel.isSelected());
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

                //send count here
                listener.onClick(getAdapterPosition(), count);

            } else {
                Intent intent = new Intent(context, ViewPagerActivity.class);
                intent.putExtra("position", getAdapterPosition());
                intent.putExtra("type", "media");
                intent.putExtra("DELETED_TYPE", type);
                context.startActivity(intent);
            }
        }

        @Override
        public boolean onLongClick(View view) {
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


    public interface MediaListener {
        void onClick(int pos, int count);

        void onLongClick(int pos, int count);
    }
}
