
<%--
 * DESCRIPTION
 *  Footer component for Noticieros Televisa
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 24/01/2013  | jdiaz@xumak.com        | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%
    
%><%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<%@page import="javax.jcr.Node"%><%@ page
    import="com.day.cq.wcm.api.WCMMode"%>

<%@page import="javax.jcr.Node"%><%@page
    import="com.televisa.commons.services.datamodel.impl.LinkListImpl"%><%@ page
    import="com.day.cq.wcm.api.WCMMode"%>
<fmt:message key="commons.editcomponent.placeholder" var="placeholderfooter" />
<%
    String mainlistpath = properties.get("globalpage","");
%>

<c:set var="mode" value="<%=WCMMode.fromRequest(request)%>" />
<c:set var="modeEdit" value="<%=WCMMode.EDIT%>" />
<c:set var="mainlistpath" value="${properties.globalpage}"/>

<c:set var="mainListNode"
    value='<%=new LinkListImpl(currentNode, "mainlist")%>' />
<c:set var="altListNode"
    value='<%=new LinkListImpl(currentNode, "altlist")%>' />
<c:set var="menuListNode"
    value='<%=new LinkListImpl(currentNode, mainlistpath )%>' />


<c:if test="${mode==modeEdit}">
   ${placeholderfooter}
</c:if>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>

<%-- adding <footer class="footerVertical"> tag--%>

    <c:if test="${properties.showHomePageBook}">
        <div class="nav_bookmark_01">
            <ul>
                <li class="image">
                    <a href="${properties.path}" title="${properties.titleLink}" class="selected">
                        <span class="nav_bookmark_01_sprite bluesq"></span> ${properties.text}
                    </a>
                </li>
            </ul>
        </div>
    </c:if>
    
    <footer class="nav_footer_01">
        <nav class="desktopNav">
            <ul>
                <!-- Menu List -->
                <c:if
                    test="${not empty menuListNode.items[0] && menuListNode.items[0].long!=0}">
    
                    <c:forEach var="i" begin="0" end="${menuListNode.items[0].long-1 }">
                        <c:choose>
                            <c:when test="${menuListNode.link[i].string=='internal'}">
                                <%-- <c:set var="link" value="${menuListNode.path[i].string}.html" /> --%>
                                <c:set var="link" value="${menuListNode.path[i].string}" />
                                <c:if test = "${not empty link}">
                                    <c:set var="link" value="${menuListNode.path[i].string}.html" />
                                </c:if>
                                
                            </c:when>
                            <c:when test="${menuListNode.link[i].string=='external'}">
                                <c:set var="link" value="${menuListNode.externalPath[i].string}" />
                                <c:if test = "${not empty link}">
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
                        <c:if test="${i==0}">
                            <li class="active" id="item-active">
                        </c:if>
                        <c:if test="${i>0}">
                            <li>
                        </c:if>
                        <a href="${link}" target="${menuListNode.target[i].string}"
                            title="${menuListNode.title[i].string}">
                            ${menuListNode.text[i].string} </a>
                        </li>
                    </c:forEach>
                </c:if>
            </ul>
        </nav>
    
        <div class="white-row">
            <div class="wrapper">
                <ul class="items-white-row">
                    <!-- Main List -->
                    <c:if
                        test="${not empty mainListNode.items[0] && mainListNode.items[0].long!=0}">
    
    
                        <c:forEach var="i" begin="0" end="${mainListNode.items[0].long-1}">
                            <c:choose>
                                <c:when test="${mainListNode.link[i].string=='internal'}">
                                
                                    <%-- <c:set var="link" value="${mainListNode.path[i].string}.html" /> --%>
                                    <c:set var="link" value="${mainListNode.path[i].string}" />                                    
                                    <c:if test="${not empty link}">
                                        <c:set var="link" value="${mainListNode.path[i].string}.html" />
                                    </c:if>
                                    
                                </c:when>
                                <c:when test="${mainListNode.link[i].string=='external'}">
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
                            <c:if test="${i==0}">
                                <li class="displaynone">
                            </c:if>
                            <c:if test="${i>0}">
                                <li>
                            </c:if>
                            
                            <a href="${link}"
                                target="${mainListNode.target[i].string}"
                                title="${mainListNode.title[i].string}">
                                    ${mainListNode.text[i].string} </a></li>
                        </c:forEach>
                    </c:if>
    
                    <!-- Alternate List -->
                    <c:if
                        test="${not empty altListNode.items[0] && altListNode.items[0].long!=0}">
                        <li>-</li>
                        <c:forEach var="i" begin="0" end="${altListNode.items[0].long}">
                            <c:choose>
                                <c:when test="${altListNode.link[i].string=='internal'}">
                                   <%--  <c:set var="link" value="${altListNode.path[i].string}.html" /> --%>
                                    
                                    <c:set var="link" value="${altListNode.path[i].string}" />                                    
                                    
                                    <c:if test="${not empty link}">
                                        <c:set var="link" value="${altListNode.path[i].string}.html" />
                                    </c:if>
                                </c:when>
                                <c:when test="${altListNode.link[i].string=='external'}">
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
                            <li><a href="${link}"
                                target="${altListNode.target[i].string}"
                                title="${altListNode.title[i].string}">
                                    ${altListNode.text[i].string} </a></li>
                        </c:forEach>
                    </c:if>
                </ul>
    
                <c:forEach var="col" begin="1" end="${properties.columnsitem}">
                    <c:choose>
                        <c:when test = "${col == 1}">
                            <c:set var="columnClass" value="redes" />
                        </c:when>
                        <c:when test = "${col == 2}">
                            <c:set var="columnClass" value="blogs" />
                        </c:when>
                        <c:when test = "${col == 3}">
                            <c:set var="columnClass" value="movil" />
                        </c:when>
                    </c:choose>
                    <div class="column ${columnClass}">
                        <cq:include path="footerColumnParsys${col}" resourceType="foundation/components/parsys" />
                    </div>
                </c:forEach>

            </div>
        </div>
        <nav class="mobileNav">
            <ul>
                <li class="back">
                    <a href="#" title="">
                        ${properties.gobackmessage}
                        <i class="tvsa-caret-up"></i>
                    </a>
                </li>
    
                <!-- Menu List -->
                <c:if
                    test="${not empty menuListNode.items[0] && menuListNode.items[0].long!=0}">
    
                    <c:forEach var="i" begin="0" end="${menuListNode.items[0].long-1}">
                        <c:choose>
                            <c:when test="${menuListNode.link[i].string=='internal'}">
                            
                                <%-- <c:set var="link" value="${menuListNode.path[i].string}.html" /> --%>
                                
                                <c:set var="link" value="${menuListNode.path[i].string}" />
                                <c:if test = "${not empty link}">
                                    <c:set var="link" value="${menuListNode.path[i].string}.html" />
                                </c:if>

                            </c:when>
                            <c:when test="${menuListNode.link[i].string=='external'}">
                            
                                <%-- <c:set var="link" value="${menuListNode.path[i].string}" /> --%>
                                <c:set var="link" value="${menuListNode.externalPath[i].string}" />
                                
                                <c:if test = "${not empty link}">
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
                        <c:set var="channelParts" value="${fn:split(menuListNode.channel[i].string, ';')}" />
                        <c:set var="menuChannel" value="${channelParts[0]}" />
                        <li class="${menuChannel}"><a href="${link}"
                            target="${menuListNode.target[i].string}"
                            title="${menuListNode.title[i].string}">
                            ${menuListNode.text[i].string}
                            <i class="tvsa-caret-right"></i></a>
                        </li>
                    </c:forEach>
                </c:if>
            </ul>
        </nav>
    </footer>

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>
