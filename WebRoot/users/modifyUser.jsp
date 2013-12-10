<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		adiv.innerText="用户管理》用户信息修改";
	</script>
</head>
<body>
	<div style="padding:5px;border:1px solid #95B8E7;width:388px;background:#EFF5FF">
		<a id="sub" href="javascript:document.ff.submit();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'" >修改保存</a>
	</div>
	<div class="easyui-panel" title="" style="width:400px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" name="ff" action="modifyUser.action" method="post">
		    <input type="hidden" name="user.id" value="${ muser.id}"/>
		    <input type="hidden" name="passwd" value="${ muser.password}"/>
		    <input type="hidden" name="orgid" value="${muser.orgId }"/>
		    	<table>
		    	<tr>
	    			<td><label>账号：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入账号" name="user.account" data-options="required:true" value="${ muser.account}" disabled=true></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>密码：</label></td>
	    			<td><input class="easyui-validatebox" type="password" missingMessage="请输入密码" name="user.password" data-options="required:true" value="${ muser.password}"></input></td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>姓名：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入姓名" name="user.userName" data-options="required:true" value="${ muser.userName}"></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>移动电话：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入移动电话" name="user.mobelTel" data-options="required:true" value="${ muser.mobelTel}"> </input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>座机：</label></td>
	    			<td><input class="easyui-validatebox" type="text"  name="user.officeTel" value="${ muser.officeTel}"> </input> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>邮箱：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入邮箱" name="user.email" data-options="required:true,validType:'email'" value="${ muser.email}"> </input> 
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
	    			<td><textarea class="datagrid-editable-input"  name="user.userDesc" style="resize:none;">${ muser.userDesc}</textarea> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>是否启用：</label></td>
	    			<td>
	    			<c:if test="${ muser.enableAccount eq '0'}">
	    				<input type="radio" name="user.enableAccount" value="0" checked="true">启用</input>
	    				<input type="radio" name="user.enableAccount" value="1">暂停</input>
	    			</c:if>
	    			<c:if test="${ muser.enableAccount eq '1'}">
	    			<input type="radio" name="user.enableAccount" value="0" >启用</input>
	    				<input type="radio" name="user.enableAccount" value="1" checked="true">暂停</input>
	    			</c:if>
	    			</td>
	    		</tr>
	    		</table>
		    </form>		     
    	</div>
  </div>
</body>
</html>