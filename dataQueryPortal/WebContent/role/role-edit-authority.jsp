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
<title>编辑权限</title>
</head>
<body>
	<div class="pd-20">
		<form action="<%=basePath%>account/account_updateAuthority.action" method="post" class="form form-horizontal"
			id="form-user-character-add">
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>角色：</label>
				<div class="formControls col-5">
					<input type="text" class="input-text" value="${requestScope.role}" readonly="readonly"
						id="role" name="role" datatype="*2-16"
						nullmsg="角色不能为空">
				</div>
				<div class="col-4"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-2">角色权限：</label>
				<div class="formControls col-10">
					<div class="check-box" >
						<b>回收相关</b>
						<table border="0" cellpadding="0" cellspacing="0" style="text-align:left; ">
							 <tr height="28px">
								 <td width="130px"><input id="authorities" name="authorities" value="admin:report_findccrs" type="checkbox">各基站出差人数</td>
								 <td width="130px"><input id="authorities" name="authorities" value="admin:report_findsmwy" type="checkbox">首次上门违约用户</td>
							 </tr>
						 </table>
					 	<b>客服相关</b>
						 <table border="0" cellpadding="0" cellspacing="0" style="text-align:left; ">
						 	  <tr height="28px">
							 	  <td width="130px"><input id="authorities" name="authorities" value="admin:report_findtyyh" type="checkbox">停用用户数</td>
							 	  <td width="130px"><input id="authorities" name="authorities" value="admin:report_finddhgw" type="checkbox"> 电话购物信息</td>
							  </tr>
						  </table>
					 	<b>商城相关</b>
						 <table border="0" cellpadding="0" cellspacing="0" style="text-align:left; ">
							 <tr height="28px">
								 <td width="130px"><input id="authorities" name="authorities" value="admin:report_findappxdxx" type="checkbox">app下单信息</td>
								 <td width="130px"><input id="authorities" name="authorities" value="admin:report_findwyhq" type="checkbox">未发放优惠券用户</td>
							 </tr>
						 </table>
					 	<b>市场相关</b>
						 <table border="0" cellpadding="0" cellspacing="0" style="text-align:left; ">
							 <tr height="28px">
							 	<td width="130px"><input id="authorities" name="authorities" value="admin:report_findtgrs" type="checkbox">基站推广人数</td>
							 </tr>
						 </table>
					 	<b>推广相关</b>
						 <table border="0" cellpadding="0" cellspacing="0" style="text-align:left; ">
						   	<tr height="28px">
							   <td width="130px"><input id="authorities" name="authorities" value="admin:report_finddtzc" type="checkbox">地推注册明细</td>
							   <td width="130px"><input id="authorities" name="authorities" value="admin:report_findfwrq" type="checkbox">地推推荐服务日期</td>
						   	</tr>
						 </table>
					 	<b>交互查询</b>
						 <table border="0" cellpadding="0" cellspacing="0" style="text-align:left; ">
							 <tr height="28px">
							   <td width="130px"><input id="authorities" name="authorities" value="admin:report_findyhxx" type="checkbox">查询用户信息</td>
							   <td width="130px"><input id="authorities" name="authorities" value="admin:report_findcfdz" type="checkbox">重复地址用户信息</td>
							   <td width="130px"><input id="authorities" name="authorities" value="admin:report_findzxyy" type="checkbox">客户注销原因</td>
							 </tr>
						 </table>
					 	<b style="display: none;">隐藏查询</b>
						 <table border="0" cellpadding="0" cellspacing="0" style="text-align:left;display: none; ">
							<tr height="28px">
								<td width="130px"><input id="authorities" name="authorities" value="admin:report_finddtxx" type="checkbox">单个地推信息</td>
								<td width="130px"><input id="authorities" name="authorities" value="admin:report_findhsje" type="checkbox">每日回收金额</td>
							    <td width="130px"><input id="authorities" name="authorities" value="admin:report_findzcyh" type="checkbox">某时段注册用户数</td>
							</tr>
							<tr height="28px">
							    <td width="130px"><input id="authorities" name="authorities" value="admin:report_findnoOpenid" type="checkbox">注册无openID用户</td>
								<td width="130px"><input id="authorities" name="authorities" value="admin:report_findhsxx" type="checkbox">回收任务信息</td>
							    <td width="130px"><input id="authorities" name="authorities" value="admin:report_findscsm" type="checkbox">基站首次上门数</td>
							</tr>
							<tr height="28px">
								<td width="130px"><input id="authorities" name="authorities" value="admin:report_findscyh" type="checkbox">未购买过商品用户</td>
							    <td width="130px"><input id="authorities" name="authorities" value="admin:report_finddddc" type="checkbox">订单查询</td>
								<td width="130px"><input id="authorities" name="authorities" value="admin:report_findyxyh" type="checkbox">营销用户数</td>
							</tr>
							<tr height="28px">
								<td width="130px"><input id="authorities" name="authorities" value="admin:report_findwssj" type="checkbox">未完善时间用户</td>
								<td width="130px"><input id="authorities" name="authorities" value="admin:report_findscxd" type="checkbox">首次下单信息</td>
							    <td width="130px"><input id="authorities" name="authorities" value="admin:report_findxdmx" type="checkbox">首次下单商品明细</td>
							</tr>
							<tr height="28px">
							    <td width="130px"><input id="authorities" name="authorities" value="admin:report_findzcfw" type="checkbox">新用户服务设置</td>
								<td width="130px"><input id="authorities" name="authorities" value="admin:report_finddtfw" type="checkbox">地推用户服务设置</td>
							    <td width="130px"><input id="authorities" name="authorities" value="admin:report_findzhye" type="checkbox">所有账户总余额</td>
							</tr>
							<tr height="28px">
								<td width="130px"><input id="authorities" name="authorities" value="admin:report_findscsy" type="checkbox">商城首次使用数</td>
							    <td width="130px"><input id="authorities" name="authorities" value="admin:report_findhssj" type="checkbox">回收手机任务信息</td>
							    <td width="130px"><input id="authorities" name="authorities" value="admin:report_findtsgl" type="checkbox">用户投诉管理</td>
							 </tr>
							<tr height="28px">
							    <td width="130px"><input id="authorities" name="authorities" value="admin:report_findsmkh" type="checkbox">上门客户数</td>
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
				}
			});
		});
	window.onload=authority;
	/* 初始用户角色checkbox */
	function authority(){
		var authoritiesJson=<%= request.getAttribute("authoritiesJson")%>;
		for(var i=0;i<authoritiesJson.authorities.length;i++){
			var input = document.getElementsByTagName("input");
			for (var j = 0; j < input.length; j++) {
				if(input[j].type == "checkbox"){
					if(input[j].value==authoritiesJson.authorities[i]){
						input[j].checked=true;//已有角色初始选中
					}
				}
			}
		};
	};
	</script>
</body>
</html>