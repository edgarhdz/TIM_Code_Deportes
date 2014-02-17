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
        <div class="c2 left">
            <c:if test="${authorMode}">
                ${centralContainer1}
            </c:if>
            <cq:include path="article" resourceType="deportes/generics/components/content/article/article"/>
        </div>
        <!-- sidebar part one-->
        <div class="c3 right">
            <div class="c3_1 left">
                <c:if test="${authorMode}">
                    ${sideContainer1Left}
                </c:if>
                <cq:include path="containerThreeLeft" resourceType="foundation/components/parsys"  />
            </div>
            <div class="c3_2 right">
                <c:if test="${authorMode}">
                    ${sideContainer1Right}
                </c:if>
                <cq:include path="containerThreeRight" resourceType="foundation/components/parsys"  />
            </div>
        </div>

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