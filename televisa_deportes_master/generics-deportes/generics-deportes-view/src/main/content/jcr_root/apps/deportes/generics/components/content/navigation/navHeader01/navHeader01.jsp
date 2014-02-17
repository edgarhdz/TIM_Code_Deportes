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
%><%@page session="false"
          pageEncoding="utf-8"
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

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>
    
<header class="nav_header_01" data-enhance="false">

    <div class="topnav">
        <div class="inner">
            <a href="#menu" class="menu">
                <span>Men&uacute; Principal</span>
            </a>
            <a class="televisa deportes" href="${logolink}" ${logotitle}>
                <span class="mobile-logo"></span>
                <span>${logotext}</span></a>
            <span class="date" id="date">-</span>

            <form action="/bin/commons/search" method="post" class="site_search">
                <button class="search_submit" type="submit">
                    <i class="tvsa-search"></i>
                </button>
                <input class="search_term" type="text" name="q" value="" placeholder="<%= searchLabel %>" />
                <input type="hidden" name="url" value="<%= searchUrl %>" />
            </form>

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
                            <li role="menuitem">
                                <c:set var="channelParts" value="${fn:split(menuListNode.channel[i].string, ';')}" />
                                <c:set var="menuChannel" value="${channelParts[1]}" />
                                <a href="${link}" title="${menuListNode.title[i].string}" data-collapsable="nav_header_01_${menuChannel}" class="${menuChannel}" >${menuListNode.text[i].string}</a>
                            </li>

                        </c:forEach>
                    </c:if>
                </ul>
            </nav>

        </div>
    </div>

    <div class="collapsable-verticals" style="display:none;">
        <div class="inner">
            <%
                LinkListImpl list = new LinkListImpl( currentNode, confPath );
                if ( list.getItems().length > 0 ){
                    for ( int n = 0; n < list.getItems()[0].getLong() ; n++ ) {
                        String act_conf_path = list.getConfigurationPath()[n].getString()+"/jcr:content/header/navheader01";
                        String act_social_path  = list.getConfigurationPath()[n].getString()+"/jcr:content/headerFiltering";
                        String act_content_path = list.getConfigurationPath()[n].getString()+"/jcr:content/headercontent";
                        String class_type    = "";
                        String classTypeContent  = "";

                        if ( list.getChannel() != null ) {
                            String classChannel    = list.getChannel()[n].getString();
                            String[] channelParts = classChannel.split(";");
                            if ( channelParts != null) {
                                if (channelParts.length >= 3) {
                                    class_type = channelParts[1];
                                    classTypeContent = channelParts[2];
                                }
                            }
                        }

                        Node act_node = resourceResolver.resolve( act_conf_path ).adaptTo( Node.class );
            %>

            <c:set var="mainListNode" value='<%= new LinkListImpl( act_node,"mainlist")%>' />
            <c:set var="altListNode"  value='<%= new LinkListImpl( act_node,"altlist")%>' />

            <div class="nav_header_01_<%= class_type %> collapsable-content open">
                <div class="collapsable-content-inner">
                    <strong class="maintitle <%=classTypeContent%>"><%= list.getText()[n].getString().trim() %> &nbsp;<i class="tvsa-double-caret-right"></i></strong>
                    <div class="categories">
                        <em class="sectiontitle <%=classTypeContent%>">Categor&iacute;as</em>
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
                                <li class="separator">-</li>
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
                    <div class="featured">
                        <em class="sectiontitle <%=classTypeContent%>">Destacados</em>
                        <%
                            if( !configMode ) { %>
                        <cq:include resourceType="foundation/components/parsys" path="<%= act_content_path %>"/>
                        <% } %>
                    </div>

                    <%--  adding a new div to move the parsys  --%>
                    <% /*
                    <c:if test = "${authorMode}">
                        <div style="clear:both;"></div>
                    </c:if>
                    */%>
                    <hr class="clearboth" />
                    <% if( !configMode ) { %>
                        <cq:include resourceType="/apps/commons/components/content/navigation/navHeader01Filtering"  path="<%= act_social_path %>"/>
                    <% } %>
                </div>
            </div>

            <%
                    }
                }
            %>
        </div>
    </div>

    <div class="title">
        <c:if test="${not empty properties.mainTitle}">
            <div class="inner">
                <strong>${properties.mainTitle}<a class="arrow" href="#"><i class="tvsa-caret-down"></i></a></strong>
            </div>
        </c:if>
        <c:if test="${empty properties.mainTitle}">
            <h2><a href="#"> ${placeholdermaintitle} <span></span></a></h2>
        </c:if>
    </div>

    <cq:include resourceType="/apps/deportes/generics/components/content/navigation/nav_header_01_subsections" path="subsections" />

</header>
<script>
    setTime();
</script>


<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>