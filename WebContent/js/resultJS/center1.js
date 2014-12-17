	if(Ext.isChrome===true){       
	    var chromeDatePickerCSS = ".x-date-picker {border-color: #1b376c;background-color:#fff;position: relative;width: 185px;}";
	    Ext.util.CSS.createStyleSheet(chromeDatePickerCSS,'chromeDatePickerStyle');
	}

  	Ext.onReady(function() {
  		//=========================================================
  		var gridStore;//���store
		var gridCm;//���columnModel
		var pSize = 10;
		var dateArr;//�������飬���ڹ���columnModel��
		var cumgrid;//���
		var type = 'month';//month, quarter, year
		var chartStore;//����ͼstore
		var storeChartAll;//��״ͼstore
		var series;//����ͼseries
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
	  				sortAscText : '����',
	  				sortDescText : '����'
	  			},
	  			height:299,
	  			autowidth:500,
	  			colModel:gridCm,
	  			bbar : new Ext.PagingToolbar({
	                    	  pageSize : pSize,
	                    	  store : gridStore,
	                    	  displayInfo : true,
	                    	  displayMsg : "",
	                    	  emptyMsg : 'û�м�¼'
	                      }),
	            remoteSort: true
	  		});
	  		
	  		gridStore.load({params:{start: 0,limit:pSize}});
	  		generateChartStore(json);
	  		generateChartStoreAll(json);
	  		totalChart = new Ext.Panel({
					        iconCls:'chart',
					        title: '�����ۺ���������ͼ',
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
					                displayName: '�÷�',
					                labelRenderer : Ext.util.Format.numberRenderer('0,0')
					            }),
					            tipRenderer : function(chart, record){
					                return record.data.name + ' �����ĵ÷�Ϊ ' + record.data.score + "��";
					            }
					        }
				    	});
	  		detailChart = new Ext.Panel({
					        iconCls:'chart',
					        title: '����һ����������ͼ',
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
  			colModel[2] = {header:'ָ������',dataIndex:'itemName'};
  			colModel[3] = {header:'ָ��ȼ�',dataIndex:'itemGrade'};
  			colModel[4] = {header:'��ָ��',dataIndex:'parentItemName'};
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
			Ext.MessageBox.alert("��ʾ��Ϣ","����д��ʼ-��������");
			return false;
		}
		//alert(from +"  "+to+"  "+type);
		var requestConfig = {
			url : "gyx/centerResult/centerResultAction.action",
			method : 'post',
			params :{'from':from,'to':to,'type':type,'removeList':removeList},
			callback :function(options,success,response) {
				if(response.responseText == "Date error") {
  					Ext.MessageBox.alert("��ʾ��Ϣ","����д��ʼ�ͽ�������");
  					return false;
  				}
  				if(response.responseTest == "inner error"){
  					Ext.MessageBox.alert("��ʾ��Ϣ","����������Ժ�����");
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
		  				sortAscText : '����',
		  				sortDescText : '����'
		  			},
		  			height:300,
		  			autowidth:500,
		  			colModel:gridCm,
		  			bbar : new Ext.PagingToolbar({
		                    	  pageSize : pSize,
		                    	  store : gridStore,
		                    	  displayInfo : true,
		                    	  displayMsg : "",
		                    	  emptyMsg : 'û�м�¼'
		                      }),
		            remoteSort: true
		  		});
		  		gridStore.load({params:{start: 0,limit:pSize}});
		  		Ext.getCmp("chartPanel").destroy();
		  		generateChartStore(json);
		  		generateChartStoreAll(json);
		  		totalChart = new Ext.Panel({
							        iconCls:'chart',
							        title: '�����ۺ���������ͼ',
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
							                displayName: '�÷�',
							                labelRenderer : Ext.util.Format.numberRenderer('0,0')
							            }),
							            tipRenderer : function(chart, record){
							                return record.data.name + ' �����ĵ÷�Ϊ ' + record.data.score + "��";
							            }
							        }
						    	});
		  		detailChart = new Ext.Panel({
			        iconCls:'chart',
			        title: '������ϸ��������ͼ',
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
			Ext.MessageBox.alert("��ʾ��Ϣ","����д��ʼ-��������");
			return false;
		}
		var requestConfig = {
			url : "gyx/centerResult/centerResultAction.action",
			method : 'post',
			params :{'from':from,'to':to,'type':type,'removeList':removeList,'displayLev2':true},
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
  				Ext.getCmp("chartPanel").destroy();
		  		generateChartStore(json);
		  		totalChart = new Ext.Panel({
							        iconCls:'chart',
							        title: '�����ۺ���������ͼ',
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
							                displayName: '�÷�',
							                labelRenderer : Ext.util.Format.numberRenderer('0,0')
							            }),
							            tipRenderer : function(chart, record){
							                return record.data.name + ' �����ĵ÷�Ϊ ' + record.data.score + "��";
							            }
							        }
						    	});
		  		detailChart = new Ext.Panel({
			        iconCls:'chart',
			        title: '����һ��ָ����������ͼ',
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
  			title:'���ļ�Ч�������۽��չʾ',
  			frame:true,
  			labelAlign:'left',
  			id : 'centerForm',
  			bodyPadding:5,
  			layout : 'table',
  			layoutConfig: {columns:12},
  			defaults: { width:120},
  			items : [
  				{xtype : 'label',text : 'ʱ�䷶Χ  ��'},
  				{    
   			 		xtype : 'datefield',
                    fieldLabel : 'ʱ���',
        			name : 'from',
		            id : 'from',
		            invalidText : 'ʱ�������ϸ�ʽ:2000-01-01',
		            format : 'Y-m-d'
                },
                {xtype : 'label',text : '~'	},
  				{
	   				xtype : 'datefield',
	              	fieldLabel : '��',
					name : 'to',
	           	 	id : 'to',
	           	 	invalidText : 'ʱ�������ϸ�ʽ:2000-01-01',
	            	format : 'Y-m-d',
	            },{colspan : 1, text : ' ',width:50},
            		   new Ext.form.Radio({
                		   name : 'mjy',
                		   id : 'rad1',
                		   inputValue : 'month',
                		   boxLabel : '�½��չʾ'
                	   }),new Ext.form.Radio({
                		   name : 'mjy',
                		   id : 'rad2',
                		   inputValue : 'quarter',
                		   boxLabel : '���Ƚ��չʾ'
                	   }),new Ext.form.Radio({
                		   name : 'mjy',
                		   id : 'rad3',
                		   inputValue : 'year',
                		   boxLabel : '����չʾ'
                	   }),{colspan : 1, text : ' ',width : 50},
	            			new Ext.Button({
	            				text : '��ѯ',
	            				id : 'queryButton',
	            				minWidth : 800,
	            				listeners : {
	            					"click" : query
	            				}
	            			}),{colspan : 1, text : ' ',width : 50},
	            			new Ext.Button({
	            				text : '������ϸ��Ϣ',
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
			Ext.MessageBox.alert("��ʾ��Ϣ","����д��ʼ-��������");
			return false;
		}
		//alert(from +"  "+to+"  "+type);
		var requestConfig = {
			url : "gyx/centerResult/centerExport.action",
			method : 'post',
			params :{'from':from,'to':to,'type':type},
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
  			}
	   };
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