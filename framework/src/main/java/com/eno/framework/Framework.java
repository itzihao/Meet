package com.eno.framework;

import android.content.Context;

import com.eno.framework.bmob.BmobManager;
import com.eno.framework.utils.ContextUtils;
import com.eno.framework.utils.LogUtils;
import com.eno.framework.utils.SPUtils;

import io.rong.imkit.RongIM;

/**
 * Created by Hao on 2020-01-07.
 * Email:itzihao@sina.com
 * Framework 入口
 */
public class Framework {
    private volatile static Framework mFramework;

    private Framework() {
    }

    public static Framework getFramework() {
        if (mFramework == null) {
            synchronized (Framework.class) {
                if (mFramework == null) {
                    mFramework = new Framework();
                }
            }
        }
        return mFramework;
    }

    public void initFramework(Context mContext) {
        SPUtils.init(mContext);
        ContextUtils.init(mContext);
        LogUtils.logInit();
        BmobManager.getInstance().initBmob(mContext);
        RongIM.init(mContext);
    }
}
