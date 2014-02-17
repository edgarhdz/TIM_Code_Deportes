<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 * This componet show an main image, title and channel image entered by author.
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 12/09/2013  | jbarrera               | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="genericsdeportes.btnProgr02.title" var="componentname"/>
<fmt:message key="genericsdeportes.component.message.placeholder" var="placeholder"/>
<fmt:message key="genericsdeportes.component.message.placeholdercomponent" var="placeholdercomponent"/>

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
<c:if test="${not empty properties.linkText}">
    <c:set var="target" value="target= '${properties.linkTarget}'"/>
</c:if>


<c:set var="imageNodePath" value="${currentNode.path}/image" />
<c:set var="imagePath">
    <tg:getPropertyFromNode path="${imageNodePath}" property="fileReference"/>
</c:set>

<c:set var="imageIconNodePath" value="${currentNode.path}/image2" />
<c:set var="imageIconPath">
    <tg:getPropertyFromNode path="${imageIconNodePath}" property="fileReference"/>
</c:set>

<c:if test="${empty properties.text}">
    <c:if test="${authorMode}">
        <p><c:out value="${placeholder}" />&nbsp;<c:out value="${componentname}" /></p>
        </br>
    </c:if>
</c:if>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>

<c:if test="${not empty properties.text}">
    <div class="btn_progr_02" data-enhance="false">
        <div class="envivo">
            <img src="${imagePath}" alt="${properties.linkTitle}"/>
            <div class="overlay"></div>
            <div class="envivo_content">
                <a ${link} ${target} title="${properties.linkTitle}">
                    <h3>${properties.text}</h3>
                    <div class="line"></div>
                    <c:if test="${not empty imageIconPath}">
                        <div  class="channelImg">
                            <img src="${imageIconPath}" class="channelImg">
                            <span>&raquo;</span>
                        </div>
                    </c:if>
                </a>
            </div>
        </div>
    </div>
</c:if>

<!-- end div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>