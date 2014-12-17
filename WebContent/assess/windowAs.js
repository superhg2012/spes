Ext.onReady(function(){
	//��¼ҳ�沼��״̬
	//Ext.state.Manager.setProvider(new Ext.state.CookieProvider({}));
	var firstItemId = undefined;
	var selectedNode = undefined;
	var root = new Ext.tree.AsyncTreeNode({
		id : 'root',
		text : Ext.get("centerName").dom.value,
		icon : 'image/icon/brick.png',
		expanded : true,
		children : [] 
	});
	
	var loader = new Ext.tree.TreeLoader({});
	
	var windowItemTree = new Ext.tree.TreePanel({
		title : '��������ָ�굼��',
		rootVisible : true,
		collapsible : true,
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
		loader:loader
	});
	
	root.expand();
	/**
	 * ����ָ��������¼�
	 * @param {Object} node
	 * @param {Object} e
	 */
	windowItemTree.on('click',function(node,e){
		if(!node.isLeaf()){
			e.stopEvent();
		} else if(node.isLeaf()){
			e.stopEvent();
			loadWindowItem(node);
		}
	});
	
	/**
	 * ��Ⱦ����ɫ
	 * @param {Object} value
	 * @param {Object} metadata
	 * @return {TypeName} 
	 */
	function formatItem(value, metadata) {
		metadata.attr = 'style="color:red"';
		return value;
	}
	
	var store = new Ext.data.Store({});
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
	   {header : "ָ����", width : 80,dataIndex : 'itemId'},
	   {header : "ָ������", width : 250,dataIndex : 'itemName'},
	   {header : "ָ��ȡֵ", width : 160,xtype : 'numbercolumn',dataIndex : 'itemValue',editor : new Ext.grid.GridEditor(new Ext.form.NumberField({allowBlank:false,blankText : '��������ֵ������'}))},
	   {header : "����ָ��", width : 200,dataIndex :'pItemName',renderer:formatItem},
	   {header : "��ָ����", width : 100,dataIndex:'pItemId',hidden : true},
	   {header : "ָ������", width : 100,dataIndex:'itemType',hidden : true},
	   {header : "Ȩ��", width : 50,dataIndex:'itemWeight',hidden : true},
	   {header : "���ڱ��", width : 50,dataIndex:'windowId',hidden :true},
	   {header : "���ı��", width : 50,dataIndex:'centerId',hidden : true}
	  ]);
	
	/**
	 * ���ض���ָ��ڵ�
	 * @param {Object} node
	 */
	loadWindowItem = function(node){
		var itemId = node.attributes.id;
		firstItemId = itemId;
		selectedNode = node;
		var itemType = node.attributes.type;
		var windowId = windowCombox.getValue();
		var centerId = Ext.get("centerId").dom.value;//�����
		if(''== windowId || null == windowId){
			Ext.Msg.alert("��ʾ��Ϣ","��ѡ��Ҫ���˵Ĵ��ڣ�");
			return;
		}
		if(node.attributes.checked==true){
			store.removeAll();
			return;
		}
		textfield.setValue(node.attributes.text);
		//clear data of store first
		store.removeAll();
	/*	if(itemType == "����"){
			var record = new Ext.data.Record({itemId : node.attributes.id, itemName : node.attributes.text,windowId:windowId,centerId:centerId,itemIcon : 'image/icon/disk.png'});
			store.add(record);
			return;
		}
		* */
		//����ָ��
		Ext.Ajax.request({
			url : 'win/windowItemAction!getWindowItemByItemId2.action',
		    method : 'POST',
		    timeout : 10000,
		    scope : this,
		    params : {'itemId' : itemId,"centerId" : Ext.get("centerId").dom.value},
		    
		    callback : function(options , success , response){
		    	if(success){
		    		if(response.responseText==""){
		    	       Ext.Msg.alert("��ʾ��Ϣ","��ָ��δ������ָ�꣡");
		    	       return;
		    		}
		    		var json = Ext.util.JSON.decode(response.responseText);
		    		json = eval(json);
		    	//	var records = [];
		    		if(json.length > 0){
			    		for(var i = 0;i<json.length; i++){
			    			var record = new Ext.data.Record({itemId : json[i].itemId, itemName : json[i].itemName,windowId : windowId, centerId:centerId,pItemName:json[i].pItemName,pItemId:json[i].pItemId,itemType:json[i].itemType,itemWeight:json[i].itemWeight});
			    			store.add(record);
			    		}
		    		}//end if
		    	}else {
		    		Ext.Msg.alert("��ʾ��Ϣ","��������ָ�����ʧ�ܣ�");
		    		return;
		    	}
		    }//end call back;
		});
	}; //end function
	
	//��ȡ������Ϣ
	var comboStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'sys/windowAction!getWindowByCenterId.action?centerId='+ Ext.get("centerId").dom.value,
			method : 'POST'
		}),
		reader : new Ext.data.JsonReader({
			root : 'root',
			fields : [{name : 'value',mapping:'windowId'},
				      {name : 'text', mapping : 'windowName'}]
		})
	});
	//�����˵�ѡ�񴰿�
	var windowCombox = new Ext.form.ComboBox({
		emptyText : '��ѡ�񿼺˴���',
		fieldLabel : '��������',
	    width :140,
		store : comboStore,
		mode  : 'local',
		triggerAction : 'all',
		valueField : 'value',
		displayField : 'text'
	});
	
	comboStore.load();
	
	/**combox for check type :{month,quarter,year} */
//	var checkArrayStore = new Ext.data.ArrayStore({
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
//	  store : checkArrayStore,
//	  triggerAction:'all',
//	  displayField:'key',
//	  valueField:'type',
//	  mode:'local',
//	  width : 80
//	});
	
	/**
	 * ���󴰿�ָ�����ݣ����ɴ���ָ����
	 * @param {Object} options
	 * @param {Object} success
	 * @param {Object} response
	 * @return {TypeName} 
	 */
	function requestItems(windowId){
	  Ext.Ajax.request({
		url : 'win/windowItemAction!getFirstWindowItem.action',
		method :'POST',
		type : 'json',
		params : {'centerId' :Ext.get("centerId").dom.value, windowId:windowId, sheetId : GLOBAL_MAP['SHEET_ID'], sheetName:GLOBAL_MAP['SHEET_NAME']},
		scope : this,
		callback:function(options, success ,response){
		  if(success) {
			  var jsonStr = Ext.util.JSON.decode(response.responseText);
			  var array = eval(jsonStr);
			  var rootNode = windowItemTree.getRootNode();
			  if(rootNode) {
				for(var i = 0; i < array.length; i++){
				  var node = array[i];
				  var childNode;
				  //һ��ָ��ڵ�
				  if(node.itemGrade == 1){
					  var nodeAttrConfig = {id : node.itemId, qtip : node.itemName, text : node.itemName,leaf:true,checked:node.checked};
					  childNode = new Ext.tree.TreeNode(nodeAttrConfig);
					  rootNode.appendChild(childNode);
					  if(childNode.attributes.checked){
					    	 childNode.getUI().addClass("highlight");
//					    	 childNode.disable();   //����Ӧʱ����
					  }
				    }
			    }//end for
			}//end if
			  else {
				  Ext.Msg.alert("��ʾ��Ϣ","���ڵ㲻���ڣ�");
				  return;
			}
		  }	
		}
	}); //end ajax request
  }
	windowCombox.on('select', function(combox){
		store.removeAll(); 
		var rootNode = windowItemTree.getRootNode();
		while(rootNode.hasChildNodes()){
			var childNode = rootNode.lastChild;
			rootNode.removeChild(childNode);
		}
	    requestItems(combox.getValue());
	    windowItemTree.getRootNode().expand();
	});
	
	var  textfield = new Ext.form.TextField({disabled:true,width : 160,style:'color:red;'});
	var sheetTypeField = new Ext.form.TextField({disabled:true,width : 120,style:'color:red;'});
	sheetTypeField.value = GLOBAL_MAP['SHEET_TYPE'];
	var windowAssessPanel = new Ext.grid.EditorGridPanel({
		id : 'window_panel',
		region : 'center',
		width : 600,
		height : 600,
		frame : true,
		loadMash : true,
		waitMsg :'���ݼ�����,���Ժ�...',
		store : store,
		cm : cm,
		clicksToEdit: '1',
		stripeRows : true,
		autoScroll : true,
		tbar : ['&nbsp;',{
				text : '���濼������',
				icon : 'image/icon/disk.png',
				style:{border : 'solid 1px #abc'},// set button border css style
				handler : function(){
			       if(store.getCount()<=0){
			    	   Ext.Msg.alert("��ʾ��Ϣ","����ָ������ѡ��Ҫ���˵�ָ��");
			    	   return;
			       } else {
			    	   var records = store.getRange();
			    	   //var records = store.getModifiedRecords();
			    	   if(records.length > 0){
			    		   var dataArray = [];
			    		   for(var i=0;i<records.length;i++){
			    			   var record = records[i];
			    			   if(null==record.get('itemValue')){
			    				   Ext.Msg.alert("��ʾ��Ϣ","������ָ��÷�ȡֵ��");
			    				   return;
			    			   }
			    		   		var jsonData = Ext.util.JSON.encode(records[i].data);
			    		   		dataArray.push(jsonData);
			    		   }
//			    		   var data = "{" + '"secondItemId":{' + '"itemId":' + secondItemId + '},"windowItem":[' + dataArray + "]}";
			    		    if(confirm("���˺����ݲ����޸ģ�ȷ��ָ��������ȷ��")== true){
						    } else {
							   return;
						    }
			    		   var data = "{" + '"windowItem":[' + dataArray + "]" + "}";
			    		   Ext.Ajax.request({
			    			   url : 'win/windowScoreAction!saveWindowItemScore.action',
			    			   method : 'POST',
			    			   type : 'json',
			    			   params : {"jsonData": data, itemId:firstItemId,sheetName:GLOBAL_MAP['SHEET_NAME'],sheetId : GLOBAL_MAP['SHEET_ID'], sheetType : GLOBAL_MAP['SHEET_TYPE'], checkType : GLOBAL_MAP['CHECK_TYPE']},
			    			   success : function(response) {
			    				    Ext.Msg.alert("��ʾ��Ϣ","ָ�꿼�˳ɹ���");
			    				    selectedNode.getUI().addClass("highlight");
			    				    selectedNode.attributes.checked = true;
//			    				    selectedNode.disable();
			    				    store.removeAll();
			    			   },
			    			   failure : function(response){
			    				   Ext.Msg.alert("������ʾ",response.responseText);
			    				   return;
			    			   }
			    			   
			    		   });
			           } else {
			        	   Ext.Msg.alert("�������","����ȷ����ָ��ĵ÷�");
			    		   return;
			           }
			       } //end else
				}//end handler
			  },'-',"��ǰ����ָ�꣺",textfield,"-",'&nbsp;��&nbsp;��:',windowCombox,'-',"��������:",sheetTypeField
			]
	});
	
	 //var myBorderPanel = undefined;
	 new Ext.Panel({
		id : 'windowPanel',
	    title : '',
	    width : '100%',
	    height: 600,
	    split : true,
	    layout: 'border',
	    renderTo : "windowassess-div",
	    items : [windowItemTree, windowAssessPanel]
	});
	
});
	