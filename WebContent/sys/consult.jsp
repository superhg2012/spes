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
    
    <title>问题咨询</title>
    
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
	   
	   //override the onDisable and onEnable
	   Ext.override(Ext.form.HtmlEditor, {
			onDisable: function(){
				if(this.rendered){
					var roMask = this.wrap.mask();
					roMask.dom.style.filter = "alpha(opacity=0);"; //IE
					roMask.dom.style.opacity = "0"; //Mozilla
					roMask.dom.style.background = "white";
					roMask.dom.style.overflow = "scroll";
					//roMask.dom.innerHTML = this.getValue();  
				}
				Ext.form.HtmlEditor.superclass.onDisable.call(this);
			},
			onEnable: function(){
				if(this.rendered){
					this.wrap.unmask();
				}
				Ext.form.HtmlEditor.superclass.onEnable.call(this);
			}
		});
		
	   var configConsult = {
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
		   renderTo :'consult',
		   method : 'post',
		   url : 'sys/consultAction!pubConsult.action',
		   //表单子元素数组
		   items : [
			   new Ext.form.TextField({
				   fieldLabel : '标 题',
				   id : 'title',
				   name : 'consultTitle',
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
				   name : 'consultContent',
				   width : 600,
				   height : 300,
				   fieldLabel :'咨询内容',
				   emptyText : '请输入咨询内容',
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
	   var formc = new Ext.form.FormPanel(configConsult);
	   
	   var win = new Ext.Window({
			title : '信息咨询',
			width : 600,
			height : 300,
			layout : 'fit',
			plain : true,
			resizable :false,
			closeAction : 'hide',
			split : true,
			items : formc,
			shadow : true,
			modal : true,//模态
			listeners : {
		      'hide' : function (e){
		         formc.form.reset();
		         Ext.getCmp("htmleditor").reset();
		      }
			},
			buttons : [
				   new Ext.Button({
					   text : '咨询',
					   handler: function(){
					     if(!formc.getForm().isValid()){
					    	 return;
					     }
					     //提交表单处理
					     formc.getForm().submit({
					    	success : function(curForm,response){
					    		Ext.Msg.alert("提示信息",response.result.resptext);
					    		win.hide();
					    		formc.form.reset();
					    		store.reload();
					    	},
					    	failure : function(){
					    		Ext.Msg.show({title:'错误信息提示',msg : response.result.resptext,buttons : Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
					    	}
					     });
					   }
				   }),
				   new Ext.Button({
					   text : '重 置',
					   handler: function(){
					     Ext.getCmp("htmleditor").reset();
					     formc.form.reset();
					   }
				   })
			   ]
		});
	   
	   //show the reply
	    var configShowReply = {
		   id :'formShowReply',
		   title : '',
		   labelSeparator : ':',
		   labelWidth : 60,
		   labelAlign : 'right',
		   bodyStyle  : 'padding:5 5 5 5',
		   defaultType: 'textfield',
		   height : 'auto',
		   width : 700,
		   frame : true,//是否渲染表单
		   renderTo :'consult',
		   //表单子元素数组
		   items : [
		   	      new Ext.form.TextField({
				   fieldLabel : '回复时间',
				   id : 'timeShowReply',
				   name : 'replyTime',
				   selectOnFocus : true,//得到焦点自动选择文本
				   allowBlank : false,
				   width : 400,
				   maxLength : 100,
				   readOnly : true
			   }),
			     new Ext.form.HtmlEditor({
				   id : 'textShowReply',
				   width : 600,
				   height : 300,
				   fieldLabel :'回复内容',
				   enableAlignments : true,
				   enableColors : true,
				   enableFont : true,
				   enableFontSize :true,
				   enalbeLinks :true,
				   enableLists:true,
				   enableSourceEdit:true,
				   readOnly: true,
    			   disabled: true 
			   })
		   ]
	   };//end configEdit
	   
	   var formShowReply = new Ext.form.FormPanel(configShowReply);
	   var winShowReply = new Ext.Window({
			title : '回复查看',
			width : 600,
			height : 300,
			layout : 'fit',
			plain : true,
			resizable :false,
			closeAction : 'hide',
			split : true,
			items : formShowReply,
			shadow : true,
			modal : true,//模态
			buttons : [
				   new Ext.Button({
					   text : '返回',
					   handler: function(){
					   winShowReply.hide();
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
	    	    {header:"咨询Id",width : 100,dateIndex :'consultId',hidden : true},
	            {header : "咨询标题", width: 180,dataIndex : 'consultTitle'}, 
	            {header : "咨询者", width: 120,dataIndex : 'userName'},
	            {header : "咨询时间", width: 280,dataIndex : 'consultTime',sortable : true},
	            {header : "是否回复", width: 120, dataIndex : 'isReply'}
	    ]);
	    
	    var store = new Ext.data.Store({
		 proxy : new Ext.data.HttpProxy({
			  url : 'sys/consultAction!getConsultList.action',
			  method  : 'POST'
		 }), 
		 
		 reader : new Ext.data.JsonReader({
			 type:'json',
			 totalProperty : 'total',
			 root : 'data',
			 fields : ['consultId','consultTitle','userName','consultTime','isReply']
		 }),
		 //autoLoad : true,
		  sortInfo: {
		 	field: 'consultTime',
		 	direction: 'desc'
		 },
		 remoteSort : true
	   });

		//加载数据
	    store.load({params:{
	    	start : 0,
	    	limit : 16
	    }});
	    
	   var consultGridPanel = new Ext.grid.EditorGridPanel({
	    id : 'consult-panel',
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
		renderTo : "consultlist",
		tbar :['&nbsp;',{
			 iconCls: 'icon-center-add',
			   id:'add-btn',
			   text: '信息咨询',
			    icon : 'image/icon/comments_add.png',
	            handler: function(){
			      win.show("add-btn");
			      store.reload();
	            }
		},'-',{
			 iconCls: 'icon-center-edit',
	            text: '回复查看',
	             icon : 'image/icon/comment_edit.png',
	            handler: function(){
	             if(sm.getCount() < 1) {
            		 Ext.Msg.alert("提示信息",'请选择要查看回复的公告！');
            		 return;
            	 }
            	 var grid = Ext.getCmp('consult-panel'); 
            	 var cells = grid.getSelectionModel().getSelections()[0];
                 idselect = cells.get("consultId");
                 var isReply = cells.get("isReply"); 
				if (isReply != "已回复"){
					Ext.MessageBox.alert("消息提示框！", "您的消息还没有回复，无法查看！");
				} else {
	                 Ext.lib.Ajax.request(
						    	  'post',
						    	  'sys/replyAction!getReplyByConsultId.action',
						    	  {   success:function(response, option) {
							    			var data = Ext.util.JSON.decode(response.responseText);
								    	  	Ext.getCmp('timeShowReply').setValue(data.replyTime);
		                				  	Ext.getCmp('textShowReply').setValue(data.replyContent);
		                				  //	Ext.getCmp('cid').setValue(data.consultId);
							    	}
						    	  },
						    	  'consultId=' + encodeURIComponent(Ext.encode(idselect))
				    			);
				    	
				    		 winShowReply.show("show-btn");
		            }
	          }
		},'-',{
			 iconCls: 'icon-center-delete',
	            text: '删除咨询',
	             icon : 'image/icon/comments_delete.png',
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
            		      var grid = Ext.getCmp('consult-panel'); 
            		       var cells = grid.getSelectionModel().getSelections()[0];
                        //   var record = store.getAt(cells[0]);
                           var id = cells.get("consultId");
                           
                           Ext.lib.Ajax.request(
					    	  'post',
					    	  'sys/consultAction!deleteConsult.action',
					    	  {   success:function(response) {
					    			store.reload();
						    	}
					    	  },
					    	  'consultId=' + encodeURIComponent(Ext.encode(id))
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
		'rowdblclick' : function(consultGridPanel, rowIndex, e){
		  consultGridPanel.getSelectionModel().clearSelections();
		  consultGridPanel.getSelectionModel().selectRow(rowIndex);
		  var record = consultGridPanel.getSelectionModel().getSelected();
		 // Edit_Window.show();
		}
       }
	}); 

   	//渲染
	consultGridPanel.render();   
   });  
  </script>
  </head>
  
  <body>
  <div id="consult"  style="display: none;"></div>
  <div id="consultlist" ></div>
  </body>
</html>
