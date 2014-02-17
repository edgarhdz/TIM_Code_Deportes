
Util = {

    /* Retrieves the value of the property with <property name> from <path> */
    getProperty : function(path, propertyName){
        var response = CQ.utils.HTTP.get(path); //we get a json with all the component's properties
        var property = "";
        try{
            eval('var data ='+response.responseText);    
            property = data[propertyName]; //we get the value of the property and put is as a default value.
            property = property? property : "";
        }catch(err){
            console.log("could not parse json");
        }
        return property;
    }
};