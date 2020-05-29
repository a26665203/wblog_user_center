package com.wblog.user;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Timestamp;
import com.wblog.content.rpc.WblogCommentRpc;
import com.wblog.proto.WblogCommentProto;
import com.wblog.proto.WblogContentProto;
import com.wblog.user.constant.CommonConstant;
import com.wblog.user.constant.WblogResult;
import com.wblog.user.dto.WblogComment;
import com.wblog.user.dto.WblogContentShow;
import com.wblog.user.rpo.WblogUserAboutRpo;
import com.wblog.user.util.RedisPool;
import com.wblog.user.util.RedisUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class WblogParser {
    @Autowired
    WblogUserAboutRpo wblogUserAboutRpo;
    @Resource
    WblogCommentRpc wblogCommentRpc;
    public  WblogContentShow parseContent(WblogContentProto.WblogContentPojo pojo,String nickName,int commentPage,int commentSize) throws InvalidProtocolBufferException {
        WblogContentShow wblogContentShow = new WblogContentShow();
        BeanUtils.copyProperties(pojo,wblogContentShow);
        Timestamp createDate = pojo.getCreateDate();
        Date date = new Date(createDate.getSeconds()*1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sendDate = simpleDateFormat.format(date);
        wblogContentShow.setSendDate(sendDate);
        String imageUrl = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT + wblogContentShow.getCreator(), "imageUrl");
        wblogContentShow.setUserIcon(imageUrl);
        //分布式锁，如果拿不到锁则一直自旋等待。
        String likeCount = RedisUtil.get(CommonConstant.WBLOG_MESSAGE_LIKE+wblogContentShow.getId());
        //分布式锁，防止多个同时设置;
        if(StringUtils.isBlank(likeCount)){
            likeCount ="0";
            RedisUtil.set(CommonConstant.WBLOG_MESSAGE_LIKE+wblogContentShow.getId(),likeCount);
        }
        String commentCount = RedisUtil.get(CommonConstant.WBLOG_MESSAGE_COMMENT+wblogContentShow.getId());
        if(StringUtils.isBlank(commentCount)){
            commentCount = "0";
            RedisUtil.set(CommonConstant.WBLOG_MESSAGE_COMMENT+wblogContentShow.getId(),commentCount);
        }
        wblogContentShow.setLikeCount(likeCount);
        wblogContentShow.setCommentCount(commentCount);
        String s = RedisUtil.get(CommonConstant.WBLOG_USER_WBLOG_LIKE_MAPPER + nickName + "#" + pojo.getId());
        if(StringUtils.isBlank(s)){
            wblogContentShow.setLikeOrNot("false");
        }else{
            wblogContentShow.setLikeOrNot("true");
        }
        WblogCommentProto.FindWblogCommentByBlogIdReq.Builder builder = WblogCommentProto.FindWblogCommentByBlogIdReq.newBuilder();
        builder.setBlogId(wblogContentShow.getId());
        builder.setPage(0);
        builder.setSize(commentSize);
        byte[] wblogCommentByBlogId = wblogCommentRpc.findWblogCommentByBlogId(builder.build().toByteArray());
        WblogCommentProto.FindWblogCommentByBlogIdRes res = WblogCommentProto.FindWblogCommentByBlogIdRes.parseFrom(wblogCommentByBlogId);
        List<WblogComment> comments = new ArrayList<>();
        int comment = res.getResultCount();
        for(int k =0;k<comment;k++){
            WblogCommentProto.CommentContentPojo result = res.getResult(k);
            WblogComment mid = new WblogComment();
            mid.setBlogId(result.getBlogId());
            mid.setCommentContent(result.getCommentContent());
            mid.setCommenter(result.getCommenter());
            Timestamp midDate = result.getCreateDate();
            Date transDate = new Date(midDate.getSeconds()*1000);
            mid.setCreateDate(transDate);
            mid.setShowDate(simpleDateFormat.format(transDate));
            mid.setId(result.getId());
            mid.setUserIcon(RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT+result.getCommenter(),"imageUrl"));
            String commentLike = RedisUtil.get(CommonConstant.WBLOG_COMMENT_LIKE+result.getId());
            if(StringUtils.isBlank(commentLike)){
                commentLike = "0" ;
            }
            if(result.getCommenter().equals(nickName)){
                mid.setDeleteOrNot("true");
            }else{
                mid.setDeleteOrNot("false");
            }
            String likeOrNot = RedisUtil.get(CommonConstant.WBLOG_COMMENT_USER_MAPPER+result.getId()+"#"+nickName);
            if(StringUtils.isBlank(likeOrNot)){
                mid.setLikeOrNot("false");
            }else{
                mid.setLikeOrNot("true");
            }
            mid.setLikeCount(commentLike);
            comments.add(mid);
        }
        wblogContentShow.setComments(comments);
        return wblogContentShow;
    }
    public WblogContentShow parseWblogSimple(WblogContentProto.WblogContentPojo pojo,String nickName){
        WblogContentShow wblogContentShow = new WblogContentShow();
        BeanUtils.copyProperties(pojo,wblogContentShow);
        Timestamp createDate = pojo.getCreateDate();
        Date date = new Date(createDate.getSeconds()*1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sendDate = simpleDateFormat.format(date);
        wblogContentShow.setSendDate(sendDate);
        String imageUrl = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT + wblogContentShow.getCreator(), "imageUrl");
        wblogContentShow.setUserIcon(imageUrl);
        //分布式锁，如果拿不到锁则一直自旋等待。
        String likeCount = RedisUtil.get(CommonConstant.WBLOG_MESSAGE_LIKE+wblogContentShow.getId());
        //分布式锁，防止多个同时设置;
        if(StringUtils.isBlank(likeCount)){
            likeCount ="0";
            RedisUtil.set(CommonConstant.WBLOG_MESSAGE_LIKE+wblogContentShow.getId(),likeCount);
        }
        String commentCount = RedisUtil.get(CommonConstant.WBLOG_MESSAGE_COMMENT+wblogContentShow.getId());
        if(StringUtils.isBlank(commentCount)){
            commentCount = "0";
            RedisUtil.set(CommonConstant.WBLOG_MESSAGE_COMMENT+wblogContentShow.getId(),commentCount);
        }
        wblogContentShow.setLikeCount(likeCount);
        wblogContentShow.setCommentCount(commentCount);
        return wblogContentShow;
    }
}
