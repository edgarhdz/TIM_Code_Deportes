vid_player_01_player = new Object();

vid_player_01_player.width = 625; // Delay entre cambio y cambio de imagenes en el autoplay
vid_player_01_player.height = 380;

$(document).ready(function() {

    $('.vid_player_01c #videoPlayerCancha').hide();

    // Si existe el componente de video
    if($('.vid_player_01c')){

        // Click  del btn play
        $('.vid_player_01c #videobtn').click(function (){

            $('.vid_player_01c #img_stage_01_IMG').hide();
            $('.vid_player_01c #videobtn').hide();
            $('.vid_player_01c .vid_player_01c_whtbkg').hide();
            $('.vid_player_01c .overlay').css('margin-top','25px');

            //$('.vid_player_01c #theaterContainer').show();

            $('.vid_player_01c #videoPlayerCancha').show();
        });


    }


    // Abrir Tu diriges
    $('.vid_player_01c .vid_player_01c_image .openbtn_td').click(function(){

        $(this).hide();
        $(this).parents('.vid_player_01c').find('.cameras .cameras_field').show();
        $(this).parents('.vid_player_01c').find('.cameras').show();
    });


    // Posicion de CAMARAS
    $('.vid_player_01c .cameras_field .camera').each(function(){
        var $x = $(this).attr('data-x');
        var $y = $(this).attr('data-y');
        $(this).css({'top' : $y, 'left': $x });
    });


    // Click en la camara
    $('.vid_player_01c .cameras_field .camera').click(function(){


        // Camaras
        $('.vid_player_01c .cameras_field .camera').removeClass('selected');
        $(this).addClass('selected');

        // Lista
        var $id = $(this).attr('id');
        $(this).parents('.vid_player_01c').find('.cameras_index ul li a').removeClass('selected');
        $(this).parents('.vid_player_01c').find('.cameras_index ul li #' + $id).addClass('selected');


        // si es tablet se ocultan
        if ($(window).width() < 948) {
            $(this).parents('.cameras_field').hide();
            $(this).parents('.cameras').hide();
            $(this).parents('.vid_player_01c').find('.openbtn_td').show();

        }
    });

    // HOVER CAMARA
    $( ".vid_player_01c .cameras .cameras_index ul li .indice" ).hover(
        function() {
            var $id = $(this).attr('id');
            $(this).parents('.vid_player_01c').find('.cameras_field .game_field #' + $id).addClass('selected');
        },
        function() {
            if( ! $(this).hasClass('selected') ){
                var $id = $(this).attr('id');
                $(this).parents('.vid_player_01c').find('.cameras_field .game_field #' + $id).removeClass('selected');
            }
        }
    );


    $( ".vid_player_01c .cameras .cameras_index ul li a" ).click(function(event){
        event.preventDefault();
    });

    // Click LISTA
    $( ".vid_player_01c .cameras .cameras_index ul li" ).click(function(){

        $(this).parents('.vid_player_01c').find('.cameras .cameras_index ul li .indice').removeClass('selected');
        $(this).find('.indice').addClass('selected');

        var $id = $(this).find('a').attr('id');
        $(this).parents('.vid_player_01c').find('.cameras_field .camera').removeClass('selected');
        $(this).parents('.vid_player_01c').find('.cameras_field #' + $id).addClass('selected');

    });

    $( window ).resize(function() {

        // Web
        if ($(window).width() > 948) {
            $(".vid_player_01c .cameras").show();
            $(".vid_player_01c .cameras .cameras_index").show();
            $(".vid_player_01c .cameras .cameras_field").show();
            $('.vid_player_01c').find('.openbtn_td').hide();
        }

        // Tablet
        else if ($(window).width() < 948 && $(window).width() > 624) {
            $( ".vid_player_01c .cameras").hide();
            $( ".vid_player_01c .cameras .cameras_index").hide();
            $( ".vid_player_01c .cameras .cameras_field").hide();
            $( '.vid_player_01c').find('.openbtn_td').show();
        }

        // Movil
        else if ($(window).width() < 624) {

            $(".vid_player_01c .cameras").show();
            $( ".vid_player_01c .cameras .cameras_index").show();
            $( ".vid_player_01c .cameras .cameras_field").hide();
            $('.vid_player_01c').find('.openbtn_td').hide();
        }
    });

});