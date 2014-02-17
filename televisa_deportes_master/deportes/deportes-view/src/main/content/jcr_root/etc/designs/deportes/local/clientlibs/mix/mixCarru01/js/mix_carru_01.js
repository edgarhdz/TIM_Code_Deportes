$(document).ready(function() {
   if( $(window).width() <  624 ){
        var $height = $('.mix_carru_01 .resize img');
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
	
	/*Inicio gray*/
 if ($.browser.version == 10.0 && $.browser.msie) {
	 
	var paint_gray = function(salta){
	$('.mix_carru_01 .galleryThumbs li').each(function(index) {
		//Get id
		$(this).find('svg').remove('svg');
		name = $(this).attr("id");
		//alert("My id is: " + name + " debes brincarte: " + salta);
		if(name != salta){
			var $image = $(this).find('img').attr('src');
			var $svn = '<svg class="cube" xmlns="http://www.w3.org/2000/svg" id="svgroot" viewBox="0 0 62 61" width="62" height="61">';
			$svn += '<defs>';
			$svn += '<filter id="filtersPicture">';
			$svn += '<feComposite result="inputTo_38" in="SourceGraphic" in2="SourceGraphic" operator="arithmetic" k1="0" k2="1" k3="0" k4="0" />';
			$svn += '<feColorMatrix id="filter_38" type="saturate" values="0" data-filterid="38" />';
			$svn += '</filter>';
			$svn += '</defs>';
			$svn += '<image filter="url(&quot;#filtersPicture&quot;)" x="0" y="0" width="62" height="61" xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="'+$image+'" />';
			$svn += '</svg>';
			$svn += '<img src="'+$image+'" alt="Color Image"/>';
			$svn += '<span class="border"></span>';
			$(this).html($svn); 
		}
			
	});
	};
	paint_gray("one");
	
	/*$('.galleryThumbs li').each(function(index) {
		if(index!=0){
			var $image = $(this).find('img').attr('src');
			var $svn = '<svg xmlns="http://www.w3.org/2000/svg" id="svgroot" viewBox="0 0 62 61" width="62" height="61">';
			$svn += '<defs>';
			$svn += '<filter id="filtersPicture">';
			$svn += '<feComposite result="inputTo_38" in="SourceGraphic" in2="SourceGraphic" operator="arithmetic" k1="0" k2="1" k3="0" k4="0" />';
			$svn += '<feColorMatrix id="filter_38" type="saturate" values="0" data-filterid="38" />';
			$svn += '</filter>';
			$svn += '</defs>';
			$svn += '<image filter="url(&quot;#filtersPicture&quot;)" x="0" y="0" width="62" height="61" xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="'+$image+'" />';
			$svn += '</svg>';
			$(this).html($svn); 
		} 
	});*/
}

// call with .each() to address multiple occurrences of the component
	$(".mix_carru_01").each(function(){
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
	                $parent.find('ul.galleryThumbs li img:eq('+num+')').click();
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
	});




	












	
	
	
    $(".mix_carru_01 div.title a.right").click(function(e) {

        $(".mix_carru_01 ul.galleryThumbs li.active").next().trigger("click");
        e.stopPropagation();
    });

    $(".mix_carru_01 div.title a.left").click(function(e) {

        $(".mix_carru_01 ul.galleryThumbs li.active").prev().trigger("click");
        e.stopPropagation();
    });

    $(".mix_carru_01 ul.galleryThumbs li").click(function(e) {
		
		 if ($.browser.version == 10.0 && $.browser.msie) {
		 	name = $(this).attr("id");
			paint_gray(name);
		 }
		
        var image = $(this).attr("image");
        var seccion = $(this).attr("seccion");
        var urlSeccion = $(this).attr("urlSeccion");
        var title = $(this).attr("title");
        var urlTitle = $(this).attr("urlTitle");

        $(this).parent().find("li").removeClass();
        $(this).addClass("active");

        $(".mix_carru_01 div#bigImgA img").attr("href", urlTitle);
        $(".mix_carru_01 div#bigImgA img").attr("title", title);

        var anchor = document.createElement("a");
        anchor.setAttribute("href", urlTitle);
        var img = document.createElement("img");
        img.setAttribute("src", image);
        $(".mix_carru_01 div#bigImgA img").remove();
        anchor.appendChild(img);
        $(".mix_carru_01 div#bigImgA").append(anchor);
        /*var img = new Image();
         img.src = image;
         img.onload = function() {
         $(".mix_carru_01 div#bigImgA img").remove();
         anchor.appendChild(img);
         $(".mix_carru_01 div#bigImgA").append(anchor);
         }*/

        $(".mix_carru_01 div#bigImgA img").attr("src", image);

        $(".mix_carru_01 .itemSection a").attr("href", urlSeccion);
        $(".mix_carru_01 .itemSection a").attr("title", seccion);
        $(".mix_carru_01 .itemSection a").text(seccion);

        $(".mix_carru_01 .itemCaption a").attr("href", urlTitle);
        $(".mix_carru_01 .itemCaption a").attr("title", title);
        $(".mix_carru_01 .itemCaption a").text(title);

        e.stopPropagation();
    });

    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*IPAD touchstart*/
    $(".mix_carru_01 ul.galleryThumbs li").on('touchstart',function(e) {
        var image = $(this).attr("image");
        var seccion = $(this).attr("seccion");
        var urlSeccion = $(this).attr("urlSeccion");
        var title = $(this).attr("title");
        var urlTitle = $(this).attr("urlTitle");

        $(this).parent().find("li").removeClass();
        $(this).addClass("active");

        $(".mix_carru_01 div#bigImgA img").attr("href", urlTitle);
        $(".mix_carru_01 div#bigImgA img").attr("title", title);

        var anchor = document.createElement("a");
        anchor.setAttribute("href", "#");
        var img = document.createElement("img");
        img.setAttribute("src", image);
        $(".mix_carru_01 div#bigImgA img").remove();
        anchor.appendChild(img);
        $(".mix_carru_01 div#bigImgA").append(anchor);
        /*var img = new Image();
         img.src = image;
         img.onload = function() {
         $(".mix_carru_01 div#bigImgA img").remove();
         anchor.appendChild(img);
         $(".mix_carru_01 div#bigImgA").append(anchor);
         }*/

        $(".mix_carru_01 div#bigImgA img").attr("src", image);

        $(".mix_carru_01 .itemSection a").attr("href", urlSeccion);
        $(".mix_carru_01 .itemSection a").attr("title", seccion);
        $(".mix_carru_01 .itemSection a").text(seccion);

        $(".mix_carru_01 .itemCaption a").attr("href", urlTitle);
        $(".mix_carru_01 .itemCaption a").attr("title", title);
        $(".mix_carru_01 .itemCaption a").text(title);

        e.stopPropagation();
    });
    /*IPAD touchstart FINISHED*/ 
    
    
    var imgs = $(".mix_carru_01 .galleryImages ul li");
    if ($.browser.msie && parseFloat($.browser.version) <= 8) {
        width = $(document).width();
    }
    else {
        width = $(window).width();
		
    }
    if (width <= 624) {
        totalwidth = (width + 5) * imgs.length;
    }
    else {
		width =  625;
        totalwidth = (629 * imgs.length)+10;
    }
    $(".mix_carru_01 .galleryImages ul").css({"width": totalwidth});
    $(".mix_carru_01 .galleryImages ul li").css({"width": width});
    
    
    $(".mix_carru_01 .galleryImages").bind('touchstart', function(e) {
        var clientX = e.originalEvent.changedTouches[0].clientX;
        $(this).attr('startX', clientX);
        $(this).attr('down', true);
        $(this).attr('scrollLeft', $(this).scrollLeft());
    });

    $(".mix_carru_01 .galleryImages").bind('touchend', function(e) {
        if ($(this).attr('active') === 'true') {
            e.preventDefault();
            e.stopPropagation();
        }
        $(this).attr('active', false);
        $(this).attr('down', false);
    });

    $(".mix_carru_01 .galleryImages").bind('touchmove', function(e) {
        var left = $(".mix_carru_01 .galleryImages").attr("scrollleft");

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
        for(var i=0;  i<=imgs.length; i++){
            var limitI = width * i;
            var limitS = (width * i)+width;
            if(left>=limitI && left<limitS){
                var imgsthumbs = $(".mix_carru_01 .galleryThumbs li");
                for(var j = 0; j<imgsthumbs.length; j++){
                    if(i == j){
                        var seccion      = $(imgsthumbs[j]).attr("seccion");
                        var urlSeccion   = $(imgsthumbs[j]).attr("urlSeccion");
                        var title        = $(imgsthumbs[j]).attr("title");
                        var urlTitle     = $(imgsthumbs[j]).attr("urlTitle");
                        $(".mix_carru_01 .itemSection a").attr("href",urlSeccion);
                        $(".mix_carru_01 .itemSection a").attr("title",seccion);
                        $(".mix_carru_01 .itemSection a").text(seccion);
                        $(".mix_carru_01 .itemCaption a").attr("href",urlTitle);
                        $(".mix_carru_01 .itemCaption a").attr("title",title);
                        $(".mix_carru_01 .itemCaption a").text(title);
                    }
                }
                var bullets = $(".mix_carru_01 .bullets ul li");
                for (var h=0; h<bullets.length; h++){
                    if(i==h ){
                        $(bullets).removeClass("selected");
                        $(bullets[h]).addClass("selected");
                    }
                }
            }
        }

        return false;
    });
    
    $(window).resize(function() {
       width = $(window).width();
        totalwidth = ((width + 5) * imgs.length) - 5;
        if (width <= 624) {
            $(".galleryImages ul li").css({"width": width});
            $(".galleryImages ul").css({"width": totalwidth});

        } else {
            $(".galleryImages ul").css({"width": (628.33 * imgs.length)-5});
            $(".galleryImages ul li").css({"width": "auto"});
        }
        
    });

});

var isMobile = {
    Android: function() {
        return navigator.userAgent.match(/Android/i);
    },
    BlackBerry: function() {
        return navigator.userAgent.match(/BlackBerry/i);
    },
    iOS: function() {
        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
    },
    Opera: function() {
        return navigator.userAgent.match(/Opera Mini/i);
    },
    Windows: function() {
        return navigator.userAgent.match(/IEMobile/i);
    },
    any: function() {
        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
    }
	
	
	
	
	
	
	
	
	
	
};

