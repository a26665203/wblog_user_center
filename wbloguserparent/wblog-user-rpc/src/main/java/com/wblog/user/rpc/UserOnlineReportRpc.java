package com.wblog.user.rpc;

import com.google.protobuf.InvalidProtocolBufferException;

public interface UserOnlineReportRpc {
    public byte[] reportUserStatus(byte[] param) throws InvalidProtocolBufferException;
    public byte[] getUserStatus(byte[] param) throws InvalidProtocolBufferException;
}
