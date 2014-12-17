//���ڹ���
Ext.onReady(function(){
	Ext.QuickTips.init();

	var store = new Ext.data.Store({
	 proxy : new Ext.data.HttpProxy({
		  url : 'sys/windowAction!getWindowListByCenterId.action?centerId='+Ext.get("centerId").dom.value,//tod o
		  method  : 'POST'
	 }), 
	 
	 reader : new Ext.data.JsonReader({
		 type:'json',
		 root : 'data',
		 totalProperty : 'total',
		 fields : ['windowId','windowName','centerId','windowBussiness','windowDesc']
	 }),
	 autoLoad : true
   });

	//��������
    store.load({params:{
    	start : 0,
    	limit : 10
    }});
    
    //ʹ�ã����õ�comobox
/**    var windowState = [['����','����'],['����','����']];
    var combox = new Ext.form.ComboBox({
    	store : new Ext.data.SimpleStore({
    		fields:['value','text'],
            data:windowState
    	}),
		displayField:'text',
		valueField:'value',
	    mode: "local",
		readOnly: true,
		triggerAction:"all"
    });
    */
   	//checkBox
	var sm = new Ext.grid.CheckboxSelectionModel({
	    	singleSelect : true
	});// ���ø�ѡ��
	var cm = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(),
	   {header : "���ڱ��", width : 100,dataIndex : 'windowId',hidden : true},
	   {header : "��������", width : 250,dataIndex : 'windowName',editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:false,blankText:'�����ֶ�'}))},
	   {header : "����ҵ��",width : 300,dataIndex: 'windowBussiness',editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:false,blankText:'�����ֶ�'}))},
	   {header : "��������",width : 300,dataIndex: 'windowDesc',editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))},
	   {header : "���ı��", width : 100,dataIndex : 'centerId',hidden :true}
	]);
	
	var windowRecord = Ext.data.Record.create([
		{name:'windowId',type:'string'},
		{name:'windowName',type:'string'},
		{name:'windowBussiness',type:'string'},
		{name:'windowDesc',type:'string'},
		{name:'centerId',tyoe:'string'}
		]);
	
	var windowGrid = new Ext.grid.EditorGridPanel({
		id:'winpanel',
		height : 200,
		region : 'center',
		store : store,
		layout : 'fit',
		cm : cm,
		sm : sm,
		tbar : ['&nbsp;',{
			text:'��Ӵ���',
			icon : 'image/icon/add.png',
			handler : function(){
     		   var intValue = {windowId:'',windowName:'',windowBussiness:'',windowDesc:'',centerId:''}
 		       var p = new windowRecord(intValue);
			   var record = new Ext.data.Record({});
			   windowGrid.stopEditing();
           	   store.insert(0,p);
         	   windowGrid.startEditing(0,2);
         	   p.dirty = true;				//������
//         	   p.modified = initValue;      
         	   if(store.modified.indexOf(p) == -1){
         			store.modified.push(p); //��ӵ����ص�store��
         	   }
			}
		},'-',{
			text : '���洰��',
			icon : 'image/icon/disk.png',
			handler : function(){
			    var modifiedRecords = store.modified.slice(0);
				var submitRecords=[];
				if(modifiedRecords.length==0){
					Ext.Msg.alert("��ʾ��Ϣ",'û�з����������޸Ĺ��ļ�¼��');
					return;
				}
				
				for(var i=0,len=modifiedRecords.length;i<len;i++){
					var jsonData = Ext.util.JSON.encode(modifiedRecords[i].data);
			    	submitRecords.push(jsonData);
			    }
				
				var json = "{" + '"window":[' + submitRecords + "]" +"}";
				Ext.Ajax.request({
					url:'sys/windowAction!addOrUpdateWindow.action',
					params:{jsonData:json},
					callback:function(options,success,response){
						if(success){
							Ext.Msg.alert("��ʾ��Ϣ","����ɹ���"); 
							store.reload();
						} else{
							Ext.Msg.alert("��ʾ��Ϣ","����ʧ�ܣ�");
						}
					}
					
				});
			}
		}],
		bbar : new Ext.PagingToolbar({
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
	
	var layoutPanel = new Ext.Panel({
		id : 'total',
		title : '',
		width : '100%',
		height:500,
		renderTo : 'winManage',
		layout : 'border',
		items : [windowGrid]
	});
	
});