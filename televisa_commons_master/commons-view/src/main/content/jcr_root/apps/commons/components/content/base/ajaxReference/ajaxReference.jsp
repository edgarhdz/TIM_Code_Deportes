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
 * 1.0     | 05/03/2013  | Jose Alejandro Urizar   | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================

  Copyright 1997-2008 Day Management AG
  Barfuesserplatz 6, 4001 Basel, Switzerland
  All Rights Reserved.


  This software is the confidential and proprietary information of
  Day Management AG, ("Confidential Information"). You shall not
  disclose such Confidential Information and shall use it only in
  accordance with the terms of the license agreement you entered into
  with Day.


  ==============================================================================


  ajaxReference component.


--%>
<%@include file="/apps/commons/components/global.jsp"%>

<fmt:message key="noticieros.reference.error.placeholder" var="placeholdererror"/>

<%@page import="com.day.cq.wcm.api.WCMMode,
    com.day.cq.wcm.api.components.DropTarget" %>

<% 
    WCMMode mode = WCMMode.DISABLED.toRequest(request);
    try {
        // Use request attributes to guard against reference loops
        String path = resource.getPath();
        String key = "ajaxReference:" + path;
        if (request.getAttribute(key) == null) {
            request.setAttribute(key, Boolean.TRUE);
        } else {
            throw new IllegalStateException("ajaxReference loop: " + path );
        }
    
        //drop target css class = dd prefix + name of the drop target in the edit config
        String ddClassName = DropTarget.CSS_CLASS_PREFIX + "paragraph";
    
        // Include the target paragraph by path
        String target = currentStyle.get("path", String.class);
        long time = System.currentTimeMillis();
        
        Integer next = (Integer)request.getAttribute( "ajaxReferenceIndex" );
        if( next == null ) {
            next = new Integer( 0 );
        }
        
        next = new Integer( next.intValue() + 1 );
        
        request.setAttribute( "ajaxReferenceIndex", next );

        if (target != null) {
%>
            <div id="ajaxReference-<%= time %>-<%= next %>"></div>
            <script>
            $.get('<%= target %>.html', function(data) {
                  document.getElementById("ajaxReference-<%= time %>-<%= next %>").innerHTML = data;
                });
            </script>
            <div style="display:inline;" class="<%= ddClassName %>"></div>
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
