$(document).ready(function() {
    

    ;
    jQuery(function($){ 
        (function($,T){
            $('.timetable').each(function(ix,element){
            
                var $this = $(this), 
                Pointer = {
                    UP: (T.getIsTouchDevice()) ? 'touchend' : 'mouseup',
                    DOWN: (T.getIsTouchDevice()) ? 'touchstart' : 'mousedown'
                }, 
                $theUl = $this.find('>ul')
                ;
            
                $this.find('a.prev, a.next, .deportes-prev, .deportes-next').click(function(event){
                    event.preventDefault();
                });
            
                $this.find('a.prev, .deportes-prev').bind(Pointer.DOWN,function(){
                    $theUl.animate({
                        'scrollTop': $theUl.scrollTop() - $theUl.height()
                    }, 500);
                });
            
                $this.find('a.next, .deportes-next').bind(Pointer.DOWN,function(){
                    $theUl.animate({
                        'scrollTop': $theUl.scrollTop() + $theUl.height()
                    }, 500);

                });
            
            });
        })($,Televisa);
    });

});
