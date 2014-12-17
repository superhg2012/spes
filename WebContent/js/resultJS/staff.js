if(Ext.isChrome===true){       
        var chromeDatePickerCSS = ".x-date-picker {border-color: #1b376c;background-color:#fff;position: relative;width: 185px;}";
        Ext.util.CSS.createStyleSheet(chromeDatePickerCSS,'chromeDatePickerStyle');
}

Ext.onReady(function(){
	var storeCombo;//comboBox的store
	var gridStoreStaff;//表格store
	var gridCmStaff;//表格columnModel
	var pSizeStaff = 10;
	var dateArrStaff;//日期数组，用于构造columnModel等
	var cumgridStaff;//表格
	var typeStaff = 'month';//month, quarter, year
	var chartStoreStaff;//折线图store
	var seriesStaff;//折线图series
	var removeListStaff = new Array();
	var displayListStaff = new Array();
	var removeStrStaff = removeListStaff.join(",");
	var gridStoreDetailStaff;//明细表格的store
	var gridCmDetailStaff;//明细表格的columnmodel
	var pSizeDetailStaff = 9;//明细表格的每页行数
	var cumgridDetailStaff;
	var chartStoreDetailStaff;
	var seriesDetailStaff;
	var curWindowIdStaff;
	var curWindowIdArrStaff = new Array();
	var curWindowIdArrForSeriesStaff = new Array();
	var curUserId;
	var curUserIdArr = new Array();
	var curUserIdArrForSeries = new Array();
	Ext.QuickTips.init();
	
	var requestConfigStaff = {
		url:'gyx/windowResult/availbleWindowAction.action',
		callback:function(options,success,response){
			var json = Ext.util.JSON.decode(response.responseText);
			var sRoot = eval(json.root);
			var datas = new Array();
			for(var i = 0; i<sRoot.length; i++){
				var temp = new Array();
				temp.push(sRoot[i].windowId);
				temp.push(sRoot[i].windowName);
				datas.push(temp);
			}
			storeCombo = new Ext.data.SimpleStore({
				fields : ['value','text'],
				data : datas
			});
			//Ext.get("ext-gen151").remove();
			var staffForm = new Ext.Panel({
	  			title:'人员绩效考核评价结果展示',
	  			frame:true,
	  			renderTo : 'bodysGyxStaff',
	  			labelAlign:'left',
	  			id : 'staffForm',
	  			bodyPadding:5,
	  			layout : 'table',
	  			layoutConfig: {columns:11},
	  			defaults: { width:120},
	  			items : [new Ext.form.ComboBox({
   						store : storeCombo,
   						id : 'departments',
	       				triggerAction:'all',
	       				displayField:'text',
	       				valueField:'value',
	       				mode:'local',
	       				listeners: {
							select: function(f,r,i){
								var windowId = f.value;
								queryStaff(windowId);
							}
						}
	       			}),
	  				{xtype : 'label',text : '时间范围：'},
	  				{    
	   			 		xtype : 'datefield',
	                    fieldLabel : '时间从',
	        			name : 'fromStaff',
			            id : 'fromStaff',
			            invalidText : '时间必需符合格式:2000-01-01',
			            format : 'Y-m-d',
	                },
	                {xtype : 'label',text : '~'	},
	  				{
		   				xtype : 'datefield',
		              	fieldLabel : '到',
						name : 'toStaff',
		           	 	id : 'toStaff',
		           	 	invalidText : '时间必需符合格式:2000-01-01',
		            	format : 'Y-m-d',
		            },{colspan : 1, text : ' ',width:50},
	            		   new Ext.form.Radio({
	                		   name : 'mjy',
	                		   id : 'rad1Staff',
	                		   inputValue : 'month',
	                		   boxLabel : '月结果展示'
	                	   }),new Ext.form.Radio({
	                		   name : 'mjy',
	                		   id : 'rad2Staff',
	                		   inputValue : 'quarter',
	                		   boxLabel : '季度结果展示'
	                	   }),new Ext.form.Radio({
	                		   name : 'mjy',
	                		   id : 'rad3Staff',
	                		   inputValue : 'year',
	                		   boxLabel : '年结果展示'
	                	   }),{colspan : 1, text : ' ',width : 50},
		            			new Ext.Button({
		            				text : '查询',
		            				id : 'queryButtonStaff',
		            				minWidth : 800,
		            				listeners : {
		                        		  	"click": function(){
		                        		  			queryStaff(curWindowIdStaff);
		                        		  	}
		                        		  }
		            				})
	  				]
	  		});
			
			Ext.get('rad1Staff').on('click',modeMonthStaff);
		  	Ext.get('rad2Staff').on('click',modeQuarterStaff);
		  	Ext.get('rad3Staff').on('click',modeYearStaff);
		  	
		  	function modeMonthStaff(){
		  		typeStaff = "month";
		  		queryStaff(curWindowIdStaff);
		  	}
		  	function modeQuarterStaff(){
		  		typeStaff = "quarter";
		  		queryStaff(curWindowIdStaff);
		  	}
		  	function modeYearStaff(){
		  		typeStaff = "year";
		  		queryStaff(curWindowIdStaff);
		  	}
			
			Ext.getCmp("departments").setValue(storeCombo.getAt(0).get("value"));
			curWindowIdStaff = Ext.getCmp("departments").getValue(); 
			var from = Ext.fly("fromStaff").getValue();
			var to = Ext.fly("toStaff").getValue();
			if(from == "" ^ to == ""){
				Ext.MessageBox.alert("提示信息","请填写起始-结束日期");
				return false;
			}

			var requestConfigStaff = {
				url : 'gyx/staffResult/getStaffAndScore.action',
				params : {"windowId":curWindowIdStaff,"from":from,"to":to,"type":typeStaff},
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
  					var sRoot = eval(json.root);
  					for(var i = 0; i<sRoot.length; i++){
  						curUserIdArrForSeries.push(sRoot[i].userId);
  					}
  					var dateArrStaff = eval(json.dateList);
  					generateJSONStoreStaff('gyx/staffResult/getStaffAndScore.action',dateArrStaff,typeStaff,curWindowIdStaff);
  					generateGridColumnStoreStaff(dateArrStaff);
  					var sm0 = new Ext.grid.CheckboxSelectionModel({handleMouseDown: showDetailStaff});
			  		sm0.on('rowselect',function(sm_,rowIndex,record){deleteParamStaff(sm_,rowIndex,record)});
			  		sm0.on('rowdeselect',function(sm_,rowIndex,record){addParamStaff(sm_,rowIndex,record)});
			  		cumgridStaff = new Ext.grid.GridPanel({
			  			id:'cumgridStaff',
			  			sm:sm0,
			  			store:gridStoreStaff,
			  			stripeRows:true,
			  			viewConfig:{
			  				forceFit:true,
			  				sortAscText : '升序',
			  				sortDescText : '降序'
			  			},
			  			height:299,
			  			autowidth:500,
			  			colModel:gridCmStaff,
			  			bbar : new Ext.PagingToolbar({
			                    	  pageSize : pSizeStaff,
			                    	  store : gridStoreStaff,
			                    	  displayInfo : true,
			                    	  displayMsg : "",
			                    	  emptyMsg : '没有记录'
			                      }),
			            remoteSort: true
			  		});
			  		Ext.getCmp("cumgridStaff").render("tableGyxStaff");
			  		gridStoreStaff.load({params:{start: 0,limit:pSizeStaff}});
			  		//Ext.get("ext-gen13").remove();
					//queryStaff(curWindowIdStaff);
			  		generateChartStoreStaff(json);
			  		detailChartStaff = new Ext.Panel({
				        iconCls:'chart',
				        title: '人员总分趋势图',
				        id : 'detailPanelStaff',
				        frame:true,
				        width:1500,
				        height:299,
				        layout:'fit',
				        items: {
				            xtype: 'linechart',
				            store: chartStoreStaff,
				            url: 'ext-3.0.0/resources/charts.swf',
				            xField: 'name',
				            series: json.series
				        }
			    	});
			  		var chartPanel = new Ext.form.FormPanel({
				 		frame:true,
			  			autoHeight:true,
			  			autoWidth:true,
			  			id : 'chartPanelStaff',
			  			labelSeparator:':',
			  			labelAlign:'left',
			  			renderTo:'containerStaff',
			  			bodyPadding:0,
			  			items : [{
					        layout : 'column',
					        items : [{
					                columnWidth : 1, 
									layout : 'form',
									style:"padding-top:1px;padding-left:1px;padding-bottom:0px",
					       			items : detailChartStaff
					        				}]
			                      }]
					 });
				}
			}
			Ext.Ajax.request(requestConfigStaff);
		}//callback结束
	}
	Ext.Ajax.request(requestConfigStaff);
	
	function generateJSONStoreStaff(url,dateArrStaff,typeStaff,windowId){
		var readerRecord = new Array();
		readerRecord.push({'name':'userName'});
		readerRecord.push({'name':'totalScore'});
		readerRecord.push({'name':'userId'});
		for(var i = 0 ; i<dateArrStaff.length; i++){
			readerRecord.push({'name':dateArrStaff[i]});
		}
		gridStoreStaff = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({url : url+"?type="+typeStaff+"&windowId="+windowId}),
			reader : new Ext.data.JsonReader({
				totalProperty : 'totalProperty',
				root : 'root'
			},readerRecord)
		});
	}
	
	function generateGridColumnStoreStaff(dateArrStaff){
			var colModel = new Array();
  			colModel[0] = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
  			colModel[1] = new Ext.grid.RowNumberer();
  			colModel[2] = {header:'姓名',dataIndex:'userName'};
  			colModel[3] = {header:'总分',dataIndex:'totalScore'};
  			for(var i = 0; i<dateArrStaff.length; i++){
  				colModel[i+4] = {header:dateArrStaff[i].split("&")[2],dataIndex:dateArrStaff[i]};
  			}
			gridCmStaff = new Ext.grid.ColumnModel(colModel);
	}
	
	function generateChartStoreStaff(json){
		var data = eval(json.chart);
		var fields = eval(json.fields);
		
		//console.log(json.chart);
		chartStoreStaff = new Ext.data.JsonStore({
			fields : fields,
			data : data
		});
	}
	
	function deleteParamStaff(sm_,rowIndex,record){
		var userId = record.json.userId;
		if(arrayContains(removeListStaff,userId) == -1){
			removeListStaff.push(userId);
		}
		reGenerateChartStaff();
	}
	
	function addParamStaff(sm_,rowIndex,record){
		var userId = record.json.userId;
		var Index = arrayContains(removeListStaff,userId);
		if(Index != -1){
			removeListStaff.splice(Index,1);
		}
		reGenerateChartStaff();
	}
	
	function radiochange(smt,data){
  		typeStaff = data.inputValue;
  		queryStaff(Ext.getCmp("departments").getValue());
	}
	
	function reGenerateChartStaff(){
		var from = Ext.fly("fromStaff").getValue();
		var to = Ext.fly("toStaff").getValue();
		if(from == "" ^ to == ""){
			Ext.MessageBox.alert("提示信息","请填写起始-结束日期");
			return false;
		}
		var requestConfigStaff = {
			url : "gyx/staffResult/getStaffAndScore.action",
			method : 'post',
			params :{"windowId":Ext.getCmp("departments").getValue(),"from":from,"to":to,"type":typeStaff,'removeList':removeListStaff},
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
  				Ext.getCmp("chartPanelStaff").destroy();
		  		generateChartStoreStaff(json);
		  		detailChartStaff = new Ext.Panel({
			        iconCls:'chart',
			        title: '人员明细评分趋势图',
			        id : 'detailPanelStaff',
			        frame:true,
			        width:1500,
			        height:350,
			        layout:'fit',
			        items: {
			            xtype: 'linechart',
			            store: chartStoreStaff,
			            url: 'ext-3.0.0/resources/charts.swf',
			            xField: 'name',
			            series: json.series
			        }

		    	});
		  		var chartPanelStaff = new Ext.form.FormPanel({
			 		frame:true,
		  			autoHeight:true,
		  			autoWidth:true,
		  			id : 'chartPanelStaff',
		  			labelSeparator:':',
		  			labelAlign:'left',
		  			renderTo:'containerStaff',
		  			bodyPadding:5,
		  			items : [{
				        layout : 'column',
				        items : [{
				                columnWidth : 1, 
								layout : 'form',
								style:"padding-top:20px;padding-left:50px;padding-bottom:20px",
				       			items : detailChartStaff
				        				}]
		                      }]
						 });
		  		
  			}
		}
		Ext.Ajax.request(requestConfigStaff);
	}
	
	
	function queryStaff(windowId){
		curUserIdArr = new Array();
		displayListStaff = new Array();
		curUserId = "";
		var from = Ext.fly("fromStaff").getValue();
		var to = Ext.fly("toStaff").getValue();
		if(from == "" ^ to == ""){
			Ext.MessageBox.alert("提示信息","请填写起始-结束日期");
			return false;
		}
		var requestConfigStaff = {
			url : "gyx/staffResult/getStaffAndScore.action",
			method : 'post',
			params :{"windowId":windowId,"from":from,"to":to,"type":typeStaff},
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
  				dateArrStaff = eval(json.dateList);
  				generateJSONStoreStaff('gyx/staffResult/getStaffAndScore.action',dateArrStaff,typeStaff,windowId);
  				generateGridColumnStoreStaff(dateArrStaff);
  				Ext.getCmp("cumgridStaff").destroy();
				var sm0 = new Ext.grid.CheckboxSelectionModel({handleMouseDown: showDetailStaff});
	  			sm0.on('rowselect',function(sm_,rowIndex,record){deleteParamStaff(sm_,rowIndex,record)});
	  			sm0.on('rowdeselect',function(sm_,rowIndex,record){addParamStaff(sm_,rowIndex,record)});
	  			cumgridStaff = new Ext.grid.GridPanel({
		  			renderTo:'tableGyxStaff',
		  			id:'cumgridStaff',
		  			sm:sm0,
		  			store:gridStoreStaff,
		  			viewConfig:{
		  				forceFit:true,
		  				sortAscText : '升序',
		  				sortDescText : '降序'
		  			},
		  			height:300,
		  			autowidth:500,
		  			colModel:gridCmStaff,
		  			bbar : new Ext.PagingToolbar({
		                    	  pageSize : pSizeStaff,
		                    	  store : gridStoreStaff,
		                    	  displayInfo : true,
		                    	  displayMsg : "",
		                    	  emptyMsg : '没有记录'
		                      }),
		            remoteSort: true
		  		});
		  		gridStoreStaff.load({params:{start: 0,limit:pSizeStaff}});
		  		Ext.getCmp("chartPanelStaff").destroy();
		  		generateChartStoreStaff(json);
		  		detailChartStaff = new Ext.Panel({
			        iconCls:'chart',
			        title: '中心明细评分趋势图',
			        id : 'detailPanelStaff',
			        frame:true,
			        width:1500,
			        height:298,
			        layout:'fit',
			        items: {
			            xtype: 'linechart',
			            store: chartStoreStaff,
			            url: 'ext-3.0.0/resources/charts.swf',
			            xField: 'name',
			            series: json.series
			        }

		    	});
		  		var chartPanelStaff = new Ext.form.FormPanel({
			 		frame:true,
		  			autoHeight:true,
		  			autoWidth:true,
		  			id : 'chartPanelStaff',
		  			labelSeparator:':',
		  			labelAlign:'left',
		  			renderTo:'containerStaff',
		  			bodyPadding:5,
		  			items : [{
				        layout : 'column',
				        items : [{
				                columnWidth : 1, 
								layout : 'form',
								style:"padding-top:0px;padding-left:1px;padding-bottom:1px",
				       			items : detailChartStaff
				        				}]
		                      }]
						 });
			}
		}
		Ext.Ajax.request(requestConfigStaff);
	}
	
	function showDetailStaff(a,b,c){
		var userId = gridStoreStaff.getAt(b).get('userId');
		curUserId=userId;
		var from = Ext.fly("fromStaff").getValue();
		var to = Ext.fly("toStaff").getValue();
		if(from == "" ^ to == ""){
			Ext.MessageBox.alert("提示信息","请填写起始-结束日期");
			return false;
		}
		var requestConfigStaff = {
			url : "gyx/staffResult/getStaffAndScore!getStaffScore.action",
			params : {'from':from,'to':to,'type':typeStaff,'userId':userId,'displayList':displayListStaff,'userIdArray':curUserIdArrForSeries},
			callback :function(options,success,response){
					var json = Ext.util.JSON.decode(response.responseText);
					var dateList = json.dateList;
					//console.log("dsdasd:"+dateList);
					gridStoreDetailStaff = generateJSONStoreForStaff("gyx/staffResult/getStaffAndScore!getStaffScore.action",dateList,typeStaff,userId,from,to);
					gridCmDetailStaff = generateGridColumnStoreForStaff(dateList);
					Ext.getCmp("chartPanelStaff").destroy();
					var sm0 = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
	  				sm0.on('rowselect',function(sm_,rowIndex,record){addParamDetailStaff(sm_,rowIndex,record)});
	  				sm0.on('rowdeselect',function(sm_,rowIndex,record){deleteParamDetailStaff(sm_,rowIndex,record)});
					cumgridDetailStaff = new Ext.grid.GridPanel({
						title:'人员指标得分明细',
			  			id:'cumgridDetailStaff',
			  			sm:sm0,
			  			store:gridStoreDetailStaff,
			  			stripeRows:true,
			  			viewConfig:{
			  				forceFit:true,
			  				sortAscText : '升序',
			  				sortDescText : '降序'
			  			},
			  			height:298,
			  			autowidth:500,
			  			colModel:gridCmDetailStaff,
			  			bbar : new Ext.PagingToolbar({
			                    	  pageSize : pSizeDetailStaff,
			                    	  store : gridStoreDetailStaff,
			                    	  displayInfo : true,
			                    	  displayMsg : "",
			                    	  emptyMsg : '没有记录'
			                      }),
			            remoteSort: true
			  		});
					gridStoreDetailStaff.load({params:{start:0,limit:pSizeDetailStaff}});
					//chartStoreDetailStaff = generateChartStoreForItem(json);
					detailChartStaff = new Ext.Panel({
				        iconCls:'chart',
				        title: '人员明细评分趋势图',
				        id : 'detailPanelStaff',
				        frame:true,
				        width:700,
				        height:298,
				        layout:'fit',
				        items: {
				            xtype: 'linechart',
				            id : 'detailChartStaff',
				            store: chartStoreDetailStaff,
				            url: 'ext-3.0.0/resources/charts.swf',
				            xField: 'name',
				            series: json.series
				        }
	
			    	});
		  		var chartPanelStaff = new Ext.form.FormPanel({
			 		frame:true,
		  			autoHeight:true,
		  			autoWidth:true,
		  			id : 'chartPanelStaff',
		  			labelSeparator:':',
		  			labelAlign:'left',
		  			renderTo:'containerStaff',
		  			bodyPadding:5,
		  			items : [{
				        layout : 'column',
				        items : [{
				                columnWidth : .5, 
								layout : 'form',
				       			items : cumgridDetailStaff
				        				},{
				                columnWidth : .5, 
								layout : 'form',
								style:"padding-top:0px;padding-left:1px;padding-bottom:1px",
				       			items : detailChartStaff
				        				}]
		                      }]
						 });
		  		//Ext.getCmp("detailChart").bindStore(chartStoreDetailStaff);
			}
		}
		Ext.Ajax.request(requestConfigStaff);
	}
	
	function generateChartStoreForItemStaff(json){
		var res = eval(json.chartDetail);
		for(var i=0; i<res.length; i++){
			res[i].name = res[i].name.split("&")[2];
		}
		var chartStoreStaff = new Ext.data.JsonStore({
			fields : json.fields,
			data : res
		});
		return chartStoreStaff;
	}
	
	function deleteParamDetailStaff(sm_,rowIndex,record){
		var itemId = record.json.itemId;
		var Index = arrayContains(displayListStaff,itemId);
		if(Index != -1){
			displayListStaff.splice(Index,1);
		}
		reGenerateChartDetailStaff();
		
	}
	
	function addParamDetailStaff(sm_,rowIndex,record){
		var itemId = record.json.itemId;
		var Index = arrayContains(displayListStaff,itemId);
		var Index1 = arrayContains(curUserIdArr,curUserId);
		if(Index == -1){
			displayListStaff.push(itemId);
		}
		if(Index1 == -1){
			curUserIdArr.push(curUserId);
		}
		reGenerateChartDetailStaff();
	}
	
	function generateJSONStoreForStaff(url,dateArrStaff,typeStaff,userId,from,to){
		var readerRecord = new Array();
		readerRecord.push({'name':'itemName'});
		readerRecord.push({'name':'userId'});
		readerRecord.push({'name':'itemGrade'});
		readerRecord.push({'name':'parentItemName'});
		for(var i = 0 ; i<dateArrStaff.length; i++){
			readerRecord.push({'name':dateArrStaff[i]});
		}
		var gridStoreStaff = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({url : url+"?type="+typeStaff+"&userId="+userId+"&from="+from+"&to="+to}),
			reader : new Ext.data.JsonReader({
				totalProperty : 'totalProperty',
				root : 'root'
			},readerRecord)
		});
		return gridStoreStaff;
	}
	
	function reGenerateChartDetailStaff(){
		var from = Ext.fly("fromStaff").getValue();
		var to = Ext.fly("toStaff").getValue();
		if(from == "" ^ to == ""){
			Ext.MessageBox.alert("提示信息","请填写起始-结束日期");
			return false;
		}
		var requestConfigStaff = {
			url : "gyx/staffResult/getStaffAndScore!getStaffScore.action",
			method : 'post',
			params :{'from':from,'to':to,'type':typeStaff,'displayList':displayListStaff,'userId':curUserId,'userIdArray':curUserIdArr},
			callback :function(options,success,response){
				var json = Ext.util.JSON.decode(response.responseText);
				var dateList = json.dateList;
				//console.log(json);
				chartStoreDetailStaff = generateChartStoreForItemStaff(json);
				Ext.getCmp("detailChartStaff").bindStore(chartStoreDetailStaff);
				
			}
		}
		Ext.Ajax.request(requestConfigStaff);
	}
	
	function generateGridColumnStoreForStaff(dateArrStaff){
		var colModel = new Array();
  		colModel[0] = new Ext.grid.CheckboxSelectionModel({handleMouseDown: Ext.emptyFn});
  		colModel[1] = new Ext.grid.RowNumberer();
  		colModel[2] = {header:'指标',dataIndex:'itemName'};
 		colModel[3] = {header:'等级',dataIndex:'itemGrade'};
 		colModel[4] = {header:'父指标',dataIndex:'parentName'};
 		for(var i = 0; i<dateArrStaff.length; i++){  				
 			colModel[i+5] = {header:dateArrStaff[i].split("&")[2],dataIndex:dateArrStaff[i]};
  		}
		var gridCmStaff = new Ext.grid.ColumnModel(colModel);
		return gridCmStaff;
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
})