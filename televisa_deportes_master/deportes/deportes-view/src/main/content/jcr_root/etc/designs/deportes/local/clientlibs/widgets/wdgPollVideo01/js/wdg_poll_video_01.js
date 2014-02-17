;jQuery(function($){ 
    (function($,T){
       
	   
	   $('.wdg_poll_video_01 input:radio').focus(updateSelectedStyle);
       $('.wdg_poll_video_01 input:radio').blur(updateSelectedStyle);
       $('.wdg_poll_video_01 input:radio').change(updateSelectedStyle);
	   
	  
		function updateSelectedStyle() {
			$('.wdg_poll_video_01 input:radio').removeClass('focused').next().removeClass('focused');
			$('.wdg_poll_video_01 input:radio:checked').addClass('focused').next().addClass('focused');
		}
		
		
		// Click  de Votar
		$('.wdg_poll_video_01 .linkVotar, .wdg_poll_video_01 .buttonVotar').click(function(){
			
			
			
			// Obtenemos los resultados
			var r1 = $('.wdg_poll_video_01 input[name=player1]:radio:checked').val();
			var r2 = $('.wdg_poll_video_01 input[name=player2]:radio:checked').val();
			var r3 = $('.wdg_poll_video_01 input[name=player3]:radio:checked').val();
			
			$('.wdg_poll_video_01 #score1').text(r1);
			$('.wdg_poll_video_01 #score2').text(r2);
			$('.wdg_poll_video_01 #score3').text(r3);
			
			$('.wdg_poll_video_01 .imgPlayer').hide();
			
			// mostramos los resultados
			$('.wdg_poll_video_01 #score1').show();
			$('.wdg_poll_video_01 #score2').show();
			$('.wdg_poll_video_01 #score3').show();
			
			// cambiamos el boton votar
			$('.wdg_poll_video_01 .buttonVotar').removeClass('background-color2');			
			$('.wdg_poll_video_01 .buttonVotar').addClass('buttonVotado');
			$('.wdg_poll_video_01 .buttonVotar').removeClass('buttonVotar');
			
			var totalVotos = 1500; 
			$('.wdg_poll_video_01 .buttonVotado .linkVotar').text('Total de votos ' + totalVotos);
			
			// Quitamos los radio buttons
			$('.wdg_poll_video_01 .col2 .scores').hide();
			
			var p1= '15.5';
			var p2= '45.5';
			var p3= '55.5';
			
			//Colocamos la grafica y el porcentaje
			$('.wdg_poll_video_01 #percentage1').text(p1 + '%');
			$('.wdg_poll_video_01 #percentage2').text(p2 + '%');
			$('.wdg_poll_video_01 #percentage3').text(p3 + '%');
			
			$('.wdg_poll_video_01 #percentage1').addClass('rate2');
			$('.wdg_poll_video_01 #percentage2').addClass('rate2');
			$('.wdg_poll_video_01 #percentage3').addClass('rate1');
			
			
			// Creamos las barras o graficas
			// 137px es el tope maximo del ancho de la barra
			var graph1 = (p1 * 137) / 100;
			var graph2 = (p2 * 137) / 100;
			var graph3 = (p3 * 137) / 100;
			
			$('.wdg_poll_video_01 #graph1').width (graph1);
			$('.wdg_poll_video_01 #graph2').width (graph2);
			$('.wdg_poll_video_01 #graph3').width (graph3);
			
			$('.wdg_poll_video_01 #graph1').addClass('graph2');
			$('.wdg_poll_video_01 #graph2').addClass('graph2');
			$('.wdg_poll_video_01 #graph3').addClass('graph1');
			
			$('.wdg_poll_video_01 .graph').show();
			
			$(this).unbind('click');
			
		});
	   
    })($,Televisa);
});