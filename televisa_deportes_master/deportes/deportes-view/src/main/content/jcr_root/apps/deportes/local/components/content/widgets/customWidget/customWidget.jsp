<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Takes the html/script input in the script text area and sends the ouput as it is
 *  to the page
 *
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 60/17/2013  | Otto Giron        | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>

<%@include file="/apps/deportes/local/components/global.jsp"%><%
%>
<%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<!-- Add styles to devices -->
<fmt:message key="deportes.wdgcustomewidget01.message" var="message" />
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>
<c:if test="${authorMode}">
	<p>${message}</p>
</c:if>
<!-- BEGIN: str_pleca_01 -->
<c:if test="${properties.showpleca}">
    <cq:include path="str_pleca" resourceType="/apps/deportes/generics/components/content/structure/strPleca01" />
</c:if>

<!-- END: str_pleca_01 -->
<div id="custom-widget">
	${properties.script}
</div>
 <!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>
