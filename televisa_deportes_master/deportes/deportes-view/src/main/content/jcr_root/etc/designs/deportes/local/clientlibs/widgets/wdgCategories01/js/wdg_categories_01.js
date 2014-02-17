;jQuery(function($){ 
    (function(T, $) {
        if($.browser.msie && parseFloat($.browser.version) <= 8){

        }
        else{
            $(window).resize(function() {
                if( $(window).width() > 960  ){
                    $('.wdg_categories_01 li:lt(15)').show();
                    $('.wdg_categories_01 li:gt(15)').hide();
                }
                
                else if( $(window).width() > 623 && $(window).width() <= 960){
                    $('.wdg_categories_01 li:lt(10)').show();
                    $('.wdg_categories_01 li:gt(10)').hide();
                }
                else if( $(window).width() < 624 ){ 
                    $('.wdg_categories_01 li:lt(5)').show();
                    $('.wdg_categories_01 li:gt(5)').hide();
                }
            });
        }
        $('.wdg_categories_01 #verTodo').click(function(event){
            event.preventDefault();
            $(this).hide();
			$('.wdg_categories_01 .ocultar').show();
            $('.wdg_categories_01 li').show();
            
        });
		$('.wdg_categories_01 #ocultar').click(function(event){
            event.preventDefault();
            $(this).hide();
			$('.wdg_categories_01 #verTodo').show();
			if( $(window).width() > 960  ){
            	$('.wdg_categories_01 li:gt(14)').hide();
			}
			else if( $(window).width() > 623 && $(window).width() <= 960){
                    $('.wdg_categories_01 li:gt(9)').hide();
                }
                else if( $(window).width() < 624 ){ 
                    $('.wdg_categories_01 li:gt(5)').hide();
                }
            
        });
        if (T.getDeviceSize() === 'small') {
            jQuery('.wdg_categories_01 li:lt(5)').show();
        }
        if (T.getDeviceSize() === 'medium') {
            jQuery('.wdg_categories_01 li:lt(10)').show();
        }
        if (T.getDeviceSize() === 'large') {
            jQuery('.wdg_categories_01 li:lt(15)').show();
        }


    }(Televisa, jQuery));
});
