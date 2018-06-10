package com.mustafafidan.rahatlaticisesler.ui.favorites;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.mustafafidan.rahatlaticisesler.BR;
import com.mustafafidan.rahatlaticisesler.R;
import com.mustafafidan.rahatlaticisesler.base.BaseFragment;
import com.mustafafidan.rahatlaticisesler.base.BaseRecyclerViewAdapter;
import com.mustafafidan.rahatlaticisesler.databinding.FragmentFavoritesBinding;
import com.mustafafidan.rahatlaticisesler.databinding.FragmentFavoritesItemBinding;
import com.mustafafidan.rahatlaticisesler.model.Sound;
import com.mustafafidan.rahatlaticisesler.utils.RxMediaPlayer;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends BaseFragment<FavoritesPresenter,FragmentFavoritesBinding> implements FavoritesView {




    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    @LayoutRes
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_favorites;
    }

    @Override
    protected FavoritesPresenter instantiatePresenter() {
        return new FavoritesPresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        binding.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        binding.setAdapter(new BaseRecyclerViewAdapter<Sound>(new ArrayList<>(),getActivity().getBaseContext(),R.layout.fragment_favorites_item,BR.sound,presenter,BR.presenter,
                (itemBinding, data) -> {
                    if(itemBinding instanceof FragmentFavoritesItemBinding){



                        ((FragmentFavoritesItemBinding) itemBinding).playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_circle));


                        // media player her item için oluşturuluyor
                        RxMediaPlayer mediaPlayer = RxMediaPlayer.create(new RxMediaPlayer.MediaPlayerListener() {
                            @Override
                            public void onPrepareSuccess(long audioDuration) {}
                            @Override
                            public void onComplete(RxMediaPlayer mediaPlayer1) {
                                if(!mediaPlayer1.isPause()){
                                    mediaPlayer1.resume(0);
                                }
                            }
                        });


                        presenter.addMediaPlayer(mediaPlayer);



                        //play butonuna basılınca çalıştırılıyor
                        ((FragmentFavoritesItemBinding) itemBinding).playButton.setOnClickListener(view -> {
                            if(mediaPlayer.isFirstPlay()){ //ilk kere çalındığı zaman prepare edliyor
                                mediaPlayer.play(data.getSoundUrl());
                                ((FragmentFavoritesItemBinding) itemBinding).playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle));
                                mediaPlayer.setFirstPlay(false);
                            }
                            else{
                                if(mediaPlayer.isPrepareSuccess()){
                                    if(mediaPlayer.isPause()){ //durduma
                                        mediaPlayer.resume(mediaPlayer.getLastPosition());
                                        ((FragmentFavoritesItemBinding) itemBinding).playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle));
                                    }
                                    else{ // devam etme
                                        mediaPlayer.pause(()->{});
                                        mediaPlayer.setLastPosition(mediaPlayer.getLastPosition());//son kalınan pozisyon kaydediliyor
                                        ((FragmentFavoritesItemBinding) itemBinding).playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_circle));
                                    }
                                }

                            }
                        });


                        ((FragmentFavoritesItemBinding) itemBinding).seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                                if(fromUser){

                                    // seek barın değişimine göre volume atanıyor
                                    mediaPlayer.setVolume(seekBar.getMax(),i);
                                }
                            }
                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {}
                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {}
                        });


                        ((FragmentFavoritesItemBinding) itemBinding).favoriteButton.setOnClickListener(view -> {
                            if(data.isFavorite()){
                                presenter.removeFromList(binding.getAdapter().getItems(),data);
                            }
                        });

                    }

                }));

        presenter.getFavouritesByUserId(1);




        return binding.getRoot();
    }

    /*
     * eklenen çıkarılan favoriler burada pass ediliyor
     *
     * burdan arayüz güncellemesi için düzenleme için presentera veriliyor
     *
     *
     * */
    public void updateItems(List<Sound> favoriteItems,List<Sound> unFavoriteItems){

        presenter.updateItems(favoriteItems,unFavoriteItems,binding.getAdapter().getItems());
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
    public void updateFavorites(List<Sound> favorites) {
        presenter.clearAllMediaPlayer();
        binding.getAdapter().update(favorites);
        binding.notifyChange();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroyed();
    }





}
