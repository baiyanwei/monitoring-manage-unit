<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
	String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
%>
<head>
	<title>防火墙核查系统</title>
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<link rel="stylesheet" media="all" type="text/css" href="style/blue/css/main.css" />
	<link rel="stylesheet" media="all" type="text/css" href="style/blue/css/basic.css" />
	<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/style/app/css/app_main.css" />
	<script src="js/ueframe/main.js"></script>
	<script language="javascript">
		window.onresize = function() {
			setCenter(4, 80);
		}
		window.onload = function() {
			setCenter(4, 80);
		}
	</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false"  style="height:80px;padding:0px">
		
		<div class="content_title_bg">
			<div class="content_title">欢迎 李衍！</div>
		</div>
		<div class="about_title">防火墙策略核查系统</div>
	
	</div>
	<div data-options="region:'west',split:true,title:'资源树'" style="width:200px;padding:10px;">
		<iframe src="tree/treeViewFrame.jsp" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
	</div>
	
	<div data-options="region:'center',title:''">
		<div id="aa" class="easyui-accordion" scrolling="false" data-options="fit:true,border:false,onSelect:ref">
			
					<div class="content" title="主视图" data-options="selected:true" style="padding:0px;">
						<div>
							<table class="page_bar" align="center" style="padding:0px;">
								<tr>
									<td class="page_bar_bg">
										<div align="left" style="padding-left: 10px;">
											<a href="javascript:shouye();" class="easyui-linkbutton" data-options="plain:true">首页</a>
											<a href="#" class="easyui-linkbutton" data-options="plain:true">基线管理</a>
											<a href="javascript:mcamanager();" class="easyui-linkbutton" data-options="plain:true">采集机管理</a>
											<a href="javascript:baselinemanager();" class="easyui-linkbutton" data-options="plain:true">模板管理</a>
											<a href="#" class="easyui-linkbutton" data-options="plain:true">系统管理</a>
											<a href="#" class="easyui-linkbutton" data-options="plain:true">告警策略管理</a>
											<a href="javascript:companymanager();" class="easyui-linkbutton" data-options="plain:true">厂商管理</a>
										</div>
									</td>
								</tr>
							</table>
						</div>
						<div class="content_title_bg">
							<div id="operation" class="content_title">
								
							</div>
						</div>
						<div id="center" style="overflow: auto; width: 100%; padding: 0px;">
							<iframe target="contextMain" name="contextMain" id="contextMain" src="" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>	
					
						</div>
					</div>
					
			
				<div title="告警视图"  style="padding:10px;">
					<iframe id="eventl" src="event/eventlist.jsp" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
				</div>
			</div>	
		
			
	</div>
	<script type="text/javascript">
	
		function ref(title,index){
			if(title=="告警视图"){
				window.frames.eventl.location.reload();
			}
			//alert(title);
		}
		function shouye(){
			window.frames.contextMain.location.href="resobj/addResObj.jsp";
		}
		function companymanager(){
			window.frames.contextMain.location.href="devcompany/viewAllDevCompany.jsp";
		}
		function mcamanager(){
			window.frames.contextMain.location.href="resobj/viewMca.jsp";
		}
		function baselinemanager(){
			window.frames.contextMain.location.href="baseline/viewBaseLineTemplate.jsp";
		}
	</script>
</body>
</html>
