package com.mustafafidan.rahatlaticisesler.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.mustafafidan.rahatlaticisesler.R;
import com.mustafafidan.rahatlaticisesler.base.BaseViewPagerAdapter;
import com.mustafafidan.rahatlaticisesler.ui.favorites.FavoritesFragment;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        setupViewPager();
    }


    void setupViewPager(){

        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(getSupportFragmentManager());

        FavoritesFragment favoritesFragment = FavoritesFragment.newInstance();


        baseViewPagerAdapter.addFrag(favoritesFragment,"");

        viewPager.setAdapter(baseViewPagerAdapter);


    }

}
