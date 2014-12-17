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
			{header:"��������",dataIndex:"centerName",sortable:true},
			{header:"ʡ��",dataIndex:"province",sortable:true},
			{header:"����",dataIndex:"city",sortable:true},
			{header:"��/��",dataIndex:"county",sortable:true},
			{header:"��֯����",dataIndex:"organcode",sortable:true},
			{header:"��ϵ��",dataIndex:"linkman",sortable:true},
			{header:"��ϵ��ʽ",dataIndex:"contact",sortable:true},
			{header:"�ʼ�",dataIndex:"email"},
			{header:"���˴���",dataIndex:"legalrepresent",sortable:true}
		],
		sm:sm,
		tbar:['&nbsp;',
		{
			text:'�����޸�',
			icon:'image/icon/add.png',
			handler: function(){
				//var selectedKeys = gridPanel.selModel.selections.keys;//
				//var selectedKeys = gridPanel.selModel.selections;
				var selectedKeys = gridPanel.getSelectionModel().getSelections();
				if(selectedKeys.length > 1 ){
					Ext.Msg.alert("�޸���ʾ","ÿ��ֻ���޸�һ����¼!");
					return;
				} 
				//Ext.Msg.alert("�޸�id",selectedKeys[0].get('centerId'));
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
			text:'����ɾ��',
			icon : 'image/icon/delete.png',
			handler: function(){
				var selectedKeys = gridPanel.selModel.selections.keys;
				//var sel = gridPanel.getSelectionModel().getSelected();//���Ի�ȡѡ��Ķ���
				if(selectedKeys){
          			Ext.Msg.confirm("ɾ��ע����Ϣ","ȷ��Ҫɾ����",function(btn){
               		 if(btn == 'yes'){
               		 //֮�������ֲ�����һ������ǰ̨ѭ������selectedKey������̨
                    	for (var i = 0, max = selectedKeys.length; i < max; i++) {
��������������������������������			Ext.Ajax.request({
                            	url:'centermg/centerManageAction!deleteServiceCenterbyId.action',
                              	success: function(resp,opts){
                                     var msg = Ext.util.JSON.decode(resp.responseText);
           							 Ext.Msg.alert(msg);
                               	},
                               	failure: function(){ },
                               	params: {'delAuditId': selectedKeys[i]}
                           });��������
                      }//end_for
                 	//��һ���ǽ�selectedKeys���鴫����̨���ں�̨����ֻ�Ǵ��Ĳ�����������ͬ��params:{'oids':selectedKeys}
                 	��gridPanel.render();
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
               displayMsg : '��ʾ {0} - {1} �� {2} ��',
               emptyMsg : "û��������ʾ��",
               beforePageText : "ҳ�� ",
               afterPageText : "�� {0} ҳ",
               firstText : "��ҳ",
               lastText : "ĩҳ",
               nextText : "��һҳ",
               prevText : "��һҳ",
               refreshText : "ˢ��",
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
