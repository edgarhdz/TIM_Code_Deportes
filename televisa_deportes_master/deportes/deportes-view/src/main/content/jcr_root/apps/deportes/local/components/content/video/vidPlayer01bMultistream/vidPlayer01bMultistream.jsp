<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  MultiStream
 *  This script displays a player that holds 4 different signals within the same
 * 	player.
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 06/12/2013  | Luis Emilio Lopez      | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/deportes/local/components/global.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@page session="false" %>

<%
    String configXMLPath = "";
    if (properties.get("status") != null) {
        configXMLPath = currentNode.getPath().replace("jcr:content","_jcr_content") + "/config-multi-vods.xml";
        pageContext.setAttribute("configXMLPath",configXMLPath);
        pageContext.setAttribute("panelImage1",currentNode.getPath() + "/panelimage1");
        pageContext.setAttribute("panelImage2",currentNode.getPath() + "/panelimage2");
        pageContext.setAttribute("panelImage3",currentNode.getPath() + "/panelimage3");
        pageContext.setAttribute("panelImage4",currentNode.getPath() + "/panelimage4");
    }

%>

<c:set var="panelImagePath1">
    <tg:getPropertyFromNode path="${panelImage1}" property="fileReference" />
</c:set>
<c:set var="panelImagePath2">
    <tg:getPropertyFromNode path="${panelImage2}" property="fileReference" />
</c:set>
<c:set var="panelImagePath3">
    <tg:getPropertyFromNode path="${panelImage3}" property="fileReference" />
</c:set>
<c:set var="panelImagePath4">
    <tg:getPropertyFromNode path="${panelImage4}" property="fileReference" />
</c:set>

<c:set var="multiPanelSlateImages" value="${panelImagePath1};${panelImagePath2};${panelImagePath3};${panelImagePath4};" />

<c:set var="title" value="${properties.title}" />
<c:if test="${empty title}">
    <c:set var="title" value="${currentPage.title}" />
</c:if>
<%-- adding the tags of the page--%>
<c:set var="tags" value="${currentPage.tags}" />

<c:if test="${empty properties.status || empty properties.signalURL}">
    Please edit settings.
</c:if>

<c:if test="${not empty properties.status && not empty properties.signalURL}">
<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>
    <div class="vid_player_01b">
        <div id="divspacer"></div>
        <div id="akamaiMultiStreamPlayer">   </div>
        <div id="cb_medrect1_div" width="300" height="250" ></div>
        <input type="hidden" id="pagePath" value="<%= currentPage.getPath() %>" />
        <input type="hidden" id="nodePath" value="<%= currentNode.getPath() %>" />

        <script type="text/javascript" src="http://gksrv.esmas.com/esmasgeo?js=1"></script>
        <script type="text/javascript">
            var noAdds = '0';
            var esmas_dart_ord = Math.floor( Math.random() * 1E16 );
            var swf = "http://i2.esmas.com/tvolucion/swf/amsp1142.swf";
            var options = {
                version:"10.2.0",
                swfinstall:"http://www.esmas.com/cosmosfiles/avp/expressInstall.swf",
                enabledGbs: true,
                gbs: "${properties.geoFilter}",
                enabledVideoLog: true,
                normalWidth: '624',
                normalHeight: '351',
                expandWidth: '955',
                expandHeight: '544',
                linkBaseURL: ''
            };

            var localpath = document.URL;
            var requestUrl = null;
            var sponvar = '';
            var statusvar = '';

            requestUrl = 'es.esmas.video_embed' + '/' + 'clientes.editorial' +
            ';tile=9'+
            ';sz=1x1'+
            ';ord=' + esmas_dart_ord +
            ';dcmt=text/html'+
            ';zone0=clientes.editorial'+
            ';roll=pre'+
            ';pais=' + MN_geo.country.toLowerCase() +
            ';estado=' + escape(MN_geo.state.toLowerCase()) +
            ';ciudad=' + escape(MN_geo.city.toLowerCase()) +
            ';id_video=238404' + sponvar + statusvar;

            var urlReq = "";
            if(noAdds != 1){
                urlReq='http://ad.doubleclick.net/adx/'+requestUrl+'?callback=cbResponse';
            }
            var pagePath = $("#pagePath").val();
            var flashvars =
            {
                settings_url: "${configXMLPath}"
                , core_ads_enabled: true
                , auto_play: "true"
                , cache_bust_key: 123456
                , location: window.location.host
                , location: window.location.host+window.location.pathname
                , auto_play_list: false
                , ad_component_player_url: ""
                , ad_autoplay_btn_enabled: false
                , playlist_fullscreen_behavior: "persist"
                , fullscreen_enabled: true
                , multi_focus_color: 0xdc143c
                , multi_panel_slate_images: "${multiPanelSlateImages}"
                , ad_server_timeout: 20
                , dfp_tag_url: urlReq
                , dvr_enabled: 0
                , report_IDGalaxy: '238404'
                , report_IDTV:localpath
            };

            var params =
            {
                allowFullScreen: "true"
                , allowScriptAccess: "always"
                , wmode: "direct"
            };

            addMPlayer(flashvars, params, options);

        </script>

        <div class="overlay">
            <div class="txt1 left">
                <h3>${title}</h3>
                <p class="introtxt">${properties.shortDescription}</p>
                <p class="vid_player_01b_txtdetail topspace"></p>
                <p class="vid_player_01b_txtdetail"></p>
                <p class="vid_player_01b_txtdetail"></p>
            </div>

            <div id="vid_player_01b_txts" class="left">

                <div class="txt2 dotted-bottom">
                    <h3 class="textcolor-title1">Temas relacionados</h3>
                    <h4>
                        <%-- -iterate inside all the tags  to create the content and link--%>
                        <c:forEach var="tag" begin="0" end="${fn:length(tags)}"  items ="${tags}" varStatus="status" >
                            <a class="ui-link" href="<tg:searchByTagUrl tag='${tag.title}'/>"  ${target}> <c:out value="${tag.title}" />
                                <c:if test="${status.count != fn:length(tags)}">,</c:if>
                            </a>
                        </c:forEach>
                    </h4>
                </div>

                <div class="txt3">
                    <div class="wdg_social_01">
                        <div id="widgetSocialShare"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- close div of styles to devices -->
    <c:if test="${not empty properties.hideDevice}">
        </div>
    </c:if>
</c:if>