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
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="<%=basePath%>css/H-ui.min.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>css/H-ui.admin.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>lib/Hui-iconfont/1.0.1/iconfont.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>lib/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="<%=basePath%>lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript"
	src="<%=basePath%>lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/H-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>js/H-ui.admin.js"></script>
<script type="text/javascript" src="<%=basePath%>lib/Validform/5.3.2/Validform.min.js"></script>
<title>修改密码</title>
</head>
<body>
	<div class="pd-20">
		<form id="form1" name="form1" action="<%=basePath%>account/account_updatePassword.action" method="post" class="form form-horizontal"
			id="form-user-character-add">
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>用户名：</label>
				<div class="formControls col-5">
					<input type="text" class="input-text" value="" readonly="readonly"
						id="username" name="username" datatype="*2-16"
						nullmsg="用户名不能为空">
				</div>
				<div class="col-4"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>原密码：</label>
				<div class="formControls col-5">
					<input type="password" class="input-text"  placeholder="请输入原密码"
						id="oldpwd" name="oldpwd" datatype="*2-16"
						nullmsg="原密码不能为空">${requestScope.msg }
				</div>
				<div class="col-4"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>新密码：</label>
				<div class="formControls col-5">
					<input type="password" class="input-text"
						id="newpwd" name="newpwd" datatype="*2-16" nullmsg="请填写新密码！">
				</div>
				<div class="col-4"></div>
			</div>
			<div class="row cl">
				<div class="col-10 col-offset-2">
					<button type="submit" class="btn btn-success radius"
						id="admin-role-save" name="admin-role-save">
						<i class="icon-ok"></i> 确定
					</button>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		$(function() {
			/* 处理格式验证后提交 */
			$("#form1").Validform({
				tiptype:2,
				callback:function(form){
					form[0].submit();
				}
			});
			
		});
		window.onload=user;
		/* 初始用户名 */
		function user(){
			var	usernamevalue='<%= request.getParameter("username")%>';
			var username=document.getElementById("username");
			username.value=usernamevalue;
		};
	</script>
</body>
</html>