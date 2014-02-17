<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Akamai livestream
 *  This script renders the akamai video player.
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 05/11/2013  | Jorge Diaz             | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/deportes/local/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.day.cq.wcm.foundation.Image,
                org.apache.sling.api.resource.ValueMap"%>
<%@page session="false" %>
<cq:includeClientLib categories="deportes.vidplayer01c" />
<fmt:message key="deportes.akamai.empty.label" var="emptyVideo"/>

<c:if test="${not empty currentPage}">
    <input type="hidden" id="pagePath" value="<%= currentPage.getPath() %>" />
</c:if>
<c:if test="${not empty currentNode}">
    <input type="hidden" id="nodePath" value="<%= currentNode.getPath() %>" />
</c:if>


<%
    Image img;
    Image imgOff;
    Image imgPrev;
    Image imgPost;
    Image imgError;

    String imgSrc = "#";
    String imgOffSrc = "#";
    String imgPrevSrc = "#";
    String imgPostSrc = "#";
    String imgErrorSrc = "#";

    //get the resources
    Resource resImg = resourceResolver.getResource(resource, "img");
    if (resImg != null) {
        img = new Image(resImg);
        if (img != null && img.hasContent()) {
            img.setSelector(".img");
            imgSrc = img.getFileReference();
        }
    }
    pageContext.setAttribute("imgSrc", imgSrc);

    Resource resImgOff = resourceResolver.getResource(resource, "off");
    if (resImgOff != null) {
        imgOff= new Image(resImgOff);
        if (imgOff != null && imgOff.hasContent()) {
            imgOff.setSelector(".img");
            imgOffSrc = imgOff.getFileReference();
        }
    }
    pageContext.setAttribute("imgOffSrc", imgOffSrc);

    Resource resImgPrev = resourceResolver.getResource(resource, "prev");
    if (resImgPrev != null) {
        imgPrev= new Image(resImgPrev);
        if (imgPrev != null && imgPrev.hasContent()) {
            imgPrev.setSelector(".img");
            imgPrevSrc = imgPrev.getFileReference();
        }
    }
    pageContext.setAttribute("imgPrevSrc", imgPrevSrc);

    Resource resImgPost = resourceResolver.getResource(resource, "post");
    if (resImgPost != null) {
        imgPost= new Image(resImgPost);
        if (imgPost != null && imgPost.hasContent()) {
            imgPost.setSelector(".img");
            imgPostSrc = imgPost.getFileReference();
        }
    }
    pageContext.setAttribute("imgPostSrc", imgPostSrc);

    Resource resImgError = resourceResolver.getResource(resource, "error");
    if (resImgError != null) {
        imgError= new Image(resImgError);
        if (imgError != null && imgError.hasContent()) {
            imgError.setSelector(".img");
            imgErrorSrc = imgError.getFileReference();
        }
    }
    pageContext.setAttribute("imgErrorSrc", imgErrorSrc);

    //get ads properties
    ValueMap programProperties = null;
    String paramSite = "";
    String paramBits = "";
    programProperties = currentPage.getPageManager().getPage(currentPage.getPath()).getParent(2).getProperties();
    paramSite = programProperties.get("adSite","");
    paramBits = programProperties.get("adBits","");

    pageContext.setAttribute("paramSite", paramSite);
    pageContext.setAttribute("paramBits", paramBits);

%>

<script>
    if(window.esmas_dart_ord == undefined){
        esmas_dart_ord = window.esmas_dart_ord || Math.floor(Math.random()*1E16);
    }
</script>

<c:set var="title" value="${properties.title}" />
<c:if test="${empty title}">
    <c:set var="title" value="${currentPage.title}" />
</c:if>

<%-- Scripts --%>
<script type="text/javascript" src="http://gksrv.esmas.com/esmasgeo?js=1"></script>
<script language="JavaScript" src="http://i2.esmas.com/tvolucion/js/Tim-Akamai-Player-2.0.js"></script>

<script>
    // ipad
    var isiPad = false;
    isiPad = navigator.userAgent.match(/iPad/i) != null;
    if(!isiPad)
        isiPad = navigator.userAgent.match(/iPhone/i) != null;
    if(!isiPad)
        isiPad = navigator.userAgent.match(/iPod/i) != null;
    if (!isiPad)
        isiPad = navigator.userAgent.match(/Android/i) != null;

    var contenidoIpad =
            '		<div id="videoPlayerContainer">'+
                    '			<div id="videoLink" style="border: 0pt none; padding: 0pt; position: absolute; z-index: 20001; display:none;"></div>'+
                    '		</div>';
    var idvidg = "222153";


    var objHtml5 = new HTML5Player({
        thumbnail: "",
        title: "${properties.title}",
        id: "",
        cat: "",
        profile: "hls2",
        gbs: "${properties.geoFilter}",
        permalink: '',
        duration: '${properties.duration}',
        width : "604",
        height: "341",
        idgal : "224321"
    });

    function InitIpad() {
        var ism3 = true;
        document.getElementById('reproduceVideoCancha').innerHTML = '';
        document.getElementById('reproduceVideoCancha').innerHTML =contenidoIpad;
        var origin = '11';
        var extension = '';
        if( isiPad && (((origin == 8 && ism3 == false ) || origin==9 ) || idvidg == "163226" )){
            objHtml5.CreatePlayer('videoPlayerContainer');
        }else if(isiPad && (origin == 8 || origin == 10 || origin == 11) && ism3 ){
            objHtml5.CreatePlayerM3('videoPlayerContainer','${properties.m3u8}');

        }else {
            objHtml5.VideoNotAvailable('videoPlayerContainer');
            papa = document.getElementById('videoSplash');
            if(papa.childNodes[0]){
                hijo = papa.childNodes[0];
                hijo.style.height = "100%";
            }
        }

        setInterval(getState, 120000);

    }

</script>

<script>
    function s4() {
        return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
    };

    function guidDTvUserToken() {
        return s4() + s4() + '-' + s4() + '-' + s4() + '-' +s4() + '-' + s4() + s4() + s4();
    }
    function leerCookie(nombre){
        a = document.cookie.substring(document.cookie.indexOf(nombre + '=') + nombre.length + 1,document.cookie.length);
        if(a.indexOf(';') != -1)a = a.substring(0,a.indexOf(';'))
        return a;
    }
    var cookieTDTV=leerCookie("TDTVUserToken");
    if(cookieTDTV==''){
        var fechaCookie = new Date();
        fechaCookie.setTime(fechaCookie.getTime() + (360*24*60*60*1000));
        document.cookie="TDTVUserToken = "+guidDTvUserToken()+" ; expires = "+ fechaCookie.toGMTString()+"; path=/";
        cookieTDTV=leerCookie("TDTVUserToken");
    }
</script>
<%-- /Scripts --%>

<%-- Tags --%>
<c:set var="tags" value="${currentPage.tags}" />

<c:set var="display" value="false"/>
<c:if test="${not empty properties.akamaiId}">
    <c:set var="akamaiId" value="id=${properties.akamaiId}&"/>
    <c:set var="display" value="true"/>
</c:if>
<c:set var="smavp" value="smavp=false"/>
<c:if test="${not empty properties.smavp}">
    <c:set var="smavp" value="smavp=true"/>
</c:if>

<%-- Image Rendition --%>
<c:set var="image">
    <%-- actual size 624*351 --%>
    <tg:getAssetPathBySize path="${imgSrc}" width="319" height="319" />
</c:set>

<%-- Title --%>
<c:set var="title" value="${properties.title}" />
<c:if test="${empty title}">
    <c:set var="title" value="${currentPage.title}" />
</c:if>

<c:choose>
    <c:when test="${(not empty properties.height) && (not empty properties.width)}">
        <c:set var="height" value="${properties.height}"/>
        <c:set var="width" value="${properties.width}"/>
    </c:when>
    <c:otherwise>
        <c:set var="height" value="343"/>
        <c:set var="width" value="613"/>
    </c:otherwise>
</c:choose>

<c:set var="dimensions" value="&h=${height}&w=${width}"/>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>

<div class="vid_player_01c">
    <div class="videowrapper">
        <!-- PLAYER OnDemand-->

        <div id="theaterContainers" class="hide">
            <div id="leftBarLink" class="theaterSideSpacer"></div>
            <div style="float:left">
                <div id="videoLink" style="border: 0pt none; padding: 0pt; position: absolute; z-index: 10001;" ></div>
                <!-- Start of Brightcove Player -->
                <div id="contenedor-OnDemand" style="margin:0px !important;"></div>

            </div>

            <div id="rightBarLink" class="theaterSideSpacer"></div>

        </div>

        <div style="display:none;" id="companionBanner"></div>
        <!-- // PLAYER OnDemand-->


        <div class="vid_player_01c_image">

            <div class="image-container">
                <c:if test="${(not empty image)}">
                    <img src="${image}" alt="${properties.shortDescription}" id="img_stage_01_IMG">
                </c:if>
            </div>

            <!-- PLAYER -->

            <div id="videoPlayerCancha">
                <div id="reproduceVideoCancha">
                    <div id="videoPlayerContainer">
                        <!-- video error -->
                        <div id="videoLink" style="border: 0pt none; padding: 0pt; position: absolute; z-index: 20001; display:none;"></div>

                    </div>

                    <div id="flashContent">
                        <p>
                            Para ver esta pagina asegurate de tener la version de Adobe Flash Player 10.0.0 o mayor esta instalado.
                        </p>
                        <script type="text/javascript">
                            var pageHost = ((document.location.protocol == "https:") ? "https://" :	"http://");
                            document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='"
                                    + pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>" );
                        </script>
                    </div>

                    <script type="text/javascript">
                        //var sponsor=parent.getElementById('banheader').contentWindow.lt_index;
                        var localpath = document.URL;
                        //var swf	= "http://tvolucion.esmas.com/AkamaiStandardPlayer2.swf";
                        var swf	= "http://tvolucion.esmas.com/aspv2170.swf";
                        var path = window.location.href;
                        var swfVersionStr = "10.1.0";
                        var xiSwfUrlStr = "http://www.esmas.com/cosmosfiles/avp/expressInstall.swf";
                        var attributes = {};
                        attributes.id = "AkamaiMediaPlayer";
                        attributes.name = "AkamaiMediaPlayer";
                        attributes.align = "left";
                        attributes.onmouseover = "AVPToolbarShow()";
                        attributes.onmouseout = "AVPToolbarHide()";
                        var flashvars=[];
                        flashvars={
                            poster:"http://i2.esmas.com/2013/11/06/584061/pant-tu-diriges-154x113.jpg",
                            title:"${properties.title}",
                            settings_url:"${currentPage.path}" + "/_jcr_content/livestream/config_hds.xml",
                            autoplay:true,
                            cache_bust_key:"123456",
                            start_bitrate_index:"-1",
                            controls:true,
                            report_IDGalaxy: "238404",
                            report_IDTV: "HDN2 LIVE-238404"
                        };

                        var options = {
                            video_url:"${properties.url}",
                            enabledGbs: true,
                            gbs: "${properties.geoFilter}",
                            enabledVideoLog: true,
                            videoTitle:"${properties.title}",
                            showFacebookTools: false,
                            showTwitterTools: false,
                            showExpandTools: false,
                            showPopOutTools: false,
                            videoDuration:'${properties.duration}',
                            normalWidth: '604',
                            normalHeight: '341',
                            expandWidth: '955',
                            expandHeight: '544',
                            linkBaseURL: 'http://tvolucion.esmas.com/30/rama-prueba/238404/prueba-multi-multi-multi/',
                            ad: {
                                adSite: 'es.esmas.videodep',
                                adZone: '${paramSite}',
                                adTile: '9',
                                adSize: '1x1',
                                adConfig: '${paramBits}',
                                ord: esmas_dart_ord,
                                topPanel: 'videoPlayerContainer',
                                rightPanel: 'videoSplash',
                                cubePanel: 'companionBanner',
                                videoId: "238404",
                                curtainPanel: 'superTelon',
                                sponsor: ( ( typeof parent.window.sponsor != 'undefined' ) ? parent.window.sponsor : '' ),
                                oficial: false
                            }
                        };
                        if(!isiPad)	{
                            var params = {
                                allowFullScreen: 'true'
                                ,allowScriptAccess: 'always'
                                ,bgcolor:"#000000"
                                ,wmode:'transparent'
                                ,quality:'high'
                            };
                            swfobject.embedSWF(swf,'flashContent','613','343',swfVersionStr,xiSwfUrlStr,flashvars,params,attributes);
                            swfobject.createCSS("#flashContent", "display:block;text-align:left;");
                        }else{
                            //document.body.innerHTML = '<div id="videoSplash" class="sectionContainer" style="display:none;"></div>';
                            window.onload=InitIpad;
                        }
                    </script>
                </div>
            </div>

            <!-- // PLAYER -->
            <!-- Boton Tu diriges-->
            <div class="openbtn_td" style="display: none; "><i class="tvsa-videocamera"></i></div>
            <!--// Tu diriges-->
        </div>

        <a href="javascript: void(0);" class="btn square ui-link">
            <i class="tvsa-play" id="videobtn"></i>
        </a>
        <div class="vid_player_01c_whtbkg">
            <p class="vid_player_01c_black">${title}</p>
        </div>

        <div class="overlay">
            <div class="txt1 left">
                <h3>${title}</h3>
                <p class="introtxt">${properties.shortDescription}</p>
                <p class="vid_player_01c_txtdetail topspace"></p>
                <p class="vid_player_01c_txtdetail"></p>
                <p class="vid_player_01c_txtdetail"></p>
            </div>

            <div id="vid_player_01c_txts" class="left">
                <div class="txt2 dotted-bottom">
                    <h3 class="textcolor-title1">Temas relacionados</h3>
                    <h4>
                        <c:set var="index" value="${1}" />
                        <c:forEach var="i" items ="${tags}" varStatus="loop">
                            <c:set var="keyword"    value="${fn:toUpperCase(fn:substring(i.title, 0, 1))}${fn:toLowerCase(fn:substring(i.title, 1,fn:length(i.title)))}" />
                            <c:set var="keywordval" value="${fn:toUpperCase(fn:substring(i.name, 0, 1))}${fn:toLowerCase(fn:substring(i.name, 1,fn:length(i.name)))}" />
                            <c:choose>
                                <c:when test="${index % 3 != 0}">
                                    <a class="art_related_01_black" href="<tg:searchByTagUrl tag='${keywordval}'/>">${keyword}</a>
                                    ${!loop.last ? ', ' : ''}
                                </c:when>
                                <c:otherwise>
                                    <a class="art_related_01_black" href="<tg:searchByTagUrl tag='${keywordval}'/>">${keyword}</a>
                                    <br/>
                                </c:otherwise>
                            </c:choose>
                            <c:set var="index" value="${index + 1}" />
                        </c:forEach>
                    </h4>
                </div>

                <div class="txt3">

                </div>
            </div>
            <div class="txt4">
                <img src="http://lorempixel.com/294/245/sports">
            </div>
        </div>
    </div>






    <c:if test="${properties.showCancha}">
        <cq:include path="cameras" resourceType="/apps/deportes/local/components/content/video/cameraSelection"/>
    </c:if>
</div>

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>
