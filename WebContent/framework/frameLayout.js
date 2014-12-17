/**
 * �����沼��
 * @memberOf {TypeName} 
 * @return {TypeName} 
 */

//some global variables
var GLOBAL = [];
var GLOBAL_MAP = {};

//var GLOBAL_SHEETTYPE_MAP ={};
//GLOBAL_SHEETTYPE_MAP['month'] = '�¶ȿ���';
//GLOBAL_SHEETTYPE_MAP['quarter']='���ȿ���';
//GLOBAL_SHEETTYPE_MAP['year'] = '��ȿ���';

Ext.onReady(function(){
	
	Ext.QuickTips.init();

	var mainPanel = new MainPanel(); //���������
	var leftPanel  = new LeftTreePanel(); //����������
	
	
	leftPanel.on('click',function(node, e){ //������ڵ����¼�
		if(node.isLeaf()) {
			e.stopEvent();
			mainPanel.loadClass(node.attributes.href, node.id);
		} 
	});
	
//	leftPanel.getRootNode().expand();
	Ext.getCmp("left-tree").getRootNode().expand(true);
	//������е�tab�л�ʱ,���˵������л�
	mainPanel.on('tabchange', function(tp, tab){
		leftPanel.selectClass(tab.cclass);
	});
	
	//������ҳ
//	mainPanel.loadClass("welcome.jsp", "ϵͳ��ҳ.ϵͳ��ҳ",false);
	
	var htmlT = '<div  style="border:solid 1px red;background-color:#D2E0F0;padding-left:50px;padding-top:20px;height:40px;position:relative;">'
	          + '<span style="font-size:x-large;color:white; "><b>����������ļ�Ч����ϵͳ</b></span>'
	          + '<span style="font-size:12px;color:white;"><b>&nbsp;�汾��:v1.0 </b></span>';
	          + '</div>';
	          
	var html =' <div class="headBg"> ' 
	   	  + ' <div style="top:30px; left :70px; position:absolute ;"> ' 
	      + ' <span style="font-size:x-large;color:white; "><b>����������ļ�Ч����ϵͳ</b></span> ' 
	      + ' <span style="font-size:12px;color:white; "><b>�汾��:v1.0 </b></span> '
	      + ' </div> ' 
    	+ ' <div class="head"> '        
	 		+ ' <div class="bmsBg">' 
	 		      + ' <div id="head_timer" style="clear:both;padding-top:20px;padding-right:30px;text-align:right;color:white;">&nbsp;&nbsp;&nbsp;</div>'
	 			  + ' <div class="hlep" style="padding-right:30px"> '
                  + '   <img alt="��ѯ" id="search" src="skin/blue/images/frame/ico_yfgl.gif"/><span><font color="red"><b>[' + Ext.get("userId").dom.value +']</b></font></span>,��ӭ����½ϵͳ����ɫ:<span><font color="red"><b>[' + Ext.get("roleName").dom.value + ']</b></font></span>'
			  	  + '   <img alt="����" id="passWord" src="skin/blue/images/frame/psw.gif"/>'
		          + '   <a id="amodify" href="javascript:void()" onFocus="this.blur()">�����޸�</a>&nbsp;&nbsp;'
				  + '   <img alt="ע��" id="cancel" src="skin/blue/images/frame/logout.gif"/>'
				  + '   <a href="javascript:logout();" onFocus="this.blur()">�˳�ϵͳ</a>'
				  + ' </div>' 
	 	   + '</div> '
  	   + ' </div> '
  	   + '<div class="line01"></div> '
	   +' </div> ';
	 var navToolBar;
	//ͷ���
	 var header = new Ext.Panel({
        border: false,
        collapsible : true,
        border:true,
        layout:'vbox',
        region:'north',
        cls: 'docs-header',
        height:120,
        maxSize : 120,
        width : '100%',
        html : html,
        items: [{
            xtype:'box',
            el:'header'
        },
        //������
       navToolBar = new Ext.Toolbar({
    	    id:"menubar",
            cls:'top-toolbar',
            height :30,
            width : '100%',
            border : false,
            suspendLayout : true,
            items :['&nbsp;&nbsp;']
            
        })//end toolbar
        ]
    });
	 
	 //footer
	 var footer = new Ext.Panel({
		id :"footer",
        height:30,
		border: true,
        frame : true,
        region:'south',
        cls: 'docs-footer',
        html :"<div style='fong-family:����;font-weight:normal;text-align:left;vertical-align:top;line-height:1.5;clear:both;margin:0 auto;width:100%;bottom:0;position:absolute;padding-left:30px'>Copyrights&copy; 2013.4.�й���׼���о�Ժ.All Rights Reserved.</div>"
        	//+ "<div class='span6' style='text-align:right;padding-right:30px'  id='status-bar'>Error...</div>"
	 });
	 
	 //east
	 var easter = new Ext.Panel({
        region:'east',
        cls: 'docs-easter',
        title : '�����˵�',
        width: 240,
        border:true,
        split : true,
        minSize: 175,
        maxSize: 360,
        animate: true,
        border : false,
        autoScroll:true,
        collapsible: true,
        animCollapse:true,
        collapseMode:'mini'
	 });
	 
	//�����ӿ�,��Ⱦ��body��
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [header,footer,leftPanel, mainPanel],
		listeners :{
		    //�ı��ӿڴ�С
		    resize : function(){
		     // viewport.doLayout();
		    }
		}
	});
	
	viewport.doLayout();
	//���ص����˵�
	setToolButtons(navToolBar,leftPanel);
	//�޸�����
	Ext.get("amodify").on('click',function(){
		modifyPass(mainPanel);
	});
	
	setTimeout(function(){
        Ext.get('loading').remove();
        Ext.get('loading-mask').fadeOut({remove:true});
    }, 250);
	
	
	//skin----
	
	/**
	 * ����ѡ���¼�
	 * @param {Object} e
	 */
	function onThemeSelect(e){
		Ext.util.CSS.swapStyleSheet("theme", "ext-3.0.0/resources/css/xtheme-" + e.getValue() + ".css");
    	Ext.util.CSS.refreshCache();
	}
	
	var filter = new Ext.tree.TreeFilter(leftPanel, {
		clearBlank: true,
		autoClear: true
	});
	
	var hiddenPkgs = [];
	
	function filterTree(e){
		var text = e.target.value;
		Ext.each(hiddenPkgs, function(n){
			n.ui.show();
		});
		if(!text){
			filter.clear();
			return;
		}
		leftPanel.expandAll();
		
		var re = new RegExp('^' + Ext.escapeRe(text), 'i');
		filter.filterBy(function(n){
			return !n.attributes.isClass || re.test(n.text);
		});
		
		// hide empty packages that weren't filtered
		hiddenPkgs = [];
		leftPanel.root.cascade(function(n){
			if(!n.attributes.isClass && n.ui.ctNode.offsetHeight < 3){
				n.ui.hide();
				hiddenPkgs.push(n);
			}
		});
	}
});//end on ready


Ext.app.SearchField = Ext.extend(Ext.form.TwinTriggerField, {
    initComponent : function(){
        if(!this.store.baseParams){
			this.store.baseParams = {};
		}
		Ext.app.SearchField.superclass.initComponent.call(this);
		this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER){
                this.onTrigger2Click();
            }
        }, this);
    },

    validationEvent:false,
    validateOnBlur:false,
    trigger1Class:'x-form-clear-trigger',
    trigger2Class:'x-form-search-trigger',
    hideTrigger1:true,
    width:180,
    hasSearch : false,
    paramName : 'query',

    onTrigger1Click : function(){
        if(this.hasSearch){
            this.store.baseParams[this.paramName] = '';
			this.store.removeAll();
			this.el.dom.value = '';
            this.triggers[0].hide();
            this.hasSearch = false;
			this.focus();
        }
    },

    onTrigger2Click : function(){
        var v = this.getRawValue();
        if(v.length < 1){
            this.onTrigger1Click();
            return;
        }
		if(v.length < 2){
			return;
		}
		this.store.baseParams[this.paramName] = v;
        var o = {start: 0};
        this.store.reload({params:o});
        this.hasSearch = true;
        this.triggers[0].show();
		this.focus();
    }
});

/**
 * Makes a ComboBox more closely mimic an HTML SELECT.  Supports clicking and dragging
 * through the list, with item selection occurring when the mouse button is released.
 * When used will automatically set {@link #editable} to false and call {@link Ext.Element#unselectable}
 * on inner elements.  Re-enabling editable after calling this will NOT work.
 *
 * @author Corey Gilmore
 * http://extjs.com/forum/showthread.php?t=6392
 *
 * @history 2007-07-08 jvs
 * Slight mods for Ext 2.0
 */
Ext.ux.SelectBox = function(config){
	this.searchResetDelay = 1000;
	config = config || {};
	config = Ext.apply(config || {}, {
		editable: false,
		forceSelection: true,
		rowHeight: false,
		lastSearchTerm: false,
        triggerAction: 'all',
        mode: 'local'
    });

	Ext.ux.SelectBox.superclass.constructor.apply(this, arguments);

	this.lastSelectedIndex = this.selectedIndex || 0;
};

Ext.extend(Ext.ux.SelectBox, Ext.form.ComboBox, {
    lazyInit: false,
	initEvents : function(){
		Ext.ux.SelectBox.superclass.initEvents.apply(this, arguments);
		// you need to use keypress to capture upper/lower case and shift+key, but it doesn't work in IE
		this.el.on('keydown', this.keySearch, this, true);
		this.cshTask = new Ext.util.DelayedTask(this.clearSearchHistory, this);
	},

	keySearch : function(e, target, options) {
		var raw = e.getKey();
		var key = String.fromCharCode(raw);
		var startIndex = 0;

		if( !this.store.getCount() ) {
			return;
		}

		switch(raw) {
			case Ext.EventObject.HOME:
				e.stopEvent();
				this.selectFirst();
				return;

			case Ext.EventObject.END:
				e.stopEvent();
				this.selectLast();
				return;

			case Ext.EventObject.PAGEDOWN:
				this.selectNextPage();
				e.stopEvent();
				return;

			case Ext.EventObject.PAGEUP:
				this.selectPrevPage();
				e.stopEvent();
				return;
		}

		// skip special keys other than the shift key
		if( (e.hasModifier() && !e.shiftKey) || e.isNavKeyPress() || e.isSpecialKey() ) {
			return;
		}
		if( this.lastSearchTerm == key ) {
			startIndex = this.lastSelectedIndex;
		}
		this.search(this.displayField, key, startIndex);
		this.cshTask.delay(this.searchResetDelay);
	},

	onRender : function(ct, position) {
		this.store.on('load', this.calcRowsPerPage, this);
		Ext.ux.SelectBox.superclass.onRender.apply(this, arguments);
		if( this.mode == 'local' ) {
			this.calcRowsPerPage();
		}
	},

	onSelect : function(record, index, skipCollapse){
		if(this.fireEvent('beforeselect', this, record, index) !== false){
			this.setValue(record.data[this.valueField || this.displayField]);
			if( !skipCollapse ) {
				this.collapse();
			}
			this.lastSelectedIndex = index + 1;
			this.fireEvent('select', this, record, index);
		}
	},

	render : function(ct) {
		Ext.ux.SelectBox.superclass.render.apply(this, arguments);
		if( Ext.isSafari ) {
			this.el.swallowEvent('mousedown', true);
		}
		this.el.unselectable();
		this.innerList.unselectable();
		this.trigger.unselectable();
		this.innerList.on('mouseup', function(e, target, options) {
			if( target.id && target.id == this.innerList.id ) {
				return;
			}
			this.onViewClick();
		}, this);

		this.innerList.on('mouseover', function(e, target, options) {
			if( target.id && target.id == this.innerList.id ) {
				return;
			}
			this.lastSelectedIndex = this.view.getSelectedIndexes()[0] + 1;
			this.cshTask.delay(this.searchResetDelay);
		}, this);

		this.trigger.un('click', this.onTriggerClick, this);
		this.trigger.on('mousedown', function(e, target, options) {
			e.preventDefault();
			this.onTriggerClick();
		}, this);

		this.on('collapse', function(e, target, options) {
			Ext.getDoc().un('mouseup', this.collapseIf, this);
		}, this, true);

		this.on('expand', function(e, target, options) {
			Ext.getDoc().on('mouseup', this.collapseIf, this);
		}, this, true);
	},

	clearSearchHistory : function() {
		this.lastSelectedIndex = 0;
		this.lastSearchTerm = false;
	},

	selectFirst : function() {
		this.focusAndSelect(this.store.data.first());
	},

	selectLast : function() {
		this.focusAndSelect(this.store.data.last());
	},

	selectPrevPage : function() {
		if( !this.rowHeight ) {
			return;
		}
		var index = Math.max(this.selectedIndex-this.rowsPerPage, 0);
		this.focusAndSelect(this.store.getAt(index));
	},

	selectNextPage : function() {
		if( !this.rowHeight ) {
			return;
		}
		var index = Math.min(this.selectedIndex+this.rowsPerPage, this.store.getCount() - 1);
		this.focusAndSelect(this.store.getAt(index));
	},

	search : function(field, value, startIndex) {
		field = field || this.displayField;
		this.lastSearchTerm = value;
		var index = this.store.find.apply(this.store, arguments);
		if( index !== -1 ) {
			this.focusAndSelect(index);
		}
	},

	focusAndSelect : function(record) {
		var index = typeof record === 'number' ? record : this.store.indexOf(record);
		this.select(index, this.isExpanded());
		this.onSelect(this.store.getAt(record), index, this.isExpanded());
	},

	calcRowsPerPage : function() {
		if( this.store.getCount() ) {
			this.rowHeight = Ext.fly(this.view.getNode(0)).getHeight();
			this.rowsPerPage = this.maxHeight / this.rowHeight;
		} else {
			this.rowHeight = false;
		}
	}
});

Ext.Ajax.on('requestcomplete', function(ajax, xhr, o){
    if(typeof urchinTracker == 'function' && o && o.url){
        urchinTracker(o.url);
    }
});


/**
 * �˳�ϵͳ
 */
function logout(){
	Ext.Msg.confirm('ȷ���˳�','��ȷ��Ҫ�˳�ϵͳ��',function(btn){
		if(btn=='yes'){
		  Ext.Ajax.request({
			url : 'logoutAction.action',
			method : 'get',
			params : {},
			callback : function(options,success,response){
				if(success){
					window.location.replace("./login.jsp");//�����������棬����ͨ����������˻���ǰ������ϵͳ
				}
			}
	     });
	  }//end if
	});
	/*if (confirm("��ȷʵҪ�˳���ϵͳ��")){
	 * Ext.Ajax.request({
		url : 'logoutAction.action',
		method : 'get',
		params : {},
		callback : function(options,success,response){
			if(success){
				window.location.replace("./login.jsp");//�����������棬����ͨ����������˻���ǰ������ϵͳ
			}
		}
	 });
     return;
    }*/
}//end function

/**
 * �����޸�����ҳ��
 * @param {Object} mainPanel
 */
function modifyPass(mainPanel){
	mainPanel.loadClass("user/modifyPass.jsp", "ϵͳ����.�����޸�",true);
}//end function
var themes = [
            ['Ĭ��', 'ext-all.css'],
            ['��ɫ', 'xtheme-purple.css'],
            ['���ɫ', 'xtheme-olive.css'],
            ['����ɫ', 'xtheme-slate.css'],
            ['ǳ��ɫ', 'xtheme-darkgray.css'],
            ['��ɫ', 'xtheme-gray.css'],
            ['����ɫ', 'xtheme-galdaka.css'],
            ['��ɫ', 'xtheme-pink.css'],
            ['����ɫ', 'xtheme-indigo.css'],
            ['��ҹ', 'xtheme-midnight.css'],
            ['����ɫ', 'xtheme-silverCherry.css']
          ];

    var Mystore = new Ext.data.SimpleStore({
            fields:['theme','css'],
            data : [['Ĭ��','blue'],['��ɫ','black'],['���','darkgray'],['δ֪','galdaka'],['��ɫ','purple'],['����','slate'],['���','olive']]
//            data:themes
          });
    
	var skincombo = new Ext.form.ComboBox({
                    fieldLabel:'����Ƥ��',
                    id:'css',
                    triggerAction:'all',
                    store:Mystore,
                    displayField:'theme',
                    valueField:'css',
                    value:'Ĭ��',
                    mode:'local',
                    anchor:'95%',
                    handleHeight:10,
                    resizable:true,
                    selectOnfocus:true,
                    typeAhead:true
          });
	
	    skincombo.on('select',function(e){
	    	   Ext.util.CSS.swapStyleSheet("theme", "ext-3.0.0/resources/css/xtheme-" + e.getValue() + ".css");
    	       Ext.util.CSS.refreshCache();
               var css =   skincombo.getValue();
               //����cookies
               var date=new Date();
               //alert(css);
               date.setTime(date.getTime()+30*24*3066*1000);
               //document.getElementsByTagName("link")[1].href="ext-3.2.0/resources/css/"+css;
               //document.cookie="css="+css+";expires="+date.toGMTString();
            });
/**
 * ����Ƥ������
 */
var win; //���嵯������
function changeSkin(){
	if(!win){
		win = new Ext.Window({
			id : 'skinWin',
			title : 'Ƥ���������',
			width : 480,
			height : 280,
			layout : 'fit',
			resizable :false,
			closeAction : 'hide',
			split : true,
			modal :true,
			shadow :true,
			constrain : true,//Լ��������ʾ������������ڲ�
			items :[skincombo]
			
		});
	}
	win.show('face');
}//end function
/**
 * ���õ�������ť
 * @param {Object} toolbar
 */
setToolButtons = function(toolbar,treePanel){
	//���ý�ɫ��Ӧ�Ķ���
	Ext.Ajax.request({
		loadMask : true,
    	url : 'roleAction!getActions.action',
    	method : 'post',
    	params : {"roleId" : Ext.get("roleId").dom.value},
    	callback : function(options,success,response){
    		if(success){
    			var array  = eval(response.responseText);
    			var total = array.length;
    			var arrays = new Array(total);
    			var defaultIndex;
    			var index = 0;
    			for(var i=0; i < total; i++){
    				var o = array[i];
    				//���ɵ����˵�
    				if(o.parentId == 0) {
    					var firstNavIndex = o.actionId;
    					if(index == 0){
	    					defaultIndex = firstNavIndex;	
    					}
//    					arrays[i] = new Ext.Toolbar.Button({text : o.actionName,handler : callLeftMenu(o.actionId)});
//    					toolbar.add(header,new Ext.Toolbar.Button({text : o.actionName,handler : callLeftMenu(o.actionId)}),i);
    					toolbar.addButton({id : "'"+ firstNavIndex +"'",text : o.actionName,scope : this,iconCls : 'icon-cls',isClass:true, icon:o.icon, cls : 'toolbarCls', listeners:{
    						"click" : function(){
    					         setTreeNodes(treePanel,this.id);	
    						}
    					}});
    					toolbar.addSeparator();
    					index++;
    				}//end if
    			}//end for
//			    toolbar.add(arrays);
//				toolbar.addFill();
    			toolbar.doLayout();//refresh
    			setTreeNodes(treePanel, "'" + defaultIndex + "'");//Ĭ�ϼ���
    			Ext.get(document.body).unmask();  
    		} else {
    			Ext.Msg.alert("��ʾ��Ϣ","�����˵�����ʧ��! ����ϵ����Ա");
    		}
    	}
   });//end ajax
}//end function

/**
 * ���ݵ����˵�Id���������β˵�
 * @param {Object} treePanel
 */
 setTreeNodes = function (treePanel,actionId) {
	if(actionId!=undefined){
   		actionId = actionId.replace("\'","").replace("\'","");//ȥ���������ߵĵ�����
		var loader = treePanel.getLoader();
		 
		Ext.Ajax.request({
			url : 'roleAction!getActionByActionId.action',
	    	method : 'post',
	    	params : {"parentId" : actionId,"roleId" : Ext.get("roleId").dom.value},
		    success :function(response){
			   var array = eval(response.responseText);
			   var rootNode = treePanel.getRootNode();//��ȡ�����ڵ�
			   if(rootNode) {//���ڵ��Ѿ�����
				   //��ո��ڵ��µ��ӽڵ�
				   if(rootNode.childNodes.length > 0){
				   		removeChildren(rootNode);
				   }
				   rootNode.leaf = false;
				   //�����ڵ�
				   for(var i = 0; i < array.length; i++){
					   var obj = array[i];
					   var nodeAttrConfig = {"id" : obj.target,qtip : obj.actionName,"href":obj.url,"text":obj.actionName, iconCls:'',"isClass":true,"leaf":true};
					   rootNode.appendChild(new Ext.tree.TreeNode(nodeAttrConfig));
			       }
				   loadFirstNode(rootNode.firstChild);
//				  treePanel.expandAll();
				   //��ӵ���¼�
			   }
		    },
		    failure : function(response){
		    	Ext.Msg.alert("��ʾ��Ϣ","�Ӳ����β˵�ʧ��");
		    	return;
		    }
	   });//end ajax
	}//end if
}//end function
 /**
  * ɾ���ڵ�node���ӽڵ�
  * @param {Object} node
  * @return {TypeName} 
  */
 removeChildren = function (node) {
    if (node == null) return;
    while (node.hasChildNodes()) {
        removeChildren(node.firstChild);
        node.removeChild(node.firstChild);
        if(node.childNodes.length == 0){
        	break;
        }
    }
 }//end function
 
 function updateHeadTimer() {
	var headTimer = document.getElementById("head_timer");
	if (headTimer) {
		headTimer.innerHTML = getCurrentTimeText();
	}
	setTimeout(updateHeadTimer, 1000);
}

function getCurrentTimeText() {
	var d = new Date();
	d.setTime(d.getTime());//-timeDiff
	return d.getFullYear()+"��"+(d.getMonth()+1)+"��"+d.getDate()+"�� "+d.toLocaleTimeString();
//	return new Date().toLocaleString(); //chrome��ʽ�����ַ�������������Ҫ����ʽ
}

setTimeout(updateHeadTimer, 1000);

function loadFirstNode(node){
	Ext.getCmp("doc-body").loadClass(node.attributes.href,node.id,true);
}

//��������û�ע��ҳ��
function loadAddUserTab(){
   Ext.getCmp("doc-body").loadClass("user/addUser.jsp", "�û�����.����û�",true);
}
