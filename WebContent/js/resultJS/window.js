if(Ext.isChrome===true){       
        var chromeDatePickerCSS = ".x-date-picker {border-color: #1b376c;background-color:#fff;position: relative;width: 185px;}";
        Ext.util.CSS.createStyleSheet(chromeDatePickerCSS,'chromeDatePickerStyle');
}

Ext.onReady(function(){
	var gridStoreWindow;//���store
	var gridCmWindow;//���columnModel
	var pSizeWindow = 10;
	var dateArrWindow;//�������飬���ڹ���columnModel��
	var cumgridWindow;//���
	var typeWindow = 'month';//month, quarter, year
	var chartStoreWindow;//����ͼstore
	var seriesWindow;//����ͼseries
	var removeListWindow = new Array();
	var displayListWindow = new Array();
	var removeStrWindow = removeListWindow.join(",");
	var gridStoreDetailWindow;//��ϸ����store
	var gridCmDetailWindow;//��ϸ����columnmodel
	var pSizeDetailWindow = 9;//��ϸ����ÿҳ����
	var cumgridDetailWindow;
	var chartStoreDetailWindow;
	var seriesDetailWindow;
	var curWindowId;
	var curWindowIdArr = new Array();
	var curWindowIdArrForSeries = new Array();
	var windowForm = new Ext.form.FormPanel({
  			title:'���ڼ�Ч�������۽��չʾ',
  			frame:true,
  			renderTo : 'bodysGyxWindow',
  			labelAlign:'left',
  			id : 'windowForm',
  			bodyPadding:5,
  			layout : 'table',
  			layoutConfig: {columns:10},
  			defaults: { width:150},
  			items : [
  				{xtype : 'label',text : 'ʱ�䷶Χ  ��'},
  				{    
   			 		xtype : 'datefield',
                    fieldLabel : 'ʱ���',
        			name : 'fromWindow',
		            id : 'fromWindow',
		            invalidText : 'ʱ�������ϸ�ʽ:2000-01-01',
		            format : 'Y-m-d',
                },
                {xtype : 'label',text : '~'	},
  				{
	   				xtype : 'datefield',
	              	fieldLabel : '��',
					name : 'toWindow',
	           	 	id : 'toWindow',
	           	 	invalidText : 'ʱ�������ϸ�ʽ:2000-01-01',
	            	format : 'Y-m-d',
	            },{colspan : 1, text : ' ',width:50},
            		   new Ext.form.Radio({
                		   name : 'mjy',
                		   id : 'rad1Window',
                		   inputValue : 'month',
                		   boxLabel : '�½��չʾ'
                	   }),new Ext.form.Radio({
                		   name : 'mjy',
                		   id : 'rad2Window',
                		   inputValue : 'quarter',
                		   boxLabel : '���Ƚ��չʾ'
                	   }),new Ext.form.Radio({
                		   name : 'mjy',
                		   id : 'rad3Window',
                		   inputValue : 'year',
                		   boxLabel : '����չʾ'
                	   }),{colspan : 1, text : ' ',width : 50},
	            			new Ext.Button({
	            				text : '��ѯ',
	            				id : 'queryButtonWindow',
	            				minWidth : 800,
	            				listeners : {
	            					"click" : queryWindow
	            				}
	            			})
  				]
  		});
  	Ext.get('rad1Window').on('click',modeMonthWindow);
  	Ext.get('rad2Window').on('click',modeQuarterWindow);
  	Ext.get('rad3Window').on('click',modeYearWindow);
  	
  	function modeMonthWindow(){
  		typeWindow = "month";
  		queryWindow();
  	}
  	function modeQuarterWindow(){
  		typeWindow = "quarter";
  		queryWindow();
  	}
  	function modeYearWindow(){
  		typeWindow = "year";
  		queryWindow();
  	}
	function queryWindow(){
		curWindowIdArr = new Array();
		displayListWindow = new Array();
		curWindowId = "";
		var from = Ext.fly("fromWindow").getValue();
		var to = Ext.fly("toWindow").getValue();
		if(from == "" ^ to == ""){
			Ext.MessageBox.alert("��ʾ��Ϣ","����д��ʼ-��������");
			return false;
		}
		var requestConfigWindow = {
			url : "gyx/windowResult/availbleWindowAction.action",
			method : 'post',
			params :{'from':from,'to':to,'type':typeWindow,'removeList':removeListWindow},
			callback :function(options,success,response){
				if(response.responseText == "Date error"){
  					Ext.MessageBox.alert("��ʾ��Ϣ","����д��ʼ�ͽ�������");
  					return false;
  				}
  				if(response.responseTest == "inner error"){
  					Ext.MessageBox.alert("��ʾ��Ϣ","����������Ժ�����");
  					return false;
  				}
  				var json = Ext.util.JSON.decode(response.responseText);
  				dateArrWindow = eval(json.dateList);
  				gridStoreWindow = generateJSONStoreWindow("gyx/windowResult/availbleWindowAction.action",dateArrWindow,typeWindow);
  				gridCmWindow = generateGridColumnStoreWindow(dateArrWindow,'����','windowName');
  				Ext.getCmp("cumgridWindow").destroy();
				var sm0 = new Ext.grid.CheckboxSelectionModel({handleMouseDown: showDetailWindow});
	  			sm0.on('rowselect',function(sm_,rowIndex,record){deleteParamWindow(sm_,rowIndex,record)});
	  			sm0.on('rowdeselect',function(sm_,rowIndex,record){addParamWindow(sm_,rowIndex,record)});
	  			cumgridWindow = new Ext.grid.GridPanel({
		  			renderTo:'tableGyxWindow',
		  			id:'cumgridWindow',
		  			sm:sm0,
		  			store:gridStoreWindow,
		  			stripeRows:true,
		  			viewConfig:{
		  				forceFit:true,
		  				sortAscText : '����',
		  				sortDescText : '����'
		  			},
		  			height:298,
		  			autowidth:300,
		  			colModel:gridCmWindow,
		  			bbar : new Ext.PagingToolbar({
		                    	  pageSize : pSizeWindow,
		                    	  store : gridStoreWindow,
		                    	  displayInfo : true,
		                    	  displayMsg : "",
		                    	  emptyMsg : 'û�м�¼'
		                      }),
		            remoteSort: true
		  		});
		  		gridStoreWindow.load({params:{start: 0,limit:pSizeWindow}});
		  		Ext.getCmp("chartPanelWindow").destroy();
		  		chartStoreWindow = generateChartStoreWindow(json);
		  		detailChartWindow = new Ext.Panel({
			        iconCls:'chart',
			        title: '�����ܷ�����ͼ',
			        id : 'detailPanelWindow',
			        frame:true,
			        width:1500,
			        height:298,
			        layout:'fit',
			        items: {
			            xtype: 'linechart',
			            store: chartStoreWindow,
			            url: 'ext-3.0.0/resources/charts.swf',
			            xField: 'name',
			            series: json.series
			        }

		    	});
		  		var chartPanelWindow = new Ext.form.FormPanel({
			 		frame:true,
		  			autoHeight:true,
		  			autoWidth:true,
		  			id : 'chartPanelWindow',
		  			labelSeparator:':',
		  			labelAlign:'left',
		  			renderTo:'containerWindow',
		  			bodyPadding:5,
		  			items : [{
				        layout : 'column',
				        items : [{
				                columnWidth : 1, 
								layout : 'form',
								style:"padding-top:0px;padding-left:1px;padding-bottom:1px",
				       			items : detailChartWindow
				        				}]
		                      }]
						 });
			}
		}
		Ext.Ajax.request(requestConfigWindow);
	}
	
	var requestConfigWindow = {
		url : "gyx/windowResult/availbleWindowAction.action",
		callback :function(options,success,response){
			var json = Ext.util.JSON.decode(response.responseText);
			var sRoot = eval(json.root);
			for(var i = 0; i<sRoot.length; i++){
				curWindowIdArrForSeries.push(sRoot[i].windowId);
			}
			dateArrWindow = eval(json.dateList);
			gridStoreWindow = generateJSONStoreWindow('gyx/windowResult/availbleWindowAction.action',dateArrWindow,'month');
			gridCmWindow = generateGridColumnStoreWindow(dateArrWindow,'����','windowName');
			var sm0 = new Ext.grid.CheckboxSelectionModel({handleMouseDown: showDetailWindow});
	  		sm0.on('rowselect',function(sm_,rowIndex,record){deleteParamWindow(sm_,rowIndex,record)});
	  		sm0.on('rowdeselect',function(sm_,rowIndex,record){addParamWindow(sm_,rowIndex,record)});
	  		cumgridWindow = new Ext.grid.GridPanel({
	  			renderTo:'tableGyxWindow',
	  			id:'cumgridWindow',
	  			sm:sm0,
	  			store:gridStoreWindow,
	  			stripeRows:true,
	  			viewConfig:{
	  				forceFit:true,
	  				sortAscText : '����',
	  				sortDescText : '����'
	  			},
	  			height:299,
	  			autowidth:300,
	  			colModel:gridCmWindow,
	  			bbar : new Ext.PagingToolbar({
	                    	  pageSize : pSizeWindow,
	                    	  store : gridStoreWindow,
	                    	  displayInfo : true,
	                    	  displayMsg : "",
	                    	  emptyMsg : 'û�м�¼'
	                      }),
	            remoteSort: true
	  		});
	  		gridStoreWindow.load({params:{start: 0,limit:pSizeWindow}});
	  		chartStoreWindow = generateChartStoreWindow(json);
	  		detailChartWindow = new Ext.Panel({
									        iconCls:'chart',
									        title: '�����ܷ�����ͼ',
									        id : 'detailPanelWindow',
									        frame:true,
									        width:1500,
									        height:299,
									        layout:'fit',
									        items: {
									            xtype: 'linechart',
									            store: chartStoreWindow,
									            url: 'ext-3.0.0/resources/charts.swf',
									            xField: 'name',
									            series: json.series
									        }
			
								    	});
	  		var chartPanelWindow = new Ext.form.FormPanel({
		 		frame:true,
	  			autoHeight:true,
	  			autoWidth:true,
	  			id : 'chartPanelWindow',
	  			labelSeparator:':',
	  			labelAlign:'left',
	  			renderTo:'containerWindow',
	  			bodyPadding:0,
	  			items : [{
			        layout : 'column',
			        items : [{
			                columnWidth : 1, 
							layout : 'form',
							style:"padding-top:0px;padding-left:1px;padding-bottom:1px",
			       			items : detailChartWindow
			        				}]
	                      }]
					 });
		}
	}
	Ext.Ajax.request(requestConfigWindow);
	function generateJSONStoreWindow(url,dateArrWindow,typeWindow){
		var readerRecord = new Array();
		readerRecord.push({'name':'windowName'});
		readerRecord.push({'name':'windowId'});
		for(var i = 0 ; i<dateArrWindow.length; i++){
			readerRecord.push({'name':dateArrWindow[i]});
		}
		var gridStoreWindow = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({url : url+"?type="+typeWindow}),
			reader : new Ext.data.JsonReader({
				totalProperty : 'totalProperty',
				root : 'root'
			},readerRecord)
		});
		return gridStoreWindow;
		
	}
	
	function generateJSONStoreForWindow(url,dateArrWindow,type,windowId,from,to){
		var readerRecord = new Array();
		readerRecord.push({'name':'itemName'});
		readerRecord.push({'name':'windowId'});
		readerRecord.push({'name':'itemGrade'});
		readerRecord.push({'name':'parentItemName'});
		for(var i = 0 ; i<dateArrWindow.length; i++){
			readerRecord.push({'name':dateArrWindow[i]});
		}
		var gridStoreWindow = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({url : url+"?type="+type+"&windowId="+windowId+"&from="+from+"&to="+to}),
			reader : new Ext.data.JsonReader({
				totalProperty : 'totalProperty',
				root : 'root'
			},readerRecord)
		});
		return gridStoreWindow;
	}
	
	function generateGridColumnStoreWindow(dateArrWindow,header,dataIndex){
			var colModel = new Array();
  			colModel[0] = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
  			colModel[1] = new Ext.grid.RowNumberer();
  			colModel[2] = {header:header,dataIndex:dataIndex};
  			if(dataIndex == "itemName"){
  				colModel[3] = {header:'�ȼ�',dataIndex:'itemGrade'};
  				colModel[4] = {header:'��ָ��',dataIndex:'parentItemName'};
  				for(var i = 0; i<dateArrWindow.length; i++){
  					colModel[i+5] = {header:dateArrWindow[i].split("&")[2],dataIndex:dateArrWindow[i]};
	  			}
				var gridCmWindow = new Ext.grid.ColumnModel(colModel);
				return gridCmWindow;
  			}
  			for(var i = 0; i<dateArrWindow.length; i++){
  				colModel[i+3] = {header:dateArrWindow[i].split("&")[2],dataIndex:dateArrWindow[i]};
  			}
			var gridCmWindow = new Ext.grid.ColumnModel(colModel);
			return gridCmWindow;
	}
	
	function radiochangeWindow(smt,data){
  		typeWindow = data.inputValue;
  		queryWindow();
	}
	
	function generateChartStoreWindow(json){
		seriesWindow = new Array();
		dateArrWindow = eval(json.dateList);
		root = eval(json.root);
		var fields = new Array();
		fields.push('name');
		for(var i = 0; i<root.length; i++){
			fields.push(root[i].windowName);
			seriesWindow.push(root[i].windowName);
		}
		var chartStoreWindow = new Ext.data.JsonStore({
			fields : fields,
			data : eval(json.chart)
		});
		return chartStoreWindow;
	}
	
	function generateChartStoreForItemWindow(json){
		var chartData = eval(json.chart);
		for(var i=0; i<chartData.length; i++){
			chartData[i].name = chartData[i].name.split("&")[2];
		}
		var chartStoreWindow = new Ext.data.JsonStore({
			fields : json.fields,
			data : chartData
		});
		return chartStoreWindow;
	}
	
	
	function deleteParamWindow(sm_,rowIndex,record){
		var windowName = record.json.windowName;
		if(arrayContains(removeListWindow,windowName) == -1){
			removeListWindow.push(windowName);
		}
		reGenerateChartWindow();
		
	}
	
	function addParamWindow(sm_,rowIndex,record){
		var windowName = record.json.windowName;
		var Index = arrayContains(removeListWindow,windowName);
		if(Index != -1){
			removeListWindow.splice(Index,1);
		}
		reGenerateChartWindow();
	}
	
	function reGenerateChartWindow(){
		var from = Ext.fly("fromWindow").getValue();
		var to = Ext.fly("toWindow").getValue();
		if(from == "" ^ to == ""){
			Ext.MessageBox.alert("��ʾ��Ϣ","����д��ʼ-��������");
			return false;
		}
		var requestConfigWindow = {
			url : "gyx/windowResult/availbleWindowAction.action",
			method : 'post',
			params :{'from':from,'to':to,'type':typeWindow,'removeList':removeListWindow},
			callback :function(options,success,response){
				if(response.responseText == "Date error"){
  					Ext.MessageBox.alert("��ʾ��Ϣ","����д��ʼ�ͽ�������");
  					return false;
  				}
  				if(response.responseTest == "inner error"){
  					Ext.MessageBox.alert("��ʾ��Ϣ","����������Ժ�����");
  					return false;
  				}
  				var json = Ext.util.JSON.decode(response.responseText);
  				Ext.getCmp("chartPanelWindow").destroy();
		  		chartStoreWindow = generateChartStoreWindow(json);
		  		detailChartWindow = new Ext.Panel({
			        iconCls:'chart',
			        title: '������ϸ��������ͼ',
			        id : 'detailPanelWindow',
			        frame:true,
			        width:1500,
			        height:300,
			        layout:'fit',
			        items: {
			            xtype: 'linechart',
			            store: chartStoreWindow,
			            url: 'ext-3.0.0/resources/charts.swf',
			            xField: 'name',
			            series: json.series
			        }

		    	});
		  		var chartPanelWindow = new Ext.form.FormPanel({
			 		frame:true,
		  			autoHeight:true,
		  			autoWidth:true,
		  			id : 'chartPanelWindow',
		  			labelSeparator:':',
		  			labelAlign:'left',
		  			renderTo:'containerWindow',
		  			bodyPadding:5,
		  			items : [{
				        layout : 'column',
				        items : [{
				                columnWidth : 1, 
								layout : 'form',
								style:"padding-top:0px;padding-left:1px;padding-bottom:1px",
				       			items : detailChartWindow
				        				}]
		                      }]
						 });
		  		
  			}
		}
		Ext.Ajax.request(requestConfigWindow);
	}
	
	function showDetailWindow(a,b,c){
		var windowId = gridStoreWindow.getAt(b).get('windowId');
		curWindowId=windowId;
		var from = Ext.fly("fromWindow").getValue();
		var to = Ext.fly("toWindow").getValue();
		if(from == "" ^ to == ""){
			Ext.MessageBox.alert("��ʾ��Ϣ","����д��ʼ-��������");
			return false;
		}
		var requestConfigWindow = {
			url : "gyx/windowResult/getWindowResult.action",
			params : {'from':from,'to':to,'type':typeWindow,'windowId':windowId,'displayList':displayListWindow,'windowIdArray':curWindowIdArrForSeries},
			callback :function(options,success,response){
					var json = Ext.util.JSON.decode(response.responseText);
					var dateList = json.dateList;
					gridStoreDetailWindow = generateJSONStoreForWindow("gyx/windowResult/getWindowResult.action",dateList,typeWindow,windowId,from,to);
					gridCmDetailWindow = generateGridColumnStoreWindow(dateList,"ָ��",'itemName');
					Ext.getCmp("chartPanelWindow").destroy();
					var sm0 = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
	  				sm0.on('rowselect',function(sm_,rowIndex,record){addParamDetailWindow(sm_,rowIndex,record)});
	  				sm0.on('rowdeselect',function(sm_,rowIndex,record){deleteParamDetailWindow(sm_,rowIndex,record)});
					cumgridDetailWindow = new Ext.grid.GridPanel({
						title:'����ָ��÷���ϸ',
			  			id:'cumgridDetailWindow',
			  			sm:sm0,
			  			store:gridStoreDetailWindow,
			  			stripeRows:true,
			  			viewConfig:{
			  				forceFit:true,
			  				sortAscText : '����',
			  				sortDescText : '����'
			  			},
			  			height:298,
			  			autowidth:500,
			  			colModel:gridCmDetailWindow,
			  			bbar : new Ext.PagingToolbar({
			                    	  pageSize : pSizeDetailWindow,
			                    	  store : gridStoreDetailWindow,
			                    	  displayInfo : true,
			                    	  displayMsg : "",
			                    	  emptyMsg : 'û�м�¼'
			                      }),
			            remoteSort: true
			  		});
					gridStoreDetailWindow.load({params:{start:0,limit:pSizeDetailWindow}});
					chartStoreDetailWindow = generateChartStoreForItemWindow(json);
					detailChartWindow = new Ext.Panel({
				        iconCls:'chart',
				        title: '������ϸ��������ͼ',
				        id : 'detailPanelWindow',
				        frame:true,
				        width:700,
				        height:298,
				        layout:'fit',
				        items: {
				            xtype: 'linechart',
				            id : 'detailChartWindow',
				            store: chartStoreDetailWindow,
				            url: 'ext-3.0.0/resources/charts.swf',
				            xField: 'name',
				            series: json.series
				        }
	
			    	});
		  		var chartPanelWindow = new Ext.form.FormPanel({
			 		frame:true,
		  			autoHeight:true,
		  			autoWidth:true,
		  			id : 'chartPanelWindow',
		  			labelSeparator:':',
		  			labelAlign:'left',
		  			renderTo:'containerWindow',
		  			bodyPadding:5,
		  			items : [{
				        layout : 'column',
				        items : [{
				                columnWidth : .5, 
								layout : 'form',
				       			items : cumgridDetailWindow
				        				},{
				                columnWidth : .5, 
								layout : 'form',
								style:"padding-top:1px;padding-left:1px;padding-bottom:1px",
				       			items : detailChartWindow
				        				}]
		                      }]
						 });
		  		Ext.getCmp("detailChartWindow").bindStore(chartStoreDetailWindow);
			}
		}
		Ext.Ajax.request(requestConfigWindow);
	}
	
	function deleteParamDetailWindow(sm_,rowIndex,record){
		var itemName = record.json.itemName;
		var Index = arrayContains(displayListWindow,itemName);
		if(Index != -1){
			displayListWindow.splice(Index,1);
		}
		
		reGenerateChartDetailWindow();
		
	}
	
	function addParamDetailWindow(sm_,rowIndex,record){
		var itemName = record.json.itemName;
		var Index = arrayContains(displayListWindow,itemName);
		var Index1 = arrayContains(curWindowIdArr,curWindowId);
		if(Index == -1){
			displayListWindow.push(itemName);
		}
		if(Index1 == -1){
			curWindowIdArr.push(curWindowId);
		}
		reGenerateChartDetailWindow();
	}
	
	function reGenerateChartDetailWindow(){
		var from = Ext.fly("fromWindow").getValue();
		var to = Ext.fly("toWindow").getValue();
		if(from == "" ^ to == ""){
			Ext.MessageBox.alert("��ʾ��Ϣ","����д��ʼ-��������");
			return false;
		}
		var requestConfigWindow = {
			url : "gyx/windowResult/getWindowResult.action",
			method : 'post',
			params :{'from':from,'to':to,'type':typeWindow,'displayList':displayListWindow,'windowIdArray':curWindowIdArr},
			callback :function(options,success,response){
				var json = Ext.util.JSON.decode(response.responseText);
				var dateList = json.dateList;
				console.log(json);
				chartStoreDetailWindow = generateChartStoreForItemWindow(json);
				console.log("commmmm");
				console.log(chartStoreDetailWindow);
				Ext.getCmp("detailChartWindow").bindStore(chartStoreDetailWindow);
				
			}
		}
		Ext.Ajax.request(requestConfigWindow);
	}
	
	
	/**
  	 * ����������Ƿ����ĳ�ַ���
  	 * @param {Object} array������
  	 * @param {Object} string���ַ���
  	 * @return {TypeName} true��������false��������
  	*/
  	function arrayContains(array,string){
  		for(var i = 0; i<array.length; i++){
  			if(array[i] == string){
  				return i;
  			}
  		}
  		return -1;
  	}
});