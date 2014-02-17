<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Generics Two Primary Six Shorts Container Component.
 *
 *  Displays a generic primary four shorts container component.
 *
 *  component id = mix_8arts_01
 *
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 15/02/2013  | Gerardo Escobar        | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%><%
%><%@include file="/apps/deportes/generics/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%><%
%><%@page session="false" %>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>

<div class="mix_8arts_01" data-enhance="false">
    <div class="cont-left">

        <cq:include path="str_pleca_01_1" resourceType="/apps/deportes/generics/components/content/structure/strPleca01" />

        <cq:include path="mix_4arts_01_1" resourceType="/apps/deportes/generics/components/content/mix/mix4arts01" />

    </div>

    <div class="cont-left">

        <cq:include path="str_pleca_01_2" resourceType="/apps/deportes/generics/components/content/structure/strPleca01" />

        <cq:include path="mix_4arts_01_2" resourceType="/apps/deportes/generics/components/content/mix/mix4arts01" />

    </div>

    <%-- Clear the previous float so it will not overlap the next container. --%>
    <div style="float: none; clear: both;">&nbsp;</div>
</div>

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>