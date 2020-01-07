package com.eno.meet.fragment;

import com.eno.meet.base.BaseUIFragment;

import androidx.fragment.app.Fragment;


public class MineFragment extends BaseUIFragment {

    @Override
    public Fragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public int bindLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onFirstLoadData() {

    }

    @Override
    protected void onCancelLoad() {

    }
}
