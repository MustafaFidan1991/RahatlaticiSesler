package com.mustafafidan.rahatlaticisesler.ui.library;

import com.mustafafidan.rahatlaticisesler.base.BaseView;
import com.mustafafidan.rahatlaticisesler.model.Category;
import com.mustafafidan.rahatlaticisesler.model.Sound;

import java.util.List;

public interface LibraryView
        extends BaseView {


    void showLoading();

    void hideLoading();

    void updateLibraryItems(List<Category> categoryList);
}
