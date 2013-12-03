<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.byw.platform.flow.web.util.service.ServiceHelper"%>
<%@page import="com.byw.platform.flow.web.services.AuthenticationService"%>
<%@page import="com.byw.platform.flow.web.util.ApplicationConfiguration"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	System.out.println(request.getAttribute("WEB_MSG"));
	String pageAction="goBack";
	if(request.getAttribute("previousURL")==null){
		pageAction="close";
	}
%><script language="javascript">
try{
		var pageAction="<%=pageAction%>";
		alert("<%=request.getAttribute("exception")%>");
		if(pageAction=="close"){
			window.close();
		}else{	
			window.location.href="<%=request.getAttribute("previousURL")%>";
		}
}catch(e){
	alert(e);
}
</script>