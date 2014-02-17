
<%@ page session="false" import="java.text.DateFormat,
                     com.day.cq.commons.Externalizer,
                     com.day.cq.i18n.I18n,
                     com.adobe.cq.social.commons.Comment,
                     com.adobe.cq.social.commons.CommentSystem,
                     com.adobe.cq.social.commons.CollabUser,
                     com.day.cq.commons.date.DateUtil,
                     java.util.Locale,
                     com.adobe.cq.social.commons.CollabUtil,
                     javax.jcr.Session,
                     javax.jcr.Node,
                     org.apache.sling.api.resource.Resource,
                     org.apache.sling.api.scripting.SlingScriptHelper,
					 com.day.cq.wcm.api.WCMMode" %>
<%@include file="/libs/social/commons/commons.jsp"%><%


    SlingScriptHelper scriptHelper = bindings.getSling();

    final Comment comment = resource.adaptTo(Comment.class);
    final CommentSystem cs = comment.getCommentSystem();


    String id = comment.getId();

    CollabUser author = comment.getAuthor();

    //resolve name
    String authorName = resourceAuthorName;
    String authorImg = resourceAuthorAvatar;

    //resolve date
    DateFormat dateFormat = DateUtil.getDateFormat(cs.getProperty("dateFormat", String.class), DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, pageLocale), pageLocale);
    String date = dateFormat.format(comment.getDate());


    String message = comment.getMessage();
    // #26898 - line breaks in comments
    // xss protection will remove line breaks before we can format them
    // for the output, so it is done here a a workaround:
    // first remove the CR, then replace the LF with <BR>
    message = message.replaceAll("\\r", "");
    message = message.replaceAll("\\n", "<br>");

%>



            <div class="wdg_comen_01_img">

        		<img src="http://lorempixel.com/50/50" alt="<%= xssAPI.encodeForHTMLAttr(authorName) %>">

            </div>
            <div class="wdg_comen_01_comment">
                <p><span class="media_user"><%= xssAPI.encodeForHTML(authorName) %></span><span class="comment_time"> Escribio el <%= xssAPI.encodeForHTML(date) %></span></p>
                <p><%= xssAPI.filterHTML(message) %></p>
            </div>

        