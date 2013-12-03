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
    <title>模板资源映射</title>
    
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
		adiv.innerText="基线模板管理>模板资源映射";
	</script>
  </head>
    <body>
    <form id="ff" action="templateResMapping.action" method="post">
    <table>
    	<tr>
    		<td>
			    <lable>&nbsp;&nbsp;省份：</lable>
			    <input name="cityCode" id="cc1" class="easyui-combobox" data-options=" required:true, valueField: 'id', textField: 'text', url: 'getAllCity.action', 
			
										onSelect: function(rec){ 
			
										var url = 'getCityByParent.action?cityCode='+rec.id; 
										$('#cc2').combobox('clear');
										$('#cc2').combobox('reload', url); 
			
									}" /> 
			</td>
			<td>
		<lable>&nbsp;&nbsp;单位：</lable>
		<input id="cc2" class="easyui-combobox" name="sbucityCode" data-options="required:true,valueField:'id',textField:'text',
			onSelect: function(rec){ 
								var url = 'getResObjByCity.action?cityCode='+rec.id; 
								$('#cc3').combobox('clear');
								$('#cc3').combobox('reload', url); 
		}" />
			</td>
			<td>
		<lable>&nbsp;防火墙：</lable>
		<input id="cc3" class="easyui-combobox" name="resId" data-options="required:true,valueField:'id',textField:'text',
			onSelect: function(rec){ 
								var url = 'getBaseLineTemplateByCompany.action?resId='+rec.id; 
								$('#cc5').combobox('clear');
								$('#cc5').combobox('reload', url); 
		
		}" />
		</td>
	  	</tr>
	  	<tr>
	  		<td>
	  	<lable>设备厂商：</lable>
	  	<input name="companyCode" id="cc4" class="easyui-combobox" data-options=" required:true, valueField: 'id', textField: 'text', url: 'findAllCompany.action', 
	
								onSelect: function(rec){ 
	
								var url = 'getBaseLineTemplateByCompany.action?companyCode='+rec.id; 
								$('#cc5').combobox('clear');
								$('#cc5').combobox('reload', url); 
	
							}" /> 
		</td>
		<td>
		<lable>&nbsp;基线模板</lable>
		<input id="cc5" class="easyui-combobox" name="templateId" data-options="required:true,valueField:'id',textField:'text'" />
		</td>
		<td></td>
		</tr>
		<tr>
		<td>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
		</td>
		<td>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">清除</a>
		</td>
		<td></td>
		</tr>
	</table>
	</form>
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
