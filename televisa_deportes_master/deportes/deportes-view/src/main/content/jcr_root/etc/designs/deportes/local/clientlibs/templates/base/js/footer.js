(function() {
    $(document).ready(function() {
        var blueRow = $(".blue-row");

        blueRow.find(".show").bind("click",function(event){
            event.preventDefault();
            blueRow.removeClass("notExpanded");
            blueRow.addClass("expanded");
        });

        blueRow.find(".hide").bind("click",function(event){
            event.preventDefault();
            blueRow.removeClass("expanded");
            blueRow.addClass("notExpanded");
        });

    });
}());
