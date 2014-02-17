<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Component to include inside the footer component, 
 *  displays a title and links, optional an image
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
--%><%
%><%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%><%
%><%@ page import="com.day.cq.wcm.api.WCMMode"%>
<c:set var="mode" value="<%= WCMMode.fromRequest(request) %>"/>
<c:set var="modeEdit" value="<%= WCMMode.EDIT %>"/>
<fmt:message key="commons.editcomponent.placeholder" var="placeholderitem"/>

<c:if test="${mode==modeEdit && (empty properties.titleItem || empty properties.classItem)}">
   ${placeholderitem}
</c:if>

<%
    String classItem  = properties.get("classItem", "");
    String[] items = classItem.split(",");
    if (items.length == 2){
        pageContext.setAttribute("classItemUl", items[0]);
        pageContext.setAttribute("classIcon", items[1]);
    }

    pageContext.setAttribute("linkTypeParent", properties.get("linkTypeParent", "internal"));
    pageContext.setAttribute("internalLinkParent", properties.get("internalLinkParent", ""));
    pageContext.setAttribute("externalLinkParent", properties.get("externalLinkParent", ""));
    pageContext.setAttribute("targetParent", properties.get("targetParent", "_blank"));
%>

<c:choose>
    <c:when test="${linkTypeParent == 'internal'}">
        <c:if test="${not empty internalLinkParent}">
            <c:set var="linkParent" value="href='${internalLinkParent}.html'" />
        </c:if>
    </c:when>
    <c:otherwise>

        <c:if test="${not empty externalLinkParent}">
            <c:set var="linkParent" value="href='${externalLinkParent}'" />
        </c:if>
    </c:otherwise>
</c:choose>

<%-- adding target propety to the links --%>
<c:if test="${not empty targetParent}">
    <c:set var="targetParent" value="target= '${targetParent}'"/>
</c:if>

<ul class="${classItemUl}">
    <li class="icon-social">
        <a ${linkParent} ${targetParent} title="${properties.titleParent}">
	        <span class="icon">
	            <i class="${classIcon}"></i>
	        </span> ${properties.titleItem}
        </a>
    </li>
    <li>
        <ul class="container-element-sec">
            <c:if test="${properties.numberofitems>0}">
            <li>-</li>
            <c:choose>
            <c:when test="${properties.numberofitems==1}">
                <c:choose>
                        <c:when test="${properties.linkType=='internal'}">
                            <c:set var="linkOne" value="${properties.path}" />
                            <c:if test="${not empty linkOne}">
                                <c:set var="linkOne" value="${properties.path}.html" />
                            </c:if>
                        </c:when>
                        <c:when test="${properties.linkType=='external'}">
                            <c:set var="linkOne" value="${properties.externalLink}" />
                            
                            <c:if test="${not empty linkOne}">
                                <c:choose>
                                    <c:when test="${fn:contains(linkOne, 'http://')}">
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="linkOne" value="http://${linkOne}" />
                                    </c:otherwise>
                                </c:choose>
                            </c:if>    
                        </c:when>
                    </c:choose>
                    <li><a href="${linkOne}" target="${properties.target}"
                        title="${properties.title}"> ${properties.text} </a></li>
            </c:when>
            <c:otherwise>
                <c:forEach var="i" begin="0" end="${properties.numberofitems-1}">
                    <c:choose>
                        <c:when test="${properties.linkType[i]=='internal'}">
                            <c:set var="link" value="${properties.path[i]}" />
                            <c:if test="${not empty link}">
                                <c:set var="link" value="${properties.path[i]}.html" />
                            </c:if>
                        </c:when>
                        <c:when test="${properties.linkType[i]=='external'}">
                        
                            <%-- <c:set var="link" value="${properties.path[i]}" /> --%>
                            <c:set var="link" value="${properties.externalLink[i]}" />
                            
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
                    <li><a href="${link}" target="${properties.target[i]}"
                        title="${properties.title[i]}"> ${properties.text[i]} </a></li>
                </c:forEach>
            </c:otherwise>
            </c:choose>

            </c:if>
        </ul>
    </li>
</ul>
