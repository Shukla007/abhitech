package com.smart.teach.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.viewpager.widget.ViewPager;

import com.smart.teach.Adapter.ViewPagerAdapter;
import com.smart.teach.R;
import com.smart.teach.joinUsers_Activity;

public class HomeH extends Fragment {
    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homemain, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);
        ViewPager.OnPageChangeListener viewpager = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        viewPager.setOnPageChangeListener(viewpager);

        return view;
    }
}

