<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.byw.platform.flow.web.util.service.ServiceHelper"%>
<%@page import="com.byw.platform.flow.web.services.AuthenticationService"%>
<%@page import="com.byw.platform.flow.web.util.ApplicationConfiguration"%>
<%
	String pageAction="goBack";
	if(request.getAttribute("previousURL")==null){
		pageAction="close";
	}
	request.getAttribute("WEB_MSG");
%><script language="javascript">
try{	
		var pageAction="<%=pageAction%>";
		alert("<%=request.getAttribute("WEB_MSG")%>");
		//
		<%if (request.getAttribute("refreshFrame") != null) {
				String refreshFrame = String.valueOf(request.getAttribute("refreshFrame"));
				String[] frames = refreshFrame.split(",");
				for (int i = 0; i < frames.length; i++) {%>
		//refresh frame
		if(window.parent.<%=frames[i]%>!=null){
			window.parent.<%=frames[i]%>.location.reload();
		}
		<%}
			}%>
		//
		if(pageAction=="close"){
			if(opener){
				opener.location.reload();
			}
			window.close();
		}else{	
			window.location.href="<%=request.getAttribute("previousURL")%>";
		}
	} catch (e) {
		alert(e);
	}
</script>