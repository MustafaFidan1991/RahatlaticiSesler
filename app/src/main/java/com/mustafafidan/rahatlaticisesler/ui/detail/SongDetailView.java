package com.mustafafidan.rahatlaticisesler.ui.detail;

import com.mustafafidan.rahatlaticisesler.base.BaseView;
import com.mustafafidan.rahatlaticisesler.model.Sound;

import java.util.List;

public interface SongDetailView extends BaseView {



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
     * @param items update edilcek list
     */
    void updateItems(List<Sound> items);
}
