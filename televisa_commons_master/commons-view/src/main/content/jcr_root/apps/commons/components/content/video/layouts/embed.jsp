<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Youtube script.
 *  This script renders the youtube video player.
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 27/02/2013  | Pablo Alecio           | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page session="false" %>

<c:set var="overlay" value=" large" />
<c:if test="${not empty properties.overlay}">
    <c:set var="overlay" value=" small" />
</c:if>

<c:choose>
    <c:when test="${not empty properties.path}">  <%-- if the 'path' propery exists, it means it is a video embed --%>
        <c:set var="path" value="${properties.path}" />
    </c:when>
    <c:otherwise>  <%-- else, it is a video detail --%>
        <c:set var="path" value="${currentPage.path}" />
    </c:otherwise>
</c:choose>

<tg:noteProvider path="${path}"/>

<c:set var="rendition">
    <tg:renditionSelector image="${note.videoImageAsset}" width="624" height="351"/>
</c:set>

<c:choose>
    <c:when test="${note.videoPlayer == '13'}">  <%-- if the 'path' propery exists, it means it is a video embed --%>
        <c:set var="beforeLoad" value="class='before-load'" />
    </c:when>
    <c:otherwise>  <%-- else, it is a video detail --%>
        <c:set var="hiddenPlayer" value="class='hidden-player'" />
    </c:otherwise>
</c:choose>

<style>

</style>
<div class="vid_player_01_image">
    <img src="${rendition}" alt="${properties.imageAlt}" id="img_stage_01_IMG" />
    <!-- PLAYER -->
    <div id="theaterContainers"	style="margin: 0px !important;" class="hide">
        <div id="leftBarLink" class="theaterSideSpacer"></div>
        <div style="float:left">
            <div id="videoLink" style="border: 0pt none; padding: 0pt; position: absolute; z-index: 10001; display:none;"></div>
            <div id="contenedor" ${beforeLoad} style="margin: 0px !important;"></div>
        </div>
        <!-- Start of Brightcove Player -->

        <div id="rightBarLink" class="theaterSideSpacer"></div>
    </div>
    <div style="display: none;" id="companionBanner"></div>
    <!-- // PLAYER -->
</div>
<a href="javascript: void(0);" class="btn square">
    <i class="tvsa-play" id="videobtn"></i>
</a>
<!--     <a href="javascript: void(0);"> -->
<%--         <div class="playersprite play${overlay}" id="videobtn"></div> --%>
<!--     </a> -->
