package com.eno.meet.model;

import java.io.File;

/**
 * Created by Hao on 2020/3/4.
 * Email:itzihao@sina.com
 * 聊天的数据模型
 */
public class ChatModel {

    private int type;

    //文本
    private String text;

    //图片
    private String imgUrl;
    private File localFile;

    //位置
    private double la;
    private double lo;
    private String address;
    private String mapUrl;


    public int getType() {
        return type;
    }

    public void setType(final int type) {
        this.type = type;
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

    public File getLocalFile() {
        return localFile;
    }

    public void setLocalFile(final File localFile) {
        this.localFile = localFile;
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

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(final String mapUrl) {
        this.mapUrl = mapUrl;
    }
}
