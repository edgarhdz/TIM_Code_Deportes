$(document).ready(function() {

    /*
     $('.str_expertcmt_02 .more').on('click',function(){
     $(this).siblings('.expert_container_block').show();
     $(this).css('display','none');
     $('.str_expertcmt_02 .see_less').show();
     })

     $('.str_expertcmt_02 .see_less').on('click', function(){
     $('.str_expertcmt_02 .expert_container_block').hide();
     $('.str_expertcmt_02 .see_less').hide();
     $('.str_expertcmt_02 .more').show();
     $(this).hide();
     });*/
    /*Monitoreo scroll*/
    $('.str_expertcmt_02 .scroll').scroll(function() {
        var $str2_altura = $('.str_expertcmt_02 .alto').height();
        var $str2_position = $(this).scrollTop();
        var $str2_med = $str2_position + $(this).height();
        if($str2_position == 0){

        }else{

        }
        if($str2_med == $str2_altura){
            $('.str_expertcmt_02 .degradado').css('visibility','hidden');
        }else{
            $('.str_expertcmt_02 .degradado').css('visibility','visible');
        }
    });

    if($(window).width()>623){
        $('.str_expertcmt_02 .expert_container_right').each(function(index){
            if(index==2){
                //$(this).css("cssText", 'background: url("../../img/dotted-horizontal_none.png") repeat-x bottom !important');
            }
        });
    }

    $(window).resize(function() {
        if($(window).width()>623){
            $('.str_expertcmt_02 .expert_container_right').each(function(index){
                if(index==2){
                    //$(this).css("cssText", 'background: url("../../img/dotted-horizontal_none.png") repeat-x bottom !important');
                }
            });
        }
    });
});