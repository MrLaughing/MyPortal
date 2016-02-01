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
	function submitjson() {
		var order_status = new Array();
		$("input[name='order_status']:checked").each(function(i) {
			order_status[i] = $(this).val();
		});
		order_status = decodeURIComponent(order_status, true);//获取checkbox中的多个order_status
		var source_type = new Array();
		$("input[name='source_type']:checked").each(function(i) {
			source_type[i] = $(this).val();
		});
		source_type = decodeURIComponent(source_type, true);//获取checkbox中的多个source_type
		$.ajax({
			type : "post",
			url : "../find/findAction_search.action?serialVersionUID=<%=request.getParameter("serialVersionUID")%>",
			data : {
				datetime_min : $("#datetime_min").val(),
				datetime_max : $("#datetime_max").val(),
				order_status : order_status,
				source_type : source_type,
				page:1,
				rows:10
			},
			dataType : "json",
			success : function(result) {
			alert(result);
			}
		});

	}
	function find() {
		var order_status = new Array();
		$("input[name='order_status']:checked").each(function(i) {
			order_status[i] = $(this).val();
		});
		order_status = decodeURIComponent(order_status, true);//获取checkbox中的多个order_status
		var source_type = new Array();
		$("input[name='source_type']:checked").each(function(i) {
			source_type[i] = $(this).val();
		});
		source_type = decodeURIComponent(source_type, true);//获取checkbox中的多个source_type
		var datetime_min =  $("#datetime_min").val();
		var datetime_max =  $("#datetime_max").val();
		if (datetime_min!=""&&datetime_max !="") {
			$('#table').datagrid({
				url : "../find/findAction_search.action?serialVersionUID=<%=request.getParameter("serialVersionUID")%>",
				method : "post",//请求方式
				title : "app下单信息",//标题
				collapsible : true,//是否可以折叠
				fitColumns : false,//自动适应列宽
				striped : true,//隔行变色
				loadMsg : "客官，店小二正在为您加载中...",//加载时的提示信息
				pagination : true,//分页
				rownumbers : true,//显示序号
				singleSelect : true,//单选，只能选择一行
				queryParams : {
					datetime_min : $("#datetime_min").val(),
					datetime_max : $("#datetime_max").val(),
					order_status : order_status,
					source_type : source_type
				},//queryParams 传送的额外参数
				columns : [ [ {
					field : "总金额",
					title : '总金额'
				}, {
					field : '手机号',
					title : '手机号'
				}, {
					field : '订单',
					title : '订单'
				}, {
					field : '客户端',
					title : '客户端'
				} ] ]
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
							var datetime_min = document.getElementById("datetime_min").value;
							var datetime_max = document.getElementById("datetime_max").value;
							if (datetime_min!=""&&datetime_max !="") {							
								document.getElementById("form1").action = "../distribute/distribute_distribute.action?serialVersionUID=<%=request.getParameter("serialVersionUID")%>";
								document.getElementById("form1").submit();
							} else {
								alert("请完善查询日期");
							}
						});
	});
</script>
<title>app下单信息</title>
</head>
<body>
	<nav class="breadcrumb"> <i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>常用查询 <span class="c-gray en">&gt;</span>
	app下单信息 <a class="btn btn-success radius r mr-20"
		style="line-height: 1.6em; margin-top: 3px"
		href="javascript:location.replace(location.href);" title="刷新"><i
		class="Hui-iconfont">&#xe68f;</i>刷新</a> </nav>
	<div class="pd-20">
		<div class="text-c">
			<form action="" id="form1" name="form1" method="post">
				下单时间： <input type="text"
					onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'datetime_max\')||\'%y-%M-%d\'}'})"
					name="datetime_min" id="datetime_min"
					class="input-text Wdate" placeholder="2015-08-03"
					style="width: 120px;"> - <input type="text"
					onfocus="WdatePicker({minDate:'#F{$dp.$D(\'datetime_min\')}',maxDate:'%y-%M-%d'})"
					name="datetime_max" id="datetime_max"
					class="input-text Wdate" placeholder="2015-08-20"
					style="width: 120px;"> 订单状态： <input type="checkbox"
					name="order_status" value="0">未确认 <input type="checkbox"
					name="order_status" value="1">已确认 <input type="checkbox"
					name="order_status" value="2">已完成<span>&nbsp&nbsp&nbsp&nbsp</span>
				终端： <input type="checkbox" name="source_type" value="0">iOS
				<input type="checkbox" name="source_type" value="1">Android
				<input type="checkbox" name="source_type" value="2">微信
				<a href="javascript:;" name="find" id="find" class="btn btn-success"
					onclick="find()"> <i class="Hui-iconfont">&#xe665;</i> 查询
				</a> <a href="javascript:;" name="export" class="btn btn-success"
					id="export"> 导出excel
				</a>
			</form>
		</div>
	</div>
	<div class="easyui-layout" data-options="fit:true"
		data-options="fit:true,region:'center',border:true">
		<div style="width:1000px; margin-left: auto;margin-right: auto; overflow: hidden;">
			<table id="table" style="height: 550px;">
			</table>
		</div>
	</div>
</body>
</html>