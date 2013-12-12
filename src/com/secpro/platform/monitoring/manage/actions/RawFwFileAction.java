package com.secpro.platform.monitoring.manage.actions;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.RawFwFile;
import com.secpro.platform.monitoring.manage.services.RawFwFileService;

@Controller("RawFwFileAction")
public class RawFwFileAction {
	private RawFwFileService fileService;

	public RawFwFileService getFileService() {
		return fileService;
	}
	@Resource(name = "RawFwFileServiceImpl")
	public void setFileService(RawFwFileService fileService) {
		this.fileService = fileService;
	}
	public void queryFwFileByRes(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		SimpleDateFormat sdf2 =   new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" );
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
				from=sdf.format(sdf2.parse(from));

			}else{
				 String todays=sdf1.format(new Date());
			     from=sdf.format(sdf1.parse(todays));
			}
			
			if(to!=null&&!to.trim().equals("")){
				to=sdf.format(sdf2.parse(to));
			}else{
			
				to=sdf.format(new Date());
			}
			
			List fileList=fileService.queryAll("from RawFwFile r where r.cdate>='"+from+"' and r.cdate <='"+to+"' and r.resId="+resId);
			List filePage=fileService.queryByPage("from RawFwFile r where r.cdate>='"+from+"' and r.cdate <='"+to+"' and r.resId="+resId,pageNum,maxPage);
			if (fileList == null) {
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			sb.append("{\"total\":" + fileList.size() + ",\"rows\":[");
			for (int i = 0; i < filePage.size(); i++) {
				RawFwFile file=(RawFwFile)filePage.get(i);
				sb.append("{\"fileId\":" + file.getId() + ",");
				sb.append("\"fileName\":\"" + file.getFileName() + "\",");
				
				sb.append("\"cdate\":\"" + sdf1.format(sdf.parse(file.getCdate())) + "\",");
				sb.append("\"fileSize\":\"" + file.getFileSize() + "\",");
				
				if(i==(filePage.size()-1)){
					sb.append("\"filePath\":\"" + file.getFilePath()+ "\"}");
				}else{
					sb.append("\"filePath\":\"" +file.getFilePath()+ "\"},");
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
