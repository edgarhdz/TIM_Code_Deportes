$(document).ready(function() {

/*BEGIN: traffic*/
;(function($, T) {
    $(document).ready(function() {

        var Pointer = {
            DOWN: (T.getIsTouchDevice()) ? 'touchstart' : 'mousedown',
            UP: (T.getIsTouchDevice()) ? 'touchend' : 'mouseup'
        }

        $('.traffic #openMenu').bind(Pointer.DOWN, function() {
            if ($(this).parent().parent().find('div.dropdown').is(':visible')) {
                $(this).parent().parent().find('div.dropdown').slideUp();
            } else {
                $(this).parent().parent().find('div.dropdown').slideDown();
            }
        });
    });
}(jQuery, Televisa));
/*END: traffic*/


});