<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript" src="js/resultJS/staff.js"></script>

  <style type="text/css">
   /* #container {
        padding-top:5px;
    }
    #container .x-panel {
    }
    #container .x-panel-ml {
        padding-left:1px;
    }
    #container .x-panel-mr {
        padding-right:1px;
    }
    #container .x-panel-bl {
        padding-left:2px;
    }

    #container .x-panel-br {
        padding-right:2px;
    }
    #container .x-panel-body {

    }
    #container .x-panel-mc {
        padding-top:0;
    }
    #container .x-panel-bc .x-panel-footer {
        padding-bottom:2px;
    }
    #container .x-panel-nofooter .x-panel-bc {
        height:2px;
    }
    #container .x-toolbar {
        border:1px solid #99BBE8;
        border-width: 0 0 1px 0;
    }
    .chart {
      /**
        *background-image: url(chart.gif) !important;
        */
    }*/
    </style>

    <div id="bodysGyxStaff"></div>
  	<div id="tableGyxStaff"></div>
  	<div id="containerStaff"></div>

