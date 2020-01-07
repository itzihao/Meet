package com.eno.framework.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

/**
 * Created by Hao on 2020-01-06.
 * Email:itzihao@sina.com
 * SystemUI
 */
public class SystemUI {
    public static void fixSystemUI(Activity activity) {
        /**
         * 沉侵式状态栏
         */
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
}
