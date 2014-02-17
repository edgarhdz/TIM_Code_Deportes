$(document).ready(function() {
    var onDocumentReady = function() {
        // TODO: refactor for a better approach
        var $parent = $('.wdg_otherpost_01');
        var $dropdownAnchor = $parent.find('.wdg_otherpost_01_dropdown');
        var $firstItem = $('.wdg_otherpost_01_dropdownlist li:first-child');
        var $dropdownItems = $parent.find('.wdg_otherpost_01_dropdownlist li');
        $('.wdg_otherpost_01_dropdowncontent p').html($firstItem.find('p').html());
        $dropdownAnchor.bind('click', function(evt) {
            evt.preventDefault();
            //var totalHeight= 0;
			var $listItems = $(this).find('.wdg_otherpost_01_dropdownlist');
            var visibilidad = $listItems.css('visibility'); 
            /*$listItems.find('li').each(function() {
                totalHeight += $(this).outerHeight(true);
            });*/
            if ( visibilidad == 'hidden' ) {
                $listItems.css({
                    visibility: 'visible',
                    height: '176px',
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
        
       
        $dropdownAnchor.bind('mouseleave', function(evt) {
            evt.preventDefault();
			var $listItems = $(this).find('.wdg_otherpost_01_dropdownlist');
			var visibilidad = $listItems.css('visibility');
            if ( visibilidad == 'visible' ) {
                $listItems.css({
                    visibility: 'hidden',
                    height: '0px',         
                });
            } 
        });
        
        $dropdownItems.bind('click', function(evt) {
            evt.preventDefault()
			var $html = $(this).find('p').html();
            $(this).closest('.wdg_otherpost_01_dropdown').find('.wdg_otherpost_01_dropdowncontent p').html($html);
        });
     	
           
         
    };
    $(onDocumentReady);
});