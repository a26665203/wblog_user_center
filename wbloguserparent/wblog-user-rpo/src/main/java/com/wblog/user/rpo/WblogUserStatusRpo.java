package com.wblog.user.rpo;

/**
 * 用于单点登录，用户登录状态上报
 */

public interface WblogUserStatusRpo {
    public boolean reportUserStatus(String account,String status);
    public int getUserStatus(String account);
}
