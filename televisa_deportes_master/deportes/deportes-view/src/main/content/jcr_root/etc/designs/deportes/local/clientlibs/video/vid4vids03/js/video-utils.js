/*Video utilities*/
(function(window,$){
    var Vid3Utils=Vid3Utils || {};
    var galleryDisplayedVid3=false;
    var esmas_dart_ord = Math.floor( Math.random() * 1E16 );
    Vid3Utils.galleryDisplayed=galleryDisplayedVid3;

    //This variable is the only dependency of the vid_4vids_01 script
    var vid_4vids_01_player=vid_4vids_01_player || {
        width:624,
        height:370
    };
    /*When the document is ready generic functions*/
    $(function(){
        //$(Vid3Globals.videoContainerSelector).hide();
    });


    window.Vid3Utils=Vid3Utils;

    Vid3Utils.getVideoData=function(url,callback){
        var path =url.replace(/.html/,"") + "/jcr:content/video.data.json";
        $.getJSON(path,function(data){
            callback(data);
        });
    }

    Vid3Utils.isIpad=function(){
        var ret = false;
        var isiPad = navigator.userAgent.match(/iPad/i) != null;
        var isiPhone = navigator.userAgent.match(/iPhone/i) != null;
        var isiPod = navigator.userAgent.match(/iPod/i) != null;
        if (isiPad || isiPhone || isiPod) {
            ret = true;
        }
        return ret;
    }

    Vid3Utils.resetStageImage=function(options){

    }

    Vid3Utils.prepareVideoDisplay=function(){
	$('.vid_player_01c .vid_player_01c_image').removeClass("show");
	$('.vid_player_01c .vid_player_01c_image').addClass("hide");
        $('.vid_player_01c #theaterContainers').removeClass("hide");
        $('.vid_player_01c #theaterContainers').addClass("show");
    }

    Vid3Utils.hideVideo=function(){
        $('.vid_player_01c #theaterContainers').removeClass("show");
        $('.vid_player_01c #theaterContainers').addClass("hide");
	$('.vid_player_01c .vid_player_01c_image').removeClass("hide");
	$('.vid_player_01c .vid_player_01c_image').addClass("show");
    }

    Vid3Utils.getPlayerDimensions=function(){
        var dimensions = new Object();
        var windowSize = Vid3Utils.getWindowSize();
        if(windowSize.type == 'desktop' || windowSize.type == 'tablet'){
            dimensions.height = vid_player_01_player.height;
            dimensions.width = vid_player_01_player.width;
        }else if (windowSize.type == 'mobile'){
            dimensions.width = $(window).width();
            dimensions.height = (dimensions.width * vid_player_01_player.height) / vid_player_01_player.width;
        }
        return dimensions;
    }

    Vid3Utils.getWindowSize=function(){
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


    Vid3Utils.getMobilePlayerSize=function(){
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

    Vid3Utils.setPlayerSize=function(){
        if ($.browser.msie && parseInt($.browser.version, 10) <= 7){
            var anchoVentna = document.body.offsetWidth;
        }
        else{
            var anchoVentna =  $(window).width();
        }

        // Si el version movil obtenemos el alto y ancho de la imagen para adaptar el tama√±o del player
        if( anchoVentna <= 590 ){

            // proporcion entre el ancho y alto fijos con el ancho variable
            var altoVentana = (anchoVentna * vid_4vids_01_player.height) / vid_4vids_01_player.width;

            $('.vid_player_01c #myExperience').width( anchoVentna );
            $('.vid_player_01c #myExperience').height( altoVentana );

            $('.vid_player_01c #contenedor-OnDemand').width( anchoVentna );
            $('.vid_player_01c #contenedor-OnDemand').height( altoVentana );
        }
        else{

            $('.vid_player_01c #myExperience').width( vid_4vids_01_player.width );
            $('.vid_player_01c #myExperience').height( vid_4vids_01_player.height );

            $('.vid_player_01c #contenedor-OnDemand').width( vid_4vids_01_player.width );
            $('.vid_player_01c #contenedor-OnDemand').height( vid_4vids_01_player.height );

        }
    }

    Vid3Utils.setPlayerSize1=function(){
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

                $('.vid_player_01c #myExperience').width( anchoVentna );
                $('.vid_player_01c #myExperience').height( altoVentana );

                $('.vid_player_01c #contenedor-OnDemand').width( anchoVentna );
                $('.vid_player_01c #contenedor-OnDemand').height( altoVentana );

            }else{

                $('.vid_player_01c #myExperience').width( vid_player_01_player.width );
                $('.vid_player_01c #myExperience').height( vid_player_01_player.height );

                $('.vid_player_01c #contenedor-OnDemand').width( vid_player_01_player.width );
                $('.vid_player_01c #contenedor-OnDemand').height( vid_player_01_player.height );

            }
        }
    }

    /*TODO:hacerlo parte de utils y aplicarlo en brightcove-video-gallery*/
    Vid3Utils.generateAdServerURL=	function (){

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
    var BCPlayerHandlerVid3 = BCPlayerHandlerVid3 || {};
    window.BCPlayerHandlerVid3=BCPlayerHandlerVid3;
    BCPlayerHandlerVid3.firstVideo = false;
    BCPlayerHandlerVid3.pauseVideo = true;

    BCPlayerHandlerVid3.changeVideo = function(properties){
        if (properties.pauseVideo !== 'undefined' && BCPlayerHandlerVid3.pauseVideo) {
            BCPlayerHandlerVid3.pauseVideo = properties.pauseVideo;
        }

        if (properties.videoId) {
            BCPlayerHandlerVid3.videoId = properties.videoId;
        } else {
            BCPlayerHandlerVid3.videoId = BCPlayerHandlerVid3.properties.videoId;
        }

        if (BCPlayerHandlerVid3.playerReady && !BCPlayerHandlerVid3.firstVideo) {
            BCPlayerHandlerVid3.videoPlayer.loadVideo(properties.videoId);
        } else {
            Vid3Utils.prepareVideoDisplay();
        }

        Vid3Utils.setPlayerSize();
    }

    //Template loaded event handler
    BCPlayerHandlerVid3.onTemplateLoad = function (experienceID) {
        // get references to the player and API Modules and Events
        BCPlayerHandlerVid3.player = brightcove.getExperience("myExperience");
        BCPlayerHandlerVid3.APIModules = brightcove.api.modules.APIModules;

        BCPlayerHandlerVid3.videoPlayer = BCPlayerHandlerVid3.player.getModule(BCPlayerHandlerVid3.APIModules.VIDEO_PLAYER);
        BCPlayerHandlerVid3.modExp = BCPlayerHandlerVid3.player.getModule(BCPlayerHandlerVid3.APIModules.EXPERIENCE);

        BCPlayerHandlerVid3.videoPlayer.addEventListener(BCVideoEvent.START_BUFFER, function (event) {
            if (BCPlayerHandlerVid3.firstVideo) {
                if (!BCPlayerHandlerVid3.pauseVideo) {
                    BCPlayerHandlerVid3.firstVideo = false;
                } else {
                    BCPlayerHandlerVid3.videoPlayer.pause();
                }

                if (BCPlayerHandlerVid3.videoId !== BCPlayerHandlerVid3.properties.videoId) {
                    BCPlayerHandlerVid3.firstVideo = false;
                    BCPlayerHandlerVid3.changeVideo( { videoId: BCPlayerHandlerVid3.videoId } );
                }
            }
        });
    };

    //Template ready event handler
    BCPlayerHandlerVid3.onTemplateReady = function (event) {
        BCPlayerHandlerVid3.playerReady = true;
    };


    BCPlayerHandlerVid3.onTemplateError = function(event){
		//TODO
    }

    BCPlayerHandlerVid3.createVideoObject = function(params){
        BCPlayerHandlerVid3.videoId = params.videoId;

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
        videoObject.append($('<param />').attr({"name" : "adServerURL", "value" :Vid3Utils.generateAdServerURL()}));
        videoObject.append($('<param />').attr({"name" : "includeAPI", "value" : "true"}));
        videoObject.append($('<param />').attr({"name" : "templateLoadHandler", "value" : "BCPlayerHandlerVid3.onTemplateLoad"}));
        videoObject.append($('<param />').attr({"name" : "templateReadyHandler", "value" : "BCPlayerHandlerVid3.onTemplateReady"}));
        videoObject.append($('<param />').attr({"name" : "templateErrorHandler", "value" : "BCPlayerHandlerVid3.onTemplateError"}));

        $('#contenedor-OnDemand').html(videoObject);
    }
})(window);
