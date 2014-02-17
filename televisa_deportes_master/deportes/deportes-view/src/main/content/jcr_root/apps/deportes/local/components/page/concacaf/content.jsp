<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@ page import="com.day.cq.wcm.api.WCMMode" pageEncoding="utf-8" %><%
%>
<fmt:message key="td.container.label.one" var="centralContainer1" />
<fmt:message key="td.container.label.two" var="centralContainer2" />
<fmt:message key="td.sidecontainer.label.one.left" var="sideContainer1Left" />
<fmt:message key="td.sidecontainer.label.one.right" var="sideContainer1Right" />

<!-- begin section -->
<section class="td_concacaf_f1_01">
    <header class="header1">
        <cq:include path="navHeader" resourceType="/apps/commons/components/content/base/reference" />
        <cq:include path="genericWidget" resourceType="/apps/deportes/local/components/content/widgets/customWidget"/>
    </header>

    <!-- main -->
    <div class="main">
        <!--banners -->

        <!-- central container one -->
        <div class="c2 left">
            <div class="c2_1">
                <c:if test="${authorMode}">
                    ${centralContainer1}
                </c:if>
                <cq:include path="containerOne" resourceType="foundation/components/parsys"  />
            </div>
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
        <!-- central container two> -->
        <div class="c4 left">
            <div class="c4_1">
                <c:if test="${authorMode}">
                    ${centralContainer2}
                </c:if>
                <cq:include path="containerTwo" resourceType="foundation/components/parsys"  />
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