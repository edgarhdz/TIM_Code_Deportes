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

  Feed link component

  Draws a feed link. Optionally, it can display a text along with a feed icon

--%><%@ page session="false" import="com.day.cq.wcm.api.WCMMode" %><%
%><%@include file="/libs/foundation/global.jsp"%><%

    String text = properties.get("text", "");
    boolean icon = properties.get("icon", false);
    String format = properties.get("format", "atom");
    String type = format.equals("atom") ? "application/atom+xml" : "application/rss+xml";
    String title = format.equals("atom") ? "Atom 1.0" : "RSS 2.0";
    String suffix = format.equals("atom") ? ".feed" : ".feed.rss.xml";
    String link = currentPage.getPath() + suffix;
    String iconCls = icon ? " feedlink-icon" : "";

    if (text.length() > 0 || icon) {
        %><a class="feedlink-text<%= xssAPI.encodeForHTMLAttr(iconCls) %>" <%
            %>href="<%= xssAPI.getValidHref(link) %>" <%
            %>title="<%= xssAPI.encodeForHTMLAttr(title) %>" <%
        %>><%
            if (text.length() > 0) {
                %><%= xssAPI.filterHTML(text) %><%
            }
        %></a><%
    } else if (WCMMode.fromRequest(request) == WCMMode.EDIT) {
        %><img src="/libs/cq/ui/resources/0.gif" class="cq-feedlink-placeholder" alt=""><%
    }

%><link rel="alternate" <%
    %>type="<%= xssAPI.encodeForHTMLAttr(type) %>" <%
    %>title="<%= xssAPI.encodeForHTMLAttr(title) %> (Page)" <%
    %>href="<%= xssAPI.getValidHref(link) %>" />
