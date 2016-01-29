package com.zai360.portal.test.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.zai360.portal.test.captcha.CaptchaException;
import com.zai360.portal.test.captcha.CaptchaServlet;
import com.zai360.portal.test.interceptor.ResponseInfo;
import com.zai360.portal.test.service.AccountService;
import com.zai360.portal.test.shiro.UsernamePasswordCaptchaToken;
import com.zai360.portal.test.util.Sql4Account;
import com.zai360.portal.test.util.UrlUtil;
import com.zai360.portal.test.util.jsonUtil;

public class LoginAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	@Autowired
	private AccountService accountService;
	private ResponseInfo result;
	/**
	 * 登录入口
	 * @throws UnsupportedEncodingException 
	 */
	public String login() throws UnsupportedEncodingException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String,String[]> newparametersmap =UrlUtil.decodeurl(request);
		String username = newparametersmap.get("username")[0];
		String password = newparametersmap.get("password")[0];
		
//		String username = request.getParameter("username");
//		String password = request.getParameter("password");
		String captchacode =(String) request.getSession().getAttribute(CaptchaServlet.KEY_CAPTCHA);
		boolean rememberMe = request.getParameter("rememberMe") != null;// 记住密码
		String login_ip = request.getRemoteAddr();// 接收请求端的IP地址
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordCaptchaToken token = new UsernamePasswordCaptchaToken(
				username, password, rememberMe, login_ip,captchacode);
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
		request.getSession().setAttribute("authorizationCode", jsonUtil.generateRandomCode(10));//模拟生成10位授权码，存入session中
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
		jsonUtil.authorizationCode="清除授权码";
		// currentUser.logout(); //报错！栈溢出
		return "logout";
	}
	/*************************************************/
	public ResponseInfo getResult() {
		return result;
	}
	public void setResult(ResponseInfo result) {
		this.result = result;
	}
}
