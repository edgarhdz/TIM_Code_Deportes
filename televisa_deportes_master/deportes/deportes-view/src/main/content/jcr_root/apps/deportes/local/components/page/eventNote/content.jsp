<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@ page import="com.day.cq.wcm.api.WCMMode" pageEncoding="utf-8" %><%
%>
<fmt:message key="td.container.label.one" var="centralContainer1" />
<fmt:message key="td.sidecontainer.label.one.left" var="sideContainer1Left" />
<fmt:message key="td.sidecontainer.label.one.right" var="sideContainer1Right" />

<!-- begin template -->
<section class="td_note_generic_01">
    <header class="header1">
        <cq:include path="navHeader" resourceType="/apps/commons/components/content/base/reference"/>
    </header>
    <!-- main -->
    <div class="main">
        <!-- central container one -->
            <c:if test="${authorMode}">
                ${centralContainer1}
            </c:if>
            <cq:include path="containerEvent" resourceType="/apps/deportes/local/components/content/button/btnCarruProg02/btnCarruProg02Config"/>
    <!-- end main -->
    </div>
    <!-- footer -->
    <footer class="footerPleca">
        <div class="footer" style="clear: both">
            <cq:include path="footer" resourceType="/apps/commons/components/content/base/reference" />
            <cq:include path="corporateFooter" resourceType="/apps/commons/components/content/base/reference" />
        </div>
    </footer>
</section>
<!-- end section -->