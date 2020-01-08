package com.eno.framework.bmob;

import android.content.Context;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Hao on 2020-01-07.
 * Email:itzihao@sina.com
 * Bmob管理类
 */
public class BmobManager {

    private static final String BMOB_SDK_ID = "4ed91c155b7f26c50f6aad50538a0b52";
    private static final String BMOB_NEW_DOMAIN = "http://sdk.cilc.cloud/8/";

    private volatile static BmobManager mInstance = null;

    private BmobManager() {

    }

    public static BmobManager getInstance() {
        if (mInstance == null) {
            synchronized (BmobManager.class) {
                if (mInstance == null) {
                    mInstance = new BmobManager();
                }
            }
        }
        return mInstance;
    }

    public void initBmob(Context mContext) {
        //如果Bmob绑定独立域名，则需要在初始化之前重置
        Bmob.resetDomain(BMOB_NEW_DOMAIN);
        Bmob.initialize(mContext, BMOB_SDK_ID);
    }


    /**
     * 获取本地对象
     *
     * @return
     */
    public IMUser getUser() {
        return BmobUser.getCurrentUser(IMUser.class);
    }

    public boolean isLogin() {
        return BmobUser.isLogin();
    }

    /**
     * 发送短信验证码
     *
     * @param phone    手机号码
     * @param listener 回调
     */
    public void requestSMS(String phone, QueryListener<Integer> listener) {
        BmobSMS.requestSMSCode(phone, "", listener);
    }

    /**
     * 通过手机号码注册或者登陆
     *
     * @param phone    手机号码
     * @param code     短信验证码
     * @param listener 回调
     */
    public void signOrLoginByMobilePhone(String phone, String code, LogInListener<IMUser> listener) {
        BmobUser.signOrLoginByMobilePhone(phone, code, listener);
    }


    /**
     * 上传头像
     *
     * @param nickName
     * @param file
     * @param listener
     */
    public void uploadFirstPhoto(final String nickName, File file, final OnUploadPhotoListener listener) {
        /**
         * 1.上传文件拿到地址
         * 2.更新用户信息
         */
        final IMUser imUser = getUser();
        //独立域名 设置 - 应用配置 - 独立域名 一年/100
        //免费的办法： 使用我的Key
        //你如果怕数据冲突
        //解决办法：和我的类名不一样即可
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //上传成功
                    imUser.setNickName(nickName);
                    imUser.setPhoto(bmobFile.getFileUrl());

                    imUser.setTokenNickName(nickName);
                    imUser.setTokenPhoto(bmobFile.getFileUrl());

                    //更新用户信息
                    imUser.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                listener.OnUpdateDone();
                            } else {
                                listener.OnUpdateFail(e);
                            }
                        }
                    });
                } else {
                    listener.OnUpdateFail(e);
                }
            }
        });
    }

    public interface OnUploadPhotoListener {

        void OnUpdateDone();

        void OnUpdateFail(BmobException e);
    }
}
