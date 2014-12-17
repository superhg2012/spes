<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>������Ϣ</title>
    
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
	

	var sm = new Ext.grid.CheckboxSelectionModel();

     var editGridPanel = new Ext.grid.EditorGridPanel({
     	//title:'������Ϣ',
        renderTo:"audit",
        frame:true,  
        clicksToEdit:2,  
     	width:'100%',
     	height:420,
     	store:store,
     	/*enableColumnMove:false,   //֧�����ƶ�
     	enableColumnResize:true,
      	autoExpandColumn : false,*/
     	viewConfig:{
     		forceFit:true
     	},
     	columns:[//���ñ����  
     	sm,
    	{header: "id", width:100, dataIndex: 'centerId',sortable:true,hideable: false, 
			hidden: true },  
    	{header: "��������", width:120, dataIndex: 'centerName', sortable:true,
    			  editor:new Ext.form.TextField({  
      	          	allowBlank : false 
     			  })  
        },
       	{header:"ʡ��",dataIndex:"province",sortable:true,
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
		                		Ext.getCmp('comb2').setValue(); //����к����ص�����������
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
	                  //Ext.Msg.alert('ʡ��:'+index);
					  if (record == null) { 
					  	return value; //ÿ�ζ�Ҫ�䶯��ÿ�ζ�Ҫ��Ⱦ
					  } else { 
							return record.get('name'); // ��ȡrecord�е����ݼ��е�display�ֶε�ֵ 
					  } 
           		  }
       	},
       	{header:"����", width:150,  dataIndex:"city",sortable:true,
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
					// Ext.Msg.alert("���в����ڣ�"+value);
					  	//return value; 
					  	return r.get('city');
					  } else { 
					  	//Ext.Msg.alert("���д��ڣ�"+value);
							return record.get('name'); // ��ȡrecord�е����ݼ��е�display�ֶε�ֵ 
					  } 
           		  }
       	},//end_city
       	{header:"��/��", width:150, dataIndex:"county",sortable:true,
       				editor:new Ext.form.ComboBox({
       				id:'comb3',
             		name:'county',
              		fieldLabel : "��/��",
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
							return record.get('name'); // ��ȡrecord�е����ݼ��е�display�ֶε�ֵ 
					  } 
           		  }
       	},
		{header:"��֯����", width:120, dataIndex:"organcode",sortable:true},
		{header:"��ϵ��", width:150, dataIndex:"linkman",sortable:true,
					editor:new Ext.form.TextField({  
      	          	allowBlank : false 
     			  }) 
		},
		{header:"��ϵ��ʽ", width:120, dataIndex:"contact",sortable:true,
				   editor:new Ext.form.TextField({  
      	           allowBlank : false 
     			  }) 
		},
		{header:"�ʼ�",width: 120, dataIndex:"email",
				  editor:new Ext.form.TextField({  
      	          	allowBlank : false,
      	          	ytype:'email'
     			  }) 
		},
		{header:"���˴���",width:120, dataIndex:"legalrepresent",sortable:true,
				  editor:new Ext.form.TextField({  
      	          	allowBlank : false 
     			  }) 
		}
        ],
        sm:sm,
         tbar:new Ext.Toolbar(['&nbsp;',
 		{
         	text:'�����޸�',
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
                                    Ext.Msg.confirm("��ʾ��Ϣ","����ɹ���",function(btn){});
                               	},
                               	failure: function(){ },
                               	params: {'changes': Ext.encode(jsonArray)}
                            });	
               //store.commitChanges();  
         	}
         },'-',
         {
         	text:'ɾ������',
         	icon :'image/icon/delete.png',
         	handler: function(){
         		var selModel = editGridPanel.getSelectionModel(); 
				//alert(selModel.getCount());
				if(selModel.getCount()> 0){
					var recs = selModel.getSelected();  
					if(selModel.getCount() != 1){
					Ext.Msg.confirm("��ʾ��Ϣ","��ÿ��ѡ��һ����",function(btn){});
						//Ext.Msg.alert('��ʾ��Ϣ','��ÿ��ѡ��һ����');
						return;
					}
					
          			Ext.Msg.confirm("ɾ��ע����Ϣ","ȷ��Ҫɾ����",function(btn){
               			if(btn == 'yes'){
               				editGridPanel.getStore().remove(recs);//store��ɾ��ѡ���¼  
	                    	Ext.Ajax.request({  
	                        	url :'centermg/centerManageAction!deleteServiceCenterbyId',  
	                        	params :{  
	                            	'delIds':recs.get("centerId")
	                            },  
	                       		method : "POST",  
	                        	success : function(response) {  
	                        		Ext.Msg.confirm("��ʾ��Ϣ","����ɾ���ɹ���",function(btn){
	                        			if(btn == 'yes' || btn == 'no'){
	                        				store.reload();
	                        			}
	                        		});
		                            /*Ext.Msg.alert("��Ϣ","����ɾ���ɹ���", function() {  
		                                store.reload();  
		                            }); */ 
	                           },  
	                           failure : function(response) {  
	                           Ext.Msg.confirm("��ʾ��Ϣ","����ɾ��ʧ�ܣ����Ժ����ԣ�",function(btn){});
	                           //Ext.Msg.alert("����","����ɾ��ʧ�ܣ����Ժ����ԣ�");  
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
               displayMsg : '��ʾ {0} - {1} �� {2} ��',
               emptyMsg : "û��������ʾ��",
               beforePageText : "ҳ�� ",
               afterPageText : "�� {0} ҳ",
               firstText : "��ҳ",
               lastText : "ĩҳ",
               nextText : "��һҳ",
               prevText : "��һҳ",
               refreshText : "ˢ��"
        })
         
         
     });
     
     editGridPanel.on("afteredit", afterEdit, editGridPanel);//��Ӽ�������
     
     
    function afterEdit(obj){   
  
	    var r = obj.record;//��ȡ���޸ĵ���   
	    var l = obj.field; //��ȡ���޸ĵ���   
	    var province = r.get("province");   
	    var city = r.get("city");
	    var county = r.get("county");
	    var lie = r.get(l); //���޸ĵ��е�ֵ  
	   // Ext.Msg.alert(lie);//��ȡ�޸ĺ��ֵ
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
