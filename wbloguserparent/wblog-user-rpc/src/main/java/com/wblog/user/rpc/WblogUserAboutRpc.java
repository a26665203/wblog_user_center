package com.wblog.user.rpc;

import com.google.protobuf.InvalidProtocolBufferException;
import com.wblog.user.constant.WblogResult;

public interface WblogUserAboutRpc {
    public byte[] incrReq(byte[] param) throws InvalidProtocolBufferException;
    public byte[]decrReq(byte[] param) throws InvalidProtocolBufferException;;
    public byte[]updateStatusReq(byte[]param) throws InvalidProtocolBufferException;
    public byte[] getReq(byte[] param) throws InvalidProtocolBufferException;

}
