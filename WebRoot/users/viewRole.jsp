<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import = "com.secpro.platform.monitoring.manage.entity.SysUserInfo" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
SysUserInfo user=(SysUserInfo)session.getAttribute("user");
Map app=user.getApp();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <%
	String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
%>
    <title>角色列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery/autoMergeCells.js"></script>
	<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="用户管理>角色列表";
	</script>
  </head>
    <body>
  	<table id="listDetail"></table>
	<script type="text/javascript">
		$(function(){  
    $("#listDetail").datagrid({  
    	width:700,
        heigth:700,     
        idField:'roleid',  
         pageSize:10,
   	 pageList:[10,20,50,100],
        url:'viewRole.action',  
       // queryParams:{'viewType':'RK','RKD_ID':_rkdId},  
        singleSelect:false,  
        fitColumns:true,  
        nowrap:true,  
        loadMsg:'数据加载中,请稍后……',  
        pagination:true ,
        rownumbers:true,
        columns:[[  
            {field:'roleid',checkbox:true},  
            {field:'rolename',title:'角色名称',width:100,editor:'text',sortable:true},    
            {field:'roledesc',title:'角色描述',width:100,editor:'text'}
           	          	
        ]],   
       toolbar: [
       <% if(app.get("创建角色")!=null||user.getAccount().equals("admin")){ %>
       {   
            text: '创建角色',   
            iconCls: 'icon-add',   
            handler: function () {   
               window.location.href="<%=_contexPath%>/users/addRole.jsp";
            }   
        }, '-', 
        <% }if(app.get("删除角色")!=null||user.getAccount().equals("admin")){ %>
        {   
            text: '删除角色',   
            iconCls: 'icon-remove',   
            handler: function () {   
                if (confirm("确定删除吗？")) {   
               	 var rows = $('#listDetail').datagrid('getSelections');  
				     if (null == rows || rows.length == 0) {  
				       alert('未选择条目');  
				        return;  
				     }  
				    var urll="deleteRole.action?roleid=";  
				    for(var i=0;i<rows.length;i++){ 
				    	if(i==(rows.length-1)){
				    		urll+=rows[i].roleid;
				    	}else{
				    		urll=urll+rows[i].roleid+",";
				    	}
				    }  
				 	window.location.href=urll;
				}  

            }   
        }, '-', 
        <% }if(app.get("修改角色")!=null||user.getAccount().equals("admin")){ %>
        {   
            text: '修改角色',   
            iconCls: 'icon-edit',   
            handler: function () {     
               	 var rows = $('#listDetail').datagrid('getSelections');  
				     if (null == rows || rows.length == 0) {  
				       alert('未选择条目');  
				        return;  
				     }  
				     if (rows.length>1){
				     	alert('修改角色只能选择一个条目');
				     	return;
				     }
				    var urll="toModifyRole.action?roleid="+rows[0].roleid;  
				    
				 	window.location.href=urll;
				}  

               
        },'-', 
         <% }if(app.get("角色权限关联")!=null||user.getAccount().equals("admin")){ %>
        {   
            text: '角色权限关联',   
            iconCls: 'icon-edit',   
            handler: function () {     
               	 
				    
				 	window.location.href="<%=_contexPath%>/users/roleAppMapping.jsp";
				}  

               
        }, '-',
        <%}%>
         {   
            text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#listDetail').datagrid('reload'); }
        }]
    }); 
		$('#listDetail').datagrid("getPager").pagination({
		    		displayMsg:'当前显示从{from}到{to}共{total}记录',
		   			 onBeforeRefresh:function(pageNumber, pageSize){
		     		$(this).pagination('loading');
		    		
		    		 $(this).pagination('loaded');
		   		 }
		  	 });  
		
		    $('#listDetail').datagrid("getPager").pagination({  
		
		        pageSize: 10,//每页显示的记录条数，默认为10  
		
		        pageList: [10,20,50,100],//可以设置每页记录条数的列表  
		
		        beforePageText: '第',//页数文本框前显示的汉字  
		
		        afterPageText: '页    共 {pages} 页',  
		
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
		
		        /*onBeforeRefresh:function(){ 
		
		            $(this).pagination('loading'); 
		
		            alert('before refresh'); 
		
		            $(this).pagination('loaded'); 
		
		        }*/ 
		
		    });
	});  
		
		
	</script>
  </body>
</html>
