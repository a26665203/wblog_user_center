package com.wblog.user.rpc;

import com.google.protobuf.InvalidProtocolBufferException;
import com.wblog.proto.AddFriendProto;
import com.wblog.proto.GetFriendProto;
import com.wblog.user.constant.WblogResult;
import com.wblog.user.pojo.WblogUserFriend;
import com.wblog.user.rpc.WblogUserFriendRpc;
import com.wblog.user.rpo.WblogUserFriendsRpo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("WblogUserFriendRpc")
public class WblogUserFriendRpcImpl implements WblogUserFriendRpc {
    @Autowired
    WblogUserFriendsRpo wblogUserFriendsRpo;

    @Override
    public byte[] addFriend(byte[] param) throws InvalidProtocolBufferException {
        AddFriendProto.addFriendReq req = AddFriendProto.addFriendReq.parseFrom(param);
        WblogUserFriend wblogUserFriend = new WblogUserFriend();
        wblogUserFriend.setNickName(req.getNickName());
        wblogUserFriend.setFriendNickName(req.getFriendNickName());
        WblogResult<String> result = wblogUserFriendsRpo.addFriend(wblogUserFriend);
        AddFriendProto.addFriendRes.Builder builder = AddFriendProto.addFriendRes.newBuilder();
        builder.setCode(result.getCode());
        builder.setDesc(result.getDesc());
        builder.setResult(result.getResult());
        return builder.build().toByteArray();
    }

    @Override
    public byte[] getFriends(byte[] param) throws InvalidProtocolBufferException {
        GetFriendProto.GetFriendReq req = GetFriendProto.GetFriendReq.parseFrom(param);
        String nickName = req.getNickName();
        WblogResult<String> friends = wblogUserFriendsRpo.getFriends(nickName);
        GetFriendProto.GetFriendRes.Builder builder = GetFriendProto.GetFriendRes.newBuilder();
        builder.setResult(friends.getResult());
        builder.setCode(friends.getCode());
        builder.setDesc(friends.getDesc());
        return builder.build().toByteArray();
    }

    @Override
    public byte[] deleteFriend(byte[] param) {
        return new byte[0];
    }
}
