package com.mustafafidan.rahatlaticisesler.model;

import com.mustafafidan.rahatlaticisesler.base.BaseModel;

public class Category extends BaseModel {

    int categoryId;

    String categoryName;

    String categoryImageUrl;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public void setCategoryImageUrl(String categoryImageUrl) {
        this.categoryImageUrl = categoryImageUrl;
    }
}
