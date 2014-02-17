<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%><%@page import="com.day.cq.wcm.api.WCMMode,java.util.Iterator,com.adobe.cq.social.blog.Blog,com.adobe.cq.social.blog.BlogManager, java.util.List"%>
<%@page import="java.awt.print.Pageable,com.adobe.cq.social.blog.BlogEntry" %>
<%@ page import="java.text.SimpleDateFormat,java.text.DateFormat,java.util.Date,
                    com.day.cq.commons.jcr.*" %>
<fmt:message key="deportes.wdgopinion01.placeholder" var="placeholder"/>
<%

    BlogManager blogMgr = resource.getResourceResolver().adaptTo(BlogManager.class);
    String blogPath = properties.get("firstpath", "/content/televisa");
    Blog blog = blogMgr.getBlog(blogPath);
    Boolean hasBlog = false;

    if (blog != null){
        hasBlog = true;
        pageContext.setAttribute("hasBlog", hasBlog);
        DateFormat sdf = new SimpleDateFormat("d. MMM. yyyy");
        String userId=null;
        String familyName="";
        String givenName="";
        int count=0;
        List<BlogEntry> entries = blog.getEntries();
        BlogEntry firstEntry=null;
        BlogEntry secondEntry=null;

        Iterator<BlogEntry> entriesIterator = entries.iterator();

        while(entriesIterator.hasNext() && count<2){
            switch (count){
                case 0:
                    firstEntry=(BlogEntry)entriesIterator.next();
                    pageContext.setAttribute("firstAuthor", firstEntry.getAuthor()+" ");
                    pageContext.setAttribute("firstDate",sdf.format((Date) firstEntry.getDate()));
                    pageContext.setAttribute("firstTitle",firstEntry.getTitle());
                    pageContext.setAttribute("firstURL",firstEntry.getUrl());
                    pageContext.setAttribute("firstTitle",firstEntry.getTitle());
                    count++;
                    break;
                case 1:
                    secondEntry=(BlogEntry)entriesIterator.next();
                    pageContext.setAttribute("secondAuthor", secondEntry.getAuthor()+" ");
                    pageContext.setAttribute("secondDate",sdf.format((Date)secondEntry.getDate()));
                    pageContext.setAttribute("secondTitle",secondEntry.getTitle());
                    pageContext.setAttribute("secondURL",secondEntry.getUrl());
                    pageContext.setAttribute("secondTitle",secondEntry.getTitle());
                    count++;
                    break;
            }
        }
    }

%>


<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>
<!-- BEGIN: wdg_opinion_01 -->
<c:if test="${!hasBlog && authorMode}">
    <h4 class="cq-texthint-placeholder">${placeholder}</h4>
</c:if>
<c:if test="${hasBlog}">
    <div class="wdg_opinion_01">
        <!-- BEGIN: str_pleca_01 -->
        <cq:include path="strPleca01" resourceType="/apps/deportes/generics/components/content/structure/strPleca01" />
        <!-- END: str_pleca_01 -->
            <div class="wdg_opinion_01_blog">
                <ul>
                    <li>
                        <a href="${firstURL}" title="${firstTitle}">
                            <div>
                                <div>
                                    <i class="tvsa-comments tvsa-3x"></i>
                                </div>
                                <div class="container_blog">
                                    <p class="autor">
                                        <span class="textcolor-title1 name">${firstAuthor}</span>
                                        <span class="name">| ${firstDate}</span>
                                    </p>
                                    <p class="blog">${firstTitle}.</p>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="${secondURL}" title="${secondTitle}">
                            <div>
                                <div>
                                    <i class="tvsa-comments tvsa-3x"></i>
                                </div>
                                <div class="container_blog">
                                    <p class="autor">
                                        <span class="textcolor-title1 name">${secondAuthor}</span>
                                        <span class="name">| ${secondDate}</span>
                                    </p>
                                    <p class="blog">${secondTitle}.</p>
                                </div>
                            </div>
                        </a>
                    </li>
                </ul>
            </div>
    </div>
    <!-- END: wdg_opinion_01 -->
</c:if>

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>