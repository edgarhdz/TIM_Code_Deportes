
function geoFilterBeforeLoadContent(field, record, path){
    var targetField=field.getName().replace('./',''); //the name of the property.
    var response = CQ.utils.HTTP.get(path +'.json'); //we get a json with all the component's properties
    eval('var data ='+response.responseText);
    field.defaultValue = data[targetField]; //we get the value of the property and put is as a default value.
    field.setValue(data[targetField]);
    var options = [];
    var propertyName = "geoFilterOptions"; //the name of the design property we are looking for
    var pagePath = CQ.WCM.getPagePath(); //gets the path of the current page
    var design = CQ.WCM.getDesign(pagePath); //gets a Design object
    var editableCell = CQ.WCM.getEditable(path).getCell(); //gets an editable cell
    var geoFilterOptions = design.getStyleProperty(editableCell, propertyName); //gets the value of the design property we are looking for (a String array)
    if(geoFilterOptions instanceof Array){
         for (var i = 0; i < geoFilterOptions.length; i++) {
        var geoFilterArray = geoFilterOptions[i].split("||");//split the string to obtain the geo filter value and its md5 hash
        var geoFilter = geoFilterArray[0];
        var md5 = geoFilterArray[1];
        options.push({
                "value": md5,
                "text": geoFilter,
                "qtip": "MD5 Hash: " + md5
            });        
        }         
    }else{
        var geoFilterArray = geoFilterOptions.split("||");//split the string to obtain the geo filter value and its md5 hash
        var geoFilter = geoFilterArray[0];
        var md5 = geoFilterArray[1];
        options.push({
                "value": md5,
                "text": geoFilter,
                "qtip": "MD5 Hash: " + md5
            });       
    }
   
    field.setOptions(options); //set the object as the selection's options
    return false; 
}