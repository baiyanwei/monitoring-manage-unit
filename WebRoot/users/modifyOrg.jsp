<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <base href="<%=basePath%>">
    <%
	String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
%>
<title>部门信息修改</title>
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
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<link rel="stylesheet" media="all" type="text/css" href="style/blue/css/main.css" />
	<link rel="stylesheet" media="all" type="text/css" href="style/blue/css/basic.css" />
	<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/style/app/css/app_main.css" />
	<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="用户管理>部门信息修改";
	</script>
</head>
<body>
	<div style="padding:5px;border:1px solid #95B8E7;width:400px;background:#EFF5FF">
		<a id="sub" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'"  onclick="submitForm()">修改保存</a>
	</div>
	<div class="easyui-panel" title="" style="width:400px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="modifyOrg.action" method="post">
		    <input type="hidden" name="org.id" value="${ morg.id}"/>
		    	<table>
		    	<tr>
	    			<td><label>部门名称：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入部门名称" name="org.orgName" data-options="required:true" value="${ morg.orgName}"></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>部门描述：</label></td>
	    			<td><textarea class="datagrid-editable-input"  name="org.orgDesc" style="resize:none;">${ morg.orgDesc}</textarea> 
	    			</td>
	    		</tr>
	    		
	    		</table>
		    </form>		     
    	</div>
  </div>
  <script>
		function submitForm(){
			$('#ff').form('submit');
		}
		
	</script>
</body>
</html>