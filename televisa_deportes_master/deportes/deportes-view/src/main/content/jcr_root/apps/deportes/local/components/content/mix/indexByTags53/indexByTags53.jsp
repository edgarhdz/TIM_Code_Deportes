
<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Index By Tags component.

 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer               | Changes
 * 1.0     | 28/01/2013  | Luis Jose Sztul         | Initial Creation.
 * 1.0     | 27/06/2013  | Pablo Alecio            | Removed 'Not find any note' message.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/televisa/components/global.jsp"%>

<%@page import="com.televisa.generics.services.datamodel.Note"%>
<%@page import="com.televisa.generics.services.datamodel.RenditionSize"%>
<%@page import="com.televisa.generics.services.datamodel.objects.impl.FilterIndexByTagsImpl"%>
<%@page import="com.televisa.generics.services.datamodel.objects.InfoPage"%>
<%@page import="com.televisa.generics.services.services.InfoPageManagerService"%>
<%@page import="com.televisa.generics.services.utilities.Utilities"%>

<%@ page import="java.util.List" %>
<%@ page import="com.day.cq.wcm.api.WCMMode" %>

<fmt:message key="generics.indexByTags.placeholder" var="placeholder"/>

<c:set var="mode" value="<%= WCMMode.fromRequest(request) %>"/>
<c:set var="modeEdit" value="<%= WCMMode.EDIT %>"/>

<c:if test="${mode == modeEdit}">
    <style type="text/css">
        .mix_5x3artspag_01{
            min-height: 1250px !important;
        }
    </style>
</c:if>

<%
    String type = properties.get("type", "automatic");

    String path = properties.get("path", "");
    String[] tags = properties.get("cq:tags", String[].class);
    boolean showarticles = properties.get("showarticles", false);
    boolean showvideos = properties.get("showvideos", false);
    boolean showphotogalleries = properties.get("showphotogalleries", false);
    boolean showpartidos = properties.get("showpartidos", false);
    
    InfoPage infoPage = null;    
    List<Note> notes = null;
    boolean results = false;
    Integer pageNumber = 1;

    if(type.equals("automatic")){
        String[] selectors = Utilities.validateSelectors(slingRequest.getRequestPathInfo().getSelectors(), true);        
        if((selectors[0].trim().length() == 0) || (pageNumber == 0)){
            tags = new String[]{};
            pageNumber = 0;
        }else{
            tags = new String[]{selectors[0]};
            pageNumber = Integer.parseInt(selectors[1]);
        }        
    }else{
        String[] selectors = Utilities.validateSelectors(slingRequest.getRequestPathInfo().getSelectors(), false);
        if(selectors != null || selectors.length >= 2){
            pageNumber = Integer.parseInt(selectors[1]);
        }else{
            pageNumber = 1;
        }
    }

    if(!path.trim().equals("") && (tags != null && tags.length > 0) && pageNumber > 0){
        InfoPageManagerService service = (InfoPageManagerService) sling.getService(InfoPageManagerService.class);
        FilterIndexByTagsImpl filter = new FilterIndexByTagsImpl(path, tags, showarticles, showvideos, showphotogalleries, showpartidos, 15, 15, pageNumber);
        infoPage = service.getNotesByPathAndTags(slingRequest, filter);
        if(infoPage == null || infoPage.getNotes() == null || infoPage.getNotes().size() <= 0){
            pageContext.setAttribute("notes", notes);
        }else{
            notes = infoPage.getNotes();
            pageContext.setAttribute("notes", notes);
            results = true;                                            
        }
    }

    boolean hasTag = ((type.equalsIgnoreCase("automatic")) ? true : false);
    pageContext.setAttribute("infoPage", infoPage);
    pageContext.setAttribute("results", results);    
%>

<!-- BEGIN: mix_5x3artspag_01 -->
<c:choose>
    <c:when test="${(not empty notes) && (not empty infoPage) && results}">
        <div class="mix_5x3artspag_01">
            <%
                if(notes != null){    
                    for(int i = 0; (i < notes.size()); i++){
                        Note note = notes.get(i);
                        pageContext.setAttribute("note", note);
                        //pageContext.setAttribute("RENDITION_192_108", note.getRendition(RenditionSize.RENDITION_192_108));  
            %>
                    <c:set var="RENDITION_192_108">
                        <tg:renditionSelector image="${note.noteImageAsset}" width="192" height="108"/>
                    </c:set>

            <%    if( (i != 0) && (i % 3) == 0){     %>
                    </div>    
                    <!--END:mix_3arts_02-->

                    <div class="separador"></div>

                    <!-- BEGIN:mix_3arts_02 -->
                    <div class="mix_3arts_02">  
            <%    }else if(i == 0){     %>
                    <!-- BEGIN:mix_3arts_02 -->
                    <div class="mix_3arts_02">
            <%    }     %>

                       <% if( i % 3 == 0 ) { %>
                        	    <div  class="mix_3arts_02_first">
                       <% } else if (  i % 3 == 2  ) { %>
								<div  class="mix_3arts_02_last">
                       <% } else { %>
                            	<div>
                       <% } %>
                        <!-- BEGIN:mix_1arts_09 -->
                        <div class="mix_1arts_09">
                            <ul class="mix_1arts_09_thumb">
                                <li>                        
                                    <a href="${note.url}.html" title="${note.title}" target="${properties.linkTarget}" > 

                                        <img src="${RENDITION_192_108}" alt="${note.title}"/>
                                        <%-- show image context --%>
                                        <c:if test="${properties.showcontext == true}">                            
                                            <span class="${imageContext}"></span>
                                        </c:if>
                                           
                                        <h2><c:out value="${note.topic}"/></h2>
                                        <p><c:out value="${note.title}"/></p>
                                        <span class="icon"></span> 
                                    </a>                         
                                    
                                </li>
                            </ul>
                        </div>
                        <!--END:mix_1arts_09-->
                        </div>
                <%    if (i == (notes.size() - 1)){     %>    
                        </div>
                        <!--END:mix_3arts_02-->
                <%    }     %>
            <%
                    }
                }
            %>
            <div class="separador"></div>

            
            <div class="nav_paginator_01">
            
                <ul>                                   
                
                    <% 
                        String URL = slingRequest.getRequestURI();
                        int totalPages = infoPage.getTotalPages();
                        int actualPage = infoPage.getActualPage();

                        for(int i = 1; i < (totalPages + 1); i++ ){ 
                            if(totalPages > 4){
                                if((i ==  1) && (actualPage > 1)){
                    %>
                                    <li class="nav1"><a href="<%= Utilities.getPageURL(URL, (actualPage - 1), hasTag) %>" class="left sprite carouselArrowLeft" title ="Link Description">&laquo;</a></li>
                    <%                                    
                                    if((i == 1) && (actualPage > 2)){
                    %>
                                        <li><a href="<%= Utilities.getPageURL(URL, 1, hasTag) %>"><%= 1 %></a></li>  
                                        <li>...</li>
                    <%                                     
                                    }                                  
                                }
                                
                                if((i >=  1) && (i <= totalPages)){
                                    if((i >= 1 && i <= 3) && (actualPage == 1)){
                                        if(actualPage <= i){
                                            if(i == actualPage){
                    %>
                                                <li><span class="selected"><%= i %></span></li>
                    <%                       
                                            }else{
                    %>
                                                <li ><a href="<%= Utilities.getPageURL(URL, i, hasTag) %>"><%= i %></a></li>
                    <%                                                                   
                                            }                                    
                                        }                                                                    
                                    }                                
                                }
                                
                                if(((i >= 1) && (i <= totalPages)) && (actualPage != 1 && actualPage != totalPages)){
                                    if((actualPage >= (i - 1)) && (actualPage <= (i + 1))){
                                        if(i == actualPage){                          
                    %>
                                            <li><span class="selected"><%= i %></span></li>
                    <%                       
                                        }else{
                    %>
                                            <li ><a href="<%= Utilities.getPageURL(URL, i, hasTag) %>"><%= i %></a></li>
                    <%
                                        }
                                    }
                                }
                                
                                
                                if((i >= (totalPages - 2) && i <= totalPages) && (actualPage == totalPages)){
                                    if(actualPage >= i){
                                        if(i == actualPage){                                
                    %>
                                                <li><span class="selected"><%= i %></span></li>
                    <%                       
                                        }else{
                    %>
                                                <li ><a href="<%= Utilities.getPageURL(URL, i, hasTag) %>"><%= i %></a></li>
                    <%                                                                   
                                        }
                                    }
                                }
                                
                                if((i == totalPages) && (actualPage < totalPages)){
                                    if((i == totalPages) && (actualPage < (totalPages - 1))){
                    %>
                                        <li>...</li>
                                        <li><a href="<%= Utilities.getPageURL(URL, totalPages, hasTag) %>"><%= totalPages %></a></li>                                                                                                
                    <%                                    
                                    }
                    %>
                                    <li class="nav2"><a href="<%= Utilities.getPageURL(URL, (actualPage + 1), hasTag) %>" class="right sprite carouselArrowRight" title ="Link Description">&raquo;</a></li>
                    <%                                 
                                }
                            }else{
                                if((i ==  1) && (actualPage > 1)){
                    %>
                                    <li class="nav1"><a href="<%= Utilities.getPageURL(URL, (actualPage - 1), hasTag) %>" class="left sprite carouselArrowLeft" title ="Link Description">&laquo;</a></li>
                    <%                                 
                                }
   
                  
                                if(i == actualPage){
                    %>
                                        <li><span class="selected"><%= i %></span></li>
                    <%                                
                                }else{
                    %>                                
                                        <li ><a href="<%= Utilities.getPageURL(URL, i, hasTag) %>"><%= i %></a></li>
                    <%                                
                                }
                  
                                if((i == totalPages) && (actualPage < totalPages)){
                    %>
                                    <li class="nav2"><a href="<%= Utilities.getPageURL(URL, (actualPage + 1), hasTag) %>" class="right sprite carouselArrowRight" title ="Link Description">&raquo;</a></li>
                    <%                                 
                                }
                            }                    
                        } 
                    %>
                    
                </ul>
            </div>                       
        </div>            

    </c:when>
    <c:otherwise>
        <c:if test="${mode == modeEdit}">
            <h2>${placeholder}</h2>
        </c:if>
    </c:otherwise>
</c:choose>
<!-- END: mix_5x3artspag_01 -->
