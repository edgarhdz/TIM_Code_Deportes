// JavaScript Document
var $large = 0;
$(document).ready(function(e) {
    $('.wdg_twitt_02').each(function(ix, element) {
        $elements = $(this).children().children('.wdg_twitt_02_carousel').children().children().size();
        $large = $(this).children().children('.wdg_twitt_02_carousel').children().children().width();
        $large = 958; //$elements * $large + (29 * ($elements - 1));

        if (!$.browser.msie){
            window.setTimeout("$('.wdg_twitt_02 .wdg_twitt_02_carousel ul').attr('style', 'width: '+$large+'px !important');",500);
        }
        if ($.browser.msie && parseInt($.browser.version, 10) <= 8){
            //$large += 100;
            $large = 958;
            window.setTimeout("$('.wdg_twitt_02 .wdg_twitt_02_carousel ul').attr('style', 'width: '+$large+'px !important');",2000);
            //alert($large);

        }
    });
    /*Monitor flechas*/
    $('.wdg_twitt_02 .tvsa-double-caret-left').addClass('inactive');
    $('.wdg_twitt_02 .tvsa-double-caret-left').click(function() {
        /*Verifico posiciÃ³n del scroll*/
        var large_tot = $(this).parent().parent().parent().parent().siblings('.wdg_twitt_02_carousel').children().width();
        var position = $(this).parent().parent().parent().parent().siblings('.wdg_twitt_02_carousel').scrollLeft();
        med = position + $(this).parent().parent().parent().parent().siblings('.wdg_twitt_02_carousel').width() + 201;
        if(position == 0 || position <= 329)
        {
            $(this).addClass('inactive');
            $(this).parents('.wdg_twitt_02').children('.bullets').children().removeClass();
            $(this).parents('.wdg_twitt_02').children('.bullets').children().eq(0).addClass('background-color1');
        }
        else
        {
            $(this).removeClass('inactive');
            $(this).parents('.wdg_twitt_02').children('.bullets').children().removeClass();
            $(this).parents('.wdg_twitt_02').children('.bullets').children().eq(1).addClass('background-color1');
        }
        $(this).parent().parent().siblings('.wt2right').children().children().removeClass('inactive');
    });

    $('.wdg_twitt_02 .tvsa-double-caret-right').click(function() {
        //alert($large);
        // Reafirmamos el ancho del UL --- Problemas en IE
        if ($.browser.msie ){
            $('.wdg_twitt_02 .wdg_twitt_02_carousel ul').attr('style', 'width: '+$large+'px !important');
        }
        /*Verifico posiciÃ³n del scroll*/
        var large_tot = $(this).parent().parent().parent().parent().siblings('.wdg_twitt_02_carousel').children().width();
        var position = $(this).parent().parent().parent().parent().siblings('.wdg_twitt_02_carousel').scrollLeft();
        med = position + $(this).parent().parent().parent().parent().siblings('.wdg_twitt_02_carousel').width() + 329;
        $(this).parent().parent().siblings('.wt2left').children().children().removeClass('inactive');
        if(med == large_tot || med > large_tot)
        {
            $(this).addClass('inactive');
            $(this).parents('.wdg_twitt_02').children('.bullets').children().removeClass();
            $(this).parents('.wdg_twitt_02').children('.bullets').children().eq(2).addClass('background-color1');
        }
        else
        {
            $(this).removeClass('inactive');
            $(this).parents('.wdg_twitt_02').children('.bullets').children().removeClass();
            $(this).parents('.wdg_twitt_02').children('.bullets').children().eq(1).addClass('background-color1');
        }

    });
    /*Swipe*/
    $('.wdg_twitt_02 .wdg_carousa .wdg_twitt_02_carousel').bind('swipeleft',function(){
        $('.wdg_twitt_02 .wdg_carousa .wdg_twitt_02_carousel').animate({
            'scrollLeft': $('.wdg_twitt_02 .wdg_carousa .wdg_twitt_02_carousel').scrollLeft() + 329
        }, 500);
    });
    $('.wdg_twitt_02 .wdg_carousa .wdg_twitt_02_carousel').bind('swiperight',function(){
        $('.wdg_twitt_02 .wdg_carousa .wdg_twitt_02_carousel').animate({
            'scrollLeft': $('.wdg_twitt_02 .wdg_carousa .wdg_twitt_02_carousel').scrollLeft() - 329
        }, 500);
    });
    /*Monitoreo scroll*/
    $('.wdg_twitt_02 .wdg_twitt_02_carousel').scroll(function() {
        var $wt2_position = $(this).scrollLeft();
        var $wt2_med = $wt2_position;
        if($wt2_position == 0){
            $(this).parents('.wdg_twitt_02').children('.bullets').children().removeClass();
            $(this).parents('.wdg_twitt_02').children('.bullets').children().eq(0).addClass('background-color1');
        }
        else{
            $(this).parents('.wdg_twitt_02').children('.bullets').children().removeClass();
            $(this).parents('.wdg_twitt_02').children('.bullets').children().eq(1).addClass('background-color1');
        }
        if($wt2_med > 350){
            $(this).parents('.wdg_twitt_02').children('.bullets').children().removeClass();
            $(this).parents('.wdg_twitt_02').children('.bullets').children().eq(2).addClass('background-color1');
        }
    });

    $('.wdg_twitt_02').bind("touchmove",function(event){
        event.preventDefault();
    });

});