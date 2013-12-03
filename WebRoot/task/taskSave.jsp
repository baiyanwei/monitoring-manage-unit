<%@page import="com.secpro.platform.monitoring.manage.dao.TaskScheduleActionDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.secpro.platform.monitoring.manage.entity.SysOperation"%>
<%
	String pageTitle = "任务新建";
	String operationType = "new";
	String actionButtonName="新建";
	if (request.getParameter("operationType") != null) {
		operationType = request.getParameter("operationType");
	}
	if ("update".equalsIgnoreCase(operationType)) {
		pageTitle = "任务更新";
		actionButtonName="更新";
	}
	String pageDescrption = pageTitle;
	String formSubmitTarget = "/TaskScheduleAction.createMSUTask";

	//
	String region = request.getParameter("region");
	String resId = request.getParameter("resId");
	String targetIp="";
	String resObjName = request.getParameter("resObjName");
	String regionName = "";
	
	List<Object> sysOperationList=TaskScheduleActionDao.findAll(SysOperation.class);
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
	function authRowChange(authType) {
		if ("none" == authType) {
			document.getElementById("operationSSH/TELNETAuthRow").style.display = "none";
			document.getElementById("operationTELNETAuthRow").style.display = "none";
			document.getElementById("operationSNMPV1/2AuthRow").style.display = "none";
			document.getElementById("operationSNMPV3AuthRow").style.display = "none";
			return;
		} else {
			authRowChange("none");
		}
		if ("telnet" == authType || "ssh" == authType) {
			document.getElementById("operationSSH/TELNETAuthRow").style.display = "block";
		}
		if ("telnet" == authType) {
			document.getElementById("operationTELNETAuthRow").style.display = "block";
			document.getElementById("operationTELNETAuthRow").width = "800OX";
		}
		if ("snmpv1" == authType || "snmpv2c" == authType) {
			document.getElementById("operationSNMPV1/2AuthRow").style.display = "block";
		}
		if ("snmpv3" == authType) {
			document.getElementById("operationSNMPV3AuthRow").style.display = "block";
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
								<input type="hidden" name="resId" id="resId" value="<%=resId%>" /> <input type="hidden" name="region" id="region" value="<%=region%>" /> <input type="hidden"
									name="targetIp" id="targetIp" value="<%=targetIp%>" /> <input type="hidden" name="targetIp" id="targetIp" value="<%=targetIp%>" />
								<div id="div1">
									<p></p>
									<table cellpadding="0" cellspacing="0" border="0" width="600px" align="center">
										<tr>
											<td>
												<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
													<tr>
														<td valign="top" width="35%"><div align="right">采集目标：</div>
														</td>
														<td valign="top" width="65%"><div align="left">
																<span><%=resObjName%></span>
															</div>
														</td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">目标位置：</div>
														</td>
														<td valign="top" width="65%"><div align="left">
																<span><%=regionName%></span>
															</div>
														</td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">目标IP：</div>
														</td>
														<td valign="top" width="65%"><div align="left">
																<span><%=targetIp%></span>
															</div>
														</td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">
																<span class="input_redstar ">*</span>采集方式：
															</div>
														</td>
														<td valign="top" width="65%"><div align="left">
																<select id="operation" name="operation" hint="采集方式" allownull="false" onchange="authRowChange(this.options[this.options.selectedIndex].value)">
																	<option value="none" selected></option>
																	<%
																		if (sysOperationList != null) {
																			for (int i = 0; i < sysOperationList.size(); i++) {
																				SysOperation sysOperationBean = (SysOperation) (sysOperationList.get(i));
																				out.println("<option value=\"" + sysOperationBean.getOperationName() + "\">" + sysOperationBean.getOperationName() + "</option>");
																			}
																		}
																	%>
																</select><span class="input_redstar ">*</span>
															</div>
														</td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">采集端口：</div>
														</td>
														<td valign="top" width="65%"><div align="left">
																<input name="targetPort" id="targetPort" type="text" size="10" maxlength="10" hint="采集端口" allownull="false" />
															</div>
														</td>
													</tr>
												</table></td>
										</tr>

										<tr>
											<td>
												<div id="operationSSH/TELNETAuthRow" style="display:none">
													<%
														//opeation:ssh/telnet
														//用户名	username	varchar2(20)	ssh/telnet用户名
														//密码	password	varchar2(20)	ssh/telnet密码
													%>
													<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
														<tr>
															<td valign="top" width="35%"><div align="right">
																	<span class="input_redstar ">*</span>用户名：
																</div>
															</td>
															<td valign="top" width="65%"><div align="left">
																	<input name="username" id="username" type="text" size="20" maxlength="20" hint="用户名" allownull="false" />
																</div>
															</td>
														</tr>
														<tr>
															<td valign="top" width="35%"><div align="right">
																	<span class="input_redstar ">*</span>密码：
																</div>
															</td>
															<td valign="top" width="65%"><div align="left">
																	<input name="password" id="password" type="password" size="20" maxlength="20" hint="密码" allownull="false" />
																</div>
															</td>
														</tr>
														<tr>
															<td valign="top" width="35%"><div align="right">
																	<span class="input_redstar ">*</span>确认密码：
																</div>
															</td>
															<td valign="top" width="65%"><div align="left">
																	<input name="password_confirm" id="password_confirm" type="password" size="20" maxlength="20" hint="确认密码" allownull="false" />
																</div>
															</td>
														</tr>
													</table>
												</div>
												<div id="operationTELNETAuthRow" style="display:none">
													<%
														//opeation:telent
														//用户名提示字符串	user_prompt	varchar2(20)	telnet连接所需参数
														//密码提示字符串	pass_prompt	varchar2(20)	telnet连接所需参数
														//命令提示符	prompt	varchar2(20)	telnet连接所需参数
														//执行命令提示符	exec_prompt	varchar2(20)	telnet连接所需参数
														//翻页命令提示符	next_prompt	varchar2(20)	telnet连接所需参数
													%>
													<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
														<tr>
															<td valign="top" width="35%"><div align="right">
																	<span class="input_redstar ">*</span>用户名提示字符串：
																</div>
															</td>
															<td valign="top" width="65%"><div align="left">
																	<input name="userPrompt" id="userPrompt" type="text" size="20" maxlength="20" hint="用户名提示字符串" allownull="false" />
																</div>
															</td>
														</tr>
														<tr>
															<td valign="top" width="35%"><div align="right">
																	<span class="input_redstar ">*</span>密码提示字符串：
																</div>
															</td>
															<td valign="top" width="65%"><div align="left">
																	<input name="passPrompt" id="passPrompt" type="text" size="20" maxlength="20" hint="密码提示字符串" allownull="false" />
																</div>
															</td>
														</tr>
														<tr>
															<td valign="top" width="35%"><div align="right">
																	<span class="input_redstar ">*</span>命令提示符：
																</div>
															</td>
															<td valign="top" width="65%"><div align="left">
																	<input name="prompt" id="prompt" type="text" size="20" maxlength="20" hint="命令提示符" allownull="false" />
																</div>
															</td>
														</tr>
														<tr>
															<td valign="top" width="35%"><div align="right">
																	<span class="input_redstar ">*</span>执行命令提示符：
																</div>
															</td>
															<td valign="top" width="65%"><div align="left">
																	<input name="execPrompt" id="execPrompt" type="text" size="20" maxlength="20" hint="执行命令提示符" allownull="false" />
																</div>
															</td>
														</tr>
														<tr>
															<td valign="top" width="35%"><div align="right">
																	<span class="input_redstar ">*</span>翻页命令提示符：
																</div>
															</td>
															<td valign="top" width="65%"><div align="left">
																	<input name="nextPrompt" id="nextPrompt" type="text" size="20" maxlength="20" hint="翻页命令提示符" allownull="false" />
																</div>
															</td>
														</tr>
													</table>
												</div>
												<div id="operationSNMPV1/2AuthRow" style="display:none">
													<%
														//operation:snmpv1/snmpv2c
														//团体名	community	varchar2(20)	snmpv1、v2c所需参数
													%>
													<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
														<tr>
															<td valign="top" width="35%"><div align="right">团体名：</div>
															</td>
															<td valign="top" width="65%"><div align="left">
																	<input name="community" id="community" type="text" size="20" maxlength="20" hint="团体名" allownull="false" />
																</div>
															</td>
														</tr>
													</table>
												</div>
												<div id="operationSNMPV3AuthRow" style="display:none">
													<%
														//operation:snmpv3
														//V3版本用户名	snmpv3_user	varchar2(20)	snmpv3所需参数
														//认证算法	snmpv3_auth	varchar2(20)	snmpv3所需参数
														//认证密钥	snmpv3_authpass	varchar2(20)	snmpv3所需参数
														//加密算法	snmpv3_priv	varchar2(20)	snmpv3所需参数
														//加密密钥	snmpv3_privpass	varchar2(20)	snmpv3所需参数
													%>
													<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
														<tr>
															<td valign="top" width="35%"><div align="right">V3版本用户名：</div>
															</td>
															<td valign="top" width="65%"><div align="left">
																	<input name="snmpv3User" id="snmpv3User" type="text" size="20" maxlength="20" hint="V3版本用户名" allownull="false" />
																</div>
															</td>
														</tr>
														<tr>
															<td valign="top" width="35%"><div align="right">认证算法：</div>
															</td>
															<td valign="top" width="65%"><div align="left">
																	<input name="snmpv3Auth" id="snmpv3Auth" type="text" size="20" maxlength="20" hint="认证算法" allownull="false" />
																</div>
															</td>
														</tr>
														<tr>
															<td valign="top" width="35%"><div align="right">认证密钥：</div>
															</td>
															<td valign="top" width="65%"><div align="left">
																	<input name="snmpv3Authpass" id="snmpv3Authpass" type="text" size="20" maxlength="20" hint="认证密钥" allownull="false" />
																</div>
															</td>
														</tr>
														<tr>
															<td valign="top" width="35%"><div align="right">加密算法：</div>
															</td>
															<td valign="top" width="65%"><div align="left">
																	<input name="snmpv3Priv" id="snmpv3Priv" type="text" size="20" maxlength="20" hint="加密算法" allownull="false" />
																</div>
															</td>
														</tr>
														<tr>
															<td valign="top" width="35%"><div align="right">加密密钥：</div>
															</td>
															<td valign="top" width="65%"><div align="left">
																	<input name="snmpv3Privpass" id="snmpv3Privpass" type="text" size="20" maxlength="20" hint="加密密钥" allownull="false" />
																</div>
															</td>
														</tr>
													</table>
											</div>
											</td>
										</tr>
										<tr>
											<td>
												<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
													<tr>
														<td valign="top" width="35%"><div align="right">调度周期：</div>
														</td>
														<td valign="top" width="65%"><div align="left">
																<input name="schedule" id="schedule" type="text" size="30" maxlength="50" hint="调度周期" allownull="false" />
															</div>
														</td>
													</tr>
													<tr>
														<td valign="top" width="35%"><div align="right">
																<span class="input_redstar ">*</span>采集命令：
															</div>
														</td>
														<td valign="top" width="65%"><div align="left">
																<textarea name="content" id="content" cols="45" rows="5" maxlength="500" hint="采集命令" allownull="false"></textarea>
																<span class="input_redstar ">*</span>
															</div>
														</td>
													</tr>
												</table></td>
										</tr>
										<tr>
											<td>
												<div align="center" style="margin-top: 15px; margin-bottom: 15px;">
													<input type="button" class="button" onmouseover="this.className='buttonhover'" onmouseout="this.className='button'" name="Submit" value="<%=actionButtonName%>"
														onclick="myFormSubmit(taskForm)" /> <input type="button" class="button" onmouseover="this.className='buttonhover'" onmouseout="this.className='button'"
														onclick="window.history.back();" name="back" value="返回" />
												</div>
											</td>
										</tr>
									</table>
								</div>
							</form>
						</fieldset>
					</td>
				</tr>
			</table>

		</div>
	</div>
</body>
</html>