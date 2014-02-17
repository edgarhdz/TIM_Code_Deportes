<%--

  weather-small component.

  

--%><%
%><%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%><%
%><%@page session="false"
            import="java.text.SimpleDateFormat,java.util.Locale,com.day.cq.commons.Doctype,java.util.Date,java.util.Iterator,com.day.cq.wcm.api.PageFilter,com.day.cq.wcm.api.Page" %><%
%><%
    String[] sections = properties.get( "sections", new String[]{} );
    
%>


<!-- youtube starts -->
<c:choose>
    <c:when test="${properties.youtubeLinkType == 'internal'}">
        <c:if test="${not empty properties.youtubeInternalLink}">
            <c:set var="youtubelink" value="${properties.youtubeInternalLink}.html" />  
        </c:if>    
    </c:when>
    <c:otherwise>
        <c:set var="youtubelink" value="${properties.youtubeExternalLink}" />    
    </c:otherwise>
</c:choose>
<c:if test="${empty youtubelink}">
    <c:set var="youtubelink" value="#"/>
</c:if>    
<c:if test="${not empty properties.youtubeLinkTarget && properties.youtubeLinkTarget != 'select'}">
    <c:set var="youtubetarget" value="target= '${properties.youtubeLinkTarget}'"/>
</c:if>
<c:if test="${not empty properties.youtubeLinkTitle}">
    <c:set var="youtubetitle" value="title= '${properties.youtubeLinkTitle}'" />
</c:if>
<c:set var="youtubetext" value="${properties.youtubeLinkText}"/>
<c:if test="${empty youtubetext}">
    <c:set var="youtubetext" value=""/>
</c:if>
<!-- youtube ends -->

<!-- video starts -->
<c:choose>
    <c:when test="${properties.videoLinkType == 'internal'}">
        <c:if test="${not empty properties.videoInternalLink}">
            <c:set var="videolink" value="${properties.videoInternalLink}.html" />  
        </c:if>    
    </c:when>
    <c:otherwise>
        <c:set var="videolink" value="${properties.videoExternalLink}" />    
    </c:otherwise>
</c:choose>
<c:if test="${empty videolink}">
    <c:set var="videolink" value="#"/>
</c:if>    
<c:if test="${not empty properties.videoLinkTarget && properties.videoLinkTarget != 'select'}">
    <c:set var="videotarget" value="target= '${properties.videoLinkTarget}'"/>
</c:if>
<c:if test="${not empty properties.videoLinkTitle}">
    <c:set var="videotitle" value="title= '${properties.videoLinkTitle}'" />
</c:if>
<c:set var="videotext" value="${properties.videoLinkText}"/>
<c:if test="${empty videotext}">
    <c:set var="videotext" value=""/>
</c:if>
<!-- video ends -->
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

           
            <ul class="nav_header_01_social">
                <li><a href="#"><span class="nav_header_01_sprite nav_header_01_logoftv"></span></a></li>
                <li class="nav_header_01_dropdown">
                    <a href="#"><span class="nav_header_01_sprite nav_header_01_dropdown"></span><span class="nav_header_01_sprite nav_header_01_dropdown"></span></a>
                    <ul>
                        <!-- <li><a href="${youtubelink}"  class="nav_header_01_subdropa" ${youtubetarget}    ${youtubetitle}><span class="nav_header_01_sprite nav_header_01_youtube"></a></li> -->
                        <!-- <li><a href="${videolink}"    class="nav_header_01_subdropa" ${videotarget}    ${videotitle}><span class="nav_header_01_sprite nav_header_01_video"></a></li> -->
                        <li><a href="${videolink}"  class="nav_header_01_subdropa" ${videotarget}    ${videotitle}><span class="nav_header_01_sprite nav_header_01_youtube"></a></li> 
                        <li><a href="#"    class="nav_header_01_subdropvid" data-trigger="#sections-dropdown-content2" ><span class="nav_header_01_sprite nav_header_01_video"></a></li>
                        <li><a href="${photolink}"    class="nav_header_01_subdropa" ${phototarget}    ${phototitle}><span class="nav_header_01_sprite nav_header_01_photo"></a></li>
                        <li><a href="${twitterlink}"  class="nav_header_01_subdropa" ${twittertarget}  ${twittertitle}><span class="nav_header_01_sprite nav_header_01_twitter"></a></li>
                        <li><a href="${facebooklink}" class="nav_header_01_subdropa" ${facebooktarget} ${facebooktitle}><span class="nav_header_01_sprite nav_header_01_facebook"></a></li>
                        
                    </ul>
                </li>
            </ul>
             <nav>
                <ul>
                <li><a href="${homelink}"   ${hometarget}    ${hometitle}><span class="nav_header_01_sprite nav_header_01_home"></span></a></li>
                <%
                       for( int n=0; n<sections.length && n < 4; n++ ){
                            Page act_section_page = slingRequest.getResourceResolver().getResource( sections[ n ] ).adaptTo( Page.class );
                            log.error( sections[ n ] );
                            %>
                            <li><a href="<%= sections[ n ] %>.html" class="nav_header_01_subsect"> <%= act_section_page.getTitle() %> </a></li>
                            <%
                        }
                        if( sections.length >= 4 ){
                        %>
                            <li><a href="#" class="nav_header_01_subsect" class="nav_header_01_subsect" data-trigger="#sections-dropdown-content">Secciones <span class="nav_header_01_sprite nav_header_01_dropdown"></span></a></li>
                           
                        <%
                        }
                  %>     
                </ul>
                
            </nav>

        </div>
        
        <div id="sections-dropdown-content" class="nav_header_01_dropdowncnt">
            <div class="nav_header_01_wrapper">
                <ul class="nav_header_01_horizontal">
                   <%
                    if( sections.length >= 4 ) {
                            for( int n=4; n<sections.length ; n++ ){
                                Page act_section_page = slingRequest.getResourceResolver().getResource( sections[ n ] ).adaptTo( Page.class );
                                %>
                                <li><a href="<%= sections[ n ] %>.html"> <%= act_section_page.getTitle() %> </a></li>
                                <%
                            }
                    }
                            %>
                </ul>
            </div>
         </div>
         
       <div id="sections-dropdown-video" class="nav_header_01_dropdownvideo">
            <div class="nav_header_01_wrapper">
                <div class="nav_header_01_videotitle">
                    <h4>Videos
                        <span class="nav_header_01_sprite nav_header_01_rq"></span>
                        <span class="nav_header_01_sprite nav_header_01_rq"></span>
                    </h4>
                </div>
                <div class="nav_header_01_videofirstcolumn">
                    <ul class="nav_header_01_videocol1a">
                    <cq:include path="headerColumnParsys1"
                            resourceType="foundation/components/parsys" />
                    </ul>
                </div>
                
                
               <div class="nav_header_01_videofirstcolumn">
                    <ul class="nav_header_01_videocol1a">   
                        <cq:include path="headerColumnParsys2"
                            resourceType="foundation/components/parsys" />
                </ul></div>
               
               
               
               <div class="nav_header_01_videofirstcolumn">
                    <ul class="nav_header_01_videocol1a">   
                        <cq:include path="headerColumnParsys3"
                            resourceType="foundation/components/parsys" />
                </ul></div>
                
                
                
               
               
                <div class="nav_header_01_videohide">
                    <a href="#" class="nav_header_01_videohidetxt"><h3>Ocultar<span class="nav_header_01_sprite nav_header_01_dropdowngray"></span></h3></a>
                </div>
            </div>
        </div>
        
           
        <!-- sections ends -->