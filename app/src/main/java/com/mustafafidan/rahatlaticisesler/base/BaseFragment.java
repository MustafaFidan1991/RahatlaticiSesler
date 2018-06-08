package com.mustafafidan.rahatlaticisesler.base;

import android.support.v4.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

abstract public class BaseFragment<P extends BasePresenter,DB extends ViewDataBinding> extends Fragment implements BaseView {

    protected DB binding;

    protected P presenter;


    protected abstract int getLayoutRes();

    protected abstract P instantiatePresenter();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = instantiatePresenter();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,getLayoutRes(),container,false);
        return binding.getRoot();
    }
}
