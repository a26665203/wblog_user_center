package com.wblog.user.rpo;

/**
 * 用于单点登录，用户登录状态上报
 */

public interface WblogUserStatusRpo {
    public void reportUserStatus(String account);
    public int getUserStatus(String account);
}
