package com.secpro.platform.monitoring.manage.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.RawKpi;
import com.secpro.platform.monitoring.manage.entity.SysKpiInfo;
import com.secpro.platform.monitoring.manage.entity.SysResObj;
import com.secpro.platform.monitoring.manage.services.RawKpiService;
import com.secpro.platform.monitoring.manage.services.SysKpiInfoService;
import com.secpro.platform.monitoring.manage.services.SysResObjService;
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
	private String returnMsg;
	private String backUrl;

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
			backUrl = "/resobj/viewMca.jsp";
			return "failed";
		}
		if (resid.trim().equals("")) {
			returnMsg = "查询指标失败！";
			logger.info("fetch resid failed ,resid is ''");
			backUrl = "/resobj/viewMca.jsp";
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
		System.out.println("---------------44444444444 "+ resid);
		if (resid == null) {
			returnMsg = "查询指标失败！";
			logger.info("fetch resid failed ,resid is null");
			backUrl = "/resobj/viewMca.jsp";
			return "failed";
		}
		if (resid.trim().equals("")) {
			returnMsg = "查询指标失败！";
			logger.info("fetch resid failed ,resid is ''");
			backUrl = "/resobj/viewMca.jsp";
			return "failed";
		}
		SysResObj res = (SysResObj) objService.getObj(SysResObj.class,
				Long.parseLong(resid));
		List kpiList = kpiService.queryAll("from SysKpiInfo s where s.classId="
				+ res.getClassId());
		if (kpiList == null) {
			returnMsg = "查询指标失败！";
			logger.info("fetch kpi failed ,classid is " + res.getClassId());
			backUrl = "/resobj/viewMca.jsp";
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
				sb.append("\"kpiDesc\":\"" + kpi.getKpiDesc() + "\",");
				
				if (i != (kpiList.size() - 1)) {
					if (l != null && l.size() != 0) {
						if (kpi.getKpiType().equals("0")) {
							sb.append("\"kpiValue\":\""
									+ ((RawKpi) l.get(0)).getValueStr() + "\"},");
						} else if (kpi.getKpiType().equals("1")) {
							sb.append("\"kpiValue\":"
									+ ((RawKpi) l.get(0)).getValueNum() + "},");
						} else {
							sb.append("\"kpiValue\":\"\"},");
						}
					}else{
						sb.append("\"kpiValue\":\"\"},");
					}
				} else {
					if (l != null && l.size() != 0) {
						if (kpi.getKpiType().equals("0")) {
							sb.append("\"kpiValue\":\""
									+ ((RawKpi) l.get(0)).getValueStr() + "\"}");
						} else if (kpi.getKpiType().equals("1")) {
							sb.append("\"kpiValue\":"
									+ ((RawKpi) l.get(0)).getValueNum() + "}");
						} else {
							sb.append("\"kpiValue\":\"\"}");
						}
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
		System.out.println("=================="+mcaid+"--------"+operation);
		if(mcaid==null){
			returnMsg = "操作失败！";
			logger.info("fetch mcaid failed ,mcaid is null");
			backUrl = "/resobj/viewMca.jsp";
			return "failed";
		}
		if(mcaid.trim().equals("")){
			returnMsg = "操作失败！";
			logger.info("fetch mcaid failed ,mcaid is ''");
			backUrl = "/resobj/viewMca.jsp";
			return "failed";
		}
		if(operation==null){
			returnMsg = "操作失败！";
			logger.info("fetch operation failed ,operation is null");
			backUrl = "/resobj/viewMca.jsp";
			return "failed";
		}
		if(operation.trim().equals("")){
			returnMsg = "操作失败！";
			logger.info("fetch operation failed ,operation is ''");
			backUrl = "/resobj/viewMca.jsp";
			return "failed";
		}
		JSONObject task=null;
		try {
			JSONObject json=new JSONObject();
			json.put("operation", operation);
			task=new JSONObject();
			task.put("watchdog", json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(task==null){
			returnMsg = "任务创建失败，请重新执行操作！";
			logger.info("task is null ");
			backUrl = "/resobj/viewMca.jsp";
			return "failed";
		}
		//-----------------下发及时任务到watchdog-------------------
		System.out.println("---------------------------"+task.toString());
		
		
		//-------------------------------------------
		return "success";
	} 
}
