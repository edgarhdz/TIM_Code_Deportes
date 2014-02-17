<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Article Note Component
 *  This show a text block of an article, the author can set the content for this text
 *  block using the dialog of this component.
 * 
 *  component id = art_note_01
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 06/02/2013  | Juan Jose Pol.         | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>


<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="commons.message.placeholder" var="placeholdercomponentmessage"/>
<fmt:message key="commons.artnote01.title" var="componentname"/>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>

    <%-- Merge hmtl and css for the art_note_01 Component--%>
    <!-- BEGIN:str_title_01 -->
    <div class="art_note_01">  
      
        <%--  text block validation  --%>
        <c:choose>
            <c:when test="${properties.textblock == null  || empty properties.textblock}">
                <c:if test = "${authorMode}">                            
                    <h3 class="cq-texthint-placeholder" id="cq-gen96"><c:out value="${placeholdercomponentmessage}"/> <c:out value="${componentname}"/></h3>
                </c:if>    
            </c:when>
            <c:otherwise>
                <c:out value="${properties.textblock}"  escapeXml="false" />
            </c:otherwise>
        </c:choose>
    </div>
    <!-- END:str_title_01-->

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>
    
     <%--  adding a new div to move the parsys   --%>
    <c:if test = "${authorMode}"> 
        <div style="clear:both;"></div>
    </c:if> 