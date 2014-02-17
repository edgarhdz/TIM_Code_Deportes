<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Video Embed component.
 *  This component can be dragged to a parsys. It displays a youtube or brightcove video.
 *  It has a short and a large version.
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 25/03/2013  | Pablo Alecio           | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<%@page session="false" %>
<fmt:message key="commons.videoEmbed.empty.label" var="invalidVideoMessage"/>
<cq:includeClientLib categories="deportes.vidPlayer01" />

<% request.setAttribute("embedPath", properties.get("path", "")); %>

<tg:noteProvider path="${properties.path}"/>
<c:set var="title" value="${note.title}" />
<c:if test="${empty title}">
    <c:set var="title" value="${currentPage.title}" />
</c:if>
<c:set var="tags" value="${note.tags}"/>
<c:set var="display" value="true"/>
<c:choose>
    <c:when test="${note.videoPlayer == '8'}">
        <c:set var="script" value="/apps/commons/components/content/video/players/brightcove.jsp" />
    </c:when>
    <c:when test="${note.videoPlayer == '13'}">
        <c:set var="script" value="/apps/commons/components/content/video/players/youtube.jsp" />
    </c:when>
    <c:otherwise>
        <c:set var="display" value="false"/>
    </c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${display == 'true'}">
        <div class="vid_player_01">

            <cq:include script="/apps/commons/components/content/video/layouts/embed.jsp" />
            <cq:include script="${script}" />

            <c:choose>
                <c:when test="${not empty properties.overlay}">

                    <div class="vid_player_01_whtbkg">
                        <p class="vid_player_01_black">${title}</p>
                    </div>


                </c:when>
                <c:otherwise>

                    <%-- adding title --%>
 					<div class="vid_player_01_whtbkg">
                        <p class="vid_player_01_black">${title}</p>
                    </div>


                    <div class="overlay">
                        <div id="vid_player_01_txts">
                            <div class="txt1">
                                <h2>${title}</h2>
                                <p class="introtxt" >${note.summary}</p>
                            </div>

                            <div class="txt2">
                                <!-- BEGIN:art_reltd_01 -->
                                    <h3 class="textcolor-title1">Temas Relacionados</h3>
                                    <%-- -iterate inside all the tags  to create the content and link--%>
                                    <c:forEach var="tag" begin="0" end="${fn:length(tags)}"  items ="${tags}" varStatus="status" >
                                        <a href="<tg:searchByTagUrl tag='${tag.title}'/>"  ${target}> <c:out value="${tag.title}" />
                                            <c:if test="${status.count != fn:length(tags)}">,</c:if>
                                        </a>
                                    </c:forEach>
                                <!-- END:art_reltd_01-->
                            </div>

                            <div class="txt3">
                                <cq:include path="socialNetworks" resourceType="deportes/generics/components/content/widgets/wdgSocial01" />
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
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
