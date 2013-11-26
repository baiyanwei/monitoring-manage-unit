package com.secpro.platform.monitoring.manage.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.secpro.platform.monitoring.manage.entity.SysEvent;
import com.secpro.platform.monitoring.manage.entity.SysEventHis;
import com.secpro.platform.monitoring.manage.services.SysEventHisService;
import com.secpro.platform.monitoring.manage.services.SysEventService;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

/**
 * 
 * @author liyan
 * 处理告警事件类的请求
 *
 */

@Controller("EventAction")
public class EventAction extends ActionSupport{
	PlatformLogger logger=PlatformLogger.getLogger(EventAction.class);
	private SysEventService sysEventService;
	private SysEventHisService sysEventHisService;
	private SysEventHis syh;
	private SysEvent se;
	
	public SysEvent getSe() {
		return se;
	}
	public void setSe(SysEvent se) {
		this.se = se;
	}
	public SysEventHis getSyh() {
		return syh;
	}
	public void setSyh(SysEventHis syh) {
		this.syh = syh;
	}
	
	
	
	public SysEventService getSysEventService() {
		return sysEventService;
	}
	@Resource(name="SysEventServiceImpl")
	public void setSysEventService(SysEventService sysEventService) {
		this.sysEventService = sysEventService;
	}
	
	public SysEventHisService getSysEventHisService() {
		return sysEventHisService;
	}
	@Resource(name="SysEventHisServiceImpl")
	public void setSysEventHisService(SysEventHisService sysEventHisService) {
		this.sysEventHisService = sysEventHisService;
	}
	//告警确认
	public void confirmEvent(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		//session中获取用户信息
		se.setConfirmUser("liyan");
		se.setConfirmDate(sdf.format(new Date()));
		sysEventService.update(se);
		
	}
	//告警清除
	public void clearEvent(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		SysEvent setemp=null;
		if(syh.getId()==null){
			logger.info("event id is null");
			return;
		}
		List sysEvents=sysEventService.queryAll("from SysEvent s where s.id="+syh.getId());
		if(sysEvents==null){
			logger.info("query eventList filed");
			return ;
		}
		if(sysEvents.size()==0){
			logger.info("query eventList size is 0");
			return;
		}
		setemp=(SysEvent)sysEvents.get(0);
		syh.setCdate(setemp.getCdate());
		syh.setClearDate(sdf.format(new Date()) );
		//获取当前用户
		syh.setClearUser("liyan");
		syh.setEventLevel(setemp.getEventLevel());
		syh.setEventTypeId(setemp.getEventTypeId());
		syh.setMessage(setemp.getMessage());
		syh.setResId(setemp.getResId());
		sysEventHisService.save(syh);
		sysEventService.delete(setemp);
	}
	
	//根据资源ID获取告警列表
	public String getEventbyResId(){
		ActionContext actionContext = ActionContext.getContext(); 
		List eventList=sysEventService.queryAll("from SysEvent s where s.resId="+syh.getResId());
		Map<String,Object> request=(Map)actionContext.get("request");
		request.put("eventList", eventList);
		return "eventlisByid";
	}
	
	//根据时间段查找告警事件
	public String getEventbyTime(){
		
		ActionContext actionContext = ActionContext.getContext(); 
		HttpServletRequest request=ServletActionContext.getRequest();
		String from=request.getParameter("from");
		String to=request.getParameter("to");
		List eventList=sysEventService.queryAll("from SysEvent s where s.cdate>='"+from+"' and s.cdate <='"+to+"'");
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("eventList", eventList);
		return "eventlisByTime";
	}
}
