
function sourceBeforeLoadContent(field, record, path){

    var configurationURL = '/content/televisa/noticieros/configuration/jcr:content/source.json' //the path to the config page where the source component is located.
    //set the value of the field
    var targetField=field.getName().replace('./',''); //the name of the property.
    var response = CQ.utils.HTTP.get(path +'.json'); //we get a json with all the component's properties
    eval('var data ='+response.responseText);
    field.defaultValue = data[targetField]; //we get the value of the property and put is as a default value.
    field.setValue(data[targetField]);

    //get the options from the config component
    var fieldName = "sources"; //the name of the property.
    /* TODO Replace for real path */
    var response = CQ.utils.HTTP.get(configurationURL); //we get a json with all the component's properties
    eval('var data ='+response.responseText);    
    var sources = data[fieldName]; //we get the value of the property and put is as a default value.
    var options = [];

    for(var i = 0; i < sources.length; i++){
        var sourcesArray = sources[i].split("||");
        var source = sourcesArray[0];
        if(sourcesArray[1] == "activated"){
            options.push({
                value: source,
                text: source,
                qtip: source
            });         
        }
    }
    field.setOptions(options); //set the object as the selection's options*/
    return false; 
}