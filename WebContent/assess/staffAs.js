Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget  = 'side';

	var firstItemId = undefined;
	var selectedNode = undefined;
	
	var root = new Ext.tree.AsyncTreeNode({
		id : 'root',
		text :'暂无窗口普通人员评价指标',
		icon : 'image/icon/brick.png',
		expanded : true,
		children : [] 
	});

	var treeLoader = new Ext.tree.TreeLoader({});
	
	var staffItemTree = new Ext.tree.TreePanel({
		id : 'staffItemTree',
		title : '窗口普通人员评价指标导航',
		rootVisible : true,
		collapsible : false,
	//	collapseMode:'mini', //小箭头
		animate : true,
		frame : true,
		region : 'east',
		width : 240,
		root : root,
		autoScroll : true,
		lines : true,
		minSize: 175,
		maxWidth : 260,
		split : true,
        border : true,
        autoScroll:true,
        animCollapse:true,
		loader:treeLoader,
		listeners : {
		  beforecollapse : function(staffItemTree,animate){
		    staffPagePanel.doLayout();
		    topPanel.doLayout();
		  },
		  click : function(node,e){
			  if(!node.isLeaf()){
				  e.stopEvent();
			  } else if(node.isLeaf()){
				  e.stopEvent();
				  loadStaffItem(node);
			  }
		  }
		}
	});
//获取窗口信息
	var comboStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'sys/windowAction!getWindowByCenterId.action?centerId='+ Ext.get("centerId").dom.value,
			method : 'POST'
		}),
		reader : new Ext.data.JsonReader({
			root : 'root',
			fields : [
			          {name : 'value',mapping:'windowId'},
				      {name : 'text', mapping : 'windowName'}
			         ]
		})
	});
	//下拉菜单选择窗口
	var combox2 = new Ext.form.ComboBox({
	    width :160,
		mode : 'local',
		store : comboStore,
		triggerAction : 'all',
		displayField : 'text',
		valueField : 'value',
		emptyText : '----请选择窗口----'
	});
	
	comboStore.load();
	combox2.on('select', function(combox){
		staffStore.removeAll(); 
		var windowId = combox.getValue();
		var rootNode = staffItemTree.getRootNode(); 
		rootNode.setText(combox.getRawValue() + "评价指标");
		while(rootNode.hasChildNodes()){
			var childNode = rootNode.lastChild;
			rootNode.removeChild(childNode);
		}
		loadStaffOfWindow(windowId);
//		loadStaffItemTree(windowId);
//		staffItemTree.getRootNode().expand();
	});

  var staffStore = new Ext.data.Store({});
  var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});// 设置复选框
  var staffCm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),sm,
   {header : "人员编号", width : 100,dataIndex : 'userId',fixed :true},
   {header : "人员姓名", width : 200,dataIndex : 'realName'},
   {header : "所属窗口", width : 200,dataIndex : 'windowName'},
  // {header : "考核状态", width : 100,dataIndex : 'state'},
   {header : "窗口编号", width : 100,dataIndex : 'windowId', hidden :true},
   {header : "中心编号", width : 100,dataIndex:  'centerId', hidden : true}
  ]);
 
 // var searchField = new Ext.form.TextField({id:'staff-search-field',allowBlank:true,blankText :'请输入搜索人员姓名',emptyText:'输入搜索人员'});
  
  var searchField = new Ext.form.TextField({
	    allowBlank :true,
		blankText :'请输入搜索人员姓名',
		emptyText:'输入搜索人员',
		enableKeyEvents: true,
		listeners :{
			'keyup' : function(record,id){
				staffStore.filterBy(function(record,id){
					var value = searchField.getValue(); 
					if(value =="" || value==null){
						return true;
					}
					return (record.get("realName")===searchField.getValue()) ? true:false;
				});
			}
		}
  });
  
  var  textfield = new Ext.form.TextField({disabled:true,width : 200,style:'color:red;'});
  var sheetTypeField = new Ext.form.TextField({disabled:true,width : 120,style:'color:red;'});
  sheetTypeField.value = GLOBAL_MAP['SHEET_RAWTYPE'];
  var staffGridPanel = new Ext.grid.GridPanel({
	  id : 'staffGridPanel',
	  height :300,
	  border : true, 
	  store :staffStore,
	  cm : staffCm,
	  sm : sm,
	  viewConfig : {forceFit: true},
      tbar :['&nbsp;窗口名称:',
             combox2,
    	  '&nbsp;',
    	  '-',
    	  '&nbsp;',
    	  searchField,
    	  '&nbsp;',
    	  {
    	   text : '搜索人员',
    	   icon : 'image/icon/search_icon.gif',
    	   style:{border : 'solid 1px #abc'},// set button border css style
    	   handler: function(){
    	     if(searchField.isValid()){
    	    	 staffStore.filterBy(function(record,id){
					var value = searchField.getValue(); 
					if(value =="" || value==null){
						return true;
					}
					return (record.get("realName")===searchField.getValue()) ? true:false;
				});
    	     }
    	     return;
    	  }
      },'-',"考核类型:",sheetTypeField],
	  bbar : new Ext.PagingToolbar({
               pageSize : 10,
               store : staffStore,
               displayInfo : true,
               displayMsg : '显示 {0} - {1} 共 {2} 条',
               emptyMsg : "没有数据显示！",
               beforePageText : "页码 ",
               afterPageText : "共 {0} 页",
               refreshText : "刷新",
               firstText : "首页",
               lastText : "末页",
               nextText : "下一页",
               prevText : "上一页"
        }),

        listeners:{
			rowclick : function(staffGridPanel, rowIndex, e){
		       var store = staffGridPanel.getStore();
		       var sm = staffGridPanel.getSelectionModel();
			   var selections = sm.getSelections();
			   var record = selections[0];
			   var userId = record.get("userId");
			   var windowId = combox2.getValue();
			   loadStaffItemTree(windowId,userId);
			   staffItemTree.getRootNode().expand();
			}
		}
        
  });	
  
  var itemStore = new Ext.data.Store({});
  var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
   {header : "指标编号", width : 80,dataIndex : 'itemId'},
   {header : "指标名称", width : 200,dataIndex : 'itemName'},
   {header : "指标取值", width : 160,dataIndex : 'itemValue', editor:new Ext.grid.GridEditor(new Ext.form.NumberField({allowNegative:false,allowBlank:false,blankText:'请输入数值型数据'}))},
   {header : "所属指标", width : 200,dataIndex :'pItemName',renderer:formatItem},
   {header : "父指标编号", width : 100,dataIndex:'pItemId',hidden : true},
   {header : "指标类型", width : 100,dataIndex:'itemType',hidden : true},
   {header : "权重", width : 50,dataIndex:'itemWeight',hidden : true},
   {header : "窗口编号", dataIndex : 'windowId', hidden :true},
   {header : "中心编号", dataIndex : 'centerId', hidden : true}
  ]);
    //中心指标评价面板
  var staffPageAssessPanel = new Ext.grid.EditorGridPanel({
		id : 'staff_panel',
		autoWidth : true,
		height : 300,
		frame : true,
		enableColumnMove : true,
		store : itemStore,
		cm : cm,
		clicksToEdit: '1',
		viewConfig : {forceFit: true},
		stripeRows : true,
		autoScroll : true,
		tbar : ['&nbsp;',{
			text : '保存考核数据',
			icon : 'image/icon/disk.png',
			style:{border : 'solid 1px #abc'},// set button border css style
			handler : function(){
			   if(itemStore.getCount()<=0){
				   Ext.Msg.alert("提示信息","请在指标树中选择要考核的指标！");
				   return;
			   } else {
				   var records = itemStore.getRange();
				   if(records.length > 0){
					   var dataArray = [];
					   for(var index = 0;index < records.length; index++){
						   var record = records[index];
						   if(null == record.get('itemValue')){
							   Ext.Msg.alert("提示信息","请输入指标取值！");
			    			   return;
						   }
						   var jsonData = Ext.util.JSON.encode(records[index].data);
			    		   dataArray.push(jsonData);
					   }//end for
					   
					   var sm = staffGridPanel.getSelectionModel();
					   var selections = sm.getSelections();
					   var userId;
					   if(selections.length <=0){
						   Ext.Msg.alert("提示信息","请选择要考核的人员！");
						   return;
					   } else {
						   var record = selections[0];
						   userId = record.get("userId");
					   }
					   
					   if(confirm("考核后数据不可修改，确定指标输入正确？")== true){
					   } else {
						   return;
					   }
					   var data = "{" +'"staffItem":[' + dataArray + "]}";
					   Ext.Ajax.request({
			    			   url : 'staff/staffScoreAction!saveStaffItemScore2.action',
			    			   method : 'POST',
			    			   params : {"jsonData": data, itemId : firstItemId, userId :userId, sheetId : GLOBAL_MAP['SHEET_ID'], sheetType : GLOBAL_MAP['SHEET_TYPE']},
			    			   success : function(response) {
			    				   Ext.Msg.alert("提示信息","指标考核成功！");
			    				   selectedNode.getUI().addClass("highlight");
			    				   selectedNode.attributes.checked = true;
//			    				   selectedNode.disable();
			    				   itemStore.removeAll();
			    				   return;
			    			   },
			    			   failure : function(response){
			    				   Ext.Msg.alert("错误提示",response.responseText);
			    				   return;
			    			   }
			    			   
			    		   });
					   
				   }//end if
			   }
			}
		},
		'-',
		'当前考核指标：',
		textfield
	]
		
	});
	
	var topPanel = new Ext.Panel({
		id : 'top-panel',
		border : false,
		width : '100%',
		items : [staffGridPanel]
	});
	//页面中部布局总容器
	var centerPanel = new Ext.Panel({
		title :'',
		border:false,
	    region : 'center',
	    width : '100%',
	    height : '100%',
	    layout : 'vbox',
	    margins:'1,1,1,1',
	    items : [
	             topPanel,
	             staffPageAssessPanel
	            ]
	});
	
	//页面总体布局
	var staffPagePanel = new Ext.Panel({
		id : 'staffPagePanel',
	    width : '100%',
	    height: 600,
	    border: false,
	    layout: 'border',
	    renderTo : "staff-div",
	    items : [staffItemTree,centerPanel]
	});
	
	//加载窗口人员
	loadStaffOfWindow = function(windowId){
		var centerId = Ext.get("centerId").dom.value; 
		staffStore.removeAll();
		Ext.Ajax.request({
			url : 'staffAction!getUserByWindowIdAndCenterId.action',
			method : 'POST',
			params : {'windowId' : windowId,'centerId':centerId},
			scope : this,
			callback : function(options, success, response){
				if(success){
					var result = response.responseText; 
					if(null == result || '' == result){
					   Ext.Msg.alert("提示信息","该窗口人员暂无人员可考核！");
					   return;
					}
					var json = Ext.util.JSON.decode(response.responseText);
					var array = eval(json);
					for(var i=0;i < array.length;i++){
						var record = new Ext.data.Record({userId : array[i].userId,realName : array[i].realName, windowName:array[i].windowName});
					    staffStore.add(record);
					}//end for
				} else {
					showErrorMsg("加载窗口人员出错","错误信息");
					return;
				}//end else
			}// end callback
		});
	};
	//请求窗口普通人员评价指标
	loadStaffItemTree = function (windowId, userId) {
		var centerId = Ext.get("centerId").dom.value;
//		alert(windowId + "|" + centerId + "|" + userId);
		itemStore.removeAll();
		//移除现有人员的评价指标
		var rootNode = staffItemTree.getRootNode();
		while(rootNode.hasChildNodes()){
			var childNode = rootNode.lastChild;
			rootNode.removeChild(childNode);
		}
		//重新加载人员的评价指标
		Ext.Ajax.request({
			url : 'staff/staffItemAction!getFirstStaffItem.action',
			method : 'post',
			params : {'windowId' : windowId, 'centerId' : centerId, 'userId': userId, sheetId : GLOBAL_MAP['SHEET_ID']},
			scope   : this,
			callback : function(options, success , response){
				if(success){
					if(response.responseText==""){
						Ext.Msg.alert("提示信息",combox2.getRawValue() + "的评价指标未设置！");
						return;
					}
					var json = Ext.util.JSON.decode(response.responseText);
					var array = eval(json);
					var rootnode = staffItemTree.getRootNode();
					if(rootnode){
						for(var i=0;i<array.length;i++){
							var node = array[i];
							var childNode;
							if(node.itemGrade ==1){
								var childNodeConfig =({id : node.itemId, tip : node.itemName, type : node.itemType, checked:node.checked, text:node.itemName,leaf:true,isClass:true});
								childNode =  new Ext.tree.TreeNode(childNodeConfig);
								rootnode.appendChild(childNode);
								if(childNode.attributes.checked){
					    	 	  childNode.getUI().addClass("highlight");
//					    	 	  childNode.disable();   //不响应时间了
					            }
							} 
						}//end for
					}//end if
				} else {
					showErrorMsg("加载窗口人员考核指标错误！","错误信息");
				}
			}
			
		});
	};//end funciton
	
	function findLeafChildNodes(node,rootnode){
		var childnodes = rootnode.childNodes;
		for(var i = 0; i < childnodes.length;i++){
			var childNode = childnodes[i];
			if(node.parentId == childNode.attributes.id){
				var leafNodeConfig = {"id" : node.itemId, "type" : node.itemType,  qtip : node.itemName, text : node.itemName,isClass : true, leaf :true,icon:'image/icon/chart_node.png'};
		        var leafNode = new Ext.tree.TreeNode(leafNodeConfig);
				childNode.appendChild(leafNode);
				break;
			}
		}
	}
	//请求三级指标
	function loadStaffItem(node){
		var itemId = node.attributes.id;
		var itemType = node.attributes.type;
		if(node.attributes.checked==true){
    		itemStore.removeAll();
    		return;
    	}
		firstItemId = itemId;
		selectedNode = node;
		var windowId = combox2.getValue();
		var centerId = Ext.get("centerId").dom.value;
		if(''== windowId || null == windowId){
			Ext.Msg.alert("提示信息","请选择要考核的窗口！");
			return;
		}
		textfield.setValue(node.attributes.text);
		//clear data of store first
		itemStore.removeAll();
		/*if(itemType == "定性"){
			var record = new Ext.data.Record({itemId : node.attributes.id, itemName : node.attributes.text,windowId : windowId,centerId : centerId,itemIcon : 'image/icon/disk.png'});
			itemStore.add(record);
			return;
		}*/
		//定量指标
		Ext.Ajax.request({
			url : 'staff/staffItemAction!getStaffItemByItemId.action',
		    method : 'POST',
		    type : 'json',
		    scope : this,
		    params : {'itemId' : itemId,'windowId':windowId,'centerId' : centerId},
		    callback : function(options , success , response){
		    	if(success){
		    		if(response.responseText==""){
		    			Ext.Msg.alert("提示信息","该指标尚无子指标！");
		    			return;
		    		}
		    		var json = Ext.util.JSON.decode(response.responseText);
		    		json = eval(json);
//		    		var records = [];
		    		if(json.length > 0){
			    		for(var i = 0;i<json.length; i++) {
			    			var record = new Ext.data.Record({itemId : json[i].itemId, itemName : json[i].itemName,'windowId' : windowId, 'centerId' : centerId,pItemName:json[i].pItemName,pItemId:json[i].pItemId,itemType:json[i].itemType,itemWeight:json[i].itemWeight});
			    			itemStore.add(record);
			    		}
		    		} //end if
		    	} else {
		    		Ext.Msg.alert("提示信息","窗口评价指标加载失败！");
		    		return;
		    	}
		    }//end call back;
		});
	};//end function
	
	/**
	 * 渲染列颜色
	 * @param {Object} value
	 * @param {Object} metadata
	 * @return {TypeName} 
	 */
	function formatItem(value,metadata){
		metadata.attr = 'style="color:red"';
		return value;
	};
});