/*Video utilities*/
(function(window,$){
    var VideoUtils=VideoUtils || {};
    var galleryDisplayed=false;
    var esmas_dart_ord = Math.floor( Math.random() * 1E16 );
    VideoUtils.galleryDisplayed=galleryDisplayed;

    //This variable is the only dependency of the vid_4vids_01 script
    var vid_4vids_01_player=vid_4vids_01_player || {
        width:624,
        height:370
    };
    /*When the document is ready generic functions*/
    $(function(){
        //$(VideoGlobals.videoContainerSelector).hide();
    });


    window.VideoUtils=VideoUtils;

    VideoUtils.getVideoData=function(url,callback){
        var path =url.replace(/.html/,"") + "/jcr:content/video.data.json";
        $.getJSON(path,function(data){
            callback(data);
        });
    }

    VideoUtils.isIpad=function(){
        var ret = false;
        var isiPad = navigator.userAgent.match(/iPad/i) != null;
        var isiPhone = navigator.userAgent.match(/iPhone/i) != null;
        var isiPod = navigator.userAgent.match(/iPod/i) != null;
        var isAndroid = navigator.userAgent.match(/Android/i) != null;
        if (isiPad || isiPhone || isiPod || isAndroid) {
            ret = true;
        }
        return ret;
    }

    VideoUtils.resetStageImage=function(options){

    }

    VideoUtils.prepareVideoDisplay=function(){
        if (!galleryDisplayed) {
            $('.vid_4vids_01 .overlay').hide();
            $('.vid_4vids_01 #img_stage_01_IMG').hide();
            $(VideoGlobals.videoPlayButtonSelector).hide();
            galleryDisplayed = true;
            //$(VideoGlobals.videoContainerSelector).show();
            $('.vid_4vids_01 .imgContainer #theaterContainers').removeClass("hide");
            $('.vid_4vids_01 .imgContainer #theaterContainers').addClass("show");
            $('.vid_player_01_image #theaterContainers').removeClass("hide");
            $('.vid_player_01_image #theaterContainers').addClass("show");
            $('.vid_player_01 #img_stage_01_IMG').hide();

        }
    }
    
    
    VideoUtils.prepareGalleryVideoForDisplay = function(){
    	 if (!galleryDisplayed) {        
             $(VideoGlobals.videoPlayButtonSelector).hide();
             $('.vid_4vids_05 #theaterContainer').removeClass("hide");
             $('.vid_4vids_05 #theaterContainer').addClass("show");
             $(".vid_4vids_05 .mainimage").hide()
             galleryDisplayed = true;
         }
    }

    VideoUtils.getPlayerDimensions=function(){
        var dimensions = new Object();
        var windowSize = VideoUtils.getWindowSize();
        if(windowSize.type == 'desktop' || windowSize.type == 'tablet'){
            dimensions.height = vid_player_01_player.height;
            dimensions.width = vid_player_01_player.width;
        }else if (windowSize.type == 'mobile'){
            dimensions.width = $(window).width();
            dimensions.height = (dimensions.width * vid_player_01_player.height) / vid_player_01_player.width;
        }
        return dimensions;
    }

    VideoUtils.getWindowSize=function(){
        var me = new Object();

        if ($.browser.msie && parseInt($.browser.version, 10) <= 7){
            me.width = document.body.offsetWidth;
            me.height = document.body.offsetHeight;
        }
        else{
            me.width =  $(window).width();
            me.height =  $(window).height();
        }

        if(me.width >= 960)
            me.type = 'desktop';
        else if(me.width < 960 && me.width >= 624)
            me.type = 'tablet';
        else if(me.width < 624)
            me.type = 'mobile';

        return me;
    }


    VideoUtils.getMobilePlayerSize=function(){
        var anchoVentana;

        if ($.browser.msie && parseInt($.browser.version, 10) <= 7){
            anchoVentana = document.body.offsetWidth;
        }
        else{
            anchoVentana =  $(window).width();
        }
        if( anchoVentana <= 590 ){
            // proporcion entre el ancho y alto fijos con el ancho variable
            var altoVentana = (anchoVentana * vid_player_01_player.height) / vid_player_01_player.width;
        }
        var dimensions = new Object();
        dimensions.height = altoVentana;
        dimensions.width = anchoVentana;

        return dimensions;
    }

    VideoUtils.setPlayerSize=function(){

        if ($.browser.msie && parseInt($.browser.version, 10) <= 7){
            var anchoVentna = document.body.offsetWidth;
            //alert( anchoVentna );
        }
        else{
            var anchoVentna =  $(window).width();
        }

        // Si el version movil obtenemos el alto y ancho de la imagen para adaptar el tama√±o del player
        if( anchoVentna <= 590 ){

            // proporcion entre el ancho y alto fijos con el ancho variable
            var altoVentana = (anchoVentna * vid_4vids_01_player.height) / vid_4vids_01_player.width;

            $('.vid_4vids_01 #myExperience').width( anchoVentna );
            $('.vid_4vids_01 #myExperience').height( altoVentana );

            $('.vid_4vids_01 #contenedor').width( anchoVentna );
            $('.vid_4vids_01 #contenedor').height( altoVentana );
        }
        else{

            $('.vid_4vids_01 #myExperience').width( vid_4vids_01_player.width );
            $('.vid_4vids_01 #myExperience').height( vid_4vids_01_player.height );

            $('.vid_4vids_01 #contenedor').width( vid_4vids_01_player.width );
            $('.vid_4vids_01 #contenedor').height( vid_4vids_01_player.height );

        }
    }

    VideoUtils.setPlayerSize1=function(){
        function setPlayerSize1(){

            if ($.browser.msie && parseInt($.browser.version, 10) <= 7){
                var anchoVentna = document.body.offsetWidth;

            }
            else{
                var anchoVentna =  $(window).width();
            }

            // Si el version movil obtenemos el alto y ancho de la imagen para adaptar el tamaño del player
            if( anchoVentna <= 590 ){

                // proporcion entre el ancho y alto fijos con el ancho variable
                var altoVentana = (anchoVentna * vid_player_01_player.height) / vid_player_01_player.width;

                $('.vid_player_01 #myExperience').width( anchoVentna );
                $('.vid_player_01 #myExperience').height( altoVentana );

                $('.vid_player_01 #contenedor').width( anchoVentna );
                $('.vid_player_01 #contenedor').height( altoVentana );

            }
            else{

                $('.vid_player_01 #myExperience').width( vid_player_01_player.width );
                $('.vid_player_01 #myExperience').height( vid_player_01_player.height );

                $('.vid_player_01 #contenedor').width( vid_player_01_player.width );
                $('.vid_player_01 #contenedor').height( vid_player_01_player.height );

            }
        }
    }

    /*TODO:hacerlo parte de utils y aplicarlo en brightcove-video-gallery*/
    VideoUtils.generateAdServerURL=	function (){

        var paramSite = "${paramSite}";
        var paramBits = "${paramBits}";

        var withAd = true;
        var adUrlParam = "";

        if(paramBits == '0000'){
            paramSite = '';
        }

        if(paramBits == null){
            paramBits = '0000';
        }

        var arraySite = paramSite.split("/");
        var arrayBits = paramBits.split(""); // to have an array like this --> [0][0][0][0]

        var site = paramSite;
        if(arraySite.length > 1){
            site = arraySite[0];
            if(arrayBits[1]=='1'){
                site = arraySite[0] + ".mov.vast";
            }
            //site = site + "/" + arraySite[1];
            for ( i = 1; i < arraySite.length; i++) {
                site += "/" + arraySite[i];
            }
        }

        var urlAdRule = "&ad_rule=1";
        if( arrayBits[0] == '0'){

            urlAdRule = "&ad_rule=0";
        }

        cust_paramTXT = "&cust_params=hasoverlay%3D";
        hasOverValid = "false";
        if( arrayBits[2] == '1'){
            hasOverValid = "true";
        }
        cust_paramTXT += hasOverValid;
        cust_paramTXT += "%26player%3Dbc";
        if( arrayBits[3] === '0'){
            withAd = false;
        }

        var urlAdsWeb = "http://pubads.g.doubleclick.net/gampad/ads?";
        urlAdsWeb += "sz=1x1";
        urlAdsWeb += "&ciu_szs=300x250,1x1";
        urlAdsWeb += "&impl=s";
        urlAdsWeb += "&gdfp_req=1";
        urlAdsWeb += "&env=vp";
        urlAdsWeb += "&output=xml_vast2";
        urlAdsWeb += "&unviewed_position_start=1";
        urlAdsWeb += "&url=tvolucion.esmas.com";

        if(withAd){
            adUrlParam = urlAdsWeb + "&iu=/5644/" + site + urlAdRule + cust_paramTXT;
        }

        var adURL = adUrlParam + "&correlator=" + esmas_dart_ord;

        return adURL;
    }


})(window,$);

/*Brightcove Player Handler*/
(function(window){
    //Brightcove Player Handler
    var BCPlayerHandler = BCPlayerHandler || {};
    window.BCPlayerHandler=BCPlayerHandler;
    BCPlayerHandler.firstVideo = true;
    BCPlayerHandler.pauseVideo = true;

    BCPlayerHandler.changeVideo = function(properties){

        if (properties.pauseVideo !== 'undefined' && BCPlayerHandler.pauseVideo) {
            BCPlayerHandler.pauseVideo = properties.pauseVideo;
        }

        if (properties.videoId) {
            BCPlayerHandler.videoId = properties.videoId;
        } else {
            BCPlayerHandler.videoId = BCPlayerHandler.properties.videoId;
        }

        if (BCPlayerHandler.playerReady && !BCPlayerHandler.firstVideo) {
            //BCPlayerHandler.videoPlayer.loadVideoByID(properties.videoId);
            BCPlayerHandler.videoPlayer.loadVideo(properties.videoId);
        } else {
            VideoUtils.prepareVideoDisplay();
        }

        VideoUtils.setPlayerSize();
    }

    //Template loaded event handler
    BCPlayerHandler.onTemplateLoad = function (experienceID) {
        // get references to the player and API Modules and Events
        BCPlayerHandler.player = brightcove.getExperience("myExperience");
        BCPlayerHandler.APIModules = brightcove.api.modules.APIModules;

        BCPlayerHandler.videoPlayer = BCPlayerHandler.player.getModule(BCPlayerHandler.APIModules.VIDEO_PLAYER);
        BCPlayerHandler.modExp = BCPlayerHandler.player.getModule(BCPlayerHandler.APIModules.EXPERIENCE);

        BCPlayerHandler.videoPlayer.addEventListener(BCVideoEvent.START_BUFFER, function (event) {
            if (BCPlayerHandler.firstVideo) {
                if (!BCPlayerHandler.pauseVideo) {
                    BCPlayerHandler.firstVideo = false;
                } else {
                    BCPlayerHandler.videoPlayer.pause();
                }

                if (BCPlayerHandler.videoId !== BCPlayerHandler.properties.videoId) {
                    BCPlayerHandler.firstVideo = false;
                    BCPlayerHandler.changeVideo( { videoId: BCPlayerHandler.videoId } );
                }
            }
        });
    };

    //Template ready event handler
    BCPlayerHandler.onTemplateReady = function (event) {
        BCPlayerHandler.playerReady = true;
    };


    BCPlayerHandler.createVideoObject = function(params){

        BCPlayerHandler.videoId = params.videoId;

        var videoObject = $('<object></object>').attr("id","myExperience").addClass("BrightcoveExperience");

        videoObject.append($('<param />').attr({"name" : "bgcolor" , "value" : params.bgcolor}));
        videoObject.append($('<param />').attr({"name" : "width" , "value" : params.width}));
        videoObject.append($('<param />').attr({"name" : "height" , "value" : params.height}));
        videoObject.append($('<param />').attr({"name" : "playerID" , "value" : params.playerID}));
        videoObject.append($('<param />').attr({"name" : "publisherID" , "value" : params.publisherID})); // HARDCODED, REFERENCE TO THE USER IN BrightCove (NEVER CHANGE)
        videoObject.append($('<param />').attr({"name" : "playerKey" , "value" : params.playerKey}));
        videoObject.append($('<param />').attr({"name" : "isVid" , "value" : params.isVid}));
        videoObject.append($('<param />').attr({"name" : "isUI" , "value" : params.isUI}));
        videoObject.append($('<param />').attr({"name" : "dynamicStreaming" , "value" : params.dynamicStreaming}));
        videoObject.append($('<param />').attr({"name" : "@videoPlayer" , "value" : params.videoId}));
        videoObject.append($('<param />').attr({"name" : "wmode" , "value" : params.wmode}));
        videoObject.append($('<param />').attr({"name" : "linkBaseURL" , "value" : params.linkBaseURL}));
        videoObject.append($('<param />').attr({"name" : "autoStart" , "value" : params.autoStart}));
        videoObject.append($('<param />').attr({"name" : "profile", "value" : params.profile}));
        videoObject.append($('<param />').attr({"name" : "quality", "value" : params.quality}));
        videoObject.append($('<param />').attr({"name" : "adServerURL", "value" :VideoUtils.generateAdServerURL()}));
        videoObject.append($('<param />').attr({"name" : "includeAPI", "value" : "true"}));
        videoObject.append($('<param />').attr({"name" : "templateLoadHandler", "value" : "BCPlayerHandler.onTemplateLoad"}));
        videoObject.append($('<param />').attr({"name" : "templateReadyHandler", "value" : "BCPlayerHandler.onTemplateReady"}));
        videoObject.append($('<param />').attr({"name" : "templateErrorHandler", "value" : "BCPlayerHandler.onTemplateError"}));

        $('#contenedor').html(videoObject);
    }
})(window);

