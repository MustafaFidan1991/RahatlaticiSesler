package com.mustafafidan.rahatlaticisesler.ui.library;

import com.mustafafidan.rahatlaticisesler.base.BasePresenter;
import com.mustafafidan.rahatlaticisesler.network.LibraryApi;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LibraryPresenter extends BasePresenter<LibraryView> {

    @Inject
    LibraryApi libraryApi;


    private Disposable subscription = null;

    public LibraryPresenter(LibraryView view) {
        super(view);
    }


    /**
     * servisten dinamik olarak kategoriler getiriliyor
     * */
    public void getLibraryItems(){


        view.showLoading();
        subscription = libraryApi
                .getCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate(() -> {
                    view.hideLoading();
                }).subscribe(listBaseResponse -> {

                    // gelen data
                    view.updateLibraryItems(listBaseResponse.getValue());

                }, throwable -> {
                    /// hata alınan kısım
                });





    }
}
