package com.mustafafidan.rahatlaticisesler.network;

import com.mustafafidan.rahatlaticisesler.model.Sound;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FavoritesApi {

    @POST("/favorites")
    Observable<Sound> getFavorites(@Body int userId);

}
