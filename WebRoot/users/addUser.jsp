<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.secpro.platform.monitoring.manage.util.PasswdRuleUtil" %>
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
    <title>创建用户</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery/autoMergeCells.js"></script>
	<script type="text/javascript" src="js/jquery/easyui-lang-zh_CN.js"></script>
	<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="用户管理>创建用户";
	</script>
  </head>
  
  <body>
  <div class="easyui-panel" title="" style="width:400px">
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="saveUser.action" method="post" onsubmit="return submitForm();">
		    	<table>
		    	<tr>
	    			<td><label>账号：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入账号" name="user.account" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>密码：</label></td>
	    			<td><input id="newpasswd" class="easyui-validatebox" type="password" missingMessage="请输入新密码,长度要大于<%=PasswdRuleUtil.passwdLong%>,至少包括1个数字，1个字母，1个特殊字符" name="user.password" data-options="required:true" onblur="checkPasswd();"></input></td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>姓名：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入姓名" name="user.userName" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>移动电话：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入移动电话" name="user.mobelTel" data-options="required:true"> </input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>座机：</label></td>
	    			<td><input class="easyui-validatebox" type="text"  name="user。officeTel" > </input> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>邮箱：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入邮箱" name="user.email" data-options="required:true,validType:'email'"> </input> 
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>部门：</label></td>
	    			<td> 
	    			<input class="easyui-combotree" name="user.orgId" missingMessage="请选择部门" data-options="url:'allOrgRree.action',required:true" style="width:200px;">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>用户描述：</label></td>
	    			<td><textarea class="datagrid-editable-input"  name="user.userDesc" style="resize:none;"></textarea> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>是否启用：</label></td>
	    			<td><input type="radio" name="user.enableAccount" value="0" checked="true">启用</input>
	    				<input type="radio" name="user.enableAccount" value="1">暂停</input>
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
		function checkPasswd(){
			var newpasswd=document.getElementById("newpasswd");
			
			$.post('checkPasswd.action', {newpasswd:newpasswd.value},function(data){
								 													 						
								 						if(data.checkok=="1"){
 															alert("密码错误长度不够，请重新填写！");
 															newpasswd.focus();
 														}else if(data.checkok=="2"){
 															alert("密码中最少需要一个数字，请重新填写！");
 															newpasswd.focus();
 														}else if(data.checkok=="0"){
 															alert("新密码不能为空！");
 															newpasswd.focus();
 														}else if(data.checkok=="3"){
 															alert("密码中最少需要一个英文字母，请重新填写");
 															newpasswd.focus();
 														}else if(data.checkok=="4"){
 															alert("密码中最少需要一个特殊字符，请重新填写");
 															newpasswd.focus();
 														}else if(data.checkok=="5"){
 															alert("密码不能超过16位，请重新填写");
 															newpasswd.focus();
 														}else{
 															
 														}
 														
													});
		}
	</script>
  </body>
</html>
