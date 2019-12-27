package com.wblog.user.pojo;

import java.util.Date;

public class WblogUser {
    String userAccount;
    String userPassword;
    String nickName;
    String email;
    String phone;
    Integer userRole;
    Integer id;

    @Override
    public String toString() {
        return "WblogUser{" +
                "userAccount='" + userAccount + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", userRole=" + userRole +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
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

    Date createTime;
    Date modifyTime;

}
