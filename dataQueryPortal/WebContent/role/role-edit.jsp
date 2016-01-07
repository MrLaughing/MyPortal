<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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
<title>编辑角色</title>
</head>
<body>
	<div class="pd-20">
		<form action="<%=basePath%>account/account_updateRole.action" method="post" class="form form-horizontal"
			id="form-user-character-add">
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>用户名：</label>
				<div class="formControls col-5">
					<input type="text" class="input-text" value="${requestScope.username}" readonly="readonly"
						id="username" name="username" datatype="*2-16"
						nullmsg="用户名不能为空">
				</div>
				<div class="col-4"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-2">用户角色：</label>
				<div class="formControls col-10">
					<div class="check-box" >
						<b>基本角色</b>
						<table border="0" cellpadding="0" cellspacing="0" style="text-align:left; ">
							<tr height="28px">
							   <td width="140px"><input id="roles" name="roles" value="1" type="checkbox">回收人员</td>
							   <td width="140px"><input id="roles" name="roles" value="2" type="checkbox">客服人员</td>
							   <td width="140px"><input id="roles" name="roles" value="3" type="checkbox">商城人员</td>
							</tr>
						 	<tr height="28px">
							   <td width="140px"><input id="roles" name="roles" value="4" type="checkbox">市场人员</td>
							   <td width="140px"><input id="roles" name="roles" value="5" type="checkbox">推广人员</td>
							</tr>
						</table>
						<b>额外角色</b>
						<table border="0" cellpadding="0" cellspacing="0" style="text-align:left; ">
							<tr height="28px">
							   <td width="140px"><input id="roles" name="roles" value="6" type="checkbox">仅查各基站出差人数</td>
							   <td width="140px"><input id="roles" name="roles" value="7" type="checkbox">仅查首次上门违约用户</td>
							   <td width="140px"><input id="roles" name="roles" value="8" type="checkbox">仅查停用用户数</td>
							</tr>
						 	<tr height="28px">
							   <td width="140px"><input id="roles" name="roles" value="9" type="checkbox">仅查电话购物信息</td>
							   <td width="140px"><input id="roles" name="roles" value="10" type="checkbox">仅查app下单信息</td>
							   <td width="140px"><input id="roles" name="roles" value="11" type="checkbox">仅查未发放优惠券用户</td>
							</tr>
						 	<tr height="28px">
							   <td width="140px"><input id="roles" name="roles" value="12" type="checkbox">仅查基站推广人数</td>
							   <td width="140px"><input id="roles" name="roles" value="13" type="checkbox">仅查地推注册明细</td>
							   <td width="140px"><input id="roles" name="roles" value="14" type="checkbox">仅查地推推荐服务日期</td>
							</tr>
						 	<tr height="28px">
							   <td width="140px"><input id="roles" name="roles" value="15" type="checkbox">仅查查询用户信息</td>
							   <td width="140px"><input id="roles" name="roles" value="16" type="checkbox">仅查重复地址用户信息</td>
							   <td width="140px"><input id="roles" name="roles" value="17" type="checkbox">仅查客户注销原因</td>
							</tr>
						</table>
					</div>
				</div>
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
	/* 处理角色权限选择 */
		$(function() {
			/* 处理格式验证 */
			$("#form-user-character-add").Validform({
				tiptype:2,
				callback:function(form){
					form[0].submit();
				/* 	var index = parent.layer.getFrameIndex(window.name);
					parent.$('.btn-refresh').click();
					parent.layer.close(index); */
				}
			});
		});
	window.onload=role;
	/* 初始用户角色checkbox */
	function role(){
		var roleidsJson=<%= request.getAttribute("roleidsJson")%>;
		var ewairoleidsJson=<%= request.getAttribute("ewairoleidsJson") %>;
	/* 	alert(roleidsJson.roleids.length); */
		for(var i=0;i<roleidsJson.roleids.length;i++){
		/* 	alert(roleidsJson.roleids[i]); */
			var input = document.getElementsByTagName("input");
			for (var j = 0; j < input.length; j++) {
				if(input[j].type == "checkbox"){
				/* 	alert(input[j].value+","+roleidsJson.roleids[i]); */
					if(input[j].value==roleidsJson.roleids[i]){
						input[j].checked=true;//已有角色初始选中
						/* input[j].disabled=true;//已有的角色不再提交 */
					}
				}
			}
		};
		for(var i=0;i<ewairoleidsJson.ewairoleids.length;i++){
			var input = document.getElementsByTagName("input");
			for(var j = 0; j < input.length; j++){
				if(input[j].type=="checkbox"){
					if(input[j].value==ewairoleidsJson.ewairoleids[i]){
						/* input[j].style.display=""; */
						input[j].disabled=true;/* 已重叠的额外角色不再tijiao */
					}
				}
			}
		}
		
	};
	</script>
</body>
</html>