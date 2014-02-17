<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 * btn_carru_prog_02
 *
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 12/08/2013  | jbarrera               | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<div class="btn_carru_prog_02">
    <h1>${properties.title}</h1>
        <div class="carrusel">
            <ul class="carrousa" style="height: auto">
                <div class="itemSize" style="width:310px">
                    <cq:include path="containerItems" resourceType="foundation/components/parsys"/>
                </div>
            </ul>
        </div>
</div>
