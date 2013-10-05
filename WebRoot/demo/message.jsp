<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" href="css/cupertino/jquery-ui-1.8.23.custom.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.23.custom.min.js"></script>
<title></title>
<script type="text/javascript">
	function doClose() {
		alert('>>>>>>>>');
	}
</script>
</head>
<body>
	<div class="ui-widget" style="padding: 100px 0.25em 2ex 10px;width:800px;height:400px">
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
			<p>
				<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span> <strong>Hey!</strong> Sample ui-state-highlight style.
			</p>
			<input name="close" value="close" type="button" onclick="doClose()" class="ui-state-default ui-corner-all"/>
		</div>
	</div>
</body>
</html>