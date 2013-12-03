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
	<div data-options="region:'north',border:false"  style="height:80px;width:1270px;padding:0px">
		
		<div class="content_title_bg" >
			<div class="content_title">欢迎 李衍！</div>
		</div>
		<div class="about_title" >防火墙策略核查系统</div>
	
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
									<td class="page_bar_bg" >
										<div align="left" style="padding-left: 10px;">
											<a href="javascript:shouye();" class="easyui-linkbutton" data-options="plain:true">首页</a>
											<a href="javascript:baselinemanager();" class="easyui-linkbutton" data-options="plain:true">基线管理</a>
											<a href="javascript:mcamanager();" class="easyui-linkbutton" data-options="plain:true">采集机管理</a>
											<a href="javascript:baselinetemplatemanager();" class="easyui-linkbutton" data-options="plain:true">基线模板管理</a>
											<a href="#" class="easyui-linkbutton" data-options="plain:true">系统管理</a>
											<a href="javascript:eventmanager();" class="easyui-linkbutton" data-options="plain:true">告警策略管理</a>
											<a href="javascript:companymanager();" class="easyui-linkbutton" data-options="plain:true">厂商管理</a>
											<a href="javascript:rulemanager();" class="easyui-linkbutton" data-options="plain:true">规则管理</a>
											<a href="javascript:usermanager();" class="easyui-linkbutton" data-options="plain:true">用户管理</a>
											<a href="javascript:rolemanager();" class="easyui-linkbutton" data-options="plain:true">角色管理</a>
											<a href="javascript:orgmanager();" class="easyui-linkbutton" data-options="plain:true">部门管理</a>
											<a href="javascript:kpimanager();" class="easyui-linkbutton" data-options="plain:true">指标管理</a>
											<a href="javascript:eventrulemanager();" class="easyui-linkbutton" data-options="plain:true">告警规则管理</a>
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
		function baselinetemplatemanager(){
			window.frames.contextMain.location.href="baseline/viewBaseLineTemplate.jsp";
		}
		function baselinemanager(){
			window.frames.contextMain.location.href="baseline/viewBaseLine.jsp";
		}
		function rulemanager(){
			window.frames.contextMain.location.href="rule/viewAllDevType.jsp";
		}
		function usermanager(){
			window.frames.contextMain.location.href="users/viewUser.jsp";
		}
		function rolemanager(){
			window.frames.contextMain.location.href="users/viewRole.jsp";
		}
		function orgmanager(){
			window.frames.contextMain.location.href="users/viewOrg.jsp";
		}
		function kpimanager(){
			window.frames.contextMain.location.href="resobj/viewKpiInfo.jsp";
		}
		function eventmanager(){
			window.frames.contextMain.location.href="event/viewEventType.jsp";
		}
		function eventrulemanager(){
			window.frames.contextMain.location.href="event/eventRule.jsp";
		}
	</script>
</body>
</html>
