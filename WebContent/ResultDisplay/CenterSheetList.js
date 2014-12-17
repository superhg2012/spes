Ext.onReady(function (){
	
	 /*列模型中渲染的按钮*/  
    var operateRender = function cancel(value, metadata, record, rowIndex, colIndex, store){
    	
    	var cancelButton='<TABLE class="x-btn-wrap x-btn x-btn-text-icon" id=save style="WIDTH:55px" cellSpacing=0 cellPadding=0 border=0>';  
	    cancelButton+='<TBODY><TR>'; 
	    
    	if(record.get('sheetState') == '考核未完成'){
    		cancelButton+='<TD class=x-btn-center><EM unselectable="on"><BUTTON id="ext-gen97" onclick="javascript:doEvaluateOperations('+value+')">继续考核</BUTTON>&nbsp;|&nbsp;<BUTTON id="ext-gen98" onclick="javascript:doDelOperations('+value+')">删 除</BUTTON></EM></TD>';  
    	} else if(record.get('sheetState')== '未考核'){
    		cancelButton+='<TD class=x-btn-center><EM unselectable="on"><BUTTON id="ext-gen97" onclick="javascript:startEvaluateOperations('+value+')">开始考核</BUTTON>&nbsp;|&nbsp;<BUTTON id="ext-gen98" onclick="javascript:doDelOperations('+value+')">删 除</BUTTON></EM></TD>'; 
    	} else {
    		cancelButton+='<TD class=x-btn-center><EM unselectable="on"><BUTTON id="ext-gen97" onclick="javascript:doDelOperations('+value+')">删 除</BUTTON></EM></TD>';
    	}
	    cancelButton+='<TD class=x-btn-right><I> </I></TD></TR></TBODY></TABLE>';  
	    return cancelButton;  
    };
	
    var renderState = function stateRender(value, metadata, record, rowIndex, colIndex, store) {
    	if(value == "考核未完成"){
    		metadata.attr = 'style="background-color:red !important;color:white;"';
    	} else if(value=="考核完成"){
    		metadata.attr = 'style="background-color:blue !important;color:white;"';
    	}
    	return value;
    };
    
	var renderSheetType = function sheetTypeRender(value, metadata, record, rowIndex, colIndex, store){
    	var formatedValue = value;
    	if(value == 'month'){
    		formatedValue = '月度考核';
    	} else if(value=='quarter'){
    		formatedValue = '季度考核';
    	} else {
    		formatedValue = '年度考核';
    	}
    	return formatedValue;
    };
	
	// 设置列
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
    	    {header : "考核编号", width : 100,dataIndex :'sheetId',hidden : true, fixed:true},
    	    {header : "考核表单", width : 100,dataIndex :'sheetName',renderer:function(value,metadata){metadata.attr = 'style="color:red"';return value;}},
            {header : "表单类型",width : 60, dataIndex: 'sheetType',renderer : function(value, metadata, record, rowIndex, colIndex, store){ return renderSheetType(value, metadata, record, rowIndex, colIndex, store);}},
            {header : "考核用户", width: 100,dataIndex : 'userName'},
            {header : "创建时间", width: 120,dataIndex : 'createTime'}, 
            {header : "考核状态", width : 100,dataIndex : 'sheetState',renderer : function (value, metadata, record, rowIndex, colIndex, store) {return renderState(value, metadata, record, rowIndex, colIndex, store);}},
            {header : '操作', width : 100, dataIndex:'sheetId',renderer : function (value, metadata, record, rowIndex, colIndex, store) { return operateRender(value, metadata, record, rowIndex, colIndex, store);}}
    ]);

    var store = new Ext.data.Store({
	 proxy : new Ext.data.HttpProxy({
		  url : '../center/evaluateCSheetAction!getHisEvaluateSheets.action?checkType=center',
		  method  : 'POST'
	 }), 
	 
	 reader : new Ext.data.JsonReader({
		 type:'json',
		 totalProperty : 'total',
		 root : 'data',
		 fields : ['sheetId','sheetName','sheetType','userName','createTime','sheetState','sheetId']
	 }),
	 	autoLoad : true,
	 	remoteSort : true
   });

	//加载数据
    store.load({params:{
    	start : 0,
    	limit : 16
    }});
	
    
    var sheetGridPanel = new Ext.grid.GridPanel({
	    id : 'sheet-panel',
		width : '100%',
		height : 'auto',
		autoHeight : true,
		frame : true,
		border : true,
		loadMash : true,
		waitMsg :'数据加载中，请稍后...',
		viewConfig : {
		  forceFit : true 
		},
		store : store,
		cm : cm,
		stripeRows : true,
		renderTo : "centersheetList",
		bbar : new Ext.PagingToolbar({
            pageSize : 16,
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
    
    sheetGridPanel.render();
	
});