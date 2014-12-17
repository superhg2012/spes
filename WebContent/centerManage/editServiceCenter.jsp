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
	    //ͳһ�ƶ�������Ϣ��ʾ������ʾ��ʽ
	    Ext.form.Field.prototype.msgTarget = 'side';
	    
		/*ʡ������Դ*/
		var storeProvince = new Ext.data.Store({   		 //ʡ�������� ����
	        proxy: new Ext.data.HttpProxy({        
	             url: 'centermg/centerRegisterAction!getAllProvince.action',//ͨaction��ȡ���е�ʡ�� ���б�
	        	 method : 'GET'
	        	 }),        
	        reader: new Ext.data.JsonReader({        
		         root: "root",
	             fields :['code','name']
        	}) 
        });
        
        /*��������Դ */
        var storeCity = new Ext.data.Store({    //ʡ�������� ����
	        reader: new Ext.data.JsonReader({        
             	root: "root",
             	fields :['code','name']
        	})
		});
        
       	/*��/������Դ*/
        var storeCounty = new Ext.data.Store({  //ʡ�������� ����
	        reader: new Ext.data.JsonReader({        
	            root: "root",
	            fields:['code','name']
	        })
	   });  
		
	  storeProvince.load();
	  var formPanel = new Ext.FormPanel({
			renderTo:"center_register",
			id:'register',
			title:'����ע��',
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
			new Ext.form.TextField({fieldLabel:"��������",
										name:"centerName",
										minLength:2,
										minLengthText:"���ĳ��Ȳ���С�� 2",
										allowBlank:false,
										blankText:"����������Ϊ�� ",
										msgTarget:'side',
										width:250}),	
			new Ext.form.TextField({fieldLabel:"����ID",
										name:"centerId",
										minLength:2,
										minLengthText:"����id���Ȳ���С�� 2",
										allowBlank:false,
										blankText:"����id����Ϊ�� ",
										msgTarget:'side',
										width:250}),						
			new Ext.form.ComboBox({
					id:"comb1",
					name:"province",
					fieldLabel:"ʡ��",
					triggerAction:"all",
					store:storeProvince,
					typeAhead : true,
					displayField:"name",
					valueField:'code',
					mode:"local",			//��ʾ�ֶ����ػ��ߵ�һ��ѡ��ʱ�����
					listeners:{
						select: function(combo, record, index){        
                		Ext.getCmp('comb2').setValue(); //����к����ص�����������
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
					fieldLabel:"����",
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
					fieldLabel:"��/��",
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
							fieldLabel : "��֯����",
							name:'organcode',
							allowDecimals : true,
							allowNegative : false,
							nanText : "��������Ч������",
							allowBlank : false,
							blankText : "��֯���벻��Ϊ��",
							width : 150
						}),                // ��֯����
			new Ext.form.TextField({
							fieldLabel : "��ϵ��", // ��ϵ��
							name:'linkman',
							validateOnBlu : true,
							validationDelay : 1000,
							minLength : 2,
							minLengthText : "��ϵ�����ֳ��Ȳ���С��2",
							allowBlank : false,
							blankText : "��ϵ�����ֳ��Ȳ���Ϊ��",
							msgTarget : 'side',
							width : 150
						}), 
			new Ext.form.TextField({
							fieldLabel:'��ϵ��ʽ',
							name:'contact',
							validateOnBlu:true,
							validationDelay:1000,
							minLength:2,
							width:150,
							allowBlank:false,
							blankText:"��ϵ��ʽ����Ϊ��"
				}),		
			new Ext.form.TextField({
								fieldLabel : "����",
								validateOnBlu : true,
								name:'email',
								allowBlank : false,
								blankText : "���䲻��Ϊ��",
								validationDelay : 1000,
								regex : /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/,
								regexText : "��������ȷ�������ʽ",
								width : 150
				}), 
		   new Ext.form.TextField({
							fieldLabel : "���˴���", // ��ϵ��
							validateOnBlu : true,
							validationDelay : 1000,
							name:'legalrepresent',
							minLength : 2,
							minLengthText : "���˴������ֳ��Ȳ���С��2",
							allowBlank : false,
							blankText : "���˴������ֳ��Ȳ���Ϊ��",
							msgTarget : 'side',
							width : 150
				}), 
			new Ext.form.TextArea({
							name: 'remarks',
							width : 350,
							height : 150,
							fieldLabel : '��ע'// ��ע
						})],
			buttons : [
				 new Ext.Button({
					   text : 'ע��',
					   handler: function(){
					     if(!formPanel.getForm().isValid()){
					    	 return;
					     }
					     //�ύ������
					     formPanel.getForm().submit({
					     	waitTitle:'�ύ',
					     	waitMsg:'���ڼ��ύ',
					    	success:function(curForm, response){
					    		Ext.Msg.alert("��ʾ��Ϣ",response.result.resptext);
					    		formPanel.getForm().reset();
					    	},
					    	failure : function(){
					    		Ext.Msg.show({title:'������Ϣ��ʾ',msg : response.result.resptext,buttons : Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
					    	}
					     });
					   }
				   }),
				  new Ext.Button({
					   text : '�� ��',
					   handler: function(){
					     //Ext.getCmp("formPanel").reset();
					     formPanel.getForm.reset();
					   }
				   }),
				  new Ext.Button({
				  	text : '�鿴',
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
