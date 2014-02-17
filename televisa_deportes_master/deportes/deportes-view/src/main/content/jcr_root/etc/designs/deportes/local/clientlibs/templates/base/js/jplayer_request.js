$(document).ready(function(){
	 $(function(){
        if(isAndroidDefault){
            alert("Lo sentimos, el navegador no soporta la implementacion del player.")
        }
            $(".audio").mb_miniPlayer({
            width:240,
            inLine:false
        });
    });
});