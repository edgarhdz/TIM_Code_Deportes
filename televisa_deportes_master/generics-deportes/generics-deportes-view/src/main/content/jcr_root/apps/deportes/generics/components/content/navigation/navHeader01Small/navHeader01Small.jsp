<%--
 * DESCRIPTION
 *  Header component for Noticieros Televisa
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 24/01/2013  | Jose Alejandro Urizar  | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%><%
%><%@include file="/apps/commons/components/global.jsp"%><%
%><%@page session="false"
          pageEncoding="utf-8"
          import="java.text.SimpleDateFormat,java.util.Locale,com.day.cq.commons.Doctype,java.util.Date,java.util.Iterator,com.day.cq.wcm.api.PageFilter,com.day.cq.wcm.api.Page" %><%
%><%@page
        import="com.televisa.commons.services.datamodel.impl.LinkListImpl"%>


<fmt:message key="commons.navheader01.description" var="placeholdermaintitle" />

<style>
    .parmessage {
        display: none;
    }
    .cq-paragraphreference-paragraph .parmessage {
        display: block;
    }

</style>
<div class="parmessage">
    <br/><br/><br/>headerNav<br/><br/><br/><br/><br/><br/>
</div>


<!-- logo starts -->
<c:choose>
    <c:when test="${properties.logoLinkType == 'internal'}">
        <c:if test="${not empty properties.logoInternalLink}">
            <c:set var="logolink" value="${properties.logoInternalLink}.html" />
        </c:if>
    </c:when>
    <c:otherwise>
        <c:set var="logolink" value="${properties.logoExternalLink}" />
    </c:otherwise>
</c:choose>
<c:if test="${empty logolink}">
    <c:set var="logolink" value="#"/>
</c:if>
<c:if test="${not empty properties.logoLinkTarget && properties.logoLinkTarget != 'select'}">
    <c:set var="logotarget" value="target= '${properties.logoLinkTarget}'"/>
</c:if>
<c:if test="${not empty properties.logoLinkTitle}">
    <c:set var="logotitle" value="title= '${properties.logoLinkTitle}'" />
</c:if>
<c:set var="logotext" value="${properties.logoLinkText}"/>
<c:if test="${empty logotext}">
    <c:set var="logotext" value=""/>
</c:if>
<!-- logo ends -->


<!-- BEGIN: nav_header_01 -->

<style>
    legend {
        display: block;
    }
</style>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>

<header class="nav_header_01" data-enhance="false">

    <div class="topnav">
        <div class="inner">
            <a class="televisa deportes" href="${logolink}" ${logotitle}>
                <span class="mobile-logo"></span>
                <span>${logotext}</span></a>
        </div>
    </div>

    <div class="title">
        <c:if test="${not empty properties.mainTitle}">
            <div class="inner">
                <strong>${properties.mainTitle}<a class="arrow" href="#"><i class="tvsa-caret-down"></i></a></strong>
            </div>
        </c:if>
        <c:if test="${empty properties.mainTitle}">
            <h2><a href="#"> ${placeholdermaintitle} <span></span></a></h2>
        </c:if>
    </div>



</header>
<script>
    setTime();
</script>


<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>