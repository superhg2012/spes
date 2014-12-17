Ext.onReady(function (){
	
	 /*��ģ������Ⱦ�İ�ť*/  
    var operateRender = function cancel(value, metadata, record, rowIndex, colIndex, store){
    	
    	var cancelButton='<TABLE class="x-btn-wrap x-btn x-btn-text-icon" id=save style="WIDTH:55px" cellSpacing=0 cellPadding=0 border=0>';  
	    cancelButton+='<TBODY><TR>'; 
	    
    	if(record.get('sheetState') == '����δ���'){
    		cancelButton+='<TD class=x-btn-center><EM unselectable="on"><BUTTON id="ext-gen97" onclick="javascript:doEvaluateOperations('+value+')">��������</BUTTON>&nbsp;|&nbsp;<BUTTON id="ext-gen98" onclick="javascript:doDelOperations('+value+')">ɾ ��</BUTTON></EM></TD>';  
    	} else if(record.get('sheetState')== 'δ����'){
    		cancelButton+='<TD class=x-btn-center><EM unselectable="on"><BUTTON id="ext-gen97" onclick="javascript:startEvaluateOperations('+value+')">��ʼ����</BUTTON>&nbsp;|&nbsp;<BUTTON id="ext-gen98" onclick="javascript:doDelOperations('+value+')">ɾ ��</BUTTON></EM></TD>'; 
    	} else {
    		cancelButton+='<TD class=x-btn-center><EM unselectable="on"><BUTTON id="ext-gen97" onclick="javascript:doDelOperations('+value+')">ɾ ��</BUTTON></EM></TD>';
    	}
	    cancelButton+='<TD class=x-btn-right><I> </I></TD></TR></TBODY></TABLE>';  
	    return cancelButton;  
    };
	
    var renderState = function stateRender(value, metadata, record, rowIndex, colIndex, store) {
    	if(value == "����δ���"){
    		metadata.attr = 'style="background-color:red !important;color:white;"';
    	} else if(value=="�������"){
    		metadata.attr = 'style="background-color:blue !important;color:white;"';
    	}
    	return value;
    };
    
	var renderSheetType = function sheetTypeRender(value, metadata, record, rowIndex, colIndex, store){
    	var formatedValue = value;
    	if(value == 'month'){
    		formatedValue = '�¶ȿ���';
    	} else if(value=='quarter'){
    		formatedValue = '���ȿ���';
    	} else {
    		formatedValue = '��ȿ���';
    	}
    	return formatedValue;
    };
	
	// ������
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
    	    {header : "���˱��", width : 100,dataIndex :'sheetId',hidden : true, fixed:true},
    	    {header : "���˱�", width : 100,dataIndex :'sheetName',renderer:function(value,metadata){metadata.attr = 'style="color:red"';return value;}},
            {header : "������",width : 60, dataIndex: 'sheetType',renderer : function(value, metadata, record, rowIndex, colIndex, store){ return renderSheetType(value, metadata, record, rowIndex, colIndex, store);}},
            {header : "�����û�", width: 100,dataIndex : 'userName'},
            {header : "����ʱ��", width: 120,dataIndex : 'createTime'}, 
            {header : "����״̬", width : 100,dataIndex : 'sheetState',renderer : function (value, metadata, record, rowIndex, colIndex, store) {return renderState(value, metadata, record, rowIndex, colIndex, store);}},
            {header : '����', width : 100, dataIndex:'sheetId',renderer : function (value, metadata, record, rowIndex, colIndex, store) { return operateRender(value, metadata, record, rowIndex, colIndex, store);}}
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

	//��������
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
		waitMsg :'���ݼ����У����Ժ�...',
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
    
    sheetGridPanel.render();
	
});