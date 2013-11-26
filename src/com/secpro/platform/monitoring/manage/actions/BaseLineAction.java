package com.secpro.platform.monitoring.manage.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
	
}
