Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	/*
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
		name : 'centerName',
		allowBlank :false,
		blankText : "��ѡ������,����Ϊ��"
	});
	comboStore.load();
	*/
	/*
	var windowStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'sys/windowAction!getWindowByCenterId.action?centerId=' + Ext.get("centerId").dom.value,
			method : 'POST'
		}),
		reader : new Ext.data.JsonReader({
			root : 'root',
			fields : [{name : 'value',mapping:'windowId'},
				      {name : 'text', mapping : 'windowName'}]
		})
	});
	//�����˵�ѡ�񴰿�
	var windowcombox = new Ext.form.ComboBox({
	    width :200,
		mode : 'local',
		hideMode:'visibility',
		hidden :true,
		fieldLabel : '��������',
		store : windowStore,
		triggerAction : 'all',
		displayField : 'text',
		valueField : 'value',
		emptyText : '----��ѡ����������----'
	});
	*/
	
	var IsExist;//�û��Ƿ��Ѵ���
	var name = new Ext.form.TextField( {
		id:'txtusername',
		name : 'userName',
		fieldLabel : '�û���',
		allowBlank : false,
		blankText : '�������½�û���,����Ϊ��',
		invalidText:'���û��Ѿ����ڣ�����������',
		width : 300,
//		validateOnBlur : false,
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
		maxLength:18,
		miniLength:15,
		allowBlank : false,
		blankText : '�������û����֤��,����Ϊ��',
		width : 300
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

	/**
	var checkWindow = new Ext.form.Checkbox({
		fieldLabel:'�Ƿ����ڴ���',
		name : 'cc',
		width : 100,
		listeners : {
		check : function(checkWindow, checked) {
		   if(checked){
			   windowcombox.setVisible(true);
			   windowStore.load();
		   } else{
			   windowcombox.hide();
		   }
		}
	   }
	});
	*/
	var email = new Ext.form.TextField( {
		fieldLabel : '����',
		name : 'email',
		vtype : 'email',
		vtypeText : '��������ȷ�������ʽ',
		allowBlank:false,
		blankText : '�������û�����,����Ϊ��',
		width : 300
	//	height:28
	});
	
	var contact = new Ext.form.TextField( {
		fieldLabel : '��ϵ�绰',
		name : 'contact',
		width : 300
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
			if(form1.getForm().isValid()){
				form1.getForm().submit({
	                url:'userAction!addUser2.action',
	                method:'POST',
	                success: function(form, action){
	                  Ext.MessageBox.alert("��ʾ","�û�ע��ɹ�!");
	                  form1.getForm().reset();
	                  return;
	                },
	                failure: function(form, action){
	                    Ext.MessageBox.alert("��ʾ","�û�ע��ʧ��!");
	                    return;
	                }
			    });
			}
		}
	});

	var reset = new Ext.Button( {
		text : 'ȡ��ע��',
		icon : 'image/icon/arrow_undo.png',
		handler : function() {
			form1.getForm().reset();
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
		height: 160,
		layout:'form',
		items:[realName,idcard, email,group]
	});
	
	var fieldset3 = new Ext.form.FieldSet({
		title : '��ϵ��Ϣ',
		height : 160,
		layout:'form',
		items :[contact,address, html]
	});
	
	
	var form1 = new Ext.form.FormPanel( {
	//	items : [ name, password,realName,idcard, email, group, contact,address, html ],
	    items : [fieldset1,fieldset2,fieldset3],
		bbar : ['&nbsp',subbutton,'-', reset ],
		width : '100%',
		authHeight : true,
		labelWidth : 120,
		labelAlign : 'right',                   
		layout : 'form',
		renderTo : "addUser-div",
		frame :true,
		border:false,
		title : '�û�ע����Ϣ'
	});
});