package com.zai360.portal.test.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.zai360.portal.test.vo.Admin;

public class Design {
	ClassLoader cl = new Admin().getClass().getClassLoader();
	
	
	
	public static void main(String[] args) {
		
	}
}
class dynamicproxy implements InvocationHandler{

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//在代理中处理方法
		if(method.getName().equalsIgnoreCase("login")){
			//处理
			Admin.class.getInterfaces();
		}
		return null;
	}
	
}
