package com.mustafafidan.rahatlaticisesler.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class DataBinder {


    @BindingAdapter("app:image_url")
    public static void loadImage(ImageView v, String imgUrl){
        Glide.with(v.getContext()).load(imgUrl).into(v);
    }

}
