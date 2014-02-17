<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Feed Generator Template
 *
 *  This component generates the feed from a parsys components.
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
    IncludeOptions.getOptions(request, true).forceSameContext(Boolean.TRUE);
    WCMMode.PREVIEW.toRequest(request);
%>

<sling:include resourceType="/apps/commons/components/content/mix/primaryFourShortsTeaserContainer" path="mix_4arts_01_1" />
<sling:include resourceType="/apps/commons/components/content/mix/primaryFourShortsTeaserContainer" path="mix_4arts_01_2" />
