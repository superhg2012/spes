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

		<title>公告查看</title>

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

	    var configCheck = {
		   id :'form1',
		   title : '',
		   labelSeparator : ':',
		   labelWidth : 60,
		   labelAlign : 'right',
		   bodyStyle  : 'padding:5 5 5 5',
		   defaultType: 'textfield',
		   height : 'auto',
		   width : 700,
		   frame : true,//是否渲染表单
		   renderTo :'checkNotice',
		   //表单子元素数组
		   items : [
			   new Ext.form.TextField({
				   fieldLabel : '公告标题',
				   id : 'titleCheck',
				   name : 'title',
				   selectOnFocus : true,//得到焦点自动选择文本
				   allowBlank : false,
				   blankText: '标题不能为空',
				   emptyText : '请输入标题',
				   width : 400,
				   maxLength : 100,
				   readOnly : true
			   }),
			   new Ext.form.HtmlEditor({
				   id : 'contentCheck',
				   name : 'content',
				   width : 600,
				   height : 300,
				   fieldLabel :'公告内容',
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
	   };//end config
	   
	   var formCheck = new Ext.form.FormPanel(configCheck);
	   var winCheck = new Ext.Window({
			title : '公告查看',
			width : 600,
			height : 300,
			layout : 'fit',
			plain : true,
			resizable :false,
			closeAction : 'hide',
			split : true,
			items : formCheck,
			shadow : true,
			modal : true,//模态
			buttons : [
				   new Ext.Button({
					   text : '返回',
					   handler: function(){
					   		win.hide();
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
			  url : 'sys/noticeAction!checkNotice.action',
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
	  
	   var noticeGridPanelCheck = new Ext.grid.EditorGridPanel({
	    id : 'noticePanel',
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
		renderTo : "noticelistByUser",
		tbar :['&nbsp;',{
			 iconCls: 'icon-center-add',
			   id:'show-btn',
			   text: '查看公告详细内容',
			    icon : 'image/icon/email_open_image.png',
	            handler: function(){
	              if(sm.getCount() < 1) {
            		 Ext.Msg.alert("提示信息",'请选择要查看的公告！');
            		 return;
            	 }
            	 var grid = Ext.getCmp('noticePanel'); 
            	 var cells = grid.getSelectionModel().getSelections()[0];
                 var ids = cells.get("noticeId");
                  Ext.lib.Ajax.request(
					    	  'post',
					    	  'sys/noticeAction!getNoticeById.action',
					    	  {   success:function(response, option) {
						    	var data = Ext.util.JSON.decode(response.responseText);
						    	  Ext.getCmp('titleCheck').setValue(data.noticeTitle);
                				  Ext.getCmp('contentCheck').setValue(data.noticeContent);
                				 
						    	}
					    	  },
					    	  'noticeId=' + encodeURIComponent(Ext.encode(ids))
			    			);
			    		 winCheck.show("show-btn");
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
		'rowdblclick' : function(noticeGridPanelCheck, rowIndex, e){
		  noticeGridPanelCheck.getSelectionModel().clearSelections();
		  noticeGridPanelCheck.getSelectionModel().selectRow(rowIndex);
		  var record = noticeGridPanelCheck.getSelectionModel().getSelected();
		}
       }
	}); 
   	//渲染
	noticeGridPanelCheck.render();   
   });  
  </script>
  </head>
  
  <body>
  <div id="checkNotice"  style="display: none;"></div>
  <div id="noticelistByUser" ></div>
  </body>
</html>
