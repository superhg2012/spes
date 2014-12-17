<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String username = (String)session.getAttribute("userName");
String roleId = (String)session.getAttribute("roleId");
String roleName = (String) session.getAttribute("roleName");
String centerId = (String) session.getAttribute("centerId");
String centerName = (String) session.getAttribute("centerName");

if(username==null) {
	response.sendRedirect("login.jsp");
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>政务服务中心绩效管理系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="ext-3.0.0/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="ext-3.0.0/ext-all.js"></script>
	<script type="text/javascript" src="ext-3.0.0/ext-all-debug.js"></script>
	<link rel="stylesheet" type="text/css"	href="ext-3.0.0/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css"	href="skin/blue/style/bms.css" />
	<script type="text/javascript"	src="framework/TabCloseMenu.js"></script>
	<script type="text/javascript"	src="framework/LeftTreePanel.js"></script>
	<script type="text/javascript"	src="framework/DocPanel.js"></script>
	<script type="text/javascript"	src="framework/MainPanel.js"></script>
	<script type="text/javascript"	src="framework/tree.js"></script>
	<script type="text/javascript"	src="framework/frameLayout.js"></script>
	
	
	<script src="js/jquery-1.7.1.js" type="text/javascript"></script>
	<script src="js/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
    <script src="js/jquery.tokeninput.js" type="text/javascript"></script>
	
	<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
	<script src="js/jquery.ui.widget.js"></script>
	<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
	<script src="js/jquery.iframe-transport.js"></script>
	<!-- The basic File Upload plugin -->
	<script src="js/jquery.fileupload.js"></script>
	<!-- The JQuery File Download plugin -->
	<script src="js/jquery.fileDownload.js"></script>
	
	<style type="text/css">
	
	#ext-gen42{
	  font-size : 30px;
	} 
	
	#menubar .x-btn button{
	  font-size:14px;
	  font-weight:bold;
	  color:#1C3E7D;
	}
	
	
	.toolbarCls{
	  font-size:30;
	}
	
	#ext-gen61{
	  font-size : 10px;
	} 
	
	#ext-gen66{
	  font-size : 10px;
	} 
	
	
	#ext-gen71{
	  font-size : 10px;
	} 
	
	
	#ext-gen76{
	  font-size : 10px;
	} 
	
	
	#ext-gen81{
	  font-size : 10px;
	} 
	
	#ext-gen86{
	  font-size : 10px;
	} 
	
	#ext-gen91{
	  font-size : 10px;
	} 
	
	.x-layout-collapsed-west {
	  background-image : url(image/nav.PNG);
	  background-repeat : no-repeat;
	  background-position :center;
	}
	.zbsj {
	  background-image : url(image/icon/house.png);
	}
	
	.zxsj {
	 background-image : url(image/icon/package_green.png);
	}
	.ckkh {
	 background-image : url(image/icon/window.png) !important;
    }
    
    .khjgzs {
    	background-image : url(image/icon/chart_pie.png) !important;
    }
    
    .zxkh {
     background-image : url(image/icon/bricks.png) !important;
    }
    
	.ckptrykh{
	  background-image : url(image/icon/user_suit.png);
	}
	
	.xtgg{
	  background-image : url(image/icon/email.png);
	}
	
	.ggck {
	  background-image : url(image/icon/email_open.png);
	}
	
	.wdhf{
	  background-image : url(image/icon/comment.png);
	}
	
	.fwwd{
	  background-image : url(image/icon/user_comment.png);
	}
	
	.tjyh {
	  background-image : url(image/icon/user_add.png);
	}
	
	.xgyh {
	  background-image : url(image/icon/user_edit.png);
	}
	
	.zxyh {
	  background-image : url(image/icon/user_delete.png);
	}
	
	.yhsh {
	    background-image : url(image/icon/group_key.png);
	}
	
	.yhzx {
	    background-image : url(image/icon/user_suit.png);
	}
	
	.jsgl {
	    background-image : url(image/icon/user_green.png);
	}
	
	.qxfp {
	    background-image : url(image/icon/group_key.png);
	}
	
	.icon-expand-all {
        background-image: url(css/expand-all.gif) !important;
    }
    
	.icon-collapse-all {
	    background-image: url(css/collapse-all.gif) !important;
	}
	
	.zxzc{
	  background-image : url(image/icon/image_add.png);
	}
	
	.zxsh{
	  background-image : url(image/icon/lock_open.png);
	}
	
	.zxxg {
	  background-image : url(image/icon/image_edit.png);
	}
	
	.mmxg{
	  background-image : url(image/icon/lock.png);
	}
	.zxzb{
	  background-image : url(image/icon/application_view_icons.png);
	}
	.ckzb{
	  background-image : url(image/icon/application_view_icons.png);
	}
	.ckptryzb{
	  background-image : url(image/icon/application_view_icons.png);
	}
	
	.cksz{
	  background-image : url(image/icon/window.png);
	}
	.grxx{
	  background-image : url(image/icon/user_edit.png);
	}
	
	</style>
	
	<script type="text/javascript">
	
	function buildNav(){
		var id = document.getElementById("roleName").value;
	}
	</script>
	
  </head>
  
  <body id="docs">
  <div id="loading-mask" style=""></div>
  <div id="loading">
    <div class="loading-indicator">Loading...</div>
  </div>
  <div id="header" style="background-color : #D2E0F0">
	<!--   <div class="api-title">行政服务中心绩效评价系统</div> -->
  </div>

  <div id="classes"></div>
  <div id="hello-win"></div>
  <div id="main"></div>
  </body>
  <input id="userId" type="hidden" value="<%=username%>"/>
  <input id="roleId" type="hidden" value="<%=roleId%>" />
  <input id="roleName" type="hidden" value="<%=roleName%>" />
  <input id="centerId" type="hidden" value="<%=centerId%>" />
  <input id="centerName" type="hidden" value="<%=centerName %>" />
</html>
