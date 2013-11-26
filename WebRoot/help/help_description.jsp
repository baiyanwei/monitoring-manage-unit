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
		<div>
			<table class="page_bar" align="center">
				<tr>
					<td class="page_bar_bg">
						<div align="left" style="padding-left: 10px;">page tool bar</div>
					</td>
				</tr>
			</table>
		</div>
		<div class="content_title_bg">
			<div class="content_title">河北>防火墙1</div>
		</div>
		<div id="center" style="overflow: auto; width: 100%; padding: 0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
				<tr>
					<td valign="top">
						<fieldset style="width: 96%; border: 1px #cccccc solid; margin-left: 5px; margin-top: 5px; text-align: center;">
							<legend> </legend>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td align="left">
										<div style="height: 100%; overflow: auto; width: 100%; padding: 0px;">
											<li style="list-style: none">在这写点有关报表的东西</li>
										</div></td>
								</tr>
							</table>
						</fieldset></td>
				</tr>
			</table>

		</div>
	</div>
</body>
</html>

