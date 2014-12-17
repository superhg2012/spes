<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>中心审核</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  	<style type="text/css">
  	
  	span.checked_span {
		background: url(centerManage/checked.png) no-repeat right;
		width: 16px;
		line-height: 16px;
		padding-left: 10px;
	}

	span.unchecked_span {
		background: url(centerManage/unchecked.png) no-repeat right;
		width: 16px;
		line-height: 16px;
		padding-left: 10px;
	}
  	</style>
  
	<script type="text/javascript">
	
	Ext.onReady(function(){
	
	var audit = function(value){
		if(value == 'true'){
			return '<span class="checked_span">&nbsp;</span>';
		}else{
			return '<span class="unchecked_span">&nbsp;</span>';
		}
	}
	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
		  url: "centermg/centerRegisterAction!listAuditInfo.action",
	      method: 'post'
		}),
	   reader: new Ext.data.JsonReader({
			 root:"data",
			 type : 'json',
			 totalProperty : 'total',
			 fields : ['id','centerName','province','city',
			 'county','organcode','linkman','contact','email',
			 'legalrepresent','valid']
		})
	});
	
	//store.load();
    store.load({params:{
    	start : 0,
    	limit : 16
    }});
   
	var sm = new Ext.grid.CheckboxSelectionModel();
	//
	var gridPanel = new Ext.grid.GridPanel({
		id:"gridPanel",
		renderTo:"listCenterInfo",
		width:'100%',
		height:420,
		store:store,
        frame :true,
        loadMash:true,
		columns:[
			sm,
			{header:"Id",dataIndex:"id",sortable:true,hideable: false, hidden: true },
			{header:"中心名称",dataIndex:"centerName",sortable:true},
			{header:"省份",dataIndex:"province",sortable:true},
			{header:"城市",dataIndex:"city",sortable:true},
			{header:"区/县",dataIndex:"county",sortable:true},
			{header:"组织代码",dataIndex:"organcode",sortable:true},
			{header:"联系人",dataIndex:"linkman",sortable:true},
			{header:"联系方式",dataIndex:"contact",sortable:true},
			{header:"邮件",dataIndex:"email",sortable:true},
			{header:"法人代表",dataIndex:"legalrepresent",sortable:true},
			{header:"审核结果",dataIndex:"valid",sortable:true,renderer:audit}
		],
		sm:sm,
		tbar:['&nbsp;',
		{
			iconCls:'icon-user-add',
			text:'审核通过',
			icon : 'image/icon/lock.png',
			handler: function(){
				var selected = gridPanel.getSelectionModel().getSelections();
			
				if(selected.length > 1 ){
				Ext.Msg.confirm("提示信息","每次只能修改一条记录！",function(btn){});
					//Ext.Msg.alert("提示信息","每次只能修改一条记录!");
					return;
				} 
				if(selected[0].get('valid') == 'true'){
				Ext.Msg.confirm("提示信息","该中心已经通过审核！",function(btn){});
					//Ext.Msg.alert('提示信息','已经通过审核！');
					return;
				}
				Ext.Ajax.request({
                            	url:'centermg/centerRegisterAction!passAudit.action',
                               	params: {'delAuditId': selected[0].get('id')},
                              	success: function(resp, opts){
                              	Ext.Msg.confirm("提示信息","通过审核！",function(btn){});
                              		//Ext.Msg.alert('提示信息','通过审核！');
                              		store.reload();
                               	},
                               	failure: function(){ }
                });
                //store.reload();
                //gridPanel.fresh();
			}
		}
		],
		bbar:
			 new Ext.PagingToolbar({
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
