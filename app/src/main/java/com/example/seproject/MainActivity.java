package com.example.seproject;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;



public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    WorkFragment workFragment = new WorkFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flFragment, homeFragment).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new ItemSelectedListener());

    }


    class ItemSelectedListener implements NavigationBarView.OnItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            int id = item.getItemId();

            if (id == R.id.menu_home) {
                transaction.replace(R.id.flFragment, homeFragment).commitAllowingStateLoss();
                return true;
            } else if (id == R.id.menu_work) {
                transaction.replace(R.id.flFragment, workFragment).commitAllowingStateLoss();
                return true;
            } else if (id == R.id.menu_profile) {
                transaction.replace(R.id.flFragment, profileFragment).commitAllowingStateLoss();
                return true;
            }
            return false;
        }
    }
}


