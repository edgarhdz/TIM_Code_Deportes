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
<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" %><%
%>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>
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
<div class="wdg_sched_02">
    <!-- BEGIN: str_pleca_01 -->
    	<cq:include path="str_pleca" resourceType="/apps/commons/components/content/structure/singleTitleBox" />
    <!-- END: str_pleca_01 -->
    <ul id="deg" <c:if test="${authorMode}"> style="width:300px"</c:if>>
    	<cq:include path="itemContainer" resourceType="foundation/components/parsys"/>
    </ul>
    <div class="nav"> <a class="prev bginactive" title="Link Description" href="http://www.televisa.com"><span class="tvsa-caret-up dropdown-white"></span></a>
        <a class="next bgactive" title="Link Description" href="http://www.televisa.com"><span class="tvsa-caret-down dropdown-white"></span></a>
        	<a class="full-timetable" href="${link}" ${target} ${title}>
					<span><c:out value="${properties.linkText}" default="Ver Todos"/> </span>
			</a> 
    </div>
    <div class="degraded"></div>
    <div class="seemore">
        <a href="#">Ver todos</a>
    </div>
</div>
 <!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>