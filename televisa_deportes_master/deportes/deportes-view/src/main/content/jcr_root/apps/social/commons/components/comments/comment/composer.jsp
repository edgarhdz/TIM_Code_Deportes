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
<%--
This makes the composer for a comment available via a URL.  Enables the UI to "GET" the composer on demand,
 for nested replies.
 --%>
<%@page session="false" %><%@include file="/libs/foundation/global.jsp"%>
<sling:include resourceType="social/commons/components/composer" replaceSelectors="simple-template"/>
