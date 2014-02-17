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

  Comments component sub-script

  Draws a form for new comments.

--%><%@ page import="com.adobe.cq.social.commons.CollabUser,
                     com.adobe.cq.social.commons.Comment,
                     com.adobe.cq.social.commons.CommentSystem,
                     com.day.cq.i18n.I18n,
                     com.day.cq.wcm.api.WCMMode,
                     com.day.cq.wcm.foundation.forms.FormsHelper,
                     java.util.List,
                     java.util.Locale,
                     java.util.ResourceBundle,
                     org.apache.sling.api.resource.ResourceUtil" %><%
%><%@taglib uri="http://www.day.com/taglibs/cq/personalization/1.0" prefix="personalization" %><%
%><%@include file="/libs/social/commons/commons.jsp"%><%

    final String targetResourceType = resourceResolver.resolve(resource.getPath()).getResourceType();
    CommentSystem cs = resource.adaptTo(CommentSystem.class);
    // @Todo - find out how to do this without a request attribute.
    //boolean isRenderByForm  = request.getAttribute("isRenderByForm")==null ? false : ((Boolean)request.getAttribute("isRenderByForm")).booleanValue();
    final Boolean allowFileUploads = cs.getProperty("allowFileUploads", Boolean.class) != null ?
            cs.getProperty("allowFileUploads", Boolean.class) :false;

    Resource commentResource = resource;
    if (cs == null) {
        // form called directly
        final List<Resource> editResources = FormsHelper.getFormEditResources(slingRequest);
        if (null != editResources && editResources.size() > 0) {
            cs = editResources.get(0).adaptTo(CommentSystem.class);
            commentResource = (null != cs) ? cs.getResource() : commentResource;
        } else {
            cs = resource.adaptTo(CommentSystem.class);
        }
    }
    if (cs == null) {
        log.error("failed to retrieve comment system for " + resource.getPath());
        return;
    }

    String defaultMessage = cs.getProperty("defaultMessage", i18n.get("Type your comment here."));
    String id = cs.getId();

    if (request.getParameter("idPrefix") != null) {
        id = xssAPI.getValidJSToken(request.getParameter("idPrefix"), "XSS");
    }

// In nested replies, when there are muliple composer from the same comment system, the taglib:
// contextProfileHtmlInput fails to select the right thing because it only takes in an ID so,
// the page must give us how many composers are already on the page to make the ID unique.
    String composerCount = request.getParameter("composerCount");
    if (composerCount != null) {
        id += "_" + composerCount;
    }

//    String loginId = id + "-login";
    String formId = id + "-form";
    String textId = id +  "-text";
    String nameId = id + "-" + CollabUser.PROP_NAME;
    String mailId = id +"-" + CollabUser.PROP_EMAIL;
    String webId = id + "-" + CollabUser.PROP_WEBSITE;
    String formAction = commentResource.getPath() + ".social.createcomment.html";

/*     if(isRenderByForm){
        formAction = (String) request.getAttribute("commentsRoot") + ".createcomment.html";
    } */

    //ValueMap props = commentResource.adaptTo(ValueMap.class);
    //boolean requireLogin = props.get("requireLogin", Boolean.FALSE);
    boolean requireLogin = false;

//    boolean isDisabled = WCMMode.fromRequest(request).equals(WCMMode.DISABLED);
    String authorizableId = loggedInUserID;
    boolean anonymous = isAnonymous;

    String commentBlockClass = "comment-block-author";
    if(anonymous){
        commentBlockClass = "comment-block-publish";
    }
    if (allowFileUploads) {
%><script type="text/javascript">
    function cancelPostFileUpload() {
        var formID = "#"+ "formFileUploadDiv";
        var div = $CQ(formID).html();
        $CQ(formID).html($CQ(formID).html());
    }
</script><%
    }%>

<script type="text/javascript">
    function validateSubmitWrapper<%= formId.replaceAll("[^a-zA-Z0-9]","") %>(id) {
        var validationResult = CQ_collab_comments_validateSubmit(id);
        if (validationResult && CQ_Analytics.Sitecatalyst){
            CQ_Analytics.record({event: 'postComment',
                values: {commenterName: document.getElementById("<%= nameId %>").value},
                componentPath: '<%=resource.getResourceType()%>'
            });
        }
        return <% if (WCMMode.fromRequest(request).equals(WCMMode.DISABLED)) { %>validationResult<% } else { %>(CQ_Analytics.ClickstreamcloudUI.isVisible() ? false : validationResult)<% } %>;
    }
    function recordPostCommentEvent(name, user, component, category) {

        CQ_Analytics.record({event: 'postComment',
            values: {commenterName: user,
                topic: name,
                category: category
            },
            componentPath: component
        });
    }
</script>
<!-- ********************************** -->
<div class="wdg_comen_01">
    <div class="wdg_comen_01_form">

        <form
                class="comment"
                action="<%= formAction %>"
                onsubmit="return CQ.soco.comments.validateCommentForm(this)"
                method="POST"
                name="comment"
                enctype="multipart/form-data"
                id="<%= formId %>">
            <script type="text/javascript" charset="utf-8">
                CQ_collab_comments_defaultMessage = "<%= defaultMessage.replaceAll("\\\"", "\\\\\"") %>";
                CQ_collab_comments_requireLogin = <%= requireLogin %>;
                CQ_collab_comments_enterComment = "<%= i18n.get("Please enter a comment") %>";
                CQ_collab_comments_commentActivated = "<%= i18n.get("Comment activated") %>";
                CQ_collab_comments_unableToActivate = "<%= i18n.get("Unable to activate comment") %>";
            </script>
            <div class="comment-info">
                <span class="comment-error" id="<%= nameId %>-displayName-error"></span>
                <span class="wdg_comen_title"><%= i18n.get("Name", "Label for commenter's name") %></span>
                <personalization:contextProfileHtmlInput id="<%= nameId %>" clazz="comment-text" type="text" name="<%= CollabUser.PROP_NAME %>" propertyName="authorizableId"/>
                <br>
                <span class="comment-error" id="<%= mailId %>-email-error"></span>
                <span class="wdg_comen_title"><%= i18n.get("Email", "Label for commenter's email") %></span>
                <personalization:contextProfileHtmlInput id="<%= mailId %>" clazz="comment-text" type="text" name="<%= CollabUser.PROP_EMAIL %>" propertyName="email"/>
                <br>
                <div class="<%= commentBlockClass %>" id="<%= webId %>-comment-block" >
                    <span class="comment-error" id="<%= webId %>-url-error"></span>
                    <span class="wdg_comen_title"><%= i18n.get("Website (optional)", "Label for commenter's website") %></span>
                    <personalization:contextProfileHtmlInput id="<%= webId %>" clazz="comment-text" type="text" name="<%= CollabUser.PROP_WEBSITE %>" propertyName="url"/>
                </div>
                <span class="wdg_comen_title left">Comentario</span>
                <div class="wdg_comen_fb_tw"> <%= i18n.get("Comment") %>
                    <input type="radio" name="wdg_comen_radio" id="fb" value="1" class="wdg_comen_radio">
                    <i class="tvsa-facebook facebook"></i>
                    <input type="radio" name="wdg_comen_radio" id="tw" value="1" class="wdg_comen_radio">
                    <i class="tvsa-twitter twitter"></i>
                </div>
            </div>
            <textarea
                    name="<%= Comment.PROP_MESSAGE %>"
                    id="<%= textId %>"
                    class="comment-text"
                    onfocus="CQ.soco.commons.handleOnFocus(this, '<%= defaultMessage%>');"
                    onblur="CQ.soco.commons.handleOnBlur(this, '<%= defaultMessage%>');"
                    rows="10"
                    cols="10"><%= defaultMessage %></textarea>
            <div class="wdg_comen_aviso"> Aviso: Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.
            </div>
            <div class="wdg_comen_line submit-block">

                <input type="hidden" name="_charset_" value="<%= response.getCharacterEncoding() %>"/>
                <input class="submit wdg_comen_btn" type="submit" name="submit" id="<%= id %>-submit" value="<%= i18n.get("Post Comment", "Form submit action button") %>"
                       onclick="recordPostCommentEvent('<%= ResourceUtil.getValueMap(commentResource).get("jcr:title","")%>','<%= authorizableId %>','social/commons/components/composer' , 'forum')"/>
            </div>
        </form>
        <br>
        <br>
    </div>
</div>

<!-- ********************************** -->


<script type="text/javascript">
    $CQ(function(){
        CQ.soco.commons.fillInputFromClientContext($CQ("<%=formId%> #XSS-userIdentifier"), "<%= CollabUser.PROP_NAME %>");
    });
</script>