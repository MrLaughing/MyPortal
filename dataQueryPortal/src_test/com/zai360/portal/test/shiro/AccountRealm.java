package com.zai360.portal.test.shiro;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.zai360.portal.test.captcha.CaptchaException;
import com.zai360.portal.test.captcha.CaptchaServlet;
import com.zai360.portal.test.service.AccountService;
import com.zai360.portal.test.vo.Admin;
import com.zai360.portal.test.vo.Authority;
import com.zai360.portal.test.vo.Role;

public class AccountRealm extends AuthorizingRealm {
	@Autowired
	private AccountService accountService;
/*	private static final Logger log = org.slf4j.LoggerFactory.getLogger(AccountRealm.class);*/
//	@Override
//	public String getName() {
//		return "laugh";
//	}
	
	/***
	 * 获取身份认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken arg0) {
		UsernamePasswordCaptchaToken token = (UsernamePasswordCaptchaToken) arg0;
		// 通过表单接收的用户名 这里不需要验证密码，只需要将syuser对象放入SimpleAuthenticationInfo
		String username = token.getUsername();
		String ip=token.getHost();//ip地址信息存储？
		if (username != null && !"".equals(username)) {//判断表单中是否输入用户名
			//增加判断验证码逻辑
			String captcha=token.getCaptcha();//用户输入的验证码
			String existcode=(String) SecurityUtils.getSubject().getSession().getAttribute(CaptchaServlet.KEY_CAPTCHA);//服务器生成的验证码
			if(existcode!=null){//若服务器未生成验证码,不比较。??(用于oauth2和简易的登录接口)
				if (captcha!=null&&!captcha.equalsIgnoreCase(existcode)) {
					throw new CaptchaException("验证码错误");
				}
			}
			Admin admin = this.accountService.findAdmin(username);//将按照表单username查询结果存入SimpleAuthenticationInfo
			if (admin != null) {
				return new SimpleAuthenticationInfo(admin.getUsername(),admin.getPassword(), getName());
			}
		}
		return null;
	}
	
	/***
	 * 获取授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
//		String username = (String) arg0.fromRealm(getName()).iterator().next();
		String username = (String)arg0.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		List<Role> roles = accountService.findRole(username);// 获取角色list
		List<String> rolenames=new ArrayList<String>();//角色名 list
		Iterator<Role> it=roles.iterator();
		while(it.hasNext()){
			rolenames.add(it.next().getName());
		}
		authorizationInfo.addRoles(rolenames);//添加角色
		for (String rolename : rolenames) {
			List<Authority> role_authority = accountService.findAuthority(rolename);// 获取资源
			List<String> authorities=new ArrayList<String>();//角色id list
			Iterator<Authority> it1=role_authority.iterator();
			while(it1.hasNext()){
				authorities.add(it1.next().getName());//获取角色权限
			}
			authorizationInfo.addStringPermissions(authorities);//添加权限
		}
		return authorizationInfo;
	}
	
	@Override
	protected void clearCachedAuthorizationInfo(PrincipalCollection principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}
	
}
