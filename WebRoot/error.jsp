<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <base href="<%=basePath%>">
    <%
	String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
%>
    <title>系统错误</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="<%=_contexPath%>/js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=_contexPath%>/js/jquery/jquery.easyui.min.js"></script>
	<link rel="stylesheet" media="all" type="text/css" href="<%=_contexPath%>/style/blue/css/main.css" />
	<link rel="stylesheet" media="all" type="text/css" href="<%=_contexPath%>/style/blue/css/basic.css" />
	<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/style/app/css/app_main.css" />
		<script type="text/javascript">	
			function OK(){
				var url = "${backUrl}";
			//	alert(url);
				if(url==null || url=="" || url=="null"){
				  	//self.location=first.jsp;
				}else{
					self.location.href = url;
				}
		   }
		   function show(){
		   	  $.messager.alert('错误提示','${returnMsg}','error',function () { 
		            OK();
			});
		   	 	
		   	  
		   	  
		   }
		</script>
	</head>

	<body>
		<div id="main_container">
			<table border="0" align="center" cellpadding="0" cellspacing="0"
				class="operation_top">
				<tr>
					<td valign="top" height="500">
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
	</body>
	<script type="text/javascript">show();</script>
</html>