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

  Blog: Categories component

  ==============================================================================

--%><%@ page session="false" import="com.adobe.cq.social.blog.Blog,
                     com.adobe.cq.social.blog.BlogManager,
                     com.day.cq.i18n.I18n,
                     com.day.cq.tagging.Tag,
                     java.util.ResourceBundle" %>
<%
%><%@include file="/libs/foundation/global.jsp" %><%

    BlogManager blogMgr = resource.getResourceResolver().adaptTo(BlogManager.class);
    Blog blog = blogMgr.getBlog(slingRequest);
    ResourceBundle resourceBundle = slingRequest.getResourceBundle(currentPage.getLanguage(true));

    %>

<script>
$(document).ready(function() {
		var ulObject=$('.wdg_categories_01_content').find('ul');
		ulObject.addClass('wdg_categories_01_container');
    //$('.wdg_categories_01_content').attr('class', 'blogmain');
    });

</script>


<!-- BEGIN: wdg_categories_01 -->
<div class="wdg_categories_01">
    <!-- BEGIN: str_pleca_01 -->
    <cq:include path="header" resourceType="/apps/deportes/generics/components/content/structure/strPleca01"  />

    <!-- END: str_pleca_01 -->
    <div class="wdg_categories_01_content">
        <%
        Tag[] tags = blog.getTags();
        if (tags.length > 0) {
            %><%= xssAPI.filterHTML(blog.getTagsAsHTML(resourceBundle)) %><%
        }
        %>
        <div class="wdg_categories_01_hidecontent">
            <a href="#" id="verTodo"><p>Ver Todo<span class="sprite dropdown-gray"></span></p></a>
            <a href="#" class="ocultar" id="ocultar"><p>Ocultar<span class="sprite dropdown-gray up"></span></p></a>
        </div>
    </div>
</div>

