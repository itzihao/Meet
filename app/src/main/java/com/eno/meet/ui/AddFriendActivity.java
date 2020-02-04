package com.eno.meet.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eno.framework.adapter.BaseAdapter;
import com.eno.framework.adapter.BaseViewHolder;
import com.eno.framework.bmob.BmobManager;
import com.eno.framework.bmob.IMUser;
import com.eno.framework.manager.KeyWordManager;
import com.eno.framework.utils.CommonUtils;
import com.eno.meet.R;
import com.eno.meet.base.BaseUIActivity;
import com.eno.meet.common.AppConfig;
import com.eno.meet.model.AddFriendModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 添加朋友
 */
public class AddFriendActivity extends BaseUIActivity {

    @BindView(R.id.et_phone)
    AppCompatEditText mEtPhone;
    @BindView(R.id.searchResultView)
    RecyclerView mSearchResultView;
    @BindView(R.id.include_empty_view)
    RelativeLayout emptyView;
    //标题
    public static final int TYPE_TITLE = 0;
    //内容
    public static final int TYPE_CONTENT = 1;

    private List<AddFriendModel> mLists;
    private BaseAdapter<AddFriendModel> mAdapter;
    private String phoneNumber;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected void initData() {
        mLists = new ArrayList<>();
        phoneNumber = getUser().getMobilePhoneNumber();
        mSearchResultView.setLayoutManager(new LinearLayoutManager(mActivity));
        mSearchResultView.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));

        mAdapter = new BaseAdapter<>(mLists, new BaseAdapter.OnMoreBindDataListener<AddFriendModel>() {
            @Override
            public int getItemType(final int position) {
                return mLists.get(position).getType();
            }

            @Override
            public void onBindViewHolder(final AddFriendModel model, final BaseViewHolder viewHolder, final int type, final int position) {
                if (type == TYPE_TITLE) {
                    viewHolder.setText(R.id.tv_title, model.getTitle());
                } else if (type == TYPE_CONTENT) {
                    //设置头像
                    viewHolder.setImageUrl(AddFriendActivity.this, R.id.iv_photo, model.getPhoto());
                    //设置性别
                    viewHolder.setImageResource(R.id.iv_sex,
                            model.getSex() == 1 ? R.drawable.img_boy_icon : R.drawable.img_girl_icon);
                    //设置昵称
                    viewHolder.setText(R.id.tv_nickname, model.getNickName());
                    //年龄
                    viewHolder.setText(R.id.tv_age, model.getAge() + getString(R.string.text_search_age));
                    //设置描述
                    viewHolder.setText(R.id.tv_desc, model.getDesc());

                    //点击事件
                    viewHolder.itemView.setOnClickListener(v -> {
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConfig.Bd.KEY_USER_ID, model.getUserId());
                        startActivity(UserInfoActivity.class, bundle);
                    });
                }
            }

            @Override
            public int getLayoutId(final int type) {
                if (type == TYPE_TITLE) {
                    return R.layout.layout_search_title_item;
                } else if (type == TYPE_CONTENT) {
                    return R.layout.layout_search_user_item;
                }
                return 0;
            }
        });

        mSearchResultView.setAdapter(mAdapter);
    }


    @OnClick(R.id.img_search)
    public void onViewClicked() {
        //1.获取电话号码
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, getString(R.string.text_login_phone_null),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //2.过滤自己
        if (phone.equals(phoneNumber)) {
            Toast.makeText(this, getString(R.string.text_add_friend_no_me), Toast.LENGTH_SHORT).show();
            return;
        }

        //3.查询
        BmobManager.getInstance().queryPhoneUser(phone, new FindListener<IMUser>() {
            @Override
            public void done(List<IMUser> list, BmobException e) {
                KeyWordManager.getInstance().hideKeyWord(AddFriendActivity.this);
                if (e != null) {
                    return;
                }
                if (CommonUtils.isEmpty(list)) {
                    IMUser imUser = list.get(0);
                    emptyView.setVisibility(View.GONE);
                    mSearchResultView.setVisibility(View.VISIBLE);

                    //每次你查询有数据的话则清空
                    mLists.clear();

                    addTitle(getString(R.string.text_add_friend_title));
                    addContent(imUser);
                    mAdapter.notifyDataSetChanged();

                    //推荐
                    pushUser(phone);
                } else {
                    //显示空数据
                    emptyView.setVisibility(View.VISIBLE);
                    mSearchResultView.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 推荐好友
     *
     * @param phone 过滤所查询的电话号码
     */
    private void pushUser(String phone) {
        //查询所有的好友 取100个
        BmobManager.getInstance().queryAllUser(new FindListener<IMUser>() {
            @Override
            public void done(List<IMUser> list, BmobException e) {
                if (e == null) {
                    if (CommonUtils.isEmpty(list)) {
                        addTitle(getString(R.string.text_add_friend_content));
                        int num = (list.size() <= 100) ? list.size() : 100;
                        for (int i = 0; i < num; i++) {
                            //也不能自己推荐给自己
                            if (list.get(i).getMobilePhoneNumber().equals(phoneNumber)) {
                                //跳过本次循环
                                continue;
                            }
                            //也不能查询到所查找的好友
                            if (list.get(i).getMobilePhoneNumber().equals(phone)) {
                                //跳过本次循环
                                continue;
                            }

                            addContent(list.get(i));
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    /**
     * 添加头部
     *
     * @param title
     */
    private void addTitle(String title) {
        AddFriendModel model = new AddFriendModel();
        model.setType(TYPE_TITLE);
        model.setTitle(title);
        mLists.add(model);
    }

    /**
     * 添加内容
     *
     * @param imUser
     */
    private void addContent(IMUser imUser) {
        AddFriendModel model = new AddFriendModel();
        model.setType(TYPE_CONTENT);
        model.setUserId(imUser.getObjectId());
        model.setPhoto(imUser.getPhoto());
        model.setSex(imUser.getSex());
        model.setAge(imUser.getAge());
        model.setNickName(imUser.getNickName());
        model.setDesc(imUser.getDesc());
        mLists.add(model);
    }

}
