;jQuery(function($){
    (function($,T){
        //Aggregate on : 16/Jun/2013, 04:02:15 PM
        //Author     		 : Isaac AarÃ³n DÃ­az Reveles    	
        $('.wdg_goals_carru_01 .deg').scroll(function() {
            if($(this).scrollTop()>=329){
                $('.wdg_goals_carru_01 .degraded').css("visibility","hidden");
            }else{
                $('.wdg_goals_carru_01 .degraded').css("visibility","visible");
            }
        });

        /*Swipe*/
        $('.wdg_goals_carru_01 .deg').bind('swipeup',function(){
            $(this).animate({
                'scrollTop': $(this).scrollTop() + 265
            }, 500);
        });
        $('.wdg_goals_carru_01 .deg').bind('swipedown',function(){
            $(this).animate({
                'scrollTop': $(this).scrollTop() - 265
            }, 500);
        });
        //----------------

        $('.wdg_goals_carru_01').each(function(ix,element){
            var $this = $(this),
                Pointer = {
                    UP: (T.getIsTouchDevice()) ? 'touchend' : 'mouseup',
                    DOWN: (T.getIsTouchDevice()) ? 'touchstart' : 'mousedown'
                },
                $wgc1_theUl = $this.find('>ul')
                ;

            $this.find('a.prev, a.next, .deportes-prev, .deportes-next').click(function(event){
                event.preventDefault();
            });

            $this.find('a.prev, .deportes-prev').bind(Pointer.DOWN,function(){
                $wgc1_theUl.animate({
                    'scrollTop': $wgc1_theUl.scrollTop() - $wgc1_theUl.height()
                }, 500);
            });

            $this.find('a.next, .deportes-next').bind(Pointer.DOWN,function(){
                $wgc1_theUl.animate({
                    'scrollTop': $wgc1_theUl.scrollTop() + $wgc1_theUl.height()
                }, 500);
            });
        });


        $wgc_list = $('.wdg_goals_carru_01 .deg li').size();
        $wgc_altura_li = $('.wdg_goals_carru_01 .deg li').outerHeight();
        $wgc_altura = $wgc_altura_li * $wgc_list;
        $('.wdg_goals_carru_01 .deg').scroll(function() {
            $wgc_scrollTop = parseInt($(this).scrollTop()) + parseInt($(this).height());
            //console.log('Movimiento: '+$wgc_scrollTop+' Altura_tot: '+$wgc_altura +' Elementos: '+ $wgc_list+' Cada uno: '+$wgc_altura_li);
            if($(this).scrollTop() == 0){
                $(this).siblings('.controls').children('.prev').addClass('bginactive');
                $(this).siblings('.controls').children('.prev').removeClass('bgactive');
            }else{
                $(this).siblings('.controls').children('.prev').removeClass('bginactive');
                $(this).siblings('.controls').children('.prev').addClass('bgactive');
            }
            if($wgc_scrollTop == $wgc_altura){
                $(this).siblings('.degradado').css("visibility","hidden");
                $(this).siblings('.controls').children('.next').addClass('bginactive');
                $(this).siblings('.controls').children('.next').removeClass('bgactive');
            }else{
                $(this).siblings('.degradado').css("visibility","visible");
                $(this).siblings('.controls').children('.next').removeClass('bginactive');
                $(this).siblings('.controls').children('.next').addClass('bgactive');
            }
        });

        var $parent = $('.wdg_goals_carru_01 ');
        var $dropdownAnchor = $parent.find('.lineaResultado');
        $dropdownAnchor.on('click', function(evt) {
            var $listItems = $(this).find('.wdg_goals_carru_012_dropdownlist');
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
            var $dropdownItems2 = $(this).find('.wdg_goals_carru_012_dropdownlist li');
            $dropdownItems2.bind('click', function(evt) {
                evt.preventDefault();
                padre.find('.wdg_goals_carru_012_dropdowncontent p').html($(this).find('p').html());
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
});

if ($.browser.msie && parseInt($.browser.version, 10) <= 7){
    $(function() {
        var zIndexNumber = 1000;
        $('.wdg_goals_carru_01 div').each(function() {
            $(this).css('zIndex', zIndexNumber);
            zIndexNumber -= 10;
        });
    });
}
 