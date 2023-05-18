package in.whatsaga.whatsapplongerstatus.pro.adapters;

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
import in.whatsaga.whatsapplongerstatus.pro.R;
import in.whatsaga.whatsapplongerstatus.pro.utils.Common;

import java.io.File;
import java.util.List;

public class SlidingImageAdapter extends PagerAdapter {

    Context context;
    List<File> list;
    LayoutInflater inflater;
    VideoListener listener;

    public SlidingImageAdapter(Context context, List<File> list, VideoListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
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
        View imageLayout = inflater.inflate(R.layout.sliding_images_layout, view, false);
        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        final ImageView videoIcon = imageLayout.findViewById(R.id.video_icon);
        Glide.with(context).load(list.get(position)).into(imageView);

        final FrameLayout video = imageLayout.findViewById(R.id.video_layout);
        final UniversalVideoView videoView = imageLayout.findViewById(R.id.video_view);
        final UniversalMediaController mediaController = imageLayout.findViewById(R.id.media_controller);
        videoView.setMediaController(mediaController);


        if (Common.isImageFile(list.get(position).getPath())) {
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

        view.addView(imageLayout);

        return imageLayout;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    public interface VideoListener {
        void onPlayButtonClick(int pos, UniversalVideoView view, FrameLayout video, UniversalMediaController controller);
    }
}
