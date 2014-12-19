Ext.ns("Ext.hg");//self defined namespace;

Ext.hg.ParameterWindow = Ext.extend(Ext.Window, {
	closable: true,
	modal : true,
	plain: true,
	constrain : true,
	layout: 'fit',
	autoScroll: true,
	align : 'center',
	autoShow :true,
	height : 420,
	width : 940,
	sheetId : '',
	modify : false,
	
	initComponent : function() {
		Ext.hg.ParameterWindow.superclass.initComponent.call(this);
	},
	
	beforeShow : function() {
		var self = this;
		self.setTitle('���Ŀ��˽�� (���� : ' + self.sheetId + ')');
		self.createParamTree(self.sheetId);
		var tabPanel = self.getComponent('tabPanel');
		tabPanel.setTitle("���Ŀ��˽��");
	},
	
	createParamTree : function (sheetId) {
		var self = this;
		var tabPanel = self.getComponent('tabPanel');
		  //����ָ����
		 var itemroot = new Ext.tree.AsyncTreeNode({
			id : 'itemroot',
			text : Ext.get("centerName").dom.value,
			icon : 'image/icon/brick.png',
			expanded : true,
			children : [] 
		});
		
		var loader = new Ext.tree.TreeLoader({});
		
		//����ָ����
		var centerItemTree = new Ext.tree.TreePanel({
			title : '��������ָ�굼��',
			rootVisible : true,
			collapsible : true,
			width : 200,
			animate : true,
			region : 'west',
			root : itemroot,
//			autoScroll : true,
//			bodyStyle : {'padding-left' : -10},
			lines : true,
			frame : true,
			minSize: 200,
			maxWidth : 250,
			split : true,
	        border : false,
//	        autoScroll:true,
	        animCollapse:true,
			loader:loader,
			listeners : { }//end listeners
		});
		
		centerItemTree.on('click',function(node,e){
			if(!node.isLeaf()){
				e.stopEvent();
			} else if(node.isLeaf()){
				e.stopEvent();
				loadCenterItemScore(node);
			} 
		});
		
		
		loadCenterItemScore = function (node) {
			var itemId = node.attributes.id;
	    	var centerId = Ext.get("centerId").dom.value;
	    	selectedNode = node;
	    	//��ȡ�Ѿ����˹�������
	    	if(node !=null){
	    		store.removeAll();
	    		Ext.Ajax.request({
	    		  url : 'center/centerScoreAction!getHisCenterItemScore.action',
	    		  method : 'POST',
	    		  scope : this,
	    		  params : {'itemId' : itemId,"centerId" : centerId, sheetId :sheetId},
	    		  callback : function(options, success, response){
	    		    if(success){
	    		      var json = Ext.util.JSON.decode(response.responseText);
	    		      var length = json["length"];
			    	  json = eval(json["all"]);
 		    		  if(length > 0) {
			    			//loop second level items
				    		for(var i = 0;i < length; i++) {
				    			var second = json[i]["second"];
				    			var third = json[i]["third"];
				    			var length2 = json[i]["length"];
				    			if(second !=undefined || second!=null){
					    			var record = new Ext.data.Record({itemId : second.itemId, itemValue : second.itemScore, itemName : second.itemName, itemGrade: '����',centerId:centerId});
				   					store.add(record);
				    			}
				    			if(third != undefined || third != null) {
					    			//loop third level items
				    				for(var j=0;j<length2;j++) {
				    					var tt = third[i+"_3"][j];
				    					if(tt!=null || tt!=undefined){
					    					var record = new Ext.data.Record({itemId : tt.itemId, itemValue : tt.itemValue, itemName : tt.itemName, itemGrade: '����',centerId:centerId});
					    					store.add(record);
				    					}

				    				}
				    			}

				    		}
			    		}//end if
	    		    } else{
			    		return;
	    		    }//end else
	    		  }//end callback
	    		});//end ajax
	    		return;
	    	}
	    	
//	    	firstItemId = itemId;
//	    	store.removeAll();
//			Ext.Ajax.request({
//				url : 'sys/centerItemAction!getCenterItemByItemId2.action',
//			    method : 'POST',
//			    scope : this,
//			    params : {'itemId' : itemId,"centerId" : centerId},
//			    callback : function(options , success , response){
//			    	if(success){
//			    		if(response.responseText==""){
//			    	       return;
//			    		}
//			    		var json = Ext.util.JSON.decode(response.responseText);
//			    		json = eval(json);
//			    		if(json.length > 0){
//				    		for(var i = 0;i < json.length; i++){
//				    			var record = new Ext.data.Record({itemId : json[i].itemId, itemName : json[i].itemName,pItemName:json[i].pItemName,pItemId:json[i].pItemId,itemType:json[i].itemType,itemWeight:json[i].itemWeight,centerId:centerId});
//		        	 		   	store.add(record);
//				    		}
//			    		}//end if
//			    	}else {
//			    		return;
//			    	}
//			    }//end call back;
//			});
		};
		
		 var store = new Ext.data.Store({});
		 var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		   {header : "ָ����", width : 80,dataIndex : 'itemId'},
		   {header : "ָ������", width : 250,dataIndex : 'itemName'},
		   {header : "ָ��÷�", width : 80,dataIndex : 'itemValue'},
		   {header : "ָ��ȼ�", width : 160,dataIndex :'itemGrade'}
		  ]);
		
		//����ָ�����ݿ������
		var centerAssessPanel = new Ext.grid.GridPanel({
			id : 'center_panel',
			region : 'center',
			frame : false,
			loadMash : true,
			waitMsg :'���ݼ�����,���Ժ�...',
			store : store,
			cm : cm,
			stripeRows : true,
			autoScroll : true,
			tbar : ['&nbsp;',{
				text : '������������',
				icon : 'image/icon/disk.png',
				style:{border : 'solid 1px #abc'},// set button border css style
				handler : function(){
					Ext.Ajax.request({
					});
			   }//end han
			}]
//			getRowClass : function (record, rowIndex, p, ds){
//				var cls = "white-row";
//				if(record.get('itemGrade') == "����"){
//					cls = "green-row";
//				}
//				return cls;
//			}
		});
		
		var myBorderPanel = new Ext.Panel({
			id : 'centerPanel',
		    width: 900,
		    height : 400,
		    split :true,
		    layout: 'border',
		    items: [
		    	centerItemTree,
		    	centerAssessPanel]
	   });
		
		
		Ext.Ajax.request({
			url : 'sys/centerItemAction!getCenterFirstItemByCenterId.action',
			params :{centerId : Ext.get("centerId").dom.value, sheetId:GLOBAL_MAP['SHEET_ID'], sheetName : GLOBAL_MAP["SHEET_NAME"]},
			scope : this,
			callback : function(options,success,response){
				if(success){
					var array = eval(response.responseText);
					var rootnode = centerItemTree.getRootNode();
					if(rootnode){
						for(var i = 0; i < array.length; i++){
						   var node = array[i];
						   var childNode;
						   if(node.itemGrade == 1){
						     var childAttrConfig = {id : node.itemId, "type" : node.itemType,tip : node.itemName, text : node.itemName,isClass : true, leaf :true};
						     childNode = new Ext.tree.TreeNode(childAttrConfig);
	  					     rootnode.appendChild(childNode);
	  					     if(childNode.attributes.checked){
						    	 childNode.getUI().addClass("highlight");
						     }
						   } 
						}//end for
					}//end if
				} else {
					self.close();
				}
			}
		});
		
		tabPanel.add(myBorderPanel);
		tabPanel.setActiveTab(0);
	},
	
	
	renderTo: Ext.getBody()
});

Ext.reg('paramWindow', Ext.hg.ParameterWindow);
