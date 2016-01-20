package com.zai360.portal.test.interceptor;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.zai360.portal.test.service.WriteService;

/**
 * 拦截器，获取Request信息和参数，有何用呢？
 * @author Laughing_Lz
 * @date 2016年1月14日
 */
public class Myinterceptor implements Interceptor {

	private static final long serialVersionUID = 1L;
	@Autowired
	private WriteService writeService;//为查询数据库中是否存在url2该表···
	
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
			System.out.println("已授权");
			
			//其实判断请求表是否存在
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='polan' AND TABLE_NAME='"
					+invocation.getInvocationContext().getName()+ "' ;");
			String havetable = this.writeService.havetable(sql);
			if (havetable == null) {
				System.out.println("该表不存在！");
				return null;
			}
			return invocation.invoke();//通过拦截器
		}else {
			System.out.println("未授权");
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return null;
		}
	}
}
