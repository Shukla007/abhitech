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

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smart.teach.R;
import com.smart.teach.model.livebatchModel;
import java.util.List;


public class ViewPagerAdapter extends PagerAdapter {

    List<Integer> list;
//    ViewPagerAdapter(List<Integer> imageList){
//        this.list=imageList;
//    }
  static   private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
    private Context ctx;
    private LayoutInflater pagerLayoutInflater;
    View splashView;

    public ViewPagerAdapter(Context ctx,List<Integer> imageList){
        this.ctx = ctx;
        this.list=imageList;
    }

    @Override
    public int getCount() {
        return list.size();
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
        //Glide.with(ctx).load().into(splashImageView);

splashImageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Snackbar.make(view,"Image"+position,Snackbar.LENGTH_LONG).show();
    }
});
        //Go login activitywhen skip is clicked
//        splashSkip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  goLoginActivity();
//            }
//        });

        StorageReference storage = FirebaseStorage.getInstance().getReference().child("CourseImages");



        splashImageView.setImageResource(list.get(position));
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

