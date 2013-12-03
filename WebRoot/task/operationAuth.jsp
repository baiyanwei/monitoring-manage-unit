<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String opeationType = request.getParameter("operationType");
	String resID = request.getParameter("resID");
	if (opeationType == null || opeationType.length() == 0) {
		out.println("invalid parameter operationType");
		return;
	}
	if (resID == null || resID.length() == 0) {
		out.println("invalid parameter resID");
		return;
	}
	String pageTitle = "认证信息";

	if ("ssh".equalsIgnoreCase(opeationType)) {
		pageTitle = "ssh认证信息";
	} else if ("telnet".equalsIgnoreCase(opeationType)) {
		pageTitle = "telnet认证信息";
	} else if ("snmpv1".equalsIgnoreCase(opeationType)) {
		pageTitle = "snmpv1认证信息";
	} else if ("snmpv2c".equalsIgnoreCase(opeationType)) {
		pageTitle = "snmpv2c认证信息";
	} else if ("snmpv3".equalsIgnoreCase(opeationType)) {
		pageTitle = "snmpv3认证信息";
	} else {
		out.println("invalid parameter operationType");
		return;
	}
	String pageDescrption = pageTitle;
	String formSubmitTarget = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" media="all" type="text/css" href="../style/blue/css/main.css" />
<link rel="stylesheet" media="all" type="text/css" href="../style/blue/css/basic.css" />
<title><%=pageTitle%></title>
<link rel="stylesheet" type="text/css" href="../style/app/css/app_main.css" />
<link rel="stylesheet" type="text/css" href="../style/blue/css/main.css" />
<script src="../js/ueframe/main.js"></script>
<script src="../js/form_check.js"></script>
<script src="../js/form_util.js"></script>
<script language="javascript">
	window.onresize = function() {
		setCenter(4, 80);
	}
	window.onload = function() {
		setCenter(4, 80);
	}
</script>
<script language="javascript">
	function myFormSubmit(form) {
		if (checkOwnRule(form)) {
			form.submit();
		}
	}
</script>
</head>
<body>
	<div class="content">
		<div class="content_title_bg">
			<div class="content_title">
				<%=pageDescrption%></div>
		</div>
		<div class="about_title"></div>
		<div id="center" style="overflow: auto; width: 100%; padding: 0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
				<tr>
					<td valign="top">
						<fieldset style="width: 96%; border: 1px #cccccc solid; margin-left: 5px; margin-top: 5px; text-align: center;">
							<legend><%=pageTitle%></legend>
							<form name="taskForm" id="taskForm" action="<%=formSubmitTarget%>" method="post">
								<input type="hidden" name="res_id" id="res_id" value="<%=resID%>" />
								<div id="div1">
									<p></p>
									<table cellpadding="0" cellspacing="0" border="0" width="600px" align="center">

										<%
											if ("ssh".equalsIgnoreCase(opeationType)) {
												//SSH username,password
										%>
										<tr>
											<td>
												<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
													<tr>
														<td valign="top" width="35%"><div align="right">
																<span class="input_redstar ">*</span>用户名：
															</div></td>
														<td valign="top" width="65%"><div align="left">
																<input name="username" id="username" type="text" size="20" maxlength="20" hint="用户名" allownull="false" />
															</div></td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">
																<span class="input_redstar ">*</span>密码：
															</div></td>
														<td valign="top" width="65%"><div align="left">
																<input name="password" id="password" type="password" size="20" maxlength="20" hint="密码" allownull="false" />
															</div></td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">
																<span class="input_redstar ">*</span>确认密码：
															</div></td>
														<td valign="top" width="65%"><div align="left">
																<input name="password_confirm" id="password_confirm" type="password" size="20" maxlength="20" hint="确认密码" allownull="false" />
															</div></td>
													</tr>
												</table>
											</td>
										</tr>
										<%
											} else if ("telnet".equalsIgnoreCase(opeationType)) {
												//用户名提示字符串	user_prompt	varchar2(20)	telnet连接所需参数
												//密码提示字符串	pass_prompt	varchar2(20)	telnet连接所需参数
												//命令提示符	prompt	varchar2(20)	telnet连接所需参数
												//执行命令提示符	exec_prompt	varchar2(20)	telnet连接所需参数
												//翻页命令提示符	next_prompt	varchar2(20)	telnet连接所需参数
										%>
										<tr>
											<td>
												<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
													<tr>
														<td valign="top" width="35%"><div align="right">
																<span class="input_redstar ">*</span>用户名：
															</div></td>
														<td valign="top" width="65%"><div align="left">
																<input name="username" id="username" type="text" size="20" maxlength="20" hint="用户名" allownull="false" />
															</div></td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">
																<span class="input_redstar ">*</span>密码：
															</div></td>
														<td valign="top" width="65%"><div align="left">
																<input name="password" id="password" type="password" size="20" maxlength="20" hint="密码" allownull="false" />
															</div></td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">
																<span class="input_redstar ">*</span>确认密码：
															</div></td>
														<td valign="top" width="65%"><div align="left">
																<input name="password_confirm" id="password_confirm" type="password" size="20" maxlength="20" hint="确认密码" allownull="false" />
															</div></td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">
																<span class="input_redstar ">*</span>用户名提示字符串：
															</div></td>
														<td valign="top" width="65%"><div align="left">
																<input name="user_prompt" id="user_prompt" type="text" size="20" maxlength="20" hint="用户名提示字符串" allownull="false" />
															</div></td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">
																<span class="input_redstar ">*</span>密码提示字符串：
															</div></td>
														<td valign="top" width="65%"><div align="left">
																<input name="pass_prompt" id="pass_prompt" type="text" size="20" maxlength="20" hint="密码提示字符串" allownull="false" />
															</div></td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">
																<span class="input_redstar ">*</span>命令提示符：
															</div></td>
														<td valign="top" width="65%"><div align="left">
																<input name="prompt" id="prompt" type="text" size="20" maxlength="20" hint="命令提示符" allownull="false" />
															</div></td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">
																<span class="input_redstar ">*</span>执行命令提示符：
															</div></td>
														<td valign="top" width="65%"><div align="left">
																<input name="exec_prompt" id="exec_prompt" type="text" size="20" maxlength="20" hint="执行命令提示符" allownull="false" />
															</div></td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">
																<span class="input_redstar ">*</span>翻页命令提示符：
															</div></td>
														<td valign="top" width="65%"><div align="left">
																<input name="next_prompt" id="next_prompt" type="text" size="20" maxlength="20" hint="翻页命令提示符" allownull="false" />
															</div></td>
													</tr>
												</table>
											</td>
										</tr>
										<%
											} else if ("snmpv1".equalsIgnoreCase(opeationType) || "snmpv2c".equalsIgnoreCase(opeationType)) {
												//团体名	community	varchar2(20)	snmpv1、v2c所需参数
										%>
										<tr>
											<td>
												<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
													<tr>
														<td valign="top" width="35%"><div align="right">团体名：</div></td>
														<td valign="top" width="65%"><div align="left">
																<input name="community" id="community" type="text" size="20" maxlength="20" hint="团体名" allownull="false" />
															</div></td>
													</tr>
												</table>
											</td>
										</tr>
										<%
											} else if ("snmpv3".equalsIgnoreCase(opeationType)) {
												//V3版本用户名	snmpv3_user	varchar2(20)	snmpv3所需参数
												//认证算法	snmpv3_auth	varchar2(20)	snmpv3所需参数
												//认证密钥	snmpv3_authpass	varchar2(20)	snmpv3所需参数
												//加密算法	snmpv3_priv	varchar2(20)	snmpv3所需参数
												//加密密钥	snmpv3_privpass	varchar2(20)	snmpv3所需参数
										%>
										<tr>
											<td>
												<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
													<tr>
														<td valign="top" width="35%"><div align="right">V3版本用户名：</div></td>
														<td valign="top" width="65%"><div align="left">
																<input name="snmpv3_user" id="snmpv3_user" type="text" size="20" maxlength="20" hint="V3版本用户名" allownull="false" />
															</div></td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">认证算法：</div></td>
														<td valign="top" width="65%"><div align="left">
																<input name="snmpv3_auth" id="snmpv3_auth" type="text" size="20" maxlength="20" hint="认证算法" allownull="false" />
															</div></td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">认证密钥：</div></td>
														<td valign="top" width="65%"><div align="left">
																<input name="snmpv3_authpass" id="snmpv3_authpass" type="text" size="20" maxlength="20" hint="认证密钥" allownull="false" />
															</div></td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">加密算法：</div></td>
														<td valign="top" width="65%"><div align="left">
																<input name="snmpv3_priv" id="snmpv3_priv" type="text" size="20" maxlength="20" hint="加密算法" allownull="false" />
															</div></td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">加密密钥：</div></td>
														<td valign="top" width="65%"><div align="left">
																<input name="snmpv3_privpass" id="snmpv3_privpass" type="text" size="20" maxlength="20" hint="加密密钥" allownull="false" />
															</div></td>
													</tr>
												</table>
											</td>
										</tr>
										<%
											}
										%>
										<tr>
											<td>
												<div align="center" style="margin-top: 15px; margin-bottom: 15px;">
													<input type="button" class="button" onmouseover="this.className='buttonhover'" onmouseout="this.className='button'" name="Submit" value="<%=title%>"
														onclick="myFormSubmit(taskForm)" /> <input type="reset" class="button" onmouseover="this.className='buttonhover'" onmouseout="this.className='button'"
														name="reset" value="重置" /> <input type="button" class="button" onmouseover="this.className='buttonhover'" onmouseout="this.className='button'"
														onclick="window.history.back();" name="back" value="返回" />
												</div></td>
										</tr>
									</table>
								</div>
							</form>
						</fieldset></td>
				</tr>
			</table>

		</div>
	</div>
</body>
</html>