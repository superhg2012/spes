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
	<meta http-equiv="expires" content="2">    
	<meta http-equiv="keywords" content="keyword12,keyword22,keyword32">
	<meta http-equiv="description" content="This is my page2">
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
	   .formulaAddS{
	      background-image : url(js/image/center/formulaAdd.png)!important;
	   }
	   .formulaMinusS{
	      background-image : url(js/image/center/formulaMinus.png)!important;
	   }
	   .formulaDivideS{
	      background-image : url(js/image/center/formulaDiv.png)!important;
	   }
	   .formulaMultifyS{
	      background-image : url(js/image/center/formulaMulti.png)!important;
	   }
	   .formulaContainS{
	      background-image : url(js/image/center/formulaContain.png)!important;
	   }
	   .formulaLeftS{
	     background-image : url(js/image/center/formulaLeft.png)!important;
	   }
	   .formulaRightS{
	     background-image : url(js/image/center/formulaRight.png)!important;
	   }
	   .formulaBackS{
	     background-image : url(js/image/center/backspace.png)!important;
	   }
	   .formulaEditS{
	     background-image : url(js/image/center/formulaEdit.png)!important;
	   }
	   
	   #main-panelS{
	     position:absolute;left:20px;top : 20px;
	   }
	   #edit-panelS{
	     display:none;
	   }
	   #addimageCenterS{
	      position:absolute;left:70px;top : 18px;cursor:hand;
	   }
	   .editDivS{
	      cursor: hand;
	   }
	   .tipS {
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
	     var plusCurPanelS = "";
	     //编辑二级$三级指标信息时候，返回当前一级指标，再次进入二级&三级指标编辑，[核心.4]
	     var curPanelIdS = "";
	     //记录右键菜单，点击所在的行索引
	     var rightContextRowIndexS = "";
         //即时显示参数
         var instanceDisplayS = false;
         var curRowsS =  "";
         //公式保存内容
		 var formulaContentS = "";
		 var formulaArrayS = [];
		 //公式编辑参数开关（所有参数不能连续两次使用）
		 //内容编辑参数开关（内容不能连续两次使用）
		 var symbolSwitchS = true;
         var contentSwitchS = true;		
         var leftContainS = 0;
         var originalFormulaS = "";
         var originalFormulaIdS = "";
         var curFormulaParentIdS = "";
         //一级指标面板的消息提示开关（panel 的tools消息提示）
         var firstPanelTipSwitchS = true;
         //总消息提示开关
         var totalTipSwitchS = true;
         //中心Id，中心名称
         var centerIdS = "";
         var centerNameS = "";
         var windowNameS = "";
         //当前选择面板的Id
         var curFirstLevelIdS = "";
         
         
		 var framePanelS = null;
       
         var addPanelS = new Ext.Panel({
               
               id:"addPanelS",
               title:"添加一级指标",
               html: "<img id='addimageCenterS' title='单击，新添加一级指标信息！' src='js/image/center/addPara.png'  onclick='addParaImageS()' onmouseover='imagemouseroverS()' onmouseout='imagemouseoutS()'/>",
               tools:[
                      {
                        id:'refresh',
                        qtip:'刷新全部一级指标',
                        handler:function(event,toolEl,panel){
		                     repaintCurPageS();
		                },
                         on:{
                         		     mouseover:function(){
                         				panelTipInfoDisplayS("刷新全部一级指标信息");
                         			 },
                         			 mouseout:function(){
                         			   panelTipInfoHideS();
                         			 }
                             }
                      },
                      {
                        id:"gear",
                        qtip:'设置显示模式',
		                handler:function(event,toolEl,panel){
		                    gearDisplayParamsS();
		                },
		                on:{
                         		     mouseover:function(){
                         				panelTipInfoDisplayS("设置显示模式");
                         			 },
                         			 mouseout:function(){
                         			   panelTipInfoHideS();
                         			 }
                             }
		                 
		              }
                    ]
         });
         function imagemouseroverS(){
         
           panelTipInfoDisplayS("添加一级指标信息"); 
         }
         function imagemouseoutS(){
           panelTipInfoHideS();
         }
/////////添加新指标窗口(start)///////////////////////////////////////////////////
         var inputFormS = new Ext.form.FormPanel({
         
             title : "指标输入",
             layout : "form",
             frame: true,
             labelSeparator : ":",
             labelAlign:'right',
             labelWidth:120,
             bodyStyle :"padding 5 5 5 5",
             width :400,
             height:280,
             url:"staffjson/centerAllPara!storeFirstLevelParaData.action",
             method:"post",
             items:[
             		new  Ext.form.TextField({
             			id: "pnameInputIdS",
             			name:"pnameInputS",
             			fieldLabel: "<b>指标名称  </b>",
             			width:'200',
             			allowBlank:false,
             			blankText:"此字段为必填"
             		}),
             		new  Ext.form.NumberField({
             			id: "pweightInputIdS",
             			name:"pweightInputS",
             			fieldLabel: "<b>指标权重  </b>",
             			allowNegative:false,
             			decimalPrecision:2,
             			maxValue:10,
             			maxText:"最大权重为10",
             			minValue:0,
             			minText:"最小权重为0",
             			blankText:"此字段为必填",
             			width:'200',
             			allowBlank:false
             		}),
             		new  Ext.form.TextField({ 
             			id: "pgradeInputIdS",
             			name:"pgradeInputS",
             			fieldLabel: "<b>指标等级<font color='red'>*</font></b>",
             			width:'200',
             			value:"一级指标",
             			readOnly:true
             		}),
             		new  Ext.form.TextField({ 
             			id: "pcenterInputIdS",
             			name:"pcenterInputS",
             			fieldLabel: "<b>所属中心<font color='red'>*</font></b>",
             			width:'200',
             			emptyText:"",
             			readOnly:true
             		}),
             		new  Ext.form.TextField({ 
             			id: "pwindowInputIdS",
             			name:"pwindowInputS",
             			fieldLabel: "<b>所属窗口<font color='red'>*</font></b>",
             			width:'200',
             			emptyText:"",
             			readOnly:true
             		}),
             		new Ext.form.RadioGroup({
             		    id : "panalyseInputIdS",
             		    name :"panalyseInputS",
             		    fieldLabel:"<b>指标类型  </b>",
             		    itemCls : 'x-check-group-alt',
				        style : "margin-left : 0px;margin-top:10px;",
             		    items:[
             		            new Ext.form.Radio({
             		                name : "pstyleInputS",
             		                inputValue:"定量",
             		                boxLabel:"定量",
             		                checkboxToggle:true,
             		                checked:true,
             		                xtype:"fieldset",
             		                style:"margin-top:-5px;"
             		            }),new Ext.form.Radio({
             		                name : "pstyleInputS",
             		                boxLabel:"定性",
             		                inputValue:'定性',
             		                style:"margin-top:-5px;"
             		            })
             		          ]
             		})
                   ]
         });
         var inputWinS = new Ext.Window({
         
             title : "",
             width :400,
             height:280,
             closeAction:'hide',
             plain: true,
             collapsible:true,
             resizable:false,
             modal:true,
             buttons:[
                       {text:"确定",
                        handler:function(){
                        
                            
                           inputFormS.getForm().submit({
                           
                              success: function(curForm,responseText){
                                
                                
								//添加一级指标后，判断当前模式是否为即时刷新，分别执行
								if(instanceDisplayS == "true"){
								   inputWinS.close();
								   //刷新页面
								   repaintCurPageS();
								}else{
								   inputWinS.hide();
								   var jdata = responseText.result.data.split(";");
								   if(jdata.length != 3)return;
								   addParamToMasterPanelS(jdata[0],jdata[1],jdata[2]);
								   //刷新主面板
				     			  framePanelS.doLayout();
								}
								
								//消息提示
								panelTipInfoDisplayS('添加一级指标成功','addDelete');
								
                              },
                              failure: function(curForm,responseText){
                               
                               //表格填写问题，在此提示
                               if(responseText.result == undefined){
                                	Ext.MessageBox.alert("出错啦！","请检查是否漏填表格，\n或者表格格式错误？\n");
                                }else{
                                    Ext.MessageBox.alert("出错啦！4",responseText.result.data);
                                }
                              }
                           });
                       }},
                       {
                         text:"取消",
                         handler:function(){inputWinS.hide();}
                       
                       }
                      ],
             items:[
                    inputFormS
                   ]
         
         });
/////////////设置显示方式窗口(start)///////////////////////////////////////////////////
         var gearDisplayFormS = new Ext.form.FormPanel({
         
             title : "显示参数设置",
             layout : "form",
             frame: true,
             labelSeparator : ":",
             labelAlign:'right',
             labelWidth:120,
             bodyStyle :"padding 5 5 5 5",
             width :400,
             height:160,
             url:"staffjson/centerParamsRows!storeCurCenterBack1Para.action",
             method:"post",
             items:[
             		new  Ext.form.NumberField({
             			id: "displayRowsNumS",
             			name:"displayRowsS",
             			fieldLabel: "<b>显示行数</b>",
             			emptyText: "",
             			allowNegative:false,
             			decimalPrecision:0,
             			minValue:2,
             			minText:"最小为每行2个指标",
             			maxValue:10,
             			maxText:"最大为每行10个指标",
             			width:'200',
             			allowBlank:true
             		}),
             		new Ext.form.RadioGroup({
             		    id : "pcheckboxS",
             		    name :"pcheckboxS",
             		    fieldLabel:"<b>即时刷新</b>",
             		    itemCls : 'x-check-group-alt',
				        style : "margin-left : 0px;margin-top:10px;",
             		    items:[
             		            new Ext.form.Radio({
             		                id:"solidanalyseS",
             		                name : "instanceRepaintS",
             		                inputValue:"true",
             		                boxLabel:"即时",
             		                checkboxToggle:true,
             		                xtype:"fieldset",
             		                style:"margin-top:-5px;"
             		            }),new Ext.form.Radio({
             		            	id:"nosolidanalyseS",
             		                name : "instanceRepaintS",
             		                boxLabel:"非即时",
             		                checked:true,
             		                inputValue:'false',
             		                style:"margin-top:-5px;"
             		            })
             		          ]
             		})
                   ]
         });
         var gearDisplayWinS = new Ext.Window({
         
             title : "",
             width :400,
             height:160,
             closeAction:'hide',
             plain: true,
             collapsible:true,
             resizable:false,
             modal:true,
             buttons:[
                       {text:"确定",
                        handler:function(){
                              gearDisplayFormS.getForm().submit({
                           
                              success: function(curForm,responseText){
                                
                                //获取结果，如果行显示数目不变则不刷新页面；其余情况刷新
                                //如果为“norepaint;true” 需要instanceDisplayS重新赋值
                                if(responseText.result.data.split(";").length == 2){
                                   if(responseText.result.data.split(";")[0] == "norepaint"){
										
										instanceDisplayS = responseText.result.data.split(";")[1];
										//设置参数窗口关闭
                                		gearDisplayWinS.hide();
										return;
                                   }
                                }
                                //设置参数窗口关闭
                                gearDisplayWinS.close();
								//刷新页面
								repaintCurPageS();
                              },
                              failure: function(curForm,responseText){
                               //表格填写问题，在此提示
                               if(responseText.result == undefined){
                                	Ext.MessageBox.alert("出错啦！","请检查是否漏填表格，\n或者表格格式错误？\n");
                                }else{
                                    Ext.MessageBox.alert("出错啦！",responseText.result.data);
                                }
                              }
                           });
                       
                       }},
                       {
                         text:"取消",
                         handler:function(){gearDisplayWinS.hide();}
                       }
                      ],
             items:[
                   gearDisplayFormS
                   ]
         
         });
//////////编辑一级指标窗口//////////////////////////////////////////////////////////////////
		  var editFirstLevelFormS = new Ext.form.FormPanel({
             title : "一级指标编辑",
             layout : "form",
             frame: true,
             labelSeparator : ":",
             labelAlign:'right',
             labelWidth:120,
             bodyStyle :"padding 5 5 5 5",
             width :400,
             height:300,
             url:"staffjson/centerAllPara!updateFirstLevelParameters.action",
             method:"post",
             baseParams:{centerItemId:"0"},
             items:[
             		new  Ext.form.TextField({
             			id: "efnameIdS",
             			name:"efnameS",
             			fieldLabel: "<b>指标名称  </b>",
             			width:'200',
             			emptyText:"",
             			allowBlank:true,
             			blankText:"此字段为必填"
             		}),
             		new  Ext.form.NumberField({
             			id: "efweightIdS",
             			name:"efweightS",
             			fieldLabel: "<b>指标权重  </b>",
             			emptyText: "",
             			allowNegative:false,
             			decimalPrecision:2,
             			minValue:0,
             			minText:"最小权重为0",
             			maxValue:10,
             			maxText:"最大为权重为10",
             			width:'200',
             			allowBlank:true
             		}),
             		new  Ext.form.TextField({ 
             			id: "efgradeIdS",
             			name:"efgradeS",
             			fieldLabel: "<b>指标等级<font color='red'>*</font></b>",
             			width:'200',
             			emptyText:"",
             			readOnly:true
             		}),
             		new  Ext.form.TextField({ 
             			id: "efcenterIdS",
             			name:"efcenterS",
             			fieldLabel: "<b>所属中心<font color='red'>*</font></b>",
             			width:'200',
             			emptyText:"",
             			readOnly:true
             		}),
             		new  Ext.form.TextField({ 
             			id: "efwindowIdS",
             			name:"efwindowS",
             			fieldLabel: "<b>所属窗口<font color='red'>*</font></b>",
             			width:'200',
             			emptyText:"",
             			readOnly:true
             		}),
             		new Ext.form.RadioGroup({
             		    id : "efanalyseRadioIdS",
             		    name :"efanalyseRadioS",
             		    fieldLabel:"<b>指标类型  </b>",
             		    itemCls : 'x-check-group-alt',
				        style : "margin-left : 0px;margin-top:2px;",
             		    items:[
             		            new Ext.form.Radio({
             		                id:"efstylesolidIdS",
             		                name : "efstyleS",
             		                inputValue:"定量",
             		                boxLabel:"定量",
             		                checkboxToggle:true,
             		                checked:true,
             		                xtype:"fieldset",
             		                style:"margin-top:-5px;"
             		            }),new Ext.form.Radio({
             		                id:"efstylenosolidIdS",
             		                name : "efstyleS",
             		                boxLabel:"定性",
             		                inputValue:'定性',
             		                style:"margin-top:-5px;"
             		            })
             		          ]
             		}),
             		new Ext.form.RadioGroup({
             		    id : "efustyleRadioIdS",
             		    name :"efusestyleRadioS",
             		    fieldLabel:"<b>可用状态 </b>",
             		    itemCls : 'x-check-group-alt',
				        style : "margin-left : 0px;margin-top:2px;",
             		    items:[
             		            new Ext.form.Radio({
             		                id:"efusedstyleIdS",
             		                name : "efusestyleS",
             		                inputValue:"使用",
             		                boxLabel:"使用",
             		                checkboxToggle:true,
             		                checked:true,
             		                xtype:"fieldset",
             		                style:"margin-top:-5px;"
             		            }),new Ext.form.Radio({
             		                id:"efnousedstyleIdS",
             		                name : "efusestyleS",
             		                boxLabel:"禁用",
             		                inputValue:'禁用',
             		                style:"margin-top:-5px;"
             		            })
             		          ]
             		}),
                   ]
         });
         var editFirstLevelWinS = new Ext.Window({
         	
             title : "",
             width :400,
             height:300,
             closeAction:'hide',
             plain: true,
             collapsible:true,
             resizable:false,
             modal:true,
             buttons:[
                       {text:"确定",
                        handler:function(){
                           //动态更改baseParams参数
             			   editFirstLevelFormS.baseParams.centerItemId = curFirstLevelIdS;
                           editFirstLevelFormS.getForm().submit({
                           
                              success: function(curForm,responseText){
									
									var jsonTitle =responseText.result.title;
									//一级指标内容更改,则panel的Id为数据库Id，一直不变
									    if(jsonTitle.alter == "true"){
									       Ext.getCmp(curFirstLevelIdS).setTitle(jsonTitle.newTitle);
									    }
									    
									    //关闭窗口
									    editFirstLevelWinS.hide();
									    //更新panel的内容
									    updatePanelContentByTitleS(curFirstLevelIdS,"false");
									    //消息提示
									    panelTipInfoDisplayS("更新一级指标信息成功！",'addDelete');
															
                              },
                              failure: function(curForm,responseText){
                               
                               //表格填写问题，在此提示
                               if(responseText.result == undefined){
                                	Ext.MessageBox.alert("出错啦！","请检查是否漏填表格，\n或者表格格式错误？\n");
                                }else{
                                    Ext.MessageBox.alert("出错啦！",responseText.result.data);
                                }
                              }
                           });
                       }},
                       {
                         text:"取消",
                         handler:function(){
                         
                         	editFirstLevelWinS.hide();
                         	editFirstLevelFormS.getForm().reset();
                         	}
                       
                       }
                      ],
             items:[
                    editFirstLevelFormS
                   ]
         
         });
 //=======二级&&三级指标编辑设置=====================================================================================================================================//
      
        //表格右键菜单
        var rightMenuS = new Ext.menu.Menu({
           id:"gridContextMenuS",
           items:[
                   { 
                    id:'rMenu1S',  
			        text:'保 存',  
			        icon:'js/image/center/save.png',  
			        handler:storeEditParametersS,
			        listeners:{
			        			activate:rowContextHideFnS
			                  }
			       },
			        { 
                    id:'rMenu2S',  
			        text:'添加二级指标',  
			        icon:'js/image/center/plus.png',  
			        handler:addSecondParaRecordS,
			        listeners:{
			        			activate : rowContextHideFnS
			                  }
			       },
			       { 
                    id:'rMenu3S',  
			        text:'添加三级指标',  
			        icon:'js/image/center/add.png',  
			        handler:addThirdParaRecordS,
			        listeners:{
			        			activate : rowSelectionOtherS
			                  }
			       },
			       { 
                    id:'rMenu4S',  
			        text:'删 除',  
			        icon:'js/image/center/trash.png',  
			        handler:deleteParaInfoS,
			        listeners:{
			        			activate : rowSelectionOtherS
			                  }
			       },
			       { 
                    id:'rMenu5S',  
			        text:'返 回',  
			        icon:'js/image/center/back.png',  
			        handler:backToFrontFirstLevelParaS,
			        listeners:{
			        			activate:rowContextHideFnS
			                  }
			       }
           ]
        });
        //使用，禁用的comobox
        var enableTextS = [['使用','使用'],['禁用','禁用']];
        //定性，定量的comobox
        var styleTextS = [['定量','定量'],['定性','定性']];
        //编辑窗口的顶部工具栏
        var centetTopBarS = new  Ext.Toolbar({
            buttons:[
                    {
                      text:"保 存",
                      icon:"js/image/center/save.png",
                      cls:"x-btn-text-icon",
                      handler : storeEditParametersS
                    },
                    {
                      xtype:"tbseparator"   
                    },
                    { text:"添加二级",
                      icon:"js/image/center/plus.png",
                      cls:"x-btn-text-icon",
                      handler:addSecondParaRecordS
                    },
                    {
                      xtype:"tbseparator"   
                    },
                    {
                      text : "添加三级",
                      icon:"js/image/center/add.png",
                      cls:"x-btn-text-icon",
                      handler:addThirdParaRecordS
                    },
                    {
                      xtype:"tbseparator"   
                    },
                    { text:"删 除",
                      icon:"js/image/center/trash.png",
                      cls:"x-btn-text-icon",
                      handler:deleteParaInfoS
                    },
                    {
                      xtype:"tbseparator"   
                    },
                    { text:"返 回",
                      icon:"js/image/center/back.png",
                      cls:"x-btn-text-icon",
                      handler : backToFrontFirstLevelParaS
                    }
                  ]
        });
        
        var dataProxyS = new Ext.data.HttpProxy({url:"staffjson/centerSenThirdItems!getSecAndThirdItemsInfo.action"});
        var dataRecorderS = new Ext.data.Record.create([
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
	        				{name:"center",type:"string",mapping:"itemCenter"},
	        				{name:"window",type:"string",mapping:"itemWindow"},	
                         ]);
		var dataReaderS = new Ext.data.JsonReader({totalProperty:"totalProperty",root:"root"},dataRecorderS);
		var columnModelS = new Ext.grid.ColumnModel([
		                   
		                   {header:"指标序号",align:"center",width:100,dataIndex:"id",tooltip:"指标在数据的序号"},
		                   {
		                     align:"left",
		                     width:200,
		                     header:"指标名称",
		                     dataIndex:"name",
		                     tooltip:"指标名称",
		                     editor: new Ext.grid.GridEditor(new Ext.form.TextField({
		                     									allowBlank:false,
		                     									blankText:"此字段为必填"
		                                                     }))
		                    },
		                   {header:"指标等级",align:"center",width:100,dataIndex:"grade",tooltip:"指标等级"},
		                   {
		                     header:"指标权重",
		                     align:"center",
		                     width:100,
		                     dataIndex:"weight",
		                     tooltip:"指标权重",
		                     editor: new Ext.grid.GridEditor(new  Ext.form.NumberField({
             			                                                allowNegative:false,
												             			decimalPrecision:2,
												             			minValue:0,
												             			minText:"最小权重为0",
												             			maxValue:10,
												             			maxText:"最大权重为10",
												             			allowBlank:false,
												             			nanText:"请输入数字",
												             			blankText:"此字段为必填"
             		                                                    }))
		                    },
		                   { 
		                     header:"指标可用",
		                     align:"center",
		                     width:100,
		                     dataIndex:"enable",
		                     tooltip:"指标是否可用",
		                     editor: new Ext.grid.GridEditor(new Ext.form.ComboBox({
		                     														store: new Ext.data.SimpleStore({
		                     														       fields:['value','text'],
		                     														       data:enableTextS
		                     														    }),
		                     														displayField:'text',
		                     														valueField:'value',
		                     														mode: "local",
																	                readOnly: true,
																	                triggerAction:"all"
		                                                                            }))
		                   },
		                   {
		                     header:"指标类型",
		                     align:"center",
		                     width:120,
		                     dataIndex:"type",
		                     tooltip:"指标类型",
		                     editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
		                     														store: new Ext.data.SimpleStore({
		                     														       fields:['value','text'],
		                     														       data:styleTextS
		                     														    }),
		                     														displayField:'text',
		                     														valueField:'value',
		                     														mode: "local",
																	                readOnly: true,
																	                triggerAction:"all"
		                                                                            }))
		                    },
		                   {
		                     align:"center",
		                     width:120,
		                     header:"所属中心",
		                     dataIndex:"center",
		                     tooltip:"指标所属中心"
		                    }, 
		                    {
		                     align:"window",
		                     width:120,
		                     header:"所属窗口",
		                     dataIndex:"window",
		                     tooltip:"指标所属窗口"
		                    }
		]);            
		var dataStoreS = new Ext.data.Store({
							proxy:dataProxyS,
							reader:dataReaderS,
							autoLoad:true,
							pruneModifiedRecords:true
		});   
		var pagingToolbarS =   new Ext.PagingToolbar({
								   		store:dataStoreS,
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
				            			                            var view = editorGridS.getView();
				            			                            view.showPreview = pressed;
				            			                            view.refresh();
				            			                        }
				            			        }
				            			       ]
								   });
		var editorGridS = new   Ext.grid.EditorGridPanel({
		         			title:"一级指标内容",
		         			store:dataStoreS,
		         			cm:columnModelS,
		         			sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
				            clicksToEdit: '1',
				            width:'100%',
				            autoHeight:true,
				            bbar:[
								 pagingToolbarS
				                 ],
				            tbar:[centetTopBarS],
				            viewConfig:{
				            		    forceFit      : true,
				            		    enableRowBody : true,
				            		    showPreview   : true,
				            		    getRowClass   : function(record,rowIndex,p,store){
				            		                          
				            		                       if(this.showPreview && record.data.grade == "<b>二级指标</b>"){
				            		                        	//设置RowBody主体，并对公式内容点击后可以进行编辑	
				            		                        	var para = record.data.name+";"+record.data.sqlId+";"+record.data.formula+";"+record.data.formulaId;
				            		                             p.body = '<p title="点击编辑公式" onmouseover=javascript:editFormulaInfoS() onmouseout=javascript:editFormulaOutS() onclick=javascript:edtiFormulasWinS("'+para+'") style="cursor:hand;padding:4px;border:1px;margin:2px;padding-left:116px;"><span style="color:#15428B;font-weight:bold;">'+
    				            		                                       '公式：</span>'+record.data.formula+'</p>'
				            		                             return 'x-grid3-row-expanded';
				            		                       }else{
				            		                           return 'x-grid3-row-collapsed';
				            		                       }
				            		                    }
				                       }
		}); 
		
		//二级&三级指标编辑模板       
        var editPanelS = new Ext.Panel({
        
               id : "editPanelS",
               layout : "column",
               frame: true,
               items:[editorGridS]
        });      
        //【功能.1】保存指标信息函数
        function storeEditParametersS(){
            var data = editorGridS.getStore().modified.slice(0);
            if(0 == data.length){
                  Ext.Msg.alert("请编辑内容后保存！","您未进行任何修改，无法保存！");
                  return;
             }		   
            Ext.Msg.confirm("保存确认窗口","确认保存表格中修改的红色箭头标记的内容？",function(btn){
            if( "yes" == btn){
                        				
                  var resultData = editorGridS.getStore().modified.slice(0);
                  var jsonArray = [];
                  var resultInner  = false;
				  Ext.each(resultData,function(m){
				        //判断【功能.2】中增加的新行数据是否，有为空现象。如果用户填写，则会符合ExtJs的相关规则;
				        //如果未填写直接保存会出现“空”现象
				        if(m.data.name == "" || m.data.weight == "0--10" || m.data.type == ""){
				           Ext.Msg.alert("新增加行记录存在空字段！","请将空字段重新填写后保存！");
					       //消息提示
						   panelTipInfoDisplayS("[新增加行记录存在空字段]"+"存在空字段","addDelete");
				           resultInner = true;
				           return;
				        }
						jsonArray.push(m.data);
				  });
				  //在此进行判断，如果存在空字段，不继续执行
				  if(resultInner)return;
				  //发送内容到后台
                  Ext.Ajax.request({
	                        					
	                 method:'post',
	                 url:"staffjson/centerItemEditor!storeEditorSencondOrThirdParameter.action",
	                 params: {data:Ext.encode(jsonArray)},
	                 success:function(response,config){
														
							var json = Ext.decode(response.responseText);
							if("true" == json.success){
								//获取当前的页面位置与页面容量
								var curCursor = pagingToolbarS.cursor;
								var curPageSize = pagingToolbarS.pageSize;
								dataStoreS.load({params:{start:curCursor,limit:curPageSize}});
								//(另外一种方法) pagingToolbarC.doLoad(pagingToolbarC.cursor);
								
								//消息提示
								panelTipInfoDisplayS("数据保存成功","addDelete");
								}
							else if("false" == json.success){
							    Ext.Msg.alert("错误!",json.info);
							   // pagingToolbarC.doLoad(pagingToolbarC.cursor);
							    return;
							}		                        					
	                      }//success end
                        });
                }// if end
               });
        }  
        //【功能.2】 添加二级指标记录
        function addSecondParaRecordS(){
             //判断当前二级指标的PanelId 是否为空
             if( curPanelIdS == "" || null == curPanelIdS){
               Ext.Msg.alert("错误！","获取二级指标父类的Id，发生错误！");
               return;
             }
           
             var initValue = {
            					sqlId    : "+1",
            					parentId : curPanelIdS,
            					id       : "+1",
            					name     : "",
            					grade    : "二级指标",
            					weight   : "0--10",
            					enable   : "使用",
            					type     : "定量",
            					center   : centerNameS,
            					window   : windowNameS
                             };
            var myrecord = new dataRecorderS(initValue);
            //表格停止编辑
            editorGridS.stopEditing();
            //将此条记录加入到表格中
            editorGridS.getStore().add(myrecord);
            
            myrecord.dirty = true;
            myrecord.modified = initValue;
            editorGridS.getStore().modified.push(myrecord);
            //获取当前添加的行索引
            var curIndex = editorGridS.getStore().indexOf(myrecord);
            editorGridS.startEditing(curIndex,1);
            
        }
        
        //【功能.3】 添加新的三级指标记录
        function addThirdParaRecordS(){
            
            //[0]判断是否选取指标信息
            var sm = editorGridS.getSelectionModel();
            if(!sm.hasSelection()){
                Ext.Msg.alert("未选择行记录！","请选择一行记录后添加三级指标！");
                return;
            }       
            var record = sm.getSelected();
	        //[1]判断当前选择的Record是否为 未保存 状态
	        if(record.data.sqlId == "+1" || record.data.sqlId == ""){
	          Ext.Msg.alert("违规操作！","无法在未保存的指标信息下添加！");
	          return;
	        } 
            var parentIdInner = "";
            var sqlIdInner = "";
            if(record.data.grade == "<b>二级指标</b>"){
                  parentIdInner = record.data.sqlId;  
                  sqlIdInner = "+1";    
            }else if(record.data.grade  == "三级指标"){
                  parentIdInner = record.data.parentId;
                  sqlIdInner = "";
            }
            var initValue = {
            					sqlId    : "+1",
            					parentId : parentIdInner,
            					id       : sqlIdInner,
            					name     : "",
            					grade    : "三级指标",
            					weight   : "0--10",
            					enable   : "使用",
            					type     : "定量",
            					center   : record.data.center,
            					window   : windowNameS
                             };
            var myrecord = new dataRecorderS(initValue);
           
            //表格停止编辑
            editorGridS.stopEditing();
            //将此条记录加入到表格中
            var index = editorGridS.getStore().indexOf(record);
            editorGridS.getStore().insert(index+1,myrecord);
            
            myrecord.dirty = true;
            myrecord.modified = initValue;
            editorGridS.getStore().modified.push(myrecord);
            //初始化编辑
            editorGridS.startEditing(index+1,1);
            
        }
        //【功能.4】删除指标信息（二级&三级）
        function deleteParaInfoS(){
            //[0]判断是否选取指标信息
            var sm = editorGridS.getSelectionModel();
            if(!sm.hasSelection()){
                Ext.Msg.alert("未选择行记录！","请选择一行记录后删除！");
                return;
            }
            var record = sm.getSelected();
            //[1]判断此条记录是否保存
            if(record.data.sqlId == "+1"){
               editorGridS.getStore().remove(record);
               //消息提示
			   panelTipInfoDisplayS("删除未保存的指标信息成功！","addDelete");
            }else{
               //提交后台处理
               var itemIdInner = record.data.sqlId;
               var itemTypeInner = record.data.grade;
               var gradeInner = "";
               var info = "";
               if("<b>二级指标</b>" == itemTypeInner){
                 info = "本指标下的三级指标以及公式将一起删除，请确认此操作！";
                 gradeInner = "二级指标";
               }else if("三级指标" == itemTypeInner){
                 info = "删除此三级指标，请确认！";
                 gradeInner = "三级指标";
               }
               Ext.MessageBox.confirm("确认删除？",info,function(btn){
					if("yes" == btn){
						 Ext.Ajax.request({
		                 method:'post',
		                 url:"staffjson/centerItemEditor!deleteEditorSecOrThirdPara.action",
		                 params: {itemId:itemIdInner,itemType:gradeInner},
		                 success:function(response,config){
		                    var json = Ext.decode(response.responseText);
								if("true" == json.success){
								    //刷新grid本页面
									var curCursor = pagingToolbarS.cursor;
									var curPageSize = pagingToolbarS.pageSize;
									dataStoreS.load({params:{start:curCursor,limit:curPageSize}});
									//消息提示
									panelTipInfoDisplayS("删除"+gradeInner+"指标信息成功！","addDelete");
								}else if("false" == json.success){
								    Ext.Msg.alert("错误",json.info);
								    //消息提示
									panelTipInfoDisplayS("删除"+gradeInner+"指标信息失败！","addDelete");
								}
		                   }//success end
                        });
					}//yes btn end
			   }); //confirm end
            }//else end
        }
        
        //【功能.5】返回一级指标编辑页面
        function backToFrontFirstLevelParaS(){
                  //显示一级指标面板
                  handleMainPanelDisplayS();
                  //刷新一级指标面板
                  updatePanelContentByTitleS(plusCurPanelS,"true");
        }
        //【功能.6】公式界面编辑
        function edtiFormulasWinS(para){
          var paraArray = para.split(';');
          //经过分割，4个参数，分别为 二级参数名称、二级参数Id，原始公式内容,原始公式Id
		  if(paraArray.length == 4){
		     
		     //设置原始公式以及Id
		     originalFormulaS = paraArray[2];
		     originalFormulaIdS = paraArray[3];
		     //设置当前指标的名称与Id，传递给前台
		     curFormulaParentIdS = paraArray[0]+";"+paraArray[1];
		     //获取二级指标的三级指标信息
		     Ext.Ajax.request({
		     
		         url    : "staffjson/centerSenThirdItems!getCurThirdPara.action",
		         method :"post",
		         params : {paraId:paraArray[1]},
		         success :function(response,confing){
		         
		                var json = Ext.decode(response.responseText);
		                if(json.success == "false"){
		                  Ext.Msg.alert("错误！","获取三级指标信息错误，无法编辑公式！");
		                  return;
		                }
		                //三级指标数量 为0 ，则返回
		                if(json.data.length == 0){
		                   Ext.Msg.alert("提示！","此指标下无三级指标，无法编辑公式！");
		                   return;
		                }
		                
		                radioItems = [];
		                for(var i = 0;i<json.data.length;i++){
		                   var data = json.data[i];
		                   radioItems.push({
             		                name : "formulaStyle",
             		                inputValue:data.name,
             		                boxLabel:data.name,
             		                style : "margin-top:-3px"
		                   
		                   });
		                }
		                formulaPanelS.remove("fgroupCenterS");
		                var formulaRadioGroupInner   = new Ext.form.RadioGroup({
						           id :"fgroupCenterS",
						           name: "fradiogroupS",
				        		   autoHeight: true,
				                   fieldLabel: '<b>三级指标<b>',
				                   columns: 1,
				                   itemCls : 'x-check-group-alt',
				                   style : "margin-left : 0px;margin-top:20px;",
				                   items: radioItems,
				                   //radioGroup事件
				                   listeners : {
													'change' : 	radioSelectContentS			                   
				                                }
						});
		                formulaPanelS.insert(1,formulaRadioGroupInner);
		                
		                formulaWinS.setTitle(paraArray[0]);
		                formulaTextAreaS.reset();
             			formulaWinS.show();
             			//符号开关关闭（初始不能以符号开始，括号除外）//内容开关打开
             			symbolSwitchS = false;
             			contentSwitchS = true;
             			//清空公式内容
             			formulaContentS ="";
             			formulaArrayS = [];
             			//左括号数量归零
             			leftContainS = 0;
             			//设置公式查看按钮文字
             			viewButtonS.setText("原始公式");
             			
		         }//success end
		     });
		  
            
          }
        }
        //【功能.7】公式提示信息 与 解除
        function editFormulaInfoS(){
           panelTipInfoDisplayS("点击编辑公式",'plus');
        }
        function editFormulaOutS(){
           panelTipInfoHideS();
        }
//======================主面板右键菜单==============================================================================================//        
        
        var mainPanelRightMenuS = new Ext.menu.Menu({
            id : "mainPanelRMWenter",
            items:[
                   { 
                    id:'mainMenu1S',  
			        text:'刷 新',  
			        icon:'js/image/center/refresh.png',  
			        handler:repaintCurPageS
			       },
			        { 
                    id:'mainMenu2S',  
			        text:'设 置',  
			        icon:'js/image/center/gear.png',  
			        handler:gearDisplayParamsS
			       }
                  ]
        });
//===========公式编辑窗口==================================================================================================================================================================//       
		var radioItemsS = [];
		var formulaRadioGroupS   = new Ext.form.RadioGroup({
		           id :"fgroupCenterS",
		           name: "fradiogroupS",
        		   autoHeight: true,
                   fieldLabel: '<b>三级指标<b>',
                   columns: 1,
                   itemCls : 'x-check-group-alt',
                   style : "margin-left : 0px;margin-top:10px;",
                   items: radioItemsS
		});
		var formulaTextAreaS = new  Ext.form.TextArea({
 			       xtype: 'textarea',
				   name: 'msg',
				   width : 460,
				   height : 200,
				   preventScrollbars : true,
				   readOnly : true
		});
		var viewButtonS = new Ext.Button({
	 			                         xtype:'button',
										 text: '原始公式',
								         iconCls: 'formulaEditS',
								         iconAlign: 'top',
								         width: 10,
								         style : "margin-left:8px;",
								         handler : function(btn){
								            var text = btn.getText();
								            if("原始公式" == text){
								               formulaTextAreaS.setValue(originalFormulaS);
								               btn.setText("编辑公式");
								            }
								            if("编辑公式" == text){
								               formulaTextAreaS.setValue(formulaContentS);
								               btn.setText("原始公式");
								            }
								         }
 			                         });
		var formulaPanelS = new Ext.form.FormPanel({
 			    id         : "fromulaPanelWenter",
 			    autoHeight : true,
 			    frame      : true,
 			    layout     : "form",
 			    labelWidth : 70,
 			    labelStyle : "margin-left:0px;",
 			    labelAlign : "left",
 			    items :[
 			             { 
 			               layout : "column",
 			               items :[
 			                       {
						            xtype:'button',
						            text: '加 号',
		                            iconCls: 'formulaAddS',
		                            iconAlign: 'top',
		                            width: 10,
		                            handler : function(){
		                               formulaConentAddS('+','add');
		                            }
 			                       },
 			                       {
 			                        xtype:'button',
						            text: '减 号',
		                            iconCls: 'formulaMinusS',
		                            iconAlign: 'top',
		                            width: 10,
		                            style : "margin-left:8px;",
		                            handler : function(){
		                               formulaConentAddS('-','minus');
		                            }
 			                       },
 			                       {
 			                        xtype:'button',
						            text: '乘 号',
		                            iconCls: 'formulaMultifyS',
		                            iconAlign: 'top',
		                            width: 10,
		                            style : "margin-left:8px;",
		                            handler : function(){
		                               formulaConentAddS('*','multify');
		                            }
 			                       },
 			                       {
 			                        xtype:'button',
						            text: '除 号',
		                            iconCls: 'formulaDivideS',
		                            iconAlign: 'top',
		                            width: 10,
		                            style : "margin-left:8px;",
		                            handler : function(){
		                               formulaConentAddS('/','divide');
		                            }
 			                       },
 			                       {
 			                        xtype:'button',
						            text: '左括号',
		                            iconCls: 'formulaLeftS',
		                            iconAlign: 'top',
		                            width: 10,
		                            style : "margin-left:8px;",
		                            handler : function(){
		                               formulaConentAddS('(','left');
		                            }
 			                       },
 			                       {
 			                        xtype:'button',
						            text: '右括号',
		                            iconCls: 'formulaRightS',
		                            iconAlign: 'top',
		                            width: 10,
		                            style : "margin-left:8px;",
		                            handler : function(){
		                               formulaConentAddS(')','right');
		                            }
 			                       },
 			                       {
 			                        xtype:'button',
						            text: '退格',
		                            iconCls: 'formulaBackS',
		                            iconAlign: 'top',
		                            width: 10,
		                            style : "margin-left:8px;",
		                            handler : formulaBackspaceS
 			                       },
 			                        viewButtonS                
 			                      ]
 			             }//第一行
 			             ,
 			             formulaRadioGroupS
 			             , //第二行
 			             {
 			                layout : "column",
 			                items:[formulaTextAreaS]
 			             }
 			            ]
		});
		var formulaWinS = new Ext.Window({
		            title: '',
			        width: 500,
			        height:480,
			        layout: 'fit',
			        plain:true,
			        modal:true,
			        bodyStyle:'padding:5px;',
			        buttonAlign:'center',
			        closeAction:'hide',
			        resizable : false,
			        items: formulaPanelS,
			        buttons: [{
			            text    : '保存',
			            handler : saveFormulaS
			        },{
			            text    : '退出',
			            handler : cancelEditFormulaS
			        }]
		});
		
		//【功能.1】radioGroup事件，确定用户选择三级指标内容
		function radioSelectContentS(){
		    //【最开始判断】每清空一次Radio,会触发一次change事件。（此时内容为undefined）
			var content = formulaPanelS.getForm().getValues()['formulaStyle'];
			if(content == undefined || content == "" || content == null){
				return;
			}
		    //设置公式查看按钮文字
            viewButtonS.setText("原始公式");
		    //判断内容开关是否打开
		    if(!contentSwitchS){
		       Ext.Msg.alert("错误","请先添加运算符号！")
		       return;
		    }
			
			formulaContentS += content;
			formulaArrayS.push(content);
			formulaTextAreaS.setValue(formulaContentS);
			//开关设置
			contentSwitchS = false;
			symbolSwitchS  = true;
		}
		//【功能.2】+-*/()公式内容加载
		function formulaConentAddS(content,type){
		   //清空RadioGroup选择
		   clearRadioGroupS();
		   //设置公式查看按钮文字
           viewButtonS.setText("原始公式");
		   //左括号具有优先权，可以与符号相接，可以首次使用(不改变内容与符号的顺序)
		   if(!symbolSwitchS && type == "left"){
		      
		      formulaContentS += content;
		      formulaArrayS.push(content);
		      formulaTextAreaS.setValue(formulaContentS);
		      //左括号数量+1
		      leftContainS ++;
		      return;
		   }
		   //右括号，添加完成后不改变符号顺序
		   if(symbolSwitchS && type == "right"){
		      
		      if(leftContainS <= 0){
		        Ext.Msg.alert('错误','右括号数量大于左括号数量！');
		        return;
		      }
		      formulaContentS += content;
		      formulaArrayS.push(content);
		      
		      formulaTextAreaS.setValue(formulaContentS);
		      //左括号数量-1
		      leftContainS --;
		      return;
		   }
		   //符号开关 关闭则返回(控制在需要符号情况下，左括号进入)
		   if(!symbolSwitchS ){
		      Ext.Msg.alert('错误','请先添加参数！');
		      return;
		   }
		   //控制 在运算符允许条件下，左括号是不允许进入
		   if(type == 'left'){
		      Ext.Msg.alert('错误','请先添加运算符号！');
		      return;
		   }
		   formulaContentS += content;
		   formulaArrayS.push(content);
		   formulaTextAreaS.setValue(formulaContentS);
		   //符号开关
		   symbolSwitchS = false;
		   contentSwitchS = true;
		}
		//【功能.3】退格功能
		function formulaBackspaceS(){
		     //清空RadioGroup选择
		     clearRadioGroupS();
		     //设置公式查看按钮文字
             viewButtonS.setText("原始公式");
		     //确定公式内容不为空
		     if(formulaArrayS.length == 0){
		       Ext.Msg.alert('提示','无公式内容可以删除！');
		       return;
		     }
		     var last = formulaArrayS.pop();
		     //更新TextArea的内容
		     var bpContent = "";
		     for(var i=0;i<formulaArrayS.length;i++){
			    bpContent += formulaArrayS[i];
			 }
			 formulaContentS = bpContent;
			 formulaTextAreaS.setValue(bpContent);
			 //更新公式标记开关
			 if(last == "+" || last == "-" || last == "*" || last == "/" || last == ")"  ){
			    contentSwitchS = false;
			    symbolSwitchS = true;
			    if(last == ")")
			    //删除右括号，恢复左括号数量
			    leftContainS ++;
			 }else{
			    contentSwitchS = true;
			    symbolSwitchS = false;
			   //删除左括号，则需对其数量进行减少
			    if(last == "(")
			    leftContainS --;
			 }
		}
		
		//【功能.4】清空radioGroup选择
		function clearRadioGroupS(){
		   formulaPanelS.findById("fgroupCenterS").reset();
		}
		//【功能.5】公式保存
		function saveFormulaS(){
		      //[0]判断编辑内容过是否为空
		      if(formulaContentS == ""){
		         Ext.Msg.alert('提示','您未编辑任何内容！');
		         return;
		      }
		      //[1]判断获取的信息是否为2个
		      var content = curFormulaParentIdS.split(";");
		      if(content.length !=2){
		         Ext.Msg.alert('错误','编辑信息无法提取！');
		         return;
		      }
		      //[2]判断当前括号数量是否少
		      if(leftContainS != 0) {
		         Ext.Msg.alert('提示','请查看是否缺少右括号！');
		         return;
		      }
		      var endStr = formulaContentS.charAt(formulaContentS.length-1);
			  if(endStr == '+'||endStr == '-'||endStr == '*'||endStr == '/' || endStr == '('){
			     Ext.Msg.alert('提示','不能以运算符结尾！');
		         return;
			  }
		      
		      //提交前台[2]判断当前是  “无公式”Or“更新公式状态”
		      Ext.MessageBox.confirm("确认","确认保存编辑公式?",function(btn){
					if("yes" == btn){
					    
					       Ext.Ajax.request({
		     
					         url    : "staffjson/centerFormulas!storeCenterFormula.action",
					         method : "post",
					         params : {
					                    itemName : content[0],
					                    itemId   : content[1],
					                    formula  : formulaContentS,
					                    formulaId : originalFormulaIdS
					                   },
					         success : function(response,config){
					         			 var json = Ext.decode(response.responseText);
					         			 if(json.success == "false"){
					         			    Ext.Msg.alert('错误',json.info);
		        							 return;
					         			 }
					         			 //保存成功后
					         			 cancelEditFormulaS();
					                     //刷新grid本页面
										 var curCursor = pagingToolbarS.cursor;
										 var curPageSize = pagingToolbarS.pageSize;
										 dataStoreS.load({params:{start:curCursor,limit:curPageSize}});
					                   }
					         });
					}
					
			    });
			    
		}
		//【功能.6】公式保存公式退出
		function cancelEditFormulaS(){
		    formulaWinS.hide();
		}
//======================================函数功能开始==================================================================================================================================//         
        //【起始处函数】获取当前中心的back1参数，一行显示几个指标参数
	    function initCenterParaRowsS(){
	      
	        var urlPath = "staffjson/centerParamsRows!getCurCenterBack1Para.action";
				Ext.Ajax.request({
				  url :  urlPath,
				  method : "post",
				  success: function(response,config){
				   
				     var json = Ext.util.JSON.decode(response.responseText);
				     if(json.success == "false"){
				       //未设置相关的即时显示参数，采用默认设置
				       curRowsS = 3;
				       instanceDisplayS = "false";
				      
				     }else{
				       var jarray = json.data.split(";");
				       if(jarray.length !=2 ){
				          Ext.MessageBox.alert("出错啦！2","分割中心设置参数发生错误！");
				          return;
				       }
				       //赋值即时显示参数
				       curRowsS = jarray[0];
				       instanceDisplayS = jarray[1];
				     }//else end
				       
				       //创建主面板
				       //初始化提示函数tools-->qtip
				       Ext.QuickTips.init();  
				       framePanelS = new Ext.Panel({
		   
								        id: "pn",
						                layout:'table',
						                layoutConfig: {columns:curRowsS},
						                baseCls:'x-plain',
						                defaults : {frame:true,width:230,height:150},
		                           });
		              
		               //主面板渲染
		               framePanelS.render("main-panelS");
		               //获取主面板数据
		               getCenterDataS();
		               //主面板添加右键事件
		               framePanelS.getEl().on('contextmenu',function(event){
		                  event.stopEvent();
		                  mainPanelRightMenuS.showAt(event.getXY());
		                   
		                });
				  }//success end
				 });
	    }
		//获取一级&&二级指标数据	
		 function getCenterDataS(){
				
				var urlPath = "staffjson/centerAllPara!getFirstSecondParaData.action";
				Ext.Ajax.request({
				  url :  urlPath,
				  method : "post",
				  success: function(response,config){
				  
				     var data = response.responseText;
				     var json = Ext.util.JSON.decode(data);
				     //如果出现错误，出现错误消息并返回
				     if(json.success == "false"){
				       Ext.MessageBox.alert("出现错误啦！4",json.data);
				       return;
				     }
				     //主面板清空
				     framePanelS.removeAll();
				     //获取中心数据
				     centerIdS = json.centerId;
				     centerNameS = json.centerName;
				     windowNameS = json.windowName;
				     //获取json的data数据
				     var jdata = json.data;
				     //处理Json数据
				     for(var i =0;i<jdata.length;i++){
				       addParamToMasterPanelS(jdata[i].title,jdata[i].content,jdata[i].titleId);
				     }
				     //添加“增加面板”
				     framePanelS.add(addPanelS);
				     //重新布局主面板
				     framePanelS.doLayout();
				  } //success
				});
		}
		//一级&&二级指标数据添加到主面板中
		function addParamToMasterPanelS(title,content,titleId){
		    //html 参数，带鼠标点击事件
		    var titleName = title;
		    title +="edit";
		    
		    var panelS = new Ext.Panel({
		         id    : titleId,
		         title : titleName,
		         html  : "<div  name ='centerDivEdit' class='editDivS' id='"+title+"'>"+content+"</div>", 
		         tools:[
                        {
                         id : "plus",
                         qtip:"编辑此指标下二级&三级指标信息",
                         handler : function(event,toolEl,panelS){
							editPanelParaS(panelS);
                         },
                         on:{
                         		     mouseover:function(){
                         		        firstPanelTipSwitchS = false;
                         				panelTipInfoDisplayS("编辑此指标下二级&三级指标信息",'plus');
                         			 },
                         			 mouseout:function(){
                         			   firstPanelTipSwitchS = true;
                         			   panelTipInfoHideS();
                         			 }
                             }
                         
                        },{
                          id:"close",
                          qtip:"删除此一级指标",
                          handler : function(event,toolEl,panelS){
                             deleteCurrentParaS(panelS);
                          },
                          on:{
                         		     mouseover:function(){
                         		        firstPanelTipSwitchS = false;
                         				panelTipInfoDisplayS("删除此一级指标(所属二级&三级指标一并删除)",'delete');
                         			 },
                         			 mouseout:function(){
                         			   firstPanelTipSwitchS = true;
                         			   panelTipInfoHideS();
                         			 }
                             }
                        }		         
		               ],
		    });
		   
		    //记录当前title的Id信息
		    panelS.contentId = titleId;
		    //增加："添加面板"
		    framePanelS.add(panelS);
		    //主面板重新布局
		    framePanelS.doLayout();
           //////////////提示信息设置////////////////////
             //采用获取Panel的Element原型的方法，进行事件添加
           panelS.getEl().on('mouseover',function(){
                panelTipInfoDisplayS("编辑一级指标信息");     
               
		   });
		   panelS.getEl().on('mouseout',function(){
		        panelTipInfoHideS();
		   });
            //设置面板中的<div>单击进行事件设置
            Ext.get(title).on('click',function(){
				editCurPanelFirstLevelParaS(titleId);
           });
		}	
		
		//[核心.0]添加一个面板，（添加一个一级参数）
		function addParaImageS(){
		 
			 //表单数据清空
			 inputFormS.getForm().reset();
			 Ext.getCmp("pcenterInputIdS").setValue(centerNameS);
			 Ext.getCmp("pwindowInputIdS").setValue(windowNameS);
			 //指标窗口打开
			 inputWinS.show();

		}
		//[核心.1]重新刷新本页面
	    function repaintCurPageS(){
	       
	       mainPanelRightMenuS.removeAll();
	       rightMenuS.removeAll();
	       inputFormS.destroy();
           gearDisplayFormS.destroy();
           editFirstLevelFormS.destroy();
           formulaPanelS.destroy();
	       inputWinS.close();
		   gearDisplayWinS.close();
		   editFirstLevelWinS.close();
		   formulaWinS.close();
	       var doc = Ext.getCmp('doc-body');
	       doc.getActiveTab().load({url:'staffPara/staffPara.jsp',scripts:true});
	    }
	   
		//[核心.2]删除当前面板
		function deleteCurrentParaS(curPanel){
			 Ext.MessageBox.confirm("确认删除？","本指标下的二级&三级指标以及公式将一起删除，请确认此操作！",function(btn){
					if("yes" == btn){
						
					    var urlPath = "staffjson/centerAllPara!deleteFirstLevelParams.action";
						Ext.Ajax.request({
						                    url :  urlPath,
						     				method : "post",
						     				params:{centerItemId:curPanel.contentId},
						  					success: function(response,config){
						  					
						  					  var json = Ext.util.JSON.decode(response.responseText);
						  					  if(json.success == "false"){
						  					     Ext.MessageBox.alert("出错啦！3",json.data);
						  					     return;
						  					  }else{
						  					    
						  					     //删除一级指标成功后，判断当前模式是否为即时刷新，分别执行
												if(instanceDisplayS == "true"){
												   //刷新页面
												   repaintCurPageS();
												}else{
												   //刷新主面板
								     			   framePanelS.remove(curPanel);
												}//inner else end
												
												//消息提示
												panelTipInfoDisplayS('删除一级指标成功','addDelete');
								
						  					  }// else end
						  					}//success
						});
					}else{
						 //do nothing
					}
					});   
		}
		//[核心.3]设置显示参数
		function gearDisplayParamsS(){
		   
		  //由于gearDisplayFormS早定义好，因此其emptyText需要再
		  Ext.getCmp("displayRowsNumS").setValue(curRowsS);
		  //表单数据清空
		  gearDisplayFormS.getForm().reset();
          //判断当前的显示模式
          if(instanceDisplayS == "true"){
           //即时显示模式
            Ext.getCmp("solidanalyseS").setValue(true);
		    Ext.getCmp("nosolidanalyseS").setValue(false);
          }else{
            Ext.getCmp("solidanalyseS").setValue(false);
		 	Ext.getCmp("nosolidanalyseS").setValue(true);
          }
		  //显示设置窗口
		  gearDisplayWinS.show();
		}
		//[核心.4]编辑当前面板的二级指标参数
		function editPanelParaS(curPanel){
	         handleEditPanelDisplayS();
	         if(curPanel.title.indexOf('(') >0){
	          		var secondTitle = curPanel.title.substring(0,curPanel.title.indexOf('('));
	          		editorGridS.setTitle(secondTitle);
	         }else{
	         		editorGridS.setTitle(curPanel.title);
	         }
	         //判断当前选择的PanelId 是否与curPanelIdS 相同。如果相同，就不进行数据读取；如果不同则数据读取
	         if( curPanel.contentId != curPanelIdS){
	            curPanelIdS = curPanel.contentId;
	            dataStoreS.removeAll();
	            dataStoreS.load({params:{start:0,limit:9,paraId:curPanel.contentId}});   
	         }else{
	            // do nothing
	         }
	         //记录当前一级指标的名称信息（标题信息）
	         plusCurPanelS = curPanel.id;
		}
		
		//[核心.5] 编辑当前面板以及指标参数
		function editCurPanelFirstLevelParaS(curId){
		   //记录当前Panel的Id
		   curFirstLevelIdS = curId;
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
		        Ext.getCmp("efnameIdS").setValue(json.name);
		        Ext.getCmp("efweightIdS").setValue(json.weight);
		        Ext.getCmp("efcenterIdS").setValue(json.center);
		        Ext.getCmp("efwindowIdS").setValue(json.window);
		        Ext.getCmp("efgradeIdS").setValue("一级指标");;
		  
		        if(json.cstyle == "定量"){
		           Ext.getCmp("efstylesolidIdS").setValue(true);
		           Ext.getCmp("efstylenosolidIdS").setValue(false);
		        }else{
		        
		           Ext.getCmp("efstylesolidIdS").setValue(false);
		           Ext.getCmp("efstylenosolidIdS").setValue(true);
		        }
		        if(json.estyle == "true"){
		           Ext.getCmp("efusedstyleIdS").setValue(true);
		           Ext.getCmp("efnousedstyleIdS").setValue(false);
		        }else{
		           Ext.getCmp("efusedstyleIdS").setValue(false);
		           Ext.getCmp("efnousedstyleIdS").setValue(true);
		        }
		        //【重要】需要清空内容后显示-
		        editFirstLevelWinS.show();
		      }
		   });	
		}
		//根据Panel的id更新Panel的内容
		function updatePanelContentByTitleS(panelId,secondback){
		  //获取Panel实例
		  var curPanel =  Ext.getCmp(panelId);
		  var curParaId = curPanel.contentId;
		  //获取当前Panel的数据信息
		  Ext.Ajax.request({
		      url: "staffjson/centerAllPara!getFirstLevelAndBelongSecondLevelPara.action",
		      method:"post",
		      params:{centerItemId:curParaId},
		      success:function(response,config){
		        
		         var json = Ext.util.JSON.decode(response.responseText);
		         if(json.success == "false"){
		         	Ext.Msg.alert("出错啦!",json.data);
		        	return;
		         }
		         //获取正确结果，更新Panel面板
		         var content = "<div name ='centerDivEdit' class='editDivS' id='"+panelId+"'>"+json.data+"</div>";
		         
		         curPanel.body.dom.innerHTML = content;
		         
		         //设置面板中的<div>单击进行事件设置
		         if(curPanel.title.indexOf('改') <0 && secondback == 'false'){
		           curPanel.setTitle(curPanel.title+"<font color='red'>(改)</font>");
		         }
                 Ext.get(panelId).on('click',function(){
				  editCurPanelFirstLevelParaS(curParaId);
                 });
                 //主面板重新布局
		         curPanel.doLayout();
		         framePanelS.doLayout();
		       }
           });
		}
		  
		//panel提示信息的开启
		function panelTipInfoDisplayS(text,disType){
            
            //总消息显示开关
            if(totalTipSwitchS){
	             //消息开关 打开才可以显示信息(控制总Panel的提示信息)
	             if(firstPanelTipSwitchS){
	               document.getElementById('tipS').innerHTML = text;
	               Ext.get('tipS').show();
	             }
	             //tools 的plus delete提示信息
	             else if('plus' == disType || 'delete' == disType){
	               document.getElementById('tipS').innerHTML = text;
	               Ext.get('tipS').show();
	             }
	             
	             //控制删除、添加成功后显示提示
	             if(disType == "addDelete"){
	                 //关闭总消息显示开关
	                 totalTipSwitchS = false;
	                 document.getElementById('tipS').innerHTML = text;
	                 Ext.get('tipS').fadeIn("r",{duration:5});
	                 window.setTimeout(function(){
	                       totalTipSwitchS = true;
	                       panelTipInfoHideS();
	                 },5000);
	             }
             }//if end
             else{
             	// do nothing
             }
		}
		//panel提示信息的关闭
		function panelTipInfoHideS(){
		     if(totalTipSwitchS){
		     	Ext.get('tipS').hide();
		     }
		}
		
///////////editorGridSPanel---Start/////////////////
		//【1】表格的右键点击选择事件
		function rowContextFnS(grid,rowIndex,e){
		    
		    e.stopEvent();
		    rightMenuS.showAt(e.getXY());
		    //记录当前的行
		    rightContextRowIndexS = rowIndex;
		    //清除所有选择行
		    rowContextHideFnS();
		    e.stopEvent();
		}
		//【2】表格编辑之后，取消对行的选择(此函数所有的参数均在e 里面，row、column)
		function afterEditFnS(e){
		 	var sm = editorGridS.getSelectionModel();
		 	sm.deselectRow(e.row);
		}
		//【3】右键菜单消失后，清除行选择
		function rowContextHideFnS(){
		    var sm = editorGridS.getSelectionModel();
		    for(var i = 0; i< editorGridS.getView().getRows().length;i++){
               sm.deselectRow(i);              
            }
		}
		//[.1]右键菜单除了（保存），之外所有的菜单进行调用。保存为全局性，因此不需要选择一行
		function rowSelectionOtherS(){
		    //gridpanel默认右击是不会选择当前行的，所以必须添加这句代码  
            editorGridS.getSelectionModel().selectRow(rightContextRowIndexS); 
		}
		
		//editorGridCPanel---End
		
		//处理主面板模块的显示&&隐藏
		function handleMainPanelDisplayS(){
		    
		    //编辑面板清空
		   // editPanel.removeAll();
		    Ext.get("main-panelS").setVisible(true);
		    Ext.get("edit-panelS").setVisible(false);
		}
		//处理编辑面板模块的显示&&隐藏
		function handleEditPanelDisplayS(){
		    
		    //主面板清空
			//framePanelC.removeAll();
		    Ext.get("main-panelS").setVisible(false);
		    Ext.get("edit-panelS").setVisible(true);
		}
	   
		Ext.onReady(function(){
		     editPanelS.render("edit-panelS");
		     initCenterParaRowsS();
		     //设置表格编辑相关事件
		     editorGridS.addListener('rowcontextmenu',rowContextFnS);
		     editorGridS.addListener('afteredit',afterEditFnS);
		     rightMenuS.addListener('hide',rowContextHideFnS);
		     //设置一级指标编辑关闭前事件		     
		     var doc = Ext.getCmp('doc-body').getActiveTab();
		     doc.addListener('beforeclose',function(){
		      	    mainPanelRightMenuS.removeAll();
	       			rightMenuS.removeAll();
	       			inputFormS.destroy();
          			gearDisplayFormS.destroy();
          			editFirstLevelFormS.destroy();
           			formulaPanelS.destroy();
	       			inputWinS.close();
		   			gearDisplayWinS.close();
		   			editFirstLevelWinS.close();
		   			formulaWinS.close();	
		     });
		});
	
	</script>
	
  </head>
      
  <body>
  		<div id='tipS' class='tipS'></div>
		<div id="main-panelS"></div>
		<div id="edit-panelS" ></div>
		
  </body>
</html>
