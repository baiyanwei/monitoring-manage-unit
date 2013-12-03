<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <title>创建指标</title>
    
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
		adiv.innerText="指标管理>创建指标";
	</script>
  </head>
  
  <body>
  <div class="easyui-panel" title="" style="width:400px">
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="saveKpiInfo.action" method="post">
		    	<table>
		    	<tr>
	    			<td><label>指标名称：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入指标英文名称" name="kpiInfo.kpiName" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>指标描述：</label></td>
	    			<td><textarea class="datagrid-editable-input"  name="kpiInfo.kpiDesc" style="resize:none;"></textarea> 
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>指标类型：</label></td>
	    			<td> 
	    			<select class="easyui-combobox" name="kpiInfo.kpiType" data-options="required:true">
	    					<option value="0" selected>字符串</option>
	    					<option value="1">数字</option>
	    			</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>资源类型：</label></td>
	    			<td> 
	    			<input name="kpiInfo.classId" id="cc1" class="easyui-combobox" data-options=" required:true, valueField: 'classid', textField: 'text', url: 'findAllResClass.action'" /> 
	    			</td>
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
