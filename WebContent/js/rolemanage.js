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
    	    {header :"�û����",width : 100,dataIndex :'userId',hidden : true},
            {header :"�û���", width: 120,dataIndex : 'realName',sortable : true},
            {header :"��ɫ", width: 180,dataIndex : 'roleName',sortable : true,editor:new Ext.grid.GridEditor(combox)},
            {header :"��ɫ���",width : 100,dataIndex:'roleId',hidden:true}
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

	//��������
    store.load({params:{
    	start : 0,
    	limit : 15
    }});
	
	var searchField = new Ext.form.TextField({
		allowBlank :true,
		blankText : '������Ҫ���ҵ��û���',
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
			text : '�����޸�',
			icon : 'image/icon/disk.png',
			handler:function(){
			   var modifiedRecords = store.modified.slice(0);
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
					    		store.reload();
					    	} else{
					    		Ext.Msg.alert('������ʾ','��ɫ�޸Ĳ��ɹ���');
					    		return;
					    	}
					    }
					});
				}
				
			}
		},'-','������Ա��',searchField,'&nbsp;',{
			text:'����',
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