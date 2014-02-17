<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Image Gallery Full component.
 *  
 *  component id = img_fotogalfull_01
 *  
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 12/02/2013  | Julio Esquivel         | Initial Creation.
 * 1.0     | 18/03/2013  | jpol@xumak.com         | HTML updated
 * 1.1     | 13/05/2013  | Pablo Alecio           | Externalized image paths
 *
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<%@page import="com.televisa.commons.services.datamodel.RenditionSize"%>
<%@page import="com.day.cq.dam.api.Asset"%>
<%@page import="com.day.cq.dam.api.Rendition"%>
<%@page import="java.util.*, org.apache.jackrabbit.commons.JcrUtils"%>

<%@page import="java.util.ArrayList,org.apache.commons.lang.StringEscapeUtils"%>

<fmt:message key="commons.component.message.titleboxes.placeholder" var="placeholdercomponentmessage"/>
<fmt:message key="commons.photogalleryfull01.description" var="photogallleryfulldescription"/>
<fmt:message key="commons.photogalleryfull01.titlemessage.description" var="titlemessagedescription"/>
<fmt:message key="commons.photogalleryfull01.title" var="componentname"/>

<%-- Content Validation  --%>
<c:choose>
    <c:when test="${currentPage.title != null}">
        <c:set var="content" value="${currentPage.title}" />
    </c:when>
    <c:otherwise>
        <c:set var="content" value="${currentPage.name}" />
    </c:otherwise>
</c:choose>

<%-- First Image --%>    
<c:set var="firstChildNode" value="${tg:getFirstChildNode(currentNode)}" />
<c:set var="firstDesc" value="${tg:getPropertyString(firstChildNode, 'imgDescription')}" />
<c:set var="imageAlt" value="${tg:getPropertyString(firstChildNode, 'imagealt')}" />
    <c:if test="${not empty firstChildNode}">  
        <c:set var="imagePrefix" value="${firstChildNode.name}/" />
        
        <%-- First Image --%>
        <c:set var="firstImage">
            <tg:renditionFromImageAsset name="${imagePrefix}fileReference" width="1024" height="768" />
        </c:set>
        <c:set var="firstSmallImage">
            <tg:renditionFromImageAsset name="${imagePrefix}fileReference" width="67" height="38" />
        </c:set>

    </c:if>

<%-- Tags --%>
<c:set var="tags" value="${currentPage.tags}" />

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>

<%-- Merge HTML and CSS for the img_fotogalfull_01 Component  --%>
<div id="main">
    <div id="col2">
        <div id="col2_content" class="clearfix">
            <h1>${currentPage.title}</h1>
            <p>${properties.shortDescription}</p>
        </div>

        <div class="related-social dotted-top">
            <strong>Temas Relacionados</strong>
            <p class="dotted-bottom">
                <!-- tags -->
                <c:set var="index" value="${1}" />
                <c:forEach var="i" items ="${tags}" varStatus="loop">
                    <c:set var="keyword"    value="${fn:toUpperCase(fn:substring(i.title, 0, 1))}${fn:toLowerCase(fn:substring(i.title, 1,fn:length(i.title)))}" />
                    <c:set var="keywordval" value="${fn:toUpperCase(fn:substring(i.name, 0, 1))}${fn:toLowerCase(fn:substring(i.name, 1,fn:length(i.name)))}" />
                        <a href="<tg:searchByTagUrl tag='${keywordval}'/>" title="${keyword}">${keyword}</a>
                        ${!loop.last ? ', ' : ''}
                    <c:set var="index" value="${index + 1}" />
                </c:forEach>
            </p>
            <!-- end related themes -->

            <!-- Widget social -->
            <div class="wdg_social_01">
                <div id="widgetSocialShare"></div>
            </div>

        </div>
    </div>

    <div id="col1">
        <div id="col1_content" class="clearfix">
            <div class="img_fotogalfull_01">

                <!-- image container -->

                <div class="image-container" data-currpos="0">
                    <!-- overlay -->
                    <c:choose>
                        <c:when test="${empty firstImage}">
                            <img  style="width: 99%; height: 550px;" src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="">
                        </c:when>
                        <c:otherwise>
                            <img class="mainimage" src="${firstImage}" alt="${firstAlt}"/>

                            <div class="share">
                                <a href="http://twitter.com/intent/tweet/?url=http%3A%2F%2Ftelevisa.com/some/path&amp;text=Hello+World!" class="td_bg lnk_twitter back_overlay" title="Tweet this!" target="_blank">
                                    <i class="tvsa-twitter"></i>
                                </a>
                                <a href="https://www.facebook.com/sharer/sharer.php?u=http%3A%2F%2Ftelevisa.com/some/path" class="td_bg lnk_facebook back_overlay" title="Share on Facebook" target="_blank">
                                    <i class="tvsa-facebook"></i>
                                </a>
                                <a href="http://pinterest.com/pin/create/button/?url=http://televisa.com/some/page/&amp;media=http://televisa.com/cool-image.jpg&amp;description=Description" title="Pin it!" class="td_bg pinterest lnk_pinterest pin-it-button back_overlay" target="_blank" >
                                    <i class="tvsa-pinterest"></i>
                                </a>
                            </div>

                            <div class="overlay">
                                <div class="controls">
                                    <a class="td_bg rewind back_overlay" href="#"><i class="tvsa-rewind"></i></a>
                                    <a class="td_bg play back_overlay" href="#"><i class="tvsa-play"></i></a>
                                    <a class="td_bg forward back_overlay" href="#"><i class="tvsa-forward"></i></a>
                                </div>
                                <p class="imageDescription">${firstDesc}</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>


                <!-- related container -->
                <div class="related-container" data-currpos="0">
                    <div class="suggestions">
                    </div>
                    <div class="overlay">
                        <div class="controls">
                            <a class="td_bg replay back_overlay" href="#">
                                <i class="tvsa-replay"></i>
                            </a>
                        </div>
                    </div>
                </div>

                <!-- carrousel -->
                    <div class="carousel">
                        <div class="carousel-content" <c:if test="${!previewMode}">style="overflow:visible"</c:if>>
                        <ul class="carousel" data-swipepos="0" <c:if test="${!previewMode}">id="sortable" style="width:100%"</c:if>>
							<%-- Loop through the image items --%>
                             <c:forEach  var="res" items="${tg:listChildren(resource)}" >
                                 <sling:include resource="${res}"/> 
                             </c:forEach>
                                <div class="imageItems">
                                    <li style="display:none;" >
                                        <a href="#" title="Related Content" data-src="" data-description=""></a>
                                    </li>
                                </div>
                            </ul>
                         </div>
                        <c:if test="${previewMode}">
                        	<a href="#" class="left disabled"><i class="tvsa-double-caret-left"></i></a>
                        	<a href="#" class="right"><i class="tvsa-double-caret-right"></i></a>
                        </c:if>
                    </div>
                <!-- DETAILS DOTTED-TOP -->

            </div>
            <%-- end img_fotogalfull_01 --%>
        </div>

        <!-- IE Column Clearing -->
        <div id="ie_clearing">&#160;</div>

    </div>

</div>
<!-- end main -->

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>

                        <script>
                        if(ImageGallery.Gallery){
                        	ImageGallery.Gallery.beginSort();
                    	}
                        	ImageGallery.FotoGalFull.init();                
                        </script>
