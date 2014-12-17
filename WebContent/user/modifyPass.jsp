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
    <title>用户密码修改</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<script type="text/javascript">
 Ext.onReady(function(){
	 Ext.QuickTips.init();
	 Ext.form.Field.prototype.msgTarget = 'side';
	 
     Ext.apply(Ext.form.VTypes,{
	   password : function(val, field) {
		 if(field.confirmTo){
			 var pwd = Ext.getCmp(field.confirmTo);
			 return (val == pwd.getValue().trim());
		 }
		 return true;
	   },
	    passwordText:'两次密码不一致'
     });

var passForm = new Ext.FormPanel({
	width : 400,
	height : 240,
	frame : true,
	title : '用户密码修改',
	iconCls: 'passform-title',
	labelAlign : 'right',
	labelWidth : 60,
	layout : 'form',
  	labelPad : 8,	
	bodyStyle : 'padding : 20 5 10 5',
	defaultType:'textfield',
	renderTo :'form-div',
	items :[{
		id:'txtPass',
		name:'initPass',
		allowBlank : false,
		fieldLabel :'初始密码',
		blankText : '原始密码不能为空!',
		inputType : 'password',
		width : 260
	},{
	  id : 'p_newPass',
	  name : 'newPass',
	  fieldLabel : '新密码',
	  allowBlank : false,
	  inputType : 'password',
	  blankText : '新密码不能为空!',
	  minLength : 4,
	  minLengthText:'密码不能少于4位',  
      maxLength :8,  
      maxLengthText:'密码不能超过8位',
      width : 260
	},{
		id:'p_confrimPass',
		fieldLabel : '确认密码',
		allowBlank : false,
		blankText :'两次密码不一致',
		name : 'confirmPass',
		vtype : 'password', 
		inputType : 'password',
		confirmTo : 'p_newPass',
		minLength : 4,
	    minLengthText:'密码不能少于4位',  
        maxLength :8,  
        maxLengthText:'密码不能超过8位',
		width : 260
	}],
	buttons: [{
             text :'提 交',
             icon :'image/icon/accept.png',
             handler : function(){
			      if(!passForm.form.isValid()){
			    	  return;
			      }
			   	  passForm.form.submit({
			   		   url : 'pwdAction!changePassword.action',
			   		   method : 'post',
			   		   success : function(form, response){
				   			  alert(response.result.resp);
				   			  window.location.replace("./login.jsp");
				   	   },
				   	   failure:function(form,response){
				   			  Ext.Msg.alert(response.result.resp);
				   			  return;
				   	   }
			   	   });//end submit
             }//end hander
         },{
         	text : '重 置',
         	icon : 'image/icon/arrow_undo.png',
         	handler : function(){
         	  passForm.form.reset();
         	}
         }]
  });//end form
});
</script>
<style type="text/css">
 .passform-title {
   background-image : url(image/icon/lock_open.png) !important;
 }
</style>
  </head>
  <body>
    <center>
      <div id="form-div" style="padding-top:100px"></div>
    </center>
  </body>
</html>
