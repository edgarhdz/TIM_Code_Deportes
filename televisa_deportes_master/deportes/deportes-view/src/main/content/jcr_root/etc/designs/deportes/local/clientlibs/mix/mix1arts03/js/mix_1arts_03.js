$(document).ready(function(){
    if( $(window).width() <  624 ){
        var $height = $('.mix_1arts_03 .resize img');
        $height.load(function() {
            var $imageHeight = $(this).width();
            var $totalHeight = $imageHeight * 0.75;
            $height.parent().height($totalHeight);
        });
    }
    $(window).resize(function() {
        if( $(window).width() <  624 ){
            var $imageHeight = $height.width();
            var $totalHeight = $imageHeight * 0.75;
            $height.parent().height($totalHeight);
        }
    });
});