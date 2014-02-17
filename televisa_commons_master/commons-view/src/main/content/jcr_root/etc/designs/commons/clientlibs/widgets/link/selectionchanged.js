function linkSelectionChanged(selection, value, checked){ 

    var internalLink = selection.nextSibling();   
    var externalLink = internalLink.nextSibling();
      
    if(value == "external"){
        internalLink.hide();
        externalLink.show();
    }else if(value == "internal"){
        externalLink.hide();
        internalLink.show();
    }
}
