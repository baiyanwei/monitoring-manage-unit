package com.secpro.platform.monitoring.manage.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.secpro.platform.monitoring.manage.actions.forms.DevCompanyForm;
import com.secpro.platform.monitoring.manage.entity.SysDevCompany;
import com.secpro.platform.monitoring.manage.entity.SysDevType;
import com.secpro.platform.monitoring.manage.services.SysDevCompanyService;
import com.secpro.platform.monitoring.manage.services.SysDevTypeService;
import com.secpro.platform.monitoring.manage.services.SysResObjService;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

@Controller("SysDevAction")
public class SysDevAction extends ActionSupport {
	private SysDevCompanyService sdcs;
	private SysDevTypeService sdts;
	private SysResObjService sros;
	private String returnMsg;
	private String backUrl;
	private DevCompanyForm company;
	
	public SysResObjService getSros() {
		return sros;
	}
	@Resource(name = "SysResObjServiceImpl")
	public void setSros(SysResObjService sros) {
		this.sros = sros;
	}

	public DevCompanyForm getCompany() {
		return company;
	}

	public void setCompany(DevCompanyForm company) {
		this.company = company;
	}


	PlatformLogger logger=PlatformLogger.getLogger(SysDevAction.class);
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

	public SysDevCompanyService getSdcs() {
		return sdcs;
	}

	@Resource(name = "SysDevCompanyServiceImpl")
	public void setSdcs(SysDevCompanyService sdcs) {
		this.sdcs = sdcs;
	}

	public SysDevTypeService getSdts() {
		return sdts;
	}

	@Resource(name = "SysDevTypeServiceImpl")
	public void setSdts(SysDevTypeService sdts) {
		this.sdts = sdts;
	}

	public void getAllCompany() {
		List companys = sdcs.queryAll("from SysDevCompany");
		StringBuilder result = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (companys == null) {
				result.append("[]");
				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			result.append("[");
			for (int i = 0; i < companys.size(); i++) {
				SysDevCompany sdc=(SysDevCompany)companys.get(i);
				
				result.append("{\"id\":"+sdc.getCompanyCode()+",\"text\":\""+sdc.getCompanyName()+"\"}");
				
				if((i+1)!=companys.size()){
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
	public void getDevTypeByCompanyCode(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String companyCode=request.getParameter("companyCode");
		List SysDevTypes=sdts.queryAll("from SysDevType s where s.companyCode='"+companyCode+"'");
		StringBuilder result = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (SysDevTypes == null) {
				result.append("[]");
				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			result.append("[");
			for (int i = 0; i < SysDevTypes.size(); i++) {
				SysDevType sdc=(SysDevType)SysDevTypes.get(i);
				if(i==0){
					result.append("{\"id\":"+sdc.getTypeCode()+",\"text\":\""+sdc.getTypeName()+"\",\"selected\":true}");
				}else{
					result.append("{\"id\":"+sdc.getTypeCode()+",\"text\":\""+sdc.getTypeName()+"\"}");
				}
				if((i+1)!=SysDevTypes.size()){
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
	public void toViewAllCompany(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String rows=request.getParameter("rows");
		String page=request.getParameter("page");
		
		List allSize = sdcs.queryAll("from SysDevCompany");
		int pageNum=1;
		int maxPage=10;
		if(rows!=null&&!rows.trim().equals("")){
			maxPage=Integer.parseInt(rows); 
		}
		if(page!=null&&!page.trim().equals("")){
			pageNum=Integer.parseInt(page); 
		}
		
		List companys = sdcs.queryByPage("from SysDevCompany",pageNum,maxPage);
		if(companys==null){
			returnMsg="获取设备厂商信息失败！";
			//return "failed";
			return;
		}
		if(companys.size()==0){
			returnMsg="获取设备厂商信息失败！";
		//	return "failed";
		}
		StringBuilder sb=new StringBuilder();
		PrintWriter pw = null;
		try {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/json");
		pw = resp.getWriter();
		sb.append("{\"total\":"+allSize.size()+",\"rows\":[");
		for(int i=0;i<companys.size();i++){
			SysDevCompany company=(SysDevCompany)companys.get(i);
			sb.append("{\"companyid\":"+company.getId()+",");
			sb.append("\"companyname\":\""+company.getCompanyName()+"\",");
			sb.append("\"companycode\":\""+company.getCompanyCode()+"\",");
			if(i!=companys.size()-1){			
				if(company.getCompanyDesc()==null){
					sb.append("\"companydesc\":\" \"},");
				}else{
					sb.append("\"companydesc\":\""+company.getCompanyDesc()+"\"},");
				}
			}else{
				
				if(company.getCompanyDesc()==null){
					sb.append("\"companydesc\":\" \"}");
				}else{
					sb.append("\"companydesc\":\""+company.getCompanyDesc()+"\"}");
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
		/*ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("companyList", companys);
		return "success";*/
	}
	public String deleteCompany(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String companyIds=request.getParameter("companyId");
		if(companyIds==null){
			returnMsg="删除设备厂商失败！";
			logger.info("fetch companyid failed ,companyid is null");
			return "failed";
		}
		if(companyIds.equals("")){
			returnMsg="删除设备厂商失败！";
			logger.info("fetch companyid failed ,companyid is ''");
			return "failed";
		}
		String companyId[]=companyIds.split(",");
		List resList=null;
		for(int i=0;i<companyId.length;i++){
			
			resList=sros.queryAll("select s.id from SysResObj s, SysDevCompany c  where s.companyCode=c.companyCode and c.id="+companyId[i]);
			
			if(resList!=null&&resList.size()>0){
				
				returnMsg="删除厂商前，请先删除属于此厂商的防火墙资源！";
				logger.info("delete company failed ,one of company has fwRes companyid is "+companyId[i]);
				backUrl="viewAllDevCompany.jsp";
				return "failed";
			}
		}
		for(int i=0;i<companyId.length;i++){
			SysDevCompany sdc=new SysDevCompany();
			sdc.setId(Long.parseLong(companyId[i]));
			String res=sdts.deleteDevtypeByCompanyId(companyId[i]);
			if(res!=null&&res.equals("0")){
				sdcs.delete(sdc);
			}
		}
		return "success";
	}
	public String toViewDevType(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String companyCode=request.getParameter("companyCode");
		if(companyCode==null){
			returnMsg="查询设备型号失败！";
			logger.info("fetch companyCode failed ,companyCode is null");
			return "failed";
		}
		if(companyCode.equals("")){
			returnMsg="查询设备型号失败！";
			logger.info("fetch companyCode failed ,companyCode is null");
			return "failed";
		}
		List devTypes=sdts.queryAll("from SysDevType s where s.companyCode='"+companyCode+"'");
		if(devTypes==null){
			returnMsg="查询设备型号失败！";
			logger.info("fetch devType failed by companyCode is "+companyCode);
			return "failed";
		}
		if(devTypes.size()==0){
			returnMsg="查询设备型号失败！";
			logger.info("fetch devType failed by companyCode is "+companyCode);
			return "failed";
		}
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("devType", devTypes.get(0));
		return "success";
	}
	public String modifyCompany(){
		if(company.getId()==null){
			returnMsg="设备厂商更新失败！";
			logger.info("modify company failed , companyid is null");
			return "failed";
		}
		if(company.getId().equals("")){
			returnMsg="设备厂商更新失败！";
			logger.info("modify company failed , companyid is ''");
			return "failed";
		}
		if(company.getCompanyCode()==null){
			returnMsg="设备厂商更新失败！";
			logger.info("modify company failed , companycode is null");
			return "failed";
		}
		if(company.getCompanyCode().equals("")){
			returnMsg="设备厂商更新失败！";
			logger.info("modify company failed , companycode is ''");
			return "failed";
		}
		if(company.getCompanyName()==null){
			returnMsg="设备厂商更新失败！";
			logger.info("modify company failed , companycode is null");
			return "failed";
		}
		if(company.getCompanyName().trim().equals("")){
			returnMsg="设备厂商更新失败！";
			logger.info("modify company failed , companycode is ''");
			backUrl="toViewCompanyById.action?companyid="+company.getId();
			return "failed";
		}
		SysDevCompany c=new SysDevCompany();
		c.setId(Long.parseLong(company.getId()));
		c.setCompanyName(company.getCompanyName());
		c.setCompanyCode(company.getCompanyCode());
		c.setCompanyDesc(company.getCompanyDesc());
		sdcs.update(c);
		return "success";
		
	}
	public String getCompanyById(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String companyid=request.getParameter("companyid");
		if(companyid==null){
			returnMsg="查询设备厂商失败！";
			logger.info("fetch companyid failed ,companyid is null");
			backUrl="toViewCompany.action";
			return "failed";
		}
		if(companyid.equals("")){
			returnMsg="查询设备厂商失败！";
			logger.info("fetch companyid failed ,companyid is ''");
			backUrl="toViewCompany.action";
			return "failed";
		}
		List companys=sdcs.queryAll("from SysDevCompany s where s.id="+companyid);
		if(companys==null){
			returnMsg="查询设备厂商失败！";
			logger.info("fetch companyid failed ,by companyid is "+companyid);
			backUrl="toViewCompany.action";
			return "failed";
		}
		if(companys.size()==0){
			returnMsg="查询设备厂商失败！";
			logger.info("fetch companyid failed ,by companyid is "+companyid);
			backUrl="toViewCompany.action";
			return "failed";
		}
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("company",companys.get(0) );
		return "success";
	}
	public String saveCompany(){
		if(company.getCompanyName()==null){
			returnMsg="厂商名称不能为空，保存失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch companyName failed is null from web browser");
			return "failed";
		}
		if(company.getCompanyName().trim().equals("")){
			returnMsg="厂商名称不能为空，保存失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch companyName failed is '' from web browser");
			return "failed";
		}
		if(company.getCompanyCode()==null){
			returnMsg="厂商编码不能为空，保存失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch companyCode failed is null from web browser");
			return "failed";
		}
		if(company.getCompanyCode().trim().equals("")){
			returnMsg="厂商编码不能为空，保存失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch companyCode failed is '' from web browser");
			return "failed";
		}
		SysDevCompany c=new SysDevCompany();
		c.setCompanyCode(company.getCompanyCode());
		c.setCompanyName(company.getCompanyName());
		c.setCompanyDesc(company.getCompanyDesc());
		sdcs.save(c);
		return "success";
	}
	public String toAddType(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String companyCode=request.getParameter("companyCode");
		if(companyCode==null){
			returnMsg="跳转添加防火墙型号页面失败";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch companyCode failed is null from web browser");
			return "failed";
		}
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("companyCode", companyCode);
		return "success";
	}
	public String saveDevType(){
		if(company.getCompanyCode()==null){
			returnMsg="厂商编码获取失败，保存失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch companyCode failed is null from web browser");
			return "failed";
		}
		if(company.getCompanyCode().trim().equals("")){
			returnMsg="厂商编码获取失败，保存失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch companyCode failed is '' from web browser");
			return "failed";
		}
		if(company.getTypeName()==null){
			returnMsg="设备类型名称不能为空，保存失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch typeName failed is null from web browser");
			return "failed";
		}
		if(company.getTypeName().trim().equals("")){
			returnMsg="设备类型名称不能为空，保存失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch typeName failed is '' from web browser");
			return "failed";
		}
		if(company.getTypeCode()==null){
			returnMsg="设备编码不能为空，保存失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch typeCode failed is null from web browser");
			return "failed";
		}
		if(company.getTypeCode().trim().equals("")){
			returnMsg="设备编码不能为空，保存失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch typeCode failed is '' from web browser");
			return "failed";
		}
		SysDevType sdt=new SysDevType();
		sdt.setTypeName(company.getTypeName());
		sdt.setTypeCode(company.getTypeCode());
		sdt.setTypeDesc(company.getTypeDesc());
		sdt.setCompanyCode(company.getCompanyCode());
		sdts.save(sdt);
		return "success";
	}
	public String deleteDevType(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String devTypeId=request.getParameter("devTypeId");
		
		if(devTypeId==null){
			returnMsg="设备类型删除失败，请重试！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch devTypeId failed is null from web browser");
			return "failed";
		}
		if(devTypeId.trim().equals("")){
			returnMsg="设备类型删除失败，请重试！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch devTypeId failed is '' from web browser");
			return "failed";
		}
		String devTypeIds[]=devTypeId.split(",");
		List resList=null;
		for(int i=0;i<devTypeIds.length;i++){
			
			resList=sros.queryAll("select s.id from SysResObj s, SysDevType c  where s.typeCode=c.typeCode and c.id="+Long.parseLong(devTypeIds[i].split("_")[0]));
			
			
			if(resList!=null&&resList.size()>0){
				returnMsg="删除设备型号前，请先删除属于此型号的防火墙资源！";
				logger.info("delete devType failed ,one of devType has fwRes devTypeId is "+devTypeIds[i]);
				return "failed";
			}
		}
		for(int i=0;i<devTypeIds.length;i++){
			SysDevType sdt=new SysDevType();
			sdt.setId(Long.parseLong(devTypeIds[i].split("_")[0]));
			sdts.delete(sdt);
		}
		return "success";
	}
	public String viewDevTypeByCompanyCode(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String companyCode=request.getParameter("companyCode");
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
		List allDevTypes=sdts.queryAll("from SysDevType s where s.companyCode='"+companyCode+"'");
		List sysDevTypes=sdts.queryByPage("from SysDevType s where s.companyCode='"+companyCode+"'",pageNum,maxPage);
		StringBuilder sb = new StringBuilder();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/json");
		PrintWriter pw = null;
		try {
			pw = resp.getWriter();
			sb.append("{\"total\":"+allDevTypes.size()+",\"rows\":[");
			for (int i = 0; i < sysDevTypes.size(); i++) {
				SysDevType sdc=(SysDevType)sysDevTypes.get(i);
				sb.append("{\"typeid_ccode\":\""+sdc.getId()+"_"+sdc.getCompanyCode()+"\",");
				
				sb.append("\"typename\":\""+sdc.getTypeCode()+"\",");
				
				sb.append("\"typecode\":\""+sdc.getTypeCode()+"\",");
				
				if(i!=sysDevTypes.size()-1){
					if(sdc.getTypeDesc()==null){
						sb.append("\"typedesc\":\" \"},");
					}else{
						sb.append("\"typedesc\":\""+sdc.getTypeDesc()+"\"},");
					}
				}else{
					if(sdc.getTypeDesc()==null){
						sb.append("\"typedesc\":\" \"}");
					}else{
						sb.append("\"typedesc\":\""+sdc.getTypeDesc()+"\"}");
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
	public String toModifyType(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String devTypeId=request.getParameter("devTypeId");
		if(devTypeId==null){
			returnMsg="跳转失败，请重试！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch devTypeId failed is null from web browser");
			return "failed";
		}
		if(devTypeId.trim().equals("")){
			returnMsg="跳转失败，请重试，请重试！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch devTypeId failed is '' from web browser");
			return "failed";
		}
		List devTypes=sdts.queryAll("from SysDevType s where s.id="+devTypeId.split("_")[0]);
		if(devTypes==null){
			returnMsg="查询设备类型失败！";
			logger.info("fetch devType failed ,by companyid is "+devTypeId.split("_")[0]);
			backUrl="viewAllDevCompany.jsp";
			return "failed";
		}
		if(devTypes.size()==0){
			returnMsg="查询设备类型失败！";
			logger.info("fetch devType failed ,by companyid is "+devTypeId.split("_")[0]);
			backUrl="viewAllDevCompany.jsp";
			return "failed";
		}
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("devType", devTypes.get(0));
		return "success";
	}
	public String modifyType(){
		
		if(company.getCompanyCode()==null){
			returnMsg="厂商编码获取失败，修改失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch companyCode failed is null from web browser");
			return "failed";
		}
		if(company.getCompanyCode().trim().equals("")){
			returnMsg="厂商编码获取失败，修改失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch companyCode failed is '' from web browser");
			return "failed";
		}
		
		if(company.getTypeId()==null){
			returnMsg="设备类型ID获取失败，修改失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch typeId failed is null from web browser");
			return "failed";
		}
		if(company.getTypeId().trim().equals("")){
			returnMsg="设备类型ID获取失败，修改失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch typeId failed is '' from web browser");
			return "failed";
		}
		
		if(company.getTypeName()==null){
			returnMsg="设备类型名称不能为空，保存失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch typeName failed is null from web browser");
			return "failed";
		}
		if(company.getTypeName().trim().equals("")){
			returnMsg="设备类型名称不能为空，保存失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch typeName failed is '' from web browser");
			return "failed";
		}
		
		if(company.getTypeCode()==null){
			returnMsg="设备编码不能为空，保存失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch typeCode failed is null from web browser");
			return "failed";
		}
		if(company.getTypeCode().trim().equals("")){
			returnMsg="设备编码不能为空，保存失败！";
			backUrl="viewAllDevCompany.jsp";
			logger.info("fetch typeCode failed is '' from web browser");
			return "failed";
		}
		
		SysDevType sdt=new SysDevType();
		sdt.setCompanyCode(company.getCompanyCode());
		sdt.setId(Long.parseLong(company.getTypeId()));
		sdt.setTypeCode(company.getTypeCode());
		sdt.setTypeName(company.getTypeName());
		sdt.setTypeDesc(company.getTypeDesc());
		sdts.update(sdt);
		return "success";	
	}
	
}