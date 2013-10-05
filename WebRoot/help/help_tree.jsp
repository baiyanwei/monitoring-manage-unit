<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String [] company_trees=new String [8];
	company_trees[0]="\"1|0| 帮助|#|help.png\"";
	company_trees[1]="\"2|1| 系统说明|help_description.jsp|question.gif\"";
	company_trees[2]="\"3|2| 版本信息|help_description.jsp|question.gif\"";
	company_trees[3]="\"4|2| 升级说明|help_description.jsp|question.gif\"";
	company_trees[4]="\"5|1| 服务支持|help_description.jsp|question.gif\"";
	company_trees[5]="\"6|5| 联系方式|help_description.jsp|question.gif\"";
	company_trees[6]="\"7|1| 常见问题|help_description.jsp|question.gif\"";
	company_trees[7]="\"8|1| 诉求流程图|help_flow_image.jsp|question.gif\"";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;   charset=utf-8">
		<title></title>
		<link href="../style/blue/css/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../js/common_tree.js"></script>
		<link rel="stylesheet" type="text/css" href="../style/app/css/tree.css">
	</head>
	<body>

		<div class="treein">
			<script type="text/javascript">
					<!--
							setIcoPath('../style/app/images/');
							var Tree1 = new Array;
							<%
							if(company_trees!=null){
								for(int i=0;i<company_trees.length;i++){
									out.println("Tree1["+i+"]="+company_trees[i]);
								}
							}
							%>
							createTree(Tree1,1)
					//-->
				</script>
		</div>

	</body>

</html>