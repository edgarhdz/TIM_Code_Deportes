<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Image Gallery Full Item Component
 *  This component is used to configure the image item for Image Gallery Full,
 *  component.
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 03/12/2013  | jbarrera               | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>

<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<c:if test="${authorMode && empty properties.fileReference}">
    Please edit component
</c:if>

<c:if test="${not empty properties.fileReference}">
    <c:set var="smallImage">
        <tg:renditionFromImageAsset name="fileReference" width="67" height="38" />
    </c:set>
    <c:set var="image">
        <tg:renditionFromImageAsset name="fileReference" width="1024" height="768" />
    </c:set>
    <c:set var="imgDescription" value="${properties.imgDescription}" />
        <c:set var="imgAlt" value="${properties.imagealt}" />

    <c:if test="${!previewMode}">
<div class="gallery-item" data-path="<%=currentNode.getPath()%>">
        </c:if>
        <a data-src="${image}" href="#"  title="${fn:replace(imgDescription, "\"", "&quot;")}" data-description="${fn:replace(imgDescription, "\"", "&quot;")}">
		<c:choose>
  		  	<c:when test="${empty smallImage && !previewMode}">
				<img class="mainimage" style="width: 61px; height: 32px;" src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="">
            </c:when>
            <c:otherwise>
                <img src="${smallImage}" alt="${fn:replace(imgAlt, "\"", "&quot;")}" />
			</c:otherwise>
		</c:choose>

            <span class="border"></span>
        </a>
    <c:if test="${!previewMode}">

</div>
<div style="clear:both;"></div>
        </c:if>

</c:if>