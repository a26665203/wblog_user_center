<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: hezijian
  Date: 2020/4/17
  Time: 3:48 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html><head>
    <link rel="shortcut icon" href="images/tx.jpg" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <title>个人空间</title>
    <meta name="keywords" content="司空琪个人微博,司空琪个人主页,司空琪空间,画江湖动漫" />
    <meta name="description" content="司空琪个人微博,司空琪个人主页,司空琪空间说说,画江湖动漫" />
    <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="cropper/cropper.min.css" rel="stylesheet">
    <link href="sitelogo/sitelogo.css" rel="stylesheet">
    <script src="cropper/cropper.min.js"></script>
    <script src="sitelogo/sitelogo.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
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
    <script type='text/javascript' src='js/base.js'></script>
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
                    <i class="lmlblog-verify lmlblog-verify-a" title="个人"></i></div>
                <div class="lmlblog-member-username">
                    <h1>${nickName}</h1>
                    <span class="lmlblog-mark lmlblog-boy"><i class="fa fa-mars"></i></span>
                    <span class="lmlblog-mark lmlblog-lv" title="经验：3815">Lv.6</span>
                    <span class="lmlblog-mark lmlblog-vip">VIP 6</span></div>
                <div class="lmlblog-member-desc">${userDesc}</div>
                <div class="lmlblog-member-follow-info">
                    <span class="follow no opacity" onclick="followSomeone()" id="follow"><i class="lmlblog-icon"></i>+ 关注</span>
                    <span class="opacity"><i class="lmlblog-icon">&#xe612;</i> 私聊</span>
                </div>
            </div>
            <div class="lmlblog-member-menu clear">
                <li><a href="./toIndex?nickName=${nickName}&account=${userAccount}">主页</a></li>
                <li><a href="./toBlog?nickName=${nickName}&userDesc=${userDesc}&blogCount=${blogCount}&fanCount=${fanCount}&likeCount=${likeCount}&commentCount=${commentCount}&email=${userEmail}&account=${userAccount}&userIcon=${userIcon}">微博</a></li>
                <li class="on"><a href="./toPersonSet?nickName=${nickName}&userDesc=${userDesc}&blogCount=${blogCount}&fanCount=${fanCount}&likeCount=${likeCount}&commentCount=${commentCount}&email=${userEmail}&account=${userAccount}" target="_blank">个人信息</a></li>
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
                        <audio src="mp3/农夫%20-%20伟大航道.mp3" controls></audio>
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
                    <div class="lmlblog-post-list" style="text-align:center">
                        <div  class="lmlblog-posts-list words" style="background-image:url(images/058.png); " data="4197">
                            <div style="text-align: center">
                                <div class="ibox-content">
                                    <div class="row">
                                        <div>
                                        <h1>当前头像是（点击头像修改）：</h1>
                                        </div>
                                        <div id="crop-avatar" class="col-md-6" style="margin-left: 250px">

                                            <div class="avatar-view" title="Change Logo Picture">
                                                <img src="${userIcon}" alt="Logo">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal fade" id="avatar-modal" aria-hidden="true" aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">
                                            <form class="avatar-form" action="./uploadHead?nickName=${nickName}" enctype="multipart/form-data;charset=utf-8" method="post">
                                                <div class="modal-header">
                                                    <button class="close" data-dismiss="modal" type="button">&times;</button>
                                                    <h4 class="modal-title" id="avatar-modal-label">Change Logo Picture</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="avatar-body">
                                                        <div class="avatar-upload">
                                                            <input class="avatar-src" name="avatar_src" type="hidden">
                                                            <input class="avatar-data" name="avatar_data" type="hidden">
                                                            <label for="avatarInput">图片上传</label>
                                                            <input class="avatar-input" id="avatarInput" name="file" type="file">

                                                        </div>
                                                        <div class="row">
                                                            <div class="col-md-9">
                                                                <div class="avatar-wrapper"></div>
                                                            </div>
                                                            <div class="col-md-3">
                                                                <div class="avatar-preview preview-lg"></div>
                                                                <div class="avatar-preview preview-md"></div>
                                                                <div class="avatar-preview preview-sm"></div>
                                                            </div>
                                                        </div>
                                                        <div class="row avatar-btns">
                                                            <div class="col-md-9">
                                                                <div class="btn-group">
                                                                    <button class="btn" data-method="rotate" data-option="-90" type="button" title="Rotate -90 degrees"><i class="fa fa-undo"></i> 向左旋转</button>
                                                                </div>
                                                                <div class="btn-group">
                                                                    <button class="btn" data-method="rotate" data-option="90" type="button" title="Rotate 90 degrees"><i class="fa fa-repeat"></i> 向右旋转</button>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-3">
                                                                <button class="btn btn-success btn-block avatar-save" type="submit"><i class="fa fa-save"></i> 保存修改</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <span></span>
                                <form action="./uploadDesc">
                                    <label>
                                        <span>个性签名:</span>
                                        <input class="input-lg" placeholder="${userDesc}" id="userDesc" name="userDesc">
                                        <input type="hidden" name="account" value="${userAccount}">
                                        <input type="hidden" name="nickName" value="${nickName}">
                                    </label>
                                    <label>
                                        <button class="btn btn-success btn-block avatar-save" id="messageModify" >确认修改</button>
                                    </label>
                                </form>
                                <div class="loading" aria-label="Loading" role="img" tabindex="-1"></div>


                            </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div style="clear:both"></div>
<div class="footer">
    <div class="footer_b_foot">
        <div class="footer_b_top">
        </div>
        <div class="footer_b_bottom">Design by 2020 软件1163何梓健  毕设微博个人主页</div>
    </div>
</div>
</div>
</body>
<script type='text/javascript' src='js/layui.js'></script>
<script type="text/javascript" src="js/person.js"/>
</html>
