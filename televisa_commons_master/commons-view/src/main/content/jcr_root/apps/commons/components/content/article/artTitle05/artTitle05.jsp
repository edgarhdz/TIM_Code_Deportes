<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Article Program Component
 *
 *  This component modifies the properties of a program article.
 *  
 *  component id = art_title_05
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 08/02/2013  | Jorge Diaz             | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>

<fmt:message key="commons.message.placeholder" var="placeholdercomponentmessage"/>
<fmt:message key="commons.artTitle05.title" var="componentname"/>

<c:choose>

    <c:when test="${empty properties.programName}">
        <c:if test="${authorMode}">
            <div class="btn_progr_01">
                <p><c:out value="${placeholdercomponentmessage}" />&nbsp;<c:out value="${componentname}" /></p>
            </div>
        </c:if>
    </c:when>

    <c:otherwise>

                <!-- link starts -->
        <c:choose>
            <c:when test="${properties.linkType == 'internal'}">
                <c:if test="${not empty properties.internalLink}">
                    <c:set var="link" value="${properties.internalLink}.html" />  
                </c:if>    
            </c:when>
            <c:otherwise>
                <c:set var="link" value="${properties.externalLink}" />    
            </c:otherwise>
        </c:choose>
        <c:if test="${empty link}">
            <c:set var="link" value="#"/>
        </c:if>    
        <c:if test="${not empty properties.linkTarget && properties.linkTarget != 'select'}">
            <c:set var="target" value="target= '${properties.linkTarget}'"/>
        </c:if>
        <c:if test="${not empty properties.linkTitle}">
            <c:set var="title" value="title= '${properties.linkTitle}'" />
        </c:if>
        <c:set var="text" value="${properties.linkText}"/>
        <c:if test="${empty text}">
            <c:set var="text" value=""/>
        </c:if>
        <!-- link ends -->

    </c:otherwise>

</c:choose>

<!-- BEGIN:art_title_05 -->
<div class="art_title_05">
    <div class="art_title_05_principal">
            <h1><a class="textcolor-title1" href="${link}" ${target} ${title}>${text}</a></h1>
            <div></div>
    </div>
    <div class="art_title_05_left">
        
       <p class="canal">${properties.programChannel}</p>
		<p class="textcolor-title2">${properties.programSchedule}</p>
    </div>
    <div class="art_title_05_right">
        
        <div class="art_title_05_texto">
            <p class="textcolor-title4">${properties.programSynopsis}</p>
        </div>
    </div>
</div>
<!-- END:art_title_05-->