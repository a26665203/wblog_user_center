<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hezijian
  Date: 2020/4/17
  Time: 11:37 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="images/tx.jpg" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <title>个人主页</title>
    <meta name="keywords" content="司空琪个人微博,司空琪个人主页,司空琪空间,画江湖动漫" />
    <meta name="description" content="司空琪个人微博,司空琪个人主页,司空琪空间说说,画江湖动漫" />
    <style type="text/css">
        img.wp-smiley,
        img.emoji {
            display: inline !important;
            border: none !important;
            box-shadow: none !important;
            height: 1em !important;
            width: 1em !important;
            margin: 0 .07em !important;
            vertical-align: -0.1em !important;
            background: none !important;
            padding: 0 !important;
        }
    </style>
    <link rel='stylesheet' id='style-css'  href='css/style.css' type='text/css' media='all' />
    <link rel='stylesheet' id='lmlblog-css'  href='css/lmlblog.css' type='text/css' media='all' />
    <link rel='stylesheet' id='all-css'  href='css/all.css' type='text/css' media='all' />
    <script type='text/javascript' src='js/jquery.min.js'></script>
    <script type='text/javascript' src='js/ueditor.all.min.js'></script>
</head>
<body>
<!-- 菜单 -->
<div class="menu-bar">
    <div class="head_content">
        <h1 class="m-logo"><a href="/" ></a></h1>
        <div id="nav" class="menu-menu-1-container"><ul id="menu-menu-1" class="menu">
            <li class="menu-item menu-item-type-custom"><a href="#1"><i class="fa fa-home"></i> 个人中心</a></li>
            <li class="menu-item menu-item-type-taxonomy menu-item-object-category">
                <a href="#1"><i class="fa fa-comments-o"></i> 说说</a></li>
            <li class="menu-item"><a href="#1">贴吧</a></li>
            <li class="menu-item"><a href="#1">微博</a></li>
            <li id="menu-item" class="gengxin menu-item"><a href="#1">空间</a></li>
            <li class="menu-item menu-item-has-children"><a href="#1">好友</a>
                <ul class="sub-menu">
                    <li class="menu-item"><a href="#">燕凌姣</a></li>
                    <li class="menu-item"><a href="#">姬如雪</a></li>
                    <li class="menu-item"><a href="#">陆林轩</a></li>
                    <li class="menu-item"><a href="#">红莲</a></li>
                    <li class="menu-item"><a href="#">陆雪琪</a></li>
                    <li class="menu-item"><a href="#">画江湖</a></li>
                </ul>
            </li>
        </ul></div>
        <div class="head_user_info">
            <ul>
                <li id="head_search"><i class="fa fa-search"></i></li>
                <!-- <li id="head_login" onclick="#1">登录</li>
                <span class="head_login_middle">or</span>
                <li id="head_reg" onclick="#1">注册</li> --></ul>
        </div>
        <div style="clear: both;"></div>
    </div>
</div><!-- menu-bar -->
<div style="clear: both;"></div><div class="lmlblog-member-main" data="1">
    <div class="lmlblog-member-bg">
        <div class="lmlblog-member-content">
            <div class="lmlblog-member-header">
                <div class="lmlblog-member-avatar other">
                    <img src="${userIcon}" class="avatar"/>
                    <i class="lmlblog-verify lmlblog-verify-a" title="${nickName}"></i></div>
                <div class="lmlblog-member-username">
                    <h1>${nickName}</h1>
                    <span class="lmlblog-mark lmlblog-boy"><i class="fa fa-mars"></i></span>
                    <span class="lmlblog-mark lmlblog-lv" title="经验：3815">Lv.6</span>
                    <span class="lmlblog-mark lmlblog-vip">VIP 6</span></div>
                <div class="lmlblog-member-desc" id="userDesc">${userDesc}</div>
                <div class="lmlblog-member-follow-info">
                    <span class="follow no opacity" onclick="followSomeone()" id="follow"><i class="lmlblog-icon"></i>+ 关注</span>
                    <span class="opacity"><i class="lmlblog-icon">&#xe612;</i> 私聊</span>
                </div>
            </div>
            <div class="lmlblog-member-menu clear">
                <li class="on">主页</li>
                <li><a href="./toBlog?nickName=${nickName}&userDesc=${userDesc}&blogCount=${blogCount}&fanCount=${fanCount}&likeCount=${likeCount}&commentCount=${commentCount}&email=${userEmail}&account=${userAccount}&userIcon=${userIcon}">微博</a></li>
                <li><a href="./toPersonSet?nickName=${nickName}&userDesc=${userDesc}&blogCount=${blogCount}&fanCount=${fanCount}&likeCount=${likeCount}&commentCount=${commentCount}&email=${userEmail}&account=${userAccount}&userIcon=${userIcon}" target="_blank">个人信息</a></li>
                <li>文章</li>
                <li><a href="./toSquare?nickName=${nickName}&userDesc=${userDesc}&blogCount=${blogCount}&fanCount=${fanCount}&likeCount=${likeCount}&commentCount=${commentCount}&email=${userEmail}&account=${userAccount}&userIcon=${userIcon}" target="_blank">个人微博</a></li>
            </div>
            <div class="lmlblog-member-content-list clear">
                <div class="lmlblog-member-left">
                    <div class="lmlblog-member-left-follow clear">
                        <li>
                            <strong>${blogCount}</strong>
                            <span>微博</span>
                        </li>
                        <li>
                            <strong>${fanCount}</strong>
                            <span>粉丝</span>
                        </li>
                        <li>
                            <strong>${likeCount}</strong>
                            <span>点赞</span>
                        </li>
                        <li>
                            <strong>${commentCount}</strong>
                            <span>评论</span>
                        </li>
                    </div>
                    <div class="lmlblog-member-left-bg-music clear">
                        <h3>背景音乐</h3>
                        <div id="lmlblog-memeber-bg-music" class="aplayer"></div>
                        <img src="images/nongfu.png" alt="农夫">
                        <audio src="mp3/农夫%20-%20伟大航道.mp3" autoplay controls></audio>
                    </div>
                    <div class="lmlblog-member-left-profile">
                        <h3>资料简介</h3>
                        <li><i class="fa fa-address-book-o"></i>账号：<span>${userAccount}</span></li>
                        <li><i class="fa fa-transgender"></i> 性别：<span>男</span></li>
                        <li><i class="fa fa-map-marker"></i> 学校：<span>广东海洋大学</span></li>
                        <li><i class="fa fa-smile-o"></i> 签名：<span>${userDesc}</span></li>
                        <div class="lmlblog-member-left-profile-hide">
                            <li><i class="fa fa-weibo"></i> 昵称：<span>${nickName}</span></li>
                            <li><i class="fa fa-clock-o"></i> 生日：<span>2月15日</span></li>
                            <li><i class="fa fa-qq"></i> 邮箱：<span>${userEmail}</span></li>
                        </div>
                        <div class="lmlblog-member-left-profile-more">查看更多 <i class="fa fa-angle-right"></i></div>
                    </div>

                    <div class="lmlblog-member-left-visitor clear">
                        <h3>最近访客</h3>
                        <li><a href="#1" title="访问时间：22分钟前"><img src="picture/1.gif" class="avatar" /><p>born</p></a></li>
                        <li><a href="#2" title="访问时间：3小时前"><span class="lmlblog-vip-icon"></span><img src="picture/qi.jpg" class="avatar" /><p>司空琪</p></a></li>
                        <li><a href="#3" title="访问时间：10小时前"><span class="lmlblog-vip-icon"></span><img src="picture/2.gif" class="avatar" /><p>晴川</p></a></li>
                        <li><a href="#4" title="访问时间：14小时前"><img src="picture/3.gif" class="avatar" /><p>大白兔</p></a></li>
                        <li><a href="#5" title="访问时间：15小时前"><img src="picture/4.jpg" class="avatar" /><p>momo</p></a></li>
                        <li><a href="#6" title="访问时间：1天前"><span class="lmlblog-vip-icon"></span><img src="picture/5.png" class="avatar" /><p>啦啦啦123</p></a></li>
                        <li><a href="#7" title="访问时间：1天前"><img src="picture/6.gif" class="avatar" /><p>随风</p></a></li>
                        <li><a href="#8" title="访问时间：1天前"><img src="picture/7.gif" class="avatar" /><p>哒哒</p></a></li>
                        <li><a href="#9" title="访问时间：1天前"><img src="picture/8.gif" class="avatar" /><p>陌若</p></a></li>
                        <li><a href="#10" title="访问时间：2天前"><span class="lmlblog-vip-icon"></span><img src="picture/9.gif" class="avatar" /><p>hidhz</p></a></li>
                        <li><a href="#11" title="访问时间：2天前"><span class="lmlblog-vip-icon"></span><img src="picture/10.gif" class="avatar" /><p>老虎</p></a></li>
                        <li><a href="#12" title="访问时间：3天前"><img src="picture/11.gif" class="avatar" /><p>不要演有事说</p></a></li>
                        <li><a href="#13" title="访问时间：3天前"><img src="picture/12.gif" class="avatar" /><p>瑾似流年</p></a></li>
                        <li><a href="#14" title="访问时间：3天前"><img src="picture/13.gif" class="avatar" /><p>九张机</p></a></li>
                        <li><a href="#15" title="访问时间：4天前"><img src="picture/14.jpg" class="avatar" /><p>美美哒</p></a></li>
                        <li><a href="#16" title="访问时间：4天前"><span class="lmlblog-vip-icon"></span><img src="picture/15.gif" class="avatar" /><p>zanbocm</p></a></li>
                        <li><a href="#2528" title="访问时间：4天前"><img src="picture/16.gif" class="avatar" /><p>yfdaifhc</p></a></li>
                        <li><a href="#52" title="访问时间：4天前"><img src="picture/17.png" class="avatar" /><p>xcxla</p></a></li>
                        <li><a href="#0292" title="访问时间：4天前"><img src="picture/18.jpg" class="avatar" /><p>墨虹客栈</p></a></li>
                        <li><a href="#1753" title="访问时间：5天前"><img src="picture/19.jpg" class="avatar" /><p>空间吧</p></a></li>
                    </div>
                    <div class="lmlblog-member-left-bg-xg clear">
                        <h3>相关推荐</h3>
                        <div id="lmlblog-memeber-bg-xg" class="aplayer"></div>
                        <a href="http://www.lmlblog.com/blog/yanlingjiao/1.html" target="_blank"><h2>燕凌姣个人主页</h2>
                            <img src="http://www.lmlblog.com/blog/yanlingjiao/images/jiao8.jpg" alt="燕凌姣个人主页">
                            <p>　　一袭蓝衣亭亭玉立，姣若春梅绽雪，神如月射寒江，香培玉琢、英姿飒爽;其静兰生幽谷，其动若飞若扬，性格大气坚毅、疏朗开阔，举止敢爱敢恨、聪明磊落...他就是燕凌姣</p></a>
                    </div>
                </div>
                <div class="lmlblog-member-right">
                    <div class="lmlblog-post-list">
                        <c:forEach items="${personalWblogs}" var="wblog">
                        <div  class="lmlblog-posts-list words" style="background-image:url(images/058.png); " data="4197">

                            <!-- 动态内容部分，包括列表 -->
                            <div class="lmlblog-post-user-info">
                                <div class="lmlblog-post-user-info-avatar" user-data="1">
                                    <a href="#1" style="display: inline-block;">
                                        <span class="lmlblog-vip-icon"></span><img src="${userIcon}" class="avatar"/><i class="lmlblog-verify lmlblog-verify-a" title="司空琪"></i></a>
                                    <div class="lmlblog-user-info-card">
                                        <div class="info_card_loading"><img src="picture/chat-loading.gif"><p>资料加载中...</p>
                                        </div>
                                    </div>
                                </div>

                                <div class="lmlblog-post-user-info-name">
                                    <a href="#1">
                                        <font style="color:#333;font-weight:600">${nickName}</font>
                                    </a>
                                    <span class="lmlblog-mark lmlblog-lv" title="经验：3815">Lv.6</span><span class="lmlblog-mark lmlblog-vip">VIP 6</span>
                                </div>
                                <div class="lmlblog-post-user-info-time" title="2017-12-14 05:25">${wblog.sendDate}</div>
                            </div><!-- 作者信息 -->
                            <div class="lmlblog-post-setting">
                                <i class="fa fa-angle-down"></i>
                                <div  class="lmlblog-post-setting-box">
                                    <ul>
                                        <a href="#" title="查看全文"><li>查看全文</li></a>
                                        <a href="#1" title="访问主页"><li>访问主页</li></a>
                                    </ul>
                                </div>
                            </div>
                            <div class="lmlblog-post-content ">
                                <a class="post_list_link" href="#">
                                    <P>　${wblog.content}</P>
                                   </a>
                            </div>
                            <c:if test="${(wblog.imageUrl!=NULL && wblog.imageUrl!='')}">
                            <div class="lmlblog-post-images-list clear">
                                <a href="${wblog.imageUrl}" data-fancybox="gallery" data-caption='<i class="fa fa-copyright"></i> lmlblog'>
                                    <img src="${wblog.imageUrl}" alt="啥都没有"/></a>
                            </div>
                            </c:if>
                            <div class="lmlblog-post-bar">
                                <li class="lmlblog-no-like" onclick='lmlblog_like_posts(4197,this,"post");'>
                                    <i class="lmlblog-icon">&#xe608;</i> <span>5</span>
                                </li>
                                <li onclick='list_comments_show(this);'>
                                    <i class="lmlblog-icon">&#xe633;</i> <span>5</span>
                                </li>
                                <li onclick='lmlblog_pop_login_style();'>
                                    <i class="lmlblog-icon"></i>评论 <span>${wblog.commentCount}</span>
                                </li>
                                <li>
                                    <i class="lmlblog-icon">&#xe602;</i> <span>${wblog.likeCount}</span>
                                </li>
                                <li>
                                    <a href="./deleteWblog?nickName=${nickName}&account=${userAccount}&wblogId=${wblog.id}">删除微博</a>
                                </li>
                                <li class="tag clear">
                                    <i class="lmlblog-icon">&#xe895;</i><a href="#2" title="微博毕设"># 微博毕设</a></li>
                            </div>
                            <div class="lmlblog-post-like-list">
                                <a href="#17" id="had_like_11788">
                                    <img src="picture/11.gif" class="avatar" /></a>
                                <a href="#18" id="had_like_11499"><img src="picture/12.gif" class="avatar" /></a>
                                <a href="#22" id="had_like_11488"><img src="picture/20.png" class="avatar" />
                                    <i class="lmlblog-verify lmlblog-verify-a" title="认证信息：作者许仙白"></i></a><a href="#22" id="had_like_11477">
                                <img src="picture/13.gif" class="avatar" />
                                <i class="lmlblog-verify lmlblog-verify-a" title="认证信息：168号计师"></i></a><a href="#22" id="had_like_1">
                                <img src="images/tx2.jpg" class="avatar"/>
                                <i class="lmlblog-verify lmlblog-verify-a" title="司空琪"></i></a></div>
                            <div class="lmlblog-post-footer-bar">
                                <span title="${wblog.sendDate}">${wblog.sendDate}</span>
                                <span>电脑端</span><i class="lmlblog-icon" onclick="lmlblog_post_type_open();">&#xe62c;</i></div>

                        </div><!-- posts_list -->
                        </c:forEach>
                        <div  class="lmlblog-posts-list words" style="background-image:url(images/03.png); " data="4048">
                        <!-- posts_list -->
                        <div class="lmlblog-pager">
                            <c:if test="${page !=1}">
                                <a class="lmlblog-pager-left" href="./findPersonWblog?nickName=${nickName}&account=${userAccount}&page=${page-1}">上一页</a>
                            </c:if>
                            <div class="lmlblog-pager-center layui-form">
                                <input type="hidden" value="${page}">
                               第 ${page} 页</div>
                            <c:if test="${wblogCount==4}">
                                <a class="lmlblog-pager-right" href="./findPersonWblog?nickName=${nickName}&account=${userAccount}&page=${page+1}">下一页</a>
                            </c:if>
                           </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 底部 -->
<div style="clear:both"></div>
<div class="footer">
    <div class="footer_b_foot">
        <div class="footer_b_top">
        </div>
        <div class="footer_b_bottom">Design by 2020 软件1163何梓健  毕设微博个人主页</div>
    </div>
</div>
<!-- 返回顶部 -->
<a href="javascript:returnTop();" class="cd-top"></a>
<script type="text/javascript">
    var sdelay=0;
    function returnTop() {
        window.scrollBy(0,-100);//Only for y vertical-axis
        if(document.body.scrollTop>0) {
            sdelay= setTimeout('returnTop()',50);
        }
    }
</script>
</body>
<script type='text/javascript' src='js/base.js'></script>
<script type='text/javascript' src='js/all.js'></script>
<script type='text/javascript' src='js/plupload.full.min.js'></script>
<script type='text/javascript' src='js/layui.js'></script>
<script type="text/javascript" src="js/person.js"/>
<script>
    jQuery(document).ready(function($){
        $.fn.smartFloat = function() {
            var position = function(element) {
                var top = element.position().top, pos = element.css("position");
                $(window).scroll(function() {
                    var scrolls = $(this).scrollTop();
                    if (scrolls > top) {
                        if (window.XMLHttpRequest) {
                            element.css({
                                position: "fixed",
                                top: 0
                            });
                        } else {
                            element.css({
                                top: scrolls
                            });
                        }
                    }else {
                        element.css({
                            position: "absolute",
                            top: top
                        });
                    }
                });
            };
            return $(this).each(function() {
                position($(this));
            });
        };
        $(".lmlblog-member-left-bg-xg").smartFloat();
    });
</script>
</html>
