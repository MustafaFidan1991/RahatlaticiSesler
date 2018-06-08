package com.mustafafidan.rahatlaticisesler.injection.component;


import com.mustafafidan.rahatlaticisesler.base.BaseView;
import com.mustafafidan.rahatlaticisesler.injection.module.ContextModule;
import com.mustafafidan.rahatlaticisesler.injection.module.NetworkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, NetworkModule.class})
public interface PresenterInjector {


    @Component.Builder
    interface Builder{
        PresenterInjector build();
        Builder networkModule(NetworkModule networkModule);
        Builder contextModule(ContextModule contextModule);

        @BindsInstance
        Builder baseView(BaseView baseView);
    }

}
