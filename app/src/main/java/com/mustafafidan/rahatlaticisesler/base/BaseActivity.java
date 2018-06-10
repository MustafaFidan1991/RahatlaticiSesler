package com.mustafafidan.rahatlaticisesler.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

abstract public class BaseActivity<P extends BasePresenter,DB extends ViewDataBinding> extends AppCompatActivity implements BaseView {

    protected DB binding;

    protected P presenter;


    protected abstract int getLayoutRes();

    protected abstract P instantiatePresenter();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        presenter = instantiatePresenter();
        binding = DataBindingUtil.setContentView(this,getLayoutRes());

    }
}
