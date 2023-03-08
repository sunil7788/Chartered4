package com.chartered4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.chartered4.models.IntroBean;

import java.util.ArrayList;

public class IntroViewPagerAdapter extends PagerAdapter {
    ArrayList<IntroBean> arrSlider;
    LayoutInflater inflater;
    Context context;
//    int position = 3;

    public IntroViewPagerAdapter(Context context, ArrayList<IntroBean> arrSlider) {
        this.context = context;
        this.arrSlider = arrSlider;
    }

    @Override
    public int getCount() {
        return arrSlider.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imgPreview;
        TextView txtTitle;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = inflater.inflate(R.layout.itemview_intro, container, false);

        imgPreview = itemview.findViewById(R.id.imgPreview);
        txtTitle = itemview.findViewById(R.id.txtTitle);

        imgPreview.setImageDrawable(arrSlider.get(position).getImg());
        txtTitle.setText(arrSlider.get(position).getTitle());

        //add item.xml to viewpager
        ((ViewPager) container).addView(itemview);
        return itemview;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);
    }

    /*@Override
    public float getPageWidth(int position) {
        return .20f;   //it is used for set page widht of view pager
    }*/
}
