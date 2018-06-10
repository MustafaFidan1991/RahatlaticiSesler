package com.mustafafidan.rahatlaticisesler.network;

import com.mustafafidan.rahatlaticisesler.base.BaseResponse;
import com.mustafafidan.rahatlaticisesler.model.Category;
import com.mustafafidan.rahatlaticisesler.model.Sound;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface LibraryApi {


    @GET("/rahatlaticisesler/category.html")
    Observable<BaseResponse<List<Category>>> getCategories();
}
