package com.mustafafidan.rahatlaticisesler.base;

import com.mustafafidan.rahatlaticisesler.injection.component.DaggerPresenterInjector;
import com.mustafafidan.rahatlaticisesler.injection.component.PresenterInjector;
import com.mustafafidan.rahatlaticisesler.injection.module.ContextModule;
import com.mustafafidan.rahatlaticisesler.injection.module.NetworkModule;
import com.mustafafidan.rahatlaticisesler.ui.favorites.FavoritesPresenter;

public abstract class BasePresenter<V extends BaseView> {

    protected V view;
    PresenterInjector presenterInjector;

    public BasePresenter(V view){
        this.view = view;


        presenterInjector = DaggerPresenterInjector
                .builder()
                .baseView(view)
                .contextModule(new ContextModule())
                .networkModule(new NetworkModule())
                .build();
        inject();
    }




    private void inject(){
        if(this instanceof FavoritesPresenter){
            presenterInjector.inject((FavoritesPresenter) this);
        }

    }



    void onViewCreated(){}


    void onViewDestroyed(){}



}
