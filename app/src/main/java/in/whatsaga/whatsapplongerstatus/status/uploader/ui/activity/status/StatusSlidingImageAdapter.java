package in.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.status;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;
import in.whatsaga.whatsapplongerstatus.status.uploader.R;
import in.whatsaga.whatsapplongerstatus.status.uploader.utils.Common;

import java.io.File;
import java.util.List;

public class StatusSlidingImageAdapter extends PagerAdapter {

    Context context;
    List<File> list;
    LayoutInflater inflater;
    VideoListener listener;
    List<String> onlyPaths;

    public StatusSlidingImageAdapter(Context context, List<File> list, VideoListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
    }
    public StatusSlidingImageAdapter(List<String> onlyPaths, Context context, VideoListener listener) {
        this.context = context;
        this.onlyPaths = onlyPaths;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
    }



    @Override
    public int getCount() {
        if (Common.isRorAbove()) {
            return onlyPaths.size();
        } else {
            return list.size();
        }

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = null;
        if (!Common.isRorAbove()) {
            imageLayout = inflater.inflate(R.layout.status_sliding, view, false);
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
            final ImageView videoIcon = imageLayout.findViewById(R.id.video_icon);
            Glide.with(context).load(list.get(position)).into(imageView);

            final FrameLayout video = imageLayout.findViewById(R.id.video_layout);
            final UniversalVideoView videoView = imageLayout.findViewById(R.id.video_view);
            final UniversalMediaController mediaController = imageLayout.findViewById(R.id.media_controller);
            videoView.setMediaController(mediaController);

            if (!list.get(position).getPath().contains(".mp4")) {
                videoIcon.setVisibility(View.GONE);
            } else {
                videoIcon.setVisibility(View.VISIBLE);
            }

            videoIcon.setOnClickListener(v -> {
                imageView.setVisibility(View.GONE);
                videoIcon.setVisibility(View.GONE);
                video.setVisibility(View.VISIBLE);
                listener.onPlayButtonClick(position, videoView, video, mediaController);
            });
        } else {
            imageLayout = inflater.inflate(R.layout.status_sliding, view, false);
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
            final ImageView videoIcon = imageLayout.findViewById(R.id.video_icon);
            Glide.with(context).load(onlyPaths.get(position)).into(imageView);
            final FrameLayout video = imageLayout.findViewById(R.id.video_layout);
            final UniversalVideoView videoView = imageLayout.findViewById(R.id.video_view);
            final UniversalMediaController mediaController = imageLayout.findViewById(R.id.media_controller);
            videoView.setMediaController(mediaController);

            if (!onlyPaths.get(position).contains(".mp4")) {
                videoIcon.setVisibility(View.GONE);
            } else {
                videoIcon.setVisibility(View.VISIBLE);
            }

            videoIcon.setOnClickListener(v -> {
                imageView.setVisibility(View.GONE);
                videoIcon.setVisibility(View.GONE);
                video.setVisibility(View.VISIBLE);
                listener.onPlayButtonClick(position, videoView, video, mediaController);
            });
        }


        view.addView(imageLayout);

        return imageLayout;
    }

    public interface VideoListener {
        void onPlayButtonClick(int pos, UniversalVideoView view, FrameLayout video, UniversalMediaController controller);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }
}
