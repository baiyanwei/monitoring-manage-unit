<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <%
	String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
%>
    <title>修改基线</title>
    
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
	<script type="text/javascript" src="js/jquery/autoMergeCells.js"></script>
	<link rel="stylesheet" media="all" type="text/css" href="style/blue/css/main.css" />
	<link rel="stylesheet" media="all" type="text/css" href="style/blue/css/basic.css" />
	<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/style/app/css/app_main.css" />
	<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="基线管理>基线修改";
	</script>
  </head>
  
  <body>
  <div class="easyui-panel" title="" style="width:700px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="modifyBaseLine.action" method="post">
		    	<input type="hidden"  name="sbl.id" value="${baseLine.id }"/>
		    	<table>
	    		<tr>
	    			<td><label>黑/白名单：</label></td>
	    			<td>
	    			
	    			<c:if test="${ baseLine.baselineBlackWhite eq '0'}">
	    				<input type="radio" name="sbl.baselineBlackWhite" value="0" checked="true">白名单</input>
	    				<input type="radio" name="sbl.baselineBlackWhite" value="1">黑名单</input>
	    			</c:if>
	    			<c:if test="${ baseLine.baselineBlackWhite eq '1'}">
	    				<input type="radio" name="sbl.baselineBlackWhite" value="0" >白名单</input>
	    				<input type="radio" name="sbl.baselineBlackWhite" value="1" checked="true">黑名单</input>
	    			</c:if>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>基线类型：</label></td>
	    			<td>
	    				<c:if test="${ sbl.baselineType eq '0'}">
	    				<input type="radio" name="sbl.baselineType" value="0" checked="true">配置基线</input>
	    				<input type="radio" name="sbl.baselineType" value="1">策略基线</input>
	    			</c:if>
	    			<c:if test="${ sbl.baselineType eq '1'}">
	    				<input type="radio" name="sbl.baselineType" value="0" >配置基线</input>
	    				<input type="radio" name="sbl.baselineType" value="1" checked="true">策略基线</input>
	    			</c:if>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>基线描述：</label></td>
	    			<td><textarea class="datagrid-editable-input" name="sbl.baselineDesc" style="resize:none;">${ sbl.baselineDesc}</textarea></td>
	    		</tr>
	    		<tr>
	    			<td>
	    			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	    			</td>
	    			<td>
	    			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
	    			</td>
	    		<tr>
	    		
	    	</table>
	    	
    	</div>
  </div>
  <script>
  
		function submitForm(){
			
			$('#ff').form('submit');
		}
		function clearForm(){
			$('#ff').form('reset');
		}
		
		
	</script>
	</form>	
  </body>
</html>