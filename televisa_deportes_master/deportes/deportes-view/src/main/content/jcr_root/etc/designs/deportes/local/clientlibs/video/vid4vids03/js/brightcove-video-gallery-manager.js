(function(window){
	var Vid3GalleryManager=Vid3GalleryManager || {};
	var galleryVideos;	
	var html5VideoPlayer;
	var $playerContainer;
    var objHtml5;
	var galleryDisplayed = false;
	var ipadConfig;	
	
	/*We expose the gallery through the global namespace*/
	window.BrightCoveVid3GalleryManager=Vid3GalleryManager;

	Vid3GalleryManager.initialize=function(pgalleryVideos,pBrightcoveconfig,pIpadConfig){
		galleryVideos=pgalleryVideos;
		ipadConfig=pIpadConfig;


	    if(!Vid3Utils.isIpad()) { 
	        var params = pBrightcoveconfig;
	        BCPlayerHandlerVid3.properties = params;
	        BCPlayerHandlerVid3.createVideoObject(params);

	    }else{
	        document.getElementById(Vid3Globals.videoContainerID).innerHTML = '<div id="videoSplash" class="sectionContainer" style="display:none;"></div>';
	        $(document).ready(function () {
	        	InitIpad();
	        });
	    }   

	    $(function(){

		    $(window).resize(function(){
		    	   var dimensions =Vid3Utils.getPlayerDimensions();
	               $("#myExperience").width( dimensions.width );
	               $("#myExperience").height( dimensions.height );
	               Vid3Utils.setPlayerSize1();
		    });
		    Vid3Utils.setPlayerSize1();
	    });    
	}

    Vid3GalleryManager.onClickItem=function(){
        $(Vid3Globals.carrouselLinkSelector).click(function(event){
            event.preventDefault();
            event.stopImmediatePropagation();
            var url=this.href;
            var videoIndex = $(this).data("index");			
            if (Vid3Utils.isIpad() && galleryVideos.length > 0) {
                var data=galleryVideos[videoIndex];
                videoImage = data.thumbnail;
                renderBrightcoveVideoPlayer(data);
            } else {
                var data=galleryVideos[videoIndex];
                Vid3Utils.prepareVideoDisplay();
                renderBrightcoveVideoPlayer(data);
            }
        });
    }

	function renderBrightcoveVideoPlayer(data){
		if(data.origin == 8 && Vid3Utils.isIpad()){
			createHTML5Player(data, false);                
        } else if(data.origin == 8){
            BCPlayerHandlerVid3.changeVideo(data);
        }
	}
	
	function InitIpad() {
        document.getElementById('videoSplash').innerHTML =contenidoIpad;
        document.getElementById('videoSplash').style.display = 'block';
		$playerContainer = $("#videoContainer")		
        var dimensions = Vid3Utils.getMobilePlayerSize();
        createHTML5Player(ipadConfig, true);
    }
	
	function createHTML5Player(properties, firstTime) {
        objHtml5 = new HTML5Player(properties);

        if (!firstTime && Vid3Utils.galleryDisplayed) {
            Vid3Utils.prepareVideoDisplay();
        }

        if( Vid3Utils.isIpad() && (properties.origin == 8 && !properties.ism3 )){
            objHtml5.CreatePlayer('videoPlayer');
        }else if(isiPad && (properties.origin == 8 && properties.ism3)){
            objHtml5.CreatePlayerM3('videoPlayer', properties.m3u8);
        }else {
            objHtml5.VideoNotAvailable('videoPlayer');
            papa = document.getElementById('videoSplash'); 
            if(papa.childNodes[0]){
                hijo = papa.childNodes[0];
                hijo.style.height = "100%";
            }
        }

        html5VideoPlayer = $("video", $playerContainer)[0]; //Get the video html5 element
        if (firstTime) {
            html5VideoPlayer.pause(); //To avoid the video autoplay
        } else {
            html5VideoPlayer.play();
        }
	}

})(window);
