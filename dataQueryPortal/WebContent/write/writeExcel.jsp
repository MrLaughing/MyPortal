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
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="../css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="../css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<link href="../lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="../lib/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="../lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="../lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="../lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../js/H-ui.js"></script>
<script type="text/javascript" src="../js/H-ui.admin.js"></script>
<%@ include file="../util/easyui.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#upload")
				.on(
						"click",
						function() {
							var uploadexcel = document.getElementById("uploadexcel").value;
							if (uploadexcel!=""&&uploadexcel!="该表已存在，请勿再次创建！") {							
								document.getElementById("form1").action = "<%=basePath%>write/writeAction_write.action";
								document.getElementById("form1").submit();
							} else {
								alert("请上传文件");
							}
						});
	});
</script>
<title>读取excel</title>
</head>
<body>
	<nav class="breadcrumb"> <i class="Hui-iconfont">&#xe67f;</i> <a
		href="<%=basePath%>welcome.jsp">首页</a> <span class="c-gray en">&gt;</span>上传管理
	<span class="c-gray en">&gt;</span> 读取文件<a
		class="btn btn-success radius r mr-20"
		style="line-height: 1.6em; margin-top: 3px"
		href="javascript:location.replace('<%=basePath%>write/writeExcel.jsp');" title="刷新"><i
		class="Hui-iconfont">&#xe68f;</i>刷新</a> </nav>
	<div class="pd-20">
		<b>读取excel中的数据，将在数据库中创建包含excel内容的同名表</b>
		<div class="text-c">
			<form action="" name="form1" id="form1" method="post"
				enctype="multipart/form-data">
				<span class="btn-upload form-group"> <input
					class="input-text upload-url" type="text" name="uploadexcel"
					id="uploadexcel" readonly datatype="*" nullmsg="请添加附件！"
					value="${requestScope.msg }" style="width: 200px"> <a href="javascript:void();"
					class="btn btn-primary upload-btn"><i class="Hui-iconfont">&#xe642;</i>
						浏览文件</a> <input type="file" name="excel" id="excel" class="input-file">
				</span>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp <a href="javascript:;"
					name="upload" class="btn btn-success" id="upload"><i
					class="Hui-iconfont">&#xe6a7;</i> 提交 </a>
			</form>
		</div>
	</div>
	<div class="easyui-layout" data-options="fit:true"
		data-options="fit:true,region:'center',border:true">
		<div
			style="width: 1000px; margin-left: auto; margin-right: auto; overflow: hidden;">
			<table id="table" style="height: 550px;">
			</table>
		</div>
	</div>
</body>
</html>