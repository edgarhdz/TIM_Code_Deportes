function hideEmailText(){
    $('.email-txt').val("");
}

function showEmailText(){
    if($('.email-txt').val()==""){
        $('.email-txt').val("Correo electrónico");
    }
}

var config_mailing = {
    msgs: {
        "title": 'Televisi&oacuten',
        "text": 'Inscr&iacute;bete y recibe las noticias m&aacute;s populares y lo &uacuteltimo de las telenovelas',
        "button_text": 'Inscr&iacute;bete',
        "thanks_text": 'Gracias por suscribirte.',
        "input_text": 'Correo electrónico',
        "working_msg": 'La informaci&oacute;n se est&aacute; enviando.'

    },
    templates: {
        "form": '<form method="post" action="{server}" target="social_mailing" name="mailing-form-post" id="mailing-form-post" onsubmit="return mailing.validate(this);"> <div class="container-top background-color1"><div class="performance background-color1"></div><div class="img-download"></div><p class="textcolor-title3">{text}</p></div><div class="container-email"><div class="container-right"><input type="text" value="{input_text}" name="email"  onfocus="mailing.cleaninput(this)" onblur="mailing.cleaninput(this,0)"  class="email-txt"></div></div><div class="container-btn"><input type="submit" value="{button_text}" class="subscribe"></div></form>',
        "working": '<div class="container-top background-color1"><div class="img-download"></div><p class="textcolor-title3">{working_msg}</p></div><div class="container-email"><div class="container-right"></div></div>',
        "thanks": '<div class="container-top background-color1"><div class="img-download"></div><p class="textcolor-title3">{thanks_text}</p></div><div class="container-email"><div class="container-right"></div></div>'
    },
    imgs: {
        logo: 'http://i2.esmas.com/comunidades/registro/img/multireg_ic_tvsa.png'
    },
    css: 'http://i2.esmas.com/comunidades/css/mailing/mailing-rediseno.css'
};