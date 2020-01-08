package com.eno.meet.fragment;

import com.eno.meet.R;
import com.eno.meet.base.BaseUIFragment;

import androidx.fragment.app.Fragment;


public class MineFragment extends BaseUIFragment {

    public static Fragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public int bindLayoutId() {
        return R.layout.fragment_mine;
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
