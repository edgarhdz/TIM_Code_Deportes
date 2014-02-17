;jQuery(function($){
    (function ($, T) {
        // CSS please
        if($(window).width() < 590){
            $('.wdg_teams_carru_02 .row').toggle();
            $('.wdg_teams_carru_02 .up').toggle();


            $('.wdg_teams_carru_02 .titulo_bar .ocultar a').hide();
            $('.wdg_teams_carru_02 .titulo_bar .mostrar a').show();
            $('.wdg_teams_carru_02').height('40px');
        }

        $('.wdg_teams_carru_02 .titulo_bar .ocultar a').click(function(event){
            event.preventDefault();
            $('.wdg_teams_carru_02 .row').toggle();
            $('.wdg_teams_carru_02 .up').toggle();


            $('.wdg_teams_carru_02 .titulo_bar .ocultar a').hide();
            $('.wdg_teams_carru_02 .titulo_bar .mostrar a').show();
            $('.wdg_teams_carru_02').height('40px');


        });

        $('.wdg_teams_carru_02 .titulo_bar .mostrar a').click(function(event){
            event.preventDefault();
            $('.wdg_teams_carru_02 .row').toggle();
            $('.wdg_teams_carru_02 .up').toggle();

            $('.wdg_teams_carru_02 .titulo_bar .ocultar a').show();
            $('.wdg_teams_carru_02 .titulo_bar .mostrar a').hide();
            $('.wdg_teams_carru_02').height('105px');
        });
    }(jQuery, Televisa));
});