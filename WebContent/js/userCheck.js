Ext.onReady(function(){
	Ext.QuickTips.init();
	var centerId = Ext.get("centerId").dom.value; //中心Id
	//审核标记
	var audit = function(value){
		if(value == 'true'){
			return '<span class="checked_span">&nbsp;</span>';
		}else{
			return '<span class="unchecked_span">&nbsp;</span>';
		}
	};
	
	var  store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'userAction!getUnValidUserOfCenter.action',
			method:'post'
		}),
		reader : new Ext.data.JsonReader({
			type:'json',
		 	totalProperty : 'total',
		 	root : 'root',
			fields : ['userId','userName','realName','gender','email','valid']
		})
	});
	
//	store.load({params:{start:0,limit:15}});
	
		//加载数据
    store.load({params:{
    	start : 0,
    	limit : 15
    }});
    
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});// 设置复选框
	var cm = new Ext.grid.ColumnModel([
	    new Ext.grid.RowNumberer(),
	    sm,
	    {header : '用户编号',width : 50,dataIndex:'userId',hidden:true},
	    {header : '用户名',width : 50,dataIndex:'userName',hidden:true},
		{header : '姓名',width : 150,dataIndex:'realName'},
		{header : '性别', width:150,dataIndex:'gender'},
		{header : '邮箱', width:200,dataIndex:'email'},
		{header : "审核结果",width:100,dataIndex:"valid",renderer:audit}
	]);
	
	var userEditorPanel = new Ext.grid.EditorGridPanel({
		width : '100%',
//		autoHeight:true,
		height:400,
		store : store,
		cm : cm,
		sm : sm,
		frame : true,
		border : true,
		loadMash : true,
		waitMsg :'数据加载中，请稍后...',
//		viewConfig : { forceFit : true},
		stripeRows : true,
		renderTo :'userCheck-div',
	    tbar : ['&nbsp;',{
	    	text:'审核通过',
	    	icon : 'image/icon/lock.png',
	    	handler : function(){
	    	   var selections = userEditorPanel.getSelectionModel().getSelections();
	    	   if(selections.length > 0){
	    		   if(confirm("确认审核通过该用户？")== true){
		    		   var record = selections[0];
		    		   Ext.Ajax.request({
		    			   url : 'userAction!upDateUserValid.action',
		    			   method:'post',
		    			   callback : function(options, success, response){
		    			     if(success){
//		    			    	 Ext.Msg.alert("提示信息","用户审核成功！");
//		    			    	 userEditorPanel.getView().refresh();
		    			    	 store.reload();
		    			     } else {
		    			    	 Ext.Msg.alert("提示信息","用户审核失败！");
		    			    	 return;
		    			     }
		    			   },
		    			   params : {userId:record.get("userId")}
		    		   });
	    		   }
	    	   } else {
	    		   Ext.Msg.alert("提示信息","未选择要审核的用户");
		    	   return;
	    	   }
	    	}
	    },'-'],
	    bbar:new Ext.PagingToolbar({
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
	
});