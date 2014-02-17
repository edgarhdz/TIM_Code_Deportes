<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Poll
 *
 *  wdg_poll_01 component
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
<%@include file="/apps/commons/components/global.jsp"%>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>

<c:choose>
    <c:when test="${not empty properties.pollId}">
        <div class="esmas_safe_simple_poll_box"
             id="esmas_safe_simple_poll_box_${ properties.pollId }">
        </div>
    </c:when>
    <%--  no poll ID exists yet? show a message --%>
    <c:otherwise>
        <c:if test="${ authorMode }">
            Please edit the component to add the poll Id.
        </c:if>
    </c:otherwise>
</c:choose>

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>