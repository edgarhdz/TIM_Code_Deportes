<%--
 * DESCRIPTION
 *  Header reference for external Html
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 24/01/2013  | Jose Alejandro Urizar  | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%><%
%><%@include file="/apps/commons/components/global.jsp"%><%
%><%@page import="java.net.HttpURLConnection"%><%
%><%@page import="java.net.URL"%><%
%><%@page session="false" pageEncoding="utf-8" %><%   
    String externalPath = properties.get( "externalPath", "" );

	boolean validRequest = false;
    if(externalPath != null){
        try{
            URL obj = new URL(externalPath);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if(responseCode == 200){
                validRequest = true;
            }else{
                validRequest = false;
            }
        }catch(Exception e){
            validRequest = false;
        }
    }
    pageContext.setAttribute("validRequest", validRequest);
%>
<fmt:message key="commons.externalHTMLSource.path.description" var="placeholder" />
<c:set var="externalPath" value="<%= externalPath %>"/>
<div id="external">
    <c:choose>
        <c:when test="${externalPath != '' && not empty externalPath}">
            <c:choose>
                <c:when test="${validRequest}">
                    <c:import url="${externalPath}" />    
                </c:when>
                <c:otherwise>
                    <c:if test="${authorMode}">
                        <h2>${placeholder}</h2>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="">
            <h4 class="cq-texthint-placeholder">${placeholder}</h4>
        </c:otherwise>
    </c:choose>
</div>