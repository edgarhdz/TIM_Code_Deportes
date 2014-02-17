<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@ page import="com.day.cq.wcm.api.WCMMode" pageEncoding="utf-8" %><%
%>
<fmt:message key="td.container.label.one" var="centralContainer1" />
<fmt:message key="td.sidecontainer.label.one.left" var="sideContainer1Left" />
<fmt:message key="td.sidecontainer.label.one.right" var="sideContainer1Right" />

<!-- begin template -->
<section class="td_opinion_blog_01">
    <header class="header1 top_space">
        <cq:include path="navHeader" resourceType="/apps/commons/components/content/base/reference" />
    </header>

    <!-- main -->
    <div class="main">
        <div class="topTitle">
            <cq:include path="artBranch01" resourceType="/apps/deportes/generics/components/content/article/artBranch01"/>
        </div>
        <!-- central container one -->
        <div class="c1">
            <div class="c1_1">
                <c:if test="${authorMode}">
                    ${centralContainer1}
                </c:if>
                <cq:include path="containerOne"  resourceType="foundation/components/parsys"  />
            </div>
        </div>

        <!-- side container one -->
    <div class="c2">
        <div class="c2_2 left">
            <c:if test="${authorMode}">
                ${sideContainer1Left}
            </c:if>
            <cq:include path="containerOneLeft" resourceType="foundation/components/parsys"  />
        </div>
        <div class="c2_3 right">
            <c:if test="${authorMode}">
                ${sideContainer1Right}
            </c:if>
            <cq:include path="containerOneRight" resourceType="foundation/components/parsys"  />
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