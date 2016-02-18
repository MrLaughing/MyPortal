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
					<div>
						<p><b>回收</b></p>
						<c:forEach items="${requestScope.allAuthorities }" var="authority" >
							<c:if test="${authority.type == '回收' }">
							<span style="display:inline-block;width:32%;"><input id="authorities" name="authorities" value="${authority.id }" type="checkbox">${authority.description }</span>
							</c:if>
						</c:forEach>
						<p><b>客服</b></p>
						<c:forEach items="${requestScope.allAuthorities }" var="authority" >
							<c:if test="${authority.type == '客服' }">
							<span style="display:inline-block;width:32%;"><input id="authorities" name="authorities" value="${authority.id }" type="checkbox">${authority.description }</span>
							</c:if>
						</c:forEach>
						<p><b>商城</b></p>
						<c:forEach items="${requestScope.allAuthorities }" var="authority" >
							<c:if test="${authority.type == '商城' }">
							<span style="display:inline-block;width:32%;"><input id="authorities" name="authorities" value="${authority.id }" type="checkbox">${authority.description }</span>
							</c:if>
						</c:forEach>
						<p><b>市场</b></p>
						<c:forEach items="${requestScope.allAuthorities }" var="authority" >
							<c:if test="${authority.type == '市场' }">
							<span style="display:inline-block;width:32%;"><input id="authorities" name="authorities" value="${authority.id }" type="checkbox">${authority.description }</span>
							</c:if>
						</c:forEach>
						<p><b>推广</b></p>
						<c:forEach items="${requestScope.allAuthorities }" var="authority" >
							<c:if test="${authority.type == '推广' }">
							<span style="display:inline-block;width:32%;"><input id="authorities" name="authorities" value="${authority.id }" type="checkbox">${authority.description }</span>
							</c:if>
						</c:forEach>
						<p><b>交互</b></p>
						<c:forEach items="${requestScope.allAuthorities }" var="authority" >
							<c:if test="${authority.type == '交互' }">
							<span style="display:inline-block;width:32%;"><input id="authorities" name="authorities" value="${authority.id }" type="checkbox">${authority.description }</span>
							</c:if>
						</c:forEach>
						<p><b>导表</b></p>
						<c:forEach items="${requestScope.allAuthorities }" var="authority" >
							<c:if test="${authority.type == '导表' }">
							<span style="display:inline-block;width:32%;"><input id="authorities" name="authorities" value="${authority.id }" type="checkbox">${authority.description }</span>
							</c:if>
						</c:forEach>
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