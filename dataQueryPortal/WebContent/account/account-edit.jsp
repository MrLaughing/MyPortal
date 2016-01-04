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
<title>编辑用户</title>
</head>
<body>
	<div class="pd-20">
		<form id="form1" name="form1" action="<%=basePath%>account/account_updateAccount.action" method="post" class="form form-horizontal"
			id="form-user-character-add">
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>用户名：</label>
				<div class="formControls col-5">
					<input type="text" class="input-text" value="${requestScope.account.username}" readonly="readonly"
						id="username" name="username" datatype="*2-16"
						nullmsg="用户名不能为空">
				</div>
				<div class="col-4"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>姓名：</label>
				<div class="formControls col-5">
					<input type="text" class="input-text" value="${requestScope.account.name}"
						id="name" name="name" datatype="*2-16">
				</div>
				<div class="col-4"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>邮箱：</label>
				<div class="formControls col-5">
					<input type="text" class="input-text" value="${requestScope.account.email}" name="email"
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
							<option value="总经理">总经理</option>
							<option value="副总经理">副总经理</option>
							<option value="测试部">测试部</option>
							<option value="运营管理部">运营管理部</option>
							<option value="废品回收部">废品回收部</option>
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
	window.onload=department;
	/* 初始select标签下部门、角色权限 */
	function department(){
		var	selected='<%= request.getAttribute("department")%>';
		var departments=document.getElementById("department");
		for(var i=0;i<departments.options.length;i++){
			if(departments.options[i].value==selected){
				departments.options[i].selected=true;
				break;
			};
		};
	};
	</script>
</body>
</html>