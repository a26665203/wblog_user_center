package com.wblog.user.enums;

public enum WblogUserStatusEnum {
    ONLINE("用户在线",1),OFFLINE("用户离线",0);
    private String desc;
    private Integer status;

   private WblogUserStatusEnum(String desc, Integer status) {
        this.desc = desc;
        this.status = status;
    }
}
