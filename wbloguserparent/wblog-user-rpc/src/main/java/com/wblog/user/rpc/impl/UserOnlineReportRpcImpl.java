package com.wblog.user.rpc.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.wblog.proto.UserOnlineStatusProto;
import com.wblog.user.rpc.UserOnlineReportRpc;
import com.wblog.user.rpo.WblogUserStatusRpo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserOnlineReportRpc")
public class UserOnlineReportRpcImpl implements UserOnlineReportRpc {
    @Autowired
    WblogUserStatusRpo wblogUserStatusRpo;
    @Override
    public byte[] reportUserStatus(byte[] param) throws InvalidProtocolBufferException {
        UserOnlineStatusProto.reportUserStatusReq req = UserOnlineStatusProto.reportUserStatusReq.parseFrom(param);
        boolean b = wblogUserStatusRpo.reportUserStatus(req.getAccount(), req.getStatus());
        UserOnlineStatusProto.reportUserStatusRes.Builder builder = UserOnlineStatusProto.reportUserStatusRes.newBuilder();
        builder.setResult(b);
        return builder.build().toByteArray();
    }

    @Override
    public byte[] getUserStatus(byte[] param) throws InvalidProtocolBufferException {
        UserOnlineStatusProto.getUserStatusReq req = UserOnlineStatusProto.getUserStatusReq.parseFrom(param);
        int userStatus = wblogUserStatusRpo.getUserStatus(req.getAccount());
        UserOnlineStatusProto.getUserStatusRes.Builder builder = UserOnlineStatusProto.getUserStatusRes.newBuilder();
        builder.setResult(userStatus);
        return builder.build().toByteArray();
    }
}
