package com.mustafafidan.rahatlaticisesler.network;

import com.mustafafidan.rahatlaticisesler.base.BaseResponse;
import com.mustafafidan.rahatlaticisesler.model.Sound;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface SongDetailApi {

    @GET
    Observable<BaseResponse<List<Sound>>> getSongDetail(@Url String url);
}
