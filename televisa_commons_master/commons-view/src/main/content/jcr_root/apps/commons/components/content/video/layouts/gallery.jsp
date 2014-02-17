<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Video Gallery script.
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 04/03/2013  | Pablo Alecio           | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page session="false" %>
<fmt:message key="commons.video.emptygallery.label" var="emptyGalleryLabel"/>

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
<tg:noteProvider path="${path}"/>
<c:set var="rendition">
    <tg:renditionSelector image="${note.videoImageAsset}" width="624" height="468"/>
</c:set>
<c:set var ="display" value="true"/>
<c:if test="${empty properties.videos}">
    <c:set var ="display" value="false"/>
</c:if>

<c:choose>
    <c:when test="${note.videoPlayer == '13'}">  <%-- if the 'path' propery exists, it means it is a video embed --%>
        <c:set var="beforeLoad" value="class='before-load'" />
    </c:when>
    <c:otherwise>  <%-- else, it is a video detail --%>
        <c:set var="hiddenPlayer" value="class='hidden-player'" />
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${display}">

		<!-- BEGIN: str_pleca_01 -->
            <cq:include path="str_pleca_01" resourceType="/apps/commons/components/content/structure/singleTitleBox" />
        <!-- END: str_pleca_01 -->

		<div class="vid_4vids_05" data-current="0" data-enhance="false">
            <div class="image-container">
               <a href="#" class="twitter td_bg lnk_twitter back_overlay" title="Tweet this!" target="_blank"><i class="tvsa-twitter"></i></a>
               <a href="#" class="facebook td_bg lnk_facebook back_overlay" title="Share on Facebook" target="_blank"><i class="tvsa-facebook"></i></a>
               <a href="#" title="Pin it!" class="td_bg pinterest pin-it-button back_overlay"><i class="tvsa-pinterest"></i></a>

                <img class="mainimage" src="${rendition}" alt="${note.title}" width="624" height="468" />
	            <div id="theaterContainer" style="display: block;"  class="hide"><div id="contenedor"></div></div>
                <span class="loading"><i class="tvsa-spinner tvsa-spin tvsa-3x"></i></span>

                <div class="overlay">
                  <div class="controls">
                     <a class="td_bg play back_overlay" href="#"><i class="tvsa-play"></i></a>
                  </div>
                  <p class="imageDescription">${note.title}</p>
                </div>
            </div>

            <div class="gallery-controls">
               <div class="type1a_ carrusel2">
                  <div class="arrowLeft"><a href="#left" class="left" title="Link Description"><i class="tvsa-double-caret-left inactive"></i></a></div>
                  <div class="arrowRight"><a href="#right" class="right" title="Link Description"><i class="tvsa-double-caret-right"></i></a></div>
                  <div class="carruselcontainer">
                     <ul id="carrusel2_ul" class="ulcarrusel">
                        <c:set var="i" value="${0}" />
                        <c:forEach var="video" items="${properties.videos}">
                            <c:set var="i" value="${i+1}" />
                        
                            <tg:noteProvider path="${video}"/>
                            <c:if test="${note.video}"> <%-- At this point, all paths should reference videos, but we still double check. --%>
                                <c:set var="thumbnail">
                                    <tg:renditionSelector image="${note.videoImageAsset}" width="136" height="77"/>
                                </c:set>
                                <li>
                                    <c:if test="${i == 1}">
                                       <a href="${note.url}.html" title="${note.title}" class="album active" data-pos="${i - 1}">
                                    </c:if>
                                    <c:if test="${i != 1}">
	                                   <a href="${note.url}.html" title="${note.title}" class="album" data-pos="${i - 1}">
                                    </c:if>
                                       <img src="${thumbnail}" alt="${note.title}" width="136" height="77" />
                                       <span class="icon"><i class="tvsa-play"></i></span>
                                       <span class="overlay"><i class="tvsa-video tvsa-3x"></i></span>
                                       <span class="description">
	                                       <span class="title">${note.title}</span>
                                       </span>
                                   </a>
                                </li>
                            </c:if>
                        </c:forEach>
                        <span class="data">
                            [
                            <c:set var="i" value="${0}" />                            
                            <c:forEach var="video" items="${properties.videos}">
                                <c:set var="i" value="${i+1}" />

                                <tg:noteProvider path="${video}"/>
                                <c:set var="rendition">
                                    <tg:renditionSelector image="${note.videoImageAsset}" width="624" height="468"/>
                                </c:set>

                                <c:if test="${i > 1}">
                                   ,
                                </c:if>

                                <c:if test="${note.video}">
                                    {"images":[{"path":"${rendition}","description":"${note.title}","expandurl":"${note.url}.html"}]}
                                </c:if>
                            </c:forEach>                            
                            ]
                        </span>
                     </ul>
                  </div>
                  <div class="degradado"></div>
               </div>
            </div>
            <!-- END: overlay -->
        </div>
    </c:when>
    <c:otherwise>
        <div class="vid_4vids_01">
            <div class="imgContainer">
                <img src="/libs/cq/ui/resources/0.gif" class="cq-video-placeholder" alt="" id="cq-gen97"  style="width: 624px; height: 351px;">
            </div>
            <div class="overlay">
                <p>${emptyGalleryLabel}</p>
            </div>
        </div>
    </c:otherwise>
</c:choose>
