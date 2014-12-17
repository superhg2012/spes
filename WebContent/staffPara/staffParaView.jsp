<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
      <title>政务服务中心绩效管理系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="1">    
	<meta http-equiv="keywords1" content="keyword11,keyword21,keyword31">
	<meta http-equiv="description1" content="This is my page1">
	
	<style type="text/css">
		     
	  #pn td {
	    padding : 15px;
	  }
	  #tbar td{
	    padding : 0px;
	  }
	   html, body {
            font: normal 11px verdana;
      }
	   #main-panelSV{
	     position:absolute;left:20px;top : 20px;
	   }
	   #edit-panelSV{
	     display:none;
	   }
	  
	   .editDivSV{
	      cursor: hand;
	   }
	   .tipSV {
	      display:none;/*一开始div隐藏*/
	      padding:10px;
	      width: 400px;
	      text-indent:2em;/*文字缩进2*/
	      font-weight:bold;
	      position:absolute;/*绝对位置*/
	      left:15px;
	      color:D5AE74;
     }
	</style>
	
	<script type="text/javascript">
	     //编辑二级$三级指标信息时候，保存当前一级指标的名称，用于（返回一级指标面板刷新）[核心.4]
	     var plusCurPanelSV = "";
	     //编辑二级$三级指标信息时候，返回当前一级指标，再次进入二级&三级指标编辑，[核心.4]
	     var curPanelIdSV = "";
	     //记录右键菜单，点击所在的行索引
	     var rightContextRowIndexSV = "";
         //一级指标面板的消息提示开关（panel 的tools消息提示）
         var firstPanelTipSwitchSV = true;
         //总消息提示开关
         var totalTipSwitchSV = true;
         //中心Id，中心名称
         var centerIdSV = "";
         var centerNameSV = "";
         //当前选择面板的Id
         var curFirstLevelIdSV = "";
		
		 var framePanelSV = null;
//////////查看一级指标窗口//////////////////////////////////////////////////////////////////
		  var editFirstLevelFormSV = new Ext.form.FormPanel({
             title : "一级指标内容查看",
             layout : "form",
             frame: true,
             labelSeparator : ":",
             labelAlign:'right',
             labelWidth:120,
             bodyStyle :"padding 5 5 5 5",
             width :400,
             height:260,
             url:"staffjson/centerAllPara!updateFirstLevelParameters.action",
             method:"post",
             baseParams:{centerItemId:"0"},
             items:[new  Ext.form.TextField({
             			id: "efnameIdSV",
             			name:"efnameSV",
             			fieldLabel: "<b>指标名称  </b>",
             			width:'200',
             			emptyText:"",
             			readOnly : true,
             			allowBlank:true,
             			blankText:"此字段为必填"
             		}),
             		new  Ext.form.NumberField({
             			id: "efweightIdSV",
             			name:"efweightSV",
             			fieldLabel: "<b>指标权重  </b>",
             			emptyText: "",
             			allowNegative:false,
             			decimalPrecision:2,
             			minValue:0,
             			minText:"最小权重为0",
             			maxValue:10,
             			maxText:"最大为权重为10",
             			width:'200',
             			allowBlank:true,
             			readOnly : true
             		}),
             		new  Ext.form.TextField({ 
             			id: "efgradeIdSV",
             			name:"efgradeSV",
             			fieldLabel: "<b>指标等级<font color='red'>*</font></b>",
             			width:'200',
             			emptyText:"",
             			readOnly:true
             		}),
             		new  Ext.form.TextField({ 
             			id: "efcenterIdSV",
             			name:"efcenterSV",
             			fieldLabel: "<b>所属中心<font color='red'>*</font></b>",
             			width:'200',
             			emptyText:"",
             			readOnly:true
             		}),
             		new Ext.form.RadioGroup({
             		    id : "efanalyseRadioIdSV",
             		    name :"efanalyseRadioSV",
             		    fieldLabel:"<b>指标类型  </b>",
             		    readOnly : true,
             		    itemCls : 'x-check-group-alt',
				        style : "margin-left : 0px;margin-top:2px;",
             		    items:[
             		            new Ext.form.Radio({
             		                id:"efstylesolidIdSV",
             		                name : "efstyleSV",
             		                inputValue:"定量",
             		                boxLabel:"定量",
             		                checkboxToggle:true,
             		                checked:true,
             		                xtype:"fieldset",
             		                style:"margin-top:-5px;",
             		                readOnly : true
             		            }),new Ext.form.Radio({
             		                id:"efstylenosolidIdSV",
             		                name : "efstyleSV",
             		                boxLabel:"定性",
             		                inputValue:'定性',
             		                style:"margin-top:-5px;",
             		                readOnly : true
             		            })
             		          ]
             		}),
             		new Ext.form.RadioGroup({
             		    id : "efustyleRadioIdSV",
             		    name :"efusestyleRadioSV",
             		    fieldLabel:"<b>可用状态 </b>",
             		    readOnly : true,
             		    itemCls : 'x-check-group-alt',
				        style : "margin-left : 0px;margin-top:2px;",
             		    items:[
             		            new Ext.form.Radio({
             		                id:"efusedstyleIdSV",
             		                name : "efusestyleSV",
             		                inputValue:"使用",
             		                boxLabel:"使用",
             		                checkboxToggle:true,
             		                checked:true,
             		                xtype:"fieldset",
             		                style:"margin-top:-5px;",
             		                readOnly : true
             		            }),new Ext.form.Radio({
             		                id:"efnousedstyleIdSV",
             		                name : "efusestyleSV",
             		                boxLabel:"禁用",
             		                inputValue:'禁用',
             		                style:"margin-top:-5px;",
             		                readOnly : true
             		            })
             		          ]
             		}),
                   ]
         });
         var editFirstLevelWinSV = new Ext.Window({
             title : "",
             width :400,
             height:260,
             closeAction:'hide',
             plain: true,
             collapsible:true,
             resizable:false,
             modal:true,
             buttons:[
                       {
                         text:"关闭",
                         handler:function(){
                         
                         	editFirstLevelFormSV.getForm().reset();
                         	editFirstLevelWinSV.hide();
                         	}
                       
                       }
                      ],
             items:[
                    editFirstLevelFormSV
                   ]
         
         });
 //=======二级&&三级指标编辑设置=====================================================================================================================================//
      
        //表格右键菜单
        var rightMenuSV = new Ext.menu.Menu({
           id:"gridContextMenuSV",
           items:[
			       { 
                    id:'rMenu5SV',  
			        text:'返 回',  
			        icon:'js/image/center/back.png',  
			        handler:backToFrontFirstLevelParaSV,
			        listeners:{
			        			activate:rowContextHideFnSV
			                  }
			       }
                  ]
        });
        //编辑窗口的顶部工具栏
        var centetTopBarSV = new  Ext.Toolbar({
            buttons:[
                    
                    { text:"返 回",
                      icon:"js/image/center/back.png",
                      cls:"x-btn-text-icon",
                      handler : backToFrontFirstLevelParaSV
                    }
                  ]
        });
        
        var dataProxySV = new Ext.data.HttpProxy({url:"staffjson/centerSenThirdItems!getSecAndThirdItemsInfo.action"});
        var dataRecorderSV = new Ext.data.Record.create([
        					{name:"sqlId",type:"string",mapping:"itemDBId"},
        					{name:"parentId",type:"string",mapping:"itemParentId"},
        					{name:"formula",type:"string",mapping:"itemFormula"},
        					{name:"formulaId",type:"string",mapping:"itemFormulaId"},
        					{name:"id",type:"string",mapping:"itemId"},
        					{name:"name",type:"string",mapping:"itemName"},
        					{name:"grade",type:"string",mapping:"itemGrade"},
        					{name:"weight",type:"string",mapping:"itemWeight"},
        					{name:"enable",type:"string",mapping:"itemEnable"},
	        				{name:"type",type:"string",mapping:"itemType"},
	        				{name:"center",type:"string",mapping:"itemCenter"}	
                         ]);
		var dataReaderSV = new Ext.data.JsonReader({totalProperty:"totalProperty",root:"root"},dataRecorderSV);
		var columnModelSV = new Ext.grid.ColumnModel([
		                   
		                   {header:"指标序号",align:"center",width:100,dataIndex:"id",tooltip:"指标在数据的序号"},
		                   {
		                     align:"left",
		                     width:200,
		                     header:"指标名称",
		                     dataIndex:"name",
		                     tooltip:"指标名称",
		                    },
		                   {header:"指标等级",align:"center",width:100,dataIndex:"grade",tooltip:"指标等级"},
		                   {
		                     header:"指标权重",
		                     align:"center",
		                     width:100,
		                     dataIndex:"weight",
		                     tooltip:"指标权重"
		                   },
		                   { 
		                     header:"指标可用",
		                     align:"center",
		                     width:100,
		                     dataIndex:"enable",
		                     tooltip:"指标是否可用",
		                   },
		                   {
		                     header:"指标类型",
		                     align:"center",
		                     width:120,
		                     dataIndex:"type",
		                     tooltip:"指标类型",
		                    },
		                   {
		                     align:"center",
		                     width:120,
		                     header:"所属中心",
		                     dataIndex:"center",
		                     tooltip:"指标所属中心",
		                    }
		]);            
		var dataStoreSV = new Ext.data.Store({
							proxy:dataProxySV,
							reader:dataReaderSV,
							autoLoad:true,
							pruneModifiedRecords:true
		});   
		var pagingToolbarSV =   new Ext.PagingToolbar({
								   		store:dataStoreSV,
								   		pageSize:9,
								   		displayInfo:true,
				            			displayMsg:"您选择的是 {0}--{1}记录，共{2}条记录",
				            			emptyMsy:"zhou",
				            			items:[
				            			        "-",
				            			        {
				            			          icon : "js/image/center/star.png",
				            			          cls  : "x-btn-text-icon",
				            			          enableToggle: true,
				            			          text:"隐藏公式",
				            			          pressed: true,
				            			          toggleHandler:function(btn,pressed){
				            			         
				            			                           if(pressed){
				            			                              btn.setText("隐藏公式");
				            			                            }else{
				            			                              btn.setText("显示公式");
				            			                            }
				            			                            var view = editorGridSV.getView();
				            			                            view.showPreview = pressed;
				            			                            view.refresh();
				            			                        }
				            			        }
				            			       ]
								   });
		var editorGridSV = new   Ext.grid.GridPanel({
		         			title:"一级指标内容",
		         			store:dataStoreSV,
		         			cm:columnModelSV,
		         			sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
				            width:850,
				            autoHeight:true,
				            bbar:[
								 pagingToolbarSV
				                 ],
				            tbar:[centetTopBarSV],
				            viewConfig:{
				            		    forceFit      : true,
				            		    enableRowBody : true,
				            		    showPreview   : true,
				            		    getRowClass   : function(record,rowIndex,p,store){
				            		                          
				            		                       if(this.showPreview && record.data.grade == "<b>二级指标</b>"){
				            		                        	//设置RowBody主体，并对公式内容点击后可以进行编辑	
				            		                        	var para = record.data.name+";"+record.data.sqlId+";"+record.data.formula+";"+record.data.formulaId;
				            		                             p.body = '<p title="公式内容" onmouseover=javascript:editFormulaInfoSV() onmouseout=javascript:editFormulaOutSV()  style="cursor:hand;padding:4px;border:1px;margin:2px;padding-left:116px;"><span style="color:#15428B;font-weight:bold;">'+
    				            		                                       '公式：</span>'+record.data.formula+'</p>'
				            		                             return 'x-grid3-row-expanded';
				            		                       }else{
				            		                           return 'x-grid3-row-collapsed';
				            		                       }
				            		                    }
				                       }
		}); 
		
		//二级&三级指标编辑模板       
        var editPanelSV = new Ext.Panel({
        
               id : "editpanelSV",
               layout : "column",
               frame: true,
               items:[editorGridSV]
        });      
        
        //【功能.5】返回一级指标编辑页面
        function backToFrontFirstLevelParaSV(){
                  //显示一级指标面板
                  handleMainPanelDisplaySV();
             
        }
        //【功能.7】公式提示信息 与 解除
        function editFormulaInfoSV(){
           panelTipInfoDisplaySV("二级指标所属公式",'plus');
        }
        function editFormulaOutSV(){
           panelTipInfoHideSV();
        }
       
//======================主面板右键菜单==============================================================================================//        
        
        var mainPanelRightMenuSV = new Ext.menu.Menu({
            id : "mainPanelRMSV",
            items:[
                   { 
                    id:'mainMenu1SV',  
			        text:'刷 新',  
			        icon:'js/image/center/refresh.png',  
			        handler:repaintCurPageSV
			       }
                  ]
        });

//======================================函数功能开始==================================================================================================================================//         
        //【起始处函数】获取当前中心的back1参数，一行显示几个指标参数
	    function initCenterParaRowsSV(){
	      
	        var urlPath = "staffjson/centerParamsRows!getCurCenterBack1Para.action";
				Ext.Ajax.request({
				  url :  urlPath,
				  method : "post",
				  success: function(response,config){
				   
				     var json = Ext.util.JSON.decode(response.responseText);
				     if(json.success == "false"){
				       Ext.MessageBox.alert("出错啦！",json.data);
				       return;
				     }else{
				       var jarray = json.data.split(";");
				       if(jarray.length !=2 ){
				          Ext.MessageBox.alert("出错啦！","分割中心设置参数发生错误！");
				          return;
				       }
				       //赋值即时显示参数jarray[0]显示行数)
				       //创建主面板
				       //初始化提示函数tools-->qtip
				       Ext.QuickTips.init();  
				       framePanelSV = new Ext.Panel({
		   
								        id: "pn",
						                layout:'table',
						                layoutConfig: {columns:jarray[0]},
						                baseCls:'x-plain',
						                defaults : {frame:true,width:230,height:150},
		                           });
		               //主面板渲染
		               framePanelSV.render("main-panelSV");
		               //获取主面板数据
		               getCenterDataSV();
		               //主面板添加右键事件
		               framePanelSV.getEl().on('contextmenu',function(event){
		                  event.stopEvent();
		                  mainPanelRightMenuSV.showAt(event.getXY());
		                   
		                });
				     }//else end
				  }//success end
				 });
	    }
		//获取一级&&二级指标数据	
		 function getCenterDataSV(){
				
				var urlPath = "staffjson/centerAllPara!getFirstSecondParaData.action";
				Ext.Ajax.request({
				  url :  urlPath,
				  method : "post",
				  success: function(response,config){
				  
				     var data = response.responseText;
				     var json = Ext.util.JSON.decode(data);
				     //如果出现错误，出现错误消息并返回
				     if(json.success == "false"){
				       Ext.MessageBox.alert("出现错误啦！",json.data);
				       return;
				     }
				     //主面板清空
				     framePanelSV.removeAll();
				     //获取中心数据
				     centerIdSV = json.centerId;
				     centerNameSV = json.centerName;
				     //获取json的data数据
				     var jdata = json.data;
				     //处理Json数据
				     for(var i =0;i<jdata.length;i++){
				       addParamToMasterPanelSV(jdata[i].title,jdata[i].content,jdata[i].titleId);
				     }
				    
				     //重新布局主面板
				     framePanelSV.doLayout();
				  } //success
				});
		}
		//一级&&二级指标数据添加到主面板中
		function addParamToMasterPanelSV(title,content,titleId){
		    //html 参数，带鼠标点击事件
		    var titleName = title;
		    title +="view";
		    var panelSV = new Ext.Panel({
		         id    : titleName,
		         title : titleName,
		         html  : "<div  name ='centerViewDiv' class='editDivSV' id='"+title+"'>"+content+"</div>", 
		         tools:[
                        {
                         id : "plus",
                         qtip:"查看此指标下二级&三级指标信息",
                         handler : function(event,toolEl,panelSV){
							editPanelParaSV(panelSV);
                         },
                         on:{
                         		     mouseover:function(){
                         		        firstPanelTipSwitchSV = false;
                         				panelTipInfoDisplaySV("查看此指标下二级&三级指标信息",'plus');
                         			 },
                         			 mouseout:function(){
                         			   firstPanelTipSwitchSV = true;
                         			   panelTipInfoHideSV();
                         			 }
                             }
                         
                        }		         
		               ],
		    });
		   
		    //记录当前title的Id信息
		    panelSV.contentId = titleId;
		    //增加："添加面板"
		    framePanelSV.add(panelSV);
		    //主面板重新布局
		    framePanelSV.doLayout();
           //////////////提示信息设置////////////////////
             //采用获取Panel的Element原型的方法，进行事件添加
           panelSV.getEl().on('mouseover',function(){
                panelTipInfoDisplaySV("查看一级指标信息");     
               
		   });
		   panelSV.getEl().on('mouseout',function(){
		        panelTipInfoHideSV();
		   });
            //设置面板中的<div>单击进行事件设置
            Ext.get(title).on('click',function(){
				editCurPanelFirstLevelParaSV(titleId);
           });
		}	
		
		//[核心.1]重新刷新本页面
	    function repaintCurPageSV(){
	       
	       mainPanelRightMenuSV.removeAll();
	       editFirstLevelFormSV.destroy();
	       editFirstLevelWinSV.close();
           var doc = Ext.getCmp('doc-body');
	       doc.getActiveTab().load({url:'staffPara/staffParaView.jsp',scripts:true});
	    }
	  
	
		//[核心.4]查看当前面板的二级指标参数
		function editPanelParaSV(curPanel){
	         handleEditPanelDisplaySV();
	         editorGridSV.setTitle(curPanel.title);
	         //判断当前选择的PanelId 是否与curPanelIdSV 相同。如果相同，就不进行数据读取；如果不同则数据读取
	         if( curPanel.contentId != curPanelIdSV){
	            curPanelIdSV = curPanel.contentId;
	            dataStoreSV.removeAll();
	            dataStoreSV.load({params:{start:0,limit:9,paraId:curPanel.contentId}});   
	         }else{
	            // do nothing
	         }
	         //记录当前一级指标的名称信息（标题信息）
	         plusCurPanelSV = curPanel.title;
		}
		
		//[核心.5] 查看当前面板以及指标参数
		function editCurPanelFirstLevelParaSV(curId){
		  
		  
		   //记录当前Panel的Id
		   curFirstLevelIdSV = curId;
		   //获取当前CurId相关信息
		   Ext.Ajax.request({
		      url: "staffjson/centerAllPara!getFirstLevelParaById.action",
		      method:"post",
		      params:{centerItemId:curId},
		      success:function(response,config){
		      
		        var  json = Ext.util.JSON.decode(response.responseText);
		        if(json.success == "false"){
		        	Ext.Msg.alert("出错啦!","获取Id为"+curId+"的指标数据发生错误！");
		        	return;
		        }
		        //设置一级指标编辑窗口的相关
		        Ext.getCmp("efnameIdSV").setValue(json.name);
		        Ext.getCmp("efweightIdSV").setValue(json.weight);
		        Ext.getCmp("efcenterIdSV").setValue(json.center);
		        Ext.getCmp("efgradeIdSV").setValue("一级指标");;
		  
		        if(json.cstyle == "定量"){
		           Ext.getCmp("efstylesolidIdSV").setValue(true);
		           Ext.getCmp("efstylenosolidIdSV").setValue(false);
		        }else{
		        
		           Ext.getCmp("efstylesolidIdSV").setValue(false);
		           Ext.getCmp("efstylenosolidIdSV").setValue(true);
		        }
		        if(json.estyle == "true"){
		           Ext.getCmp("efusedstyleIdSV").setValue(true);
		           Ext.getCmp("efnousedstyleIdSV").setValue(false);
		        }else{
		           Ext.getCmp("efusedstyleIdSV").setValue(false);
		           Ext.getCmp("efnousedstyleIdSV").setValue(true);
		        }
		        //【重要】需要清空内容后显示-
		       
		        editFirstLevelWinSV.show();
		      }
		   });	
		}
		
		//panel提示信息的开启
		function panelTipInfoDisplaySV(text,disType){
            
            //总消息显示开关
            if(totalTipSwitchSV){
	             //消息开关 打开才可以显示信息(控制总Panel的提示信息)
	             if(firstPanelTipSwitchSV){
	               document.getElementById('tipSV').innerHTML = text;
	               Ext.get('tipSV').show();
	             }
	             //tools 的plus delete提示信息
	             else if('plus' == disType || 'delete' == disType){
	               document.getElementById('tipSV').innerHTML = text;
	               Ext.get('tipSV').show();
	             }
	             
	             //控制删除、添加成功后显示提示
	             if(disType == "addDelete"){
	                 //关闭总消息显示开关
	                 totalTipSwitchSV = false;
	                 document.getElementById('tipSV').innerHTML = text;
	                 Ext.get('tipSV').fadeIn("r",{duration:5});
	                 window.setTimeout(function(){
	                       totalTipSwitchSV = true;
	                       panelTipInfoHideSV();
	                 },5000);
	             }
             }//if end
             else{
             	// do nothing
             }
		}
		//panel提示信息的关闭
		function panelTipInfoHideSV(){
		     if(totalTipSwitchSV){
		     	Ext.get('tipSV').hide();
		     }
		}
		
///////////editorGridSVPanel---Start/////////////////
		//【1】表格的右键点击选择事件
		function rowContextFnSV(grid,rowIndex,e){
		    
		    e.stopEvent();
		    rightMenuSV.showAt(e.getXY());
		    //记录当前的行
		    rightContextRowIndexSV = rowIndex;
		    //清除所有选择行
		    rowContextHideFnSV();
		}
	   
		//【3】右键菜单消失后，清除行选择
		function rowContextHideFnSV(){
		    var sm = editorGridSV.getSelectionModel();
		    for(var i = 0; i< editorGridSV.getView().getRows().length;i++){
               sm.deselectRow(i);              
            }
		}
	
///////////editorGridSVPanel---End////////////////////
		
		//处理主面板模块的显示&&隐藏
		function handleMainPanelDisplaySV(){
		    
		    //编辑面板清空
		   // editPanel.removeAll();
		    Ext.get("main-panelSV").setVisible(true);
		    Ext.get("edit-panelSV").setVisible(false);
		}
		//处理编辑面板模块的显示&&隐藏
		function handleEditPanelDisplaySV(){
		    
		    //主面板清空
			//framePanelSV.removeAll();
		    Ext.get("main-panelSV").setVisible(false);
		    Ext.get("edit-panelSV").setVisible(true);
		}
	  
		Ext.onReady(function(){

		     editPanelSV.render("edit-panelSV");
		     initCenterParaRowsSV();
		     //设置表格编辑相关事件
		     editorGridSV.addListener('rowcontextmenu',rowContextFnSV);
		     rightMenuSV.addListener('hide',rowContextHideFnSV);
			 //设置一级指标编辑关闭前事件		     
		     var doc = Ext.getCmp('doc-body').getActiveTab();
		     doc.addListener('beforeclose',function(){
		      		editFirstLevelFormSV.destroy();
	          		editFirstLevelWinSV.close();
		     });
		     
		});
	
	</script>
	
  </head>
      
  <body>
  		<div id='tipSV' class='tipSV'>zhoushaojun</div>
		<div id="main-panelSV"></div>
		<div id="edit-panelSV"></div>
		
  </body>
</html>
