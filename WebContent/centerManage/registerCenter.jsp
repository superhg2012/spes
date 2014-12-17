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
		<script type="text/javascript">
		 Ext.onReady(function() {
		
		 Ext.QuickTips.init();
		 Ext.form.Field.prototype.msgTarget = 'side';
		/*ʡ������Դ*/
		var storeProvince = new Ext.data.Store({   		                    //ʡ�������� ����
	        proxy: new Ext.data.HttpProxy({        
	             url: 'centermg/centerRegisterAction!getAllProvince.action',//ͨaction��ȡ���е�ʡ�� ���б�
	        	 method : 'GET'
	        	 }),        
	        reader: new Ext.data.JsonReader({        
		         root: "root",
	             fields :['code','name']
        	}) 
        });
        storeProvince.load();
        
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
	
	
	
	var IsExist;
    var form = new Ext.form.FormPanel({
       title : "����ע��",
       width : '100%',
       autoHeight : 'auto',
       frame : true,
       renderTo : "a",
       layout : "form",       // ������ı���form����
       bodyStyle: 'padding:15px',
       labelWidth : 65,
       labelAlign : "right",
       url:'centermg/centerRegisterAction!addCenterServiceRegister.action',
       items :[
       { // ��1
        layout : "column",    //�������ҵĲ���
        items : [
        {
           columnWidth : .45, // ��������������ռ�ٷֱ�
           layout : "form",   // �������µĲ���
           items : [
           		{
           			xtype:'fieldset',
           			height:115,
           			title:'������Ϣ',
           			collapsible:true,
           			items:[
           			{
             		xtype : "textfield",
              		fieldLabel : "��������",
              		width : 200,
              		name:'centerName',
              		id:'center',
              		allowBlank:false,
              		blankText:'�������Ʋ�����Ϊ��',
              		invalidText:'�����������Ѿ����ڣ�����������',
              		validator:function(){
              			var cn = Ext.getCmp("center").getValue();
              			Ext.Ajax.request({
					    	url : 'centermg/centerRegisterAction!checkCenter.action',
					    	params : {centerName:cn},
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
						//alert(IsExist);
						return IsExist;
              		 }//У�麯��
             		},
             		{
             		xtype : "textfield",
              		fieldLabel : "��֯����",
              		width : 200,
              		allowBlank:false,
              		name:'organcode',
              		blankText:'��֯���벻����Ϊ��'
             		}//end_��֯����
             		]
           		}]
          }, {
           columnWidth : .45, // ��������������ռ�ٷֱ�
           style:'margin-left:20px',
           layout : "form", // �������µĲ���
           items : [
           		{
           			xtype:'fieldset',
           			collapsible:true,
           			title:'������Ϣ',
           			items:[
           			{
             		xtype : "combo",
             		id:'provinceID',
             		name:'provinceName',
              		fieldLabel : "ʡ��",
              		triggerAction:'all',
              		valueField:'code',
              		displayField:'name',
              		store:storeProvince,
              		emptyText:'--��ѡ��ʡ��--',
              		allowBlank:false,
              		blankText:'ʡ�ݲ�����Ϊ�գ�',
              		mode:"local",	
              		width : 180,
              		listeners:{
								select: function(combo, record, index){
		                		Ext.getCmp('cityID').setValue(); //����к����ص�����������
		                		Ext.getCmp('countyID').setValue();
		                		var provinceID = Ext.getCmp('provinceID').getRawValue();
						    	storeCity.proxy = new Ext.data.HttpProxy({        
							            url:'centermg/centerRegisterAction!findCitiesByProvince.action'
						    	});
		              			storeCity.load({
		              				params:{province:provinceID}
		              			});  
		              			}        
				           		}
             		},
           			{
             		xtype : "combo",
             		id:'cityID',
             		name:'cityName',
              		fieldLabel : "����",
              		triggerAction:'all',
              		valueField:'code',
              		displayField:'name',
              		store:storeCity,
              		mode:"local",	
              		emptyText:'--��ѡ�����--',
              		allowBlank:false,
              		blankText:'���в�����Ϊ�գ�',
              		width : 180,
              		listeners:{
								select : function(combo, record,index){    
								Ext.getCmp('countyID').setValue(); 
		              			var cityID = Ext.getCmp('cityID').getValue();
						    		storeCounty.proxy = new Ext.data.HttpProxy({        
							            url:'centermg/centerRegisterAction!findCountiesByCity.action'
						    		});
		              				storeCounty.load({
		              					params:{city:cityID}
		              				}); 
		              			}        
				           	}    
             		},
             		{
             		xtype : "combo",
             		id:'countyID',
             		name:'countyName',
              		fieldLabel : "��/��",
              		triggerAction:'all',
              		valueField:'code',
              		displayField:'name',
              		emptyText:'--��ѡ����/��--',
              		store:storeCounty,
              		allowBlank:false,
              		blankText:'��/�ز�����Ϊ�գ�',
              		mode:"local",	
              		width : 180
             		}
             		]
           		}]
          }
          ]
       }, 
       
        { // ��2
        layout : "column", // �������ҵĲ���
        items : [
          {
           columnWidth : .45, // ��������������ռ�ٷֱ�
           layout : "form", // �������µĲ���
           items : [
           		{
           			xtype:'fieldset',
           			collapsible:true,
           			title:'��ϵ����Ϣ',
           			items:[
           			{
             		xtype : "textfield",
              		fieldLabel : "��ϵ��",
              		width : 200,
              		name:'linkman',
              		allowBlank:false,
              		blankText:'��ϵ�˲�����Ϊ��'
             		},
             		{
             		xtype : "textfield",
              		fieldLabel : "��ϵ��ʽ",
              		allowBlank:false,
              		name:'contact',
              		blankText:'��ϵ��ʽ������Ϊ��',
              		width : 200
             		},
             		{
             		xtype : "textfield",
              		fieldLabel : "����",
              		regex : /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/,
              		name:'email',
					regexText : "��������ȷ�������ʽ",
					allowBlank:false,
					blankText : '���䲻��Ϊ��',
              		width : 200
             		},
             		{
	             		xtype : "textfield",
	              		fieldLabel : "���˴���",
	              		name:'legalrepresent',
	              		allowBlank:false,
	              		blankText:'���˴�������Ϊ��',
	              		width : 200
             		}
             		]
           		
           		}]
          }, {
           columnWidth : .45, // ��������������ռ�ٷֱ�
           style:'margin-left:20px',
           layout : "form", // �������µĲ���
           items : [
           		{
           			xtype:'fieldset',
           			collapsible:true,
           			title:'��ע��Ϣ',
           			items:[
           			{
             		xtype : "textarea",
              		fieldLabel : "��ע",
              		name:'remarks',
              		height:'100',
              		width : 180
             		}
             		]
           		
           		}]
          }
          
          ]
          }
       
       ],
       buttonAlign : "left",
       buttons : [{
          text : "ע��",
          handler:function(){
          	          form.getForm().submit({
								     	/*waitTitle:'�ύ',
								        waitMsg:'���ڼ��ύ',*/
					    	success:function(curForm, response){
					    		//Ext.Msg.alert("��ʾ��Ϣ",response.result.resptext);
					    		form.getForm().reset();
					    		Ext.Msg.confirm("��ʾ��Ϣ","����ע��ɹ���",function(btn){});
					    	},
					    	failure : function(curForm, response){
					    	Ext.Msg.confirm("��ʾ��Ϣ","����ע��ʧ�ܣ�",function(btn){});
					    	
					    	}
					     });
          }
         }, {
          text : "����",
           handler: function(){
				     //Ext.getCmp("formPanel").reset();
				     form.getForm().reset();
				   }
         }]
      });
      
      form.render();
      
   });
        </script>
   </head>
  <body>
  <div id="a"></div>
  </body>
</html>
