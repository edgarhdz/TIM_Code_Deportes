<%--

 ADOBE CONFIDENTIAL
 __________________

  Copyright 2011 Adobe Systems Incorporated
  All Rights Reserved.

 NOTICE:  All information contained herein is, and remains
 the property of Adobe Systems Incorporated and its suppliers,
 if any.  The intellectual and technical concepts contained
 herein are proprietary to Adobe Systems Incorporated and its
 suppliers and are protected by trade secret or copyright law.
 Dissemination of this information or reproduction of this material
 is strictly forbidden unless prior written permission is obtained
 from Adobe Systems Incorporated.

  ==============================================================================

  Blog: Content script (included by body.jsp)

  ==============================================================================

--%><%@ page session="false" import="com.adobe.cq.social.blog.Blog,
                     com.adobe.cq.social.blog.BlogManager,
                     com.day.cq.i18n.I18n" %><%
%><%@include file="/libs/foundation/global.jsp"%>
<%@ page import="com.day.cq.wcm.api.WCMMode" pageEncoding="utf-8" %><%

    BlogManager blogMgr = resource.getResourceResolver().adaptTo(BlogManager.class);
    Blog blog = blogMgr.getBlog(slingRequest);
	pageContext.setAttribute("isNotEntry",!blog.isEntry());	

    I18n i18n = new I18n(slingRequest);
    pageContext.setAttribute("sideContainer1Left", i18n.getVar("td.sidecontainer.label.one.left"));
    pageContext.setAttribute("sideContainer1Right", i18n.getVar("td.sidecontainer.label.one.right"));
%>

<!-- begin template -->
<section class="td_opinion_blog_01">
    <header class="header1 top_space">
        <cq:include path="navHeader" resourceType="/apps/commons/components/content/base/reference" />
    </header>

    <!-- main -->
    <div class="main">
        <div class="topTitle">
            <cq:include path="artBranch01"  resourceType="/apps/deportes/generics/components/content/article/artBranch01" />
        </div>

        <!-- central container one -->
        <div class="c1">
            <div class="c1_1">
                <c:if test="${isNotEntry}">
					<cq:include path="containerOneUp"  resourceType="foundation/components/parsys" />
                </c:if>
                <cq:include path="blogmain" resourceType="social/blog/components/main" />
            </div>
        </div>

        <!-- side container two -->
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




