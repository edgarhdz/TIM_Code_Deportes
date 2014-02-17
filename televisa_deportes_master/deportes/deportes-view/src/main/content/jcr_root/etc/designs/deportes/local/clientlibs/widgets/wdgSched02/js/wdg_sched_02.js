;jQuery(function($){ 
    (function($,T){

        $('.wdg_sched_02').each(function(ix,element){ 
            var $this = $(this), 
                Pointer = {
                    UP: (T.getIsTouchDevice()) ? 'touchend' : 'mouseup',
                    DOWN: (T.getIsTouchDevice()) ? 'touchstart' : 'mousedown'
                }, 
                $theUl = $this.find('>ul')
            ;
            
            $this.find('a.prev, a.next, .deportes-prev, .deportes-next').click(function(event){
                event.preventDefault();
            });
            
            $this.find('a.prev, .deportes-prev').bind(Pointer.DOWN,function(){
                $theUl.animate({
                    'scrollTop': $theUl.scrollTop() - $theUl.height()
                }, 900);
            });
            
            $this.find('a.next, .deportes-next').bind(Pointer.DOWN,function(){
                $theUl.animate({
                    'scrollTop': $theUl.scrollTop() + $theUl.height()
                }, 900);
            }); 
        });
		
		/*Swipe*/
		$(document).ready(function(){
			$('.wdg_sched_02 #deg').bind('swipeup',function(){
				$(this).animate({
						'scrollTop': $('.wdg_sched_02 #deg').scrollTop() + 270
					}, 500);
			});
			$('.wdg_sched_02 #deg').bind('swipedown',function(){
				$(this).animate({
						'scrollTop': $('.wdg_sched_02 #deg').scrollTop() - 270
					}, 500);
			});
		});
		
 		//Altura
		$elements = $('.wdg_sched_02 ul#deg li').size() + 1;
		$altura =0;
		
		/*Tamaño de pantalla modificado*/
		$(window).resize(function() {
			if($.browser.msie && $.browser.version < 9){
				if($(window).width()>947){
					$alt_w = 690;
				}
				else
				{
					$alt_w = 222;
				}
			}else{
				$alt_w = $('.wdg_sched_02 #deg').height();
			}
		});
		/*Hack < IE8*/
		if ( $.browser.msie && $.browser.version < 9) {
					$elements = $elements -1;
					$altura = $altura + $('.wdg_sched_02 ul li').outerHeight();
					$altura = $altura * $elements;	
					if ($(window).width()>947){
							$alt_w = 690;
						}
						else
						{
							$alt_w = 222;
						}
		}
		else
		{
			for($i=1;$i<$elements;$i++){
				$altura = $altura + $('.wdg_sched_02 ul#deg li:nth-child('+$i+')').outerHeight();
			}
			$alt_w = $('.wdg_sched_02 #deg').height();
		}
		
		$('.wdg_sched_02 #deg').scroll(function() {
				var $position = $(this).scrollTop();
				var $med = $position + $alt_w; 
				var $down = $(this).siblings('.nav').children().children('.tvsa-caret-down').parent();
				var $up = $(this).siblings('.nav').children().children('.tvsa-caret-up').parent();
				
				console.log($position);
				
				if($med == $altura){
					// inactivo
					 $(this).siblings('.degraded').css("visibility","hidden");
					 $down
					 .removeClass('bgactive')
					 .addClass('bginactive');
				}
				// Hack IE 7 
				else if ($.browser.msie && parseInt($.browser.version, 10) == 7 && $position >= 740){
					// inactivo
					 $(this).siblings('.degraded').css("visibility","hidden");
					 $down
					 .removeClass('bgactive')
					 .addClass('bginactive');
					 console.log($position);
				}
				// Hack IE 7 
				else if ($.browser.msie && parseInt($.browser.version, 10) >= 8 && $position >= 680){
					// inactivo
					 $(this).siblings('.degraded').css("visibility","hidden");
					 $down
					 .removeClass('bgactive')
					 .addClass('bginactive');
					 console.log($position);
				}
				else{
					// activo
					 $(this).siblings('.degraded').css("visibility","visible");
					 $down
					 .removeClass('bginactive')
					 .addClass('bgactive');
				}
				if($position == 0){
					$up
					.removeClass('bgactive')
					.addClass('bginactive');
				}else{
					$up
					.removeClass('bginactive')
					.addClass('bgactive');
				}
			});	
    })($,Televisa);
});
