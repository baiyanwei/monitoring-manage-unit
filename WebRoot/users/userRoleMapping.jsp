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
<title>用户角色关联</title>
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
		adiv.innerText="用户管理>用户角色关联";
	</script>
</head>
<body>
	<div style="padding:5px;border:1px solid #95B8E7;width:988px;background:#EFF5FF">
		<a id="sub" href="javascript:document.ff.submit();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'" >修改保存</a>
	</div>
	<form id="ff" name="ff" action="userRoleMapping.action" method="post">
		<div class="easyui-panel" title="" style="width:1000px;height:400px;padding:0px;">
		<div class="easyui-layout" data-options="fit:true">
			
			<div data-options="region:'center'" style="padding:10px">
				<table id="listuser"></table>
	<script type="text/javascript">
		$(function(){  
    $("#listuser").datagrid({  
    	width:550,
        heigth:700,     
        idField:'userid',  
         pageSize:10,
   	 pageList:[10,20,50,100],
        url:'viewUserInfo.action',  
       // queryParams:{'viewType':'RK','RKD_ID':_rkdId},  
        singleSelect:false,  
        fitColumns:true,  
        nowrap:true,  
        loadMsg:'数据加载中,请稍后……',  
        pagination:true ,
        rownumbers:true,
        columns:[[  
            {field:'userid',checkbox:true},  
            {field:'account',title:'账号',width:100,editor:'text',sortable:true},    
            {field:'userName',title:'姓名',width:100,editor:'text'},  
           	{field:'userDesc',title:'用户描述',width:200,editor:'text'},  
           	{field:'enableAccount',title:'用户状态',width:100,editor:'text'},  
           	          	
        ]],   
       toolbar: [ '-', {   
            text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#listuser').datagrid('reload'); }
        }],
        onClickRow:function(index,row){  
        	$.post("getRoleByUser.action", {userid:row.userid},function(data){
        		var str=data.roleid;
		 		var strs= new Array();
		 		strs=str.split(",");
		 		$('#listrole').datagrid('clearChecked');
				var rows = $("#listrole").datagrid("getRows"); 
				for(var i=0;i<rows.length;i++){
					for(var j=0;j<strs.length;j++){
						if(rows[i].roleid==strs[j]){
							$('#listrole').datagrid('selectRow', i);
						}
					}
				}
        	
        	});
        }
    }); 
		$('#listuser').datagrid("getPager").pagination({
		    		displayMsg:'当前显示从{from}到{to}共{total}记录',
		   			 onBeforeRefresh:function(pageNumber, pageSize){
		     		$(this).pagination('loading');
		    		
		    		 $(this).pagination('loaded');
		   		 }
		  	 });  
		
		    $('#listuser').datagrid("getPager").pagination({  
		
		        pageSize: 10,//每页显示的记录条数，默认为10  
		
		        pageList: [10,20,50,100],//可以设置每页记录条数的列表  
		
		        beforePageText: '第',//页数文本框前显示的汉字  
		
		        afterPageText: '页    共 {pages} 页',  
		
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
		
		        
		
		    });
	});  
		
		
	</script>
			</div>
			<div data-options="region:'east',split:true" style="width:400px;padding:10px">
				<table id="listrole"></table>
	<script type="text/javascript">
		$(function(){  
    $("#listrole").datagrid({  
    	width:350,
        heigth:700,     
        idField:'roleid',  
         pageSize:50,
   	 pageList:[50,100],
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
       toolbar: [ {   
            text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#listrole').datagrid('reload'); }
        }]
    }); 
		$('#listrole').datagrid("getPager").pagination({
		    		displayMsg:'当前显示从{from}到{to}共{total}记录',
		   			 onBeforeRefresh:function(pageNumber, pageSize){
		     		$(this).pagination('loading');
		    		
		    		 $(this).pagination('loaded');
		   		 }
		  	 });  
		
		    $('#listrole').datagrid("getPager").pagination({  
		
		        pageSize: 50,//每页显示的记录条数，默认为10  
		
		        pageList: [50,100],//可以设置每页记录条数的列表  
		
		        beforePageText: '第',//页数文本框前显示的汉字  
		
		        afterPageText: '页    共 {pages} 页',  
		
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
		
		        
		
		    });
	});  
		
		
	</script>
			</div>
		</div>
	</div>
	</form>		     
    <script>
		function submitForm(){
			$('#ff').form('submit');
		}
		
	</script>
</body>
</html>