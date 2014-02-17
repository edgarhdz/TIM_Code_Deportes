 $(document).ready(function(){
	 /**
	  * @desc make the separator line as high as the higher div that it is separating
	  * @todo shouldn't the separator line be a component itself?
	  */
		$('.separator_component').each(function(){
		var h = Math.max($(this).prev('div:eq(0)').height(),$(this).next('div:eq(0)').height()); // higher value
		h = h -36;
		$(this).css({height:h+'px'}); // set separator height
	});
});