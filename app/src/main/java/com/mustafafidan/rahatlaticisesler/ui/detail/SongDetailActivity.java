package com.mustafafidan.rahatlaticisesler.ui.detail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

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


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int categoryId = getIntent().getIntExtra("categoryId",1);

        binding.setLayoutManager(new LinearLayoutManager(this));

        binding.setAdapter(new BaseRecyclerViewAdapter<Sound>(
                new ArrayList<>(),
                this,
                R.layout.activity_song_detail_item,
                BR.sound, presenter,
                BR.presenter,
                (itemBinding, data,position) -> {
                    if(presenter.isPlaying(data.getSoundId())){
                        ((ActivitySongDetailItemBinding) itemBinding).playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle));
                    }
                    else {
                        ((ActivitySongDetailItemBinding) itemBinding).playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_circle));
                    }

                    ((ActivitySongDetailItemBinding) itemBinding).playButton.setOnClickListener(view -> {
                        if(presenter.isPlayerNull(data.getSoundId())){
                            presenter.playMedia(data.getSoundUrl(), data.getSoundId(), () -> {
                                ((ActivitySongDetailItemBinding) itemBinding).playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle));
                            });
                        }
                        else{
                            if(presenter.isPlaying(data.getSoundId())){
                                presenter.pauseMedia(data.getSoundId(),()->{
                                    ((ActivitySongDetailItemBinding) itemBinding).playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_circle));
                                });
                            }
                            else{
                                presenter.resumeMedia(data.getSoundId(),()->{
                                    ((ActivitySongDetailItemBinding) itemBinding).playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle));
                                });
                            }
                        }
                    });


                    ((ActivitySongDetailItemBinding) itemBinding).seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                            if(fromUser){
                                // seek barın değişimine göre volume atanıyor
                                presenter.setVolume(data.getSoundId(),(float) i/seekBar.getMax());
                            }
                        }
                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {}
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {}
                    });



                    ((ActivitySongDetailItemBinding) itemBinding).favoriteButton.setOnClickListener(view -> {


                        /*
                         * favorilere eklenme veya kaldırılma durumu burda yakalanılıyor.
                         * */

                        if(!data.isFavorite()){
                            data.setFavorite(true);
                            ((ActivitySongDetailItemBinding) itemBinding).favoriteButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_filled));
                            presenter.addFavoriteItems(data);
                            presenter.removeUnFavoriteItems(data);
                        }
                        else{
                            data.setFavorite(false);
                            ((ActivitySongDetailItemBinding) itemBinding).favoriteButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_empty));
                            presenter.addUnFavoriteItems(data);
                            presenter.removeFavoriteItems(data);
                        }
                    });
                }));
        presenter.getItems(categoryId);
    }



    /*
     * result olarak
     * eklenen veya çıkarılan favorileri
     * diğer activitye iletir
     *
     * */
    public void finishActivity(){
        presenter.clearAllMediaPlayer();
        setResult(RESULT_OK,presenter.getResultIntent());
        finish();
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

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finishActivity();
                break;
        }
        return true;
    }





}
