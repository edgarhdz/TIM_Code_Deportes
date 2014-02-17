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

  Form 'start' component

  Draws the start of a form

--%><%@include file="/libs/foundation/global.jsp"%>
<%
%><%@ page session="false" import="com.day.cq.wcm.foundation.forms.ValidationInfo,
                 com.day.cq.wcm.foundation.forms.FormsConstants,
                 com.day.cq.wcm.foundation.forms.FormsHelper,
                 org.apache.sling.api.resource.Resource,
                 org.apache.sling.api.resource.ResourceUtil,
                 org.apache.sling.api.resource.ValueMap,
                 org.apache.sling.scripting.jsp.util.JspSlingHttpServletResponseWrapper"%><%
%><cq:setContentBundle/>
<%
    String mode= properties.get("mode", "createContent");
    if("viewContent".equals(mode)){
        FormsHelper.setFormReadOnly(slingRequest);
    }else{
        slingRequest.removeAttribute(FormsHelper.REQ_ATTR_READ_ONLY);
    }

%>
<cq:include script="abacus.jsp"/><%
    FormsHelper.startForm(slingRequest, new JspSlingHttpServletResponseWrapper(pageContext));

    // we create the form div our selfs, and turn decoration on again.
    %><div class="form"><%
    componentContext.setDecorate(true);
    // check if we have validation erros
    final ValidationInfo info = ValidationInfo.getValidationInfo(request);
    if ( info != null ) {
        %><p class="form_error"><fmt:message key="Please correct the errors and send your information again."/></p><%
        final String[] msgs = info.getErrorMessages(null);
        if ( msgs != null ) {
            for(int i=0;i<msgs.length;i++) {
                %><p class="form_error"><%=msgs[i]%></p><%
            }
        }
    }
%>
