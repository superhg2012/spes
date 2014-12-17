Ext.onReady(function(){
	Ext.QuickTips.init();
	//checkBox
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});// 设置复选框
 // 设置列
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),sm, 
    	    {header:"用户编号",width : 100,dataIndex :'userId',hidden : true},
            {header : "用户名", width: 180,dataIndex : 'userName',sortable : true}, 
            {header : "角色", width: 180,dataIndex : 'roleName',sortable : true},
            {header : "中心名称", width: 120,dataIndex : 'centerName',sortable : true}
    ]);
    
    var store = new Ext.data.Store({
	 proxy : new Ext.data.HttpProxy({
		  url : 'userAction!getUserList.action?centerId='+1,
		  method  : 'POST'
	 }), 
	 
	 reader : new Ext.data.JsonReader({
		 type:'json',
		 totalProperty : 'total',
		 root : 'data',
		 fields : ['userId','userName','roleName','centerName']
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
					return (record.get("userName")===searchField.getValue()) ? true:false;
				});
			}
		}
	});
	
	var userPanel = new Ext.grid.GridPanel({
		id : 'usergrid',
		title : '',
		cm : cm,
		sm : sm,
		store : store,
		width : '100%',
		autoHeight : true,
		frame : true,
		border : true,
		loadMash : true,
		waitMsg :'数据加载中，请稍后...',
		viewConfig : { forceFit : true},
		stripeRows : true,
		renderTo : "user-div",
		tbar : ['&nbsp;',{
			text : '删除用户',
			icon : 'image/icon/delete.png',
			handler: function(){
			}
		},'-',{
			text : '编辑用户',
			icon : 'image/icon/user_edit.png',
			handler : function(){
			
			}
		},'-','查找用户:',searchField,'&nbsp;',{
			text : '查找',
			icon : 'image/icon/search_icon.gif',
			handler: function(){
				if(searchField.isValid()){
					var value = searchField.getValue();
				    //store.filter('userName',value,false,false);
					store.filterBy(function(record,id){
						if(value="" || value==null){
							return true;
						}
					   return (record.get("userName")===searchField.getValue())? true:false;
					});
				}
			}
		}],
		bbar : new Ext.PagingToolbar({
               pageSize : 15,
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
});