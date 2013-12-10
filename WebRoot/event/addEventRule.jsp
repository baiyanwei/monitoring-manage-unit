<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
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
    <title>创建告警规则</title>
    
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
	<script type="text/javascript" src="js/jquery/autoMergeCells.js"></script>
	<link rel="stylesheet" media="all" type="text/css" href="style/blue/css/main.css" />
	<link rel="stylesheet" media="all" type="text/css" href="style/blue/css/basic.css" />
	<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/style/app/css/app_main.css" />
	<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="告警规则管理>创建告警规则";
	</script>
  </head>
  
  <body>
  <div class="easyui-panel" title="" style="width:400px">
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="saveEventRule.action" method="post" onsubmit="return submitForm();">
		    	<input type="hidden" name="eventRule.resId" value="${resId }"/>
		    	<input type="hidden" name="eventRule.eventTypeId" value="${type.id }"/>
		    	<table>
		    	<tr>
	    			<td><label>告警级别：</label></td>
	    			<td>
	    				<select class="easyui-combobox" name="eventRule.eventLevel" missingMessage="请选择"  data-options="required:true">
	    					<c:if test="${fn:contains(levels,'1')==false}">
	    						<option value="1">通知</option>
	    					</c:if>
	    					<c:if test="${fn:contains(levels,'2')==false}">
	    						<option value="2">轻微</option>
	    					</c:if>
	    					<c:if test="${fn:contains(levels,'3')==false}">
	    						<option value="3">重要</option>
	    					</c:if>
	    					<c:if test="${fn:contains(levels,'4')==false}">
	    						<option value="4">紧急</option>
	    					</c:if>
	    				</select>
					</td>
	    		</tr>
	    		<tr>
	    			<td><label>是否生成告警短信：</label></td>
	    			<td>
	    				 <select class="easyui-combobox" name="eventRule.setMsg" missingMessage="请选择" data-options="required:true">
	    				 	<option value="0" selected>否</option>
	    				 	<option value="1" >是</option>
	    				 </select>
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>是否重复告警：</label></td>
	    			<td>
	    				 <select class="easyui-combobox" name="eventRule.repeat" missingMessage="请选择" data-options="required:true">
	    				 	<option value="0" selected>否</option>
	    				 	<option value="1" >是</option>
	    				 </select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>是否产生恢复短信：</label></td>
	    			<td>
	    				 <select class="easyui-combobox" name="eventRule.recoverSetMsg" missingMessage="请选择" data-options="required:true">
	    				 	<option value="0" selected>否</option>
	    				 	<option value="1">是</option>
	    				 </select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>告警判断条件：</label><select id="thresholdOpr" class="easyui-combobox" missingMessage="请选择" name="eventRule.thresholdOpr" data-options="required:true">
	    				 	<c:if test="${kpi.kpiType eq '0' }">
		    				 	<option value="==" selected>等于</option>
		    				 	<option value="!=">不等于</option>
	    				 	</c:if>
	    				 	<c:if test="${kpi.kpiType eq '1' }">
		    				 	<option value=">" selected>大于</option>
		    				 	<option value=">=">大于等于</option>
		    				 	<option value="=">等于</option>
		    				 	<option value="<=">小于等于</option>
		    				 	<option value="<">小于</option>
	    				 	</c:if>
	    				 </select></td>
	    			<td>
	    				 <lable>阀值:</lable>
	    				 <c:if test="${kpi.kpiType eq '0' }">
	    				 	<input id="thresholdValue" class="easyui-validatebox" type="text" missingMessage="请输入阀值" name="eventRule.thresholdValue" data-options="required:true"></input>
	    				 </c:if>
	    				 <c:if test="${kpi.kpiType eq '1' }">
	    				 	<input id="thresholdValue1" class="easyui-validatebox"  type="text" name="eventRule.thresholdValue" missingMessage="请输入阀值"  data-options="required:true"></input>
	    				 </c:if>
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
			var thresholdValue=document.getElementById("thresholdValue");
			var thresholdValue1=document.getElementById("thresholdValue1");
			var thresholdOpr=document.getElementById("thresholdOpr");
			if(thresholdValue!=null){
				if(thresholdValue.value=='null'&&thresholdOpr.value=='=='){
					alert("只有选择不等于时，阀值才能未字符串null");
					return;
				}
			}
			if(thresholdValue1!=null&&isNaN(thresholdValue1.value)){
				alert("阀值请输入数字！");
				return false;
			}
			return true;
		}
		function clearForm(){
			$('#ff').form('clear');
		}
		
		 
		
	</script>
  </body>
</html>
