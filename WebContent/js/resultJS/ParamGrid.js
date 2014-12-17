ParamGrid = Ext.extend(Ext.grid.GridPanel,{
	sheetId: '',
	selType: 'cellmodel',
	layout: 'fit',
	autoScroll: true,
	columnLines: true,
	columns: [
	  	{ text: 'OBJECT', 
	  	  dataIndex: 'object', 
	  	  width: 200, 
	  	  sortable: false, 
	  	  menuDisabled: true, 
	  	  renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {return this.renderColor(value, metaData, record, rowIndex, colIndex, store, view);}
	  	},
	  	{ text: 'CATEGORY',
	  	  dataIndex: 'category',
	  	  width: 250,
	  	  sortable: false, 
	  	  menuDisabled: true, 
	  	  renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {return this.renderColor(value, metaData, record, rowIndex, colIndex, store, view);}
	  	},
        {
          text : 'OBJECT IDENTIFIER',
          dataIndex : 'identifier',
          width : 160,
          sortable : false,
          menuDisabled : true,
          renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {return this.renderColor(value, metaData, record, rowIndex, colIndex, store, view);}
        }
        ,
	  	{ text: 'PARAMETER NAME',
	  	  dataIndex: 'paramName',
	  	  width: 200, 
	  	  sortable: false, 
	  	  menuDisabled: true, 
	  	  renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {return this.renderColor(value, metaData, record, rowIndex, colIndex, store, view);}
	  	},
	  	{ text: 'INVENTORY',
	  	  dataIndex: 'inventory',
	  	  width: 150,
	  	  sortable: false,
	  	  editor: {xtype: "textfield", allowBlank: false}, 
	  	  menuDisabled: true, 
	  	  renderer: function (value, metaData, record, rowIndex, colIndex, store, view) { return this.renderColor(value, metaData, record, rowIndex, colIndex, store, view);}
	  	},
	  	{ text: 'POLICY',
	  	  dataIndex: 'policy',
	  	  width: 150,
	  	  sortable: false,
	  	  menuDisabled: true,
	  	  renderer: function (value, metaData, record, rowIndex, colIndex, store, view) {return this.renderColor(value, metaData, record, rowIndex, colIndex, store, view);}
	  	}
	]
	,
	initComponent: function()
	{
		// Generate warning message due to activation and compatibility
		this.iconCls = 'iconWarning';
		var policyLabel = '';
		
		if (this.compatibility == 'nok' && this.activation == 'nok') {
			policyLabel = "No Policy Compliancy because of incompatible configuration between 'mobility intra/inter-freq' and 'automatic neighbour relation' measurements ! <br/> No Policy Compliancy because of activation flag which is not aligned !";
		} else if (this.compatibility == 'nok') {
			policyLabel = "No Policy Compliancy because of incompatible configuration between 'mobility intra/inter-freq' and 'automatic neighbour relation' measurements !";
		} else if (this.activation == 'nok') {
			policyLabel = "No Policy Compliancy because of activation flag which is not aligned !";
		}
		else {
			this.iconCls = '';
		}
		
		this.dockedItems =
		[
	  		{
	  			dock: 'top',
	  			xtype: 'toolbar',
	  			items:
	  			[{
                    xtype : 'button',
                    text : 'Filter NonCompliant',
                    style : 'border : solid 1px #abc',
                    handler : function (filterBtnObj){
                        var grid = filterBtnObj.findParentByType("gridpanel");
	                    var store = grid.getStore();
	                    if (store.isFiltered()) {
	                        store.clearFilter();
	                    } else {
	                        store.filter([
	                           Ext.create('Ext.util.Filter', {filterFn: function(record) { return record.get("inventory") != record.get("policy"); }, root: 'data'})
	                        ]);
	                    } 
                    }
                }
	  			,
	  			{
	  				xtype: 'tbtext',
	  				text: policyLabel,
	  				style: {
	  					width: '95%',
	  					color: 'orange'
	  			 	}
	  			}]
	  		}
	  	];
		
		this.listeners = {
		    beforeedit : function(editor, e, eOpts) {
		    	return false;
		    }
		};
		
		this.plugins = [
			                Ext.create('Ext.grid.plugin.CellEditing', {
			                	clicksToEdit: 2
			                })
		                ];
		
		 
		
		this.callParent(arguments);
	}
	,
	viewConfig: {
		stripeRows: true,
		enableTextSelection: true
	}
	,
	renderColor: function(value, metaData, record, rowIndex, colIndex, store, view) {
		var col = view.getHeaderAtIndex(colIndex);
		
		if (col.dataIndex == 'inventory') {
			
			var policyValue = record.get('policy');
			
			if (value.toString() != policyValue.toString()) {
				metaData.tdAttr = 'style="background:red !important; color: white"';
			}
			
			for(var i=0;i<store.getCount();i++){
                var model = store.getAt(i);
	    		if(model.dirty){
	    			metaData.tdAttr = 'style="background:orange !important; color: white"';
	            }
			}
		}
		if (col.dataIndex == 'category' || col.dataIndex == 'object' || col.dataIndex == 'identifier') {
			metaData.tdAttr = 'style="background:black !important; color: white"';
		}
		if (col.dataIndex == 'paramName') {
			metaData.tdAttr = 'style="background:blue !important; color: white"';
		}

		return value;
	}
});