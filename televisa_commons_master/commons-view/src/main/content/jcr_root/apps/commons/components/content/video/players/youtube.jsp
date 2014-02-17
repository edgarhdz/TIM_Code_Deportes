<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Youtube script.
 *  This script renders the youtube video player javascript.
 *  See /etc/designs/televisa/noticieros/clientlibs/vid_player_01/js/vid_player_01.js
 *    
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 13/02/2013  | Pablo Alecio           | Initial Creation
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

<c:choose>
    <c:when test="${not empty properties.videos}"> <%-- if the 'videos' propery exists, it means it is a photogallery --%>
        <c:set var="isMultiple">
            <tg:isMultiple property="${properties.videos}"/>
        </c:set>
        <c:choose>
            <c:when test="${isMultiple}">
                <c:set var="path" value="${properties.videos[0]}" />
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
<c:if test="${note.video}">
    <c:set var="videoId" value="${note.videoId}"/>
</c:if>    

<script type="text/javascript">
	//This code is in vid_4vids_01 client library
	YoutubeVideoGalleryManager.initialize('${videoId}');    
</script>
