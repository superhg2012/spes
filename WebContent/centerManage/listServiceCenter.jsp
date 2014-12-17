<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>中心信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<style type="text/css">
	
	span.checked_span {
		background: url(centerManage/checked.png) no-repeat right;
		width: 16px;
		line-height: 16px;
		padding-left: 10px;
	}

	span.unchecked_span {
		background: url(centerManage/unchecked.png) no-repeat right;
		width: 16px;
		line-height: 16px;
		padding-left: 10px;
	}
	
	</style>
	
<script type="text/javascript">


Ext.onReady(function(){
	   //Ext.tip.QuickTipManager.init();
	   
		var store = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
			  url: "centermg/centerManageAction!listServiceCenter.action",
		      method: 'post'
			}),
	   		 reader: new Ext.data.JsonReader({
				 root:"data",
				 type :'json',
				 totalProperty : 'total',
				 fields : ['id','centerId','centerName','province','city',
				 'county','organcode','linkman','contact','email','valid',
				 'legalrepresent','remarks']
	   		})
		});
		store.load({params:{
	    	start : 0,
	    	limit : 16
	    }});
	
	
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
	

	var sm = new Ext.grid.CheckboxSelectionModel();

     var editGridPanel = new Ext.grid.EditorGridPanel({
     	//title:'中心信息',
        renderTo:"audit",
        frame:true,  
        clicksToEdit:2,  
     	width:'100%',
     	height:420,
     	store:store,
     	/*enableColumnMove:false,   //支持列移动
     	enableColumnResize:true,
      	autoExpandColumn : false,*/
     	viewConfig:{
     		forceFit:true
     	},
     	columns:[//配置表格列  
     	sm,
    	{header: "id", width:100, dataIndex: 'centerId',sortable:true,hideable: false, 
			hidden: true },  
    	{header: "中心名称", width:120, dataIndex: 'centerName', sortable:true,
    			  editor:new Ext.form.TextField({  
      	          	allowBlank : false 
     			  })  
        },
       	{header:"省份",dataIndex:"province",sortable:true,
       			    width:150,
       			    editor:new Ext.form.ComboBox({
       			    id:'comb1',
       			    name:'province',
       			  	triggerAction:'all',
       			  	forceSelection:true,
       			  	displayField:'name',
       			  	valueField:'code',
       			  	mode:'local',
       			  	store:storeProvince,
       			  	listeners:{
								select: function(combo, record, index){
		                		Ext.getCmp('comb2').setValue(); //清空市和区县的下拉框数据
		                		Ext.getCmp('comb3').setValue();
		                		var com1 = Ext.getCmp('comb1').getRawValue();//valueField
		                	    var pro = Ext.getCmp('comb1').getValue();
		                	    //editGridPanel.get
						    	storeCity.proxy = new Ext.data.HttpProxy({        
							            url:'centermg/centerRegisterAction!findCitiesByProvince.action'
						    	});
		              			storeCity.load({
		              				params:{province:com1}
		              			});  
		              			}        
				     }//end_listner
       			  }),
       			  renderer: function(value, p, r){
	                  var index = storeProvince.find('code', value);
	                  var record = storeProvince.getAt(index); 
	                  //Ext.Msg.alert('省份:'+index);
					  if (record == null) { 
					  	return value; //每次都要变动，每次都要渲染
					  } else { 
							return record.get('name'); // 获取record中的数据集中的display字段的值 
					  } 
           		  }
       	},
       	{header:"城市", width:150,  dataIndex:"city",sortable:true,
       				editor:new Ext.form.ComboBox({
       			    id:'comb2',
       			    name:'city',
       			  	triggerAction:'all',
       			  	forceSelection:true,
       			  	displayField:'name',
       			  	valueField:'code',
       			  	mode:'local',
       			  	store:storeCity,
       			  	listeners:{
								select: function(combo, record, index){
		                		Ext.getCmp('comb3').setValue(); 
		              			var com2 = Ext.getCmp('comb2').getValue();
						    		storeCounty.proxy = new Ext.data.HttpProxy({        
							            url:'centermg/centerRegisterAction!findCountiesByCity.action'
						    		});
		              				storeCounty.load({
		              					params:{city:com2}
		              				}); 
		              			}
				   }//end_listner   
       			  }),//end_combox
       			  renderer: function(value, p, r){
       			  	  //Ext.Msg.alert(value);
	                  var index = storeCity.find('code', value);
	                  var record = storeCity.getAt(index); 
					  if (record == null) { 
					// Ext.Msg.alert("城市不存在："+value);
					  	//return value; 
					  	return r.get('city');
					  } else { 
					  	//Ext.Msg.alert("城市存在："+value);
							return record.get('name'); // 获取record中的数据集中的display字段的值 
					  } 
           		  }
       	},//end_city
       	{header:"区/县", width:150, dataIndex:"county",sortable:true,
       				editor:new Ext.form.ComboBox({
       				id:'comb3',
             		name:'county',
              		fieldLabel : "区/县",
              		triggerAction:'all',
              		valueField:'code',
              		displayField:'name',
              		store:storeCounty,
              		mode:"local"
       				}),
       			    renderer: function(value, p, r){
	                  var index = storeCounty.find('code', value);
	                  var record = storeCounty.getAt(index); 
					  if (record == null) { 
					  	//return value;
					  	return r.get('county'); 
					  } else { 
							return record.get('name'); // 获取record中的数据集中的display字段的值 
					  } 
           		  }
       	},
		{header:"组织代码", width:120, dataIndex:"organcode",sortable:true},
		{header:"联系人", width:150, dataIndex:"linkman",sortable:true,
					editor:new Ext.form.TextField({  
      	          	allowBlank : false 
     			  }) 
		},
		{header:"联系方式", width:120, dataIndex:"contact",sortable:true,
				   editor:new Ext.form.TextField({  
      	           allowBlank : false 
     			  }) 
		},
		{header:"邮件",width: 120, dataIndex:"email",
				  editor:new Ext.form.TextField({  
      	          	allowBlank : false,
      	          	ytype:'email'
     			  }) 
		},
		{header:"法人代表",width:120, dataIndex:"legalrepresent",sortable:true,
				  editor:new Ext.form.TextField({  
      	          	allowBlank : false 
     			  }) 
		}
        ],
        sm:sm,
         tbar:new Ext.Toolbar(['&nbsp;',
 		{
         	text:'保存修改',
         	icon:'image/icon/disk.png',
         	handler:function(){
				var m = store.modified.slice(0);
				var jsonArray = [];
				Ext.each(m,function(item){
					jsonArray.push(item.data);
				});
				Ext.Ajax.request({
                            	url:'centermg/centerManageAction!saveCenterModify',
                              	success: function(resp,opts){
                                    // var msg = Ext.util.JSON.decode(resp.responseText);
                                    store.reload();
                                    Ext.Msg.confirm("提示信息","保存成功！",function(btn){});
                               	},
                               	failure: function(){ },
                               	params: {'changes': Ext.encode(jsonArray)}
                            });	
               //store.commitChanges();  
         	}
         },'-',
         {
         	text:'删除中心',
         	icon :'image/icon/delete.png',
         	handler: function(){
         		var selModel = editGridPanel.getSelectionModel(); 
				//alert(selModel.getCount());
				if(selModel.getCount()> 0){
					var recs = selModel.getSelected();  
					if(selModel.getCount() != 1){
					Ext.Msg.confirm("提示信息","请每次选择一个！",function(btn){});
						//Ext.Msg.alert('提示信息','请每次选择一个！');
						return;
					}
					
          			Ext.Msg.confirm("删除注册信息","确定要删除吗？",function(btn){
               			if(btn == 'yes'){
               				editGridPanel.getStore().remove(recs);//store中删除选择记录  
	                    	Ext.Ajax.request({  
	                        	url :'centermg/centerManageAction!deleteServiceCenterbyId',  
	                        	params :{  
	                            	'delIds':recs.get("centerId")
	                            },  
	                       		method : "POST",  
	                        	success : function(response) {  
	                        		Ext.Msg.confirm("提示信息","中心删除成功！",function(btn){
	                        			if(btn == 'yes' || btn == 'no'){
	                        				store.reload();
	                        			}
	                        		});
		                            /*Ext.Msg.alert("信息","数据删除成功！", function() {  
		                                store.reload();  
		                            }); */ 
	                           },  
	                           failure : function(response) {  
	                           Ext.Msg.confirm("提示信息","中心删除失败，请稍后再试！",function(btn){});
	                           //Ext.Msg.alert("警告","数据删除失败，请稍后再试！");  
	                           }  
	                       });//endRequest
                    				  
                 		}//end_if 
          			}); 
      			}//end_if
			}
         }
         ]),
         bbar:
			 new Ext.PagingToolbar({
               pageSize : 16,
               store : store,
               displayInfo : true,
               displayMsg : '显示 {0} - {1} 共 {2} 条',
               emptyMsg : "没有数据显示！",
               beforePageText : "页码 ",
               afterPageText : "共 {0} 页",
               firstText : "首页",
               lastText : "末页",
               nextText : "下一页",
               prevText : "上一页",
               refreshText : "刷新"
        })
         
         
     });
     
     editGridPanel.on("afteredit", afterEdit, editGridPanel);//添加监听函数
     
     
    function afterEdit(obj){   
  
	    var r = obj.record;//获取被修改的行   
	    var l = obj.field; //获取被修改的列   
	    var province = r.get("province");   
	    var city = r.get("city");
	    var county = r.get("county");
	    var lie = r.get(l); //被修改的列的值  
	   // Ext.Msg.alert(lie);//获取修改后的值
	   if(province != null){
	   		//Ext.Msg.alert(province);
	   		var newProvince = Ext.getCmp('comb1').getRawValue();
	   		r.set(obj.grid.getColumnModel().getDataIndex(3),newProvince);
	   }
	   if(city != null){
	   	    var newCity = Ext.getCmp('comb2').getRawValue();
	   	    r.set(obj.grid.getColumnModel().getDataIndex(4),newCity);
	   }
	   if(county != null){
	   	    var newCounty = Ext.getCmp('comb3').getRawValue();
	   	     r.set(obj.grid.getColumnModel().getDataIndex(5),newCounty);
	   }
	   
    }   
     
     
	//editGridPanel.render();
 });
	</script>

  </head>
  
  <body>
  <div id="audit"></div>
  </body>
</html>
