package com.eno.meet;

import android.view.View;

import com.eno.framework.utils.LogUtils;
import com.eno.meet.base.BaseUIActivity;
import com.eno.meet.fragment.ChatFragment;
import com.eno.meet.fragment.MineFragment;
import com.eno.meet.fragment.SquareFragment;
import com.eno.meet.fragment.StarFragment;

import java.util.List;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseUIActivity {

    @BindView(R.id.tv_main_star)
    AppCompatTextView mTvMainStar;
    @BindView(R.id.tv_main_square)
    AppCompatTextView mTvMainSquare;
    @BindView(R.id.tv_main_chat)
    AppCompatTextView mTvMainChat;
    @BindView(R.id.tv_main_mine)
    AppCompatTextView mTvMainMine;

    private Fragment mStarFragment;
    private Fragment mSquareFragment;
    private Fragment mChatFragment;
    private Fragment mMineFragment;

    private FragmentTransaction mStarTransaction;
    private FragmentTransaction mSquareTransaction;
    private FragmentTransaction mChatTransaction;
    private FragmentTransaction mMineTransaction;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        requestPermiss();
        initFragment();
        checkMainTab(0);
    }

    /**
     * 请求权限
     */
    private void requestPermiss() {
        //危险权限
        request(new OnPermissionsResult() {
            @Override
            public void OnSuccess() {

            }

            @Override
            public void OnFail(List<String> noPermissions) {
                LogUtils.logE("noPermissions:" + noPermissions.toString());
            }
        });
    }

    private void initFragment() {
        if (mStarFragment == null) {
            mStarFragment = StarFragment.newInstance();
            mStarTransaction = getSupportFragmentManager().beginTransaction();
            mStarTransaction.add(R.id.main_layout, mStarFragment);
            mStarTransaction.commit();
        }
        if (mSquareFragment == null) {
            mSquareFragment = SquareFragment.newInstance();
            mSquareTransaction = getSupportFragmentManager().beginTransaction();
            mSquareTransaction.add(R.id.main_layout, mSquareFragment);
            mSquareTransaction.commit();
        }
        if (mChatFragment == null) {
            mChatFragment = ChatFragment.newInstance();
            mChatTransaction = getSupportFragmentManager().beginTransaction();
            mChatTransaction.add(R.id.main_layout, mChatFragment);
            mChatTransaction.commit();
        }
        if (mMineFragment == null) {
            mMineFragment = MineFragment.newInstance();
            mMineTransaction = getSupportFragmentManager().beginTransaction();
            mMineTransaction.add(R.id.main_layout, mMineFragment);
            mMineTransaction.commit();
        }
    }

    /**
     * 防止重叠
     * 当应用的内存紧张的时候，系统会回收掉Fragment对象
     * 再一次进入的时候会重新创建Fragment
     * 非原来对象，我们无法控制，导致重叠
     *
     * @param fragment
     */
    @Override
    public void onAttachFragment(Fragment fragment) {
        if (mStarFragment == null && fragment instanceof StarFragment) {
            mStarFragment = fragment;
        }
        if (mSquareFragment == null && fragment instanceof SquareFragment) {
            mSquareFragment = fragment;
        }
        if (mChatFragment == null && fragment instanceof ChatFragment) {
            mChatFragment = fragment;
        }
        if (mMineFragment == null && fragment instanceof MineFragment) {
            mMineFragment = fragment;
        }
    }

    @OnClick({R.id.tv_main_star, R.id.tv_main_square, R.id.tv_main_chat, R.id.tv_main_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_main_star:
                checkMainTab(0);
                break;
            case R.id.tv_main_square:
                checkMainTab(1);
                break;
            case R.id.tv_main_chat:
                checkMainTab(2);
                break;
            case R.id.tv_main_mine:
                checkMainTab(3);
                break;
        }
    }

    private void checkMainTab(final int index) {
        mTvMainStar.setSelected(index == 0);
        mTvMainSquare.setSelected(index == 1);
        mTvMainChat.setSelected(index == 2);
        mTvMainMine.setSelected(index == 3);
        switch (index) {
            case 0:
                showFragment(mStarFragment);
                break;
            case 1:
                showFragment(mSquareFragment);
                break;
            case 2:
                showFragment(mChatFragment);
                break;
            case 3:
                showFragment(mMineFragment);
                break;
        }
    }

    /**
     * 显示对应的Fragment
     *
     * @param fragment
     */
    private void showFragment(final Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            hideAllFragment(transaction);
            transaction.show(fragment);
            transaction.commitAllowingStateLoss();
        }
    }

    /**
     * 隐藏所有Fragment
     *
     * @param transaction
     */
    private void hideAllFragment(final FragmentTransaction transaction) {
        if (mStarFragment != null) {
            transaction.hide(mStarFragment);
        }
        if (mSquareFragment != null) {
            transaction.hide(mSquareFragment);
        }
        if (mChatFragment != null) {
            transaction.hide(mChatFragment);
        }
        if (mMineFragment != null) {
            transaction.hide(mMineFragment);
        }
    }

}
