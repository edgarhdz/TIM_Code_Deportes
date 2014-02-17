$(document).ready(function() {
    (function(T, $) {
        var $m = $('.wdg_matchesresult_01_components'),
        animationDelay = 500;
        var $w = $('.wdg_matchesresult_01_components ul li');
        var $count = 0;
        $w.each(function() {
            $count ++;
        });
        var $unitary = $w.outerWidth(true);
        var $unitaryH = $w.outerHeight(true);
        var $round = 1;
        $(window).resize(function() {
            $unitary = $w.outerWidth(true);
            if( $(window).width() > 960  ){
                $round = Math.round($count/3);
                if( ($count / 3) > $round )
                    $m.find('ul').width( ($round + 1) * $unitary);
                else
                    $m.find('ul').width( $round * $unitary);
                $m.find('ul').height($unitaryH * 3);
            }
            if( $(window).width() > 623 && $(window).width() <= 960){
                $round = Math.round($count/2);
                if( ($count / 2) > $round )
                    $m.find('ul').width( ($round + 1) * $unitary);
                else
                    $m.find('ul').width( $round * $unitary);
                $m.find('ul').height($unitaryH * 2);
            }
            if( $(window).width() < 624 ){    
                $round = Math.round($count);
                $m.find('ul').width( $round * $unitary);
                $m.find('ul').height($unitaryH);
            }
        });
        $m.each(function() {
            var $parent = $(this);
            if (T.getDeviceSize() === 'large') {
                $round = Math.round($count/3);
                if( ($count / 3) > $round )
                    $(this).find('ul').width( ($round + 1) * $unitary);
                else
                    $(this).find('ul').width( $round * $unitary);
                $m.find('ul').height($unitaryH * 3);
            }
            if (T.getDeviceSize() === 'medium') {
                $round = Math.round($count/2);
                if( ($count / 2) > $round )
                    $(this).find('ul').width( ($round + 1) * $unitary);
                else
                    $(this).find('ul').width( $round * $unitary);
                $m.find('ul').height($unitaryH * 2);
            }
            if (T.getDeviceSize() === 'small') {
                $round = Math.round($count);
                $(this).find('ul').width( $round * $unitary);
                $m.find('ul').height($unitaryH);
            }
            /*if($.browser.msie && parseFloat($.browser.version) <= 8){
                $(document).resize(function() {
                    if( $(document).width() > 623 ){

                    }
                    if( $(document).width() > 623 ){

                    }
                    if( $(document).width() < 624 ){      
                    }
                    $('.nav_header_01_subsect.active.selected').trigger('click');
                });
            }
            else{*/

            //}
            $parent.parent().find('a.wdg_matchesresult_left').click(function(e) {
                e.preventDefault();
                $parent.animate({
                    'scrollLeft': $parent.scrollLeft() - $unitary
                }, animationDelay);      
            });

            $parent.parent().find('a.wdg_matchesresult_right').click(function(e) {
                e.preventDefault();
                $parent.animate({
                    'scrollLeft': $parent.scrollLeft() + $unitary
                }, animationDelay);
            });
        /*if($.browser.msie && parseFloat($.browser.version) <= 8){
                $(document).resize(function() {
                    $unitary = $w.outerWidth(true);
                    if( $(document).width()  > 960  ){
                        $round = Math.round($count/3);
                        if( ($count / 3) > $round )
                            $m.find('ul').width( ($round + 1) * $unitary);
                        else
                            $m.find('ul').width( $round * $unitary);
                    }
                    if( $(document).width() > 623 && $(document).width() <= 960){
                        $round = Math.round($count/2);
                        if( ($count / 2) > $round )
                            $m.find('ul').width( ($round + 1) * $unitary);
                        else
                            $m.find('ul').width( $round * $unitary);
                    }
                    if( $(document).width() < 624 ){
                        $round = Math.round($count);
                        $m.find('ul').width( $round * $unitary);
                    }
                });
            }
            else{*/

        //}
        });
    }(Televisa, jQuery));
});
