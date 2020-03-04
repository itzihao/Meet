package com.eno.meet.model;

/**
 * Created by Hao on 2020/3/4.
 * Email:itzihao@sina.com
 * 星球View的数据模型
 */
public class StarModel {

    //昵称
    private String nickName;
    //ID
    private String userId;
    //头像
    private String photoUrl;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(final String nickName) {
        this.nickName = nickName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(final String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
