package com.eno.framework.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.eno.framework.manager.AppManager;
import com.eno.framework.utils.SystemUI;
import com.eno.framework.utils.ToastUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * Created by Hao on 2020-01-06.
 * Email:itzihao@sina.com
 * 基类Activity
 */
public abstract class BaseActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();

    private View mView;
    public Activity mActivity;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUI.fixSystemUI(this);

        if (mView == null) {
            mView = LayoutInflater.from(this)
                    .inflate(bindLayoutId(), null);
        }
        setContentView(mView);

        mActivity = this;

        AppManager.getAppManager().addActivity(mActivity);

        ButterKnife.bind(this);

        initData();
    }

    protected abstract int bindLayoutId();

    protected abstract void initData();

    protected abstract void toIntent(final Intent intent);

    protected abstract boolean isLogin();

    protected abstract void toLoginActivity();

    protected void showToast(String strMsg) {
        ToastUtils.showToast(strMsg);
    }

    protected void showToast(int resId) {
        showToast(getString(resId));
    }


    public void startActivityFinishSelf(Class<?> clz) {
        startActivity(clz, true);
    }

    public void startActivity(Class<?> clz) {
        startActivity(clz, false);
    }


    public void startActivity(Class<?> clz, boolean isClose) {
        startActivity(clz, isClose, null);
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        startActivity(clz, false, bundle);
    }

    public void startActivity(Class<?> clz, boolean isClose, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);

        if (isClose) {
            backActivity();
        }
    }

    public void backActivity() {
        AppManager.getAppManager().finishActivity(mActivity);
    }


    public void startActivityNeedLogin(Class<?> clz) {
        startActivityNeedLogin(clz, null);
    }

    public void startActivityNeedLogin(Class<?> clz, Bundle bundle) {
        if (isLogin()) {
            startActivity(clz, false, bundle);
        } else {
            Intent intent = new Intent(this, clz);
            if (bundle != null)
                intent.putExtras(bundle);
            toIntent(intent);
            toLoginActivity();
        }
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
}
