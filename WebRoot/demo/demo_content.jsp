<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>视图</title>
<script language="javascript">
	
</script>
<link rel="stylesheet" type="text/css" href="style/app/css/app_main.css" />
<link rel="stylesheet" type="text/css" href="style/blue/css/main.css" />
<link rel="stylesheet" type="text/css" href="style/blue/css/dropdown_ie.css" />
<link rel="stylesheet" type="text/css" href="style/blue/css/dropdown.css" />
<script language="JavaScript" type="text/javascript" src="js/ueframe/changeframe.js"></script>
<script language="JavaScript" type="text/javascript" src="js/ueframe/left.js"></script>
<script language="JavaScript" type="text/javascript" src="js/ueframe/main.js"></script>
<script language="javascript">
	//center DIV 设置大小
	window.onresize = function() {
		setQutSelectDiv();
		changeRow_color('edittab');
	}
	window.onload = function() {
		setCenter(6, 92);
		changeRow_color('edittab');
	}
	function ak_lookupData() {
		if (event.keyCode == 13) {
			if (document.getElementById('qutSelDiv').style.display == 'block') {
				document.getElementById('uqt_lookup_but').click();
			}
		}
	}
	function setQutSelectDiv() {
		if (document.getElementById('qutSelDiv') == null)
			return;
		var displayStr = document.getElementById('qutSelDiv').style.display;
		if (displayStr == "none") {
			setCenter(6, 92);
		} else {
			var lookupVal = 0
			if (document.getElementById("qutSelDiv") != null) {
				lookupVal = document.getElementById("qutSelDiv").offsetHeight;
			}
			setCenter(6, 96 + lookupVal);
		}
	}
	function showdiv() {
		if (document.getElementById('qutSelDiv') == null)
			return;
		if (document.getElementById('qutSelDiv').style.display == 'none') {
			document.getElementById('qutSelDiv').style.display = 'block';
			var lookupVal = 0
			if (document.getElementById("qutSelDiv") != null) {
				lookupVal = document.getElementById("qutSelDiv").offsetHeight;
			}
			setCenter(6, 96 + lookupVal);
		} else {
			document.getElementById('qutSelDiv').style.display = 'none';
			setCenter(6, 92);
		}
	}
	function edit_formC(basePath) {
		if (check_select_cbx() == false) {
			alert("您没有选择任何的操作资源配置项！");
			return;
		}
		var inputObj = document.forms["univeiwform"]
				.getElementsByTagName("input");
		var cbcount = 0;
		var input_tmp;
		for (i = 0; i < inputObj.length; i++) {
			var temp = inputObj[i];
			if (temp.type == "checkbox" && temp.checked) {
				cbcount++;
				input_tmp = temp;
			}
		}
		if (cbcount > 1) {
			alert("请选择一条需要修改的资源配置项！");
			return;
		}
		if (!privileCtrl(input_tmp, "update")) {
			alert("您对" + input_tmp.getAttribute("hint") + "没有修改操作权限!");
			return;
		}
		//配置项修改
		var form = document.getElementById("univeiwform");
		form.action = basePath
				+ "jsp/customized/hebei_bss/bc/autoform/updatetoform.jsp";
		form.target = '_self';
		form.method = 'POST';
		form.submit();
	}
</script>
</head>
<body onkeydown="ak_lookupData();">

	<div class="content">
		<div class="content_title_bg">
			<div class="content_title">视图</div>
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
							</tr>
							<tr>
								<td class="button_bar_bg">
									<div align="left" style="margin-top: 4px; padding-left: 3px;">
										<span class="data_objshow"><img title="后退" src="back.png" onClick="gobackPage('')" /> </span> <span class="data_objshow"><img title="刷新" src="shua.png" onClick="refreshPage('')" /> </span>

									</div></td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td height="2px"></td>
				</tr>
			</table>
			<div id="qutSelDiv" style='position: relative;display: "block"'>
				<table cellpadding="0" cellspacing="0" border="0" width="98%" style="margin-left: 10px;">
					<tr>
						<td>
							<div class="edit_tab_div">
								<div class="edit_tab_div_text">查询属性</div>
							</div>

							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder" id="tab1">

							</table></td>
					</tr>
					<tr>
						<td>
							<div align="center">
								<input type="button" id="uqt_lookup_but" name="button" value="查询" onClick="" class="button" onmouseover="this.className='buttonhover'" onmouseout="this.className='button'" /> <input type="button" name="button" value="关闭" onclick="showdiv()" class="button"
									onmouseover="this.className='buttonhover'" onmouseout="this.className='button'" />
							</div></td>
					</tr>
				</table>
			</div>
			<table class="page_bar" align="center">
				<tr>
					<td class="page_bar_bg">
						<div align="left" style="padding-left: 10px;">当前： 第</div></td>
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
										<th nowrap="true" id="tdbox" width="40"><input type="checkbox" name="chkAll" id="checkbox" onclick="checkAll('univeiwform')" /></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>ssssssss</td>
									</tr>
								</tbody>
							</table></td>
					</tr>
					<tr>
						<td height="15px"></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>