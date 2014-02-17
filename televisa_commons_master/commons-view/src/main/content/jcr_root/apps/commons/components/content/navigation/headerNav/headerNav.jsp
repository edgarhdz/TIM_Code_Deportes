<%--
 * DESCRIPTION
 *  Header component for Noticieros Televisa
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 24/01/2013  | Jose Alejandro Urizar  | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%><%
%><%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%><%
%><%@page session="false"
            import="java.text.SimpleDateFormat,java.util.Locale,com.day.cq.commons.Doctype,java.util.Date,java.util.Iterator,com.day.cq.wcm.api.PageFilter,com.day.cq.wcm.api.Page" %><%
%><%@page
    import="com.televisa.commons.services.datamodel.impl.LinkListImpl"%>

    <%
    
    boolean configMode = "true".equals( slingRequest.getAttribute( "configuration" ) );
    
    String date = "--";
    String searchLabel = properties.get( "searchlabel", "" );
    String searchUrl = properties.get( "searchurl", "" );
    
    String path = currentNode.getPath();
    path = path.substring(0,path.indexOf("/jcr:content/"));
    String confPath  = properties.get( "globalconfiguration", path ) ; 
    
    Node node = resourceResolver.resolve( confPath ).adaptTo( Node.class );
      
%>
<fmt:message key="commons.navheader01.description" var="placeholdermaintitle" />
<header class="main-header"> 
<style>
    .parmessage {
        display: none;
    }
    .cq-paragraphreference-paragraph .parmessage {
        display: block;
    }
    
</style>
<div class="parmessage">
    <br/><br/><br/>headerNav<br/><br/><br/><br/><br/><br/>
</div>
<c:set var="menuListNode" value='<%= new LinkListImpl(currentNode,confPath)%>' />

<!-- logo starts -->
<c:choose>
    <c:when test="${properties.logoLinkType == 'internal'}">
        <c:if test="${not empty properties.logoInternalLink}">
            <c:set var="logolink" value="${properties.logoInternalLink}.html" />  
        </c:if>    
    </c:when>
    <c:otherwise>
        <c:set var="logolink" value="${properties.logoExternalLink}" />    
    </c:otherwise>
</c:choose>
<c:if test="${empty logolink}">
    <c:set var="logolink" value="#"/>
</c:if>    
<c:if test="${not empty properties.logoLinkTarget && properties.logoLinkTarget != 'select'}">
    <c:set var="logotarget" value="target= '${properties.logoLinkTarget}'"/>
</c:if>
<c:if test="${not empty properties.logoLinkTitle}">
    <c:set var="logotitle" value="title= '${properties.logoLinkTitle}'" />
</c:if>
<c:set var="logotext" value="${properties.logoLinkText}"/>
<c:if test="${empty logotext}">
    <c:set var="logotext" value=""/>
</c:if>
<!-- logo ends -->

                                     
<!-- BEGIN: nav_header_01 -->

<style>
    legend { 
        display: block;
    }
</style>    
<header class="nav_header_01" >
    <div class="nav_header_01_top">
        <a href="#menu" class="nav_header_01_mobile">
            <span class="nav_header_01_sprite nav_header_01_menu">Men&uacute; Principal</span>
        </a>
        <a href="${logolink}"  class="nav_header_01_logotv" ${logotarget}    ${logotitle}>
            <span class="nav_header_01_sprite nav_header_01_logotv">${logotext}</span>
        </a>

        <span class="nav_header_01_date" id="date">-</span>
        <a href="#search" class="nav_header_01_search"><span class="nav_header_01_sprite nav_header_01_search">Buscar</span></a>
    </div>

    <div class="nav_header_01_search">
    
     <form method="post" action="/bin/commons/search">
        <input type="text" placeholder="<%= searchLabel %>" name="q"/>
        <input type="hidden" name="url" value="<%= searchUrl %>" />        
     </form>
     
    </div>

    <nav>
        <ul role="menu">
        <!-- Menu List -->
 
        <c:if test="${not empty menuListNode.items[0] && menuListNode.items[0].long!=0}">
             
            <c:forEach var="i" begin="0" end="${menuListNode.items[0].long-1}">
                <c:choose>
                    <c:when test="${menuListNode.link[i].string=='internal'}">
                        <c:set var="link" value="${menuListNode.path[i].string}.html" />
                    </c:when>
                    <c:when test="${menuListNode.link[i].string=='external'}">
                       <%-- <c:set var="link" value="${menuListNode.path[i].string}" /> --%>
                        
                        <c:set var="link" value="${menuListNode.externalPath[i].string}" />
                        
                        <c:choose>
                            <c:when test="${fn:contains(link, 'http://')}">
                            </c:when>
                            <c:otherwise>
                                <c:set var="link" value="http://${link}" />
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                </c:choose>
                <li role="menuitem" class="nav_header_01_${menuListNode.channel[i].string}" id="${menuListNode.configurationPath[i].string}">
                
                <a href="${link}" target="${menuListNode.target[i].string}" title="${menuListNode.title[i].string}">${menuListNode.text[i].string}<span></span></a>
                </li>
            </c:forEach>
        </c:if>
        </ul>
    </nav>

    <div class="nav_header_01_toggleable">
        <div class="nav_header_01_wrapper">
    <%
    
    LinkListImpl list = new LinkListImpl( currentNode, confPath );

    

    if( list.getItems().length > 0 ){
        for( int n = 0; n < list.getItems()[0].getLong() ; n++ ) {
           String act_conf_path = list.getConfigurationPath()[n].getString()+"/jcr:content/header/navheader01";
           String act_social_path  = list.getConfigurationPath()[n].getString()+"/jcr:content/headerFiltering";
           String act_content_path = list.getConfigurationPath()[n].getString()+"/jcr:content/headercontent";
           
           String class_type    = list.getChannel()[n].getString();
           
           Node act_node = resourceResolver.resolve( act_conf_path ).adaptTo( Node.class );

%>        
<c:set var="mainListNode" value='<%= new LinkListImpl( act_node,"mainlist")%>' />
<c:set var="altListNode"  value='<%= new LinkListImpl( act_node,"altlist")%>' />                  
   
        
            <div class="nav_header_01_<%= class_type %>">
                <h5><%= list.getText()[n].getString() %> <span class="nav_header_01_sprite nav_header_01_rq"></span><span class="nav_header_01_sprite nav_header_01_rq"></span></h5>
                <div class="nav_header_01_categories">
                    <h6>Categor&iacute;as</h6>
                    <ul>
                         <c:if
                test="${not empty mainListNode.items[0] && mainListNode.items[0].long!=0}">


                <c:forEach var="i" begin="0" end="${mainListNode.items[0].long-1}">
                    <c:choose>
                        <c:when test="${mainListNode.link[i].string=='internal'}">
                            <c:set var="link" value="${mainListNode.path[i].string}" />
                            <c:if test="${not empty link}">
                                <c:set var="link" value="${mainListNode.path[i].string}.html" /> 
                            </c:if>
                        </c:when>
                        <c:when test="${mainListNode.link[i].string=='external'}">
                            <%-- <c:set var="link" value="${mainListNode.path[i].string}" /> --%>
                            
                            <c:set var="link" value="${mainListNode.externalPath[i].string}" />                            
                            <c:if test="${not empty link}">
                                <c:choose>
                                    <c:when test="${fn:contains(link, 'http://')}">
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="link" value="http://${link}" />
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </c:when>
                    </c:choose>
                    <li><a href="${link}" target="${mainListNode.target[i].string}" title="${mainListNode.title[i].string}">${mainListNode.text[i].string}</a></li>
                </c:forEach>
            </c:if>
            <!-- Alternate List -->
            <c:if
                test="${not empty altListNode.items[0] && altListNode.items[0].long!=0}">
                <li>-</li>
                <c:forEach var="i" begin="0" end="${altListNode.items[0].long-1}">
                    <c:choose>
                        <c:when test="${altListNode.link[i].string=='internal'}">
                        
                            <c:set var="link" value="${altListNode.path[i].string}" />
                            <c:if test="${not empty link}">
                                <c:set var="link" value="${altListNode.path[i].string}.html" />
                            </c:if>
                        </c:when>
                        <c:when test="${altListNode.link[i].string=='external'}">
                            <%-- <c:set var="link" value="${altListNode.path[i].string}" /> --%>
                            <c:set var="link" value="${altListNode.externalPath[i].string}" />
                            <c:if test="${not empty link}">
                                <c:choose>
                                    <c:when test="${fn:contains(link, 'http://')}">
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="link" value="http://${link}" />
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </c:when>
                    </c:choose>
                    <li><a href="${link}" target="${altListNode.target[i].string}" title="${altListNode.title[i].string}"><em>${altListNode.text[i].string}</em></a></li>
                </c:forEach>
            </c:if>
                        
                    </ul>
                </div>
                <%-- check if is configuration page --%>
                <div class="nav_header_01_featured">
                    <h6>Destacados</h6>
                    
                    <%
                     if( !configMode ) { %>
                           <cq:include resourceType="foundation/components/parsys" path="<%= act_content_path %>"/> 
                    <% } %>
                </div> 
                            
              
    <%--  adding a new div to move the parsys  --%>
    <c:if test = "${authorMode}"> 
        <div style="clear:both;"></div>
    </c:if>  
              
                <cq:include resourceType="/apps/commons/components/content/navigation/nav_header_01_filtering"  path="<%= act_social_path %>"/>

            </div>
            
<%
  
      }
  }
%>            
            
            
         
        </div>
    </div>

    <div class="nav_header_01_section nav_header_01_news">
        <c:if test="${not empty properties.mainTitle}">
            <h2><a href="#"> ${properties.mainTitle} <span></span></a></h2>                        
        </c:if>
        <c:if test="${empty properties.mainTitle}">
            <h2><a href="#"> ${placeholdermaintitle} <span></span></a></h2>
        </c:if>

     <!--   <cq:include resourceType="televisa/components/content/noticieros/navigation/weather-small" path="weathersmall" />   -->
        <cq:include resourceType="/apps/commons/components/content/navigation/nav_header_01_subsections" path="subsections" />
       

 
    </div>
</header>
<script>
    setTime();
</script>
</header>

