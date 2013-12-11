<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.secpro.platform.monitoring.manage.util.ApplicationConfiguration"%>
<%
	String loginError = "";
	if (request
			.getAttribute(ApplicationConfiguration.WEB_MESSAGE_ERROR) != null) {
		loginError = String
				.valueOf(request
						.getAttribute(ApplicationConfiguration.WEB_MESSAGE_ERROR));
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="./style/login/css/2github.css" media="screen" rel="stylesheet" type="text/css">
<link href="./style/login/css/87github.css" media="screen" rel="stylesheet" type="text/css">
<title><%=ApplicationConfiguration.APPLCATION_NAME%></title>
<script src="./js/form_check.js"></script>
<script language="javascript">
	function locationToTop(){
		if(window.top!=null){
			if(window.top.location.href!=window.location.href){
				window.top.location.href=window.location.href;
			}
		}
	}
	function myFormSubmit(form) {
		if (checkOwnRule(form)) {
			form.submit();
		}
	}
</script>
</head>
<body onload="locationToTop()">
	<div id="wrapper">
		<div id="header" class="true clearfix">
			<div class="container clearfix">
				<span class="site-logo "><font size="6" color="navy">客户诉求处理系统</font> </span>
				<ul class="top-nav logged_out">
				</ul>
			</div>
		</div>
		<div class="site clearfix">
			<div id="site-container" class="container" data-pjax-container="">
				<div id="login" class="login_form">
					<form name="resourceForm" id="resourceForm" action="loginAuthenticationAction.action" method="post">
						<div style="margin:0;padding:0;display:inline"></div>
						<font color="red"><%=loginError%></font>
						<h1>请输入认证信息</h1>
						<div class="formbody">
							<label for="login_field"> 登录名称<br> <input autocapitalize="off" autofocus="autofocus" class="text" id="username" name="username" style="width: 21em;" tabindex="1" type="text"> </label> <label for="password"> 密码 <br> <input
								autocomplete="disabled" class="text" id="passwd" name="passwd" style="width: 21em;" tabindex="2" type="password"> </label> <label class="submit_btn"><input name="commit" tabindex="3" type="button" value="登录" onclick="myFormSubmit(resourceForm)"> </label>
						</div>
					</form>
				</div>
			</div>
			<div class="context-overlay"></div>
		</div>
		<div id="footer-push"></div>
		<!-- hack for sticky footer -->
	</div>
	<div id="footer">

		<div class="upper_footer">
			<div class="container clearfix">系统与功能简单介绍</div>
			<!-- /.site -->
		</div>
		<div class="lower_footer">
			<div class="container clearfix">
				<div id="legal">
					<ul>
						<li>系统支持</li>
						<li>baiyanwei@gmail.com</li>
						<li>QQ:4757090</li>
					</ul>
					<ul>
						<li>2012/9/18</li>
						<li>Simple Flow System</li>
						<li>Version 0.1 测试版</li>
					</ul>
				</div>
				<!-- /#legal or /#legal_ie-->
			</div>
			<!-- /.site -->
		</div>
		<!-- /.lower_footer -->
	</div>
	<!-- /#footer -->
</body>
</HTML>