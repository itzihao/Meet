package com.eno.meet.fragment;

import com.eno.meet.base.BaseUIFragment;

import androidx.fragment.app.Fragment;


public class SquareFragment extends BaseUIFragment {

    @Override
    public Fragment newInstance() {
        SquareFragment fragment = new SquareFragment();
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
