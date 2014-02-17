<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Link component.
 *  This component will be used as a base for all the links. Its dialog will be
 *  reused in every component that uses a link.
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 22/01/2013  | Pablo Alecio           | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%><%
%><%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%><%
%><%@page session="false" %>

<fmt:message key="commons.link.text" var="textMessage"/>

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
    <c:set var="link" value="#"/>
</c:if>    

<c:if test="${not empty properties.linkTarget && properties.linkTarget != 'select'}">
    <c:set var="target" value="target= '${properties.linkTarget}'"/>
</c:if>

<c:if test="${not empty properties.linkTitle}">
    <c:set var="title" value="title= '${properties.linkTitle}'" />
</c:if>

<c:set var="text" value="${properties.linkText}"/>
<c:if test="${empty text}">
    <c:set var="text" value="${textMessage}"/>
</c:if>

<a href="${link}" ${target} ${title}> ${text} </a>