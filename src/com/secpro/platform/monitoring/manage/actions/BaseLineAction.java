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

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.BaselineMatchScore;
import com.secpro.platform.monitoring.manage.entity.RawBaselineMatch;
import com.secpro.platform.monitoring.manage.entity.SysBaseline;
import com.secpro.platform.monitoring.manage.entity.SysResObj;
import com.secpro.platform.monitoring.manage.services.SysBaselineService;
import com.secpro.platform.monitoring.manage.services.SysResObjService;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

@Controller("BaseLineAction")
public class BaseLineAction {
	PlatformLogger logger = PlatformLogger.getLogger(BaseLineAction.class);
	private SysBaselineService sbService;
	private String returnMsg;
	private String backUrl;
	private SysBaseline sbl;
	private SysResObjService resService;
	
	public SysResObjService getResService() {
		return resService;
	}
	@Resource(name="SysResObjServiceImpl")
	public void setResService(SysResObjService resService) {
		this.resService = resService;
	}
	public SysBaseline getSbl() {
		return sbl;
	}
	public void setSbl(SysBaseline sbl) {
		this.sbl = sbl;
	}
	public SysBaselineService getSbService() {
		return sbService;
	}
	@Resource(name="SysBaselineServiceImpl")
	public void setSbService(SysBaselineService sbService) {
		this.sbService = sbService;
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
	public void getAllBaseline(){
		List baselineList=sbService.queryAll("from SysBaseline s order by s.baselineType,s.baselineBlackWhite");
		StringBuilder sb = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if(baselineList!=null&&!baselineList.isEmpty()){
				sb.append("{\"total\":" + baselineList.size() + ",\"rows\":[");
				for(int i=0;i<baselineList.size();i++){
					SysBaseline baseline=(SysBaseline)baselineList.get(i);
					sb.append("{\"baselineId\":" + baseline.getId() + ",");
					sb.append("\"baselineType\":\"" + (baseline.getBaselineType().equals("0") ? "配置基线":"策略基线") + "\",");
					sb.append("\"blackWhite\":\"" + (baseline.getBaselineBlackWhite().equals("0") ? "白名单":"黑名单") + "\",");
					sb.append("\"score\":\"10\",");
					if(i==(baselineList.size()-1)){
						sb.append("\"baselineDesc\":\"" + (baseline.getBaselineDesc()==null?" ":baseline.getBaselineDesc())
								+ "\"}");
					}else{
						sb.append("\"baselineDesc\":\"" + (baseline.getBaselineDesc()==null?" ":baseline.getBaselineDesc())
								+ "\"},");
					}
				}
			}else{
				sb.append("{\"total\":0,\"rows\":[]}");
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
	}
	public String saveBaseLine(){
		if(sbl.getBaselineDesc()==null){
			returnMsg = "基线描述不能为空，保存失败！";
			logger.info("fetch baselineDesc failed ,baselineDesc is null");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		if(sbl.getBaselineDesc().trim().equals("")){
			returnMsg = "基线描述不能为空，删除失败！";
			logger.info("fetch baselineDesc failed ,baselineDesc is ''");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		if(sbl.getBaselineBlackWhite()==null){
			returnMsg = "基线黑白名单不能为空，保存失败！";
			logger.info("fetch baseBlackWhite failed ,baseBlackWhite is null");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		if(sbl.getBaselineBlackWhite().trim().equals("")){
			returnMsg = "基线黑白名单不能为空，删除失败！";
			logger.info("fetch baseBlackWhite failed ,baseBlackWhite is ''");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		if(sbl.getBaselineType()==null){
			returnMsg = "基线类型不能为空，保存失败！";
			logger.info("fetch baseType failed ,baseBlackWhite is null");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		if(sbl.getBaselineType().trim().equals("")){
			returnMsg = "基线类型不能为空，删除失败！";
			logger.info("fetch baseType failed ,baseBlackWhite is ''");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		sbService.save(sbl);
		return "success";
	}
	public String deleteBaseLine(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String baseLineId=request.getParameter("baseLineId");
		if(baseLineId==null){
			returnMsg = "系统错误，删除失败！";
			logger.info("fetch baseLineId failed ,templateId is null");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		if(baseLineId.trim().equals("")){
			returnMsg = "系统错误，删除失败！";
			logger.info("fetch baseLineId failed ,templateId is ''");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		String[] baseLineIds=baseLineId.split(",");
		boolean flag=sbService.deleteBaseLine(baseLineIds);
		if(!flag){
			returnMsg = "系统错误，删除失败！";
			logger.info("delete baseline error ");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		return "success";
	}
	public String toModifyBaseLine(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String baseLineId=request.getParameter("baseLineId");
		if(baseLineId==null){
			returnMsg = "系统错误，跳转页面失败！";
			logger.info("fetch baseLineId failed ,templateId is null");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		if(baseLineId.trim().equals("")){
			returnMsg = "系统错误，跳转页面失败！";
			logger.info("fetch baseLineId failed ,templateId is ''");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		SysBaseline sbl=(SysBaseline)sbService.getObj(SysBaseline.class, Long.parseLong(baseLineId));
		if(sbl==null){
			returnMsg = "系统错误，跳转页面失败！";
			logger.info("query baseLine failed by baseLineId = "+baseLineId);
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("baseLine", sbl);
		return "success";
	}
	
	public String modifyBaseLine(){
		if(sbl.getId()==null){
			returnMsg = "基线描述不能为空，保存失败！";
			logger.info("fetch baseLineId failed ,baseLineId is null");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		if(sbl.getBaselineDesc()==null){
			returnMsg = "基线描述不能为空，保存失败！";
			logger.info("fetch baselineDesc failed ,baselineDesc is null");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		if(sbl.getBaselineDesc().trim().equals("")){
			returnMsg = "基线描述不能为空，删除失败！";
			logger.info("fetch baselineDesc failed ,baselineDesc is ''");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		if(sbl.getBaselineBlackWhite()==null){
			returnMsg = "基线黑白名单不能为空，保存失败！";
			logger.info("fetch baseBlackWhite failed ,baseBlackWhite is null");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		if(sbl.getBaselineBlackWhite().trim().equals("")){
			returnMsg = "基线黑白名单不能为空，删除失败！";
			logger.info("fetch baseBlackWhite failed ,baseBlackWhite is ''");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		if(sbl.getBaselineType()==null){
			returnMsg = "基线类型不能为空，保存失败！";
			logger.info("fetch baseType failed ,baseBlackWhite is null");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		if(sbl.getBaselineType().trim().equals("")){
			returnMsg = "基线类型不能为空，删除失败！";
			logger.info("fetch baseType failed ,baseBlackWhite is ''");
			backUrl = "baseline/viewBaseLine.jsp";
			return "failed";
		}
		sbService.update(sbl);
		return "success";
	}
	public String baseLineRuleMapping(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String typeCode=request.getParameter("typeCode");
		String baselineRule=request.getParameter("baselineRule");
		String baselineId=request.getParameter("baselineId");
		if(typeCode==null){
			returnMsg = "系统错误，基线规则保存失败！";
			logger.info("fetch typeCode failed ,typeCode is null");
			backUrl = "/rule/viewAllDevType.jsp";
			return "failed";
		}
		if(typeCode.trim().equals("")){
			returnMsg = "系统错误，基线规则保存失败！";
			logger.info("fetch typeCode failed ,typeCode is ''");
			backUrl = "/rule/viewAllDevType.jsp";
			return "failed";
		}
		if(baselineRule==null){
			returnMsg = "系统错误，基线规则保存失败！";
			logger.info("fetch baselineRule failed ,baselineRule is null");
			backUrl = "/rule/viewAllDevType.jsp";
			return "failed";
		}
		if(baselineRule.trim().equals("")){
			returnMsg = "系统错误，基线规则保存失败！";
			logger.info("fetch baselineRule failed ,baselineRule is ''");
			backUrl = "/rule/viewAllDevType.jsp";
			return "failed";
		}
		if(baselineId==null){
			returnMsg = "系统错误，基线规则保存失败！";
			logger.info("fetch baselineId failed ,baselineId is null");
			backUrl = "/rule/viewAllDevType.jsp";
			return "failed";
		}
		if(baselineId.trim().equals("")){
			returnMsg = "系统错误，基线规则保存失败！";
			logger.info("fetch baselineId failed ,baselineId is ''");
			backUrl = "/rule/viewAllDevType.jsp";
			return "failed";
		}
		boolean flag=sbService.createBaseLineRule(typeCode, baselineId, baselineRule);
		if(!flag){
			returnMsg = "系统错误，基线规则保存失败！";
			logger.info("insert rule failed");
			backUrl = "/rule/viewAllDevType.jsp";
			return "failed";
		}
		return "success";
	}
	public String toAddTemplateRule(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String baselineId=request.getParameter("baselineId");
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("baselineId", baselineId);
		return "success";
	}
	public void getBaseLineRuleByDev(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String typeCode=request.getParameter("typeCode");
		String baselineId=request.getParameter("baselineId");
		if(typeCode==null||baselineId==null){
			return ;
		}
		if(typeCode.trim().equals("")||baselineId.trim().equals("")){
			return ;
		}
		
		String rule=sbService.getRule(baselineId, typeCode);
		String json="{\"rule\":\""+rule+"\"}";
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
	public void queryResBaseLineMatchScore(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	//	SimpleDateFormat sdf2 =   new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" );
		ActionContext actionContext = ActionContext.getContext(); 
		HttpServletRequest request=ServletActionContext.getRequest();
		String from=request.getParameter("ff");
		String to=request.getParameter("tt");
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
		String resId=request.getParameter("resId");
		StringBuilder sb = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			
			
			if(resId==null){
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			if(resId.trim().equals("")){
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			if(from!=null&&!from.trim().equals("")){
				from=sdf.format(sdf1.parse(from));

			}else{
				 String todays=sdf1.format(new Date());
			     from=sdf.format(sdf1.parse(todays));
			}
			
			if(to!=null&&!to.trim().equals("")){
				to=sdf.format(sdf1.parse(to));
			}else{
			
				to=sdf.format(new Date());
			}
			int count=sbService.queryAllScoreCountByRes(Long.parseLong(resId), from, to);
			List scorePage=sbService.queryResMatchScorePage(Long.parseLong(resId), from, to, maxPage, pageNum);
			if(count==0){
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			sb.append("{\"total\":" + count + ",\"rows\":[");
			for (int i = 0; i < scorePage.size(); i++) {
				BaselineMatchScore score=(BaselineMatchScore)scorePage.get(i);
				sb.append("{\"resId_taskCode\":\"" + score.getResId()+"_"+score.getTaskCode() + "\",");
				sb.append("\"cdate\":\"" + sdf1.format(sdf.parse(score.getCdate())) + "\",");
				
				if(i==(scorePage.size()-1)){
					sb.append("\"score\":" + score.getSocre() + "}");
				}else{
					sb.append("\"score\":" + score.getSocre() + "},");
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
	public void queryBaseLineMatchScore(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	//	SimpleDateFormat sdf2 =   new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" );
		ActionContext actionContext = ActionContext.getContext(); 
		HttpServletRequest request=ServletActionContext.getRequest();
		String from=request.getParameter("ff");
		String to=request.getParameter("tt");
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
		
		StringBuilder sb = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			
			if(from!=null&&!from.trim().equals("")){
				from=sdf.format(sdf1.parse(from));

			}else{
				 String todays=sdf1.format(new Date());
			     from=sdf.format(sdf1.parse(todays));
			}
			
			if(to!=null&&!to.trim().equals("")){
				to=sdf.format(sdf1.parse(to));
			}else{
			
				to=sdf.format(new Date());
			}
			int count=sbService.queryAllMatchScorePageCount(from, to);
			List scorePage=sbService.queryAllMatchScorePage(from, to, maxPage, pageNum);
			if(count==0){
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			sb.append("{\"total\":" + count + ",\"rows\":[");
			for (int i = 0; i < scorePage.size(); i++) {
				BaselineMatchScore score=(BaselineMatchScore)scorePage.get(i);
				SysResObj res=(SysResObj)resService.getObj(SysResObj.class, score.getResId());
				sb.append("{\"resId_taskCode\":\"" + score.getResId()+"_"+score.getTaskCode() + "\",");
				sb.append("\"resName\":\"" + res.getResName()+ "\",");
				sb.append("\"resIp\":\"" + res.getResIp()+ "\",");
				sb.append("\"cdate\":\"" + sdf1.format(sdf.parse(score.getCdate())) + "\",");				
				if(i==(scorePage.size()-1)){
					sb.append("\"score\":" + score.getSocre() + "}");
				}else{
					sb.append("\"score\":" + score.getSocre() + "},");
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
	public void queryResBaseLineScoreDatil(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		ActionContext actionContext = ActionContext.getContext(); 
		HttpServletRequest request=ServletActionContext.getRequest();
		String resId=request.getParameter("resId");
		String taskCode=request.getParameter("taskCode");
		System.out.println(resId+"------------------------=========================="+taskCode);
		StringBuilder sb = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if(resId==null){
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			if(resId.trim().equals("")){
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			if(taskCode==null){
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			if(taskCode.trim().equals("")){
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			List matchDatil=sbService.queryMatchDatil(resId, taskCode);
			if(matchDatil==null){
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			
			sb.append("{\"total\":" + matchDatil.size() + ",\"rows\":[");
			for (int i = 0; i < matchDatil.size(); i++) {
				RawBaselineMatch datil=(RawBaselineMatch)matchDatil.get(i);
				sb.append("{\"mcatchResult\":\"" + (datil.getMatchResult().equals("0")?"比对成功":"比对失败") + "\",");
				sb.append("\"baselineDesc\":\"" + datil.getBaseLineDesc() + "\",");
				sb.append("\"cdate\":\"" + sdf1.format(sdf.parse(datil.getCdate())) + "\",");
				sb.append("\"baselineId\":" + datil.getBaselineId() + ",");
				if(i==(matchDatil.size()-1)){
					sb.append("\"result\":\"" + datil.getResult() + "\"}");
				}else{
					sb.append("\"result\":\"" + datil.getResult() + "\"},");
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
	public String toQueryMatchDatil(){
		ActionContext actionContext = ActionContext.getContext(); 
		HttpServletRequest request=ServletActionContext.getRequest();
		String resId_taskCode=request.getParameter("resId_taskCode");
		if(resId_taskCode==null){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch resId_taskCode failed ,resId_taskCode is null");
			backUrl = "baseline/viewMatchScore.jsp";
			return "failed";
		}
		if(resId_taskCode.trim().equals("")){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch resId_taskCode failed ,resId_taskCode is null");
			backUrl = "baseline/viewMatchScore.jsp";
			return "failed";
		}
		String[] rt=resId_taskCode.split("_");
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("resId", rt[0]);
		requestMap.put("taskCode", rt[1]);
		return "success";
	}
}
