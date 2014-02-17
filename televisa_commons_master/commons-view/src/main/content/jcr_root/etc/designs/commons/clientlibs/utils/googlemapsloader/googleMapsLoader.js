
var GMapsLoader={};

GMapsLoader.loaded=false;

GMapsLoader.loadGoogleMapsAPI=function(myLogicCallback,options){

    if(GMapsLoader.loaded===false){
		var script_tag = document.createElement('script');
        script_tag.setAttribute('type','text/javascript');
        script_tag.setAttribute('src',"http://maps.google.com/maps/api/js?sensor=false&callback=gMapsCallback");

        (document.getElementsByTagName('head')[0] || document.documentElement).appendChild(script_tag);


		window.gMapsCallback = function(){
			myLogicCallback (google,options);
            GMapsLoader.loaded=true;
		} 
    }
    else{
        myLogicCallback(google,options);
    }
}