
/*Toggles unused note fielsd using values of carrousel mode type (manual/automatic)*/
function toggleMixCarru02UnusedFieldsFromCarrouselTypeSelection(selection){
	var panel=selection.findParentByType("panel");
	panel.findBy(function(component){
		var cName=component.name;
		var value=component.value
		if(cName!=null&&value!=null){
			if(cName=="./carrouselType"&&(value=="carru01"||value=="carru02")){
				toggleMixCarru02UnusedNotesFields(component,component.getValue())
			}
		}
	});
}


/*Toggles unused note fields using values of carrousel type selection*/
function toggleMixCarru02UnusedNotesFields(selection, value, checked){
	var panel=selection.findParentByType("panel");
	var mixCarru02UnusedNotes=panel.findBy(findUnusedMixCarru02NoteFields);	
	CQ.Ext.each(mixCarru02UnusedNotes,function(unusedMixCarru02PathField){
		if(value=="carru01"){
			unusedMixCarru02PathField.show();
		}
		else if(value=="carru02"){
			unusedMixCarru02PathField.hide();
		}
	});

}



var findUnusedMixCarru02NoteFields=function(component){
	if(component.name!==null){
		var componentName=component.name;
		var isMixCarru02UnusedNote=(componentName==="./fourthPage")
									||(componentName==="./fifthPage")
									||(componentName==="./sixthPage");
		if(isMixCarru02UnusedNote){
			return true;
		}
	}
	
};