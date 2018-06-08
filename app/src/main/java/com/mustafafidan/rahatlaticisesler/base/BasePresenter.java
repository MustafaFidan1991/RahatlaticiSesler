package com.mustafafidan.rahatlaticisesler.base;

import com.mustafafidan.rahatlaticisesler.injection.component.DaggerPresenterInjector;
import com.mustafafidan.rahatlaticisesler.injection.component.PresenterInjector;
import com.mustafafidan.rahatlaticisesler.injection.module.ContextModule;
import com.mustafafidan.rahatlaticisesler.injection.module.NetworkModule;

abstract class BasePresenter<V extends BaseView> {


    protected V view;

    public BasePresenter(V view){
        this.view = view;
        inject();
    }

    PresenterInjector presenterInjector = DaggerPresenterInjector
            .builder()
            .baseView(view)
            .contextModule(new ContextModule())
            .networkModule(new NetworkModule())
            .build();


    private void inject(){

    }

}
