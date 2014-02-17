/*
	Tue Jan 29 18:52:45 CST 2013 @78 /Internet Time/
*/
var validReg = true;
var AEP = {
	counter: 1,
	folder: null,
	playerReady: false,
	findSWF: function(movieName) {
		return ($.browser.msie) ? window[movieName] : document[movieName];
	},
	eventProgress: function(data) {
		var player = this.findSWF("akamaiMultiStreamPlayer");
	},
	callPlayer: function(str) {
		
		var player = this.findSWF("akamaiMultiStreamPlayer");
		var val = null;
		var param = 30;
		
		var clear = false;
		switch (str) {
			case 'stop':
				player.stopPlayer();
				val = 'stopped';
				break;
				
			case 'pause':
				player.pause();
				val = 'paused';
				break;
				
			case 'unpause':
				player.unpause();
				val = 'unpaused';
				break;
				
			case 'mute':
				player.mute();
				val = 'muted';
				break;
				
			case 'unmute':
				player.unmute();
				val = 'unmuted';
				break;
				
			case 'getPlayerState':
				val = player.getPlayerState();
				break;
				
			case 'isPlayerPlaying':
				val = player.isPlayerPlaying();
				break;
				
			case 'isStopped':
				val = player.isStopped();
				break;
				
			case 'isBuffering':
				val = player.isBuffering();
				break;
				
			case 'currentTime':
				val = player.currentTime();
				break;
				
			case 'duration':
				val = player.duration();
				break;
				
			case 'playbackKbps':
				val = player.playbackKbps();
				break;
				
			case 'jumpToTime':
				param = $('#input-jumpToTime').val();
				player.jumpToTime(param);
				val = param;
				break;
                
            case 'setShareEnabledState':
				param = $('#input-setShareEnabledState').val();
				player.setShareEnabledState(param);
				val = param;
				break;
				
			case 'goLive':
				player.goLive();
				break;
				
			case 'playFile':
				param = $('#input-playFile').val();
				if( param != "" )
				{
					player.playFile(param, $("#autoplay").attr("checked"));
				}
				val = param;
				break;
		}
	},
	jsCallbackHandler: function(objId, eventName, body) 
	{
		//console.log("Notification received: " + eventName + (eventName == "mediaPlayerStateChange" ? ("State: " + body.state) : ""));
		if( eventName == "jsApiReady") 
		{
			playerReady = true;
			
			// Event listeners or other routines relevant to
			// API readiness should be placed here.
			// Also note that properties of the underlying
			// media player are exposed. By default, these
			// will be the properties of the OSMF MediaPlayer 
			var player = this.findSWF("akamaiMultiStreamPlayer");
        }
        if(eventName == "videoLog")
        {
            console.log("ACTION:" + eventName, body.action);
        }
    }
    
};

function retrieveToken(guid, url)
{
	var player = AEP.findSWF("akamaiMultiStreamPlayer");
	var val = "&primaryToken=XXXX";
	player.success(val);
	
    return true;
}

function needsToken(guid, url)
{
	if(guid == "1241186546001" || guid == "124118654")
	{
		return true;
	}
}

function addMPlayer(flashvars, params, options) {
	//if(isValidRegion() == true && flashvars.video_id != "179490" && flashvars.video_id != "180325" && !(valoresp1)) {
	if(isValidRegion() == true) {
		swfobject.embedSWF(swf, "akamaiMultiStreamPlayer", options.normalWidth, options.normalHeight, options.version, options.swfinstall, flashvars, params, { name: "akamaiMultiStreamPlayer" });
	}else{
		validReg = false;
	}
}


function isValidRegion() {
	//if(enabledGbs == false) return true;
	var result = false;
	switch( options.gbs ) {
		case '49010dd1ebf31e2005d4c624eef19176':
			if( MN_geo.country.toLowerCase() == 'mex' )
				result = true
			break;
		case '716ee5cf447289f814a8ef5f9ad86bb5':
			var cString = 'usa,pri,vir,umi,asm,plw';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 )
				result = true;
			break;
		case '991d48815c3a68294305aaf78e0bdeeb':
			var cString = 'ant,arg,brb,bmu,bol,bra,bra,bhs,blz,can,chl,col,cri,cri,cub,dma,ecu,dom,guf,glp,sgs,gtm,gum,guy,hnd,hti,jam,kna,cym,lca,mtq,mex,nic,pan,per,pry,sur,stp,slv,tto,ury,vct,ven';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 )
				result = true;
			break;
		case '48bfa84d06702d2185b4b0a47eb4d01d':
			var cString = 'mex,gtm';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 )
				result = true;
			break;
		case '71791115dd7384c59214ad4697e6d15b':
			var cString = 'bol,chl,col,cri,ecu,gtm,hnd,nic,pan,per,pry,slv,ury,ven,mex';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 )
				result = true;
			break;
		case 'c1c734120e257f118c92aa2293bed0bf':
			var cString = 'ant,brb,bmu,bol,bhs,blz,can,chl,col,cri,cri,cub,dma,ecu,dom,guf,glp,sgs,gtm,gum,guy,hnd,hti,jam,kna,cym,lca,mtq,mex,nic,pan,per,pry,sur,stp,slv,tto,ury,vct,ven';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 )
			result = true;
			break;
		case 'ce91be1cd60cdc434d7e5604182030bf':
			var cString = 'mex,gtm,slv,nic,hnd,pan,cri';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 )
			result = true;
			break;
		case '5115ee5df30264131b7a9c41b8fd752e':
			var cString = 'bol,chl,col,cri,ecu,slv,gtm,hnd,mex,nic,pan,per,pry,ury,ven';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 )
			result = true;
			break;
		case 'd7491456701ad4c98d1fe0f389a1225e':
			var cString = 'bol,chl,col,cri,ecu,slv,gtm,hnd,nic,pan,per,pry,ury,ven';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 )
			result = true;
			break;
		case '11a344833988376463d9fecda369580b':
			if( MN_geo.country.toLowerCase() == 'usa' )
				result = true;
			break;
		case '5fb1f955b45e38e31789286a1790398d':
			result = true;
			break;
		// ESTADOS	
		case '70d696597ff47b9b71ee3c9384c62f9e':
			var cString = 'usa,pri,vir,umi,asm,plw';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 && MN_geo.state.toLowerCase() != 'que' )
			result = true;
			break;
		case 'e673e0d8ec9ab6188f5551fffdb8dd22':
			var cString = 'usa,pri,vir,umi,asm,plw';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 && MN_geo.state.toLowerCase() != 'jal' )
			result = true;
			break;
		case '69239913289a8cb723a7fa071a52625c':
			var cString = 'usa,pri,vir,umi,asm,plw';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 && MN_geo.state.toLowerCase() != 'slp' )
			result = true;
			break;
		case '0064dc1902181149302bbf47aa921928':
			var cString = 'usa,pri,vir,umi,asm,plw';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 && MN_geo.state.toLowerCase() != 'nle' )
			result = true;
			break;
		case '54afac809a26220a351763e3734c8e93':
			var cString = 'usa,pri,vir,umi,asm,plw';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 && MN_geo.state.toLowerCase() != 'roo' )
			result = true;
			break;
	}
	if( document.cookie.indexOf( '__aYrUtmZkj=' ) != -1 ) {
		var raVal = ''; for( var i = 0; i < publicLockKey.length; i+=4 ) raVal += publicLockKey.charAt(i);
		if( eval('String.fromCharCode(' + unescape( document.cookie.substring( document.cookie.indexOf( '__aYrUtmZkj=' ) + 12, document.cookie.indexOf( '__aYrUtmZkj' ) + 109 ) ) + ')') == raVal ) result = true;
	}
	if(MN_geo.ip == '213.23.36.34'){
		result = true;
	}
	return result;
}

function resizePlayer(w, h)
{	
	if(isValidRegion() == true) {
	$("#akamaiMultiStreamPlayer").empty();
	swfobject.embedSWF(swf, "akamaiMultiStreamPlayer", w, h, options.version, options.swfinstall, flashvars, params, { name: "akamaiMultiStreamPlayer" });	
	}else{
		validReg = false;
		regScreen();
}
}

$(window).load(function () {
	if(!validReg){
		regScreen()
	}
});

function regScreen(){
	document.getElementById('akamaiMultiStreamPlayer').style.display="block";
	document.getElementById('akamaiMultiStreamPlayer').innerHTML='<div id="AVP_error_display" style="width:100%;height:100%;"><img alt="No disponible para tu region" style="width:100%;height:100%;" src="http://i2.esmas.com/tvolucion/img/zona_denegada_cocatri.jpg"></div>';
	sendVideoLog("blocked");
}