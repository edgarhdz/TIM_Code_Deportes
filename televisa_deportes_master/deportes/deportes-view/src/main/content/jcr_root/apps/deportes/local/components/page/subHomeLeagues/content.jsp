<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@ page import="com.day.cq.wcm.api.WCMMode" pageEncoding="utf-8" %><%
%>
<fmt:message key="td.container.label.one" var="centralContainer1" />
<fmt:message key="td.container.label.two" var="centralContainer2" />
<fmt:message key="td.container.label.three" var="centralContainer3" />
<fmt:message key="td.sidecontainer.label.one.left" var="sideContainer1Left" />
<fmt:message key="td.sidecontainer.label.one.right" var="sideContainer1Right" />
<fmt:message key="td.sidecontainer.label.two.left" var="sideContainer2Left" />
<fmt:message key="td.sidecontainer.label.two.right" var="sideContainer2Right" />
<fmt:message key="td.sidecontainer.label.three.left" var="sideContainer3Left" />
<fmt:message key="td.sidecontainer.label.three.right" var="sideContainer3Right" />

<!-- begin section -->
<section class="td_subhome_nba_01">
    <header class="header1">
        <cq:include path="navHeader" resourceType="/apps/commons/components/content/base/reference" />
        <cq:include path="wdgTeams" resourceType="foundation/components/parsys" />
    </header>

    <!-- begin main -->
    <div class="main">

        <div class="c1">
            <cq:include path="artBranch01" resourceType="/apps/deportes/generics/components/content/article/artBranch01"  />
        </div>

        <!-- central container one -->
        <div class="c2 left">
            <c:if test="${authorMode}">
                ${centralContainer1}
            </c:if>
            <cq:include path="containerOne" resourceType="foundation/components/parsys" />
        </div>
        <div class="c3">
            <div class="c3_1 left">
                <c:if test="${authorMode}">
                    ${sideContainer1Left}
                </c:if>
                <cq:include path="containerOneLeft" resourceType="foundation/components/parsys"  />
            </div>
            <div class="c3_2 right">
                <c:if test="${authorMode}">
                    ${sideContainer1Right}
                </c:if>
                <cq:include path="containerOneRight" resourceType="foundation/components/parsys"  />
            </div>
        </div>

        <!-- central container two -->
        <div class="c4 left">
            <div class="c4_1">
                <c:if test="${authorMode}">
                    ${centralContainer2}
                </c:if>
                <cq:include path="containerTwo" resourceType="foundation/components/parsys" />
            </div>
        </div>
        <!-- side container two -->
        <div class="c5 right">
            <div class="c5_1 left">
                <c:if test="${authorMode}">
                    ${sideContainer2Left}
                </c:if>
                <cq:include path="containerTwoLeft" resourceType="foundation/components/parsys"  />
            </div>
            <div class="c5_2 right">
                <c:if test="${authorMode}">
                    ${sideContainer2Right}
                </c:if>
                <cq:include path="containerTwoRight" resourceType="foundation/components/parsys"  />
            </div>
        </div>

        <!-- central container three -->
        <div class="c7 left">
            <c:if test="${authorMode}">
                ${centralContainer3}
            </c:if>
            <cq:include path="containerThree" resourceType="foundation/components/parsys" />
        </div>
        <!-- side container three-->
        <div class="c8">
            <div class="c8_1 left">
                <c:if test="${authorMode}">
                    ${sideContainer3Left}
                </c:if>
                <cq:include path="containerThreeLeft" resourceType="foundation/components/parsys"  />
            </div>
            <div class="c8_2 right">
                <c:if test="${authorMode}">
                    ${sideContainer3Right}
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
