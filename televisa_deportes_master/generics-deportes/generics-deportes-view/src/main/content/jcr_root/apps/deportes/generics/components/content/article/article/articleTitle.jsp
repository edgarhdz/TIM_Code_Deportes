<%@include file="/apps/deportes/generics/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.HashMap"%>
<%
    if(resource.getChild("title") != null){
        pageContext.setAttribute("articleTitle", resource.getChild("title").adaptTo(ValueMap.class));
    } else {
    	pageContext.setAttribute("articleTitle", new HashMap());
    }
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
    <c:if test="${not empty articleTitle.author}">
        <c:set var="author" value="Por: ${articleTitle.author}" />
    </c:if>

    <%--  Validation Source --%>  
    <c:if test="${not empty articleTitle.source}">
        <c:set var="source" value="Fuente: ${articleTitle.source}" />
    </c:if>
    
    <%-- Date Published --%>
    <c:set var="date" value="${note.pubDate.time}" />
    
    
    
    <%--  VALIDATION LINK   --%>
    <c:choose>
        <c:when test="${articleTitle.linkType == 'internal'}">
            <c:if test="${not empty articleTitle.internalLink}">
                <c:set var="link" value="href='${articleTitle.internalLink}.html'" />  
            </c:if>    
        </c:when>
        <c:otherwise>
            <c:set var="link" value="href='${articleTitle.externalLink}'" />    
        </c:otherwise>
    </c:choose>

    <%-- Validation Target Attribute  --%>
    <c:if test="${not empty articleTitle.linkTarget && articleTitle.linkTarget != 'select'}">
        <c:set var="target" value="target='${articleTitle.linkTarget}'"/>
    </c:if>
    
     <%-- Validation Title Attribute  --%>
    <c:if test="${not empty articleTitle.linkTitle}">
        <c:set var="title" value="${articleTitle.linkTitle}" />
    </c:if>

    <%-- Merge hmtl and css for the art_branch_01 Component    --%>
    <!-- BEGIN:art_title_01 -->
    <div class="art_title_01">
        <div class="art_title_01_title" data-enhance="false">
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
<%
	pageContext.removeAttribute("articleTitle");
%>