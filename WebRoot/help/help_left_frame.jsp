<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;   charset=utf-8">
<title>树导航</title>
<link href="../style/blue/css/main.css" rel="stylesheet" type="text/css" />
<link href="../style/app/css/app_main.css" rel="stylesheet" type="text/css" />
<script src="../js/ueframe/left.js"></script>
<script src="../js/ueframe/changeframe.js"></script>
<script language="javascript">
	function changeColor(obj, topourl) {
		//执行OUTLOOK 菜单动作
		try{
		var submunuTbody = getElementsByClass("submunuTbody");
		if (submunuTbody == null) {
			return;
		}
		for ( var i = 0; i < submunuTbody.length; i++) {
			if (i != obj) {
				document.getElementById("td" + i).className = "tree_title_bg0";
			} else {
				document.getElementById("td" + i).className = "tree_title_bg1";
			}
		}
		if (topourl == "#" || topourl == "") {
			topourl = "../blank.html";
		}
		var form = document.getElementById("hrefForm");
		form.action = topourl;
		form.method = "POST";
		form.target = "contentFrame";
		form.submit();
		}catch(e){
			alert(e);
		}
	}
	function setFrameUrl(frameIndex, frameUrl) {
		//执行OUTLOOK 菜单动作
		var curFrame = document.getElementById("left_frame_" + frameIndex);
		if (curFrame.contentWindow.location == "about:blank") {
			curFrame.src = frameUrl;
		}
	}
</script>
</head>
<body>
	<table class="left" cellpadding="0" cellspacing="0" border="0" align="left" id="showtree">
		<form id="hrefForm" name="hrefForm" method="get" action="#"></form>
		<tr>
			<td id="td0" class="tree_title_bg1" valign="top">
				<div class="showtree" id="hideleft" onClick="changeFrameleft()" title="隐藏左侧菜单"></div> <span class="data_objshow" onClick="showSubmenu(0);changeColor(0,'help_description.jsp');">帮助</span></td>
		</tr>
		<tr class=submunuTbody id=submunuTbody>
			<td valign="top"><iframe src="help_tree.jsp" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe></td>
		</tr>
	</table>
	<table class="left" cellpadding="0" cellspacing="0" border="0" align="center" id="notree" style="display: none; width: 75%;">
		<tr>
			<td class="tree_title_bg2" valign="top">
				<div class="notree" id="openleft" onClick="changeFrameleft()" title="显示左侧菜单"></div></td>
		</tr>

	</table>
	<!--多个OutLook菜单类型-->
</body>
</html>