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
<title>角色应用关联</title>
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
		adiv.innerText="角色管理》角色应用关联";
	</script>
</head>
<body>
	<div style="padding:5px;border:1px solid #95B8E7;width:400px;background:#EFF5FF">
		<a id="sub" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'"  onclick="submitForm()">修改保存</a>
	</div>
	<form id="ff" action="roleAppMapping.action" method="post">
		<input id="appid" type="hidden" name="appid" value=""/>
		<div class="easyui-panel" title="" style="width:1000px;height:400px;padding:0px;">
		<div class="easyui-layout" data-options="fit:true">
			
			<div data-options="region:'center'" style="padding:10px">
				<table id="listDetail"></table>
	<script type="text/javascript">
		$(function(){  
    $("#listDetail").datagrid({  
    	width:550,
        heigth:700,     
        idField:'roleid',  
         pageSize:10,
   	 pageList:[10,20,50,100],
        url:'viewRole.action',  
       // queryParams:{'viewType':'RK','RKD_ID':_rkdId},  
        singleSelect:true,  
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
			handler:function(){$('#listDetail').datagrid('reload'); }
        }],
        onClickRow:function(index,row){  
        	//从后台获取所选role当前对应的app
           $.post("getAppByRole.action", {roleid:row.roleid},function(data){
		 		var str=data.appids;
		 		var strs= new Array();
		 		strs=str.split(",");
		 		var roots=$('#tt').tree('getRoots'),children,i,j;  
		 		//清除当前选中状态
   				for(i=0;i<roots.length;i++){    
   					children=$('#tt').tree('getChildren',roots[i].target);
	   				for(j=0;j<children.length;j++){
	     						$('#tt').tree('uncheck',children[j].target);
	     			} 
   				}	
		 		//根据当前角色选择的APPID，对应用树选中状态进行设置
		 		for (y=0;y<strs.length ;y++ )    
  				{    
   					for(i=0;i<roots.length;i++){    
    				 children=$('#tt').tree('getChildren',roots[i].target);  
    				
     				for(j=0;j<children.length;j++){
     						if(children[j].id==strs[y]){
     							$('#tt').tree('check',children[j].target);
     						}
     					
     				}
  				 }  
    			} 
			});
           
        }  
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
			</div>
			<div data-options="region:'east',split:true" style="width:400px;padding:10px">
				<ul id="tt" name="id" class="easyui-tree" data-options="url:'getAppTree.action',animate:true,checkbox:true"></ul>				
			</div>
		</div>
	</div>
	</form>		     
    <script>
		function submitForm(){
			//获取选中的APP
			var nodes = $('#tt').tree('getChecked');
			var s = '';
			//将选中的app的ID值进行拼接
			for(var i=0; i<nodes.length; i++){
				if (s != '') s += ',';
				s += nodes[i].id;
			}
			//获取隐藏的input，并设置他的value为选中的appid的拼接字符串
			var inp=document.getElementById("appid");
			inp.value=s;
			$('#ff').form('submit');
		}
		
	</script>
</body>
</html>