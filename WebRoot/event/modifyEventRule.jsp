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
<title>告警规则修改</title>
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
		adiv.innerText="告警规则管理>告警规则修改";
	</script>
</head>
<body>
	<div style="padding:5px;border:1px solid #95B8E7;width:400px;background:#EFF5FF">
		<a id="sub" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'"  onclick="submitForm()">修改保存</a>
	</div>
	<div class="easyui-panel" title="" style="width:400px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="modifyEventRule.action" method="post">
		     <input type="hidden" name="eventRule.id" value="${eRule.id }"/>
		   <input type="hidden" name="eventRule.resId" value="${eRule.resId }"/>
		    	<input type="hidden" name="eventRule.eventTypeId" value="${eRule.eventTypeId }"/>
		    	<table>
		    	<tr>
	    			<td><label>告警级别：</label></td>
	    			<td>
	    				<select class="easyui-combobox" name="eventRule.eventLevel" missingMessage="请选择"  data-options="required:true" disabled=true>
	    					<c:if test="${eRule.eventLevel eq '1'}">
	    						<option value="1">通知</option>
	    					</c:if>
	    					<c:if test="${eRule.eventLevel eq '2'}">
	    						<option value="2">轻微</option>
	    					</c:if>
	    					<c:if test="${eRule.eventLevel eq '3'}">
	    						<option value="3">重要</option>
	    					</c:if>
	    					<c:if test="${eRule.eventLevel eq '4'}">
	    						<option value="4">紧急</option>
	    					</c:if>
	    				</select>
					</td>
	    		</tr>
	    		<tr>
	    			<td><label>是否生成告警短信：</label></td>
	    			<td>
	    				 <select class="easyui-combobox" name="eventRule.setMsg" missingMessage="请选择" data-options="required:true">
	    				 	<c:if test="${eRule.setMsg eq '0'}">
		    				 	<option value="0" selected>否</option>
		    				 	<option value="1" >是</option>
	    				 	</c:if>
	    				 	<c:if test="${eRule.setMsg eq '1'}">
		    				 	<option value="0" >否</option>
		    				 	<option value="1" selected>是</option>
	    				 	</c:if>
	    				 </select>
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>是否重复告警：</label></td>
	    			<td>
	    				 <select class="easyui-combobox" name="eventRule.repeat" missingMessage="请选择" data-options="required:true">
	    				 	<c:if test="${eRule.repeat eq '0'}">
		    				 	<option value="0" selected>否</option>
		    				 	<option value="1" >是</option>
	    				 	</c:if>
	    				 	<c:if test="${eRule.repeat eq '1'}">
		    				 	<option value="0" >否</option>
		    				 	<option value="1" selected>是</option>
	    				 	</c:if>
	    				 </select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>是否产生恢复短信：</label></td>
	    			<td>
	    				 <select class="easyui-combobox" name="eventRule.recoverSetMsg" missingMessage="请选择" data-options="required:true">
	    				 	<c:if test="${eRule.recoverSetMsg eq '0'}">
	    				 		<option value="0" selected>否</option>
	    				 		<option value="1">是</option>
	    				 	</c:if>
	    				 	<c:if test="${eRule.recoverSetMsg eq '1'}">
	    				 		<option value="0" >否</option>
	    				 		<option value="1" selected>是</option>
	    				 	</c:if>
	    				 </select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>告警判断条件：</label><select id="thresholdOpr" class="easyui-combobox" missingMessage="请选择" name="eventRule.thresholdOpr" data-options="required:true">
	    				 	<c:if test="${kpi.kpiType eq '0' }">
		    				 	<c:if test="${eRule.thresholdOpr eq '==' }">
			    				 	<option value="==" selected>等于</option>
			    				 	<option value="!=">不等于</option>
		    				 	</c:if>
		    				 	<c:if test="${eRule.thresholdOpr eq '!=' }">
			    				 	<option value="==" >等于</option>
			    				 	<option value="!=" selected>不等于</option>
		    				 	</c:if>
	    				 	</c:if>
	    				 	
	    				 	<c:if test="${kpi.kpiType eq '1' }">
	    				 		<c:if test="${eRule.thresholdOpr eq '>' }">
			    				 	<option value=">" selected>大于</option>
			    				 	<option value=">=">大于等于</option>
			    				 	<option value="=">等于</option>
			    				 	<option value="<=">小于等于</option>
			    				 	<option value="<">小于</option>
			    				</c:if>
			    				<c:if test="${eRule.thresholdOpr eq '>=' }">
			    				 	<option value=">" >大于</option>
			    				 	<option value=">=" selected>大于等于</option>
			    				 	<option value="=">等于</option>
			    				 	<option value="<=">小于等于</option>
			    				 	<option value="<">小于</option>
			    				</c:if>
			    				<c:if test="${eRule.thresholdOpr eq '=' }">
			    				 	<option value=">" >大于</option>
			    				 	<option value=">=" >大于等于</option>
			    				 	<option value="=" selected>等于</option>
			    				 	<option value="<=">小于等于</option>
			    				 	<option value="<">小于</option>
			    				</c:if>
			    				<c:if test="${eRule.thresholdOpr eq '<=' }">
			    				 	<option value=">" >大于</option>
			    				 	<option value=">=" >大于等于</option>
			    				 	<option value="=" >等于</option>
			    				 	<option value="<=" selected >小于等于</option>
			    				 	<option value="<">小于</option>
			    				</c:if>
			    				<c:if test="${eRule.thresholdOpr eq '<' }">
			    					<option value="<">小于</option>
			    				 	<option value=">" >大于</option>
			    				 	<option value=">=" >大于等于</option>
			    				 	<option value="=" >等于</option>
			    				 	<option value="<=" >小于等于</option>
			    				 	
			    				</c:if>
	    				 	</c:if>
	    				 </select></td>
	    			<td>
	    				 <lable>阀值:</lable>
	    				 <c:if test="${kpi.kpiType eq '0' }">
	    				 	<input id="thresholdValue" class="easyui-validatebox" type="text" missingMessage="请输入阀值" name="eventRule.thresholdValue" data-options="required:true" value="${ eRule.thresholdValue}"></input>
	    				 </c:if>
	    				 <c:if test="${kpi.kpiType eq '1' }">
	    				 	<input id="thresholdValue1" class="easyui-validatebox"  type="text" name="eventRule.thresholdValue" missingMessage="请输入阀值"  data-options="required:true" value="${ eRule.thresholdValue}"></input>
	    				 </c:if>
	    			</td>
	    		</tr>
	    		
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
				return ;
			}
			$('#ff').form('submit');
		}
		
	</script>
</body>
</html>