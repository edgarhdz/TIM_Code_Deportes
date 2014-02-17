<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 * Title Article 3 Component
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 19/06/2013  | Jorge Diaz             | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" %><%
%>

<!-- Add styles to devices -->
<fmt:message key="commons.arttitle03.title.label" var="altTitle" />
<fmt:message key="commons.arttitle03.description.label" var="altDescription" />

<c:set var="logoPath">
    <tg:getImageAsset name="./image/fileReference" />

</c:set>

<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>
<!-- BEGIN:art_title_03 -->
<div class="art_title_03">
    <div class="art_title_03_principal">
        <c:choose>
            <c:when test="${empty properties.title}">
                <h1><a class="textcolor-title1" href="">[${altTitle}]</a></h1>
            </c:when>
            <c:otherwise>
                <h1><a class="textcolor-title1" href="">${properties.title}</a></h1>
            </c:otherwise>
        </c:choose>

        <div></div>
    </div>
    <div class="art_title_03_left">

        <a href="">
            <img src="${logoPath}" alt="Image description"/>
        </a>
    </div>
    <div class="art_title_03_right">

        <div class="art_title_03_texto">
            <c:choose>
                <c:when test="${empty properties.description}">
                    <div class="titulo textcolor-title1">[${altDescription}]</div>
                </c:when>
                <c:otherwise>
                    <p class="textcolor-title4">
                        ${properties.description}
                    </p>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</div>
<!-- END:art_title_03-->
 <!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>