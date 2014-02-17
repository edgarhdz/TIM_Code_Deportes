// JavaScript Document
$(document).ready(function(e) {

    $('.wdg_top10_01').each(function(ix, element) {
        $wt10_elements = $('.wdg_top10_01 .wdg_top10_01_carousel ul li').size();
        $wt10_large = $('.wdg_top10_01 .wdg_top10_01_carousel').width();
        $wt10_large = $wt10_elements * $wt10_large + (29 * ($wt10_elements - 1));
        window.setTimeout("$('.wdg_top10_01 .wdg_top10_01_carousel').children().attr('style', 'width: '+$wt10_large+'px !important');",500);
        $wt10_move = 0;
    });


    /*Monitor flechas*/
    $('.wdg_top10_01 .tvsa-double-caret-left').click(function() {
        if($wt10_move == 0){}else{$wt10_move = $wt10_move - 1;}
        $(this).parents('.wdg_top10_01').find('.wdg_top10_01_carousel').animate({
            'scrollLeft': $(this).parents('.wdg_top10_01').find('.wdg_top10_01_carousel').scrollLeft() - 329
        }, 500);
        if($wt10_move <= 0)
        {
            $(this).addClass('inactive');
            $(this).parent().parent().siblings().children().children('.tvsa-double-caret-right').removeClass('inactive');
            $(this).parents('.wdg_top10_01').children('.bullets').children().removeClass();
            $(this).parents('.wdg_top10_01').children('.bullets').children().eq(0).addClass('background-color1');
        }
        else
        {
            $(this).parents('.wdg_top10_01').find('.bullets').children().removeClass();
            $(this).parents('.wdg_top10_01').find('.bullets').children().eq(1).addClass('background-color1');
        }
        $(this).parent().parent().siblings('.wt2right').children().children().css('color', '#000000');
    });


    $('.wdg_top10_01 .tvsa-double-caret-right').click(function() {
        console.log("click here");
        /*Verifico posiciÃ³n del scroll*/
        if ($.browser.msie && parseInt($.browser.version, 10) <= 8){
            var $wt10_large = 625;
            $('.wdg_top10_01 .wdg_top10_01_carousel').children().attr('style', 'width: '+$wt10_large+'px !important');
        }

        $(this).parents('.wdg_top10_01').find('.wdg_top10_01_carousel').animate({
            'scrollLeft': $(this).parents('.wdg_top10_01').find('.wdg_top10_01_carousel').scrollLeft() + 329
        }, 500);
        if($wt10_move == 1){}else{$wt10_move = $wt10_move + 1;}
        if($wt10_move == 1)
        {
            $(this).addClass('inactive');
            $(this).parents('.wdg_top10_01').find('.tvsa-double-caret-left').removeClass('inactive');
            $(this).parents('.wdg_top10_01').find('.bullets').children().removeClass();
        }
        else
        {
            $(this).removeClass('inactive');
            $(this).parents('.wdg_top10_01').find('.bullets').children().removeClass();
            $(this).parents('.wdg_top10_01').find('.bullets').children().eq(1).addClass('background-color1');
        }

    });


    /*Swipe*/
    $('.wdg_top10_01 .wdg_carousa .wdg_top10_01_carousel').bind('swipeleft',function(){
        $('.wdg_top10_01 .wdg_carousa .wdg_top10_01_carousel').animate({
            'scrollLeft': $('.wdg_top10_01 .wdg_carousa .wdg_top10_01_carousel').scrollLeft() + 329
        }, 500);
    });

    $('.wdg_top10_01 .wdg_carousa .wdg_top10_01_carousel').bind('swiperight',function(){
        $('.wdg_top10_01 .wdg_carousa .wdg_top10_01_carousel').animate({
            'scrollLeft': $('.wdg_top10_01 .wdg_carousa .wdg_top10_01_carousel').scrollLeft() - 329
        }, 500);
    });

    /*Monitoreo scroll*/
    $('.wdg_top10_01 .wdg_top10_01_carousel').scroll(function() {
        var $wt10_position = $(this).scrollLeft();
        var $wt10_med = $wt10_position;
        if($wt10_position == 0){
            $(this).parents('.wdg_top10_01').children('.bullets').children().removeClass();
            $(this).parents('.wdg_top10_01').children('.bullets').children().eq(0).addClass('background-color1');
        }
        else{
            $(this).parents('.wdg_top10_01').children('.bullets').children().removeClass();
            $(this).parents('.wdg_top10_01').children('.bullets').children().eq(1).addClass('background-color1');
        }
        if($wt10_med > 350){
            $(this).parents('.wdg_top10_01').children('.bullets').children().removeClass();
        }
    });

    $('.wdg_top10_01').bind("touchmove",function(event){
        event.preventDefault();
    });
});