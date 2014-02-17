<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@ page import="com.day.cq.wcm.api.WCMMode" pageEncoding="utf-8" %><%
%>
<fmt:message key="td.container.label.one" var="centralContainer1" />
<fmt:message key="td.sidecontainer.label.one.left" var="sideContainer1Left" />
<fmt:message key="td.sidecontainer.label.one.right" var="sideContainer1Right" />
<fmt:message key="td.sidecontainer.label.two.left" var="sideContainer2Left" />
<fmt:message key="td.sidecontainer.label.two.right" var="sideContainer2Right" />

<!-- begin template -->
<section class="td_tdn_quienes_01">
    <header class="header1">
        <cq:include path="navHeader" resourceType="/apps/commons/components/content/base/reference"/>
        <cq:include path="customWidget" resourceType="/apps/deportes/local/components/content/widgets/customWidget"/>
    </header>

    <!-- main -->
    <div class="main">
        <!-- banners -->
        <div class="c1_1 banner_template">
            <cq:include path="containerLogo" resourceType="foundation/components/parsys"/>
            <cq:include path="artBranch01" resourceType="/apps/deportes/generics/components/content/article/artBranch01"/>
        </div>
        <!-- central container one -->
        <div class="c2 left">
            <c:if test="${authorMode}">
                ${centralContainer1}
            </c:if>
            <cq:include path="containerOne" resourceType="foundation/components/parsys"/>
        </div>
        <!-- sidebar part one-->
        <div class="c3 right">
            <div class="c3_2 left">
                <c:if test="${authorMode}">
                    ${sideContainer1Left}
                </c:if>
                <cq:include path="containerOneLeft" resourceType="foundation/components/parsys"/>
            </div>
            <div class="c3_3 right">
                <c:if test="${authorMode}">
                    ${sideContainer1Right}
                </c:if>
                <cq:include path="containerOneRight" resourceType="foundation/components/parsys"/>
            </div>
        </div>
        <!-- sidebar part two-->
        <div class="c3bis right">
            <div class="c3bis_1 left">
                <c:if test="${authorMode}">
                    ${sideContainer2Left}
                </c:if>
                <cq:include path="containerTwoLeft" resourceType="foundation/components/parsys"/>
            </div>
            <div class="c3bis_2 right">
                <c:if test="${authorMode}">
                    ${sideContainer2Right}
                </c:if>
                <cq:include path="containerTwoRight" resourceType="foundation/components/parsys"/>
            </div>
        </div>
    <!-- end main -->
    </div>
    <!-- footer -->
    <footer class="footerPleca">
        <div class="footer" style="clear: both">
            <cq:include path="footer" resourceType="/apps/commons/components/content/base/reference"/>
            <cq:include path="corporateFooter" resourceType="/apps/commons/components/content/base/reference"/>
        </div>
    </footer>
</section>
<!-- end section -->