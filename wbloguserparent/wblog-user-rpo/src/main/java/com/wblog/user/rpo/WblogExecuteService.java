package com.wblog.user.rpo;

import com.wblog.user.constant.WblogResult;

public interface WblogExecuteService {
    public WblogResult<String> likeWblog(String nickName,String blogId);
    public WblogResult<Boolean> commentBlog(String comment,String blogId,String commenter);
    public WblogResult<String> unLikeBlog(String nickName,String blogId);
    public WblogResult<Boolean> likeComment(String nickName,String commentId);
    public WblogResult<Boolean> unLikeComment(String nickName,String commentId);
}
