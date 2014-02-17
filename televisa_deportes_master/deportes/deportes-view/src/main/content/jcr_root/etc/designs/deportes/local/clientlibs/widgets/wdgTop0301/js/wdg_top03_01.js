// JavaScript Document
$(document).ready(function(e) {



    $('.wdg_top03_01').each(function(ix, element) {
        $wt10_elements = $(this).children().children('.wdg_top03_01_carousel').children().children().size();
        $wt10_large = $(this).children().children('.wdg_top03_01_carousel').children().children().width();
        $wt10_large = $wt10_elements * $wt10_large + (29 * ($wt10_elements - 1));
        window.setTimeout("$('.wdg_top03_01 .wdg_top03_01_carousel').children().attr('style', 'width: '+$wt10_large+'px !important');",500);
        $wt10_move = 0;
    });

    /*Monitor flechas*/
    $('.wdg_top03_01 .tvsa-double-caret-left').click(function() {

        /*Verifico posiciÃ³n del scroll*/
        if($wt10_move == 0){}else{$wt10_move = $wt10_move - 1;}
        $(this).parent().parent().parent().parent().siblings('.wdg_top03_01_carousel').animate({'scrollLeft': $(this).parent().parent().parent().parent().siblings('.wdg_top03_01_carousel').scrollLeft() - 329}, 500);
        $(this).parent().parent().siblings('.wt2right').children().children().css('color', '#000000');
    });

    $('.wdg_top03_01 .tvsa-double-caret-right').click(function() {

        if ($.browser.msie && parseInt($.browser.version, 10) <= 8){
            var $wt10_large = 958;
            $('.wdg_top03_01 .wdg_top03_01_carousel').children().attr('style', 'width: '+$wt10_large+'px !important');
        }

        /*Verifico posiciÃ³n del scroll*/
        $(this).parent().parent().parent().parent().siblings('.wdg_top03_01_carousel').animate({
            'scrollLeft': $(this).parent().parent().parent().parent().siblings('.wdg_top03_01_carousel').scrollLeft() + 329
        }, 500);
    });

    /*Swipe*/
    $('.wdg_top03_01 .wdg_carousa .wdg_top03_01_carousel').bind('swipeleft',function(){
        $('.wdg_top03_01 .wdg_carousa .wdg_top03_01_carousel').animate({
            'scrollLeft': $('.wdg_top03_01 .wdg_carousa .wdg_top03_01_carousel').scrollLeft() + 329
        }, 500);
    });
    $('.wdg_top03_01 .wdg_carousa .wdg_top03_01_carousel').bind('swiperight',function(){
        $('.wdg_top03_01 .wdg_carousa .wdg_top03_01_carousel').animate({
            'scrollLeft': $('.wdg_top03_01 .wdg_carousa .wdg_top03_01_carousel').scrollLeft() - 329
        }, 500);
    });

    /*Monitoreo scroll*/
    $('.wdg_top03_01 .wdg_top03_01_carousel').scroll(function() {
        var $wt10_position = $(this).scrollLeft();
        var $wt10_med = $wt10_position;
        if($wt10_position == 0){
            $('.wdg_top03_01 .tvsa-double-caret-left').addClass('inactive');
            $(this).parents('.wdg_top03_01').children('.bullets').children().removeClass();
            $(this).parents('.wdg_top03_01').children('.bullets').children().eq(0).addClass('background-color1');
        } else {
            $('.wdg_top03_01 .tvsa-double-caret-left').removeClass('inactive');
            $('.wdg_top03_01 .tvsa-double-caret-right').removeClass('inactive');
            $(this).parents('.wdg_top03_01').children('.bullets').children().removeClass();
            $(this).parents('.wdg_top03_01').children('.bullets').children().eq(1).addClass('background-color1');
        }
        if($wt10_med > 350) {
            $('.wdg_top03_01 .tvsa-double-caret-right').addClass('inactive');
            $(this).parents('.wdg_top03_01').children('.bullets').children().removeClass();
            $(this).parents('.wdg_top03_01').children('.bullets').children().eq(2).addClass('background-color1');
        }
    });
    $('.wdg_top03_01').bind("touchstart",function(event){
        event.preventDefault();
    });
});