
/**
 * 主面板
 * @memberOf {TypeName} 
 */
Docs = {}; //树形数据

MainPanel = function(){
	
	this.searchStore = new Ext.data.Store({
        proxy: new Ext.data.ScriptTagProxy({
            url: 'http://extjs.com/playpen/api.php'
        }),
        reader: new Ext.data.JsonReader({
	            root: 'data'
	        }, 
			['cls', 'member', 'type', 'doc']
		),
		baseParams: {},
        listeners: {
            'beforeload' : function(){
                this.baseParams.qt = Ext.getCmp('search-type').getValue();
            }
        }
    }); 
	
	//暂时不用
	this.store = new Ext.data.Store({
		 proxy : new Ext.data.HttpProxy({
			  url : 'userAction!findAll.action',
			  method  : 'POST'
		 }), 
		 
		 reader : new Ext.data.JsonReader({
			 totalProperty : 'totalRecords',
			 root : 'rows',
			 id : 'id',
			 fields : ['gradeId','grade']
		 }),
		 remoteSort : true
	});
	
    MainPanel.superclass.constructor.call(this, {
        id:'doc-body',
        region:'center',
        margins:'0 0 27 0',
        resizeTabs: true,
        minTabWidth: 135,
        tabWidth: 135,
        plugins: new Ext.ux.TabCloseMenu(),
        enableTabScroll: true,
        activeTab: 0,
        frame : false
      /*
       * items: {
            id:'welcome-panel',
            title: 'index',
            autoLoad: {url: 'welcome.jsp', callback: this.initSearch, scope: this},
            iconCls:'icon-docs',
            autoScroll: true,
        }
        * */
        
    });
};


Ext.extend(MainPanel, Ext.TabPanel, {

    initEvents : function(){
        MainPanel.superclass.initEvents.call(this);
        this.body.on('click', this.onClick, this);
    },

    onClick: function(e, target){
        if(target = e.getTarget('a:not(.exi)', 3)){
            var cls = Ext.fly(target).getAttributeNS('ext', 'cls');
            e.stopEvent();
            if(cls){
                var member = Ext.fly(target).getAttributeNS('ext', 'member');
                this.loadClass(target.href, cls, member);
            }else if(target.className == 'inner-link'){
                this.getActiveTab().scrollToSection(target.href.split('#')[1]);
            }else{
                window.open(target.href);
            }
        }else if(target = e.getTarget('.micon', 2)){
            e.stopEvent();
            var tr = Ext.fly(target.parentNode);
            if(tr.hasClass('expandable')){
                tr.toggleClass('expanded');
            }
        }
    },

    loadClass : function(href, cls, member){
        var id = 'docs-' + cls; //cls==node.id
        var tab = this.getComponent(id);
        if(tab){//如果已经加载了此面板，则无需再重新加载。
            this.setActiveTab(tab);
            if(member){
                tab.scrollToMember(member);
            }
        }else{
            var autoLoad = {url: href, scripts:true};
            if(member){
                autoLoad.callback = function(){
                    Ext.getCmp(id).scrollToMember(member);
                }
            }
            if(cls == "系统首页.系统首页"){
            	var p = this.add(new DocPanel({
	                id: id,
	                cclass : cls,//原数据Id
	                autoLoad: autoLoad,
	                closable : false,
	                iconCls: Docs.icons[cls]//图标
           	   }));
            	this.setActiveTab(p);
            	return;
            }
            var p = this.add(new DocPanel({
                id: id,
                cclass : cls,//原数据Id
                autoLoad: autoLoad,
                iconCls: Docs.icons[cls]//图标
            }));
            this.setActiveTab(p);
        }
    },
	
	initSearch : function(){
    	/**
    	 * 模板
    	 * @param {Object} e
    	 * @memberOf {TypeName} 
    	 */
		// Custom rendering Template for the View
	 
//    	 var resultTpl = new Ext.XTemplate(
//	        '<tpl for=".">',
//	        '<div class="search-item">',
//	            '<a class="member" ext:cls="{cls}" ext:member="{member}" href="./user/{cls}.jsp">',
//				'<img src="resources/images/default/s.gif" class="item-icon icon-{type}"/>{member}',
//				'</a> ',
//				'<a class="cls" ext:cls="{cls}" href="output/{cls}.html">{cls}</a>',
//	            '<p>{doc}</p>',
//	        '</div></tpl>'
//	    );
		/*
		var p = new Ext.DataView({
            applyTo: 'search',
			tpl: resultTpl,
			loadingText:'Searching...',
            store: this.searchStore,
            itemSelector: 'div.search-item',
			emptyText: '<h3>Use the search field above to search the Ext API for classes, properties, config options, methods and events.</h3>'
        });
		*/
	},
	
	doSearch : function(e){
		var k = e.getKey();
		if(!e.isSpecialKey()){
			var text = e.target.value;
			if(!text){
				this.searchStore.baseParams.q = '';
				this.searchStore.removeAll();
			}else{
				this.searchStore.baseParams.q = text;
				this.searchStore.reload();
			}
		}
	}
});
