package com.secpro.platform.monitoring.manage.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.SysApp;
import com.secpro.platform.monitoring.manage.entity.SysRole;
import com.secpro.platform.monitoring.manage.services.SysAppService;
import com.secpro.platform.monitoring.manage.services.SysRoleService;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
@Controller("SysRoleAction")
public class SysRoleAction {
	PlatformLogger logger = PlatformLogger.getLogger(SysRoleAction.class);
	private String returnMsg;
	private String backUrl;
	private SysRoleService roleService;
	private SysRole role;
	private SysAppService appService;
	
	public SysAppService getAppService() {
		return appService;
	}
	@Resource(name = "SysAppServiceImpl")
	public void setAppService(SysAppService appService) {
		this.appService = appService;
	}
	public SysRole getRole() {
		return role;
	}
	public void setRole(SysRole role) {
		this.role = role;
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
	public SysRoleService getRoleService() {
		return roleService;
	}
	@Resource(name = "SysRoleServiceImpl")
	public void setRoleService(SysRoleService roleService) {
		this.roleService = roleService;
	}
	public void viewRole(){
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
		List allRole=roleService.queryAll("from SysRole");
		List rolepage=roleService.queryByPage("from SysRole", pageNum,maxPage);
		StringBuilder sb = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (allRole == null) {
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			sb.append("{\"total\":" + rolepage.size() + ",\"rows\":[");
			for (int i = 0; i < rolepage.size(); i++) {
				SysRole role1=(SysRole)rolepage.get(i);
				sb.append("{\"roleid\":" + role1.getId() + ",");
				sb.append("\"rolename\":\"" + role1.getRoleName() + "\",");
				if(i==(rolepage.size()-1)){
					sb.append("\"roledesc\":\"" + (role1.getRoleDesc()==null?"":role1.getRoleDesc()) + "\"}");
				}else{
					sb.append("\"roledesc\":\"" + (role1.getRoleDesc()==null?"":role1.getRoleDesc()) + "\"},");
				}
			}
			sb.append("]}");
			System.out.println(sb.toString());
			pw.println(sb.toString());
			pw.flush();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	public String saveRole(){
		if(role.getRoleName()==null){
			returnMsg = "角色名称不能为空，角色添加！";
			logger.info("fetch roleName failed , roleName is null!");
			backUrl = "viewRole.jsp";
			return "failed";
		}
		if(role.getRoleName()==null){
			returnMsg = "角色名称不能为空，角色添加！";
			logger.info("fetch roleName failed , roleName is null!");
			backUrl = "viewRole.jsp";
			return "failed";
		}
		roleService.save(role);
		return "success";
	}
	public String toModifyRole(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String roleid=request.getParameter("roleid");
		if(roleid==null){
			returnMsg = "系统错误，跳转页面失败！";
			logger.info("fetch roleid failed , roleid is null!");
			backUrl = "viewRole.jsp";
			return "failed";
		}
		if(roleid.trim().equals("")){
			returnMsg = "系统错误，跳转页面失败！";
			logger.info("fetch userid failed , userid is ''!");
			backUrl = "viewRole.jsp";
			return "failed";
		}
		SysRole r=(SysRole)roleService.getObj(SysRole.class, Long.parseLong(roleid));
		if(r==null){
			returnMsg = "系统错误，跳转页面失败！";
			logger.info("fetch user failed from database !");
			backUrl = "viewRole.jsp";
			return "failed";
		}
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("mrole", r);
		return "success";
	}
	public String modifyRole(){
		if(role.getId()==null){
			returnMsg = "系统错误，角色修改失败！";
			logger.info("fetch userid failed , userid is null !");
			backUrl = "viewRole.jsp";
			return "failed";
		}
		if(role.getRoleName()==null){
			returnMsg = "角色名称不能为空，保存失败！";
			logger.info("fetch roleName failed , roleName is null !");
			backUrl = "viewRole.jsp";
			return "failed";
		}
		if(role.getRoleName().trim().equals("")){
			returnMsg = "角色名称不能为空，保存失败！";
			logger.info("fetch roleName failed , roleName is ''!");
			backUrl = "viewRole.jsp";
			return "failed";
		}
		roleService.update(role);
		return "success";
	}
	public String deleteRole(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String roleid=request.getParameter("roleid");
		if(roleid==null){
			returnMsg = "系统错误，删除失败！";
			logger.info("fetch roleid failed , roleid is ''!");
			backUrl = "viewRole.jsp";
			return "failed";
		}
		if(roleid.trim().equals("")){
			returnMsg = "系统错误，删除失败！";
			logger.info("fetch roleid failed , roleid is ''!");
			backUrl = "viewRole.jsp";
			return "failed";
		}
		String[] roleids=roleid.split(",");
		boolean flag=roleService.deleteRole(roleids);
		if(!flag){
			returnMsg = "系统错误，删除失败！";
			logger.info("delete role failed !");
			backUrl = "viewRole.jsp";
			return "failed";
		}
		return "success";
	}
	public String roleAppMapping(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String[] roleids=request.getParameterValues("roleid");
		String appid=request.getParameter("appid");
		if(roleids==null){
			returnMsg = "系统错误，角色应用映射失败！";
			logger.info("fetch roleid failed , roleid is null!");
			backUrl = "viewRoleAppMapping.jsp";
			return "failed";
		}
		
		if(appid==null){
			returnMsg = "系统错误，角色应用映射失败！";
			logger.info("fetch appid failed , appid is null!");
			backUrl = "viewRoleAppMapping.jsp";
			return "failed";
		}
		if(appid.trim().equals("")){
			returnMsg = "系统错误，角色应用映射失败！";
			logger.info("fetch appid failed , appid is null!");
			backUrl = "viewRoleAppMapping.jsp";
			return "failed";
		}
		String[] appids=appid.split(",");
		boolean flag =roleService.createRoleAppMapping(roleids, appids);
		if(!flag){
			returnMsg = "系统错误，角色应用映射失败！";
			logger.info("insert database error!");
			backUrl = "viewRoleAppMapping.jsp";
			return "failed";
		}
		return "success";
	}
	public void getRoleByUser(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String roleId=request.getParameter("roleId");
		if(roleId==null){
			return ;
		}
		if(roleId.trim().equals("")){
			return ;
		}
		List roleList=roleService.getAppByRole(Long.parseLong(roleId));
		//创建JSON
	}
	public void getAppTree(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String id=request.getParameter("id");
		String hql="";
		if(id==null){
			hql="from SysApp a where a.parentId=0";
		}else if(id.trim().equals("")){
			hql="from SysApp a where a.parentId=0";
		}else{
			hql="from SysApp a where a.parentId="+id;
		}
		List appList=appService.queryAll(hql);
		if(appList==null){
			return ;
		}
		if(appList.size()==0){
			return ;
		}
		JSONArray jsonArrayIn = new JSONArray();
		try {
			for(Object o:appList){
				SysApp app=(SysApp)o;
				JSONObject json = new JSONObject();
				json.put("id", app.getId());
				json.put("text", app.getAppName());
				if(app.getHasLeaf().equals("1")){
					json.put("state", "closed");
				}
				jsonArrayIn.put(json);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
		PrintWriter pw = null;
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/json");
		try {
			pw = resp.getWriter();
			pw.println(jsonArrayIn.toString());
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getAppByRole(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String roleid=request.getParameter("roleid");
		if(roleid==null){
			return ;
		}
		if(roleid.trim().equals("")){
			return ;
		}
		String appids=appService.queryAppByRoleid(roleid);
		String json="{\"appids\":\""+appids+"\"}";
		PrintWriter pw = null;
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/json");
		try {
			pw = resp.getWriter();
			pw.println(json);
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}