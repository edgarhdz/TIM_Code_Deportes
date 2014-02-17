<%--
 * DESCRIPTION
 *  Header component for Newsletter Template
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 27/08/2013  | Antonio Oliva          | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%><%
%><%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%><%
%>

<%-- logo starts --%>
<c:choose>
    <c:when test="${properties.logoLinkType == 'internal'}">
        <c:if test="${not empty properties.logoInternalLink}">
            <c:set var="logolink" value="${properties.logoInternalLink}.html" />
        </c:if>
    </c:when>
    <c:otherwise>
        <c:set var="logolink" value="${properties.logoExternalLink}" />
    </c:otherwise>
</c:choose>
<c:if test="${empty logolink}">
    <c:set var="logolink" value="#"/>
</c:if>
<c:if test="${not empty properties.logoLinkTarget && properties.logoLinkTarget != 'select'}">
    <c:set var="logotarget" value="target= '${properties.logoLinkTarget}'"/>
</c:if>
<c:if test="${not empty properties.logoLinkTitle}">
    <c:set var="logotitle" value="title= '${properties.logoLinkTitle}'" />
</c:if>
<c:set var="logotext" value="${properties.logoLinkText}"/>
<c:if test="${empty logotext}">
    <c:set var="logotext" value=""/>
</c:if>
<%-- logo ends --%>

<%-- Date Retrieval --%>
<c:set var="date" value="${properties.date}"/>
<c:if test="${empty date}">
    <c:set var="date" value="-"/>
</c:if>
<%-- End Date Retrieval --%>

<!-- BEGIN: nav_header_newsletter_01 -->
<header class="nav_header_newsletter_01">
    <div class="nav_header_newsletter_01_section nav_header_newsletter_01_news">
    	<div class="nav_header_newsletter_01_content">
	    	<div class="nav_header_newsletter_01_top">
	        	<a href="${logolink}" target="${logotarget}" title="${logotext}" class="nav_header_newsletter_01_logotv">
	            	<span class="nav_header_newsletter_01_sprite nav_header_newsletter_01_logotv"></span>
	        	</a>
	        	<h2><a href="#">${properties.mainTitle}<span></span></a></h2>
	        	<span class="nav_header_newsletter_01_date">${date}</span>
	    	</div>
    	</div>
    </div>
</header>
<!-- END: nav_header_newsletter_01 -->