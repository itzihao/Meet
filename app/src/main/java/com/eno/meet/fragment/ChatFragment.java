package com.eno.meet.fragment;

import com.eno.meet.R;
import com.eno.meet.base.BaseUIFragment;

import androidx.fragment.app.Fragment;


public class ChatFragment extends BaseUIFragment {


    public static Fragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public int bindLayoutId() {
        return R.layout.fragment_chat;
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
