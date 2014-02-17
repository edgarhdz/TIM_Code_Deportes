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
<%@ page import="com.televisa.commons.services.query.impl.QueryBuilderManagerImpl" %>
<%@ page import="com.day.cq.search.QueryBuilder" %>
<%@ page import="javax.jcr.Session" %>
<%@page import="org.apache.sling.api.SlingHttpServletRequest" %>
<%@page import="com.televisa.commons.services.utilities.Base64Encoding" %>

<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@page session="false" %>

<fmt:message key="commons.inheritance.empty.label" var="emptyResource"/>

<%	
    String[] selectors = ((SlingHttpServletRequest)request).getRequestPathInfo().getSelectors();

    QueryBuilderManagerImpl queryBuilderManager = new QueryBuilderManagerImpl(resourceResolver.adaptTo(QueryBuilder.class),resourceResolver.adaptTo(Session.class));
    String resourcePath = queryBuilderManager.createInheritanceResource(Base64Encoding.decoding(selectors[0]),Base64Encoding.decoding(selectors[1]));
    Resource newResource = resourceResolver.getResource(resourcePath);
    
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
