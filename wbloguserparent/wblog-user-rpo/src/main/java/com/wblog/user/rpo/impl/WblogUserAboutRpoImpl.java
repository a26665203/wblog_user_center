package com.wblog.user.rpo.impl;

import com.sun.deploy.util.StringUtils;
import com.wblog.user.constant.CommonConstant;
import com.wblog.user.constant.WblogResult;
import com.wblog.user.pojo.WblogUserFriend;
import com.wblog.user.rpo.WblogUserAboutRpo;
import com.wblog.user.util.RedisUtil;
import org.omg.PortableInterceptor.INACTIVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WblogUserAboutRpoImpl implements WblogUserAboutRpo {
    Logger logger = LoggerFactory.getLogger(WblogUserAboutRpoImpl.class);
    @Override
    public WblogResult<Integer> incrComment(String nickName) {
        logger.info("WblogUserAboutRpoImpl.incrComment.nickName:{}",nickName);
        WblogResult<Integer> result = new WblogResult();
        if(nickName == null || "".equals(nickName)){
            result.setCode(400);
            result.setDesc("传入参数错误");
        }
        try{
            String commentCount = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT+nickName,"comment");
            if("error".equals(commentCount)){
                result.setCode(400);
                result.setDesc("Redis方法出错");
                return result;
            }
            if("".equals(commentCount)){
                commentCount = "0";
            }
            int mid = Integer.valueOf(commentCount)+1;
            RedisUtil.hset(CommonConstant.WBLOB_USER_ABOUT+nickName,"comment",mid+"");
            result.setCode(200);
            result.setResult(mid);
            return result;
        }catch (Exception e){
            logger.error("WblogUserAboutRpoImpl.incrComment.error--->{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return result;
        }
    }

    @Override
    public WblogResult<Integer> decrComment(String nickName) {
        logger.info("WblogUserAboutRpoImpl.decrComment.nickName:{}",nickName);
        WblogResult<Integer> result = new WblogResult();
        if(nickName == null || "".equals(nickName)){
            result.setDesc("传参错误");
            result.setCode(400);
            return result;
        }
        try{
            String commentCount = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT+nickName,"comment");
            if("error".equals(commentCount)){
                result.setDesc("Redis方法错误");
                result.setCode(400);
                return result;
            }
            if("".equals(commentCount)||"0".equals(commentCount)){
                result.setCode(400);
                result.setDesc("评论数已经为0");
                return result;
            }
            int mid = Integer.valueOf(commentCount) - 1;
            RedisUtil.hset(CommonConstant.WBLOB_USER_ABOUT+nickName,"comment",mid+"");
            result.setCode(200);
            result.setResult(mid);
            return result;
        }catch (Exception e){
            logger.error("WblogUserAboutRpoImpl.decrComment.error--->{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return result;
        }
    }

    @Override
    public WblogResult<Integer> incrLike(String nickName) {
        logger.info("WblogUserAboutRpoImpl.incrLike.nickName---{}",nickName);
        WblogResult<Integer> result = new WblogResult();
        try{
            if(nickName == null || "".equals(nickName)){
                result.setDesc("传入参数错误");
                result.setCode(400);
                return result;
            }
            String likeCount = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT+nickName,"like");
            if("error".equals(likeCount)){
                result.setDesc("Redis方法错误");
                result.setCode(400);
                return result;
            }
            if("".equals(likeCount)){
                likeCount = "0";
            }
            int mid = Integer.valueOf(likeCount) + 1;
            RedisUtil.hset(CommonConstant.WBLOB_USER_ABOUT+nickName,"like",mid+"");
            result.setCode(200);
            result.setResult(mid);
            return result;
        }catch (Exception e){
            logger.error("WblogUserAboutRpoImpl.incrLike.error---->{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return  result;
        }
    }

    @Override
    public WblogResult<Integer> decrLike(String nickName) {
        logger.info("WblogUserAboutRpoImpl.decrLike.nickName---{}",nickName);
        WblogResult<Integer> result = new WblogResult();
        try{
            if(nickName == null || "".equals(nickName)){
                result.setDesc("传入参数错误");
                result.setCode(400);
                return result;
            }
            String likeCount = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT+nickName,"like");
            if("error".equals(likeCount)){
                result.setCode(400);
                result.setDesc("Redis 方法错误");
                return result;
            }
            if("".equals(likeCount)||"0".equals(likeCount)){
                result.setCode(400);
                result.setDesc("点赞数已为0");
                return result;
            }
            int mid = Integer.valueOf(likeCount) - 1;
            RedisUtil.hset(CommonConstant.WBLOB_USER_ABOUT+nickName,"like",mid+"");
            result.setCode(200);
            result.setResult(mid);
            return result;
        }catch (Exception e){
            logger.error("WblogUserAboutRpoImpl.decrLike.error---->{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return  result;
        }
    }

    @Override
    public WblogResult<Integer> incrBlog(String nickName) {
        logger.info("WblogUserAboutRpoImpl.incrBlog.nickName--->{}",nickName);
        WblogResult<Integer> result = new WblogResult();
        if(nickName == null || "".equals(nickName)){
            result.setCode(400);
            result.setDesc("传入参数错误");
            return result;
        }
        try {
            String blogCount = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT+nickName,"blog");
            if("error".equals(blogCount)){
                result.setCode(400);
                result.setDesc("传入参数错误");
                return result;
            }
            if(blogCount == null || "".equals(blogCount)){
                blogCount = "0";
            }
            int mid = Integer.valueOf(blogCount) + 1;
            RedisUtil.hset(CommonConstant.WBLOB_USER_ABOUT+nickName,"blog",mid+"");
            result.setCode(200);
            result.setResult(mid);
            return result;
        }catch (Exception e){
            logger.error("WblogUserAboutRpoImpl.incrBlog.error---->{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return result;
        }
    }

    @Override
    public WblogResult<Integer> decrBlog(String nickName) {
        logger.info("WblogUserAboutRpoImpl.decrBlog.nickName--->{}",nickName);
        WblogResult<Integer> result = new WblogResult();
        if(nickName == null || "".equals(nickName)){
            result.setCode(400);
            result.setDesc("传入参数错误");
            return result;
        }
        try {
            String blogCount = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT+nickName,"blog");
            if("error".equals(blogCount)){
                result.setCode(400);
                result.setDesc("传入参数错误");
                return result;
            }
            if(blogCount == null || "".equals(blogCount)){
                result.setCode(400);
                result.setDesc("微博数已为0");
                return result;
            }
            int mid = Integer.valueOf(blogCount) - 1;
            RedisUtil.hset(CommonConstant.WBLOB_USER_ABOUT+nickName,"blog",mid+"");
            result.setCode(200);
            result.setResult(mid);
            return result;
        }catch (Exception e){
            logger.error("WblogUserAboutRpoImpl.decrBlog.error---->{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return result;
        }
    }

    @Override
    public WblogResult<Integer> incrFan(String nickName) {
        logger.info("WblogUserAboutRpoImpl.incrFan.nickName--->{}",nickName);
        WblogResult<Integer> result = new WblogResult();
        if(nickName == null || "".equals(nickName)){
            result.setCode(400);
            result.setDesc("传入参数错误");
            return result;
        }
        try{
            String fanCount = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT+nickName,"fan");
            if("error".equals(fanCount)){
                result.setCode(400);
                result.setDesc("Redis方法错误");
                return result;
            }
            if(fanCount == null || "".equals(fanCount)){
                fanCount = "0";
            }
            int mid = Integer.valueOf(fanCount) + 1;
            RedisUtil.hset(CommonConstant.WBLOB_USER_ABOUT+nickName,"fan",mid+"");
            result.setCode(200);
            result.setResult(mid);
            return result;
        }catch (Exception e){
            logger.error("WblogUserAboutRpoImpl.incrFan.error--->{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return result;
        }
    }

    @Override
    public WblogResult<Integer> decrFan(String nickName) {
        logger.info("WblogUserAboutRpoImpl.decrFan.nickName--->{}",nickName);
        WblogResult<Integer> result = new WblogResult();
        if(nickName == null || "".equals(nickName)){
            result.setCode(400);
            result.setDesc("传入参数错误");
            return result;
        }
        try{
            String fanCount = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT+nickName,"fan");
            if("error".equals(fanCount)){
                result.setCode(400);
                result.setDesc("Redis方法错误");
                return result;
            }
            if(fanCount == null || "".equals(fanCount)){
                result.setCode(400);
                result.setDesc("粉丝数已为0");
                return result;
            }
            int mid = Integer.valueOf(fanCount) + 1;
            RedisUtil.hset(CommonConstant.WBLOB_USER_ABOUT+nickName,"fan",mid+"");
            result.setCode(200);
            result.setResult(mid);
            return result;
        }catch (Exception e){
            logger.error("WblogUserAboutRpoImpl.decrFan.error--->{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return result;
        }
    }

    @Override
    public WblogResult<Integer> incrLiked(String nickName) {
        logger.info("WblogUserAboutRpoImpl.incrLiked.nickName--->{}",nickName);
        WblogResult<Integer> result = new WblogResult();
        if(nickName == null || "".equals(nickName)){
            result.setCode(400);
            result.setDesc("传入参数错误");
            return result;
        }
        try{
            String likedCount = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT+nickName,"liked");
            if("error".equals(likedCount)){
                result.setCode(400);
                result.setDesc("redis 方法错误");
                return result;
            }
            if(likedCount == null || "".equals(likedCount)){
                likedCount = "0";
            }
            int mid = Integer.valueOf(likedCount) + 1;
            RedisUtil.hset(CommonConstant.WBLOB_USER_ABOUT+nickName,"liked",mid+"");
            result.setCode(200);
            result.setResult(mid);
            return result;
        }catch (Exception e){
            logger.error("WblogUserAboutRpoImpl.incrLiked.error---->{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return result;
        }
    }

    @Override
    public WblogResult<Integer> decrLiked(String nickName) {
        logger.info("WblogUserAboutRpoImpl.decrLiked.nickName--->{}",nickName);
        WblogResult<Integer> result = new WblogResult();
        if(nickName == null || "".equals(nickName)){
            result.setCode(400);
            result.setDesc("传入参数错误");
            return result;
        }
        try{
            String likedCount = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT+nickName,"liked");
            if("error".equals(likedCount)){
                result.setCode(400);
                result.setDesc("redis 方法错误");
                return result;
            }
            if(likedCount == null || "".equals(likedCount)){
                result.setCode(400);
                result.setDesc("被赞数已为0");

            }
            int mid = Integer.valueOf(likedCount) + 1;
            RedisUtil.hset(CommonConstant.WBLOB_USER_ABOUT+nickName,"liked",mid+"");
            result.setCode(200);
            result.setResult(mid);
            return result;
        }catch (Exception e){
            logger.error("WblogUserAboutRpoImpl.incrLiked.error---->{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return result;
        }
    }
    //1代表在线，0代表离线
    @Override
    public WblogResult<Boolean> updateStatus(String nickName,Integer status) {
        WblogResult<Boolean> result = new WblogResult<Boolean>();
        logger.info("WblogUserAboutRpoImpl.updateStatus---->nickName:{},status:{}",nickName,status);
        if(nickName == null || "".equals(nickName)){
            result.setCode(400);
            result.setDesc("传入参数错误");
            return result;
        }
        try{
            RedisUtil.hset(CommonConstant.WBLOB_USER_ABOUT+nickName,"status",status+"");
            result.setCode(200);
            result.setResult(true);
            return result;
        }catch (Exception e){
            logger.error("WblogUserAboutRpoImpl.updateStatus.error------>{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return result;
        }
    }

    @Override
    public WblogResult<Integer> getCommentCount(String nickName) {
        WblogResult<Integer> result = new WblogResult<Integer>();
        logger.info("WblogUserAboutRpoImpl.getCommentCount.nickName:{}",nickName);
        if(nickName == null || "".equals(nickName)){
            result.setCode(400);
            result.setDesc("传入参数错误");
            return result;
        }
        try{
            String  count = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT+nickName,"comment");
            if(count == null || "".equals(count)){
                count = "0";
            }
            result.setCode(200);
            result.setResult(Integer.valueOf(count));
            return result;
        }catch (Exception e){
            logger.error("WblogUserAboutRpoImpl.getCommentCount.error------>{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return result;
        }
    }

    @Override
    public WblogResult<Integer> getLikeCount(String nickName) {
        WblogResult<Integer> result = new WblogResult<Integer>();
        logger.info("WblogUserAboutRpoImpl.getLikeCount.nickName:{}",nickName);
        if(nickName == null || "".equals(nickName)){
            result.setCode(400);
            result.setDesc("传入参数错误");
            return result;
        }
        try{
            String  count = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT+nickName,"like");
            if(count == null || "".equals(count)){
                count = "0";
            }
            result.setCode(200);
            result.setResult(Integer.valueOf(count));
            return result;
        }catch (Exception e){
            logger.error("WblogUserAboutRpoImpl.getLikeCount.error------>{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return result;
        }
    }

    @Override
    public WblogResult<Integer> getBlogCount(String nickName) {
        WblogResult<Integer> result = new WblogResult<Integer>();
        logger.info("WblogUserAboutRpoImpl.getBlogCount.nickName:{}",nickName);
        if(nickName == null || "".equals(nickName)){
            result.setCode(400);
            result.setDesc("传入参数错误");
            return result;
        }
        try{
            String  count = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT+nickName,"blog");
            if(count == null || "".equals(count)){
                count = "0";
            }
            result.setCode(200);
            result.setResult(Integer.valueOf(count));
            return result;
        }catch (Exception e){
            logger.error("WblogUserAboutRpoImpl.getBlogCount.error------>{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return result;
        }
    }

    @Override
    public WblogResult<Integer> getFanCount(String nickName) {
        WblogResult<Integer> result = new WblogResult<Integer>();
        logger.info("WblogUserAboutRpoImpl.getFanCount.nickName:{}",nickName);
        if(nickName == null || "".equals(nickName)){
            result.setCode(400);
            result.setDesc("传入参数错误");
            return result;
        }
        try{
            String  count = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT+nickName,"fan");
            if(count == null || "".equals(count)){
                count = "0";
            }
            result.setCode(200);
            result.setResult(Integer.valueOf(count));
            return result;
        }catch (Exception e){
            logger.error("WblogUserAboutRpoImpl.getFanCount.error------>{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return result;
        }
    }

    @Override
    public WblogResult<Integer> getLikedCount(String nickName) {
        WblogResult<Integer> result = new WblogResult<Integer>();
        logger.info("WblogUserAboutRpoImpl.getLikedCount.nickName:{}",nickName);
        if(nickName == null || "".equals(nickName)){
            result.setCode(400);
            result.setDesc("传入参数错误");
            return result;
        }
        try{
            String  count = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT+nickName,"liked");
            if(count == null || "".equals(count)){
                count = "0";
            }
            result.setCode(200);
            result.setResult(Integer.valueOf(count));
            return result;
        }catch (Exception e){
            logger.error("WblogUserAboutRpoImpl.getLikedCount.error------>{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return result;
        }
    }

    @Override
    public WblogResult<Integer> getUserStatus(String nickName) {
        WblogResult<Integer> result = new WblogResult<Integer>();
        logger.info("WblogUserAboutRpoImpl.getUserStatus.nickName:{}",nickName);
        if(nickName == null || "".equals(nickName)){
            result.setCode(400);
            result.setDesc("传入参数错误");
            return result;
        }
        try{
            String  count = RedisUtil.hget(CommonConstant.WBLOB_USER_ABOUT+nickName,"status");
            if(count == null || "".equals(count)){
                count = "0";
            }
            result.setCode(200);
            result.setResult(Integer.valueOf(count));
            return result;
        }catch (Exception e){
            logger.error("WblogUserAboutRpoImpl.getUserStatus.error------>{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return result;
        }
    }
}
