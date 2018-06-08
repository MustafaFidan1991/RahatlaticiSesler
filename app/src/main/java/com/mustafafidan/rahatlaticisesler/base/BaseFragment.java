package com.mustafafidan.rahatlaticisesler.base;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

abstract public class BaseFragment<P extends BasePresenter<BaseView>,DB extends ViewDataBinding> extends Fragment implements BaseView {


    DB binding;

    P presenter;


    abstract int getLayoutRes();

    abstract P instantiatePresenter();


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
