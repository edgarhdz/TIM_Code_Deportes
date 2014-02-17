<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Bookmark Navigation Component
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 01/07/2013  | Otto Giron             | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%>
<%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%
    Resource res = resourceResolver.getResource(currentNode.getPath());
    Node node = res.adaptTo(Node.class);

    if(node.hasProperty("numberofitems") ){
        pageContext.setAttribute("numberofitems" , node.getProperty("numberofitems").getValue().getString());
    }else{
        pageContext.setAttribute("numberofitems" , null);
    }

%>


<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>
<!-- BEGIN: wdg_scroll_dropdown -->
<div class="wdg_scroll_dropdown">
    <div class="wdg_tennisresult_01_pleca background-color-pleca1">
        <p class="textcolor-title3"> ${properties.title} </p>
    </div>
    <div class="wdg_scroll_list background-gradient-content2">
        <p class="wdg_scroll_value"></p>
        <div class="wdg_tennisresult_01_click background-color-pleca1">
            <span class="tvsa-caret-down"></span>
        </div>
        <div class="wdg_scroll_events">
            <ul class="wdg_scroll_elements">

                 <c:if test="${numberofitems != null && numberofitems > 0 }">
                     <c:set var="index" value="${0}" />

                     <c:forEach var="item" items ="${properties.path}" varStatus="loop">
                         <c:choose>
                             <c:when test="${properties.numberofitems gt 1}">
                                 <tg:noteProvider path="${properties.path[index]}"/>
                                 <li><p path="${properties.path[index]}"> ${note.title} </p></li>
                             </c:when>
                             <c:otherwise>
                                 <tg:noteProvider path="${properties.path}"/>
                                 <li><p path="${properties.path}"> ${note.title} </p></li>
                             </c:otherwise>


                         </c:choose>
                         <c:set var="index" value="${index + 1}" />
                     </c:forEach>

                 </c:if>


            </ul>
        </div>
    </div>
</div>
<!-- END: wdg_scroll_dropdown -->
<!-- close div of styles to devices -->
<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>
