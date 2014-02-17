$(document).ready(function () {
    var $firstItem = $('.wdg_scroll_dropdown').find('.wdg_scroll_elements li:first-child');
    var $first = $firstItem.find('p').attr('path');
    if ($first==undefined) {
        $("#referenceEvent").html( $("#info").html() );
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
        $.get(attr + '.html',function(responseText){
            var $items=$(responseText).find(".containerItems");
            console.log($items.html());
            $items.find(".editModeSeparator").remove();
            $("#referenceEvent").html($items.html());
            updateConteainerSize();
        });
    }

    function updateConteainerSize(){
        var $m = $('.typecarruse .carrusel');
        $m.each(function() {
        var $items = $(this).find('ul li');
        var padding = ($(this).parent().hasClass('type1c')) ? 41 : 0;
        var $parent = $(this);

            /* adding new code block to get the width of the elements,
             along with left and right padding, border, and optionally margin,
             in pixels. */

            var itemsLength = $items.length;
            var ulWidth = (itemsLength  * 137) + ((itemsLength - 1) * 25) + 22;
            // Sacamos el ancho total del UL del carrusel
            var anchoTotalUL = $(".btn_carru_prog_02 .typecarruse .carrusel ul").width(); // 100%
            var elements = $(".btn_carru_prog_02 .typecarruse .carrusel li").size();
            var ulWidth = elements * 325;

            $(this).find('ul').width(ulWidth + padding);
        });
    }









});