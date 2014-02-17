/*BEGIN: weather*/
;(function() {
    $(document).ready(function() {
        if (!$.browser.opera) {

            $('select.select').each(function(){
                var title = $(this).attr('title');
                if( $('option:selected', this).val() != ''  ) title = $('option:selected',this).text();
                $(this)
                .css({'z-index':10,'opacity':0,'-khtml-appearance':'none'})
                .after('<span class="select">' + title + '<span class="arrow">></span></span>')
                .change(function(){
                    val = $('option:selected',this).text();
                    $(this).next().html( val + '<span class="arrow">></span>');
                    
                });
            });
        };
    });
}());
/*END: weather*/