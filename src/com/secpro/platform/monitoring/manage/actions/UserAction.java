package com.secpro.platform.monitoring.manage.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.Log;
import com.secpro.platform.monitoring.manage.entity.SysOrg;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.services.SysOrgService;
import com.secpro.platform.monitoring.manage.services.SysUserInfoService;
import com.secpro.platform.monitoring.manage.util.MD5Builder;
import com.secpro.platform.monitoring.manage.util.PasswdRuleUtil;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
@Controller("UserAction")
public class UserAction {
	PlatformLogger logger = PlatformLogger.getLogger(UserAction.class);
	private String returnMsg;
	private String backUrl;
	private SysUserInfoService suiService;
	private SysUserInfo user;
	private SysOrgService orgService;
	private SysLogService logService;
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
	}
	public SysOrgService getOrgService() {
		return orgService;
	}
	@Resource(name = "SysOrgServiceImpl")
	public void setOrgService(SysOrgService orgService) {
		this.orgService = orgService;
	}
	public SysUserInfoService getSuiService() {
		return suiService;
	}
	@Resource(name = "SysUserInfoServiceImpl")
	public void setSuiService(SysUserInfoService suiService) {
		this.suiService = suiService;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String getBackUrl() {
		return backUrl;
	}
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	
	public SysUserInfo getUser() {
		return user;
	}
	public void setUser(SysUserInfo user) {
		this.user = user;
	}
	public void viewUserInfo(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd" );
		HttpServletRequest request=ServletActionContext.getRequest();
		String rows=request.getParameter("rows");
		String page=request.getParameter("page");
		int pageNum=1;
		int maxPage=10;
		if(rows!=null&&!rows.trim().equals("")){
			maxPage=Integer.parseInt(rows); 
		}
		if(page!=null&&!page.trim().equals("")){
			pageNum=Integer.parseInt(page); 
		}
		List allUsers=suiService.queryAll("select s.id,s.account,s.cdate,s.mobelTel,s.officeTel,s.enableAccount,s.userDesc,o.id,o.orgName,s.userName,s.email from SysUserInfo s ,SysOrg o where s.orgId=o.id and s.deleted is null");
		List users=suiService.queryByPage("select s.id,s.account,s.cdate,s.mobelTel,s.officeTel,s.enableAccount,s.userDesc,o.id,o.orgName,s.userName,s.email from SysUserInfo s ,SysOrg o where s.orgId=o.id and s.deleted is null",pageNum,maxPage);
		StringBuilder sb = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (allUsers == null) {
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			sb.append("{\"total\":" + allUsers.size() + ",\"rows\":[");
			for (int i = 0; i < users.size(); i++) {
				Object obj[]=(Object[])users.get(i);
				sb.append("{\"userid\":" + obj[0] + ",");
				sb.append("\"account\":\"" + obj[1] + "\",");
				
				sb.append("\"cdate\":\"" + sdf1.format(sdf.parse((String)obj[2])) + "\",");
				sb.append("\"mobelTel\":\"" + (obj[3]==null?"":obj[3]) + "\",");
				sb.append("\"officeTel\":\"" + (obj[4]==null?"":obj[4])+ "\",");
				sb.append("\"enableAccount\":\"" + (obj[5].equals("0")?"启用":"暂停") + "\",");
				sb.append("\"userDesc\":\"" + (obj[6]==null?" ":obj[6]) + "\",");
				sb.append("\"userName\":\"" + (obj[9]==null?" ":obj[9]) + "\",");
				sb.append("\"email\":\"" + (obj[10]==null?" ":obj[10]) + "\",");
				sb.append("\"orgid\":" + obj[7] + ",");
				if(i==(users.size()-1)){
					sb.append("\"orgName\":\"" + obj[8] + "\"}");
				}else{
					sb.append("\"orgName\":\"" + obj[8] + "\"},");
				}
			}
			sb.append("]}");
			System.out.println(sb.toString());
			pw.println(sb.toString());
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
	public String saveUser(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		if(user.getAccount()==null){
			returnMsg = "用户账号不能为空，保存失败！";
			logger.info("fetch account failed , account is null !");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(user.getAccount().trim().equals("")){
			returnMsg = "用户账号不能为空，保存失败！";
			logger.info("fetch account failed , account is ''!");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		
		List ulist=suiService.queryAll("from SysUserInfo u where u.account='"+user.getAccount()+"'");
		if(ulist!=null&&ulist.size()>0){
			returnMsg = "账号已存在，保存失败！";
			logger.info("账号已经存在，保存失败 !");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		
		if(user.getPassword()==null){
			returnMsg = "密码不能为空，保存失败！";
			logger.info("fetch password failed , password is null !");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(user.getPassword().trim().equals("")){
			returnMsg = "密码不能为空，保存失败！";
			logger.info("fetch password failed , password is ''!");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(user.getUserName()==null){
			returnMsg = "用户名不能为空，保存失败！";
			logger.info("fetch username failed , username is null !");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(user.getUserName().trim().equals("")){
			returnMsg = "用户名不能为空，保存失败！";
			logger.info("fetch username failed , username is ''!");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(user.getMobelTel()==null){
			returnMsg = "手机号不能为空，保存失败！";
			logger.info("fetch mobelTel failed , mobelTel is null !");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(user.getMobelTel().trim().equals("")){
			returnMsg = "手机号不能为空，保存失败！";
			logger.info("fetch mobelTel failed , mobelTel is ''!");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(user.getEmail()==null){
			returnMsg = "邮箱不能为空，保存失败！";
			logger.info("fetch email failed , email is null !");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(user.getEmail().trim().equals("")){
			returnMsg = "邮箱不能为空，保存失败！";
			logger.info("fetch email failed , email is ''!");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(user.getOrgId()==null){
			returnMsg = "用户组织ID不能为空，保存失败！";
			logger.info("fetch orgid failed , orgid is null!");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(user.getEnableAccount()==null){
			returnMsg = "用户启停状态不能为空，保存失败！";
			logger.info("fetch EnableAccount failed , EnableAccount is null!");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		user.setCdate(sdf.format(new Date()));
		user.setPassword(MD5Builder.getMD5(user.getPassword()));
		suiService.save(user);
		return "success";
	}
	public String toModifyUser(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String userid=request.getParameter("userid");
		if(userid==null){
			returnMsg = "系统错误，跳转页面失败！";
			logger.info("fetch userid failed , userid is null!");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(userid.trim().equals("")){
			returnMsg = "系统错误，跳转页面失败！";
			logger.info("fetch userid failed , userid is ''!");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		SysUserInfo muser=(SysUserInfo)suiService.getObj(SysUserInfo.class, Long.parseLong(userid));
		
		if(muser==null){
			returnMsg = "系统错误，跳转页面失败！";
			logger.info("fetch user failed from database !");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		SysOrg org=(SysOrg)orgService.getObj(SysOrg.class, muser.getOrgId());
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("muser", muser);
		requestMap.put("org", org);
		return "success";
	}
	public String modifyUser(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		HttpServletRequest request=ServletActionContext.getRequest();
		String passwd=request.getParameter("passwd");
		String orgid=request.getParameter("orgid");
		if(passwd==null){
			returnMsg = "系统错误，用户修改失败！";
			logger.info("fetch passwd failed , passwd is null !");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(passwd.trim().equals("")){
			returnMsg = "系统错误，用户修改失败！";
			logger.info("fetch passwd failed , passwd is '' !");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(user.getId()==null){
			returnMsg = "系统错误，用户修改失败！";
			logger.info("fetch userid failed , userid is null !");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		Long orgId=0L;
		
		if(user.getOrgId()!=null){
			orgId=user.getOrgId();
		}else{
			if(orgid==null){
				returnMsg = "用户部门不能为空，保存失败！";
				logger.info("fetch orgId failed , orgId is null !");
				backUrl = "users/viewUser.jsp";
				return "failed";
			}
			if(orgid.equals("")){
				returnMsg = "用户部门不能为空，保存失败！";
				logger.info("fetch orgId failed , orgId is null !");
				backUrl = "users/viewUser.jsp";
				return "failed";
			}
			orgId=Long.parseLong(orgid);
		}
		SysUserInfo u=(SysUserInfo)suiService.getObj(SysUserInfo.class, user.getId());
		if(user.getPassword()==null){
			returnMsg = "用户密码不能为空，保存失败！";
			logger.info("fetch password failed , password is null !");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(user.getPassword().trim().equals("")){
			returnMsg = "用户密码不能为空，保存失败！";
			logger.info("fetch password failed , password is ''!");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(!passwd.equals(user.getPassword())){
			u.setPassword(MD5Builder.getMD5(user.getPassword()));
		}
		if(user.getUserName()==null){
			returnMsg = "用户名不能为空，保存失败！";
			logger.info("fetch username failed , username is null !");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(user.getUserName().trim().equals("")){
			returnMsg = "用户名不能为空，保存失败！";
			logger.info("fetch username failed , username is ''!");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		u.setUserName(user.getUserName());
		if(user.getMobelTel()==null){
			returnMsg = "手机号不能为空，保存失败！";
			logger.info("fetch mobelTel failed , mobelTel is null !");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(user.getMobelTel().trim().equals("")){
			returnMsg = "用户名不能为空，保存失败！";
			logger.info("fetch mobelTel failed , mobelTel is ''!");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		u.setMobelTel(user.getMobelTel());
		if(user.getEmail()==null){
			returnMsg = "邮箱不能为空，保存失败！";
			logger.info("fetch email failed , email is null !");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(user.getEmail().trim().equals("")){
			returnMsg = "邮箱不能为空，保存失败！";
			logger.info("fetch email failed , email is ''!");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		u.setEmail(user.getEmail());
		/*if(user.getOrgId()==null){
			returnMsg = "用户组织ID不能为空，保存失败！";
			logger.info("fetch orgid failed , orgid is null!");
			backUrl = "viewUserInfo.jsp";
			return "failed";
		}*/
		u.setOrgId(orgId);
		if(user.getEnableAccount()==null){
			returnMsg = "用户启停状态不能为空，保存失败！";
			logger.info("fetch EnableAccount failed , EnableAccount is null!");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		u.setUserDesc(user.getUserDesc());
		u.setEnableAccount(user.getEnableAccount());
		u.setOfficeTel(user.getOfficeTel());
		u.setMdate(sdf.format(new Date()));
		suiService.update(u);
		return "success";
	}
	public String deleteUser(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String userid=request.getParameter("userid");
		if(userid==null){
			returnMsg = "系统错误，用户删除失败！";
			logger.info("fetch userid failed , userid is null!");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(userid.trim().equals("")){
			returnMsg = "系统错误，用户删除失败！";
			logger.info("fetch userid failed , userid is ''!");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		String[] userids=userid.split(",");
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		for(int i=0;i<userids.length;i++){
			SysUserInfo usi=(SysUserInfo)suiService.getObj(SysUserInfo.class, Long.parseLong(userids[i]));
			if(usi!=null){
				usi.setDeleted("1");
				usi.setDeleteDate(sdf.format(new Date()));
				suiService.update(usi);
			}
		}
		return "success";
	}
	public String userRoleMapping(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String[] userids=request.getParameterValues("userid");
		String[] roleids=request.getParameterValues("roleid");
		if(userids==null){
			returnMsg = "系统错误，用户角色映射失败！";
			logger.info("fetch userid failed , userid is null!");
			backUrl = "users/userRoleMapping.jsp";
			return "failed";
		}
		
		if(roleids==null){
			returnMsg = "系统错误，用户角色映射失败！";
			logger.info("fetch roleid failed , roleid is null!");
			backUrl = "users/userRoleMapping.jsp";
			return "failed";
		}
		boolean flag =suiService.createUserRoleMapping(userids, roleids);
		if(!flag){
			returnMsg = "系统错误，用户角色映射失败！";
			logger.info("insert database error!");
			backUrl = "users/userRoleMapping.jsp";
			return "failed";
		}
		return "success";
	}
	public void getRoleByUser(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String userid=request.getParameter("userid");
		System.out.println(userid+"----------------------");
		if(userid==null){
			return ;
		}
		if(userid.trim().equals("")){
			return ;
		}
		orgService.queryAll("from SysOrg o where o.parent_id=1");
		List roleList=suiService.getRoleByUser(Long.parseLong(userid));
		String roleids="";
		for(int i=0;i<roleList.size();i++){
			if(i!=roleList.size()-1){
				roleids=roleids+roleList.get(i)+",";
			}else{
				roleids=roleids+roleList.get(i);
			}
		}
		String json="{\"roleid\":\""+roleids+"\"}";
		PrintWriter pw = null;
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/json");
		try {
			pw = resp.getWriter();
			pw.println(json);
			pw.flush();
			System.out.println(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	public String modifySelf(){
		
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		HttpServletRequest request=ServletActionContext.getRequest();
		String passwd=request.getParameter("passwd");
		String orgid=request.getParameter("orgid");
		
		if(passwd==null){
			returnMsg = "系统错误，用户修改失败！";
			logger.info("fetch passwd failed , passwd is null !");
			backUrl = "first.jsp";
			return "failed";
		}
		if(passwd.trim().equals("")){
			returnMsg = "系统错误，用户修改失败！";
			logger.info("fetch passwd failed , passwd is '' !");
			backUrl = "first.jsp";
			return "failed";
		}
		if(user.getId()==null){
			returnMsg = "系统错误，用户修改失败！";
			logger.info("fetch userid failed , userid is null !");
			backUrl = "first.jsp";
			return "failed";
		}
		Long orgId=0L;
		
		if(user.getOrgId()!=null){
			orgId=user.getOrgId();
		}else{
			if(orgid==null){
				returnMsg = "用户部门不能为空，保存失败！";
				logger.info("fetch orgId failed , orgId is null !");
				backUrl = "first.jsp";
				return "failed";
			}
			if(orgid.equals("")){
				returnMsg = "用户部门不能为空，保存失败！";
				logger.info("fetch orgId failed , orgId is null !");
				backUrl = "first.jsp";
				return "failed";
			}
			orgId=Long.parseLong(orgid);
		}
		SysUserInfo u=(SysUserInfo)suiService.getObj(SysUserInfo.class, user.getId());
		
		if(user.getPassword()==null){
			returnMsg = "用户密码不能为空，保存失败！";
			logger.info("fetch password failed , password is null !");
			backUrl = "first.jsp";
			return "failed";
		}
		if(user.getPassword().trim().equals("")){
			returnMsg = "用户密码不能为空，保存失败！";
			logger.info("fetch password failed , password is ''!");
			backUrl = "first.jsp";
			return "failed";
		}
		if(!passwd.equals(user.getPassword())){
			u.setPassword(MD5Builder.getMD5(user.getPassword()));
		}
		if(user.getUserName()==null){
			returnMsg = "用户名不能为空，保存失败！";
			logger.info("fetch username failed , username is null !");
			backUrl = "first.jsp";
			return "failed";
		}
		if(user.getUserName().trim().equals("")){
			returnMsg = "用户名不能为空，保存失败！";
			logger.info("fetch username failed , username is ''!");
			backUrl = "first.jsp";
			return "failed";
		}
		u.setUserName(user.getUserName());
		if(user.getMobelTel()==null){
			returnMsg = "手机号不能为空，保存失败！";
			logger.info("fetch mobelTel failed , mobelTel is null !");
			backUrl = "first.jsp";
			return "failed";
		}
		if(user.getMobelTel().trim().equals("")){
			returnMsg = "用户名不能为空，保存失败！";
			logger.info("fetch mobelTel failed , mobelTel is ''!");
			backUrl = "first.jsp";
			return "failed";
		}
		u.setMobelTel(user.getMobelTel());
		if(user.getEmail()==null){
			returnMsg = "邮箱不能为空，保存失败！";
			logger.info("fetch email failed , email is null !");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		if(user.getEmail().trim().equals("")){
			returnMsg = "邮箱不能为空，保存失败！";
			logger.info("fetch email failed , email is ''!");
			backUrl = "users/viewUser.jsp";
			return "failed";
		}
		u.setEmail(user.getEmail());
		/*if(user.getOrgId()==null){
			returnMsg = "用户组织ID不能为空，保存失败！";
			logger.info("fetch orgid failed , orgid is null!");
			backUrl = "viewUserInfo.jsp";
			return "failed";
		}*/
		u.setOrgId(orgId);
		
		u.setUserDesc(user.getUserDesc());
		u.setOfficeTel(user.getOfficeTel());
		u.setMdate(sdf.format(new Date()));
		
		suiService.update(u);
		HttpSession session=request.getSession();
		SysUserInfo olduser=(SysUserInfo)session.getAttribute("user");
		Map app=olduser.getApp();
		u.setApp(app);
		session.setAttribute("user", u);
		
		return "success";
		
	}
	public String resetPasswd(){
		
		HttpServletRequest request=ServletActionContext.getRequest();
		 SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		String uaccount=request.getParameter("uaccount");
		String oldpasswd=request.getParameter("oldpasswd");
		String newpaswd=request.getParameter("newpasswd");
		
		ActionContext ctx = ActionContext.getContext();
		Map<String,Object> requestMap=(Map)ctx.get("request");
		requestMap.put("uaccount", uaccount);
		if (uaccount == null||oldpasswd==null||newpaswd==null) {
			logger.info("account or oldpassword or rnewpassword is null!");
			Log log=new Log();
		 	log.setAccount(uaccount);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("修改密码失败");
		 	logService.save(log);
		 	requestMap.put("loginError", "用户名或密码不能为空，修改失败!");
			return "resetError";
		}
		
		if (uaccount.trim().equals("")||oldpasswd.trim().equals("")||newpaswd.trim().equals("")) {
			logger.info("account or oldpassword or rnewpassword is ''!");
			Log log=new Log();
		 	log.setAccount(uaccount);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("修改密码失败");
		 	logService.save(log);
		 	requestMap.put("loginError", "用户名或密码不能为空，修改失败!");
			return "resetError";
		}
		if(oldpasswd.trim().equals(newpaswd.toString())){
			logger.info("oldpassword the same as newpassword !");
			Log log=new Log();
		 	log.setAccount(uaccount);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("修改密码失败");
		 	logService.save(log);
		 	requestMap.put("loginError", "新旧密码不能相同，修改失败！");
			return "resetError";
		}
		if(!isDigit(newpaswd)){
			logger.info("new passwd must contain num !");
			Log log=new Log();
		 	log.setAccount(uaccount);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("修改密码失败");
		 	logService.save(log);
		 	requestMap.put("loginError", "新密码必须有一个数字，修改失败！");
			return "resetError";
		}
		if(!isYing(newpaswd)){
			logger.info("new passwd must contain zimu !");
			Log log=new Log();
		 	log.setAccount(uaccount);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("修改密码失败");
		 	logService.save(log);
		 	requestMap.put("loginError", "新密码必须有一个字母，修改失败！");
			return "resetError";
		}
		if(!isConSpeCharacters(newpaswd)){
			logger.info("new passwd must contain zimu !");
			Log log=new Log();
		 	log.setAccount(uaccount);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("修改密码失败");
		 	logService.save(log);
		 	requestMap.put("loginError", "新密码必须有一个特殊字符，修改失败！");
			return "resetError";
		}
		String passwd=MD5Builder.getMD5(oldpasswd);	
		List userList=suiService.queryAll("from SysUserInfo u where u.account='"+uaccount+"' and u.password='"+passwd+"' and u.deleted is null ");
		if(userList!=null&&userList.size()>0){
			SysUserInfo u=(SysUserInfo)userList.get(0);
			String npaswd=MD5Builder.getMD5(newpaswd);
			u.setPassword(npaswd);
			u.setMdate(sdf.format(new Date()));
			suiService.update(u);
			suiService.updateLastLoginDate(sdf.format(new Date()), uaccount);
			Log log=new Log();
		 	log.setAccount(uaccount);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("修改密码成功");
		 	logService.save(log);
		 	requestMap.put("loginError", "密码修改成功！");
			return "success";
		}else{
			logger.info("account is not exist!");
			Log log=new Log();
		 	log.setAccount(uaccount);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("修改密码失败");
		 	logService.save(log);
		 	requestMap.put("loginError", "用户不存在，修改失败！");
			return "resetError";
		}
	}
	public void checkPasswd(){
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
