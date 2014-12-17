if(Ext.isChrome===true){       
        var chromeDatePickerCSS = ".x-date-picker {border-color: #1b376c;background-color:#fff;position: relative;width: 185px;}";
        Ext.util.CSS.createStyleSheet(chromeDatePickerCSS,'chromeDatePickerStyle');
}

Ext.onReady(function(){
	Ext.QuickTips.init();
	var comStore = new Ext.data.ArrayStore({
		fields : ['id','key'],
		data : [
					[1,'男'],
					[2,'女']
				]
	});
	var valid = true;
	var requestConfig = {
		url : "gyx/userModify/loadUser.action",
			method : 'post',
			params :{},
			callback :function(options,success,response){
				var json = Ext.util.JSON.decode(response.responseText);
				var form = new Ext.form.FormPanel({
					title:'用户个人信息',
					renderTo : 'modifyUserInfo',
					id : 'userInfoPanel',
					frame : true,
					defaultType : 'textfield',
					labelAlign:'right',
					items : [
						{fieldLabel : '用户名', readOnly : true, id : 'usernameM', width :500},
						{fieldLabel : '实名',id:'realNameM',width:500},
						{fieldLabel : '新密码', inputType: 'password',width :500, id : 'passwordM'},
						{fieldLabel : '密码确认', inputType: 'password',width :500, id : 'passwordC',listeners :{'blur':checkPass}},
						{fieldLabel : '邮箱',width :500, id : 'emailM',vtype:'email',vtext:'邮箱格式错误，请重新输入'},
						{fieldLabel : '地址',width :500, id : 'addrM'},
						new Ext.form.ComboBox({
							fieldLabel : '性别',
	   						store : comStore,
	   						id : 'genderM',
		       				triggerAction:'all',
		       				displayField:'key',
		       				valueField:'key',
		       				mode:'local',
	       				}),
	       				{fieldLabel: '联系方式',width : 500,id:'contactM'},
	       				new Ext.form.TextArea({
	       					fieldLabel : '自我介绍',
	       					id : 'remarkM',
	       					width : 500,
	       					height: 200
	       				})
						//{fieldLabel : '性别',id : 'genderM', inputType : 'comboBox', store : comStore, displayField : 'value'}
					],
					buttons : [
							new Ext.Button({
		            				text : '保 存',
		            				id : 'modifyUser',
		            				listeners : {
		                        		  	"click": function(){
		                        		  			modifyUserInfo();
		                        		  	}
		                        		  }
		            				}),new Ext.Button({
		            				text : '取 消',
		            				id : 'cancelM',
		            				listeners : {
		                        		  	"click": function(){
		                        		  			cancel();
		                        		  	}
		                        		  }
		            				})
						],
						buttonAlign : 'left'
				});
				
				Ext.getCmp("usernameM").setValue(json.userName);
				Ext.getCmp("realNameM").setValue(json.backup2);
				Ext.getCmp("passwordC").setValue(json.userPass);
				Ext.getCmp("passwordM").setValue(json.userPass);
				Ext.getCmp("emailM").setValue(json.email);
				Ext.getCmp("addrM").setValue(json.address);
				Ext.getCmp("genderM").setValue(json.gender);
				Ext.getCmp("contactM").setValue(json.contact);
				Ext.getCmp("remarkM").setValue(json.remarks);
			}
	};
	//Ext.getCmp("usernameM").setValue("ggx");
	Ext.Ajax.request(requestConfig);
	
	function modifyUserInfo(){
		if(valid){
			var userName = Ext.getCmp("usernameM").getValue();
			var realName = Ext.getCmp("realNameM").getValue();
			var password = Ext.getCmp("passwordC").getValue();
			var email = Ext.getCmp("emailM").getValue();
			var addr = Ext.getCmp("addrM").getValue();
			var gender = Ext.getCmp("genderM").getValue();
			var contact = Ext.getCmp("contactM").getValue();
			var remark = Ext.getCmp("remarkM").getValue();
			//alert(password+" "+email+" "+addr+" "+gender + "  "+contact+" "+remark);
			var requestC = {
				url : "gyx/userModify/modifyUser.action",
				method : 'post',
				params :{userName:userName,realName:realName,userPass : password,email : email, address:addr,gender:gender,contact:contact,remarks:remark},
				callback :function(options,success,response){
					if(response.responseText == "success"){
						Ext.MessageBox.alert("提示信息","修改成功！");
						reloadInfo();
					}else{
						Ext.MessageBox.alert("提示信息","修改失败，请稍后再试！");
						reloadInfo();
					}
				}
			}
			Ext.Ajax.request(requestC);
		}
	}
	
	function reloadInfo(){
		var requestConfig = {
			url : "gyx/userModify/loadUser.action",
				method : 'post',
				params :{},
				callback :function(options,success,response){
					var json = Ext.util.JSON.decode(response.responseText);
					Ext.getCmp("usernameM").setValue(json.userName);
					Ext.getCmp("realNameM").setValue(json.backup2);
					Ext.getCmp("passwordC").setValue(json.userPass);
					Ext.getCmp("passwordM").setValue(json.userPass);
					Ext.getCmp("emailM").setValue(json.email);
					Ext.getCmp("addrM").setValue(json.address);
					Ext.getCmp("genderM").setValue(json.gender);
					Ext.getCmp("contactM").setValue(json.contact);
					Ext.getCmp("remarkM").setValue(json.remarks);
				}
		};
		//Ext.getCmp("usernameM").setValue("ggx");
		Ext.Ajax.request(requestConfig);
	}
	
	function cancel(){
		clearPass();
	}
	
	function checkPass(){
		var pass = Ext.getCmp("passwordM").getValue();
		var passAgain = Ext.getCmp("passwordC").getValue();
		if(pass != passAgain){
			valid = false;
			Ext.MessageBox.alert("提示","两次输入密码不一致，请重新输入");
			clearPass();
		}else{
			valid = true;
		}
	}
	
	function clearPass(){
		Ext.getCmp("passwordM").setValue("");
		Ext.getCmp("passwordC").setValue("");
	}
	
});