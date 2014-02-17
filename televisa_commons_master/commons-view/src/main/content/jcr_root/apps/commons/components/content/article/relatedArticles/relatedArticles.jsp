<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Related Articles Component
 *  This script display all tags asigned to the page.
 *
 *  component id = art_reltd_01
 *
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 14/02/2013  | Juan Jose Pol.         | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<%@page import="com.day.cq.tagging.Tag"%>


<%-- adding length tags of the page--%>
<c:set var="tagslength" value="<%=currentPage.getTags().length %>" />

<%-- adding the tags of the page--%>
<c:set var="tags" value="${currentPage.tags}" />


<%-- adding validation to show the tags --%>
<c:if test="${not empty properties.totalarticlerelated}">
    <c:set var="total" value="${properties.totalarticlerelated}"/>
</c:if>

<c:if test="${empty properties.totalarticlerelated}">
    <c:set var="total" value='4'/>
</c:if>


<%-- Validation Target Attribute  --%>
<c:if test="${not empty properties.linkTarget && properties.linkTarget != 'select'}">
    <c:set var="target" value='target="${properties.linkTarget}"'/>
</c:if>


<%-- Merge hmtl and css for the art_reltd_01 Component    --%>
<!-- BEGIN:art_reltd_01 -->
<div class="mix_2arts_06_title">
    <a class="mix_2arts_06_blue" href="#" title="">Temas Relacionados</a>
    <br>
    <%-- iterate inside all the tags  to create the content and link --%>
    <c:forEach var="i" begin="0" end="${total - 1 }"  items ="${tags}" varStatus="status" >
        <a class="mix_2arts_06_black"  href="<tg:searchByTagUrl tag='${i.name}'/>"  ${target}> <c:out value="${i.title}" />
            <c:choose>
                <c:when test="${status.count < tagslength && status.count < total }">,</c:when>
            </c:choose>
        </a>
    </c:forEach>
</div>

<!-- END:art_reltd_01-->


<%--  adding a new div to move the parsys   --%>
<c:if test = "${authorMode}">
    <div style="clear:both;"></div>
</c:if>