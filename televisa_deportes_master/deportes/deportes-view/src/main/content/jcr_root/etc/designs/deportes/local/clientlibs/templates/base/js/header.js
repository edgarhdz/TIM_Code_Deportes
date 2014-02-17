$(document).ready(function() {
    (function(T, $, Modernizr) {
        header = new Object();
        header.jsonurl =  "../json/nav_header_01_weather.php?w=";
        header.idWeather;
        
        var getRealHeight = function($what) {
            var prevStyle = $what.attr('style'),
            value = $what.css({
                position: 'absolute',
                top: '-100%',
                visibility: 'hidden',
                height: 'auto'
            }).height();
            if (prevStyle) {
                $what.attr('style', prevStyle);
            }
            else {
                $what.removeAttr('style');
            }
            return value;
        };

        var onDocumentReady = function() {
            // TODO: refactor for a better approach
            var $parent = $('header.nav_header_01'),
            $sectionHeader = $parent.children('div.nav_header_01_section'),
            $links = $parent.find('a'),
            $nav = $parent.children('nav').children('ul'),
            $search = $parent.children('.nav_header_01_search'),
            $subsections = $sectionHeader.children('.nav_header_01_subsections'),
            $sectionLink = $sectionHeader.find('h2 a'),
            $superDropdowns = $parent.children('.nav_header_01_toggleable').find('.nav_header_01_wrapper > div'),
            navHeight = getRealHeight($nav),
            subsectionsHeight = getRealHeight($subsections),
            searchHeight = getRealHeight($search);

            $links.filter('.nav_header_01_mobile').bind('click', function(evt) {
                evt.preventDefault();
                if ($parent.hasClass('show-menu')) {
                    $parent.removeClass('show-menu');
                    $nav.css('height', 0);
                } else {
                    $parent.addClass('show-menu');
                    $nav.css('height', navHeight);
                }
            });

            $links.filter('.nav_header_01_search').bind('click', function(evt) {
                evt.preventDefault();
                if ($parent.hasClass('show-search')) {
                    $parent.removeClass('show-search');
                    $search.css('height', 0);
                } else {
                    $parent.addClass('show-search');
                    $search.css('height', searchHeight);
                }
            });
            //Event handlers for mobile menu navigation links
            $nav.on('click', 'a', function(evt) {
                var $anchor = $(this),
                section = $anchor.parent().attr('class');

                if (T.getDeviceSize() !== 'small') {
                    evt.preventDefault();

                    $superDropdowns.slideUp();
                    if ($anchor.hasClass('active')) {
                        $anchor.removeClass('active');
                    } else {
                        $nav.find('a.active').removeClass('active');
                        $anchor.addClass('active');
                        $superDropdowns.filter('.' + section).slideDown();
                    }
                }
            // TODO: should I hide header '.nav_header_01_section.' + section ? e.g.: .nav_header_01_section.news
            });

            $sectionLink.bind('click', function(evt) {
                evt.preventDefault();
                if (T.getDeviceSize() === 'small') {
                    if ($sectionLink.hasClass('active') ) {
                        $sectionLink.removeClass('active');
                        if( $(".nav_header_01_index").hasClass("selected") == false){
                            $('.nav_header_01_index').addClass("selected");
                            $subsections.css('height', 41);
                        }
                    }
                    else{
                        $sectionLink.addClass('active');
                        $('.nav_header_01_index').removeClass("selected");
                        $subsections.css('height', 158);
                    }
                    $('.nav_header_01_subsect').trigger('click');

                }
            });
        	$(window).resize(function() {
                if( $(window).width() > 623 ){
                    $subsections.css('height', 32);
                    $('.nav_header_01_index').addClass("selected");
                }
                if( $(window).width() < 624 ){
                    $subsections.css('height', 41);          
                }
                $('.nav_header_01_subsect.active.selected').trigger('click');
            });
            
            var $dropdownLinks = $parent.find('.nav_header_01_weather .nav_header_01_dropdown > a');//
            $dropdownLinks.bind('click', function(evt) {
                var $anchor = $(this),
                $ul = $anchor.next();
                evt.preventDefault();

                if ($ul.is(':visible')) {
                    $anchor.removeClass('active');
                    $ul.removeAttr('style');
                } else {
                    $dropdownLinks.filter('.active').each(function() {
                        $(this).removeClass('active')
                        .next().removeAttr('style');
                    });
                    $anchor.addClass('active');
                    $ul.show();
                }
                if($('.nav_header_01_weatherselected').hasClass("selected") === false){
                    $('.nav_header_01_weatherselected').addClass("selected")
                }
                else{
                    $('.nav_header_01_weatherselected').removeClass("selected")
                }
            });
            
            var $dropdownSocial = $parent.find('.nav_header_01_social .nav_header_01_dropdown > a');//
            $dropdownSocial.bind('click', function(evt) {
                var $anchor = $(this),
                $ul = $anchor.next();
                evt.preventDefault();

                if ($ul.is(':visible')) {
                    $anchor.removeClass('active');
                    $ul.removeAttr('style');
                } else {
                    $dropdownSocial.filter('.active').each(function() {
                        $(this).removeClass('active')
                        .next().removeAttr('style');
                    });
                    $anchor.addClass('active');
                    $ul.show();
                }
            });


            
            var $dropdownAnchor = $parent.find('.nav_header_01_cities li');
            var $firstItem = $('.nav_header_01_cities li:first-child');
            var $weatherActual = $parent.find('.nav_header_01_stat');
            var $weatherRange = $parent.find('.nav_header_01_forecast');
            $('.nav_header_01_weatherselected p').html($firstItem.find('a').html());
            //$firstItem.addClass("selected");
            $dropdownAnchor.bind('click', function(evt) {
                evt.preventDefault();
                /*$('.nav_header_01_cities li').removeClass("selected");
                $(this).addClass("selected");*/
                $('.nav_header_01_weatherselected.selected p').html($(this).find('a').html());
                header.idWeather = $(this).attr('weatherid');
                fillList(header.idWeather);
                $dropdownLinks.trigger('click');
                if($('.nav_header_01_weatherselected').hasClass("selected") )
                    $('.nav_header_01_weatherselected').removeClass("selected");
            });
            
            function fillList(idWeather){
                var jsonRequest  = header.jsonurl + idWeather
                $.getJSON( jsonRequest, function(data) {
                    $weatherActual.find('p').html(data.weather + "&deg;");
                    $weatherActual.find('span').attr('class','sprite_weatherwhite-header weatherheader_' + data.icon);
                    $weatherRange.find('.nav_header_01_forecastmin').html("Mín " + data.min + "&deg; C");
                    $weatherRange.find('.nav_header_01_forecastmax').html("Máx " + data.max + "&deg; C");
                });
            }

            
            var $dataTriggers = $parent.find('a[data-trigger]'),
            $dataTriggerTargets = $($dataTriggers.map(function(){
                return $(this).data('trigger');
            }).get().join());
            $dataTriggers.bind('click', function(evt) {
                var $anchor = $(this),
                $target = $dataTriggerTargets.filter($anchor.data('trigger'));
                evt.preventDefault();

                $dataTriggers.removeClass('active');
                if ($target.is(':visible')) {
                    $anchor.removeClass('active');
                    $target.removeAttr('style');
                } else {
                    $dataTriggerTargets.removeAttr('style');
                    $anchor.addClass('active');
                    $target.show();
                }
            });

            var $tabsContainer = $parent.find('.nav_header_01_dropdowncnt .tabs'),
            $tabsLinks = $tabsContainer.find('.filter a'),
            $tabs = $tabsContainer.find('.container').children();
            $tabsLinks.bind('click', function(evt) {
                var $anchor = $(this);
                evt.preventDefault();
                $tabsLinks.parent().removeClass('active');
                $anchor.parent().addClass('active');
                $tabs.removeClass('selected').filter('[data-tab="' + $anchor.data('tab') + '"]').addClass('selected');
            });

            var lastSize = null;
            lastSize = T.getDeviceSize();
            $(T).on('resize.Televisa', function() {
                if (lastSize !== T.getDeviceSize()) {
                    lastSize = T.getDeviceSize();
                    // reset measures
                    navHeight = getRealHeight($nav);
                    subsectionsHeight = getRealHeight($subsections);
                    searchHeight = getRealHeight($search);
                    // clear style
                    $subsections.add($search).add($nav).removeAttr('style');
                    $parent.removeClass('show-search show-menu');
                    $sectionLink.removeClass('active');
                }
            });
            var button = $(".nav_header_01_subsect");
            var selectedButton = $(".selected");
            button.bind("click", function(){
                toggleSelect(this);
            });
            function toggleSelect(pressedButton){
                if($(pressedButton).hasClass("selected") === false){
                    $(".nav_header_01_subsect").removeClass("selected");
                    $(pressedButton).addClass("selected");
                }else{
                    $(pressedButton).removeClass("selected");
 
                }
            }
            
            var buttonsub = $(".nav_header_01_subdropvid");
            buttonsub.bind("click", function(){
                toggleSelectSub(this);
            });
            function toggleSelectSub(pressedButtonSub){
                if($(pressedButtonSub).hasClass("selectedsub") === false){
                    $(pressedButtonSub).addClass("selectedsub");
                    if( $(".nav_header_01_dropdownvideo").hasClass("selected") == false)
                        $(".nav_header_01_dropdownvideo").addClass("selected");
                }
                else{
                    $(pressedButtonSub).removeClass("selectedsub");
                    $(".nav_header_01_dropdownvideo").removeClass("selected");
                }
            }
            var buttonvid = $(".nav_header_01_videohidetxt");
            buttonvid.bind("click", function(){
                toggleSelectVideo(this);
            });
            function toggleSelectVideo(){
                $(".nav_header_01_subdropvid").removeClass("selectedsub");
                $(".nav_header_01_dropdownvideo").removeClass("selected");
            }
        
        };
        
        $(onDocumentReady);
    }(Televisa, jQuery, Modernizr));

});