package com.smart.teach.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
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

public class HomeH extends Fragment {
    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;
    View view;
    FloatingActionButton fab;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore fStore;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homemain, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        fab = view.findViewById(R.id.fab_btn);

        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        checkRole(auth.getCurrentUser().getUid());


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "You Clicked on Create button", Toast.LENGTH_SHORT).show();
            }
        });

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
}

