<%--
  Created by IntelliJ IDEA.
  User: hezijian
  Date: 2020/4/17
  Time: 12:28 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<!DOCTYPE html>--%>
<html lang="zh-CN">

<head>
    <meta charset="utf-8">
    <title>wblog</title>
    <link rel="stylesheet" href="css/loginstyle.css">
</head>
<body>
<div class="content">
    <div class="form sign-in">
        <h2>欢迎回来</h2>
        <form id="userLogin">
            <label>
                <span>账号</span>
                <input name="account" id="account"type="account" />
            </label>
            <label>
                <span>密码</span>
                <input name="password" id="password"type="password" />
            </label>
        </form>
        <button id="loginSubmit"type="button" class="submit" onclick="loginsubmit()">登 录</button>
    </div>
    <div class="sub-cont">
        <div class="img">
            <div class="img__text m--up">
                <h2>还未注册？</h2>
                <p>立即注册，看看世界！</p>
            </div>
            <div class="img__text m--in">
                <h2>已有帐号？</h2>
                <p>有帐号就登录吧，好久不见了！</p>
            </div>
            <div class="img__btn">
                <span class="m--up">注 册</span>
                <span class="m--in">登 录</span>
            </div>
        </div>
        <div class="form sign-up">
            <form id="register">
                <label>
                    <span>用户名</span>
                    <input name="userAccount" id="userAccount" type="text" />
                </label>
                <label>
                    <span>昵称</span>
                    <input name="nickName" placeholder="昵称，唯一" id="nickName" type="text" />
                </label>
                <label>
                    <span>邮箱</span>
                    <input name="email" id ="email" type="email" />
                </label>
                <label>
                    <input name="bizCode" id ="bizCode" type="text" placeholder="邮箱验证码"/><button type="button" class="submit" id="sendCode" onclick="sendBizCode()">获取验证码</button>
                </label>
                <label>
                    <span>密码</span>
                    <input name="userPassword" id ="userPassword"type="password" />
                </label>
            </form>
            <button type="button" class="submit" onclick="registe()">注 册</button>
        </div>
    </div>
</div>
<script type='text/javascript' src='js/jquery-3.3.1.min.js'></script>
<script src="js/loginscript.js"></script>
</body>
</html>
