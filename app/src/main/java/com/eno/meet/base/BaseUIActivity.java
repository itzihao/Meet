package com.eno.meet.base;

import android.content.Intent;

import com.eno.framework.base.BaseActivity;
import com.eno.framework.utils.SPUtils;
import com.eno.meet.common.AppConfig;
import com.eno.meet.ui.LoginActivity;

/**
 * Created by Hao on 2020-01-06.
 * Email:itzihao@sina.com
 * TODO
 */
public abstract class BaseUIActivity extends BaseActivity {

    @Override
    protected void toIntent(final Intent intent) {
        startActivity(intent);
    }

    @Override
    protected boolean isLogin() {
        return SPUtils.getBoolean(AppConfig.Sp.IS_LOGIN, false);
    }

    @Override
    protected void toLoginActivity() {
        startActivity(LoginActivity.class);
    }
}
