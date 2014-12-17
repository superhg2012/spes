<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="../inc/comm.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>中心考核</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
    <style type="text/css">
	 .highlight .x-tree-node-anchor span {
	   color : yellow !important;
	   background-color :red !important;
	 }
	 
	 .alert-ul {
	   list-style-type : disc;
	   list-style-image : url(image/icon/bullet_blue.png) !important;
	   list-style-position : inside;
	   margin-left : 5px;
	   margin-top : 10px;
	 }
	 .alert-ul li {
	   height : 24px;
	 }
	 .big {
	   font-weight :bold;
	 }
	</style>
	<script type="text/javascript">
	//中心考核面板
	if(Ext.isChrome===true){       
        var chromeDatePickerCSS = ".x-date-picker {border-color: #1b376c;background-color:#fff;position: relative;width: 185px;}";
        Ext.util.CSS.createStyleSheet(chromeDatePickerCSS,'chromeDatePickerStyle');
    }

	function formatItem(value,metadata){
		metadata.attr = 'style="color:red"';
		return value;
	}
	
  Ext.onReady(function(){
      //提示信息设置
	  Ext.QuickTips.init();
	  Ext.form.Field.prototype.msgTarget  = 'side';
	//  var tabHeight = Ext.getCmp("doc-body").getHeight() - 40;
	  var firstItemId = undefined;
	  var selectedNode = undefined;
	  
	  var store = new Ext.data.Store({});
	  var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
	   {header : "指标编号", width : 100,dataIndex : 'itemId'},
	   {header : "指标名称", width : 250,dataIndex : 'itemName'},
	   {header : "指标取值", width : 160,xtype : 'numbercolumn',dataIndex : 'itemValue',editor : new Ext.grid.GridEditor(new Ext.form.NumberField({allowBlank:false,blankText : '请输入数值型数据'}))},
	   {header : "所属指标", width : 200,dataIndex :'pItemName',renderer:formatItem},
	   {header : "父指标编号", width : 100,dataIndex:'pItemId',hidden : true},
	   {header : "指标类型", width : 100,dataIndex:'itemType',hidden : true},
	   {header : "权重", width : 50,dataIndex:'itemWeight',hidden : true},
	   {header : "中心编号", width : 80,dataIndex:'centerId',hidden : true}
	  ]);
	  
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
		animate : true,
		region : 'east',
		width : 230,
		root : itemroot,
		autoScroll : true,
		bodyStyle : 'padding : 2',
		lines : true,
		frame : true,
		minSize: 175,
		maxWidth : 260,
		split : true,
        border : true,
        autoScroll:true,
        animCollapse:true,
		loader:loader,
		tbar :[
		 new Ext.form.TextField({
	        	cls : 'top-boolbar',
	        	width : 150,
	        	emptyText : '请输入指标名称',
	        	listeners : {
	        		render : function (f) {
	        		   f.el.on('keydown',filterTree, f, {buffer : 350});
	        		}
	        	}
	        }),'->',{
	           iconCls :'icon-expand-all',
	           tooltip : '全部展开',
	           handler : function(){centerItemTree.root.expand(true);}
	        },'-',{
	           iconCls : 'icon-collapse-all',
	           tooltip : '全部折叠',
	           handler : function (){centerItemTree.root.collapse(true);}
	        }
		],
		listeners : { }//end listeners
	});
	
	var  textfield = new Ext.form.TextField({disabled:true,width : 160,style:'color:red;'});
	var sheetTypeField = new Ext.form.TextField({disabled:true,width : 160,style:'color:red;'});
	sheetTypeField.value = GLOBAL_MAP['SHEET_RAWTYPE'];
/*	if(GLOBAL['sheetRawType']=='' ||  GLOBAL['sheetRawType']==undefined ){
	   sheetTypeField.value = GLOBAL_MAP['SHEET_RAWTYPE'];
	}
*/	
	
	
	/*var  checkRadioGroup = new Ext.form.RadioGroup({
	   id : 'checkRadio',
	   fieldLabel : '考核类型',
	   items : [new Ext.form.Radio({
        	name : 'mjy',
     		id : 'radio1',
     		inputValue : 'month',
     		checked : true,//default selected
     		boxLabel : '月考核'
	  }),new Ext.form.Radio({
	  	    name : 'mjy',
     		id : 'radio2',
     		inputValue : 'quarter',
     		boxLabel : '季度考核'
	  }),new Ext.form.Radio({
	    	name : 'mjy',
     		id : 'radio3',
     		inputValue : 'year',
     		boxLabel : '年考核'
	  })]
	});
	
    var comStore = new Ext.data.ArrayStore({
		fields : ['type','key'],
		data : [
					['month','月度考核'],
					['quarter','季度考核'],
					['year','年度考核']
				]
	});
	
	var checkCombox = new Ext.form.ComboBox({
	  id : 'checkCombox',
	  fieldLabel : '考核类型',
	  store : comStore,
	  triggerAction:'all',
	  displayField:'key',
	  valueField:'type',
	  mode:'local',
	  width : 80
	});
	*/
	
	//请求中心指标信息
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
					   //二级节点
					   if(node.itemGrade == 1){
					     var childAttrConfig = {id : node.itemId, "type" : node.itemType,tip : node.itemName, checked:node.checked, text : node.itemName,isClass : true, leaf :true};
					     //var leafNodeConfig = {"id" : node.itemId, "type" : node.itemType,  qtip : node.itemName, text : node.itemName,isClass : true, leaf :true,icon:'image/icon/chart_node.png'};
					     childNode = new Ext.tree.TreeNode(childAttrConfig);
  					     rootnode.appendChild(childNode);
  					    
  					     if(childNode.attributes.checked){
					    	 childNode.getUI().addClass("highlight");
					    	 //childNode.disable();   //不响应事件了
					     }
					   } 
					   /*else if(node.itemGrade == 2){
					  	  findChildNodes(node,rootnode);
 					   }*///end if
					}//end for
				}//end if
			} else {
				Ext.Msg.alert("错误提示","中心指标加载失败！");
				return;
			}
		}
	});
	
	//中心指标数据考核面板
	var centerAssessPanel = new Ext.grid.EditorGridPanel({
		id : 'center_panel',
		region : 'center',
		width : 600,
		height : 600,
		frame : true,
		loadMash : true,
		waitMsg :'数据加载中,请稍后...',
		store : store,
		cm : cm,
		clicksToEdit: '1',
		stripeRows : true,
		autoScroll : true,
		tbar : ['&nbsp;',{
			text : '保存考核数据',
			icon : 'image/icon/disk.png',
			style:{border : 'solid 1px #abc'},// set button border css style
			handler : function(){
				if(store.getCount()<=0){
		    	   Ext.Msg.alert("提示信息","请在指标树中选择要考核的指标");
		    	   return;
				} else {
					var records = store.getRange();
					if(records.length > 0){
						var dataArray = [];
						for(var i=0;i < records.length;i++){
							var record = records[i];
						    if(null==record.get('itemValue')){
		    				   Ext.Msg.alert("提示信息","请输入指标取值");
		    				   return;
			    			}
						    var jsonData = Ext.util.JSON.encode(records[i].data);
						    dataArray.push(jsonData);
						}//end for
						if(confirm("考核后数据不可修改，确定指标输入正确？")== true){
						} else {
						  return;
						}
						 var data = "{" + '"centerItem":[' + dataArray + "]" + "}"; 
						 Ext.Ajax.request({
							 url : 'center/centerScoreAction!saveCenterItemScore.action',
							 method : 'Post',
							 params : {'jsonData' : data,itemId :firstItemId,sheetName:GLOBAL_MAP['SHEET_NAME'], sheetId : GLOBAL_MAP['SHEET_ID'], sheetType : GLOBAL_MAP['SHEET_TYPE']},
							 scope :this,
							 callback : function(options, success , response){
								 if(success){
		    					    Ext.Msg.alert("提示信息","指标考核成功！");
		    					    //考核后，指标节点变红
		    					    selectedNode.getUI().addClass("highlight");
		    					    selectedNode.attributes.checked = true;
		    					    //selectedNode.disable();
		    					    store.removeAll();
								 } else {
									Ext.Msg.alert("提示信息","指标考核不成功！");
								 }
								 return;
							 }//edn callback
						 });//end ajax
					}//ende if
				}//end else
		   }//end han
		},'-',"当前考核指标：",textfield,'-',"考核类型:",sheetTypeField]
	});
	
	
	findChildNodes = function (node,rootnode) {
		var childnodes = rootnode.childNodes;
		for(var i = 0; i <childnodes.length;i++){
			var childNode = childnodes[i];
			if(node.parentId == childNode.attributes.id){
				var leafNodeConfig = {"id" : node.itemId, "type" : node.itemType,  qtip : node.itemName, text : node.itemName,checked:false,isClass : true, leaf :true,iconClass:'',icon:'image/icon/chart_node.png'};
		        var leafNode = new Ext.tree.TreeNode(leafNodeConfig);
				childNode.appendChild(leafNode);
				break;
			}
		}
	}
	
	 //展开根节点
	itemroot.expand();
	//注册节点点击事件
	centerItemTree.on('click',function(node,e){
		if(!node.isLeaf()){
			e.stopEvent();
		} else if(node.isLeaf()){
			e.stopEvent();
			loadCenterItem(node);
		} 
	});
	
    //页面总体布局
  	var myBorderPanel = new Ext.Panel({
		id : 'centerPanel',
	    width: '100%',
	    height: 600,
	    split :true,
	    renderTo: "center-div",
	    layout: 'border',
	    items: [
	    	centerItemTree,
	    	centerAssessPanel]
   });

    var filter = new Ext.tree.TreeFilter(centerItemTree,{
      clearBlank : true,
      autoClear :true
    });
    
   var hiddenPkgs = [];
   /**
   *过滤树节点
   */
   function filterTree(e) {
      //获取输入的查找文本
      var text = e.target.value;
      Ext.each(hiddenPkgs,function(node){
         node.ui.show();
      });
      //输入为空，则
      if(!text) {
        filter.clear();
        return;
      }
      //展开树全部节点
      centerItemTree.expandAll();
      var re = new RegExp('^' + Ext.escapeRe(text), 'i');
      filter.filterBy(function(n){
        return !n.attributes.isClass || re.test(n.text);
      });
      //hide empty packages that weren't filtered
      hiddenPkgs = [];
      centerItemTree.root.cascade( function(node){
	      if(!node.attributes.isClass && node.ui.ctNode.offsetHeight < 3){
	         node.ui.hide();
	         hiddenPkgs.push(node);
	      }
      });
    } //end function
    
    loadCenterItem = function(node) {
    	var itemId = node.attributes.id;
    	var centerId = Ext.get("centerId").dom.value;
    	var checked = node.attributes.checked;
    	selectedNode = node;
    	//获取已经考核过的数据
    	if(node.attributes.checked==true){
    		store.removeAll();
    		Ext.Ajax.request({
    		  url : 'sys/centerItemAction!getHisCenterItemByItemId.action',
    		  method : 'POST',
    		  timeout : 10000,
    		  scope : this,
    		  params : {'itemId' : itemId,"centerId" : centerId,checkType:Ext.getCmp('checkRadio').getValue().inputValue, sheetName : sheetNameField.getValue()},
    		  callback : function(options, success, response){
    		    if(success){
    		      var json = Ext.util.JSON.decode(response.responseText);
		    	  json = eval(json);
		    		//var records = [];
		    		if(json.length > 0){
			    		for(var i = 0;i<json.length; i++){
			    			var record = new Ext.data.Record({itemId : json[i].itemId, itemValue : json[i].pItemValue, itemName : json[i].itemName,pItemName:json[i].pItemName,pItemId:json[i].pItemId,itemType:json[i].itemType,itemWeight:json[i].itemWeight,centerId:centerId});
		   					store.add(record);
			    		}
		    		}//end if
    		    } else{
    		        Ext.Msg.alert("提示信息","中心评价指标加载失败！");
		    		return;
    		    }//end else
    		  }//end callback
    		});//end ajax
    		return;
    	}
    	firstItemId = itemId;
    	textfield.setValue(node.attributes.text);
    	store.removeAll();
		Ext.Ajax.request({
			url : 'sys/centerItemAction!getCenterItemByItemId2.action',
		    method : 'POST',
		    timeout : 10000,
		    scope : this,
		    params : {'itemId' : itemId,"centerId" : centerId},
		    callback : function(options , success , response){
		    	if(success){
		    		if(response.responseText==""){
		    	       Ext.Msg.alert("提示信息","该指标未设置子指标！");
		    	       return;
		    		}
		    		var json = Ext.util.JSON.decode(response.responseText);
		    		json = eval(json);
		    		var records = [];
		    		if(json.length > 0){
			    		for(var i = 0;i<json.length; i++){
			    			var record = new Ext.data.Record({itemId : json[i].itemId, itemName : json[i].itemName,pItemName:json[i].pItemName,pItemId:json[i].pItemId,itemType:json[i].itemType,itemWeight:json[i].itemWeight,centerId:centerId});
	　　　		 		   		store.add(record);
			    		}
		    		}//end if
		    	}else {
		    		Ext.Msg.alert("提示信息","中心评价指标加载失败！");
		    		return;
		    	}
		    }//end call back;
		});
    }//end function loadCenterItem
}); //end onready
//加载要考核的指标信息
</script>
	<style rel="stylesheet" type="text/css" href=" css/docs.css"></style>
  </head>
 	<body>
 	<div id="center-div"></div>
  </body>
</html>
