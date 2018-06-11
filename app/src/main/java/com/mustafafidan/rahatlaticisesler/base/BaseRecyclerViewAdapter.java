package com.mustafafidan.rahatlaticisesler.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by a on 19.12.2017.
 */

public class BaseRecyclerViewAdapter<T extends BaseModel> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder>{
    List<T> entityList;
    Context context;
    int viewId;
    int dataId;
    BasePresenter presenter;
    int presenterId;

    OnItemValidateListener<T> onItemValidateListener;


    public BaseRecyclerViewAdapter(List<T> entityList, Context context, int viewId, int dataId, BasePresenter presenter, int presenterId, OnItemValidateListener<T> onItemValidateListener) {
        this.entityList=entityList;
        this.context=context;
        this.viewId = viewId;
        this.dataId = dataId;
        this.presenter = presenter;
        this.presenterId = presenterId;
        this.onItemValidateListener = onItemValidateListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),viewId, parent, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bindto(entityList.get(position),position,dataId,presenterId,presenter,onItemValidateListener);
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }


    public List<T> getItems(){
        return entityList;
    }

    public void update(List<T>entityList) {
        this.entityList = entityList;
        notifyDataSetChanged();
    }


    public static class BaseViewHolder<T extends BaseModel> extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        BaseViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindto(T data,int position,int dataId,int presenterId,BasePresenter presenter,OnItemValidateListener<T> onItemValidateListener) {

            if(onItemValidateListener!=null) onItemValidateListener.onItemValidate(binding,data,position);
            if(data!=null) binding.setVariable(dataId,data);
            if(presenter!=null) binding.setVariable(presenterId,presenter);
            binding.executePendingBindings();

        }
    }

    public interface OnItemValidateListener<T extends BaseModel>{
        void onItemValidate(ViewDataBinding binding,T data,int position);
    }

}





