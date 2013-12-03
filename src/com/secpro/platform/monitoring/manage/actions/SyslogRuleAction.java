package com.secpro.platform.monitoring.manage.actions;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.services.SyslogRuleService;
import com.secpro.platform.monitoring.manage.util.ApplicationConfiguration;
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
		System.out.println(typeCode+"-------------"+operation);
		if(typeCode==null){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch typeCode failed ,typeCode is null ");
			backUrl = "/rule/viewAllDevType.jsp";
		}
		if(typeCode.trim().equals("")){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch typeCode failed ,typeCode is '' ");
			backUrl = "/rule/viewAllDevType.jsp";
		}
		if(operation==null){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch operation failed ,operation is null ");
			backUrl = "/rule/viewAllDevType.jsp";
		}
		if(operation.trim().equals("")){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch operation failed ,operation is '' ");
			backUrl = "/rule/viewAllDevType.jsp";
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
	           
	           service.setRulePath(fullFileName);
				service.setTypeCode(typeCode);
				service.setCrudOper(oper);
				
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
			backUrl = "/rule/viewAllDevType.jsp";
		}
		if(typeCode.trim().equals("")){
			returnMsg = "系统错误，删除规则失败！";
			logger.info("fetch typeCode failed ,typeCode is '' ");
			backUrl = "/rule/viewAllDevType.jsp";
		}
		if(operation==null){
			returnMsg = "系统错误，删除规则失败！";
			logger.info("fetch operation failed ,operation is null ");
			backUrl = "/rule/viewAllDevType.jsp";
		}
		if(operation.trim().equals("")){
			returnMsg = "系统错误，删除规则失败！";
			logger.info("fetch operation failed ,operation is '' ");
			backUrl = "/rule/viewAllDevType.jsp";
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
			backUrl = "/rule/viewAllDevType.jsp";
			return "failed";
		}
	}
}
