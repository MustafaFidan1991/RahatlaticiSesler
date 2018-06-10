package com.mustafafidan.rahatlaticisesler.injection.module;


import com.mustafafidan.rahatlaticisesler.network.FavoritesApi;
import com.mustafafidan.rahatlaticisesler.network.LibraryApi;
import com.mustafafidan.rahatlaticisesler.network.SongDetailApi;
import com.mustafafidan.rahatlaticisesler.utils.Constants;


import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {


    @Provides
    @Reusable
    FavoritesApi provideFavoritesApi(Retrofit retrofit) {
        return retrofit.create(FavoritesApi.class);
    }


    @Provides
    @Reusable
    LibraryApi provideLibraryApi(Retrofit retrofit) {
        return retrofit.create(LibraryApi.class);
    }


    @Provides
    @Reusable
    SongDetailApi provideSongDetailApi(Retrofit retrofit) {
        return retrofit.create(SongDetailApi.class);
    }

    @Provides
    @Reusable
    Retrofit provideRetrofitInterface() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

}
