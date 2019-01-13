package views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;
import com.android.dooyd.R;
import com.bumptech.glide.Glide;
import datamodel.SlideItem;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {

    private Context context;
    private List<SlideItem> imageUrls;


    public SliderPagerAdapter(Context context, List<SlideItem> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_slide_item, container, false);


        Glide.with(context).load(imageUrls.get(position).getItemImageUrl()).into((AppCompatImageView) view.findViewById(R.id.slide_image_view));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
