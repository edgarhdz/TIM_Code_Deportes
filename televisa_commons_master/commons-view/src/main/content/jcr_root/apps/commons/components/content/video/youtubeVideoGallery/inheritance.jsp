<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  
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
<%@page import="com.televisa.commons.services.utilities.Base64Encoding" %>

<%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@page session="false" %>

<%	
	String pathInheritance = properties.get("inheritancePath",null);
	pageContext.setAttribute("inheritancePath", Base64Encoding.encoding(pathInheritance));

	String referenceComponent = properties.get("sling:resourceType",null);
	pageContext.setAttribute("referenceComponent", Base64Encoding.encoding(referenceComponent));

%>

<sling:include resourceType="/apps/commons/components/content/base/inheritance/inheritanceComponent.jsp" replaceSelectors="${inheritancePath}.${referenceComponent}" /> 
