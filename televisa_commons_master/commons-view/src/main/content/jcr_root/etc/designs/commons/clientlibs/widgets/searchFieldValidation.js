
function searchFieldValidation(template, path){
    var requestURL = path + "/jcr:content.infinity.json";
    var returnValue = false;
    var pageTemplate = Util.getProperty(requestURL, "cq:template");
    if(template == pageTemplate || path == ""){
        return true;
    }
    return returnValue;
}

function videoValidation(videoType, path){
    var returnValue = false;
    var requestURL = path + "/jcr:content/video.infinity.json";
    var fieldName = "videoPlayer"; //the name of the property.
    var pageVideoType = Util.getProperty(requestURL, "videoPlayer");
    if(videoType == pageVideoType || path == ""){
        return true;
    }
    return returnValue;
}
