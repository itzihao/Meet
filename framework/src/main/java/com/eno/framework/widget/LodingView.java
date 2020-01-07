package com.eno.framework.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;

import com.eno.framework.R;
import com.eno.framework.manager.DialogManager;
import com.eno.framework.utils.AnimUtils;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by Hao on 2020-01-07.
 * Email:itzihao@sina.com
 * 加载弹窗
 */
public class LodingView {

    private DialogView mLodingView;
    private AppCompatImageView img_loding;
    private AppCompatTextView tv_loding_text;
    private ObjectAnimator mAnim;

    public LodingView(Context mContext) {
        mLodingView = DialogManager.getInstance().initView(mContext, R.layout.dialog_loding);
        img_loding = mLodingView.findViewById(R.id.iv_loding);
        tv_loding_text = mLodingView.findViewById(R.id.tv_loding_text);
        mAnim = AnimUtils.rotation(img_loding);
    }

    /**
     * 设置加载的提示文本
     *
     * @param text
     */
    public void setLodingText(String text) {
        if (!TextUtils.isEmpty(text)) {
            tv_loding_text.setText(text);
        }
    }

    public void show() {
        mAnim.start();
        DialogManager.getInstance().show(mLodingView);
    }

    public void show(String text) {
        mAnim.start();
        setLodingText(text);
        DialogManager.getInstance().show(mLodingView);
    }

    public void hide() {
        mAnim.pause();
        DialogManager.getInstance().hide(mLodingView);
    }

    /**
     * 外部是否可以点击消失
     *
     * @param flag
     */
    public void setCancelable(boolean flag) {
        mLodingView.setCancelable(flag);
    }
}
