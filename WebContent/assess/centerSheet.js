Ext.onReady(function(){
		
       /*render button in columns*/  
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
	    
	    //column configs
	    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
	    	    {header : "���˱��", width : 100,dataIndex :'sheetId',hidden : true, fixed:true},
	    	    {header : "���˱�", width : 100,dataIndex :'sheetName',renderer:function(value,metadata){metadata.attr = 'style="color:red"';return value;}},
	            {header : "������", width : 60, dataIndex: 'sheetType',renderer : function(value, metadata, record, rowIndex, colIndex, store){ return renderSheetType(value, metadata, record, rowIndex, colIndex, store);}},
	            {header : "�����û�", width: 100,dataIndex : 'userName'},
	            {header : "����ʱ��", width: 120,dataIndex : 'createTime'}, 
	            {header : "����״̬", width : 100,dataIndex : 'sheetState',renderer : function (value, metadata, record, rowIndex, colIndex, store) {return renderState(value, metadata, record, rowIndex, colIndex, store);}},
	            {header : '����', width : 100, dataIndex:'sheetId',renderer : function (value, metadata, record, rowIndex, colIndex, store) { return operateRender(value, metadata, record, rowIndex, colIndex, store);}}
	    ]);
	
	    var store = new Ext.data.Store({
		 proxy : new Ext.data.HttpProxy({
			  url : 'center/evaluateCSheetAction!getHisEvaluateSheets.action?checkType=center',
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

		//load data from server side
	    store.load({params:{
	    	start : 0,
	    	limit : 16
	    }});
	    
	    
//    var comStore = new Ext.data.ArrayStore({
//		fields : ['type','key'],
//		data : [
//					['month','�¶ȿ���'],
//					['quarter','���ȿ���'],
//					['year','��ȿ���']
//				]
//	});
	
//	var checkCombox = new Ext.form.ComboBox({
//	  id : 'checkCombox',
//	  fieldLabel : '��������',
//	  store : comStore,
//	  triggerAction:'all',
//	  displayField:'key',
//	  valueField:'type',
//	  mode:'local',
//	  width : 80,
//	  allowBlank : false,
//      blankText : '��ѡ�񿼺����ͣ�'
//	});
	
	var win = null;
    var sheetGridPanel = new Ext.grid.GridPanel({
	    id : 'sheet-panel',
		width : '100%',
		height : 'auto',
		autoHeight : true,
		frame : true,
		border : true,
		loadMash : true,
		waitMsg :'���ݼ����У����Ժ�...',
		viewConfig : { forceFit : true },
		store : store,
		cm : cm,
		stripeRows : true,
		renderTo : "centerSheetList",
		tbar :['&nbsp;',{
			   id:'add-btn',
			   text: '�������Ŀ��˱�',
			   icon : 'image/icon/table_add.png',
			   style:{border : 'solid 1px #abc'},// set button border css style
	           handler: function(buttonObj) {
		    		win = new Ext.hg.EvaluateSheetWindow({
		    			sheetId : '#',
		    		    items : 
		    		        [{
		    		            xtype : 'fieldset',
		    		            title : '����',
		    		            items: [{
		    		    	            xtype : 'textfield',
		    		    				id : 'sheetname',
		    		    	            fieldLabel:"������",
		    		    	            allowBlank :false,
		    		    	            blankText : '��������Ϊ�գ�'
		    		                },{
		    		    	          xtype : 'combo',
		    		    	      	  id : 'checkCombox',
		    		    	    	  fieldLabel : '��������',
		    		    	    	  store : new Ext.data.ArrayStore({
		    		    	    		   fields : ['type','key'],
		    		    	    		   data : [
		    		    	    					['month','�¶ȿ���'],
		    		    	    					['quarter','���ȿ���'],
		    		    	    					['year','��ȿ���']
		    		    	    				]
		    		    	    	  }) ,
		    		    	    	  triggerAction:'all',
		    		    	    	  displayField:'key',
		    		    	    	  valueField:'type',
		    		    	    	  mode:'local',
		    		    	    	  width : 200,
		    		    	    	  allowBlank : false,
		    		    	          blankText : '��ѡ�񿼺����ͣ�'
		    		             }]
		    		        }],
		    		        buttons:[{          //buttons��������������еİ�ť����������
		    		            text:"�� ��",
		    		            handler :function(buttonObj){
		    		    	      var sheetName = Ext.getCmp("sheetname").getValue();
		    		    	      var checktype = Ext.getCmp("checkCombox").getValue();
		    		    	      var checkRawType = Ext.getCmp("checkCombox").getRawValue();
		    		    	      GLOBAL['sheetName'] = sheetName;
		    		    	      GLOBAL['sheetType'] = checktype;
		    		    	      GLOBAL['sheetRawType'] = checkRawType;
		    		    	      if( sheetName!="" && checktype!=""){
		    		    	    	 Ext.Ajax.request({
		    		    	    		url : 'center/evaluateCSheetAction!createEvaluateSheet.action',
		    		    	    		method : 'post',
		    		    	    		type :'json',
		    		    	    		params : {'sheetName' : sheetName,'sheetType': checktype,'checkType' : 'center'},
		    		    	    		callback : function(options, success,responseText){
		    		    	    		   if(success){
//		    		    	    			 Ext.getCmp("sheetname").reset();
//		    		             			 Ext.getCmp("checkCombox").reset();
		    		    	    			 win.close();
		    		    	    			 store.reload();
		    		    				     Ext.getCmp("doc-body").loadClass("assess/centerassess.jsp", "��Ч����.���Ŀ���" ,true);
		    		    	    		   } else {
		    		    	    			   updateStatusBar("�������˱�ʧ��...", true);
		    		    	    			   return;
		    		    	    		   }
		    		    	    		}
		    		    	    	});
		    		    	      }
		    		            }
		    		        },{
		    		            text:"ȡ ��",
		    		            handler:function(buttonObj) {
//		    		              Ext.getCmp("sheetname").reset();
//		    		              Ext.getCmp("checkCombox").reset();
		    		        	  win.close();
		    		            }
		    		      }]
		    		
		    		});
		    		win.focus();
		    		win.show();
	            }
		}],

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
   	//render
	sheetGridPanel.render();   
});

//--start evaluate----begin--------------
function startEvaluateOperations(sheetId) {
	Ext.Ajax.request({
		url  : 'center/evaluateCSheetAction!getEvaluateSheets.action',
		method : 'POST',
		type : 'json',
		params : {'sheetId' : sheetId},
		callback : function(opts, success, response){
			if(success){
				var json = Ext.util.JSON.decode(response.responseText);
		    	json = eval(json);
		    	GLOBAL_MAP['SHEET_ID'] = json.sheetId;
		    	GLOBAL_MAP['SHEET_NAME']= json.sheetName;
		    	GLOBAL_MAP['SHEET_TYPE'] = json.sheetType;//month
		    	GLOBAL_MAP['CHECK_TYPE'] = json.checkType;//center
		    	GLOBAL_MAP['SHEET_RAWTYPE'] = ExtJSUtil.getRawSheetType(json.sheetType);
				Ext.getCmp("doc-body").loadClass("assess/centerassess.jsp", "��Ч����.���Ŀ���" ,true);
			} else {
				alert("errors encounter when requesting info from database!");
				return;
			}
		}
	});
}

//---start evaluate----end--------------

//-------continue to evaluate-----begin------
function doEvaluateOperations(sheetId){
	Ext.Ajax.request({
		url  : 'center/evaluateCSheetAction!getEvaluateSheets.action',
		method : 'POST',
		type : 'json',
		params : {'sheetId' : sheetId},
		callback : function(opts, success, response){
			if(success){
				var json = Ext.util.JSON.decode(response.responseText);
		    	json = eval(json);
		    	GLOBAL_MAP['SHEET_ID'] = json.sheetId;
		    	GLOBAL_MAP['SHEET_NAME']= json.sheetName;
		    	GLOBAL_MAP['SHEET_TYPE'] = json.sheetType;
		    	GLOBAL_MAP['CHECK_TYPE'] = json.checkType;//center
		    	GLOBAL_MAP['SHEET_RAWTYPE'] = ExtJSUtil.getRawSheetType(json.sheetType);
				Ext.getCmp("doc-body").loadClass("assess/centerassess2.jsp", "��Ч����.���Ŀ���->��" ,true);
			} else {
				alert("errors encounter when requesting info from database!");
				return;
			}
		}
	});
}

//-------continue to evaluate-----end------

//--------click delete operations----begin----
function doDelOperations(value) {
	var mask = new Ext.LoadMask(Ext.getBody(), {                          
		msg : 'waiting ... ',                           
		removeMask : true 
	});
	Ext.Msg.confirm('��ʾ','�Ƿ�ɾ���ÿ��˱�?',function(btn){  
        if(btn!='yes'){  
            return;  
        } 
        mask.show();
        Ext.Ajax.request({  
            url:'center/evaluateCSheetAction!deleteEvaluateSheet.action',  
            method : 'post',
            params : {'sheetId' : value},
			callback : function(data, success, responseTxt) {
				if (success) {
					Ext.getCmp("sheet-panel").store.reload();
					mask.hide();
					return;
				} else {
					Ext.Msg.alert('��ʾ', "ɾ����ʧ�ܣ�");
					return;
				}
			}  
        });  
    });  
}
//----------click delete operations----end----