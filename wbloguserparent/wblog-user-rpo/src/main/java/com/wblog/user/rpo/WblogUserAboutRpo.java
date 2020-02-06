package com.wblog.user.rpo;



import com.wblog.user.constant.WblogResult;


public interface WblogUserAboutRpo {
    public WblogResult<Integer> incrComment(String nickName);
    public WblogResult<Integer> decrComment(String nickName);
    public WblogResult<Integer> incrLike(String nickName);
    public WblogResult<Integer> decrLike(String nickName);
    public WblogResult<Integer> incrBlog(String nickName);
    public WblogResult<Integer> decrBlog(String nickName);
    public WblogResult<Integer> incrFan(String nickName);
    public WblogResult<Integer> decrFan(String nickName);
    public WblogResult<Integer> incrLiked(String nickName);
    public WblogResult<Integer> decrLiked(String nickName);
    public WblogResult<Boolean> updateStatus(String nickName,Integer status);
    public WblogResult<Integer> getCommentCount(String nickName);
    public WblogResult<Integer> getLikeCount(String nickName);
    public WblogResult<Integer> getBlogCount(String nickName);
    public WblogResult<Integer> getFanCount(String nickName);
    public WblogResult<Integer> getLikedCount(String nickName);
    public WblogResult<Integer> getUserStatus(String nickName);
}
