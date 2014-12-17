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
		name : 'centerName',
		allowBlank :false,
		blankText : "请选择中心,不能为空"
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
	//下拉菜单选择窗口
	var windowcombox = new Ext.form.ComboBox({
	    width :200,
		mode : 'local',
		hideMode:'visibility',
		hidden :true,
		fieldLabel : '所属窗口',
		store : windowStore,
		triggerAction : 'all',
		displayField : 'text',
		valueField : 'value',
		emptyText : '----请选择所属窗口----'
	});
	*/
	
	var IsExist;//用户是否已存在
	var name = new Ext.form.TextField( {
		id:'txtusername',
		name : 'userName',
		fieldLabel : '用户名',
		allowBlank : false,
		blankText : '请输入登陆用户名,不能为空',
		invalidText:'该用户已经存在，请重新输入',
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
		maxLength:18,
		miniLength:15,
		allowBlank : false,
		blankText : '请输入用户身份证号,不能为空',
		width : 300
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

	/**
	var checkWindow = new Ext.form.Checkbox({
		fieldLabel:'是否属于窗口',
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
		fieldLabel : '邮箱',
		name : 'email',
		vtype : 'email',
		vtypeText : '请输入正确的邮箱格式',
		allowBlank:false,
		blankText : '请输入用户邮箱,不能为空',
		width : 300
	//	height:28
	});
	
	var contact = new Ext.form.TextField( {
		fieldLabel : '联系电话',
		name : 'contact',
		width : 300
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
			if(form1.getForm().isValid()){
				form1.getForm().submit({
	                url:'userAction!addUser2.action',
	                method:'POST',
	                success: function(form, action){
	                  Ext.MessageBox.alert("提示","用户注册成功!");
	                  form1.getForm().reset();
	                  return;
	                },
	                failure: function(form, action){
	                    Ext.MessageBox.alert("提示","用户注册失败!");
	                    return;
	                }
			    });
			}
		}
	});

	var reset = new Ext.Button( {
		text : '取消注册',
		icon : 'image/icon/arrow_undo.png',
		handler : function() {
			form1.getForm().reset();
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
		height: 160,
		layout:'form',
		items:[realName,idcard, email,group]
	});
	
	var fieldset3 = new Ext.form.FieldSet({
		title : '联系信息',
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
		title : '用户注册信息'
	});
});