/*
	23/07/13 13:21:53
*/
/* Valida consola debug */
if (typeof console == "undefined" || typeof console.log == "undefined") var console = { log: function() {} };
/* ******************** */


Array.prototype.indexOf = function(obj, start) {
     for (var i = (start || 0), j = this.length; i < j; i++) {
         if (this[i] === obj) { return i; }
     }
     return -1;
}

var videoPlayer;
var AdServerResponse = [];//Arreglo donde se guarda la informacion de la publicidad.
var publicLockKey = 'YAOUIVJOBKJWGLPSEBCJ'+'JLOSAPWLMAQOPGHDGLPZ'+'ILISKKJSMPOEAJSWJLO'+'DOPOSRKJSAPQAGJHYHGFD'; 
var orginalVideoSrc;
var contAd = 0;			// Control de elementos solicitados ad server
var contResponse = 0;
var contRespAds = 0;
var valoresRoll = new Array("pre","mid","pos","tel","cub");
var currentIndex = 0;
var queue = [];
var secFlag = 0;
var adReady = 0;
var adPreFinish = 0;
var plyrReady = 0;
var changedSign = "";
var contaIntent = 0;
var currentplaying = false;
var startFlag = false;


//this function is meant to initialize the videoPlayer component


function genAdUrlCall(roll,oficial){
	var requestUrl = null;
	var sponvar = '';
	var statusvar = '';
	
	if(typeof options.ad.sponsor != "undefined"){
		if(options.ad.sponsor !== ''){
			sponvar = ';sponsor=' + options.ad.sponsor;
		}
	}
	
	if(roll == 'pre' || roll == 'cort'){
		if(typeof options.ad.oficial != "undefined"){
			if(oficial){
				statusvar = ';status=oficial';
			}else if(oficial !== null){
				statusvar = ';status=nooficial';
				sponvar = '';
			}
		}
	}
	
	var valAdSite = options.ad.adSite;

	//requestUrl = valAdSite + '/' + 
	//options.ad.adZone +
	requestUrl = options.ad.adZone + 
	';tile=' + options.ad.adTile + 
	';sz=' + options.ad.adSize + 
	';ord=' + options.ad.ord + 
	';dcmt=text/javascript'+
	';zone0=' + options.ad.adZone + 
	';roll=' + roll + 
	';pais=' + MN_geo.country.toLowerCase() + 
	';estado=' + escape(MN_geo.state.toLowerCase()) + 
	';ciudad=' + escape(MN_geo.city.toLowerCase()) + 
	';id_video=' + options.ad.videoId + sponvar + statusvar;
	urlReq='http://ad.doubleclick.net/adx/'+requestUrl+'?callback=cbResponse';

	insertJS(urlReq);
}


function waitcubePanel(cubePanel , outPut, timeLimit){
	if (timeLimit < 10 ){
		timeLimit=timeLimit+1;
		if( document.getElementById(cubePanel) ){
			document.getElementById(cubePanel).innerHTML = outPut;
		}
		else{
			setTimeout(function(){waitcubePanel(cubePanel,outPut,timeLimit);},1000);
		}
	}
}

function isValidRegion() {
	//if(enabledGbs == false) return true;
	var cString = "";
	var result = false;
	switch( options.gbs ) {
		case '49010dd1ebf31e2005d4c624eef19176':
			if( MN_geo.country.toLowerCase() == 'mex' ){
				result = true;
			}
			break;
		case '716ee5cf447289f814a8ef5f9ad86bb5':
			cString = 'usa,pri,vir,umi,asm,plw';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 ){
				result = true;
			}
			break;
		case '991d48815c3a68294305aaf78e0bdeeb':
			cString = 'ant,arg,brb,bmu,bol,bra,bra,bhs,blz,can,chl,col,cri,cri,cub,dma,ecu,dom,guf,glp,sgs,gtm,gum,guy,hnd,hti,jam,kna,cym,lca,mtq,mex,nic,pan,per,pry,sur,stp,slv,tto,ury,vct,ven';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 ){
				result = true;
			}
			break;
		case '48bfa84d06702d2185b4b0a47eb4d01d':
			cString = 'mex,gtm';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 ){
				result = true;
			}
			break;
		case '71791115dd7384c59214ad4697e6d15b':
			cString = 'bol,chl,col,cri,ecu,gtm,hnd,nic,pan,per,pry,slv,ury,ven,mex';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 ){
				result = true;
			}
			break;
		case 'c1c734120e257f118c92aa2293bed0bf':
			cString = 'ant,brb,bmu,bol,bhs,blz,can,chl,col,cri,cri,cub,dma,ecu,dom,guf,glp,sgs,gtm,gum,guy,hnd,hti,jam,kna,cym,lca,mtq,mex,nic,pan,per,pry,sur,stp,slv,tto,ury,vct,ven';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 ){
				result = true;
			}
			break;
		case 'ce91be1cd60cdc434d7e5604182030bf':
			cString = 'mex,gtm,slv,nic,hnd,pan,cri';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 ){
				result = true;
			}
			break;
		case '5115ee5df30264131b7a9c41b8fd752e':
			cString = 'bol,chl,col,cri,ecu,slv,gtm,hnd,mex,nic,pan,per,pry,ury,ven';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 ){
				result = true;
			}
			break;
		case 'd7491456701ad4c98d1fe0f389a1225e':
			// cString = 'bol,chl,col,cri,ecu,slv,gtm,hnd,nic,pan,per,pry,ury,ven';
			cString = 'bol,bhs,blz,chl,col,cri,cub,dma,ecu,dom,gtm,guy,hnd,hti,jam,mex,nic,pan,per,pry,sur,slv,tto,ury,ven';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 ){
				result = true;
			}
			break;
		case '11a344833988376463d9fecda369580b':
			if( MN_geo.country.toLowerCase() == 'usa' ){
				result = true;
			}
			break;
		case '5fb1f955b45e38e31789286a1790398d':
			result = true;
			break;
		// ESTADOS	
		case '70d696597ff47b9b71ee3c9384c62f9e':
			cString = 'usa,pri,vir,umi,asm,plw';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 && MN_geo.state.toLowerCase() != 'que' ){
				result = true;
			}
			break;
		case 'e673e0d8ec9ab6188f5551fffdb8dd22':
			cString = 'usa,pri,vir,umi,asm,plw';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 && MN_geo.state.toLowerCase() != 'jal' ){
				result = true;
			}
			break;
		case '69239913289a8cb723a7fa071a52625c':
			cString = 'usa,pri,vir,umi,asm,plw';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 && MN_geo.state.toLowerCase() != 'slp' ){
				result = true;
			}
			break;
		case '0064dc1902181149302bbf47aa921928':
			cString = 'usa,pri,vir,umi,asm,plw';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 && MN_geo.state.toLowerCase() != 'nle' ){
				result = true;
			}
			break;
		case '54afac809a26220a351763e3734c8e93':
			cString = 'usa,pri,vir,umi,asm,plw';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 && MN_geo.state.toLowerCase() != 'roo' ){
				result = true;
			}
			break;
		// LATAM - NOT SINGLE ESTATE
		case '1e9d517c7b36eb14ec294f39f4189583':
			var cString = 'arg,bol,bra,bhs,blz,can,chl,col,cri,cub,dma,ecu,dom,gtm,guy,hnd,hti,jam,mex,nic,pan,per,pry,sur,slv,tto,ury,ven';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 && MN_geo.state.toLowerCase() != 'que' )
				result = true;
			break;
		case '57d994124e8845f6315041d77e9d295e':
			var cString = 'arg,bol,bra,bhs,blz,can,chl,col,cri,cub,dma,ecu,dom,gtm,guy,hnd,hti,jam,mex,nic,pan,per,pry,sur,slv,tto,ury,ven';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 && MN_geo.state.toLowerCase() != 'jal' )
				result = true;
			break;
		case '5cc46744223866f912ff282557caadda':
			var cString = 'arg,bol,bra,bhs,blz,can,chl,col,cri,cub,dma,ecu,dom,gtm,guy,hnd,hti,jam,mex,nic,pan,per,pry,sur,slv,tto,ury,ven';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 && MN_geo.state.toLowerCase() != 'slp' )
				result = true;
			break;
		case 'fb5361cce87bdb60261f0b41335f3771':
			var cString = 'arg,bol,bra,bhs,blz,can,chl,col,cri,cub,dma,ecu,dom,gtm,guy,hnd,hti,jam,mex,nic,pan,per,pry,sur,slv,tto,ury,ven';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 && MN_geo.state.toLowerCase() != 'nle' )
				result = true;
			break;
		case '163b5b0f07d61ae8d1e33f6a9d379631':
			var cString = 'arg,bol,bra,bhs,blz,can,chl,col,cri,cub,dma,ecu,dom,gtm,guy,hnd,hti,jam,mex,nic,pan,per,pry,sur,slv,tto,ury,ven';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 && MN_geo.state.toLowerCase() != 'roo' )
				result = true;
			break;
		case 'e4a15899c397ca5ba2b77563b90eb811':
			var cString = 'arg,bol,bra,bhs,blz,can,chl,col,cri,cub,dma,ecu,dom,gtm,guy,hnd,hti,jam,mex,nic,pan,per,pry,sur,slv,tto,ury,ven';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 && MN_geo.state.toLowerCase() != 'ver' )
				result = true;
			break;
		case '9a3ae7c45fd231b425519f979ba20112':
			var cString = 'arg,bol,bra,bhs,blz,can,chl,col,cri,cub,dma,ecu,dom,gtm,guy,hnd,hti,jam,mex,nic,pan,per,pry,sur,slv,tto,ury,ven';
			if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 && MN_geo.state.toLowerCase() != 'chi' )
				result = true;
			break;
		default:
			result = false;
			break;
	}
	if( document.cookie.indexOf( '__aYrUtmZkj=' ) != -1 ) {
		var raVal = ''; for( var i = 0; i < publicLockKey.length; i+=4 ) { raVal += publicLockKey.charAt(i);}
		if( eval('String.fromCharCode(' + unescape( document.cookie.substring( document.cookie.indexOf( '__aYrUtmZkj=' ) + 12, document.cookie.indexOf( '__aYrUtmZkj' ) + 109 ) ) + ')') == raVal ){ result = true;}
	}
	if(MN_geo.ip == '213.23.36.34' || MN_geo.ip == '173.162.114.97'){
		result = true;
	}
	return result;
}

function AVPToolbarShow() {
	if(document.getElementById("AVPToolbar")){
		document.getElementById("AVPToolbar").style.display = "block";
	}
}

function AVPToolbarHide() {
	if(document.getElementById("AVPToolbar")){
		document.getElementById("AVPToolbar").style.display = "none";
	}
}

function convertToSeconds(stringTime) {
	var sec = 0;
	if(stringTime.match(/\d\d?\:\d\d?\:\d\d?/)) {
		var arrTime = stringTime.split(/\:/);
		if(arrTime.length == 3){
			sec = parseInt(arrTime[0],10)*3600 + parseInt(arrTime[1],10)*60 + parseInt(arrTime[2],10);
		}else if(arrTime.length == 2){
			sec = parseInt(arrTime[0],10)*60 + parseInt(arrTime[1],10);
		}else if(arrTime.length == 1){
			sec = parseInt(arrTime[0],10);
		}
	}
	return sec;
}

function insertJS (source) {
	   var headID = document.getElementsByTagName("head")[0];         
	   var newScript = document.createElement('script');
	   newScript.type = 'text/javascript';
	   newScript.src = source;
	   headID.appendChild(newScript);
}

function getHash() {
	var strHash = '';
	var randomNumber = null;
	var start = null;
	var total = null;
	for ( i = 0; i < 39; i++ ) {
		randomNumber = Math.round( Math.random() * 2 ) + 1;
		if( randomNumber == 1 ) {
			start = 65;
			total = 25;
		}
		else if( randomNumber == 2 ) {
			start = 97;
			total = 25;
		}
		else {
			start = 48;
			total = 9;
		}
		strHash += String.fromCharCode(start + Math.round(Math.random() * total));
	}
	
	return strHash;
}

function getCSIE() {
	if( document.cookie.indexOf("esmasstats=") == -1 )  {
		var nd = new Date();
		document.cookie = "esmasstats="+(nd.getYear()+"").substring(2,4)+(((nd.getMonth()+1)<10)?"0":"")+(nd.getMonth()+1)+((nd.getDate()<10)?"0":"")+nd.getDate()+((nd.getHours()<10)?"0":"")+nd.getHours()+((nd.getMinutes()<10)?"0":"")+nd.getMinutes()+((nd.getSeconds()<10)?"0":"")+nd.getSeconds()+"-"+(Math.random()*1000000000000000000)+"-"+Math.round(Math.random()*100000000)+"; expires=Fri, 31 Jan 2020 23:59:59 GMT; path=/;";
	}
	
	var begin = document.cookie.indexOf("esmasstats=");
	
	var CSIE=document.cookie.substring(begin + 11, begin + 50);
	
	return CSIE;
}

function sendVideoLog(action) {
	var hashCode = getHash();
	var dtvuserTxt = "";
	if(typeof cookieTDTV != "undefined"){
		dtvuserTxt = "dtvuserguid=" + cookieTDTV;
	}

	var urlParams = 	"action=" + action + 
					"&duration=" + convertToSeconds(options.videoDuration) + 
					"&cc=" + MN_geo.country.toLowerCase() + 
					"&state=" + MN_geo.state.toLowerCase() + 
					"&city=" + MN_geo.city.toLowerCase() + 
					"&url=" + escape(options.linkBaseURL) + 
					"&hash=" + hashCode + 
					"&title=" + options.videoTitle + 
					"&CSIE=" + getCSIE() + 
					"&type=3";

	var src = "http://videolog.esmas.com/set_view.php?" + urlParams + dtvuserTxt;
	var img = null;
	
	if(document.getElementById('vl')){
		img = document.getElementById('vl');
	} else {
		img = document.createElement("img");
		img.setAttribute("id", "vl");
		img.setAttribute("display", "none");
		img.setAttribute("style", "display:none;");
		document.body.appendChild(img);
	}
	
	//console.log("PREVIO A LLAMADO");
		img.setAttribute("src", src);
	//console.log("LLAMADO APLICADO: " + src);
		
	// Mandamos llamar Log de comunidades
	if( action == 'start' && !startFlag) {
		var viewsLog = new Image();
		var viewsUrlLog = 'http://v.esmas.com:8081/vistas/spacer.gif?2|' + escape(options.linkBaseURL);
		viewsLog.src = viewsUrlLog;
		startFlag = true;
	}

	try{
		var video_info={
			videoDuration:convertToSeconds(options.videoDuration),
			progressTime:0,
			country:MN_geo.country,
			state:MN_geo.state,
			city:MN_geo.city,
			videoTitle:options.videoTitle,
			playerType:'ak',
			videoType:'live',
			ip:MN_geo.ip,
			url:escape(options.linkBaseURL)

		};

		videolog.sendVideoLog(action, video_info);
	}
	catch(err)
	{
	
	}

}

function mutePlayer(silencio){
	videoPlayer = document.getElementById('AkamaiMediaPlayer');
	if(silencio){
		videoPlayer.mute();
	}else{
		videoPlayer.unmute();
	}
	
}

function onPaused(){
		sendVideoLog("paused");
}

function onEnded(){
	if (currentIndex < queue.length){
		if(queue.length == currentIndex + 1){
			sendVideoLog("end");
		}
		currentIndex++;
		loadVideo(queue[currentIndex]);
	}
	
}
var contloading=0;
function loadVideo(src){
	if( plyrReady == 1 && adReady == 1 ){			
		setTimeout( function() {
			document.getElementById("AkamaiMediaPlayer").loadURL(src);
			if(queue.length == currentIndex + 1 || queue.length === 0){
				adPreFinish = 1;
			}
		}, 3500);
	}else{
		setTimeout( function() {
			//console.log("Player setTimeout LoadVideo " + contloading);
			loadVideo(src);
			contloading++;
		}, 100);
	}
}

function iniciaPlayer(){
	if(isValidRegion() === true) {
		if(typeof options.ad != "undefined") {
			if(options.ad.adConfig != '00000'){
				var roll = null;
				for(var c=0; c < valoresRoll.length; c++){
					if(c != 1 && c != 2 && c != 4){ // Temporal valid mid y pos
						roll = options.ad.adConfig.charAt(c);
						if(roll == 1){
							valRoll = valoresRoll[c];
							if(c === 0 && convertToSeconds(options.videoDuration) < 60){
								valRoll = 'cort';
							}
							var directo = true;
							if(options.ad.oficial != 'undefined'){
								if(options.ad.oficial !== false){
									if(c === 0){
										for(d=0; d<2; d++){
											if(d==1)	{ genAdUrlCall(valRoll,true); }
											if(d===0){	genAdUrlCall(valRoll,false); }
										}
										contAd++;
									}
									directo = false;
								}
							}
							if(directo)
							{
								genAdUrlCall(valRoll,null);
								contAd++;
							}
						}
					}
				}
			}else{
				adReady = 1;
				loadVideo(options.video_url);
			}
		}
	}
	else{
		document.getElementById('videoPlayerContainer').style.display="block";
		document.getElementById('videoPlayerContainer').innerHTML='<div id="AVP_error_display"><h1>Este video <br>no est&aacute; disponible<br>para tu ubicaci&oacute;n</h1></div>';
		if(document.getElementById('AkamaiMediaPlayer')){
			var vidspace = document.getElementById('AkamaiMediaPlayer');
			var theparent = vidspace.parentNode;
			theparent.removeChild(vidspace);
		}
		sendVideoLog("blocked");
	}
}
/**
* Verificador de json
*/
function IsJson(str) {
    try {
    	if(Object.prototype.toString.call(str) == '[object Array]'){
    		return true
    	}else{
    		return false;
    	}
        //Array.isArray(str);
    } catch (e) {
        return false;
    }
    // return true;
}

function cbResponse( json ) {
	var flag=0;
	if(IsJson(json)){	
		for(z in json[0]){
			if(z == 'adStatus'){
				
				// SOLO SI CARGA NOOFICIAL, LEVANTARA BANDERA PARA OFICIAL
				if(json[0]['adStatus'] == 1 || secFlag == 1){
					//if(json[0]['adStatus'] == 1){
					flag =1;
				}
				if(json[0]['adStatus']===0){
					secFlag=1;
				} 
				

			}else if(secFlag == 1){
				flag =1;
			}
		}
		
		for(x in json[0]){
			if(x == 'adPre' && flag == 1){ // Tiene que ser PRE y estar marcado como Oficial
				AdServerResponse['adPreOfi']=json[0][x];
			}else if (flag != 1){
				if(x != 'adStatus'){
					// No debe ser la bandera de oficial incluida en la respuesta json
					AdServerResponse[x]=json[0][x];
				}
			}
			contRespAds++;
		}
	
	}
	contResponse++;
	if(contAd == contResponse){
		adReady =1;
		AdRefreshed();
	}
}

function AdRefreshed( ) {
	//orginalVideoSrc=options.video_url;	
	var outPut ="";
	if(AdServerResponse.adSkin){
		if( document.getElementById(options.ad.topPanel) ){
			if(AdServerResponse.adSkin.top){
				document.getElementById(options.ad.topPanel).style.backgroundImage = 'url(' + AdServerResponse.adSkin.top + ')';
			}
		}
		if( document.getElementById(options.ad.rightPanel) ){
			if(AdServerResponse.adSkin.right){
				document.getElementById(options.ad.rightPanel).style.backgroundImage = 'url(' + AdServerResponse.adSkin.right + ')';
				document.getElementById(options.ad.rightPanel).style.backgroundRepeat = 'no-repeat';
				document.getElementById(options.ad.rightPanel).style.backgroundPosition = 'center top';
				if(document.getElementById(options.ad.rightPanel).className == 'sectionContainer deportes'){
					document.getElementById(options.ad.rightPanel).style.backgroundPosition = 'right top';
				}				
				if ( document.getElementById("leftBarLink") && document.getElementById("rightBarLink") ){
					if(AdServerResponse.adPre.adLink){
						outPut = '<a href="' + AdServerResponse.adPre.adLink + '" target="_blank"><img src="http://i2.esmas.com/tvolucion/img/spacer.gif" width="171" height="341" border="0"></a>';
						document.getElementById("leftBarLink").innerHTML = outPut;
						document.getElementById("rightBarLink").innerHTML = outPut;
					}
				}
			}
		}
	}
	contavideo = 0;
	if(AdServerResponse.adPre){		
		if(AdServerResponse.adPre.adMedia.flv){
			queue[contavideo]=AdServerResponse.adPre.adMedia.flv;
			contavideo++;
			//flashvars.video_url=AdServerResponse.adPre.adMedia.flv;
		}
	}
	if(AdServerResponse.adPreOfi){
		if(AdServerResponse.adPreOfi.adMedia.flv){
			queue[contavideo]=AdServerResponse.adPre.adMedia.flv;
			contavideo++;
		}
	}
	queue[contavideo]=options.video_url;
	loadVideo(queue[0]);
	
	
	if(AdServerResponse.adCub){
		outPut = '';
		if( AdServerResponse.adCub.adFormat == "IMAGE") { //con cubo de imagen
			outPut = '<a href="' + AdServerResponse.adCub.adLink + '" target="_blank"><img src="' + AdServerResponse.adCub.adImage.url + '" width="' +AdServerResponse.adCub.adImage.width + '" height="' +AdServerResponse.adCub.adImage.height + '" border="0"></a>';
			if( document.getElementById(options.ad.cubePanel) ){
				document.getElementById(options.ad.cubePanel).innerHTML = outPut;
			}else{
				setTimeout(function(){waitcubePanel(options.ad.cubePanel,outPut,0);},1000);
			}
		}
		else if( AdServerResponse.adCub.adFormat == "SWF") {//con cubo de flash
			outPut = '' +
			'<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" ID=flashad WIDTH=' +  AdServerResponse.adCub.adSwf.width + ' HEIGHT=' +  AdServerResponse.adCub.adSwf.height + '>' +
			'	<PARAM NAME=movie VALUE="' +  AdServerResponse.adCub.adSwf.url + '?clickTag=' + escape( AdServerResponse.adCub.adLink) + '">' +
			'	<PARAM NAME=quality VALUE=autohigh>' +
			'	<PARAM NAME=wmode VALUE=transparent>' +
			'	<EMBED SRC="' + AdServerResponse.adCub.adSwf.url + '?clickTag=' + escape( AdServerResponse.adCub.adLink) + '" QUALITY=autohigh NAME=flashad swLiveConnect=TRUE WIDTH=' +  AdServerResponse.adCub.adSwf.width + ' HEIGHT=' +  AdServerResponse.adCub.adSwf.height + ' TYPE="application/x-shockwave-flash" PLUGINSPAGE="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash" WMODE="transparent"></EMBED>' +
			'</OBJECT>';
			if( document.getElementById(options.ad.cubePanel) ){
				document.getElementById(options.ad.cubePanel).innerHTML = outPut;
			}else{
				setTimeout(function(){waitcubePanel(options.ad.cubePanel,outPut,0);},1000);
			}
		}
	}
	
	
	
}

var mirevision;


var AEP = {
	jsCallbackHandler: function(objId, eventName, data) 
	{
		switch (eventName)
		{
			case "jsApiReady":
				//	loadFeed();
				plyrReady = 1;
				//console.log("Player listo " + plyrReady);
			break;
			
			case "mediaPlayerPlaybackClose":
				onEnded();
				//updateEndedHandler();
			break;
			case "mediaPlayerPlaying":
				currentplaying = true;
				sendVideoLog("start");
				//console.log("CurrentPlaying");
				if( currentIndex === 0 && AdServerResponse.adPre ){
					if(document.getElementById('videoLink')) {
						var title_height = 23;
						if(AdServerResponse.adPre.adTitleLines){
							var numLines = Number(AdServerResponse.adPre.adTitleLines);
							title_height = (title_height - 3) * numLines;
						}
						var ima_height = options.normalHeight - (title_height);
						var mensaje = "Anuncio: Haz clic en el video para m&aacute;s informaci&oacute;n";
						if(AdServerResponse.adPre.adTitle){
							if(AdServerResponse.adPre.adTitle !== ''){
								mensaje = AdServerResponse.adPre.adTitle;
							}
						}
						document.getElementById('videoLink').style.display = "block";
						//$("#videoLink").fadeIn('fast')
						document.getElementById('videoLink').style.width=options.normalWidth+'px';
						document.getElementById('videoLink').style.height=options.normalHeight+'px';
						var contenidoVideoLink = '<a href="' + AdServerResponse.adPre.adLink + 
						'" id="publink" style="display: none; text-decoration: none !important;" target="_blank" >' + 
						'<img width="' + ((options.normalWidth) ? options.normalWidth : "100%") + 
						'" height="' + ((options.normalHeight) ? ima_height : "100%") + 
						'" src="http://i2.esmas.com/tvolucion/img/spacer.gif" border="0">' + 
						'<p id="AVP_title_display" style="height:'+ title_height +'px;">' + mensaje + '</p></a>';
						document.getElementById('videoLink').innerHTML = contenidoVideoLink;
						document.getElementById('publink').style.display = "block";
						//$("#publink").fadeIn('fast')
					}
					/*else{
						//document.getElementById('videoLink').style.display = "block";
						//document.getElementById('publink').style.display = "block";
						$("#videoLink").fadeOut('slow');
						$("#publink").fadeOut('slow');
					}*/
				}else{
					document.getElementById('videoLink').style.display = "none";
					document.getElementById('publink').style.display = "none";
				}
				
			break;
			case "mediaPlayerVolumeChanged":
				if (data.volume <= 0)
				{
					//mutedHandler();
					mutePlayer(true);
				}
				else
				{
					//unmutedHandler();
					mutePlayer(false);
				} 
			break;
			case "mediaPlayerPaused":
				onPaused();
			break;
			default:
			break;

		}
	}
};
var quitabarForce;
$(window).load(function () {
	iniciaPlayer();
	mirevision = setInterval(function(){if(contaIntent == 7){if(!currentplaying){adReady = 1;loadVideo(options.video_url);window.clearInterval(mirevision);}else{window.clearInterval(mirevision)};}else{	contaIntent++;/*console.log("Player reintento " + contaIntent);*/}},1000);
	quitabarForce = setInterval(function(){if(document.getElementById('videoLink')){document.getElementById('videoLink').style.display = "none";if(document.getElementById('publink')){document.getElementById('publink').style.display = "none";}}},50000);
});

function cambiaVideo(urlvideo, senales){
    console.log(">>>>>>>>>>> START!!!!!! ");
	if( Object.prototype.toString.call( senales ) !== '[object Array]' ) {
	    console.log(">>>>>>>>>>> DEFAULT !!!!!! ");
        senales = new Array;
        senales[1]="http://tvsabldep-lh.akamaihd.net/z/4m3l3on1_1@143663/manifest.f4m";
        senales[2]="http://tvsabldep-lh.akamaihd.net/z/4m3l3on2_1@143664/manifest.f4m";
        senales[3]="http://tvsabldep-lh.akamaihd.net/z/4m3l3on3_1@143665/manifest.f4m";
        senales[4]="http://tvsabldep-lh.akamaihd.net/z/4m3l3on4_1@143666/manifest.f4m";
        senales[5]="http://tvsabldep-lh.akamaihd.net/z/4m3l3on5_1@143667/manifest.f4m";
        senales[6]="http://tvsabldep-lh.akamaihd.net/z/4m3l3on6_1@143669/manifest.f4m";
        senales[7]="http://tvsabldep-lh.akamaihd.net/z/4m3l3on7_1@143670/manifest.f4m";
        senales[8]="http://tvsabldep-lh.akamaihd.net/z/4m3l3on8_1@143671/manifest.f4m";
        senales[9]="http://tvsabldep-lh.akamaihd.net/z/4m3l3on9_1@143672/manifest.f4m";
    }
	
	var urlvideoTmp = urlvideo.substring(0 , urlvideo.indexOf("?"));
	if( plyrReady == 1 && adPreFinish == 1 && (changedSign != urlvideoTmp || changedSign == "")) {
		changedSign = urlvideoTmp;
		var lasenal = senales.indexOf(urlvideoTmp);
		setTimeout( function() {

			tempis = document.getElementById("AkamaiMediaPlayer");
			cadena1 = tempis.getElementsByTagName("param")[5].value;
			idtvpos = cadena1.indexOf("report_IDTV") + 12;
			lasub = cadena1.substr(0, idtvpos) + lasenal;
			//tempis.getElementsByTagName("param")[5].value = lasub;
			//var idgalaxy2 = "238917-" + lasenal;
			var idgalaxy2 = options.ad.videoId + "-" + lasenal;

			var myparams = {dimensions:[{key:"IDTV",value:lasenal},{key:"IDGalaxy",value:idgalaxy2}]};

			document.getElementById("AkamaiMediaPlayer").loadURL(urlvideo,myparams);
			//document.getElementById("AkamaiMediaPlayer").loadURL(urlvideoTmp);
			if(queue.length == currentIndex + 1){
				sendVideoLog("change");
			}
		}, 100);
	}
    console.log(">>>>>>>>>>> END!!!!!! ");    
}

/*
	senales[1]="http://tvsabldep-lh.akamaihd.net/z/4m3ch1lat1_1@143663/manifest.f4m";
	senales[2]="http://tvsabldep-lh.akamaihd.net/z/4m3ch1lat2_1@143664/manifest.f4m";
	senales[3]="http://tvsabldep-lh.akamaihd.net/z/4m3ch1lat3_1@143665/manifest.f4m";
	senales[4]="http://tvsabldep-lh.akamaihd.net/z/4m3ch1lat4_1@143666/manifest.f4m";
	senales[5]="http://tvsabldep-lh.akamaihd.net/z/4m3ch1lat5_1@143667/manifest.f4m";
	senales[6]="http://tvsabldep-lh.akamaihd.net/z/4m3ch1lat6_1@143669/manifest.f4m";
	senales[7]="http://tvsabldep-lh.akamaihd.net/z/4m3ch1lat7_1@143670/manifest.f4m";
	senales[8]="http://tvsabldep-lh.akamaihd.net/z/4m3ch1lat8_1@143671/manifest.f4m";
	senales[9]="http://tvsabldep-lh.akamaihd.net/z/4m3ch1lat9_1@143672/manifest.f4m";
	
	senales[1]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n01_1@138939/manifest.f4m";
	senales[2]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n02_1@138942/manifest.f4m";
	senales[3]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n03_1@138943/manifest.f4m";
	senales[4]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n04_1@138944/manifest.f4m";
	senales[5]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n05_1@138945/manifest.f4m";
	senales[6]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n06_1@138946/manifest.f4m";
	senales[7]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n07_1@138947/manifest.f4m";
	senales[8]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n08_1@138948/manifest.f4m";
	senales[9]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n09_1@138949/manifest.f4m";
*/