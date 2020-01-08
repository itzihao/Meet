package com.eno.meet.ui;

import com.eno.meet.R;
import com.eno.meet.base.BaseUIActivity;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加朋友
 */
public class AddFeiendActivity extends BaseUIActivity {

    @BindView(R.id.et_phone)
    AppCompatEditText mEtPhone;
    @BindView(R.id.mSearchResultView)
    RecyclerView mMSearchResultView;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_add_feiend;
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.img_search)
    public void onViewClicked() {
    }
}
