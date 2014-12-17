<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
	<script type="text/javascript"	src="framework/ExtJSUtil.js"></script>
	<script type="text/javascript" src="assess/EvaluateSheetWindow.js"></script>
    <script type="text/javascript" src="assess/centerSheet.js"></script>
    <script type="text/javascript">
	// Update status bar with given msg
	function updateStatusBar(msg, error) {
		if(error) {
			if (document.getElementById('alert-error-msg') != null) {
				document.getElementById('alert-error-msg').innerHTML = msg;
				$('div#alert-error-block').slideDown(300);
				document.getElementById('status-bar').innerHTML = 'Error';
			}
		}
		else {
			$('div#alert-error-block').hide();
			document.getElementById('status-bar').innerHTML = msg;
		}
	}
</script>
  </head>
  <body>
	<div id="alert-error-block" class="alert alert-block alert-error" style="display:none">
	    <h4 class="alert-heading" style="color:#727272;margin-bottom:3px;font-size:17px">Oops, an error has occured :(</h4>
	    <span id="alert-error-msg"></span>
    </div>
  	<div id="centerSheetList" style="width:100%;height:100%"></div>
  </body>
</html>

