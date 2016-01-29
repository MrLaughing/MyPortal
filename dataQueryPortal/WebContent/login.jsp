<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="<%=basePath%>css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>再生活（北京）信息技术有限公司</title>
<meta name="keywords" content="H-ui.admin v2.3,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
<meta name="description" content="H-ui.admin v2.3，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
<script type="text/javascript" src="<%=basePath%>lib/jquery/jquery-1.8.3.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>js/H-ui.js"></script> 
<script type="text/javascript">
	$(function() {
		$("#login")
				.on(
						"click",
						function() {
							var username = document.getElementById("username").value;
							var password = document.getElementById("password").value;
							var captchacode=document.getElementById("captchacode").value;
							if (username!= ""&&password!="") {
								if(captchacode!=""){
								document.getElementById("form1").action = "<%=basePath%>account/account_login.action";
								document.getElementById("form1").submit();
								}else{
									alert('请输入验证码');	
								}
							} else {
								alert('请填写账号和密码');
							}
						});
	});
	var _captcha_id = "#img_captcha";
	function refreshCaptcha() {
		$(_captcha_id).attr("src","<%=basePath%>/captchaCode.jpg?t=" + Math.random());
	};
</script>
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="zai360" />
<div class="header"></div>
<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <form id="form1" class="form form-horizontal" method="post" action="">
      <div class="row cl">
        <label class="form-label col-3"><i class="Hui-iconfont">&#xe60d;</i></label>
        <div class="formControls col-8">
          <input id="username" name="username" type="text" placeholder="账户" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-3"><i class="Hui-iconfont">&#xe60e;</i></label>
        <div class="formControls col-8">
          <input id="password" name="password" type="password" placeholder="密码" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <div class="formControls col-8 col-offset-3">
          <input class="input-text size-L" type="text" name="captchacode" id="captchacode" placeholder="验证码"  value="" style="width:150px;">
          <img id="img_captcha" src="<%=basePath%>/captchaCode.jpg"> <a id="kanbuqing" href="javascript:;" onclick="refreshCaptcha()">看不清，换一张</a> </div>
      </div>
      <div class="row">
        <div class="formControls col-8 col-offset-3">
         <div style="float:left; height: 35px; position: relative;">
         	<label for="online"><input type="checkbox" name="rememberMe" id="rememberMe" value="true">记住密码</label>	
          &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
          	<font style="color: red;font-weight: bold;">${requestScope.msg }</font>
         </div>
         <div style="float: right; margin-right: 50px">
        	<input type="button" id="login" name="login" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;" class="btn btn-success radius size-L">		
         </div>
        </div>
      </div>
    </form>
  </div>
</div>
<div class="footer">
感谢jQuery、layer、laypage、Validform、UEditor、My97DatePicker、iconfont、Datatables、WebUploaded、icheck、highcharts、bootstrap-Switch<br>
	Copyright &copy;zai360.com  All Rights Reserved.
</div>
</body>
</html>