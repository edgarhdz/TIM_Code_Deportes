<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@ page import="com.day.cq.wcm.api.WCMMode" pageEncoding="utf-8" %><%
%>
<fmt:message key="td.container.label.one" var="centralContainer1" />
<fmt:message key="td.container.label.two" var="centralContainer2" />
<fmt:message key="td.sidecontainer.label.one.left" var="sideContainer1Left" />
<fmt:message key="td.sidecontainer.label.one.right" var="sideContainer1Right" />
<!-- begin template -->
<section class="td_videos_progs_01">

    <header class="header1 top_space">
        <cq:include path="navHeader" resourceType="/apps/commons/components/content/base/reference"/>
    </header>

    <!-- begin main -->
    <div class="main">
        <!-- central container one -->
        <div class="c2">
            <c:if test="${authorMode}">
                ${centralContainer1}
            </c:if>
            <cq:include path="program" resourceType="/apps/deportes/generics/components/content/article/articleProgram"/>
        </div>
        <!-- sidebar part one-->
        <div class="c3">
            <div class="c3_1 left">
                <c:if test="${authorMode}">
                    ${sideContainer1Left}
                </c:if>
                <cq:include path="containerOneLeft" resourceType="foundation/components/parsys"  />
            </div>
            <div class="c3_3 right">
                <c:if test="${authorMode}">
                    ${sideContainer1Right}
                </c:if>
                <cq:include path="containerOneRight" resourceType="foundation/components/parsys"  />
            </div>
        </div>
        <!-- central container two -->
        <div class="c4">
            <c:if test="${authorMode}">
                ${centralContainer1}
            </c:if>
            <cq:include path="containerOne" resourceType="foundation/components/parsys"/>
        </div>
        <!-- central container two -->
        <div class="c6">
            <c:if test="${authorMode}">
                ${centralContainer2}
            </c:if>
            <cq:include path="containerTwo" resourceType="foundation/components/parsys"/>
        </div>
    </div>
    <!-- end main -->   <!-- footer -->
    <footer class="footerPleca">
        <div class="footer" style="clear: both">
            <cq:include path="footer" resourceType="/apps/commons/components/content/base/reference" />
            <cq:include path="corporateFooter" resourceType="/apps/commons/components/content/base/reference" />
        </div>
    </footer>
</section>
<!-- end template -->