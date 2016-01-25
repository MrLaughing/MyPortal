package com.zai360.portal.test.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.zai360.portal.test.util.jsonUtil;

/**
 * 拦截器，判断是否登录及授权
 * @author Laughing_Lz
 * @date 2016年1月14日
 */
public class Myinterceptor implements Interceptor {

	private static final long serialVersionUID = 1L;
	@Override
	public void destroy() {
		System.out.println("Myinterceptor销毁");
	}
	@Override
	public void init() {
		System.out.println("Myinterceptor初始化");
	}
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		System.out.println("初始后先走拦截器");
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		//首先判断是否登录
		Map<String,Object> sessionmap=invocation.getInvocationContext().getSession();
		if(sessionmap.containsKey("org.apache.shiro.subject.support.DefaultSubjectContext_AUTHENTICATED_SESSION_KEY")
				&&(boolean)sessionmap.get("org.apache.shiro.subject.support.DefaultSubjectContext_AUTHENTICATED_SESSION_KEY")){
			if(sessionmap.containsKey("authorizationCode")&jsonUtil.authorizationCode.equals(sessionmap.get("authorizationCode"))){
				System.out.println("已授权");
				return invocation.invoke();//通过拦截器
			}else {
				System.out.println("授权标识错误");
				response.sendRedirect(request.getContextPath()+"/login.jsp");
				return null;
			}
		}else{
			System.out.println("未授权");
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return null;
		}
	}
}
