<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 * Boton Program 1 Component
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 12/07/2013  | Mario Taracena         | Initial Creation.
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
<fmt:message key="commons.btnprog01.prog.title.label" var="altprogTitle" />
<fmt:message key="commons.btnprog01.invitetext.label" var="altinviteText" />




<c:set var="background">
    <tg:getImageAsset name="./image1/fileReference" />

</c:set>
<c:set var="logo">
    <tg:getImageAsset name="./image2/fileReference" />

</c:set>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>
<div class="btn_progr_01">

	<!-- BEGIN: str_pleca_01 -->
	<cq:include path="header"
		resourceType="/apps/commons/generics/components/content/structure/strPleca01" />
	<!-- END: str_pleca_01 -->
	<div class="envivo">
        <c:choose>
            <c:when test="${empty background}">
                <img src="http://placehold.it/600x386" alt="Image description" />
            </c:when>
            <c:otherwise>
                <img src="${background}" alt="Image description" />
            </c:otherwise>
        </c:choose>

		<div class="back_overlay"></div>

		<div class="envivo_content">

			<a href="${properties.linkURL}" title="Link Description">
                <c:choose>
                    <c:when test="${empty properties.progTitle}">
                        <h3>[${altprogTitle}]</h3>
                    </c:when>
                    <c:otherwise>
                        <h3>${properties.progTitle}</h3>
                    </c:otherwise>
                </c:choose>

				<div class="line"></div>
                <c:choose>
                    <c:when test="${empty properties.inviteText}">
                        <p>[${altinviteText}]</p>
                    </c:when>
                    <c:otherwise>
                        <p>${properties.inviteText}</p>
                    </c:otherwise>
                </c:choose>


				<div class="channelImg">
					<img src="${logo}" class="channelImg"> 
				</div>
				<i class="tvsa-double-caret-right"></i>
			</a>
		</div>
	</div>
</div>
<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>