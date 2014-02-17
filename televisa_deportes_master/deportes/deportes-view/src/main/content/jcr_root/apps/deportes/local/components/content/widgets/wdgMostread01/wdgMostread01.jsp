<%@include file="/apps/deportes/local/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>

<!-- BEGIN: wdg_mostread_01 -->
<div class="wdg_mostread_01">
	<!-- BEGIN: str_pleca_01 -->
	<cq:include path="str_pleca"
		resourceType="/apps/deportes/generics/components/content/structure/strPleca01" />
	<!-- END: str_pleca_01 -->
	<div class="wdg_mostread_01_content">
		<ul class="wdg_mostread_01_container">
			<li class="wdg_mostread_01_show"><a href="${properties.firstpath}.html">
					<div class="wdg_mostread_01_number textcolor-title6">1</div>
					<div class="wdg_mostread_01_text"><tg:getPageTitleFromPath path="${properties.firstpath}" /></div>
			</a></li>
			<li class="wdg_mostread_01_show"><a href="${properties.secondpath}.html">
					<div class="wdg_mostread_01_number textcolor-title6">2</div>
					<div class="wdg_mostread_01_text"><tg:getPageTitleFromPath path="${properties.secondpath}" /></div>
			</a></li>
			<li class="wdg_mostread_01_show"><a href="${properties.thirthpath}.html">
					<div class="wdg_mostread_01_number textcolor-title6">3</div>
					<div class="wdg_mostread_01_text"><tg:getPageTitleFromPath path="${properties.thirthpath}" /></div>
			</a></li>
			<li class="wdg_mostread_01_hide"><a href="${properties.fourthpath}.html">
					<div class="wdg_mostread_01_number textcolor-title6">4</div>
					<div class="wdg_mostread_01_text"><tg:getPageTitleFromPath path="${properties.fourthpath}" /></div>
			</a></li>
			<li class="wdg_mostread_01_hide"><a href="${properties.fifthpath}.html">
					<div class="wdg_mostread_01_number textcolor-title6">5</div>
					<div class="wdg_mostread_01_text"><tg:getPageTitleFromPath path="${properties.fifthpath}" /></div>
			</a></li>
		</ul>
	</div>
</div>
<!-- END: wdg_mostread_01 -->
 <!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>