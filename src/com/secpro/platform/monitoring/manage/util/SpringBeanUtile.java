package com.secpro.platform.monitoring.manage.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringBeanUtile {
	public static <T extends Object> T getSpringBean(HttpServletRequest request, Class<?> clazz, String beanId) {
		if (Assert.isEmptyString(beanId) == true || request == null || clazz == null) {
			return null;
		}
		ServletContext application = ServletActionContext.getRequest().getSession().getServletContext();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
		
		return (T) (context.getAutowireCapableBeanFactory().getBean(beanId));
	}
}
