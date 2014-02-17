$(document).ready(function() {
    (function(T, $) {
        var onDocumentReady = function() {
            var $parent = $('div.art_latestnews_01');
            var $clickListA = $parent.find('.art_latestnews_01_lasth');
            var $clickListB = $parent.find('.art_latestnews_01_morev');
            var $overLink = $parent.find('.art_latestnews_01_list1 li');
            $clickListA.bind('click', function(evt) {
                evt.preventDefault();
                $('.art_latestnews_01_morev').removeClass("selected");
                $(this).addClass("selected");
                $('.art_latestnews_01_list1').show();
                $('.art_latestnews_01_list2').hide();
            });
            $clickListB.bind('click', function(evt) {
                evt.preventDefault();
                $(this).addClass("selected");
                $('.art_latestnews_01_lasth').removeClass("selected");
                $('.art_latestnews_01_list1').hide();
                $('.art_latestnews_01_list2').show();
            });
            if (T.getDeviceSize() == 'large') {
                $overLink.bind('mouseover', function(evt) {
                    evt.preventDefault();
                    $(this).find('.art_latestnews_01_over').css("display","block");
                });
                $overLink.bind('mouseout', function(evt) {
                    evt.preventDefault();
                    $(this).find('.art_latestnews_01_over').css("display","none");
                });
            }
            $(window).resize(function() {
                if( $(window).width() > 960 ){
                    $overLink.bind('mouseover', function(evt) {
                        evt.preventDefault();
                        $(this).find('.art_latestnews_01_over').css("display","block");
                    });
                    $overLink.bind('mouseout', function(evt) {
                        evt.preventDefault();
                        $(this).find('.art_latestnews_01_over').css("display","none");
                    });
                }
                if( $(window).width() <= 960 ){
                    $overLink.bind('mouseover', function(evt) {
                        evt.preventDefault();
                        $(this).find('.art_latestnews_01_over').css("display","none");
                    });
                }
            });
        };
        $(onDocumentReady);
    }(Televisa, jQuery));

});