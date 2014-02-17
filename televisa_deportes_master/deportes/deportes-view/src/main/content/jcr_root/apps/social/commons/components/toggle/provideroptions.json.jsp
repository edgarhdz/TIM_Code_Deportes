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

  Description....

--%><%@ page session="false" import="com.day.cq.commons.jcr.JcrConstants,
                                     org.apache.sling.api.resource.ResourceUtil,
                                     org.apache.sling.commons.json.JSONException,
                                     org.apache.sling.commons.json.io.JSONWriter,
                                     javax.jcr.query.Query,
                                     java.util.Iterator" %><%
%><%@include file="/libs/foundation/global.jsp"%><%
%><%
    response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");

    final StringBuilder statement = new StringBuilder("//element(*, cq:Component)");
    statement.append("[@sling:resourceSuperType='social/commons/components/toggle/stateproviders/base']");

    final Iterator<Resource> results = resourceResolver.findResources(statement.toString(), Query.XPATH);

    JSONWriter w = new JSONWriter(response.getWriter());

    try {
        w.array();

        while (results.hasNext()) {
            final Resource r = results.next();
            final ValueMap props = ResourceUtil.getValueMap(r);

            w.object();
            w.key("text").value(props.get(JcrConstants.JCR_TITLE, r.getName()));
            w.key("value").value(r.getPath());
            w.endObject();
        }

        w.endArray();

    } catch (JSONException e) {
        log.error("error while listing toggle state providers: ", e);
    }
%>
