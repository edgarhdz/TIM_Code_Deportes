(function(window){
	var VideoGalleryManager=VideoGalleryManager || {};
	var galleryVideos;	
	var html5VideoPlayer;
	var $playerContainer;
    var objHtml5;
	var galleryDisplayed = false;
	var ipadConfig;	
	
	
	var contenidoIpad = '<div id="videoContainer">'+
    '<!-- código de player -->'+
    '<div id="videoPlayer">'+
    '<a class="player" id="ToothPlayer"></a>'+
    '</div>'+
    '<!-- código de player -->'+
    '</div>'+
    '<div id="epg"></div>';
	
	/*We expose the gallery through the global namespace*/
	window.BrightCoveVideoGalleryManager=VideoGalleryManager;
	
	VideoGalleryManager.initialize=function(pgalleryVideos,pBrightcoveconfig,pIpadConfig){
		galleryVideos=pgalleryVideos;
		ipadConfig=pIpadConfig;
		

	    if(!VideoUtils.isIpad()) { 
	        var params = pBrightcoveconfig;
	        BCPlayerHandler.properties = params;
	        BCPlayerHandler.createVideoObject(params);

	    }else{
	        document.getElementById(VideoGlobals.videoContainerID).innerHTML = '<div id="videoSplash" class="sectionContainer" style="display:none;"></div>';
	        $(document).ready(function () {
	        	InitIpad();
	        });
	    }   
	   
	    $(function(){
	    	
	    	
	    	 $carrouselLinks = $(VideoGlobals.carrouselLinkSelector);        
	         var prepareVideoDisplayFunction = VideoUtils.prepareVideoDisplay;
	         //if we are in the context of video gallery
	         if($carrouselLinks.size()>0){  
	        	  VideoGlobals={
	          			videoPlayButtonSelector:'.vid_4vids_05 .td_bg',
	          			videoContainerSelector:'#theaterContainer',
	          			videoContainerID:'contenedor',
	          			carrouselLinkSelector:'.vid_4vids_05 ul.ulcarrusel li a'
	          		};
	          	   prepareVideoDisplayFunction = VideoUtils.prepareGalleryVideoForDisplay;
	          	   
		          	 $carrouselLinks.click(function(){
		 	           	var url=this.href;
		 	        	var videoIndex = $(this).data("index");			
		 				if (VideoUtils.isIpad() && galleryVideos.length > 0) {
		 					var data=galleryVideos[videoIndex];
		 	                videoImage = data.thumbnail;
		 	                renderBrightcoveVideoPlayer(data);
		 	            } else {
		 					VideoUtils.getVideoData(url, function(data){
		 						renderBrightcoveVideoPlayer(data);
		 					});
		 	            }
		 	        });
	         }
	    	
	    	/*Attach the event handlers*/
		    $(VideoGlobals.videoPlayButtonSelector).click(function(event){
				if (VideoUtils.isIpad() && html5VideoPlayer !== undefined) {
		        	html5VideoPlayer.play();
		        }
		        prepareVideoDisplayFunction();
		        BCPlayerHandler.changeVideo( { pauseVideo: false } );
		        event.preventDefault();
		        event.stopImmediatePropagation();
		    });
		    
		   
		    
		    $(window).resize(function(){
		    	   var dimensions =VideoUtils.getPlayerDimensions();
	               $("#myExperience").width( dimensions.width );
	               $("#myExperience").height( dimensions.height );
	               VideoUtils.setPlayerSize1();
		    });
		    VideoUtils.setPlayerSize1();
	    });       

	}
	
	function renderBrightcoveVideoPlayer(data){
		if(data.origin == 8 && VideoUtils.isIpad()){
			createHTML5Player(data, false);                
        } else if(data.player == 8){
            BCPlayerHandler.changeVideo(data);
        }
	}
	
	function InitIpad() {
        document.getElementById('videoSplash').innerHTML =contenidoIpad;
        document.getElementById('videoSplash').style.display = 'block';
		$playerContainer = $("#videoContainer")		
        var dimensions = VideoUtils.getMobilePlayerSize();
        createHTML5Player(ipadConfig, true);
    }
	
	function createHTML5Player(properties, firstTime) {

        objHtml5 = new HTML5Player(properties);

        if (!firstTime && VideoUtils.galleryDisplayed) {
            VideoUtils.prepareVideoDisplay();
        }

        if( VideoUtils.isIpad() && (properties.origin == 8 && !properties.ism3 )){
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