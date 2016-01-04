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
<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>反馈意见</title>
<link href="../css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="../lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="../lib/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#push")
				.on(
						"click",
						function() {
							var comment = document
									.getElementById("comment").value;
							if (comment != "") {
								document.getElementById("form1").action = "<%=basePath%>comment/comment_sendMessage.action";
								document.getElementById("form1").submit();
								alert("感谢您的反馈！");
							} else {
								alert('您不写点什么吗？T_T');
							}
						});
	});
</script>
</head>
<body>
	<div align="center">
	<b>请写下查询中遇到的问题和意见</b><br>
		<form action="" method="post" id="form1">
			<textarea style="width: 600px; height: 150px" name="comment" id="comment"></textarea><br><br>
			<a href="javascript:;" name="push" class="btn btn-success"
				id="push"> <i class="Hui-iconfont">&#xe692;</i> 提交反馈
			</a>
		</form>
	</div>
</body>
</html>