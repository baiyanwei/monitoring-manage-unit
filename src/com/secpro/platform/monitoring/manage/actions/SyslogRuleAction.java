package com.secpro.platform.monitoring.manage.actions;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.RawSyslogHit;
import com.secpro.platform.monitoring.manage.services.SyslogRuleService;
import com.secpro.platform.monitoring.manage.util.ApplicationConfiguration;
import com.secpro.platform.monitoring.manage.util.MsuMangementAPI;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
import com.secpro.platform.monitoring.manage.webservice.SyslogRuleWSClient;

@Controller("SyslogRuleAction")
public class SyslogRuleAction {
	PlatformLogger logger = PlatformLogger.getLogger(SyslogRuleAction.class);
	private String returnMsg;
	private String backUrl;
	private String typeCode;
	private String oper;
	private File file;//获取上传文件
    private String fileFileName;//获取上传文件名称
    private String fileContentType;//获取上传文件类型
    
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
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
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}

	private SyslogRuleService service;

	public SyslogRuleService getService() {
		return service;
	}
	@Resource(name = "SyslogRuleStorage")
	public void setService(SyslogRuleService service) {
		this.service = service;
	}
	public String toChangeSyslogRule(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String typeCode = request.getParameter("typeCode");
		String operation= request.getParameter("oper");
		
		if(typeCode==null){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch typeCode failed ,typeCode is null ");
			backUrl = "rule/viewAllDevType.jsp";
		}
		if(typeCode.trim().equals("")){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch typeCode failed ,typeCode is '' ");
			backUrl = "rule/viewAllDevType.jsp";
		}
		if(operation==null){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch operation failed ,operation is null ");
			backUrl = "rule/viewAllDevType.jsp";
		}
		if(operation.trim().equals("")){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch operation failed ,operation is '' ");
			backUrl = "rule/viewAllDevType.jsp";
		}
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("typeCode",typeCode );
		requestMap.put("oper",operation );
		return "success";
	}
	public String syslogRule(){
		boolean f=true;
		if(file != null){
	        String fullFileName=  ApplicationConfiguration.SYSLOGRULEPATH+File.separator+(new Date()).getTime()+fileFileName;
	        File savefile = new File(fullFileName);
	           if(!savefile.getParentFile().exists()){
	                  savefile.getParentFile().mkdirs();
	           }
	           try {
	        	   FileUtils.copyFile(file , savefile);
	           } catch (IOException e) {
	                  // TODO Auto-generated catch block
	                  e.printStackTrace();
	                  return "failed";
	           }
	         //调用任务模块，重新洗发规则
		   		if(oper.equals("0")){//增加规则
		   			MsuMangementAPI.getInstance().publishMUSTaskToMSU(typeCode, MsuMangementAPI.MSU_COMMAND_SYSLOG_RULE_ADD);
		   		}else if(oper.equals("1")){//删除规则
		   			MsuMangementAPI.getInstance().publishMUSTaskToMSU(typeCode, MsuMangementAPI.MSU_COMMAND_SYSLOG_RULE_UPDATE);
		   		}else if(oper.equals("2")){//删除规则
		   			MsuMangementAPI.getInstance().publishMUSTaskToMSU(typeCode, MsuMangementAPI.MSU_COMMAND_SYSLOG_RULE_REMOVE);
		   		}
	            service.setRulePath(fullFileName);
				service.setTypeCode(typeCode);
				service.setCrudOper(oper);
				
				boolean flag=service.ruleStorage();
				
				if(flag){
					//调用WEBSERVICE通知核心处理重新加载规则
					int count=0;
					String res="";
					
					SyslogRuleWSClient client=new SyslogRuleWSClient();
					String wspath[]=ApplicationConfiguration.SYSLOGWSPATH.split(",");
					
					for(int j=0;j<wspath.length;j++){
						for(int i=0;i<3;i++){
							try {
								
								res=client.syslogRuleWSClient(wspath[j],typeCode+"%%"+oper);
								
								if(res.equals("1")){
									f=true;
									break;
								}else{
									f=false;
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								f=false;
							}
						}
					}
				}
		}else{
			return "failed";
		}
		
		if(f){
			return "success";
		}else{
			return "failed";
		}
	}
	public String deleteRule(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String typeCode1 = request.getParameter("typeCode");
		String operation= request.getParameter("oper");
		boolean f=true;
		if(typeCode==null){
			returnMsg = "系统错误，删除规则失败！";
			logger.info("fetch typeCode failed ,typeCode is null ");
			backUrl = "rule/viewAllDevType.jsp";
		}
		if(typeCode.trim().equals("")){
			returnMsg = "系统错误，删除规则失败！";
			logger.info("fetch typeCode failed ,typeCode is '' ");
			backUrl = "rule/viewAllDevType.jsp";
		}
		if(operation==null){
			returnMsg = "系统错误，删除规则失败！";
			logger.info("fetch operation failed ,operation is null ");
			backUrl = "rule/viewAllDevType.jsp";
		}
		if(operation.trim().equals("")){
			returnMsg = "系统错误，删除规则失败！";
			logger.info("fetch operation failed ,operation is '' ");
			backUrl = "rule/viewAllDevType.jsp";
		}
		service.setRulePath(null);
		service.setTypeCode(typeCode1);
		service.setCrudOper(operation);
		boolean flag=service.ruleStorage();
		if(flag){
			//调用WEBSERVICE
			int count=0;
			String res="";
			SyslogRuleWSClient client=new SyslogRuleWSClient();
			String wspath[]=ApplicationConfiguration.SYSLOGWSPATH.split(",");
			for(int j=0;j<wspath.length;j++){
				for(int i=0;i<3;i++){
					try {
						res=client.syslogRuleWSClient(wspath[j],typeCode+"%%"+oper);
						if(res.equals("1")){
							f=true;
							break;
						}else{
							f=false;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						f=false;
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		}

		if(f){
			return "success";
		}else{
			returnMsg = "系统错误，远程方法调用失败！";
			logger.info("syslogrule webservice error ");
			backUrl = "rule/viewAllDevType.jsp";
			return "failed";
		}
	}
	public void querySyslogHitByRes(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd" );
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
			int count=service.getRawSyslogHitCount(Long.parseLong(resId), from, to);
			List hitPage=service.getRawSyslogHitPage(Long.parseLong(resId), from, to, maxPage, pageNum);
			if(count==0){
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			sb.append("{\"total\":" + count + ",\"rows\":[");
			for (int i = 0; i < hitPage.size(); i++) {
				RawSyslogHit hit=(RawSyslogHit)hitPage.get(i);
				sb.append("{\"resId\":" + hit.getResId() + ",");
				sb.append("\"hit\":" + hit.getHitCount() + ",");
				sb.append("\"startDate\":\"" + sdf1.format(sdf.parse(hit.getStartDate())) + "\",");
				sb.append("\"endDate\":\"" + sdf1.format(sdf.parse(hit.getEndDate())) + "\",");
				
				
				if(i==(hitPage.size()-1)){
					sb.append("\"policyInfo\":\"" + hit.getPolicyInfo() + "\"}");
				}else{
					sb.append("\"policyInfo\":\"" + hit.getPolicyInfo() + "\"},");
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
}
