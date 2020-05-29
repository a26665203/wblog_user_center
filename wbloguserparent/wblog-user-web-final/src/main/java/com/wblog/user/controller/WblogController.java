package com.wblog.user.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.protobuf.InvalidProtocolBufferException;
import com.wblog.content.rpc.WblogCommentRpc;
import com.wblog.content.rpc.WblogContentRpc;
import com.wblog.proto.WblogCommentProto;
import com.wblog.proto.WblogContentProto;
import com.wblog.user.WblogParser;
import com.wblog.user.constant.CommonConstant;
import com.wblog.user.constant.WblogResult;
import com.wblog.user.dto.WblogChatoPojo;
import com.wblog.user.dto.WblogContentShow;
import com.wblog.user.dto.WblogUserInfo;
import com.wblog.user.query.WblogUserQuery;
import com.wblog.user.rpo.WblogExecuteService;
import com.wblog.user.rpo.WblogUserAboutRpo;
import com.wblog.user.rpo.WblogUserRpo;
import com.wblog.user.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Controller
public class WblogController {
    @Autowired
    WblogParser wblogParser;
    @Resource
    WblogContentRpc wblogContentRpc;
    @Autowired
    WblogUserAboutRpo wblogUserAboutRpo;
    @Autowired
    WblogExecuteService wblogExecuteService;
    @Autowired
    WblogUserRpo wblogUserRpo;
    @Resource
    WblogCommentRpc wblogCommentRpc;
    Logger logger = LoggerFactory.getLogger(WblogController.class);
    @RequestMapping("/sendWblog")
    @ResponseBody
    public String uploadWblog(MultipartFile wblogImg, String wblogContent, HttpServletRequest httpRequest, String nickName) throws IOException {
        String lastPath="";
        if(wblogImg!=null){
            String path = httpRequest.getSession().getServletContext().getRealPath("/");
            String name = wblogImg.getOriginalFilename();
            name = new String(name.getBytes("ISO-8859-1"),"utf-8");
            String finalPath = uploadFile(wblogImg.getBytes(),path+"wblogContent",name);
            lastPath = "wblogContent/"+finalPath;
        }
        nickName = new String(nickName.getBytes("ISO-8859-1"),"utf-8");
        wblogContent = new String(wblogContent.getBytes("ISO-8859-1"),"utf-8");
        WblogContentProto.WblogContentPojo.Builder builder = WblogContentProto.WblogContentPojo.newBuilder();
        WblogContentProto.AddWblogContentReq.Builder reqBuilder = WblogContentProto.AddWblogContentReq.newBuilder();
        builder.setContent(wblogContent);
        builder.setCreator(nickName);
        builder.setImageUrl(lastPath);;
        builder.setId(IDUtils.getContentID());
        reqBuilder.setPojo(builder.build());
        wblogContentRpc.addWblogContent(reqBuilder.build().toByteArray());
        //存储成功才将微博数加1；
        wblogUserAboutRpo.incrBlog(nickName);
        logger.info("执行一次了呀");
        return "success";
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
    private ModelAndView updateData(String nickName,String url){
        ModelAndView modelAndView = new ModelAndView(url);
        WblogResult<Integer> fanCount=wblogUserAboutRpo.getFanCount(nickName);
        WblogResult<Integer> blogCount = wblogUserAboutRpo.getBlogCount(nickName);
        WblogResult<Integer> likeCount=wblogUserAboutRpo.getLikeCount(nickName);
        WblogResult<Integer> commentCount=wblogUserAboutRpo.getCommentCount(nickName);
        WblogResult<String> userDesc = wblogUserAboutRpo.getUserDesc(nickName);
        WblogResult<String> headIcon = wblogUserAboutRpo.getHeadIcon(nickName);
        modelAndView.addObject("nickName",nickName);
        modelAndView.addObject("blogCount",blogCount.getResult());
        modelAndView.addObject("fanCount",fanCount.getResult());
        modelAndView.addObject("likeCount",likeCount.getResult());
        modelAndView.addObject("commentCount",commentCount.getResult());
        modelAndView.addObject("userDesc",userDesc.getResult());
        modelAndView.addObject("userIcon",headIcon.getResult());
        return modelAndView;
    }
    @RequestMapping("/updateWblog")
    public ModelAndView updateView(String nickName,String account) throws InvalidProtocolBufferException {
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
        ModelAndView modelAndView = updateData(nickName, "wblogCenter");
        modelAndView.addObject("wblogs",mid);
        modelAndView.addObject("userAccount",account);
        modelAndView.addObject("currentUsers",users);
        modelAndView.addObject("page",1);
        modelAndView.addObject("wblogCount",mid.size());

        return modelAndView;
    }
    @RequestMapping("/loadWblog")
    @ResponseBody
    public WblogResult<List<WblogContentShow>>listWblog(String nickName) throws InvalidProtocolBufferException {
        WblogResult<List<WblogContentShow>> result = new WblogResult<>();
        WblogContentProto.FindWblogContentByPageReq.Builder builder = WblogContentProto.FindWblogContentByPageReq.newBuilder();
        builder.setPage(0);
        builder.setSize(10);
        byte[] wblogContentByPage = wblogContentRpc.findWblogContentByPage(builder.build().toByteArray());
        List<WblogContentShow> mid = new ArrayList<>();
        WblogContentProto.FindWblogContentByPageRes res = WblogContentProto.FindWblogContentByPageRes.parseFrom(wblogContentByPage);
        int count = res.getResultCount();
        for(int k = 0 ;k<count;k++){
            mid.add(wblogParser.parseContent(res.getResult(k),nickName,0,2));
        }
        result.setResult(mid);
        return result;
    }
    @RequestMapping("/likeWblog")
    @ResponseBody
    public WblogResult<String> likeBlog(String nickName,String blogId){
        WblogResult<String> result = wblogExecuteService.likeWblog(nickName, blogId);
        return result;
    }
    @RequestMapping("/unLikeWblog")
    @ResponseBody
    public WblogResult<String> unLikeBlog(String nickName,String blogId){
        return wblogExecuteService.unLikeBlog(nickName,blogId);
    }

    @RequestMapping("/commentWblog")
    @ResponseBody
    public WblogResult<Boolean> commentBlog(String comment,String blogId,String commenter) {
        WblogResult<Boolean> result = new WblogResult<>();
        try {
            WblogCommentProto.AddWblogCommentReq.Builder builder = WblogCommentProto.AddWblogCommentReq.newBuilder();
            WblogCommentProto.CommentContentPojo.Builder builder1 = WblogCommentProto.CommentContentPojo.newBuilder();
            builder1.setId(IDUtils.getCommentID());
            builder1.setCommentContent(comment);
            builder1.setCommenter(commenter);
            builder1.setBlogId(blogId);
            builder.setPojo(builder1);
            byte[] bytes = wblogCommentRpc.addWblogComment(builder.build().toByteArray());
            WblogCommentProto.AddWblogCommentRes res = WblogCommentProto.AddWblogCommentRes.parseFrom(bytes);
            RedisUtil.incr(CommonConstant.WBLOG_MESSAGE_COMMENT+blogId);
            //评论数加一;
            wblogUserAboutRpo.incrComment(commenter);
            result.setCode(200);
            result.setResult(true);
        }catch (Exception e){
            logger.error("WblogController.commentBlog------->error:{}",e);
            result.setCode(400);
            result.setResult(false);
        }
        return result;
    }
    @RequestMapping("/likeComment")
    @ResponseBody
    public WblogResult<Boolean> likeComment(String nickName,String commentId){
        return wblogExecuteService.likeComment(nickName,commentId);
    }
    @RequestMapping("/unLikeComment")
    @ResponseBody
    public WblogResult<Boolean> unLikeComment(String nickName,String commentId){
        return wblogExecuteService.unLikeComment(nickName,commentId);
    }
    @RequestMapping("/deleteComment")
    @ResponseBody
    public WblogResult<Boolean> deleteComment(String nickName,String blogId,String commentId) throws InvalidProtocolBufferException {
        WblogResult<Boolean> result = new WblogResult<>();
        WblogCommentProto.DeleteWblogCommentReq.Builder builder = WblogCommentProto.DeleteWblogCommentReq.newBuilder();
        builder.setId(commentId);
        builder.setNickName(nickName);
        wblogCommentRpc.deleteWblogComment(builder.build().toByteArray());
        wblogUserAboutRpo.decrComment(nickName);
        RedisUtil.decr(CommonConstant.WBLOG_MESSAGE_COMMENT+blogId);
        result.setCode(200);
        result.setResult(true);
        return result;
    }
    @RequestMapping("/personalWlog")
    @ResponseBody
    public WblogResult<List<WblogContentShow>> personWblog(String nickName,Integer page){
        WblogResult<List<WblogContentShow>> result = new WblogResult<>();
        try {
            WblogContentProto.FindWblogContentByPageReq.Builder builder = WblogContentProto.FindWblogContentByPageReq.newBuilder();
            builder.setCreator(nickName);
            builder.setPage(page);
            builder.setSize(4);
            byte[] wblogContentByPage = wblogContentRpc.findWblogContentByPage(builder.build().toByteArray());
            WblogContentProto.FindWblogContentByPageRes res = WblogContentProto.FindWblogContentByPageRes.parseFrom(wblogContentByPage);
            int count = res.getResultCount();
            List<WblogContentShow> list = new ArrayList<>();
            for(int k =0 ;k<count;k++){
                list.add(wblogParser.parseWblogSimple(res.getResult(k),nickName));
            }
            result.setResult(list);
            result.setCode(200);
        }catch (Exception e){
            logger.error("WblogController.personWblog------>"+e);
            result.setCode(400);
            result.setDesc(e.getMessage());
        }
        return result;
    }
    @RequestMapping("/deleteWblog")
    public ModelAndView deleteWblog(String wblogId,String nickName,String account) throws InvalidProtocolBufferException, ExecutionException {
        WblogContentProto.DeleteWblogContentReq.Builder builder1 = WblogContentProto.DeleteWblogContentReq.newBuilder();
        builder1.setId(wblogId);
        builder1.setNickName(nickName);
        wblogContentRpc.deleteWblog(builder1.build().toByteArray());
        wblogUserAboutRpo.decrBlog(nickName);
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
        builder.setPage(1);
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
}
