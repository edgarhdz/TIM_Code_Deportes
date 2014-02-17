<%@include file="/apps/deportes/local/components/global.jsp"%>
<%@ page import="com.day.cq.commons.Doctype,
                   org.apache.commons.lang3.StringEscapeUtils,com.day.cq.tagging.Tag" %>
<%
    Tag[] tags = currentPage.getTags();
    String title = currentStyle.get( "title/templateTitle", properties.get( "jcr:title", ""));
%><head>
    <c:set var="tags" value="<%= tags %>"/>
    <c:set var="tagsnum" value="<%= tags.length > 0 ? tags.length - 1 : 0 %>"/>
    <c:set var="title" value="<%=title%>"/>
    <c:if test="${not empty title}">
        <c:set var="septitle" value="|"/>
    </c:if>
    <title>Televisa | Deportes ${septitle} ${title}</title>
    <meta name="Description" content="${currentPage.description}">
    <meta name="Keywords" content="<c:if test="${not empty properties.keywords}">${properties.keywords}</c:if><c:if test="${empty properties.keywords}"><c:forEach var="i" begin="0" end="${tagsnum}"><c:if test="${i>0}">, </c:if>${tags[i].title}</c:forEach></c:if>">
    <link rel="canonical" href="${slingRequest.requestURL }"/>

    <meta http-equiv="X-UA-Compatible" content="IE=10,IE=9,IE=8,IE=7,IE=6"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <meta http-equiv="imagetoolbar" content="no">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="Pragma" content="no-cache, must-revalidate, no-sztore"/>

    <!-- Robots -->
    <meta name="robots" content="index,follow">
    <meta name="robots" content="noodp,noydir">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="HandheldFriendly" content="true">
    <meta name="format-detection" content="telephone=no">

    <cq:include script="/libs/wcm/core/components/init/init.jsp"/>
    <cq:include script="headlibs.jsp"/>
</head>

