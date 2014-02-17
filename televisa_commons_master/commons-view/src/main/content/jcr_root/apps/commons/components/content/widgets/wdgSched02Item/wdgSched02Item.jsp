<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 * Schedule widget 2 Component
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 17/07/2013  | Otto Giron             | Initial Creation.
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

<c:if test="${not empty properties.linkTarget && properties.linkTarget != 'select'}">
	<c:set var="target" value="target='${properties.linkTarget}'" />
</c:if>

<c:if test="${not empty properties.linkTitle}">
	<c:set var="title" value="title='${properties.linkTitle}'" />
</c:if>
<c:set var="text" value="${properties.linkText}" />
<c:if test="${empty text}">
	<c:set var="text" value="${textMessage}" />
</c:if>
<li class="color">
	<div>
        <time itemprop="startDate" datetime="2013-10-26T20:00-08:00"><c:out value="${properties.date}" default="26. Sep"/><span class="hour"><c:out value="${properties.time}" default="01:00"/></span></time>
	</div> 
    <div class="separator"></div>
    <h3>
        <a itemprop="url" ${title} href="${link}" ${target}>
            <span class="program" itemprop="summary"><c:out value="${properties.program}" default="Programa 1"/></span> 
            <span class="channel"><c:out value="${properties.chanel}" default="Canal 1"/></span>
        </a>
    </h3>
</a>
</li>
