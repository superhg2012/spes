<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>�������</title>
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
			{header:"��������",dataIndex:"centerName",sortable:true},
			{header:"ʡ��",dataIndex:"province",sortable:true},
			{header:"����",dataIndex:"city",sortable:true},
			{header:"��/��",dataIndex:"county",sortable:true},
			{header:"��֯����",dataIndex:"organcode",sortable:true},
			{header:"��ϵ��",dataIndex:"linkman",sortable:true},
			{header:"��ϵ��ʽ",dataIndex:"contact",sortable:true},
			{header:"�ʼ�",dataIndex:"email",sortable:true},
			{header:"���˴���",dataIndex:"legalrepresent",sortable:true},
			{header:"��˽��",dataIndex:"valid",sortable:true,renderer:audit}
		],
		sm:sm,
		tbar:['&nbsp;',
		{
			iconCls:'icon-user-add',
			text:'���ͨ��',
			icon : 'image/icon/lock.png',
			handler: function(){
				var selected = gridPanel.getSelectionModel().getSelections();
			
				if(selected.length > 1 ){
				Ext.Msg.confirm("��ʾ��Ϣ","ÿ��ֻ���޸�һ����¼��",function(btn){});
					//Ext.Msg.alert("��ʾ��Ϣ","ÿ��ֻ���޸�һ����¼!");
					return;
				} 
				if(selected[0].get('valid') == 'true'){
				Ext.Msg.confirm("��ʾ��Ϣ","�������Ѿ�ͨ����ˣ�",function(btn){});
					//Ext.Msg.alert('��ʾ��Ϣ','�Ѿ�ͨ����ˣ�');
					return;
				}
				Ext.Ajax.request({
                            	url:'centermg/centerRegisterAction!passAudit.action',
                               	params: {'delAuditId': selected[0].get('id')},
                              	success: function(resp, opts){
                              	Ext.Msg.confirm("��ʾ��Ϣ","ͨ����ˣ�",function(btn){});
                              		//Ext.Msg.alert('��ʾ��Ϣ','ͨ����ˣ�');
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
               displayMsg : '��ʾ {0} - {1} �� {2} ��',
               emptyMsg : "û��������ʾ��",
               beforePageText : "ҳ�� ",
               afterPageText : "�� {0} ҳ",
               firstText : "��ҳ",
               lastText : "ĩҳ",
               nextText : "��һҳ",
               prevText : "��һҳ",
               refreshText : "ˢ��"
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
