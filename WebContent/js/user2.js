Ext.onReady(function(){
	Ext.QuickTips.init();
	//checkBox
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});// ���ø�ѡ��
 // ������
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),sm, 
    	    {header:"�û����",width : 100,dataIndex :'userId',hidden : true},
            {header : "�û���", width: 180,dataIndex : 'userName',sortable : true}, 
            {header : "��ɫ", width: 180,dataIndex : 'roleName',sortable : true},
            {header : "��������", width: 120,dataIndex : 'centerName',sortable : true}
    ]);
    
    var store = new Ext.data.Store({
	 proxy : new Ext.data.HttpProxy({
		  url : 'userAction!getUserList.action?centerId='+1,
		  method  : 'POST'
	 }), 
	 
	 reader : new Ext.data.JsonReader({
		 type:'json',
		 totalProperty : 'total',
		 root : 'data',
		 fields : ['userId','userName','roleName','centerName']
	 })
   });

	//��������
    store.load({params:{
    	start : 0,
    	limit : 15
    }});
	
	var searchField = new Ext.form.TextField({
		allowBlank :true,
		blankText : '������Ҫ���ҵ��û���',
		enableKeyEvents: true,
		listeners :{
			'keyup' : function(record,id){
				store.filterBy(function(record,id){
					var value = searchField.getValue(); 
					if(value =="" || value==null){
						return true;
					}
					return (record.get("userName")===searchField.getValue()) ? true:false;
				});
			}
		}
	});
	
	var userPanel = new Ext.grid.GridPanel({
		id : 'usergrid',
		title : '',
		cm : cm,
		sm : sm,
		store : store,
		width : '100%',
		autoHeight : true,
		frame : true,
		border : true,
		loadMash : true,
		waitMsg :'���ݼ����У����Ժ�...',
		viewConfig : { forceFit : true},
		stripeRows : true,
		renderTo : "user-div",
		tbar : ['&nbsp;',{
			text : 'ɾ���û�',
			icon : 'image/icon/delete.png',
			handler: function(){
			}
		},'-',{
			text : '�༭�û�',
			icon : 'image/icon/user_edit.png',
			handler : function(){
			
			}
		},'-','�����û�:',searchField,'&nbsp;',{
			text : '����',
			icon : 'image/icon/search_icon.gif',
			handler: function(){
				if(searchField.isValid()){
					var value = searchField.getValue();
				    //store.filter('userName',value,false,false);
					store.filterBy(function(record,id){
						if(value="" || value==null){
							return true;
						}
					   return (record.get("userName")===searchField.getValue())? true:false;
					});
				}
			}
		}],
		bbar : new Ext.PagingToolbar({
               pageSize : 15,
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