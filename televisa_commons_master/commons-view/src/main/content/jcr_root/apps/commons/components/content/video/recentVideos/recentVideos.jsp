<%--
/**
 *  DESCRIPTION
 * -----------------------------------------------------------------------------
 *  
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date           | Developer          	| Changes
 * 1.0     | 25/11/2013     | Bryan Gerhard Chávez	| Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%><%
%><%@include file="/apps/commons/components/global.jsp"%>

<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>

<c:choose>
    <c:when test="${properties.inheritanceType == 'inheritance'}">
        <cq:include script="inheritance.jsp" />
    </c:when>
    <c:otherwise> 
	<cq:include script="normal.jsp" />
    </c:otherwise>
</c:choose>
