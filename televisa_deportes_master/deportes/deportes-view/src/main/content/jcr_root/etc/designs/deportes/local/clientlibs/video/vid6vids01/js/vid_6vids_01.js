;jQuery(function($){ 
    (function ($, T) {

        var $m = $('.wdg_mix_carrousa div:last-child'),
        animationDelay = 500;
        var $w = $('.wdg_mix_carrousa div:last-child ul li');
        var $count = 0;
        $w.each(function() {
            $count ++;
        });
        $m.each(function() {
            var $total = 160;
            if( $count <= 2){
               $total = 60; 
            }
            var $items = $(this).find('ul li'),
            padding = ($(this).parent().hasClass('wdg_mix_carrousa')) ? 41 : 24,
            Pointer = {
                UP: (T.getIsTouchDevice()) ? 'touchend' : 'mouseup',
                DOWN: (T.getIsTouchDevice()) ? 'touchstart' : 'mousedown'
            },
            $parent = $(this);

            //$(this).find('ul').width(($items.outerWidth(true) + padding) * $items.length - $total + 15);
			/*Anchura........*/
			//alert($(window).width());
			if( $(window).width() > 960  ){
				$('.carrusel-sizes').width('600px');
			}
			if( $(window).width() >= 624 && $(window).width() <= 960  ){
				$('.carrusel-sizes').width('900px');
			}
			if( $(window).width() < 624 ){
				$('.carrusel-sizes').width($(window).width());
			}
			/*Default*/
			
			
            if (/Android|iPhone|iPod|iPad/.test(navigator.userAgent) && // If it's an iPad, iPod or iPhone
                !(/OS [2-4]_\d(_\d)? like Mac OS X/i.test(navigator.userAgent))) { // And the version of the OS is bigger than 4.x...
                $(this).addClass('iOS');
            } else {
                    
                // Touch devices (non-iOS or iOS < 5)
                var isWinPhone = /Windows Phone/.test(navigator.userAgent);

                if (T.getIsTouchDevice() && !T.isBlackBerryCurve()) {

                    // Hide the arrows if it's not carouseltype1b
                    /*if (!$(this).parent().hasClass('wdg_mix_carrousa')) {
					$(this).parent().find('div:first-child ul').hide();
				}*/

                    $(this).bind('touchstart', function(e) {
                        var clientX = e.originalEvent.changedTouches[0].clientX;

                        $(this).attr('startX', clientX);
                        $(this).attr('down', true);
                        $(this).attr('scrollLeft', $(this).scrollLeft());
                    });

                    $(this).bind('touchend', function(e) {
                        if ($(this).attr('active') === 'true') {
                            e.preventDefault();
                            e.stopPropagation();
                        }

                        $(this).attr('active', false);
                        $(this).attr('down', false);
                    });

                    $(this).bind('touchmove', function(e) {
                                        
                                        
                        var clientX = e.originalEvent.changedTouches[0].clientX,
                        sx = $(this).attr('startX') >> 0,
                        ex = clientX;

                        if (Math.abs(sx - ex) > 10) {
                            $(this).attr('active', true);
                        }

                        if ($(this).attr('active') === 'true') {
                            e.preventDefault();

                            $(this).scrollLeft(($(this).attr('scrollLeft') >> 0) + (sx - ex));
                        }

                        return false;
                    });
                } else {
                    // Force the arrows to show up
                    $(this).parent().addClass('showArrows');
                }
            }

            // Support the click event on arrows
            $parent.parent().find('a.wdg_carousa_left').click(function(e) {
                e.preventDefault();
				if( $(window).width() >= 624 && $(window).width() <= 960  ){
					$parent.animate({
						'scrollLeft': $parent.scrollLeft() - 300
					}, animationDelay);
				}else
				{
					$parent.animate({
						'scrollLeft': $parent.scrollLeft() - 329
					}, animationDelay);
					
				}
                        
            });

            $parent.parent().find('a.wdg_carousa_right').click(function(e) {
                e.preventDefault();
				if( $(window).width() >= 624 && $(window).width() <= 960  ){
					$parent.animate({
						'scrollLeft': $parent.scrollLeft() + 300
					}, animationDelay);
				}else
				{
					$parent.animate({
                    'scrollLeft': $parent.scrollLeft() + 329
                	}, animationDelay);
				}
            });
	
			
        });

		/*Diferente ventana*/
		$(window).resize(function() {
			if( $(window).width() > 960  ){
				$('.carrusel-sizes').width('600px');
			}
			if( $(window).width() > 624 && $(window).width() < 960  ){
				$('.carrusel-sizes').width('900px');
			}
			if( $(window).width() < 624 ){
				$('.carrusel-sizes').width($(window).width());
			}
		});
		
		/*Ver más modo mobile*/
		$('.vid_6vids_01 .vid_6vids_01_verMas').click(function(event){
            event.preventDefault();
            $('.vid_6vids_01').css('height','auto');
            $('.vid_6vids_01 .vid_6vids_01_verMas').hide();
		});
		
    }(jQuery, Televisa));
});

