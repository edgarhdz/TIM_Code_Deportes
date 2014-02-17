function linkLoadContent(field, record, path) {   

    var internalLink = field.nextSibling();
    var externalLink = internalLink.nextSibling();
    
    if(this.getValue() == "external"){
        internalLink.hide();
        externalLink.show();
    }else if(this.getValue() == "internal"){
        externalLink.hide();
        internalLink.show();
    }
}
