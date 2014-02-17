// this code was fucking up image galleries in mobile by adding a width. (code that affects a component should be inside the component)
;$(document).ready(function() {

   ;(function ($, T) {

           var $m = $('.type1a div:last-child'),
                   animationDelay = 500;

           $m.each(function() {

                   var $items = $(this).find('ul li'),
                           padding = ($(this).parent().hasClass('type1c')) ? 41 : 0,
                           Pointer = {
                                   UP: (T.getIsTouchDevice()) ? 'touchend' : 'mouseup',
                                   DOWN: (T.getIsTouchDevice()) ? 'touchstart' : 'click'
                                   //DOWN: (T.getIsTouchDevice()) ? 'touchstart' : 'mousedown'  // Linea original
                           },
                           $parent = $(this);


                   /* adding new code block to get the width of the elements, 
                   along with left and right padding, border, and optionally margin,
                   in pixels. */
                       
                   var itemsLength = $items.length;
                   var ulWidth = (itemsLength  * 137) + ((itemsLength - 1) * 25) + 22;
                   
                   $(this).find('ul').width(ulWidth + padding);
                   
                   if (/Android|iPhone|iPod|iPad/.test(navigator.userAgent) && // If it's an iPad, iPod or iPhone
                           !(/OS [2-4]_\d(_\d)? like Mac OS X/i.test(navigator.userAgent))) { // And the version of the OS is bigger than 4.x...
                           $(this).addClass('iOS');
                   } else {
                           // Touch devices (non-iOS or iOS < 5)
                           var isWinPhone = /Windows Phone/.test(navigator.userAgent);

                           if (T.getIsTouchDevice() && !T.isBlackBerryCurve()) {

                                   // Hide the arrows if it's not carouseltype1b
                                   if (!$(this).parent().hasClass('type1b')) {
                                           $(this).parent().find('div:first-child ul').hide();
                                   }

                                   $(this).bind('touchstart', function(e) {
                                           var clientX = e.originalEvent.changedTouches[0].clientX;

                                           $(this).attr('startX', clientX);
                                           $(this).attr('down', true);
                                           $(this).attr('scrollLeft', $(this).scrollLeft());
                                   });

                                   $(this).bind('touchend', function(e) {
                                           if ($(this).attr('active') === 'true') {
                                                   e.preventDefault();
                                                   e.stopPropagation();
                                           }

                                           $(this).attr('active', false);
                                           $(this).attr('down', false);
                                   });

                                   $(this).bind('touchmove', function(e) {
                                           var clientX = e.originalEvent.changedTouches[0].clientX,
                                                   sx = $(this).attr('startX') >> 0,
                                                   ex = clientX;

                                           if (Math.abs(sx - ex) > 10) {
                                                   $(this).attr('active', true);
                                           }

                                           if ($(this).attr('active') === 'true') {
                                                   e.preventDefault();

                                                   $(this).scrollLeft(($(this).attr('scrollLeft') >> 0) + (sx - ex));
                                           }

                                           return false;
                                   });
                           } else {
                                   // Force the arrows to show up
                                   $(this).parent().addClass('showArrows');
                           }
                   }

                   // Support the click event on arrows
                   $parent.parent().find('a.left').click(function(e) {
                       e.preventDefault();
                           $parent.animate({
                                   'scrollLeft': $parent.scrollLeft() - $parent.width()
                           }, animationDelay);
                   });

                   $parent.parent().find('a.right').click(function(e) {
                       e.preventDefault();
                           $parent.animate({
                                   'scrollLeft': $parent.scrollLeft() + $parent.width()
                           }, animationDelay);
                   });
           });

   }(jQuery, Televisa)); 
  
});