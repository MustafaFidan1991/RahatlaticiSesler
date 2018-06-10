package com.mustafafidan.rahatlaticisesler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.mustafafidan.rahatlaticisesler.R;
import com.mustafafidan.rahatlaticisesler.base.BaseViewPagerAdapter;
import com.mustafafidan.rahatlaticisesler.model.Sound;
import com.mustafafidan.rahatlaticisesler.ui.favorites.FavoritesFragment;
import com.mustafafidan.rahatlaticisesler.ui.library.LibraryFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;


    FavoritesFragment favoritesFragment = null;
    LibraryFragment libraryFragment = null;

    public static final int SONG_DETAIL_CODE = 1;

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

        viewPager = findViewById(R.id.viewpager);
        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        setupViewPager();
    }



    /*
    *
    * viewpager'a fragment'lar ekleniyor
    *
    * */
    void setupViewPager(){

        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(getSupportFragmentManager());

        favoritesFragment = FavoritesFragment.newInstance();
        libraryFragment = LibraryFragment.newInstance();


        baseViewPagerAdapter.addFrag(favoritesFragment,"");
        baseViewPagerAdapter.addFrag(libraryFragment,"");



        viewPager.setAdapter(baseViewPagerAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        /*
        * result olarak gelen data burda favoritesFragment'a iletiliyor
        * */
        if(SONG_DETAIL_CODE == requestCode){
            Bundle bundle = data.getExtras();
            List<Sound> favoriteItems = (ArrayList<Sound>) bundle.getSerializable("favoriteItems");
            List<Sound> unFavoriteItems = (ArrayList<Sound>)bundle.getSerializable("unFavoriteItems");

            favoritesFragment.updateItems(favoriteItems,unFavoriteItems);

        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
