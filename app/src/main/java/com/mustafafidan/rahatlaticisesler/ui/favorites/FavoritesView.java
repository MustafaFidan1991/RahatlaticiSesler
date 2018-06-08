package com.mustafafidan.rahatlaticisesler.ui.favorites;

import com.mustafafidan.rahatlaticisesler.base.BaseView;
import com.mustafafidan.rahatlaticisesler.model.Sound;

import java.util.List;

public interface FavoritesView extends BaseView {



    void showLoading();

    void hideLoading();

    void updateFavorites(List<Sound>favorites);
}
