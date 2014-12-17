Ext.onReady(function(){
		
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
	            {header : "表单类型", width : 60, dataIndex: 'sheetType',renderer : function(value, metadata, record, rowIndex, colIndex, store){ return renderSheetType(value, metadata, record, rowIndex, colIndex, store);}},
	            {header : "考核用户", width: 100,dataIndex : 'userName'},
	            {header : "创建时间", width: 120,dataIndex : 'createTime'}, 
	            {header : "考核状态", width : 100,dataIndex : 'sheetState',renderer : function (value, metadata, record, rowIndex, colIndex, store) {return renderState(value, metadata, record, rowIndex, colIndex, store);}},
	            {header : '操作', width : 100, dataIndex:'sheetId',renderer : function (value, metadata, record, rowIndex, colIndex, store) { return operateRender(value, metadata, record, rowIndex, colIndex, store);}}
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

		//加载数据
	    store.load({params:{
	    	start : 0,
	    	limit : 16
	    }});
	    
	    
//    var comStore = new Ext.data.ArrayStore({
//		fields : ['type','key'],
//		data : [
//					['month','月度考核'],
//					['quarter','季度考核'],
//					['year','年度考核']
//				]
//	});
	
//	var checkCombox = new Ext.form.ComboBox({
//	  id : 'checkCombox',
//	  fieldLabel : '考核类型',
//	  store : comStore,
//	  triggerAction:'all',
//	  displayField:'key',
//	  valueField:'type',
//	  mode:'local',
//	  width : 80,
//	  allowBlank : false,
//      blankText : '请选择考核类型！'
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
		waitMsg :'数据加载中，请稍后...',
		viewConfig : { forceFit : true },
		store : store,
		cm : cm,
		stripeRows : true,
		renderTo : "centerSheetList",
		tbar :['&nbsp;',{
			   id:'add-btn',
			   text: '创建中心考核表单',
			   icon : 'image/icon/table_add.png',
			   style:{border : 'solid 1px #abc'},// set button border css style
	           handler: function(buttonObj) {
		    		win = new Ext.hg.EvaluateSheetWindow({
		    			sheetId : '#',
		    		    items : 
		    		        [{
		    		            xtype : 'fieldset',
		    		            title : '表单项',
		    		            items: [{
		    		    	            xtype : 'textfield',
		    		    				id : 'sheetname',
		    		    	            fieldLabel:"表单名称",
		    		    	            allowBlank :false,
		    		    	            blankText : '表单名不能为空！'
		    		                },{
		    		    	          xtype : 'combo',
		    		    	      	  id : 'checkCombox',
		    		    	    	  fieldLabel : '考核类型',
		    		    	    	  store : new Ext.data.ArrayStore({
		    		    	    		   fields : ['type','key'],
		    		    	    		   data : [
		    		    	    					['month','月度考核'],
		    		    	    					['quarter','季度考核'],
		    		    	    					['year','年度考核']
		    		    	    				]
		    		    	    	  }) ,
		    		    	    	  triggerAction:'all',
		    		    	    	  displayField:'key',
		    		    	    	  valueField:'type',
		    		    	    	  mode:'local',
		    		    	    	  width : 200,
		    		    	    	  allowBlank : false,
		    		    	          blankText : '请选择考核类型！'
		    		             }]
		    		        }],
		    		        buttons:[{          //buttons：定包含在面板中的按钮的配置数组
		    		            text:"创 建",
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
		    		    				     Ext.getCmp("doc-body").loadClass("assess/centerassess.jsp", "绩效考核.中心考核" ,true);
		    		    	    		   } else {
		    		    	    			   updateStatusBar("创建考核表单失败...", true);
		    		    	    			   return;
		    		    	    		   }
		    		    	    		}
		    		    	    	});
		    		    	      }
		    		            }
		    		        },{
		    		            text:"取 消",
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
});

//---开始考核----begin--------------
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
				Ext.getCmp("doc-body").loadClass("assess/centerassess.jsp", "绩效考核.中心考核" ,true);
			} else {
				alert("errors encounter when requesting info from database!");
				return;
			}
		}
	});
}

//---开始考核----end--------------

//-------继续考核表单-----begin------
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
				Ext.getCmp("doc-body").loadClass("assess/centerassess2.jsp", "绩效考核.中心考核->续" ,true);
			} else {
				alert("errors encounter when requesting info from database!");
				return;
			}
		}
	});
}

//-------继续考核表单-----end------

//--------click delete operations----begin----
function doDelOperations(value) {
	var mask = new Ext.LoadMask(Ext.getBody(), {                          
		msg : 'waiting ... ',                           
		removeMask : true 
	});
	Ext.Msg.confirm('提示','是否删除该考核表单?',function(btn){  
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
					Ext.Msg.alert('提示', "删除表单失败！");
					return;
				}
			}  
        });  
    });  
}
//----------click delete operations----end----