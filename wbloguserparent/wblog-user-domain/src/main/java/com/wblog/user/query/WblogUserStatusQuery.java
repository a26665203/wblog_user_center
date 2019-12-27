package com.wblog.user.query;

public class WblogUserStatusQuery {
    String nickName;
    Integer pageNo;
    Integer pageSize;
    Integer startRow;

    public WblogUserStatusQuery(String nickName, Integer pageNo, Integer pageSize) {
        this.nickName = nickName;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
