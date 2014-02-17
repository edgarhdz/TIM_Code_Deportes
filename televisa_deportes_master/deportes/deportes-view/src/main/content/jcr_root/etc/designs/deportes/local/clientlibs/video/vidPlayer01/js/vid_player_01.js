vid_player_01_player = new Object();

vid_player_01_player.width = 625; // Delay entre cambio y cambio de imagenes en el autoplay
vid_player_01_player.height = 380;

$(document).ready(function() {
	
	// Si existe el componente de video
	if($('.vid_player_01')){
		
		// Click  del btn play
		$('.vid_player_01 #videobtn').click(function (){
			
			$('.vid_player_01 #img_stage_01_IMG').hide();
			$('.vid_player_01 #videobtn').hide();
			$('.vid_player_01 .vid_player_01_whtbkg').hide();
			$('.vid_player_01 .overlay').css('margin-top','25px');
			
			
			setPlayerSize();
			
			$('.vid_player_01 #theaterContainer').show();
		});
		
		// ponemos el thumb y el titulo del video 
		
		/*if (typeof(objHtml5) != "undefined"){
			$('.vid_player_01 .vid_player_01_black').text(objHtml5.properties.title);
			$('.vid_player_01 #img_stage_01_IMG').attr('src', objHtml5.properties.thumbnail);
		}*/
		
		
		
		$(window).resize(function() {
			setPlayerSize();
		});
	}
	
});


function setPlayerSize(){
	
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