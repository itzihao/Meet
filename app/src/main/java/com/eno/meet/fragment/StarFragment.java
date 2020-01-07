package com.eno.meet.fragment;

import android.os.Bundle;

import com.eno.meet.base.BaseUIFragment;

import androidx.fragment.app.Fragment;


public class StarFragment extends BaseUIFragment {

    @Override
    public Fragment newInstance(final Bundle bundle) {
        StarFragment fragment = new StarFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int bindLayoutId() {
        return 0;
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
