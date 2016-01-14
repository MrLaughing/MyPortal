package com.zai360.portal.test.interceptor;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.zai360.portal.test.controller.FindAction;

/**
 * 拦截器，获取Request信息和参数，有何用呢？
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
		RequestInfo requestInfo=new RequestInfo();
		Object action = invocation.getAction();
		if(action instanceof FindAction){//true
			HttpServletRequest request=ServletActionContext.getRequest();
			HttpServletResponse response=ServletActionContext.getResponse();
			requestInfo.setHost(request.getRemoteAddr());
			requestInfo.setPort(request.getRemotePort());
			requestInfo.setRequestMethod(request.getMethod());
			if(request.getServletPath().contains("query")){
				requestInfo.setUrl1(request.getServletPath());
			}
			requestInfo.setUrl2(invocation.getInvocationContext().getName());
			//首先判断是否登录
			Map<String,Object> sessionmap=invocation.getInvocationContext().getSession();
			if(sessionmap.containsKey("org.apache.shiro.subject.support.DefaultSubjectContext_AUTHENTICATED_SESSION_KEY")
					&&(boolean)sessionmap.get("org.apache.shiro.subject.support.DefaultSubjectContext_AUTHENTICATED_SESSION_KEY")){
				System.out.println("已授权");
				Map<String,Object> parametersmap=invocation.getInvocationContext().getParameters();//获取请求参数
				Set<String> parametersset=parametersmap.keySet();
				for(String key:parametersset){
					if("serialVersionUID".equals(key)){
						String[] serialVersionUID=(String[])parametersmap.get("serialVersionUID");
						for(int i=0;i<serialVersionUID.length;i++){
							requestInfo.setSerialVersionUID(serialVersionUID[i]);
						}
					}
					
					if("rows".equals(key)){
						String[] rows=(String[])parametersmap.get("rows");
						for(int i=0;i<rows.length;i++){
						requestInfo.setRows(rows[i]);
						}
					}
					if("page".equals(key)){
						String[] page=(String[])parametersmap.get("page");
						for(int i=0;i<page.length;i++){
						requestInfo.setPage(page[i]);
						}
					}
					if("datetime_min".equals(key)){
						String[] datetime_min=(String[])parametersmap.get("datetime_min");
						for(int i=0;i<datetime_min.length;i++){
						requestInfo.setDatetime_min(datetime_min[i]);
						}
					}
					if("datetime_max".equals(key)){
						String[] datetime_max=(String[])parametersmap.get("datetime_max");
						for(int i=0;i<datetime_max.length;i++){
						requestInfo.setDatetime_max(datetime_max[i]);
						}
					}
					if("datetime_begin".equals(key)){
						String[] datetime_begin=(String[])parametersmap.get("datetime_begin");
						for(int i=0;i<datetime_begin.length;i++){
						requestInfo.setDatetime_begin(datetime_begin[i]);
						}
					}
					if("sycode".equals(key)){
						String[] sycode=(String[])parametersmap.get("sycode");
						for(int i=0;i<sycode.length;i++){
						requestInfo.setSycode(sycode[i]);
						}
					}
					if("order_status".equals(key)){
						String[] order_status=(String[])parametersmap.get("order_status");
						for(int i=0;i<order_status.length;i++){
						requestInfo.setOrder_status(order_status[i]);
						}
					}
					if("source_type".equals(key)){
						String[] source_type=(String[])parametersmap.get("source_type");
						for(int i=0;i<source_type.length;i++){
						requestInfo.setSource_type(source_type[i]);
						}
					}
					if("phoneno".equals(key)){
						String[] phoneno=(String[])parametersmap.get("phoneno");
						for(int i=0;i<phoneno.length;i++){
						requestInfo.setPhoneno(phoneno[i]);
						}
					}
					if("deposit_type".equals(key)){
						String[] deposit_type=(String[])parametersmap.get("deposit_type");
						for(int i=0;i<deposit_type.length;i++){
						requestInfo.setDeposit_type(deposit_type[i]);
						}
					}
					if("deposit_credit".equals(key)){
						String[] deposit_credit=(String[])parametersmap.get("deposit_credit");
						for(int i=0;i<deposit_credit.length;i++){
						requestInfo.setDeposit_credit(deposit_credit[i]);
						}
					}
					if("coupon_code".equals(key)){
						String[] coupon_code=(String[])parametersmap.get("coupon_code");
						for(int i=0;i<coupon_code.length;i++){
						requestInfo.setCoupon_code(coupon_code[i]);
						}
					}
					if("recycleman_code".equals(key)){
						String[] recycleman_code=(String[])parametersmap.get("recycleman_code");
						for(int i=0;i<recycleman_code.length;i++){
						requestInfo.setRecycleman_code(recycleman_code[i]);
						}
					}
					if("refereecode".equals(key)){
						String[] refereecode=(String[])parametersmap.get("refereecode");
						for(int i=0;i<refereecode.length;i++){
						requestInfo.setRefereecode(refereecode[i]);
						}
					}
				}
				return invocation.invoke();
			}else {
				System.out.println("未授权");
				response.sendRedirect(request.getContextPath()+"/login.jsp");
				return null;
			}
		}
		return null;
	}
	
}
