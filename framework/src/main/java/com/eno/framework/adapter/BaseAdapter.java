package com.eno.framework.adapter;

import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Hao on 2020-01-09.
 * Email:itzihao@sina.com
 * BaseAdapter
 */
public class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private List<T> mLists;

    private OnBindDataListener<T> onBindDataListener;
    private OnMoreBindDataListener<T> onMoreBindDataListener;

    public BaseAdapter(List<T> mList, OnBindDataListener<T> onBindDataListener) {
        this.mLists = mList;
        this.onBindDataListener = onBindDataListener;
    }

    public BaseAdapter(List<T> mList, OnMoreBindDataListener<T> onMoreBindDataListener) {
        this.mLists = mList;
        this.onBindDataListener = onMoreBindDataListener;
        this.onMoreBindDataListener = onMoreBindDataListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        int layoutId = onBindDataListener.getLayoutId(viewType);
        return BaseViewHolder.getViewHolder(parent, layoutId);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, final int position) {
        onBindDataListener.onBindViewHolder(
                mLists.get(position), holder, getItemViewType(position), position);
    }


    @Override
    public int getItemViewType(int position) {
        if (onMoreBindDataListener != null) {
            return onMoreBindDataListener.getItemType(position);
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }


    //绑定数据
    public interface OnBindDataListener<T> {
        void onBindViewHolder(T model, BaseViewHolder viewHolder, int type, int position);

        int getLayoutId(int type);
    }

    //绑定多类型的数据
    public interface OnMoreBindDataListener<T> extends OnBindDataListener<T> {
        int getItemType(int position);
    }

}
