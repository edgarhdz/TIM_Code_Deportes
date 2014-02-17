<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@page import="com.day.cq.wcm.api.WCMMode" pageEncoding="utf-8" %><%
%>

<fmt:message key="td.container.label.one" var="centralContainer1" />
<fmt:message key="td.sidecontainer.label.one.left" var="sideContainer1Left" />
<fmt:message key="td.sidecontainer.label.one.right" var="sideContainer1Right" />

<section class="td_video_chat_01">
    <header class="header1">
        <cq:include path="header" resourceType="/apps/commons/components/content/base/reference" />
    </header>

    <!-- main -->
    <div class="main">
        <!-- central container one -->
        <div class="c2 left ">
            <c:if test="${authorMode}">
                ${centralContainer1}
            </c:if>
            <cq:include path="containerOne" resourceType="foundation/components/parsys"/>
        </div>

        <!-- side container one -->
        <div class="c3 right">
            <div class="c3_1 left">
                <c:if test="${authorMode}">
                    ${sideContainer1Left}
                </c:if>
                <cq:include path="containerOneLeft" resourceType="foundation/components/parsys" />
            </div>
            <div class="c3_2 right">
                <c:if test="${authorMode}">
                    ${sideContainer1Right}
                </c:if>
                <cq:include path="containerOneRight" resourceType="foundation/components/parsys" />
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
<!-- end template -->