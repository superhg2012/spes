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
    
    <title>´°¿ÚÔ±¹¤¿¼ºË</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/PublicFunctions.js"> </script>
	<script type="text/javascript" src="assess/staffAs.js"></script>
	<style type="text/css">
	 .highlight .x-tree-node-anchor span {
	      color : yellow !important;
	      background-color :red !important;
	    }
	  #staff-div {
	    border :solid 1px red;
	    with : 100%;
	    height : 100%;
	  }
	</style>
  </head>
  
  <body>
  	<div id="staff-div"></div>
  </body>
</html>
