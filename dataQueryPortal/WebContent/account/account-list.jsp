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
	function formatOper(value,row,index){  
	    return "<a style='text-decoration:none' class='ml-5' onClick='editUser("+index+")' title='编辑' href='javascript:;'>"
	    +"<i class='Hui-iconfont'>&#xe6df;</i></a><a style='text-decoration:none' class='ml-5' onClick='delUser(this,"+index+")' "
	    +"title='删除' href='javascript:;'><i class='Hui-iconfont'>&#xe6e2;</i></a>";  
	}
	/*删除用户*/
	function delUser(obj,index){
		$('#table').datagrid('selectRow',index);// 关键在这里  
	    var row = $('#table').datagrid('getSelected'); //拿到该行数据
		layer.confirm("确认要删除吗？",function(index){
			//此处请求后台程序，下方是成功后的前台处理……
			<%-- document.location.href="<%=basePath%>account/account_deleteAccount.action?username="+row.username; --%>
			$.ajax({
				type:'post',
				data:{username:row.username},
				url:'<%=basePath%>account/account_deleteAccount.action',
				success:function(result){
					if(result=='删除成功'){
					$('#table').datagrid('reload');//删除成功后刷新表格
					}
				}
			});
			$(obj).parents("tr").remove();
			layer.msg('已删除!',{icon:1,time:1000});
		});
	}
	/* 编辑用户 */
    function editUser(index){  
        $('#table').datagrid('selectRow',index);// 关键在这里  
        var row = $('#table').datagrid('getSelected'); //拿到该行数据
        if (row){  
        	layer_show('编辑用户','<%=basePath%>account/account_findaccount.action?username='+row.username,'500','450');
        }  
    }  
	function find() {
		if (true) {
			$('#table').datagrid({
				url : "<%=basePath%>account/account_findAccounts.action",
				method : "post",//请求方式
				title : "用户列表",//标题
				collapsible : true,//是否可以折叠
				fitColumns : false,//自动适应列宽
				striped : true,//隔行变色
				loadMsg : "加载中...",//加载时的提示信息
				pagination : true,//分页
				rownumbers : true,//显示序号
				singleSelect : true,//单选，只能选择一行
				queryParams : {
					username : $("#username").val(),
					department : $("#department").val(),
				},//queryParams 传送的额外参数
				columns : [ [ {
					field : 'username',
					title : '用户名'
				}, {
					field : 'name',
					title : '姓名'
				}, {
					field : 'email',
					title : 'E-mail'
				}, {
					field : 'department',
					title : '部门'
				} , {
					field : 'isEnabled',
					title : '是否启用'
				} , {
					field : 'isLocked',
					title : '是否锁定'
				} , {
					field : 'lockedDate',
					title : '锁定日期'
				} , {
					field : 'loginDate',
					title : '最后登录日期'
				} , {
					field : 'loginIp',
					title : '最后登录IP'
				},{
					field : 'id',
					title : '操作',
					formatter : formatOper
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
	
	};
</script>
<title>用户管理</title>
</head>
<body>
	<nav class="breadcrumb"> <i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>系统管理 <span class="c-gray en">&gt;</span>
	用户管理 <a class="btn btn-success radius r mr-20"
		style="line-height: 1.6em; margin-top: 3px"
		href="javascript:location.replace(location.href);" title="刷新"><i
		class="Hui-iconfont">&#xe68f;</i>刷新</a> </nav>
	<div class="pd-20">
		<div class="text-c">
			<form action="" id="form1" name="form1" method="post">
				部门：<span class="select-box inline"> <select name="department"
					id="department" class="select" size="1">
						<option selected="selected" value="">--未选择--</option>
						<option value="管理员">管理员</option>
						<option value="回收">回收</option>
						<option value="客服">客服</option>
						<option value="商城">商城</option>
						<option value="市场">市场</option>
						<option value="推广">推广</option>
						<option value="地推">地推</option>
				</select>
				</span> <span>&nbsp&nbsp&nbsp&nbsp</span> 用户名：<input type="text"
					name="username" id="username" style="width: 120px"
					class="input-text" /> <span>&nbsp&nbsp&nbsp&nbsp</span> <a
					href="javascript:;" name="find" id="find" class="btn btn-success"
					onclick="find()"> <i class="Hui-iconfont">&#xe665;</i> 查询
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