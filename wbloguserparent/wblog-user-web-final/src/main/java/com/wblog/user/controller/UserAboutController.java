package com.wblog.user.controller;

import com.wblog.user.constant.WblogResult;
import com.wblog.user.rpo.WblogUserAboutRpo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UserAboutController {
    @Autowired
    WblogUserAboutRpo wblogUserAboutRpo;
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @RequestMapping("/getWblogCount")
    @ResponseBody
    public WblogResult<Integer> getWblogCount(String nickName){
        WblogResult<Integer> blogCount = wblogUserAboutRpo.getBlogCount(nickName);
        return blogCount;
    }
    @RequestMapping("/getUserAbout")
    @ResponseBody
    public WblogResult<String> getUserAbout(String nickName){
        WblogResult<Integer> fanCount=wblogUserAboutRpo.getFanCount(nickName);
        WblogResult<Integer> blogCount = wblogUserAboutRpo.getBlogCount(nickName);
        WblogResult<Integer> likeCount=wblogUserAboutRpo.getLikeCount(nickName);
        WblogResult<Integer> commentCount=wblogUserAboutRpo.getCommentCount(nickName);
        WblogResult<String> userDesc = wblogUserAboutRpo.getUserDesc(nickName);
        WblogResult<String> finalResult = new WblogResult<>();
        if(fanCount.getCode()==200&&blogCount.getCode()==200&&likeCount.getCode()==200&&commentCount.getCode()==200||userDesc.getCode()==200){
            finalResult.setCode(200);
            String result = fanCount.getResult()+","+blogCount.getResult()+","+likeCount.getResult()+","+commentCount+","+userDesc.getResult();
            logger.info("结果是----------》"+result);
            finalResult.setResult(result);
            return finalResult;
        }
        finalResult.setCode(400);
        finalResult.setDesc("获取用户信息出错");
        return finalResult;
    }
}
