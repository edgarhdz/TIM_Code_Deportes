<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Video component json script.
 *  This script renders a json used by the video gallery
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 13/03/2013  | Pablo Alecio           | Initial Creation
 * 1.1     | 13/05/2013  | Pablo Alecio           | Externalized image paths
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page session="false" %>
<%@page import="com.televisa.commons.services.services.GsaService"%>
<%
    GsaService gsaService = sling.getService(GsaService.class);
    pageContext.setAttribute("gsaService", gsaService);
%>
<tg:noteProvider path="${currentPage.path}"/>
<tg:getBrightcoveProperties var="brightcove" path="${currentPage.path}" />
<c:set var="videoId" value="${properties.youtubeVideoId}"/>
<c:if test="${properties.videoPlayer == '8'}">
    <c:set var="videoId" value="${brightcove.videoId}"/>
</c:if>
<c:set var="rendition">
    <tg:renditionSelector image="${note.videoImageAsset}" width="624" height="351"/>
</c:set>

<c:set var="image" value="${rendition}"/>
<c:if test="${previewMode == 'true'}">
    <c:set var="image" value="${tg:getCompleteURL(gsaService, rendition, 'static')}"/>
</c:if>
{
"image" : "${image}",
"title" : "<c:out value="${note.title}"/>",
"player" : "${properties.videoPlayer}",
"videoId" : "${videoId}"
<c:if test="${properties.videoPlayer == '8'}">
    ,"bgcolor" : "${brightcove.bgColor}",
    "publicUrl" : "${brightcove.publicUrl}",
    "autoStart" : "${brightcove.autoStart}",
    "profile" : "${brightcove.profile}",
    "gbs" : "${brightcove.gbs}",
    "showRepeatVideoButton" : "${brightcove.showRepeatVideoButton}",
    "showFacebookTools" : "${brightcove.showFacebookTools}",
    "brightcove.showTwitterTools" : "${brightcove.showTwitterTools}",
    "brightcove.showExpandTools" : "${brightcove.showExpandTools}",
    "brightcove.showPopOutTools" : "${brightcove.showPopOutTools}",
    "brightcove.duration" : "${brightcove.duration}"
</c:if>
}