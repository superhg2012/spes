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
	
	//加载数据
    windowstore.load();
    
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), 
    	    {header :"用户编号",width : 100,dataIndex :'userId',hidden : true},
            {header :"用户名", width: 120,dataIndex : 'realName',sortable : true},
            {header :"窗口名称", width: 180,dataIndex : 'windowName',sortable : true,editor:new Ext.grid.GridEditor(combox)},
            {header :"窗口编号",width : 100,dataIndex:'windowId',hidden:true}
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

	//加载数据
    staffstore.load({params:{
    	start : 0,
    	limit : 15
    }});
	
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
	
	var windowGridPanel = new Ext.grid.EditorGridPanel({
		width : '100%',
		autoHeight : true,
		renderTo:'staffalloc',
		frame : true,
		store : staffstore,
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
				var json = "{" + '"userOfWindow":[' + submitRecords + "]" +"}";
				
				if(confirm("您确定要执行该操作？")==true){
					Ext.Ajax.request({
						url : 'userAction!upDateUserWindow.action',
					    method :'post',
					    params:{'jsonData' : json},
					    callback :function(options, success,response){
					    	if(success){
					    		Ext.Msg.alert("提示信息","用户所属窗口修改成功！");
					    		staffstore.reload();
					    	} else{
					    		Ext.Msg.alert('错误提示','用户所属窗口修改不成功！');
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