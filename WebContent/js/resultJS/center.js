	if(Ext.isChrome===true){       
	        var chromeDatePickerCSS = ".x-date-picker {border-color: #1b376c;background-color:#fff;position: relative;width: 185px;}";
	        Ext.util.CSS.createStyleSheet(chromeDatePickerCSS,'chromeDatePickerStyle');
	}

  	Ext.onReady(function(){
  		var pSize = 3;
  		var cm;//表格的columnModel
  		var cumgrid;//表格
  		var dateArray;
  		var dataArray;
  		var totalChart;//整体趋势图
  		var detailChart;//明细趋势图
  		var deleteItems = new Array();//不显示的指标名数组
  		Ext.QuickTips.init();
  		
  		var loginForm = new Ext.form.FormPanel({
  			title:'中心绩效考核评价结果展示',
  			frame:true,
  			autoHeight:true,
  			autoWidth:true,
  			labelSeparator:':',
  			labelAlign:'left',
  			renderTo:'bodysGyx',
  			bodyPadding:5,
  			labelWidth:60,
  			items : [{
		        layout : 'column',
		        items : [{
		                columnWidth : .18, 
						layout : 'form',
		       			items : [{                                      
			       			 		xtype : 'datefield',
			                        fieldLabel : '时间从',
			            			name : 'from',
						            id : 'from',
						            invalidText : '时间必需符合格式:2000-01-01',
						            format : 'Y-m-d',
						            anchor : '95%'
		                        }]
		        				}, {
		           				columnWidth : .18,                                        
		           				layout : 'form',
		           				items : [{
		               				 xtype : 'datefield',
		                          	fieldLabel : '到',
		            				name : 'to',
					           	 	id : 'to',
					           	 	invalidText : '时间必需符合格式:2000-01-01',
					            	format : 'Y-m-d',
					            	anchor : '95%'
		                                 }]
		                           },{ columnWidth : .24,
		                        	   items :[{
		                        		   xtype : 'radiogroup',
			                        	   items : [new Ext.form.Radio({
			                        		   name : 'mjy',
			                        		   inputValue : 'm',
			                        		   boxLabel : '月结果展示'
			                        	   }),new Ext.form.Radio({
			                        		   name : 'mjy',
			                        		   inputValue : 'j',
			                        		   boxLabel : '季度结果展示'
			                        	   }),new Ext.form.Radio({
			                        		   name : 'mjy',
			                        		   inputValue : 'y',
			                        		   boxLabel : '年结果展示'
			                        	   })],
			                        	  listeners : {
												'change' : radiochange
											}
  
		                        	   }]
		                        	   
		                           }]
                      }],
                      
  		});
  		function radiochange(smt,data){
  			var from = Ext.fly("from").getValue();
  			var to = Ext.fly("to").getValue();
  			if(from == "" ^ to == ""){
  				Ext.MessageBox.alert("提示信息","请填写起始和结束日期");
  				return false;
  			}
  			if(data.inputValue == 'm'){
  				var config = {
  					url : "gyx/centerResult/centerResultAction",
  					params : {'from' : from,'to' : to , 'type' : 'month'},
  					method : 'POST',
  					callback : function(options,success,response){
  						if(response.responseText == "Date error"){
  							Ext.MessageBox.alert("提示信息","请填写起始和结束日期");
  							return false;
  						}
  						if(response.responseTest == "inner error"){
  							Ext.MessageBox.alert("提示信息","处理错误，请稍后再试");
  							return false;
  						}
  						var json = Ext.util.JSON.decode(response.responseText);
  						var nstore = generateStore(json);
  						//cumgrid.reconfigure(nstore,cm);
  						Ext.getCmp('cumgrid').destroy();
  						var sm0 = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
  						sm0.on('rowselect',function(sm_,rowIndex,record){deleteParam(sm_,rowIndex,record);});
  						sm0.on('rowdeselect',function(sm_,rowIndex,record){addParam(sm_,rowIndex,record);});
  						cumgrid = new Ext.grid.GridPanel({
				  			renderTo:'table',
				  			sm:sm0,
				  			id:'cumgrid',
				  			store:nstore,
				  			stripeRows:true,
				  			viewConfig:{
				  				forceFit:true,
				  				sortAscText : '升序',
				  				sortDescText : '降序'
				  			},
				  			height:250,
				  			autowidth:500,
				  			colModel:cm,
				  			bbar : new Ext.PagingToolbar({
				                    	  pageSize : pSize,
				                    	  store : nstore,
				                    	  displayInfo : true,
				                    	  displayMsg : "",
				                    	  emptyMsg : '没有记录'
				                      }),
				            remoteSort: true
				  		});	
  						nstore.load({params:{start: 0,limit:pSize}});
  						Ext.getCmp('chartPanel').destroy();
  						var nstoreChartAll = generateChartStore(json);
		  				var nstoreArrayChartDetail = generateChartStoreDetail(json);
		  				//totalChart.bindStore(nstoreChartAll);
		  				totalChart = new Ext.Panel({
						        iconCls:'chart',
						        title: '中心综合评分趋势图',
						        frame:true,
						        id : 'totalPanel',
						        width:500,
						        height:300,
						        layout:'fit',
						        items: {
						            xtype: 'columnchart',
						            store: nstoreChartAll,
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
									        height:300,
									        layout:'fit',
									        items: {
									            xtype: 'linechart',
									            store: nstoreArrayChartDetail[0],
									            url: 'ext-3.0.0/resources/charts.swf',
									            xField: 'name',
									            series: nstoreArrayChartDetail[1]
									        }
			
								    	});
				  		var chartPanel = new Ext.form.FormPanel({
					 		frame:true,
				  			autoHeight:true,
				  			autoWidth:true,
				  			id : 'chartPanel',
				  			labelSeparator:':',
				  			labelAlign:'left',
				  			//applyTo:'container',
				  			renderTo : 'container',
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
  				}
  			}
  			if(data.inputValue == 'j'){
  				var config = {
  					url : "gyx/centerResult/centerResultAction",
  					params : {'from' : from,'to' : to , 'type' : 'quarter'},
  					method : 'POST',
  					callback : function(options,success,response){
  						if(response.responseText == "Date error"){
  							Ext.MessageBox.alert("提示信息","请填写起始和结束日期");
  							return false;
  						}
  						if(response.responseTest == "inner error"){
  							Ext.MessageBox.alert("提示信息","处理错误，请稍后再试");
  							return false;
  						}
  						var json = Ext.util.JSON.decode(response.responseText);
  						var nstore = generateJStore(json);
  						//cumgrid.reconfigure(nstore,cm);
  						Ext.getCmp('cumgrid').destroy();
  						var sm0 = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
  						sm0.on('rowselect',function(sm_,rowIndex,record){deleteParam(sm_,rowIndex,record);});
  						sm0.on('rowdeselect',function(sm_,rowIndex,record){addParam(sm_,rowIndex,record);});
  						cumgrid = new Ext.grid.GridPanel({
				  			renderTo:'table',
				  			sm:sm0,
				  			id:'cumgrid',
				  			store:nstore,
				  			stripeRows:true,
				  			viewConfig:{
				  				forceFit:true,
				  				sortAscText : '升序',
				  				sortDescText : '降序'
				  			},
				  			height:250,
				  			autowidth:500,
				  			colModel:cm,
				  			bbar : new Ext.PagingToolbar({
				                    	  pageSize : pSize,
				                    	  store : nstore,
				                    	  displayInfo : true,
				                    	  displayMsg : "",
				                    	  emptyMsg : '没有记录'
				                      }),
				            remoteSort: true
				  		});	
  						nstore.load({params:{start: 0,limit:pSize}});
  						Ext.getCmp('chartPanel').destroy();
  						var nstoreChartAll = generateJChartStore();
		  				var nstoreArrayChartDetail = generateJChartStoreDetail();
		  				//totalChart.bindStore(nstoreChartAll);
		  				totalChart = new Ext.Panel({
						        iconCls:'chart',
						        title: '中心综合评分趋势图',
						        frame:true,
						        id : 'totalPanel',
						        width:500,
						        height:300,
						        layout:'fit',
						
						        items: {
						            xtype: 'columnchart',
						            store: nstoreChartAll,
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
									        height:300,
									        layout:'fit',
									        items: {
									            xtype: 'linechart',
									            store: nstoreArrayChartDetail[0],
									            url: 'ext-3.0.0/resources/charts.swf',
									            xField: 'name',
									            series: nstoreArrayChartDetail[1]
									        }
			
								    	});
				  		var chartPanel = new Ext.form.FormPanel({
					 		frame:true,
				  			autoHeight:true,
				  			autoWidth:true,
				  			id : 'chartPanel',
				  			labelSeparator:':',
				  			labelAlign:'left',
				  			//applyTo:'container',
				  			renderTo : 'container',
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
  				
  			}
  			if(data.inputValue == 'y'){
  				var config = {
  					url : "gyx/centerResult/centerResultAction",
  					params : {'from' : from,'to' : to , 'type' : 'year'},
  					method : 'POST',
  					callback : function(options,success,response){
  						if(response.responseText == "Date error"){
  							Ext.MessageBox.alert("提示信息","请填写起始和结束日期");
  							return false;
  						}
  						if(response.responseTest == "inner error"){
  							Ext.MessageBox.alert("提示信息","处理错误，请稍后再试");
  							return false;
  						}
  						var json = Ext.util.JSON.decode(response.responseText);
  						var nstore = generateYStore(json);
  						Ext.getCmp('cumgrid').destroy();
  						var sm0 = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
  						sm0.on('rowselect',function(sm_,rowIndex,record){deleteParam(sm_,rowIndex,record);});
  						sm0.on('rowdeselect',function(sm_,rowIndex,record){addParam(sm_,rowIndex,record);});
  						cumgrid = new Ext.grid.GridPanel({
				  			renderTo:'table',
				  			sm:sm0,
				  			id:'cumgrid',
				  			store:nstore,
				  			stripeRows:true,
				  			viewConfig:{
				  				forceFit:true,
				  				sortAscText : '升序',
				  				sortDescText : '降序'
				  			},
				  			height:250,
				  			autowidth:500,
				  			colModel:cm,
				  			bbar : new Ext.PagingToolbar({
				                    	  pageSize : pSize,
				                    	  store : nstore,
				                    	  displayInfo : true,
				                    	  displayMsg : "",
				                    	  emptyMsg : '没有记录'
				                      }),
				            remoteSort: true
				  		});	
  						nstore.load({params:{start: 0,limit:pSize}});
  						Ext.getCmp('chartPanel').destroy();
  						var nstoreChartAll = generateJChartStore();
		  				var nstoreArrayChartDetail = generateJChartStoreDetail();
		  				totalChart = new Ext.Panel({
						        iconCls:'chart',
						        title: '中心综合评分趋势图',
						        frame:true,
						        id : 'totalPanel',
						        width:500,
						        height:300,
						        layout:'fit',
						
						        items: {
						            xtype: 'columnchart',
						            store: nstoreChartAll,
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
									        height:300,
									        layout:'fit',
									        items: {
									            xtype: 'linechart',
									            store: nstoreArrayChartDetail[0],
									            url: 'ext-3.0.0/resources/charts.swf',
									            xField: 'name',
									            series: nstoreArrayChartDetail[1]
									        }
			
								    	});
				  		var chartPanel = new Ext.form.FormPanel({
					 		frame:true,
				  			autoHeight:true,
				  			autoWidth:true,
				  			id : 'chartPanel',
				  			labelSeparator:':',
				  			labelAlign:'left',
				  			//applyTo:'container',
				  			renderTo : 'container',
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
  			}
  			Ext.Ajax.request(config);
  		}
  		/*var cum = new Ext.grid.ColumnModel([
  			new Ext.grid.RowNumberer(),
  			{header:'指标名',dataIndex:'measureName'},
  			{header:'Jul 11',dataIndex:'score1'},
  			{header:'Aug 11',dataIndex:'score2'},
  			{header:'Sep 11',dataIndex:'score3'},
  			{header:'Oct 11',dataIndex:'score4'},
  			{header:'Nov 11',dataIndex:'score5'},
  			{header:'Dec 11',dataIndex:'score6'},
  			{header:'Jan 12',dataIndex:'score7'},
  			{header:'Feb 12',dataIndex:'score8'},
  		]);
  		
  		var cumData = [['指标1','80','70','60','40','90','100','67','66'],
  						['指标2','80','70','60','40','90','100','67','66'],
  						['指标3','80','70','60','40','90','100','67','66'],
  						['指标4','80','70','60','40','90','100','67','66'],
  						['指标5','80','70','60','40','90','100','67','66'],
  						['指标6','80','70','60','40','90','100','67','66'],
  						['指标7','80','70','60','40','90','100','67','66'],
  						['指标8','80','70','60','40','90','100','67','66']];
  		var store = new Ext.data.Store({
  			proxy:new Ext.data.MemoryProxy(cumData),
  			reader:new Ext.data.ArrayReader({},[
  				{name:'measureName'},
  				{name:'score1'},
  				{name:'score2'},
  				{name:'score3'},
  				{name:'score4'},
  				{name:'score5'},
  				{name:'score6'},
  				{name:'score7'},
  				{name:'score8'}
  			])
  		});*/
  		
  		/*var score = new Ext.data.JsonStore({
  			autoLoad : false,
  			url : "gyx/centerResult/centerResultAction",
  			storeId : "centerScore",
  			root : "scores"
  		});
  		score.load();*/
  		
  		var requestConfig = {
  			url : "gyx/centerResult/centerResultAction",
  			callback : function(options,success,response){
  				var arr = eval(response.responseText);
  				for(var i = 0; i<arr.length; i++){
  					
  				}
  				var json = Ext.util.JSON.decode(response.responseText);
//  				console.log(json);
  				var store = generateStore(json);
  				var sm0 = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
  				sm0.on('rowselect',function(sm_,rowIndex,record){deleteParam(sm_,rowIndex,record);});
  				sm0.on('rowdeselect',function(sm_,rowIndex,record){addParam(sm_,rowIndex,record);});
		  		cumgrid = new Ext.grid.GridPanel({
		  			renderTo:'table',
		  			id:'cumgrid',
		  			sm:sm0,
		  			store:store,
		  			stripeRows:true,
		  			viewConfig:{
		  				forceFit:true,
		  				sortAscText : '升序',
		  				sortDescText : '降序'
		  			},
		  			height:250,
		  			autowidth:500,
		  			colModel:cm,
		  			bbar : new Ext.PagingToolbar({
		                    	  pageSize : pSize,
		                    	  store : store,
		                    	  displayInfo : true,
		                    	  displayMsg : "",
		                    	  emptyMsg : '没有记录'
		                      }),
		            remoteSort: true
		  		});
		  		store.load({params:{start: 0,limit:pSize}});
		  		cumgrid.render();
		  		//console.log(cumgrid);
		  		var storeChartAll = generateChartStore(json);
		  		var storeArrayChartDetail = generateChartStoreDetail(json);
		  		totalChart = new Ext.Panel({
						        iconCls:'chart',
						        title: '中心综合评分趋势图',
						        frame:true,
						        
						        width:500,
						        height:300,
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
							        frame:true,
							        width:500,
							        height:300,
							        layout:'fit',
							        items: {
							            xtype: 'linechart',
							            store: storeArrayChartDetail[0],
							            url: 'ext-3.0.0/resources/charts.swf',
							            xField: 'name',
							            series: storeArrayChartDetail[1]
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
				        				}, {
					           					columnWidth : .5,                                        
					           					layout : 'form',
					           					items : detailChart
				                           }]
		                      }]
						 });
  			}
  		};
  		
  		Ext.Ajax.request(requestConfig);
		
  		function generateStore(json){
  			var jsonArray = eval(json);
  			dateArray = new Array();//日期数组
  			var dataArray = new Array();//数据数组,格式：array("['指标1','80','70','60','40','90','100','67','66']","['指标2','80','70','60','40','90','100','67','66']",....)
  			for(var i=0; i<jsonArray.length; i++){
  				//初始化日期数组
  				var year = 1900 + parseInt(jsonArray[i].evaluateDate.year);
  				var month = jsonArray[i].evaluateDate.month+1;
  				var time = year+"-"+month;
  				if(arrayContains(dateArray,time) == -1){
  					dateArray.push(time);
  				}
  			}
  			for(var i=0; i<jsonArray.length; i++){
  				//初始化数据数组
  				var itemname = jsonArray[i].itemName;
  				var timeForindex = (1900 + parseInt(jsonArray[i].evaluateDate.year))+"-"+(jsonArray[i].evaluateDate.month+1);
  				var dateindex = arrayContains(dateArray,timeForindex);
				if(jsonArrayContainsTitle(dataArray,itemname)==-1){
					if(dateindex == 0){
						dataArray.push({'title':jsonArray[i].itemName,"score":"['"+jsonArray[i].itemName+"',"+jsonArray[i].itemScore});//返回数据需要按照时间有序
					}else{
						var scoreString = ",";
						for(var j = 0;j<dateindex;j++){
							scoreString += "'当月未统计',";
						}
						dataArray.push({'title':jsonArray[i].itemName,"score":"['"+jsonArray[i].itemName+"'"+scoreString+"'"+jsonArray[i].itemScore});
					}
				}else{
					var index = jsonArrayContainsTitle(dataArray,itemname);
					var score = dataArray[index].score;
					var itemsInScore = score.split(",");
					var indexForLastDate = itemsInScore.length-2;
					if(indexForLastDate == dateindex-1){
						score += "','";
						score += jsonArray[i].itemScore;
						dataArray[index].score = score;
					}else{
						var space = dateindex-1-indexForLastDate;
						for(var k = 0;k<space; k++){
							score += "','当月未统计";
						}
						score += ",";
						score += jsonArray[i].itemScore;
						dataArray[index].score = score;
					}
				}
  			}
  			for(var i = 0; i<dataArray.length; i++){
  				var scoreLength = dataArray[i].score.split(",").length-1;
  				if(scoreLength < dateArray.length){
  					for(var j = 0; j<dateArray.length-scoreLength; j++){
  						dataArray[i].score += "','当月未统计";
  					}
  				}
  			}
  			for(var i = 0; i<dataArray.length; i++){
  				dataArray[i].score += "']";
  			}
  			//得到columnModel
  			var colModel = new Array();
  			colModel[0] = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
  			colModel[1] = new Ext.grid.RowNumberer();
  			colModel[2] = {header:'指标名',dataIndex:'itemName'};
  			var readConfig = new Array();
  			readConfig.push({'name':'itemName'});
  			for(var i = 0; i<dateArray.length; i++){
  				colModel[i+3] = {header:dateArray[i],dataIndex:dateArray[i]};
  				readConfig.push({'name':dateArray[i]});
  			}
			
			cm = new Ext.grid.ColumnModel(colModel);

			var dataFinal = new Array();
			for(var i =0; i<dataArray.length; i++){
				var arr = new Array();
				arr = dataArray[i].score.replace(/(\[)|(\])|\'/g,"").split(",");
				dataFinal[i] = arr;
			}
			
			var rstore = new Ext.data.Store({
	  			proxy:new Ext.data.PagingMemoryProxy(dataFinal),
	  			reader:new Ext.data.ArrayReader({},readConfig)
  			});
			//console.log(rstore);
			//console.log(cm);
			return rstore;
  		}
  		/**
  		 * 得到季度store和columnmodel
  		 * @param {Object} json季度store
  		 */
  		function generateJStore(json){
  			var jsonArray = eval(json);
  			//调整jsonArray
  			var titleArr = new Array();
  			//初始化指标名数组
  			for(var i = 0; i<jsonArray.length; i++){
  				if(arrayContains(titleArr,jsonArray[i].itemName) == -1){
  					titleArr.push(jsonArray[i].itemName);
  				}
  			}
  			console.log("titleArr:"+titleArr);
  			dateArray = new Array();
  			var scoreArr = new Array();
  			for(var i=0 ; i<titleArr.length; i++){
  				for(var j = 0; j<jsonArray.length ; j++){
  					//alert(titleArr[i]+ "  "+jsonArray[j].itemScore+"  "+jsonArray[j].itemName);
  					var times = (1900+parseInt(jsonArray[j].evaluateDate.year))+"年第"+quarterJudge(jsonArray[j].evaluateDate.month+1)+"季度";
  					if(arrayContains(dateArray,times) == -1){
  							dateArray.push(times);
  					}
  					if(titleArr[i] == jsonArray[j].itemName){
  						var ind1 = -1;
  						var ind2 = -1;
  						for(var k = 0; k<scoreArr.length; k++){
  							if((ind2 = jsonArrayContainsTitle(scoreArr[k],titleArr[i])) != -1){
  								//alert(titleArr[i]+"  "+ind+"  "+jsonArray[j].itemScore);
  								ind1 = k;
  								break;
  							}
  						}
  						if(ind2 == -1){
  							//alert(ind1+"  "+titleArr[i]+ "  "+jsonArray[j].itemScore+"  "+jsonArray[j].itemName);
  							var temp = new Array();
  							var time = (1900+parseInt(jsonArray[j].evaluateDate.year))+"年第"+quarterJudge(jsonArray[j].evaluateDate.month+1)+"季度";
  							temp.push({'title':titleArr[i]});
  							temp.push({'time':time,'score':jsonArray[j].itemScore});
  							scoreArr.push(temp);
  							//console.log(temp);
  						}else{
  							//alert(ind1+"  "+titleArr[i]+ "  "+jsonArray[j].itemScore+"  "+jsonArray[j].itemName);
  							var time = (1900+parseInt(jsonArray[j].evaluateDate.year))+"年第"+quarterJudge(jsonArray[j].evaluateDate.month+1)+"季度";
  							scoreArr[ind1].push({'time':time,'score':jsonArray[j].itemScore});
  						}
  						//console.log(scoreArr);
  					}
  				}
  				//console.log(scoreArr);
  			}
  			/*for(var i = 0;i<scoreArr.length;i++){
  				alert(scoreArr[i][0].title);
  				for(var j=1;j<scoreArr[i].length;j++){
  					alert(scoreArr[i][j].time+"  "+scoreArr[i][j].score);
  				}
  			}*/
  			dataArray = new Array();//数据数组,格式：array("['指标1','80','70','60','40','90','100','67','66']","['指标2','80','70','60','40','90','100','67','66']",....)  			初始化日期数组  			for(var i = 0 ; i<jsonArray.length ; i++){
  			for(var i = 0;i<scoreArr.length;i++){
  				var itemname = scoreArr[i][0].title;
  				var scoreItem = new Array();
  				scoreItem.push(itemname);
  				for(var j = 1 ; j<scoreArr[i].length ; j++){
  					if(scoreArr[i][j].time != -1){
  						//alert(scoreArr[i][j].time);
  						var monthCount = 1;
	  					for(var k = j+1; k<scoreArr[i].length ; k++){
	  						if((scoreArr[i][k].time != -1) && (scoreArr[i][j].time == scoreArr[i][k].time)){
	  							scoreArr[i][j].score += parseInt(scoreArr[i][k].score);
	  							scoreArr[i][k].time = -1;
	  							monthCount ++;
	  						}
	  					}
	  					//alert(scoreArr[i][j].score);
	  					var dateIndex = arrayContains(dateArray,scoreArr[i][j].time);
	  					var itemIndex = scoreItem.length -1;
	  					if(dateIndex == itemIndex){
	  						scoreItem.push(scoreArr[i][j].score);
	  					}else{
	  						var dif = dateIndex - itemIndex;
	  						for(var m = 0 ; m<dif ; m++){
	  							scoreItem.push("该季度未统计");
	  						}
	  						scoreItem.push(scoreArr[i][j].score/monthCount);
	  					}
  					}
  				}
  				dataArray.push(scoreItem);
  			}
  			var dataMaxLength = 0;
  			for(var i = 0; i<dataArray.length; i++){
  				if(dataMaxLength<dataArray[i].length){
  					dataMaxLength = dataArray[i].length;
  				}
  			}
  			for(var i = 0; i<dataArray.length; i++){
  				if(dataArray[i].length<dataMaxLength){
  					for(var j =0 ; j<dataMaxLength - dataArray[i].length; j++){
  						dataArray[i].push("该季度未统计");
  					}
  				}
  			}
  			console.log("dataArray:"+dataArray);
  			console.log("dateArray:"+dateArray);
  			var colModel = new Array();
  			colModel[0] = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
  			colModel[1] = new Ext.grid.RowNumberer();
  			colModel[2] = {header:'指标名',dataIndex:'itemName'};
  			var readConfig = new Array();
  			readConfig.push({'name':'itemName'});
  			for(var i = 0; i<dateArray.length; i++){
  				colModel[i+3] = {header:dateArray[i],dataIndex:dateArray[i]};
  				readConfig.push({'name':dateArray[i]});
  			}
			
			cm = new Ext.grid.ColumnModel(colModel);
  			var rstore = new Ext.data.Store({
	  			proxy:new Ext.data.PagingMemoryProxy(dataArray),
	  			reader:new Ext.data.ArrayReader({},readConfig)
  			});
			//console.log(rstore);
			//console.log(cm);
			return rstore;
  		}
  		/**
  		 * 得到年gridpanel的store
  		 * @param {Object} json
  		 */
  		function generateYStore(json){
  			var jsonArray = eval(json);
  			//调整jsonArray
  			var titleArr = new Array();
  			//初始化指标名数组
  			for(var i = 0; i<jsonArray.length; i++){
  				if(arrayContains(titleArr,jsonArray[i].itemName) == -1){
  					titleArr.push(jsonArray[i].itemName);
  				}
  			}
  			console.log("titleArr:"+titleArr);
  			dateArray = new Array();
  			var scoreArr = new Array();
  			for(var i=0 ; i<titleArr.length; i++){
  				for(var j = 0; j<jsonArray.length ; j++){
  					//alert(titleArr[i]+ "  "+jsonArray[j].itemScore+"  "+jsonArray[j].itemName);
  					var times = (1900+parseInt(jsonArray[j].evaluateDate.year))+"年";
  					if(arrayContains(dateArray,times) == -1){
  							dateArray.push(times);
  					}
  					if(titleArr[i] == jsonArray[j].itemName){
  						var ind1 = -1;
  						var ind2 = -1;
  						for(var k = 0; k<scoreArr.length; k++){
  							if((ind2 = jsonArrayContainsTitle(scoreArr[k],titleArr[i])) != -1){
  								//alert(titleArr[i]+"  "+ind+"  "+jsonArray[j].itemScore);
  								ind1 = k;
  								break;
  							}
  						}
  						if(ind2 == -1){
  							//alert(ind1+"  "+titleArr[i]+ "  "+jsonArray[j].itemScore+"  "+jsonArray[j].itemName);
  							var temp = new Array();
  							var time = (1900+parseInt(jsonArray[j].evaluateDate.year))+"年";
  							temp.push({'title':titleArr[i]});
  							temp.push({'time':time,'score':jsonArray[j].itemScore});
  							scoreArr.push(temp);
  							//console.log(temp);
  						}else{
  							//alert(ind1+"  "+titleArr[i]+ "  "+jsonArray[j].itemScore+"  "+jsonArray[j].itemName);
  							var time = (1900+parseInt(jsonArray[j].evaluateDate.year))+"年";
  							scoreArr[ind1].push({'time':time,'score':jsonArray[j].itemScore});
  						}
  						//console.log(scoreArr);
  					}
  				}
  				//console.log(scoreArr);
  			}
  			/*for(var i = 0;i<scoreArr.length;i++){
  				alert(scoreArr[i][0].title);
  				for(var j=1;j<scoreArr[i].length;j++){
  					alert(scoreArr[i][j].time+"  "+scoreArr[i][j].score);
  				}
  			}*/
  			dataArray = new Array();//数据数组,格式：array("['指标1','80','70','60','40','90','100','67','66']","['指标2','80','70','60','40','90','100','67','66']",....)  			初始化日期数组  			for(var i = 0 ; i<jsonArray.length ; i++){
  			for(var i = 0;i<scoreArr.length;i++){
  				var itemname = scoreArr[i][0].title;
  				var scoreItem = new Array();
  				scoreItem.push(itemname);
  				for(var j = 1 ; j<scoreArr[i].length ; j++){
  					if(scoreArr[i][j].time != -1){
  						//alert(scoreArr[i][j].time);
  						var monthCount = 1;
	  					for(var k = j+1; k<scoreArr[i].length ; k++){
	  						if((scoreArr[i][k].time != -1) && (scoreArr[i][j].time == scoreArr[i][k].time)){
	  							scoreArr[i][j].score += parseInt(scoreArr[i][k].score);
	  							scoreArr[i][k].time = -1;
	  							monthCount ++;
	  						}
	  					}
	  					//alert(scoreArr[i][j].score);
	  					var dateIndex = arrayContains(dateArray,scoreArr[i][j].time);
	  					var itemIndex = scoreItem.length -1;
	  					if(dateIndex == itemIndex){
	  						scoreItem.push(scoreArr[i][j].score);
	  					}else{
	  						var dif = dateIndex - itemIndex;
	  						for(var m = 0 ; m<dif ; m++){
	  							scoreItem.push("当年未统计");
	  						}
	  						scoreItem.push(scoreArr[i][j].score/monthCount);
	  					}
  					}
  				}
  				dataArray.push(scoreItem);
  			}
  			var dataMaxLength = 0;
  			for(var i = 0; i<dataArray.length; i++){
  				if(dataMaxLength<dataArray[i].length){
  					dataMaxLength = dataArray[i].length;
  				}
  			}
  			for(var i = 0; i<dataArray.length; i++){
  				if(dataArray[i].length<dataMaxLength){
  					for(var j =0 ; j<dataMaxLength - dataArray[i].length; j++){
  						dataArray[i].push("当年未统计");
  					}
  				}
  			}
  			console.log("dataArray:"+dataArray);
  			console.log("dateArray:"+dateArray);
  			var colModel = new Array();
  			colModel[0] = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
  			colModel[1] = new Ext.grid.RowNumberer();
  			colModel[2] = {header:'指标名',dataIndex:'itemName'};
  			var readConfig = new Array();
  			readConfig.push({'name':'itemName'});
  			for(var i = 0; i<dateArray.length; i++){
  				colModel[i+3] = {header:dateArray[i],dataIndex:dateArray[i]};
  				readConfig.push({'name':dateArray[i]});
  			}
			
			cm = new Ext.grid.ColumnModel(colModel);
  			var rstore = new Ext.data.Store({
	  			proxy:new Ext.data.PagingMemoryProxy(dataArray),
	  			reader:new Ext.data.ArrayReader({},readConfig)
  			});
			//console.log(rstore);
			//console.log(cm);
			return rstore;
  		}
  		/**
  		 * 初始化图标的store
  		 * @param {Object} json 后台返回的原始json数据
  		 */
  		function generateChartStore(json){
  			var jsonArray = eval(json);
  			//console.log('jsonArray'+jsonArray);
  			var fields = new Array();
  			fields.push('name');
  			fields.push('score');
  			var datas = new Array();
  			for(var i = 0; i<jsonArray.length; i++){
  				var year = 1900 + jsonArray[i].evaluateDate.year;
  				var month = jsonArray[i].evaluateDate.month + 1;
  				var time = year + "-" + month;
  				
  				if(jsonArrayContainsName(datas,time) == -1){
  					datas.push({'name':time,'score':jsonArray[i].itemScore});
  				}else{
  					var index = jsonArrayContainsName(datas,time);
  					datas[index].score += jsonArray[i].itemScore;
  				}
  			}
  			//console.log('fields'+fields+'data'+datas);
  			var cscore = new Ext.data.JsonStore({
  				fields : fields,
  				data : datas
  			});
  			return cscore;
  		}
  		/**
  		 * 初始化季度图标的store
  		 * @param {Object} json 后台返回的原始json数据
  		 */
  		function generateJChartStore(){
  			var fields = new Array();
  			fields.push('name');
  			fields.push('score');
  			var datas = new Array();
  			for(var i = 0; i<dateArray.length; i++){
  				var date = dateArray[i];
  				var score = 0;
  				var dateIndex = arrayContains(dateArray,date);
  				for(var j = 0; j<dataArray.length; j++){
  					if(typeof(dataArray[j][dateIndex+1]) == "number"){
  						score += dataArray[j][dateIndex+1];
  					}
  				}
  				datas.push({"name":date,"score":score});
  			}
  			var cscore = new Ext.data.JsonStore({
  				fields : fields,
  				data : datas
  			});
  			return cscore;
  		}
  		
  		/*function generateYChartStore(){
  			var fields = new Array();
  			fields.push('name');
  			fields.push('score');
  			var datas = new Array();
  			for(var i = 0; i<dateArray.length; i++){
  				var date = dateArray[i];
  				var score = 0;
  				var dateIndex = arrayContains(dateArray,date);
  				for(var j = 0; j<dataArray.length; j++){
  					if(typeof(dataArray[j][dateIndex+1]) == "number"){
  						score += dataArray[j][dateIndex+1];
  					}
  				}
  				datas.push({"name":date,"score":score});
  			}
  			var cscore = new Ext.data.JsonStore({
  				fields : fields,
  				data : datas
  			});
  			return cscore;
  		}*/
  		/**
  		 * 初始化细化指标得分图标的store
  		 * @param {Object} json 后台返回的原始json数据
  		 */
  		function generateChartStoreDetail(json){
  			var jsonArray = eval(json);
  			var fields = new Array();
  			var data = new Array();
  			var series = new Array();
  			fields.push('name');
  			for(var i = 0; i<jsonArray.length; i++){
  				var year = 1900 + parseInt(jsonArray[i].evaluateDate.year);
  				var month = jsonArray[i].evaluateDate.month+1;
  				var time = year+"-"+month;
  				
  				if(arrayContains(fields,jsonArray[i].itemName) == -1){
  					fields.push(jsonArray[i].itemName);
  					series.push(jsonArray[i].itemName);
  				}
  				if(jsonArrayContainsName(data,time) == -1){
  					var jsonString = '{\"name\":\"'+time+'\",\"'+jsonArray[i].itemName+'\":'+jsonArray[i].itemScore+'';
  					//alert(jsonString);
  					//var obj = eval('('+jsonString+')');
  					//console.log('jsonString'+obj);
  					data.push({'name':time,'jsonString':jsonString});
  				}else{
  					var index = jsonArrayContainsName(data,time);
  					data[index].jsonString += ',\"'+jsonArray[i].itemName+'\":'+jsonArray[i].itemScore+'';
  				}
  			}
  			for(var i = 0; i<data.length; i++){
  				data[i].jsonString += '}';
  				data[i] = eval('('+data[i].jsonString+')');
  			}
  			var cdscore = new Ext.data.JsonStore({
  				fields : fields,
  				data : data
  			});
  			for(var i = 0; i<series.length; i++){
  				var itemName = series[i];
  				var a=Math.round(Math.random()*0x1000000);
				var c="00000".concat(a.toString(16));
  				var jsonObject = {type:'line',displayName:itemName,yField:itemName,style:{color:c.substr(c.length-6,6)}};
  				series[i] = jsonObject;
  			}
  			var result = new Array();
  			result.push(cdscore);
  			result.push(series);
  			console.log('data'+data+'fields'+fields+'series'+series);
  			return result;
  		}
  		
  		function generateJChartStoreDetail(){
  			var fields = new Array();
  			var data = new Array();
  			var series = new Array();
  			fields.push('name');
  			for(var i = 0; i<dataArray.length; i++){
  				fields.push(dataArray[i][0]);
  				series.push(dataArray[i][0]);
  			}
  			for(var i = 0; i<dateArray.length; i++){
  				var dataString = "{\"name\":\""+dateArray[i]+"\"";
  				for(var j = 0; j<dataArray.length; j++){
  					dataString += ",\""+dataArray[j][0]+"\":";
  					if(typeof(dataArray[j][i+1]) == "number"){
  						dataString += dataArray[j][i+1];
  					}else{
  						dataString += "\""+dataArray[j][i+1]+"\"";
  					}
  				}
  				dataString += "}";
  				var da = eval('('+dataString+')');
  				data.push(da);
  			}
  			var cdscore = new Ext.data.JsonStore({
  				fields : fields,
  				data : data
  			});
  			for(var i = 0; i<series.length; i++){
  				var itemName = series[i];
  				var a=Math.round(Math.random()*0x1000000);
				var c="00000".concat(a.toString(16));
  				var jsonObject = {type:'line',displayName:itemName,yField:itemName,style:{color:c.substr(c.length-6,6)}};
  				series[i] = jsonObject;
  			}
  			console.log(series);
  				var result = new Array();
  			result.push(cdscore);
  			result.push(series);
  			return result;
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
  		/**
  		 * 判断json数组中是否含有title为string值的项
  		 * @param {Object} array
  		 * @param {Object} string
  		 * @return {TypeName} -1：不包含，其他：项目所在位置
  		 */
  		function jsonArrayContainsTitle(array,string){
  			if(array.length == 0){
  				return -1;
  			}
  			for(var i = 0; i<array.length; i++){
  				if(array[i].title == string){
  					return i;
  				}
  			}
  			return -1;
  		}
  		/**
  		 * 判断json数组中是否含有name为string值的项
  		 * @param {Object} array
  		 * @param {Object} string
  		 * @return {TypeName} -1：不包含，其他：项目所在位置
  		 */
  		function jsonArrayContainsName(array,string){
  			if(array.length == 0){
  				return -1;
  			}
  			for(var i = 0; i<array.length; i++){
  				if(array[i].name == string){
  					return i;
  				}
  			}
  			return -1;
  		}
  		
  		function quarterJudge(month){
  			return month%3 == 0 ? month/3 : Math.floor(month/3) + 1 ;
  		}
  		/*var stores = new Ext.data.JsonStore({
	        fields:['name', 'a', 'b'],
	        data: [
	            {name:'Jul 11', a: 245000, b: 3000000},
	            {name:'Aug 11', a: 240000, b: 3500000},
	            {name:'Sep 11', a: 355000, b: 4000000},
	            {name:'Oct 11', a: 375000, b: 4200000},
	            {name:'Nov 11', a: 490000, b: 4500000},
	            {name:'Dec 11', a: 495000, b: 5800000},
	            {name:'Jan 12', a: 520000, b: 6000000},
	            {name:'Feb 12', a: 620000, b: 7500000}
	        ]
   		});
	   	var cumDataDetail = new Ext.data.JsonStore({
	   		fields:['name','a','b','c','d','e','f','g','h'],
	   		data:[
				{name:'Jul 11',a:80,b:70,c:60,d:40,e:90,f:100,g:67,h:66},
				{name:'Aug 11',a:70,b:34,c:60,d:40,e:90,f:100,g:67,h:66},
				{name:'Sep 11',a:60,b:12,d:40,e:90,f:100,g:67,h:66},
				{name:'Oct 11',a:55,b:76,c:60,d:40,e:90,f:100,g:67,h:66},
				{name:'Nov 11',a:43,b:23,c:60,d:40,e:90,f:100,g:67,h:66},
				{name:'Dec 11',a:29,b:76,c:60,d:40,e:90,f:100,g:67,h:66},
				{name:'Jan 12',a:51,b:98,c:60,d:40,e:90,f:100,g:67,h:66},
				{name:'Feb 12',a:78,b:65,c:60,d:40,e:90,f:100,g:67,h:66}
	   		]
	   	});*/
   	
  	function deleteParam(sm_,rowIndex,record){
  		/*alert(rowIndex);
  		alert((record.json)[0]);*/
  		if(arrayContains(deleteItems,(record.json)[0]) == -1){
  			deleteItems.push(record.json[0]);
  		}
  		var fields = new Array();
  			var data = new Array();
  			var series = new Array();
  			fields.push('name');
  			for(var i = 0; i<dataArray.length; i++){
  				if(arrayContains(deleteItems,dataArray[i][0]) == -1){
  					fields.push(dataArray[i][0]);
  					series.push(dataArray[i][0]);
  				}
  			}
  			for(var i = 0; i<dateArray.length; i++){
  				var dataString = "{\"name\":\""+dateArray[i]+"\"";
  				for(var j = 0; j<dataArray.length; j++){
	  					if(arrayContains(deleteItems,dataArray[j][0]) == -1){
		  					dataString += ",\""+dataArray[j][0]+"\":";
		  					if(typeof(dataArray[j][i+1]) == "number"){
		  						dataString += dataArray[j][i+1];
		  					}else{
		  						dataString += "\""+dataArray[j][i+1]+"\"";
		  					}
  					}
  				}
  				dataString += "}";
  				var da = eval('('+dataString+')');
  				data.push(da);
  			}
  			var cdscore = new Ext.data.JsonStore({
  				fields : fields,
  				data : data
  			});
  			for(var i = 0; i<series.length; i++){
  				var itemName = series[i];
  				var a=Math.round(Math.random()*0x1000000);
				var c="00000".concat(a.toString(16));
  				var jsonObject = {type:'line',displayName:itemName,yField:itemName,style:{color:c.substr(c.length-6,6)}};
  				series[i] = jsonObject;
  			}
  			Ext.getCmp("chartPanel").destroy();
  			ncstore = generateJChartStore();
  			totalChart = new Ext.Panel({
						        iconCls:'chart',
						        title: '中心综合评分趋势图',
						        frame:true,
						        id : 'totalPanel',
						        width:500,
						        height:300,
						        layout:'fit',
						        items: {
						            xtype: 'columnchart',
						            store: ncstore,
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
						        height:300,
						        layout:'fit',
						        items: {
						            xtype: 'linechart',
						            store: cdscore,
						            url: 'ext-3.0.0/resources/charts.swf',
						            xField: 'name',
						            series: series
						        }
			
					    	});
  			var chartPanel = new Ext.form.FormPanel({
					 		frame:true,
				  			autoHeight:true,
				  			autoWidth:true,
				  			id : 'chartPanel',
				  			labelSeparator:':',
				  			labelAlign:'left',
				  			//applyTo:'container',
				  			renderTo : 'container',
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
	 
  	function addParam(sm_,rowIndex,record){
  		/*alert(rowIndex);
  		alert((record.json)[0]);*/
  		indexItem = arrayContains(deleteItems,(record.json)[0]);
  		if(indexItem != -1){
  			di = deleteItems.splice(indexItem,1);
  		}
  		var fields = new Array();
  		var data = new Array();
  			var series = new Array();
  			fields.push('name');
  			for(var i = 0; i<dataArray.length; i++){
  				if(arrayContains(deleteItems,dataArray[i][0]) == -1){
  					fields.push(dataArray[i][0]);
  					series.push(dataArray[i][0]);
  				}
  			}
  			for(var i = 0; i<dateArray.length; i++){
  				var dataString = "{\"name\":\""+dateArray[i]+"\"";
  				for(var j = 0; j<dataArray.length; j++){
	  					if(arrayContains(deleteItems,dataArray[j][0]) == -1){
		  					dataString += ",\""+dataArray[j][0]+"\":";
		  					if(typeof(dataArray[j][i+1]) == "number"){
		  						dataString += dataArray[j][i+1];
		  					}else{
		  						dataString += "\""+dataArray[j][i+1]+"\"";
		  					}
  					}
  				}
  				dataString += "}";
  				var da = eval('('+dataString+')');
  				data.push(da);
  			}
  			var cdscore = new Ext.data.JsonStore({
  				fields : fields,
  				data : data
  			});
  			for(var i = 0; i<series.length; i++){
  				var itemName = series[i];
  				var a=Math.round(Math.random()*0x1000000);
				var c="00000".concat(a.toString(16));
  				var jsonObject = {type:'line',displayName:itemName,yField:itemName,style:{color:c.substr(c.length-6,6)}};
  				series[i] = jsonObject;
  			}
  			Ext.getCmp("chartPanel").destroy();
  			ncstore = generateJChartStore();
  			totalChart = new Ext.Panel({
						        iconCls:'chart',
						        title: '中心综合评分趋势图',
						        frame:true,
						        id : 'totalPanel',
						        width:500,
						        height:300,
						        layout:'fit',
						        items: {
						            xtype: 'columnchart',
						            store: ncstore,
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
						        height:300,
						        layout:'fit',
						        items: {
						            xtype: 'linechart',
						            store: cdscore,
						            url: 'ext-3.0.0/resources/charts.swf',
						            xField: 'name',
						            series: series
						        }
			
					    	});
  			var chartPanel = new Ext.form.FormPanel({
					 		frame:true,
				  			autoHeight:true,
				  			autoWidth:true,
				  			id : 'chartPanel',
				  			labelSeparator:':',
				  			labelAlign:'left',
				  			//applyTo:'container',
				  			renderTo : 'container',
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
  	
	 var defaultConfig = {
		 url : 'resultAction',
		 params : {"type" : "center"},
		 method : "POST",
		 callback : function(options,success,response){
			 
		 }
	 };
  		
  	});