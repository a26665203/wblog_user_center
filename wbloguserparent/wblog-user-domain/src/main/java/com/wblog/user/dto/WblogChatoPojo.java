package com.wblog.user.dto;

import java.io.Serializable;

public class WblogChatoPojo implements Serializable {
    String nickName;
    String userIcon;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }
}
