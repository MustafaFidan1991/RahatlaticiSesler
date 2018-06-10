package com.mustafafidan.rahatlaticisesler.ui.detail;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mustafafidan.rahatlaticisesler.BR;
import com.mustafafidan.rahatlaticisesler.R;
import com.mustafafidan.rahatlaticisesler.base.BaseActivity;
import com.mustafafidan.rahatlaticisesler.base.BaseRecyclerViewAdapter;
import com.mustafafidan.rahatlaticisesler.databinding.ActivitySongDetailBinding;
import com.mustafafidan.rahatlaticisesler.databinding.ActivitySongDetailItemBinding;
import com.mustafafidan.rahatlaticisesler.model.Sound;

import java.util.ArrayList;
import java.util.List;

public class SongDetailActivity extends BaseActivity<SongDetailPresenter,ActivitySongDetailBinding>  implements SongDetailView{

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_song_detail;
    }

    @Override
    protected SongDetailPresenter instantiatePresenter() {
        return new SongDetailPresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setLayoutManager(new LinearLayoutManager(this));
        binding.setAdapter(new BaseRecyclerViewAdapter(new ArrayList<Sound>(),this,R.layout.activity_song_detail_item,BR.sound,presenter,BR.presenter,null));


        presenter.getItems();
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void showLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.GONE);

    }

    @Override
    public void hideLoading() {
        binding.progressBar.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateItems(List<Sound> favorites) {
        binding.getAdapter().update(favorites);
        binding.notifyChange();
    }
}
