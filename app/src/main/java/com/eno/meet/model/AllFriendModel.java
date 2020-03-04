package com.eno.meet.model;

/**
 * Created by Hao on 2020/3/4.
 * Email:itzihao@sina.com
 * TODO
 */
public class AllFriendModel {
    private String userId;
    private String url;
    private boolean sex;
    private String nickName;
    private String desc;

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(final boolean sex) {
        this.sex = sex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(final String nickName) {
        this.nickName = nickName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(final String desc) {
        this.desc = desc;
    }
}
