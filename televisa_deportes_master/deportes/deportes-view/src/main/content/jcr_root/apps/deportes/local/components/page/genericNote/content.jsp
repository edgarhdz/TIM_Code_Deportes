<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@ page import="com.day.cq.wcm.api.WCMMode" pageEncoding="utf-8" %><%

    Style bannerStyle = currentDesign.getStyle("/article/banner");
    String hideOutOfPage = bannerStyle.get("hideOutOfPage", "false");

    pageContext.setAttribute("hideOutOfPageBanners", hideOutOfPage);
%>
<fmt:message key="td.container.label.one" var="centralContainer1" />
<fmt:message key="td.sidecontainer.label.one.left" var="sideContainer1Left" />
<fmt:message key="td.sidecontainer.label.one.right" var="sideContainer1Right" />
<fmt:message key="td.sidecontainer.label.two.left" var="sideContainer2Left" />
<fmt:message key="td.sidecontainer.label.two.right" var="sideContainer2Right" />
<fmt:message key="td.sidecontainer.label.three.left" var="sideContainer3Left" />
<fmt:message key="td.sidecontainer.label.three.right" var="sideContainer3Right" />

<!-- begin template -->
<section class="td_note_generic_01">
    <header class="header1 top_space">
        <cq:include path="navHeader" resourceType="/apps/commons/components/content/base/reference"/>
    </header>
    <!-- main -->
    <div class="main">
        <!-- banners -->
        <div class="c1_1 banner_template">
            <c:if test="${hideOutOfPageBanners == 'false'}">
                <!-- Start Out Of Page Banners -->
                <div class="out-of-page-banners">
                    <div id="dfp_slot_oop" class="out-of-page">
                        <script type="text/javascript">
                            googletag.cmd.push(function() {
                                googletag.display('dfp_slot_oop');
                            });
                        </script>
                    </div>
                    <div id="dfp_slot_skin" class="out-of-page">
                        <script type="text/javascript">
                            googletag.cmd.push(function() {
                                googletag.display('dfp_slot_skin');
                            });
                        </script>
                    </div>
                </div>
                <!-- End Out Of Page Banners -->
            </c:if>
        </div>
        <!-- central container one -->
        <div class="c2 left">
            <c:if test="${authorMode}">
                ${centralContainer1}
            </c:if>
            <div class="c2_1">
                <cq:include path="article" resourceType="deportes/generics/components/content/article/article"/>
            </div>
            <cq:include path="articleComments" resourceType="/apps/deportes/generics/components/content/widgets/wdgComen01"/>
        </div>

        <div class="c3 right">
            <!-- sidebar part one-->
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

            <!-- sidebar part two-->
            <div class="c3_3 left">
                <c:if test="${authorMode}">
                    ${sideContainer2Left}
                </c:if>
                <cq:include path="containerTwoLeft" resourceType="foundation/components/parsys"  />
            </div>
            <div class="c3_4 right">
                <c:if test="${authorMode}">
                    ${sideContainer2Right}
                </c:if>
                <cq:include path="containerTwoRight" resourceType="foundation/components/parsys"  />
            </div>

            <!-- sidebar part three-->
            <div class="c3_6 left">
                <c:if test="${authorMode}">
                    ${sideContainer3Left}
                </c:if>
                <cq:include path="containerThreeLeft" resourceType="foundation/components/parsys"  />
            </div>
            <div class="c3_7 right">
                <c:if test="${authorMode}">
                    ${sideContainer3Right}
                </c:if>
                <cq:include path="containerThreeRight" resourceType="foundation/components/parsys"  />
            </div>
        </div>
        <!-- sidebar part one-->

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