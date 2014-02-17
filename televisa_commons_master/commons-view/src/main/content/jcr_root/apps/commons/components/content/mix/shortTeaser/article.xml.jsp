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
 * 1.0     | 26/03/2013  | Gerardo Escobar        |  RSS 2 & JSON Feed Review
 * 1.0     | 28/02/2013  | Gerardo Escobar        | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/feedGlobal.jsp"%><%
%><%@page import="com.day.cq.wcm.api.components.IncludeOptions"%><%
%><%@page import="com.day.cq.wcm.api.WCMMode"%><%
%><%@page session="false" %><%
    response.setContentType("text/xml");
    IncludeOptions.getOptions(request, true).forceSameContext(Boolean.TRUE);
    WCMMode.PREVIEW.toRequest(request);
%>

<c:if test="${!empty properties.pathshortarticle}">
    <tg:namedNoteProvider name="note" path="${properties.pathshortarticle}" />
    <c:if test="${!empty note}">
        <tg:feedNoteListAdd name="notes" note="${note}" />
    </c:if>
</c:if>
