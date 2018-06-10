package com.mustafafidan.rahatlaticisesler.ui.library;

import com.mustafafidan.rahatlaticisesler.base.BaseView;
import com.mustafafidan.rahatlaticisesler.model.Category;
import com.mustafafidan.rahatlaticisesler.model.Sound;

import java.util.List;

public interface LibraryView
        extends BaseView {


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
     * @param categoryList update edilcek list
     */
    void updateLibraryItems(List<Category> categoryList);
}
