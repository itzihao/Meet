package com.eno.meet.model;

/**
 * Created by Hao on 2020-01-10.
 * Email:itzihao@sina.com
 * AddFriendModel
 */
public class AddFriendModel {
    //类型
    private int type;

    //标题
    private String title;

    //内容
    private String userId;
    private String photo;
    private int sex;
    private int age;
    private String nickName;
    private String desc;

    //联系人
    private boolean isContact = false;
    private String contactName;
    private String contactPhone;

    public int getType() {
        return type;
    }

    public void setType(final int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
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

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
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

    public boolean isContact() {
        return isContact;
    }

    public void setContact(final boolean contact) {
        isContact = contact;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(final String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(final String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
