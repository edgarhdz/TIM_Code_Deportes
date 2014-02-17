<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@page import="com.day.cq.wcm.api.WCMMode" pageEncoding="utf-8" %><%
%>

<fmt:message key="td.container.label.one" var="centralContainer1" />
<fmt:message key="td.container.label.two" var="centralContainer2" />
<fmt:message key="td.sidecontainer.label.one.left" var="sideContainer1Left" />
<fmt:message key="td.sidecontainer.label.one.right" var="sideContainer1Right" />
<fmt:message key="td.sidecontainer.label.two.left" var="sideContainer2Left" />
<fmt:message key="td.sidecontainer.label.two.right" var="sideContainer2Right" />

<!-- begin section -->
<section class="td_video_detalle_01">
    <header class="header1 top_space">
        <cq:include resourceType="/apps/commons/components/content/base/reference" path="header" />
    </header>
    <!-- main -->
    <div class="main">
        <div class="c2">
            <div class="c2_1">
                <cq:include path="video" resourceType="/apps/deportes/generics/components/content/video/video" />
            </div>
        </div>
        <!-- side container one -->
        <div class="c3">
            <div class="c3_1 left">
                <c:if test="${authorMode}">
                    ${sideContainer1Left}
                </c:if>
                <cq:include path="containerOneLeft" resourceType="foundation/components/parsys" />
            </div>
        </div>

        <!-- central container one -->
        <div class="c3_banner">
            <c:if test="${authorMode}">
                ${centralContainer1}
            </c:if>
            <cq:include path="containerOne" resourceType="foundation/components/parsys" />
        </div>

        <!-- comments component -->
        <div class="c4">
            <cq:include path="comments" resourceType="/apps/deportes/generics/components/content/widgets/wdgComen01"/>
        </div>

        <!-- side container two -->
        <div class="c5">
            <div class="c5_1 left">
                <c:if test="${authorMode}">
                    ${sideContainer2Left}
                </c:if>
                <cq:include path="containerTwoLeft" resourceType="foundation/components/parsys" />
            </div>
            <div class="c5_2 right">
                <c:if test="${authorMode}">
                    ${sideContainer2Right}
                </c:if>
                <cq:include path="containerTwoRight" resourceType="foundation/components/parsys" />
            </div>
        </div>
    </div>
    <!-- end main -->
    <footer class="footerPleca">
        <div class="footer" style="clear: both">
            <cq:include resourceType="/apps/commons/components/content/base/reference" path="footer" />
            <cq:include resourceType="/apps/commons/components/content/base/reference" path="corporateFooter" />
        </div>
    </footer>
</section>
