/*	
	Document   : mix_15arts_02_js
	Created on : 22/08/2013, 06:34:00 pm
	Author     : Luis Jesús Ruelas Merino
	Description: Listado de 15 componentes de mix_1arts_16
 */
	/*BEGIN: Swipe*/		
	$(document).ready(function(){	
		$('.mix_15arts_02 div.list_container').bind('swipeup',function(){
			$(this).animate({
	                'scrollTop': $('.mix_15arts_02 .list_container').scrollTop() + 265
	            }, 500);	
		});
		$('.mix_15arts_02 div.list_container').bind('swipedown',function(){
			$(this).animate({
	                'scrollTop': $('.mix_15arts_02 .list_container').scrollTop() - 265
	            }, 500);
		});	
		
		//Altura		
		$altura_mix =0;
		$altura_mix = $altura_mix + $('.mix_15arts_02 div.list_container').outerHeight();
		$alt_w_mix = $('.mix_15arts_02 div.list_container').height();
	
		//--DEGRADADO
		$('.mix_15arts_02 .list_container').scroll(function() {
			
			var $position_mix = $(this).scrollTop();
			var $med_mix = $position_mix + $alt_w_mix; 
			
			if($med_mix == $altura_mix){
			
				$(this).siblings('.degradado').css("visibility","hidden");
				$(this).siblings('.nav').children().children('.tvsa-caret-down').css('color','#000');
				
			
			}else{
			
			 $(this).siblings('.degradado').css("visibility","visible");
			 $(this).siblings('.nav').children().children('.tvsa-caret-down').css('color','#FFF');
			}
			
			if($position_mix == 0){
				$(this).siblings('.nav').children().children('.tvsa-caret-up').css('color','#000');
			}else{
				$(this).siblings('.nav').children().children('.tvsa-caret-up').css('color','#FFF');
			}
		});
	/*END: Swipe*/
	
	
	
	
	$('.mix_15arts_02 .list_container').bind('swipedown',function(){
		
		if($(this).scrollTop() == 0){
			$('body,html').animate({
				scrollTop: $(window).scrollTop() - 265
			}, 800);
			return false;
		}
	});	
	
	var scrollNew = 2;
	$('.mix_15arts_02 .list_container').bind('swipeup',function(){
		
		if(scrollNew == $(this).scrollTop()){
			$('body,html').animate({
				scrollTop: $(window).scrollTop() + 265
			}, 800);
			return false;
		}
		else{
			scrollNew = $(this).scrollTop();
		}
	});
}); 