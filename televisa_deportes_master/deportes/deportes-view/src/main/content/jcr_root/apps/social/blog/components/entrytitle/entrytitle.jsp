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

  Title component.

  Draws a title either store on the resource or from the page

--%><%@include file="/libs/foundation/global.jsp"%><%
%><%@ page session="false" import="com.adobe.cq.social.blog.Blog,
                   com.adobe.cq.social.blog.BlogEntry,
                   com.day.cq.i18n.I18n,
                   java.util.ResourceBundle,
				   java.util.Date,
					java.text.DateFormat,
					java.text.SimpleDateFormat" %>
<%
    ResourceBundle bundle = slingRequest.getResourceBundle(currentPage.getLanguage(true));
    BlogEntry entry = resource.adaptTo(BlogEntry.class);
	DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	Date date=entry.getDate();
	String blogAuthor=entry.getAuthor();
	pageContext.setAttribute("date",dateFormat.format(date));
	pageContext.setAttribute("blogAuthor", blogAuthor);
    %><%
	%>
<!-- BEGIN:art_title_01 -->
<br>
<div class="art_title_01">
    <div class="art_title_01_title">
        <h2>
            <a href="#" title="Link description">
                <span class="art_info">${blogAuthor}</span>
                <span class="art_info">Televisa Fuente: EFE</span>
                <span class="art_info">${date}</span>
            </a>
        </h2>
        <h1>
            <a href="#" title="Link description">
                <p><%= xssAPI.filterHTML(entry.getTitle()) %></p>
            </a>
        </h1>
    </div>
</div>
<!-- END:art_title_01-->
	
