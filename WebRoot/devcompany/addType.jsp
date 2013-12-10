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
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
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
	    			<input type="submit" value="提交"/>
	    			</td>
	    			<td>
	    			<input type="reset" value="重置"/>
	    			</td>
	    		<tr>
	    	</table>
		    </form>		     
    	</div>
  </div>
</body>
</html>