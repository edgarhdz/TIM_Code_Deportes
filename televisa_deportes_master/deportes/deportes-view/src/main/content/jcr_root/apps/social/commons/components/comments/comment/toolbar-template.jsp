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

--%>
<%@ page session="false" import="com.adobe.cq.social.commons.Comment,
                     com.adobe.cq.social.commons.CommentSystem,
                     com.adobe.cq.social.commons.CollabUtil"%>
<%@include file="/libs/social/commons/commons.jsp" %>
<%

    final Comment comment = resource.adaptTo(Comment.class);
    final CommentSystem cs = comment.getCommentSystem();
    final Boolean userMayEdit = CollabUtil.mayEdit(resourceResolver, loggedInUserID, comment);
    final Boolean allowEditComments = cs.allowsEdit() && userMayEdit;
    final Boolean allowDeleteComments = cs.allowsDelete() && userMayEdit;
    final String editFormLocation = cs.getProperty("editform", "/etc/forms/social/comments/editcomment");

%>

<ul class="comment-toolbar">
<%if(allowEditComments) {
    final String editURL =  resource.getPath() +".form.html" + editFormLocation;
%><li>
    <a href="<%= editURL %>"><%= i18n.get("Edit") %></a>
</li><%
}%>

<%if(allowDeleteComments) {
    final String deleteURL = resource.getPath() + ".social.deletecomment.html";
%><li>
    <form method="post" action="<%=xssAPI.getValidHref(deleteURL)%>" onclick="event.preventDefault();$CQ(event.target).trigger(CQ.soco.comments.events.DELETE, '<%=deleteURL%>')"><input type="submit" value="<%= i18n.get("Delete") %>"></input></form>
</li><%
}%>
</ul>
