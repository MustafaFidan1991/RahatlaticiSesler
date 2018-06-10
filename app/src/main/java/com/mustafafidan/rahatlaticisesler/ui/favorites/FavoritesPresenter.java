package com.mustafafidan.rahatlaticisesler.ui.favorites;

import com.mustafafidan.rahatlaticisesler.base.BasePresenter;
import com.mustafafidan.rahatlaticisesler.base.BaseResponse;
import com.mustafafidan.rahatlaticisesler.model.Sound;
import com.mustafafidan.rahatlaticisesler.network.FavoritesApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FavoritesPresenter extends BasePresenter<FavoritesView> {

    @Inject
    FavoritesApi favoritesApi;


    private Disposable subscription = null;

    public FavoritesPresenter(FavoritesView view) {
        super(view);
    }

    public void getFavouritesByUserId(int userId){
        view.showLoading();
        subscription = favoritesApi
                .getFavorites()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate(() -> {
                    view.hideLoading();
                }).subscribe(listBaseResponse -> {
                    // gelen data
                    view.updateFavorites(listBaseResponse.getValue());

                }, throwable -> {
                    /// hata alınan kısım
                });
    }


    @Override
    protected void onViewDestroyed() {
        super.onViewDestroyed();
        if(subscription!=null){
            subscription.dispose();
        }
    }
}
