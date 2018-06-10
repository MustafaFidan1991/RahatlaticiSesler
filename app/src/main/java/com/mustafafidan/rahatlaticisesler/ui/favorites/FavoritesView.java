package com.mustafafidan.rahatlaticisesler.ui.favorites;

import com.mustafafidan.rahatlaticisesler.base.BaseView;
import com.mustafafidan.rahatlaticisesler.model.Sound;

import java.util.List;

public interface FavoritesView extends BaseView {


    /*
     * arayüz tarafında progress gösterilceğinde çağrılır
     * presenter tarafından
     * */
    void showLoading();


    /*
     * arayüz tarafında progress kaldırılacağı zaman çağrılır
     * presenter tarafından
     * */
    void hideLoading();


    /**
     * arayüz tarafında recyclerview güncellenirken çağrılır
     * @param favorites update edilcek list
     */
    void updateFavorites(List<Sound>favorites);
}
