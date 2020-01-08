package com.eno.meet.fragment;

import com.eno.meet.R;
import com.eno.meet.base.BaseUIFragment;

import androidx.fragment.app.Fragment;


public class SquareFragment extends BaseUIFragment {

    public static Fragment newInstance() {
        SquareFragment fragment = new SquareFragment();
        return fragment;
    }

    @Override
    public int bindLayoutId() {
        return R.layout.fragment_square;
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
