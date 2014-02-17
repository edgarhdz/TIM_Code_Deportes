<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 7/1/2013    | Bryan Gerhard Chávez   | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>  
<%@include file="/apps/deportes/local/components/global.jsp"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.Map" %>
<%@page import="org.apache.sling.api.SlingHttpServletRequest" %>
<%@page import="com.televisa.commons.services.utilities.Base64Encoding" %>


<%
    response.setContentType("application/json"); 
	response.setCharacterEncoding("utf-8");

	String[] selectors = ((SlingHttpServletRequest)request).getRequestPathInfo().getSelectors();
	String path = selectors[1];
    pageContext.setAttribute("path", Base64Encoding.decoding(path));
%>

<tg:getBrightcoveProperties var="brightcove" path="${path}" />

<%
	JSONObject json = new JSONObject((Map)pageContext.getAttribute("brightcove"));
	pageContext.setAttribute("json", json);

%>
${json}