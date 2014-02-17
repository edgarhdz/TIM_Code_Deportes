$(window).load(function(){
	/**
	 * @Open firts element mix_12arts_02
	 * @in the html
	 **/
		$val = $('.td_videos_progs_01').find('.mix_12arts_01:nth-of-type(1)');
		//console.log($val.find('.type1a_'));
		//console.log($val.height());
		$val.find('.type1a_').toggle();
		$val.find('.str_pleca_01').find('.ocultar').show();
		$val.find('.str_pleca_01').find('.mostrar').hide();
		$val.find('.nav_paginator_01').show();
		$val.css('margin-bottom','25px');
		$val.height('auto')
});