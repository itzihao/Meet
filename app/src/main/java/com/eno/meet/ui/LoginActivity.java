package com.eno.meet.ui;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.eno.framework.bmob.BmobManager;
import com.eno.framework.bmob.IMUser;
import com.eno.framework.manager.DialogManager;
import com.eno.framework.utils.SPUtils;
import com.eno.framework.widget.DialogView;
import com.eno.framework.widget.LodingView;
import com.eno.framework.widget.TouchPictureV;
import com.eno.meet.MainActivity;
import com.eno.meet.R;
import com.eno.meet.base.BaseUIActivity;
import com.eno.meet.common.AppConfig;

import java.util.Objects;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * 登录界面
 */
public class LoginActivity extends BaseUIActivity {


    @BindView(R.id.et_phone)
    AppCompatEditText mEtPhone;
    @BindView(R.id.et_code)
    AppCompatEditText mEtCode;
    @BindView(R.id.btn_send_code)
    AppCompatButton mBtnSendCode;
    @BindView(R.id.btn_login)
    AppCompatButton mBtnLogin;
    @BindString(R.string.text_login_phone_null)
    String phoneNull;
    @BindString(R.string.text_login_code_null)
    String codeNull;
    @BindString(R.string.text_user_resuest_succeed)
    String sendSucceed;
    @BindString(R.string.text_user_resuest_fail)
    String sendFail;
    @BindString(R.string.text_login_code_error)
    String codeError;
    @BindString(R.string.text_login_now_login_text)
    String loginText;

    private DialogView mCodeView;
    private TouchPictureV mPictureV;
    private LodingView mLodingView;

    private static final int H_TIME = 1001;
    //60s倒计时
    private static int TIME = 60;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == H_TIME) {
                TIME--;
                mBtnSendCode.setText(TIME + "s");
                if (TIME > 0) {
                    mHandler.sendEmptyMessageDelayed(H_TIME, 1000);
                } else {
                    mBtnSendCode.setEnabled(true);
                    mBtnSendCode.setText(getString(R.string.text_login_send));
                }
            }
            return false;
        }
    });


    @Override
    protected int bindLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        mCodeView = DialogManager.getInstance().initView(this, R.layout.dialog_code_view);
        mPictureV = mCodeView.findViewById(R.id.mPictureV);
        mPictureV.setViewResultListener(() -> {
            DialogManager.getInstance().hide(mCodeView);
            onSendSMS();
        });

        mLodingView = new LodingView(this);
    }


    @OnClick({R.id.btn_send_code, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send_code:
                DialogManager.getInstance().show(mCodeView);
                break;
            case R.id.btn_login:
                onLogin();
                break;
        }
    }

    private void onLogin() {
        final String phone = Objects.requireNonNull(mEtPhone.getText()).toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast(phoneNull);
            return;
        }

        String code = Objects.requireNonNull(mEtCode.getText()).toString().trim();
        if (TextUtils.isEmpty(code)) {
            showToast(codeNull);
            return;
        }

        mLodingView.show(loginText);
        BmobManager.getInstance().signOrLoginByMobilePhone(phone, code, new LogInListener<IMUser>() {
            @Override
            public void done(IMUser imUser, BmobException e) {
                mLodingView.hide();
                if (e == null) {
                    //登陆成功
                    startActivityFinishSelf(MainActivity.class);
                    //把手机号码保存下来
                    SPUtils.putString(AppConfig.Sp.PHONE, phone);
                } else {
                    if (e.getErrorCode() == 207) {
                        showToast(codeError);
                    } else {
                        showToast("ERROR:" + e.toString());
                    }
                }
            }
        });
    }

    private void onSendSMS() {
        String phone = Objects.requireNonNull(mEtPhone.getText()).toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast(phoneNull);
            return;
        }


        BmobManager.getInstance().requestSMS(phone, new QueryListener<Integer>() {
            @Override
            public void done(final Integer integer, final BmobException e) {
                if (e == null) {
                    mBtnSendCode.setEnabled(false);
                    mHandler.sendEmptyMessage(H_TIME);
                    showToast(sendSucceed);
                } else {
                    showToast(sendFail);
                }
            }
        });
    }
}
