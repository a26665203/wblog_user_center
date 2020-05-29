<%--
  Created by IntelliJ IDEA.
  User: hezijian
  Date: 2020/4/20
  Time: 6:12 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="format-detection" content="telephone=no" />
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel='stylesheet' id='lmlblog-css'  href='css/lmlblog.css' type='text/css' media='all' />
    <link rel="stylesheet" type="text/css" href="font_Icon/iconfont.css">
    <link rel="stylesheet" type="text/css" href="css/chat.css">
    <title>微博发布</title>
    <link rel="stylesheet" href="css/commentstyle.css">
    <link rel="stylesheet" href="css/comment.css">

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/wblogstyle.css">
    <link rel="stylesheet" type="text/css" href="fonts-like/font-awesome-4.5.0/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="css/demo.css">
    <link rel="stylesheet" type="text/css" href="css/icons.css" />

</head>
<body>
<nav class="navbar  navbar-fixed-top" role="navigation" style="background: #e0620d ;padding-top: 3px;height:50px;">
    <div class="container-fluid" style="background: #fff;">
        <div class="navbar-header ">
            <span class="navbar-brand " href="#">WBLOG</span>

            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#my-navbar-collapse">
                <span class="sr-only">切换导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <form class="navbar-form navbar-left" role="search">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="#热门话题#">
                <i class="glyphicon glyphicon-search btn_search" ></i>
                <!--  <button type="submit" class="btn btn-default">提交</button> -->
            </div>

        </form>

        <div class="collapse navbar-collapse"  id="my-navbar-collapse">
            <input type="hidden" id="currentNickName" value="${nickName}">
            <ul class="nav navbar-nav navbar-right" >
                <li ><a href="./toIndex?nickName=${nickName}&account=${userAccount}"><i class="glyphicon glyphicon-user"></i>&nbsp;&nbsp;${nickName}</a></li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        设置 <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="./logout">注销</a></li>
                        <li><a href="#">登陆</a></li>

                    </ul>
                </li>
            </ul>

        </div>


    </div>
    <hr style="margin: 0;padding: 0;color:#222;width: 100%">
</nav>

<div class="container container_bg"  style="background: #dd6572;
	background-color:rgba(255,237,242,0.5);
	padding-bottom: 30px; ">
    <div class="row">
        <div class="col-sm-2"></div>

        <div class="col-sm-6 col-xs-12 my_edit" >
            <div class="row" id="edit_form" >
                <span class="pull-left" style="margin:15px;">编写新鲜事</span>
                <span class="tips pull-right" style="margin:15px;"></span>
                <form role="form" style="margin-top: 50px;" enctype="multipart/form-data" action="./sendWblog" method="post">
                    <div class="form-group">
                        <div class="col-sm-12">

                            <div contentEditable="true" id="content" class="form-control " name="wblogContent"></div>
                        </div>
                        <div class="col-sm-12" style="margin-top: 12px;">
                            <span class="emoji" >表情</span>

                            <span class="pic" >图片	</span>
                            <span>
									<input type="file" name="wblogImg" id="wblogImg" class="select_Img" style="display: none" onchange="show(this)">
                                    <input type="hidden" id="hideUser" value="${nickName}"/>
                                    <input type="hidden" id="hideAccount" value="${userAccount}">
									<img class="preview" src="" id="previewImg">
								</span>


                            <div class="myEmoji" >
                                <ul id="myTab" class="nav nav-tabs">
                                    <li class="active">
                                        <a href="#set" data-toggle="tab">
                                            预设
                                        </a>
                                    </li>
                                    <li><a href="#hot" data-toggle="tab">热门</a></li>

                                </ul>
                                <div id="myTabContent" class="tab-content">
                                    <div class="tab-pane fade in active" id="set">
                                        <div class="emoji_1"></div>

                                    </div>
                                    <div class="tab-pane fade" id="hot">
                                        <div class="emoji_2"></div>
                                    </div>

                                </div>
                            </div>
                            <!-- <span> <input type="file" id="selectImg" value=""></input> </span> -->
                            <input type="button" id="send" class="btn btn-default pull-right disabled" onclick="sendBlog()" value="发布"/>
                        </div>
                    </div>
                </form>
            </div>


            <div class="row item_msg" >
                <c:forEach items="${wblogs}" var="wblogShow">
                <div class="col-sm-12 col-xs-12 message" >
                    <img src="${wblogShow.userIcon}" class="col-sm-2 col-xs-2" style="border-radius: 50%">
                    <div class="col-sm-10 col-xs-10">
                        <span style="font-weight: bold;">${wblogShow.creator}</span>
                        <br>
                        <small class="date" style="color:#999">${wblogShow.sendDate}</small>
                        <div class="msg_content">${wblogShow.content}
                            <c:if  test="${(wblogShow.imageUrl!=NULL && wblogShow.imageUrl!='')}">
                                <img class="mypic" src="${wblogShow.imageUrl}" >
                            </c:if>
                        </div>
                    </div>
                    <br>
                    <br>
                    <div class="likeNode" >
                        <button class="icobutton icobutton--heart">
                            <input type="hidden" class="likeOrNot" value="${wblogShow.likeOrNot}">
                            <input type="hidden" class="myBlogId" value="${wblogShow.id}">
                            <span class="fa fa-heart"></span><span class="icobutton__text icobutton__text--side">${wblogShow.likeCount}</span></button>
                    </div>
                    <div class="commentAll">
                        <!--评论区域 begin-->
                        <div class="reviewArea clearfix">
                            <textarea class="content comment-input" placeholder="Please enter a comment&hellip;" onkeyup="keyUP(this)"></textarea>
                            <a href="javascript:;" class="plBtn">评论<input type="hidden" class="hideBlogId" value="${wblogShow.id}"></a>
                        </div>
                        <!--评论区域 end-->
                        <!--回复区域 begin-->
                        <div class="comment-show">
                            <c:forEach items="${wblogShow.comments}" var="comment">
                            <div class="comment-show-con clearfix mycomment">
                                <div class="comment-show-con-img pull-left"><img src="${comment.userIcon}" alt=""></div>
                                <div class="comment-show-con-list pull-left clearfix comment-start">
                                    <div class="pl-text clearfix">
                                        <a href="#" class="comment-size-name">${comment.commenter} : </a>
                                        <span class="my-pl-con">&nbsp;${comment.commentContent}</span>
                                    </div>
                                    <div class="date-dz comment-end">
                                        <span class="date-dz-left pull-left comment-time">${comment.showDate}</span>
                                        <div class="date-dz-right pull-right comment-pl-block comment-end-mid">
                                            <c:if test='${comment.deleteOrNot eq "true"}'>
                                            <a href="javascript:;" class="removeBlock"><input type="hidden" class="hideBlogId" value="${wblogShow.id}">
                                                <input type="hidden" class="hideCommentId" value="${comment.id}">
                                                删除</a>
                                            </c:if>
                                            <a href="javascript:;" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a>
                                            <span class="pull-left date-dz-line">|</span>
                                            <a href="javascript:;" class="date-dz-z pull-left comment-end-dz"><i class="date-dz-z-click-red"></i><input type="hidden" class="hideCommentId" value="${comment.id}">赞 (<i class="z-num">${comment.likeCount}</i>)</a>
                                            <input type="hidden" class="likeOrNot" value="${comment.likeOrNot}">
                                        </div>
                                    </div>
                                    <div class="hf-list-con"></div>
                                </div>
                            </div>
                            </c:forEach>
                        </div>
                        <!--回复区域 end-->
                    </div>

                </div>
                </c:forEach>
                </div>


            </div>

        </div>

    </div>


</div>
<div class="lmlblog-pager">
    <c:if test="${page !=1}">
        <a class="lmlblog-pager-left" href="./findBlog?nickName=${nickName}&account=${userAccount}&page=${page-1}&blogCount=${blogCount}&fanCount=${fanCount}&likeCount=${likeCount}&commentCount=${commentCount}&userDesc=${userDesc}&email=${userEmail}&userIcon=${userIcon}">上一页</a>
    </c:if>
    <div class="lmlblog-pager-center layui-form">
        <input type="hidden" value="${page}">
        第 ${page} 页</div>
    <c:if test="${wblogCount==4}">
        <a class="lmlblog-pager-right" href="./findBlog?nickName=${nickName}&account=${userAccount}&page=${page+1}&blogCount=${blogCount}&fanCount=${fanCount}&likeCount=${likeCount}&commentCount=${commentCount}&userDesc=${userDesc}&email=${userEmail}&userIcon=${userIcon}">下一页</a>
    </c:if>
</div>
</div>
<div class="chatContainer">
    <div class="chatBtn">
        <i class="iconfont icon-xiaoxi1"></i>
    </div>
    <div class="chat-message-num"></div>
    <div class="chatBox" ref="chatBox">
        <input id="onlineOrNot" type="hidden" value="0">
        <div class="chatBox-head">
            <div class="chatBox-head-one">
                聊天
                <a id="chat-online">设置在线</a>
                <div class="chat-close" style="margin: 10px 10px 0 0;font-size: 14px">关闭</div>
            </div>
            <div class="chatBox-head-two">
                <div class="chat-return">返回</div>
                <div class="chat-people">
                    <div class="ChatInfoHead">
                        <img src="" alt="头像"/>
                    </div>
                    <div class="ChatInfoName">这是用户的名字，看看名字到底能有多长</div>
                </div>
                <div class="chat-close">关闭</div>
            </div>
        </div>
        <div class="chatBox-info"  >
            <div class="chatBox-list" ref="chatBoxlist" id="chatBoxList">
                <c:forEach items="${currentUsers}" var="oneUser">
                <div class="chat-list-people">
                    <div><img class="chatIcon" src="${oneUser.userIcon}" alt="头像"/></div>
                    <div class="chat-name">
                        <p class="chatNickName">${oneUser.nickName}</p>
                    </div>
                    <input class="hidden-msg" type="hidden">
                    <div class="message-num"></div>
                </div>
                </c:forEach>
            </div>
            <div class="chatBox-kuang" ref="chatBoxkuang">
                <input type="hidden" id="currentChatName">
                <input type="hidden" id="currentChatIcon">
                <div class="chatBox-content">
                    <div class="chatBox-content-demo" id="chatBox-content-demo">


                    </div>
                </div>
                <div class="chatBox-send">
                    <div class="div-textarea" contenteditable="true"></div>
                    <div>
                        <button id="chat-biaoqing" class="btn-default-styles">
                            <i class="iconfont icon-biaoqing"></i>
                        </button>
                        <label id="chat-tuxiang" title="发送图片" for="inputImage" class="btn-default-styles">
                            <input type="file" onchange="selectImg(this)" accept="image/jpg,image/jpeg,image/png"
                                   name="file" id="inputImage" class="hidden">
                            <i class="iconfont icon-tuxiang"></i>
                        </label>
                        <button id="chat-fasong" class="btn-default-styles"><i class="iconfont icon-fasong"></i>
                        </button>
                    </div>
                    <div class="biaoqing-photo">
                        <ul>
                            <li><span class="emoji-picker-image" style="background-position: -9px -18px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -40px -18px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -71px -18px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -102px -18px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -133px -18px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -164px -18px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -9px -52px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -40px -52px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -71px -52px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -102px -52px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -133px -52px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -164px -52px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -9px -86px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -40px -86px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -71px -86px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -102px -86px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -133px -86px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -164px -86px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -9px -120px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -40px -120px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -71px -120px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -102px -120px;"></span>
                            </li>
                            <li><span class="emoji-picker-image" style="background-position: -133px -120px;"></span>
                            </li>
                            <li><span class="emoji-picker-image" style="background-position: -164px -120px;"></span>
                            </li>
                            <li><span class="emoji-picker-image" style="background-position: -9px -154px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -40px -154px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -71px -154px;"></span></li>
                            <li><span class="emoji-picker-image" style="background-position: -102px -154px;"></span>
                            </li>
                            <li><span class="emoji-picker-image" style="background-position: -133px -154px;"></span>
                            </li>
                            <li><span class="emoji-picker-image" style="background-position: -164px -154px;"></span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="js/jquery-3.1.0.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/wblog.js"></script>
<script type="text/javascript">
    $(function(){

        $("#content").keyup(function(){

            //判断输入的字符串长度
            var content_len = $("#content").text().replace(/\s/g,"").length;

            $(".tips").text("已经输入"+content_len+"个字");


            if(content_len==0){
                // alert(content);
                $(".tips").text("");
                $("#send").addClass("disabled");
                return false;
            }else{
                $("#send").removeClass("disabled");
            }
        });


        $(".pic").click(function(){

            $(".select_Img").click();


        })

        // 	function confirm(){

        // 		var r= new FileReader();
        // f=$(".select_Img").files[0];
        // r.readAsDataURL(f);
        // r.onload=function(e) {
        // 	$(".preview").src=this.result;

        // };
        // 	}

        //点击按钮发送内容
        $("#send").click(function(){

            // var myDate = new Date();

            //   var min = myDate.getMinutes();

            //   var time = min-(min-1);

            //   //alert(time);

            var content=$("#content").html();

            //判断选择的是否是图片格式
            var imgPath = $(".imgPath").text();
            var start  = imgPath.lastIndexOf(".");
            var postfix = imgPath.substring(start,imgPath.length).toUpperCase();


            if(imgPath!=""){

                if(postfix!=".PNG"&&postfix!=".JPG"&&postfix!=".GIF"&&postfix!=".JPEG"){
                    alert("图片格式需为png,gif,jpeg,jpg格式");
                }else{
                    $(".item_msg").append("<div class='col-sm-12 col-xs-12 message' > <img src='img/icon.png' class='col-sm-2 col-xs-2' style='border-radius: 50%'><div class='col-sm-10 col-xs-10'><span style='font-weight: bold;''>Jack.C</span> <br><small class='date' style='color:#999'>刚刚</small><div class='msg_content'>"+content+"<img class='mypic' onerror='this.src='img/bg_1.jpg' src='file:///"+imgPath+"' ></div></div></div>");
                }
            }else{
                $(".item_msg").append("<div class='col-sm-12 col-xs-12 message' > <img src='img/icon.png' class='col-sm-2 col-xs-2' style='border-radius: 50%'><div class='col-sm-10 col-xs-10'><span style='font-weight: bold;''>Jack.C</span> <br><small class='date' style='color:#999'>刚刚</small><div class='msg_content'>"+content+"</div></div></div>");
            }

        });

        //添加表情包1
        for (var i = 1; i < 60; i++) {

            $(".emoji_1").append("<img src='img/f"+i+".png' style='width:35px;height:35px' >");
        }
        //添加表情包2
        for (var i = 1; i < 61; i++) {

            $(".emoji_2").append("<img src='img/h"+i+".png' style='width:35px;height:35px' >");
        }


        $(".emoji").click(function(){

            $(".myEmoji").show();

            //点击空白处隐藏弹出层
            $(document).click(function (e) {

                if (!$("#edit_form").is(e.target) && $("#edit_form").has(e.target).length === 0) {

                    $(".myEmoji").hide();
                }
            });


        });

        //将表情添加到输入框
        $(".myEmoji img").each(function(){
            $(this).click(function(){
                var url = $(this)[0].src;

                $('#content').append("<img src='"+url+"' style='width:25px;height:25px' >");

                $("#send").removeClass("disabled");
            })
        })

        //放大或缩小预览图片
        $(".mypic").click(function(){
            var oWidth=$(this).width(); //取得图片的实际宽度
            var oHeight=$(this).height(); //取得图片的实际高度

            if($(this).height()!=200){
                $(this).height(200);
            }else{
                $(this).height(oHeight + 200/oWidth*oHeight);

            }

        })

    })
</script>

<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
    <p>适用浏览器：360、FireFox、Chrome、Safari、Opera、傲游、搜狗、世界之窗. 不支持IE8及以下浏览器。</p>
    <p>来源：<a href="http://sc.chinaz.com/" target="_blank">站长素材</a></p>
</div>
<script src="js/mo.min.js"></script>
<script src="js/demo.js"></script>
<script type="text/javascript" src="js/jquery-1.12.0.min.js"></script>
<script type="text/javascript" src="js/jquery.flexText.js"></script>
<!--textarea高度自适应-->
<script type="text/javascript">
    $(function () {
        $('.content').flexText();
    });
</script>
<!--textarea限制字数-->
<script type="text/javascript">
    function keyUP(t){
        var len = $(t).val().length;
        if(len > 139){
            $(t).val($(t).val().substring(0,140));
        }
    }
</script>
<!--点击评论创建评论条-->
<script type="text/javascript">
    $('.commentAll').on('click','.plBtn',function(){
        var hideBlogId = $(this).find('.hideBlogId').val();
        var myDate = new Date();
        //获取当前年
        var year=myDate.getFullYear();
        //获取当前月
        var month=myDate.getMonth()+1;
        //获取当前日
        var date=myDate.getDate();
        var h=myDate.getHours();       //获取当前小时数(0-23)
        var m=myDate.getMinutes();     //获取当前分钟数(0-59)
        if(m<10) m = '0' + m;
        var s=myDate.getSeconds();
        if(s<10) s = '0' + s;
        var now=year+'-'+month+"-"+date+" "+h+':'+m+":"+s;
        //获取输入内容
        // var oSize = $(this).siblings('.flex-text-wrap').find('.comment-input').val();
        var oSize =$(this).parents('.reviewArea').find('.comment-input').val()
        console.log(oSize);
        //动态创建评论模块
        oHtml = '<div class="comment-show-con clearfix"><div class="comment-show-con-img pull-left"><img src="${userIcon}" alt=""></div> <div class="comment-show-con-list pull-left clearfix"><div class="pl-text clearfix"> <a href="#" class="comment-size-name">${nickName} : </a> <span class="my-pl-con">&nbsp;'+ oSize +'</span> </div> <div class="date-dz"> <span class="date-dz-left pull-left comment-time">'+now+'</span> <div class="date-dz-right pull-right comment-pl-block"><a href="javascript:;" class="removeBlock">删除</a> <a href="javascript:;" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a> <span class="pull-left date-dz-line">|</span> <a href="javascript:;" class="date-dz-z pull-left"><i class="date-dz-z-click-red"></i>赞 (<i class="z-num">0</i>)</a> </div> </div><div class="hf-list-con"></div></div> </div>';
        if(oSize.replace(/(^\s*)|(\s*$)/g, "") != ''){
            $(this).parents('.reviewArea ').siblings('.comment-show').prepend(oHtml);
            $(this).siblings('.flex-text-wrap').find('.comment-input').prop('value','').siblings('pre').find('span').text('');
        }
        var hideNickName = $("#hideUser").val();
        var commentBlogId = $("#myBlogId").val();
        $.get("./commentWblog",{comment : oSize,blogId:hideBlogId,commenter:hideNickName},function (result) {

        })


    });
</script>
<!--点击回复动态创建回复块-->
<script type="text/javascript">
    $('.comment-show').on('click','.pl-hf',function(){
        //获取回复人的名字
        var fhName = $(this).parents('.date-dz-right').parents('.date-dz').siblings('.pl-text').find('.comment-size-name').html();
        //回复@
        var fhN = '回复@'+fhName;
        //var oInput = $(this).parents('.date-dz-right').parents('.date-dz').siblings('.hf-con');
        var fhHtml = '<div class="hf-con pull-left"> <textarea class="content comment-input hf-input" placeholder="" onkeyup="keyUP(this)"></textarea> <a href="javascript:;" class="hf-pl">评论</a></div>';
        //显示回复
        if($(this).is('.hf-con-block')){
            $(this).parents('.date-dz-right').parents('.date-dz').append(fhHtml);
            $(this).removeClass('hf-con-block');
            $('.content').flexText();
            $(this).parents('.date-dz-right').siblings('.hf-con').find('.pre').css('padding','6px 15px');
            //console.log($(this).parents('.date-dz-right').siblings('.hf-con').find('.pre'))
            //input框自动聚焦
            $(this).parents('.date-dz-right').siblings('.hf-con').find('.hf-input').val('').focus().val(fhN);
        }else {
            $(this).addClass('hf-con-block');
            $(this).parents('.date-dz-right').siblings('.hf-con').remove();
        }
    });
</script>
<!--评论回复块创建-->
<script type="text/javascript">
    $('.comment-show').on('click','.hf-pl',function(){
        var oThis = $(this);
        var myDate = new Date();
        //获取当前年
        var year=myDate.getFullYear();
        //获取当前月
        var month=myDate.getMonth()+1;
        //获取当前日
        var date=myDate.getDate();
        var h=myDate.getHours();       //获取当前小时数(0-23)
        var m=myDate.getMinutes();     //获取当前分钟数(0-59)
        if(m<10) m = '0' + m;
        var s=myDate.getSeconds();
        if(s<10) s = '0' + s;
        var now=year+'-'+month+"-"+date+" "+h+':'+m+":"+s;
        //获取输入内容
        var oHfVal = $(this).siblings('.flex-text-wrap').find('.hf-input').val();
        console.log(oHfVal)
        var oHfName = $(this).parents('.hf-con').parents('.date-dz').siblings('.pl-text').find('.comment-size-name').html();
        var oAllVal = '回复@'+oHfName;
        if(oHfVal.replace(/^ +| +$/g,'') == '' || oHfVal == oAllVal){

        }else {
            $.getJSON("json/pl.json",function(data){
                var oAt = '';
                var oHf = '';
                $.each(data,function(n,v){
                    delete v.hfContent;
                    delete v.atName;
                    var arr;
                    var ohfNameArr;
                    if(oHfVal.indexOf("@") == -1){
                        data['atName'] = '';
                        data['hfContent'] = oHfVal;
                    }else {
                        arr = oHfVal.split(':');
                        ohfNameArr = arr[0].split('@');
                        data['hfContent'] = arr[1];
                        data['atName'] = ohfNameArr[1];
                    }

                    if(data.atName == ''){
                        oAt = data.hfContent;
                    }else {
                        oAt = '回复<a href="#" class="atName">@'+data.atName+'</a> : '+data.hfContent;
                    }
                    oHf = data.hfName;
                });

                var oHtml = '<div class="all-pl-con"><div class="pl-text hfpl-text clearfix"><a href="#" class="comment-size-name">我的名字 : </a><span class="my-pl-con">'+oAt+'</span></div><div class="date-dz"> <span class="date-dz-left pull-left comment-time">'+now+'</span> <div class="date-dz-right pull-right comment-pl-block"> <a href="javascript:;" class="removeBlock">删除</a> <a href="javascript:;" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a> <span class="pull-left date-dz-line">|</span> <a href="javascript:;" class="date-dz-z pull-left"><i class="date-dz-z-click-red"></i>赞 (<i class="z-num">666</i>)</a> </div> </div></div>';
                oThis.parents('.hf-con').parents('.comment-show-con-list').find('.hf-list-con').css('display','block').prepend(oHtml) && oThis.parents('.hf-con').siblings('.date-dz-right').find('.pl-hf').addClass('hf-con-block') && oThis.parents('.hf-con').remove();
            });
        }
    });
</script>
<!--删除评论块-->
<script type="text/javascript">
    $('.commentAll').on('click','.removeBlock',function(){
        var blogId=$(this).find('.hideBlogId').val();
        var hideNickName = $("#hideUser").val();
        var hideCommentId=$(this).find('.hideCommentId').val();
        var oT = $(this).parents('.date-dz-right').parents('.date-dz').parents('.all-pl-con');
        if(oT.siblings('.all-pl-con').length >= 1){
            oT.remove();
        }else {
            $(this).parents('.date-dz-right').parents('.date-dz').parents('.all-pl-con').parents('.hf-list-con').css('display','none')
            oT.remove();
        }
        $(this).parents('.date-dz-right').parents('.date-dz').parents('.comment-show-con-list').parents('.comment-show-con').remove();
        $.get("./deleteComment",{nickName : hideNickName,commentId:hideCommentId,blogId:blogId},function (result) {

        })
    })
</script>
<!--点赞-->
<script type="text/javascript">
    var hideNickName = $("#hideUser").val();
    $('.comment-show').on('click','.date-dz-z',function(){
        var zNum = $(this).find('.z-num').html();
        var commentId = $(this).find('.hideCommentId').val();
        if($(this).is('.date-dz-z-click')){
            zNum--;
            $(this).removeClass('date-dz-z-click red');
            $(this).find('.z-num').html(zNum);
            $(this).find('.date-dz-z-click-red').removeClass('red');
            $.get("./unLikeComment",{nickName : hideNickName,commentId:commentId},function (result) {
            })
        }else {
            zNum++;
            $(this).addClass('date-dz-z-click');
            $(this).find('.z-num').html(zNum);
            $(this).find('.date-dz-z-click-red').addClass('red');
            $.get("./likeComment",{nickName : hideNickName,commentId:commentId},function (result) {

            })
        }
    })
</script>
<script type="text/javascript">
    $().ready(function () {
        var items = [].slice.call(document.querySelectorAll('.mycomment'));
        for(var i = 0;i<items.length;i++){
            var el14 = items[i].querySelector('div.comment-end')
            var comment_end_mid = el14.querySelector('div.comment-end-mid');
            var dz = comment_end_mid.querySelector('a.comment-end-dz');
            var hideVal = comment_end_mid.querySelector('input.likeOrNot');
            var lastVal = hideVal.getAttribute("value");
            if(lastVal == "true"){
                dz.setAttribute("class","date-dz-z date-dz-z-click");
                var showDz = dz.querySelector("i.date-dz-z-click-red");
                showDz.setAttribute("class","date-dz-z-click-red red");
            }
        }
    })
</script>
<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script>
        var ipaddress="127.0.0.1";
//新建socket对象
        window.socket = new WebSocket("ws://"+ipaddress+":8085/ws");
//监听netty服务器消息并打印到页面上
        socket.onmessage = function(event) {
            //如果是停留在当前页
            var curName=$("#currentChatName").val();
            var chatItems = [].slice.call(document.querySelectorAll('.chat-list-people'));
            var datas = event.data.split("@#");
            var msg = datas[1];
            var msgDate =  msg.split("--->");
            var senderMsg = datas[0].split(",");
            var senderNickName = senderMsg[0];
            if (msgDate[0] == "该用户不在线") {
                $(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
                    "<div class=\"author-name\"><small class=\"chat-date\">"+msgDate[1]+"</small> </div> " +
                    "<div class=\"left\">"+
                    "<div class=\"chat-avatars\"><img src=\""+senderMsg[1]+"\" alt=\"头像\" /></div>" + "<div class=\"chat-message\"> " + msgDate[0] + " </div> "+
                " </div> </div>");
            } else {
                if(curName == senderNickName){
                    $(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
                        "<div class=\"author-name\"><small class=\"chat-date\">"+msgDate[1]+"</small> </div> " +
                        "<div class=\"left\">"+
                        "<div class=\"chat-avatars\"><img src=\""+senderMsg[1]+"\" alt=\"头像\" /></div>" + "<div class=\"chat-message\"> " + msgDate[0] + " </div> "+
                        " </div> </div>");
                }else {
                    var judgeExit = false;
                    for (var i = 0; i < chatItems.length; i++) {
                        var mid = chatItems[i].querySelector('div.chat-name');
                        var chatNick = mid.querySelector('p.chatNickName')
                        if (chatNick.innerHTML == senderNickName) {
                            var messageNum = chatItems[i].querySelector('div.message-num');
                            var currentMsg = chatItems[i].querySelector('input.hidden-msg');
                            if (messageNum.innerHTML == "") {
                                messageNum.innerHTML = "1";
                            } else {
                                messageNum.innerHTML = Number(messageNum.innerHTML) + 1;
                            }
                            var midValue = currentMsg.getAttribute("value") + "#@#" + msg;
                            currentMsg.setAttribute("value", midValue);
                            judgeExit = true;
                        }
                    }
                    if (!judgeExit) {
                        $("#chatBoxList").append("<div class=\"chat-list-people\">" +
                            "<div><img src=\"" + senderMsg[1] + "\"" + "alt=\"头像\"/></div>" +
                            "<div class=\"chat-name\">" + "<p class=\"chatNickName\">" + senderNickName + "</p>" +
                            "</div>" + "<input class=\"hidden-msg\" type=\"hidden\" value=\"" + msg + "\">" +
                            "<div class=\"message-num\">1</div>" + "</div>"
                        )
                    }
                }
            }
        }
    screenFuc();
    function screenFuc() {
        var topHeight = $(".chatBox-head").innerHeight();//聊天头部高度
        //屏幕小于768px时候,布局change
        var winWidth = $(window).innerWidth();
        $(".chatBox").toggle(10);
        if (winWidth <= 768) {
            var totalHeight = $(window).height(); //页面整体高度
            $(".chatBox-info").css("height", totalHeight - topHeight);
            var infoHeight = $(".chatBox-info").innerHeight();//聊天头部以下高度
            //中间内容高度
            $(".chatBox-content").css("height", infoHeight - 46);
            $(".chatBox-content-demo").css("height", infoHeight - 46);

            $(".chatBox-list").css("height", totalHeight - topHeight);
            $(".chatBox-kuang").css("height", totalHeight - topHeight);
            $(".div-textarea").css("width", winWidth - 106);
        } else {
            $(".chatBox-info").css("height", 495);
            $(".chatBox-content").css("height", 448);
            $(".chatBox-content-demo").css("height", 448);
            $(".chatBox-list").css("height", 495);
            $(".chatBox-kuang").css("height", 495);
            $(".div-textarea").css("width", 260);
        }
    }
    (window.onresize = function () {
        screenFuc();
    })();
    //未读信息数量为空时
    var totalNum = $(".chat-message-num").html();
    if (totalNum == "") {
        $(".chat-message-num").css("padding", 0);
    }
    $(".message-num").each(function () {
        var wdNum = $(this).html();
        if (wdNum == "") {
            $(this).css("padding", 0);
        }
    });


    //打开/关闭聊天框
    $(".chatBtn").click(function () {
        $(".chatBox").toggle(10);
    })
    $(".chat-close").click(function () {
        $(".chatBox").toggle(10);
        document.getElementById("onlineOrNot").value="0";
    })
        $("#chat-online").click(function () {
            var data="connect@#${nickName},${userIcon}";
            socket.send(data);
            document.getElementById("onlineOrNot").value="1";
            alert("设置成功")
        })
    //进聊天页面
        $(document).on("click",".chat-list-people",function () {
            document.getElementById("currentChatName").value=$(this).children(".chat-name").children("p").eq(0).html();
            document.getElementById("currentChatIcon").value=$(this).children().eq(0).children("img").attr("src");
            var n = $(this).index();
            $(".chatBox-head-one").toggle();
            $(".chatBox-head-two").toggle();
            $(".chatBox-list").fadeToggle();
            $(".chatBox-kuang").fadeToggle();

            var msgs=$(this).find('.hidden-msg').val().split('#@#');
            //清空数字
            $(this).find('.message-num').html("");
            for(var k = 0;k<msgs.length;k++){
                if($(this).find('.hidden-msg').val().split('#@#')!="") {
                    var midDate = msgs[k].split("--->")[1];
                    var midMsg = msgs[k].split("--->")[0];
                    $(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
                        "<div class=\"author-name\"><small class=\"chat-date\">" + midDate + "</small> </div> " +"<div class=\"left\">"+
                        "<div class=\"chat-avatars\"><img src=\"" + $(this).children().eq(0).children("img").attr("src") + "\" alt=\"头像\" /></div>" +"<div class=\"chat-message\"> " + midMsg + " </div> " + "</div> </div>"
                    );
                }
            }
            //传名字
            $(".ChatInfoName").text($(this).children(".chat-name").children("p").eq(0).html());

            //传头像
            $(".ChatInfoHead>img").attr("src", $(this).children().eq(0).children("img").attr("src"));
                $(this).find('.hidden-msg').val("");
            //聊天框默认最底部
            $(document).ready(function () {
                $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
            });
        }
        )
    //返回列表
    $(".chat-return").click(function () {
        document.getElementById("currentChatName").value=""
        $(".chatBox-content-demo").empty();
        $(".chatBox-head-one").toggle(1);
        $(".chatBox-head-two").toggle(1);
        $(".chatBox-list").fadeToggle(1);
        $(".chatBox-kuang").fadeToggle(1);
    });

    //发送信息
    $("#chat-fasong").click(function () {
        if($("#onlineOrNot").val()=="0"){
            alert("你当前是离线状态，先设置成在线");
        }else{
        var textContent = $(".div-textarea").html().replace(/[\n\r]/g, '<br>')
        var myDate = new Date();
        if (textContent != "") {
            $(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
                "<div class=\"author-name\"><small class=\"chat-date\">"+myDate.toLocaleTimeString()+"</small> </div> " +
                "<div class=\"right\"> <div class=\"chat-message\"> " + textContent + " </div> " +
                "<div class=\"chat-avatars\"><img src=\"${userIcon}\" alt=\"头像\" /></div> </div> </div>");
            //发送后清空输入框
            $(".div-textarea").html("");
            //聊天框默认最底部
            $(document).ready(function () {
                $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
            });
        }
        var receiveNick=$("#currentChatName").val();
        var receiveIcon=$("#currentChatIcon").val();
        var data="chat@#${nickName},${userIcon}"+"@#"+receiveNick+","+receiveIcon+"@#"+textContent;
        socket.send(data);
        }
    });

    //      发送表情
    $("#chat-biaoqing").click(function () {
        $(".biaoqing-photo").toggle();
    });
    $(document).click(function () {
        $(".biaoqing-photo").css("display", "none");
    });
    $("#chat-biaoqing").click(function (event) {
        event.stopPropagation();//阻止事件
    });

    $(".emoji-picker-image").each(function () {
        $(this).click(function () {
            var bq = $(this).parent().html();
            console.log(bq)
            $(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
                "<div class=\"author-name\"><small class=\"chat-date\">2017-12-02 14:26:58</small> </div> " +
                "<div class=\"right\"> <div class=\"chat-message\"> " + bq + " </div> " +
                "<div class=\"chat-avatars\"><img src=\"img/icon01.png\" alt=\"头像\" /></div> </div> </div>");
            //发送后关闭表情框
            $(".biaoqing-photo").toggle();
            //聊天框默认最底部
            $(document).ready(function () {
                $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
            });
        })
    });

    //      发送图片
    function selectImg(pic) {
        if (!pic.files || !pic.files[0]) {
            return;
        }
        var reader = new FileReader();
        reader.onload = function (evt) {
            var images = evt.target.result;
            $(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
                "<div class=\"author-name\"><small class=\"chat-date\">2017-12-02 14:26:58</small> </div> " +
                "<div class=\"right\"> <div class=\"chat-message\"><img src=" + images + "></div> " +
                "<div class=\"chat-avatars\"><img src=\"img/icon01.png\" alt=\"头像\" /></div> </div> </div>");
            //聊天框默认最底部
            $(document).ready(function () {
                $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
            });
        };
        reader.readAsDataURL(pic.files[0]);

    }


</script>
<script type="text/javascript">
</script>
</body>
</html>
