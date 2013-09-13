<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.byw.platform.flow.web.util.service.ServiceHelper"%>
<%@page import="com.byw.platform.flow.web.services.TablesService"%>
<%@page import="com.byw.platform.flow.web.services.DataViewService"%>
<%@page import="resource.db.dao.DataBaseHelper"%>
<%@page import="resource.db.bean.ResRoleBean"%>
<%
	String type = "ResUserBean";
	String departmentId = request.getParameter("departmentID");
	DataViewService dataViewService = ServiceHelper.findService(DataViewService.class);
	TablesService tablesService=ServiceHelper.findService(TablesService.class);

	Class<?> clazz=dataViewService.getBeanClass(type);
	List<Object> pageDataList=dataViewService.getUserListInSubPage(clazz,departmentId);
	List<String[]> tableRawList=tablesService.getAuthorizeTableRawsList(pageDataList);
	//	
	//
	String ico_path="../style/app/images/";
	String basePath="";
	String authorizeAction="authorizeAuthenticationAction.action";
	String cancelAuthorizeAction="cancelAuthorizeAuthenticationAction.action";
	//out.println("pageSize:"+pageSize+"  pageNo:"+pageNo +" DataSize:"+pageDataList.size());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统维护视图</title>
<link rel="stylesheet" type="text/css" href="../style/app/css/app_main.css" />
<link rel="stylesheet" type="text/css" href="../style/blue/css/main.css" />
<link rel="stylesheet" type="text/css" href="../style/blue/css/dropdown_ie.css" />
<link rel="stylesheet" type="text/css" href="../style/blue/css/dropdown.css" />
<script language="JavaScript" type="text/javascript" src="../js/ueframe/changeframe.js"></script>
<script language="JavaScript" type="text/javascript" src="../js/ueframe/left.js"></script>
<script language="JavaScript" type="text/javascript" src="../js/ueframe/main.js"></script>
<script language="JavaScript" type="text/javascript" src="../js/common_table.js"></script>
<script language="javascript">
	//center DIV 设置大小
	window.onresize = function() {
		setQutSelectDiv();
		changeRow_color('edittab');
	}
	window.onload = function() {
		setCenter(6, 61);
		changeRow_color('edittab');
	}
	function ak_lookupData() {
		if(event.keyCode == 13) {
			if(document.getElementById('qutSelDiv').style.display == 'block') {
				document.getElementById('uqt_lookup_but').click();
			}
		}
	}

	function setQutSelectDiv() {
		if(document.getElementById('qutSelDiv') == null)
			return;
		var displayStr = document.getElementById('qutSelDiv').style.display;
		if(displayStr == "none") {
			setCenter(6, 61);
		} else {
			var lookupVal = 0
			if(document.getElementById("qutSelDiv") != null) {
				lookupVal = document.getElementById("qutSelDiv").offsetHeight;
			}
			setCenter(6, 61 + lookupVal);
		}
	}

	function showdiv() {
		if(document.getElementById('qutSelDiv') == null)
			return;
		if(document.getElementById('qutSelDiv').style.display == 'none') {
			document.getElementById('qutSelDiv').style.display = 'block';
			var lookupVal = 0
			if(document.getElementById("qutSelDiv") != null) {
				lookupVal = document.getElementById("qutSelDiv").offsetHeight;
			}
			setCenter(6, 96 + lookupVal);
		} else {
			document.getElementById('qutSelDiv').style.display = 'none';
			setCenter(6, 92);
		}
	}
	
</script>
<script language="javascript">
	var selectNodeID;
	var selectNodeName;
	function setSelBut() {
		document.getElementById("confbut").disabled = false;
	}
	function sendDataToFatherPage() {
		if (selectNodeID == null || selectNodeID == "") {
			alert("请选择一个人员");
			return;
		}
		if (selectNodeName == null || selectNodeName == "") {
			alert("请选择一个人员");
			return;
		}
		dialogArguments[2].value = selectNodeID;
		dialogArguments[3].innerHTML = selectNodeName;
		window.close();
	}
	function setSelector(id,name){
	selectNodeID=id;
	selectNodeName=name;
	}
	function refreshPage() {
		//刷新页面
		window.location.reload();
	}
</script>
<script language="javascript">
</script>
</head>
<body onkeydown="ak_lookupData();">
	<form name="univeiwform" id="univeiwform" action="" method="post">
		<input type="hidden" name="type" id="type" value="<%=type%>" />
		<div class="content">
			<div class="content_title_bg">
				<div class="content_title">系统维护视图</div>
			</div>
			<div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="mune_div">
					<tr>
						<td height="2px"></td>
					</tr>
					<tr>
						<td valign="top">
							<table align="center" class="button_bar">
								<tr>
									<td height="2px"></td>
									<td height="2px"></td>
								</tr>
								<tr>
									<td class="button_bar_bg">
										<div align="left" style="margin-top: 1px; padding-left: 2px;">
											<span class="data_objshow"><img title="刷新" src="<%=ico_path%>shua.png" onClick="refreshPage()" /> <span class="data_objshow"><INPUT name="confbut" id="confbut" VALUE="选择" TYPE=button onclick="sendDataToFatherPage();" disabled=true class="button"
													onmouseover="this.className='buttonhover'" onmouseout="this.className='button'" />
											</span> <span class="data_objshow"><INPUT VALUE="关闭" TYPE=button onclick="window.close();" class="button" onmouseover="this.className='buttonhover'" onmouseout="this.className='button'" />
											</span>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td height="2px"></td>
					</tr>
				</table>
			</div>
			<div style="height: 100%; width: 100%; overflow: auto; padding: 0px; vertical-align: top;" id="center">
				<div id="div1">
					<table cellpadding="0" cellspacing="0" border="0" width="98%" align="center" style="margin-top: 2px;">
						<tr>
							<td valign="top">
								<table width="100%" border="1" cellpadding="0" cellspacing="0" class="tableborder" id="edittab">
									<thead>
										<tr>
											<th nowrap="true" id="tdbox" width="40"></th>
											<%
												if (tableRawList != null && tableRawList.size() > 0) {
													String[] titleRow = tableRawList.get(0);
													for (int m = 0; m < titleRow.length; m++) {
														out.println("<th title=\"单击标题可以排序\" nowrap=\"true\" onclick=\"sortTable(" + (m + 1) + ",'string','edittab')\" style=\"cursor:pointer\">" + titleRow[m] + "</th>");
													}
												}
											%>
										</tr>
									</thead>
									<tbody>
										<%
											if (tableRawList != null && tableRawList.size() >= 2) {
												// 操作表记录生成
												for (int i = 1; i < tableRawList.size(); i++) {
													try {
														String[] rawDataArray = tableRawList.get(i);
														out.println("<tr>");
														out.println("<td id=\"ttbox\" nowrap=\"true\"><input type=\"radio\" name=\"selObj\" id=\"ids\" value=\"" + rawDataArray[0] + "\" onclick=\"setSelBut();setSelector('" + rawDataArray[0] + "','" + rawDataArray[1] + "')\"/></td>");
														for (int k = 0; k < rawDataArray.length; k++) {
															try {
																if (k == 0) {
																	out.println("<td nowrap=\"true\" \" style=\"cursor:pointer\">" + rawDataArray[k] + "</td>");
																} else {
																	out.println("<td nowrap=\"true\">" + rawDataArray[k] + "</td>");

																}
															} catch (Exception tableException) {
																tableException.printStackTrace();
																out.println("<td>error</td>");
															}
														}
														out.println("</tr>");
													} catch (Exception e) {
														e.printStackTrace();
													}
												}
											}
										%>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td height="15px"></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>