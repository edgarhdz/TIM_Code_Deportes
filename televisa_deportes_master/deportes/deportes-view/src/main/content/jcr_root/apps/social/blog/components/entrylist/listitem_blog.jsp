<%--

 ADOBE CONFIDENTIAL
 __________________

  Copyright 2011 Adobe Systems Incorporated
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

  List component sub-script

  Draws a list item as a teaser.

  request attributes:
  - {com.day.cq.wcm.foundation.List} list The list
  - {com.day.cq.wcm.api.Page} listitem The list item as a page

--%><%
%><%@ page session="false" import="com.adobe.cq.social.blog.Blog,
                   com.adobe.cq.social.blog.BlogEntry,
                   com.adobe.cq.social.blog.BlogManager,
                   com.day.cq.commons.date.DateUtil,
                   com.day.cq.i18n.I18n,
                   com.day.cq.tagging.Tag,
                   com.day.cq.wcm.api.components.IncludeOptions,
                   com.day.cq.wcm.api.WCMMode,
                   com.day.cq.wcm.foundation.Paragraph,
                   com.day.cq.wcm.foundation.ParagraphSystem,
                   java.text.DateFormat,
                   java.util.Locale,
                   java.util.ResourceBundle" %>
<%@include file="/libs/social/commons/commons.jsp"%><%

    BlogManager blogMgr = resource.getResourceResolver().adaptTo(BlogManager.class);
    BlogEntry entry = blogMgr.getBlogEntry(slingRequest, ((Page)request.getAttribute("listitem")).getPath());
    String title = entry.getTitle();
    String text = entry.getText();
    ParagraphSystem parsys = entry.getContentResource() != null ?
            new ParagraphSystem(entry.getContentResource()) : null;

    DateFormat dateFormat = DateUtil.getDateFormat(properties.get("dateFormat", String.class), DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, pageLocale), pageLocale);
    String date = entry.getDate() != null ? dateFormat.format(entry.getDate()) : "";

    String author = entry.getAuthor();
    String url = entry.getUrl();
    Tag[] tags = entry.getTags();

    int comments = entry.countComments();
%> 
    <li>
    	<div class="titulos">
			<h2>Sociedad</h2>
			<h3>
				<a href="<%= xssAPI.getValidHref(url) %>" <%
            %>rel="bookmark" <%
            %>title="<%= xssAPI.encodeForHTMLAttr(i18n.get("Permanent link to '{0}'", null, title)) %>" <%
            %>onclick="blogSearchTrackClick('<%= xssAPI.encodeForJSString(title) %>')"<%
        %>  class="textcolor-title6"><%= date %><%
        if (!Blog.ANONYMOUS.equals(author)) {
        %> <%
        }
    %></a>

			</h3>
				<p><%= xssAPI.filterHTML(title) %></p>
				<span class="icon"></span>
		</div>
		<div class="info-note">
			<%
            if (parsys == null) {
        %><3%= xssAPI.filterHTML(text) %><%
            } else {
                for (Paragraph par : parsys.paragraphs()) {
                    if (par.getResourceType().endsWith("break")) {
                        %><a href="<%= xssAPI.getValidHref(url) %>" <%
                            %>title="<%= xssAPI.encodeForHTMLAttr(entry.getTitle()) %>" <%
                            %>onclick="blogSearchTrackClick('<%= xssAPI.encodeForJSString(entry.getTitle()) %>')"<%
                        %>><%= i18n.get("Read More", "Name of the link to jump to the full blog entry page") %> &raquo;</a><%
                        break;
                    }
                    IncludeOptions.getOptions(request, true).forceSameContext(true);
                    %><sling:include resource="<%= par %>"/><%
                }
            }
        %>
      	</div>
    </li>

