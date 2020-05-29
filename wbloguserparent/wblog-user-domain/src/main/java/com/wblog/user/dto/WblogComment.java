package com.wblog.user.dto;

import java.io.Serializable;
import java.util.Date;

public class WblogComment implements Serializable {
    String id;
    String blogId;
    String commentContent;
    String commenter;
    Date createDate;
    String userIcon;
    String likeCount;//评论点赞数
    String deleteOrNot;//当前用户是否可以删除该评论
    String likeOrNot;//当前用户是否点赞了该评论
    String showDate;

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getDeleteOrNot() {
        return deleteOrNot;
    }

    public void setDeleteOrNot(String deleteOrNot) {
        this.deleteOrNot = deleteOrNot;
    }

    public String getLikeOrNot() {
        return likeOrNot;
    }

    public void setLikeOrNot(String likeOrNot) {
        this.likeOrNot = likeOrNot;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
