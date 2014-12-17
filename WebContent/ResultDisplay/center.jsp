<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <title>dddd</title>
    <script type="text/javascript" src="js/resultJS/ParameterWindow.js"></script>
     <script type="text/javascript" src="js/resultJS/ManageSheet.js"></script>
     <script type="text/javascript" src="js/resultJS/center1.js"></script>

<style type="text/css">
   #containerCen {
       padding:10px;
   }
   #containerCen .x-panel {
       margin:10px;
   }
   #containerCen .x-panel-ml {
       padding-left:1px;
   }
   #containerCen .x-panel-mr {
       padding-right:1px;
   }
   #containerCen .x-panel-bl {
       padding-left:2px;
   }

   #containerCen .x-panel-br {
       padding-right:2px;
   }
   #containerCen .x-panel-body {

   }
   #containerCen .x-panel-mc {
       padding-top:0;
   }
   #containerCen .x-panel-bc .x-panel-footer {
       padding-bottom:2px;
   }
   #containerCen .x-panel-nofooter .x-panel-bc {
       height:2px;
   }
   #containerCen .x-toolbar {
       border:1px solid #99BBE8;
       border-width: 0 0 1px 0;
   }
   .chart {
     /*
     *  background-image: url(chart.gif) !important;
     */
   }
   .iconChart {
     background-image : url(image/icon/chart_bar.png)!important;
   }
  </style>
  </head>
  <body>
    <div id="centerTopContianer"> 
    	<div id="centerSheetListDiv"></div>
    	<div id="displayDiv"></div>	
    </div>
    <div id="centerMainContainer" style="display:none;">
		<div id="bodysGyx"></div>
		<div id="tableGyx"></div>
		<div id="container"></div>
	</div>
  </body>
</html>

