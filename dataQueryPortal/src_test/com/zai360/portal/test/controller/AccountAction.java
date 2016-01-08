package com.zai360.portal.test.controller;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ndktools.javamd5.Mademd5;
import com.opensymphony.xwork2.ActionSupport;
import com.zai360.portal.test.captcha.CaptchaException;
import com.zai360.portal.test.service.AccountService;
import com.zai360.portal.test.shiro.UsernamePasswordCaptchaToken;
import com.zai360.portal.test.util.Md5Util;
import com.zai360.portal.test.util.Page;
import com.zai360.portal.test.util.Sql4Account;
import com.zai360.portal.test.util.jsonUtil;
import com.zai360.portal.test.vo.Admin;
import com.zai360.portal.test.vo.Role;
import com.zai360.portal.test.vo.Role_authority;

/**
 * 账户相关
 * 
 * @author report
 *
 */
@Controller
public class AccountAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	@Autowired
	private AccountService accountService;
	// 返回流对象（用于AJAX和文件下载）
	private InputStream inputStream;
	private Page page;

	/**
	 * 登录入口
	 */
	public String login() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String captchacode = request.getParameter("captchacode");// 接收验证码
		boolean rememberMe = request.getParameter("rememberMe") != null;// 记住密码
		String login_ip = request.getRemoteAddr();// 接收请求端的P地址
		password=Md5Util.toMd5(password);// MD5加密 字母小写
		ThreadContext.bind(SecurityUtils.getSubject()); //
		Subject currentUser = SecurityUtils.getSubject();

		UsernamePasswordCaptchaToken token = new UsernamePasswordCaptchaToken(
				username, password, rememberMe, login_ip, captchacode);
		try {
			currentUser.login(token);// 登录 这里会回调reaml里的一个方法 AuthenticationInfo
										// doGetAuthenticationInfo()
		} catch (UnknownAccountException uae) {
			request.setAttribute("msg", "用户不存在");
			return "error";
		} catch (IncorrectCredentialsException ice) {
			request.setAttribute("msg", "密码不正确");
			return "error";
		} catch (UnauthorizedException ue) {
			request.setAttribute("msg", "没有权限");
			return "error";
		} catch (CaptchaException ce) {
			request.setAttribute("msg", "验证码错误");
			return "error";
		} catch (AuthenticationException e) {
			request.setAttribute("msg", "其它错误");
			return "error";
		}
		// 更新用户信息
		Date now = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String login_date = dateformat.format(now);
		StringBuffer updatesql = Sql4Account.updateAdmin(username, login_ip,
				login_date);
		this.accountService.updateAccount(updatesql);// 更新用户登录ip和登录时间
		return SUCCESS;
	}

	/**
	 * 账户登出
	 * 
	 * @return
	 */
	public String logout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.getSession().touch();
		currentUser.getSession().stop();
		// currentUser.logout(); //报错！栈溢出
		return "logout";
	}

	/**
	 * 根据条件查询用户（分页）
	 * 
	 * @return
	 */
	public String findAccounts() {
		HttpServletRequest request = ServletActionContext.getRequest();
		int pageNumber = Integer.parseInt(request.getParameter("page"));// 当前页，页码
		int pageSize = Integer.parseInt(request.getParameter("rows"));// 每页记录数
		this.page = this.accountService.findPage(
				Sql4Account.findAccountsTotal(), "account.findAccounts",
				Sql4Account.findAccounts(pageNumber, pageSize), pageNumber,
				pageSize);

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();// Gson处理Date格式
		String content = gson.toJson(page.getContent());
		String info = "{\"total\":" + page.getTotalNumber() + ",\"rows\":"
				+ content + "}";// 拼接json数据
		this.inputStream = jsonUtil.string2stream(info);
		return "ajax";
	}
	/**
	 * 用户注册--1、添加用户
	 * 并返回用户的用户名
	 * @return
	 */
	public String signupAccount(){
		HttpServletRequest request= ServletActionContext.getRequest();
		String username=request.getParameter("username");//获取用户名（基本为工号）
		StringBuffer sql=Sql4Account.insertAccount();
		this.accountService.insertAccount(sql);//添加用户
		request.setAttribute("username", username);//将新注册用户的用户名返回给下级页面
		return "signupAccount";
	}
	/**
	 * 编辑用户： 1、根据用户名查询用户对象
	 * 
	 * @return
	 */
	public String findaccount() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		Admin account = this.accountService.findAdmin(username);
		request.setAttribute("account", account);// 存入request域中
		request.setAttribute("department", account.getDepartment());// 部门
		return "findaccount";

	}

	/**
	 * 编辑用户： 2、更新用户信息
	 * 
	 * @return
	 */
	public String updateAccount() {
		StringBuffer sql = Sql4Account.updateAccount();// 获取更新用户SQL
		this.accountService.updateAccount(sql);// 更新用户信息
		return "updatesuccess";
	}
	/**
	 * 删除用户
	 * @return
	 */
	public String deleteAccount(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String username=request.getParameter("username");
		Long id=this.accountService.findAdmin(username).getId();//获取用户id
		this.accountService.deleteRole(Sql4Account.deleteRole(id));//删除用户角色
		this.accountService.deleteAccount(Sql4Account.deleteAccount(username));//删除用户
		return null;//返回null,方法执行完后不执行任何跳转
	}

	/**
	 * 根据条件查询角色（分页）
	 * 
	 * @return
	 */
	public String findRoles() {
		HttpServletRequest request = ServletActionContext.getRequest();
		int pageNumber = Integer.parseInt(request.getParameter("page"));// 当前页，页码
		int pageSize = Integer.parseInt(request.getParameter("rows"));// 每页记录数
		StringBuffer rolestotalsql = Sql4Account.findRolesTotal();
		StringBuffer rolessql = Sql4Account.findRoles(pageNumber, pageSize);
		this.page = this.accountService.findPage(rolestotalsql,
				"account.findRoles", rolessql, pageNumber, pageSize);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();// Gson处理Date格式
		String content = gson.toJson(page.getContent());
		String info = "{\"total\":" + page.getTotalNumber() + ",\"rows\":"
				+ content + "}";// 拼接json数据
		this.inputStream = jsonUtil.string2stream(info);
		return "ajax";
	}

	/**
	 * 编辑用户角色： 1、根据用户名查询用户角色
	 * 同时查询同权限的其他额外角色id
	 * 
	 * @return
	 */
	public String findrole() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		List<Role> roles = this.accountService.findRole(username);
		Iterator<Role> it = roles.iterator();
		List<String> roleids = new ArrayList<String>();
		List<String> ewairoleids=new ArrayList<String>();
		while (it.hasNext()) {//遍历该用户的角色's
			Role role=it.next();//获取角色
			String id=String.valueOf(role.getId());// 获取角色id
			roleids.add(String.valueOf(role.getId()));
			if("基本角色".equals(role.getType())){//为用户筛选基本角色已有的权限（id）
				List<String> findAuthorityByIdList=new ArrayList<String>();
				findAuthorityByIdList=this.accountService.findAuthorityById(Sql4Account.findAuthorityById(id));
				ewairoleids.addAll(findAuthorityByIdList);
			}
		}
		request.setAttribute("username", username);// 存入request域中
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();// Gson处理Date格式
		String roleidsJson = gson.toJson(roleids);
		String ewairoleidsJson=gson.toJson(ewairoleids);
		roleidsJson = "{\"roleids\":" + roleidsJson + "}";// 拼接json数据
		ewairoleidsJson = "{\"ewairoleids\":" + ewairoleidsJson + "}";
		request.setAttribute("roleidsJson", roleidsJson);// 存入request域中
		request.setAttribute("ewairoleidsJson", ewairoleidsJson);
		return "findrole";

	}

	/**
	 * 编辑用户角色： 2、更新用户角色
	 * 
	 * 用户注册--2、添加角色
	 * 并返回用户的用户名
	 * @return
	 */
	public String updateRole() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Long id = this.accountService.findAdmin(
				request.getParameter("username")).getId();// 根据username查出adminid
		String[] roles = request.getParameterValues("roles");
		if (roles == null || (roles.length > 0 && roles[0] == "")) {
			return "norole";// 跳转norole页面
		} else {
			/* 1、删除用户原有角色 */
			StringBuffer deletesql = Sql4Account.deleteRole(id);
			this.accountService.deleteRole(deletesql);
			/* 2、重新添加用户角色 */
			StringBuffer insertsql = Sql4Account.insertRole(id);
			if (insertsql != null) {// 判断是否选择角色
				this.accountService.insertRole(insertsql);
				return "updatesuccess";
			} else {
				return "norole";// 跳转norole页面
			}
		}
	}

	/**
	 * 查询所有角色权限信息（分页）
	 * 
	 * @return
	 */
	public String findAuthorities() {
		HttpServletRequest request = ServletActionContext.getRequest();
		int pageNumber = Integer.parseInt(request.getParameter("page"));// 当前页，页码
		int pageSize = Integer.parseInt(request.getParameter("rows"));// 每页记录数
		StringBuffer authoritiestotalsql = Sql4Account.findAuthoritiesTotal();
		StringBuffer authoritiessql = Sql4Account.findAuthorities(pageNumber, pageSize);
		this.page = this.accountService
				.findPage(authoritiestotalsql, "account.findAuthorities",
						authoritiessql, pageNumber, pageSize);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();// Gson处理Date格式
		String content = gson.toJson(page.getContent());
		String info = "{\"total\":" + page.getTotalNumber() + ",\"rows\":"
				+ content + "}";// 拼接json数据
		this.inputStream = jsonUtil.string2stream(info);
		return "ajax";
	}

	/**
	 * 编辑角色权限： 1、根据角色查询权限信息
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String findauthority() throws UnsupportedEncodingException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String role = request.getParameter("role");
		role = new String(role.getBytes("iso-8859-1"), "utf-8");// 将ASCII解码，ISO-8859-1编码是单字节编码，向下兼容ASCII
		List<Role_authority> role_authorities = this.accountService
				.findAuthority(role);
		Iterator<Role_authority> it = role_authorities.iterator();
		List<String> authorities = new ArrayList<String>();
		while (it.hasNext()) {
			authorities.add(it.next().getAuthorities());// 获取角色权限
		}
		request.setAttribute("role", role);// 存入request域中
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();// Gson处理Date格式
		String authoritiesJson = gson.toJson(authorities);
		authoritiesJson = "{\"authorities\":" + authoritiesJson + "}";// 拼接json数据
		request.setAttribute("authoritiesJson", authoritiesJson);// 存入request域中
		return "findauthority";
	}

	/**
	 * 编辑角色权限： 2、更新角色的权限
	 * 
	 * @return
	 */
	public String updateAuthority() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Long id = this.accountService.findRoleByRoleName(
				request.getParameter("role")).getId();// 根据rolename查出roleid
		String[] authorities = request.getParameterValues("authorities");
		if (authorities == null
				|| (authorities.length > 0 && authorities[0] == "")) {
			return "noauthority";// 跳转noauthority页面
		} else {
			/* 1、删除角色原有权限 */
			StringBuffer deletesql = Sql4Account.deleteAuthority(id);
			this.accountService.deleteAuthority(deletesql);
			/* 2、重新添加角色权限 */
			StringBuffer insertsql = Sql4Account.insertAuthority(id);
			if (insertsql != null) {// 判断是否选择权限
				this.accountService.insertAuthority(insertsql);
				return "updatesuccess";
			} else {
				return "noauthority";// 跳转norole页面
			}
		}
	}

	/********************************************/
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
