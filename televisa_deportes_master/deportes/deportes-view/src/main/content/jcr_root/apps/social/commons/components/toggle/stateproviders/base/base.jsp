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

  State Provider Base

--%><%@ page session="false" import="com.adobe.cq.social.commons.ToggleUtil,
                     com.adobe.cq.social.commons.UserStateToggleManager" %><%
%><%@include file="/libs/foundation/global.jsp"%><%

    final UserStateToggleManager manager = sling.getService(UserStateToggleManager.class);
    if (null != manager) {

        final boolean isTransitioning = manager.isTransitioning(component.getPath(), slingRequest);

        if (isTransitioning) {
            ToggleUtil.toRequest(ToggleUtil.STATE.TRANSITION, slingRequest);
            return;
        }
    }

%><cq:include script="state.jsp"/>
