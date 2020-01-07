package com.eno.framework.event;

import android.view.SurfaceView;

/**
 * Created by Hao on 2020-01-07.
 * Email:itzihao@sina.com
 * TODO
 */
public class MessageEvent {
    private int type;

    //文本消息
    private String userId;  //每个消息都应该持有userId
    private String text;

    //图片消息
    private String imgUrl;

    //位置消息
    private double la;
    private double lo;
    private String address;

    //相机
    private SurfaceView mSurfaceView;

    //服务器连接状态
    private boolean connectStatus;


    public MessageEvent(int type) {
        this.type = type;
    }


    public int getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(final String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getLa() {
        return la;
    }

    public void setLa(final double la) {
        this.la = la;
    }

    public double getLo() {
        return lo;
    }

    public void setLo(final double lo) {
        this.lo = lo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public SurfaceView getSurfaceView() {
        return mSurfaceView;
    }

    public void setSurfaceView(final SurfaceView surfaceView) {
        mSurfaceView = surfaceView;
    }

    public boolean isConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(final boolean connectStatus) {
        this.connectStatus = connectStatus;
    }
}
