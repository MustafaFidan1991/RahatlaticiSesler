package com.mustafafidan.rahatlaticisesler.ui.library;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mustafafidan.rahatlaticisesler.BR;
import com.mustafafidan.rahatlaticisesler.R;
import com.mustafafidan.rahatlaticisesler.base.BaseFragment;
import com.mustafafidan.rahatlaticisesler.base.BaseRecyclerViewAdapter;
import com.mustafafidan.rahatlaticisesler.databinding.FragmentLibraryBinding;
import com.mustafafidan.rahatlaticisesler.model.Category;
import com.mustafafidan.rahatlaticisesler.model.Sound;
import com.mustafafidan.rahatlaticisesler.ui.favorites.FavoritesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryFragment extends BaseFragment<LibraryPresenter,FragmentLibraryBinding> implements LibraryView {

    public static LibraryFragment newInstance() {
        LibraryFragment fragment = new LibraryFragment();
        return fragment;
    }
    public LibraryFragment() {

    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_library;
    }

    @Override
    protected LibraryPresenter instantiatePresenter() {
        return new LibraryPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater,container,savedInstanceState);
        binding.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        binding.setAdapter(new BaseRecyclerViewAdapter(new ArrayList<Category>(),getActivity().getBaseContext(),R.layout.fragment_library_item,BR.category,presenter,BR.presenter,null));



        return binding.getRoot();
    }

    @Override
    public void showLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.GONE);

    }

    @Override
    public void hideLoading() {
        binding.progressBar.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateLibraryItems(List<Category> categoryList) {
        binding.getAdapter().update(categoryList);
        binding.notifyChange();
    }
}
