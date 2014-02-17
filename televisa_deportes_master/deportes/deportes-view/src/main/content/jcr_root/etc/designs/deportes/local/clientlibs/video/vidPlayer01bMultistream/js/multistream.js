doStats('esmas');

function change_size(){
    var t=setTimeout("change_size_code()",1500);
}
function change_size_code(){
    if (document.body && document.body.offsetWidth) {
        winW = document.body.offsetWidth;
        winH = document.body.offsetHeight;

        if (document.compatMode=='CSS1Compat' &&
            document.documentElement &&
            document.documentElement.offsetWidth ) {
            winW = document.documentElement.offsetWidth;
            winH = document.documentElement.offsetHeight;
        }
        if (window.innerWidth && window.innerHeight) {
            winW = window.innerWidth;
            winH = window.innerHeight;
        }
        var form = document.getElementById('akamaiMultiStreamPlayer');
        if( form != null) {

            resizePlayer(winW, winH);
        }
    }
}

function generarMultistreamXml() {

    var pagePath = $("#pagePath").val();
    var nodePath = $("#nodePath").val();

    $.ajax({
        type: "GET",
        url: "/bin/generics/multistream/xml",
        data: { currentPage : pagePath , currentNode : nodePath },
        dataType: "html"
    }).done(function (jsonData) {
            location.reload();
        });

}
			
			
			