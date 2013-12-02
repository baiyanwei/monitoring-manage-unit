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
    <title>资源添加</title>
    
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
		adiv.innerText="资源管理>创建资源";
	</script>
  </head>
  
  <body>
  	<form id="ff" action="baseLineRuleMapping.action" method="post" target="contextMain">
    		<input type="hidden" name="baselineId" value="${baselineId }"/>
    		
  			<div id="p" class="easyui-panel" title="基线规则" style="width:500px;height:300px;padding:10px;">
				<table width="400" border="0">
				 	
					 <tr>
					 	<td>
					 		<tr>
	    			<td><label>防火墙厂商：</label></td>
	    			<td><input name="companyCode" id="cc1" class="easyui-combobox" data-options=" required:true, valueField: 'id', textField: 'text', url: 'findAllCompany', 

							onSelect: function(rec){ 

							var url = 'findDevTypeByCompanyCode?companyCode='+rec.id; 
							$('#cc2').combobox('clear'); 
							$('#cc2').combobox('reload', url); 

						}" /> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>防火墙型号：</label></td>
	    			<td><input id="cc2" class="easyui-combobox" name="typeCode" data-options="required:true,valueField:'id',textField:'text',
	    				onSelect: function(rec){ 
							$.post('getBaseLineRuleByDev.action', {typeCode:rec.id,baselineId:${baselineId }},function(data){
					 		var str=data.rule;
					 		var cc=document.getElementById('ccr');
					 		cc.innerText=str;
						});
							
	    			}" /></td>
	    		</tr>
		
       				 <tr>
				          <td><lable>防火墙策略包含与冲突比对规则：</lable></td>
				          <td><label>
				            <textarea id="ccr" class="datagrid-editable-input"  name="baselineRule" style="resize:none;width=300;height=100">${configRule.containConflictRule }</textarea> 
				           
				          </label></td>
       				 </tr>
       				 <tr>
	    			<td>
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
			</div>
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