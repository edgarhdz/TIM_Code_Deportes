//var PATH = "../global/js/";
//head.js(
//    PATH+"modernizr.js",
//    PATH+"Televisa.js",
//    PATH+"respond.js",
//    PATH+"selectivizr.js",
//    PATH+"transit.js",
//    PATH+"swipe.js",
//    PATH+"orientationfix.js",
//    PATH+"verticalhero.js",
//    PATH+"wdg_carousa.js",
//    PATH+"carouseltype1a.js",
//    PATH+"dropdown.js",
//    PATH+"header.js",
//    PATH+"footer.js",
//    PATH+"jquery.touchSwipe.js",
//	PATH+"swipe2.js"
//    );
// Quitamos la imagen que pone el script: http://comentarios.esmas.com/js/communities.js (redes sociales)
$(window).load(function() {
    $('img[src*="vistas/spacer.gif"]').hide();
});

$(".closepopalert").click(function() {
    $(this).parent().hide();
});