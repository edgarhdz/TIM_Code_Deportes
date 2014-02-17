<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Video component.
 *  This component is located in a Video Template. It displays a video along with 
 *  additional information such as video duration, published date, etc.
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 13/02/2013  | Pablo Alecio           | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>  
<%@include file="/apps/commons/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@page session="false" %>
<%-- <%@ taglib prefix="tg" uri="http://generics.televisa.com/taglibs/televisa-generics-taglib" %>
--%>
<fmt:message key="commons.videoEmbed.empty.label" var="invalidVideoMessage"/>

<%
    Resource ressss = resourceResolver.getResource(currentPage.getPath());
    Node pageNode = ressss.adaptTo(Node.class);
    
//TODO:This component is not implemented yet on deportes, so  this logic should be adjusted when the component is done 
//     if(WCMMode.fromRequest(request) == WCMMode.EDIT){
//         if(pageNode.hasNode("jcr:content/video") ){
//             if(!pageNode.hasNode("jcr:content/video/relatedArticles")){
//                 Node newNode = pageNode.addNode("jcr:content/video/relatedArticles" );
//                 newNode.setProperty( "sling:resourceType","televisa/components/content/generics/article/relatedArticles");
//                 pageNode.save();
//             }

//         }
//     }

%>

<c:set var="title" value="${properties.title}" />
<c:if test="${empty title}">
    <c:set var="title" value="${currentPage.title}" />
</c:if>
<c:set var="display" value="true" />    
<tg:noteProvider path="${currentPage.path}"/>

<c:choose>
    <c:when test="${properties.videoPlayer == '8'}">
        <c:set var="script" value="/apps/commons/components/content/video/players/brightcove.jsp" />
        <c:set var="duration" value=" ${note.brightcoveProperties['duration']}"/>
    </c:when>
    <c:when test="${properties.videoPlayer == '13'}">
        <c:set var="script" value="/apps/commons/components/content/video/players/youtube.jsp" />
        <c:set var="duration" value="${properties.youtubeVideoDuration}"/>
    </c:when>
    <c:otherwise>
        <c:set var="display" value="false" />        
    </c:otherwise>
</c:choose>
   
<c:choose>
    <c:when test="${display == 'true'}">    
        <div class="vid_player_01">   
            <cq:include script="/apps/commons/components/content/video/layouts/embed.jsp" />
            <cq:include script="${script}" />


            <%-- adding new change --%>

            <div class="vid_player_01_whtbkg">
                <p class="vid_player_01_black">${title}</p>
            </div>




            <div class="overlay">
                <div id="vid_player_01_txts">
                    <div class="txt1">
                        <h3>${title}</h3>
                        <p class="introtxt">${properties.summary}</p>
                        <c:if test="${not empty duration}">
                            <p class="vid_player_01_txtdetail topspace">Duración: ${duration}</p>
                        </c:if>
                        <c:if test="${not empty properties.chapter}">
                            <p class="vid_player_01_txtdetail">Capítulo: ${properties.chapter}</p>
                        </c:if>
                        <c:if test="${not empty properties.releaseDate}">
                            <p class="vid_player_01_txtdetail">Fecha: <fmt:formatDate pattern="dd/MM/yy" value="${properties.releaseDate.time}"/></p>
                        </c:if>
                    </div>
                    
                    <div class="txt2">
						 <cq:include path="relatedArticles" resourceType="/apps/deportes/generics/components/content/article/artreltd01" />
                    </div>
                    
                    <div class="txt3">
						<div class="wdg_social_01">
							<div id="widgetSocialShare"></div>
						</div>
					</div>
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="vid_player_01">
            <div class="vid_player_01_image">
                <img src="/libs/cq/ui/resources/0.gif" class="cq-video-placeholder" alt="" id="cq-gen97"  style="width: 624px; height: 351px;">
            </div>
            <div class="vid_player_01_whtbkg">
                <p class="vid_player_01_black">${invalidVideoMessage}</p>
            </div>
        </div>
    </c:otherwise>
</c:choose>
