;jQuery(function($){
    (function(T, $) {
        var $x = $('.nav_team_header_01_nav');
        var $y = $('.nav_team_header_01_nav ul li');
        var $z = $('.nav_team_header_01_nav ul li:first-child');
        var $show = $('.wdg_matchesresult_hide');
        var $hide = $('.wdg_matchesresult_show');
        var $elements = $('.nav_team_header_01_nav ul li').size();
        var $totalWidth = 0;
        var $slide = $z.outerWidth(true);
        var $position = 1;
        $('.nav_team_header_01 .wdg_matchesresult_navlefticon .tvsa-double-caret-left').css('color', '#BABABA');

        $('.nav_team_header_01 .nav_team_header_01_nav p').on('mouseenter',function(e){
            $(this).parents('li').addClass('border-bottom2');
            $(this).addClass('textcolor-title2');
        }).on('mouseleave',function(e){
                if( ! $(this).parent().hasClass('textcolor-title2') ){
                    $(this).removeClass('textcolor-title2');
                    $(this).parents('li').removeClass('border-bottom2');
                }else{
                    //No elimina la clase
                }

            });

        $y.each(function() {
            $totalWidth += $(this).outerWidth(true);
        });
        var $m = $('.nav_team_header_01_components'),
            animationDelay = 500;
        var $w = $('.nav_team_header_01_components ul li');
        var $count = 0;
        $w.each(function() {
            $count ++;
        });
        var $unitary = $w.outerWidth(true);
        var $unitaryH = $w.outerHeight(true);
        var $round = 1;
        if($.browser.msie && parseFloat($.browser.version) <= 8){

        }
        else{
            $(window).resize(function() {
                var visShow = $show.css('visibility');
                //var visHide = $hide.css('visibility'); 
                $unitary = $w.outerWidth(true);
                $totalWidth = 0;
                $y.each(function() {
                    $totalWidth += $(this).outerWidth(true);
                });

                if( $(window).width() > 960  ){
                    $round = Math.round($count/3);
                    if( ($count / 3) > $round )
                        $m.find('ul').width( ($round + 1 + 2) * $unitary);
                    else
                        $m.find('ul').width( ($round * $unitary) + 2);
                    $m.find('ul').height($unitaryH * 3);
                    $m.height($unitaryH * 3);
                    $x.find('ul').width( $totalWidth + 20 + 2 );
                    if ( visShow == 'hidden' ) {
                        $('.nav_team_header_01').animate({
                            'height': 575
                        }, 0);
                    }
                }
                if( $(window).width() > 623 && $(window).width() <= 960){
                    $round = Math.round($count/2);
                    if( ($count / 2) > $round )
                        $m.find('ul').width( ($round + 1 + 2) * $unitary);
                    else
                        $m.find('ul').width( ($round * $unitary) + 2);
                    $m.find('ul').height($unitaryH * 2);
                    $m.height($unitaryH * 2);
                    $x.find('ul').width( $totalWidth + 20 + 2);
                    if ( visShow == 'hidden' ) {
                        $('.nav_team_header_01').animate({
                            'height': 460
                        }, 0);
                    }
                }
                if( $(window).width() < 624 ){
                    $round = Math.round($count);
                    $m.find('ul').width( ($round * $unitary) + 2);
                    $m.height($unitaryH);
                    $m.find('ul').height($unitaryH);
                    if ( visShow == 'hidden' ) {

                    }
                    $('.nav_team_header_01').animate({
                        'height': 134
                    }, 0);

                }
                $m.animate({
                    'scrollLeft': 0
                }, 0);


            });
        }
        $m.each(function() {
            var $parent = $(this);
            var $items = $(this).find('ul li');
            $count = 0;
            $items.each(function() {
                $count ++;
            });
            if (T.getDeviceSize() === 'large') {
                $round = Math.round($count/3);
                if( ($count / 3) > $round )
                    $(this).find('ul').width( ($round + 1 + 2) * $unitary);
                else
                    $(this).find('ul').width( ($round * $unitary) + 2);
                $(this).find('ul').height($unitaryH * 3);
                $(this).height($unitaryH * 3);
            }
            if (T.getDeviceSize() === 'medium') {
                $round = Math.round($count/2);
                if( ($count / 2) > $round )
                    $(this).find('ul').width( ($round + 1 + 2) * $unitary);
                else
                    $(this).find('ul').width( ($round * $unitary) + 2);
                $(this).find('ul').height($unitaryH * 2);
                $(this).height($unitaryH * 2);
            }
            if (T.getDeviceSize() === 'small') {
                $round = Math.round($count);
                $(this).find('ul').width( ($round * $unitary) + 2);
                $(this).find('ul').height($unitaryH);
                $(this).height($unitaryH);
            }
        });
        $x.each(function() {

            var $parent = $(this);

            if (T.getDeviceSize() === 'small') {
                $x.find('ul').width( $totalWidth + 2);
            }
            $parent.parent().parent().find('a.wdg_matchesresult_navleft').click(function(e) {
                e.preventDefault();
                //calculate move
                $z = $('.nav_team_header_01_nav ul li:nth-child('+$position+')');
                $slide = $z.outerWidth(true) + 10;
                if($position == 1){}else{$position--;}
                $parent.animate({
                    'scrollLeft': $parent.scrollLeft() - $slide
                }, animationDelay,"linear",function(){
                    /*Verifico posición del scroll*/
                    var large_tot = $(this).children().width();
                    var position = $parent.scrollLeft();
                    med = position + $(this).parent().width();
                    if(position == 0)
                    {
                        $(this).parent().siblings('.wdg_matchesresult_navarrowleft').children().children().children().css('color', '#BABABA');
                    }
                    else
                    {
                        $(this).parent().siblings('.wdg_matchesresult_navarrowleft').children().children().children().css('color', '#000000');
                    }
                    if(med == large_tot)
                    {
                        $(this).parent().siblings('.wdg_matchesresult_navarrowright').children().children().children().css('color', '#BABABA');
                    }
                    else
                    {
                        $(this).parent().siblings('.wdg_matchesresult_navarrowright').children().children().children().css('color', '#000000');
                    }
                });
            });

            $parent.parent().parent().find('a.wdg_matchesresult_navright').click(function(e) {
                e.preventDefault();
                //calculate move
                $z = $('.nav_team_header_01_nav ul li:nth-child('+$position+')');
                $slide = $z.outerWidth(true) + 10;
                if($position == $elements){}else{$position++;}
                $parent.animate({
                    'scrollLeft': $parent.scrollLeft() + $slide
                }, animationDelay,"linear",function(){
                    /*Verifico posición del scroll*/
                    var large_tot = $(this).children().width();
                    var position = $parent.scrollLeft();
                    med = position + $(this).parent().width();
                    if(position == 0)
                    {
                        $(this).parent().siblings('.wdg_matchesresult_navarrowleft').children().children().children().css('color', '#BABABA');
                    }
                    else
                    {
                        $(this).parent().siblings('.wdg_matchesresult_navarrowleft').children().children().children().css('color', '#000000');
                    }
                    if(med == large_tot)
                    {
                        $(this).parent().siblings('.wdg_matchesresult_navarrowright').children().children().children().css('color', '#BABABA');
                    }
                    else
                    {
                        $(this).parent().siblings('.wdg_matchesresult_navarrowright').children().children().children().css('color', '#00000');
                    }
                });
            });
        });

        /*Monitoreo scroll*/
        $('.nav_team_header_01 .nav_team_header_01_nav').scroll(function() {
            var position3 =  parseInt($(this).scrollLeft());
            var large_tot3 =  parseInt($(this).children().width());
            med3 = parseInt(position3) + parseInt($(this).width());
            console.log('scroll esta en: '+position3+' largo tot: '+large_tot3+' suma: '+med3);
            if(position3 == 0)
            {
                $(this).parent().siblings('.wdg_matchesresult_navarrowleft').children().children().children().css('color', '#BABABA');
            }
            else
            {
                $(this).parent().siblings('.wdg_matchesresult_navarrowleft').children().children().children().css('color', '#000000');
            }
            if(med3 == large_tot3)
            {
                $(this).parent().siblings('.wdg_matchesresult_navarrowright').children().children().children().css('color', '#BABABA');
            }
            else
            {
                $(this).parent().siblings('.wdg_matchesresult_navarrowright').children().children().children().css('color', '#000000');
            }
        });


        var color = $('.nav_team_header_01 .link_color').attr('data-color');
        if(color == "" || color == null)
        {
            color = '#A70A0B';
            $('.nav_team_header_01 .selected a').css('color', color);
            $('.nav_team_header_01 .link_color').css('color',color);
        }
        else
        {
            $('.nav_team_header_01 .selected a').css('color', $('.nav_team_header_01 .link_color').attr('data-color'));
        }
        $('.nav_team_header_01 .nav_team_header_01_container .nav_team_header_01_right .nav_team_header_01_navcontainer .nav_team_header_01_nav ul li.selected').css('border-bottom','3px solid ' + color);


    }(Televisa, jQuery));
});
