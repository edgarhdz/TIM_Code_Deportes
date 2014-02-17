$(document).ready(function() {
	
	
	var latitude = 	$('.wdg_gmap_01 #wdg_gmap_01_properties').attr('data-latitude');  
	var longitude = $('.wdg_gmap_01 #wdg_gmap_01_properties').attr('data-longitude'); 
	var maptype = 	$('.wdg_gmap_01 #wdg_gmap_01_properties').attr('data-map');
	var zoom = 		$('.wdg_gmap_01 #wdg_gmap_01_properties').attr('data-zoom');
	var mapgoogle =  document.getElementById('mapgoogle');
	
	$('.wdg_gmap_01_properties').html('<iframe class="iframe_mapgoogle" id="mapgoogle" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" width="100%"  src="https://maps.google.com.mx/maps/ms?msa=0&amp;msid=216094874971383578550.0004e1162c42b0cb9d398&amp;ie=UTF8&amp;ll=' + latitude + ',' + longitude + '&amp;spn=0,0&amp;t='+maptype+'&amp;iwloc=lyrftr:m,3647405097792540577,' + latitude + ',' + longitude + '&amp;output=embed&amp;z='+parseInt(zoom)+'"></iframe>');
	
	
	$(window).resize(function() {
		
		resize();
		
	});
	
	resize();
	
	function resize(){
		
		if($(window).width() <= 590){
			
			var alto = ($(window).width() * 350) / 624;
            $('.wdg_gmap_01 .wdg_gmap_01_properties .iframe_mapgoogle').css('height', alto);			
        }
	}


	
	
});	