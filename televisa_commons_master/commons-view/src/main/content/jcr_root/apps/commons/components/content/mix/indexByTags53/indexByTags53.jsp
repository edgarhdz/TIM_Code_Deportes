
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
<%@include file="/apps/commons/components/global.jsp"%>

<%@page import="com.televisa.commons.services.datamodel.Note"%>
<%@page import="com.televisa.commons.services.datamodel.RenditionSize"%>
<%@page import="com.televisa.commons.services.datamodel.objects.impl.FilterIndexByTagsImpl"%>
<%@page import="com.televisa.commons.services.datamodel.objects.InfoPage"%>
<%@page import="com.televisa.commons.services.services.InfoPageManagerService"%>
<%@page import="com.televisa.commons.services.utilities.Utilities"%>


<%@page import="java.util.List" %>
<%@page import="com.day.cq.wcm.api.WCMMode" %>

<fmt:message key="commons.indexByTags.placeholder" var="placeholder"/>

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
    String[] tags = properties.get("cq:tags", new String[0]);
  
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

    if(!path.trim().equals("") && pageNumber > 0){
        
        FilterIndexByTagsImpl filter;
        InfoPageManagerService service = (InfoPageManagerService) sling.getService(InfoPageManagerService.class);
        if (tags.length==0) {
            filter = new FilterIndexByTagsImpl(path, null, showarticles, showvideos, showphotogalleries, showpartidos, 15, 15, pageNumber);
            infoPage = service.getNotesByPath(slingRequest, filter);
        } else {
            filter = new FilterIndexByTagsImpl(path, tags, showarticles, showvideos, showphotogalleries, showpartidos, 15, 15, pageNumber);
            infoPage = service.getNotesByPathAndTags(slingRequest, filter);
        }
     
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

<c:choose>
    <c:when test="${(not empty notes) && (not empty infoPage) && results}">
        <!-- BEGIN: mix_15arts_02 -->
        <div class="mix_15arts_02" data-enhance="false">
           <!-- BEGIN: list_container -->
           <div class="list_container">            
            <%
                if(notes != null){
                    for(int i = 0; (i < notes.size()); i++){
                        Note note = notes.get(i);
                        pageContext.setAttribute("note", note);
            %>
                    <c:set var="RENDITION_192_107">
                        <tg:renditionSelector image="${note.noteImageAsset}" width="192" height="107"/>
                    </c:set>

            <%    if( (i != 0) && (i % 3) == 0){     %>
                    </div>

                    <div class=" renglon">
            <%    }else if(i == 0){     %>
                    <!-- BEGIN: mix_1arts_16 -->
                    <div class=" renglon">
            <%    }     %>
                   <% if (  (i + 1 ) % 3 == 2  ) { %>
                        <div class="middle">
                   <% }  %>
                         <!-- BEGIN: mix_1arts_16 -->
                         <div class="mix_1arts_16">
                            <ul class="mix_1arts_16_thumb">
                               <li>
                                  <a href="${note.url}.html" title="${note.title}">
                                     <img src="${RENDITION_192_107}" alt="${note.title}"/>
                                     <%-- show image context --%>
                                     <span class="mix_1arts_16_sprite video-corner"></span>
                                     <div class="background-color-pleca1"><i class="tvsa-play textcolor-title3"></i></div>
                                     <h6 class="textcolor-title2"><c:out value="${note.topic}"/></h6>
                                     <p><c:out value="${note.title}"/></p>
                                     <i class="icon tvsa-caret-right"></i>
                                  </a>
                               </li>
                            </ul>
                         </div>
                        <!-- END: mix_1arts_16 -->
                   <% if (  (i + 1 ) % 3 == 2  ) { %>
                       </div>
                   <% }  %>
                <%    if (i == (notes.size() - 1)){     %>    
                        </div>
                        <!-- END: mix_1arts_16 -->
                <%    }     %>
            <%
                    }
                }
            %>
            </div>
            <div class="degraded"></div>

            <!-- BEGIN: nav_paginator_01-->
            <div class="nav_paginator_01 dotted-top">
                <ul>                                   
                    <% 
                        String URL = slingRequest.getRequestURI();
                        int totalPages = infoPage.getTotalPages();
                        int actualPage = infoPage.getActualPage();

                        for(int i = 1; i < (totalPages + 1); i++ ){ 
                            if(totalPages > 4){
                                if((i ==  1) && (actualPage > 1)){
                    %>
                    				<li class="dotted-right"><a href="<%= Utilities.getPageURL(URL, (actualPage - 1), hasTag) %>"><i class="tvsa-double-caret-left"></i></a></li>
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
                                    <li class="dotted-left"><a href="<%= Utilities.getPageURL(URL, (actualPage + 1), hasTag) %>"><i class="tvsa-double-caret-right"></i></a></li>
                    <%                                 
                                }
                            }else{
                                if((i ==  1) && (actualPage > 1)){
                    %>
				                    <li class="dotted-right"><a href="<%= Utilities.getPageURL(URL, (actualPage - 1), hasTag) %>" title ="Link Description"><i class="tvsa-double-caret-left"></i></a></li>
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
				                    <li class="dotted-left"><a href="<%= Utilities.getPageURL(URL, (actualPage + 1), hasTag) %>" title ="Link Description"><i class="tvsa-double-caret-right"></i></a></li>
                    <%
                                }
                            }
                        }
                    %>
                </ul>
            </div>
            <!-- END: nav_paginator_01 -->
        </div>
        <!-- END: mix_15arts_02 -->
    </c:when>
    <c:otherwise>
        <c:if test="${mode == modeEdit}">
            <h2>${placeholder}</h2>
        </c:if>
    </c:otherwise>
</c:choose>