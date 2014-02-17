<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Single Title Box component.
 *  This component is used to display a title customized using the dialog of this component
 *  also, you can add an internal or external link to the title.
 *  
 *
 *  component id = str_pleca_01
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 18/01/2013  | Juan Jose Pol.         | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>


<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="commons.strpleca01.title" var="componentname"/>
<fmt:message key="commons.strpleca01.placeholder" var="placeholder"/>
<fmt:message key="commons.strpleca01.hide-text" var="hidemessage"/>

<%-- Adding validation for the content of the text --%>
<c:set var="text" value="${properties.linkText}"/>
<c:if test="${empty text}">
    <c:if test = "${authorMode}">
        <c:set var="text" value="${placeholder} ${componentname}"/>
    </c:if>
</c:if>

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

<%-- adding target propety to the link --%>
<c:if test="${not empty properties.linkText}">
    <c:set var="target" value="target= '${properties.linkTarget}'"/>
</c:if>
<%-- adding title propety to the link --%>
<c:if test="${not empty properties.linkTitle}">
    <c:set var="title" value="title= '${properties.linkTitle}'" />
</c:if>

<c:set var="plecaVisible" value="true"/>
<c:if test="${not empty properties.linkHide && properties.linkHide == true}">
    <c:set var="plecaVisible" value="false"/>
</c:if>


<%--  Merge hmtl and css for the str_pleca_01 Component --%>

<!-- BEGIN: str_pleca_01 -->

<c:if test="${plecaVisible}">
    <div class="str_pleca_01" data-enhance="false">
        <div class="str_pleca_01_title" >
            <h3 class="background-color-pleca1">
                <a ${link} ${target} ${title} class="textcolor-title3"> ${text}
                    <span class="str_pleca_01_arrowa selected"></span>
                    <span class="str_pleca_01_arrowb"></span>
                </a>
            </h3>
        </div>
    </div>
</c:if>
<c:if test="${!plecaVisible && authorMode}">
    <h4 class="cq-texthint-placeholder">${hidemessage}</h4>
</c:if>


<!-- END: str_pleca_01 -->
    