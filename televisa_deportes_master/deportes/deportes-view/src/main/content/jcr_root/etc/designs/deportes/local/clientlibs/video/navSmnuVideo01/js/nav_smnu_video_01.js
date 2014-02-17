;jQuery(function($){
    (function(T, $) {

        /*Monitorero flechas*/
        $('.nav_smnu_video_01 .navarrowleft').click(function(e) {
            e.preventDefault();
            /*$push= $('.nav_smnu_video_01 .container').scrollLeft();
             $push = $push - 60;
             $('.nav_smnu_video_01 .container').scrollLeft($push);*/
            $('.nav_smnu_video_01 .nav_smnu_video_01_bar').animate({
                'scrollLeft': $('.nav_smnu_video_01 .nav_smnu_video_01_bar').scrollLeft() - 350
            });
        });

        $('.nav_smnu_video_01 .navarrowright').click(function(e) {
            e.preventDefault();

            $('.nav_smnu_video_01 .nav_smnu_video_01_bar').animate({
                'scrollLeft': $('.nav_smnu_video_01 .nav_smnu_video_01_bar').scrollLeft() + 350
            });
        });

        /*Swipe*/
        $('.nav_smnu_video_01 .nav_smnu_video_01_bar').bind('swipeleft',function(){
            $(this).animate({
                'scrollLeft': $(this).scrollLeft() + 100
            }, 500);
        });
        $('.nav_smnu_video_01 .nav_smnu_video_01_bar').bind('swiperight',function(){
            $(this).animate({
                'scrollLeft': $(this).scrollLeft() - 100
            }, 500);
        });

        /*Monitoreo inactive*/

        $('.nav_smnu_video_01 .nav_smnu_video_01_bar').scroll(function(){
            var $scroll = $(this).scrollLeft();
            var $width = $scroll + $(this).width();
            var $widthT = $(this).find('ul').width();
            if ($scroll ==  0){
                $('.nav_smnu_video_01 .tvsa-double-caret-left').addClass('inactivenav');
            }
            else if($width==$widthT){
                $('.nav_smnu_video_01 .tvsa-double-caret-right').addClass('inactivenav');
            }
            else{
                $('.nav_smnu_video_01 .tvsa-double-caret-left').removeClass('inactivenav');
                $('.nav_smnu_video_01 .tvsa-double-caret-right').removeClass('inactivenav');
            }
        });

        if($(window).width() < 624){
            $('.nav_smnu_video_01 .nav_smnu_video_01_bar').scroll(function(){
                var $scroll = $(this).scrollLeft();
                var $width = $scroll + $(this).width();
                var $widthT = $(this).find('ul').width();
                if ($scroll ==  0){
                    $('.nav_smnu_video_01 .tvsa-double-caret-left').addClass('inactivenav');
                    $('.nav_smnu_video_01 .degraded_darked_left').hide();
                }
                else if($width==$widthT){
                    $('.nav_smnu_video_01 .tvsa-double-caret-right').addClass('inactivenav');
                    $('.nav_smnu_video_01 .degraded_darked_right').hide();
                }
                else{
                    $('.nav_smnu_video_01 .tvsa-double-caret-left').removeClass('inactivenav');
                    $('.nav_smnu_video_01 .tvsa-double-caret-right').removeClass('inactivenav');
                    $('.nav_smnu_video_01 .degraded_darked_left').show();
                    $('.nav_smnu_video_01 .degraded_darked_right').show();
                }
            });
        }

    }(Televisa, jQuery));
});