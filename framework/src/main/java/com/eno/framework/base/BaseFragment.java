package com.eno.framework.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eno.framework.event.EventManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hao on 2020-01-07.
 * Email:itzihao@sina.com
 * BaseFragment
 */
public abstract class BaseFragment extends Fragment {

    private View rootView;
    private Unbinder mUnbinder;
    private BaseActivity mActivity;

    private boolean isFragmentVisible;
    //是否是第一次开启网络加载
    public boolean isFirst;

    public abstract Fragment newInstance();

    public abstract Fragment newInstance(final Bundle bundle);

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(bindLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        mActivity = (BaseActivity) getActivity();
        EventManager.register(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            initView(bundle);
        } else {
            initView();
        }

        //可见，但是并没有加载过
        onFragmentVisibleChange(isFragmentVisible && !isFirst);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(final boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isFragmentVisible = true;
        }
        if (rootView == null) {
            return;
        }
        //可见，并且没有加载过
        if (!isFirst && isFragmentVisible) {
            onFragmentVisibleChange(true);
            return;
        }
        //由可见——>不可见 已经加载过
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    /**
     * 是否可见并且第一次加载数据
     *
     * @param isVisible
     */
    private void onFragmentVisibleChange(final boolean isVisible) {
        if (isVisible) {
            //可见，并且是第一次加载
            onFirstLoadData();
        } else {
            //取消加载
            onCancelLoad();
        }
    }


    public abstract int bindLayoutId();

    protected abstract void initView();

    protected abstract void initView(final Bundle bundle);

    protected abstract void onFirstLoadData();

    protected abstract void onCancelLoad();

    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        mActivity.startActivity(cls, bundle);
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        mActivity.startActivityForResult(cls, bundle, requestCode);
    }


    /**
     * 判断窗口权限
     *
     * @return
     */
    protected boolean checkWindowPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(getActivity());
        }
        return true;
    }

    /**
     * 请求窗口权限
     */
    protected void requestWindowPermissions() {
        showToast("申请窗口权限，暂时没做UI交互");
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION
                , Uri.parse("package:" + getActivity().getPackageName()));
        startActivityForResult(intent, BaseActivity.PERMISSION_WINDOW_REQUEST_CODE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        EventManager.unregister(this);
    }


    public void showToast(String msg) {
        mActivity.showToast(msg);
    }

    public void showToast(@StringRes int resId) {
        mActivity.showToast(resId);
    }
}
