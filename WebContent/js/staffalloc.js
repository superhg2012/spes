Ext.onReady(function(){
	Ext.QuickTips.init();

	var windowstore = new Ext.data.Store({
	 proxy : new Ext.data.HttpProxy({
		  url : 'sys/windowAction!getWindowByCenterId.action?centerId='+Ext.get("centerId").dom.value,//tod o
		  method  : 'POST'
	 }), 
	 
	 reader : new Ext.data.JsonReader({
		 type:'json',
		 root : 'root',
		 fields : [
			 {name:'value', mapping:'windowId'},
		 	 {name:'text',mapping:'windowName'}]
	 }),
	 autoLoad : true
   });
    
    var combox = new Ext.form.ComboBox({
		id:'windowcombox',
		mode : 'local',
		store : windowstore,
		triggerAction : 'all',
		displayField : 'text',
		valueField : 'value'
	});
	
	//��������
    windowstore.load();
    
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), 
    	    {header :"�û����",width : 100,dataIndex :'userId',hidden : true},
            {header :"�û���", width: 120,dataIndex : 'realName',sortable : true},
            {header :"��������", width: 180,dataIndex : 'windowName',sortable : true,editor:new Ext.grid.GridEditor(combox)},
            {header :"���ڱ��",width : 100,dataIndex:'windowId',hidden:true}
    ]);
	
	 var staffstore = new Ext.data.Store({
		 proxy : new Ext.data.HttpProxy({
			  url : 'userAction!getUserList2.action?centerId='+ Ext.get("centerId").dom.value,
			  method  : 'POST'
		 }), 
		 reader : new Ext.data.JsonReader({
			 type:'json',
			 totalProperty : 'total',
			 root : 'data',
			 fields : ['userId','realName','windowName','windowId']
		 })
    });

	//��������
    staffstore.load({params:{
    	start : 0,
    	limit : 15
    }});
	
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
	
	var windowGridPanel = new Ext.grid.EditorGridPanel({
		width : '100%',
		autoHeight : true,
		renderTo:'staffalloc',
		frame : true,
		store : staffstore,
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
				var json = "{" + '"userOfWindow":[' + submitRecords + "]" +"}";
				
				if(confirm("��ȷ��Ҫִ�иò�����")==true){
					Ext.Ajax.request({
						url : 'userAction!upDateUserWindow.action',
					    method :'post',
					    params:{'jsonData' : json},
					    callback :function(options, success,response){
					    	if(success){
					    		Ext.Msg.alert("��ʾ��Ϣ","�û����������޸ĳɹ���");
					    		staffstore.reload();
					    	} else{
					    		Ext.Msg.alert('������ʾ','�û����������޸Ĳ��ɹ���');
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
               pageSize : 15,
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
	 windowGridPanel.on("afteredit", afterEdit,windowGridPanel);
	  
	 function afterEdit(o){
		  var record =  o.record;
		  var field = o.field;
		  var windowName = record.get("windowName");
		  var windowId = record.get("windowId");
		  if(null!=windowName && null!=windowId){
			  var newwindowName = Ext.getCmp("windowcombox").getRawValue();
			  var newwindowId = Ext.getCmp("windowcombox").getValue();
			  record.set(o.grid.getColumnModel().getDataIndex(3),newwindowName);
			  record.set(o.grid.getColumnModel().getDataIndex(4),newwindowId);
		  }
	 }
    
});