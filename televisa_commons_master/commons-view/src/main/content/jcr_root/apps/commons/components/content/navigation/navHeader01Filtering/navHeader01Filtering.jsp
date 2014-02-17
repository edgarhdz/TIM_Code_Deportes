<%--

  nav_header_01_filtering component.
 
  

--%><%
%><%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%><%
%><%@page session="false"
            import="java.text.SimpleDateFormat,java.util.Locale,com.day.cq.commons.Doctype,java.util.Date,java.util.Iterator,com.day.cq.wcm.api.PageFilter,com.day.cq.wcm.api.Page" %><%
%>
<%
    boolean isConfigurationTemplate = "true".equals( slingRequest.getAttribute( "configuration" ) );
%>
<c:set var="isConfigurationTemplate" value="<%=isConfigurationTemplate%>" />

<c:if test="${not empty properties.channelType}">
    <c:set var="channelType" value="${properties.channelType}" />
</c:if>

<!-- video starts -->
<c:choose>
    <c:when test="${properties.videoLinkType == 'internal'}">
        <c:if test="${not empty properties.videoInternalLink}">
            <c:set var="videolink" value="${properties.videoInternalLink}.html" />  
        </c:if>    
    </c:when>
    <c:otherwise>
        <c:set var="videolink" value="${properties.videoExternalLink}" />    
    </c:otherwise>
</c:choose>
<c:if test="${empty videolink}">
    <c:set var="videolink" value="#"/>
</c:if>    
<c:if test="${not empty properties.videoLinkTarget && properties.videoLinkTarget != 'select'}">
    <c:set var="videotarget" value="target= '${properties.videoLinkTarget}'"/>
</c:if>
<c:if test="${not empty properties.videoLinkTitle}">
    <c:set var="videotitle" value="title= '${properties.videoLinkTitle}'" />
</c:if>
<c:set var="videotext" value="${properties.videoLinkText}"/>
<c:if test="${empty videotext}">
    <c:set var="videotext" value=""/>
</c:if>
<!-- video starts -->
<!-- photo starts -->
<c:choose>
    <c:when test="${properties.photoLinkType == 'internal'}">
        <c:if test="${not empty properties.photoInternalLink}">
            <c:set var="photolink" value="${properties.photoInternalLink}.html" />  
        </c:if>    
    </c:when>
    <c:otherwise>
        <c:set var="photolink" value="${properties.photoExternalLink}" />    
    </c:otherwise>
</c:choose>
<c:if test="${empty photolink}">
    <c:set var="photolink" value="#"/>
</c:if>    
<c:if test="${not empty properties.photoLinkTarget && properties.photoLinkTarget != 'select'}">
    <c:set var="phototarget" value="target= '${properties.photoLinkTarget}'"/>
</c:if>
<c:if test="${not empty properties.photoLinkTitle}">
    <c:set var="phototitle" value="title= '${properties.photoLinkTitle}'" />
</c:if>
<c:set var="phototext" value="${properties.photoLinkText}"/>
<c:if test="${empty phototext}">
    <c:set var="phototext" value=""/>
</c:if>
<!-- photo starts -->
<!-- twitter starts -->
<c:choose>
    <c:when test="${properties.twitterLinkType == 'internal'}">
        <c:if test="${not empty properties.twitterInternalLink}">
            <c:set var="twitterlink" value="${properties.twitterInternalLink}.html" />  
        </c:if>    
    </c:when>
    <c:otherwise>
        <c:set var="twitterlink" value="${properties.twitterExternalLink}" />    
    </c:otherwise>
</c:choose>
<c:if test="${empty twitterlink}">
    <c:set var="twitterlink" value="#"/>
</c:if>    
<c:if test="${not empty properties.twitterLinkTarget && properties.twitterLinkTarget != 'select'}">
    <c:set var="twittertarget" value="target= '${properties.twitterLinkTarget}'"/>
</c:if>
<c:if test="${not empty properties.twitterLinkTitle}">
    <c:set var="twittertitle" value="title= '${properties.twitterLinkTitle}'" />
</c:if>
<c:set var="twittertext" value="${properties.twitterLinkText}"/>
<c:if test="${empty twittertext}">
    <c:set var="twittertext" value=""/>
</c:if>
<!-- twitter starts -->
<!-- facebook starts -->
<c:choose>
    <c:when test="${properties.facebookLinkType == 'internal'}">
        <c:if test="${not empty properties.facebookInternalLink}">
            <c:set var="facebooklink" value="${properties.facebookInternalLink}.html" />  
        </c:if>    
    </c:when>
    <c:otherwise>
        <c:set var="facebooklink" value="${properties.facebookExternalLink}" />    
    </c:otherwise>
</c:choose>
<c:if test="${empty facebooklink}">
    <c:set var="facebooklink" value="#"/>
</c:if>    
<c:if test="${not empty properties.facebookLinkTarget && properties.facebookLinkTarget != 'select'}">
    <c:set var="facebooktarget" value="target= '${properties.facebookLinkTarget}'"/>
</c:if>
<c:if test="${not empty properties.facebookLinkTitle}">
    <c:set var="facebooktitle" value="title= '${properties.facebookLinkTitle}'" />
</c:if>
<c:set var="facebooktext" value="${properties.facebookLinkText}"/>
<c:if test="${empty facebooktext}">
    <c:set var="facebooktext" value=""/>
</c:if>
<!-- facebook ends -->
<c:choose>
    <c:when test="${isConfigurationTemplate == 'true'}">
        <a href="${videolink}"    ${videotarget}    ${videotitle}>${videotext}</a><br/>
        <a href="${photolink}"    ${phototarget}    ${phototitle}>${phototext}</a><br/>
        <a href="${twitterlink}"  ${twittertarget}  ${twittertitle}>${twittertext}</a><br/>
        <a href="${facebooklink}" ${facebooktarget} ${facebooktitle}>${facebooktext}</a><br/>
    </c:when>
    <c:otherwise>
<!-- socialButtons starts -->

<div class="filtering">
    <a href="${videolink}"    ${videotarget}    ${videotitle}><i class="tvsa-videocamera ${channelType}"></i></a>
    <a href="${photolink}"    ${phototarget}    ${phototitle}><i class="tvsa-camera ${channelType}"></i></a>
    <a href="${twitterlink}"  ${twittertarget}  ${twittertitle}><i class="tvsa-twitter twitter"></i></a>
    <a href="${facebooklink}" ${facebooktarget} ${facebooktitle}><i class="tvsa-facebook facebook"></i></a>
</div>

<!-- socialButtons ends -->
    </c:otherwise>
</c:choose>