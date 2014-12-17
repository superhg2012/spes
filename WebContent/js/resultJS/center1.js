	if(Ext.isChrome===true){       
	    var chromeDatePickerCSS = ".x-date-picker {border-color: #1b376c;background-color:#fff;position: relative;width: 185px;}";
	    Ext.util.CSS.createStyleSheet(chromeDatePickerCSS,'chromeDatePickerStyle');
	}

  	Ext.onReady(function() {
  		//=========================================================
  		var gridStore;//表格store
		var gridCm;//表格columnModel
		var pSize = 10;
		var dateArr;//日期数组，用于构造columnModel等
		var cumgrid;//表格
		var type = 'month';//month, quarter, year
		var chartStore;//折线图store
		var storeChartAll;//柱状图store
		var series;//折线图series
		var totalChart;
		var detailChart;
		var removeList = new Array();
  		var centerForm = createFormPanel();
  		Ext.getCmp('centerForm').render('bodysGyx');
  		Ext.get('rad1').on('click',modeMonth);
  		Ext.get('rad2').on('click',modeQuarter);
  		Ext.get('rad3').on('click',modeYear);
  		
  		var requestConfig = {
		url : "gyx/centerResult/centerResultAction.action",
		callback :function(options,success,response){
			var json = Ext.util.JSON.decode(response.responseText);
			dateArr = eval(json.dateList);
			generateJSONStore('gyx/centerResult/centerResultAction', dateArr,'month');
			generateGridColumnStore(dateArr);
			var sm0 = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
	  		sm0.on('rowselect',function(sm_,rowIndex,record){deleteParam(sm_,rowIndex,record);});
	  		sm0.on('rowdeselect',function(sm_,rowIndex,record){addParam(sm_,rowIndex,record);});
	  		cumgrid = new Ext.grid.GridPanel({
	  			id:'cumgrid',
	  			sm:sm0,
	  			renderTo : 'tableGyx',
	  			store : gridStore,
	  			stripeRows:true,
	  			viewConfig:{
	  				forceFit:true,
	  				sortAscText : '升序',
	  				sortDescText : '降序'
	  			},
	  			height:299,
	  			autowidth:500,
	  			colModel:gridCm,
	  			bbar : new Ext.PagingToolbar({
	                    	  pageSize : pSize,
	                    	  store : gridStore,
	                    	  displayInfo : true,
	                    	  displayMsg : "",
	                    	  emptyMsg : '没有记录'
	                      }),
	            remoteSort: true
	  		});
	  		
	  		gridStore.load({params:{start: 0,limit:pSize}});
	  		generateChartStore(json);
	  		generateChartStoreAll(json);
	  		totalChart = new Ext.Panel({
					        iconCls:'chart',
					        title: '中心综合评分趋势图',
					        frame:true,
					        width:500,
					        height:299,
					        layout:'fit',
					        items: {
					            xtype: 'columnchart',
					            store: storeChartAll,
					            url: 'ext-3.0.0/resources/charts.swf',
					            xField: 'name',
					            yField: 'score',
					            yAxis: new Ext.chart.NumericAxis({
					                displayName: '得分',
					                labelRenderer : Ext.util.Format.numberRenderer('0,0')
					            }),
					            tipRenderer : function(chart, record){
					                return record.data.name + ' 该中心得分为 ' + record.data.score + "分";
					            }
					        }
				    	});
	  		detailChart = new Ext.Panel({
					        iconCls:'chart',
					        title: '中心一级评分趋势图',
					        id : 'detailPanel',
					        frame:true,
					        width:500,
		        			height:299,
					        layout:'fit',
					        items: {
					            xtype: 'linechart',
					            store: chartStore,
					            url: 'ext-3.0.0/resources/charts.swf',
					            xField: 'name',
					            series: json.series
					        }

				    	});
	  		var chartPanel = new Ext.form.FormPanel({
		 		frame:true,
	  			autoHeight:true,
	  			autoWidth:true,
	  			id : 'chartPanel',
	  			labelSeparator:':',
	  			labelAlign:'left',
	  			bodyPadding:5,
	  			items : [{
			        layout : 'column',
			        items : [{
				                columnWidth : .5, 
								layout : 'form',
				       			items : totalChart
				        				}, {
					           					columnWidth : .5,                                        
					           					layout : 'form',
					           					items : detailChart
				                           }]
	                      }]
					 });
	  		Ext.getCmp('chartPanel').render('container');
		}
	};
  		
	Ext.Ajax.request(requestConfig);
  		
  	function generateJSONStore(url,dateArr,type){
		var readerRecord = new Array();
		readerRecord.push({'name':'itemName'});
		readerRecord.push({'name':'itemGrade'});
		readerRecord.push({'name':'parentItemName'});
		for(var i = 0 ; i<dateArr.length; i++) {
			readerRecord.push({'name':dateArr[i]});
		}
		gridStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({url : url+"?type="+type}),
			reader : new Ext.data.JsonReader({
				totalProperty : 'totalProperty',
				root : 'root'
			},readerRecord)
		});
	}
	
	function generateGridColumnStore(dateArr){
			var colModel = new Array();
  			colModel[0] = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
  			colModel[1] = new Ext.grid.RowNumberer();
  			colModel[2] = {header:'指标名称',dataIndex:'itemName'};
  			colModel[3] = {header:'指标等级',dataIndex:'itemGrade'};
  			colModel[4] = {header:'父指标',dataIndex:'parentItemName'};
  			for(var i = 0; i< dateArr.length; i++){
  				colModel[i+5] = {header:dateArr[i],dataIndex:dateArr[i]};
  			}
			gridCm = new Ext.grid.ColumnModel(colModel);
	}
	
	function generateChartStore(json) {
		data = eval(json.chartDetail);
		fields = eval(json.detailChartField);
		//console.log(json.chart);
		chartStore = new Ext.data.JsonStore({
			fields : fields,
			data : data
		});
	}
	
	function generateChartStoreAll(json) {
		var fields=  new Array();
		fields.push("name");
		fields.push("score");
		storeChartAll = new Ext.data.JsonStore({
	        fields:fields,
	        data: json.chartAll
   		});
	}
	
	function query() {
		//alert(removeStr);
		var from = Ext.fly("from").getValue();
		var to = Ext.fly("to").getValue();
		if(from == "" ^ to == ""){
			Ext.MessageBox.alert("提示信息","请填写起始-结束日期");
			return false;
		}
		//alert(from +"  "+to+"  "+type);
		var requestConfig = {
			url : "gyx/centerResult/centerResultAction.action",
			method : 'post',
			params :{'from':from,'to':to,'type':type,'removeList':removeList},
			callback :function(options,success,response) {
				if(response.responseText == "Date error") {
  					Ext.MessageBox.alert("提示信息","请填写起始和结束日期");
  					return false;
  				}
  				if(response.responseTest == "inner error"){
  					Ext.MessageBox.alert("提示信息","处理错误，请稍后再试");
  					return false;
  				}
  				var json = Ext.util.JSON.decode(response.responseText);
  				dateArr = eval(json.dateList);
  				generateJSONStore("gyx/centerResult/centerResultAction.action",dateArr,type);
  				generateGridColumnStore(dateArr);
  				Ext.getCmp("cumgrid").destroy();
				var sm0 = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
	  			sm0.on('rowselect',function(sm_,rowIndex,record){deleteParam(sm_,rowIndex,record);});
	  			sm0.on('rowdeselect',function(sm_,rowIndex,record){addParam(sm_,rowIndex,record);});
	  			cumgrid = new Ext.grid.GridPanel({
		  			renderTo:'tableGyx',
		  			id:'cumgrid',
		  			sm:sm0,
		  			store:gridStore,
		  			stripeRows:true,
		  			viewConfig:{
		  				forceFit:true,
		  				sortAscText : '升序',
		  				sortDescText : '降序'
		  			},
		  			height:300,
		  			autowidth:500,
		  			colModel:gridCm,
		  			bbar : new Ext.PagingToolbar({
		                    	  pageSize : pSize,
		                    	  store : gridStore,
		                    	  displayInfo : true,
		                    	  displayMsg : "",
		                    	  emptyMsg : '没有记录'
		                      }),
		            remoteSort: true
		  		});
		  		gridStore.load({params:{start: 0,limit:pSize}});
		  		Ext.getCmp("chartPanel").destroy();
		  		generateChartStore(json);
		  		generateChartStoreAll(json);
		  		totalChart = new Ext.Panel({
							        iconCls:'chart',
							        title: '中心综合评分趋势图',
							        frame:true,
							        
							        width:500,
							        height:299,
							        layout:'fit',
							
							        items: {
							            xtype: 'columnchart',
							            store: storeChartAll,
							            url: 'ext-3.0.0/resources/charts.swf',
							            xField: 'name',
							            yField: 'score',
							            yAxis: new Ext.chart.NumericAxis({
							                displayName: '得分',
							                labelRenderer : Ext.util.Format.numberRenderer('0,0')
							            }),
							            tipRenderer : function(chart, record){
							                return record.data.name + ' 该中心得分为 ' + record.data.score + "分";
							            }
							        }
						    	});
		  		detailChart = new Ext.Panel({
			        iconCls:'chart',
			        title: '中心明细评分趋势图',
			        id : 'detailPanel',
			        frame:true,
			        width:500,
			        height:299,
			        layout:'fit',
			        items: {
			            xtype: 'linechart',
			            store: chartStore,
			            url: 'ext-3.0.0/resources/charts.swf',
			            xField: 'name',
			            series: json.series
			        }

		    	});
		  		var chartPanel = new Ext.form.FormPanel({
			 		frame:true,
		  			autoHeight:true,
		  			autoWidth:true,
		  			id : 'chartPanel',
		  			labelSeparator:':',
		  			labelAlign:'left',
		  			renderTo:'container',
		  			bodyPadding:5,
		  			items : [{
				        layout : 'column',
				        items : [{
				                columnWidth : .5, 
								layout : 'form',
				       			items : totalChart
				        				},{
				                columnWidth : .5, 
								layout : 'form',
				       			items : detailChart
				        				}]
		                      }]
						 });
			}
		};

		Ext.Ajax.request(requestConfig);
	}
	
	function modeMonth(){
		type = "month";
  		query();
	}

	function modeQuarter(){
		type = "quarter";
  		query();
	}

	function modeYear(){
		type = "year";
  		query();
	}

	function deleteParam(sm_,rowIndex,record){
		var itemName = record.json.itemName;
		if(arrayContains(removeList,itemName) == -1){
			removeList.push(itemName);
		}
		reGenerateChart();
	}
	
	function addParam(sm_,rowIndex,record){
		var itemName = record.json.itemName;
		var Index = arrayContains(removeList,itemName);
		if(Index != -1){
			removeList.splice(Index,1);
			//console.log(removeList);
		}
		reGenerateChart();
	}
	
	function reGenerateChart(){
		var from = Ext.fly("from").getValue();
		var to = Ext.fly("to").getValue();
		if(from == "" ^ to == ""){
			Ext.MessageBox.alert("提示信息","请填写起始-结束日期");
			return false;
		}
		var requestConfig = {
			url : "gyx/centerResult/centerResultAction.action",
			method : 'post',
			params :{'from':from,'to':to,'type':type,'removeList':removeList,'displayLev2':true},
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
  				Ext.getCmp("chartPanel").destroy();
		  		generateChartStore(json);
		  		totalChart = new Ext.Panel({
							        iconCls:'chart',
							        title: '中心综合评分趋势图',
							        frame:true,
							        width:500,
							        height:299,
							        layout:'fit',
							        items: {
							            xtype: 'columnchart',
							            store: storeChartAll,
							            url: 'ext-3.0.0/resources/charts.swf',
							            xField: 'name',
							            yField: 'score',
							            yAxis: new Ext.chart.NumericAxis({
							                displayName: '得分',
							                labelRenderer : Ext.util.Format.numberRenderer('0,0')
							            }),
							            tipRenderer : function(chart, record){
							                return record.data.name + ' 该中心得分为 ' + record.data.score + "分";
							            }
							        }
						    	});
		  		detailChart = new Ext.Panel({
			        iconCls:'chart',
			        title: '中心一级指标评分趋势图',
			        id : 'detailPanel',
			        frame:true,
			        width:500,
			        height:299,
			        layout:'fit',
			        items: {
			            xtype: 'linechart',
			            store: chartStore,
			            url: 'ext-3.0.0/resources/charts.swf',
			            xField: 'name',
			            series: json.series
			        }

		    	});
		  		var chartPanel = new Ext.form.FormPanel({
			 		frame:true,
		  			autoHeight:true,
		  			autoWidth:true,
		  			id : 'chartPanel',
		  			labelSeparator:':',
		  			labelAlign:'left',
		  			renderTo:'container',
		  			bodyPadding:5,
		  			items : [{
				        layout : 'column',
				        items : [{
				                columnWidth : .5, 
								layout : 'form',
				       			items : totalChart
				        				},{
				                columnWidth : .5, 
								layout : 'form',
				       			items : detailChart
				        				}]
		                      }]
						 });
  			}
		};
		Ext.Ajax.request(requestConfig);
	}
	
	function createFormPanel(){
		var centerForm = new Ext.form.FormPanel({
  			title:'中心绩效考核评价结果展示',
  			frame:true,
  			labelAlign:'left',
  			id : 'centerForm',
  			bodyPadding:5,
  			layout : 'table',
  			layoutConfig: {columns:12},
  			defaults: { width:120},
  			items : [
  				{xtype : 'label',text : '时间范围  ：'},
  				{    
   			 		xtype : 'datefield',
                    fieldLabel : '时间从',
        			name : 'from',
		            id : 'from',
		            invalidText : '时间必需符合格式:2000-01-01',
		            format : 'Y-m-d'
                },
                {xtype : 'label',text : '~'	},
  				{
	   				xtype : 'datefield',
	              	fieldLabel : '到',
					name : 'to',
	           	 	id : 'to',
	           	 	invalidText : '时间必需符合格式:2000-01-01',
	            	format : 'Y-m-d',
	            },{colspan : 1, text : ' ',width:50},
            		   new Ext.form.Radio({
                		   name : 'mjy',
                		   id : 'rad1',
                		   inputValue : 'month',
                		   boxLabel : '月结果展示'
                	   }),new Ext.form.Radio({
                		   name : 'mjy',
                		   id : 'rad2',
                		   inputValue : 'quarter',
                		   boxLabel : '季度结果展示'
                	   }),new Ext.form.Radio({
                		   name : 'mjy',
                		   id : 'rad3',
                		   inputValue : 'year',
                		   boxLabel : '年结果展示'
                	   }),{colspan : 1, text : ' ',width : 50},
	            			new Ext.Button({
	            				text : '查询',
	            				id : 'queryButton',
	            				minWidth : 800,
	            				listeners : {
	            					"click" : query
	            				}
	            			}),{colspan : 1, text : ' ',width : 50},
	            			new Ext.Button({
	            				text : '导出详细信息',
	            				id : 'exportExcel',
	            				minWidth : 800,
	            				listeners : {
	            					"click" : exportExcel
	            				}
	            			})
  				]
  		});
		return centerForm;
	}
	
	function exportExcel(){
		var from = Ext.fly("from").getValue();
		var to = Ext.fly("to").getValue();
		if(from == "" ^ to == ""){
			Ext.MessageBox.alert("提示信息","请填写起始-结束日期");
			return false;
		}
		//alert(from +"  "+to+"  "+type);
		var requestConfig = {
			url : "gyx/centerResult/centerExport.action",
			method : 'post',
			params :{'from':from,'to':to,'type':type},
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
  			}
	   };
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