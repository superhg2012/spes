<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>年级列表</title>
	<script type="text/javascript" src="./user/RowEditor.js"></script>
	<script type="text/javascript">
	   Ext.onReady(function(){
		   var store = new Ext.data.Store({
			 proxy : new Ext.data.HttpProxy({
				  url : 'gradeAction!list.action',
				  method  : 'POST'
			 }), 
			 
			 reader : new Ext.data.JsonReader({
				 totalProperty : 'total',
				 root : 'rows',
				 id : 'id',
				 fields : ['gradeId','grade']
			 }),
			 remoteSort : true
		   });
	/**	  var  Employee = Ext.data.Record.create([{
		        name: 'gradeId',
		        type: 'string'
		    }, {
		        name: 'grade',
		        type: 'string'
		    }]);
		*/  
 	       store.load({
 	    	   params : {
 	    	     start : 0, 
 	    	     limit : 20
 	    	   }
 	       });
		   // 设置默认的排序字段和升降序
           store.setDefaultSort('grade', 'asc');
           var sm = new Ext.grid.CheckboxSelectionModel();// 设置复选框
            // 设置列
           var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),sm, 
                    {header : "序号", width: 120,dataIndex : 'gradeId',sortable : true}, 
                    {header : "年级", width: 280,dataIndex : 'grade',sortable : true}
            ]);
            // 默认排序设置为可排序
            cm.defaultSortable = true;
		   //行编辑器
        	/**var editor = new Ext.Editor({
       			 saveText: '更新'
    		});
		   */
            //数据表格
		    grid = new Ext.grid.GridPanel({
                       el : 'listWin',
                       width : '100%',
                       title : '',
                       height : 540,
                       store : store,
                       cm : cm,
                       sm : sm,
                       animCollapse : false,
                       trackMouseOver : false,
                       loadMask : {
                           msg : '载入中,请稍候 '
                       },
		                 // 上方工具条
                        tbar : [{
                                    id : 'addDicButton',
                                    text : '增加',
                                    tooltip : '添加一条记录'
                               /**     handler: function(){
					                var e = new Employee({
					                    gradeId: '2014',
					                    grade: '研2014级'
					                });
					              //  editor.stopEditing();
					                store.insert(0, e);
					                grid.getView().refresh();
					                grid.getSelectionModel().selectRow(0);
					               // editor.startEditing(0);
                                  }
                        */
                                }, '-', {
                                    id : 'updateDicButton',
                                    text : '修改',
                                    tooltip : '修改所选择的一条记录'
                                }, '-', {
                                    id : 'deleteDicButton',
                                    text : '删除',
                                    tooltip : '删除所选择的记录'
                                 /**   handler: function(){
						             //   editor.stopEditing();
						                var s = grid.getSelectionModel().getSelections();
						                for(var i = 0, r; r = s[i]; i++){
						                    store.remove(r);
						                }
						            }
                                */
                                }],
                         bbar : new Ext.PagingToolbar({
                                    pageSize : 20,
                                    store : store,
                                    displayInfo : true,
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
            // 加载grid
            grid.render();
		    // 设置分页数据
	   });
	</script>
  </head>
  
  <body>
    <div id="listWin" style="border:none;width:100%;height:99%;" ></div>
    <div id="addDicWin"></div>
  </body>
</html>
