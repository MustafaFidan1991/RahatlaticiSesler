package com.mustafafidan.rahatlaticisesler.injection.component;


import com.mustafafidan.rahatlaticisesler.base.BaseView;
import com.mustafafidan.rahatlaticisesler.injection.module.ContextModule;
import com.mustafafidan.rahatlaticisesler.injection.module.NetworkModule;
import com.mustafafidan.rahatlaticisesler.ui.detail.SongDetailPresenter;
import com.mustafafidan.rahatlaticisesler.ui.favorites.FavoritesPresenter;
import com.mustafafidan.rahatlaticisesler.ui.library.LibraryPresenter;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/*
 *modüller component arayüzünden
 *
 * dagger'a tanıtılıyor
 * */
@Singleton
@Component(modules = {ContextModule.class, NetworkModule.class})
public interface PresenterInjector {


    void inject(FavoritesPresenter favoritesPresenter);
    void inject(SongDetailPresenter songDetailPresenter);
    void inject(LibraryPresenter libraryPresenter);



    @Component.Builder
    interface Builder{
        PresenterInjector build();
        Builder networkModule(NetworkModule networkModule);
        Builder contextModule(ContextModule contextModule);

        @BindsInstance
        Builder baseView(BaseView baseView);
    }

}
