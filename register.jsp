<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>
<html>
	<head>
		<title>用户注册</title>

		<meta http-equiv='keywords' content='keyword1,keyword2,keyword3'>
		<meta http-equiv='description' content='this is my page'>
		<meta http-equiv='content-type' content='text/html; charset=UTF-8'>
		<script type="text/javascript" src="ext-3.0.0/adapter/ext/ext-base.js"></script>
	    <script type="text/javascript" src="ext-3.0.0/ext-all.js"></script>
	    <link rel="stylesheet"  type="text/css" href="ext-3.0.0/resources/css/ext-all.css" />
		<script type="text/javascript">

Ext.onReady(function() {

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

//获取窗口信息
	var comboStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'sys/centerAction!getServiceCenter.action',
			method : 'POST'
		}),
		reader : new Ext.data.JsonReader({
			root : 'root',
			fields : [{name : 'value',mapping:'centerId'},
				      {name : 'text', mapping : 'centerName'}]
		})
	});
	//下拉菜单选择窗口
	var combox2 = new Ext.form.ComboBox({
	    width :200,
		mode : 'local',
		fieldLabel : '所属中心',
		store : comboStore,
		triggerAction : 'all',
		displayField : 'text',
		valueField : 'value',
		emptyText : '----请选择所属中心----',
		allowBlank : false,
		blankText : '请选择中心,不能为空',
		name:'centerName'
	});
	
	comboStore.load();
	var IsExist;//用户是否已存在
	var name = new Ext.form.TextField( {
		id:'txtusername',
		name : 'userName',
		fieldLabel : '用户名',
		allowBlank : false,
		blankText : '请输入登陆用户名,不能为空',
		invalidText:'该用户已经存在，请重新输入',
		width : 300,
		validator : function(){
			Ext.Ajax.request({
			    	url : 'userAction!checkUserExist.action',
			    	params : {userName:Ext.getCmp("txtusername").getValue()},
			    	scope : true,
			        callback : function(options,success,response){
			    	  if(success) {
			    		  var data = eval(response.responseText);
			    		  if(response.responseText=="true"){
			    		     returnValue(false);
			    		  } else if(response.responseText=="false"){
			    		     returnValue(true);
			    		  }
			    	  }
			       }
			});
			
			function returnValue(ok){
				IsExist = ok;
			}
			return IsExist;
		}
	//	height:28
	});

	var password = new Ext.form.TextField( {
		name : 'userPass',
		fieldLabel : '密码',
		allowBlank : false,
		blankText : '请输入密码,不能为空',
		inputType : 'password',
		width : 300
	//	height:28
	});
	
	var realName = new Ext.form.TextField({
		name : 'realName',
		fieldLabel : '真实姓名',
		allowBlank : false,
		blankText : '请输入用户真实姓名,不能为空',
		width : 300
	//	height:28
	});
	
	
	var idcard = new Ext.form.TextField( {
		fieldLabel : '身份证号',
		name : 'idCardNum',
		vtype : 'alphanum',
		width : 300,
		allowBlank : false,
		blankText : '请输入身份证号,不能为空'
	//	height:28
	});
	
	var man = new Ext.form.Radio( {
		name : 'gender',
		checked : true,
		boxLabel : '男',
		inputValue : '男'
	});

	var girl = new Ext.form.Radio( {
		name : 'gender',
		boxLabel : '女',
		inputValue :'女'
	});

	var group = new Ext.form.RadioGroup( {
		fieldLabel : '性别',
		name : 'gender',
		items : [ man, girl ],
		width : 300
	});

	var email = new Ext.form.TextField( {
		fieldLabel : '邮箱',
		name : 'email',
		vtype : 'email',
		vtypeText : '请输入正确的邮箱格式',
		width : 300,
		allowBlank : false,
		blankText : '请输入邮箱,不能为空'
	//	height:28
	});
	
	var contact = new Ext.form.TextField( {
		fieldLabel : '联系电话',
		allowBlank : false,
		blankText : '请输入联系电话,不能为空',
		name : 'contact',
		width : 300
	//	height:28
	});
	
	var address = new Ext.form.TextField( {
		fieldLabel : '联系地址',
		name : 'address',
		width : 300
	//	height:28
	});
	
	var html = new Ext.form.TextArea( {
		fieldLabel : '其他',
		name : 'remarks',
		maxLength:100,
		width:300
	//	height:80
	});
	
	var subbutton = new Ext.Button( {
		text : '注册用户',
		icon :'image/icon/accept.png',
		handler:function(){ 
			form1.getForm().submit({
                   url:'userAction!addUser.action',
                   method:'POST',
                   success: function(form, action){
                     Ext.Msg.alert("提示信息","用户注册成功!正转到登陆界面");
                     window.location.replace("login.jsp");
                   },
                   failure: function(form, action){
                       Ext.Msg.alert("提示信息","用户注册失败");
                       return;
                   }
		    });
		}
	});
	
	

	var reset = new Ext.Button( {
		text : '取消注册',
		icon : 'image/icon/arrow_undo.png',
		handler : function() {
			form1.getForm().reset();
		}
	});

	var back = new Ext.Button({
		text:'回到登陆界面',
		icon: 'image/icon/house_link.png',
		handler:function(){
		   window.history.go(-1);
		}
	});
	
	
	var fieldset1 = new Ext.form.FieldSet({
		title : '登陆信息',
		height:100,
		layout:'form',
		items:[name,password]
	});
	
	
	var fieldset2 = new Ext.form.FieldSet({
		title : '用户基本信息',
		height:160,
		layout:'form',
		items:[realName,idcard, email, combox2, group]
	});
	
	var fieldset3 = new Ext.form.FieldSet({
		title : '联系信息',
		height : 180,
		layout:'form',
		items :[contact,address, html ],
		bodyStyle:{
		 //  textAlign:'center',
		   //labelAlign:'center'
		}
	});
	
	
	var form1 = new Ext.form.FormPanel( {
	//	items : [ name, password,realName,idcard, email, group, contact,address, html ],
	    items : [fieldset1,fieldset2,fieldset3],
		bbar : ['&nbsp',subbutton,'-', reset,'-',back],
		width : '90%',
		authHeight : true,
		labelWidth : 120,
		labelAlign : 'right',                   
		layout:'form',
		renderTo : Ext.getBody(),
		frame :true,
		border:false,
		title : "用户注册"
	});
});
   

</script>
<style type="text/css">
body{
  margins:'5,5,5,5'
}
</style>
	</head>
	<body>
	</body>

</html>




