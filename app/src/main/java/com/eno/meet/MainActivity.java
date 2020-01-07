package com.eno.meet;

import android.view.View;
import android.widget.FrameLayout;

import com.eno.meet.base.BaseUIActivity;

import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseUIActivity {

    @BindView(R.id.main_layout)
    FrameLayout mMainLayout;
    @BindView(R.id.tv_main_star)
    AppCompatTextView mTvMainStar;
    @BindView(R.id.tv_main_square)
    AppCompatTextView mTvMainSquare;
    @BindView(R.id.tv_main_chat)
    AppCompatTextView mTvMainChat;
    @BindView(R.id.tv_main_mine)
    AppCompatTextView mTvMainMine;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_main_star, R.id.tv_main_square, R.id.tv_main_chat, R.id.tv_main_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_main_star:
                break;
            case R.id.tv_main_square:
                break;
            case R.id.tv_main_chat:
                break;
            case R.id.tv_main_mine:
                break;
        }
    }
}
