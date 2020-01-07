package com.eno.framework.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Hao on 2020-01-07.
 * Email:itzihao@sina.com
 * 动画工具类
 */
public class AnimUtils {
    /**
     * 旋转动画
     *
     * @param view
     * @return
     */
    public static ObjectAnimator rotation(View view) {
        return rotation(view, 2 * 1000);
    }

    public static ObjectAnimator rotation(View view, long duration) {
        ObjectAnimator mAnim = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        mAnim.setDuration(duration);
        mAnim.setRepeatMode(ValueAnimator.RESTART);
        mAnim.setRepeatCount(ValueAnimator.INFINITE);
        mAnim.setInterpolator(new LinearInterpolator());
        return mAnim;
    }
}
