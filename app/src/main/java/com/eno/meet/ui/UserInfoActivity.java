package com.eno.meet.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eno.framework.adapter.BaseAdapter;
import com.eno.framework.adapter.BaseViewHolder;
import com.eno.framework.bmob.BmobManager;
import com.eno.framework.bmob.Friend;
import com.eno.framework.bmob.IMUser;
import com.eno.framework.manager.DialogManager;
import com.eno.framework.utils.CommonUtils;
import com.eno.framework.widget.DialogView;
import com.eno.meet.R;
import com.eno.meet.base.BaseUIActivity;
import com.eno.meet.common.AppConfig;
import com.eno.meet.model.UserInfoModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 用户信息
 * 1.根据传递过来的ID 查询用户信息 并且显示
 * - 普通的信息
 * - 构建一个RecyclerView 宫格
 * 2.建立好友关系模型
 * 与我有关系的是好友，
 * 1.在我的好友列表中
 * 2.同意了我的好友申请 BmobObject 建表
 * 3.查询所有的Friend表，其中user对应自己的列都是我的好友
 * 3.实现添加好友的提示框
 * 4.发送添加好友的消息
 * 1.自定义消息类型
 * 2.自定义协议
 * 发送文本消息 Content, 我们对文本进行处理：增加Json 定义一个标记来显示了
 * 点击提示框的发送按钮去发送
 * 5.接收好友的消息
 */
public class UserInfoActivity extends BaseUIActivity implements View.OnClickListener {

    @BindView(R.id.ll_back)
    RelativeLayout mLlBack;
    @BindView(R.id.iv_user_photo)
    CircleImageView mIvUserPhoto;
    @BindView(R.id.tv_nickname)
    AppCompatTextView mTvNickname;
    @BindView(R.id.tv_desc)
    AppCompatTextView mTvDesc;
    @BindView(R.id.userInfoView)
    RecyclerView mUserInfoView;
    @BindView(R.id.btn_add_friend)
    AppCompatButton mBtnAddFriend;
    @BindView(R.id.btn_chat)
    AppCompatButton mBtnChat;
    @BindView(R.id.btn_audio_chat)
    AppCompatButton mBtnAudioChat;
    @BindView(R.id.btn_video_chat)
    AppCompatButton mBtnVideoChat;
    @BindView(R.id.ll_is_friend)
    LinearLayout mLlIsFriend;

    private DialogView mAddFriendDialogView;
    private AppCompatEditText et_msg;
    private AppCompatTextView tv_cancel;
    private AppCompatTextView tv_add_friend;
    private List<UserInfoModel> mUserInfos;
    private BaseAdapter<UserInfoModel> mAdapter;
    private String uid;
    private IMUser imUser;

    //个人信息颜色
    private int[] mColor = {0x881E90FF, 0x8800FF7F, 0x88FFD700, 0x88FF6347, 0x88F08080, 0x8840E0D0};


    @Override
    protected int bindLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initData() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            uid = bundle.getString(AppConfig.Bd.KEY_USER_ID);
        }
    }

    /**
     * 添加好友的提示框
     */
    private void initAddFriendDialog() {
        mUserInfos = new ArrayList<>();
        mAddFriendDialogView = DialogManager.getInstance().initView(this, R.layout.dialog_send_friend);

        et_msg = mAddFriendDialogView.findViewById(R.id.et_msg);
        tv_cancel = mAddFriendDialogView.findViewById(R.id.tv_cancel);
        tv_add_friend = mAddFriendDialogView.findViewById(R.id.tv_add_friend);

        et_msg.setText(getString(R.string.text_me_info_tips) + getUser().getNickName());

        tv_cancel.setOnClickListener(this);
        tv_add_friend.setOnClickListener(this);

        mAdapter = new BaseAdapter<>(mUserInfos, new BaseAdapter.OnBindDataListener<UserInfoModel>() {
            @Override
            public void onBindViewHolder(final UserInfoModel model, final BaseViewHolder viewHolder, final int type, final int position) {
                viewHolder.getView(R.id.ll_bg).setBackgroundColor(model.getBgColor());
                viewHolder.setText(R.id.tv_type, model.getTitle());
                viewHolder.setText(R.id.tv_content, model.getContent());
            }

            @Override
            public int getLayoutId(final int type) {
                return R.layout.layout_user_info_item;
            }
        });

        mUserInfoView.setLayoutManager(new GridLayoutManager(mActivity, 3));
        mUserInfoView.setAdapter(mAdapter);

        queryUserInfo();
    }

    private void queryUserInfo() {
        if (TextUtils.isEmpty(uid)) {
            return;
        }
        //查询用户信息
        BmobManager.getInstance().queryObjectIdUser(uid, new FindListener<IMUser>() {
            @Override
            public void done(List<IMUser> list, BmobException e) {
                if (e == null) {
                    if (CommonUtils.isEmpty(list)) {
                        imUser = list.get(0);
                        updateUserInfo(imUser);
                    }
                }
            }
        });
        //判断好友关系
        BmobManager.getInstance().queryMyFriends(new FindListener<Friend>() {
            @Override
            public void done(List<Friend> list, BmobException e) {
                if (e == null) {
                    if (CommonUtils.isEmpty(list)) {
                        //你有一个好友列表
                        for (int i = 0; i < list.size(); i++) {
                            Friend friend = list.get(i);
                            //判断这个对象中的id是否跟我目前的userId相同
                            if (friend.getFriendUser().getObjectId().equals(uid)) {
                                //你们是好友关系
                                mBtnAddFriend.setVisibility(View.GONE);
                                mLlIsFriend.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * 更新用户信息
     *
     * @param imUser
     */
    private void updateUserInfo(final IMUser imUser) {
        //设置基本属性
//        GlideHelper.loadUrl(UserInfoActivity.this, imUser.getPhoto(),
//                mIvUserPhoto);
        mTvNickname.setText(imUser.getNickName());
        mTvDesc.setText(imUser.getDesc());

        //性别 年龄 生日 星座 爱好 单身状态
        addUserInfoModel(mColor[0], getString(R.string.text_me_info_sex), imUser.getSex() == 0 ? getString(R.string.text_me_info_boy) : getString(R.string.text_me_info_girl));
        addUserInfoModel(mColor[1], getString(R.string.text_me_info_age), imUser.getAge() + getString(R.string.text_search_age));
        addUserInfoModel(mColor[2], getString(R.string.text_me_info_birthday), imUser.getBirthday());
        addUserInfoModel(mColor[3], getString(R.string.text_me_info_constellation), imUser.getConstellation());
        addUserInfoModel(mColor[4], getString(R.string.text_me_info_hobby), imUser.getHobby());
        addUserInfoModel(mColor[5], getString(R.string.text_me_info_status), imUser.getStatus());
        //刷新数据
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param color
     * @param title
     * @param content
     */
    private void addUserInfoModel(int color, String title, String content) {
        UserInfoModel model = new UserInfoModel();
        model.setBgColor(color);
        model.setTitle(title);
        model.setContent(content);
        mUserInfos.add(model);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.tv_add_friend:
                String msg = et_msg.getText().toString().trim();
                if (TextUtils.isEmpty(msg)) {
                    msg = getString(R.string.text_user_info_add_friend);
                    return;
                }
//                CloudManager.getInstance().sendTextMessage(msg,
//                        CloudManager.TYPE_ADD_FRIEND, userId);
                DialogManager.getInstance().hide(mAddFriendDialogView);
                Toast.makeText(this, getString(R.string.text_user_resuest_succeed), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_cancel:
                DialogManager.getInstance().hide(mAddFriendDialogView);
                break;
        }
    }

    @OnClick({R.id.ll_back, R.id.iv_user_photo, R.id.btn_add_friend,
            R.id.btn_chat, R.id.btn_audio_chat, R.id.btn_video_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                backActivity();
                break;
            case R.id.iv_user_photo:
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConfig.Bd.INTENT_IMAGE_TYPE, true);
                bundle.getString(AppConfig.Bd.INTENT_IMAGE_URL, imUser.getPhoto());
                startActivity(ImagePreviewActivity.class, bundle);
                break;
            case R.id.btn_add_friend:
                DialogManager.getInstance().show(mAddFriendDialogView);
                break;
            case R.id.btn_chat:
                Bundle bundle1 = new Bundle();
                bundle1.putString(AppConfig.Bd.KEY_USER_ID, uid);
                bundle1.putString(AppConfig.Bd.KEY_USER_NICKNAME, imUser.getNickName());
                bundle1.putString(AppConfig.Bd.KEY_USER_PHOTO, imUser.getPhoto());
                startActivity(ChatActivity.class, bundle1);
                break;
            case R.id.btn_audio_chat:
                //窗口权限
                if (!checkWindowPermissions()) {
                    requestWindowPermissions();
                } else {
//                    CloudManager.getInstance().startAudioCall(this, userId);
                }
                break;
            case R.id.btn_video_chat:
                if (!checkWindowPermissions()) {
                    requestWindowPermissions();
                } else {
//                    CloudManager.getInstance().startVideoCall(this, userId);
                }
                break;
        }
    }
}
