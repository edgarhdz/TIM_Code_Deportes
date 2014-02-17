<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Bookmark Navigation Component
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 01/07/2013  | Otto Giron             | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%>
<%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%
%>
<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>
<!-- BEGIN: wdg_humor_01 -->
<div class="wdg_gmap_01">


    <div class="wdg_gmap_01_margen"></div>


    <div id ="wdg_gmap_01_properties" class="wdg_gmap_01_properties" data-latitude="${properties.latitude}" data-longitude="${properties.longitude}" data-zoom="17" data-map="k"></div>


</div>
<!-- END: wdg_humor_01 -->
<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>
