<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@ page import="com.day.cq.wcm.api.WCMMode" pageEncoding="utf-8"%>

<fmt:message key="td.container.label.one" var="centralContainer1" />
<fmt:message key="td.sidecontainer.label.one.left" var="sideContainer1Left" />
<fmt:message key="td.sidecontainer.label.one.right" var="sideContainer1Right" />

<!-- begin section -->
<section class="td_multisenal_01">
    <header class="header1 top_space">
        <cq:include path="navHeader" resourceType="/apps/commons/components/content/base/reference" />
        <cq:include path="genericWidget" resourceType="/apps/deportes/local/components/content/widgets/customWidget"/>
    </header>

    <!-- main -->
    <div class="main">
        <!-- central container one -->
        <div class="c2">
            <div class="c2_1">
                <!-- nav_smnu_video01 component -->
            </div>
            <div class="c2_3">
                <cq:include path="livestream" resourceType="/apps/deportes/local/components/content/video/livestream"/>
                <cq:include path="comments" resourceType="/apps/deportes/generics/components/content/widgets/wdgComen01"/>
            </div>
        </div>

        <!-- side container one -->
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

        <!-- central container one -->
        <div class="c4">
            <c:if test="${authorMode}">
                ${centralContainer1}
            </c:if>
            <cq:include path="containerOne" resourceType="foundation/components/parsys"  />
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
<!-- END: <Template> -->