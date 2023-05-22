package in.whatsaga.whatsapplongerstatus.pro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import in.whatsaga.whatsapplongerstatus.pro.R;
import in.whatsaga.whatsapplongerstatus.pro.models.ScreenItems;

public class IntroViewPagerAdapter extends PagerAdapter {
    Context context;
    List<ScreenItems> list;

    public IntroViewPagerAdapter(Context context, List<ScreenItems> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.intro_layout, null);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView title = view.findViewById(R.id.textView);
        TextView desc = view.findViewById(R.id.desc);

        title.setText(list.get(position).getTitle());
        desc.setText(list.get(position).getDesc());
        imageView.setImageResource(list.get(position).getImage());

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
