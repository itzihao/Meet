package com.eno.framework.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.eno.framework.utils.LogUtils;

import java.util.Stack;

/**
 * Created by Hao on 2019-07-08.
 * Email:itzihao@sina.com
 * Activity 管理类
 */
public class AppManager {


    private static Stack<Activity> sActivityStack = new Stack<>();
    private volatile static AppManager sAppManager;

    private AppManager() {
    }

    public static AppManager getAppManager() {
        if (sAppManager == null) {
            synchronized (AppManager.class) {
                sAppManager = new AppManager();
            }
        }
        return sAppManager;
    }

    public void addActivity(Activity activity) {
        if (sActivityStack != null) {
            sActivityStack.add(activity);
            LogUtils.logE(" 当前添加Activity为：" + activity.getClass().getSimpleName() + "--> Activity size : " + sActivityStack.size());
        }
    }

    private int getAcitivitySize() {
        if (sActivityStack != null) {
            return sActivityStack.size();
        }
        return 0;
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity getCurrentActivity() {
        Activity activity = sActivityStack.lastElement();
        if (activity == null) {
            LogUtils.logE("Current activity Is null");
            activity = sActivityStack.get(0);
        }
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        LogUtils.logE("sActivityStack " + sActivityStack.size());
        try {
            if (sActivityStack.size() > 0) {
                Activity activity = sActivityStack.lastElement();
                LogUtils.logE("finishActivity Name :" + sActivityStack.get(sActivityStack.size() - 1).getClass().getSimpleName() + "    size :" + getAcitivitySize());
                sActivityStack.remove(sActivityStack.size() - 1);
                LogUtils.logE("finishActivity  after Size:" + getAcitivitySize());
                activity.finish();
            }
        } catch (Exception e) {
            LogUtils.logE("finishActivity  Exception " + e);
        }
    }


    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?>... cls) {
        boolean needRemoveCurExisting = false;
        int curExistingItemIdx = 0;
        try {
            if (cls.length > 0) {
                for (final Class<?> cl : cls) {
                    for (int i = 0, j = sActivityStack.size(); i < j; i++) {
                        if (sActivityStack.get(i).getClass().equals(cl)) {
                            needRemoveCurExisting = true;
                            curExistingItemIdx = i;
                            break;
                        }
                    }
                    if (needRemoveCurExisting) {
                        needRemoveCurExisting = false;
                        sActivityStack.get(curExistingItemIdx).finish();
                        sActivityStack.remove(curExistingItemIdx);
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.logE(" finishActivity Exception :" + e);
        }
    }


    public void finishAllActivity() {
        for (int i = 0, size = getAcitivitySize(); i < size; i++) {
            if (null != sActivityStack.get(i)) {
                sActivityStack.get(i).finish();
            }
        }
        sActivityStack.clear();
    }


    /**
     * 退出应用程序
     *
     * @param context
     * @param isBackGroud
     */
    @SuppressLint("MissingPermission")
    public void AppExit(Context context, boolean isBackGroud) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            if (activityMgr != null) {
                activityMgr.killBackgroundProcesses(context.getPackageName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!isBackGroud) {
                System.exit(0);
            }
        }
    }

}
