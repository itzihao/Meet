package com.eno.meet.model;

/**
 * Created by Hao on 2020-01-10.
 * Email:itzihao@sina.com
 * TODO
 */
public class UserInfoModel {
    //背景颜色
    private int bgColor;
    //标题
    private String title;
    //内容
    private String content;

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(final int bgColor) {
        this.bgColor = bgColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }
}
