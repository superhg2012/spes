
/**
 * ����������
 * @memberOf {TypeName} 
 */
LeftTreePanel = function(){
	LeftTreePanel.superclass.constructor.call(this, {
        title : '�����˵�',
		id : 'left-tree',
		region : 'west',
		width: 240,
        minSize: 175,
        maxSize: 360,
        collapsible: true,
        rootVisible:false,
        margins:'0 0 5 5',
        cmargins:'0 5 5 5',
        lines : true,
        frame : false,
        split : true,
        border : true,
        autoScroll:true,
        animCollapse:true,
        layoutConfig:{
                animate:true
        },
        //loader
  /*      loader: new Ext.tree.TreeLoader({
			preloadChildren: true,
			clearOnLoad: false
		}),
	*/	
		loader : new Ext.tree.TreeLoader({}),
		//�첽��ʽ����Ҷ�ӽڵ�
        root: new Ext.tree.AsyncTreeNode({
            id : 'root',
            text : '���ڵ�', 
            leaf : false,
 			expanded : true
//            children:[Docs.classData] //�ڵ�����
         }),
        collapseFirst:true,
        tbar : [
        	new Ext.form.TriggerField({
	        	hideTrigger:true,
	        	cls : 'searchField',
	        	hideTriggerClick : true,
	        	triggerClass : 'x-from-clear-trigger',
	        	onTriggerClick : function(){
		        	 this.setRawValue("");
		        	 this.focus();
	        	},
	        	enableKeyEvents : true,
	        	width : 175,
	        	emptyText : '������ؼ��ֲ�ѯ',
	        	scope : this,
	        	listeners : {
	        		//keyup : this.doQuery(),
	        		scope :this
	        	},
	        	style : "margin-left:5px"
	        })]
        
	});
	
	this.getSelectionModel().on('beforeselect', function(sm, node){
        return node.isLeaf();
    });
	
};



Ext.extend(LeftTreePanel,Ext.tree.TreePanel, {
	selectClass : function(cls){
	
	  if(cls) {
		    var parts = cls.split('.');
            var last = parts.length-1;
            var res = [];
            var pkg = [];
            for(var i = 0; i < last; i++){ // things get nasty - static classes can have .
                var p = parts[i];
                var fc = p.charAt(0);
                var staticCls = fc.toUpperCase() == fc;
                if(p == 'Ext' || !staticCls){
                    pkg.push(p);
                    res[i] = 'pkg-'+pkg.join('.');
                }else if(staticCls){
                    --last;
                    res.splice(i, 1);
                }
            }
            res[last] = cls;
            this.selectPath('/root/apidocs/'+res.join('/')); //???/
	  }
	}

});

