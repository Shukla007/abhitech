package com.smart.teach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.smart.teach.Fragment.Home;

public class MainActivity extends AppCompatActivity {
    Button button;
    BottomNavigationView bottom_navigation;
     DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer);
loadFragment(new Home());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        navigationView = findViewById(R.id.nav_view);
        toggle.setDrawerIndicatorEnabled(true);//enable hamburger sign
        drawer.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_btnD:
                         Toast.makeText(getApplicationContext(),"Home Panel is Open",Toast.LENGTH_LONG).show();
                        drawer.closeDrawer(GravityCompat.START);

                        break;


                    case R.id.notification_btn:
                         Toast.makeText(getApplicationContext(),"Setting Panel is Open",Toast.LENGTH_LONG).show();
                        drawer.closeDrawer(GravityCompat.START);




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
                                loadFragment(new Home());
                                Toast.makeText(MainActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });



              }
    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null).commit();

    }

}
