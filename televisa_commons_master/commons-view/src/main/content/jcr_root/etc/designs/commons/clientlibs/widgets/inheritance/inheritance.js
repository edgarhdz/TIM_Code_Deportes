//selection changed
function inheritanceSelectionChanged(selection, value, checked){
	var parent = selection.findParentByType('tabpanel');
	var activeTab = selection.findParentByType('panel');
	var sibling = activeTab.nextSibling();
	if(value == "normal"){
        selection.nextSibling().hide();
		while(sibling != null){
			changeAllowBlank(sibling,false);
			parent.unhideTabStripItem(sibling);
			sibling = sibling.nextSibling();
		}
	} else if(value == "inheritance"){
        	selection.nextSibling().show();
		while(sibling != null){
			changeAllowBlank(sibling,true);
			parent.hideTabStripItem(sibling);
			sibling = sibling.nextSibling();
		}
	}
}

//load content
function inheritanceLoadContent(field, record, path) { 
	var parent = selection.findParentByType('tabpanel');
	var activeTab = selection.findParentByType('panel');
	var sibling = activeTab.nextSibling();
	if(this.getValue() == "normal"){
        	field.nextSibling().hide();
		while(sibling != null){
			changeAllowBlank(sibling,false);
			parent.unhideTabStripItem(sibling);
			sibling = sibling.nextSibling();
		}
	} else if(this.getValue() == "inheritance"){
        	field.nextSibling().show();
		while(sibling != null){
			changeAllowBlank(sibling,true);
			parent.hideTabStripItem(sibling);
			sibling = sibling.nextSibling();
		}
	}
}

//to change the allowBlank of the fields
function changeAllowBlank(sibling,allowBlank){
	var field = sibling.getComponent(0);
    while(field != null){
        if(field.xtype == "dialogfieldset"){
			changeAllowBlank(field,allowBlank);
        }
        if(field.xtype == "genericmultifield"){
			changeInGenericMultiField(field, allowBlank)
        }
        if(field.initialConfig.allowBlank !== undefined){
            if(field.xtype == "selection" && field.type == "select"){
                //field.optionsConfig.allowBlank = allowblank;
                field.comboBox.allowBlank = allowBlank;
            }else{
                field.allowBlank = allowBlank;
            }
        }
        field = field.nextSibling();
    }
}

function changeInGenericMultiField(field,allowBlank){
    //this will only work for this genericMultiField (becuase of the "internallink" in the sequence).
    for(var cont = 0; cont < field.items.items.length; cont++){
        if(field.items.items[cont].xtype == "genericmultifielditem"){
            field.items.items[cont].initialConfig.items[0].items.componentItems.internallink.allowBlank = allowBlank;
        }
    }
}
