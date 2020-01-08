package com.eno.meet.fragment;

import android.os.Bundle;

import com.eno.meet.R;
import com.eno.meet.base.BaseUIFragment;

import androidx.fragment.app.Fragment;


public class StarFragment extends BaseUIFragment {

    public static Fragment newInstance() {
        StarFragment fragment = new StarFragment();
        return fragment;
    }

    @Override
    public int bindLayoutId() {
        return R.layout.fragment_star;
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
