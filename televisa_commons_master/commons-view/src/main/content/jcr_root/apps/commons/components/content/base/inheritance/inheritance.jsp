<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  
 *  
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 7/1/2013    | Bryan Gerhard Chávez   | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>  
<%@include file="/apps/deportes/local/components/global.jsp"%>

<%@ page import="com.day.cq.wcm.api.components.IncludeOptions" %>

<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@page session="false" %>

<fmt:message key="commons.inheritance.empty.label" var="emptyResource"/>

<%	
	String path = properties.get("resourcePath",null);
    Resource newResource = resourceResolver.getResource(path);
	pageContext.setAttribute("newResource", newResource);
%>

<c:choose>
    <c:when test="${not empty newResource}">
        <%
        IncludeOptions.getOptions(request, true).forceSameContext(true);
		%>
		<cq:include path="<%= newResource.getPath() %>" resourceType="<%= newResource.getResourceType() %>"/>
	</c:when>
    <c:otherwise>
        <div style="min-height:40px;margin-top:15px">
        	${emptyResource}
        </div>
    </c:otherwise>
</c:choose>
