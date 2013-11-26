<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>添加防火墙类型</title>
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
		adiv.innerText="厂商管理>添加设备型号";
	</script>
</head>
<body>
	<div class="easyui-panel" title="" style="width:400px">
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="saveDevType.action" method="post">
		    	<input type="hidden" name="company.companyCode" value="${companyCode }"/>
		    	<table>
	    		<tr>
	    			<td><label>防火墙类型名称：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入厂商名称" name="company.typeName" data-options="required:true"></input></td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>类型编码：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入厂商编码" name="company.typeCode" data-options="required:true"></input></td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>类型描述：</label></td>
	    			<td><textarea class="datagrid-editable-input" name="company.typeDesc" style="resize:none;"></textarea></td>
	    		</tr>
	    		<tr>
	    			<td>
	    			&nbsp;
	    			</td>
	    			<td>
	    			&nbsp;
	    			</td>
	    		<tr>
	    		<tr>
	    			<td>
	    			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	    			</td>
	    			<td>
	    			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">清除</a>
	    			</td>
	    		<tr>
	    	</table>
		    </form>		     
    	</div>
  </div>
  <script>
		function submitForm(){
			$('#ff').form('submit');
		}
		function clearForm(){
			$('#ff').form('clear');
		}
		
	</script>
</body>
</html>