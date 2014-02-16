<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.secpro.platform.monitoring.manage.util.PasswdRuleUtil" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>用户修改</title>
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
		adiv.innerText="用户管理>个人信息修改";
	</script>
</head>
<body>
	<div style="padding:5px;border:1px solid #95B8E7;width:388px;background:#EFF5FF">
		<a id="sub" href="javascript:if(document.ff.onsubmit()!=false)document.ff.submit();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'"  onclick="submitForm()">修改保存</a>
	</div>
	<div class="easyui-panel" title="" style="width:400px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" name="ff" action="modifySelf.action" method="post" onsubmit="return submitForm();">
		    <input type="hidden" name="user.id" value="${ user.id}"/>
		    <input type="hidden" name="passwd" value="${ user.password}"/>
		    <input type="hidden" name="orgid" value="${user.orgId }"/>
		    	<table>
		    	<tr>
	    			<td><label>账号：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入账号" name="user.account" data-options="required:true" value="${ user.account}" disabled=true></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>密码：</label></td>
	    			<td><input id="newpasswd" class="easyui-validatebox" type="password" missingMessage="请输入新密码,长度要大于<%=PasswdRuleUtil.passwdLong%>,至少包括1个数字，1个字母，1个特殊字符" name="user.password" data-options="required:true" value="${ user.password}" onblur="checkPasswd();"></input>
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>姓名：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入姓名" name="user.userName" data-options="required:true" value="${ user.userName}"></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>移动电话：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入移动电话" name="user.mobelTel" data-options="required:true" value="${ user.mobelTel}"> </input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>座机：</label></td>
	    			<td><input class="easyui-validatebox" type="text"  name="user.officeTel" value="${ user.officeTel}"> </input> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>邮箱：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入邮箱" name="user.email" data-options="required:true,validType:'email'" value="${ user.email}"> </input> 
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>部门：</label></td>
	    			<td> 
	    			<input class="easyui-combotree" name="user.orgId" missingMessage="请选择部门" data-options="url:'allOrgRree.action'" style="width:200px;">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>用户描述：</label></td>
	    			<td><textarea class="datagrid-editable-input"  name="user.userDesc" style="resize:none;">${ user.userDesc}</textarea> 
	    			</td>
	    		</tr>
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