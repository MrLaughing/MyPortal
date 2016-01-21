<%@page import="com.zai360.portal.test.util.PathUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	List huishouList = PathUtil.huishouList();
		request.setAttribute("huishouList", huishouList);

	List kefuList = PathUtil.kefuList();
		request.setAttribute("kefuList", kefuList);

	List shangchengList = PathUtil.shangchengList();
		request.setAttribute("shangchengList", shangchengList);

	List shichangList = PathUtil.shichangList();
		request.setAttribute("shichangList", shichangList);

	List tuiguangList = PathUtil.tuiguangList();
		request.setAttribute("tuiguangList", tuiguangList);
%>
<%
	/*部门交互查询  */
	List jiaohuList = PathUtil.jiaohuList();
	request.setAttribute("jiaohuList", jiaohuList);
%>
<%
	/*额外查询  */
	List ewaiList = PathUtil.ewaiList();
	request.setAttribute("ewaiList", ewaiList);
%>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<LINK rel="Bookmark" href="/favicon.ico">
<LINK rel="Shortcut Icon" href="/favicon.ico" />

<!-- <script type="text/javascript" src="lib/PIE_IE678.js"></script>  -->
<script type="text/javascript" src="lib/jquery/jquery.js"></script>
<script type="text/javascript" src="lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="js/H-ui.js"></script>
<script type="text/javascript" src="js/H-ui.admin.js"></script>
<!-- <script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script> -->
<link href="css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="skin/default/skin.css" rel="stylesheet" type="text/css"
	id="skin" />
<link href="lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet"
	type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />

<title>再生活（北京）信息技术有限公司</title>
</head>
<body>
	<header class="Hui-header cl"> <a class="Hui-logo l"
		title="H-ui.admin v2.3"
		href="javascript:location.replace(location.href);">再生活</a> <span
		class="Hui-subtitle l">V1.0</span>
		<ul class="Hui-userbar">
			<li class="dropDown dropDown_hover"><a href="#"
				class="dropDown_A"><shiro:principal></shiro:principal> <i
				class="Hui-iconfont">&#xe6d5;</i></a>
			<ul class="dropDown-menu radius box-shadow">
				<li><a href="#">个人信息</a></li>
				<li><a href="<%=basePath%>account/account_logout.action">退出</a></li>
			</ul></li>
		<li id="Hui-skin" class="dropDown right dropDown_hover"><a
			href="javascript:;" title="换肤"><i class="Hui-iconfont"
				style="font-size: 18px">&#xe62a;</i></a>
			<ul class="dropDown-menu radius box-shadow">
				<li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
				<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
				<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
				<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
				<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
				<li><a href="javascript:;" data-val="orange" title="绿色">橙色</a></li>
			</ul></li>
		</ul>
		<a aria-hidden="false" class="Hui-nav-toggle" href="#"></a> 
	</header>
	<aside class="Hui-aside"> <input runat="server"
		id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2">
		<shiro:hasAnyRoles name="后台导表--回收人员,后台导表--管理员">
			<dl id="menu-article">
				<dt>
					<i class="Hui-iconfont">&#xe653;</i>回收相关查询<i
						class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<c:forEach items="${requestScope.huishouList }" var="var"
							varStatus="id">
							<li><a
								_href="page/${var.path}?serialVersionUID=${var.serialVersionUID}"><i
									class="Hui-iconfont">&#xe64b;</i>${id.index+1}.${var.name}</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="后台导表--客服人员,后台导表--管理员">
			<dl id="menu-article">
				<dt>
					<i class="Hui-iconfont">&#xe6d0;</i>客服相关查询<i
						class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<c:forEach items="${requestScope.kefuList }" var="var"
							varStatus="id">
							<li><a
								_href="page/${var.path}?serialVersionUID=${var.serialVersionUID}"><i
									class="Hui-iconfont">&#xe64b;</i>${id.index+1}.${var.name}</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="后台导表--商城人员,后台导表--管理员">
			<dl id="menu-article">
				<dt>
					<i class="Hui-iconfont">&#xe66a;</i>商城相关查询<i
						class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<c:forEach items="${requestScope.shangchengList }" var="var"
							varStatus="id">
							<li><a
								_href="page/${var.path}?serialVersionUID=${var.serialVersionUID}"><i
									class="Hui-iconfont">&#xe64b;</i>${id.index+1}.${var.name}</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="后台导表--市场人员,后台导表--管理员">
			<dl id="menu-article">
				<dt>
					<i class="Hui-iconfont">&#xe627;</i>市场相关查询<i
						class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<c:forEach items="${requestScope.shichangList }" var="var"
							varStatus="id">
							<li><a
								_href="page/${var.path}?serialVersionUID=${var.serialVersionUID}"><i
									class="Hui-iconfont">&#xe64b;</i>${id.index+1}.${var.name}</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="后台导表--推广人员,后台导表--管理员,后台导表--地推负责人,后台导表--地推队长">
			<dl id="menu-article">
				<dt>
					<i class="Hui-iconfont">&#xe6b6;</i>推广相关查询<i
						class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<c:forEach items="${requestScope.tuiguangList }" var="var"
							varStatus="id">
							<li><a
								_href="page/${var.path}?serialVersionUID=${var.serialVersionUID}"><i
									class="Hui-iconfont">&#xe64b;</i>${id.index+1}.${var.name}</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles
			name="后台导表--回收人员,后台导表--客服人员,后台导表--商城人员,后台导表--市场人员,后台导表--推广人员,
			后台导表--管理员,后台导表--地推负责人,后台导表--地推队长">
			<dl id="menu-article">
				<dt>
					<i class="Hui-iconfont">&#xe611;</i>部门交互查询<i
						class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<c:forEach items="${requestScope.jiaohuList }" var="var"
							varStatus="id">
							<li><a
								_href="page/${var.path}?serialVersionUID=${var.serialVersionUID}"><i
									class="Hui-iconfont">&#xe64b;</i>${id.index+1}.${var.name}</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles
			name="仅查各基站出差人数,仅查首次上门违约用户,仅查停用用户数,仅查电话购物信息,仅查app下单信息,仅查未发放优惠券用户,
			仅查基站推广人数,仅查地推注册明细,仅查地推推荐服务日期,仅查查询用户信息,仅查重复地址用户信息,仅查客户注销原因">
			<dl id="menu-article">
				<dt>
					<i class="Hui-iconfont">&#xe681;</i>额外查询<i
						class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<c:forEach items="${requestScope.ewaiList }" var="var"
							varStatus="id">
							<li><a
								_href="page/${var.path}?serialVersionUID=${var.serialVersionUID}"><i
									class="Hui-iconfont">&#xe64b;</i>${id.index+1}.${var.name}</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>
		</shiro:hasAnyRoles>
		<dl id="menu-comments">
			<dt>
				<i class="Hui-iconfont">&#xe622;</i> 评论管理<i
					class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
			</dt>
			<dd>
				<ul>
					<li><a _href="comment/feedback-list.jsp"
						href="javascript:void(0)"><i class="Hui-iconfont">&#xe691;</i>意见反馈</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-write">
			<dt>
				<i class="Hui-iconfont">&#xe6dc;</i> 上传管理<i
					class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
			</dt>
			<dd>
				<ul>
					<li><a _href="write/writeExcel.jsp" href="javascript:void(0)"><i
							class="Hui-iconfont">&#xe642;</i>读取文件</a></li>
				</ul>
			</dd>
		</dl>
		<shiro:hasRole name="后台导表--管理员">
			<dl id="menu-member">
				<dt>
					<i class="Hui-iconfont">&#xe63c;</i> 系统管理<i
						class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<li><a _href="signup/signup-account.jsp" href="javascript:;">用户注册</a></li>
						<li><a _href="account/account-list.jsp" href="javascript:;">用户管理</a></li>
						<li><a _href="role/role-list.jsp" href="javascript:;">角色管理</a></li>
						<li><a _href="authority/authority-list.jsp"
							href="javascript:void(0)">权限管理</a></li>
					</ul>
				</dd>
			</dl>
		</shiro:hasRole>
	</div>
	</aside>
	<div class="dislpayArrow">
		<a class="pngfix" href="javascript:void(0);"
			onClick="displaynavbar(this)"></a>
	</div>
	<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active"><span data-href="welcome.jsp">我的桌面</span><em></em></li>
			</ul>
		</div>
		<div class="Hui-tabNav-more btn-group">
			<a id="js-tabNav-prev" class="btn radius btn-default size-S"
				href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a
				id="js-tabNav-next" class="btn radius btn-default size-S"
				href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a>
		</div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display: none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="welcome.jsp"></iframe>
		</div>
	</div>
	</section>
</body>
</html>