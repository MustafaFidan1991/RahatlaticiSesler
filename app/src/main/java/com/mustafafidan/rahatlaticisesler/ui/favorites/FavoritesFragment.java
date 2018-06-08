package com.mustafafidan.rahatlaticisesler.ui.favorites;


import android.databinding.ViewDataBinding;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mustafafidan.rahatlaticisesler.BR;
import com.mustafafidan.rahatlaticisesler.R;
import com.mustafafidan.rahatlaticisesler.base.BaseFragment;
import com.mustafafidan.rahatlaticisesler.base.BaseRecyclerViewAdapter;
import com.mustafafidan.rahatlaticisesler.databinding.FragmentFavoritesBinding;
import com.mustafafidan.rahatlaticisesler.model.Sound;


import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FavoritesFragment extends BaseFragment<FavoritesPresenter,FragmentFavoritesBinding> implements FavoritesView {

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
                new BaseRecyclerViewAdapter.OnItemValidateListener<Sound>() {
            @Override
            public void onItemValidate(ViewDataBinding binding, Sound data) {

            }
        }));


        final MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("https://www.sample-videos.com/audio/mp3/wave.mp3");
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {

                }
            });
            mediaPlayer.start();


            Disposable subscription = Observable.timer(1000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        presenter.getFavouritesByUserId(1);




        return binding.getRoot();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void updateFavorites(List<Sound> favorites) {
        binding.getAdapter().update(favorites);
        binding.notifyChange();
    }
}
