$(document).ready(function(){
	$('.wdg_comen_01 .wdg_comen_01_verMas').click(function(e){ 
		e.preventDefault();
		$('.wdg_comen_01 .wdg_comen_01_comments li').css('display','block');
	});
	/* alert(navigator.userAgent);
	Safari browser style 
	if ($.browser.safari) {   
	alert("Realiza js");		
		$('.wdg_comen_01 .wdg_comen_01_comments li').css('padding-top','20px');
		$('.wdg_comen_01 .wdg_comen_01_comments li .wdg_comen_01_img').css('margin-top','0px');		
		$('.wdg_comen_01 .wdg_comen_01_comments li').css('padding-bottom','20px');
		$('.wdg_comen_01 .wdg_comen_01_comments li.reply1 div').css('background','none');
		$('.wdg_comen_01 .wdg_comen_01_comments li.reply1 .wdg_comen_01_img:nth-child(1)').css('margin-top','-5px');
		$('.wdg_comen_01 .wdg_comen_01_comments li.reply1 .wdg_comen_01_img:nth-child(3)').css('margin-top','10px');
	}*/
	
});