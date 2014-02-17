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

  Form 'element' component

  Initialize this field.

--%><%@include file="/libs/foundation/global.jsp"%><%
%><%@page session="false" import="com.day.cq.wcm.foundation.forms.FieldDescription,
                  com.day.cq.wcm.foundation.forms.FieldHelper"%><%
    final FieldDescription desc = FieldHelper.createDefaultDescription(slingRequest, resource);
    desc.setMultiValue(properties.get("multivalue", false));
%>
