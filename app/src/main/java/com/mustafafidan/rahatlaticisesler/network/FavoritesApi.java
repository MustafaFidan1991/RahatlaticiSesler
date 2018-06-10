package com.mustafafidan.rahatlaticisesler.network;

import com.mustafafidan.rahatlaticisesler.base.BaseResponse;
import com.mustafafidan.rahatlaticisesler.model.Sound;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FavoritesApi {

    @GET("/rahatlaticisesler/favorites.html")
    Observable<BaseResponse<List<Sound>>> getFavorites();

}
