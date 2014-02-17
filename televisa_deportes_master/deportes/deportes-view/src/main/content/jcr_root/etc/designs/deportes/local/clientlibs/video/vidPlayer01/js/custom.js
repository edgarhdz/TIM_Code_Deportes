var device = "desktop";

var contenidoIpad = '<div id="videoContainer">'+
    '<!-- código de player -->'+
    '<div id="videoPlayer">'+
    '<a class="player" id="ToothPlayer"></a>'+
    '</div>'+
    '<!-- código de player -->'+
    '</div>'+
    '<div id="epg"></div>';
    
var playerWidth = vid_player_01_player.width;
var playerHeight = vid_player_01_player.height;
var isiPad = isiPad();
    
function isiPad(){
    var ret = false;
    var isiPad = navigator.userAgent.match(/iPad/i) != null;
    var isiPhone = navigator.userAgent.match(/iPhone/i) != null;
    var isiPod = navigator.userAgent.match(/iPod/i) != null;
    if(isiPad || isiPhone || isiPod){
        ret = true;    
    }
    return ret;     
}    