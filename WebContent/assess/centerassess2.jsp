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
    <title>���Ŀ���</title>
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
	//���Ŀ������
	if(Ext.isChrome===true){       
        var chromeDatePickerCSS = ".x-date-picker {border-color: #1b376c;background-color:#fff;position: relative;width: 185px;}";
        Ext.util.CSS.createStyleSheet(chromeDatePickerCSS,'chromeDatePickerStyle');
    }

	function formatItem(value,metadata){
		metadata.attr = 'style="color:red"';
		return value;
	}
	
  Ext.onReady(function(){
      //��ʾ��Ϣ����
	  Ext.QuickTips.init();
	  Ext.form.Field.prototype.msgTarget  = 'side';
	 //var tabHeight = Ext.getCmp("doc-body").getHeight() - 40;
	  var firstItemId;
	  var selectedNode;
	  
	  var store = new Ext.data.Store({});
	  var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
	   {header : "ָ����", width : 100,dataIndex : 'itemId'},
	   {header : "ָ������", width : 250,dataIndex : 'itemName'},
	   {header : "ָ��ȡֵ", width : 160,xtype : 'numbercolumn',dataIndex : 'itemValue',editor : new Ext.grid.GridEditor(new Ext.form.NumberField({allowBlank:false,blankText : '��������ֵ������'}))},
	   {header : "����ָ��", width : 200,dataIndex :'pItemName',renderer:formatItem},
	   {header : "��ָ����", width : 100,dataIndex:'pItemId',hidden : true},
	   {header : "ָ������", width : 100,dataIndex:'itemType',hidden : true},
	   {header : "Ȩ��", width : 50,dataIndex:'itemWeight',hidden : true},
	   {header : "���ı��", width : 80,dataIndex:'centerId',hidden : true}
	  ]);
	  
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
	        	emptyText : '������ָ������',
	        	listeners : {
	        		render : function (f) {
	        		   f.el.on('keydown',filterTree, f, {buffer : 350});
	        		}
	        	}
	        }),'->',{
	           iconCls :'icon-expand-all',
	           tooltip : 'ȫ��չ��',
	           handler : function(){centerItemTree.root.expand(true);}
	        },'-',{
	           iconCls : 'icon-collapse-all',
	           tooltip : 'ȫ���۵�',
	           handler : function (){centerItemTree.root.collapse(true);}
	        }
		]
	});
	
	var  textfield = new Ext.form.TextField({disabled:true,width : 160,style:'color:red;'});
	var sheetTypeField = new Ext.form.TextField({disabled:true,width : 160,style:'color:red;'});
	sheetTypeField.value = GLOBAL_MAP['SHEET_RAWTYPE'];
	
/*	if(GLOBAL['sheetRawType'] == '' || GLOBAL['sheetRawType']==undefined){
	   sheetTypeField.value = GLOBAL_SHEETTYPE_MAP[GLOBAL_MAP['SHEET_TYPE']];
	}
*/	
	//��������ָ����Ϣ
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
					   //�����ڵ�, һ��ָ��
					   if(node.itemGrade == 1){
					     var childAttrConfig = {id : node.itemId, "type" : node.itemType,tip : node.itemName, checked:node.checked, text : node.itemName,isClass : true, leaf :true};
					     //var leafNodeConfig = {"id" : node.itemId, "type" : node.itemType,  qtip : node.itemName, text : node.itemName,isClass : true, leaf :true,icon:'image/icon/chart_node.png'};
					     childNode = new Ext.tree.TreeNode(childAttrConfig);
  					     rootnode.appendChild(childNode);
  					    
  					     if(childNode.attributes.checked){
					    	 childNode.getUI().addClass("highlight");
					    	 //childNode.disable();   //����Ӧ�¼���
					     }
					   } 
					}//end for
				}//end if
			} else {
				Ext.Msg.alert("������ʾ","����ָ�����ʧ�ܣ�");
				return;
			}
		}
	});
	
	//����ָ�����ݿ������
	var centerAssessPanel = new Ext.grid.EditorGridPanel({
		id : 'center_panel',
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
					if(records.length > 0){
						var dataArray = [];
						for(var i=0;i < records.length;i++){
							var record = records[i];
						    if(null==record.get('itemValue')){
		    				   Ext.Msg.alert("��ʾ��Ϣ","������ָ��ȡֵ");
		    				   return;
			    			}
						    var jsonData = Ext.util.JSON.encode(records[i].data);
						    dataArray.push(jsonData);
						}//end for
						if(confirm("���˺����ݲ����޸ģ�ȷ��ָ��������ȷ��")== true){
						} else {
						  return;
						}
						 var data = "{" + '"centerItem":[' + dataArray + "]" + "}"; 
						 Ext.Ajax.request({
							 url : 'center/centerScoreAction!saveCenterItemScore.action',
							 method : 'Post',
							 params : {'jsonData' : data,itemId :firstItemId,sheetName:GLOBAL_MAP['SHEET_NAME'],sheetId: GLOBAL_MAP['SHEET_ID'], checkType : GLOBAL_MAP['CHECK_TYPE']},
							 scope :this,
							 callback : function(options, success ,response){
								 if(success){
		    					    Ext.Msg.alert("��ʾ��Ϣ","ָ�꿼�˳ɹ���");
		    					    //���˺�ָ��ڵ���
		    					    selectedNode.getUI().addClass("highlight");
		    					    selectedNode.attributes.checked = true;
		    					    //selectedNode.disable();
		    					    store.removeAll();
								 } else {
									Ext.Msg.alert("��ʾ��Ϣ","ָ�꿼�˲��ɹ���");
								 }
								 return;
							 }//edn callback
						 });//end ajax
					}//ende if
				}//end else
		   }//end han
		},'-',"��ǰ����ָ�꣺",textfield,'-',"��������:",sheetTypeField]
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
	
	 //չ�����ڵ�
	itemroot.expand();
	//ע��ڵ����¼�
	centerItemTree.on('click',function(node,e){
		if(!node.isLeaf()){
			e.stopEvent();
		} else if(node.isLeaf()){
			e.stopEvent();
			loadCenterItem(node);
		} 
	});
	
    //ҳ�����岼��
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
   *�������ڵ�
   */
   function filterTree(e) {
      //��ȡ����Ĳ����ı�
      var text = e.target.value;
      Ext.each(hiddenPkgs,function(node){
         node.ui.show();
      });
      //����Ϊ�գ���
      if(!text) {
        filter.clear();
        return;
      }
      //չ����ȫ���ڵ�
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
    	//��ȡ�Ѿ����˹�������
    	if(node.attributes.checked==true){
    		store.removeAll();
    		Ext.Ajax.request({
    		  url : 'sys/centerItemAction!getHisCenterItemByItemId.action',
    		  method : 'POST',
    		  scope : this,
    		  params : {'itemId' : itemId,"centerId" : centerId,checkType:Ext.getCmp('checkRadio').getValue().inputValue, sheetName : sheetNameField.getValue()},
    		  callback : function(options, success, response){
    		    if(success){
    		      var json = Ext.util.JSON.decode(response.responseText);
		    		json = eval(json);
		    	//	var records = [];
		    		if(json.length > 0){
			    		for(var i = 0;i<json.length; i++){
			    			var record = new Ext.data.Record({itemId : json[i].itemId, itemValue : json[i].pItemValue, itemName : json[i].itemName,pItemName:json[i].pItemName,pItemId:json[i].pItemId,itemType:json[i].itemType,itemWeight:json[i].itemWeight,centerId:centerId});
			 		   		store.add(record);
			    		}
		    		}//end if
    		    } else{
    		        Ext.Msg.alert("��ʾ��Ϣ","��������ָ�����ʧ�ܣ�");
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
		    	       Ext.Msg.alert("��ʾ��Ϣ","��ָ��δ������ָ�꣡");
		    	       return;
		    		}
		    		var json = Ext.util.JSON.decode(response.responseText);
		    		json = eval(json);
		    	//	var records = [];
		    		if(json.length > 0){
			    		for(var i = 0;i<json.length; i++){
			    			var record = new Ext.data.Record({itemId : json[i].itemId, itemName : json[i].itemName,pItemName:json[i].pItemName,pItemId:json[i].pItemId,itemType:json[i].itemType,itemWeight:json[i].itemWeight,centerId:centerId});
			 		   		store.add(record);
			    		}
		    		}//end if
		    	}else {
		    		Ext.Msg.alert("��ʾ��Ϣ","��������ָ�����ʧ�ܣ�");
		    		return;
		    	}
		    }//end call back;
		});
    }//end function loadCenterItem
}); //end onready
//����Ҫ���˵�ָ����Ϣ
</script>
	<style rel="stylesheet" type="text/css" href=" css/docs.css"></style>
  </head>
 	<body>
 	<div id="center-div"></div>
  </body>
</html>
