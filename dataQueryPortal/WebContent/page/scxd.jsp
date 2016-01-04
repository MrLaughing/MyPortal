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
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
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
function find() {
	var createDate =  $("#createDate").val();
	var completeTime_min =  $("#completeTime_min").val();
	var completeTime_max =  $("#completeTime_max").val();
	if (createDate!=""&&completeTime_min!=""&&completeTime_max !="") {
		$('#table').datagrid({
			url : "../find/findAction_search.action?serialVersionUID=<%=request.getParameter("serialVersionUID")%>",
			method : "post",//请求方式
			title : "首次下单信息",//标题
			collapsible : true,//是否可以折叠
			striped : true,//隔行变色
			fitColumns : false,//自动使列适应表格宽度
			loadMsg : "客官，店小二正在为您加载中...",//加载时的提示信息
			pagination : true,//分页
			rownumbers : true,//显示序号
			singleSelect : true,//单选，只能选择一行
			queryParams : {
				createDate:$("#createDate").val(),
				completeTime_min : $("#completeTime_min").val(),
				completeTime_max : $("#completeTime_max").val(),
			},//queryParams 传送的额外参数
			frozenColumns:[ [ {
				field : '订单编号',
				title : '订单编号'
				}
			    ] ],
			columns : [ [{
				field : '订单总金额',
				title : '订单总金额'
			}, {
				field : '收货人',
				title : '收货人'
			}, {
				field : '手机号',
				title : '手机号'
			},{
				field : '地址',
				title : '地址'
			},{
				field : '所属基站',
				title : '所属基站'
			},{
				field : '完成时间',
				title : '完成时间'
			}] ]
		});
		//设置分页控件 
		var pager = $('#table').datagrid('getPager');
		$(pager).pagination({
			pageList : [ 10, 15, 20, 30 ],//可以设置每页记录条数的列表 
			beforePageText : '第',//页数文本框前显示的汉字 
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	}else{
		alert("请完善查询日期");
	}

}

	$(function() {
		$("#export")
				.on(
						"click",
						function() {
							var createDate = document.getElementById("createDate").value;
							var completeTime_min = document.getElementById("completeTime_min").value;
							var completeTime_max = document.getElementById("completeTime_max").value;
							if (createDate != "" && completeTime_min != ""&& completeTime_max != "") {
								document.getElementById("form1").action = "../distribute/distribute_distribute.action?serialVersionUID=<%= request.getParameter("serialVersionUID")%>";
								document.getElementById("form1").submit();
							} else {
								alert("请完善查询日期");
							}
						});
	});
</script>
<title>地推信息</title>
</head>
<body>
	<nav class="breadcrumb"> <i class="Hui-iconfont">&#xe67f;</i> <a
		href="../welcome.html">首页</a> <span class="c-gray en">&gt;</span>常用查询
	<span class="c-gray en">&gt;</span> 首次下单信息 <a
		class="btn btn-success radius r mr-20"
		style="line-height: 1.6em; margin-top: 3px"
		href="javascript:location.replace(location.href);" title="刷新"><i
		class="Hui-iconfont">&#xe68f;</i>刷新</a> </nav>
	<div class="pd-20">
		<div class="text-c">
			<form action="" name="form1" id="form1" method="post">
				下单起始日期： <input type="text"
					onfocus="WdatePicker({maxDate:'%y-%M-%d'})" name="createDate"
					id="createDate" class="input-text Wdate" placeholder="例如2015-02-14"
					style="width: 140px;"> 订单完成日期：<input type="text"
					onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'completeTime_max\')||\'%y-%M-%d\'}'})"
					name="completeTime_min" id="completeTime_min"
					class="input-text Wdate" placeholder="如2015-01-26"
					style="width: 140px;"> - <input type="text"
					onfocus="WdatePicker({minDate:'#F{$dp.$D(\'completeTime_min\')}',maxDate:'%y-%M-%d'})"
					name="completeTime_max" id="completeTime_max"
					class="input-text Wdate" placeholder="如2015-02-02"
					style="width: 140px;">
				<a href="javascript:;" name="find" id="find" class="btn btn-success"
					onclick="find()"> <i class="Hui-iconfont">&#xe665;</i>
					查询
				</a> 
				<a href="javascript:;" name="export" id="export"
					class="btn btn-success"> <i class="Hui-iconfont">&#xe665;</i>
					导出excel
				</a>
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