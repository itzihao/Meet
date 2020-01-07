package com.eno.meet.fragment.chat;

import com.eno.meet.base.BaseUIFragment;

import androidx.fragment.app.Fragment;


public class AllFriendFragment extends BaseUIFragment {

    @Override
    public Fragment newInstance() {
        AllFriendFragment allFriendFragment = new AllFriendFragment();
        return allFriendFragment;
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
