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
    <title>创建模板</title>
    
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
		adiv.innerText="模板管理>创建模板";
	</script>
  </head>
  
  <body>
  <div class="easyui-panel" title="" style="width:400px">
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="getAllBaseline.action" method="post">
		    	<table>
	    		<tr>
	    			<td><label>模板名称：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入模板名称" name="templateName" data-options="required:true"></input></td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>模板描述：</label></td>
	    			<td><textarea class="datagrid-editable-input" name="templateDesc" style="resize:none;"></textarea></td>
	    		</tr>
	    		<tr>
	    			<td><label>防火墙厂商：</label></td>
	    			<td><input name="companyCode" id="cc1" class="easyui-combobox" data-options=" required:true, valueField: 'id', textField: 'text', url: 'findAllCompany'" /> 
	    			</td>
	    		</tr>
	    	</table>
	    	<table id="listDetail"></table>
		    </form>		     
    	</div>
  </div>
  <script>
  $(function(){  
    $("#listDetail").datagrid({  
    	width:700,
        heigth:700,     
        idField:'baselineId',  
        url:'getAllBaseline.action',  
       // queryParams:{'viewType':'RK','RKD_ID':_rkdId},  
        singleSelect:false,  
        fitColumns:true,  
        nowrap:true,  
        loadMsg:'数据加载中,请稍后……',  
        
        rownumbers:true,
        columns:[[  
            {field:'baselineId',checkbox:true},  
            {field:'baselineType',title:'基线类型',width:100,editor:'text',sortable:true},    
            {field:'blackWhite',title:'黑白名单',width:200,editor:'text',sortable:true},  
            {field:'baselineDesc',title:'模板描述',width:100,editor:'text'}      	
        ]],   
       toolbar: [ {   
            text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#listDetail').datagrid('reload'); }
        }],     
        
        onLoadSuccess:function(){  


      	   $(this).datagrid("autoMergeCells",['baselineType','blackWhite']);  
		} 
    }); 
		
	}); 
		function submitForm(){
			$('#ff').form('submit');
		}
		function clearForm(){
			$('#ff').form('clear');
		}
		
		 
		
	</script>
  </body>
</html>