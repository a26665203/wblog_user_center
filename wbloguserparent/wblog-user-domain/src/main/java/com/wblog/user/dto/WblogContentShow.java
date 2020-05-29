package com.wblog.user.dto;

import java.io.Serializable;
import java.util.List;

public class WblogContentShow implements Serializable {
    String userIcon;
    String creator;
    String id;
    String imageUrl;
    String sendDate;
    String content;
    String likeCount;
    String commentCount;
    String likeOrNot;//记录当前用户是否点赞了这条微博
    List<WblogComment> comments;

    public List<WblogComment> getComments() {
        return comments;
    }

    public void setComments(List<WblogComment> comments) {
        this.comments = comments;
    }

    public String getLikeOrNot() {
        return likeOrNot;
    }

    public void setLikeOrNot(String likeOrNot) {
        this.likeOrNot = likeOrNot;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }
}
