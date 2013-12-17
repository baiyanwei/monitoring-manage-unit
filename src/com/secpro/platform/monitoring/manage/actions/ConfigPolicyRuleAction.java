package com.secpro.platform.monitoring.manage.actions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.ConfigPolicyRule;
import com.secpro.platform.monitoring.manage.services.ConfigPolicyRuleService;
import com.secpro.platform.monitoring.manage.util.ApplicationConfiguration;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

@Controller("ConfigPolicyRuleAction")
public class ConfigPolicyRuleAction {
	PlatformLogger logger = PlatformLogger.getLogger(ConfigPolicyRuleAction.class);
	private String returnMsg;
	private String backUrl;
	private String typeCode;
	private String oper;
	private File file;//获取上传文件
    private String fileFileName;//获取上传文件名称
    private String fileContentType;//获取上传文件类型
    private ConfigPolicyRuleService ruleService;
    private String containConflictRule;
    
	public String getContainConflictRule() {
		return containConflictRule;
	}
	public void setContainConflictRule(String containConflictRule) {
		this.containConflictRule = containConflictRule;
	}
	public ConfigPolicyRuleService getRuleService() {
		return ruleService;
	}
	@Resource(name = "ConfigPolicyRuleServiceImpl")
	public void setRuleService(ConfigPolicyRuleService ruleService) {
		this.ruleService = ruleService;
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
	public String toChangeConfigRule(){
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
		List ruleList=ruleService.queryAll("from ConfigPolicyRule c where c.typeCode='"+typeCode+"'");
		
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		if(ruleList!=null&&ruleList.size()>0){
			requestMap.put("configRule", ruleList.get(0));
		}
		requestMap.put("typeCode",typeCode );
		requestMap.put("oper",operation );
		return "success";
	}
	public String configRule(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		if(file != null){
	        String fullFileName=  ApplicationConfiguration.CONFIGRULEPATH+File.separator+typeCode+"_"+sdf.format(new Date());
			File savefile = new File(fullFileName);
			if (!savefile.getParentFile().exists()) {
				savefile.getParentFile().mkdirs();
			}
			try {
				FileUtils.copyFile(file, savefile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				returnMsg="规则上传失败，请重新上传！";
				backUrl = "rule/viewAllDevType.jsp";
				return "failed";
			}
			List ruleList=ruleService.queryAll("from ConfigPolicyRule c where c.typeCode='"+typeCode+"'");
			if(ruleList!=null&&ruleList.size()>0){
				for(Object o:ruleList){
					ConfigPolicyRule rule=(ConfigPolicyRule)o;
					File f=new File(rule.getRulePath());
					f.delete();
					ruleService.delete(o);
				}
			}
			ConfigPolicyRule config=new ConfigPolicyRule();
			config.setRulePath(fullFileName);
			config.setTypeCode(typeCode);
			
			config.setContainConflictRule(containConflictRule);
			ruleService.save(config);
		}
		return "success";
	}
	public String deleteConfigRule(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String typeCode1 = request.getParameter("typeCode");
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
		List ruleList=ruleService.queryAll("from ConfigPolicyRule c where c.typeCode='"+typeCode+"'");
		if(ruleList!=null&&ruleList.size()>0){
			for(Object o:ruleList){
				ConfigPolicyRule rule=(ConfigPolicyRule)o;
				File f=new File(rule.getRulePath());
				f.delete();
				ruleService.delete(o);
			}
		}
		return "success";
	}
}
