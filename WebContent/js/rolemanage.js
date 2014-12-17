Ext.onReady(function(){
	Ext.QuickTips.init();
	
	var comboxStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'roleAction!getRolesByRoleId.action?roleId=' + Ext.get("roleId").dom.value,//to do
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
	
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), 
    	    {header :"用户编号",width : 100,dataIndex :'userId',hidden : true},
            {header :"用户名", width: 120,dataIndex : 'realName',sortable : true},
            {header :"角色", width: 180,dataIndex : 'roleName',sortable : true,editor:new Ext.grid.GridEditor(combox)},
            {header :"角色编号",width : 100,dataIndex:'roleId',hidden:true}
    ]);
	
	 var store = new Ext.data.Store({
		 proxy : new Ext.data.HttpProxy({
			  url : 'userAction!getUserList2.action?centerId='+ Ext.get("centerId").dom.value,
			  method  : 'POST'
		 }), 
		 reader : new Ext.data.JsonReader({
			 type:'json',
			 totalProperty : 'total',
			 root : 'data',
			 fields : ['userId','realName','roleName','roleId']
		 })
   });

	//加载数据
    store.load({params:{
    	start : 0,
    	limit : 15
    }});
	
	var searchField = new Ext.form.TextField({
		allowBlank :true,
		blankText : '请输入要查找的用户名',
		enableKeyEvents: true,
		listeners :{
			'keyup' : function(record,id){
				store.filterBy(function(record,id){
					var value = searchField.getValue(); 
					if(value =="" || value==null){
						return true;
					}
					return (record.get("realName")===searchField.getValue()) ? true:false;
				});
			}
		}
	});
	
	
	var roleGridPanel = new Ext.grid.EditorGridPanel({
		width : '100%',
		autoHeight : true,
		renderTo:'rolediv',
		frame : true,
		store : store,
		cm : cm,
		tbar : ['&nbsp',{
			text : '保存修改',
			icon : 'image/icon/disk.png',
			handler:function(){
			   var modifiedRecords = store.modified.slice(0);
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
					    		store.reload();
					    	} else{
					    		Ext.Msg.alert('错误提示','角色修改不成功！');
					    		return;
					    	}
					    }
					});
				}
				
			}
		},'-','查找人员：',searchField,'&nbsp;',{
			text:'查找',
			icon : 'image/icon/search_icon.gif',
			handler : function(){
			    if(searchField.isValid()){
					var value = searchField.getValue();
				    //store.filter('userName',value,false,false);
					store.filterBy(function(record,id){
						if(value="" || value==null){
							return true;
						}
					   return (record.get("realName")===searchField.getValue())? true:false;
					});
				} //end if
			}// end handler
		}],
		bbar : new Ext.PagingToolbar({
               pageSize : 15,
               displayInfo : true,
               store : store,
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
	  
	 function afterEdit(o){
		  var record =  o.record;
		  var field = o.field;
		  var roleName = record.get("roleName");
		  var roleId = record.get("roleId");
		  if(null!=roleName && null!=roleId){
			  var newroleName = Ext.getCmp("rolecombox").getRawValue();
			  var newroleId = Ext.getCmp("rolecombox").getValue();
			  record.set(o.grid.getColumnModel().getDataIndex(3),newroleName);
			  record.set(o.grid.getColumnModel().getDataIndex(4),newroleId);
		  }
	 }
});