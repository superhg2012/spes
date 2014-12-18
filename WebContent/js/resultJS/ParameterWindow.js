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
		self.setTitle('中心考核结果 (表单名 : ' + self.sheetId + ')');
		self.createParamTree(self.sheetId);
		var tabPanel = self.getComponent('tabPanel');
		tabPanel.setTitle("中心考核结果");
	},
	
	createParamTree : function (sheetId) {
		var self = this;
		var tabPanel = self.getComponent('tabPanel');
		  //中心指标树
		 var itemroot = new Ext.tree.AsyncTreeNode({
			id : 'itemroot',
			text : Ext.get("centerName").dom.value,
			icon : 'image/icon/brick.png',
			expanded : true,
			children : [] 
		});
		
		var loader = new Ext.tree.TreeLoader({});
		
		//中心指标树
		var centerItemTree = new Ext.tree.TreePanel({
			title : '中心评价指标导航',
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
	    	//获取已经考核过的数据
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
			    	  json = eval(json);
			    		if(json.length > 0) {
				    		for(var i = 0;i<json.length; i++){
				    			var record = new Ext.data.Record({itemId : json[i].itemId, itemValue : json[i].pItemValue, itemName : json[i].itemName,pItemName:json[i].pItemName,pItemId:json[i].pItemId,itemType:json[i].itemType,itemWeight:json[i].itemWeight,centerId:centerId});
			   					store.add(record);
				    		}
			    		}//end if
	    		    } else{
			    		return;
	    		    }//end else
	    		  }//end callback
	    		});//end ajax
	    		return;
	    	}
	    	
	    	firstItemId = itemId;
	    	store.removeAll();
			Ext.Ajax.request({
				url : 'sys/centerItemAction!getCenterItemByItemId2.action',
			    method : 'POST',
			    scope : this,
			    params : {'itemId' : itemId,"centerId" : centerId},
			    callback : function(options , success , response){
			    	if(success){
			    		if(response.responseText==""){
			    	       return;
			    		}
			    		var json = Ext.util.JSON.decode(response.responseText);
			    		json = eval(json);
			    		if(json.length > 0){
				    		for(var i = 0;i < json.length; i++){
				    			var record = new Ext.data.Record({itemId : json[i].itemId, itemName : json[i].itemName,pItemName:json[i].pItemName,pItemId:json[i].pItemId,itemType:json[i].itemType,itemWeight:json[i].itemWeight,centerId:centerId});
		        	 		   	store.add(record);
				    		}
			    		}//end if
			    	}else {
			    		return;
			    	}
			    }//end call back;
			});
		};
		
		 var store = new Ext.data.Store({});
		 var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		   {header : "指标编号", width : 80,dataIndex : 'itemId'},
		   {header : "指标名称", width : 250,dataIndex : 'itemName'},
		   {header : "指标得分", width : 80,dataIndex : 'itemValue'},
		   {header : "所属指标", width : 160,dataIndex :'pItemName'}
		  ]);
		
		//中心指标数据考核面板
		var centerAssessPanel = new Ext.grid.GridPanel({
			id : 'center_panel',
			region : 'center',
			frame : false,
			loadMash : true,
			waitMsg :'数据加载中,请稍后...',
			store : store,
			cm : cm,
			stripeRows : true,
			autoScroll : true,
			tbar : ['&nbsp;',{
				text : '导出考核数据',
				icon : 'image/icon/disk.png',
				style:{border : 'solid 1px #abc'},// set button border css style
				handler : function(){
			   }//end han
			}]
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
		tabPanel.setTitle("中心考核数据");
	},
	
	
	renderTo: Ext.getBody()
});

Ext.reg('paramWindow', Ext.hg.ParameterWindow);
