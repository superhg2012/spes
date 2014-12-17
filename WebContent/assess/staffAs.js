Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget  = 'side';

	var firstItemId = undefined;
	var selectedNode = undefined;
	
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
		collapsible : false,
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
//��ȡ������Ϣ
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
	//�����˵�ѡ�񴰿�
	var combox2 = new Ext.form.ComboBox({
	    width :160,
		mode : 'local',
		store : comboStore,
		triggerAction : 'all',
		displayField : 'text',
		valueField : 'value',
		emptyText : '----��ѡ�񴰿�----'
	});
	
	comboStore.load();
	combox2.on('select', function(combox){
		staffStore.removeAll(); 
		var windowId = combox.getValue();
		var rootNode = staffItemTree.getRootNode(); 
		rootNode.setText(combox.getRawValue() + "����ָ��");
		while(rootNode.hasChildNodes()){
			var childNode = rootNode.lastChild;
			rootNode.removeChild(childNode);
		}
		loadStaffOfWindow(windowId);
//		loadStaffItemTree(windowId);
//		staffItemTree.getRootNode().expand();
	});

  var staffStore = new Ext.data.Store({});
  var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});// ���ø�ѡ��
  var staffCm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),sm,
   {header : "��Ա���", width : 100,dataIndex : 'userId',fixed :true},
   {header : "��Ա����", width : 200,dataIndex : 'realName'},
   {header : "��������", width : 200,dataIndex : 'windowName'},
  // {header : "����״̬", width : 100,dataIndex : 'state'},
   {header : "���ڱ��", width : 100,dataIndex : 'windowId', hidden :true},
   {header : "���ı��", width : 100,dataIndex:  'centerId', hidden : true}
  ]);
 
 // var searchField = new Ext.form.TextField({id:'staff-search-field',allowBlank:true,blankText :'������������Ա����',emptyText:'����������Ա'});
  
  var searchField = new Ext.form.TextField({
	    allowBlank :true,
		blankText :'������������Ա����',
		emptyText:'����������Ա',
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
      tbar :['&nbsp;��������:',
             combox2,
    	  '&nbsp;',
    	  '-',
    	  '&nbsp;',
    	  searchField,
    	  '&nbsp;',
    	  {
    	   text : '������Ա',
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
      },'-',"��������:",sheetTypeField],
	  bbar : new Ext.PagingToolbar({
               pageSize : 10,
               store : staffStore,
               displayInfo : true,
               displayMsg : '��ʾ {0} - {1} �� {2} ��',
               emptyMsg : "û��������ʾ��",
               beforePageText : "ҳ�� ",
               afterPageText : "�� {0} ҳ",
               refreshText : "ˢ��",
               firstText : "��ҳ",
               lastText : "ĩҳ",
               nextText : "��һҳ",
               prevText : "��һҳ"
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
   {header : "ָ����", width : 80,dataIndex : 'itemId'},
   {header : "ָ������", width : 200,dataIndex : 'itemName'},
   {header : "ָ��ȡֵ", width : 160,dataIndex : 'itemValue', editor:new Ext.grid.GridEditor(new Ext.form.NumberField({allowNegative:false,allowBlank:false,blankText:'��������ֵ������'}))},
   {header : "����ָ��", width : 200,dataIndex :'pItemName',renderer:formatItem},
   {header : "��ָ����", width : 100,dataIndex:'pItemId',hidden : true},
   {header : "ָ������", width : 100,dataIndex:'itemType',hidden : true},
   {header : "Ȩ��", width : 50,dataIndex:'itemWeight',hidden : true},
   {header : "���ڱ��", dataIndex : 'windowId', hidden :true},
   {header : "���ı��", dataIndex : 'centerId', hidden : true}
  ]);
    //����ָ���������
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
			text : '���濼������',
			icon : 'image/icon/disk.png',
			style:{border : 'solid 1px #abc'},// set button border css style
			handler : function(){
			   if(itemStore.getCount()<=0){
				   Ext.Msg.alert("��ʾ��Ϣ","����ָ������ѡ��Ҫ���˵�ָ�꣡");
				   return;
			   } else {
				   var records = itemStore.getRange();
				   if(records.length > 0){
					   var dataArray = [];
					   for(var index = 0;index < records.length; index++){
						   var record = records[index];
						   if(null == record.get('itemValue')){
							   Ext.Msg.alert("��ʾ��Ϣ","������ָ��ȡֵ��");
			    			   return;
						   }
						   var jsonData = Ext.util.JSON.encode(records[index].data);
			    		   dataArray.push(jsonData);
					   }//end for
					   
					   var sm = staffGridPanel.getSelectionModel();
					   var selections = sm.getSelections();
					   var userId;
					   if(selections.length <=0){
						   Ext.Msg.alert("��ʾ��Ϣ","��ѡ��Ҫ���˵���Ա��");
						   return;
					   } else {
						   var record = selections[0];
						   userId = record.get("userId");
					   }
					   
					   if(confirm("���˺����ݲ����޸ģ�ȷ��ָ��������ȷ��")== true){
					   } else {
						   return;
					   }
					   var data = "{" +'"staffItem":[' + dataArray + "]}";
					   Ext.Ajax.request({
			    			   url : 'staff/staffScoreAction!saveStaffItemScore2.action',
			    			   method : 'POST',
			    			   params : {"jsonData": data, itemId : firstItemId, userId :userId, sheetId : GLOBAL_MAP['SHEET_ID'], sheetType : GLOBAL_MAP['SHEET_TYPE']},
			    			   success : function(response) {
			    				   Ext.Msg.alert("��ʾ��Ϣ","ָ�꿼�˳ɹ���");
			    				   selectedNode.getUI().addClass("highlight");
			    				   selectedNode.attributes.checked = true;
//			    				   selectedNode.disable();
			    				   itemStore.removeAll();
			    				   return;
			    			   },
			    			   failure : function(response){
			    				   Ext.Msg.alert("������ʾ",response.responseText);
			    				   return;
			    			   }
			    			   
			    		   });
					   
				   }//end if
			   }
			}
		},
		'-',
		'��ǰ����ָ�꣺',
		textfield
	]
		
	});
	
	var topPanel = new Ext.Panel({
		id : 'top-panel',
		border : false,
		width : '100%',
		items : [staffGridPanel]
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
	    items : [
	             topPanel,
	             staffPageAssessPanel
	            ]
	});
	
	//ҳ�����岼��
	var staffPagePanel = new Ext.Panel({
		id : 'staffPagePanel',
	    width : '100%',
	    height: 600,
	    border: false,
	    layout: 'border',
	    renderTo : "staff-div",
	    items : [staffItemTree,centerPanel]
	});
	
	//���ش�����Ա
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
					   Ext.Msg.alert("��ʾ��Ϣ","�ô�����Ա������Ա�ɿ��ˣ�");
					   return;
					}
					var json = Ext.util.JSON.decode(response.responseText);
					var array = eval(json);
					for(var i=0;i < array.length;i++){
						var record = new Ext.data.Record({userId : array[i].userId,realName : array[i].realName, windowName:array[i].windowName});
					    staffStore.add(record);
					}//end for
				} else {
					showErrorMsg("���ش�����Ա����","������Ϣ");
					return;
				}//end else
			}// end callback
		});
	};
	//���󴰿���ͨ��Ա����ָ��
	loadStaffItemTree = function (windowId, userId) {
		var centerId = Ext.get("centerId").dom.value;
//		alert(windowId + "|" + centerId + "|" + userId);
		itemStore.removeAll();
		//�Ƴ�������Ա������ָ��
		var rootNode = staffItemTree.getRootNode();
		while(rootNode.hasChildNodes()){
			var childNode = rootNode.lastChild;
			rootNode.removeChild(childNode);
		}
		//���¼�����Ա������ָ��
		Ext.Ajax.request({
			url : 'staff/staffItemAction!getFirstStaffItem.action',
			method : 'post',
			params : {'windowId' : windowId, 'centerId' : centerId, 'userId': userId, sheetId : GLOBAL_MAP['SHEET_ID']},
			scope   : this,
			callback : function(options, success , response){
				if(success){
					if(response.responseText==""){
						Ext.Msg.alert("��ʾ��Ϣ",combox2.getRawValue() + "������ָ��δ���ã�");
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
//					    	 	  childNode.disable();   //����Ӧʱ����
					            }
							} 
						}//end for
					}//end if
				} else {
					showErrorMsg("���ش�����Ա����ָ�����","������Ϣ");
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
	//��������ָ��
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
			Ext.Msg.alert("��ʾ��Ϣ","��ѡ��Ҫ���˵Ĵ��ڣ�");
			return;
		}
		textfield.setValue(node.attributes.text);
		//clear data of store first
		itemStore.removeAll();
		/*if(itemType == "����"){
			var record = new Ext.data.Record({itemId : node.attributes.id, itemName : node.attributes.text,windowId : windowId,centerId : centerId,itemIcon : 'image/icon/disk.png'});
			itemStore.add(record);
			return;
		}*/
		//����ָ��
		Ext.Ajax.request({
			url : 'staff/staffItemAction!getStaffItemByItemId.action',
		    method : 'POST',
		    type : 'json',
		    scope : this,
		    params : {'itemId' : itemId,'windowId':windowId,'centerId' : centerId},
		    callback : function(options , success , response){
		    	if(success){
		    		if(response.responseText==""){
		    			Ext.Msg.alert("��ʾ��Ϣ","��ָ��������ָ�꣡");
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
		    		Ext.Msg.alert("��ʾ��Ϣ","��������ָ�����ʧ�ܣ�");
		    		return;
		    	}
		    }//end call back;
		});
	};//end function
	
	/**
	 * ��Ⱦ����ɫ
	 * @param {Object} value
	 * @param {Object} metadata
	 * @return {TypeName} 
	 */
	function formatItem(value,metadata){
		metadata.attr = 'style="color:red"';
		return value;
	};
});