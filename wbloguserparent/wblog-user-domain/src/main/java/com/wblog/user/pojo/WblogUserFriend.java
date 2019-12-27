package com.wblog.user.pojo;

import java.util.Date;

public class WblogUserFriend {
    String nickName;
    String friendNickName;
    Date createTime;
    Date modifyTime;
    Integer id;

    @Override
    public String toString() {
        return "WblogUserFriend{" +
                "nickName='" + nickName + '\'' +
                ", friendNickName='" + friendNickName + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", id=" + id +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFriendNickName() {
        return friendNickName;
    }

    public void setFriendNickName(String friendNickName) {
        this.friendNickName = friendNickName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
