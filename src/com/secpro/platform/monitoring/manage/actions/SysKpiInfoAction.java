package com.secpro.platform.monitoring.manage.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.RawKpi;
import com.secpro.platform.monitoring.manage.entity.SysKpiInfo;
import com.secpro.platform.monitoring.manage.entity.SysResClass;
import com.secpro.platform.monitoring.manage.entity.SysResObj;
import com.secpro.platform.monitoring.manage.http.client.HttpClient;
import com.secpro.platform.monitoring.manage.http.client.HttpResponseHandler;
import com.secpro.platform.monitoring.manage.services.RawKpiService;
import com.secpro.platform.monitoring.manage.services.SysKpiInfoService;
import com.secpro.platform.monitoring.manage.services.SysResClassService;
import com.secpro.platform.monitoring.manage.services.SysResObjService;
import com.secpro.platform.monitoring.manage.util.ApplicationConfiguration;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

/**
 * 处理指标类请求
 * 
 * @author liyan
 * 
 */

@Controller("SysKpiInfoAction")
public class SysKpiInfoAction {
	PlatformLogger logger = PlatformLogger.getLogger(SysKpiInfoAction.class);
	private SysKpiInfoService kpiService;
	private RawKpiService rowService;
	private SysResObjService objService;
	private SysResClassService classService;
	private String returnMsg;
	private String backUrl;
	private SysKpiInfo kpiInfo;
	
	
	public SysResClassService getClassService() {
		return classService;
	}
	@Resource(name = "SysResClassServiceImpl")
	public void setClassService(SysResClassService classService) {
		this.classService = classService;
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

	public SysKpiInfo getKpiInfo() {
		return kpiInfo;
	}

	public void setKpiInfo(SysKpiInfo kpiInfo) {
		this.kpiInfo = kpiInfo;
	}

	public SysResObjService getObjService() {
		return objService;
	}

	@Resource(name = "SysResObjServiceImpl")
	public void setObjService(SysResObjService objService) {
		this.objService = objService;
	}

	public SysKpiInfoService getKpiService() {
		return kpiService;
	}

	@Resource(name = "SysKpiInfoServiceImpl")
	public void setKpiService(SysKpiInfoService kpiService) {
		this.kpiService = kpiService;
	}

	public RawKpiService getRowService() {
		return rowService;
	}

	@Resource(name = "RawKpiServiceImpl")
	public void setRowService(RawKpiService rowService) {
		this.rowService = rowService;
	}

	public String toViewMcaRaw() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String resid = request.getParameter("resid");
		
		if (resid == null) {
			returnMsg = "查询指标失败！";
			logger.info("fetch resid failed ,resid is null");
			backUrl = "resobj/viewMca.jsp";
			return "failed";
		}
		if (resid.trim().equals("")) {
			returnMsg = "查询指标失败！";
			logger.info("fetch resid failed ,resid is ''");
			backUrl = "resobj/viewMca.jsp";
			return "failed";
		}
		
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> requestMap = (Map) actionContext.get("request");
		requestMap.put("resid", resid);
		return "success";
	}

	public String queryMcaRaw() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String resid = request.getParameter("resid");
		
		if (resid == null) {
			returnMsg = "查询指标失败！";
			logger.info("fetch resid failed ,resid is null");
			backUrl = "resobj/viewMca.jsp";
			return "failed";
		}
		if (resid.trim().equals("")) {
			returnMsg = "查询指标失败！";
			logger.info("fetch resid failed ,resid is ''");
			backUrl = "resobj/viewMca.jsp";
			return "failed";
		}
		SysResObj res = (SysResObj) objService.getObj(SysResObj.class,
				Long.parseLong(resid));
		List kpiList = kpiService.queryAll("from SysKpiInfo s where s.classId="
				+ res.getClassId());
		if (kpiList == null) {
			returnMsg = "查询指标失败！";
			logger.info("fetch kpi failed ,classid is " + res.getClassId());
			backUrl = "resobj/viewMca.jsp";
		}

		StringBuilder sb = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			sb.append("{\"total\":" + kpiList.size() + ",\"rows\":[");
			for (int i = 0; i < kpiList.size(); i++) {
				SysKpiInfo kpi = (SysKpiInfo) kpiList.get(i);
				List l = rowService
						.queryAll("from RawKpi r where r.cdate=(select max(rr.cdate) from RawKpi rr where rr.kpiId="
								+ kpi.getId()
								+ " and rr.resId="
								+ res.getId()
								+ ") and r.kpiId="
								+ kpi.getId()
								+ " and r.resId=" + res.getId());
				sb.append("{\"kpiName\":\"" + kpi.getKpiName() + "\",");
				sb.append("\"kpiDesc\":\"" + (kpi.getKpiDesc()==null?" ":kpi.getKpiDesc()) + "\",");
				
				if (i != (kpiList.size() - 1)) {
					if (l != null && l.size() != 0) {
						
							sb.append("\"kpiValue\":\""
									+ ((RawKpi) l.get(0)).getKpiValue() + "\"},");
						
					}else{
						sb.append("\"kpiValue\":\"\"},");
					}
				} else {
					if (l != null && l.size() != 0) {
						
							sb.append("\"kpiValue\":\""
									+ ((RawKpi) l.get(0)).getKpiValue() + "\"}");
						
					}else{
						sb.append("\"kpiValue\":\"\"}");
					}
				}
			}
			sb.append("]}");
			System.out.println(sb.toString());
			pw.println(sb.toString());
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
		return "success";
	}
	public String mcaOperation(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String mcaid = request.getParameter("mcaid");
		String operation = request.getParameter("operation");
		
		if(mcaid==null){
			returnMsg = "操作失败！";
			logger.info("fetch mcaid failed ,mcaid is null");
			backUrl = "resobj/viewMca.jsp";
			return "failed";
		}
		if(mcaid.trim().equals("")){
			returnMsg = "操作失败！";
			logger.info("fetch mcaid failed ,mcaid is ''");
			backUrl = "resobj/viewMca.jsp";
			return "failed";
		}
		if(operation==null){
			returnMsg = "操作失败！";
			logger.info("fetch operation failed ,operation is null");
			backUrl = "resobj/viewMca.jsp";
			return "failed";
		}
		if(operation.trim().equals("")){
			returnMsg = "操作失败！";
			logger.info("fetch operation failed ,operation is ''");
			backUrl = "resobj/viewMca.jsp";
			return "failed";
		}
		SysResObj mca=(SysResObj)objService.getObj(SysResObj.class, Long.parseLong(mcaid));
		String mcaurl="http://"+mca.getResIp()+":"+ApplicationConfiguration.WATCHDOGPORT+ApplicationConfiguration.WATCHDOGSERVERPATH;
		
		HttpClient hc = new HttpClient();
		ChannelPipeline line;
		JSONObject task=null;
		try {
			JSONObject json=new JSONObject();
			json.put("operation", operation);
			task=new JSONObject();
			task.put("watchdog", json);
			line = hc.post(mcaurl, task);
			line.addLast("handler", new HttpResponseHandler());
			Thread.sleep(4000);
			hc.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
				returnMsg = "任务下发失败，请重新执行操作！";
				logger.info("task is null ");
				backUrl = "resobj/viewMca.jsp";
				return "failed";
			
		}
		return "success";
	} 
	public String saveKpiInfo(){
		if(kpiInfo.getKpiName()==null){
			returnMsg = "指标名称不能为空，指标保存失败！";
			logger.info("fetch kpiName failed , kpiName is null!");
			backUrl = "resobj/viewKpiInfo.jsp";
		}
		if(kpiInfo.getKpiName().equals("")){
			returnMsg = "指标名称不能为空，指标保存失败！";
			logger.info("fetch kpiName failed , kpiName is ''!");
			backUrl = "resobj/viewKpiInfo.jsp";
		}
		
		if(kpiInfo.getClassId()==null){
			returnMsg = "资源类型不能为空，指标保存失败！";
			logger.info("fetch classId failed , classId is null!");
			backUrl = "resobj/viewKpiInfo.jsp";
		}
		if(kpiInfo.getClassId()==0){
			returnMsg = "资源类型不能为空，指标保存失败！";
			logger.info("fetch classId failed , classId is 0!");
			backUrl = "resobj/viewKpiInfo.jsp";
		}
		kpiService.save(kpiInfo);
		return "success";
	}
	public String modifyKpiInfo(){
		if(kpiInfo.getId()==null){
			returnMsg = "指标名称不能为空，指标修改失败！";
			logger.info("fetch kpiId failed , kpiId is null!");
			backUrl = "resobj/viewKpiInfo.jsp";
		}
		if(kpiInfo.getKpiName()==null){
			returnMsg = "指标名称不能为空，指标修改失败！";
			logger.info("fetch kpiName failed , kpiName is null!");
			backUrl = "resobj/viewKpiInfo.jsp";
		}
		if(kpiInfo.getKpiName().equals("")){
			returnMsg = "指标名称不能为空，指标修改失败！";
			logger.info("fetch kpiName failed , kpiName is ''!");
			backUrl = "resobj/viewKpiInfo.jsp";
		}
		
		if(kpiInfo.getClassId()==null){
			returnMsg = "资源类型不能为空，指标保存失败！";
			logger.info("fetch classId failed , classId is null!");
			backUrl = "resobj/viewKpiInfo.jsp";
		}
		if(kpiInfo.getClassId()==0){
			returnMsg = "资源类型不能为空，指标保存失败！";
			logger.info("fetch classId failed , classId is 0!");
			backUrl = "resobj/viewKpiInfo.jsp";
		}
		SysKpiInfo kpi=(SysKpiInfo)kpiService.getObj(SysKpiInfo.class, kpiInfo.getId());
		kpi.setKpiName(kpiInfo.getKpiName());
		kpi.setKpiDesc(kpiInfo.getKpiDesc());
		
		kpi.setClassId(kpiInfo.getClassId());
		kpiService.update(kpi);
		return "success";
	}
	public String deleteKpi(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String kpiId=request.getParameter("kpiId");
		if(kpiId==null){
			returnMsg = "系统错误，指标删除改失败！";
			logger.info("fetch kpiId failed , kpiId is null!");
			backUrl = "resobj/viewKpiInfo.jsp";
		}
		if(kpiId.equals("")){
			returnMsg = "系统错误，指标删除改失败！";
			logger.info("fetch kpiId failed , kpiId is ''!");
			backUrl = "resobj/viewKpiInfo.jsp";
		}
		String[] kpiIds=kpiId.split(",");
		for(int i=0;i<kpiIds.length;i++){
			SysKpiInfo kpi=new SysKpiInfo();
			kpi.setId(Long.parseLong(kpiIds[i]));
			kpiService.delete(kpi);
		}
		return "success";
	}
	public void viewKpiInfo(){
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
		List allKpiList=kpiService.queryAll("from SysKpiInfo ");
		List pageKpiList=kpiService.queryByPage("select k.id,k.kpiName,k.kpiDesc,r.className from SysKpiInfo k , SysResClass r where k.classId=r.id", pageNum,maxPage);
		StringBuilder sb = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (allKpiList == null) {
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			sb.append("{\"total\":" + allKpiList.size() + ",\"rows\":[");
			for (int i = 0; i < pageKpiList.size(); i++) {
				Object obj[]=(Object[])pageKpiList.get(i);
				sb.append("{\"kpiId\":" + obj[0] + ",");
				sb.append("\"kpiName\":\"" + obj[1] + "\",");
				sb.append("\"kpiDesc\":\"" + obj[2] + "\",");
				
				
				if(i==(pageKpiList.size()-1)){
					sb.append("\"className\":\"" + (obj[3].equals("fw")?"防火墙":"采集端") + "\"}");
				}else{
					sb.append("\"className\":\"" + (obj[3].equals("fw")?"防火墙":"采集端") + "\"},");
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
	public String toModifyKpi(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String kpiId=request.getParameter("kpiId");
		if(kpiId==null){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch kpiId failed , kpiId is null!");
			backUrl = "resobj/viewKpiInfo.jsp";
			return "failed";
		}
		if(kpiId.trim().equals("")){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch kpiId failed , kpiId is ''!");
			backUrl = "resobj/viewKpiInfo.jsp";
			return "failed";
		}
		SysKpiInfo kpi=(SysKpiInfo)kpiService.getObj(SysKpiInfo.class, Long.parseLong(kpiId));
		if(kpi==null){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch SysKpiInfo failed from database!!");
			backUrl = "resobj/viewKpiInfo.jsp";
			return "failed";
		}
		SysResClass resClass=(SysResClass)classService.getObj(SysResClass.class, kpi.getClassId());
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("kpi", kpi);
		requestMap.put("resClass", resClass);
		return "success";
	}
	public void findAllResClass(){
		List allClass=classService.queryAll("from SysResClass");
		StringBuilder result = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (allClass == null) {
				result.append("[]");
				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			result.append("[");
			for (int i = 0; i < allClass.size(); i++) {
				SysResClass resClass=(SysResClass)allClass.get(i);
				result.append("{\"classid\":"+resClass.getId()+",\"text\":\""+(resClass.getClassName().equals("fw")?"防火墙":"采集端")+"\"}");
				
				if((i+1)!=allClass.size()){
					result.append(",");
				}
			}
			
			result.append("]");
			System.out.println(result.toString());
			pw.println(result.toString());
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}
