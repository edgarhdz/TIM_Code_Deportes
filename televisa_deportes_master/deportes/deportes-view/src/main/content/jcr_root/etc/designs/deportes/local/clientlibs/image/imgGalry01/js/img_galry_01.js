var IMG_WIDTH = 460;
var currentImg = 0;
var maxImages = 2;
var speed = 3000;
var imgs;
var total = 0;
function img_galry_01_init() {
    $("div.img_galry_01").each(function () {
        var rendition = new Array();
        rendition['thumb'] = '63.63';
        rendition['image'] = '624.468';
        rendition['album'] = 'album';
        $img_galry_01 = $(this);
        var carousel = $img_galry_01;
        var num = 0,
            total = $img_galry_01.find('div.carrusel1 ul li').length;
        setWidthCarr1ImgGalry1(total, $img_galry_01);
        setWidthCarr2();
        //if (total > 1) {
            window.setInterval(function () {
                if ($img_galry_01.hasClass('autoplay')) {
                    num = (num + 1) % total;
                    $img_galry_01.find('div.carrusel1 ul li a:eq(' + num + ')').click();
                    $img_galry_01.attr('data-current', num);
                    if (total > 5 && ((num % 6) == 0) && num != 0) {
                        $img_galry_01.find('.carrusel1 a.right').click();
                    } else if (num == 0) {
                        $img_galry_01.find('.carrusel1 .carruselcontainer').animate({
                            'scrollLeft': 0
                        }, 500);
                    }
                }
            }, 5000);
        //}
        $img_galry_01.find('div.image-container div.controls a.td_bg:eq(0)').click(function () {
            if ($(this).hasClass('play')) {
                $img_galry_01.addClass('autoplay');
                $(this).removeClass('play').addClass('pause').find('i').removeClass('tvsa-play').addClass('tvsa-pause');
            } else {
                $img_galry_01.removeClass('autoplay');
                $(this).removeClass('pause').addClass('play').find('i').removeClass('tvsa-pause').addClass('tvsa-play');
            }
            return false;
        });
        $img_galry_01.attr({
            'data-album': 0,
            'data-image': 0
        });
        //var json = jQuery.parseJSON($img_galry_01.find('span.data').text());
        var json = $('.img_galry_01 #carrusel2_ul .album:first').attr('galleryid');

        jQuery.getJSON(json, function(data) {
        	$('#img_galry_01_txts .txt1').html('<h3>' + data.title + '</h3> ' + '<p>' + data.description + '</p>');
            loadImageImgGalry1(data.photos[0].image,"",data.photos[0].description,$img_galry_01);
            var html = '';
            for (var i = 0; i < data.photos.length; i++) {
                var active = '';
                if (i === 0) {
                    active = ' active';
                }
                html += '<li>' +
                    '<a href="javascript: void(0);" class="image' + active + '" data-pos="' + i + '" title="' + data.photos[i].description + '">' +
                    '<img src="' + data.photos[i].thumb + '" alt="" class=""  title="' + data.photos[i].description + '" />' +
                    '<span class="border"></span>' +
                    '<span class="title">' + data.photos[i].description + '</span>' +
                    '</a>' +
                    '</li>';
            }

            $img_galry_01.find('div.carrusel1 ul.ulcarrusel li').remove();
            $img_galry_01.find('div.carrusel1 ul.ulcarrusel').append(html);
            $img_galry_01.find('.carrusel1 .carruselcontainer').animate({
                'scrollLeft': 0
            }, 500);
            $img_galry_01.find('div.carrusel1 .right .tvsa-double-caret-right').addClass('inactive');
            $img_galry_01.find('div.carrusel1 .right .tvsa-double-caret-right').removeClass('active');
            if ($img_galry_01.find('div.carrusel1 ul.ulcarrusel li').length > 6) {
                $img_galry_01.find('div.carrusel1 .right .tvsa-double-caret-right').addClass('active');
                $img_galry_01.find('div.carrusel1 .right .tvsa-double-caret-right').removeClass('inactive');
            }
            $img_galry_01.find('p.imageDescription').text(data.photos[0].description); // update desc to first image
            $img_galry_01.find('a.expand').attr({
                'href': data.url,
                'title': data.description
            });
            total = $img_galry_01.find('div.carrusel1 ul li').length;
            /*setWidthCarr1ImgGalry1(total, $img_galry_01);*/
            $img_galry_01.find('div.carrusel1 ul.ulcarrusel').width(648);
            num = 0;
        });


        $img_galry_01.find('span.loading').hide();
        if ($(window).width() < 624) {
            setTimeout(function () {
                $img_galry_01.find('.carrusel2 .ulcarrusel').css('width', '95%');
            }, 200);
            $('.img_galry_01 .txt1').removeClass('dotted-right');
        	$('.img_galry_01 .txt1').addClass('dotted-bottom');
        }
        $theUl = $img_galry_01.find('.carruselcontainer');
        $theUl.bind('swipeup', function () {
            $(this).animate({
                'scrollTop': $theUl.scrollTop() + 250
            }, 500);
        });
        $theUl.bind('swipedown', function () {
            $(this).animate({
                'scrollTop': $theUl.scrollTop() - 250
            }, 500);
        });
        if ($(window).width() > 623) {
        	$('.img_galry_01 .txt1').removeClass('dotted-bottom');
   			$('.img_galry_01 .txt1').addClass('dotted-right');
   			
            $img_galry_01.find('span.laoding').hide();
            $img_galry_01.find('ul.ulcarrusel').unbind();
            var swipeOptions = {
                triggerOnTouchEnd: true,
                swipeStatus: swipeStatusImgGalry1,
                allowPageScroll: "vertical",
                threshold: 75
            }

            $img_galry_01.find('ul.ulcarrusel').on('click touchstart', 'a', function (ev) {
                var actualCar = $(this);
                json = actualCar.attr('galleryid');
                //var pos = actualCar.attr('data-pos');
                var pos = $(this).data('pos');
                if (actualCar.hasClass('album')) {
                    jQuery.getJSON(json, function(data) {
                        $img_galry_01.find('a.album').removeClass('active');
                        actualCar.addClass('active');
                        $img_galry_01.find('span.loading').fadeIn(200).show();
                        /*var firstimage = data[pos].images[0].path,
                         desc = data[pos].images[0].description;*/
                        var firstimage = data.photos[0].image,
                            desc = data.photos[0].description;

                        $img_galry_01.find("img.mainimage:eq(0)").on('load', function () {

                            $img_galry_01.find('div.image-container div.overlay > p:eq(0)').text(desc);
                            $img_galry_01.attr('data-album', pos).find('span.loading').hide();
                            var html = '',
                                src, desc;
                            $img_galry_01.find('p.imageDescription').text(desc);
                            for (var i = 0; i < data.photos.length; i++) {
                                var active = '';
                                if (i === 0) {
                                    active = ' active';
                                }
                                html += '<li>' +
                                    '<a href="#" class="image' + active + '" data-pos="' + i + '" title="' + data.photos[i].description + '">' +
                                    '<img src="' + data.photos[i].thumb + '" alt="" class=""  title="' + data.photos[i].description + '" />' +
                                    '<span class="border"></span>' +
                                    '<span class="title">' + data.photos[i].description + '</span>' +
                                    '</a>' +
                                    '</li>';
                            }
                            $img_galry_01.find('div.carrusel1 ul.ulcarrusel li').remove();
                            $img_galry_01.find('div.carrusel1 ul.ulcarrusel').append(html);
                            $img_galry_01.find('.carrusel1 .carruselcontainer').animate({
                                'scrollLeft': 0
                            }, 500);
                            $img_galry_01.find('div.carrusel1 .right .tvsa-double-caret-right').addClass('inactive');
                            $img_galry_01.find('div.carrusel1 .right .tvsa-double-caret-right').removeClass('active');
                            if ($img_galry_01.find('div.carrusel1 ul.ulcarrusel li').length > 6) {
                                $img_galry_01.find('div.carrusel1 .right .tvsa-double-caret-right').addClass('active');
                                $img_galry_01.find('div.carrusel1 .right .tvsa-double-caret-right').removeClass('inactive');
                            }
                            $img_galry_01.find('p.imageDescription').text(data.photos[0].description); // update desc to first image
                            $img_galry_01.find('a.expand').attr({
                                'href': data.url,
                                'title': data.description
                            });
                            total = $img_galry_01.find('div.carrusel1 ul li').length;
                            /*setWidthCarr1ImgGalry1(total, $img_galry_01);*/
                            $img_galry_01.find('div.carrusel1 ul.ulcarrusel').width(648);
                            num = 0;
                        }).attr("src", firstimage);
                    });
                } else {
                    $(".img_galry_01").find('a.image').removeClass('active');
                    $(this).addClass('active');
                    var src = $(this).find('img:eq(0)').attr('src').replace(rendition['thumb'], rendition['image']),
                        href = $(".img_galry_01").find('a.expand').attr('href');
                    desc = $(this).find('span.title:eq(0)').text();
                    $img_galry_01.find('span.loading').fadeIn(200).show();
                    $img_galry_01.find("img.mainimage:eq(0)").on('load', function () {
                        $img_galry_01.find('div.image-container div.overlay .imageDescription').text(desc);
                        $img_galry_01.find('a.expand').attr({
                            'href': href,
                            'title': desc
                        });
                        $img_galry_01.attr('data-image', pos).find('span.loading').hide();
                    }).attr("src", '');
                    $img_galry_01.find("img.mainimage").attr("src", src);
                    if ($img_galry_01.hasClass('autoplay')) {
                        num = pos;
                    }
                }
                $img_galry_01.find('span.loading').hide();
                return false;
            } );
        } else {
            return true;
        }
    });
    if ($(window).width() > 948) {
        $(".img_galry_01 .image-container").mouseenter(function () {
            $(this).children('.td_bg').fadeIn();
        });
        $(".img_galry_01 .image-container").mouseleave(function () {
            $(this).children('.td_bg').fadeOut();
        });
    } else {
        $(".img_galry_01 .image-container").click(function () {
            if ($(this).children('.td_bg').is(':visible'))
                $(this).children('.td_bg').fadeOut();
            else
                $(this).children('.td_bg').fadeIn();
        });
    }
    $(".img_galry_01").find("a.left").click(function () {
        $(this).parent().siblings('.carruselcontainer').animate({
            'scrollLeft': $(this).scrollLeft() - 477
        }, 500);
    });
    $(".img_galry_01").find("a.right").click(function () {
        total = $img_galry_01.find('div.carrusel1 ul li').length;
        var total2 = $img_galry_01.find('div.carrusel2 ul li').length;
        var quien = $(this).parent().parent();
        if (
            (total > 6 && $(quien).hasClass("carrusel1")) ||
                (total2 > 3 && $(quien).hasClass("carrusel2"))
            ) {
            $(this).parent().siblings('.carruselcontainer').animate({
                'scrollLeft': $(this).scrollLeft() + 477
            }, 500);
        }
    });
    $(".img_galry_01").find("a.left").bind('touchstart', function (e) {
        event.preventDefault();
        $(this).parent().siblings('.carruselcontainer').animate({
            'scrollLeft': $(this).scrollLeft() - 477
        }, 500);
    });
    $(".img_galry_01").find("a.right").bind('touchstart', function (e) {
        event.preventDefault();
        total = $img_galry_01.find('div.carrusel1 ul li').length;
        if (total > 6) {
            $(this).parent().siblings('.carruselcontainer').animate({
                'scrollLeft': $(this).scrollLeft() + 477
            }, 500);
        }
    });
    $(".img_galry_01").find('.carrusel1 .carruselcontainer').scroll(function () {
        var $ig4_position = $(this).scrollLeft();
        var $id4_med = $ig4_position + $(this).width();
        var $id4_lt = $(this).children().width();
        if ($ig4_position == 0) {
            $(this).parent().find('.left .tvsa-double-caret-left').removeClass('active');
            $(this).parent().find('.left .tvsa-double-caret-left').addClass('inactive');
        } else {
            $(this).parent().find('.left .tvsa-double-caret-left').removeClass('inactive');
            $(this).parent().find('.left .tvsa-double-caret-left').addClass('active');
        }
        if ($id4_med == $id4_lt || $img_galry_01.find('div.carrusel1 ul.ulcarrusel li').length < 7) {
            $(this).parent().find('.right .tvsa-double-caret-right').addClass('inactive');
            $(this).parent().find('.right .tvsa-double-caret-right').removeClass('active');
        } else {
            $(this).parent().find('.right .tvsa-double-caret-right').addClass('active');
            $(this).parent().find('.right .tvsa-double-caret-right').removeClass('inactive');
        }
    });
    $(".img_galry_01").find('.carrusel2 .carruselcontainer').scroll(function () {
        var $ig4_position = $(this).scrollLeft();
        var $id4_med = $ig4_position + $(this).width();
        var $id4_lt = $(this).children().width();
        if ($ig4_position == 0) {
            $(this).parent().find('.left .tvsa-double-caret-left').removeClass('active');
            $(this).parent().find('.left .tvsa-double-caret-left').addClass('inactive');
        } else {
            $(this).parent().find('.left .tvsa-double-caret-left').removeClass('inactive');
            $(this).parent().find('.left .tvsa-double-caret-left').addClass('active');
        }
        if ($id4_med > 900) {
            $(this).parent().find('.right .tvsa-double-caret-right').addClass('inactive');
            $(this).parent().find('.right .tvsa-double-caret-right').removeClass('active');
        } else {
            $(this).parent().find('.right .tvsa-double-caret-right').addClass('active');
            $(this).parent().find('.right .tvsa-double-caret-right').removeClass('inactive');
        }
    });
}
function swipeStatusImgGalry1(event, phase, direction, distance, carousel) {
    if (currentImg === "undefined") currentImg = 0;
    if (phase == "move" && (direction == "left" || direction == "right")) {
        var duration = 0;
        if (direction == "left") {
            scrollImagesImgGalry1((IMG_WIDTH * currentImg) + distance, duration, carousel);
        } else if (direction == "right") {
            scrollImagesImgGalry1((IMG_WIDTH * currentImg) - distance, duration, carousel);
        }
    } else if (phase == "cancel") {
        scrollImagesImgGalry1(IMG_WIDTH * currentImg, speed, carousel);
    } else if (phase == "end") {
        if (direction == "right") {
            previousImageImgGalry1(carousel);
        } else if (direction == "left") {
            nextImageImgGalry1(carousel);
        }
    }
}
function previousImageImgGalry1(carousel) {
    if (currentImg === "undefined") currentImg = 0;
    currentImg = Math.max(currentImg - 1, 0);
    scrollImagesImgGalry1(IMG_WIDTH * currentImg, speed, carousel);
}
function nextImageImgGalry1(carousel) {
    if (currentImg === "undefined") currentImg = 0;
    currentImg = Math.min(currentImg + 1, maxImages - 1);
    scrollImagesImgGalry1(IMG_WIDTH * currentImg, speed, carousel);
}
$(function () {
    img_galry_01_init();
    if ($.browser.msie && parseInt($.browser.version, 10) <= 8) {
        $('.img_galry_01 .type1a_ .carruselcontainer').attr('overflow', 'hidden');
    }
});

$(window).resize(function () {
    if ($(window).width() < 624 && $('body:eq(0)').data('viewport') > 623) {
        img_galry_01_init();
    }
    if ($(window).width() >= 624) {
        img_galry_01_init();
    }
    $('body:eq(0)').attr('data-viewport', $(window).width());
});

function setWidthCarr1ImgGalry1(total, where) {
    var ancho_li = where.find('div.carrusel1 ul li').width() + 11;
    var anchoul1 = (total * ancho_li) - 9;
    if ($.browser.msie && parseInt($.browser.version, 10) <= 8) {
        anchoul1 += 10;
    }
    $img_galry_01.find('div.carrusel1 ul.ulcarrusel').width(anchoul1);
}

function setWidthCarr2() {
    var total2 = $img_galry_01.find('div.carrusel2 ul li').length;
    var ancho_li = $img_galry_01.find('div.carrusel2 ul li').width() + 22;
    var anchoul2 = (total2 * ancho_li);
    $img_galry_01.find('div.carrusel2 ul.ulcarrusel').width(anchoul2);
}

function loadImageImgGalry1(path, parent, desc, where) {
    //where.find('.image-container .mainimage').remove();
    var img = new Image();
    //$(img).load(function () {
    where.find("img.mainimage:eq(0)").on('load', function () {
        where.find('.img_galry_01 .image-container .loading').hide();
        where.find('.img_galry_01 .image-container').append($(this));
        where.find('div.image-container div.overlay .imageDescription').text(desc);
    }).attr({
            "src": path,
            "class": 'mainimage',
            "width": '624',
            "height": '468'
        }).error(function () {  });
}