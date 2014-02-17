<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 * component id = btn_carru_prog_02
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 09/08/2013  | jbarrera               | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="deportes.btnCarruProg02Item.title" var="componentname"/>
<fmt:message key="deportes.component.message.placeholder" var="placeholder"/>
<fmt:message key="deportes.component.message.placeholdercomponent" var="placeholdercomponent"/>

<%-- Adding validation for the content --%>
<c:choose>
    <c:when test="${properties.linkType == 'internal'}">
        <c:if test="${not empty properties.internalLink}">
            <c:set var="link" value="href='${properties.internalLink}.html'" />
        </c:if>
    </c:when>
    <c:otherwise>
        <c:if test="${not empty properties.externalLink}">
            <c:set var="link" value="href='${properties.externalLink}'" />
        </c:if>
    </c:otherwise>
</c:choose>

<%-- adding target propety to the links --%>
<c:if test="${not empty properties.linkTarget}">
    <c:set var="target" value="target= '${properties.linkTarget}'"/>
</c:if>

<c:if test="${empty properties.text}">
    <c:if test="${authorMode}">
        <p><c:out value="${placeholder}" />&nbsp;<c:out value="${componentname}" />&nbsp;<c:out value="${placeholdercomponent}" /></p>
        </br>
    </c:if>
</c:if>

<li>
    <c:if test="${not empty properties.text}">
        <a ${link} ${target} title="${properties.linkTitle}">
            <span class="bg"></span>
            <span class="text_prin">
                    <span class="title">${properties.text}</span>
                    <div class="line"></div>
                    <c:if test="${not empty properties.description}">
                        <span class="desc">${properties.description}<i class="tvsa-double-caret-right"></i></span>
                    </c:if>
            </span>

            <img class="no_mobile" src="${properties.fileReference}" width="300" height="120" alt="${properties.linkTitle}">
        </a>
    </c:if>
</li>

