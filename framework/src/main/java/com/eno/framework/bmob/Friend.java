package com.eno.framework.bmob;

/**
 * Created by Hao on 2020-01-10.
 * Email:itzihao@sina.com
 * TODO
 */
public class Friend {
    //我自己
    private IMUser user;
    //好友
    private IMUser friendUser;

    public IMUser getUser() {
        return user;
    }

    public void setUser(IMUser user) {
        this.user = user;
    }

    public IMUser getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(IMUser friendUser) {
        this.friendUser = friendUser;
    }
}
