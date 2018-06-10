package com.mustafafidan.rahatlaticisesler.base;

import com.mustafafidan.rahatlaticisesler.injection.component.DaggerPresenterInjector;
import com.mustafafidan.rahatlaticisesler.injection.component.PresenterInjector;
import com.mustafafidan.rahatlaticisesler.injection.module.ContextModule;
import com.mustafafidan.rahatlaticisesler.injection.module.NetworkModule;
import com.mustafafidan.rahatlaticisesler.ui.detail.SongDetailPresenter;
import com.mustafafidan.rahatlaticisesler.ui.favorites.FavoritesPresenter;
import com.mustafafidan.rahatlaticisesler.ui.library.LibraryPresenter;

public abstract class BasePresenter<V extends BaseView> {

    /*
    * Her presenter bir view tarafından yönetilir
    * Fragment veya Activity olabilir
    * */
    protected V view;

    /*
     * dependency injectionda tanımlanan herhangi bir modülü tüm presenterlara kolay şekilkde eklemeyi sağlar
     *
     * */
    PresenterInjector presenterInjector;

    public BasePresenter(V view){
        this.view = view;


        /*
         *burda ilgili modüller inject ediliyor, dagger çağrılıyor
         * tüm logic presenter üzerinde tutulmalı
         * servis call'ları veya location gibi durumlar
         *
         * yine presenterda düzenlenmeli
         *
         * */
        presenterInjector = DaggerPresenterInjector
                .builder()
                .baseView(view)
                .contextModule(new ContextModule())
                .networkModule(new NetworkModule())
                .build();
        inject();
    }





    /*
    * burda duruma göre istenilen presenter classına
    * farklı moduller eklenebilir
    * */
    private void inject(){
        if(this instanceof FavoritesPresenter){
            presenterInjector.inject((FavoritesPresenter) this);
        }


        if(this instanceof SongDetailPresenter){
            presenterInjector.inject((SongDetailPresenter) this);
        }


        if(this instanceof LibraryPresenter){
            presenterInjector.inject((LibraryPresenter) this);
        }

    }


    /*
     *fragment veya activity oluşturulurken çağrılır
     * */
    protected void onViewCreated(){}


    /*
     *fragment veya activity yok edilirken çağrılır
     * */
    protected void onViewDestroyed(){}



}
