<%@ page contentType="text/html; charset=GBK"%>
<%
	response.setHeader("Expires","0");
	response.setHeader("Pragma","no-cache");
	response.setHeader("Catch-Control","no-cache");
	String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
%>
<script>
  _contexPath='<%=_contexPath%>';
</script>
<html>
	<head>
		<title>���¼��б�</title>
		<link href="css/default.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/css/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/css/icon.css">
		<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/css/demo.css">
		<script type="text/javascript"
			src='<%=_contexPath%>/js/event/MSClass.js'></script>
		<script type='text/javascript'
			src='<%=_contexPath%>/dwr/interface/EventAjax.js'></script>
		<script type='text/javascript' src='<%=_contexPath%>/dwr/engine.js'></script>
		<script type='text/javascript' src='<%=_contexPath%>/dwr/util.js'></script>
		<script type='text/javascript' src='<%=_contexPath%>/js/jquery/jquery-1.8.0.min.js'></script>
		<script type='text/javascript' src='<%=_contexPath%>/js/jquery/jquery.easyui.min.js'></script>
		<script type="text/javascript">
			var timer1 ;
			function confirmEvent(id)
			{
				window.open('toDealEvent.action?eventId='+id,'','width=900px,height=900px,left=10, top=10,toolbar=no, status=no, menubar=no, resizable=yes, scrollbars=yes');
			}
			DWREngine.setTimeout(10000);
      		DWREngine.setErrorHandler(function(){return;});
      		//��ȡȫ���澯
			function getEventList()
			{	
				EventAjax.getNewEventList(function(data){
					var s = data.indexOf("|");
					var size=0;
					if(s<0) 
					{	
						data="";
					}else{
						size=data.substring(0,data.indexOf("|"));
						data=data.substring(data.indexOf("|")+1);
					}
					document.getElementById("totalDiv").innerHTML=size+'';
					
					document.getElementById("textnomouse").innerHTML=data;
					if(marquee2.TimerID==null)return;
					clearInterval(marquee2.TimerID);
				});
			}
			
			
			
			//��ȡָ�����͸澯
			function getOneTypeEventListByLevel(eventType)
			{	
				EventAjax.getNewEventListByType(eventType,function(data){
					var s = data.indexOf("|");
					var size=0;
					if(s<0) 
					{	
						data="";
					}else{
						size=data.substring(0,data.indexOf("|"));
						data=data.substring(data.indexOf("|")+1);
					}
					document.getElementById(eventType+"Div").innerHTML=size+'';
					
					document.getElementById(eventType+"mouse").innerHTML=data;
					if(marquee2.TimerID==null)return;
					clearInterval(marquee2.TimerID);
				});
			}
			//�����¼�������ʾδ�����¼��б�
			function getNewEventByClass(){
				var checkboxs = document.getElementsByName("all");
				var classIds = ",";
				for(var i = 0; i < checkboxs.length; i++){
					if(checkboxs[i].checked){
						classIds += checkboxs[i].value+",";
					}
				}
				EventAjax.getNewEventListByLevel(classIds,function(data){
                    var s = data.indexOf("|");
					var size=0;
					if(s<0) 
					{	
						data="";
					}else{
						size=data.substring(0,data.indexOf("|"));
						 data=data.substring(data.indexOf("|")+1);
					}
					document.getElementById("totalDiv").innerHTML=size+'';
					
					document.getElementById("textnomouse").innerHTML=data;
				});
			}
			//��ȡ�ƶ������ƶ�����ĸ澯
			function getOneTypeNewEventByClass(eventType){
				
				var checkboxs = document.getElementsByName(eventType);
				var classIds = ",";
				for(var i = 0; i < checkboxs.length; i++){
					if(checkboxs[i].checked){
						classIds += checkboxs[i].value+",";
					}
				}
				
				EventAjax.getOneTypeEventListByLevel(classIds,eventType,function(data){
                    var s = data.indexOf("|");
					var size=0;
					if(s<0) 
					{	
						data="";
					}else{
						size=data.substring(0,data.indexOf("|"));
						 data=data.substring(data.indexOf("|")+1);
					}
					document.getElementById(eventType+"Div").innerHTML=size+'';
					
					document.getElementById(eventType+"mouse").innerHTML=data;
				});
			}
  	</script>
  	
	</head>

	<body style="padding-left: 0px; padding-top: 0px;">
	<div id="eventtabs" style="width:auto;height:auto;text-align:center">
		<div title="ȫ���澯" style="padding:10px;text-align:center">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="small_table">
				<tr>
					<th class="th_w">
						δ�����¼��б�&nbsp;[��
						<div id="totalDiv" style="display: inline; color: red;">
							0
						</div>
						��] &nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="minor" name="all" value="1" checked
							onclick="getNewEventByClass();">
						<font color='#C8FF00'>֪ͨ</font>
						<input type="checkbox" id="warning" name="all" value="2"
							checked onclick="getNewEventByClass();">
						<font color='#FFD200'>��΢</font>
						<input type="checkbox" id="major" name="all" value="3" checked
							onclick="getNewEventByClass();">
						<font color='#FF9200'>��Ҫ</font>
						<input type="checkbox" id="critical" name="all" value="4"
							checked onclick="getNewEventByClass();">
						<font color='#FF1042'>����</font>
					</th>
				</tr>
				<tr>
					<td colspan="5" id="textTd">
				
						<div id="textnomouse">
						</div>
					
					</td>
				</tr>
			</table>
		</div>
		<div title="���߸澯" style="padding:10px">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="small_table">
				<tr>
					<th class="th_w">
						δ�����¼��б�&nbsp;[��
						<div id="baselineDiv" style="display: inline; color: red;">
							0
						</div>
						��] &nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="minor" name="baseline" value="1" checked
							onclick="getOneTypeNewEventByClass('baseline');">
						<font color='#C8FF00'>֪ͨ</font>
						<input type="checkbox" id="warning" name="baseline" value="2"
							checked onclick="getOneTypeNewEventByClass('baseline');">
						<font color='#FFD200'>��΢</font>
						<input type="checkbox" id="major" name="baseline" value="3" checked
							onclick="getOneTypeNewEventByClass('baseline');">
						<font color='#FF9200'>��Ҫ</font>
						<input type="checkbox" id="critical" name="baseline" value="4"
							checked onclick="getOneTypeNewEventByClass('baseline');">
						<font color='#FF1042'>����</font>
					</th>
				</tr>
				<tr>
					<td colspan="5" id="textTd">
				
						<div id="baselinemouse">
						</div>
				
					</td>
				</tr>
			</table>
		</div>
		<div title="����ǽ״̬�澯" style="padding:10px">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="small_table">
				<tr>
					<th class="th_w">
						δ�����¼��б�&nbsp;[��
						<div id="fwDiv" style="display: inline; color: red;">
							0
						</div>
						��] &nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="minor" name="fw" value="1" checked
							onclick="getOneTypeNewEventByClass('fw');">
						<font color='#C8FF00'>֪ͨ</font>
						<input type="checkbox" id="warning" name="fw" value="2"
							checked onclick="getOneTypeNewEventByClass('fw');">
						<font color='#FFD200'>��΢</font>
						<input type="checkbox" id="major" name="fw" value="3" checked
							onclick="getOneTypeNewEventByClass('fw');">
						<font color='#FF9200'>��Ҫ</font>
						<input type="checkbox" id="critical" name="fw" value="4"
							checked onclick="getOneTypeNewEventByClass('fw');">
						<font color='#FF1042'>����</font>
					</th>
				</tr>
				<tr>
					<td colspan="5" id="textTd">
					
						<div id="fwmouse">
						</div>
					
					</td>
				</tr>
			</table>
		</div>
		<div title="�ɼ���״̬�澯" style="padding:10px">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="small_table">
				<tr>
					<th class="th_w">
						δ�����¼��б�&nbsp;[��
						<div id="mcaDiv" style="display: inline; color: red;">
							0
						</div>
						��] &nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="minor" name="mca" value="1" checked
							onclick="getOneTypeNewEventByClass('mca');">
						<font color='#C8FF00'>֪ͨ</font>
						<input type="checkbox" id="warning" name="mca" value="2"
							checked onclick="getOneTypeNewEventByClass('mca');">
						<font color='#FFD200'>��΢</font>
						<input type="checkbox" id="major" name="mca" value="3" checked
							onclick="getOneTypeNewEventByClass('mca');">
						<font color='#FF9200'>��Ҫ</font>
						<input type="checkbox" id="critical" name="mca" value="4"
							checked onclick="getOneTypeNewEventByClass('mca');">
						<font color='#FF1042'>����</font>
					</th>
				</tr>
				<tr>
					<td colspan="5" id="textTd">
					
						<div id="mcamouse">
						</div>
				
					</td>
				</tr>
			</table>
		</div>
		<div title="���Ը澯" style="padding:10px">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="small_table">
				<tr>
					<th class="th_w">
						δ�����¼��б�&nbsp;[��
						<div id="ployDiv" style="display: inline; color: red;">
							0
						</div>
						��] &nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" id="minor" name="ploy" value="1" checked
							onclick="getOneTypeNewEventByClass('policy');">
						<font color='#C8FF00'>֪ͨ</font>
						<input type="checkbox" id="warning" name="ploy" value="2"
							checked onclick="getOneTypeNewEventByClass('policy');">
						<font color='#FFD200'>��΢</font>
						<input type="checkbox" id="major" name="ploy" value="3" checked
							onclick="getOneTypeNewEventByClass('policy');">
						<font color='#FF9200'>��Ҫ</font>
						<input type="checkbox" id="critical" name="ploy" value="4"
							checked onclick="getOneTypeNewEventByClass('policy');">
						<font color='#FF1042'>����</font>
					</th>
				</tr>
				<tr>
					<td colspan="5" id="textTd">
					
						<div id="ploymouse">
						</div>
					
					</td>
				</tr>
			</table>
		</div>
	</div>
<script type="text/javascript">
$('#eventtabs').tabs({ 

	border:true, 

	onSelect:function(title){ 

		if(title=="ȫ���澯"){
			getEventList();
			if(timer1!=null){
				clearInterval( timer1 );
			}
			timer1=setInterval('getEventList()',60000);
		}else if(title=="���߸澯"){
			getOneTypeEventListByLevel("baseline");
			if(timer1!=null){
				clearInterval( timer1 );
			}
			timer1=setInterval('getOneTypeEventListByLevel("baseline")',60000);
		}else if(title=="����ǽ״̬�澯"){
			getOneTypeEventListByLevel("fw");
			if(timer1!=null){
				clearInterval( timer1 );
			}
			timer1=setInterval('getOneTypeEventListByLevel("fw")',60000);
		}else if(title=="�ɼ���״̬�澯"){
			getOneTypeEventListByLevel("mca");
			if(timer1!=null){
				clearInterval( timer1 );
			}
			timer1=setInterval('getOneTypeEventListByLevel("mca")',60000);
		}else if(title=="���Ը澯"){
			getOneTypeEventListByLevel("policy");
			if(timer1!=null){
				clearInterval( timer1 );
			}
			timer1=setInterval('getOneTypeEventListByLevel("ploy")',60000);
		}

	} 

}); 


//ȫ���澯
var marquee2 = new Marquee("textnomouse");
marquee2.Direction="top";
marquee2.Step=1;
marquee2.Width=1000;
marquee2.Height=300;
marquee2.Timer=300;
marquee2.Start();
//���߸澯
var baseline = new Marquee("baselinemouse");
baseline.Direction="top";
baseline.Step=1;
baseline.Width=1000;
baseline.Height=300;
baseline.Timer=300;
baseline.Start();
//����ǽ״̬�澯
var fw = new Marquee("fwmouse");
fw.Direction="top";
fw.Step=1;
fw.Width=1000;
fw.Height=300;
fw.Timer=300;
fw.Start();
//�ɼ���״̬�澯
var mca = new Marquee("mcamouse");
mca.Direction="top";
mca.Step=1;
mca.Width=1000;
mca.Height=300;
mca.Timer=300;
mca.Start();
//���Ը澯
var ploy = new Marquee("ploymouse");
ploy.Direction="top";
ploy.Step=1;
ploy.Width=1000;
ploy.Height=300;
ploy.Timer=300;
ploy.Start();

</script>

	</body>
</html>