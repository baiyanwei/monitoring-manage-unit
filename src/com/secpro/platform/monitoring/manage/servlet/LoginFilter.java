package com.secpro.platform.monitoring.manage.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.secpro.platform.monitoring.manage.entity.SysUserInfo;

public class LoginFilter implements Filter{
	private FilterConfig config;
	private ServletContext context;
	public void init(FilterConfig config) throws ServletException {
		this.config=config;
		this.context=config.getServletContext();
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession s;
		s=((HttpServletRequest)request).getSession();
		String nexturl=((HttpServletRequest)request).getServletPath();
		SysUserInfo user=(SysUserInfo)s.getAttribute("user");
		if(user!=null){
			chain.doFilter(request,response);
		}else{
			if("/login.action".equals(nexturl)){
				chain.doFilter(request,response);
			}else if("/login.jsp".equals(nexturl)){
				chain.doFilter(request,response);
			}else if(nexturl.endsWith(".css")||nexturl.endsWith(".js")||nexturl.endsWith(".png")||nexturl.endsWith(".bmp")||nexturl.endsWith(".jpg")||nexturl.endsWith(".jpeg")){
				chain.doFilter(request,response);
				
			}else{
				request.getRequestDispatcher("login.jsp").forward(request,response);
			}
		}
	}

	public void destroy() {
		this.config=null;
		this.context=null;
	}
}
