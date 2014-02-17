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
%><%@page
        import="com.televisa.commons.services.datamodel.impl.LinkListImpl"%><%
%>
<fmt:message key="noticieros.footer.footercenter.placeholder2" var="placeholdercenter"/>

<c:set var="mode" value="<%= WCMMode.fromRequest(request) %>"/>
<c:set var="modeEdit" value="<%= WCMMode.EDIT %>"/>


<c:if
    test="${mode==modeEdit}">
   <p style="color:#000">  ${placeholdercenter} </p>
</c:if>
<c:if test="${currentNode != null }">
    <c:set var="menuListNode2" value='<%= new LinkListImpl(currentNode,currentNode.getPath() )%>' />
    <div class="nav-special">
        <!-- BEGIN: nav_newsspecial_01 -->
        <div class="nav_newsspecial_01">
            <ul>

                <c:if
                        test="${not empty menuListNode2.items[0] && menuListNode2.items[0].long!=0}">

                    <c:forEach var="i" begin="0" end="${menuListNode2.items[0].long-1}">
                        <c:choose>
                            <c:when test="${menuListNode2.link[i].string=='internal'}">
                                <c:set var="link" value="${menuListNode2.path[i].string}" />
                                <c:if test="${not empty link}">
                                    <c:set var="link" value="${menuListNode2.path[i].string}.html" />
                                </c:if>
                            </c:when>
                            <c:when test="${menuListNode2.link[i].string=='external'}">
                                <%-- <c:set var="link" value="${menuListNode2.path[i].string}" /> --%>
                                <c:set var="link" value="${menuListNode2.externalPath[i].string}" />

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
                        <li><div><a href="${link}" target="${menuListNode2.target[i].string}" title="${menuListNode2.title[i].string}" class="nav_newsspecial_01_menu<c:if test="${i==0}"> </c:if>">${menuListNode2.text[i].string}</a></div></li>
        </c:forEach>
</c:if>
</ul>
</div>
<!-- END: nav_newsspecial_01 -->
</div>
</c:if>
<c:if test="${currentNode==null}">
    Edit component
</c:if>
