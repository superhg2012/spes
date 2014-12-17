Ext.ns("Ext.hg");

Ext.hg.EvaluateSheetWindow = Ext.extend(Ext.Window, {
	closable : true,
	title : '�½����˱�',
    modal : true,
    constrain : true,
    align : 'center',
    autoShow : true,
    id : 'evaluateSheetWin',
    sheetId : '',
    width  : 400,
    height : 260,
    
	bodyStyle: {
		background :'white',
		padding: '10px'
	},

    initComponent : function(){
    	Ext.hg.EvaluateSheetWindow.superclass.initComponent.call(this);
    },

    listeners: {
    	beforeShow : function() {
    		var self = this;
    		self.createSheetForm();
    	}
    },

    createSheetForm : function() {
    },

    renderTo : Ext.getBody()
});

Ext.reg("evaluateWindow", Ext.hg.EvaluateSheetWindow);
