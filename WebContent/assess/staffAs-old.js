
Ext.onReady(function(){
	Ext.QuickTips.init();
	var root = new Ext.tree.AsyncTreeNode({
		id : 'root',
		text :'���޴�����ͨ��Ա����ָ��',
		icon : 'image/icon/brick.png',
		expanded : true,
		children : [] 
	});

	var treeLoader = new Ext.tree.TreeLoader({});
	
	var staffItemTree = new Ext.tree.TreePanel({
		id : 'staffItemTree',
		title : '������ͨ��Ա����ָ�굼��',
		rootVisible : true,
		collapsible : true,
	//	collapseMode:'mini', //С��ͷ
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
	
	
	//�������ĸ��ڵ�����
	var windowRoot = new Ext.tree.AsyncTreeNode({
		id : 'winroot',
		text :'�����г�����������������',
		icon : 'image/icon/brick.png',
		expanded : true,
		children : [] 
	});
	
	var windowTreeLoader = new Ext.tree.TreeLoader();
	var windowTree = new Ext.tree.TreePanel({
		id : 'windowTree',
		title : '���Ĵ�����',
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
	//���󴰿���Ϣ
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
				Ext.Msg.alert("������ʾ",response.responseText);
				return;
			}
		}
	});


  var staffStore = new Ext.data.Store({});
  var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});// ���ø�ѡ��
  var staffCm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),sm,
   {header : "��Ա���", width : 100,dataIndex : 'userId',fixed :true},
   {header : "��Ա����", width : 200,dataIndex : 'userName'},
   {header : "��������", width : 200,dataIndex : 'windowName',},
   {header : "����״̬", width : 100,dataIndex : 'state'},
   {header : "���ڱ��", width : 100,dataIndex : 'windowId', hidden :true},
   {header : "���ı��", width : 100,dataIndex:'centerId',hidden : true}
  ]);
 
  var staffGridPanel = new Ext.grid.GridPanel({
	  id : 'staffGridPanel',
	  title : '������Ա',
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
               displayMsg : '��ʾ {0} - {1} �� {2} ��',
               emptyMsg : "û��������ʾ��",
               beforePageText : "ҳ�� ",
               afterPageText : "�� {0} ҳ",
               firstText : "��ҳ",
               lastText : "ĩҳ",
               nextText : "��һҳ",
               prevText : "��һҳ",
               refreshText : "ˢ��",
        })
  });	
  
  var store = new Ext.data.Store({});
  var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
   {header : "ָ����", width : 150,dataIndex : 'itemId',fixed:true},
   {header : "ָ������", width :.2,dataIndex : 'itemName'},
   {header : "ָ��ȡֵ", width :.2,xtype : 'numbercolumn',dataIndex : 'itemValue',editor : new Ext.grid.GridEditor(new Ext.form.NumberField({allowBlank:false,blankText : '��������ֵ������'}))},
   {header : "���ڱ��", width :.2,dataIndex : 'windowId', hidden :true},
   {header : "���ı��", width :.2,dataIndex:'centerId',hidden : true}
  ]);
    //����ָ���������
	var staffPageAssessPanel = new Ext.grid.GridPanel({
		id : 'staff_panel',
		autoWidth : true,
		autoHeight : true,
		frame : true,
		border:false,
		loadMash : true,
		waitMsg :'���ݼ�����,���Ժ�...',
		store : store,
		cm : cm,
		viewConfig : {
          forceFit: true
        },
		stripeRows : true,
		autoScroll : true,
		tbar : ['&nbsp;',{
			text : '���濼���ʱ�����',
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
	//ҳ���в�����������
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
	
	//ҳ�����岼��
	var staffPagePanel = new Ext.Panel({
		id : 'staffPagePanel',
	    layout: 'border',
	    width : '100%',
	    height: 600,
	    border: false,
	    renderTo : "staff-div",
	    items : [staffItemTree,centerPanel]
	});
	
	//���ش�����Ա
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
					   showErrorMsg("���ش�����Ա","������Ϣ");
						return;
					}
					var json = Ext.util.JSON.decode(response.responseText);
					var array = eval(json);
					for(var i=0;i<array.length;i++){
						var record = new Ext.data.Record({userId : array[i].userId,userName : array[i].userName, windowName:array[i].windowName});
					    staffStore.add(record);
					}//end for
				} else {
					showErrorMsg("���ش�����Ա","������Ϣ");
					return;
				}//end else
			}//callback
		});
	}
	//���󴰿���ͨ��Ա����ָ��
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