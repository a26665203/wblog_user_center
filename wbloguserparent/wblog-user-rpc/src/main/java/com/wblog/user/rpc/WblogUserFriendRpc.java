package com.wblog.user.rpc;

import com.google.protobuf.InvalidProtocolBufferException;
import com.wblog.user.constant.WblogResult;
import com.wblog.user.pojo.WblogUserFriend;

public interface WblogUserFriendRpc {
    /**
     * 添加好友
     * @param
     * @return
     */
    public byte[] addFriend(byte[] param) throws InvalidProtocolBufferException;

    /**
     * 获取某个用户的好友信息
     * @param
     * @return
     */
    public byte[] getFriends(byte[] param) throws InvalidProtocolBufferException;

    /**
     * 删除某个用户的好友
     * @param
     * @param
     * @return
     */
    public byte[] deleteFriend(byte[] param);

}
