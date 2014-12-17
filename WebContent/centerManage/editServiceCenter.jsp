<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		Ext.QuickTips.init();
	    //统一制定错误信息提示浮动显示方式
	    Ext.form.Field.prototype.msgTarget = 'side';
	    
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
        
        /*城市数据源 */
        var storeCity = new Ext.data.Store({    //省名下拉框 数据
	        reader: new Ext.data.JsonReader({        
             	root: "root",
             	fields :['code','name']
        	})
		});
        
       	/*区/县数据源*/
        var storeCounty = new Ext.data.Store({  //省名下拉框 数据
	        reader: new Ext.data.JsonReader({        
	            root: "root",
	            fields:['code','name']
	        })
	   });  
		
	  storeProvince.load();
	  var formPanel = new Ext.FormPanel({
			renderTo:"center_register",
			id:'register',
			title:'中心注册',
			width:'100%',
			height:450,
			labelSeparator:":",
			labelAlign:"right",
			frame:true,
			url:'centermg/centerRegisterAction!addCenterServiceRegister.action',
			
			reader: new Ext.data.JsonReader(
				{root:'data'}, 
    			fields :[{name:'centerName',mapping:'centerName'}, 
		     			{name: 'centerId',mapping:'centerId'}, 
		     			{name: 'province',mapping:'province'}, 
		     			{name: 'city',mapping:'city'},
		     			{name: 'county',mapping:'county'},
		     			{name: 'organcode',mapping:'organcode'},
		     			{name: 'linkman',mapping:'linkman'},
		     			{name: 'contact', mapping:'contact'},
		     			{name: 'email', mapping:'email'},
		     			{name: 'legalrepresent', mapping:'legalrepresent'},
		     			{name: 'remarks', mapping:'remarks'},
		     			] 
		   ), 
			
			items:[
			new Ext.form.TextField({fieldLabel:"中心名称",
										name:"centerName",
										minLength:2,
										minLengthText:"中心长度不能小于 2",
										allowBlank:false,
										blankText:"中心名不能为空 ",
										msgTarget:'side',
										width:250}),	
			new Ext.form.TextField({fieldLabel:"中心ID",
										name:"centerId",
										minLength:2,
										minLengthText:"中心id长度不能小于 2",
										allowBlank:false,
										blankText:"中心id不能为空 ",
										msgTarget:'side',
										width:250}),						
			new Ext.form.ComboBox({
					id:"comb1",
					name:"province",
					fieldLabel:"省份",
					triggerAction:"all",
					store:storeProvince,
					typeAhead : true,
					displayField:"name",
					valueField:'code',
					mode:"local",			//表示手动加载或者第一次选择时候加载
					listeners:{
						select: function(combo, record, index){        
                		Ext.getCmp('comb2').setValue(); //清空市和区县的下拉框数据
                		Ext.getCmp('comb3').setValue();
                		var com1 = Ext.getCmp('comb1').getRawValue();
				    	storeCity.proxy = new Ext.data.HttpProxy({        
					            url:'reg/centerRegisterAction!findCitiesByProvince.action'
				    	});
              			storeCity.load({
              				params:{province:com1}
              			});  
              			}        
           		   }
				}),
			new Ext.form.ComboBox({
					id:"comb2",
					name:"city",
					fieldLabel:"城市",
					triggerAction:"all",
					store:storeCity,
					typeAhead : true,
					displayField:"name",
					valueField:'code',
					mode:"local",
					listeners:{
						select : function(combo, record,index){    
						Ext.getCmp('comb3').setValue(); 
              			var com2 = Ext.getCmp('comb2').getValue();
				    		storeCounty.proxy = new Ext.data.HttpProxy({        
					            url:'reg/centerRegisterAction!findCountiesByCity.action'
				    		});
              				storeCounty.load({
              					params:{city:com2}
              				}); 
              			
              			}        
           		   }    
				}),
			new Ext.form.ComboBox({
					id:"comb3",
					name:"county",
					fieldLabel:"区/县",
					triggerAction:"all",
					store:storeCounty,
					typeAhead : true,
					displayField:"name",
					valueField:'code',
					mode:"local",
					listeners:{
						select:function(combo, record, index){
							 
						}
					}
				}),
			new Ext.form.NumberField({
							fieldLabel : "组织代码",
							name:'organcode',
							allowDecimals : true,
							allowNegative : false,
							nanText : "请输入有效的整数",
							allowBlank : false,
							blankText : "组织代码不能为空",
							width : 150
						}),                // 组织代码
			new Ext.form.TextField({
							fieldLabel : "联系人", // 联系人
							name:'linkman',
							validateOnBlu : true,
							validationDelay : 1000,
							minLength : 2,
							minLengthText : "联系人名字长度不能小于2",
							allowBlank : false,
							blankText : "联系人名字长度不能为空",
							msgTarget : 'side',
							width : 150
						}), 
			new Ext.form.TextField({
							fieldLabel:'联系方式',
							name:'contact',
							validateOnBlu:true,
							validationDelay:1000,
							minLength:2,
							width:150,
							allowBlank:false,
							blankText:"联系方式不能为空"
				}),		
			new Ext.form.TextField({
								fieldLabel : "邮箱",
								validateOnBlu : true,
								name:'email',
								allowBlank : false,
								blankText : "邮箱不能为空",
								validationDelay : 1000,
								regex : /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/,
								regexText : "请输入正确的邮箱格式",
								width : 150
				}), 
		   new Ext.form.TextField({
							fieldLabel : "法人代表", // 联系人
							validateOnBlu : true,
							validationDelay : 1000,
							name:'legalrepresent',
							minLength : 2,
							minLengthText : "法人代表名字长度不能小于2",
							allowBlank : false,
							blankText : "法人代表名字长度不能为空",
							msgTarget : 'side',
							width : 150
				}), 
			new Ext.form.TextArea({
							name: 'remarks',
							width : 350,
							height : 150,
							fieldLabel : '备注'// 备注
						})],
			buttons : [
				 new Ext.Button({
					   text : '注册',
					   handler: function(){
					     if(!formPanel.getForm().isValid()){
					    	 return;
					     }
					     //提交表单处理
					     formPanel.getForm().submit({
					     	waitTitle:'提交',
					     	waitMsg:'正在加提交',
					    	success:function(curForm, response){
					    		Ext.Msg.alert("提示信息",response.result.resptext);
					    		formPanel.getForm().reset();
					    	},
					    	failure : function(){
					    		Ext.Msg.show({title:'错误信息提示',msg : response.result.resptext,buttons : Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
					    	}
					     });
					   }
				   }),
				  new Ext.Button({
					   text : '重 置',
					   handler: function(){
					     //Ext.getCmp("formPanel").reset();
					     formPanel.getForm.reset();
					   }
				   }),
				  new Ext.Button({
				  	text : '查看',
				  	handler:function(){
				  		new Ext.Ajax.request({
				  			url:'reg/centerRegisterAction!toAuditInfo.action',
				  		});
				  	}
				  }),
			]
		});
		formPanel.render();
	});
 	</script>
  </head>
  
  <body>
  <div id="center_register"></div>
  </body>
</html>
