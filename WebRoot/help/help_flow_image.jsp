<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.secpro.platform.monitoring.manage.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" media="all" type="text/css" href="../style/blue/css/main.css" />
<link rel="stylesheet" media="all" type="text/css" href="../style/blue/css/basic.css" />
<title><%=ApplicationConfiguration.APPLCATION_NAME%></title>
<script src="../js/ueframe/main.js"></script>
<script language="javascript">
	window.onresize = function() {
		setCenter(4, 80);
	}
	window.onload = function() {
		setCenter(4, 80);
	}
</script>
</head>
<body>
	<div class="content">
		<div class="content_title_bg">
			<div class="content_title">帮助>诉求流程图</div>
		</div>
		<div class="about_title">诉求流程图</div>
		<div id="center" style="overflow: auto; width: 100%; padding: 0px;">
			<img title="诉求流程图" src="flow.png"/>
		</div>
	</div>
</body>
</html>

