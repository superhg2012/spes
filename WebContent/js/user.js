Ext.onReady(function(){
	Ext.QuickTips.init();
	var centerId = Ext.get("centerId").dom.value;
	var contactField = new Ext.form.NumberField({name:'contact',allowBlank:false});
	//checkBox
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});// ���ø�ѡ��
 // ������
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),sm, 
    	    {header:"�û����",width : 100,dataIndex :'userId',hidden : true},
            {header : "�û���", width: 120,dataIndex : 'realName',sortable : true,editor:true},
            {header : "�Ա�", width: 120,dataIndex : 'gender',sortable : true,editor:true},
            {header : "���֤��", width: 180,dataIndex : 'IdCardNum',sortable : true,editor:new Ext.grid.GridEditor(new Ext.form.TextField({vtype:'alphanum',allowBlank:false}))},
            {header : "����", width: 120,dataIndex : 'email',sortable : true,editor:new Ext.grid.GridEditor(new Ext.form.TextField({vtype : 'email'}))},
            {header : "�绰����", width: 180,dataIndex : 'contact',sortable : true,editor:new Ext.grid.GridEditor(contactField)},
            {header : "��������", width: 180,dataIndex : 'centerName',sortable : true}
    ]);
    
    var store = new Ext.data.Store({
	 proxy : new Ext.data.HttpProxy({
		  url : 'userAction!getUserList.action?centerId='+centerId,
		  method  : 'POST'
	 }), 
	 
	 reader : new Ext.data.JsonReader({
		 type:'json',
		 totalProperty : 'total',
		 root : 'data',
		 fields : ['userId','realName','gender','IdCardNum','email','contact','centerName']
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
	
	var userPanel = new Ext.grid.EditorGridPanel({
		id : 'usergrid',
		title : '',
		autoHeight : true,
		cm : cm,
		sm : sm,
		store : store,
		width : '100%',
		height : 'auto',
		autoHeight : true,
		frame : true,
		border : true,
		loadMash : true,
		waitMsg :'���ݼ����У����Ժ�...',
		viewConfig : { forceFit : true},
		stripeRows : true,
		renderTo : "user-div",
		tbar : ['&nbsp;',{
			text:'����û�',
			icon : 'image/icon/add.png',
			handler:function(){
			    loadAddUserTab();
			}
		},'-',{
			text : 'ɾ���û�',
			icon : 'image/icon/delete.png',
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
				    	var grid = Ext.getCmp('usergrid'); 
            		    var cells = grid.getSelectionModel().getSelections()[0];
				    	var id = cells.get("userId");
  				     	Ext.lib.Ajax.request(
					    	  'post',
					    	  'userAction!deleteUser.action',
					    	  {   success:function(response) {
					    			Ext.Msg.alert("��ʾ��Ϣ",response.responseText);
					    			store.reload();
						    	},failure : function(response){
						    		Ext.Msg.alert("��ʾ��Ϣ",response.responseText.respText);
						    		return;
						    	}
					    	  },
					    	  'userId=' + encodeURIComponent(Ext.encode(id))
			    			);
            		       // store.load(); //���¼���
				     }
            		 },
            		
            		scope : this,
            		animEl : 'elId',
            		icon : Ext.MessageBox.WARNING
				 
			 });
			}
		},'-',new Ext.Button(
			{
			text:'�����޸�',
			icon : 'image/icon/disk.png',
			handler:function(saveBtn,event){
				var storeObj=this;
//				var modifiedRecords = storeObj.getModifiedRecords();
				var modifiedRecords = storeObj.modified.slice(0);
				var submitRecords=[];
				if(modifiedRecords.length==0){
					Ext.Msg.alert("��ʾ��Ϣ",'û�з����޸Ĺ��ļ�¼��û�����ݿ����޸ģ�');
					return;
				}
				for(var i=0,len=modifiedRecords.length;i<len;i++){
//					submitRecords.push(storeObj.isWithAllFields==true?modifiedRecords[i].data:modifiedRecords[i].modified);//��ȡrecord�е�����ʵ��
					var jsonData = Ext.util.JSON.encode(modifiedRecords[i].data);
			    	submitRecords.push(jsonData);
			    }
				var json = "{" + '"user":[' + submitRecords + "]" +"}";
				saveBtn.disable();
				if(confirm("��ȷ��Ҫ���иò�����")==true){
					//���ڱ���ı䣬��ȴ�����
					Ext.Ajax.request({
						url:'userAction!upDateUser.action',
						params:{jsonData:json},
						success:function(){
							Ext.Msg.alert("��ʾ��Ϣ","�����ѳɹ���");
							storeObj.commitChanges();
							saveBtn.enable();
						},
						failure:function(){
							Ext.Msg.alert("��ʾ��Ϣ",'�޸Ĳ���δ�ɹ���');
							saveBtn.enable();
						},
						scope:storeObj
				   });
				} else {
					saveBtn.enable();
				}
			},
				scope:store
			}),'-','�����û�:',searchField,'&nbsp;',{
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
               displayInfo : true,
               store : store,
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