function resized() {
    if( $(window).width() <  624 ){
        var $height = $('.mix_carru_02 .resize img');
        $height.load(function() {
                var $imageHeight = $(this).width(); 
                var $totalHeight = $imageHeight * 0.75;
                $height.height($totalHeight);
                $height.parents('.resize').height($totalHeight);
        });
    }
    $(window).resize(function() {
        if( $(window).width() <  624 ){
            var $imageHeight = $height.width(); 
                var $totalHeight = $imageHeight * 0.75;
                $height.height($totalHeight+10);
                $height.parents('.resize').height($totalHeight);
        }
    });

	if ($(window).width() < 624) {
		var width = $(window).width();
		$(".mix_carru_02").attr('data-width',width);
		$(".mix_carru_02").each(function(){
			$(this).find('div.galleryImages_02 li, div.galleryImages_02 li a, div.galleryImages_02 li a img').css('width',width+'px');
			$(this).find('div.galleryImages_02 li a img').attr({width:width,height:'auto'});
		});
	}
}
$(window).resize(function(){
	resized();
	$(".mix_carru_02").each(function(){
		$(this).find('ul.galleryThumbs li a:eq(0)').click();
	});
});

$(document).ready(function() {
    resized();
	// call with .each() to address multiple occurrences of the component
	$(".mix_carru_02").each(function(){
        var $parent = $(this);
        
        // the autoplay
        var num = 0,
        total = $parent.find('ul.galleryThumbs li').length;
        if (total > 1) {
	        window.setInterval(function() {
	            // only if there is a class autoplay
	            if ($parent.hasClass('autoplay')) {
	                // increase by num 1, reset to 0 at 3 (or whatever amount of images there are)
	                num = (num + 1) % total;
	                $parent.find('ul.galleryThumbs li a:eq('+num+')').click();
	            }
	        }, 5000); // repeat forever, polling every 3 seconds
        }
        
        // pause, resume autoplay on hover
        $parent.hover(
            function(){
                $parent.removeClass('autoplay');
            },
            function(){
                $parent.addClass('autoplay');
            }
        );
        
        $parent.find('div.galleryImages_02 li:eq(0) img').load(function(){
            var height = $(this).height();
             // click event
            $parent.find('ul.galleryThumbs li a').click(function(i){
                // current state
            	var width = ($(window).width() < 624) ? $parent.data('width') : 624;
                $(this).parents('ul').find('li').removeClass('active');
                $(this).parent().addClass('active');
                
                var position = $(this).data('position')
                image = $(this).data('image'),
                topic = $(this).data('topic'),
                urltopic = $(this).data('urltopic'),
                title = $(this).data('title'),
                urltitle = $(this).data('urltitle'),
                type = $(this).data('type');
                
                // show correct image
                var pixels = (position - 1) * width;
                $parent.attr('data-current',position).find('div.galleryImages_02 ul:eq(0)').css('marginLeft','-'+pixels+'px');

                // update caption
                $parent.find('div.galleryCaption_02 h2.topic a').text(topic).attr({title:topic,href:urltopic});
                $parent.find('div.galleryCaption_02 h4.caption a').text(title).attr({title:title,href:urltitle});
                $corner = $parent.find('span.corner');
                if (type === "") {
                	$corner.hide();
                } else {
                	$corner.removeClass('photo').removeClass('video');
                	$corner.addClass(type).show();
                }
                
                // return false
                return false;
            });
            // click event
            $parent.find('ul.galleryThumbs li a').on('touchstart',function(i){
                // current state
            	var width = ($(window).width() < 624) ? $parent.data('width') : 624;
                $(this).parents('ul').find('li').removeClass('active');
                $(this).parent().addClass('active');
                
                var position = $(this).data('position')
                image = $(this).data('image'),
                topic = $(this).data('topic'),
                urltopic = $(this).data('urltopic'),
                title = $(this).data('title'),
                urltitle = $(this).data('urltitle'),
                type = $(this).data('type');
                
                // show correct image
                var pixels = (position - 1) * width;
                $parent.attr('data-current',position).find('div.galleryImages_02 ul:eq(0)').css('marginLeft','-'+pixels+'px');

                // update caption
                $parent.find('div.galleryCaption_02 h2.topic a').text(topic).attr({title:topic,href:urltopic});
                $parent.find('div.galleryCaption_02 h4.caption a').text(title).attr({title:title,href:urltitle});
                $corner = $parent.find('span.corner');
                if (type === "") {
                	$corner.hide();
                } else {
                	$corner.removeClass('photo').removeClass('video');
                	$corner.addClass(type).show();
                }
                
                // return false
                return false;
            });
			
			
			
        });
    });
});