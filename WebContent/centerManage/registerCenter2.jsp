<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>centerRegister</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="<%=basePath%>ext-3.0.0/adapter/ext/ext-base.js"></script>
    	<script type="text/javascript" src="<%=basePath%>ext-3.0.0/ext-all-debug.js"></script>
    	
    	<link rel="stylesheet" type="text/css" href="<%=basePath%>ext-3.0.0/resources/css/ext-all.css"></link>
    	
        <script type="text/javascript">
	Ext.onReady(function() {
		
		/*省份数据源*/
		var storeProvince = new Ext.data.Store({   		 //省名下拉框 数据
	        proxy: new Ext.data.HttpProxy({        
	             url: 'centermg/centerRegisterAction!getAllProvince.action',//通action获取所有的省份 的列表
	        	 method : 'GET'
	        	 }),        
	        reader: new Ext.data.JsonReader({        
		         root: "root",
	             fields :['code','name']
        	}) 
        });
        storeProvince.load();

    var form = new Ext.form.FormPanel({
       title : "中心注册",
       width : '100%',
       autoHeight : true,
       frame : true,
       renderTo : "a",
      // bodyStyle: 'padding:15px',
       labelWidth : 65,
       labelAlign : "right",
     //  url:'centermg/centerRegisterAction!addCenterServiceRegister.action',
       items :[
         		{
	           		id:'comb1',
	           		xtype : "combo",
	           		name:'province',
	           		fieldLabel : "省份",
	           		triggerAction:'all',
	           		valueField:'code',
	           		displayField:'name',
	           		store:storeProvince,
	           		mode:"local",	
	           		width : 200
           		}
       ]
      });
      
      //form.render();
      
});
		
        </script>
   </head>
  
  <body>
  <div id="a"></div>
  </body>
</html>
