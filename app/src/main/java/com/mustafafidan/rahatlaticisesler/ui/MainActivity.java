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
import com.mustafafidan.rahatlaticisesler.ui.library.LibraryFragment;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0,false);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1,false);
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
        LibraryFragment libraryFragment = LibraryFragment.newInstance();


        baseViewPagerAdapter.addFrag(favoritesFragment,"");
        baseViewPagerAdapter.addFrag(libraryFragment,"");



        viewPager.setAdapter(baseViewPagerAdapter);


    }

}
