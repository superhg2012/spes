<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="../inc/comm.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>系统公告</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  <script type="text/javascript">
   Ext.onReady(function(){
	   Ext.QuickTips.init();
	   //统一制定错误信息提示浮动显示方式
	   Ext.form.Field.prototype.msgTarget = 'side';
	   var idselect = null;
	   var config = {
		   id :'form',
		   title : '',
		   labelSeparator : ':',
		   labelWidth : 60,
		   labelAlign : 'right',
		   bodyStyle  : 'padding:5 5 5 5',
		   defaultType: 'textfield',
		   height : 'auto',
		   width : 700,
		   frame : true,//是否渲染表单
		   renderTo :'notice',
		   method : 'post',
		   url : 'sys/noticeAction!pubNotice.action',
		   //表单子元素数组
		   items : [
			   new Ext.form.TextField({
				   fieldLabel : '标 题',
				   id : 'title',
				   name : 'title',
				   selectOnFocus : true,//得到焦点自动选择文本
				   allowBlank : false,
				   blankText: '标题不能为空',
				   emptyText : '请输入标题',
				   defaultValue: '',
				   width : 400,
				   maxLength : 100
			   }),
			   new Ext.form.HtmlEditor({
				   id : 'htmleditor',
				   name : 'content',
				   width : 600,
				   height : 300,
				   fieldLabel :'公告内容',
				   emptyText : '请输入公告内容',
				   defaultValue: '',
				   enableAlignments : true,
				   enableColors : true,
				   enableFont : true,
				   enableFontSize :true,
				   enalbeLinks :true,
				   enableLists:true,
				   enableSourceEdit:true
			   })
		   ]
	   };//end config
	   var form = new Ext.form.FormPanel(config);
	   
	   // edit form
	    var configEdit = {
		   id :'formEdit',
		   title : '',
		   labelSeparator : ':',
		   labelWidth : 60,
		   labelAlign : 'right',
		   bodyStyle  : 'padding:5 5 5 5',
		   defaultType: 'textfield',
		   height : 'auto',
		   width : 700,
		   frame : true,//是否渲染表单
		   renderTo :'notice',
		   method : 'post',
		   url : 'sys/noticeAction!editNotice.action',
		   //表单子元素数组
		   items : [
		   	   new Ext.form.TextField({
		   	   		id : 'nid',
		   	   		name : 'noticeId',
		   	   		hidden : true
		   	   
		   	   }),
			   new Ext.form.TextField({
				   fieldLabel : '标 题',
				   id : 'titleEdit',
				   name : 'title',
				   selectOnFocus : true,//得到焦点自动选择文本
				   allowBlank : false,
				   blankText: '标题不能为空',
				   emptyText : '请编辑标题',
				   width : 400,
				   maxLength : 100
			   }),
			   new Ext.form.HtmlEditor({
				   id : 'htmlEdit',
				   name : 'content',
				   width : 600,
				   height : 300,
				   fieldLabel :'公告内容',
				   emptyText : '请编辑公告内容',
				   defaultValue: '',
				   enableAlignments : true,
				   enableColors : true,
				   enableFont : true,
				   enableFontSize :true,
				   enalbeLinks :true,
				   enableLists:true,
				   enableSourceEdit:true
			   })
		   ]
	   };//end configEdit
	   var formEdit = new Ext.form.FormPanel(configEdit);
	   var winEdit = new Ext.Window({
			title : '编辑公告',
			width : 600,
			height : 300,
			layout : 'fit',
			plain : true,
			resizable :false,
			closeAction : 'hide',
			split : true,
			items : formEdit,
			shadow : true,
			modal : true,//模态
			listeners : {
		      'hide' : function (e){
		         formEdit.form.reset();
		         Ext.getCmp("htmlEdit").reset();
		      }
			},
			buttons : [
				   new Ext.Button({
					   text : '编辑公告',
					   handler: function(){
					     if(!formEdit.getForm().isValid()){
					    	 return;
					     }
					     //提交表单处理
					     formEdit.getForm().submit({
					    	success : function(curForm,response){
					    		Ext.Msg.alert("提示信息","公告编辑成功！");
					    		winEdit.hide();
					    		formEdit.form.reset();
					    		store.reload();
					    	},
					    	failure : function(){
					    		Ext.Msg.show({title:'错误信息提示',msg : '公告编辑失败',buttons : Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
					    	}
					     });
					   }
				   }),
				   new Ext.Button({
					   text : '重 置',
					   handler: function(){
					     Ext.getCmp("htmlEdit").reset();
					     formEdit.form.reset();
					   }
				   })
			   ]
		});
	   
	   var win = new Ext.Window({
			title : '公告',
			width : 600,
			height : 300,
			layout : 'fit',
			plain : true,
			resizable :false,
			closeAction : 'hide',
			split : true,
			items : form,
			shadow : true,
			modal : true,//模态
			listeners : {
		      'hide' : function (e){
		         form.form.reset();
		         Ext.getCmp("htmleditor").reset();
		      }
			},
			buttons : [
				   new Ext.Button({
					   text : '发布公告',
					   handler: function(){
					     if(!form.getForm().isValid()){
					    	 return;
					     }
					     //提交表单处理
					     form.getForm().submit({
					    	success : function(curForm,response){
					    		Ext.Msg.alert("提示信息","公告发布成功！");
					    		win.hide();
					    		form.form.reset();
					    		store.reload();
					    	},
					    	failure : function(){
					    		Ext.Msg.show({title:'错误信息提示',msg : '公告发布失败',buttons : Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
					    	}
					     });
					   }
				   }),
				   new Ext.Button({
					   text : '重 置',
					   handler: function(){
					     Ext.getCmp("htmleditor").reset();
					     form.form.reset();
					   }
				   })
			   ]
		});
	   
	   	//checkBox
		var sm = new Ext.grid.CheckboxSelectionModel({
	    	singleSelect : true
	    });// 设置复选框
	    // 设置列
	    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),sm, 
	    	    {header:"通知Id",width : 100,dataIndex :'noticeId',hidden : true},
	            {header : "通知标题", width: 180,dataIndex : 'noticeTitle'}, 
	            {header : "发布者", width: 120,dataIndex : 'userName'},
	            {header : "发布时间", width: 280,dataIndex : 'noticeTime',sortable : true}
	    ]);
	    
	    var store = new Ext.data.Store({
		 proxy : new Ext.data.HttpProxy({
			  url : 'sys/noticeAction!getNoticeList.action',
			  method  : 'POST'
		 }), 
		 
		 reader : new Ext.data.JsonReader({
			 type:'json',
			 totalProperty : 'total',
			 root : 'data',
			 fields : ['noticeId','noticeTitle','userName','noticeTime']
		 }),
		 sortInfo: {
		 	field: 'noticeTime',
		 	direction: 'desc'
		 },
		 //autoLoad : true,
		 remoteSort : true
	   });

		//加载数据
	    store.load({params:{
	    	start : 0,
	    	limit : 16
	    }});
	    
	   var noticeGridPanel = new Ext.grid.EditorGridPanel({
	    id : 'notice-panel',
		width : '100%',
		height : 'auto',
		autoHeight : true,
		frame : true,
		border : true,
		loadMash : true,
		waitMsg :'数据加载中，请稍后...',
		viewConfig : {
		  forceFit : true 
		},
		store : store,
		sm : sm,
		cm : cm,
		stripeRows : true,
		//autoExpandColum : 'centerMC',
		renderTo : "noticelist",
		tbar :['&nbsp;',{
			   id:'add-btn',
			   text: '发布公告',
			    icon : 'image/icon/email_add.png',
	            handler: function(){
			    win.show("add-btn");
	            }
		},'-',{
			 id:'show-btn',
	            text: '编辑公告',
	             icon : 'image/icon/email_edit.png',
	            handler: function(){
			     
			     if(sm.getCount() < 1) {
            		 Ext.Msg.alert("提示信息",'请选择要编辑的公告！');
            		 return;
            	 }
            	 var grid = Ext.getCmp('notice-panel'); 
            	 var cells = grid.getSelectionModel().getSelections()[0];
                 idselect = cells.get("noticeId");
                 Ext.lib.Ajax.request(
					    	  'post',
					    	  'sys/noticeAction!getNoticeById.action',
					    	  {   success:function(response, option) {
						    			var data = Ext.util.JSON.decode(response.responseText);
							    	  	Ext.getCmp('titleEdit').setValue(data.noticeTitle);
	                				  	Ext.getCmp('htmlEdit').setValue(data.noticeContent);
	                				  	Ext.getCmp('nid').setValue(data.noticeId);
						    	}
					    	  },
					    	  'noticeId=' + encodeURIComponent(Ext.encode(idselect))
			    			);
			    	
			    		 winEdit.show("show-btn");
	            }
	         
		},'-',{
	            text: '删除公告',
	             icon : 'image/icon/email_delete.png',
	            handler: function(){
			      if(sm.getCount() < 1) {
            		 Ext.Msg.alert("提示信息",'请选择要删除的记录！');
            		 return;
            	 }
            	 Ext.Msg.show({
            		 title : '删除提示！',
            		 msg : '确定删除请选择是！',
            		 buttons : Ext.Msg.YESNOCANCEL,
            		 
            		 fn : function(yesno){
            		    if(yesno == 'yes'){
            		      var grid = Ext.getCmp('notice-panel'); 
            		       var cells = grid.getSelectionModel().getSelections()[0];
                        //   var record = store.getAt(cells[0]);
                           var id = cells.get("noticeId");
                           
                           Ext.lib.Ajax.request(
					    	  'post',
					    	  'sys/noticeAction!deleteNotice.action',
					    	  {   success:function(response) {
					    			store.reload();
						    	}
					    	  },
					    	  'noticeId=' + encodeURIComponent(Ext.encode(id))
			    			);
                           
            		        store.remove(sm.getSelected());
            		        store.load(); //重新加载
            		    }
            		 },
            		 scope : this,
            		 animEl : 'elId',
            		 icon : Ext.MessageBox.WARNING
            	 });
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
        }),
       listeners : {
		'rowdblclick' : function(noticeGridPanel, rowIndex, e){
		  noticeGridPanel.getSelectionModel().clearSelections();
		  noticeGridPanel.getSelectionModel().selectRow(rowIndex);
		  var record = noticeGridPanel.getSelectionModel().getSelected();
		 // Edit_Window.show();
		}
       }
	}); 

   	//渲染
	noticeGridPanel.render();   
   });  
  </script>
  </head>
  
  <body>
  <div id="notice"  style="display: none;"></div>
  <div id="noticelist" ></div>
  </body>
</html>
