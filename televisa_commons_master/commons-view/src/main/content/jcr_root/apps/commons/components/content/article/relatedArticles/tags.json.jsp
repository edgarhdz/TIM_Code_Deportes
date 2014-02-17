<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Related Articles Component
 *  This script recovers all tags asigned to the page
 *  
 *  component id = art_reltd_01
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 14/02/2013  | Juan Jose Pol.         | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%>
<%@page import="com.day.cq.tagging.Tag"%>

<%
    response.setContentType("text/plain");
  
%>[<%
    
    String delim = "";
    
    Tag[] tags = currentPage.getTags();
    
    for(int i=1; i<=tags.length; i++){
         
        String strI = "" + i;
            
        %><%= delim %><%
        %>{<%
            %>"text":"<%= xssAPI.encodeForJSString(xssAPI.encodeForHTMLAttr(strI)) %>",<%
            %>"value":"<%= xssAPI.encodeForJSString(xssAPI.encodeForHTMLAttr(strI)) %>"<%
        %>}<%
        if ("".equals(delim)) delim = ",";
    }
%>]