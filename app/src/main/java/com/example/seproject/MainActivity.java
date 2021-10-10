package com.example.seproject;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;



public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    Bundle bundle_toFragment = new Bundle();
    public static String userID;

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    WorkFragment workFragment = new WorkFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.flFragment, homeFragment.newInstance()).commit();

        Bundle bundle =   getIntent().getExtras();
       userID = bundle.getString("userID");
        bundle_toFragment.putString("userID", userID);

        replaceFragment(homeFragment);


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
                profileFragment.setArguments(bundle_toFragment);
                transaction.replace(R.id.flFragment, profileFragment).commitAllowingStateLoss();

                return true;
            }
            return false;
        }
    }

    public void replaceFragment(Fragment fragment)
    {
        fragment.setArguments(bundle_toFragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment).addToBackStack(null).commit();
//                commitAllowingStateLoss();
    }

    public void bundlePutString(String key, String val){
        bundle_toFragment.putString(key, val);
    }


}


