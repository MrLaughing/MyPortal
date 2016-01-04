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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>再生活（北京）信息技术有限公司</title>
<link rel="stylesheet" href="<%=basePath%>css/loginstyle.css">
<script type="text/javascript" src="<%=basePath%>lib/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#login")
				.on(
						"click",
						function() {
							var username = document.getElementById("username").value;
							var password = document.getElementById("password").value;
							if (username!= ""&&password!="") {
						 	document.getElementById("form1").action = "<%=basePath%>account/account_login.action";
								document.getElementById("form1").submit();
							} else {
								alert('请填写账号和密码');
							}
						});
	});
</script>
</head>
<body>

	<section class="container">
	<div class="login">
		<h1>用户登录</h1>
		<center>${requestScope.msg }</center>
		<form id="form1" name="form1" method="post" action="">
			<p>
				<input type="text" name="username" id="username" value="" placeholder="账号">
			</p>
			<p>
				<input type="password" name="password" id="password" value="" placeholder="密码">
			</p>
			<p class="remember_me">
				<label> <input type="checkbox" name="remember_me"
					id="remember_me"> 记住密码
				</label>
			</p>
			<p class="submit">
				<input type="button" name="login" id="login" value="登录">
			</p>
		</form>
	</div>

	<div class="login-help">
		<p>
			忘记密码? <a href="">点击修改</a>
		</p>
	</div>
	</section>
	<div style="text-align: center;">
		<footer>
		<p>
			感谢jQuery、layer、laypage、Validform、UEditor、My97DatePicker、iconfont、Datatables、WebUploaded、icheck、highcharts、bootstrap-Switch<br>Copyright
			&copy;2015 H-ui.admin v2.3 All Rights Reserved.<br> 本后台系统由<a
				href="http://www.h-ui.net/" target="_blank" title="H-ui前端框架">H-ui前端框架</a>提供前端技术支持
		</p>
		</footer>
	</div>
</body>
</html>
