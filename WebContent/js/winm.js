//窗口管理
Ext.onReady(function(){
	Ext.QuickTips.init();

	var store = new Ext.data.Store({
	 proxy : new Ext.data.HttpProxy({
		  url : 'sys/windowAction!getWindowListByCenterId.action?centerId='+Ext.get("centerId").dom.value,//tod o
		  method  : 'POST'
	 }), 
	 
	 reader : new Ext.data.JsonReader({
		 type:'json',
		 root : 'data',
		 totalProperty : 'total',
		 fields : ['windowId','windowName','centerId','windowBussiness','windowDesc']
	 }),
	 autoLoad : true
   });

	//加载数据
    store.load({params:{
    	start : 0,
    	limit : 10
    }});
    
    //使用，禁用的comobox
/**    var windowState = [['可用','可用'],['禁用','禁用']];
    var combox = new Ext.form.ComboBox({
    	store : new Ext.data.SimpleStore({
    		fields:['value','text'],
            data:windowState
    	}),
		displayField:'text',
		valueField:'value',
	    mode: "local",
		readOnly: true,
		triggerAction:"all"
    });
    */
   	//checkBox
	var sm = new Ext.grid.CheckboxSelectionModel({
	    	singleSelect : true
	});// 设置复选框
	var cm = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(),
	   {header : "窗口编号", width : 100,dataIndex : 'windowId',hidden : true},
	   {header : "窗口名称", width : 250,dataIndex : 'windowName',editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:false,blankText:'必填字段'}))},
	   {header : "窗口业务",width : 300,dataIndex: 'windowBussiness',editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:false,blankText:'必填字段'}))},
	   {header : "窗口描述",width : 300,dataIndex: 'windowDesc',editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))},
	   {header : "中心编号", width : 100,dataIndex : 'centerId',hidden :true}
	]);
	
	var windowRecord = Ext.data.Record.create([
		{name:'windowId',type:'string'},
		{name:'windowName',type:'string'},
		{name:'windowBussiness',type:'string'},
		{name:'windowDesc',type:'string'},
		{name:'centerId',tyoe:'string'}
		]);
	
	var windowGrid = new Ext.grid.EditorGridPanel({
		id:'winpanel',
		height : 200,
		region : 'center',
		store : store,
		layout : 'fit',
		cm : cm,
		sm : sm,
		tbar : ['&nbsp;',{
			text:'添加窗口',
			icon : 'image/icon/add.png',
			handler : function(){
     		   var intValue = {windowId:'',windowName:'',windowBussiness:'',windowDesc:'',centerId:''}
 		       var p = new windowRecord(intValue);
			   var record = new Ext.data.Record({});
			   windowGrid.stopEditing();
           	   store.insert(0,p);
         	   windowGrid.startEditing(0,2);
         	   p.dirty = true;				//脏数据
//         	   p.modified = initValue;      
         	   if(store.modified.indexOf(p) == -1){
         			store.modified.push(p); //添加到本地的store中
         	   }
			}
		},'-',{
			text : '保存窗口',
			icon : 'image/icon/disk.png',
			handler : function(){
			    var modifiedRecords = store.modified.slice(0);
				var submitRecords=[];
				if(modifiedRecords.length==0){
					Ext.Msg.alert("提示信息",'没有发现新增或修改过的记录！');
					return;
				}
				
				for(var i=0,len=modifiedRecords.length;i<len;i++){
					var jsonData = Ext.util.JSON.encode(modifiedRecords[i].data);
			    	submitRecords.push(jsonData);
			    }
				
				var json = "{" + '"window":[' + submitRecords + "]" +"}";
				Ext.Ajax.request({
					url:'sys/windowAction!addOrUpdateWindow.action',
					params:{jsonData:json},
					callback:function(options,success,response){
						if(success){
							Ext.Msg.alert("提示信息","保存成功！"); 
							store.reload();
						} else{
							Ext.Msg.alert("提示信息","保存失败！");
						}
					}
					
				});
			}
		}],
		bbar : new Ext.PagingToolbar({
               pageSize : 10,
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
	
	var layoutPanel = new Ext.Panel({
		id : 'total',
		title : '',
		width : '100%',
		height:500,
		renderTo : 'winManage',
		layout : 'border',
		items : [windowGrid]
	});
	
});