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

  Form 'action' component

  Return the action path for the new comment form handling

--%><%@page session="false" %><%
%><%@include file="/libs/foundation/global.jsp" %>
<%@page import="com.day.cq.wcm.foundation.forms.FormsHelper,
                com.day.cq.wcm.foundation.forms.FormResourceEdit,
                org.apache.sling.api.resource.ResourceUtil,
                org.apache.sling.api.resource.ValueMap,
                org.apache.commons.lang.StringUtils"%><%
%>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %><%
%><%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %><%
%><cq:defineObjects/><sling:defineObjects/><%

    final String PAGE_SUFFIX = ".html";
    final ValueMap props = ResourceUtil.getValueMap(resource);
    String type= props.get("contentType", "");
    String editSelector= props.get("endpoint", "");
    String redirectUrl = props.get("redirect", "");
    if(StringUtils.isNotBlank(redirectUrl) && !StringUtils.endsWith(redirectUrl, PAGE_SUFFIX)){
        redirectUrl = redirectUrl + PAGE_SUFFIX;
    }

    String path = FormResourceEdit.getPostResourcePath(slingRequest);
    if(StringUtils.isNotBlank(editSelector)){
        path = path+"."+editSelector;
    }

    if (FormResourceEdit.isSingleResourcePost(slingRequest)) {
        // simply use form forwarding here
        FormsHelper.setForwardPath(slingRequest, path+".html");
        FormsHelper.setForwardRedirect(slingRequest, redirectUrl);
    }

%>
