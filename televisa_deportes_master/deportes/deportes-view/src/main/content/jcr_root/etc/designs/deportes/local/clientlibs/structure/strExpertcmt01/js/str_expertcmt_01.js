$(document).ready(function() {	
	$('.more').on('click',function(){
		$(this).siblings('.expert_container_block').show();
		$(this).css('display','none');
	})
});