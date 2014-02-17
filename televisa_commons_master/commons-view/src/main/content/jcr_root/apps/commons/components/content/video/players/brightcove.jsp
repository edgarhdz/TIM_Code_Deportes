<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Brightcove script.
 *  This script renders the brightcove video player javascript.
 *  See /etc/designs/televisa/noticieros/clientlibs/vid_player_01/js/vid_player_01.js

 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 27/02/2013  | Pablo Alecio           | Initial Creation
 * -----------------------------------------------------------------------------
  * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 27/02/2013  | Otto Giron             | Refactored all javascript related to the player rendering
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>  
<%@include file="/apps/commons/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page session="false" %>
<%@page import="com.televisa.commons.services.datamodel.Video,
    			org.apache.sling.api.resource.ValueMap"%>
<%
String paramSite = "";
String paramBits = "";
String pathVideoPage = properties.get("path", "");
ValueMap videoMainProperties = null;

if(pathVideoPage.equals("")){
	String[] pathVideoPages = properties.get("videos", new String[0]);
    if(pathVideoPages.length > 0){

		videoMainProperties = currentPage.getPageManager().getPage(pathVideoPages[0]).getParent().getParent().getProperties();
    }else{

    	videoMainProperties = currentPage.getParent().getParent().getProperties();
    }

}else{

	videoMainProperties = currentPage.getPageManager().getPage(pathVideoPage).getParent().getParent().getProperties();

}

paramSite = videoMainProperties.get("site","");
paramBits = videoMainProperties.get("bits","");

pageContext.setAttribute("paramSite", paramSite);
pageContext.setAttribute("paramBits", paramBits);
%>
<c:choose>
    <c:when test="${not empty properties.videos}"> <%-- if the 'videos' propery exists, it means it is a photogallery --%>
        <c:set var="isMultiple">
            <tg:isMultiple property="${properties.videos}"/>
        </c:set>
        <c:choose>
            <c:when test="${isMultiple}">
                <c:set var="path" value="${properties.videos[0]}" />
                <c:set var="galleryVideos" value="${properties.videos}" />
            </c:when>
            <c:otherwise>
                <c:set var="path" value="${properties.videos}" />
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:when test="${not empty properties.path}">  <%-- if the 'path' propery exists, it means it is a video embed --%>
        <c:set var="path" value="${properties.path}" />
    </c:when>
    <c:otherwise>  <%-- else, it is a video detail --%>
        <c:set var="path" value="${currentPage.path}" />
    </c:otherwise>
</c:choose>

<tg:noteProvider path="${path}"/>   
<tg:getBrightcoveProperties var="brightcove" path="${path}" />

<%-- Scripts --%>
<script type="text/javascript" src="http://www.googletagservices.com/tag/js/gpt.js"></script>   
<script language="javascript" src="http://i2.esmas.com/tvolucion/js/Tim-HTML5-Player-1.0.0.js"></script> 
<script language="JavaScript" type="text/javascript" src="http://admin.brightcove.com/js/BrightcoveExperiences.js"></script>
<script language="JavaScript" type="text/javascript" src="http://admin.brightcove.com/js/APIModules_all.js"></script>
<script src="http://i2.esmas.com/tvolucion/js/brightcove-esmas-player-1.4.vst2.js" type="text/javascript"></script>
<script type="text/javascript" src="http://gksrv.esmas.com/esmasgeo?js=1"></script>   

<%-- /Scripts --%>          
<script>


   	var mobileDimensions = VideoUtils.getMobilePlayerSize();
   	var playerHeight;
    var playerWidth;
    if(mobileDimensions.height != null){
        playerHeight = mobileDimensions.height;
        playerWidth = mobileDimensions.width;
    }

    var galleryVideos = [];
    <c:if test="${not empty galleryVideos}">        <c:forEach var="video" items="${galleryVideos}">
            <tg:getBrightcoveProperties var="videoProperties" path="${video}" />
            <tg:noteProvider path="${video}"/>
            <c:set var="rendition">
                <tg:renditionSelector image="${note.videoImageAsset}" width="624" height="351"/>
            </c:set>

            galleryVideos.push({
            	thumbnail: "${rendition}",
                title: "${videoProperties.title}",               
                id: "${videoProperties.id}",
                cat: "${videoProperties.cat}",
                profile: "${videoProperties.profile}", 
                gbs: "${videoProperties.gbs}",
                permalink: "${videoProperties.publicUrl}",
                duration: "${videoProperties.duration}",
                origin: ${videoProperties.origin},
                ism3: ${videoProperties.isM3u8} || false,
                m3u8: '${videoProperties.m3u8}',
                width : playerWidth || ${videoProperties.html5PlayerWidth},
                height: playerHeight || ${videoProperties.html5PlayerHeight}
			});
        </c:forEach>
    </c:if>
    
    var ipadConfig = {
            thumbnail: "${rendition}",
            title: "${brightcove.title}",               
            id: "${brightcove.id}",
            cat: "${brightcove.cat}",
            profile: "${brightcove.profile}", 
            gbs: "${brightcove.gbs}",
            permalink: "${brightcove.publicUrl}",
            duration: "${brightcove.duration}",
            origin: ${brightcove.origin},
            ism3: ${brightcove.isM3u8} || false,
            m3u8: '${brightcove.m3u8}',
            width : playerWidth || ${brightcove.html5PlayerWidth},
            height: playerHeight || ${brightcove.html5PlayerHeight}
        };
    
    var brightcoveConfig = {
            bgcolor:"${brightcove.bgColor}",
            width:"${brightcove.flashPlayerWidth}",
            height:"${brightcove.flashPlayerHeight}",
            playerID:"${brightcove.playerId}",
            publisherID:"74091787001",
            playerKey:"AQ~~,AAAAEUA28vk~,ZZqXLYtFw-ADB2SpeHfBR3cyrCkvIrAe",
            isVid:"${brightcove.isVid}",
            isUI:"${brightcove.isUi}",
            dynamicStreaming:"${brightcove.dynamicStreaming}",
            videoId:"${brightcove.videoId}",
            wmode:"${brightcove.wMode}",
            linkBaseURL:"${brightcove.publicUrl}",
            autoStart:"${brightcove.autoStart}",
            profile:"${brightcove.profile}",
            quality:"high"
	}; 
    BrightCoveVideoGalleryManager.initialize(galleryVideos,brightcoveConfig,ipadConfig);
</script>
  
<script type="text/javascript" src="http://www.esmas.com/scripts/esmas_stats.js"></script>
<script type="text/javascript">doStats('esmas');</script>

