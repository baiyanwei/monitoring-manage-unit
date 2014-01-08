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
    <title>资源查看</title>
    
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
		adiv.innerText="厂商管理>查看采集命令";
	</script>
	<script>
		function deitable(){
			
			var openCommand=document.getElementById("openCommand");
			openCommand.disabled=false;
			var command=document.getElementById("command");
			command.disabled=false;
			$('#sub').linkbutton('enable');
			$('#clr').linkbutton('enable');
		}
	</script>
  </head>
  
  <body>
  <div style="padding:5px;border:1px solid #95B8E7;width:400px;background:#EFF5FF">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="deitable()">编辑内容</a>
		<a id="sub" href="javascript:if(document.ff.onsubmit()!=false)document.ff.submit();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'"  disabled=true>修改提交</a>
	</div>
  
  <div class="easyui-panel" title="" style="width:400px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" name="ff" action="modifyCommand.action" method="post" onsubmit="return submitForm();">
		    <input type="hidden" name="sc.id" value="${ ssc.id}"/>
		    <input type="hidden" name="sc.typeCode" value="${ ssc.typeCode}"/>
		    	<table>
	    		<tr>
	    			<td><label>开启命令：</label></td>
	    			<td><input id="openCommand" class="easyui-validatebox" type="text" missingMessage="开启命令为登录设备后开启自行才命令权限的命令，如无需开启则和采集命令相同" name="sc.openCommand" value="${ ssc.openCommand}" data-options="required:true" disabled=true></input></td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>采集命令：</label></td>
	    			<td><input id="command" class="easyui-validatebox" type="text" missingMessage="如需多个采集命令请使用^号将命令隔开" name="sc.command" value="${ ssc.command}" data-options="required:true" disabled=true></input></td>
	    		</tr>
	    		
	    		
	    		<tr>
	    			<td>
	    			&nbsp;
	    			</td>
	    			<td>
	    			&nbsp;
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
