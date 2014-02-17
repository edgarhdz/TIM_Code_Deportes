<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Footer Bottom component for Noticieros Televisa
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
<%@ page import="com.day.cq.wcm.api.WCMMode,
                com.televisa.commons.services.utilities.Utilities"%>
<fmt:message key="commons.editcomponent.placeholder"
             var="placeholderbottom" />

<c:set var="mode" value="<%=WCMMode.fromRequest(request)%>" />
<c:set var="modeEdit" value="<%=WCMMode.EDIT%>" />

<c:if test="${mode==modeEdit && ( empty properties.rights || empty properties.trademark || properties.numberofitems==0 ) }">
    ${placeholderbottom}
</c:if>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>

<div class="nav_corft_01">
    <footer>
        <div class="shortcut-home">
            <a href="http://www.televisa.com" title="Link description">
                <div class="nav_corft_01_wrapper">
                    <span></span> Haz de Televisa tu pagina de inicio
                </div>
            </a>
        </div>

        <div class="blue-row notExpanded">
            <div class="nav_corft_01_wrapper">
                <ul class="blueSections">
                    <c:forEach var="col" begin="1" end="${properties.columnscenter}">
                        <li>
                            <cq:include path="footerColumnCenterParsys${col}"
                                        resourceType="/apps/commons/components/content/navigation/footercenter" />
                        </li>
                    </c:forEach>
                </ul>

                <a href="#" title="${properties.showtitle}" class="show">
                    <span class="text"> ${properties.showmessage} </span>
                    <i class="tvsa-double-caret-down"></i>
                </a>

                <a href="#" title="${properties.hidetitle}" class="hide">
                	<span class="text"> ${properties.hidemessage} </span>
                    <i class="tvsa-double-caret-up"></i>
                </a>
            </div>
        </div>

        <div class="dark-row">
            <div class="nav_corft_01_wrapper legal">
                <div class="middle-row">
                    <c:choose>
                        <c:when test="${properties.linkTypeLogo=='internal'}">
                            <c:set var="linkLogo" value="${properties.pathLogo}.html" />
                        </c:when>
                        <c:when test="${properties.linkTypeLogo=='external'}">
                            <c:set var="linkLogo" value="${properties.pathLogo}" />
                            <c:choose>
                                <c:when test="${fn:contains(linkLogo, 'http://')}">
                                </c:when>
                                <c:otherwise>
                                    <c:set var="linkLogo" value="http://${linkLogo}" />
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                    </c:choose>

                    <div class="middle-row">
                        <a href="${linkLogo}" title="${properties.titleLinkLogo}"
                           class="logo"> <span class="nav_corft_01_sprite logo"></span>
                        </a>
                    </div>

                </div>
                <div class="row">
                    <div class="left">
                        <c:set var="year" value="<%= Utilities.getYear() %>"/>
                        ${fn:replace(properties.rights,'{year}',year)}

                    </div>
                    <div class="right">
                        <nav>
                            <ul>
                                <c:if test="${properties.numberofitems>0}">

                                    <c:choose>
                                        <c:when test="${properties.numberofitems==1}">
                                            <c:choose>
                                                <c:when test="${properties.linkType=='internal'}">
                                                    <c:set var="linkOne" value="${properties.path}.html" />
                                                </c:when>
                                                <c:when test="${properties.linkType=='external'}">
                                                    <c:set var="linkOne" value="${properties.externalLink}" />
                                                    <c:choose>
                                                        <c:when test="${fn:contains(linkOne, 'http://')}">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:set var="linkOne" value="http://${linkOne}" />
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                            </c:choose>
                                            <li><a href="${linkOne}" target="${properties.target}"
                                                   title="${properties.title}"> ${properties.text}
                                                <span class="icon mobile arrow"></span>
                                            </a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="i" begin="0"
                                                       end="${properties.numberofitems-1}">
                                                <c:choose>
                                                    <c:when test="${properties.linkType[i]=='internal'}">
                                                        <c:set var="link" value="${properties.path[i]}.html" />
                                                    </c:when>
                                                    <c:when test="${properties.linkType[i]=='external'}">
                                                        <c:set var="link" value="${properties.externalLink[i]}" />
                                                        <c:choose>
                                                            <c:when test="${fn:contains(link, 'http://')}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:set var="link" value="http://${link}" />
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                </c:choose>
                                                <li><a href="${link}" target="${properties.target[i]}"
                                                       title="${properties.title[i]}"> ${properties.text[i]}
                                                    <span class="icon mobile arrow"></span>
                                                </a></li>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>

                                </c:if>
                            </ul>
                        </nav>
                        <div class="nav_corft_01_sprite_trade">
                            ${properties.trademark}</div>
                    </div>
                </div>
            </div>
        </div>
    </footer>
</div>


<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>