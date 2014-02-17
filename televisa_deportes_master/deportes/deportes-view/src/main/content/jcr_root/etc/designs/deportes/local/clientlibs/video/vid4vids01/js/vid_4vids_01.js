$(document).ready(function() {	
	(function(){
	    
		vid_4vids_01_player = new Object();
		vid_4vids_01_player.width = 624; // Delay entre cambio y cambio de imagenes en el autoplay
		vid_4vids_01_player.height = 378;
		   
	    // Si cambian de mobile a tablet se quita el ancho fijo que pone el carrusel al 'ul' y viceversa.
		$(window).resize(function(){
			 if($(window).width() <= 590){				             
				 $('.vid_4vids_01 .type1a .carruselContainer #carrusel').css('width', '100%');
			 }
			 else{	
				 $('.vid_4vids_01 .type1a .carruselContainer ul').width(ulWidth + margin_r + 19);			 
			 }
		});
		
		// Si cargan la pagina en movil se quita el ancho fijo que pone el carrusel al 'ul'
	    $(window).load(function() {		
	    	if($(window).width() <= 590){            
	    		$('.vid_4vids_01 .type1a .carruselContainer #carrusel').css('width', '100%');
	        }
	    });
	
	    var anchoUL_carrusel;
	    
	    if($(window).width() > 620){
	    	anchoUL_carrusel = $('.vid_4vids_01 .type1a .carruselContainer #carrusel').width();            
	    }
	    
	    var lis = $('.vid_4vids_01 .type1a .carruselContainer ul li');
	   
	        var margin_r = 0;
	        var ulWidth = 0;
	        var anchoUL = 0;
	        for(var i=0; i<lis.length; i++){
	            margin_r += parseInt($(lis[i]).css("margin-right"));
	            ulWidth += $(lis[i]).width();            
	        }
	        $('.vid_4vids_01 .type1a .carruselContainer ul').width(ulWidth + margin_r + 19);        
	        
	    // Boton del VER MAS (Movil)
	    $('.vid_4vids_01 #vid_4vids_01_btnverMas').click( function (e){
	    	e.preventDefault();
	    	$('.vid_4vids_01 .type1a #carrusel li').show();
	    	$(this).hide();
			 $('.vid_4vids_01 .see_less').show();
	    });
		 // Boton del VER MENOS (Movil)
	    $('.vid_4vids_01 .see_less').click( function (e){
	    	e.preventDefault();
	    	$('.vid_4vids_01 .type1a #carrusel li:nth-child(-n+2)').hide();
	    	$(this).hide();
			$('.vid_4vids_01 #vid_4vids_01_btnverMas').show();
	    });
	
	
	    // Bind del carrsuel 2 con la galeria escogida
	    $('.vid_4vids_01 .galleryLink').click( function (event){
	    	$('.vid_4vids_01 .td_bg').hide();    	
	            event.preventDefault();
	            // Si es version tablet o desktop
	            if($(window).width() > 620){					
	                    // Quitamos el estilo y agregamos el estilo "sellecionado" a la imagen que le dieron click
	                    $('.vid_4vids_01 .galleryLink').each( function (){
	                    		$(this).find('div.selectedGallery i').removeClass('tvsa-video');  
	                    		$(this).find('div.selectedGallery i').removeClass('tvsa-3x');
	                    		$(this).find('div.selectedGallery').removeClass('selectedGallery');                                                       
	                    });
	                    $(this).find('.iconGallery').addClass('selectedGallery');
	                    $(this).find('.iconGallery i').addClass('tvsa-video');
	                    $(this).find('.iconGallery i').addClass('tvsa-3x');
	            }
	
	    });
	
	
	    //Click  del btn play
	    $('.vid_4vids_01 #video_play, .vid_4vids_01 .galleryLink').click(function (event){
	            event.preventDefault();
	            changePlayVideo($(this));
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
	
		function changePlayVideo(galleryIcon){
		
			if( typeof( modVP ) != "undefined"){
				
				if(modVP.isPlaying())
					modVP.stop();
				
				var player = new BCEsmasPlayer("myExperience",643082227001,"contenedor",params,options);
				player.removePlayer();
				player.addPlayer();
				openerSkin();
				
				modVP.play();
				//alert('play');
			}
			
			
			//var imgContainer = $(btnPlay).parent().parent();
			var imgContainer = $(galleryIcon).parents('.vid_4vids_01');
			//$(btnPlay).css('border', '1px solid red');
			
			//$('.vid_4vids_01 #theaterContainer').hide('fast', function(){
			setPlayerSize();
			
			
			//alert($(imgContainer).attr('id'));
			$(imgContainer).find('#theaterContainer').show();
			$(imgContainer).find('#img_stage_01_IMG').hide();
			$(imgContainer).find('#video_play').hide();
			$(imgContainer).find('.overlay').hide();
			//});
		}
	
	})();
	
});



