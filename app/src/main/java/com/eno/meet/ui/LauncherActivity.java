package com.eno.meet.ui;

import android.os.Handler;

import com.eno.framework.utils.SPUtils;
import com.eno.meet.MainActivity;
import com.eno.meet.R;
import com.eno.meet.base.BaseUIActivity;
import com.eno.meet.common.AppConfig;

/**
 * 启动页
 * 1、设置全屏
 * 2、延迟进入主页
 * 3、根据具体逻辑进入：主页、引导页、登录页
 * 4、适配刘海屏
 */
public class LauncherActivity extends BaseUIActivity {

    public static final int SKIP_MAIN = 1000;

    private Handler mHandle = new Handler(msg -> {
        if (msg.what == SKIP_MAIN) {
            toNextActivity();
        }
        return false;
    });

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void initData() {
        mHandle.sendEmptyMessageDelayed(SKIP_MAIN, 2 * 1000);
    }

    private void toNextActivity() {
        boolean isFirstRun = SPUtils.getBoolean(AppConfig.Sp.IS_FIRST_RUN_APP, true);
        if (isFirstRun) {
            SPUtils.putBoolean(AppConfig.Sp.IS_FIRST_RUN_APP, false);
            startActivityFinishSelf(GuideActivity.class);
        } else {
            boolean isLogin = SPUtils.getBoolean(AppConfig.Sp.IS_LOGIN, false);
            if (isLogin) {
                startActivityFinishSelf(MainActivity.class);
            } else {
                toLoginActivity();
                backActivity();
            }
        }
    }
}
