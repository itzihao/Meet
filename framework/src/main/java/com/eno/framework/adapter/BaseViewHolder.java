package com.eno.framework.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Hao on 2020-01-09.
 * Email:itzihao@sina.com
 * BaseViewHolder
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {


    private SparseArray<View> mViews;
    private View mContentView;

    public BaseViewHolder(@NonNull final View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        mContentView = itemView;
    }

    public static BaseViewHolder getViewHolder(ViewGroup parent, @LayoutRes int layout) {
        return new BaseViewHolder(View.inflate(parent.getContext(), layout, null));
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mContentView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     * @return
     */
    public BaseViewHolder setText(int viewId, String text) {
        AppCompatTextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置图片链接
     *
     * @param mContext
     * @param viewId
     * @param url
     * @return
     */
    public BaseViewHolder setImageUrl(Context mContext, int viewId, String url) {
        AppCompatImageView iv = getView(viewId);
        // TODO: 2020-01-09 加载图片资源
//        GlideHelper.loadUrl(mContext, url, iv);
        return this;
    }

    /**
     * 压缩
     *
     * @param mContext
     * @param viewId
     * @param url
     * @param w
     * @param h
     * @return
     */
    public BaseViewHolder setImageUrl(Context mContext, int viewId, String url, int w, int h) {
        AppCompatImageView iv = getView(viewId);
//        GlideHelper.loadSmollUrl(mContext, url, w, h, iv);
        return this;
    }


    /**
     * 设置图片文件
     *
     * @param mContext
     * @param viewId
     * @param file
     * @return
     */
    public BaseViewHolder setImageFile(Context mContext, int viewId, File file) {
        AppCompatImageView iv = getView(viewId);
//        GlideHelper.loadFile(mContext, file, iv);
        return this;
    }

    /**
     * 设置图片
     *
     * @param viewId
     * @param resId
     * @return
     */
    public BaseViewHolder setImageResource(int viewId, int resId) {
        AppCompatImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param viewId
     * @param color
     * @return
     */
    public BaseViewHolder setBackgroundColor(int viewId, int color) {
        AppCompatImageView iv = getView(viewId);
        iv.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置文本颜色
     *
     * @param viewId
     * @param color
     * @return
     */
    public BaseViewHolder setTextColor(int viewId, int color) {
        AppCompatTextView tv = getView(viewId);
        tv.setTextColor(color);
        return this;
    }


    /**
     * 设置控件的显示隐藏
     *
     * @param viewId
     * @param visibility
     * @return
     */
    public BaseViewHolder setVisibility(int viewId, int visibility) {
        AppCompatTextView tv = getView(viewId);
        tv.setVisibility(visibility);
        return this;
    }

}
