/**
 * 
 *         @todo add hash to a pages' URL in order to load a specific image upon pageload (so that one can share links)
 *         the structure would need to be '%component% %album% %image%'
 *         %component% is the n-th occurence of the component within a page - must be a unique ID
 *         %album% the number of the album
 *         %image% the number of the image
 *         
 *         @todo autoplay
 *         @todo expand links
 *         @todo swipe implementation based on: @see http://www.awwwards.com/demo/touchSwipe-gallery-demo.html
 *         @todo device tests
 */

var IMG_WIDTH = 460;
var currentImg=0;
var maxImages=2;
var speed=750;
var imgs;
var total = 0;

/**
 * all gallery related behavior
 */
function vid_4vids_05_init() {
	// this function is fired upon resize, so we need to unbind each listener
	// in order not to have multiple listeners per dom element

	$("div.vid_4vids_05").each(function(){
		var rendition = new Array();
	    rendition['thumb'] = 'thumb';
	    rendition['image'] = 'image';
	    rendition['album'] = 'album';
	    $vid_4vids_05 = $(this);
	    var carousel = $vid_4vids_05;
	    
	    
        var num = 0,
        total = $vid_4vids_05.find('div.carrusel1 ul li').length;

		//////////////////////////////////////////////////////////////////////
		setWidthCarr14vid5(total);
		setWidthCarr24vid5();

		// the autoplay functionality
        if (total > 1) {
	        window.setInterval(function() {
	            // only if there is a class autoplay
	            if ($vid_4vids_05.hasClass('autoplay')) {
	                // increase by num 1, reset to 0 at 3 (or whatever amount of images there are)
	                num = (num + 1) % total;
					//console.log(num);
	                $vid_4vids_05.find('div.carrusel1 ul li a:eq('+num+')').click();
	                $vid_4vids_05.attr('data-current',num);
					
					// move 1st carrusel
					if(total > 5 && ((num%6)== 0) && num != 0){
						// Move right
						$vid_4vids_05.find('.carrusel1 a.right').click();
						////console.log('click');
					}
					else if(num== 0){
						// Reset to 0 position
						$vid_4vids_05.find('.carrusel1 .carruselcontainer').animate({
							'scrollLeft': 0
						}, 500);
					}
					//console.log(num);
	            }
	        }, 5000); // repeat forever, polling every n seconds
        }

        // pause, resume of the autoplay (this is the play/pause button)
        $vid_4vids_05.find('div.image-container div.controls a.td_bg:eq(0)').click(function(){
        	if ($(this).hasClass('play')) {
        		$vid_4vids_05.addClass('autoplay');
        		$(this).removeClass('play').addClass('pause').find('i').removeClass('tvsa-play').addClass('tvsa-pause');


        	} else {
        		$vid_4vids_05.removeClass('autoplay');
        		$(this).removeClass('pause').addClass('play').find('i').removeClass('tvsa-pause').addClass('tvsa-play');
				//num= 0;
        	}
        	return false;
        });

	    $vid_4vids_05.attr({'data-album':0,'data-image':0});
	    var data = jQuery.parseJSON($vid_4vids_05.find('span.data').text());
	    $vid_4vids_05.find('span.loading').hide();
	    
	    // if we are in mobile
	    if ($(window).width() < 624) {
			// Remove the calculated width of carrusel
			setTimeout(function(){
				//$vid_4vids_05.find('.carrusel2 .ulcarrusel').css('width','95%');	
			},200);
			
	    }

		/*Swipe*/
		$theUl = $vid_4vids_05.find('.carruselcontainer');
		$theUl.bind('swipeup',function(){
			$(this).animate({
					'scrollTop': $theUl.scrollTop() + 250
				}, 500);
		});
		$theUl.bind('swipedown',function(){
			$(this).animate({
					'scrollTop': $theUl.scrollTop() - 250
				}, 500);
		});

		if ($(window).width() > 623) {

	    	$vid_4vids_05.find('span.laoding').hide();
	    	$vid_4vids_05.find('ul.ulcarrusel').unbind();
	    	
	    	var swipeOptions={
	    		triggerOnTouchEnd : true,	
	    		swipeStatus : swipeStatus,
	    		allowPageScroll:"vertical",
	    		threshold:75
	    	}
	    	
			// Click IMG - Album or Photo
	        $vid_4vids_05.find('ul.ulcarrusel').on('click touchstart', 'a', function(ev){
	            var pos = $(this).data('pos');

	            if ($(this).hasClass('album')) {
	            	// album
	            	$(this).parents('ul').find('a').removeClass('active');
	                $(this).addClass('active');
	                $vid_4vids_05.find('span.loading').fadeIn(200).show();

	                var firstimage = data[pos].images[0].path,
	                desc = data[pos].images[0].description;

	                $vid_4vids_05.find("img.mainimage:eq(0)").on('load', function() {                    
	                	$vid_4vids_05.find('div.image-container div.overlay > p:eq(0)').text(desc);
	                    $vid_4vids_05.attr('data-album', pos).find('span.loading').hide();
	                    // replace carousel1 with the albums' images
	                    var html = '', src, desc;
	                    $vid_4vids_05.find('p.imageDescription').text(desc);
	                    $vid_4vids_05.find('p.imageDescription').text(data[pos].images[0].description); // update desc to first image
	                    
						// Recalculate first carrusel
						total = $vid_4vids_05.find('div.carrusel1 ul li').length;
						setWidthCarr1(total);
						num=0;
	                }).attr("src", firstimage);
	            } else {
	                // image
					$vid_4vids_05.find('a.image').removeClass('active');
					$(this).addClass('active');
	                var src = $(this).find('img:eq(0)').attr('src').replace(rendition['thumb'],rendition['image']),
	                href = $(this).attr('href');
	                desc = $(this).find('span.title:eq(0)').text();

	                // making sure an image is loaded before showing it
	                $vid_4vids_05.find('span.loading').fadeIn(200).show();
	                $vid_4vids_05.find("img.mainimage:eq(0)").on('load', function() {
	                    $vid_4vids_05.find('div.image-container div.overlay .imageDescription').text(desc);
	                    $vid_4vids_05.find('a.expand').attr({
	                    	'href': href,
	                    	'title' : desc
	                    }); // update expand link to detail URL
	                    $vid_4vids_05.attr('data-image', pos).find('span.loading').hide();
	                }).attr("src", '');
					//$vid_4vids_05.find("img.mainimage").attr("src", src);
	                loadImage4vid5(src, 'img.mainimage', desc);
					if ($vid_4vids_05.hasClass('autoplay')) {
						num = pos;
					}
	            }
	            $vid_4vids_05.find('span.loading').hide();
	            return false;
	        });
		} else {
			return true;
		}
	});

	if ($(window).width() > 948) {
		// Mouse actions - Display/Hide Social Btns
		$(".vid_4vids_05 .image-container").mouseenter( function(){
			
			$(this).children('.td_bg').fadeIn();
		});
		
		$(".vid_4vids_05 .image-container").mouseleave( function(){
			
			$(this).children('.td_bg').fadeOut();
		});
	} else{
		// Mouse actions - Display/Hide Social Btns
		$(".vid_4vids_05 .image-container").click( function(){
			
			if( $(this).children('.td_bg').is(':visible') )
				$(this).children('.td_bg').fadeOut();
			else
				$(this).children('.td_bg').fadeIn();
		});
		
	}

	
	// ARROW CLICKS
	$(".vid_4vids_05").find("a.left").click(function(){
		$(this).parent().siblings('.carruselcontainer').animate({
				'scrollLeft': $(this).scrollLeft() - 477
			}, 500);
	});
	$(".vid_4vids_05").find("a.right").click(function(){
		
		total = $vid_4vids_05.find('div.carrusel1 ul li').length;
		var total2 = $vid_4vids_05.find('div.carrusel2 ul li').length;
		var quien = $(this).parent().parent();
		// Si es el carrusel 2
		if(
			(total > 6 && $(quien).hasClass( "carrusel1" ) ) 
			||
			(total2 > 3 && $(quien).hasClass( "carrusel2" ) ) 
		){
			$(this).parent().siblings('.carruselcontainer').animate({
				'scrollLeft': $(this).scrollLeft() + 477
			}, 500);
		}
		
	});
	// left-right arrows touch
	 $(".vid_4vids_05").find("a.left").bind('touchstart',function(e) {
		 
		 event.preventDefault();
		$(this).parent().siblings('.carruselcontainer').animate({
			'scrollLeft': $(this).scrollLeft() - 477
		}, 500);
	});
	$(".vid_4vids_05").find("a.right").bind('touchstart',function(e) {
		event.preventDefault();
		total = $vid_4vids_05.find('div.carrusel1 ul li').length;
		if(total > 6 ){
			$(this).parent().siblings('.carruselcontainer').animate({
				'scrollLeft': $(this).scrollLeft() + 477
			}, 500);
		}
	});
	
	
	$(".vid_4vids_05").find('.carrusel1 .carruselcontainer').scroll(function() {
		
		var $ig4_position = $(this).scrollLeft();
		var $id4_med = $ig4_position + $(this).width();
		var $id4_lt = $(this).children().width(); 
		
		if($ig4_position == 0){
			$(this).parent().find('.left .tvsa-double-caret-left').removeClass('active');
			$(this).parent().find('.left .tvsa-double-caret-left').addClass('inactive');
		}
		else{
			$(this).parent().find('.left .tvsa-double-caret-left').removeClass('inactive');
			$(this).parent().find('.left .tvsa-double-caret-left').addClass('active');
		}
		
		
		
		if($id4_med == $id4_lt || $vid_4vids_05.find('div.carrusel1 ul.ulcarrusel li').length < 7){
			$(this).parent().find('.right .tvsa-double-caret-right').addClass('inactive');
			$(this).parent().find('.right .tvsa-double-caret-right').removeClass('active');
		}
		else{
			$(this).parent().find('.right .tvsa-double-caret-right').addClass('active');
			$(this).parent().find('.right .tvsa-double-caret-right').removeClass('inactive');
		}
		
		
	});
	
	$(".vid_4vids_05").find('.carrusel2 .carruselcontainer').scroll(function() {
		
		var $ig4_position = $(this).scrollLeft();
		var $id4_med = $ig4_position + $(this).width();
		var $id4_lt = $(this).children().width(); 
		
		if($ig4_position == 0){
			$(this).parent().find('.left .tvsa-double-caret-left').removeClass('active');
			$(this).parent().find('.left .tvsa-double-caret-left').addClass('inactive');
		}
		else{
			$(this).parent().find('.left .tvsa-double-caret-left').removeClass('inactive');
			$(this).parent().find('.left .tvsa-double-caret-left').addClass('active');
		}
		
		//if($id4_med == $id4_lt){
		if($id4_med > 900){
			$(this).parent().find('.right .tvsa-double-caret-right').addClass('inactive');
			$(this).parent().find('.right .tvsa-double-caret-right').removeClass('active');
		}
		else{
			$(this).parent().find('.right .tvsa-double-caret-right').addClass('active');
			$(this).parent().find('.right .tvsa-double-caret-right').removeClass('inactive');
		}
		////console.log($id4_med);
	});
	
	
}

/**
* Catch each phase of the swipe.
* move : we drag the div.
* cancel : we animate back to where we were
* end : we animate to the next image
*/			
function swipeStatus(event, phase, direction, distance, carousel) {
	if (currentImg === "undefined") currentImg = 0;
	// If we are moving before swipe, and we are going Lor R in X mode, or U or D in Y mode then drag.
	if( phase=="move" && (direction=="left" || direction=="right") ) {
		var duration=0;
		if (direction == "left") {
			scrollImages((IMG_WIDTH * currentImg) + distance, duration, carousel);
		} else if (direction == "right") {
			scrollImages((IMG_WIDTH * currentImg) - distance, duration, carousel);
		}
	} else if ( phase == "cancel") {
		scrollImages(IMG_WIDTH * currentImg, speed, carousel);
	} else if ( phase =="end" ) {
		if (direction == "right") {
			previousImage(carousel);
		} else if (direction == "left") {
			nextImage(carousel);
		}
	}
}

/**
 * go to next image
 * @param carousel
 */
function previousImage(carousel) {
	if (currentImg === "undefined") currentImg = 0;
	currentImg = Math.max(currentImg-1, 0);
	scrollImages( IMG_WIDTH * currentImg, speed, carousel);
}

/**
 * go to previous image
 * @param carousel
 */
function nextImage(carousel) {
	if (currentImg === "undefined") currentImg = 0;
	currentImg = Math.min(currentImg+1, maxImages-1);
	scrollImages( IMG_WIDTH * currentImg, speed, carousel);
}

//$(document).ready(function(){
$(function(){
    // making sure that this works even if there's two or more of this component in a page...
    vid_4vids_05_init();
	if ($.browser.msie && parseInt($.browser.version, 10) <= 8 ){
		$('.vid_4vids_05 .type1a_ .carruselcontainer').attr('overflow', 'hidden');
	}

	$("div.vid_4vids_05").each(function(){
		$vid_4vids_05 = $(this);
		/**
		 * replace image with video player
		 * 
		 */
		var data = jQuery.parseJSON($(".vid_4vids_05").find('span.data').text());
//		$(".vid_4vids_05").find("a.play").click(function(){
//			var video = 'http://tv.televisadeportes.esmas.com/embed/embed.php?id=243848&amp;w=624&amp;h=468';
//			var attr = $(this).attr('data-video');
//
//            if (typeof attr !== 'undefined' && attr !== false) {
//				video = $(this).attr('data-video');
//            }else{       
//                video = data[0].images[0].expandurl;
//            }
//
//            $(".vid_4vids_05").find("img.mainimage").hide();
//			$(".vid_4vids_05").find("#theaterContainer").html('<div id="leftBarLink" class="theaterSideSpacer"></div><div><div id="videoLink"></div><embed src="' + video + '" allowscriptaccess="always" width="624" height="468" /><div id="contenedor"></div></div><div id="rightBarLink" class="theaterSideSpacer"></div>').show();
//			$(".vid_4vids_05").find("div.overlay").hide();
//		});

		/**
		 * show different image and description and prepare video url
		 * 
		 */


		$(".vid_4vids_05").find("div.carruselcontainer ul.ulcarrusel li a").click(function(){
			var pos = Number($(this).attr('data-pos'));
            console.dir(data[pos]);
			$(".vid_4vids_05").find('a.album').removeClass('active');
			$(this).addClass('active');

			// update video Url, description and image
			$(".vid_4vids_05").find("a.play").attr('data-video', data[pos].images[0].expandurl);
			$(".vid_4vids_05").find("p.imageDescription").text(data[pos].images[0].description);
			//$(".vid_4vids_05").find("img.mainimage").attr('src', data[pos].images[0].path).show();
			//$(".vid_4vids_05").find("#theaterContainer").hide();
			$(".vid_4vids_05").find("div.overlay").show();
		}); 
	});
});

$(window).resize(function(){
	vid_4vids_05_init();
});

function setWidthCarr14vid5(total){
	// 1st carrusel width
	var ancho_li = $vid_4vids_05.find('div.carrusel1 ul li').width()+11;
	var anchoul1 = (total * ancho_li) - 9;
	if ($.browser.msie && parseInt($.browser.version, 10) <= 8){
		anchoul1 += 10;
	}
	$vid_4vids_05.find('div.carrusel1 ul.ulcarrusel').width(anchoul1);
	
	
}

function setWidthCarr24vid5(){
	// 2nd carrusel width
	var total2 = $vid_4vids_05.find('div.carrusel2 ul li').length;
	var ancho_li = $vid_4vids_05.find('div.carrusel2 ul li').width()+22;
	var anchoul2 = (total2 * ancho_li);
	$vid_4vids_05.find('div.carrusel2 ul.ulcarrusel').width(anchoul2);
}
 
function loadImage4vid5(path, parent, desc) {
		$vid_4vids_05.find('.image-container .mainimage').remove();
		var img = new Image();
		
		$(img).load(function(){
			
			$('.vid_4vids_05 .image-container .loading').hide();
		  	$('.vid_4vids_05 .image-container').append($(this));
			 $vid_4vids_05.find('div.image-container div.overlay .imageDescription').text(desc);
		}).attr({
		  src: path,
		  "class": 'mainimage',
		  "width" : '624',
		  "height" : '468' 
		}).error(function(){
		});
}
