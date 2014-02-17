<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Article Program Component
 *
 *  This component modifies the properties of a program article.
 *  Component id = art_title_04
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 28/08/2013  | jbarrera               | Initial creation
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/deportes/generics/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="genericsdeportes.component.message.placeholder" var="placeholdercomponentmessage"/>
<fmt:message key="genericsdeportes.articleprogram.title" var="componentname"/>

<c:choose>
    <c:when test="${empty properties.programName}">
        <c:if test="${authorMode}">
            <div class="btn_progr_01">
                <p><c:out value="${placeholdercomponentmessage}" />&nbsp;<c:out value="${componentname}" /></p>
            </div>
        </c:if>
    </c:when>

    <c:otherwise>
        <c:if test="${ properties.programUrl != null}">
            <c:set var="programUrl"  value="${properties.programUrl}" />
        </c:if>
        <c:if test="${ properties.programUrl == null}">
            <c:set var="programUrl"  value="#" />
        </c:if>
    </c:otherwise>
</c:choose>



<%-- adding image rendition of 110 * 110--%>
<c:set var="rendition">
    <tg:renditionFromImageAsset name="fileReference" width="110" height="110"/>
</c:set>

<!-- begin art_title_04 -->
<div class="art_title_04">
    <div class="art_title_04_principal">
        <h1><a class="textcolor-title1" href="${programUrl}" title="${properties.programUrlTitle}">
            ${properties.programName}
        </a></h1>
        <div></div>
    </div>

    <c:if test="${not empty properties.programName}">
        <div class="art_title_04_left">
            <a href="${programUrl}">
                <img src="${rendition}" alt="${properties.programUrlTitle}"/>
            </a>
        </div>
    </c:if>
    <div class="art_title_04_right">
        <div class="art_title_04_texto">
            <p>${properties.programChannel}</p>
            <p class="textcolor-title2">${properties.programSchedule}</p>
            <p class="textcolor-title4">${properties.programSynopsis}</p>
        </div>
    </div>
</div>

<!-- video embed component -->
<cq:include resourceType="/apps/deportes/generics/components/content/video/videoEmbed"  path="video"/>