var ManageSheet = (function (){
	var my = {};
	
	Ext.QuickTips.init();
	
	var win = null;
	
	//=====load eveluate sheet list
    var renderSheetType = function sheetTypeRender(value, metadata, record, rowIndex, colIndex, store){
    	var formatedValue = value;
    	if(value == 'month'){
    		formatedValue = '月度考核';
    	} else if(value=='quarter') {
    		formatedValue = '季度考核';
    	} else {
    		formatedValue = '年度考核';
    	}
    	return formatedValue;
    };
    
    var buttonRender = function (value, metadata, record, rowIndex, colIndex, store) {
    	var formattedPosition = "";
		formattedPosition += '&nbsp; &nbsp;&nbsp;&nbsp;';
  	    formattedPosition += '<span title="详细考核信息" style="cursor:pointer;" onclick="ManageSheet.onClickDisplaySheetDetailInfos(\'' + value + '\');"><i class="icon-zoom-in"></i></span>';
		return formattedPosition;
    };
    
    var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});// 设置复选框
		 // 设置列
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),sm,
    	    {header : "考核编号", width : 100,dataIndex :'sheetId', hidden : true, fixed:true},
    	    {header : "考核表单", width : 100,dataIndex :'sheetName'},
            {header : "表单类型", width : 60, dataIndex: 'sheetType',renderer : function(value, metadata, record, rowIndex, colIndex, store){ return renderSheetType(value, metadata, record, rowIndex, colIndex, store);}},
            {header : "考核用户", width: 100,dataIndex : 'userName'},
            {header : "创建时间", width: 120,dataIndex : 'createTime'},
            {header : '详细考核信息', width : 50, dataIndex:'sheetId',renderer : function (value, metadata, record, rowIndex, colIndex, store) { return buttonRender(value, metadata, record, rowIndex, colIndex, store);}}
    ]);

    var store = new Ext.data.Store({
	 proxy : new Ext.data.HttpProxy({
		  url : 'center/evaluateCSheetAction!getHisEvaluateSheets.action?checkType=center&sheetState=finished',
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
    	limit : 10
    }});
		
    var chartMenu = new Ext.menu.Menu({
		items: [{
			text: '对比已选择表单', 				// Button name
			listeners: {click : onCompareSelectedSheets}	// The function called when user click on the button   			
		}, {
			text: '对比所有考核表单', 					// Button name
			listeners: {click : onCompareAllSheets} 		// The function called when user click on the button   			
		}]
	});
    
    var sheetGridPanel = new Ext.grid.GridPanel({
	    id : 'sheet-panel2',
		width : '100%',
		height : 'auto',
		autoHeight : true,
		frame : true,
		border : true,
		loadMash : true,
		waitMsg :'数据加载中，请稍后...',
		viewConfig : { forceFit : true},
		store : store,
		cm : cm,
		stripeRows : true,
		renderTo : "centerSheetListDiv",
		tbar :['&nbsp;', {
					text : '中心考核结果图表',
					iconCls :'iconChart',
					menu : chartMenu
					}
		       ],
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
   	//渲染
	sheetGridPanel.render();  
	
	function onCompareSelectedSheets() {
		alert("compare all...");
	}

	function onCompareAllSheets() {
		alert("compare selected...");
	}
	
	my.onClickDisplaySheetDetailInfos = function(sheetId) {
		win = new Ext.hg.ParameterWindow({
			sheetId : sheetId,
			modal : true,
			items: //Panel item
				[
					{
						xtype: 'tabpanel',
						activeTab: 0,
						itemId: 'tabPanel'
					}
				]
			});
		win.show();
		win.center();
		//return false;
	};
	
	return my;
	
})();