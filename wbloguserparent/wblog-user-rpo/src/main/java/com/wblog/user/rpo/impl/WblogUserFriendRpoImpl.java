package com.wblog.user.rpo.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wblog.user.constant.CommonConstant;
import com.wblog.user.constant.WblogResult;
import com.wblog.user.mapper.WblogUserFriendMapper;
import com.wblog.user.pojo.WblogUserFriend;
import com.wblog.user.query.WblogUserFriendQuery;
import com.wblog.user.rpo.WblogUserFriendsRpo;
import com.wblog.user.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
@Service
public class WblogUserFriendRpoImpl implements WblogUserFriendsRpo {
    @Autowired
    WblogUserFriendMapper wblogUserFriendMapper;
    Logger logger = LoggerFactory.getLogger(WblogUserFriendRpoImpl.class);
    //本地缓存，5分钟过期。
    Cache<String,String> cache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES)
            .maximumSize(2000).build();
    @Override
    public WblogResult<String> addFriend(WblogUserFriend wblogUserFriend) {
        WblogResult<String> result = new WblogResult<String>();
        logger.info("WblogUserFriendRpoImpl.addFriend---->"+ JSON.toJSONString(wblogUserFriend));
        try{
            if(wblogUserFriend == null || wblogUserFriend.getFriendNickName()==null||wblogUserFriend.getFriendNickName().equals("")){
                result.setCode(400);
                result.setDesc("传参错误");
                return result;
            }
            //先添加完数据库才删缓存，避免添加失败，而缓存也被删除了。
            wblogUserFriendMapper.insertUserFriend(wblogUserFriend);
            cache.cleanUp();
            RedisUtil.del(CommonConstant.WBLOB_USER_FRIEND+wblogUserFriend.getNickName());
            result.setCode(200);
            result.setResult("添加成功");
            return result;
        }
        catch (Exception e){
            logger.error("WblogUserFriendRpoImpl.addFriend.error---->{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return  result;
        }
    }


    @Override
    public WblogResult<String> getFriends(String nickName) {
        WblogResult<String> result = new WblogResult();
        if(nickName == null || "".equals(nickName)){
            result.setDesc("传参错误");
            result.setCode(400);
            return result;
        }
        try{
            logger.info("WblogUserFriendRpoImpl.getFriends.nickName:"+nickName);
            //先从本地缓存里面获取
            String mid = cache.get(CommonConstant.WBLOB_USER_FRIEND+nickName,()->{
                String friends = RedisUtil.get(CommonConstant.WBLOB_USER_FRIEND+nickName);
                if(friends == null || "".equals(friends)){
                    WblogUserFriendQuery query = new WblogUserFriendQuery();
                    query.setNickName(nickName);
                    List<WblogUserFriend> friends1 = wblogUserFriendMapper.findByCondition(query);
                    String finalResult = "";
                    if(friends1.size() == 0){
                        //防止缓存穿透
                        RedisUtil.setEx(CommonConstant.WBLOB_USER_FRIEND+nickName,CommonConstant.WBLOB_CACHE_EMPTY,3000);
                        return "";
                    }
                    for(WblogUserFriend midFriend : friends1){
                        finalResult += midFriend.getFriendNickName() + ",";
                    }
                    finalResult = finalResult.substring(0,finalResult.length()-1);
                    RedisUtil.set(CommonConstant.WBLOB_USER_FRIEND+nickName,finalResult);
                    cache.put(CommonConstant.WBLOB_USER_FRIEND+nickName,finalResult);
                    return finalResult;
                }else{
                    return friends;
                }
            });
            result.setCode(200);
            result.setResult(mid);
            return result;
        }catch (Exception e){
            logger.error("WblogUserFriendRpoImpl.getFriends.error----->{}",e);
            result.setCode(400);
            result.setDesc(e.getMessage());
            return result;
        }
    }

    @Override
    public WblogResult<String> deleteFriend(String nickName, String friendNickName) {
        WblogResult<String> result = new WblogResult<>();
        logger.info("WblogUserFriendRpoImpl.deleteFriend.nickName:{},friendNickName:{}",nickName,friendNickName);
        if(nickName == null || "".equals(nickName)||friendNickName == null || "".equals(friendNickName)){
            result.setCode(400);
            result.setDesc("参数错误");
            return result;
        }
        try{
            wblogUserFriendMapper.deleteUserFriend(nickName,friendNickName);
            cache.cleanUp();
            RedisUtil.del(CommonConstant.WBLOB_USER_FRIEND+nickName);
            result.setCode(200);
            result.setResult("success");
            return result;
        }catch (Exception e){
            logger.error("WblogUserFriendRpoImpl.deleteFriends.error--->{}",e);
            result.setCode(400);
            result.setDesc("false");
            return result;
        }
    }

}
