function generarXml() {

    var pagePath = $("#pagePath").val();
    var nodePath = $("#nodePath").val();

    $.ajax({
        type: "GET",
        url: "/bin/generics/videostream/xml",
        data: { currentPage : pagePath , currentNode : nodePath },
        dataType: "html"
    }).done(function (jsonData) {
       location.reload();
    });
}