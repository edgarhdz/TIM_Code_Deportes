$(document).ready(function() {
    var onDocumentReady = function() {
        var $element = $('.wdg_scroll_dropdown');
        $element.each(function(){
            var $dropdownAnchor = $(this).find('.wdg_scroll_list');
            var $firstItem = $(this).find('.wdg_scroll_elements li:first-child');
            var $dropdownItems = $(this).find('.wdg_scroll_elements li');
            $(this).find('.wdg_scroll_value').html($firstItem.find('p').html());
            $dropdownAnchor.bind('click', function(evt) {
                evt.preventDefault();
                var $listItems = $(this).find('.wdg_scroll_events');
                var visibilidad = $listItems.css('visibility'); 
                if ( visibilidad == 'hidden' ) {
                    $listItems.css({
                        visibility: 'visible',
                        height: '192px',
                        'overflow-y': 'scroll',
                        'overflow-x': 'hidden'         
                    });     
                } else {
                    $listItems.css({
                        visibility: 'hidden',
                        height: '0px'
                    });
                }
            });
            $dropdownItems.bind('click', function(evt) {
                evt.preventDefault()
                var $html = $(this).find('p').html();
                $(this).closest('.wdg_scroll_list').find('.wdg_scroll_value').html($html);
            });
        });
    };
    $(onDocumentReady);
});
