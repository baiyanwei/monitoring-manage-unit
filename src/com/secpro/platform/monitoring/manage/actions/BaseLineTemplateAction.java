package com.secpro.platform.monitoring.manage.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.BaselineTemplate;
import com.secpro.platform.monitoring.manage.entity.SysBaseline;
import com.secpro.platform.monitoring.manage.entity.SysResObj;
import com.secpro.platform.monitoring.manage.services.BaselineTemplateService;
import com.secpro.platform.monitoring.manage.services.SysBaselineService;
import com.secpro.platform.monitoring.manage.services.SysResObjService;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

@Controller("BaseLineTemplateAction")
public class BaseLineTemplateAction {
	PlatformLogger logger = PlatformLogger.getLogger(BaseLineTemplateAction.class);
	private BaselineTemplateService btService;
	private SysBaselineService sbService;
	private SysResObjService resService;
	private String returnMsg;
	private String backUrl;
	
	public SysResObjService getResService() {
		return resService;
	}
	@Resource(name="SysResObjServiceImpl")
	public void setResService(SysResObjService resService) {
		this.resService = resService;
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
	public BaselineTemplateService getBtService() {
		return btService;
	}
	@Resource(name="BaselineTemplateServiceImpl")
	public void setBtService(BaselineTemplateService btService) {
		this.btService = btService;
	}
	public SysBaselineService getSbService() {
		return sbService;
	}
	@Resource(name="SysBaselineServiceImpl")
	public void setSbService(SysBaselineService sbService) {
		this.sbService = sbService;
	}
	public void getAllBaseLinetemplate(){
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
		List allTemplateList = btService.queryAll("from BaselineTemplate");
		List templateList = btService.queryByPage("from BaselineTemplate",pageNum,maxPage);
		templateList = btService.getAllBaseLineTemplate(templateList);
		StringBuilder sb = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (templateList == null) {
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			sb.append("{\"total\":" + allTemplateList.size() + ",\"rows\":[");
			for (int i = 0; i < templateList.size(); i++) {
				BaselineTemplate bt = (BaselineTemplate) templateList.get(i);

				
				if(bt.getBaseLineList().size()==0){
					sb.append("{\"templateId\":" + bt.getId() + ",");
					sb.append("\"templateName\":\"" + bt.getTemplateName() + "\",");
					sb.append("\"templateDesc\":\"" + bt.getTemplateDesc() + "\",");
					sb.append("\"companyName\":\"" + (bt.getCompanyName()==null?"":bt.getCompanyName()) + "\",");
					sb.append("\"baselineDesc\":\"\",");
					sb.append("\"baselineType\":\"\",");
					if(i==(templateList.size()-1)){
						sb.append("\"blackWhite\":\"\"}");
					}else{
						sb.append("\"blackWhite\":\"\"},");
					}
				}else{
					for (int j = 0; j < bt.getBaseLineList().size(); j++) {
						sb.append("{\"templateId\":" + bt.getId() + ",");
						sb.append("\"templateName\":\"" + bt.getTemplateName() + "\",");
						sb.append("\"templateDesc\":\"" + bt.getTemplateDesc() + "\",");
						sb.append("\"companyName\":\"" + (bt.getCompanyName()==null?"":bt.getCompanyName()) + "\",");
						SysBaseline sbl = (SysBaseline) bt.getBaseLineList().get(j);
						sb.append("\"baselineDesc\":\"" + sbl.getBaselineDesc()
								+ "\",");
						sb.append("\"baselineType\":\""
								+ (sbl.getBaselineType().equals("0") ? "配置基线"
										: "策略基线") + "\",");
						if (i==(templateList.size()-1)&&j==(bt.getBaseLineList().size()-1)) {
							sb.append("\"blackWhite\":\""
									+ (sbl.getBaselineBlackWhite().equals("0") ? "白名单"
											: "黑名单") + "\"}");
						} else {
							sb.append("\"blackWhite\":\""
									+ (sbl.getBaselineBlackWhite().equals("0") ? "白名单"
											: "黑名单") + "\"},");
						}
	
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
	}
	public String saveBaseLineTemplate(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		String companyCode =request.getParameter("companyCode");
		String templateName = request.getParameter("templateName");
		
		String templateDesc = request.getParameter("templateDesc");
		
		String[] baselineIds= request.getParameterValues("baselineId");
		
		List ll=btService.queryAll("from BaselineTemplate b where b.templateName='"+templateName+"'");
		if(ll!=null && ll.size()>0){
			returnMsg = "模板名字不能重名，模板保存失败！";
			logger.info("basetemplate is Exist !");
			backUrl = "addBaseLineTemplate.jsp";
			return "failed";
		}
		if (companyCode == null) {
			returnMsg = "模板保存失败！";
			logger.info("fetch companyCode failed ,companyCode is null");
			backUrl = "addBaseLineTemplate.jsp";
			return "failed";
		}
		if (companyCode.trim().equals("")) {
			returnMsg = "模板保存失败！";
			logger.info("fetch companyCode failed ,companyCode is ''");
			backUrl = "addBaseLineTemplate.jsp";
			return "failed";
		}
		if (templateName == null) {
			returnMsg = "模板保存失败！";
			logger.info("fetch templateName failed ,templateName is null");
			backUrl = "addBaseLineTemplate.jsp";
			return "failed";
		}
		if (templateName.trim().equals("")) {
			returnMsg = "模板保存失败！";
			logger.info("fetch templateName failed ,templateName is ''");
			backUrl = "addBaseLineTemplate.jsp";
			return "failed";
		}
		if (baselineIds == null) {
			returnMsg = "模板保存失败！";
			logger.info("fetch baselineIds failed ,baselineIds is null");
			backUrl = "addBaseLineTemplate.jsp";
			return "failed";
		}
		BaselineTemplate bt=new BaselineTemplate();
		bt.setCompanyCode(companyCode);
		bt.setTemplateName(templateName);
		bt.setTemplateDesc(templateDesc);
		bt.setCdate(sdf.format(new Date()));
		Map<String,String> map=new HashMap<String,String>();
		for(int i=0;i<baselineIds.length;i++){
			String score=request.getParameter("score"+baselineIds[i]);
			
			map.put(baselineIds[i], score);	
		}
		btService.save(bt);
		boolean flag=btService.saveBaseLineTemplete(bt.getId(), baselineIds, map);
		
		if(!flag){
			btService.delete(bt);
			returnMsg = "模板保存失败！";
			logger.info("save baseline_template_mapping failed");
			backUrl = "/baseline/addBaseLineTemplate.jsp";
			return "failed";
		}
		return "success";
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
						sb.append("\"baselineDesc\":\"" + baseline.getBaselineDesc()
								+ "\"}");
					}else{
						sb.append("\"baselineDesc\":\"" + baseline.getBaselineDesc()
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
	public String deleteTemplate(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String templateId=request.getParameter("templateIds");
		if(templateId==null){
			returnMsg = "系统错误，删除失败！";
			logger.info("fetch templateId failed ,templateId is null");
			backUrl = "viewBaseLineTemplate.jsp";
			return "failed";
		}
		if(templateId.trim().equals("")){
			returnMsg = "系统错误，删除失败！";
			logger.info("fetch templateId failed ,templateId is ''");
			backUrl = "viewBaseLineTemplate.jsp";
			return "failed";
		}
		String templateIds[]=templateId.split(",");
		boolean flag=btService.deleteTemplate(templateIds);
		if(!flag){
			returnMsg = "系统错误，删除失败！";
			logger.info("delete failed");
			backUrl = "viewBaseLineTemplate.jsp";
			return "failed";
		}
		return "success";
	}
	public String toModifyTemplate(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String templateId=request.getParameter("templateId");
		if(templateId==null){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch templateId failed ,templateId is null");
			backUrl = "viewBaseLineTemplate.jsp";
			return "failed";
		}
		if(templateId.trim().equals("")){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch templateId failed ,templateId is ''");
			backUrl = "viewBaseLineTemplate.jsp";
			return "failed";
		}
		List templateList=btService.queryAll("select b.id,b.templateName,b.templateDesc,b.companyCode,s.companyName from BaselineTemplate b,SysDevCompany s where b.companyCode=s.companyCode and b.id="+templateId);
		if(templateList==null){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("query BaselineTemplate failed from datebase");
			backUrl = "viewBaseLineTemplate.jsp";
			return "failed";
		}
		if(templateList.size()==0){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("query BaselineTemplate failed from datebase");
			backUrl = "viewBaseLineTemplate.jsp";
			return "failed";
		}
		BaselineTemplate stt=new BaselineTemplate();
		Object[] obj=(Object[])templateList.get(0);
		stt.setId((Long)obj[0]);
		stt.setTemplateName((String)obj[1]);
		stt.setTemplateDesc((String)obj[2]);
		stt.setCompanyCode((String)obj[3]);
		stt.setCompanyName((String)obj[4]);
		List ll=btService.getSelectBaseLine(stt.getId());
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("baselineTemplate", stt);
		requestMap.put("selectBl", ll);
		return "success";
	}
	public String modifyBaseLineTemplate(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		String companyCode =request.getParameter("companyCode");
		String id =request.getParameter("id");
		String templateName = request.getParameter("templateName");
		
		String templateDesc = request.getParameter("templateDesc");
		
		String[] baselineIds= request.getParameterValues("baselineId");
		
		List ll=btService.queryAll("from BaselineTemplate b where b.templateName='"+templateName+"'");
		
		if(ll!=null && ll.size()>0){
			if(!(((BaselineTemplate)ll.get(0)).getId().toString()).equals(id)){
				returnMsg = "模板名字不能重名，模板保存失败！";
				logger.info("basetemplate is Exist !");
				backUrl = "toModifyTemplate.action?templateId="+id;
				return "failed";
			}
		}
		if (companyCode == null) {
			returnMsg = "模板保存失败！";
			logger.info("fetch companyCode failed ,companyCode is null");
			backUrl = "toModifyTemplate.action?templateId="+id;
			return "failed";
		}
		if (companyCode.trim().equals("")) {
			returnMsg = "模板保存失败！";
			logger.info("fetch companyCode failed ,companyCode is ''");
			backUrl = "toModifyTemplate.action?templateId="+id;
			return "failed";
		}
		if (templateName == null) {
			returnMsg = "模板保存失败！";
			logger.info("fetch templateName failed ,templateName is null");
			backUrl = "toModifyTemplate.action?templateId="+id;;
			return "failed";
		}
		if (templateName.trim().equals("")) {
			returnMsg = "模板保存失败！";
			logger.info("fetch templateName failed ,templateName is ''");
			backUrl = "toModifyTemplate.action?templateId="+id;;
			return "failed";
		}
		if (baselineIds == null) {
			returnMsg = "模板保存失败！";
			logger.info("fetch baselineIds failed ,baselineIds is null");
			backUrl = "toModifyTemplate.action?templateId="+id;;
			return "failed";
		}
		List bll=btService.queryAll("from BaselineTemplate b where b.id="+id);
		if(bll==null){
			returnMsg = "模板保存失败！";
			logger.info("fetch baselineTemplate failed from database");
			backUrl = "toModifyTemplate.action?templateId="+id;
			return "failed";
		}
		if(bll.size()==0){
			returnMsg = "模板保存失败！";
			logger.info("fetch baselineTemplate failed from database");
			backUrl = "toModifyTemplate.action?templateId="+id;
			return "failed";
		}
		BaselineTemplate bt=(BaselineTemplate)bll.get(0);
		bt.setCompanyCode(companyCode);
		bt.setTemplateName(templateName);
		bt.setTemplateDesc(templateDesc);
		bt.setCdate(sdf.format(new Date()));
		Map<String,String> map=new HashMap<String,String>();
		for(int i=0;i<baselineIds.length;i++){
			String score=request.getParameter("score"+baselineIds[i]);
			
			map.put(baselineIds[i], score);	
		}
		
		boolean flag=btService.saveBaseLineTemplete(bt.getId(), baselineIds, map);
		
		if(!flag){
			btService.delete(bt);
			returnMsg = "模板保存失败！";
			logger.info("save baseline_template_mapping failed");
			backUrl = "toModifyTemplate.action?templateId="+id;
			return "failed";
		}
		btService.update(bt);
		return "success";
	}
	public void getBaseLineTemplateByCompany(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String companyCode=request.getParameter("companyCode");
		String resId=request.getParameter("resId");
		StringBuilder result = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if(resId!=null&&!resId.trim().equals("")){
				List tbList=btService.queryAll("select b.id,b.templateName from BaselineTemplate b,SysResObj o where b.id=o.templateId and o.id="+resId);
				if(tbList==null){
					result.append("[]");				
					pw.println(result.toString());
					pw.flush();
					return;
				}
				result.append("[");
				for (int i = 0; i < tbList.size(); i++) {
					Object[] obj=(Object[])tbList.get(i);
					
					result.append("{\"id\":"+obj[0]+",\"text\":\""+obj[1]+"\"}");
					
					if((i+1)!=tbList.size()){
						result.append(",");
					}
					
				}
				result.append("]");
				System.out.println(result.toString());
				pw.println(result.toString());
				pw.flush();
				return ;
			}
			if(companyCode==null){
				result.append("[]");				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			if(companyCode.trim().equals("")){
				result.append("[]");				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			List templateList=btService.queryAll("from BaselineTemplate b where  b.companyCode='"+companyCode+"'");
			if(templateList==null){
				result.append("[]");				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			result.append("[");
			for (int i = 0; i < templateList.size(); i++) {
				BaselineTemplate template=(BaselineTemplate)templateList.get(i);
				
				result.append("{\"id\":"+template.getId()+",\"text\":\""+template.getTemplateName()+"\"}");
				
				if((i+1)!=templateList.size()){
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
	public String templateResMapping(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String resId=request.getParameter("resId");
		String templateId=request.getParameter("templateId");
		if(resId==null){
			returnMsg = "系统错误，映射关系保存失败！";
			logger.info("fetch resId failed , resId is null!");
			backUrl = "viewBaseLineTemplate.jsp";
			return "failed";
		}
		if(resId.trim().equals("")){
			returnMsg = "系统错误，映射关系保存失败！";
			logger.info("fetch resId failed , resId is ''!");
			backUrl = "viewBaseLineTemplate.jsp";
			return "failed";
		}
		if(templateId==null){
			returnMsg = "系统错误，映射关系保存失败！";
			logger.info("fetch templateId failed , templateId is null!");
			backUrl = "viewBaseLineTemplate.jsp";
			return "failed";
		}
		if(templateId.trim().equals("")){
			returnMsg = "系统错误，映射关系保存失败！";
			logger.info("fetch templateId failed , templateId is ''!");
			backUrl = "viewBaseLineTemplate.jsp";
			return "failed";
		}
		SysResObj res=(SysResObj)resService.getObj(SysResObj.class, Long.parseLong(resId));
		if(res==null){
			returnMsg = "系统错误，映射关系保存失败！";
			logger.info("fetch resObj failed from database!");
			backUrl = "viewBaseLineTemplate.jsp";
			return "failed";
		}
		res.setTemplateId(Long.parseLong(templateId));
		resService.update(res);
		return "success";
	}
	
}