package com.wblog.user.rpo;

import com.wblog.user.constant.WblogResult;
import com.wblog.user.pojo.WblogUserFriend;

public interface WblogUserFriendsRpo {
    /**
     * 添加好友
     * @param wblogUserFriend
     * @return
     */
    public WblogResult<String> addFriend(WblogUserFriend wblogUserFriend);

    /**
     * 获取某个用户的好友信息
     * @param nickName
     * @return
     */
    public WblogResult<String> getFriends(String nickName);

    /**
     * 删除某个用户的好友
     * @param nickName
     * @param friendNickName
     * @return
     */
    public WblogResult<String> deleteFriend(String nickName,String friendNickName);



}
