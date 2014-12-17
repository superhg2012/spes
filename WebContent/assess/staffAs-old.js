
Ext.onReady(function(){
	Ext.QuickTips.init();
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
		collapsible : true,
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
		    alert();
		    staffPagePanel.doLayout();
		    topPanel.doLayout();
		  }
		}
	});
	
	
	//窗口数的根节点配置
	var windowRoot = new Ext.tree.AsyncTreeNode({
		id : 'winroot',
		text :'北京市朝阳区行政服务中心',
		icon : 'image/icon/brick.png',
		expanded : true,
		children : [] 
	});
	
	var windowTreeLoader = new Ext.tree.TreeLoader();
	var windowTree = new Ext.tree.TreePanel({
		id : 'windowTree',
		title : '中心窗口树',
		rootVisible : true,
		collapisble : false,
		height : 300,
		root : windowRoot,
		autoScroll : true,
		lines : true,
		minSize: 175,
		maxWidth : 260,
        border : false,
        autoScroll:true,
        animCollapse:true,
		loader:windowTreeLoader
	});
	
	windowTree.on('click',function(node,e){
		if(!node.isLeaf()){
			e.stopEvent();
		} else if(node.isLeaf()){
			e.stopEvent();
			loadStaffOfWindow(node);
			loadStaffItemTree(node);
		}
	});
	//请求窗口信息
	Ext.Ajax.request({
		url : 'sys/windowAction!getWindowByCenterId.action',
		method : 'POST',
		params : {'centerId': 1},
		timeout : 10000,
		score : this,
		callback : function(options, success,response){
			if(success){
				var array = eval(response.responseText);
				var rootnode = windowTree.getRootNode();
				if(rootnode){
					for(var i = 0; i < array.length; i++){
					   var node = array[i];
					   var childAttrConfig = {id : node.windowId, qtip : node.windowName,isClass :true, leaf : true, text : node.windowName,icon:'image/icon/tables.gif'};
					   var childNode = new Ext.tree.TreeNode(childAttrConfig);
  					   rootnode.appendChild(childNode);
					}//end for
				}//end if
			} else {
				Ext.Msg.alert("错误提示",response.responseText);
				return;
			}
		}
	});


  var staffStore = new Ext.data.Store({});
  var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});// 设置复选框
  var staffCm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),sm,
   {header : "人员编号", width : 100,dataIndex : 'userId',fixed :true},
   {header : "人员姓名", width : 200,dataIndex : 'userName'},
   {header : "所属窗口", width : 200,dataIndex : 'windowName',},
   {header : "考核状态", width : 100,dataIndex : 'state'},
   {header : "窗口编号", width : 100,dataIndex : 'windowId', hidden :true},
   {header : "中心编号", width : 100,dataIndex:'centerId',hidden : true}
  ]);
 
  var staffGridPanel = new Ext.grid.GridPanel({
	  id : 'staffGridPanel',
	  title : '窗口人员',
	  height : 300,
	  border : false, 
	  store :staffStore,
	  cm : staffCm,
	  viewConfig : {
        forceFit: true
      },
	  bbar : new Ext.PagingToolbar({
               pageSize : 16,
               store : staffStore,
               displayInfo : true,
               displayMsg : '显示 {0} - {1} 共 {2} 条',
               emptyMsg : "没有数据显示！",
               beforePageText : "页码 ",
               afterPageText : "共 {0} 页",
               firstText : "首页",
               lastText : "末页",
               nextText : "下一页",
               prevText : "上一页",
               refreshText : "刷新",
        })
  });	
  
  var store = new Ext.data.Store({});
  var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
   {header : "指标编号", width : 150,dataIndex : 'itemId',fixed:true},
   {header : "指标名称", width :.2,dataIndex : 'itemName'},
   {header : "指标取值", width :.2,xtype : 'numbercolumn',dataIndex : 'itemValue',editor : new Ext.grid.GridEditor(new Ext.form.NumberField({allowBlank:false,blankText : '请输入数值型数据'}))},
   {header : "窗口编号", width :.2,dataIndex : 'windowId', hidden :true},
   {header : "中心编号", width :.2,dataIndex:'centerId',hidden : true}
  ]);
    //中心指标评价面板
	var staffPageAssessPanel = new Ext.grid.GridPanel({
		id : 'staff_panel',
		autoWidth : true,
		autoHeight : true,
		frame : true,
		border:false,
		loadMash : true,
		waitMsg :'数据加载中,请稍后...',
		store : store,
		cm : cm,
		viewConfig : {
          forceFit: true
        },
		stripeRows : true,
		autoScroll : true,
		tbar : ['&nbsp;',{
			text : '保存考核质保数据',
			icon : 'image/icon/disk.png',
			handler : function(){
			
			}
		}]
	});
	
	var topPanel = new Ext.Panel({
		id : 'top-panel',
		layout : 'column',
		border : false,
		width : '100%',
		items : [{
			 columnWidth:.2,
			 items : windowTree
			},
			{
				columnWidth : .8,
				style : 'margin-left:1px',
				items :staffGridPanel
			}]
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
	    items : [topPanel,staffPageAssessPanel]
		
	});
	
	//页面总体布局
	var staffPagePanel = new Ext.Panel({
		id : 'staffPagePanel',
	    layout: 'border',
	    width : '100%',
	    height: 600,
	    border: false,
	    renderTo : "staff-div",
	    items : [staffItemTree,centerPanel]
	});
	
	//加载窗口人员
	loadStaffOfWindow = function(node){
		var windowId;
		if(undefined!= node.attributes.id){
			windowId = node.attributes.id;
		}
		var centerId = 1; 
		staffStore.removeAll();
		Ext.Ajax.request({
			url : 'staffAction!getUserByWindowIdAndCenterId.action',
			method : 'POST',
			params : {'windowId' : windowId,'centerId':centerId},
			timeout : 10000,
			scope : this,
			callback : function(options, success, response){
				if(success){
					var result = response.responseText; 
					if(null == result || '' == result){
					   showErrorMsg("加载窗口人员","错误信息");
						return;
					}
					var json = Ext.util.JSON.decode(response.responseText);
					var array = eval(json);
					for(var i=0;i<array.length;i++){
						var record = new Ext.data.Record({userId : array[i].userId,userName : array[i].userName, windowName:array[i].windowName});
					    staffStore.add(record);
					}//end for
				} else {
					showErrorMsg("加载窗口人员","错误信息");
					return;
				}//end else
			}//callback
		});
	}
	//请求窗口普通人员评价指标
	loadStaffItemTree = function (node) {
		var windowId;
		if(undefined!= node.attributes.id){
			windowId = node.attributes.id;
		}
		var centerId = 1;
		Ext.Ajax.request({
			url : 'staff/staffItemAction!getStaffItem.action',
			method : 'post',
			params :{},
			timeout : 10000,
			scope : this,
			callback : function(options, success , response){
				if(success){
					var json = Ext.util.JSON.decode(response.responseText);
					alert(response.responseText);
				} else {
					
				}
			}
			
		});
	}
});