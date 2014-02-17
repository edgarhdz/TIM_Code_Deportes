<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Reference component.
 *  This component is based of the libs/foundation/components/reference.

 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer               | Changes
 * 1.0     | 30/08/2013  | jbarrera                | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="deportes.reference.error.placeholder" var="placeholdererror"/>
<fmt:message key="deportes.component.message.placeholder" var="placeholderinfo"/>

<%@page import="com.day.cq.wcm.api.WCMMode,
    com.day.cq.wcm.api.components.DropTarget" %>

<%
    WCMMode mode = WCMMode.DISABLED.toRequest(request);
    try {
        // Use request attributes to guard against reference loops
        String path = resource.getPath();
        String key = "com.day.cq.wcm.components.reference:" + path;
        if (request.getAttribute(key) == null) {
            request.setAttribute(key, Boolean.TRUE);
        } else {
            throw new IllegalStateException("Reference loop: " + path);
        }
        //drop target css class = dd prefix + name of the drop target in the edit config
        String ddClassName = DropTarget.CSS_CLASS_PREFIX + "paragraph";

        // Include the target paragraph by path
        String target = "target";
        if (target != null) {
%>
<div id="referenceEvent" style="display:inline;" class="<%= ddClassName %>"> </div>
<%
} else if (mode == WCMMode.EDIT) {
%>
<p><img src="/libs/cq/ui/resources/0.gif" class="cq-reference-placeholder <%= ddClassName %>" alt=""></p>
<%
    }
} catch (Exception e) {
    // Log errors always
    log.error("Reference component error", e);

    // Display errors only in edit mode
    if (mode == WCMMode.EDIT) {
%>
<p>${placeholdererror} <%= xssAPI.encodeForHTML(e.toString()) %></p>
<%
        }
    } finally {
        mode.toRequest(request);
    }
%>

<div id="info" style="display:none"> ${placeholderinfo} </div>