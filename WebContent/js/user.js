Ext.onReady(function(){
	Ext.QuickTips.init();
	var centerId = Ext.get("centerId").dom.value;
	var contactField = new Ext.form.NumberField({name:'contact',allowBlank:false});
	//checkBox
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});// 设置复选框
 // 设置列
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),sm, 
    	    {header:"用户编号",width : 100,dataIndex :'userId',hidden : true},
            {header : "用户名", width: 120,dataIndex : 'realName',sortable : true,editor:true},
            {header : "性别", width: 120,dataIndex : 'gender',sortable : true,editor:true},
            {header : "身份证号", width: 180,dataIndex : 'IdCardNum',sortable : true,editor:new Ext.grid.GridEditor(new Ext.form.TextField({vtype:'alphanum',allowBlank:false}))},
            {header : "邮箱", width: 120,dataIndex : 'email',sortable : true,editor:new Ext.grid.GridEditor(new Ext.form.TextField({vtype : 'email'}))},
            {header : "电话号码", width: 180,dataIndex : 'contact',sortable : true,editor:new Ext.grid.GridEditor(contactField)},
            {header : "中心名称", width: 180,dataIndex : 'centerName',sortable : true}
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

	//加载数据
    store.load({params:{
    	start : 0,
    	limit : 15
    }});
	
	var searchField = new Ext.form.TextField({
		allowBlank :true,
		blankText : '请输入要查找的用户名',
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
		waitMsg :'数据加载中，请稍后...',
		viewConfig : { forceFit : true},
		stripeRows : true,
		renderTo : "user-div",
		tbar : ['&nbsp;',{
			text:'添加用户',
			icon : 'image/icon/add.png',
			handler:function(){
			    loadAddUserTab();
			}
		},'-',{
			text : '删除用户',
			icon : 'image/icon/delete.png',
			handler: function(){
			if(sm.getCount() < 1) {
        		 Ext.Msg.alert("提示信息",'请选择要删除的记录！');
        		 return;
            }
			 Ext.Msg.show({
				  title : '删除提示！',
            		 msg : '确定删除请选择是！',
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
					    			Ext.Msg.alert("提示信息",response.responseText);
					    			store.reload();
						    	},failure : function(response){
						    		Ext.Msg.alert("提示信息",response.responseText.respText);
						    		return;
						    	}
					    	  },
					    	  'userId=' + encodeURIComponent(Ext.encode(id))
			    			);
            		       // store.load(); //重新加载
				     }
            		 },
            		
            		scope : this,
            		animEl : 'elId',
            		icon : Ext.MessageBox.WARNING
				 
			 });
			}
		},'-',new Ext.Button(
			{
			text:'保存修改',
			icon : 'image/icon/disk.png',
			handler:function(saveBtn,event){
				var storeObj=this;
//				var modifiedRecords = storeObj.getModifiedRecords();
				var modifiedRecords = storeObj.modified.slice(0);
				var submitRecords=[];
				if(modifiedRecords.length==0){
					Ext.Msg.alert("提示信息",'没有发现修改过的记录，没有内容可以修改！');
					return;
				}
				for(var i=0,len=modifiedRecords.length;i<len;i++){
//					submitRecords.push(storeObj.isWithAllFields==true?modifiedRecords[i].data:modifiedRecords[i].modified);//获取record中的数据实体
					var jsonData = Ext.util.JSON.encode(modifiedRecords[i].data);
			    	submitRecords.push(jsonData);
			    }
				var json = "{" + '"user":[' + submitRecords + "]" +"}";
				saveBtn.disable();
				if(confirm("您确定要进行该操作？")==true){
					//正在保存改变，请等待。。
					Ext.Ajax.request({
						url:'userAction!upDateUser.action',
						params:{jsonData:json},
						success:function(){
							Ext.Msg.alert("提示信息","保存已成功。");
							storeObj.commitChanges();
							saveBtn.enable();
						},
						failure:function(){
							Ext.Msg.alert("提示信息",'修改操作未成功！');
							saveBtn.enable();
						},
						scope:storeObj
				   });
				} else {
					saveBtn.enable();
				}
			},
				scope:store
			}),'-','查找用户:',searchField,'&nbsp;',{
			text : '查找',
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
});