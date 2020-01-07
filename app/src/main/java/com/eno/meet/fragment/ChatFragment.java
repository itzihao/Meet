package com.eno.meet.fragment;

import com.eno.meet.base.BaseUIFragment;
import com.eno.meet.fragment.chat.AllFriendFragment;

import androidx.fragment.app.Fragment;


public class ChatFragment extends BaseUIFragment {


    @Override
    public Fragment newInstance() {
        ChatFragment fragment = new ChatFragment();
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
