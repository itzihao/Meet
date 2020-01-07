package com.eno.meet;

import android.app.Application;

import com.eno.framework.utils.SPUtils;

/**
 * Created by Hao on 2020-01-06.
 * Email:itzihao@sina.com
 * TODO
 */
public class MeetApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    private void initData() {
        SPUtils.init(this);
    }
}
