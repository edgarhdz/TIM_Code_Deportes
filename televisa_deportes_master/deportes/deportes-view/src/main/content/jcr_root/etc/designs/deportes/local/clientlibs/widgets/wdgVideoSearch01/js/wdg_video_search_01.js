;jQuery(function($){ 
    (function($,T){
        $('.wdg_video_search_01').each(function(ix,element){ 
            var $this = $(this), 
                Pointer = {
                    UP: (T.getIsTouchDevice()) ? 'touchend' : 'mouseup',
                    DOWN: (T.getIsTouchDevice()) ? 'touchstart' : 'mousedown'
                }, 
                $theUlwdg_video_search_01 = $this.find('>ul')
            ;
            
            $this.find('a.prev, a.next, .deportes-prev, .deportes-next').click(function(event){
                event.preventDefault();
            });
            
            $this.find('a.prev, .deportes-prev').bind(Pointer.DOWN,function(){
                $theUlwdg_video_search_01.animate({
                    'scrollTop': $theUlwdg_video_search_01.scrollTop() - $theUlwdg_video_search_01.height()
                }, 500);
            });
            
            $this.find('a.next, .deportes-next').bind(Pointer.DOWN,function(){
                $theUlwdg_video_search_01.animate({
                    'scrollTop': $theUlwdg_video_search_01.scrollTop() + $theUlwdg_video_search_01.height()
                }, 500);
            }); 
        });
		
		$('.wdg_video_search_01 .scroll').scroll(function() {
				if($(this).scrollTop()>460){
				 $('.wdg_video_search_01 .degraded').css("visibility","hidden");
				}else{
				 $('.wdg_video_search_01 .degraded').css("visibility","visible");
				}
			});	

        var $parent = $('.wdg_video_search_01');
        var $dropdownAnchor = $parent.find('.lineaResultado');
        $dropdownAnchor.on('click', function(evt) {
            var $listItems = $(this).find('.wdg_video_search_01_dropdownlist');
            var $visibility = $listItems.css('visibility');
            var padre =$(this);
            if ( $visibility == 'hidden' ) 
                $listItems.css({
                    visibility: 'visible',
                    height: 'auto',
                    'max-height' : '156px',
                    'overflow-y': 'scroll',
                    'overflow-x': 'hidden'         
                });
             else 
                 $listItems.css({
                    visibility: 'hidden',
                    height: '0px'
            });
              var $dropdownItems2 = $(this).find('.wdg_video_search_01_dropdownlist li');
            $dropdownItems2.bind('click', function(evt) {
                console.log('Entando');
                evt.preventDefault();
                padre.find('.wdg_video_search_01_dropdowncontent p').html($(this).find('p').html());
            });
           
            $listItems.bind('mouseleave', function(evt) {
                evt.preventDefault();
                var visibilidad = $(this).css('visibility');
                if ( visibilidad == 'visible' ) {
                     $(this).css({
                        visibility: 'hidden',
                        height: '0px'       
                    });
                } 
            });
        });

    })($,Televisa);
    
    //BEGIN: swipe ......................................................................................................
    $(document).ready(function(){    	
    	
    	var $height = parseInt($('.wdg_video_search_01 .scroll').height()) + 28;
		
    	$theUlwdg_video_search_01 = $('.wdg_video_search_01 .scroll');
		$('.wdg_video_search_01 .scroll').bind('swipeup',function(event){
			$(this).animate({
                    'scrollTop': $theUlwdg_video_search_01.scrollTop() + 494
                }, 500);

			$scrollH = $('.wdg_video_search_01 .scroll').scrollTop();			
		
			 if($height == $scrollH){
                 // $('body,html').animate({
                 //     scrollTop: $(window).scrollTop() + 100
                 // }, 800);
                 // return false;
                 event.stopPropagation(); 
             }
		});
		
		$('.wdg_video_search_01 .scroll').bind('swipedown',function(event){
			$(this).animate({
                    'scrollTop': $theUlwdg_video_search_01.scrollTop() - 494
                }, 500);
			$scrollH = $theUlwdg_video_search_01.scrollTop() + $theUlwdg_video_search_01.height();
			if( $theUlwdg_video_search_01.scrollTop() == 0){
                // $('body,html').animate({
                //      scrollTop: $(window).scrollTop() - 100
                //  }, 800);
                //  return false;
                event.stopPropagation();
			}
		});
		//-----------------------------------------------------------------------
		 $('.wdg_video_search_01 .scroll').scroll(function(event){
             $scrollH = $theUlwdg_video_search_01.scrollTop() + $theUlwdg_video_search_01.height();
             if($height == $scrollH){
                  event.stopPropagation();
             }
             if($(this).scrollTop() == 0){
                 event.stopPropagation();
             }
         });		 		
		 
		//END: swipe......................................................................................................
		 
    });
    
});

if ($.browser.msie && parseInt($.browser.version, 10) <= 7){
      $(function() {
        var zIndexNumber = 1000;
        $('.wdg_video_search_01 div').each(function() {
            $(this).css('zIndex', zIndexNumber);
            zIndexNumber -= 10;
        });
    });
 }
 