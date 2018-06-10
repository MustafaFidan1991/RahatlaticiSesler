package com.mustafafidan.rahatlaticisesler.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

abstract public class BaseActivity<P extends BasePresenter,DB extends ViewDataBinding> extends AppCompatActivity implements BaseView {


    /*
    * generic data binding için tanımlanmıştır
    * */
    protected DB binding;



    /*
     * her activityde tanımlanacak presenter objesi
     * */
    protected P presenter;

    /*
     * ilgili layout çağrılmalı zorunludur
     * */
    protected abstract int getLayoutRes();


    /*
     * presenter oluşturulmalı zorunludur
     * */
    protected abstract P instantiatePresenter();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = instantiatePresenter();
        binding = DataBindingUtil.setContentView(this,getLayoutRes());
    }

}
