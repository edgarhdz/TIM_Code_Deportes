
(function(window){
    var VideoGalleryManager=VideoGalleryManager || {};

    window.YoutubeVideoGalleryManager=VideoGalleryManager;
    
    

    var player;
    var galleryDisplayed=false;
    var apiReady;



    VideoGalleryManager.initialize=function(videoID){
        VideoGalleryManager.videoID=videoID;
        var tag = document.createElement('script');
        tag.src = "//www.youtube.com/iframe_api";
        var firstScriptTag = document.getElementsByTagName('script')[0];
        firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

       

        /*Functions that should be executed when the document is ready*/
        $(function(){
          
            $(window).resize(function() {
                if($(window).width() < 620)
                    if( player){
                        player.stopVideo();
                    }
                var dimensions = VideoUtils.getPlayerDimensions();
                player.setSize(dimensions.width, dimensions.height);
                VideoUtils.setPlayerSize1();
            });
            VideoUtils.setPlayerSize1();
        })
    }

    /*When the YOUTube Api is ready (after initialize) it calls a function attached to the window called
     * onYouTubePlayerAPIReady
     * */
    window.onYouTubePlayerAPIReady=function() {
        var playerOptions = {
            height : 351,
            width : 624,
            videoId : VideoGalleryManager.videoID,
            playerVars : {
                controls : 1,
                showinfo : 0,
                enablejsapi : 1,
                rel : 0,
                showsearch : 0,
                iv_load_policy : 3,
                cc_load_policy : 3,
                autoplay : 0,
                modestbranding : 1
            },
            events : {
                'onReady' : onPlayerReady
            }
        };
        player = new YT.Player(VideoGlobals.videoContainerID, playerOptions);
        VideoGalleryManager.player=player;
    }
    
    function playVideo(data,prepareVideoDisplay){

        player.clearVideo();
        player.loadVideoById(data.videoId);
        prepareVideoDisplay();
    }
    
    function onPlayerReady(event) {
        apiReady=true;
        /*Only call playVideo method when the device isn't a iPhone/iPad because the iOS have
         one restriction that require the user intereaction doing click/touch
         in the video play icon, otherwise the video will not  load.*/
        
        $carrouselLinks = $(VideoGlobals.carrouselLinkSelector);        
        var prepareVideoDisplayFunction = VideoUtils.prepareVideoDisplay;
      //if we are in the context of gallery
        if($carrouselLinks.size()>0){        	
        	   VideoGlobals={
        			videoPlayButtonSelector:'.vid_4vids_05 .td_bg',
        			videoContainerSelector:'#theaterContainer',
        			videoContainerID:'contenedor',
        			carrouselLinkSelector:'.vid_4vids_05 ul.ulcarrusel li a'
        		};
        	   prepareVideoDisplayFunction = VideoUtils.prepareGalleryVideoForDisplay; 
        	   
        	   /*Thumbnails events*/
        	   $carrouselLinks.click(function(){
                   var url=this.href;
                   VideoUtils.getVideoData(url,function(data){
                       if(apiReady){
                           playVideo(data,prepareVideoDisplayFunction);
                       }
                       else{
                           setTimeout(function(){
                               playVideo(data,prepareVideoDisplayFunction);
                           },1000);
                       }
                   });
               });  

        }

        $(VideoGlobals.videoPlayButtonSelector).click(function(event){
            event.stopImmediatePropagation();
            event.preventDefault();
            if(!VideoUtils.isIpad()){
                player.playVideo();
            }
            prepareVideoDisplayFunction();
            event.preventDefault();
            var dimensions = VideoUtils.getPlayerDimensions();
            player.setSize(dimensions.width, dimensions.height);
        });     
       
    }
})(window);