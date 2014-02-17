<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@ page import="com.day.cq.wcm.api.WCMMode" pageEncoding="utf-8" %><%

    Style bannerStyle = currentDesign.getStyle("/article/banner");
    String hideOutOfPage = bannerStyle.get("hideOutOfPage", "false");

    pageContext.setAttribute("hideOutOfPageBanners", hideOutOfPage);
%>
<fmt:message key="td.container.label.one" var="centralContainer1" />

<!-- begin template -->
<section class="td_newsletter_01">
    <header class="header1">
        <cq:include path="navHeader" resourceType="/apps/commons/components/content/base/reference"/>
    </header>

    <!-- main -->
    <div class="main">
        <!-- banners -->
        <div class="c2">
            <cq:include path="standOutTeaser" resourceType="/apps/commons/components/content/mix/standoutTeaser"/>
            <cq:include path="containerOne" resourceType="foundation/components/parsys"  />
        </div>
        <!-- end main -->
    </div>
    <!-- footer -->
    <footer class="footerPleca">
        <div class="footer" style="clear: both">
            <cq:include path="corporateFooter" resourceType="/apps/commons/components/content/base/reference" />
        </div>
    </footer>
</section>
<!-- end section -->