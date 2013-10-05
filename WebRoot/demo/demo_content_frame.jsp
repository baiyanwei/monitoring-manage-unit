<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.byw.platform.flow.web.util.ApplicationConfiguration"%>
<%
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<HTML>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title><%=ApplicationConfiguration.APPLCATION_NAME%></title>
	</head>

	<FRAMESET COLS="210,*" border="4" framespacing="4">
		<FRAME src="left.jsp" name="leftFrame" scrolling="no" frameborder="0">
		<FRAME src="appliction_description.jsp" scrolling="no" name="contentFrame" frameborder="0" id="contentFrame">
	</FRAMESET>
	<noframes>
		<body>
			您的浏览器不支持HTML框架，请更换浏览器
		</body>
	</noframes>
</HTML>