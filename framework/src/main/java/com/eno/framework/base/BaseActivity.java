package com.eno.framework.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;

import com.eno.framework.manager.AppManager;
import com.eno.framework.utils.SystemUI;
import com.eno.framework.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
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

    //申请运行时权限的Code
    private static final int PERMISSION_REQUEST_CODE = 1000;
    //申请窗口权限的Code
    public static final int PERMISSION_WINDOW_REQUEST_CODE = 1001;

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
//        EventManager.register(this);
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

    protected void showToast(@StringRes int resId) {
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
        AppManager.getAppManager().finishActivity();
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


    //申明所需权限
    private String[] mStrPermission = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCESS_FINE_LOCATION

    };

    //保存没有同意的权限
    private List<String> mPerList = new ArrayList<>();
    //保存没有同意的失败权限
    private List<String> mPerNoList = new ArrayList<>();

    private OnPermissionsResult permissionsResult;

    /**
     * 一个方法请求权限
     *
     * @param permissionsResult
     */
    protected void request(OnPermissionsResult permissionsResult) {
        if (!checkPermissionsAll()) {
            requestPermissionAll(permissionsResult);
        }
    }

    /**
     * 判断单个权限
     *
     * @param permissions
     * @return
     */
    protected boolean checkPermissions(String permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int check = checkSelfPermission(permissions);
            return check == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    /**
     * 判断是否需要申请权限
     *
     * @return
     */
    protected boolean checkPermissionsAll() {
        mPerList.clear();
        for (final String s : mStrPermission) {
            boolean check = checkPermissions(s);
            //如果不同意则请求
            if (!check) {
                mPerList.add(s);
            }
        }
        return mPerList.size() <= 0;
    }

    /**
     * 请求权限
     *
     * @param mPermissions
     */
    protected void requestPermission(String[] mPermissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(mPermissions, PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * 申请所有权限
     *
     * @param permissionsResult
     */
    protected void requestPermissionAll(OnPermissionsResult permissionsResult) {
        this.permissionsResult = permissionsResult;
        requestPermission(mPerList.toArray(new String[mPerList.size()]));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mPerNoList.clear();
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        //你有失败的权限
                        mPerNoList.add(permissions[i]);
                    }
                }
                if (permissionsResult != null) {
                    if (mPerNoList.size() == 0) {
                        permissionsResult.OnSuccess();
                    } else {
                        permissionsResult.OnFail(mPerNoList);
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected interface OnPermissionsResult {
        void OnSuccess();

        void OnFail(List<String> noPermissions);
    }

    /**
     * 判断窗口权限
     *
     * @return
     */
    protected boolean checkWindowPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(this);
        }
        return true;
    }

    /**
     * 请求窗口权限
     */
    protected void requestWindowPermissions() {
        showToast("申请窗口权限，暂时没做UI交互");
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION
                , Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, PERMISSION_WINDOW_REQUEST_CODE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventManager.unregister(this);
    }
}
