package com.secpro.platform.monitoring.manage.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.Log;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.services.SysUserInfoService;
import com.secpro.platform.monitoring.manage.util.MD5Builder;

@Controller("LoginAction")
public class LoginAction {
	private SysUserInfoService suiService;
	private SysLogService logService;
	
	public SysLogService getLogService() {
		return logService;
	}
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
	}
	public SysUserInfoService getSuiService() {
		return suiService;
	}
	@Resource(name = "SysUserInfoServiceImpl")
	public void setSuiService(SysUserInfoService suiService) {
		this.suiService = suiService;
	}
	public String login(){
		 SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		HttpServletRequest request=ServletActionContext.getRequest();
		String account=request.getParameter("account");
		String password=request.getParameter("password");
		System.out.println(password+"===============-------------");
		ActionContext ctx = ActionContext.getContext();
		Map<String,Object> requestMap=(Map)ctx.get("request");
		if(account==null){
			requestMap.put("loginError", "登录失败，用户名不能为空！");
			return "loginError";
		}
		if(account.trim().equals("")){
			requestMap.put("loginError", "登录失败，用户名不能为空！");
			return "loginError";
		}
		if(password==null){
			requestMap.put("loginError", "登录失败，密码不能为空！");
			return "loginError";
		}
		if(password.trim().equals("")){
			requestMap.put("loginError", "登录失败，密码不能为空！");
			return "loginError";
		}
		String passwd=MD5Builder.getMD5(password);
		
		List userList=suiService.queryAll("from SysUserInfo u where u.account='"+account+"' and u.password='"+passwd+"' and u.deleted is null");
		
		if(userList==null){
			requestMap.put("loginError", "登录失败用户名或密码不存在，请重新登录！");
			return "loginError";
		}
		if(userList.size()==0){
			requestMap.put("loginError", "登录失败用户名或密码不存在，请重新登录！");
			return "loginError";
		}
		SysUserInfo user=(SysUserInfo)userList.get(0);
		
		
		if(user==null){
			requestMap.put("loginError", "登录失败用户名或密码不存在，请重新登录！");
			return "loginError";
		}
		if(user.getEnableAccount().equals("1")){
			requestMap.put("loginError", "账号已锁定，请解锁后登录！");
			return "loginError";
		}
		Map logApp=logService.getLogApp();
		user.setApp(suiService.getAllApp(user));
		request.getSession().setAttribute("user", user);
	 	request.getSession().setAttribute("appLog", logApp);
	 	Log log=new Log();
	 	log.setAccount(user.getAccount());
	 	log.setHandleDate(sdf.format(new Date()));
	 	log.setUserIp(request.getRemoteAddr());
	 	log.setHandleContent("登录系统");
	 	logService.save(log);
		return "success";
	}
	public String logout(){
		HttpServletRequest request=ServletActionContext.getRequest();
		request.getSession().removeAttribute("user");
		return "success";
	}
}
