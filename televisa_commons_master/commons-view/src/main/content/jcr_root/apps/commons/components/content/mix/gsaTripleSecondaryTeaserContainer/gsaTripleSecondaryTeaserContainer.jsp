<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Triple Secondary Teaser Container component.
 *  This components is used like a container composed of three mix_1arts_09 components.
 *
 *  component id = mix_3arts_02
 *  
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 21/03/2013  | Jose Alejandro Urizar  | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="commons.gsatriplesecondaryteasercontainer.placeholder" var="placeholdercomponentmessage"/>
<!--fmt:message key="generics.secondaryteaser.title" var="componentname"/-->
<fmt:message key="commons.secondaryteaser.messageuser" var="messageuser"/>

    <%-- adding change in attribute border-right to 'none' only previewMode  --%> 
    <c:if test="${authorMode2}">
        <style>
            div.mix_3arts_02 div.mix_1arts_09 {
                float: left;
                padding: 0px 12px 0 12px;
                border-right: none;
            }
    </style>
    </c:if>
    
    
    <%-- Merge HTML and CSS for the mix_3arts_03 Component   --%>
    <!-- BEGIN:mix_3arts_02 -->
    
    <div class="mix_3arts_02">
        
        <div class="mix_3arts_02_first">
          
          
            <!-- starts one -->
             <%-- adding NoteManagerService service --%>  
    <c:if test="${properties.searchOption == 'internal'}">
        <tg:noteProvider path="${properties.internalSearch}"/> 
        
        <c:set var="rendition">
            <tg:renditionSelector image="${note.noteImageAsset}" width="192" height="108"/>
        </c:set>
    </c:if>

        
    <%-- adding validation to show the image context in the main image depending of the template --%>
    <c:if test="${note != null}">
        <c:if test="${note.photo == true }">  
            <c:set var="imageContext" value="mix_1arts_09_sprite photo-corner" />  
        </c:if>    
        <c:if test="${note.video == true}">
            <c:set var="imageContext" value="mix_1arts_09_sprite video-corner" />  
        </c:if>    
    </c:if> 
        
      
    <%--  Merge hmtl and css for the mix_1arts_09 Component --%>                               
    <c:choose>
    
        <c:when test="${note != null}">        
        
            <!-- BEGIN:mix_1arts_09 -->    
                        <%--  adding the div tag only the container is a parsys --%>
            <c:if test="${componentContext.parent.component.name == 'parsys'}">
                <div class="mix_1arts_09">
            </c:if>          
            
            
                <div>
                    <ul class="mix_1arts_09_thumb">
                        <li>
                            <a href="${note.url}.html" title="${note.title}"  target="${properties.linkTarget}" >
                                
                                <%-- validation to show the image if exist the rendition--%>                        
                                <c:if test="${not empty rendition}">
                                
                                    <c:if test="${properties.showimage == true}">
                                    
                                        <img src="${rendition}" alt="${note.title}"/>
                                        
                                        <%-- adding validation to show the image context --%> 
                                        <c:if test="${properties.showcontext == true}">
                                            <span class="${imageContext}"></span>
                                        </c:if>    
                                            
                                    </c:if>
                                    
                                </c:if>
                                
                                <%-- adding validation to show the topic--%>    
                                <c:if test="${properties.showtopic == true}">
                                    <h2><c:out value="${note.topic}"/></h2>                    
                                </c:if> 
                                
                                
                                <%-- adding validation to show the description--%>        
                                <c:if test="${properties.showtitle == true}">    
                                    <p><c:out value="${note.title}"/></p>                                                        
                                </c:if> 
                                
                                <c:if test="${properties.showimage != 'true' && properties.showtopic != 'true' &&  properties.showtitle != 'true' }">
                                    <h3 class="cq-texthint-placeholder" id="cq-gen96"> <c:out value="${messageuser}"/></h3>
                                </c:if> 
                    
                                <span class="icon"></span>
                                
                           </a>
                       </li>
                     </ul>
                </div>
                
            <c:if test="${componentContext.parent.component.name == 'parsys'}">    
            </div>
            </c:if>
            <!--END:mix_1arts_09-->
            
        </c:when>
    </c:choose>  
        
    <%--  adding a new div to move the parsys --%>
    <c:if test = "${authorMode2}"> 
        <div style="clear:both;"></div>
    </c:if>    
    
    <%--  remove note object in pageContext when add two or more primaryTeaser in a container --%>
    <%pageContext.removeAttribute("note");%>
            
            <c:if test="${properties.searchOption == 'external'}">
                 <div class="mix_1arts_09">
                    <ul class="mix_1arts_09_thumb">
                        <li>
                            <a href="${properties.externalSearch_url}">
                                <img src="${properties.externalSearch_image_url}">
                                <h2></h2>
                                <p>${properties.externalSearch_title}</p>
                                <span class="icon"></span>
                            </a>
                        </li>
                    </ul>
                </div>   
            </c:if>        
             <!-- ends one -->
        </div>
        
        <div>
             <!-- starts two -->
             <%-- adding NoteManagerService service --%>  
    <c:if test="${properties.searchOption2 == 'internal2'}">
        <tg:noteProvider path="${properties.internalSearch2}"/> 
        
        <c:set var="rendition">
            <tg:renditionSelector image="${note.noteImageAsset}" width="192" height="108"/>
        </c:set>
    </c:if>

        
    <%-- adding validation to show the image context in the main image depending of the template --%>
    <c:if test="${note != null}">
        <c:if test="${note.photo == true }">  
            <c:set var="imageContext" value="mix_1arts_09_sprite photo-corner" />  
        </c:if>    
        <c:if test="${note.video == true}">
            <c:set var="imageContext" value="mix_1arts_09_sprite video-corner" />  
        </c:if>    
    </c:if> 
        
      
    <%--  Merge hmtl and css for the mix_1arts_09 Component --%>                               
    <c:choose>
    
        <c:when test="${note != null}">        
        
            <!-- BEGIN:mix_1arts_09 -->    
                        <%--  adding the div tag only the container is a parsys --%>
            <c:if test="${componentContext.parent.component.name == 'parsys'}">
                <div class="mix_1arts_09">
            </c:if>          
            
            
                <div>
                    <ul class="mix_1arts_09_thumb">
                        <li>
                            <a href="${note.url}.html" title="${note.title}"  target="${properties.linkTarget2}" >
                                
                                <%-- validation to show the image if exist the rendition--%>                        
                                <c:if test="${not empty rendition}">
                                
                                    <c:if test="${properties.showimage2 == true}">
                                    
                                        <img src="${rendition}" alt="${note.title}"/>
                                        
                                        <%-- adding validation to show the image context --%> 
                                        <c:if test="${properties.showcontext2 == true}">
                                            <span class="${imageContext}"></span>
                                        </c:if>    
                                            
                                    </c:if>
                                    
                                </c:if>
                                
                                <%-- adding validation to show the topic--%>    
                                <c:if test="${properties.showtopic2 == true}">
                                    <h2><c:out value="${note.topic}"/></h2>                    
                                </c:if> 
                                
                                
                                <%-- adding validation to show the description--%>        
                                <c:if test="${properties.showtitle2 == true}">    
                                    <p><c:out value="${note.title}"/></p>                                                        
                                </c:if> 
                                
                                <c:if test="${properties.showimage2 != 'true' && properties.showtopic2 != 'true' &&  properties.showtitle2 != 'true' }">
                                    <h3 class="cq-texthint-placeholder" id="cq-gen96"> <c:out value="${messageuser}"/></h3>
                                </c:if> 
                    
                                <span class="icon"></span>
                                
                           </a>
                       </li>
                     </ul>
                </div>
                
            <c:if test="${componentContext.parent.component.name == 'parsys'}">    
            </div>
            </c:if>
            <!--END:mix_1arts_09-->
            
        </c:when>
    </c:choose>  
        
    <%--  adding a new div to move the parsys --%>
    <c:if test = "${authorMode2}"> 
        <div style="clear:both;"></div>
    </c:if>    
    
    <%--  remove note object in pageContext when add two or more primaryTeaser in a container --%>
    <%pageContext.removeAttribute("note");%>
            
            <c:if test="${properties.searchOption2 == 'external2'}">
                 <div class="mix_1arts_09">
                    <ul class="mix_1arts_09_thumb">
                        <li>
                            <a href="${properties.externalSearch2_url}">
                                <img src="${properties.externalSearch2_image_url}">
                                <h2></h2>
                                <p>${properties.externalSearch2_title}</p>
                                <span class="icon"></span>
                            </a>
                        </li>
                    </ul>
                </div>   
            </c:if>        
             <!-- ends two -->
        </div>    
                           
        <div class="mix_3arts_02_last">
             <!-- starts three -->
             <%-- adding NoteManagerService service --%>  
    <c:if test="${properties.searchOption3 == 'internal3'}">
        <tg:noteProvider path="${properties.internalSearch3}"/> 
        
        <c:set var="rendition">
            <tg:renditionSelector image="${note.noteImageAsset}" width="192" height="108"/>
        </c:set>
    </c:if>

        
    <%-- adding validation to show the image context in the main image depending of the template --%>
    <c:if test="${note != null}">
        <c:if test="${note.photo == true }">  
            <c:set var="imageContext" value="mix_1arts_09_sprite photo-corner" />  
        </c:if>    
        <c:if test="${note.video == true}">
            <c:set var="imageContext" value="mix_1arts_09_sprite video-corner" />  
        </c:if>    
    </c:if> 
        
      
    <%--  Merge hmtl and css for the mix_1arts_09 Component --%>                               
    <c:choose>
    
        <c:when test="${note != null}">        
        
            <!-- BEGIN:mix_1arts_09 -->    
                        <%--  adding the div tag only the container is a parsys --%>
            <c:if test="${componentContext.parent.component.name == 'parsys'}">
                <div class="mix_1arts_09">
            </c:if>          
            
            
                <div>
                    <ul class="mix_1arts_09_thumb">
                        <li>
                            <a href="${note.url}.html" title="${note.title}"  target="${properties.linkTarget3}" >
                                
                                <%-- validation to show the image if exist the rendition--%>                        
                                <c:if test="${not empty rendition}">
                                
                                    <c:if test="${properties.showimage3 == true}">
                                    
                                        <img src="${rendition}" alt="${note.title}"/>
                                        
                                        <%-- adding validation to show the image context --%> 
                                        <c:if test="${properties.showcontext3 == true}">
                                            <span class="${imageContext}"></span>
                                        </c:if>    
                                            
                                    </c:if>
                                    
                                </c:if>
                                
                                <%-- adding validation to show the topic--%>    
                                <c:if test="${properties.showtopic3 == true}">
                                    <h2><c:out value="${note.topic}"/></h2>                    
                                </c:if> 
                                
                                
                                <%-- adding validation to show the description--%>        
                                <c:if test="${properties.showtitle3 == true}">    
                                    <p><c:out value="${note.title}"/></p>                                                        
                                </c:if> 
                                
                                <c:if test="${properties.showimage3 != 'true' && properties.showtopic3 != 'true' &&  properties.showtitle3 != 'true' }">
                                    <h3 class="cq-texthint-placeholder" id="cq-gen96"> <c:out value="${messageuser}"/></h3>
                                </c:if> 
                    
                                <span class="icon"></span>
                                
                           </a>
                       </li>
                     </ul>
                </div>
                
            <c:if test="${componentContext.parent.component.name == 'parsys'}">    
            </div>
            </c:if>
            <!--END:mix_1arts_09-->
            
        </c:when>
    </c:choose>  
        
    <%--  adding a new div to move the parsys --%>
    <c:if test = "${authorMode2}"> 
        <div style="clear:both;"></div>
    </c:if>    
    
    <%--  remove note object in pageContext when add two or more primaryTeaser in a container --%>
    <%pageContext.removeAttribute("note");%>
            
            <c:if test="${properties.searchOption3 == 'external3'}">
                 <div class="mix_1arts_09">
                    <ul class="mix_1arts_09_thumb">
                        <li>
                            <a href="${properties.externalSearch3_url}">
                                <img src="${properties.externalSearch3_image_url}">
                                <h2></h2>
                                <p>${properties.externalSearch3_title}</p>
                                <span class="icon"></span>
                            </a>
                        </li>
                    </ul>
                </div>   
            </c:if>        
             <!-- ends three -->
        </div>

    </div>
	<%-- Show placeholder when component is not configured yet --%>
	<c:if test="${authorMode}">
        <c:if test="${empty properties.searchOption && empty properties.searchOption2 && empty properties.searchOption3}">
            <h3 class="cq-texthint-placeholder"> <c:out value="${placeholdercomponentmessage}"/></h3>
            <img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" style="height: 150px; width: 100%;">
        </c:if>
    </c:if>
    <!--END:mix_3arts_02-->
    
    
    <%--  adding a new div to move the parsys --%>
    <c:if test = "${authorMode}"> 
        <div style="clear:both;"></div>
    </c:if>   
