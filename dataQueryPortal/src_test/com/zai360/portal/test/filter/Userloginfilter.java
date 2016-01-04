package com.zai360.portal.test.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.zai360.portal.test.vo.Admin;

public class Userloginfilter implements Filter {

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		String uri = request.getRequestURI();
		HttpSession session = request.getSession();
		if (uri.equals("/portal/") || uri.indexOf("login.jsp") != -1
				|| uri.indexOf("userlogin.action") != -1
				|| uri.indexOf("/css/") != -1 || uri.indexOf("/easyui/") != -1
				|| uri.indexOf("/lib/") != -1 || uri.indexOf("/js/") != -1
				|| uri.indexOf("/skin/") != -1 || uri.indexOf("/images/") != -1
				|| uri.indexOf("/139.196.27.2:9090/")!=-1||uri.indexOf("login4account.jsp")!=-1) {
			chain.doFilter(arg0, arg1);
		} else {
			Admin admin = (Admin) session.getAttribute("admin");
			if (admin != null) {
				chain.doFilter(arg0, arg1);
			} else {
				request.setAttribute("msg", "请先登录系统");
				request.getRequestDispatcher("/login.jsp").forward(arg0, arg1);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
	@Override
	public void destroy() {

	}
}
