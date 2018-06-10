package com.mustafafidan.rahatlaticisesler.ui.detail;

import android.content.Intent;
import android.os.Bundle;

import com.mustafafidan.rahatlaticisesler.base.BasePresenter;
import com.mustafafidan.rahatlaticisesler.model.Sound;
import com.mustafafidan.rahatlaticisesler.network.SongDetailApi;
import com.mustafafidan.rahatlaticisesler.utils.RxMediaPlayer;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SongDetailPresenter  extends BasePresenter<SongDetailView> {

    private ArrayList<RxMediaPlayer> mediaPlayers = new ArrayList<>();

    @Inject
    SongDetailApi songDetailApi;


    private Disposable subscription = null;


    ArrayList<Sound> favoriteItems = new ArrayList<Sound>();
    ArrayList<Sound> unFavoriteItems = new ArrayList<Sound>();



    public SongDetailPresenter(SongDetailView view) {
        super(view);
    }


    public void addFavoriteItems(Sound sound){
        favoriteItems.add(sound);
    }

    public void removeFavoriteItems(Sound sound){
        favoriteItems.remove(sound);
    }

    public void addUnFavoriteItems(Sound sound){
        unFavoriteItems.add(sound);
    }

    public void removeUnFavoriteItems(Sound sound){
        unFavoriteItems.remove(sound);
    }






    /**
    * servisten dinamik olarak
    * categoryId'sine göre datalar getiriliyor
    * @param categoryId getirilecek item'ların kategori id'si
    * */
    public void getItems(int categoryId){


        view.showLoading();
        subscription = songDetailApi
                .getSongDetail("/rahatlaticisesler/"+String.valueOf(categoryId)+".html")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate(() -> {
                    view.hideLoading();
                }).subscribe(listBaseResponse -> {
                    // gelen data
                    view.updateItems(listBaseResponse.getValue());

                }, throwable -> {
                    /// hata alınan kısım
                });

    }


    public void addMediaPlayer(RxMediaPlayer mediaPlayer){
        mediaPlayers.add(mediaPlayer);
    }

    public void clearMediaPlayer(RxMediaPlayer mediaPlayer){
        mediaPlayer.clear();
    }


    public void clearAllMediaPlayer(){
        for(RxMediaPlayer mediaPlayer:mediaPlayers){
            mediaPlayer.clear();
        }
    }




    /*
    * diğer activity'e gönderilcek intent hazırlanılıyor
    * */
    public Intent getResultIntent(){
        Intent intent = new Intent();

        Bundle bundle = new Bundle();
        bundle.putSerializable("favoriteItems", favoriteItems);
        bundle.putSerializable("unFavoriteItems", unFavoriteItems);
        intent.putExtras(bundle);


        return intent;
    }

    @Override
    protected void onViewDestroyed() {
        super.onViewDestroyed();
        subscription.dispose();
    }
}
