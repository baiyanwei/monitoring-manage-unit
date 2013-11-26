package com.secpro.platform.monitoring.manage.actions;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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
import com.opensymphony.xwork2.ActionSupport;
import com.secpro.platform.monitoring.manage.actions.forms.ResObjForm;
import com.secpro.platform.monitoring.manage.entity.SysCity;
import com.secpro.platform.monitoring.manage.entity.SysResAuth;
import com.secpro.platform.monitoring.manage.entity.SysResClass;
import com.secpro.platform.monitoring.manage.entity.SysResObj;
import com.secpro.platform.monitoring.manage.entity.TelnetSshDict;
import com.secpro.platform.monitoring.manage.services.CityTreeService;
import com.secpro.platform.monitoring.manage.services.SysEventService;
import com.secpro.platform.monitoring.manage.services.SysResAuthService;
import com.secpro.platform.monitoring.manage.services.SysResClassService;
import com.secpro.platform.monitoring.manage.services.SysResObjService;
import com.secpro.platform.monitoring.manage.services.TelnetSshDictService;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
/**
 * 
 * @author liyan
 * 处理资源类的请求
 * 
 */

@Controller("ResObjectAction")
public class ResObjectAction extends ActionSupport {
	PlatformLogger logger=PlatformLogger.getLogger(ResObjectAction.class);
	private SysResObjService sysService ;
	private ResObjForm resObjForm;
	private CityTreeService cityService ;
	private SysEventService eventService;
	private SysResClassService classService;
	private TelnetSshDictService telnetSshService;
	private SysResAuthService resAuthService;
	private String returnMsg;
	private String backUrl;
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public SysResAuthService getResAuthService() {
		return resAuthService;
	}
	@Resource(name="SysResAuthServiceImpl")
	public void setResAuthService(SysResAuthService resAuthService) {
		this.resAuthService = resAuthService;
	}
	public TelnetSshDictService getTelnetSshService() {
		return telnetSshService;
	}
	@Resource(name="TelnetSshDictServiceImpl")
	public void setTelnetSshService(TelnetSshDictService telnetSshService) {
		this.telnetSshService = telnetSshService;
	}
	public SysResClassService getClassService() {
		return classService;
	}
	@Resource(name="SysResClassServiceImpl")
	public void setClassService(SysResClassService classService) {
		this.classService = classService;
	}

	public SysEventService getEventService() {
		return eventService;
	}
	
	public ResObjForm getResObjForm() {
		return resObjForm;
	}

	public void setResObjForm(ResObjForm resObjForm) {
		this.resObjForm = resObjForm;
	}

	@Resource(name="SysEventServiceImpl")
	public void setEventService(SysEventService eventService) {
		this.eventService = eventService;
	}
	
	public SysResObjService getSysService() {
		return sysService;
	}
	@Resource(name="SysResObjServiceImpl")
	public void setSysService(SysResObjService sysService) {
		this.sysService = sysService;
	}
	
	public CityTreeService getCityService() {
		return cityService;
	}
	@Resource(name="CityTreeServiceImpl")
	public void setCityService(CityTreeService cityService) {
		this.cityService = cityService;
	}
	//查询资源明细
	public String getResObjInof(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String resIdtemp=request.getParameter("resid");
		
		String resids[]=null;
		String residt="";
		if(resIdtemp.contains("_")){
			resids=resIdtemp.split("_");
			residt=resids[1];
		}else{
			residt=resIdtemp;
		}
		final String resId=residt;
		
		if(resId==null){
			logger.info("resid is null");
			returnMsg="系统错误，未获取到资源id，系统将跳转首页！";
			//backUrl="";
			return "filed";
		}
		resObjForm=new ResObjForm();
		resObjForm.setResId(resId);
		sysService.getResObjForm(resObjForm);
		
		if(resObjForm.getIp()!=null){
			String ips[]=resObjForm.getIp().split("\\.");
			
			if(ips.length==4){
				resObjForm.setResIp1(ips[0]);
				resObjForm.setResIp2(ips[1]);
				resObjForm.setResIp3(ips[2]);
				resObjForm.setResIp4(ips[3]);
				
			}
		}
		String operation=request.getParameter("operation");
		request.getSession().setAttribute("operation", operation);
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("resObjForm", resObjForm);
		return "success";
	}
	
	//更新资源
	public String modifyResObj(){
	
		HttpServletRequest request=ServletActionContext.getRequest();
		String company =request.getParameter("company");
		String devType=request.getParameter("devType");
		
		boolean flag=false;
		if(resObjForm.getResId()!=null&&!resObjForm.getResId().equals("")){
			flag=true;
		}
		if(company==null){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch companycode failed from web browser");
			return "failed";
		}
		if(company.equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch companycode failed from web browser");
			return "failed";
		}
		if(devType==null){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch devType failed from web browser");
			return "failed";
		}
		if(devType.equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch devType failed from web browser");
			return "failed";
		}
		SysResObj res=new SysResObj();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		res.setCdate(sdf.format(new Date()));	
		if(resObjForm.getCityCode()==null){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}		
			logger.info("fetch citycode failed from web browser");
			return "failed";
		}
		if(resObjForm.getCityCode().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch citycode failed from web browser");
			return "failed";
		}
		res.setCityCode(resObjForm.getCityCode());
		if(resObjForm.getClassId()==null){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch class_id failed from web browser");
			return "failed";
		}
		if(resObjForm.getClassId().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch class_id failed from web browser");
			return "failed";
		}
		res.setClassId(Long.parseLong(resObjForm.getClassId()));
		if(resObjForm.getCompany()==null){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch companycode failed from web browser");
			return "failed";
		}
		if(resObjForm.getCompany().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch companycode failed from web browser");
			return "failed";
		}
		res.setCompanyCode(resObjForm.getCompany());
		if(resObjForm.getConfigOperation()==null){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch ConfigOperation failed from web browser");
			return "failed";
		}
		if(resObjForm.getConfigOperation().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch ConfigOperation failed from web browser");
			return "failed";
		}
		res.setConfigOperation(resObjForm.getConfigOperation());
		if(resObjForm.getResId()==null){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch ResId failed from web browser");
			return "failed";
		}
		if(resObjForm.getResId().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch ResId failed from web browser");
			return "failed";
		}
		res.setId(Long.parseLong(resObjForm.getResId()));
		if(resObjForm.getMcaId()==null){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch mcaId failed from web browser");
			return "failed";
		}
		if(resObjForm.getMcaId().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch mcaId failed from web browser");
			return "failed";
		}
		res.setMcaId(Long.parseLong(resObjForm.getMcaId()));
		res.setResDesc(resObjForm.getResDesc());
		if(resObjForm.getResIp1()==null||resObjForm.getResIp2()==null||resObjForm.getResIp3()==null||resObjForm.getResIp4()==null){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch ip failed from web browser");
			return "failed";
		}
		if(resObjForm.getResIp1().equals("")||resObjForm.getResIp2().equals("")||resObjForm.getResIp3().equals("")||resObjForm.getResIp4().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch ip failed from web browser");
			return "failed";
		}
		res.setResIp(resObjForm.getResIp1()+"."+resObjForm.getResIp2()+"."+resObjForm.getResIp3()+"."+resObjForm.getResIp4());
		if(resObjForm.getResName()==null){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch resid failed from web browser");
			return "failed";
		}
		if(resObjForm.getResName().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch resid failed from web browser");
			return "failed";
		}
		res.setResName(resObjForm.getResName());
		if(resObjForm.getResPaused()==null){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch resPaused failed from web browser");
			return "failed";
		}
		if(resObjForm.getResPaused().equals("")){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch resPaused failed from web browser");
			return "failed";
		}
		res.setResPaused(resObjForm.getResPaused());
		if(resObjForm.getStatusOperation()==null){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch statusOperation failed from web browser");
			return "failed";
		}
		if(resObjForm.getStatusOperation().equals("")){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch StatusOperation failed from web browser");
			return "failed";
		}
		res.setStatusOperation(resObjForm.getStatusOperation());
		if(resObjForm.getDevType()==null){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch devType failed from web browser");
			return "failed";
		}
		if(resObjForm.getDevType().equals("")){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch devType failed from web browser");
			return "failed";
		}
		res.setTypeCode(resObjForm.getDevType());
		
		if(resObjForm.getAuthId()==null){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch SysResAuth failed from db");
			return "failed";
		}
		if(resObjForm.getAuthId().equals("")){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("query SysResAuth failed from db");
			return "failed";
		}
		List auths=resAuthService.queryAll("from SysResAuth s where s.id="+resObjForm.getAuthId());
		if(auths==null){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("query SysResAuth failed from db");
			return "failed";
		}
		SysResAuth auth=(SysResAuth)auths.get(0);
		if(!company.equals(resObjForm.getCompany())||!devType.equals(resObjForm.getDevType())){
			List dicts=telnetSshService.queryAll("from TelnetSshDict t where t.companyCode='"+resObjForm.getCompany()+"' and t.typeCode='"+resObjForm.getDevType()+"'");
			if(dicts==null){
				returnMsg=("修改失败，修改值获取失败！");
				if(flag){
					backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
				}else{
					
				}
				logger.info("query dict failed from db");
				return "failed";
			}
			
			if(dicts.size()==0){
				returnMsg=("修改失败，修改值获取失败！");
				if(flag){
					backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
				}else{
					
				}
				logger.info("query dict failed from db");
				return "failed";
			}
			TelnetSshDict tsd=(TelnetSshDict)dicts.get(0);
			auth.setExecPrompt(tsd.getExecPrompt());
			auth.setNextPrompt(tsd.getExecPrompt());
			auth.setPassPrompt(tsd.getPassPrompt());
			auth.setPrompt(tsd.getPrompt());
			auth.setSepaWord(tsd.getSepaWord());
			auth.setUserPrompt(tsd.getUserPrompt());		
		}
		auth.setCommunity(resObjForm.getCommuinty());
		if(resObjForm.getPassword()==null){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch password failed from web browser");
			return "failed";
		}
		if(resObjForm.getPassword().equals("")){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("query password failed from db");
			return "failed";
		}
		auth.setPassword(resObjForm.getPassword());
		if(resObjForm.getUsername()==null){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch username failed from web browser");
			return "failed";
		}
		if(resObjForm.getUsername().equals("")){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("query username failed from db");
			return "failed";
		}
		auth.setUsername(resObjForm.getUsername());
		auth.setSnmpv3Auth(resObjForm.getSnmpau());
		auth.setSnmpv3Authpass(resObjForm.getSnmpaups());
		auth.setSnmpv3Priv(resObjForm.getSnmppr());
		auth.setSnmpv3Privpass(resObjForm.getSnmpprps());
		auth.setSnmpv3User(resObjForm.getSnmpuser());
		sysService.update(res);
		resAuthService.update(auth);
		String operation=request.getParameter("operation");
		request.getSession().setAttribute("operation", operation);
		return "success";
	}
	//删除资源
	public String removeResObj(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String resId = request.getParameter("resId");
		
		String res[]=null;
		if(resId==null){
			returnMsg=("删除失败！！");
			return "success";
		}
		if(resId.contains("_")){
			res=resId.split("_");
		}
		SysResObj reo=new SysResObj();
		if(res!=null){
			reo.setId(Long.parseLong(res[1]));
			sysService.delete(reo);
		}else{
			reo.setId(Long.parseLong(resId));
			sysService.delete(reo);
		}
		List authlist=resAuthService.queryAll("from SysResAuth s where s.resId="+reo.getId());
		
		if(authlist!=null&&authlist.size()!=0){
			for(int i=0;i<authlist.size();i++){
				resAuthService.delete(authlist.get(i));
			}
		}
		List eventlist=eventService.queryAll("from SysEvent s where s.resId="+reo.getId());
		
		
		if(eventlist!=null&&eventlist.size()!=0){
			
			for(int i=0;i<eventlist.size();i++){
				eventService.delete(eventlist.get(i));
			}
		}
		
		return "success";
	}
	//添加保存资源
	public String saveResObj() throws Exception{
		SysResObj res=new SysResObj();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		res.setCityCode(resObjForm.getCityCode());
		res.setCompanyCode(resObjForm.getCompany());
		res.setConfigOperation(resObjForm.getConfigOperation());
		if(resObjForm.getMcaId()==null){
			returnMsg=("系统错误，资源保存失败！");		
			logger.info("mca id is null ");
			return "failed";
		}
		res.setMcaId(Long.parseLong(resObjForm.getMcaId()));
		res.setResDesc(resObjForm.getResDesc());
		res.setResIp(resObjForm.getResIp1()+"."+resObjForm.getResIp2()+"."+resObjForm.getResIp3()+"."+resObjForm.getResIp4());
		res.setResName(resObjForm.getResName());
		res.setStatusOperation(resObjForm.getStatusOperation());
		res.setResPaused(resObjForm.getResPaused());
		res.setTypeCode(resObjForm.getDevType());
		res.setCdate(sdf.format(new Date()));
		List resClasses =classService.queryAll("from SysResClass s where s.className='fw'");
		if(resClasses==null){
			logger.info("fetch classid of fw faild");
			returnMsg=("系统错误，资源保存失败！");	
			return "failed";
		}
		if(resClasses.size()==0){
			logger.info("fetch classid of fw faild");
			returnMsg=("系统错误，资源保存失败！");	
			return "failed";
		}
		long classid=((SysResClass)resClasses.get(0)).getId();
		List dicts=telnetSshService.queryAll("from TelnetSshDict t where t.companyCode='"+res.getCompanyCode()+"' and t.typeCode='"+res.getTypeCode()+"'");
		if(dicts==null){
			logger.info("fetch TelnetSshDict failed ,by companycode="+res.getCompanyCode()+" and typeCode="+res.getTypeCode());
			returnMsg=("系统错误，资源保存失败！");	
			return "failed";
		}
		
		if(dicts.size()==0){
			logger.info("fetch TelnetSshDict failed ,by companycode="+res.getCompanyCode()+" and typeCode="+res.getTypeCode());
			returnMsg=("系统错误，资源保存失败！");	
			return "failed";
		}
		res.setClassId(classid);
		
		sysService.save(res);
		
		TelnetSshDict tsd=(TelnetSshDict)dicts.get(0);
		SysResAuth resAuth=new SysResAuth();
		resAuth.setCommunity(resObjForm.getCommuinty());
		resAuth.setExecPrompt(tsd.getExecPrompt());
		resAuth.setNextPrompt(tsd.getNextPrompt());
		resAuth.setPassPrompt(tsd.getPassPrompt());
		resAuth.setPassword(resObjForm.getPassword());
		resAuth.setPrompt(tsd.getPrompt());
		resAuth.setSepaWord(tsd.getSepaWord());
		resAuth.setSnmpv3Auth(resObjForm.getSnmpau());
		resAuth.setSnmpv3Authpass(resObjForm.getSnmpaups());
		resAuth.setSnmpv3Priv(resObjForm.getSnmppr());
		resAuth.setSnmpv3Privpass(resObjForm.getSnmpprps());
		resAuth.setSnmpv3User(resObjForm.getSnmpuser());
		resAuth.setUsername(resObjForm.getUsername());
		resAuth.setUserPrompt(tsd.getUserPrompt());
		resAuth.setResId(res.getId());
		resAuthService.save(resAuth);
		return "success";
	}
	//跳转到添加资源页面
	public String toAddResObj(){

		HttpServletRequest request=ServletActionContext.getRequest();
		String cityCode=request.getParameter("cityCode");
		String operation=request.getParameter("operation");
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		String mcaCityCode=sysService.getOuterParentCityCode(cityCode);
		if(mcaCityCode.equals("")){
			
			logger.info("fetch mcaCityCode  by  one CiyeCode filed , the CiyeCode ="+cityCode);
			returnMsg=("系统错误，请重新登录或联系维护人员，点击确定返回首页！");	
			return "failed";
		}
		List mcas=sysService.queryAll("select s.id from SysResObj s, SysResClass c  where s.cityCode='"+mcaCityCode+"' and s.classId=c.id and c.className='mca'");
		if(mcas==null){	
			logger.info("fetch mca by CiyeCode filed , the CiyeCode ="+cityCode);
			returnMsg=("系统错误，请重新登录或联系维护人员,点击确定返回首页！");	
			return "failed";
		}
		if(mcas.size()==0){
			logger.info("fetch mca by CiyeCode filed , the CiyeCode ="+cityCode);
			returnMsg=("系统错误，请重新登录或联系维护人员,点击确定返回首页！");	
			return "failed";
		}
		requestMap.put("cityCode", cityCode);
		requestMap.put("mca",mcas.get(0));
		request.getSession().setAttribute("operation", operation);
		return "toAddSysObj";
	}
	public String viewAllMca(){
		SimpleDateFormat fmt1 = new SimpleDateFormat("yyyyMMddHHmmss"); 
		SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd");
		HttpServletRequest request=ServletActionContext.getRequest();
		String rows=request.getParameter("rows");
		String page=request.getParameter("page");
		List mcas=sysService.queryAll("select s.id,s.resName,s.resDesc,s.cdate,s.cityCode,s.resIp,sc.cityName from SysResObj s, SysResClass c ,SysCity sc  where s.classId=c.id and c.className='mca' and s.cityCode = sc.cityCode");
		
		int pageNum=1;
		int maxPage=10;
		if(rows!=null&&!rows.trim().equals("")){
			maxPage=Integer.parseInt(rows); 
		}
		if(page!=null&&!page.trim().equals("")){
			pageNum=Integer.parseInt(page); 
		}
		List mcaPage = sysService.queryByPage("select s.id,s.resName,s.resDesc,s.cdate,s.resIp,s.resPaused,sc.cityName from SysResObj s, SysResClass c ,SysCity sc  where s.classId=c.id and c.className='mca' and s.cityCode = sc.cityCode ",pageNum,maxPage);
		
		if(mcaPage==null){
			returnMsg="获取采集端失败！";
			return "failed";

		}
		if(mcaPage.size()==0){
			returnMsg="获取采集端失败！";
			return "failed";
		}
		
		StringBuilder sb=new StringBuilder();
		PrintWriter pw = null;
		try {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/json");
		pw = resp.getWriter();
		sb.append("{\"total\":"+mcas.size()+",\"rows\":[");
		for(int i=0;i<mcaPage.size();i++){
			Object[] objs=(Object[])mcaPage.get(i);
			List maxlevel=eventService.queryAll("select max(e.eventLevel) from SysEvent e where e.resId="+objs[0]);
			String date="";
			try {
				date=fmt2.format(fmt1.parse((String)objs[3]));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sb.append("{\"mcaid\":"+objs[0]+",");
			sb.append("\"resName\":\""+objs[1]+"\",");
			sb.append("\"resDesc\":\""+objs[2]+"\",");
			sb.append("\"cdate\":\""+date+"\",");
			sb.append("\"resIp\":\""+objs[4]+"\",");
			sb.append("\"mcapaused\":\""+objs[5]+"\",");
			sb.append("\"cityName\":\""+objs[6]+"\",");
			if(objs[5].equals("1")){
				sb.append("\"maxLevel\":\"-1\"},");
			}else if(i!=mcaPage.size()-1){			
				if(maxlevel!=null&&maxlevel.size()>0){
					
					sb.append("\"maxLevel\":\""+maxlevel.get(0)+"\"},");
				}else{
					sb.append("\"maxLevel\":\"0\"},");
				}
			}else{
				
				if(maxlevel!=null&&maxlevel.size()>0){
					sb.append("\"maxLevel\":\""+maxlevel.get(0)+"\"}");
				}else{
					sb.append("\"maxLevel\":\"0\"}");
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
	public String updateMcaPaused(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String mcaId=request.getParameter("mcaid");
		String paused=request.getParameter("paused");
		if(mcaId==null){
			returnMsg="修改采集端状态失败！";
			logger.info("fetch mcaId failed ,mcaId is null");
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		if(mcaId.trim().equals("")){
			returnMsg="修改采集端状态失败！";
			logger.info("fetch mcaId failed ,mcaId is ''");
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		if(paused==null){
			returnMsg="修改采集端状态失败！";
			logger.info("fetch paused failed ,mcaId is null");
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		if(paused.trim().equals("")){
			returnMsg="修改采集端状态失败！";
			logger.info("fetch paused failed ,mcaId is ''");
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		SysResObj mca=(SysResObj)sysService.getObj(SysResObj.class, Long.parseLong(mcaId));
		if(mca==null){
			returnMsg="修改采集端状态失败！";
			logger.info("fetch mca failed by mcaid "+mcaId);
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		mca.setResPaused(paused);
		sysService.update(mca);
		return "success";
	}
	public String toModifyMca(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String mcaId=request.getParameter("mcaid");
		if(mcaId==null){
			returnMsg="跳转修改页面失败！";
			logger.info("fetch mcaId failed ,mcaId is null");
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		if(mcaId.trim().equals("")){
			returnMsg="跳转修改页面失败！";
			logger.info("fetch mcaId failed ,mcaId is ''");
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		SysResObj mca=(SysResObj)sysService.getObj(SysResObj.class, Long.parseLong(mcaId));
		System.out.println(mca.getCityCode()+"-1---------------------");
		if(mca==null){
			returnMsg="跳转修改页面失败！";
			logger.info("fetch mca failed by mcaId "+mcaId);
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		List cityList=cityService.queryAll("from SysCity s where s.cityCode='"+mca.getCityCode()+"'");
		String ciytName="";
		System.out.println(cityList.size()+"-2---------------------");
		if(cityList!=null&&cityList.size()!=0){
			ciytName=((SysCity)cityList.get(0)).getCityName();
		}
		String ip1="";
		String ip2="";
		String ip3="";
		String ip4="";
		if(mca.getResIp()!=null){
			String ips[]=mca.getResIp().split("\\.");
			
			if(ips.length==4){
				ip1=ips[0];
				ip2=ips[1];
				ip3=ips[2];
				ip4=ips[3];
			}
		}
		System.out.println(ciytName+"-3---------------------");
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("mca", mca);
		requestMap.put("cityName", ciytName);
		requestMap.put("ip1", ip1);
		requestMap.put("ip2", ip2);
		requestMap.put("ip3", ip3);
		requestMap.put("ip4", ip4);
		return "success";
	}
	public String modifyMca(){
		HttpServletRequest request=ServletActionContext.getRequest();
		if(resObjForm.getResId()==null){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch mcaId failed ,mcaId is null");
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		if(resObjForm.getResId().trim().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch mcaId failed ,mcaId is ''");
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		
		SysResObj mca=(SysResObj)sysService.getObj(SysResObj.class, Long.parseLong(resObjForm.getResId()));
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		mca.setCdate(sdf.format(new Date()));	
		if(resObjForm.getCityCode()==null){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch citycode failed ,citycode is null");
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		if(resObjForm.getCityCode().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch citycode failed ,citycode is ''");
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		mca.setCityCode(resObjForm.getCityCode());
		
		
		mca.setResDesc(resObjForm.getResDesc());
		if(resObjForm.getResIp1()==null||resObjForm.getResIp2()==null||resObjForm.getResIp3()==null||resObjForm.getResIp4()==null){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch ip failed ,one of ip is null");
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		if(resObjForm.getResIp1().equals("")||resObjForm.getResIp2().equals("")||resObjForm.getResIp3().equals("")||resObjForm.getResIp4().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch ip failed ,one of ip is ''");
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		mca.setResIp(resObjForm.getResIp1()+"."+resObjForm.getResIp2()+"."+resObjForm.getResIp3()+"."+resObjForm.getResIp4());
		if(resObjForm.getResName()==null){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch resName failed ,one of ip is null");
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		if(resObjForm.getResName().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch resName failed ,one of ip is ''");
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		mca.setResName(resObjForm.getResName());
		if(resObjForm.getResPaused()==null){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch resPaused failed ,resPaused is null");
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		if(resObjForm.getResPaused().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch resPaused failed ,resPaused is ''");
			backUrl="/resobj/viewMca.jsp";
			return "failed";
		}
		mca.setResPaused(resObjForm.getResPaused());
		
		sysService.update(mca);
		
		return "success";
	}
	public String saveMca(){
		SysResObj mca=new SysResObj();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		mca.setCityCode(resObjForm.getCityCode());
		
		mca.setResDesc(resObjForm.getResDesc());
		mca.setResIp(resObjForm.getResIp1()+"."+resObjForm.getResIp2()+"."+resObjForm.getResIp3()+"."+resObjForm.getResIp4());
		mca.setResName(resObjForm.getResName());
		
		mca.setResPaused(resObjForm.getResPaused());
		mca.setCdate(sdf.format(new Date()));
		List resClasses =classService.queryAll("from SysResClass s where s.className='mca'");
		if(resClasses==null){
			logger.info("fetch classid of fw faild");
			returnMsg=("系统错误，资源保存失败！");	
			return "failed";
		}
		if(resClasses.size()==0){
			logger.info("fetch classid of fw faild");
			returnMsg=("系统错误，资源保存失败！");	
			return "failed";
		}
		long classid=((SysResClass)resClasses.get(0)).getId();
		mca.setClassId(classid);		
		sysService.save(mca);
		return "success";
	}
	public String deleteMca(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String mcaId=request.getParameter("mcaids");
		String[] mcaIds=mcaId.split(",");
		List resList=null;
		for(int i=0;i<mcaIds.length;i++){
			if(!mcaIds[i].trim().equals("")){
				resList=sysService.queryAll("from SysResObj s where s.mcaId="+mcaIds[i]);
				if(resList!=null&&resList.size()>0){
					logger.info("MCA has FW , mcaId is "+mcaIds[i]);
					returnMsg=("请先删除归属采集端的防火墙资源在进行删除，删除失败！");	
					backUrl="/resobj/viewMca.jsp";
					return "failed";
				}
			}
		}
		for(int i=0;i<mcaIds.length;i++){
			if(!mcaIds[i].trim().equals("")){
				SysResObj mca =new SysResObj();
				mca.setId(Long.parseLong(mcaIds[i]));
				sysService.delete(mca);
			}
			
		}
		return "success";
	}
	public void findMcaDate(){
		
	}
}