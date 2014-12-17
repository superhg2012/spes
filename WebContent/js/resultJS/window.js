if(Ext.isChrome===true){       
        var chromeDatePickerCSS = ".x-date-picker {border-color: #1b376c;background-color:#fff;position: relative;width: 185px;}";
        Ext.util.CSS.createStyleSheet(chromeDatePickerCSS,'chromeDatePickerStyle');
}

Ext.onReady(function(){
	var gridStoreWindow;//表格store
	var gridCmWindow;//表格columnModel
	var pSizeWindow = 10;
	var dateArrWindow;//日期数组，用于构造columnModel等
	var cumgridWindow;//表格
	var typeWindow = 'month';//month, quarter, year
	var chartStoreWindow;//折线图store
	var seriesWindow;//折线图series
	var removeListWindow = new Array();
	var displayListWindow = new Array();
	var removeStrWindow = removeListWindow.join(",");
	var gridStoreDetailWindow;//明细表格的store
	var gridCmDetailWindow;//明细表格的columnmodel
	var pSizeDetailWindow = 9;//明细表格的每页行数
	var cumgridDetailWindow;
	var chartStoreDetailWindow;
	var seriesDetailWindow;
	var curWindowId;
	var curWindowIdArr = new Array();
	var curWindowIdArrForSeries = new Array();
	var windowForm = new Ext.form.FormPanel({
  			title:'窗口绩效考核评价结果展示',
  			frame:true,
  			renderTo : 'bodysGyxWindow',
  			labelAlign:'left',
  			id : 'windowForm',
  			bodyPadding:5,
  			layout : 'table',
  			layoutConfig: {columns:10},
  			defaults: { width:150},
  			items : [
  				{xtype : 'label',text : '时间范围  ：'},
  				{    
   			 		xtype : 'datefield',
                    fieldLabel : '时间从',
        			name : 'fromWindow',
		            id : 'fromWindow',
		            invalidText : '时间必需符合格式:2000-01-01',
		            format : 'Y-m-d',
                },
                {xtype : 'label',text : '~'	},
  				{
	   				xtype : 'datefield',
	              	fieldLabel : '到',
					name : 'toWindow',
	           	 	id : 'toWindow',
	           	 	invalidText : '时间必需符合格式:2000-01-01',
	            	format : 'Y-m-d',
	            },{colspan : 1, text : ' ',width:50},
            		   new Ext.form.Radio({
                		   name : 'mjy',
                		   id : 'rad1Window',
                		   inputValue : 'month',
                		   boxLabel : '月结果展示'
                	   }),new Ext.form.Radio({
                		   name : 'mjy',
                		   id : 'rad2Window',
                		   inputValue : 'quarter',
                		   boxLabel : '季度结果展示'
                	   }),new Ext.form.Radio({
                		   name : 'mjy',
                		   id : 'rad3Window',
                		   inputValue : 'year',
                		   boxLabel : '年结果展示'
                	   }),{colspan : 1, text : ' ',width : 50},
	            			new Ext.Button({
	            				text : '查询',
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
			Ext.MessageBox.alert("提示信息","请填写起始-结束日期");
			return false;
		}
		var requestConfigWindow = {
			url : "gyx/windowResult/availbleWindowAction.action",
			method : 'post',
			params :{'from':from,'to':to,'type':typeWindow,'removeList':removeListWindow},
			callback :function(options,success,response){
				if(response.responseText == "Date error"){
  					Ext.MessageBox.alert("提示信息","请填写起始和结束日期");
  					return false;
  				}
  				if(response.responseTest == "inner error"){
  					Ext.MessageBox.alert("提示信息","处理错误，请稍后再试");
  					return false;
  				}
  				var json = Ext.util.JSON.decode(response.responseText);
  				dateArrWindow = eval(json.dateList);
  				gridStoreWindow = generateJSONStoreWindow("gyx/windowResult/availbleWindowAction.action",dateArrWindow,typeWindow);
  				gridCmWindow = generateGridColumnStoreWindow(dateArrWindow,'窗口','windowName');
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
		  				sortAscText : '升序',
		  				sortDescText : '降序'
		  			},
		  			height:298,
		  			autowidth:300,
		  			colModel:gridCmWindow,
		  			bbar : new Ext.PagingToolbar({
		                    	  pageSize : pSizeWindow,
		                    	  store : gridStoreWindow,
		                    	  displayInfo : true,
		                    	  displayMsg : "",
		                    	  emptyMsg : '没有记录'
		                      }),
		            remoteSort: true
		  		});
		  		gridStoreWindow.load({params:{start: 0,limit:pSizeWindow}});
		  		Ext.getCmp("chartPanelWindow").destroy();
		  		chartStoreWindow = generateChartStoreWindow(json);
		  		detailChartWindow = new Ext.Panel({
			        iconCls:'chart',
			        title: '窗口总分趋势图',
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
			gridCmWindow = generateGridColumnStoreWindow(dateArrWindow,'窗口','windowName');
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
	  				sortAscText : '升序',
	  				sortDescText : '降序'
	  			},
	  			height:299,
	  			autowidth:300,
	  			colModel:gridCmWindow,
	  			bbar : new Ext.PagingToolbar({
	                    	  pageSize : pSizeWindow,
	                    	  store : gridStoreWindow,
	                    	  displayInfo : true,
	                    	  displayMsg : "",
	                    	  emptyMsg : '没有记录'
	                      }),
	            remoteSort: true
	  		});
	  		gridStoreWindow.load({params:{start: 0,limit:pSizeWindow}});
	  		chartStoreWindow = generateChartStoreWindow(json);
	  		detailChartWindow = new Ext.Panel({
									        iconCls:'chart',
									        title: '窗口总分趋势图',
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
  				colModel[3] = {header:'等级',dataIndex:'itemGrade'};
  				colModel[4] = {header:'父指标',dataIndex:'parentItemName'};
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
			Ext.MessageBox.alert("提示信息","请填写起始-结束日期");
			return false;
		}
		var requestConfigWindow = {
			url : "gyx/windowResult/availbleWindowAction.action",
			method : 'post',
			params :{'from':from,'to':to,'type':typeWindow,'removeList':removeListWindow},
			callback :function(options,success,response){
				if(response.responseText == "Date error"){
  					Ext.MessageBox.alert("提示信息","请填写起始和结束日期");
  					return false;
  				}
  				if(response.responseTest == "inner error"){
  					Ext.MessageBox.alert("提示信息","处理错误，请稍后再试");
  					return false;
  				}
  				var json = Ext.util.JSON.decode(response.responseText);
  				Ext.getCmp("chartPanelWindow").destroy();
		  		chartStoreWindow = generateChartStoreWindow(json);
		  		detailChartWindow = new Ext.Panel({
			        iconCls:'chart',
			        title: '窗口明细评分趋势图',
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
			Ext.MessageBox.alert("提示信息","请填写起始-结束日期");
			return false;
		}
		var requestConfigWindow = {
			url : "gyx/windowResult/getWindowResult.action",
			params : {'from':from,'to':to,'type':typeWindow,'windowId':windowId,'displayList':displayListWindow,'windowIdArray':curWindowIdArrForSeries},
			callback :function(options,success,response){
					var json = Ext.util.JSON.decode(response.responseText);
					var dateList = json.dateList;
					gridStoreDetailWindow = generateJSONStoreForWindow("gyx/windowResult/getWindowResult.action",dateList,typeWindow,windowId,from,to);
					gridCmDetailWindow = generateGridColumnStoreWindow(dateList,"指标",'itemName');
					Ext.getCmp("chartPanelWindow").destroy();
					var sm0 = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
	  				sm0.on('rowselect',function(sm_,rowIndex,record){addParamDetailWindow(sm_,rowIndex,record)});
	  				sm0.on('rowdeselect',function(sm_,rowIndex,record){deleteParamDetailWindow(sm_,rowIndex,record)});
					cumgridDetailWindow = new Ext.grid.GridPanel({
						title:'窗口指标得分明细',
			  			id:'cumgridDetailWindow',
			  			sm:sm0,
			  			store:gridStoreDetailWindow,
			  			stripeRows:true,
			  			viewConfig:{
			  				forceFit:true,
			  				sortAscText : '升序',
			  				sortDescText : '降序'
			  			},
			  			height:298,
			  			autowidth:500,
			  			colModel:gridCmDetailWindow,
			  			bbar : new Ext.PagingToolbar({
			                    	  pageSize : pSizeDetailWindow,
			                    	  store : gridStoreDetailWindow,
			                    	  displayInfo : true,
			                    	  displayMsg : "",
			                    	  emptyMsg : '没有记录'
			                      }),
			            remoteSort: true
			  		});
					gridStoreDetailWindow.load({params:{start:0,limit:pSizeDetailWindow}});
					chartStoreDetailWindow = generateChartStoreForItemWindow(json);
					detailChartWindow = new Ext.Panel({
				        iconCls:'chart',
				        title: '窗口明细评分趋势图',
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
			Ext.MessageBox.alert("提示信息","请填写起始-结束日期");
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
  	 * 检查数组中是否包含某字符串
  	 * @param {Object} array：数组
  	 * @param {Object} string：字符串
  	 * @return {TypeName} true：包含，false：不包含
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