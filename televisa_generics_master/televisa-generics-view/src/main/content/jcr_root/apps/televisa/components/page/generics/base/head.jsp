<%--
  Copyright 1997-2010 Day Management AG
  Barfuesserplatz 6, 4001 Basel, Switzerland
  All Rights Reserved.

  This software is the confidential and proprietary information of
  Day Management AG, ("Confidential Information"). You shall not
  disclose such Confidential Information and shall use it only in
  accordance with the terms of the license agreement you entered into
  with Day.

  ==============================================================================

  Default head script.

  Draws the HTML head with some default content:
  - includes the WCML init script
  - includes the head libs script
  - includes the favicons
  - sets the HTML title
  - sets some meta data

  ==============================================================================

--%>
<%@include file="/apps/televisa/components/global.jsp"%>
<%@ page import="com.day.cq.commons.Doctype,
                   org.apache.commons.lang3.StringEscapeUtils" %><%
    String xs = Doctype.isXHTML(request) ? "/" : "";
    String favIcon = currentDesign.getPath() + "/favicon.ico";
    if (resourceResolver.getResource(favIcon) == null) {
        favIcon = null;
    }
%><head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"<%=xs%>>
    <meta http-equiv="keywords" content="<%= StringEscapeUtils.escapeHtml4(WCMUtils.getKeywords(currentPage, false)) %>"<%=xs%>>
    <meta http-equiv="description" content="<%= StringEscapeUtils.escapeHtml4(properties.get("jcr:description", "")) %>"<%=xs%>>
    <meta name="author" content="">

    
    <cq:include script="/libs/wcm/core/components/init/init.jsp"/>
    <cq:include script="headlibs.jsp"/>
    <cq:include script="stats.jsp"/>
    
    <title><%= currentPage.getTitle() %></title>
</head>