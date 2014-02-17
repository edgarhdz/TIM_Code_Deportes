var senalesLiveStream = new Array();

senalesLiveStream[1]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n01_1@138939/manifest.f4m";
senalesLiveStream[2]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n01_1@138939/manifest.f4m";
senalesLiveStream[3]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n01_1@138939/manifest.f4m";
senalesLiveStream[4]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n01_1@138939/manifest.f4m";
senalesLiveStream[5]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n01_1@138939/manifest.f4m";
senalesLiveStream[6]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n01_1@138939/manifest.f4m";
senalesLiveStream[7]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n01_1@138939/manifest.f4m";
senalesLiveStream[8]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n01_1@138939/manifest.f4m";
senalesLiveStream[9]="http://3v3ntmex-lh.akamaihd.net/z/m3xV5h0n01_1@138939/manifest.f4m";


var cambiaVideoHtml5=function(urlm3u8){
    var vidspace = document.getElementById('videoPlayerContainer');
    var thevid = vidspace.getElementsByTagName('video')[0];
    thevid.src = urlm3u8;
};

$(document).ready(function() {


    /* senales */
    $(".nav_smnu_video_02_bar .deg li").bind("click",function(evt) {
        $(".nav_smnu_video_02_bar .deg li").removeAttr("class");
        $(this).attr("class","current");

        var m3u8 = $(this).attr("m3u8");
        var video = $(this).attr("video");

        //codigo televisa
        if(isiPad){
            cambiaVideoHtml5(m3u8);
        }else{
            cambiaVideo(video + '?dtvuserguid=' + cookieTDTV, senalesLiveStream);
        }
    });


    /* cameras */
    $(".camera").bind("click",function(evt) {

        var m3u8 = $(this).attr("m3u8");
        var video = $(this).attr("video");

        //codigo televisa
        if(isiPad){
            cambiaVideoHtml5(m3u8);
        }else{
            cambiaVideo(video + '?dtvuserguid=' + cookieTDTV, senalesLiveStream);
        }
    });

    // indices
    $('.cameras_index .indice').bind("click",function(evt) {

        var m3u8 = $(this).attr("m3u8");
        var video = $(this).attr("video");

        //codigo televisa
        if(isiPad){
            cambiaVideoHtml5(m3u8);
        }else{
            cambiaVideo(video + '?dtvuserguid=' + cookieTDTV, senalesLiveStream);
        }
    });


    if ($("#cameras_data #save").length) {

        $( ".camera" ).draggable({ containment: "parent" });
        $( ".camera" ).dblclick(function() {
            var id = $(this).attr("id");
            var name = $(this).attr("name");
            var index = $(this).attr("index");
            var m3u8 = $(this).attr("m3u8");
            var video = $(this).attr("video");

            $( "#dialog form fieldset #ID" ).attr("value", id);
            $( "#dialog form fieldset #label" ).attr("value", name);
            $( "#dialog form fieldset #index" ).attr("value", index);
            $( "#dialog form fieldset #m3u8" ).attr("value", m3u8);
            $( "#dialog form fieldset #video" ).attr("value", video);
            $( "#dialog" ).dialog({ position: { my: "left top", at: "left bottom", of: this } });
            $( "#dialog" ).dialog( "open" );

        });
        $(".camera").mouseup(function(){

            $(this).attr("data-x", $(this).css("left"));
            $(this).attr("data-y", $(this).css("top"));
        });

        var camcount = 0;

        $( ".add-camera" ).click(function() {
            camcount++;
            var addId = $(this).attr("id");
            var camclass = "";
            if (addId == "add01")
                camclass = "camera left-right";
            else if (addId == "add02")
                camclass = "camera top-right";
            else if (addId == "add03")
                camclass = "camera top-left";
            else if (addId == "add04")
                camclass = "camera right-left";
            else if (addId == "add05")
                camclass = "camera bottom-left";
            else if (addId == "add06")
                camclass = "camera bottom-right";
            else if (addId == "add07")
                camclass = "camera top-bottom";
            else if (addId == "add08")
                camclass = "camera bottom-top";

            $( ".game_field" ).append( "<div id=\""+camcount+"\" name=\"\" index=\"\" m3u8=\"\" video=\"\" data-x=\"\" data-y=\"\" class=\""+camclass+"\"></div>" );
            $( ".cameras_index ul" ).append( "<li><a class=\"indice\" href=\"#\"  id=\""+camcount+"\"></a><div id=\"l"+camcount+"\"></div></li>" );
            $( ".camera" ).draggable({ containment: "parent" });
            $( ".camera" ).dblclick(function() {
                var id = $(this).attr("id");
                var name = $(this).attr("name");
                var index = $(this).attr("index");
                var m3u8 = $(this).attr("m3u8");
                var video = $(this).attr("video");

                $( "#dialog form fieldset #ID" ).attr("value", id);
                $( "#dialog form fieldset #label" ).attr("value", name);
                $( "#dialog form fieldset #index" ).attr("value", index);
                $( "#dialog form fieldset #m3u8" ).attr("value", m3u8);
                $( "#dialog form fieldset #video" ).attr("value", video);
                $( "#dialog" ).dialog({ position: { my: "left top", at: "left bottom", of: this } });
                $( "#dialog" ).dialog( "open" );

            });
            $(".camera").mouseup(function(){
                //alert("pos x:"+ $(this).css("left") + ", pos y:"+ $(this).css("top"));
                $(this).attr("data-x", $(this).css("left"));
                $(this).attr("data-y", $(this).css("top"));
            });


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
        });

        $( "#dialog" ).dialog({
            autoOpen: false,
            buttons: {
                "Ok": function() {

                    var id = $("#dialog form fieldset #ID").val();
                    var label = $("#dialog form fieldset #label").val();
                    var index = $("#dialog form fieldset #index").val();
                    var m3u8 = $("#dialog form fieldset #m3u8").val();
                    var video = $("#dialog form fieldset #video").val();

                    $(".game_field #"+id).attr("name", label);
                    $(".game_field #"+id).attr("index", index);
                    $(".game_field #"+id).attr("m3u8", m3u8);
                    $(".game_field #"+id).attr("video", video);

                    $("#l"+id).html(label);
                    $( ".cameras_index ul" ).find("#"+id).html(index);





                    $( this ).dialog( "close" );
                },
                "Delete": function() {
                    var id = $("#dialog form fieldset #ID").val();
                    $(".game_field #"+id).remove();
                    $( ".cameras_index ul" ).find("#"+id).parent().remove();

                    $( this ).dialog( "close" );
                },
                Cancel: function() {
                    $( this ).dialog( "close" );
                }
            },
            close: function() {
                $( this ).dialog( "close" );
            }
        });

        $( "#add01" ).button({
            icons: {
                secondary: "ui-icon-arrowthick-1-e"
            }
        });
        $( "#add02" ).button({
            icons: {
                secondary: "ui-icon-arrowthick-1-se"
            }
        });
        $( "#add03" ).button({
            icons: {
                secondary: "ui-icon-arrowthick-1-sw"
            }
        });
        $( "#add04" ).button({
            icons: {
                secondary: "ui-icon-arrowthick-1-w"
            }
        });
        $( "#add05" ).button({
            icons: {
                secondary: "ui-icon-arrowthick-1-nw"
            }
        });
        $( "#add06" ).button({
            icons: {
                secondary: "ui-icon-arrowthick-1-ne"
            }
        });
        $( "#add07" ).button({
            icons: {
                secondary: "ui-icon-arrowthick-1-s"
            }
        });
        $( "#add08" ).button({
            icons: {
                secondary: "ui-icon-arrowthick-1-n"
            }
        });
        $( "#save" ).button({
            icons: {
                secondary: "ui-icon-disk"
            }
        });


        $( "#save" ).click(function() {

            var json_string = "{ 'cameras' : { 'jcr:primaryType': 'nt:unstructured'";
            var cameraCount = 0;
            $('.vid_player_01c .cameras .cameras_field .game_field .camera').each(function(){
                cameraCount++;

                var label = $(this).attr('name');
                var index = $(this).attr('index');
                var m3u8 = $(this).attr('m3u8');
                var video = $(this).attr('video');
                var x = $(this).attr('data-x');
                var y = $(this).attr('data-y');
                var camclass = $(this).attr('class');

                json_string += ", '"+cameraCount+"' : { 'label' : '"+label+"', 'index' : '"+index+"', 'm3u8' : '"+m3u8+"', 'video' : '"+video+"', 'x' : '"+x+"', 'y' : '"+y+"', 'class' : '"+camclass+"' }";

            });
            json_string += " } }";


            $("#cameras_data").append("<input type=\"hidden\" name=\":content\" value=\""+json_string+"\" />");

            return true;
        });

        $( ".game_field .camera" ).each(function () {
            camcount++;
        });

    }

})