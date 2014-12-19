var ManageSheet = (function (){
	var my = {};
	
	Ext.QuickTips.init();
	
	var win = null;
	
	//=====load eveluate sheet list
    var renderSheetType = function sheetTypeRender(value, metadata, record, rowIndex, colIndex, store){
    	var formatedValue = value;
    	if(value == 'month'){
    		formatedValue = '�¶ȿ���';
    	} else if(value=='quarter') {
    		formatedValue = '���ȿ���';
    	} else {
    		formatedValue = '��ȿ���';
    	}
    	return formatedValue;
    };
    
    var buttonRender = function (value, metadata, record, rowIndex, colIndex, store) {
    	var formattedPosition = "";
		formattedPosition += '&nbsp; &nbsp;&nbsp;&nbsp;';
  	    formattedPosition += '<span title="��ϸ������Ϣ" style="cursor:pointer;" onclick="ManageSheet.onClickDisplaySheetDetailInfos(\'' + value + '\');"><i class="icon-zoom-in"></i></span>';
		return formattedPosition;
    };
    
    var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});// ���ø�ѡ��
		 // ������
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),sm,
    	    {header : "���˱��", width : 100,dataIndex :'sheetId', hidden : true, fixed:true},
    	    {header : "���˱�", width : 100,dataIndex :'sheetName'},
            {header : "������", width : 60, dataIndex: 'sheetType',renderer : function(value, metadata, record, rowIndex, colIndex, store){ return renderSheetType(value, metadata, record, rowIndex, colIndex, store);}},
            {header : "�����û�", width: 100,dataIndex : 'userName'},
            {header : "����ʱ��", width: 120,dataIndex : 'createTime'},
            {header : '��ϸ������Ϣ', width : 50, dataIndex:'sheetId',renderer : function (value, metadata, record, rowIndex, colIndex, store) { return buttonRender(value, metadata, record, rowIndex, colIndex, store);}}
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

	//��������
    store.load({params:{
    	start : 0,
    	limit : 10
    }});
		
    var chartMenu = new Ext.menu.Menu({
		items: [{
			text: '�Ա���ѡ���', 				// Button name
			listeners: {click : onCompareSelectedSheets}	// The function called when user click on the button   			
		}, {
			text: '�Ա����п��˱�', 					// Button name
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
		waitMsg :'���ݼ����У����Ժ�...',
		viewConfig : { forceFit : true},
		store : store,
		cm : cm,
		stripeRows : true,
		renderTo : "centerSheetListDiv",
		tbar :['&nbsp;', {
					text : '���Ŀ��˽��ͼ��',
					iconCls :'iconChart',
					menu : chartMenu
					}
		       ],
		bbar : new Ext.PagingToolbar({
               pageSize : 10,
               store : store,
               displayInfo : true,
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
   	//��Ⱦ
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
						id : 'tabPanel'
					}
				]
			});
		win.show();
		win.center();
		//return false;
	};
	
	return my;
	
})();