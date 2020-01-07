package com.eno.framework.bmob;

import cn.bmob.v3.BmobUser;

/**
 * Created by Hao on 2020-01-07.
 * Email:itzihao@sina.com
 * TODO
 */
public class IMUser extends BmobUser {

    //获取Token的头像地址
    private String tokenPhoto;
    //获取Token的昵称
    private String tokenNickName;

    //基本属性

    //昵称
    private String nickName;
    //头像
    private String photo;

    //其他属性

    //性别
    private int sex = 0;
    //简介
    private String desc;
    //年龄
    private int age = 18;
    //生日
    private String birthday;
    //星座
    private String constellation;
    //爱好
    private String hobby;
    //单身状态
    private String status;

    public String getTokenPhoto() {
        return tokenPhoto;
    }

    public void setTokenPhoto(final String tokenPhoto) {
        this.tokenPhoto = tokenPhoto;
    }

    public String getTokenNickName() {
        return tokenNickName;
    }

    public void setTokenNickName(final String tokenNickName) {
        this.tokenNickName = tokenNickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(final String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(final String photo) {
        this.photo = photo;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(final int sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(final String desc) {
        this.desc = desc;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(final String birthday) {
        this.birthday = birthday;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(final String constellation) {
        this.constellation = constellation;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(final String hobby) {
        this.hobby = hobby;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }
}
