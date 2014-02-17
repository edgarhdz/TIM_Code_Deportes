<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Article Title
 *  This component show the main title of an article.
 *  The author can add a custom title for the article, by default it will use 
 *  the page title.
 *  
 *  
 *  component id = art_title_01
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 07/02/2013  | Juan Jose Pol.         | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>


    <%-- adding NoteManagerService service --%>
    <tg:noteProvider path="${currentPage.path}"/>
   
    <%--  Validation Content --%>  
    <c:choose>
        <c:when test="${currentPage.title != null}">
            <c:set var="content" value="${currentPage.title}" />  
        </c:when>
        <c:otherwise>
            <c:set var="content" value="${currentPage.name}" />    
        </c:otherwise>
    </c:choose>

    <%--  Validation Author --%>  
    <c:if test="${not empty properties.author}">
        <c:set var="author" value="Por: ${properties.author}" />
    </c:if>

    <%--  Validation Source --%>  
    <c:if test="${not empty properties.source}">
        <c:set var="source" value="Fuente: ${properties.source}" />
    </c:if>
    
    <%-- Date Published --%>
    <c:set var="date" value="${note.pubDate.time}" />
    
    
    
    <%--  VALIDATION LINK   --%>
    <c:choose>
        <c:when test="${properties.linkType == 'internal'}">
            <c:if test="${not empty properties.internalLink}">
                <c:set var="link" value="href='${properties.internalLink}.html'" />  
            </c:if>    
        </c:when>
        <c:otherwise>
            <c:set var="link" value="href='${properties.externalLink}'" />    
        </c:otherwise>
    </c:choose>

    <%-- Validation Target Attribute  --%>
    <c:if test="${not empty properties.linkTarget && properties.linkTarget != 'select'}">
        <c:set var="target" value="target='${properties.linkTarget}'"/>
    </c:if>
    
     <%-- Validation Title Attribute  --%>
    <c:if test="${not empty properties.linkTitle}">
        <c:set var="title" value="${properties.linkTitle}" />
    </c:if>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>

    <%-- Merge hmtl and css for the art_branch_01 Component    --%>
    <!-- BEGIN:art_title_01 -->
    <div class="art_title_01" data-enhance="false">
        <div class="art_title_01_title">
            <p>
                <a ${link} ${title} ${target}>
                   	<span class="art_info"><c:out value="${author}"/></span> 
		   	<span class="art_info"><c:out value="${source}"/></span>  
			<span class="art_info"><fmt:formatDate pattern="yyyy-MM-dd" value="${date}" /></span>
                </a>
            </p>
            <h1>
                <c:choose>
                    <c:when test="${not empty title}">
                        <a ${link} title="<c:out value="${title}" />" ${target}>
                            <c:out value="${content}"/> 
                        </a>    
                    </c:when>
                    <c:otherwise>
                        <a ${link} ${target}>
                            <c:out value="${content}"/> 
                        </a>
                    </c:otherwise>
                </c:choose>
            </h1>
        </div>        
        
    </div>
    <!-- END:art_title_01-->
 
<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>