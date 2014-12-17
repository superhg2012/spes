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

	   //统一制定错误信息提示浮动显示方式
	   Ext.form.Field.prototype.msgTarget = 'side';
	   var configReply = {
		   id :'formc',
		   title : '',
		   labelSeparator : ':',
		   labelWidth : 60,
		   labelAlign : 'right',
		   bodyStyle  : 'padding:5 5 5 5',
		   defaultType: 'textfield',
		   height : 'auto',
		   width : 700,
		   frame : true,//是否渲染表单
		   renderTo :'reply',
		   method : 'post',
		   url : 'sys/replyAction!relpyByConsultId.action',
		   //表单子元素数组
		   items : [
			   new Ext.form.TextArea({
				   fieldLabel : '咨询问题',
				   id : 'question',
				   name : 'question',
				   selectOnFocus : true,//得到焦点自动选择文本
				   allowBlank : false,
				   width : 400,
				   maxLength : 100,
				   readOnly : true
			   }),
			   new Ext.form.TextField({
			   		id : 'consultId',
			   		name : 'consultId',
			   		hidden : true
			   }),
			   new Ext.form.HtmlEditor({
				   id : 'htmlReply',
				   name : 'replyContent',
				   width : 600,
				   height : 300,
				   fieldLabel :'回复内容',
				   emptyText : '请输入回复内容',
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
	   var formReply = new Ext.form.FormPanel(configReply);
	   
	   var winReply = new Ext.Window({
			title : '问题回复',
			width : 600,
			height : 300,
			layout : 'fit',
			plain : true,
			resizable :false,
			closeAction : 'hide',
			split : true,
			items : formReply,
			shadow : true,
			modal : true,//模态
			listeners : {
		      'hide' : function (e){
		         formReply.form.reset();
		         Ext.getCmp("htmlReply").reset();
		      }
			},
			buttons : [
				   new Ext.Button({
					   text : '回复',
					   handler: function(){
					     if(!formReply.getForm().isValid()){
					    	 return;
					     }
					     //提交表单处理
					     formReply.getForm().submit({
					    	success : function(curForm,response){
					    		Ext.Msg.alert('提示信息','恭喜您，回复成功！');
					    		store.reload();
					    		winReply.hide();
					    		formReply.form.reset();
					    	},
					    	failure : function(){
					    		Ext.Msg.show({title:'错误信息提示',msg : '抱歉，回复失败',buttons : Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
					    	}
					     });
					   }
				   }),
				   new Ext.Button({
					   text : '重 置',
					   handler: function(){
					     Ext.getCmp("htmlReply").reset();
					     formReply.form.reset();
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
	    	    {header:"咨询Id",width : 100,dataIndex :'consultId',hidden : true},
	    	    {header : "咨询标题", width: 180,dataIndex : 'consultTitle',sortable : true},
	            {header : "咨询内容", width: 180,dataIndex : 'consultContent',sortable : true}, 
	            {header : "咨询者", width: 120,dataIndex : 'userName',sortable : true},
	            {header : "咨询时间", width: 280,dataIndex : 'consultTime',sortable : true},
	            {header : "是否回复", width: 120, dataIndex : 'isReply'}
	    ]);
	    
	    var store = new Ext.data.Store({
		 proxy : new Ext.data.HttpProxy({
			  url : 'sys/replyAction!getConsultListOrder.action',
			  method  : 'POST'
		 }), 
		 
		 reader : new Ext.data.JsonReader({
			 type:'json',
			 totalProperty : 'total',
			 root : 'data',
			 fields : ['consultId','consultTitle','consultContent','userName','consultTime','isReply']
		 }),
		 autoLoad : true
		 //remoteSort : true
	   });

		//加载数据
	    store.load({params:{
	    	start : 0,
	    	limit : 16
	    }});
	    
	   var replyGridPanel = new Ext.grid.EditorGridPanel({
	    id : 'reply-panel',
		//title : '系统回复', 
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
		renderTo : "replylist",
		tbar :['&nbsp;',{
			   id:'reply-btn',
			   text: '回复咨询',
			    icon : 'image/icon/comments.png',
	            handler: function(){
	             if(sm.getCount() < 1) {
            		 Ext.Msg.alert("提示信息",'请选择要回复的咨询！');
            		 return;
            	 }
	             var grid = Ext.getCmp('reply-panel'); 
            	 var cells = grid.getSelectionModel().getSelections()[0];
                 var idc = cells.get("consultId");
                 var isReply = cells.get("isReply"); 
                 
                 
				if (isReply == "已回复"){
					Ext.MessageBox.alert("消息提示框！", "您已经回复，无需再次回复！");
				} else {
	                  Ext.lib.Ajax.request(
						    	  'post',
						    	  'sys/consultAction!getConsultById.action',
						    	  {   success:function(response, option) {
							    	  var data = Ext.util.JSON.decode(response.responseText);
	                				  Ext.getCmp('question').setValue(data.consultTitle);
	                				  Ext.getCmp('consultId').setValue(data.consultId);
							    	}
						    	  },
						    	  'consultId=' + encodeURIComponent(Ext.encode(idc))
				    			);
				      winReply.show("reply-btn");
			      }
	            }
		}
		],
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
		'rowdblclick' : function(replyGridPanel, rowIndex, e){
		  replyGridPanel.getSelectionModel().clearSelections();
		  replyGridPanel.getSelectionModel().selectRow(rowIndex);
		  var record = replyGridPanel.getSelectionModel().getSelected();
		}
       }
	}); 

   	//渲染
	replyGridPanel.render();   
   });  
  </script>
  </head>
  
  <body>
  <div id="reply"  style="display: none;"></div>
  <div id="replylist" ></div>
  </body>
</html>
