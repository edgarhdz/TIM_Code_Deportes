<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Bookmark Navigation Component
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 01/07/2013  | Otto Giron             | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%>
<%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%
%>
<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>
<!-- BEGIN: wdg_humor_01 -->
<div class="wdg_humor_01">
	<!-- BEGIN: str_pleca_01 -->
	<cq:include path="header"
		resourceType="/apps/commons/components/content/structure/singleTitleBox" />
	<!-- END: str_pleca_01 -->
	<div>
		<article class="articleMedium">
			<fmt:message key="commons.link.text" var="textMessage" />
			<c:choose>
				<c:when test="${properties.linkType == 'internal'}">
					<c:if test="${not empty properties.internalLink}">
						<c:set var="link" value="${properties.internalLink}.html" />
					</c:if>
				</c:when>
				<c:otherwise>
					<c:set var="link" value="${properties.externalLink}" />
				</c:otherwise>
			</c:choose>
			<c:if test="${empty link}">
				<c:set var="link" value="#" />
			</c:if>

			<c:if
				test="${not empty properties.linkTarget && properties.linkTarget != 'select'}">
				<c:set var="target" value="target= '${properties.linkTarget}'" />
			</c:if>

			<c:if test="${not empty properties.linkTitle}">
				<c:set var="title" value="title= '${properties.linkTitle}'" />
			</c:if>
			<c:set var="text" value="${properties.linkText}" />
			<c:if test="${empty text}">
				<c:set var="text" value="${textMessage}" />
			</c:if>
			<a href="${link}" ${target} ${title}>
				<img alt="${properties.description}" src="${properties.fileReference }" width="300" height="200" />			
			</a> 
			<div>
				<h3>
					${properties.description} <span class="autor textcolor-title4">
						${properties.reference}</span>
				</h3>
			</div>
		</article>
	</div>
</div>
<!-- END: wdg_humor_01 -->
<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>
