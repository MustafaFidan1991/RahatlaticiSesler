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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

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
        binding.setAdapter(new BaseRecyclerViewAdapter(new ArrayList<Sound>(),getActivity().getBaseContext(),R.layout.fragment_favorites_item,BR.sound,presenter,BR.presenter,
                (BaseRecyclerViewAdapter.OnItemValidateListener<Sound>) (itemBinding, data) -> {

                    if(itemBinding instanceof FragmentFavoritesItemBinding){

                        AtomicBoolean isFirstPlay = new AtomicBoolean(true);
                        AtomicBoolean isPrepareSuccess = new AtomicBoolean(false);
                        AtomicInteger currentPosition = new AtomicInteger(0);
                        AtomicBoolean isPause = new AtomicBoolean(false);

                        RxMediaPlayer mediaPlayer = RxMediaPlayer.create(new RxMediaPlayer.MediaPlayerListener() {
                            @Override
                            public void onPrepareSuccess(long audioDuration) {
                                isPrepareSuccess.set(true);
                            }
                        });

                        ((FragmentFavoritesItemBinding) itemBinding).playButton.setOnClickListener(view -> {
                            if(isFirstPlay.get()){
                                mediaPlayer.play(data.getSoundUrl());
                                ((FragmentFavoritesItemBinding) itemBinding).playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle));
                                isFirstPlay.set(false);
                            }
                            else{
                                if(isPrepareSuccess.get()){
                                    if(isPause.get()){
                                        mediaPlayer.resume(currentPosition.get());
                                        isPause.set(false);
                                        ((FragmentFavoritesItemBinding) itemBinding).playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle));
                                    }
                                    else{
                                        mediaPlayer.pause(()->{});
                                        currentPosition.set(mediaPlayer.getCurrentPosition());
                                        isPause.set(true);
                                        ((FragmentFavoritesItemBinding) itemBinding).playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_circle));
                                    }
                                }

                            }
                        });


                        ((FragmentFavoritesItemBinding) itemBinding).seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                                if(fromUser){
                                    mediaPlayer.setVolume(seekBar.getMax(),i);
                                }
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {}

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {}
                        });


                    }

                }));

        presenter.getFavouritesByUserId(1);




        return binding.getRoot();
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
        binding.getAdapter().update(favorites);
        binding.notifyChange();
    }
}
