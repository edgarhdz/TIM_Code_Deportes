<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@ page import="com.day.cq.wcm.api.WCMMode" pageEncoding="utf-8" %><%
%>
<fmt:message key="td.container.label.one" var="centralContainer1" />
<fmt:message key="td.container.label.two" var="centralContainer2" />
<fmt:message key="td.container.label.three" var="centralContainer3"/>
<fmt:message key="td.container.label.four" var="centralContainer4"/>
<fmt:message key="td.sidecontainer.label.one.left" var="sideContainer1Left" />
<fmt:message key="td.sidecontainer.label.one.right" var="sideContainer1Right" />
<fmt:message key="td.sidecontainer.label.two.left" var="sideContainer2Left" />
<fmt:message key="td.sidecontainer.label.two.right" var="sideContainer2Right" />
<fmt:message key="td.sidecontainer.label.three.left" var="sideContainer3Left" />
<fmt:message key="td.sidecontainer.label.three.right" var="sideContainer3Right" />

<!-- begin template -->
<section class="td_home_main_01">
    <header class="header1">
        <cq:include path="navHeader" resourceType="/apps/commons/components/content/base/reference"/>
        <div class="c0">
            <cq:include path="genericWidget" resourceType="/apps/deportes/local/components/content/widgets/customWidget"/>
        </div>
    </header>

    <!-- main -->
    <div class="main">
        <!-- banners -->
        <div class="c1">
        </div>
        <!-- central container one -->
        <div class="c2">
            <c:if test="${authorMode}">
                ${centralContainer1}
            </c:if>
            <cq:include path="containerOne" resourceType="foundation/components/parsys"/>
        </div>
        <!-- sidebar part one-->
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
        <div class="c3_">
            <c:if test="${authorMode}">
                ${centralContainer2}
            </c:if>
            <div style="position:relative"></div>
            <cq:include path="containerTwo" resourceType="foundation/components/parsys"/>
        </div>

        <!-- central container three -->
        <div class="c4">
            <c:if test="${authorMode}">
                ${centralContainer3}
            </c:if>
            <cq:include path="containerThree" resourceType="foundation/components/parsys"/>
        </div>
        <!-- sidebar part two-->
        <div class="c5">
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
        <!-- central container four -->
        <div class="c6">
            <c:if test="${authorMode}">
                ${centralContainer4}
            </c:if>
            <cq:include path="containerFor" resourceType="foundation/components/parsys"/>
        </div>
        <!-- sidebar part three-->
        <div class="c7">
            <div class="c7_5 left">
                <c:if test="${authorMode}">
                    ${sideContainer3Left}
                </c:if>
                <cq:include path="containerThreeLeft" resourceType="foundation/components/parsys"  />
            </div>
            <div class="c7_2 right">
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