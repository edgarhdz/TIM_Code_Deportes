<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Feed Generator Component
 *
 *  This component generates the feed from a note.
 *
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 28/02/2013  | Gerardo Escobar        | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page import="com.day.cq.wcm.api.components.IncludeOptions"%><%
%><%@page import="com.day.cq.wcm.api.WCMMode"%><%
%><%@page session="false" %><%
    response.setContentType("text/plain");
    IncludeOptions.getOptions(request, true).forceSameContext(Boolean.TRUE);
	WCMMode.PREVIEW.toRequest(request);
%>

<c:if test="${!empty properties.patharticle}">
	<c:set var="path" value="${properties.pathProgram}" />
	<tg:namedNoteProvider name="note" path="${path}" />
	<c:if test="${!empty note}">
		<tg:feedProvider note="${note}" type="json" />
	</c:if>
</c:if>
