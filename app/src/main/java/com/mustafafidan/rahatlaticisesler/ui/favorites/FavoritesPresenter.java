package com.mustafafidan.rahatlaticisesler.ui.favorites;

import com.mustafafidan.rahatlaticisesler.base.BasePresenter;
import com.mustafafidan.rahatlaticisesler.model.Sound;
import com.mustafafidan.rahatlaticisesler.network.FavoritesApi;
import com.mustafafidan.rahatlaticisesler.utils.RxMediaPlayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FavoritesPresenter extends BasePresenter<FavoritesView> {


    private ArrayList<RxMediaPlayer> mediaPlayers = new ArrayList<>();


    @Inject
    FavoritesApi favoritesApi;


    private Disposable subscription = null;



    public FavoritesPresenter(FavoritesView view) {
        super(view);
    }

    /**
     * servisten dinamik olarak datalar getiriliyor
     * @param userId kullanıcı id
     * */
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


    public void removeFromList(List<Sound> sounds,Sound item){

        sounds.remove(item);

        view.updateFavorites(sounds);

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







    /**
     *
     * @param favoriteItems eklenecek favoriler
     * @param unFavoriteItems çıkarılacak favoriler
     * @param latestItems mevcut list
     *
     * */
    public void updateItems(List<Sound> favoriteItems, List<Sound> unFavoriteItems,List<Sound> latestItems){

        if(favoriteItems.size()>0){
            List<Sound> addedItems = new ArrayList<>();
            for (Sound favoriteItem: favoriteItems) {
                boolean isContain = false;
                for (Sound item : latestItems) {
                    if(item.getSoundId()==favoriteItem.getSoundId()){
                        isContain=true;
                    }
                }
                if(!isContain){
                    addedItems.add(favoriteItem);
                }
            }

            for(Sound addedItem : addedItems){
                latestItems.add(addedItem);
            }
        }



        if(unFavoriteItems.size()>0){
            List<Sound> deletedItems = new ArrayList<>();
            for (Sound favoriteItem: unFavoriteItems) {
                boolean isContain = false;
                for (Sound item : latestItems) {
                    if(item.getSoundId()==favoriteItem.getSoundId()){
                        isContain=true;
                    }
                }
                if(isContain){
                    deletedItems.add(favoriteItem);
                }
            }

            for(Sound deletedItem : deletedItems){


                for (Iterator<Sound> iterator = latestItems.iterator(); iterator.hasNext(); ) {
                    Sound sound = iterator.next();
                    if (sound.getSoundId() == deletedItem.getSoundId()) {
                        iterator.remove();
                    }
                }

            }
        }



        view.updateFavorites(latestItems);


    }
}
