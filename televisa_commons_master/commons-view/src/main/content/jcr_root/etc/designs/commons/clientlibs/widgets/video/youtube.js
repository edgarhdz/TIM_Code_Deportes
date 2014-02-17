/*executes everytime the youtube ID textfield's validator is called.*/
function youtubeValidator(value){
    var returnValue = CQ.I18n.get("generics.video.youtube.validation");

    if(value.length == 11){ //check if it has the desired length
        var response = getYoutubeVideoInfo(value);
        if(response.headers.Status == 200){ //any response status other than 200 means the video ID is invalid
            returnValue = true;
        }    
    }else if(value.length == 0){
        returnValue = true;
    }
    return returnValue;
}
/* Executes every time the 'valid' event is triggered */
function youtubeValid(t){// t = this
    if(t.getValue() != ""){
        var videoInfo = getYoutubeVideoInfo(t.getValue()); //returns a string containing the selected video's information
        var videoInfoObject = parseJson(videoInfo.body); //convert from a string to an XML Object
        var secondsString = videoInfoObject.entry.media$group.yt$duration.seconds;//get the 'seconds' attribute we need
        var seconds = parseInt(secondsString);
        var time = formatTime(seconds); //converts the video duration (in seconds) to the desired format
        var title = videoInfoObject.entry.title.$t;//get the video title
        var container = t.findParentByType("dialogfieldset");
        var youtubeDuration = container.getComponent("duration"); // the 'duration' widget
        var youtubeDurationLabel = container.getComponent("durationLabel"); // the 'duration label' widget
        var youtubeVideoName = container.getComponent("videoName"); // the 'videoName' widget
        var youtubeVideoNameLabel = container.getComponent("videoNameLabel"); // the 'videoName' widget

        youtubeDuration.setValue(time);
        youtubeDurationLabel.setText(time);
        youtubeVideoName.setValue(title);
        youtubeVideoNameLabel.setText(title);
    }
}

/* converts a string into an Json Object*/
function parseJson(json){
    eval('var data =' + json);
    return data;
}

/* gets the desired video information */
function getYoutubeVideoInfo(id){
    var youtubeApiUrl = "https://gdata.youtube.com/feeds/api/videos/"; 
    var v2Parameter = "?v=2";
    var jsonParameter = "&alt=json";
    var response = CQ.utils.HTTP.get(youtubeApiUrl + id + v2Parameter + jsonParameter);
    return response;
}

/*converts from seconds to the desired duration format: hh:mm:ss */
function formatTime(totalSeconds){ 
    
    var seconds = totalSeconds%60;
    var minutes = Math.floor(totalSeconds/60);
    var hours = Math.floor(minutes/60);
    if(hours > 0) minutes = minutes%60;
    
    if(hours < 10) hours = "0" + hours;
    if(minutes < 10) minutes = "0" + minutes;  
    if(seconds < 10) seconds = "0" + seconds;
   
    return hours +":"+minutes+":"+seconds;
    
}


/* Executes every time the 'invalid' event is triggered */
function youtubeInvalid(t,msg){// t = this
    var container = t.findParentByType("dialogfieldset");
    var youtubeDurationLabel = container.getComponent("durationLabel"); // the 'duration label' widget
    var youtubeVideoNameLabel = container.getComponent("videoNameLabel"); // the 'videoName' widget

    youtubeDurationLabel.setText("");
    youtubeVideoNameLabel.setText("");    
}