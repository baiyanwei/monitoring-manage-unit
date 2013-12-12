<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" media="all" type="text/css" href="../style/blue/css/main.css" />
<link rel="stylesheet" media="all" type="text/css" href="../style/blue/css/basic.css" />
<link rel="stylesheet" type="text/css" href="../style/app/css/app_main.css" />
<title></title>
<script src="../js/ueframe/main.js"></script>
<script language="javascript">
	window.onresize = function() {
		setCenter(4, 112);
	}
	window.onload = function() {
		setCenter(4, 112);
	}
</script>
</head>
<body>
	<div class="content">
		<div class="content_title_bg">
			<div class="content_title">demo</div>
		</div>
		<div class="about_title">demo</div>
		
		<div id="center" style="overflow: auto; width: 100%; padding: 0px;">
			<iframe id="SYS02" name="sys_02" src="../help/help_content_frame.jsp"  width="100%" height="100%" scrolling="auto" frameborder="0" ></iframe>
		</div>
	</div>
</body>
</html>