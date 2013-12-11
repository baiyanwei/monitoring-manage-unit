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
import com.secpro.platform.monitoring.manage.entity.SysOrg;
import com.secpro.platform.monitoring.manage.services.SysOrgService;
import com.secpro.platform.monitoring.manage.services.SysUserInfoService;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

@Controller("OrgAction")
public class OrgAction {
	PlatformLogger logger = PlatformLogger.getLogger(OrgAction.class);
	private String returnMsg;
	private String backUrl;
	private SysOrgService orgService;
	private SysOrg org;
	private SysUserInfoService suiService;
	
	public SysOrg getOrg() {
		return org;
	}
	public void setOrg(SysOrg org) {
		this.org = org;
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
	public SysOrgService getOrgService() {
		return orgService;
	}
	@Resource(name="SysOrgServiceImpl")
	public void setOrgService(SysOrgService orgService) {
		this.orgService = orgService;
	}
	public void viewAllOrg(){
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
		List allOrgs=orgService.queryAll("from SysOrg");
		List pageOrgs=orgService.queryByPage("select o1.id,o1.orgName,o1.orgDesc,o1.parentOrgId,o2.orgName from SysOrg o1,SysOrg o2 where o1.parentOrgId=o2.id order by o1.parentOrgId",pageNum,maxPage);
		StringBuilder sb = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (allOrgs == null) {
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			sb.append("{\"total\":" + allOrgs.size() + ",\"rows\":[");
			for (int i = 0; i < pageOrgs.size(); i++) {
				Object obj[]=(Object[])pageOrgs.get(i);
				sb.append("{\"orgId\":" + obj[0] + ",");
				sb.append("\"orgName\":\"" + obj[1] + "\",");
				sb.append("\"orgDesc\":\"" + (obj[2]==null?" ":obj[2]) + "\",");
				
				sb.append("\"parentOrgId\":" + obj[3] + ",");
				
				if(i==(pageOrgs.size()-1)){
					sb.append("\"parentName\":\"" + obj[4] + "\"}");
				}else{
					sb.append("\"parentName\":\"" + obj[4] + "\"},");
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
	public String saveOrg(){
		if(org.getOrgName()==null){
			returnMsg = "组织名称不能为空，组织添加失败！";
			logger.info("fetch orgName failed , orgName is null!");
			backUrl = "users/viewOrg.jsp";
			return "failed";
		}
		if(org.getOrgName().trim().equals("")){
			returnMsg = "组织名称不能为空，组织添加失败！";
			logger.info("fetch orgName failed , orgName is ''!");
			backUrl = "users/viewOrg.jsp";
			return "failed";
		}
		org.setHasLeaf("0");
		orgService.save(org);
		if(org.getParentOrgId()!=null){
			SysOrg o=(SysOrg)orgService.getObj(SysOrg.class, org.getParentOrgId());
			o.setHasLeaf("1");
			orgService.update(o);
		}
		return "success";
	}
	public String toModifyOrg(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String orgId=request.getParameter("orgId");
		if(orgId==null){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch orgId failed , orgId is null!");
			backUrl = "users/viewOrg.jsp";
			return "failed";
		}
		if(orgId.trim().equals("")){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch orgId failed , orgId is ''!");
			backUrl = "users/viewOrg.jsp";
			return "failed";
		}
		SysOrg o=(SysOrg)orgService.getObj(SysOrg.class, Long.parseLong(orgId));
		if(o==null){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch SysOrg failed from database!");
			backUrl = "users/viewOrg.jsp";
			return "failed";
		}
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		if(o.getParentOrgId()!=null){
			SysOrg parent=(SysOrg)orgService.getObj(SysOrg.class, o.getParentOrgId());
			if(parent!=null){
				requestMap.put("parent", parent);
			}
		}
		requestMap.put("morg", o);
		return "success";
	}
	public String modifyOrg(){
		if(org.getId()==null){
			returnMsg = "系统错误，组织修改失败！";
			logger.info("fetch orgId failed , orgId is null!");
			backUrl = "users/viewOrg.jsp";
			return "failed";
		}
		if(org.getOrgName()==null){
			returnMsg = "组织名称不能为空，组织修改失败！";
			logger.info("fetch orgName failed , orgName is null!");
			backUrl = "users/viewOrg.jsp";
			return "failed";
		}
		if(org.getOrgName().trim().equals("")){
			
			returnMsg = "组织名称不能为空，组织修改失败！";
			logger.info("fetch orgName failed , orgName is ''!");
			backUrl = "users/viewOrg.jsp";
			return "failed";
		}
		
		SysOrg o=(SysOrg)orgService.getObj(SysOrg.class, org.getId());
		o.setOrgName(org.getOrgName());
		o.setOrgDesc(org.getOrgDesc());
		
		orgService.update(o);
		
		
		
		return "success";
	}
	public String deleteOrg(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String orgid=request.getParameter("orgids");
		if(orgid==null){
			returnMsg = "系统错误，删除失败！";
			logger.info("fetch orgids failed , orgids is null!");
			backUrl = "users/viewOrg.jsp";
			return "failed";
		}
		if(orgid.trim().equals("")){
			returnMsg = "系统错误，删除失败！";
			logger.info("fetch orgids failed , orgids is ''!");
			backUrl = "users/viewOrg.jsp";
			return "failed";
		}
		String[] orgids=orgid.split(",");
		for(int i=0;i<orgids.length;i++){
			List users=suiService.queryAll("from SysUserInfo s where s.orgId="+Long.parseLong(orgids[i]));
			if(users!=null&&users.size()>0){
				returnMsg="部分组织下存在用户，请先删除用户！";
				backUrl = "users/viewOrg.jsp";
				return "failed";
			}else{
				
				SysOrg o=(SysOrg)orgService.getObj(SysOrg.class, Long.parseLong(orgids[i]));
				List childList=orgService.queryAll("from SysOrg s where s.parentOrgId="+o.getId());
				if(childList!=null && childList.size()>0){
					returnMsg="请先删除子部门！";
					backUrl = "users/viewOrg.jsp";
					return "failed";
				}
				orgService.delete(o);
				List l=orgService.queryAll("from SysOrg s where s.parentOrgId="+o.getParentOrgId());
				if(l!=null&&l.size()>0){
					
				}else{
					SysOrg par=(SysOrg)orgService.getObj(SysOrg.class, o.getParentOrgId());
					par.setHasLeaf("0");
					orgService.update(par);
				}
			}
		}
		return "success";
	}
	public void allOrgRree(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String id=request.getParameter("id");
		String hql="";
		if(id==null){
	//	sql="select * from sys.dept start with paredeptid=0 connect by prior deptid=paredeptid";
			hql="from SysOrg o where o.parentOrgId=0";
		}else if(id.trim().equals("")){
			hql="from SysOrg o where o.parentOrgId=0";
		}else{
			hql="from SysOrg o where o.parentOrgId="+id;
		}
		List orgList=orgService.queryAll(hql);
		if(orgList==null){
			return ;
		}
		if(orgList.size()==0){
			return ;
		}
		JSONArray jsonArrayIn = new JSONArray();
		try {
			for(Object o:orgList){
				SysOrg org=(SysOrg)o;
				JSONObject json = new JSONObject();
				json.put("id", org.getId());
				json.put("text", org.getOrgName());
				if(org.getHasLeaf().equals("1")){
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
	public void getOrgTreeByid(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String orgid=request.getParameter("id");
		
		List allOrgList =orgService.queryAll("from SysOrg o where o.parentOrgId=1");
		JSONArray jsonArrayIn = new JSONArray();
		JSONObject temp=null;
		try {
			if (orgid != null && !orgid.trim().equals("")) {
				List orgList = orgService.getOrgTreeByOrgId(orgid);

				for (int i = 0; i < allOrgList.size(); i++) {
					SysOrg par = (SysOrg) allOrgList.get(i);
					JSONObject json = new JSONObject();

					json.put("id", par.getId());
					json.put("text", org.getOrgName());

					SysOrg org = (SysOrg) orgList.get(orgList.size() - 2);
					if (org.getId() == par.getId()) {
						temp = json;
					}
					jsonArrayIn.put(json);
				}
				if(temp!=null){
					Long oid=0L;
					for(int i=0;i<orgList.size();i++){
						SysOrg org = (SysOrg) allOrgList.get(i);
						if(org.getId()!=1&&org.getParentOrgId()!=1){
							if(oid==org.getParentOrgId()){
								
							}
						}
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
