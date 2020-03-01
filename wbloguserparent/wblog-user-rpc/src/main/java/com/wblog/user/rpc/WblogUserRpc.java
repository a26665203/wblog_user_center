package com.wblog.user.rpc;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.concurrent.ExecutionException;

public interface WblogUserRpc {
    byte[] getWblogUserByAccount(byte[] param) throws InvalidProtocolBufferException, ExecutionException;
    byte[] insertWblogUser(byte[] param) throws InvalidProtocolBufferException;
    byte[] updateWblogUser(byte[] parm) throws InvalidProtocolBufferException;
}
