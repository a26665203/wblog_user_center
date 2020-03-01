package com.wblog.user.rpc.impl;
import com.google.protobuf.InvalidProtocolBufferException;
import com.wblog.proto.DecrUserAboutProto;
import com.wblog.proto.GetUserAboutReq;
import com.wblog.proto.IncrUserAboutProto;
import com.wblog.proto.UpdateUserStatusProto;
import com.wblog.user.constant.WblogResult;
import com.wblog.user.rpc.WblogUserAboutRpc;
import com.wblog.user.rpo.WblogUserAboutRpo;
import com.wblog.user.rpo.impl.WblogUserAboutRpoImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
@Service("WblogUserAboutRpc")
public class WblogUserAboutRpcImpl implements WblogUserAboutRpc {
    @Autowired
    WblogUserAboutRpo wblogUserAboutRpo;

    @Override
    public byte[] incrReq(byte[] param) throws InvalidProtocolBufferException {
        IncrUserAboutProto.incrUserAboutReq req = IncrUserAboutProto.incrUserAboutReq.parseFrom(param);
        String nickName = req.getNickName();
        String reqName = req.getReqName();
        WblogResult result;
        switch (reqName){
            case "comment":
                result = wblogUserAboutRpo.incrComment(nickName);
                break;
            case "blog":
                result = wblogUserAboutRpo.incrBlog(nickName);
                break;
            case "fan":
                result = wblogUserAboutRpo.incrFan(nickName);
                break;
            case "like":
                result = wblogUserAboutRpo.incrLike(nickName);
                break;
            case "liked":
                result = wblogUserAboutRpo.incrLiked(nickName);
                break;
            default:
                result = null;
        }
        IncrUserAboutProto.incrUserAboutRes.Builder builder = IncrUserAboutProto.incrUserAboutRes.newBuilder();
        if(result == null){
            builder.setCode(400);
            builder.setDesc("未知错误,请求类型不匹配");
        }else {
            builder.setDesc(result.getDesc());
            builder.setCode(result.getCode());
            builder.setResult((Integer) result.getResult());
        }
        return builder.build().toByteArray();
    }

    @Override
    public byte[] decrReq(byte[] param) throws InvalidProtocolBufferException {
        DecrUserAboutProto.decrUserAboutReq req = DecrUserAboutProto.decrUserAboutReq.parseFrom(param);
        String nickName = req.getNickName();
        String reqName = req.getReqName();
        WblogResult result;
        switch (reqName){
            case "comment":
                result = wblogUserAboutRpo.decrComment(nickName);
                break;
            case "blog":
                result = wblogUserAboutRpo.decrBlog(nickName);
                break;
            case "fan":
                result = wblogUserAboutRpo.decrFan(nickName);
                break;
            case "like":
                result = wblogUserAboutRpo.decrLike(nickName);
                break;
            case "liked":
                result = wblogUserAboutRpo.decrLiked(nickName);
                break;
            default:
                result = null;
        }
        DecrUserAboutProto.decrUserAboutRes.Builder builder = DecrUserAboutProto.decrUserAboutRes.newBuilder();
        if(result == null){
            builder.setCode(400);
            builder.setDesc("未知错误,请求类型不匹配");
        }else {
            builder.setDesc(result.getDesc());
            builder.setCode(result.getCode());
            builder.setResult((Integer) result.getResult());
        }
        return builder.build().toByteArray();
    }

    @Override
    public byte[] updateStatusReq(byte[] param) throws InvalidProtocolBufferException {
        UpdateUserStatusProto.updateUserStatusReq req = UpdateUserStatusProto.updateUserStatusReq.parseFrom(param);
        WblogResult<Boolean> result = wblogUserAboutRpo.updateStatus(req.getNickName(), (int) req.getStatus());
        UpdateUserStatusProto.updateUserStatusRes.Builder builder = UpdateUserStatusProto.updateUserStatusRes.newBuilder();
        builder.setCode(result.getCode());
        builder.setDesc(result.getDesc());
        builder.setResult(result.getResult());
        return builder.build().toByteArray();
    }

    @Override
    public byte[] getReq(byte[] param) throws InvalidProtocolBufferException {
        GetUserAboutReq.getUserAboutReq req = GetUserAboutReq.getUserAboutReq.parseFrom(param);
        String reqName = req.getReqName();
        WblogResult result;
        String nickName =req.getNickName();
        switch (reqName){
            case "comment":
                result = wblogUserAboutRpo.getCommentCount(nickName);
                break;
            case "blog":
                result = wblogUserAboutRpo.getBlogCount(nickName);
                break;
            case "fan":
                result = wblogUserAboutRpo.getFanCount(nickName);
                break;
            case "like":
                result = wblogUserAboutRpo.getLikeCount(nickName);
                break;
            case "liked":
                result = wblogUserAboutRpo.getLikedCount(nickName);
                break;
            case "status":
                result = wblogUserAboutRpo.getUserStatus(nickName);
                break;
            default:
                result = null;
        }
        GetUserAboutReq.getUserAboutRes.Builder builder = GetUserAboutReq.getUserAboutRes.newBuilder();
        if(result == null){
            builder.setCode(400);
            builder.setDesc("未知错误,请求类型不匹配");
        }else {
            builder.setDesc(result.getDesc());
            builder.setCode(result.getCode());
            builder.setResult((Integer)result.getResult());
        }
        return builder.build().toByteArray();
    }
}
