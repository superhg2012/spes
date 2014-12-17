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
		/*省份数据源*/
		var storeProvince = new Ext.data.Store({   		                    //省名下拉框 数据
	        proxy: new Ext.data.HttpProxy({        
	             url: 'centermg/centerRegisterAction!getAllProvince.action',//通action获取所有的省份 的列表
	        	 method : 'GET'
	        	 }),        
	        reader: new Ext.data.JsonReader({        
		         root: "root",
	             fields :['code','name']
        	}) 
        });
        storeProvince.load();
        
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
	
	
	
	var IsExist;
    var form = new Ext.form.FormPanel({
       title : "中心注册",
       width : '100%',
       autoHeight : 'auto',
       frame : true,
       renderTo : "a",
       layout : "form",       // 整个大的表单是form布局
       bodyStyle: 'padding:15px',
       labelWidth : 65,
       labelAlign : "right",
       url:'centermg/centerRegisterAction!addCenterServiceRegister.action',
       items :[
       { // 行1
        layout : "column",    //从左往右的布局
        items : [
        {
           columnWidth : .45, // 该列有整行中所占百分比
           layout : "form",   // 从上往下的布局
           items : [
           		{
           			xtype:'fieldset',
           			height:115,
           			title:'基本信息',
           			collapsible:true,
           			items:[
           			{
             		xtype : "textfield",
              		fieldLabel : "中心名称",
              		width : 200,
              		name:'centerName',
              		id:'center',
              		allowBlank:false,
              		blankText:'中心名称不允许为空',
              		invalidText:'该中心名称已经存在，请重新输入',
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
              		 }//校验函数
             		},
             		{
             		xtype : "textfield",
              		fieldLabel : "组织代码",
              		width : 200,
              		allowBlank:false,
              		name:'organcode',
              		blankText:'组织代码不允许为空'
             		}//end_组织代码
             		]
           		}]
          }, {
           columnWidth : .45, // 该列有整行中所占百分比
           style:'margin-left:20px',
           layout : "form", // 从上往下的布局
           items : [
           		{
           			xtype:'fieldset',
           			collapsible:true,
           			title:'地区信息',
           			items:[
           			{
             		xtype : "combo",
             		id:'provinceID',
             		name:'provinceName',
              		fieldLabel : "省份",
              		triggerAction:'all',
              		valueField:'code',
              		displayField:'name',
              		store:storeProvince,
              		emptyText:'--请选择省份--',
              		allowBlank:false,
              		blankText:'省份不允许为空！',
              		mode:"local",	
              		width : 180,
              		listeners:{
								select: function(combo, record, index){
		                		Ext.getCmp('cityID').setValue(); //清空市和区县的下拉框数据
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
              		fieldLabel : "城市",
              		triggerAction:'all',
              		valueField:'code',
              		displayField:'name',
              		store:storeCity,
              		mode:"local",	
              		emptyText:'--请选择城市--',
              		allowBlank:false,
              		blankText:'城市不允许为空！',
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
              		fieldLabel : "区/县",
              		triggerAction:'all',
              		valueField:'code',
              		displayField:'name',
              		emptyText:'--请选择区/县--',
              		store:storeCounty,
              		allowBlank:false,
              		blankText:'区/县不允许为空！',
              		mode:"local",	
              		width : 180
             		}
             		]
           		}]
          }
          ]
       }, 
       
        { // 行2
        layout : "column", // 从左往右的布局
        items : [
          {
           columnWidth : .45, // 该列有整行中所占百分比
           layout : "form", // 从上往下的布局
           items : [
           		{
           			xtype:'fieldset',
           			collapsible:true,
           			title:'联系人信息',
           			items:[
           			{
             		xtype : "textfield",
              		fieldLabel : "联系人",
              		width : 200,
              		name:'linkman',
              		allowBlank:false,
              		blankText:'联系人不允许为空'
             		},
             		{
             		xtype : "textfield",
              		fieldLabel : "联系方式",
              		allowBlank:false,
              		name:'contact',
              		blankText:'联系方式不允许为空',
              		width : 200
             		},
             		{
             		xtype : "textfield",
              		fieldLabel : "邮箱",
              		regex : /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/,
              		name:'email',
					regexText : "请输入正确的邮箱格式",
					allowBlank:false,
					blankText : '邮箱不能为空',
              		width : 200
             		},
             		{
	             		xtype : "textfield",
	              		fieldLabel : "法人代表",
	              		name:'legalrepresent',
	              		allowBlank:false,
	              		blankText:'法人代表不允许为空',
	              		width : 200
             		}
             		]
           		
           		}]
          }, {
           columnWidth : .45, // 该列有整行中所占百分比
           style:'margin-left:20px',
           layout : "form", // 从上往下的布局
           items : [
           		{
           			xtype:'fieldset',
           			collapsible:true,
           			title:'备注信息',
           			items:[
           			{
             		xtype : "textarea",
              		fieldLabel : "备注",
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
          text : "注册",
          handler:function(){
          	          form.getForm().submit({
								     	/*waitTitle:'提交',
								        waitMsg:'正在加提交',*/
					    	success:function(curForm, response){
					    		//Ext.Msg.alert("提示信息",response.result.resptext);
					    		form.getForm().reset();
					    		Ext.Msg.confirm("提示信息","中心注册成功！",function(btn){});
					    	},
					    	failure : function(curForm, response){
					    	Ext.Msg.confirm("提示信息","中心注册失败！",function(btn){});
					    	
					    	}
					     });
          }
         }, {
          text : "重置",
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
