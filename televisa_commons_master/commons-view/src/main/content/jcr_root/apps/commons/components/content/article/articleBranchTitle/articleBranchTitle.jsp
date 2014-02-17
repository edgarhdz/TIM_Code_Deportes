<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Article Branch Title
 *  This component is used to display a title branch of the parent of this page
 *  where the component is used, the author can set a custom content in the
 *  text widget in the dialog of this component, alse the user can add an internal
 *  or external link for this content
 *  
 *  
 *  component id = art_branch_01
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


    <%-- adding NoteManagerService service depending  if the author choose a internal link--%>
    <c:choose>
        <c:when test="${empty properties.text}">
            <tg:noteProvider path="${currentPage.path}"/>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty properties.internalLink}">
                <tg:noteProvider path="${properties.internalLink}"/>    
            </c:if>
        </c:otherwise>
    </c:choose>
    
    
    <c:choose>
        <%-- validation to show the content if is add in the text widget of the dialog--%>
        <c:when test="${not empty properties.text}">    
            <c:set var="content" value="${properties.text}" />
        </c:when>
        
        <c:otherwise>
             <%-- adding category branch --%>
             <c:if test="${note != null}" >
                 <%-- <c:set var="link" value="href = '${note.categoryUrl}.html'" /> --%>
                 <c:set var="content" value="${note.category}" />                 
             </c:if>     
        </c:otherwise>
    </c:choose> 
    


    <%-- validation link value, title and target properties for the link --%>
    
    <c:if test="${properties.linkType == 'internal' && not empty properties.internalLink}">
        <c:set var="link" value="href = '${properties.internalLink}.html'" />             
    </c:if>
    
    <c:if test="${properties.linkType == 'external' && not empty properties.externalLink}">
         <c:set var="link" value="href = '${properties.externalLink}'" />                                  
    </c:if>

    <c:set var="title" value="title = '${properties.linkTitle}'" /> 
                
   
    <c:if test="${not empty properties.linkTarget && properties.linkTarget != 'select'}">
        <c:set var="target" value="target= '${properties.linkTarget}'"/>
    </c:if>


    
    <%-- Merge hmtl and css for the art_branch_01 Component--%>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>

    <!-- BEGIN:art_branch_01 -->
    
    <div class="art_branch_01">    
                <a ${link} ${title} ${target} data-enhance="false">
                    <c:out value="${content}"/>                    
                </a>
        
    </div>
    
    <!-- END:art_branch_01-->
    
<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>
