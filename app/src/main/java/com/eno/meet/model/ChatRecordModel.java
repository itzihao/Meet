package com.eno.meet.model;

/**
 * Created by Hao on 2020/3/4.
 * Email:itzihao@sina.com
 * 会话管理的数据模型
 */
public class ChatRecordModel {
    private String userId;
    private String url;
    private String nickName;
    private String endMsg;
    private String time;
    private int unReadSize;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(final String nickName) {
        this.nickName = nickName;
    }

    public String getEndMsg() {
        return endMsg;
    }

    public void setEndMsg(final String endMsg) {
        this.endMsg = endMsg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(final String time) {
        this.time = time;
    }

    public int getUnReadSize() {
        return unReadSize;
    }

    public void setUnReadSize(final int unReadSize) {
        this.unReadSize = unReadSize;
    }
}
