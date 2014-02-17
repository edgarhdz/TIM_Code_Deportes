$(document).ready(function () {
    var $firstItem = $('.wdg_scroll_dropdown').find('.wdg_scroll_elements li:first-child');
    var $first = $firstItem.find('p').attr('path');
    if ($first==undefined) {
        $("#referenceStadium").html( $("#info").html() );
    } else {
        setContent($first);
    }

    var $dropdownItems = $('.wdg_scroll_dropdown').find('.wdg_scroll_elements li');

    $dropdownItems.bind('click', function (evt) {
        evt.preventDefault()
        var $html = $(this).find('p').html();
        var $attr = $(this).find('p').attr('path');
        $(this).closest('.wdg_scroll_list').find('.wdg_scroll_value').html($html);
        setContent($attr);
    });

    function setContent(attr) {
        $("#referenceStadium").load(attr + '.html .containerOne', function () {
            var latitude = $('.wdg_gmap_01 #wdg_gmap_01_properties').attr('data-latitude');
            var longitude = $('.wdg_gmap_01 #wdg_gmap_01_properties').attr('data-longitude');
            var maptype = $('.wdg_gmap_01 #wdg_gmap_01_properties').attr('data-map');
            var zoom = $('.wdg_gmap_01 #wdg_gmap_01_properties').attr('data-zoom');
            var mapgoogle = document.getElementById('mapgoogle');
            $('.wdg_gmap_01_properties').html('<iframe class="iframe_mapgoogle" id="mapgoogle" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" width="100%"  src="https://maps.google.com.mx/maps/ms?msa=0&amp;msid=216094874971383578550.0004e1162c42b0cb9d398&amp;ie=UTF8&amp;ll=' + latitude + ',' + longitude + '&amp;spn=0,0&amp;t=' + maptype + '&amp;iwloc=lyrftr:m,3647405097792540577,' + latitude + ',' + longitude + '&amp;output=embed&amp;z=' + parseInt(zoom) + '"></iframe>');
        });
    }
});