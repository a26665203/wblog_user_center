package com.wblog.user.rpc;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.wblog.user.constant.CommonConstant;
import com.wblog.user.constant.WblogResult;
import com.wblog.user.rpo.WblogExecuteService;
import com.wblog.user.rpo.WblogUserAboutRpo;
import com.wblog.user.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WblogExecuteServiceImpl implements WblogExecuteService {
    Logger logger = LoggerFactory.getLogger(WblogExecuteServiceImpl.class);
    @Autowired
    WblogUserAboutRpo wblogUserAboutRpo;
    @Override
    public WblogResult<String> likeWblog(String nickName, String blogId) {
        WblogResult<String> result = new WblogResult<>();
        logger.info("WblogExecuteSetviceImpl.likeWblog----->nickName:"+nickName+",blogId:"+blogId);
        if(StringUtils.isBlank(nickName)||StringUtils.isBlank(blogId)){
            result.setCode(400);
            result.setDesc("参数不完整");
        }
        wblogUserAboutRpo.incrLike(nickName);
        while(StringUtils.isBlank(RedisUtil.lock(CommonConstant.WBLOG_MESSAGE_LIKE+blogId+"#state","1"))){

        }
        String likeCount = RedisUtil.get(CommonConstant.WBLOG_MESSAGE_LIKE+blogId);
        if(StringUtils.isBlank(likeCount)){
            RedisUtil.set(CommonConstant.WBLOG_MESSAGE_LIKE+blogId,"1");
            likeCount = "1";
        }else{
            RedisUtil.incr(CommonConstant.WBLOG_MESSAGE_LIKE+blogId);
            int lot = Integer.valueOf(likeCount)+1;
            likeCount = lot+"";
        }
        //释放锁
        RedisUtil.del(CommonConstant.WBLOG_MESSAGE_LIKE+blogId+"#state");
        RedisUtil.set(CommonConstant.WBLOG_USER_WBLOG_LIKE_MAPPER+nickName+"#"+blogId,"1");
        result.setCode(200);
        result.setResult(likeCount);
        return result;
    }

    @Override
    public WblogResult<Boolean> commentBlog(String comment,String blogId,String commenter) {
        return null;
    }

    @Override
    public WblogResult<String> unLikeBlog(String nickName, String blogId) {
        WblogResult<String> result = new WblogResult<>();
        logger.info("WblogExecuteSetviceImpl.unLikeWblog----->nickName:"+nickName+",blogId:"+blogId);
        if(StringUtils.isBlank(nickName)||StringUtils.isBlank(blogId)){
            result.setCode(400);
            result.setDesc("参数不完整");
        }
        wblogUserAboutRpo.decrLike(nickName);
        while(StringUtils.isBlank(RedisUtil.lock(CommonConstant.WBLOG_MESSAGE_LIKE+blogId+"#state","1"))){

        }
        String likeCount = RedisUtil.get(CommonConstant.WBLOG_MESSAGE_LIKE+blogId);
            RedisUtil.decr(CommonConstant.WBLOG_MESSAGE_LIKE+blogId);
            int lot = Integer.valueOf(likeCount)-1;
            likeCount = lot+"";
        //释放锁
        RedisUtil.del(CommonConstant.WBLOG_MESSAGE_LIKE+blogId+"#state");
        RedisUtil.del(CommonConstant.WBLOG_USER_WBLOG_LIKE_MAPPER+nickName+"#"+blogId);
        result.setCode(200);
        result.setResult(likeCount);
        return result;
    }
    //点赞评论
    @Override
    public WblogResult<Boolean> likeComment(String nickName, String commentId) {
        WblogResult<Boolean> result = new WblogResult<>();
        try{
            while (StringUtils.isBlank(RedisUtil.lock(CommonConstant.WBLOG_COMMENT_LIKE+commentId+"#state","1"))){

            }
            String count = RedisUtil.get(CommonConstant.WBLOG_COMMENT_LIKE+commentId);
            if(StringUtils.isBlank(count)){
                RedisUtil.set(CommonConstant.WBLOG_COMMENT_LIKE+commentId,"1");
            }else{
                RedisUtil.incr(CommonConstant.WBLOG_COMMENT_LIKE+commentId);
            }
            RedisUtil.set(CommonConstant.WBLOG_COMMENT_USER_MAPPER+commentId+"#"+nickName,"1");
            RedisUtil.del(CommonConstant.WBLOG_COMMENT_LIKE+commentId+"#state");
            result.setCode(200);
            result.setResult(true);
        }catch (Exception e){
            logger.error("WblogExecuteService.likeComment.error:{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
        }
        return result;
    }

    @Override
    public WblogResult<Boolean> unLikeComment(String nickName, String commentId) {
        WblogResult<Boolean> result = new WblogResult<>();
        try{
            while (StringUtils.isBlank(RedisUtil.lock(CommonConstant.WBLOG_COMMENT_LIKE+commentId+"#state","1"))){

            }
            RedisUtil.decr(CommonConstant.WBLOG_COMMENT_LIKE+commentId);
            RedisUtil.del(CommonConstant.WBLOG_COMMENT_USER_MAPPER+commentId+"#"+nickName);
            RedisUtil.del(CommonConstant.WBLOG_COMMENT_LIKE+commentId+"#state");
            result.setCode(200);
            result.setResult(true);
        }catch (Exception e){
            logger.error("WblogExecuteService.unlikeComment.error:{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
        }
        return result;
    }
}
