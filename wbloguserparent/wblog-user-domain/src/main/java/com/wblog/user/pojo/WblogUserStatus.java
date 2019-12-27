package com.wblog.user.pojo;

import java.util.Date;

/**
 * 暂时设置，可能不用，因为记录用户状态可能要操作数据库很频繁
 */
public class WblogUserStatus {
    String nickName;
    Integer commentCount;
    Integer likeCount;
    Integer blogCount;
    Integer fansCount;
    Integer likedCount;
    Integer status;
    Date createTime;
    Date modifyTime;
    Integer id;

    @Override
    public String toString() {
        return "WblogUserStatus{" +
                "nickName='" + nickName + '\'' +
                ", commentCount=" + commentCount +
                ", likeCount=" + likeCount +
                ", blogCount=" + blogCount +
                ", fansCount=" + fansCount +
                ", likedCount=" + likedCount +
                ", status=" + status +
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

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Integer blogCount) {
        this.blogCount = blogCount;
    }

    public Integer getFansCount() {
        return fansCount;
    }

    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }

    public Integer getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(Integer likedCount) {
        this.likedCount = likedCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
