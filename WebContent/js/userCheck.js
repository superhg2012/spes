Ext.onReady(function(){
	Ext.QuickTips.init();
	var centerId = Ext.get("centerId").dom.value; //����Id
	//��˱��
	var audit = function(value){
		if(value == 'true'){
			return '<span class="checked_span">&nbsp;</span>';
		}else{
			return '<span class="unchecked_span">&nbsp;</span>';
		}
	};
	
	var  store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'userAction!getUnValidUserOfCenter.action',
			method:'post'
		}),
		reader : new Ext.data.JsonReader({
			type:'json',
		 	totalProperty : 'total',
		 	root : 'root',
			fields : ['userId','userName','realName','gender','email','valid']
		})
	});
	
//	store.load({params:{start:0,limit:15}});
	
		//��������
    store.load({params:{
    	start : 0,
    	limit : 15
    }});
    
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});// ���ø�ѡ��
	var cm = new Ext.grid.ColumnModel([
	    new Ext.grid.RowNumberer(),
	    sm,
	    {header : '�û����',width : 50,dataIndex:'userId',hidden:true},
	    {header : '�û���',width : 50,dataIndex:'userName',hidden:true},
		{header : '����',width : 150,dataIndex:'realName'},
		{header : '�Ա�', width:150,dataIndex:'gender'},
		{header : '����', width:200,dataIndex:'email'},
		{header : "��˽��",width:100,dataIndex:"valid",renderer:audit}
	]);
	
	var userEditorPanel = new Ext.grid.EditorGridPanel({
		width : '100%',
//		autoHeight:true,
		height:400,
		store : store,
		cm : cm,
		sm : sm,
		frame : true,
		border : true,
		loadMash : true,
		waitMsg :'���ݼ����У����Ժ�...',
//		viewConfig : { forceFit : true},
		stripeRows : true,
		renderTo :'userCheck-div',
	    tbar : ['&nbsp;',{
	    	text:'���ͨ��',
	    	icon : 'image/icon/lock.png',
	    	handler : function(){
	    	   var selections = userEditorPanel.getSelectionModel().getSelections();
	    	   if(selections.length > 0){
	    		   if(confirm("ȷ�����ͨ�����û���")== true){
		    		   var record = selections[0];
		    		   Ext.Ajax.request({
		    			   url : 'userAction!upDateUserValid.action',
		    			   method:'post',
		    			   callback : function(options, success, response){
		    			     if(success){
//		    			    	 Ext.Msg.alert("��ʾ��Ϣ","�û���˳ɹ���");
//		    			    	 userEditorPanel.getView().refresh();
		    			    	 store.reload();
		    			     } else {
		    			    	 Ext.Msg.alert("��ʾ��Ϣ","�û����ʧ�ܣ�");
		    			    	 return;
		    			     }
		    			   },
		    			   params : {userId:record.get("userId")}
		    		   });
	    		   }
	    	   } else {
	    		   Ext.Msg.alert("��ʾ��Ϣ","δѡ��Ҫ��˵��û�");
		    	   return;
	    	   }
	    	}
	    },'-'],
	    bbar:new Ext.PagingToolbar({
               pageSize : 10,
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
	
});