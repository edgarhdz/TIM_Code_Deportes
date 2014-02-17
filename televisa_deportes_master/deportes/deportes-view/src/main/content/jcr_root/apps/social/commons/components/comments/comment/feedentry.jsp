<%--

 ADOBE CONFIDENTIAL
 __________________

  Copyright 2012 Adobe Systems Incorporated
  All Rights Reserved.

 NOTICE:  All information contained herein is, and remains
 the property of Adobe Systems Incorporated and its suppliers,
 if any.  The intellectual and technical concepts contained
 herein are proprietary to Adobe Systems Incorporated and its
 suppliers and are protected by trade secret or copyright law.
 Dissemination of this information or reproduction of this material
 is strictly forbidden unless prior written permission is obtained
 from Adobe Systems Incorporated.

  ==============================================================================

  Atom feed entry renderer for comment nodes

  Draws the current comment node as a feed entry.

--%><%@ page session="false" %><%
%><%@ page import="java.util.Date,
                   com.adobe.cq.social.commons.Comment,
                   com.day.cq.wcm.api.WCMMode"%><%
%><%@ taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %><%
%><%@ taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %><%
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%
%><%@ taglib prefix="atom" uri="http://sling.apache.org/taglibs/atom/1.0" %><%
%><%@ taglib prefix="media" uri="http://sling.apache.org/taglibs/mediarss/1.0" %><%
%><cq:defineObjects /><%

    try {
        WCMMode.DISABLED.toRequest(request);
        Comment comment = resource.adaptTo(Comment.class);
        String url = comment.getFullUrl(slingRequest);
        String author = comment.getAuthor().getName();
        Date date = comment.getDate();
        String content = comment.getMessage();
        %><atom:entry
                id="<%= url %>"
                updated="<%= date %>"
                published="<%= date %>"><%
            %><atom:title><%= author %></atom:title><%
            %><atom:link href="<%= url %>"/><%
            if (content != null) {
                %><atom:content><%= content %></atom:content><%
            }
        %></atom:entry><%

    } catch (Exception e) {
        log.error("error while rendering default feed entry", e);
    }
%>
