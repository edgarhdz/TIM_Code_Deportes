<%@include file="/apps/deportes/local/components/global.jsp"%>
<%
    slingRequest.setAttribute( "configuration", "true" );
%>

<fmt:message key="commons.menulist.title" var="placeholderMenuList"/>
<fmt:message key="commons.footercontent.title" var="placeholderFooter"/>

<div>
    <h2><%=currentPage.getTitle()%></h2>

    <div class="sideleft" style="width: 15%; float:left; margin: 1%;">
        <h3> ${placeholderMenuList} </h3>
        <cq:include path="menulist" resourceType="/apps/commons/components/content/navigation/linklistnews" />

    </div>

    <div class="sideright" style="width: 80%; float:right; margin: 1%;">
        <h3>${placeholderFooter}</h3>
        <cq:include path="footer" resourceType="foundation/components/parsys"/>
    </div>
</div>
