var formFieldTemplate=new CQ.Ext.Template(
    '<div class="x-form-item {itemCls}" tabIndex="-1">',
        '<label for="{id}" style="{labelStyle}" class="">{label}{labelSeparator}</label>',
        '<div class="x-form-element" id="x-form-el-{id}" style="{elementStyle}">',
        '</div><div class="{clearCls}"></div>',
    '</div>'
);

CQ.form.GenericAccordionMultifield= CQ.Ext.extend(CQ.form.CompositeField, {
	constructor : function(config) {
		config = config || {};   
        var toolbar=this.createToolbar(config);
        this.fieldConfig=config.fieldConfig;
        
        this.numberOfItems = new CQ.Ext.form.Hidden({
            "name": "./numberOfItems",
            "value": 0
        });
        
        var defaultItems=[];
        /*Counter field*/
        defaultItems.push(this.numberOfItems);
		/*This fields are to reset the properties*/
        for (var i = 0; i < this.fieldConfig.length; i++) {
            defaultItems.push({
                "xtype":"hidden",
                "name":this.fieldConfig[i].name + CQ.Sling.DELETE_SUFFIX
            });
        }         
        
        config = CQ.Util.applyDefaults(config, {
        	tbar:toolbar,
        	layout:"accordion",
        	anchor:"100%",
        	hideLabel:true,
        	defaults: {
        	  bodyStyle: 'padding-top:20px;padding-bottom:80px',
        	  collapsed: false,        	 
        	},
        	items:[
                     {
                         "xtype":"panel",
                         items:defaultItems,
                         hidden:true
                     }]           
        });
    
        CQ.form.GenericAccordionMultifield.superclass.constructor.call(this, config);
    },   
	setValue:function(value){
		alert(this.numberOfItems.getValue());
	},
	createToolbar:function(config){
		
		var addBtn=new CQ.Ext.Button({
			text:"Add",
			scope:this,
			handler:function(btn){		
				btn.scope.addNewItemGroupPanel();
				btn.scope.increaseNumberOfItems();
			}
		});		
		var tabFill=new CQ.Ext.Toolbar.Fill();		
		var topToolbar=new CQ.Ext.Toolbar({
			items:[tabFill,
			       	addBtn ]			
		});
		return topToolbar;
	},
	createChildToolbar:function(){
		var removeBtn=new CQ.Ext.Button({
			text:"Remove",
			scope:this,
			handler:function(btn){
				var panelToRemove=btn.findParentByType("genericaccordioncompositefield");
				btn.scope.remove(panelToRemove,true),
				btn.scope.decreaseNumberOfItems();
			}
		});		
		var tabFill=new CQ.Ext.Toolbar.Fill();		
		var childToolbar=new CQ.Ext.Toolbar({
			items:[tabFill,
			       removeBtn ]			
		});
		return childToolbar;
	},
	addNewItemGroupPanel:function(){
		var newItemGroupPanel=this.createItemGroupPanel();
		this.add(newItemGroupPanel);
		this.doLayout();
		return newItemGroupPanel;
	},
	increaseNumberOfItems:function(){
		var value=parseInt(this.numberOfItems.getValue());
		this.numberOfItems.setValue(value+1);	
	},
	decreaseNumberOfItems:function(){
		var value=parseInt(this.numberOfItems.getValue());
		this.numberOfItems.setValue(value-1);	
	},
	createItemGroupPanel:function(){
		var items=[];
		CQ.Ext.each(this.fieldConfig,function(itemFieldConfig){
			items.push(itemFieldConfig);
		});
		var toolbar=this.createChildToolbar();
		var panelConfig={
				items:items,
				tbar:toolbar,
				layout:'form',
				title:'test',				
				layoutConfig:{
					fieldTpl:formFieldTemplate,
				},
				anchor:'100%',
				scope:this,
				defaults: {
			        // applied to each contained item
			        width: 391,
			        labelStyle:"display: block;float: left;width: 150px;padding: 3px;padding-left: 0;clear: left;z-index: 2;position: relative;"
			    },			    
			    listeners:{
			    	afterrender:function(panelCmp){
			    		var previousPanel=panelCmp.previousSibling( );
			    		if(previousPanel!==null&&previousPanel.getXType()==='genericaccordioncompositefield'){
			    			previousPanel.collapse();
			    		}
			    		panelCmp.expand();		    		
			    	}
			    },
			    parentFieldConfig:this.fieldConfig
			};
		
		var panel=new CQ.form.GenericAccordionCompositeField (panelConfig);		 
		return panel;
	},
	focusFirstChildren:function(component){
		var items=panelCmp.items.items;
	  	if(items.length>0){
	  	  items[0].focus();
	  	 }
	},
    processRecord: function(record, path) {   
    	
        var oldItems = this.items;
        var values = new Array();
         oldItems.each(function(item/*, index, length*/) {
             if (item instanceof CQ.form.GenericAccordionCompositeField) {
                 this.remove(item, true);
                 this.findParentByType("form").getForm().remove(item);
             }
         }, this);                 
  
         var numItems = record.get(this.numberOfItems.getName());  
         
         if (!numItems) {
            numItems = 0;         
         }
         this.numberOfItems.setValue(numItems);
         if (numItems == 1) {
                var item = this.addNewItemGroupPanel();
                item.processRecord(record, path);
         } else {
            for (var i = 0; i < numItems; i++ ) {
                var newRecord = record.copy();
                for (c in newRecord.data) {                 
                    if (CQ.Ext.isArray(newRecord.data[c])) {
                        newRecord.data[c] = newRecord.data[c][i];           
                    }
                }
                var item =this.addNewItemGroupPanel();
                item.processRecord(newRecord, path);
            }
         }
         this.doLayout();
     }
});

CQ.Ext.reg("genericaccordionmultifield",CQ.form.GenericAccordionMultifield );