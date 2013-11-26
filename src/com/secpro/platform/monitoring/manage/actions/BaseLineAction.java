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

import com.secpro.platform.monitoring.manage.entity.BaselineTemplate;
import com.secpro.platform.monitoring.manage.entity.SysBaseline;
import com.secpro.platform.monitoring.manage.services.BaselineTemplateService;
import com.secpro.platform.monitoring.manage.services.SysBaselineService;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

@Controller("BaseLineAction")
public class BaseLineAction {
	PlatformLogger logger = PlatformLogger.getLogger(BaseLineAction.class);
	private BaselineTemplateService btService;
	private SysBaselineService sbService;
	private String returnMsg;
	private String backUrl;
	
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
			sb.append("{\"total\":" + templateList.size() + ",\"rows\":[");
			for (int i = 0; i < templateList.size(); i++) {
				BaselineTemplate bt = (BaselineTemplate) templateList.get(i);

				sb.append("{\"templateId\":" + bt.getId() + ",");
				sb.append("\"templateName\":\"" + bt.getTemplateName() + "\",");
				sb.append("\"templateDesc\":\"" + bt.getTemplateDesc() + "\",");
				sb.append("\"companyName\":\"" + bt.getCompanyName() + "\",");
				sb.append("\"resIp\":\"" + bt.getResip() + "\",");
				sb.append("\"resName\":\"" + bt.getResname() + "\",");
				if(bt.getBaseLineList().size()==0){
					sb.append("\"baselineDesc\":\"\",");
					sb.append("\"baselineType\":\"\",");
					if(i==(templateList.size()-1)){
						sb.append("\"blackWhite\":\"\"}");
					}else{
						sb.append("\"blackWhite\":\"\"},");
					}
				}else{
					for (int j = 0; j < bt.getBaseLineList().size(); j++) {
						SysBaseline sbl = (SysBaseline) bt.getBaseLineList().get(j);
						sb.append("\"baselineDesc\":\"" + sbl.getBaselineDesc()
								+ "\",");
						sb.append("\"baselineType\":\""
								+ (sbl.getBaselineType().equals("0") ? "配置基线"
										: "策略基线") + "\",");
						if (i==(templateList.size()-1)) {
							sb.append("\"blackWhite\":\""
									+ (sbl.getBaselinBlackWhite().equals("0") ? "白名单"
											: "黑名单") + "\"}");
						} else {
							sb.append("\"blackWhite\":\""
									+ (sbl.getBaselinBlackWhite().equals("0") ? "白名单"
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
		if (companyCode == null) {
			returnMsg = "模板保存失败！";
			logger.info("fetch companyCode failed ,companyCode is null");
			backUrl = "/baseline/addBaseLineTemplate.jsp";
			return "failed";
		}
		if (companyCode.trim().equals("")) {
			returnMsg = "模板保存失败！";
			logger.info("fetch companyCode failed ,companyCode is ''");
			backUrl = "/baseline/addBaseLineTemplate.jsp";
			return "failed";
		}
		if (templateName == null) {
			returnMsg = "模板保存失败！";
			logger.info("fetch templateName failed ,templateName is null");
			backUrl = "/baseline/addBaseLineTemplate.jsp";
			return "failed";
		}
		if (templateName.trim().equals("")) {
			returnMsg = "模板保存失败！";
			logger.info("fetch templateName failed ,templateName is ''");
			backUrl = "/baseline/addBaseLineTemplate.jsp";
			return "failed";
		}
		if (baselineIds == null) {
			returnMsg = "模板保存失败！";
			logger.info("fetch baselineIds failed ,baselineIds is null");
			backUrl = "/baseline/addBaseLineTemplate.jsp";
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
		List baselineList=sbService.queryAll("from SysBaseline");
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
					sb.append("\"blackWhite\":\"" + (baseline.getBaselineType().equals("0") ? "白名单":"黑名单") + "\",");
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
	
}
