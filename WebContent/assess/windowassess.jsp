<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="../inc/comm.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>´°¿Ú¿¼ºË</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
 	<script type="text/javascript" src="assess/windowAs.js"></script> 
 	
    <style type="text/css">
	    #leftTree .x-layout-collapsed-east{
	      background-image : url(image/winNav.PNG);
		  background-repeat : no-repeat;
		  background-position :center;
	    }
	    .highlight .x-tree-node-anchor span {
	      color : yellow !important;
	      background-color :red !important;
	    }
    </style>
  </head>
  <body>
    <div id="windowassess-div"></div>
  </body>
</html>
