package com.wblog.user.rpc;

import com.google.protobuf.InvalidProtocolBufferException;
import com.wblog.proto.WblogUserProto;
import com.wblog.user.constant.WblogResult;
import com.wblog.user.dto.WblogUserInfo;
import com.wblog.user.pojo.WblogUser;
import com.wblog.user.query.WblogUserQuery;
import com.wblog.user.rpc.WblogUserRpc;
import com.wblog.user.rpo.WblogUserRpo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
@Service("WblogUserRpc")
public class WblogUserRpcImpl implements WblogUserRpc {
    @Autowired
    WblogUserRpo wblogUserRpo;

    @Override
    public byte[] getWblogUserByAccount(byte[] param) throws InvalidProtocolBufferException, ExecutionException {
        WblogUserProto.getUserByAccountReq req = WblogUserProto.getUserByAccountReq.parseFrom(param);
        WblogUserQuery wblogUserQuery = new WblogUserQuery();
        wblogUserQuery.setNickName(req.getUserAccount());
        WblogResult<WblogUserInfo> wblogUserByAccount = wblogUserRpo.getWblogUserByAccount(wblogUserQuery);
        WblogUserProto.getUserByAccountRes.Builder builder = WblogUserProto.getUserByAccountRes.newBuilder();
        WblogUserProto.WblogUser wblogUser = new WblogUserProto.WblogUser();
        BeanUtils.copyProperties(wblogUserByAccount.getResult(),wblogUser);
        builder.setResult(wblogUser);
        builder.setCode(wblogUserByAccount.getCode());
        builder.setDesc(wblogUserByAccount.getDesc());
        return builder.build().toByteArray();
    }

    @Override
    public byte[] insertWblogUser(byte[] param) throws InvalidProtocolBufferException {
       WblogUserProto.insertUserReq req = WblogUserProto.insertUserReq.parseFrom(param);
        WblogUserProto.WblogUser wblogUser =  req.getUser();
        WblogUser wblogUserInfo = new WblogUser();
        BeanUtils.copyProperties(wblogUser,wblogUserInfo);
        WblogResult<Integer> result = wblogUserRpo.insertWblogUser(wblogUserInfo);
        WblogUserProto.insertUserRes.Builder builder = WblogUserProto.insertUserRes.newBuilder();
        builder.setCode(result.getCode());
        builder.setDesc(result.getDesc());
        builder.setResult(result.getResult());
        return builder.build().toByteArray();
    }

    @Override
    public byte[] updateWblogUser(byte[] parm) throws InvalidProtocolBufferException {
        WblogUserProto.updateUserReq req = WblogUserProto.updateUserReq.parseFrom(parm);
        WblogUserProto.WblogUser wblogUser =  req.getUser();
        WblogUser wblogUserInfo = new WblogUser();
        BeanUtils.copyProperties(wblogUser,wblogUserInfo);
        WblogResult<Integer> result = wblogUserRpo.updateWblogUser(wblogUserInfo);
        WblogUserProto.updateUserRes.Builder builder = WblogUserProto.updateUserRes.newBuilder();
        builder.setCode(result.getCode());
        builder.setDesc(result.getDesc());
        builder.setResult(result.getResult());
        return builder.build().toByteArray();
    }
}
