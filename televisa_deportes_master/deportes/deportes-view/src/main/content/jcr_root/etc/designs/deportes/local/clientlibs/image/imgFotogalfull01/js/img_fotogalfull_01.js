$(document).ready(function(){

    $("ul.carousel").children().each(function(index, value){
        if(index == 0){
			$(value).addClass('active');
        }
        $(value).attr("data-pos", index);

    });

});

var ImageGallery = (function(v) {
    return v;
}(ImageGallery || {}));


ImageGallery.FotoGalFull = {

    init : function(){
        console.log("fotogalfull init");
		// set vars
     var container = Math.round(Number($(".image-container").width() * 0.8)),
     autoplaySpeed = 3000,
     img_vis = Math.floor(container / (67+12)),
     IMG_WIDTH = img_vis * (67+12),
     currentImg = 0,
     img_count = Number($("div.carousel li.imageFotoGalleryFullItem").length +1),
     maxImages = Math.ceil(img_count / img_vis),
     speed=500;
     // set sidebar height on page load (after image is loaded)
     if ($(window).width() > 947) {
          var image = new Image();
          image.onload = function(){
               setSidebarHeight();
               setRelatedDimensions(); // always set dimensions of related content box on load
          };
          image.src = $("img.mainimage:eq(0)").attr('src');
     }

    for (var indexImg = 0; indexImg < img_count; indexImg++){
        $('.carousel .carousel-content li:eq('+indexImg+')').attr("data-pos",indexImg);
    }
     $("a.play").click(function(){
          if ($(this).find("i").hasClass('tvsa-play')) {
               // start autoplay
               $(this).find("i").removeClass('tvsa-play');
               $(this).find("i").addClass('tvsa-pause');
               $(".img_fotogalfull_01").addClass('autoplay');
               var num = -1;
               if (img_count > 2) {
                  window.setInterval(function () {
                      if ($(".img_fotogalfull_01").hasClass('autoplay')) {
                          num = (num + 1) % img_count;
                          if (num == img_count - 1) {
                               $(".img_fotogalfull_01").removeClass('autoplay');
                               $("a.play").find("i").addClass('tvsa-play');
                               $("a.play").find("i").removeClass('tvsa-pause');
                          }
                          $(".img_fotogalfull_01").find('ul.carousel li a:eq(' + num + ')').click();
                          $(".img_fotogalfull_01").attr('data-currpos', num);
                      }
                  }, autoplaySpeed);
              }
          } else {
               // stop autoplay
               $(this).find("i").addClass('tvsa-play');
               $(this).find("i").removeClass('tvsa-pause');
               $(".img_fotogalfull_01").removeClass('autoplay');
          }
          return false;
     });
     
     $("a.replay").click(function(){
          $("ul.carousel li:eq(0) a").click();
          return false;
     });
     
     // add swipe
     $('.img_fotogalfull_01').each(function(){
          var $this = $(this);
          var imgs = $(this).find("div.carousel ul:eq(0)");

          // active states of arrows
          if (img_count <= img_vis) {
               $(this).find("a.right").addClass('disabled');
          }
          
          //Init touch swipe - fails in IE7
          imgs.swipe({
               triggerOnTouchEnd : true,
               swipeStatus : swipeStatus,
               allowPageScroll:"vertical"
          });
          
          // left/right arrows
          $("a.right").click(function(){
               nextImage();
               if ($('ul.carousel').attr('data-swipepos') < maxImages - 1 ) {
                    $('ul.carousel').attr('data-swipepos', Number($("ul.carousel").attr('data-swipepos')) + 1);
               }
               $('a.left').removeClass('disabled');
               if ($('ul.carousel').attr('data-swipepos') == maxImages - 1) {
                    $(this).addClass('disabled');
               }
               return false;
          });
          
          $("a.left").click(function(){
               previousImage();
               if ($('ul.carousel').attr('data-swipepos') == 1) {
                    $(this).addClass('disabled');
               }
               if ($('ul.carousel').attr('data-swipepos') > 0) {
                    $('ul.carousel').attr('data-swipepos', Number($('ul.carousel').attr('data-swipepos')) - 1);
               }
               $('a.right').removeClass('disabled');
               return false;
          });
          
          // click on thumbnail
          $("ul.carousel a").click(function(){
               
               /**
                * @todo change social share count upon image change
                * @todo adjust hash parsing as soon as the functionality will be changed to serve static HTML pages for each image.
                */
               
               var twitter = 'https://twitter.com/intent/tweet/?url=<URL>&amp;text=<DESCRIPTION>',
               facebook = 'https://www.facebook.com/sharer/sharer.php?u=<URL>',
               pinterest = 'http://pinterest.com/pin/create/button/?url=<URL>&amp;media=<IMG>&amp;description=<DESCRIPTION>';
               
               $("ul.carousel li").removeClass('active');
               $li = $(this).parent();
               $li.addClass('active');
               var pos = Number($li.attr('data-pos')),
               title = $(this).attr('title'),
               desc = htmlspecialchars_decode($(this).attr('data-description')),
               base = document.location.pathname, // @todo make dynamic
               src = $(this).attr('data-src');
               
               if (pos + 1 === img_count) {
                    //$(".image-container").hide();
                    //$(".related-container").show();
               } else {
                    $(".image-container").show();
                    $(".related-container").hide();
                    // control the swipe
                    if (pos === 0) {
                         for (var i=0;i<= img_count / img_vis;i++) {
                              $("a.left").click();
                         }
                    }
                    if (pos >= img_vis) {
                         var diff = Math.floor(pos / img_vis) - (Number($("ul.carousel").attr('data-swipepos')) + 1);
                         if (Math.floor(pos / img_vis) > Number($("ul.carousel").attr('data-swipepos'))) {
                              // console.log('go right');
                              for (var i=0;i<=diff;i++) {
                                   $("a.right").click();
                              }
                         }
                         if (pos <= img_vis * (Number($("ul.carousel").attr('data-swipepos'))) - 1 || pos <= img_vis) {
                              // console.log('go left');
                              $("a.left").click();
                         }
                    }
                    
                    // load image
                    $("img.mainimage").on('load', function() {
                     setSidebarHeight();
                    }).attr("src",src).each(function() {
                     if(this.complete) $(this).load();
                        // update URL
                        var stateObj = { position: pos };
                        var slug = (str_replace(' ','-',title)+'').toLowerCase();
                        var pos = Number($li.data("pos"));
                        document.title = title; // updates title in browser tab
                        var stateObj = { position: pos };
                        
                        // with hash or not?
                        var stateObj = { position: pos };
                        try {
                             history.pushState(title, title, base + '#' + pos +"-"+slug); // adds a new entry to the browser history
                        } catch (errror) {
                             // do nothing
                        }
                        $('.image-container').attr('data-currpos',pos);
                        
                        // update description
                        $("p.imageDescription").html(desc);
                        $(".lnk_twitter").attr("href", buildLink(twitter, title, src));
                        $(".lnk_facebook").attr("href", buildLink(facebook, title, src));
                        $(".lnk_pinterest").attr("href", buildLink(pinterest, title, src));
                 });
               }
               // update desc, social links
               return false;
          });
          
          // use cursor to navigate through images
        $(document).keydown(function(e){
             var curpos = Number($('.image-container').attr('data-currpos'));
             var img_count = Number($(this).find("div.carousel li").length);
             if (!e.altKey && !e.ctrlKey && !e.metaKey) {
                // next image
                if (e.keyCode == 39) {
                     var goto = (curpos + 1);
                     if( goto >= img_count) {
                          goto = 0;
                     }
                    $("ul.carousel li a:eq("+ goto +")").click();
                    return false;
                }
                
                // previous
                if (e.keyCode == 37) { 
                     $("ul.carousel li a:eq("+ (curpos - 1) +")").click();
                    return false;
                }
            }
        });
          if(window.location.hash) {
               
               /**
                * @example http://televisa-deportes/img_fotogalfull_01/#11-the-image-position-followed-by-the-title
                * @var position
                * @var title
                */ 
               var first_dash = (window.location.hash + '').indexOf('-', 0);
               if (first_dash === -1) {
                    // error: no dash was found
               } else {
                    
                    // with hash or not?
                    
                    var newpos = window.location.hash.substring(1,first_dash); // starts after the hash symbol until first occurence of a dash
                    $("ul.carousel li").removeClass("active");
                    $("ul.carousel li:eq("+newpos+") a").click();
               }
          }
          
          /**
          * Catch each phase of the swipe.
          * move : we drag the div.
          * cancel : we animate back to where we were
          * end : we animate to the next image
          */
          function swipeStatus(event, phase, direction, distance, fingers) {
               //If we are moving before swipe, and we are going L or R, then manually drag the images
               if( phase=="move" && (direction=="left" || direction=="right") ) {
                    var duration=0;
                    if (direction == "left")
                         scrollImages((IMG_WIDTH * currentImg) + distance, duration);
                    else if (direction == "right")
                         scrollImages((IMG_WIDTH * currentImg) - distance, duration);
               }

               //Else, cancel means snap back to the begining
               else if ( phase == "cancel") {
                    scrollImages(IMG_WIDTH * currentImg, speed);
               }

               //Else end means the swipe was completed, so move to the next image
               else if ( phase =="end" ) {
                    if (direction == "right")
                         previousImage()
                    else if (direction == "left")
                         nextImage()
               }
          }

          function previousImage() {
               currentImg = Math.max(currentImg-1, 0);
               scrollImages( IMG_WIDTH * currentImg, speed);
          }

          function nextImage() {
               currentImg = Math.min(currentImg+1, maxImages-1);
               scrollImages( IMG_WIDTH * currentImg, speed);
          }

          function buildLink(url, description, image) {
               url = str_replace('<URL>', encodeURIComponent(document.location.href), url);
               url = str_replace('<DESCRIPTION>', encodeURIComponent(description), url);
               url = str_replace('<IMG>', encodeURIComponent(image), url);
               return url;
          }
          
          /**
           * Manually update the position of the imgs on drag
           */
          function scrollImages(distance, duration) {
               var dur = (duration/1000).toFixed(1),
               value = (distance<0 ? "" : "-") + Math.abs(distance).toString();
               
               // CSS Transforms for modern browsers:
               
               if ($("html:eq(0)").hasClass("csstransforms") && !$("html:eq(0)").hasClass("ie9")) {
                    imgs.css({
                         "-webkit-transform-style" : "flat",
                         "-moz-transform-style" : "flat",
                         "-ms-transform-style" : "flat",
                         "-o-transform-style" : "flat",
                         "transform-style" : "flat",
                         "-webkit-transition-duration" : dur + "s",
                         "-moz-transition-duration" : dur + "s",
                         "-ms-transition-duration" : dur + "s",
                         "-o-transition-duration" : dur + "s",
                         "transition-duration" : dur + "s",
                         "-webkit-transform" : "translate3d("+value +"px,0px,0px)",
                         "-moz-transform" : "translate3d("+value +"px,0px,0px)",
                         "-ms-transform" : "translate3d("+value +"px,0px,0px)",
                         "-o-transform" : "translate3d("+value +"px,0px,0px)",
                         "transform" : "translate3d("+value +"px,0px,0px)"
                    });
               } 
               
               // plain jQuery animation for old browsers
               else {
                    imgs.animate({ 'marginLeft': value}, dur, 'easeInOutExpo');
               }
          }
          
          function strpos (haystack, needle, offset) {
                 var i = (haystack + '').indexOf(needle, (offset || 0));
                 return i === -1 ? false : i;
               }
          
          /**
           * html special char decode
           */
          function htmlspecialchars_decode (string, quote_style) {
                 var optTemp = 0,
                   i = 0,
                   noquotes = false;
                 if (typeof quote_style === 'undefined') {
                   quote_style = 2;
                 }
                 string = string.toString().replace(/&lt;/g, '<').replace(/&gt;/g, '>');
                 var OPTS = {
                   'ENT_NOQUOTES': 0,
                   'ENT_HTML_QUOTE_SINGLE': 1,
                   'ENT_HTML_QUOTE_DOUBLE': 2,
                   'ENT_COMPAT': 2,
                   'ENT_QUOTES': 3,
                   'ENT_IGNORE': 4
                 };
                 if (quote_style === 0) {
                   noquotes = true;
                 }
                 if (typeof quote_style !== 'number') { // Allow for a single string or an array of string flags
                   quote_style = [].concat(quote_style);
                   for (i = 0; i < quote_style.length; i++) {
                     // Resolve string input to bitwise e.g. 'PATHINFO_EXTENSION' becomes 4
                     if (OPTS[quote_style[i]] === 0) {
                       noquotes = true;
                     } else if (OPTS[quote_style[i]]) {
                       optTemp = optTemp | OPTS[quote_style[i]];
                     }
                   }
                   quote_style = optTemp;
                 }
                 if (quote_style & OPTS.ENT_HTML_QUOTE_SINGLE) {
                   string = string.replace(/&#0*39;/g, "'"); // PHP doesn't currently escape if more than one 0, but it should
                   // string = string.replace(/&apos;|&#x0*27;/g, "'"); // This would also be useful here, but not a part of PHP
                 }
                 if (!noquotes) {
                   string = string.replace(/&quot;/g, '"');
                 }
                 // Put this in last place to avoid escape being double-decoded
                 string = string.replace(/&amp;/g, '&');
                 return string;
               }
          
          /**
           * str_replace
           */
          function str_replace (search, replace, subject, count) {
                 var i = 0,
                   j = 0,
                   temp = '',
                   repl = '',
                   sl = 0,
                   fl = 0,
                   f = [].concat(search),
                   r = [].concat(replace),
                   s = subject,
                   ra = Object.prototype.toString.call(r) === '[object Array]',
                   sa = Object.prototype.toString.call(s) === '[object Array]';
                 s = [].concat(s);
                 if (count     ) {
                   this.window[count] = 0;
                 }

                 for (i = 0, sl = s.length; i < sl; i++) {
                   if (s[i] === '') {
                     continue;
                   }
                   for (j = 0, fl = f.length; j < fl; j++) {
                     temp = s[i] + '';
                     repl = ra ? (r[j] !== undefined ? r[j] : '') : r[0];
                     s[i] = (temp).split(f[j]).join(repl);
                     if (count && s[i] !== temp) {
                       this.window[count] += (temp.length - s[i].length) / f[j].length;
                     }
                   }
                 }
                 return sa ? s : s[0];
               }
     });

    }

}
$(window).resize(function(){
     // set sidebar height on resize
     if ($(window).width() > 947) {
          setSidebarHeight();
          setRelatedDimensions();
     }
     
     if ($(window).width() < 624) {
          $(".image-container").show();
          $(".related-container").hide();
     }
});

/**
 * sets the sidebar height to the content height
 */
function setSidebarHeight() {
     $('#col2').height($('.img_fotogalfull_01 .image-container').height()+'px');
     setRelatedDimensions();
}
function setRelatedDimensions() {
     var h = $('.img_fotogalfull_01 .image-container').height(),
     w = $('.img_fotogalfull_01 #col1_content').width();
     $('.related-container').css({height:h+'px', width:w+'px'});
}