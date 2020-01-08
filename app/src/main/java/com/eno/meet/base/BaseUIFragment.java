package com.eno.meet.base;

import android.os.Bundle;

import com.eno.framework.base.BaseFragment;

/**
 * Created by Hao on 2020-01-07.
 * Email:itzihao@sina.com
 * TODO
 */
public abstract class BaseUIFragment extends BaseFragment {

    @Override
    public int bindLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initView(final Bundle bundle) {

    }

    @Override
    protected void onFirstLoadData() {

    }

    @Override
    protected void onCancelLoad() {

    }
}
