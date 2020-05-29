package com.wblog.user.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.google.protobuf.InvalidProtocolBufferException;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.wblog.content.rpc.WblogContentRpc;
import com.wblog.proto.WblogContentProto;
import com.wblog.user.WblogParser;
import com.wblog.user.constant.WblogResult;
import com.wblog.user.dto.WblogChatoPojo;
import com.wblog.user.dto.WblogContentShow;
import com.wblog.user.dto.WblogUserInfo;
import com.wblog.user.pojo.WblogUser;
import com.wblog.user.query.WblogUserQuery;
import com.wblog.user.rpo.WblogUserAboutRpo;
import com.wblog.user.rpo.WblogUserRpo;
import com.wblog.user.rpo.WblogUserStatusRpo;
import com.wblog.user.util.EmailUtil;
import com.wblog.user.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Controller
public class UserController {
    @Autowired
    WblogParser wblogParser;
    Logger logger = LoggerFactory.getLogger(UserController.class);
    final String BIZCODE_PRE = "BIZCODE_";
    @Autowired
    WblogUserRpo wblogUserRpo;
    @Autowired
    WblogUserStatusRpo wblogUserStatusRpo;
    @Resource
    WblogContentRpc wblogContentRpc;
    @Autowired
    WblogUserAboutRpo wblogUserAboutRpo;
    @RequestMapping("/login")
    public String toLogin(){
        return "index";
    }
    @RequestMapping("/")
    public String toLogin1(){
        return "index";
    }

    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    @ResponseBody
    public WblogResult<String> login(String account, String password) throws ExecutionException {
        WblogUserQuery wblogUserQuery = new WblogUserQuery();
        wblogUserQuery.setUserAccount(account);
        WblogResult<WblogUserInfo> wblogUserByAccount = wblogUserRpo.getWblogUserByAccount(wblogUserQuery);
        WblogResult<String> booleanWblogResult = new WblogResult<>();
        booleanWblogResult.setDesc(wblogUserByAccount.getDesc());
        booleanWblogResult.setCode(wblogUserByAccount.getCode());
        //上报用户登陆状态
        if(wblogUserByAccount.getResult()!=null){
            if(wblogUserByAccount.getResult().getUserPassword().equals(password)) {
                wblogUserAboutRpo.updateStatus(wblogUserByAccount.getResult().getNickName(),1);
                booleanWblogResult.setResult(wblogUserByAccount.getResult().getNickName());
            }else{
                booleanWblogResult.setCode(400);
                booleanWblogResult.setDesc("密码不正确");
            }
        }else{
            booleanWblogResult.setCode(400);
        }

        return booleanWblogResult;
    }

    @RequestMapping("/toIndex")
    public ModelAndView toIndex(String nickName,String account) throws ExecutionException, InvalidProtocolBufferException {
        ModelAndView modelAndView;
        if(StringUtils.isBlank(nickName)||StringUtils.isBlank(wblogUserAboutRpo.getUserStatus(nickName)+"")){
            modelAndView = new ModelAndView("index");
            return modelAndView;
        }
        WblogUserQuery wblogUserQuery = new WblogUserQuery();
        wblogUserQuery.setUserAccount(account);
        WblogResult<WblogUserInfo> wblogUserByAccount = wblogUserRpo.getWblogUserByAccount(wblogUserQuery);
        WblogResult<Integer> fanCount=wblogUserAboutRpo.getFanCount(nickName);
        WblogResult<Integer> blogCount = wblogUserAboutRpo.getBlogCount(nickName);
        WblogResult<Integer> likeCount=wblogUserAboutRpo.getLikeCount(nickName);
        WblogResult<Integer> commentCount=wblogUserAboutRpo.getCommentCount(nickName);
        WblogResult<String> userDesc = wblogUserAboutRpo.getUserDesc(nickName);
        WblogResult<String> headIcon = wblogUserAboutRpo.getHeadIcon(nickName);
        WblogContentProto.FindWblogContentByPageReq.Builder builder = WblogContentProto.FindWblogContentByPageReq.newBuilder();
        builder.setCreator(nickName);
        builder.setPage(0);
        builder.setSize(4);
        byte[] wblogContentByPage = wblogContentRpc.findWblogContentByPage(builder.build().toByteArray());
        WblogContentProto.FindWblogContentByPageRes res = WblogContentProto.FindWblogContentByPageRes.parseFrom(wblogContentByPage);
        int count = res.getResultCount();
        List<WblogContentShow> list = new ArrayList<>();
        for(int k =0 ;k<count;k++){
            list.add(wblogParser.parseWblogSimple(res.getResult(k),nickName));
        }
        modelAndView = new ModelAndView("person");
        modelAndView.addObject("nickName",nickName);
        modelAndView.addObject("blogCount",blogCount.getResult());
        modelAndView.addObject("fanCount",fanCount.getResult());
        modelAndView.addObject("likeCount",likeCount.getResult());
        modelAndView.addObject("commentCount",commentCount.getResult());
        modelAndView.addObject("userDesc",userDesc.getResult());
        modelAndView.addObject("userEmail",wblogUserByAccount.getResult().getEmail());
        modelAndView.addObject("userAccount",account);
        modelAndView.addObject("userIcon",headIcon.getResult());
        modelAndView.addObject("personalWblogs",list);
        modelAndView.addObject("page",1);
        modelAndView.addObject("wblogCount",list.size());
        return modelAndView;
    }
    @RequestMapping("/findPersonWblog")
    public ModelAndView personWblog(String nickName,String account,int page) throws InvalidProtocolBufferException, ExecutionException {
        ModelAndView modelAndView;
        if(StringUtils.isBlank(nickName)||StringUtils.isBlank(wblogUserAboutRpo.getUserStatus(nickName)+"")){
            modelAndView = new ModelAndView("index");
            return modelAndView;
        }
        WblogUserQuery wblogUserQuery = new WblogUserQuery();
        wblogUserQuery.setUserAccount(account);
        WblogResult<WblogUserInfo> wblogUserByAccount = wblogUserRpo.getWblogUserByAccount(wblogUserQuery);
        WblogResult<Integer> fanCount=wblogUserAboutRpo.getFanCount(nickName);
        WblogResult<Integer> blogCount = wblogUserAboutRpo.getBlogCount(nickName);
        WblogResult<Integer> likeCount=wblogUserAboutRpo.getLikeCount(nickName);
        WblogResult<Integer> commentCount=wblogUserAboutRpo.getCommentCount(nickName);
        WblogResult<String> userDesc = wblogUserAboutRpo.getUserDesc(nickName);
        WblogResult<String> headIcon = wblogUserAboutRpo.getHeadIcon(nickName);
        WblogContentProto.FindWblogContentByPageReq.Builder builder = WblogContentProto.FindWblogContentByPageReq.newBuilder();
        builder.setCreator(nickName);
        builder.setPage((page-1)*4);
        builder.setSize(4);
        byte[] wblogContentByPage = wblogContentRpc.findWblogContentByPage(builder.build().toByteArray());
        WblogContentProto.FindWblogContentByPageRes res = WblogContentProto.FindWblogContentByPageRes.parseFrom(wblogContentByPage);
        int count = res.getResultCount();
        List<WblogContentShow> list = new ArrayList<>();
        for(int k =0 ;k<count;k++){
            list.add(wblogParser.parseWblogSimple(res.getResult(k),nickName));
        }
        modelAndView = new ModelAndView("person");
        modelAndView.addObject("nickName",nickName);
        modelAndView.addObject("blogCount",blogCount.getResult());
        modelAndView.addObject("fanCount",fanCount.getResult());
        modelAndView.addObject("likeCount",likeCount.getResult());
        modelAndView.addObject("commentCount",commentCount.getResult());
        modelAndView.addObject("userDesc",userDesc.getResult());
        modelAndView.addObject("userEmail",wblogUserByAccount.getResult().getEmail());
        modelAndView.addObject("userAccount",account);
        modelAndView.addObject("userIcon",headIcon.getResult());
        modelAndView.addObject("personalWblogs",list);
        modelAndView.addObject("page",page);
        modelAndView.addObject("wblogCount",list.size());
        return modelAndView;
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public WblogResult<Integer> registeUser(WblogUserInfo wblogUserInfo){
        logger.info(JSON.toJSONString(wblogUserInfo));
        WblogResult<Integer> result;
        if(StringUtils.isBlank(wblogUserInfo.getEmail())||StringUtils.isBlank(wblogUserInfo.getNickName())
                ||StringUtils.isBlank(wblogUserInfo.getUserAccount())||StringUtils.isBlank(wblogUserInfo.getUserPassword())){
            result = new WblogResult<>();
            result.setCode(400);
            result.setDesc("信息不完整");
            return result;
        }
        String email = wblogUserInfo.getEmail();
        String bizCode = wblogUserInfo.getBizCode();
        String rightCode = RedisUtil.get(BIZCODE_PRE+email);
        if(StringUtils.isBlank(rightCode)){
            result = new WblogResult<>();
            result.setCode(400);
            result.setDesc("验证码过期，请重新发送");
            return result;
        }
        if(!bizCode.equals(rightCode)){
            result = new WblogResult<>();
            result.setCode(400);
            result.setDesc("验证码错误，请重新输入");
            return result;
        }
        wblogUserInfo.setUserRole(1);
        WblogUser wblogUser = new WblogUser();
        BeanUtils.copyProperties(wblogUserInfo,wblogUser);
        result = wblogUserRpo.insertWblogUser(wblogUser);
        return result;
    }

    @RequestMapping(value = "/bizcode")
    @ResponseBody
    public String bizCode(String emailAccount) throws Exception {
        String s = EmailUtil.sendEmail(emailAccount);
        logger.info("------>邮箱地址:"+emailAccount);
        RedisUtil.setEx(BIZCODE_PRE+emailAccount,s,300);
        return s;
    }

    @RequestMapping("/toPersonSet")
    public ModelAndView toPersonSet(String nickName,String blogCount,String fanCount,String likeCount,String commentCount,String userDesc,String email,String account,String userIcon){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView = new ModelAndView("personset");
        modelAndView.addObject("nickName",nickName);
        modelAndView.addObject("blogCount",blogCount);
        modelAndView.addObject("fanCount",fanCount);
        modelAndView.addObject("likeCount",likeCount);
        modelAndView.addObject("commentCount",commentCount);
        modelAndView.addObject("userDesc",userDesc);
        modelAndView.addObject("userEmail",email);
        modelAndView.addObject("userAccount",account);
        modelAndView.addObject("userIcon",userIcon);
        return modelAndView;
    }
    @RequestMapping("/uploadHead")
    @ResponseBody
    public WblogResult<String> uploadHead(MultipartFile file, HttpServletRequest httpRequest,String nickName) throws IOException {
        String name = file.getOriginalFilename();
        String path = httpRequest.getSession().getServletContext().getRealPath("/");
        nickName = new String(nickName.getBytes(),"utf-8");
        logger.info("文件名是:"+path+"昵称是："+nickName);
        String finalPath = uploadFile(file.getBytes(),path+"userIcon",name);
        WblogResult<String> wblogResult = new WblogResult<>();
        wblogResult.setCode(200);
        String result ="userIcon/"+finalPath;
        wblogResult.setResult(result);
        wblogUserAboutRpo.setHeadIcon(nickName,result);
        return wblogResult;
    }

    private String uploadFile(byte[] bytes,String filePath,String fileName) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }
        long l = System.currentTimeMillis();
        String finalPath =filePath+"/"+l+"-"+fileName;
        FileOutputStream fileOutputStream = new FileOutputStream(finalPath);
        fileOutputStream.write(bytes);
        logger.info("----------------->路径"+finalPath);
        fileOutputStream.flush();
        fileOutputStream.close();
        return l+"-"+fileName;
    }
    @RequestMapping("/uploadDesc")
    public ModelAndView uploadDesc(String userDesc,String account,String nickName) throws ExecutionException, InvalidProtocolBufferException {
        wblogUserAboutRpo.setUserDesc(nickName,userDesc);
        logger.info("-----方法进来了");
       return toIndex(nickName,account);
    }
    @RequestMapping("/toSquare")
    public ModelAndView toSquare(String nickName,String blogCount,String fanCount,String likeCount,String commentCount,String userDesc,String email,String account,String userIcon){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView = new ModelAndView("square");
        modelAndView.addObject("nickName",nickName);
        modelAndView.addObject("blogCount",blogCount);
        modelAndView.addObject("fanCount",fanCount);
        modelAndView.addObject("likeCount",likeCount);
        modelAndView.addObject("commentCount",commentCount);
        modelAndView.addObject("userDesc",userDesc);
        modelAndView.addObject("userEmail",email);
        modelAndView.addObject("userAccount",account);
        modelAndView.addObject("userIcon",userIcon);
        return modelAndView;
    }

    @RequestMapping("/toBlog")
    public ModelAndView toBlog(String nickName,String blogCount,String fanCount,String likeCount,String commentCount,String userDesc,String email,String account,String userIcon) throws InvalidProtocolBufferException {
        ModelAndView modelAndView;
        WblogContentProto.FindWblogContentByPageReq.Builder builder = WblogContentProto.FindWblogContentByPageReq.newBuilder();
        builder.setPage(0);
        builder.setSize(4);
        byte[] wblogContentByPage = wblogContentRpc.findWblogContentByPage(builder.build().toByteArray());
        List<WblogContentShow> mid = new ArrayList<>();
        WblogContentProto.FindWblogContentByPageRes res = WblogContentProto.FindWblogContentByPageRes.parseFrom(wblogContentByPage);
        int count = res.getResultCount();
        List<WblogChatoPojo> users = new ArrayList<>();
        Set<String> names = new HashSet<>();
        for(int k = 0 ;k<count;k++){
            WblogContentProto.WblogContentPojo pojo=res.getResult(k);
            mid.add(wblogParser.parseContent(pojo,nickName,0,2));
            if(names.contains(pojo.getCreator())){
                continue;
            }
            WblogContentShow wblogContentShow = mid.get(k);
            names.add(pojo.getCreator());
            WblogChatoPojo wblogChatoPojo = new WblogChatoPojo();
            wblogChatoPojo.setNickName(pojo.getCreator());
            wblogChatoPojo.setUserIcon(wblogContentShow.getUserIcon());
            users.add(wblogChatoPojo);
        }
        modelAndView = new ModelAndView("wblogCenter");
        modelAndView.addObject("wblogs",mid);
        modelAndView.addObject("nickName",nickName);
        modelAndView.addObject("blogCount",blogCount);
        modelAndView.addObject("fanCount",fanCount);
        modelAndView.addObject("likeCount",likeCount);
        modelAndView.addObject("commentCount",commentCount);
        modelAndView.addObject("userDesc",userDesc);
        modelAndView.addObject("userEmail",email);
        modelAndView.addObject("userAccount",account);
        modelAndView.addObject("userIcon",userIcon);
        modelAndView.addObject("currentUsers",users);
        modelAndView.addObject("page",1);
        modelAndView.addObject("wblogCount",mid.size());
        return modelAndView;
    }
    //登出，更改用户状态
    @RequestMapping("/logout")
    public String logOut(String nickName){
        wblogUserAboutRpo.updateStatus(nickName,0);
        return "index";
    }
    @RequestMapping("/findBlog")
    public ModelAndView findBlog(String nickName,String blogCount,String fanCount,String likeCount,String commentCount,String userDesc,String email,String account,String userIcon,int page) throws InvalidProtocolBufferException {
        ModelAndView modelAndView;
        WblogContentProto.FindWblogContentByPageReq.Builder builder = WblogContentProto.FindWblogContentByPageReq.newBuilder();
        builder.setPage((page-1)*4);
        builder.setSize(4);
        byte[] wblogContentByPage = wblogContentRpc.findWblogContentByPage(builder.build().toByteArray());
        List<WblogContentShow> mid = new ArrayList<>();
        WblogContentProto.FindWblogContentByPageRes res = WblogContentProto.FindWblogContentByPageRes.parseFrom(wblogContentByPage);
        int count = res.getResultCount();
        List<WblogChatoPojo> users = new ArrayList<>();
        Set<String> names = new HashSet<>();
        for(int k = 0 ;k<count;k++){
            WblogContentProto.WblogContentPojo pojo=res.getResult(k);
            mid.add(wblogParser.parseContent(pojo,nickName,0,2));
            if(names.contains(pojo.getCreator())){
                continue;
            }
            WblogContentShow wblogContentShow = mid.get(k);
            names.add(pojo.getCreator());
            WblogChatoPojo wblogChatoPojo = new WblogChatoPojo();
            wblogChatoPojo.setNickName(pojo.getCreator());
            wblogChatoPojo.setUserIcon(wblogContentShow.getUserIcon());
            users.add(wblogChatoPojo);
        }
        modelAndView = new ModelAndView("wblogCenter");
        modelAndView.addObject("wblogs",mid);
        modelAndView.addObject("nickName",nickName);
        modelAndView.addObject("blogCount",blogCount);
        modelAndView.addObject("fanCount",fanCount);
        modelAndView.addObject("likeCount",likeCount);
        modelAndView.addObject("commentCount",commentCount);
        modelAndView.addObject("userDesc",userDesc);
        modelAndView.addObject("userEmail",email);
        modelAndView.addObject("userAccount",account);
        modelAndView.addObject("userIcon",userIcon);
        modelAndView.addObject("currentUsers",users);
        modelAndView.addObject("page",page);
        modelAndView.addObject("wblogCount",mid.size());
        return modelAndView;
    }

}
