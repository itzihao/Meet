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
            if (isLogin()) {
                startActivityFinishSelf(MainActivity.class);
            } else {
                toLoginActivity();
                backActivity();
            }
        }
    }

    /**
     * 优化
     * 冷启动经过的步骤：
     * 1.第一次安装，加载应用程序并且启动
     * 2.启动后显示一个空白的窗口 getWindow()
     * 3.启动/创建了我们的应用进程
     *
     * App内部：
     * 1.创建App对象/Application对象
     * 2.启动主线程(Main/UI Thread)
     * 3.创建应用入口/LAUNCHER
     * 4.填充ViewGroup中的View
     * 5.绘制View measure -> layout -> draw
     *
     * 优化手段：
     * 1.视图优化
     *   1.设置主题透明
     *   2.设置启动图片
     * 2.代码优化
     *   1.优化Application
     *   2.布局的优化，不需要繁琐的布局
     *   3.阻塞UI线程的操作
     *   4.加载Bitmap/大图
     *   5.其他的一个占用主线程的操作
     *
     *
     * 检测App Activity的启动时间
     * 1.Shell
     *   ActivityManager -> adb shell am start -S -W com.imooc.meet/com.imooc.meet.ui.IndexActivity
     *   ThisTime: 478ms 最后一个Activity的启动耗时
     *   TotalTime: 478ms 启动一连串Activity的总耗时
     *   WaitTime: 501ms 应用创建的时间 + TotalTime
     *   应用创建时间： WaitTime - TotalTime（501 - 478 = 23ms）
     * 2.Log
     *   Android 4.4 开始，ActivityManager增加了Log TAG = displayed
     */

}
