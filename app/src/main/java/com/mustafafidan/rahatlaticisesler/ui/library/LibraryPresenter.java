package com.mustafafidan.rahatlaticisesler.ui.library;

import com.mustafafidan.rahatlaticisesler.base.BasePresenter;
import com.mustafafidan.rahatlaticisesler.model.Category;
import com.mustafafidan.rahatlaticisesler.model.Sound;

import java.util.ArrayList;
import java.util.List;

public class LibraryPresenter extends BasePresenter<LibraryView> {
    public LibraryPresenter(LibraryView view) {
        super(view);
    }


    public void getLibraryItems(){


        view.showLoading();


        List<Category> categoryList = new ArrayList<>();

        categoryList.add(new Category(){{
            setCategoryId(1);
            setCategoryName("Doğa");
        }});

        categoryList.add(new Category(){{
            setCategoryId(2);
            setCategoryName("Kuşlar");
        }});



        view.hideLoading();
        view.updateLibraryItems(categoryList);





    }
}
