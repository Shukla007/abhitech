package com.smart.teach.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.smart.teach.R;

public class ViewPagerAdapter extends PagerAdapter {

    private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
    private Context ctx;
    private LayoutInflater pagerLayoutInflater;
    View splashView;

    public ViewPagerAdapter(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        pagerLayoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        splashView = pagerLayoutInflater.inflate( R.layout.viewpage, container, false);

        ImageView splashImageView = (ImageView) splashView.findViewById(R.id.imagepage);

        //Go login activitywhen skip is clicked
//        splashSkip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  goLoginActivity();
//            }
//        });

        splashImageView.setImageResource(images[position]);
        container.addView(splashView);

        return splashView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

//    public void goLoginActivity(){
//        Intent intent = new Intent(ctx, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        ctx.startActivity(intent);
//        ((Activity)ctx).finish();
//    }
}

