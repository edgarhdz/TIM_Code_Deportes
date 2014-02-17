<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Script for Digital Analytics (end of Body tag).
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.1     | 02/04/2013  | lorozco@xumak.com      | added Google Analytics code
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%><%
%>
<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@page import="com.day.cq.wcm.api.WCMMode" pageEncoding="utf-8" %><%
%><%
     String name = (String)request.getAttribute("DAXName");

    if( name != null ) {
        String ns_site = (String)request.getAttribute("ns_site");
%>

<!-- Begin DAx cs.js import -->

<script type="text/javascript" src="http://b.scorecardresearch.com/c2/6035759/cs.js"></script>

<!-- End DAx cs.js import -->

<!-- Begin, Google Analytics -->

<script language="javascript" type="text/javascript">doStats('<%= ns_site %>');</script>

<!-- End, Google Analytics -->

<% } %>