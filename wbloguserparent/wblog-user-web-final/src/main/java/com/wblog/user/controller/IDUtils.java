package com.wblog.user.controller;

import com.wblog.user.util.RedisUtil;

public class IDUtils {
    public static  void init(){
        RedisUtil.set("WBLOG_CONTENT_ID",20+"");
        RedisUtil.set("WBLOG_COMMENT_ID",10+"");
        RedisUtil.set("WBLO_MSG_ID",10+"");
    }
    public static String getContentID(){
        String wblog_content_id = RedisUtil.get("WBLOG_CONTENT_ID");
        RedisUtil.incr("WBLOG_CONTENT_ID");
        return wblog_content_id;
    }
    public static String getCommentID(){

        String wblog_comment_id = RedisUtil.get("WBLOG_COMMENT_ID");
        RedisUtil.incr("WBLOG_COMMENT_ID");
        return wblog_comment_id;
    }
    public static String getMsgId(){
        String wblo_msg_id = RedisUtil.get("WBLO_MSG_ID");
        RedisUtil.incr("WBLO_MSG_ID");
        return wblo_msg_id;
    }
}
