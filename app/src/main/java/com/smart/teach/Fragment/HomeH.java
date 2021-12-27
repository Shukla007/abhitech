package com.smart.teach.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smart.teach.Adapter.ViewPagerAdapter;
import com.smart.teach.R;
import com.smart.teach.Teacher;
import com.smart.teach.joinUsers_Activity;
import com.smart.teach.student_login;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class HomeH extends Fragment {
    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;
    View view;
    Timer timer;
    int page = 1;
    Handler handler;

    FloatingActionButton fab;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore fStore;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homemain, container, false);
        viewPager = view.findViewById(R.id.viewpager);

        fab = view.findViewById(R.id.fab_btn);
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.image1);
        imageList.add(R.drawable.image2);
        imageList.add(R.drawable.image3);

        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        checkRole(auth.getCurrentUser().getUid());


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "You clicked on Create button", Toast.LENGTH_SHORT).show();
            }
        });

        viewPagerAdapter = new ViewPagerAdapter(getActivity(), imageList);
        viewPager.setAdapter(viewPagerAdapter);
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        handler = new Handler();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int i = viewPager.getCurrentItem();
                        if (i == imageList.size() - 1) {
                            i = 0;
                            viewPager.setCurrentItem(i, true);
                        } else {
                            i++;
                            viewPager.setCurrentItem(i, true);
                        }

                    }
                });

            }
        }, 4000, 4000);
        // pageSwitcher(page);
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

    private void checkRole(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.getString("isTeacher") != null) {
                    fab.setVisibility(View.VISIBLE);
                } else {
                    fab.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
        // in
        // milliseconds

    }

    private class RemindTask extends TimerTask {
        @Override
        public void run() {


            if (page > 4) { // In my case the number of pages are 5
                timer.cancel();
                // Showing a toast for just testing purpose

            } else {
                viewPager.setCurrentItem(page++);
            }


        }


        private void checkRole(String uid) {


        }
    }

}

