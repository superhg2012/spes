<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>
<html>
	<head>
		<title>�û�ע��</title>

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

//��ȡ������Ϣ
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
	//�����˵�ѡ�񴰿�
	var combox2 = new Ext.form.ComboBox({
	    width :200,
		mode : 'local',
		fieldLabel : '��������',
		store : comboStore,
		triggerAction : 'all',
		displayField : 'text',
		valueField : 'value',
		emptyText : '----��ѡ����������----',
		allowBlank : false,
		blankText : '��ѡ������,����Ϊ��',
		name:'centerName'
	});
	
	comboStore.load();
	var IsExist;//�û��Ƿ��Ѵ���
	var name = new Ext.form.TextField( {
		id:'txtusername',
		name : 'userName',
		fieldLabel : '�û���',
		allowBlank : false,
		blankText : '�������½�û���,����Ϊ��',
		invalidText:'���û��Ѿ����ڣ�����������',
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
		fieldLabel : '����',
		allowBlank : false,
		blankText : '����������,����Ϊ��',
		inputType : 'password',
		width : 300
	//	height:28
	});
	
	var realName = new Ext.form.TextField({
		name : 'realName',
		fieldLabel : '��ʵ����',
		allowBlank : false,
		blankText : '�������û���ʵ����,����Ϊ��',
		width : 300
	//	height:28
	});
	
	
	var idcard = new Ext.form.TextField( {
		fieldLabel : '���֤��',
		name : 'idCardNum',
		vtype : 'alphanum',
		width : 300,
		allowBlank : false,
		blankText : '���������֤��,����Ϊ��'
	//	height:28
	});
	
	var man = new Ext.form.Radio( {
		name : 'gender',
		checked : true,
		boxLabel : '��',
		inputValue : '��'
	});

	var girl = new Ext.form.Radio( {
		name : 'gender',
		boxLabel : 'Ů',
		inputValue :'Ů'
	});

	var group = new Ext.form.RadioGroup( {
		fieldLabel : '�Ա�',
		name : 'gender',
		items : [ man, girl ],
		width : 300
	});

	var email = new Ext.form.TextField( {
		fieldLabel : '����',
		name : 'email',
		vtype : 'email',
		vtypeText : '��������ȷ�������ʽ',
		width : 300,
		allowBlank : false,
		blankText : '����������,����Ϊ��'
	//	height:28
	});
	
	var contact = new Ext.form.TextField( {
		fieldLabel : '��ϵ�绰',
		allowBlank : false,
		blankText : '��������ϵ�绰,����Ϊ��',
		name : 'contact',
		width : 300
	//	height:28
	});
	
	var address = new Ext.form.TextField( {
		fieldLabel : '��ϵ��ַ',
		name : 'address',
		width : 300
	//	height:28
	});
	
	var html = new Ext.form.TextArea( {
		fieldLabel : '����',
		name : 'remarks',
		maxLength:100,
		width:300
	//	height:80
	});
	
	var subbutton = new Ext.Button( {
		text : 'ע���û�',
		icon :'image/icon/accept.png',
		handler:function(){ 
			form1.getForm().submit({
                   url:'userAction!addUser.action',
                   method:'POST',
                   success: function(form, action){
                     Ext.Msg.alert("��ʾ��Ϣ","�û�ע��ɹ�!��ת����½����");
                     window.location.replace("login.jsp");
                   },
                   failure: function(form, action){
                       Ext.Msg.alert("��ʾ��Ϣ","�û�ע��ʧ��");
                       return;
                   }
		    });
		}
	});
	
	

	var reset = new Ext.Button( {
		text : 'ȡ��ע��',
		icon : 'image/icon/arrow_undo.png',
		handler : function() {
			form1.getForm().reset();
		}
	});

	var back = new Ext.Button({
		text:'�ص���½����',
		icon: 'image/icon/house_link.png',
		handler:function(){
		   window.history.go(-1);
		}
	});
	
	
	var fieldset1 = new Ext.form.FieldSet({
		title : '��½��Ϣ',
		height:100,
		layout:'form',
		items:[name,password]
	});
	
	
	var fieldset2 = new Ext.form.FieldSet({
		title : '�û�������Ϣ',
		height:160,
		layout:'form',
		items:[realName,idcard, email, combox2, group]
	});
	
	var fieldset3 = new Ext.form.FieldSet({
		title : '��ϵ��Ϣ',
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
		title : "�û�ע��"
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




