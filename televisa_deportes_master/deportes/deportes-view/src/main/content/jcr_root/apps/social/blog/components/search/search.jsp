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

  Blog: Search component

  ==============================================================================

--%><%@ page session="false" import="com.adobe.cq.social.blog.Blog,
                     com.adobe.cq.social.blog.BlogManager,
					 com.day.cq.commons.date.DateUtil,
					 java.util.Locale,
                     java.util.ResourceBundle, java.text.DateFormat,
                     com.day.cq.i18n.I18n" %>
<%
%><%@include file="/libs/foundation/global.jsp" %><%

    BlogManager blogMgr = resource.getResourceResolver().adaptTo(BlogManager.class);
    Blog blog = blogMgr.getBlog(slingRequest);

    I18n i18n = new I18n(slingRequest.getResourceBundle(currentPage.getLanguage(true)));

	Locale pageLocale = currentPage.getLanguage(true);
    ResourceBundle resourceBundle = slingRequest.getResourceBundle(pageLocale);
    DateFormat dateFormat = DateUtil.getDateFormat(properties.get("dateFormat", String.class), "yyyy MMMMM", pageLocale);

%>


<script>
$(document).ready(function() {
		var ulObject=$('.wdg_otherpost_01_listcontainer').find('ul');
		ulObject.addClass('wdg_otherpost_01_dropdownlist');
    	ulObject.attr("style", "");

    });

</script>

<!-- *********************** -->

<!-- BEGIN: wdg_otherpost_01 -->
<div class="wdg_otherpost_01">
    <!-- BEGIN: str_pleca_01 -->
   <cq:include path="pleca1" resourceType="/apps/deportes/generics/components/content/structure/strPleca01"  />
    <!-- END: str_pleca_01 -->
    <div class="wdg_otherpost_01_content">
        <div class="wdg_otherpost_01_show">
            <form id="searchform" name="searchform" method="get" action="<%= xssAPI.getValidHref(blog.getUrl()) %>">

            	<input type="text" id="livesearch" placeholder="<%= i18n.get("deportes.wdgotherpost01.form.search.placeholder") %>" name="<%= Blog.PARAM_QUERY %>" value="<%= i18n.get("tema") %>" onblur="this.value=(this.value=='') ? '<%= i18n.get("search this site") %>' : this.value;" onfocus="this.value=(this.value=='<%= i18n.get("search this site") %>') ? '' : this.value;" />
            	<input type="hidden" name="<%= Blog.PARAM_VIEW %>" value="<%= Blog.VIEW_SEARCH %>"/>
            	<input type="hidden" name="_charset_" value="<%= request.getCharacterEncoding() %>"/>

            		<input type="submit" id="searchsubmit"  value="Ir" class="wdg_otherpost_go background-color2" />

        	</form>
        </div>
        <div class="wdg_otherpost_01_dropdown">
            <div class="wdg_otherpost_01_dropdowncontent">
                <p></p>
                <span class="sprite dropdown-gray"></span>
            </div>
            <div class="wdg_otherpost_01_listcontainer">
                <%= xssAPI.filterHTML(blog.getArchiveAsHTML(0, dateFormat, resourceBundle)) %>

            </div>
        </div>
    </div>
</div>
<!-- END: wdg_otherpost_01 -->

