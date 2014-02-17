var DAxDetector = {};
DAxDetector.dax_focus = true;
DAxDetector._hasSpecificTag = true;
window.onblur = function(){ 
       DAxDetector.dax_focus = false;
};
window.onfocus= function(){ 
       DAxDetector.dax_focus = true;
};

function getCookieStats() { 
    if (document.cookie.length > 0) { 
        begin = document.cookie.indexOf("esmasstats="); 
        if (begin != -1) { 
            begin += 11; 
            (document.cookie.indexOf(";", begin) != -1)? end = document.cookie.indexOf(";", begin) : end = document.cookie.length;
            return unescape(document.cookie.substring(begin, end));
        } 
    }
    return null; 
}
var pageTracker = null;
function doStats(sitename, trackUrl) {
    //Google
    try {
        pageTracker = _gat._getTracker("UA-1776907-2");
        ( trackUrl ) ? pageTracker._trackPageview(trackUrl) : pageTracker._trackPageview();
        // Metricas comScore - DAx Generic Tag
        if(!DAxDetector._hasSpecificTag) {
            if(sitename == 'return') {
                uid_call();
            } else if(sitename == 'comments') {
                uid_call("#comments-metric");
            }
        }
    // Metricas comScore - DAx Generic Tag
    } 
    catch(err){
        //alert('Error en GA');
    }
    var nd = new Date ();
    var la_cookie = null;
    var domain = "";
    var siten = "esmas";
    if(location.href.indexOf("esmas.com")>-1) {
        domain = "domain=.esmas.com"
    }
    else {
        if(location.href.indexOf("yahoo.net")>-1) siten = "nyahoo";
        if(location.href.indexOf("esmas.segundamano.com.mx")>-1) siten = "nsegundamano";
        if(location.href.indexOf("esmascompras.com")>-1) siten = "nesmascompras";
        if(location.href.indexOf("esmas.metroscubicos.com")>-1) siten = "nmetroscubicos";
        if(location.href.indexOf("asegurateya.com/esmas/")>-1) siten = "nasegurateya";
        if(location.href.indexOf("despegar.com")>-1) siten = "ndespegar";
        if(location.href.indexOf("autocosmos.com")>-1) siten = "nautocosmos";
        if(location.href.indexOf("buongiorno.com")>-1) siten = "nbuongiorno";
        if(location.href.indexOf("esmas2006.occ.com.mx")>-1) { siten = "nocc";domain = "domain=.occ.com.mx"; }
        if(location.href.indexOf("mercadolibre.com")>-1) siten = "nmercadolibre";
        if(location.href.indexOf("televisaestudios.com")>-1) siten = "ntelevisaestudios";
        if(location.href.indexOf("chabelo.com")>-1) siten = "nchabelo";
        if(location.href.indexOf("tarabu.com")>-1) siten = "ntarabu";
        if(location.href.indexOf("sky.com.mx")>-1) siten = "nsky";
        if(location.href.indexOf("pontugranodearena.com")>-1) siten = "npontugrano";
        if(location.href.indexOf("esmastv.com.mx")>-1) siten = "nesmastv";
        if(location.href.indexOf("playcity.com.mx")>-1) siten = "nplaycity";
        if(location.href.indexOf("televisa.com")>-1) siten = "ntelevisa";
        if(location.href.indexOf("televisa.com.mx")>-1) siten = "ntelevisa";
        if(location.href.indexOf("festivalcervantino.gob.mx/Ficesmas/")>-1) siten = "ncervantino";
        if(location.href.indexOf("e-travelsolution.com.mx/search/esmas.aspx")>-1) siten = "ntravel";
        if(location.href.indexOf("multijuegos.com.mx")>-1) siten = "nmultijuegos";
        if(location.href.indexOf("gyggs.com")>-1) siten = "ngyggs"; 
        if(location.href.indexOf("enviayreporta.esmas.com")>-1) siten = "nenviayreporta";
        if(location.href.indexOf("tvjunkie.com.mx")>-1) siten = "ntvjunkie";
        if(location.href.indexOf("alchile.tv")>-1) siten = "nalchiletv";            
        if(location.href.indexOf("aniversarioesmasmovil.com")>-1) siten = "naniversario";
        if(location.href.indexOf("tuaviso.com.mx")>-1) siten = "ntuaviso";
        if(location.href.indexOf("s.com.mx/?esmas=1")>-1) siten = "ns";
        if(location.href.indexOf("estadioweb.com.mx")>-1) siten = "nestadioweb";
        if(location.href.indexOf("los10primeros.tv")>-1) siten = "nlos10primeros";
        if(location.href.indexOf("myautomovil.com")>-1) siten = "nmyautomovil";
        if(location.href.indexOf("telenautas.tv")>-1) siten = "ntelenautas";
        if(location.href.indexOf("fundaciontelevisa.org")>-1) siten = "nfundaciontelevisa";
        if(location.href.indexOf("televisadeportes.com")>-1) siten = "ntelevisadeportes";
//      if(location.href.indexOf("tvolucion.com")>-1) siten = "ntvolucion";
                if(location.href.indexOf("tvolucion.esmas.com")>-1) siten = "ntvolucion";
        if(location.href.indexOf("templeo.com")>-1) siten = "ntempleo";
        if(location.href.indexOf("vivelo.com")>-1) siten = "nvivelo";
        if(location.href.indexOf("vanidades.com")>-1) siten = "nvanidades";
    }
    if(navigator.cookieEnabled) {
        la_cookie = getCookieStats();
        if(!la_cookie) {
            document.cookie = "esmasstats="+(nd.getYear()+"").substring(2,4)+(((nd.getMonth()+1)<10)?"0":"")+(nd.getMonth()+1)+((nd.getDate()<10)?"0":"")+nd.getDate()+((nd.getHours()<10)?"0":"")+nd.getHours()+((nd.getMinutes()<10)?"0":"")+nd.getMinutes()+((nd.getSeconds()<10)?"0":"")+nd.getSeconds()+"-"+(Math.random()*1000000000000000000)+"-"+Math.round(Math.random()*100000000)+"; expires=Fri, 31 Jan 2020 23:59:59 GMT; path=/; " + domain;
            (getCookieStats())? la_cookie=getCookieStats() : la_cookie = "NO~COOKIE~ENABLED";
        }   
    }
    else {
        la_cookie = "NO~COOKIE~ENABLED";
    }
    var ref = document.referrer.replace(/\|/gi,',');
    var loc = location.href.replace(/\|/gi,',');
    loc = loc.replace('#','-');
    
    if(sitename == 'comments') {
        siten = "ncomentarios";
        loc = 'COMENTARIOS-' + loc; 
    }
//  var res = 'http://stats.esmas.com/stats/collectornew.cgi?'+loc+'|'+ref+'|'+siten+'|S000|'+nd.getTime()+'|'+la_cookie+'|'+navigator.cookieEnabled+'|'+(navigator.javaEnabled());
//  (window.screen)? res += '|'+screen.width+'x'+screen.height+'|'+screen.colorDepth : res+='||';
    var res = ''; 
    
    if( ( sitename == 'return' ) || ( sitename == 'comments' ) ) 
        return res;
    else {
//  document.write('<div><IMG SRC="'+res+'" WIDTH=1 HEIGHT=1 BORDER=0></div>');
    }
    //Comscore

}


//Google Analytics
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));

// DAx Generic Metric Methods
if(typeof prefixBase == "undefined"){
    DAxDetector._hasSpecificTag = false;
    function udm_(a){
        var t_mwf = DAxDetector.dax_focus ? "&t_mwf=yes" : "&t_mwf=no";
        var t_c7 = t_getURL();
        var b="comScore=",c=document,d=c.cookie,e="",f="indexOf",g="substring",h="length",i=2048,j,k="&ns_",l="&",m,n,o,p,q=window,r=q.encodeURIComponent||escape;
        if(d[f](b)+1)for(o=0,n=d.split(";"),p=n[h];o<p;o++)m=n[o][f](b),m+1&&(e=l+unescape(n[o][g](m+b[h])));
        a+=k+"_t="+ +(new Date)+k+"c="+(c.characterSet||c.defaultCharset||"")+t_mwf+"&c8="+r(c.title)+e+"&c7="+t_c7+"&c9="+r(c.referrer),a[h]>i&&a[f](l)>0&&(j=a[g](0,i-8).lastIndexOf(l),a=(a[g](0,j)+k+"cut="+r(a[g](j+1)))[g](0,i)),c.images?(m=new Image,q.ns_p||(ns_p=m),m.src=a):c.write("<p><img src='",a,"' height='1' width='1' alt='*'></p>");
    }
    function uid_call(a){
        var t_mwf = DAxDetector.dax_focus ? "&t_mwf=yes" : "&t_mwf=no";
        var t_c7 = t_getURL();
        var ui_pixel_url = 'http://b.scorecardresearch.com/p?c1=2&c2=6035759&ns_site=noticieros&name=no-tagged-ui&ns_type=hidden&type=hidden&ns_ui_type=no-tagged-ui';
        var b="comScore=",c=document,d=c.cookie,e="",f="indexOf",g="substring",h="length",i=2048,j,k="&ns_",l="&",m,n,o,p,q=window,r=q.encodeURIComponent||escape;
        if(d[f](b)+1)for(o=0,n=d.split(";"),p=n[h];o<p;o++)m=n[o][f](b),m+1&&(e=l+unescape(n[o][g](m+b[h])));
        ui_pixel_url+=k+"_t="+ +(new Date)+k+"c="+(c.characterSet||c.defaultCharset||"")+t_mwf+"&c8="+r(c.title)+e+"&c7="+t_c7+"&c9="+r(c.referrer)+"&b_ui_event=no-tagged-ui&c_ui_event=no-tagged-ui",ui_pixel_url[h]>i&&ui_pixel_url[f](l)>0&&(j=ui_pixel_url[g](0,i-8).lastIndexOf(l),ui_pixel_url=(ui_pixel_url[g](0,j)+k+"cut="+r(ui_pixel_url[g](j+1)))[g](0,i)),c.images?(m=new Image,q.ns_p||(ns_p=m),m.src=ui_pixel_url):c.write("<p><img src='",ui_pixel_url,"' height='1' width='1' alt='*'></p>");
    }
    function t_getURL(){
        var r = window.encodeURIComponent||escape;
        if(window.location != window.parent.location) {
            var _reportingURL =  r(window.parent.location.href);
            var _parentRestricted = (typeof _reportingURL == "undefined") ? true : false;
            _reportingURL = _parentRestricted ? r(window.location.href) + "&t_url_type=child-restricted" : _reportingURL + "&t_url_type=parent-allowed&t_childURL="+r(window.location.href);
            var _hasParentUDM = "&t_phasudm=unknown";
            if(!_parentRestricted)
                 _hasParentUDM = (typeof window.parent.udm_ == "undefined") ? "&t_paccess=restricted&t_phasudm=false" : "&t_paccess=allowed&t_phasudm=true";
            return _reportingURL + _hasParentUDM;
        }
        return r(window.location.href) + "&t_pv_type=window";
    }   
}
//Fin DAx Generic Metric Methods

function taboola_rboxReady(evnt) {
                TRC.drawRBox({mode:'story-horizontal',container:'taboola-esmas'});
}

function esmas_taboola(){    
    var taboolaLoader = document.createElement('script');
    
    document.getElementsByTagName('head')[0].appendChild(taboolaLoader);
    
    if (taboolaLoader.attachEvent)
        taboolaLoader.attachEvent("onactivate", taboola_rboxReady);
    
    else
        taboolaLoader.addEventListener("activate", taboola_rboxReady, false);
         
    
    taboolaLoader.type="text/javascript";
    
    taboolaLoader.src="../../../../../cdn.taboolasyndication.com/libtrc/esmas/rboxea04.js?article&dynamic";
}
