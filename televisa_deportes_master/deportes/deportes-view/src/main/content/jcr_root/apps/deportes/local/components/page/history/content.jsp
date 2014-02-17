<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@page import="com.day.cq.wcm.api.WCMMode" pageEncoding="utf-8" %><%
%>
<fmt:message key="td.sidecontainer.label.one.left" var="sideContainer1Left" />
<fmt:message key="td.sidecontainer.label.one.right" var="sideContainer1Right" />

<!-- begin template -->
<section class="td_history_main_01">
    <header class="header1 top_space">
        <cq:include resourceType="/apps/commons/components/content/base/reference" path="header" />
    </header>

    <!-- main -->
    <div class="main">
        <div class = "topTitle">
            <cq:include resourceType="/apps/deportes/generics/components/content/article/articleBranchTitle" path="articleBranchTitle" />
        </div>
        <!-- central container one -->
        <div class="c1">
            <div class="c1_1">
                <cq:include path="indexByTags" resourceType="/apps/deportes/generics/components/content/mix/indexByTags53" />
            </div>
        </div>
        <!-- sidebar part one-->
        <div class="c2">
            <div class="c2_1 left">
                <c:if test="${authorMode}">
                    ${sideContainer1Left}
                </c:if>
                <cq:include path="containerOneLeft" resourceType="foundation/components/parsys"  />
            </div>
            <div class="c2_2 right">
                <c:if test="${authorMode}">
                    ${sideContainer1Right}
                </c:if>
                <cq:include path="containerOneRight" resourceType="foundation/components/parsys"  />
            </div>
        </div>
    </div>
    <!-- end main -->
    <!-- footer -->
    <footer class="footerPleca">
        <div class="footer" style="clear: both">
            <cq:include path="footer" resourceType="/apps/commons/components/content/base/reference" />
            <cq:include path="corporateFooter" resourceType="/apps/commons/components/content/base/reference" />
        </div>
    </footer>
</section>
<!-- end template -->