<%--

  weather-small component.



--%><%
%><%@include file="/apps/commons/components/global.jsp"%><%
%><%@page session="false"
          pageEncoding="utf-8"
          import="java.text.SimpleDateFormat,java.util.Locale,com.day.cq.commons.Doctype,java.util.Date,java.util.Iterator,com.day.cq.wcm.api.PageFilter,com.day.cq.wcm.api.Page" %><%
%><%
    String[] sections = properties.get( "sections", new String[0] );
    String[] sectionTitles = properties.get( "sectionTitles", new String[0] );
%>


<!-- tdn starts -->
<c:choose>
    <c:when test="${properties.tdnLinkType == 'internal'}">
        <c:if test="${not empty properties.tdnInternalLink}">
            <c:set var="tdnlink" value="${properties.tdnInternalLink}.html" />
        </c:if>
    </c:when>
    <c:otherwise>
        <c:set var="tdnlink" value="${properties.tdnExternalLink}" />
    </c:otherwise>
</c:choose>
<c:if test="${empty tdnlink}">
    <c:set var="tdnlink" value="#"/>
</c:if>
<c:if test="${not empty properties.tdnLinkTarget && properties.tdnLinkTarget != 'select'}">
    <c:set var="tdntarget" value="target= '${properties.tdnLinkTarget}'"/>
</c:if>
<c:if test="${not empty properties.tdnLinkTitle}">
    <c:set var="tdntitle" value="title= '${properties.tdnLinkTitle}'" />
</c:if>
<c:set var="tdntext" value="${properties.tdnLinkText}"/>
<c:if test="${empty tdntext}">
    <c:set var="tdntext" value=""/>
</c:if>
<!-- tdn ends -->

<!-- tdntv starts -->
<c:choose>
    <c:when test="${properties.tdntvLinkType == 'internal'}">
        <c:if test="${not empty properties.tdntvInternalLink}">
            <c:set var="tdntvlink" value="${properties.tdntvInternalLink}.html" />
        </c:if>
    </c:when>
    <c:otherwise>
        <c:set var="tdntvlink" value="${properties.tdntvExternalLink}" />
    </c:otherwise>
</c:choose>
<c:if test="${empty tdntvlink}">
    <c:set var="tdntvlink" value="#"/>
</c:if>
<c:if test="${not empty properties.tdntvLinkTarget && properties.tdntvLinkTarget != 'select'}">
    <c:set var="tdntvtarget" value="target= '${properties.tdntvLinkTarget}'"/>
</c:if>
<c:if test="${not empty properties.tdntvLinkTitle}">
    <c:set var="tdntvtitle" value="title= '${properties.tdntvLinkTitle}'" />
</c:if>
<c:set var="tdntvtext" value="${properties.tdntvLinkText}"/>
<c:if test="${empty tdntvtext}">
    <c:set var="tdntvtext" value=""/>
</c:if>
<!-- tdntv ends -->
<!-- photo starts -->
<c:choose>
    <c:when test="${properties.photoLinkType == 'internal'}">
        <c:if test="${not empty properties.photoInternalLink}">
            <c:set var="photolink" value="${properties.photoInternalLink}.html" />
        </c:if>
    </c:when>
    <c:otherwise>
        <c:set var="photolink" value="${properties.photoExternalLink}" />
    </c:otherwise>
</c:choose>
<c:if test="${empty photolink}">
    <c:set var="photolink" value="#"/>
</c:if>
<c:if test="${not empty properties.photoLinkTarget && properties.photoLinkTarget != 'select'}">
    <c:set var="phototarget" value="target= '${properties.photoLinkTarget}'"/>
</c:if>
<c:if test="${not empty properties.photoLinkTitle}">
    <c:set var="phototitle" value="title= '${properties.photoLinkTitle}'" />
</c:if>
<c:set var="phototext" value="${properties.photoLinkText}"/>
<c:if test="${empty phototext}">
    <c:set var="phototext" value=""/>
</c:if>
<!-- photo starts -->
<!-- twitter starts -->
<c:choose>
    <c:when test="${properties.twitterLinkType == 'internal'}">
        <c:if test="${not empty properties.twitterInternalLink}">
            <c:set var="twitterlink" value="${properties.twitterInternalLink}.html" />
        </c:if>
    </c:when>
    <c:otherwise>
        <c:set var="twitterlink" value="${properties.twitterExternalLink}" />
    </c:otherwise>
</c:choose>
<c:if test="${empty twitterlink}">
    <c:set var="twitterlink" value="#"/>
</c:if>
<c:if test="${not empty properties.twitterLinkTarget && properties.twitterLinkTarget != 'select'}">
    <c:set var="twittertarget" value="target= '${properties.twitterLinkTarget}'"/>
</c:if>
<c:if test="${not empty properties.twitterLinkTitle}">
    <c:set var="twittertitle" value="title= '${properties.twitterLinkTitle}'" />
</c:if>
<c:set var="twittertext" value="${properties.twitterLinkText}"/>
<c:if test="${empty twittertext}">
    <c:set var="twittertext" value=""/>
</c:if>
<!-- twitter starts -->
<!-- facebook starts -->
<c:choose>
    <c:when test="${properties.facebookLinkType == 'internal'}">
        <c:if test="${not empty properties.facebookInternalLink}">
            <c:set var="facebooklink" value="${properties.facebookInternalLink}.html" />
        </c:if>
    </c:when>
    <c:otherwise>
        <c:set var="facebooklink" value="${properties.facebookExternalLink}" />
    </c:otherwise>
</c:choose>
<c:if test="${empty facebooklink}">
    <c:set var="facebooklink" value="#"/>
</c:if>
<c:if test="${not empty properties.facebookLinkTarget && properties.facebookLinkTarget != 'select'}">
    <c:set var="facebooktarget" value="target= '${properties.facebookLinkTarget}'"/>
</c:if>
<c:if test="${not empty properties.facebookLinkTitle}">
    <c:set var="facebooktitle" value="title= '${properties.facebookLinkTitle}'" />
</c:if>
<c:set var="facebooktext" value="${properties.facebookLinkText}"/>
<c:if test="${empty facebooktext}">
    <c:set var="facebooktext" value=""/>
</c:if>
<!-- facebook starts -->
<!-- home starts -->
<c:choose>
    <c:when test="${properties.homeLinkType == 'internal'}">
        <c:if test="${not empty properties.homeInternalLink}">
            <c:set var="homelink" value="${properties.homeInternalLink}.html" />
        </c:if>
    </c:when>
    <c:otherwise>
        <c:set var="homelink" value="${properties.homeExternalLink}" />
    </c:otherwise>
</c:choose>
<c:if test="${empty homelink}">
    <c:set var="homelink" value="#"/>
</c:if>
<c:if test="${not empty properties.homeLinkTarget && properties.homeLinkTarget != 'select'}">
    <c:set var="hometarget" value="target= '${properties.homeLinkTarget}'"/>
</c:if>
<c:if test="${not empty properties.homeLinkTitle}">
    <c:set var="hometitle" value="title= '${properties.homeLinkTitle}'" />
</c:if>
<c:set var="hometext" value="${properties.homeLinkText}"/>
<c:if test="${empty hometext}">
    <c:set var="hometext" value=""/>
</c:if>
<!-- home ends -->
<!-- sections starts -->




<!-- division -->

<div class="mainnav">
    <div class="inner">
        <nav class="main">
            <ul>
                <li><a href="${homelink}"   ${hometarget}    ${hometitle}><i class="tvsa-home td"></i></a></li>
                <%
                    for( int n = 0; n < sections.length && n < 4; n++ ){
                        String title = "";
                        String url ="#";
                        if(!sections[n].isEmpty()){
                            Page act_section_page = slingRequest.getResourceResolver().getResource( sections[ n ] ).adaptTo( Page.class );
                            if (sectionTitles.length > 0) {
                                title = sectionTitles[n].isEmpty() ? act_section_page.getTitle() : sectionTitles[n];
                            } else {
                                title = act_section_page.getTitle();
                            }
                            url = sections[n];

                %>
                        <li><a href="<%=url%>.html" class="nav_header_01_subsect"> <%= title %> </a></li>
                <%
                        }
                    }
                    if( sections.length >= 4 ){
                %>
                    <li class="mas-deportes"><a href="#">
                        Secciones
                        <i class="tvsa-caret-down"></i>
                    </a>
                        <ul class="dropdown">
                            <% for( int n=4; n<sections.length && n >= 4; n++ ){
                                String title = "";
                                String url ="#";
                                    if(!sections[n].isEmpty()){
                                        Page act_section_page = slingRequest.getResourceResolver().getResource( sections[ n ] ).adaptTo( Page.class );
                                        if (sectionTitles.length > 0) {
                                            title = sectionTitles[n].isEmpty() ? act_section_page.getTitle() : sectionTitles[n];
                                        } else {
                                            title = act_section_page.getTitle();
                                        }
                                        url = sections[n];
                                    %>
                                    <li><a href="<%=url%>.html" class="nav_header_01_subsect"><%=title%></a></li>
                            <%
                                    }
                                }
                            %>
                        </ul>
                    </li>

                    <% } %>

            </ul>
        </nav>


        <nav class="social">
            <a href="#" class="toggle">
                <span><i class="tvsa-double-caret-down"></i></span>
            </a>
            <ul>
                <li class="station"><a href="${tdntvlink}" ${tdntvtarget}    ${tdntvtitle}><span class="tdtv"></span></a></li>
                <li class="station"><a href="${tdnlink}" ${tdntarget} ${tdntitle}><span class="tdn"></span></a></li>
                <li class="social"><a href="${photolink}" ${phototarget} ${phototitle}><i class="tvsa-camera td"></i></a></li>
                <li class="social"><a href="${twitterlink}" ${twittertarget}  ${twittertitle}><i class="tvsa-twitter twitter"></i></a></li>
                <li class="social"><a href="${facebooklink}" ${facebooktarget} ${facebooktitle}><i class="tvsa-facebook facebook"></i></a></li>
            </ul>
        </nav>
    </div>
</div>
<!--</div>  -->

<!-- sections ends -->