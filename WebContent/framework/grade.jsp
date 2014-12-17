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
    <title>�꼶�б�</title>
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
		   // ����Ĭ�ϵ������ֶκ�������
           store.setDefaultSort('grade', 'asc');
           var sm = new Ext.grid.CheckboxSelectionModel();// ���ø�ѡ��
            // ������
           var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),sm, 
                    {header : "���", width: 120,dataIndex : 'gradeId',sortable : true}, 
                    {header : "�꼶", width: 280,dataIndex : 'grade',sortable : true}
            ]);
            // Ĭ����������Ϊ������
            cm.defaultSortable = true;
		   //�б༭��
        	/**var editor = new Ext.Editor({
       			 saveText: '����'
    		});
		   */
            //���ݱ��
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
                           msg : '������,���Ժ� '
                       },
		                 // �Ϸ�������
                        tbar : [{
                                    id : 'addDicButton',
                                    text : '����',
                                    tooltip : '���һ����¼'
                               /**     handler: function(){
					                var e = new Employee({
					                    gradeId: '2014',
					                    grade: '��2014��'
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
                                    text : '�޸�',
                                    tooltip : '�޸���ѡ���һ����¼'
                                }, '-', {
                                    id : 'deleteDicButton',
                                    text : 'ɾ��',
                                    tooltip : 'ɾ����ѡ��ļ�¼'
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
            // ����grid
            grid.render();
		    // ���÷�ҳ����
	   });
	</script>
  </head>
  
  <body>
    <div id="listWin" style="border:none;width:100%;height:99%;" ></div>
    <div id="addDicWin"></div>
  </body>
</html>
