<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>centerInfo</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- 
	<script type="text/javascript" src="<%=basePath%>/ext-3.0.0/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext-3.0.0/ext-all-debug.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>ext-3.0.0/resources/css/ext-all.css"></link>
     -->
	<script type="text/javascript">
	
	Ext.onReady(function(){
		Ext.QuickTips.init();
	/**
	 var CenterInfo = new Ext.data.Record.create([
			{name:"centerId",mapping:"centerId"},
			{name:"centerName",mapping:"centerName"},
			{name:"province",mapping:"province"},
			{name:"city",mapping:"city"},
			{name:"county",mapping:"county"},
			{name:"organcode",mapping:"organcode"},
			{name:"linkman",mapping:"linkman"},
			{name:"contact",mapping:"contact"},
			{name:"email",mapping:"email"},
			{name:"legalrepresent",mapping:"legalrepresent"}
	   ]);
	*/

	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
		  url: "centermg/centerManageAction!listServiceCenter.action",
	      method: 'post'
		}),
		
	   reader: new Ext.data.JsonReader({
			 root:"data",
			 type : 'json',
			 totalProperty : 'total',
			 fields : ['centerId','centerName','province','city',
			 'county','organcode','linkman','contact','email',
			 'legalrepresent']
		})
	});
	
	//store.load();
    store.load({params:{
    	start : 0,
    	limit : 16
    }});
    
	var sm = new Ext.grid.CheckboxSelectionModel();
	var gridPanel = new Ext.grid.GridPanel({
		id:"gridPanel",
		renderTo:"listCenterInfo",
		title:"",
		width:'100%',
		height:420,
		store:store,
        frame :true,
        loadMash:true,
		columns:[
			sm,
			{header:"id",dataIndex:"centerId",sortable:true,hidden :true,},
			{header:"中心名称",dataIndex:"centerName",sortable:true},
			{header:"省份",dataIndex:"province",sortable:true},
			{header:"城市",dataIndex:"city",sortable:true},
			{header:"区/县",dataIndex:"county",sortable:true},
			{header:"组织代码",dataIndex:"organcode",sortable:true},
			{header:"联系人",dataIndex:"linkman",sortable:true},
			{header:"联系方式",dataIndex:"contact",sortable:true},
			{header:"邮件",dataIndex:"email"},
			{header:"法人代表",dataIndex:"legalrepresent",sortable:true}
		],
		sm:sm,
		tbar:['&nbsp;',
		{
			text:'中心修改',
			icon:'image/icon/add.png',
			handler: function(){
				//var selectedKeys = gridPanel.selModel.selections.keys;//
				//var selectedKeys = gridPanel.selModel.selections;
				var selectedKeys = gridPanel.getSelectionModel().getSelections();
				if(selectedKeys.length > 1 ){
					Ext.Msg.alert("修改提示","每次只能修改一条记录!");
					return;
				} 
				//Ext.Msg.alert("修改id",selectedKeys[0].get('centerId'));
				Ext.Ajax.request({
                            	url:'centermg/centerManageAction!toEditView.action',
                            	method:'post',
                               	params: {'editServiceId': selectedKeys[0].get('centerId')},
                              	success: function(resp, opts){
                              		var centerId = selectedKeys[0].get('centerId');
									//alert(centerId);
                              		var path = "centerManage/editServiceCenter.jsp?centerId=";
                              		path = path + centerId;
									window.location.href = path;
                               	},
                               	failure: function(){ }
                               	
                });
			}
		},'-',
		{
			text:'中心删除',
			icon : 'image/icon/delete.png',
			handler: function(){
				var selectedKeys = gridPanel.selModel.selections.keys;
				//var sel = gridPanel.getSelectionModel().getSelected();//可以获取选择的对象
				if(selectedKeys){
          			Ext.Msg.confirm("删除注册信息","确定要删除吗？",function(btn){
               		 if(btn == 'yes'){
               		 //之后有两种操作，一种是在前台循环，将selectedKey传到后台
                    	for (var i = 0, max = selectedKeys.length; i < max; i++) {
　　　　　　　　　　　　　　　　			Ext.Ajax.request({
                            	url:'centermg/centerManageAction!deleteServiceCenterbyId.action',
                              	success: function(resp,opts){
                                     var msg = Ext.util.JSON.decode(resp.responseText);
           							 Ext.Msg.alert(msg);
                               	},
                               	failure: function(){ },
                               	params: {'delAuditId': selectedKeys[i]}
                           });　　　　
                      }//end_for
                 	//另一种是将selectedKeys数组传到后台。在后台处理。只是传的参数会有所不同。params:{'oids':selectedKeys}
                 	　gridPanel.render();
                 	}//end_if 
          			}); 
      			}else{ } 
			}
		}
		],
		bbar: new Ext.PagingToolbar({
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
               refreshText : "刷新",
        	})
	});
	gridPanel.render();
});
	</script>
  </head>
  
  <body>
    <div id="listCenterInfo"></div>
  </body>
</html>
