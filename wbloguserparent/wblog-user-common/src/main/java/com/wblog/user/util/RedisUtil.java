package com.wblog.user.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

public class RedisUtil {
    public static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    /**
     * 设置key的有效期，单位是秒
     * @param key
     * @param exTime
     * @return
     */
    public static Long expire(String key,int exTime){
        Jedis jedis = null;
        Long result = null;
        try {
            //从Redis连接池中获得Jedis对象
            jedis = RedisPool.getJedis();
            //设置成功则返回Jedis对象
            result = jedis.expire(key,exTime);
        } catch (Exception e) {
            logger.error("expire key:{} error",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }
    //exTime的单位是秒
    //设置key-value并且设置超时时间
    public static String setEx(String key,String value,int exTime){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key,exTime,value);
        } catch (Exception e) {
            logger.error("setex key:{} value:{} error",key,value,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }
    public static String set(String key,String value){
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key,value);
        } catch (Exception e) {
            logger.error("set key:{} value:{} error",key,value,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }
    public static String get(String key){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            logger.error("get key:{} error",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }
    public static Long del(String key){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            logger.error("del key:{} error",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    /**
     * nxxx： 只能取NX或者XX，如果取NX，则只有当key不存在是才进行set，如果取XX，则只有当key已经存在时才进行set
     * expx： 只能取EX或者PX，代表数据过期时间的单位，EX代表秒，PX代表毫秒。
     * time： 过期时间，单位是expx所代表的单位
     * @param key
     * @param value
     * @param nxxx
     * @param expx
     * @param time
     * @return
     */
    public static String set(String key,String value,String nxxx,String expx,long time){
        Jedis jedis = null;
        String result =null;
        try{
            jedis = RedisPool.getJedis();
            result = jedis.set(key,value,nxxx,expx,time);
        }
        catch (Exception e){
            logger.error("set key:{} value:{} error",key,value,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnBrokenResource(jedis);
        return result;
    }


}
