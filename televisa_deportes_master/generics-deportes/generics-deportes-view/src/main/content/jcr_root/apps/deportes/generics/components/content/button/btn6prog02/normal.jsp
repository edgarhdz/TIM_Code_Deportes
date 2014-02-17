<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Super clic component (btn_6prog_02)
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer               | Changes
 *
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>

<%@include file="/apps/deportes/generics/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<%@ page import="com.televisa.commons.services.utilities.Base64Encoding"%>
<%@ page import="java.util.Arrays" %>

<fmt:message key="genericsdeportes.btn6prog02.placeholder" var="placeholder"/>

<%
    String path = properties.get("path", "");
    pageContext.setAttribute("path", Base64Encoding.encoding(path));

    String[] tags = properties.get("cq:tags", new String[0]);
    pageContext.setAttribute("tags", Base64Encoding.encoding(Arrays.toString(tags)));
    pageContext.setAttribute("showarticles", properties.get("showarticles", "false"));
    pageContext.setAttribute("showvideos", properties.get("showvideos", "false"));
    pageContext.setAttribute("showphotogalleries", properties.get("showphotogalleries", "false"));
    pageContext.setAttribute("linkTarget", properties.get("linkTarget", "_self"));
    pageContext.setAttribute("titleLinkText", properties.get("titleLinkText", "Super clic"));

    pageContext.setAttribute("titleLinkType", properties.get("titleLinkType", "internal"));
    pageContext.setAttribute("titleInternalLink", properties.get("titleInternalLink", ""));
    pageContext.setAttribute("titleExternalLink", properties.get("titleExternalLink", ""));
    pageContext.setAttribute("titleLinkTarget", properties.get("titleLinkTarget", "_self"));
    pageContext.setAttribute("titleTitle", properties.get("titleTitle", ""));
%>

<c:choose>
    <c:when test="${titleLinkType == 'internal'}">
        <c:if test="${not empty titleInternalLink}">
            <c:set var="titlelink" value="href='${titleInternalLink}.html'" />
        </c:if>
    </c:when>
    <c:otherwise>
        <c:if test="${not empty titleExternalLink}">
            <c:set var="titlelink" value="href='${titleExternalLink}'" />
        </c:if>
    </c:otherwise>
</c:choose>
<c:if test="${empty titlelink}">
    <c:set var="titlelink" value="#"/>
</c:if>

<%-- adding target propety to the links --%>
<c:if test="${not empty titleLinkTarget}">
    <c:set var="target" value="target= '${titleLinkTarget}'"/>
</c:if>

<c:if test="${not empty titleTitle}">
    <c:set var="titleLinkTitle" value="title= '${titleTitle}'" />
</c:if>
<c:set var="titleLinkText" value="${titleLinkText}"/>
<c:if test="${empty titleLinkText}">
    <c:set var="titleLinkText" value=""/>
</c:if>

 <c:set var="plecaVisible" value="true"/>
    <c:if test="${not empty properties.linkHide && properties.linkHide == true}">
    	<c:set var="plecaVisible" value="false"/>
    </c:if>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>

<!-- btn_6prog_02 -->
<c:if test="${authorMode && empty path}">
    <h4 class="cq-texthint-placeholder">${placeholder}</h4>
</c:if>
<c:if test="${not empty properties.path}">
    <div class="btn_6prog_02" data-enhance="false">
        <div class="type1a">
			<div class="title">
				<c:if test="${plecaVisible}">
					<h2 class="background-color-pleca1">
						<a ${titlelink} ${titleLinkTarget} ${titleLinkTitle}>${titleLinkText}<span
							class="icon"></span></a>
					</h2>
				</c:if>
				<ul class="flechas">
					<li class="dotted-right"><a href="#left"
						class="left2 carouselArrowLeft" title=""><i
							class="tvsa-double-caret-left inactive"></i></a></li>
					<li><a href="#right" class="right2 carouselArrowRight"
						title=""><i class="tvsa-double-caret-right"></i></a></li>
				</ul>
			</div>
			<div class="carrusel" id="${random}carousel"></div>
		</div>

        <div class="bullets">
            <ul>
                <li class="background-color1"></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
        </div>
    </div>
</c:if>

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>

<script type="text/javascript">

    function loadNotes${random}(numberOfPage){
        if(numberOfPage == undefined || numberOfPage <= 0){
            numberOfPage = 1;
        }
        var htmlBody = '';
        var htmlPaginator = '';
        var htmlResult = [];
        var path = "${path}";
        $.ajax({
            dataType: "json",
            type: "GET",
            url: "/bin/commons/superClic/query.${path}" + ".${tags}" + ".${showarticles}" + ".${showvideos}" + ".${showphotogalleries}." + numberOfPage + ".json",
            success: function(result){
                htmlResult = result;
                $.each(htmlResult, function(key, val) {
                    if(key == 'body'){
                        htmlBody = val;
                    }
                });
                $('#${random}carousel').html(htmlBody);
            },
            error: function(result){
                console.log("Unable to get data in the Super clic");
            }
        });
    }

    jQuery(document).ready(function ($) {
        (function($,T){
            loadNotes${random}(1); //Load notes in the first page
        })($,Televisa);
    });
</script>
<!-- btn_6prog_02 -->
