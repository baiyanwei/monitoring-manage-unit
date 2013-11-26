package com.secpro.platform.monitoring.manage.actions;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.secpro.platform.monitoring.manage.entity.SysCity;
import com.secpro.platform.monitoring.manage.entity.SysDevCompany;
import com.secpro.platform.monitoring.manage.services.CityTreeService;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
@Controller("CityTreeAction")
public class SysCityAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private PlatformLogger log=PlatformLogger.getLogger(SysCityAction.class);
	private CityTreeService _userService;

	public CityTreeService get_userService() {
		return _userService;
	}
	public void set_userService(CityTreeService _userService) {
		this._userService = _userService;
	}
	public CityTreeService getUserService() {
		return _userService;
	}
	@Resource(name="CityTreeServiceImpl")
	public void setUserService(CityTreeService userService) {
		this._userService = userService;
	}
	public void getCityTree(){
		log.debug("fetch city restree");
		List sysCitys=null;
		String citycode =ServletActionContext.getRequest().getParameter("citycode");
		if(citycode!=null&&!citycode.equals("")){
			sysCitys=_userService.queryAll("from com.secpro.platform.monitoring.manage.entity.SysCity s where s.parentCode=\'"+citycode+"\'");
		}else{
			sysCitys=_userService.queryAll("from com.secpro.platform.monitoring.manage.entity.SysCity s where s.parentCode=\'0\'");
		}	
		PrintWriter pw=null;
		try {
			HttpServletResponse resp=ServletActionContext.getResponse();
		//	String path=ServletActionContext.getRequest().getRequestURL().toString();
			resp.setContentType("text/xml"); 
			pw=resp.getWriter();
			StringBuffer sb=new StringBuffer();
			sb.append("<tree>");
			if(pw!=null){
				for(int i=0;i<sysCitys.size();i++){
					SysCity sc=(SysCity)sysCitys.get(i);
					List ll=_userService.queryAll("from com.secpro.platform.monitoring.manage.entity.SysCity s where s.parentCode=\'"+sc.getCityCode()+"\'");
					if(ll.size()>0){
						sb.append("<tree text=\""+sc.getCityName()+"\" src=\"CityTreeAction?citycode="+sc.getCityCode()+"\""+"/>");
					}else{
						sb.append("<tree text=\""+sc.getCityName()+"\"/>");
					}
				}
			}
			sb.append("</tree>");
			log.debug(sb.toString());
			pw.println(sb.toString());
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(pw!=null){
				pw.close();
			}
		}
	}
	public void getAllCity(){
		List citys = _userService.queryAll("from SysCity s where s.parentCode='1'");
		StringBuilder result = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (citys == null) {
				result.append("[]");
				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			result.append("[");
			for (int i = 0; i < citys.size(); i++) {
				SysCity city=(SysCity)citys.get(i);
				
				result.append("{\"id\":"+city.getCityCode()+",\"text\":\""+city.getCityName()+"\"}");
				
				if((i+1)!=citys.size()){
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
}
