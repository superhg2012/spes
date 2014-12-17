<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'combox.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
    <script type="text/javascript">

		Ext.onReady(function(){

			var companyInfo = {
				title:'Fans Info Corp.',
				phone:'02-1234567',
				fax:'02-5678912',
				address:'Somewhere out there',
				employees:[
					{ id:1, name:'Aitch', age:28 },
					{ id:2, name:'Mary',  age:27 },
					{ id:3, name:'David', age:25 }
				]
			}
			
			var comboStore = new Ext.data.JsonStore({
				root:'employees',
				fields:[
				   'id', 'name', 'age'
				],
				data:companyInfo
			});
			
			var employees = {
				xtype:'combo',
				fieldLabel:'Employee',
				store:comboStore,
				displayField:'name',
				typeAhead:false,
				forceSelection:true,
				mode:'local'
			}

			var form = new Ext.form.FormPanel({
				title:'FormPanel',
				width:280,
				height:100,
				frame:true,
				labelWidth:55,
				items:[
					employees
				]
			});
			
			form.render('container');
		});
        
    </script>	
	
  </head>
  
 <body style="padding:10px;">
 	<div id="container"></div>
 </body>
</html>
