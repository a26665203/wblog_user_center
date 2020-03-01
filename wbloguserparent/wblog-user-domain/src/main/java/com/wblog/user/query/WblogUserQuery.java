package com.wblog.user.query;

public class WblogUserQuery {
    String userAccount;
    String nickName;
    String password;
    Integer pageNo;
    Integer pageSize;
    Integer startRow;

    public WblogUserQuery() {
    }

    public WblogUserQuery(String userAccount, String nickName, String password, Integer pageNo, Integer pageSize) {
        this.userAccount = userAccount;
        this.nickName = nickName;
        this.password = password;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        setStartRow();
    }

    public void setStartRow() {
        if(pageNo <= 0){
            this.startRow = 0;
        }else{
            this.startRow =(this.pageNo-1)*this.pageSize;
        }
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
