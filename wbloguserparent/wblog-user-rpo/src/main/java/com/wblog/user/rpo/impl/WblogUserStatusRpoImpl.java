package com.wblog.user.rpo.impl;

import com.wblog.user.constant.CommonConstant;
import com.wblog.user.rpo.WblogUserStatusRpo;
import com.wblog.user.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WblogUserStatusRpoImpl implements WblogUserStatusRpo {
    Logger logger = LoggerFactory.getLogger(WblogUserStatusRpoImpl.class);

    @Override
    public boolean reportUserStatus(String account,String status) {
        boolean result =false;
        logger.info("WblogUserStatusRpoImpl.reportUserStatus---->" + account);
        if (account == null || "".equals(account)) {
            return result;
        }
        try {
            //30分钟过期
            RedisUtil.setEx(CommonConstant.WBLOB_USER_STATUS + account, status, 1800);
            result =true;
            return result;
        } catch (Exception e) {
            logger.error("WblogUserStatusRpoImpl.reportUserStatus.error-->{}", e.getMessage());
        }
        return  result;
    }
    //0代表尚未登录，1代表已经登录
    @Override
    public int getUserStatus(String account) {
        logger.info("WblogUserStatusRpo.getUserStatus---->"+account);
        if(account == null || "".equals(account)){
            return 0;
        }
        try{
            String result = RedisUtil.get(CommonConstant.WBLOB_USER_STATUS+account);
            return Integer.valueOf(result);
        }catch (Exception e){
            logger.error("WblogUserStatusRpoImpl.getUserStatus.error--->{}",e);
        }
        return 0;
    }
}
