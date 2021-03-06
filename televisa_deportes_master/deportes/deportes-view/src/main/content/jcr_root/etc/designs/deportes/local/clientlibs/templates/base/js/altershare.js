(function ($) {
        $.fn.removePrefixedClasses = function (prefix) {
            var classNames = $(this).attr('class').split(' '),
                className,
                newClassNames = [],
                i;
            //loop class names
            for(i = 0; i < classNames.length; i++) {
                className = classNames[i];
                // if prefix not found at the beggining of class name
                if(className.indexOf(prefix) !== 0) {
                    newClassNames.push(className);
                    continue;
                }
            }
            // write new list excluding filtered classNames
            $(this).attr('class', newClassNames.join(' '));
        };
    }(jQuery));

$(document).ready(function() {
    setTimeout(function(){
        $("div.img_fotogalfull_01 div.share a:nth-of-type(3)").append('<i class="tvsa-pinterest"></i>'); 
        $("div.img_fotogalfull_01 div.share a:nth-of-type(3)").removePrefixedClasses('PIN_');
    },2500);    
});