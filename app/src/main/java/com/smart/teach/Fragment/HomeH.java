package com.smart.teach.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
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


public class HomeH extends Fragment implements View.OnClickListener{
    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;
    View view;
    Timer timer;
    int page = 1;
    Handler handler;
    CardView cardView1,cardView2,cardView3,cardView4;
    FloatingActionButton fab;
    FirebaseAuth auth;
    FirebaseUser user;
    ImageView imageView;
    String userID;
    FirebaseFirestore fStore;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homemain, container, false);
        viewPager = view.findViewById(R.id.viewpager);

        fab = view.findViewById(R.id.fab_btn);
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.image1);
        imageList.add(R.drawable.image2);

        imageList.add(R.drawable.image3);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        userID=user.getUid();
        cardView1=view.findViewById(R.id.card1);
        cardView2=view.findViewById(R.id.card2);
        cardView3=view.findViewById(R.id.card3);
        cardView4=view.findViewById(R.id.card4);
        imageView=view.findViewById(R.id.courseImg);
        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        cardView4.setOnClickListener(this);
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        checkRole(auth.getCurrentUser().getUid());

        if(user.getPhotoUrl()!=null){
            Glide.with(this).load(user.getPhotoUrl()).into(imageView);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new createBatch());
                Toast.makeText(getActivity(), "You clicked on Create button", Toast.LENGTH_SHORT).show();

                loadFragment(new createBatch());
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



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.card1:
              loadFragment(new courseDetail());
                break;
            case R.id.card2:
                loadFragment(new courseDetail());
                break;

            case R.id.card3:
                loadFragment(new courseDetail());
                break;

            case R.id.card4:
                loadFragment(new courseDetail());
                break;



            default:
        }


    }


    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null).commit();

    }
}

