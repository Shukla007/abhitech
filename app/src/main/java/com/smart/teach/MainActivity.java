package com.smart.teach;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.onesignal.OneSignal;
import com.smart.teach.Fragment.Home;
import com.smart.teach.Fragment.HomeH;
import com.smart.teach.Fragment.search;
import com.smart.teach.profile;

//import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    private static final String ONESIGNAL_APP_ID = "78227aaf-c889-4b2f-80e3-dd0ede27e2ff";
    Button button;
    BottomNavigationView bottom_navigation;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    NavigationView navigationView;
    ImageView imageView, profileImage;
    TextView textView, userName, userEmail;
    FloatingActionButton fab;
    private GoogleApiClient googleApiClient;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization here
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);


        imageView = findViewById(R.id.menu_clicked);
        drawer = findViewById(R.id.drawer);
        preferences = getApplicationContext().getSharedPreferences("myPref", 0);
        editor = preferences.edit();
        loadFragment(new HomeH());
        preferences = getApplicationContext().getSharedPreferences("myPref", 0);
        editor = preferences.edit();


        textView = findViewById(R.id.tv);
        userName = (TextView) findViewById(R.id.tv1);
        userEmail = (TextView) findViewById(R.id.tv2);
        profileImage = (ImageView) findViewById(R.id.profileD);
        fab = findViewById(R.id.fab_btn);




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        navigationView = findViewById(R.id.nav_view);
        toggle.setDrawerIndicatorEnabled(true);//enable hamburger sign
        drawer.addDrawerListener(toggle);


        //menubar
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_btnD:
                        Toast.makeText(getApplicationContext(), "Home Panel is Open", Toast.LENGTH_LONG).show();
                        drawer.closeDrawer(GravityCompat.START);

                        break;

                    case R.id.search_btnD:
                        loadFragment(new search());
                        drawer.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.Course_btnD:
                        Toast.makeText(getApplicationContext(), "Setting Panel is Open", Toast.LENGTH_LONG).show();
                        drawer.closeDrawer(GravityCompat.START);
                        break;


                }

                return true;
            }
        });



        //  User activity intents

        //  Bottom Navigation
        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_btn:
                        loadFragment(new HomeH());
                        break;
                    case R.id.profile_btn:
                        loadFragment(new Home());

                        break;
                    case R.id.search_btn:
                        loadFragment(new search());

                        break;
                }

                return true;
            }
        });
        String userNmaee = preferences.getString("userName", "");
        String userEmaill = preferences.getString("userEmail", "");
        String profilee = preferences.getString("profile", "");
        try {
            userName.setText(userNmaee);
            userEmail.setText(userEmaill);
            Glide.with(this).load(profilee).into(profileImage);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }



    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        drawer.closeDrawer(GravityCompat.START);
    }



}
