$(document).ready(function(){
	// alert('0he');
	/**
	 * @Encargado de maximizar el video en caso de reproducción.
	 */
	 //valido que se ecuentre en desktop
	 
	 $('.td_video_detalle_01 .vid_player_02 #expandbtn').click(function(event){
            event.preventDefault();
		if( $(window).width() > 959){
			//Oculta contenedor a la derecha del reproductor (2 componentes)
			$('.td_video_detalle_01 .main .c3').hide(); 
			//Agranda el contenedor del componente de video para que ocupe el ancho de la pantalla (main).
			$('.td_video_detalle_01 .main .c2').css({width:'100%'});
			$('.td_video_detalle_01 .main .c2 img').css({height: '574px'});

			//Ajusto medidas de detalle del componente de video
			$('.td_video_detalle_01 .vid_player_02 .txt1').css('width','61%');
			$('.td_video_detalle_01 .vid_player_02 .txt2').css('width','100%');
			$('.td_video_detalle_01 .vid_player_02 .txt3').css('width','100%');
			$('.td_video_detalle_01 .vid_player_02 .content_text').css('width','30%');
			$('.td_video_detalle_01 .vid_player_02 #videobtn').css('margin-top', '89px');
			$('.td_video_detalle_01 .vid_player_02 #expandbtn').css('visibility', 'hidden');
			$('.td_video_detalle_01 .vid_player_02 #contractbtn').css('visibility', 'visible');
		}
	 });
	$('.td_video_detalle_01 .vid_player_02 #contractbtn').click(function(event){
            event.preventDefault();
		if( $(window).width() > 959){
			//Oculta contenedor a la derecha del reproductor (2 componentes)
			$('.td_video_detalle_01 .main .c3').show(); 
			//Agranda el contenedor del componente de video para que ocupe el ancho de la pantalla (main).
			$('.td_video_detalle_01 .main .c2').css({width:'624'});
			$('.td_video_detalle_01 .main .c2 img').css({height: '350'});

			//Ajusto medidas de detalle del componente de video
			$('.td_video_detalle_01 .vid_player_02 .txt1').css('width','239px');
			$('.td_video_detalle_01 .vid_player_02 .txt2').css('width','239px');
			$('.td_video_detalle_01 .vid_player_02 .txt3').css('width','239px');
			$('.td_video_detalle_01 .vid_player_02 .content_text').css('width','239px');
			$('.td_video_detalle_01 .vid_player_02 #videobtn').css('margin-top', '-135px');
			$('.td_video_detalle_01 .vid_player_02 #expandbtn').css('visibility', 'visible');
			$('.td_video_detalle_01 .vid_player_02 #contractbtn').css('visibility', 'hidden');
		}
	 });
});