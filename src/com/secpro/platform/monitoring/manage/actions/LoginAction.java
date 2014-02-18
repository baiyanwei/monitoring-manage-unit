package com.secpro.platform.monitoring.manage.actions;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.Log;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.services.SysUserInfoService;
import com.secpro.platform.monitoring.manage.util.MD5Builder;
import com.secpro.platform.monitoring.manage.util.PasswdRuleUtil;

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
		ActionContext ctx = ActionContext.getContext();
		Map<String,Object> requestMap=(Map)ctx.get("request");
		if(account==null){
			requestMap.put("loginError", "登录失败，用户名不能为空！");
			Log log=new Log();
		 	log.setAccount("");
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("登录失败，用户名为空");
		 	logService.save(log);
			return "loginError";
		}
		if(account.trim().equals("")){
			requestMap.put("loginError", "登录失败，用户名不能为空！");
			Log log=new Log();
		 	log.setAccount("");
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("登录失败，用户名为空");
		 	logService.save(log);
			return "loginError";
		}
		if(password==null){
			requestMap.put("loginError", "登录失败，密码不能为空！");
			Log log=new Log();
		 	log.setAccount(account);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("登录失败，密码为空");
		 	logService.save(log);
			return "loginError";
		}
		if(password.trim().equals("")){
			requestMap.put("loginError", "登录失败，密码不能为空！");
			Log log=new Log();
		 	log.setAccount(account);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("登录失败，密码为空");
		 	logService.save(log);
			return "loginError";
		}
		String passwd=MD5Builder.getMD5(password);
		
		List userList=suiService.queryAll("from SysUserInfo u where u.account='"+account+"' and u.password='"+passwd+"' and u.deleted is null ");
		
		if(userList==null){	
			requestMap.put("loginError", "用户名不存在或密码错误，请重新登录！");
			List oneuser=suiService.queryAll("from SysUserInfo u where u.account='"+account+"' and u.deleted is null and u.enableAccount !='1'");
			if(oneuser!=null&&oneuser.size()>0){
				String times=(String)request.getSession().getAttribute(account);
				if(times!=null){
					int count=Integer.parseInt(times);
					if(count>=PasswdRuleUtil.WrongTimes){
						SysUserInfo u=(SysUserInfo)oneuser.get(0);
						u.setEnableAccount("1");
						suiService.update(u);
					}else{
						request.getSession().setAttribute(account, count+1);
					}
				}else{
					request.getSession().setAttribute(account, 1);
				}
			}
			Log log=new Log();
		 	log.setAccount(account);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("登录失败，用户名不存在或密码错误");
		 	logService.save(log);
			return "loginError";
		}
		if(userList.size()==0){			
			requestMap.put("loginError", "用户名不存在或密码错误，请重新登录！");
			List oneuser=suiService.queryAll("from SysUserInfo u where u.account='"+account+"' and u.deleted is null and u.enableAccount !='1'");
			
			if(oneuser!=null&&oneuser.size()>0){
				
				Object times=request.getSession().getAttribute(account);
				
				if(times!=null){
					Integer count=(Integer)times;
					if(count>=PasswdRuleUtil.WrongTimes){
						SysUserInfo u=(SysUserInfo)oneuser.get(0);
						u.setEnableAccount("1");
						suiService.update(u);
						request.getSession().removeAttribute(account);
						requestMap.put("loginError", "密码错误次数太多，用户被锁定！");
					}else{
						request.getSession().setAttribute(account, count+1);
					}
				}else{
					request.getSession().setAttribute(account, 1);
				}
			}
			Log log=new Log();
		 	log.setAccount(account);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("登录失败，用户名不存在或密码错误");
		 	logService.save(log);
			return "loginError";
		}
		SysUserInfo user=(SysUserInfo)userList.get(0);
		
		
		if(user==null){			
			requestMap.put("loginError", "登录失败用户名或密码不存在，请重新登录！");
			List oneuser=suiService.queryAll("from SysUserInfo u where u.account='"+account+"' and u.deleted is null and u.enableAccount !='1'");
			if(oneuser!=null&&oneuser.size()>0){
				String times=(String)request.getSession().getAttribute(account);
				if(times!=null){
					int count=Integer.parseInt(times);
					if(count>=PasswdRuleUtil.WrongTimes){
						SysUserInfo u=(SysUserInfo)oneuser.get(0);
						u.setEnableAccount("1");
						suiService.update(u);
					}else{
						request.getSession().setAttribute(account, count+1);
					}
				}else{
					request.getSession().setAttribute(account, 1);
				}
			}
			Log log=new Log();
		 	log.setAccount(account);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("登录失败，用户名不存在或密码错误");
		 	logService.save(log);
			return "loginError";
		}
		if(user.getEnableAccount().equals("1")){
			requestMap.put("loginError", "账号已锁定，请解锁后登录！");
			Log log=new Log();
		 	log.setAccount(account);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("登录失败，账号已锁定");
		 	logService.save(log);
			return "loginError";
		}
		String lastLoginDate=suiService.getLastLoginDate(account);
		System.out.println(lastLoginDate+"===================================");
		if(lastLoginDate==null){
			suiService.updateLastLoginDate(sdf.format(new Date()), account);
		}else{
			try {
				
				int d=diffDate(new Date(),sdf.parse(lastLoginDate));
				System.out.println(PasswdRuleUtil.isPasswdTimeout+"----------------------------"+d+"  "+PasswdRuleUtil.passwdTimeout*30);
				if(PasswdRuleUtil.isPasswdTimeout.equals("1")&&d>(PasswdRuleUtil.passwdTimeout*30)){				
					Log log=new Log();
				 	log.setAccount(account);
				 	log.setHandleDate(sdf.format(new Date()));
				 	log.setUserIp(request.getRemoteAddr());
				 	log.setHandleContent("超过"+(PasswdRuleUtil.passwdTimeout*30)+"天未登录，跳转修改密码页面!");
				 	logService.save(log);
				 	requestMap.put("uaccount", account);
				 	requestMap.put("minlength", PasswdRuleUtil.passwdLong);
				 	return "toResetPasswd";
				}else{
					suiService.updateLastLoginDate(sdf.format(new Date()), account);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				suiService.updateLastLoginDate(sdf.format(new Date()), account);
			}
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
	 	//增加用户登录的当前时间
	 	request.getSession().setAttribute("lastLoginTime", System.currentTimeMillis());
	 	if(request.getSession().getAttribute(account)!=null){
	 		request.getSession().removeAttribute(account);
	 	}
		return "success";
	}
	public String logout(){
		HttpServletRequest request=ServletActionContext.getRequest();
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("lastLoginTime");
		return "success";
	}
	private int diffDate(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}
	private long getMillis(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}
	public void checkOldPasswd(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String uaccount = request.getParameter("uaccount");
		String oldpasswd = request.getParameter("oldpasswd");
		
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			
			if (uaccount == null||oldpasswd==null) {
				pw.println(new JSONObject().toString());
				pw.flush();
				return;
			}
			if (uaccount.trim().equals("")||oldpasswd.trim().equals("")) {
				pw.println(new JSONObject().toString());
				pw.flush();
				return;
			}
			String passwd=MD5Builder.getMD5(oldpasswd);			
			List userList=suiService.queryAll("from SysUserInfo u where u.account='"+uaccount+"' and u.password='"+passwd+"' and u.deleted is null ");
			if(userList!=null&&userList.size()>0){
				JSONObject json=new JSONObject();
				json.put("checkok", "ok");
				System.out.println(json.toString());
				pw.println(json.toString());
				pw.flush();
				return;
			}else{	
				pw.println(new JSONObject().toString());
				pw.flush();
				return;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	public void checkNewPasswd(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String newpasswd = request.getParameter("newpasswd");
		
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			JSONObject json=new JSONObject();
			if (newpasswd == null) {
				json.put("checkok", "0");	
				System.out.println(json.toString());
				pw.println(json.toString());
				pw.flush();
				return;
			}
			if(newpasswd.trim().length()<PasswdRuleUtil.passwdLong){
				json.put("checkok", "1");	
				System.out.println(json.toString());
				pw.println(json.toString());
				pw.flush();
				return;
			}
			if(!isDigit(newpasswd)){
				json.put("checkok", "2");	
				System.out.println(json.toString());
				pw.println(json.toString());
				pw.flush();
				return;
			}
			if(!isYing(newpasswd)){
				json.put("checkok", "3");	
				System.out.println(json.toString());
				pw.println(json.toString());
				pw.flush();
				return;
			}
			if(!isConSpeCharacters(newpasswd)){
				json.put("checkok", "4");
				System.out.println(json.toString());
				pw.println(json.toString());
				pw.flush();
				return;
			}
			if(newpasswd.trim().length()>PasswdRuleUtil.maxPwdLong){
				json.put("checkok", "5");	
				System.out.println(json.toString());
				pw.println(json.toString());
				pw.flush();
				return;
			}
			json.put("checkok", "ok");
			System.out.println(json.toString());
			pw.println(json.toString());
			pw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
			
	}
	public boolean isDigit(String strNum) {  
		 return strNum.matches(".+?\\d.+?"); 
	}
	public boolean isYing(String str) {  
		 return str.matches(".*\\p{Alpha}.*"); 
	}
	private boolean isConSpeCharacters(String string) {
		  // TODO Auto-generated method stub
		  if(string.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*\\s*", "").length()==0){
		   //如果不包含特殊字符
		   return  false;
		  }
		  return true;
	 }
	
}
