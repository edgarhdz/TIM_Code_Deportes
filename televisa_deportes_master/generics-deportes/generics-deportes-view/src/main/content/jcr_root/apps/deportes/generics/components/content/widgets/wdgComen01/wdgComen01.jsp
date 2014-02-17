<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  
 *  
 *  component id = wdg_commen_01
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 26/02/2013  | Leonel Orozco          | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/deportes/generics/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<fmt:message key="commons.wdgcomment01.comments.label" var="comments"/>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>

<!-- str pleca 01 -->
<cq:include path="pleca" resourceType="/apps/deportes/generics/components/content/structure/strPleca01"/>

<!-- wdg_comen_01 -->
<div id="COMM_comments_social"></div>

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>

<%--  adding a new div to move the parsys --%>
<c:if test = "${authorMode}">
    <div style="clear:both;"></div>
</c:if>  