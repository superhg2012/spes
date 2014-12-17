Ext.onReady(function(){
	Ext.QuickTips.init();
	//staff store
	var staffstore = new Ext.data.Store({}); 
	var roleGridPanel;
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var cm2 = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm,
    	    {header :"���ı��",width : 100,dataIndex :'centerId',hidden : true},
            {header :"������", width: 120,dataIndex :'centerName',sortable : true},
            {header :"ʡ", width: 120,dataIndex :'province',sortable : true},
            {header :"��", width: 120,dataIndex :'city',sortable : true}
    ]);
	//center store
	 var cstore = new Ext.data.Store({
		 proxy : new Ext.data.HttpProxy({
			  url : 'sys/centerAction!getServiceCenter2.action',
			  method  : 'POST'
		 }), 
		 reader : new Ext.data.JsonReader({
			 type:'json',
			 totalProperty : 'total',
			 root : 'root',
			 fields : ['centerId','centerName','province','city']
		 })
   });

	//����center����
    cstore.load({params:{start : 0,limit : 8}});
	//center search
	var csearchField = new Ext.form.TextField({
		allowBlank :true,
		blankText : '������Ҫ���ҵ�������',
		enableKeyEvents: true,
		listeners :{
			'keyup' : function(record,id){
				cstore.filterBy(function(record,id){
					var value = csearchField.getValue(); 
					if(value =="" || value==null){
						return true;
					}
					return (record.get("centerName")===csearchField.getValue()) ? true:false;
				});
			}
		}
	});
    

	
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), 
    	    {header :"�û����", width : 100,dataIndex :'userId',hidden : true},
            {header :"�û���", width: 120,dataIndex : 'realName',sortable : true},
            {header :"��ɫ����", width: 180,dataIndex : 'roleName',sortable : true},
            {header :"��������", width:180,dataIndex:'centerName'},
            {header :"��ɫ���", width : 100,dataIndex:'roleId',hidden:true}
    ]);
	
	
	var searchField = new Ext.form.TextField({
		allowBlank :true,
		blankText : '������Ҫ���ҵ��û���',
		enableKeyEvents: true,
		listeners :{
			'keyup' : function(record,id){
				staffstore.filterBy(function(record,id){
					var value = searchField.getValue(); 
					if(value =="" || value==null){
						return true;
					}
					return (record.get("realName")===searchField.getValue()) ? true:false;
				});
			}
		}
	});
	
	//center grid
	var centerGrid = new Ext.grid.GridPanel({
		width : '100%',
		autoHeight:true,
		renderTo:'admin-div',
		store : cstore,
		cm : cm2,
		listeners:{
		  rowclick:function(centerGrid,rowIndex,e){
		    var cstore = centerGrid.store;
		    var record = cstore.getAt(rowIndex);
		    //��̬����store
		    generateStaffStore(record);
		    Ext.getCmp("rolegridpanel").destroy();//�����Ѿ����ڵ�gridpanel
		    
			
			searchField = new Ext.form.TextField({
				allowBlank :true,
				blankText : '������Ҫ���ҵ��û���',
				enableKeyEvents: true,
				listeners :{
					'keyup' : function(record,id){
						staffstore.filterBy(function(record,id){
							var value = searchField.getValue(); 
							if(value =="" || value==null){
								return true;
							}
							return (record.get("realName")===searchField.getValue()) ? true:false;
						});
					}
				}
			});
			
					////===��Ա��ɫ���
			var comboxStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'roleAction!getAllRoles.action',//to do
					method : 'post'
				}),
				reader : new Ext.data.JsonReader({
					root : 'root',
					type:'json',
					fields : [{name : 'value',mapping:'roleId'},
						{name : 'text',mapping:'roleName'}]
				})
			});
			
			
			var combox = new Ext.form.ComboBox({
				id:'rolecombox',
				mode : 'local',
				store : comboxStore,
				triggerAction : 'all',
				displayField : 'text',
				valueField : 'value'
			});
			comboxStore.load();
			
			cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), 
		    	    {header :"�û����", width : 100,dataIndex :'userId',hidden : true},
		            {header :"�û���", width: 120,dataIndex : 'realName',sortable : true},
		            {header :"��ɫ����", width: 180,dataIndex : 'roleName',sortable : true,editor:new Ext.grid.GridEditor(combox)},
		            {header :"��������", width:180,dataIndex:'centerName'},
		            {header :"��ɫ���", width : 100,dataIndex:'roleId',hidden:true}
		    ]);
			
			roleGridPanel = new Ext.grid.EditorGridPanel({
				id :'rolegridpanel',
				width : '100%',
				autoHeight : true,
				frame : true,
				store : staffstore,
				renderTo:'rolestaff-div',
				cm : cm,
				tbar : ['&nbsp',{
					text : '�����޸�',
					icon : 'image/icon/disk.png',
					handler:function(){
					   var modifiedRecords = staffstore.modified.slice(0);
					   var submitRecords=[];
						if(modifiedRecords.length==0){
							Ext.Msg.alert('��ʾ��Ϣ','û�з����޸Ĺ��ļ�¼��û�����ݿ����޸ģ�');
							return;
						}
						for(var i=0,len=modifiedRecords.length;i<len;i++){
							var jsonData = Ext.util.JSON.encode(modifiedRecords[i].data);
					    	submitRecords.push(jsonData);
					    }
						var json = "{" + '"user":[' + submitRecords + "]" +"}";
						
						if(confirm("��ȷ��Ҫִ�иò�����")==true){
							Ext.Ajax.request({
								url : 'userAction!upDateUserRole.action',
							    method :'post',
							    params:{'jsonData' : json},
							    callback :function(options, success,response){
							    	if(success){
							    		Ext.Msg.alert("��ʾ��Ϣ","��ɫ�޸ĳɹ���");
							    		staffstore.reload();
							    	} else{
							    		Ext.Msg.alert('������ʾ','��ɫ�޸Ĳ��ɹ���');
							    		return;
							    	}
							    }
							});
						}
					}
				},'-','������Ա��',searchField,'&nbsp;',{
					text:'������Ա',
					icon : 'image/icon/search_icon.gif',
					handler : function(){
					    if(searchField.isValid()){
							var value = searchField.getValue();
							staffstore.filterBy(function(record,id){
								if(value="" || value==null){
									return true;
								}
							   return (record.get("realName")===searchField.getValue())? true:false;
							});
						} //end if
					}// end handler
				}],
				bbar : new Ext.PagingToolbar({
		               pageSize : 10,
		               displayInfo : true,
		               store : staffstore,
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
			    //��Ӽ�������
			    roleGridPanel.on("afteredit", afterEdit, roleGridPanel);
		  }//end rowclickfunction
		},
		tbar:['&nbsp;','�������ģ�',csearchField,'&nbsp;',{
			text:'��������',
			icon : 'image/icon/search_icon.gif',
			handler : function(){
			    if(csearchField.isValid()){
					var value = csearchField.getValue();
					cstore.filterBy(function(record,id){
						if(value="" || value==null){
							return true;
						}
					   return (record.get("centerName")===csearchField.getValue())? true:false;
					});
				} //end if
			}// end handler
		}],
		bbar : new Ext.PagingToolbar({
               pageSize : 8,
               displayInfo : true,
               store : cstore,
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
	
	

	roleGridPanel = new Ext.grid.EditorGridPanel({
		id :'rolegridpanel',
		width : '100%',
		autoHeight : true,
		frame : true,
		store : staffstore,
		renderTo:'rolestaff-div',
		cm : cm,
		tbar : ['&nbsp',{
			text : '�����޸�',
			icon : 'image/icon/disk.png',
			handler:function(){
			   var modifiedRecords = staffstore.modified.slice(0);
			   var submitRecords=[];
				if(modifiedRecords.length==0){
					Ext.Msg.alert('��ʾ��Ϣ','û�з����޸Ĺ��ļ�¼��û�����ݿ����޸ģ�');
					return;
				}
				for(var i=0,len=modifiedRecords.length;i<len;i++){
					var jsonData = Ext.util.JSON.encode(modifiedRecords[i].data);
			    	submitRecords.push(jsonData);
			    }
				var json = "{" + '"user":[' + submitRecords + "]" +"}";
				
				if(confirm("��ȷ��Ҫִ�иò�����")==true){
					Ext.Ajax.request({
						url : 'userAction!upDateUserRole.action',
					    method :'post',
					    params:{'jsonData' : json},
					    callback :function(options, success,response){
					    	if(success){
					    		Ext.Msg.alert("��ʾ��Ϣ","��ɫ�޸ĳɹ���");
					    		staffstore.reload();
					    	} else{
					    		Ext.Msg.alert('������ʾ','��ɫ�޸Ĳ��ɹ���');
					    		return;
					    	}
					    }
					});
				}
				
			}
		},'-','������Ա��',searchField,'&nbsp;',{
			text:'������Ա',
			icon : 'image/icon/search_icon.gif',
			handler : function(){
			    if(searchField.isValid()){
					var value = searchField.getValue();
					staffstore.filterBy(function(record,id){
						if(value="" || value==null){
							return true;
						}
					   return (record.get("realName")===searchField.getValue())? true:false;
					});
				} //end if
			}// end handler
		}],
		bbar : new Ext.PagingToolbar({
               pageSize : 10,
               displayInfo : true,
               store : staffstore,
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
	
	 //��Ӽ�������
	 roleGridPanel.on("afteredit", afterEdit, roleGridPanel);
	 
	 function generateStaffStore(record){
		 staffstore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				  url : 'userAction!getUserList3.action?centerId='+ record.get('centerId'),
				  method  : 'POST'
			}), 
			reader : new Ext.data.JsonReader({
				 type:'json',
				 totalProperty : 'total',
				 root : 'data',
				 fields : ['userId','realName','roleName','centerName','roleId']
			 })
   		});
		 staffstore.load({params:{start : 0,limit : 10}});//call
	 }
	 
	 function afterEdit(o){
		  var record =  o.record;
		  var field = o.field;
		  var roleName = record.get("roleName");
		  var roleId = record.get("roleId");
		  if(null!=roleName && null!=roleId){
			  var newroleName = Ext.getCmp("rolecombox").getRawValue();
			  var newroleId = Ext.getCmp("rolecombox").getValue();
			  record.set(o.grid.getColumnModel().getDataIndex(3),newroleName);
			  record.set(o.grid.getColumnModel().getDataIndex(5),newroleId);
		  }
	 }
	 ///============end-role-grid===
	//ҳ���в�����������
/**	var centerPanel = new Ext.Panel({
		title :'',
		border:false,
//	    region : 'center',
	    width : '100%',
	    height : '100%',
	    layout : 'vbox',
	    margins:'1,1,1,1',
	    items : [roleGridPanel]
	});
	
	//ҳ�����岼��
	var layoutPanel = new Ext.Panel({
		id : 'centerRolePagePanel',
	    width : '100%',
	    height: 600,
	    border: false,
	    layout: 'border',
	    renderTo : "admin-div",
	    items : [centerGrid, centerPanel]
	});
	*/
});