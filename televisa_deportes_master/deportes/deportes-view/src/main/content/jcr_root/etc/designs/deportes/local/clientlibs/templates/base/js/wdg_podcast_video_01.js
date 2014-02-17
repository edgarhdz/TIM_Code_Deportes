$(document).ready(function(){
    $(function(){
        if(isAndroidDefault){
            alert("Lo sentimos, el navegadir no soporta la implementación del player.")
        }
            $(".audio").mb_miniPlayer({
            width:240,
            inLine:false
        });
    });
    	$(window).resize(function() {
    		var $size = $(window).width()/3-20;
	    	$('.map_controls').css('width', $size+'70');
	    });
});