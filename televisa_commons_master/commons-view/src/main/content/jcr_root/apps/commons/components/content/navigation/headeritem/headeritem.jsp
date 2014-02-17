<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Component to include inside the header component, 
 *  displays a list of items with a title
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 22/03/2013  | jdiaz@xumak.com        | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%><%
%><%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%><%
%>
<fmt:message key="noticieros.footer.footercenter.placeholder" var="placeholdercenter"/>

<c:set var="mode" value="<%= WCMMode.fromRequest(request) %>"/>
<c:set var="modeEdit" value="<%= WCMMode.EDIT %>"/>


<c:if
    test="${mode==modeEdit}">
   <p style="color:#000">  ${placeholdercenter} </p>
</c:if>


<c:choose>
    <c:when test="${properties.linkTypeMain=='internal'}">
        <c:set var="linkMain" value="${properties.pathMain}.html" />
    </c:when>
    <c:when test="${properties.linkTypeMain=='external'}">
        <c:set var="linkMain" value="${properties.pathMain}" />
        <c:choose>
            <c:when test="${fn:contains(link, 'http://')}">
            </c:when>
            <c:otherwise>
                <c:set var="linkMain" value="http://${linkMain}" />
            </c:otherwise>
        </c:choose>
    </c:when>
</c:choose>




<c:if test="${not empty properties.textMain}">

<li class="nav_header_01_vidcoltit"><a href="${linkMain}" 
                target="${properties.targetMain}" title="${properties.titleMain}">${properties.textMain}</a></li>

</c:if>

<c:if test="${properties.numberofitems>0}">
            <c:choose>
                <c:when test="${properties.numberofitems==1}">
                    <c:choose>
                        <c:when test="${properties.linkType=='internal'}">
                            <c:set var="linkOne" value="${properties.path}.html" />
                        </c:when>
                        <c:when test="${properties.linkType=='external'}">
                            <c:set var="linkOne" value="${properties.path}" />
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
                        title="${properties.title}"> ${properties.text} </a></li>
                </c:when>
                <c:otherwise>
                    <c:forEach var="i" begin="0" end="${properties.numberofitems-1}">
                        <c:choose>
                            <c:when test="${properties.linkType[i]=='internal'}">
                                <c:set var="link" value="${properties.path[i]}.html" />
                            </c:when>
                            <c:when test="${properties.linkType[i]=='external'}">
                                <c:set var="link" value="${properties.path[i]}" />
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
                            title="${properties.title[i]}"> ${properties.text[i]} </a></li>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </c:if>

<div class="nav_header_01_vidcoltit nav_header_01_margintop"></div>


