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
<title>用户注册-添加账户</title>
</head>
<body>
	<nav class="breadcrumb"> <i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>系统管理 <span class="c-gray en">&gt;</span>
	用户注册 <a class="btn btn-success radius r mr-20"
		style="line-height: 1.6em; margin-top: 3px"
		href="javascript:location.replace(location.href);" title="刷新"><i
		class="Hui-iconfont">&#xe68f;</i>刷新</a> </nav>
	<div class="pd-20">
		<form id="form1" name="form1" action="<%=basePath%>account/account_signupAccount.action" method="post" class="form form-horizontal"
			id="form-user-character-add">
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>用户名：</label>
				<div class="formControls col-5">
					<input type="text" class="input-text" value=""
						id="username" name="username" datatype="*2-16" placeholder="一般为工号"
						nullmsg="用户名不能为空">
				</div>
				<div class="col-4"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>姓名：</label>
				<div class="formControls col-5">
					<input type="text" class="input-text" value=""
						id="name" name="name" datatype="*2-16" nullmsg="请填写姓名！">
				</div>
				<div class="col-4"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>密码：</label>
				<div class="formControls col-5">
					<input type="text" class="input-text" value=""
						id="password" name="password" datatype="*6-16" nullmsg="请填写密码！">
				</div>
				<div class="col-4"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>邮箱：</label>
				<div class="formControls col-5">
					<input type="text" class="input-text" value="" name="email"
						id="email" datatype="e" nullmsg="请输入邮箱！">
				</div>
				<div class="col-4"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-2">部门：</label>
				<div class="formControls col-5">
					<span class="select-box"> <select class="select" size="1" 
						name="department" id="department" datatype="*" nullmsg="请选择部门！">
							<option value="" selected>--未选择--</option>
							<option value="管理员">管理员</option>
							<option value="回收">回收</option>
							<option value="客服">客服</option>
							<option value="商城">商城</option>
							<option value="市场">市场</option>
							<option value="推广">推广</option>
							<option value="地推">地推</option>
					</select>
					</span>
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
	</script>
</body>
</html>