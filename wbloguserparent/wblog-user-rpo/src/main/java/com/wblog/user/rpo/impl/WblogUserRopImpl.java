package com.wblog.user.rpo.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wblog.user.constant.CommonConstant;
import com.wblog.user.constant.WblogResult;
import com.wblog.user.dto.WblogUserInfo;
import com.wblog.user.mapper.WblogUserMapper;
import com.wblog.user.pojo.WblogUser;
import com.wblog.user.query.WblogUserQuery;
import com.wblog.user.rpo.WblogUserRpo;
import com.wblog.user.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
@Service
public class WblogUserRopImpl implements WblogUserRpo {
    Logger logger = LoggerFactory.getLogger(WblogUserRopImpl.class);
    @Autowired
    WblogUserMapper wblogUserMapper;

    //本地缓存一分钟过期
    Cache<String,String > cache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES)
            .maximumSize(500).build();
    public WblogResult<WblogUserInfo> getWblogUserByAccount(final WblogUserQuery query) throws ExecutionException {
        WblogResult<WblogUserInfo> result = new WblogResult<WblogUserInfo>();
        logger.info("WblogUserRpoImpl.getWblogUserByAccount--->"+ JSON.toJSONString(query));
        if(query.getUserAccount()==null || "".equals(query.getUserAccount())){
            result.setDesc("参数错误");
            result.setCode(400);
            result.setResult(null);
            return result;
        }
        final String account = query.getUserAccount();
        try {
            String value = cache.get(CommonConstant.WBLOB_USER_PREFIX+account, () -> {
                String json = RedisUtil.get(CommonConstant.WBLOB_USER_PREFIX + account);
                if (json != null && !"".equals(json)) {
                    return json;
                } else {
                    List<WblogUser> list = wblogUserMapper.findByCondition(query);
                    if (list.size() == 0) {
                        //防止缓存穿透
                        RedisUtil.setEx(CommonConstant.WBLOB_USER_PREFIX + account, CommonConstant.WBLOB_CACHE_EMPTY,3000);
                        return "";
                    }
                    WblogUser user = list.get(0);
                    WblogUserInfo wblogUserInfo = new WblogUserInfo();
                    BeanUtils.copyProperties(user, wblogUserInfo);
                    JSONObject object = (JSONObject) JSONObject.toJSON(wblogUserInfo);
                    RedisUtil.setEx(CommonConstant.WBLOB_USER_PREFIX + account, object.toJSONString(), 24 * 60 * 60);
                    cache.put(CommonConstant.WBLOB_USER_PREFIX+account,object.toJSONString());
                    return object.toJSONString();
                }
            });
            result.setCode(200);
            if (value == null || "".equals(value) || CommonConstant.WBLOB_CACHE_EMPTY.equals(value)) {
                result.setResult(null);
                return result;
            }
            WblogUserInfo wblogUserInfo = JSONObject.parseObject(value, WblogUserInfo.class);
            result.setResult(wblogUserInfo);
            return result;
        }
        catch (Exception e){
            logger.error("WblogUserRpoImpl.getWblogUserByAccount--->{}",e);
            result.setDesc(e.getMessage());
            result.setCode(400);
            return result;
        }
    }

    public WblogResult insertWblogUser(WblogUser user) {
        WblogResult<Integer> result = new WblogResult<>();
        logger.info("WblogUserRpoImpl.insertWblogUser---->"+JSON.toJSONString(user));
        if(user == null){
            result.setCode(400);
            result.setDesc("参数错误");
            return result;
        }
        try{
            int mid = wblogUserMapper.insertUser(user);
            result.setResult(mid);
            result.setCode(200);
            return result;
        }catch (Exception e){
            logger.error("WblogUserRpoImpl.insertWblogUser.error:{}",e);
            result.setCode(400);
            result.setDesc("注册失败，昵称或者账号重复");
            return result;
        }
    }

    public WblogResult updateWblogUser(WblogUser user) {
        WblogResult<Integer> result = new WblogResult<>();
        logger.info("WblogUserRpoImpl.updateWblogUser---->"+JSON.toJSONString(user));
        if(user == null){
            result.setDesc("参数错误");
            result.setCode(400);
            return result;
        }
        try{
            //保证数据库与缓存双写一致性，先更新数据再删缓存
            int mid = wblogUserMapper.updateUser(user);
            RedisUtil.del(CommonConstant.WBLOB_USER_PREFIX+user.getUserAccount());
            result.setResult(mid);
            result.setCode(200);
            return result;
        }catch (Exception e){
            logger.error("WblogUserRpoImpl.updateWblogUser.tpError--->{}",e.getMessage());
            result.setCode(400);
            return result;
        }
    }
}
