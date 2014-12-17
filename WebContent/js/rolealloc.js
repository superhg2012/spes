Ext.onReady(function(){
	Ext.QuickTips.init();
	//staff store
	var staffstore = new Ext.data.Store({}); 
	var roleGridPanel;
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var cm2 = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm,
    	    {header :"中心编号",width : 100,dataIndex :'centerId',hidden : true},
            {header :"中心名", width: 120,dataIndex :'centerName',sortable : true},
            {header :"省", width: 120,dataIndex :'province',sortable : true},
            {header :"市", width: 120,dataIndex :'city',sortable : true}
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

	//加载center数据
    cstore.load({params:{start : 0,limit : 8}});
	//center search
	var csearchField = new Ext.form.TextField({
		allowBlank :true,
		blankText : '请输入要查找的中心名',
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
    	    {header :"用户编号", width : 100,dataIndex :'userId',hidden : true},
            {header :"用户名", width: 120,dataIndex : 'realName',sortable : true},
            {header :"角色名称", width: 180,dataIndex : 'roleName',sortable : true},
            {header :"所属中心", width:180,dataIndex:'centerName'},
            {header :"角色编号", width : 100,dataIndex:'roleId',hidden:true}
    ]);
	
	
	var searchField = new Ext.form.TextField({
		allowBlank :true,
		blankText : '请输入要查找的用户名',
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
		    //动态生成store
		    generateStaffStore(record);
		    Ext.getCmp("rolegridpanel").destroy();//销毁已经存在的gridpanel
		    
			
			searchField = new Ext.form.TextField({
				allowBlank :true,
				blankText : '请输入要查找的用户名',
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
			
					////===人员角色表格
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
		    	    {header :"用户编号", width : 100,dataIndex :'userId',hidden : true},
		            {header :"用户名", width: 120,dataIndex : 'realName',sortable : true},
		            {header :"角色名称", width: 180,dataIndex : 'roleName',sortable : true,editor:new Ext.grid.GridEditor(combox)},
		            {header :"所属中心", width:180,dataIndex:'centerName'},
		            {header :"角色编号", width : 100,dataIndex:'roleId',hidden:true}
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
					text : '保存修改',
					icon : 'image/icon/disk.png',
					handler:function(){
					   var modifiedRecords = staffstore.modified.slice(0);
					   var submitRecords=[];
						if(modifiedRecords.length==0){
							Ext.Msg.alert('提示信息','没有发现修改过的记录，没有内容可以修改！');
							return;
						}
						for(var i=0,len=modifiedRecords.length;i<len;i++){
							var jsonData = Ext.util.JSON.encode(modifiedRecords[i].data);
					    	submitRecords.push(jsonData);
					    }
						var json = "{" + '"user":[' + submitRecords + "]" +"}";
						
						if(confirm("您确定要执行该操作？")==true){
							Ext.Ajax.request({
								url : 'userAction!upDateUserRole.action',
							    method :'post',
							    params:{'jsonData' : json},
							    callback :function(options, success,response){
							    	if(success){
							    		Ext.Msg.alert("提示信息","角色修改成功！");
							    		staffstore.reload();
							    	} else{
							    		Ext.Msg.alert('错误提示','角色修改不成功！');
							    		return;
							    	}
							    }
							});
						}
					}
				},'-','查找人员：',searchField,'&nbsp;',{
					text:'查找人员',
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
			    //添加监听函数
			    roleGridPanel.on("afteredit", afterEdit, roleGridPanel);
		  }//end rowclickfunction
		},
		tbar:['&nbsp;','查找中心：',csearchField,'&nbsp;',{
			text:'查找中心',
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
	
	

	roleGridPanel = new Ext.grid.EditorGridPanel({
		id :'rolegridpanel',
		width : '100%',
		autoHeight : true,
		frame : true,
		store : staffstore,
		renderTo:'rolestaff-div',
		cm : cm,
		tbar : ['&nbsp',{
			text : '保存修改',
			icon : 'image/icon/disk.png',
			handler:function(){
			   var modifiedRecords = staffstore.modified.slice(0);
			   var submitRecords=[];
				if(modifiedRecords.length==0){
					Ext.Msg.alert('提示信息','没有发现修改过的记录，没有内容可以修改！');
					return;
				}
				for(var i=0,len=modifiedRecords.length;i<len;i++){
					var jsonData = Ext.util.JSON.encode(modifiedRecords[i].data);
			    	submitRecords.push(jsonData);
			    }
				var json = "{" + '"user":[' + submitRecords + "]" +"}";
				
				if(confirm("您确定要执行该操作？")==true){
					Ext.Ajax.request({
						url : 'userAction!upDateUserRole.action',
					    method :'post',
					    params:{'jsonData' : json},
					    callback :function(options, success,response){
					    	if(success){
					    		Ext.Msg.alert("提示信息","角色修改成功！");
					    		staffstore.reload();
					    	} else{
					    		Ext.Msg.alert('错误提示','角色修改不成功！');
					    		return;
					    	}
					    }
					});
				}
				
			}
		},'-','查找人员：',searchField,'&nbsp;',{
			text:'查找人员',
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
	
	 //添加监听函数
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
	//页面中部布局总容器
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
	
	//页面总体布局
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