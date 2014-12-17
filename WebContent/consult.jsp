<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="inc/comm.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>������ѯ</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  <script type="text/javascript">
   Ext.onReady(function(){
	   Ext.QuickTips.init();
	   //ͳһ�ƶ�������Ϣ��ʾ������ʾ��ʽ
	   Ext.form.Field.prototype.msgTarget = 'side';
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
		   frame : true,//�Ƿ���Ⱦ��
		   renderTo :'consult',
		   method : 'post',
		   url : 'sys/consultAction!pubConsult.action',
		   //����Ԫ������
		   items : [
			   new Ext.form.TextField({
				   fieldLabel : '�� ��',
				   id : 'title',
				   name : 'consultTitle',
				   selectOnFocus : true,//�õ������Զ�ѡ���ı�
				   allowBlank : false,
				   blankText: '���ⲻ��Ϊ��',
				   emptyText : '���������',
				   width : 400,
				   maxLength : 100
			   }),
			  
			   new Ext.form.HtmlEditor({
				   id : 'htmleditor',
				   name : 'consultContent',
				   width : 600,
				   height : 300,
				   fieldLabel :'��ѯ����',
				   emptyText : '��������ѯ����',
				   enableAlignments : true,
				   enableColors : true,
				   enableFont : true,
				   enableFontSize :true,
				   enalbeLinks :true,
				   enableLists:true,
				   enableSourceEdit:true
			   })
		   ],
		  // buttonAlign :'center',
		  
		   
	   };//end config
	   var form = new Ext.form.FormPanel(config);
	   
	   var win = new Ext.Window({
			title : '��Ϣ��ѯ',
			width : 600,
			height : 300,
			layout : 'fit',
			plain : true,
			resizable :false,
			closeAction : 'hide',
			split : true,
			items : form,
			shadow : true,
			modal : true,//ģ̬
			listeners : {
		      'hide' : function (e){
		         form.form.reset();
		         Ext.getCmp("htmleditor").reset();
		      }
			},
			buttons : [
				   new Ext.Button({
					   text : '��ѯ',
					   handler: function(){
					     if(!form.getForm().isValid()){
					    	 return;
					     }
					     //�ύ������
					     form.getForm().submit({
					    	success : function(curForm,response){
					    		Ext.Msg.alert("��ʾ��Ϣ",response.result.resptext);
					    		win.hide();
					    		form.form.reset();
					    	},
					    	failure : function(){
					    		Ext.Msg.show({title:'������Ϣ��ʾ',msg : response.result.resptext,buttons : Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
					    	}
					     });
					   }
				   }),
				   new Ext.Button({
					   text : '�� ��',
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
	    });// ���ø�ѡ��
	    // ������
	    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),sm, 
	    	    {header:"��ѯId",width : 100,dateIndex :'consultId',hidden : true},
	            {header : "��ѯ����", width: 180,dataIndex : 'consultTitle',sortable : true, editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:false}))}, 
	            {header : "��ѯ��", width: 120,dataIndex : 'userId',sortable : true},
	            {header : "��ѯʱ��", width: 280,dataIndex : 'consultTime',sortable : true},
	    ]);
	    
	    var store = new Ext.data.Store({
		 proxy : new Ext.data.HttpProxy({
			  url : 'sys/consultAction!getConsultList.action',
			  method  : 'POST',
		 }), 
		 
		 reader : new Ext.data.JsonReader({
			 type:'json',
			 totalProperty : 'total',
			 root : 'data',
			 fields : ['consultId','consultTitle','userId','consultTime']
		 }),
		 //autoLoad : true,
		 //remoteSort : true
	   });

		//��������
	    store.load({params:{
	    	start : 0,
	    	limit : 16
	    }});
	    store.setDefaultSort('consultTime','asc');
	    
	   var consultGridPanel = new Ext.grid.EditorGridPanel({
	    id : 'consult-panel',
		title : 'ϵͳ��ѯ', 
		width : '100%',
		height : 'auto',
		autoHeight : true,
		frame : true,
		border : true,
		loadMash : true,
		waitMsg :'���ݼ����У����Ժ�...',
		viewConfig : {
		  forceFit : true, 
		},
		store : store,
		sm : sm,
		cm : cm,
		stripeRows : true,
		//autoExpandColum : 'centerMC',
		renderTo : "consultlist",
		tbar :['&nbsp;&nbsp;&nbsp;',{
			 iconCls: 'icon-center-add',
			   id:'add-btn',
			   text: '��Ϣ��ѯ',
			    icon : 'image/icon/building_add.png',
	            handler: function(){
			      win.show("add-btn");
	            }
		},'-',{
			 iconCls: 'icon-center-edit',
	            text: '��ѯ�༭',
	             icon : 'image/icon/building_edit.png',
	            handler: function(){
			      var m = store.modified.slice(0);
			      var data = [];
			      Ext.each(m,function(item){
			    	 data.push(item.data); 
			      });
			      alert(Ext.encode(data));//������ת����json��ʽ
			      Ext.lib.Ajax.request(
			    	  'post',
			    	  'sys/consultAction!editconsult.action',
			    	  {   success:function(response) {
				    	  Ext.Msg.alert('��Ϣ',response.responseText,function(){
			    				  store.reload();
			    		  });
				    	}
			    	  },
			    	  'row=' + encodeURIComponent(Ext.encode(data))
			    	);
			    	store.modified =[];
			 
	            }
		},'-',{
			 iconCls: 'icon-center-delete',
	            text: 'ɾ����ѯ',
	             icon : 'image/icon/building_delete.png',
	            handler: function(){
			      if(sm.getCount() < 1) {
            		 Ext.Msg.alert("��ʾ��Ϣ",'��ѡ��Ҫɾ���ļ�¼��');
            		 return;
            	 }
            	 Ext.Msg.show({
            		 title : 'ɾ����ʾ��',
            		 msg : 'ȷ��ɾ����ѡ���ǣ�',
            		 buttons : Ext.Msg.YESNOCANCEL,
            		 
            		 fn : function(yesno){
            		    if(yesno == 'yes'){
            		        store.remove(sm.getSelected());
            		        store.load(); //���¼���
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
               displayMsg : '��ʾ {0} - {1} �� {2} ��',
               emptyMsg : "û��������ʾ��",
               beforePageText : "ҳ�� ",
               afterPageText : "�� {0} ҳ",
               firstText : "��ҳ",
               lastText : "ĩҳ",
               nextText : "��һҳ",
               prevText : "��һҳ",
               refreshText : "ˢ��",
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

   	//��Ⱦ
	consultGridPanel.render();   
   });  
  </script>
  </head>
  
  <body>
  <div id="consult"  style="display: none;"></div>
  <div id="consultlist" ></div>
  </body>
</html>
