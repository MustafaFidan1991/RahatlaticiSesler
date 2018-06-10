package com.mustafafidan.rahatlaticisesler.ui.detail;

import com.mustafafidan.rahatlaticisesler.base.BaseView;
import com.mustafafidan.rahatlaticisesler.model.Sound;

import java.util.List;

public interface SongDetailView extends BaseView {


    void showLoading();

    void hideLoading();

    void updateItems(List<Sound> favorites);
}
