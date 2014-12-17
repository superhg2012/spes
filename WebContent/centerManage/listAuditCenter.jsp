<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
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
	<!-- 
	<script type="text/javascript" src="<%=basePath%>ext-3.0.0/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="<%=basePath%>ext-3.0.0/ext-all-debug.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>ext-3.0.0/resources/css/ext-all.css"></link>
     -->
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
<script type="text/javascript">

 function audit(value,metadata){
	  if(value == 0) {
	  	//�Ӻ������ͨ��
	  	return "<input type='Ext.button' style='width:45px'  value='δ���' />";
	  } else {
	  	return "<input type='Ext.button' style='width:45px'  value='�����'s onclick='alert(\""+"�����"+"\")' />";
	  }
	  return value;
}

Ext.onReady(function(){
	   Ext.QuickTips.init();
		var store = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
			  url: "centermg/centerRegisterAction!listAuditInfo.action",
		      method: 'post'
			}),
	   		 reader: new Ext.data.JsonReader({
				 root:"data",
				 type :'json',
				 totalProperty : 'total',
				 fields : ['id','centerId','centerName','province','city',
				 'county','organcode','linkman','contact','email','valid',
				 'legalrepresent','remarks']
	   		})
		});
		store.load({params:{
	    	start : 0,
	    	limit : 16
	    }});
		
	var searchField = new Ext.form.TextField({id:'filterField',allowBlank:false,blankText:'������Ҫ������������',emptyText :'��������'});
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	var gridPanel = new Ext.grid.GridPanel({
		id:"gridPanel",
		renderTo:"auditCenter",
		title:"�������",
		width:'100%',
		height:420,
		store:store,
        frame :true,
        loadMash:true,
		columns:[
			new Ext.grid.RowNumberer(),
			sm,
		//	{header:"id",dataIndex:"id",sortable:true},
			{header:"���ı��",dataIndex:"centerId",sortable:true,hidden :true},
			{header:"��������",dataIndex:"centerName",sortable:true},
			{header:"ʡ��",dataIndex:"province",sortable:true},
			{header:"����",dataIndex:"city",sortable:true},
			{header:"��/��",dataIndex:"county",sortable:true},
			{header:"��֯����",dataIndex:"organcode",sortable:true},
			{header:"��ϵ��",dataIndex:"linkman",sortable:true},
			{header:"��ϵ��ʽ",dataIndex:"contact",sortable:true},
			{header:"�ʼ�",dataIndex:"email"},
			{header:"���˴���",dataIndex:"legalrepresent",sortable:true},
			{header:"���",dataIndex:"valid",sortable:true,renderer:audit}
		],
		sm:sm,
		tbar: ['&nbsp;',
		{	
			text:'ɾ��',
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
                            	url:'centermg/centerRegisterAction!deleteAuditInfo.action',
                               	params: {'delAuditId': selectedKeys[i]},
                               	method : 'post',
                              	success: function(resp,opts){
                                     var msg = Ext.util.JSON.decode(resp.responseText);
           							 Ext.Msg.alert(msg);
                               	},
                               	failure: function(resp, options){
                               		alert(resp.responseText);
                               	}
                           });��������
                      }//end_for
                 	//��һ���ǽ�selectedKeys���鴫����̨���ں�̨����ֻ�Ǵ��Ĳ�����������ͬ��params:{'oids':selectedKeys}
                 	��gridPanel.render();
                 	}//end_if 
          			}); 
      			}else{ } 
			}
		},'-','&nbsp;�������ƣ�',searchField,'&nbsp;',{
			text : '����',
			icon : 'image/icon/search_icon.gif',
			handler : function (){
			   var value = searchField.getValue();
			   store.filter('centerName',value,false,false); 
			   return;
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
               refreshText : "ˢ��",
        	})
	});
	gridPanel.render();
});
	</script>

  </head>
  
  <body>
  <div id="auditCenter"></div>
  </body>
</html>
