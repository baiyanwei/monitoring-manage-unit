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
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="采集管理>创建采集端";
	</script>
  </head>
  
  <body>
  <div class="easyui-panel" title="" style="width:400px">
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="saveMca.action" method="post" onsubmit="return submitForm();">
		    
		    	<table>
		    	<tbody>
	    		<tr>
	    			<td><label>IP：</label></td>
	    			<td><input class="easyui-numberbox" style="width:21%" type="text" name="resObjForm.resIp1" missingMessage="请输入IP地址"  data-options="required:true"></input>.
	    			<input class="easyui-numberbox" style="width:21%" type="text" name="resObjForm.resIp2" missingMessage="请输入IP地址" data-options="required:true"></input>.
	    			<input class="easyui-numberbox" style="width:21%" type="text" name="resObjForm.resIp3" missingMessage="请输入IP地址" data-options="required:true"></input>.
	    			<input class="easyui-numberbox" style="width:21%" type="text" name="resObjForm.resIp4" missingMessage="请输入IP地址" data-options="required:true"></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>采集名称：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入资源名称" name="resObjForm.resName" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>资源描述：</label></td>
	    			<td><textarea class="datagrid-editable-input" name="resObjForm.resDesc" style="resize:none;"></textarea></td>
	    		</tr>
	    		<tr>
	    			<td><label>是否启动：</label></td>
	    			<td><input type="radio" name="resObjForm.resPaused" value="0" checked="true">启动</input>
	    				<input type="radio" name="resObjForm.resPaused" value="1">暂停</input>
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>所属城市：</label></td>
	    			<td><input name="resObjForm.cityCode" id="cc1" class="easyui-combobox" data-options=" required:true, valueField: 'id', textField: 'text', url: 'getAllCity.action'" /> 
	    			</td>
	    		</tr>
	    		
	    		</tbody>
	    		</table>
	    		<table>
	    		
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
  <script>
 
		function submitForm(){
			return $('#ff').form('validate');
		}
	</script>
  </body>
</html>
