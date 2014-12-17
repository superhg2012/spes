<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>”√ªß…Û∫À</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/userCheck.js"> </script>
	<style type="text/css">
	  	span.checked_span {
			background: url(centerManage/checked.png) no-repeat right;
			width: 16px;
			line-height: 16px;
			padding-left: 10px;
		}
	
		span.unchecked_span {
			background: url(centerManage/unchecked.png) no-repeat right;
			width: 16px;
			line-height: 16px;
			padding-left: 10px;
		}
  	</style>
  </head>
  
  <body>
  <div id="userCheck-div"></div>
  </body>
</html>
