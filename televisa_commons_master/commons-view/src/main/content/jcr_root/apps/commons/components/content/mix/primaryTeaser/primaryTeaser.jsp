<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Primary Teaser component.
 *  This component is used to retrieved the image rendition of 300 * 168, and
 *  the description of a generic article, and get the template used in the generic
 *  article, also the author can set a topic in the dialog of this component,
 *  the user can choose between show and not show the image context...
 * 
 *
 *  component id = mix_1arts_08
 *  
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 23/01/2013  | Juan Jose Pol.         | Initial Creation.
 * 1.1     | 08/02/2013  | Juan Jose Pol.         | HTML updated
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>

<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="commons.editcomponent.placeholder" var="placeholdercomponentmessage"/>
<fmt:message key="commons.primaryteaser.title" var="componentname"/>
<tg:noteRemover name="note"/>

<%-- adding NoteManagerService service --%>
    <c:if test="${not empty properties.patharticle}">
        <tg:noteProvider path="${properties.patharticle}"/> 
        
        <c:set var="rendition">
            <tg:renditionSelector image="${note.noteImageAsset}" width="300" height="169"/>
        </c:set>

        <c:choose>
          <c:when test="${fn:contains(rendition, '169')}">
              <c:set var="renditionAlpha" value="${rendition}" />  
          </c:when>
          <c:otherwise>
                    <c:set var="renditionAlpha">
                        <tg:renditionSelector image="${note.noteImageAsset}" width="300" height="168"/>
                    </c:set>
          </c:otherwise>
        </c:choose>

    </c:if>

    <%-- adding validation to show the image context in the main image depending of the template --%>
    <c:if test="${note != null}">
        <c:if test="${note.photo == true }">  
            <c:set var="imageContext" value="sprite photo-corner" />  
        </c:if>    
        <c:if test="${note.video == true}">
            <c:set var="imageContext" value="sprite video-corner" />  
        </c:if>    
    </c:if>
    

    <%-- Merge HTML and CSS for the mix_1arts_08 Component--%>
    <c:choose>        
        <c:when test="${note != null}">
            
            <!-- BEGIN: <mix_1arts_08> -->
            
            <%--  adding the div tag only the container is a parsys --%>
            <c:if test="${componentContext.parent.component.name == 'parsys'}">
                <div class="mix_1arts_08">
            </c:if>            

                <div>
                    <article class="articleMedium">
                    
                        <a href="${note.url}.html" title="${note.title}" target="${properties.linkTarget}">
                            
                            <%-- validation to show the image if exist the rendition--%>                        
                            <c:if test="${not empty renditionAlpha}">              
                                  
                                <img src="${renditionAlpha}" alt="${note.title}" />
                                                                
                                 <%-- show image context --%>
                                <c:if test="${properties.showcontext == 'true'}">                            
                                    <span class="${imageContext}"></span>
                                </c:if> 
                                                                
                            </c:if>  
                        </a>                     

                        <div>                        
                            <h2><a href="${note.url}.html" title="${note.title}" target="${properties.linkTarget}"><c:out value="${note.topic}"/></a></h2>   
                            <h4><a href="${note.url}.html" title="${note.title}" target="${properties.linkTarget}"><c:out value="${note.title}"/></a></h4>
                            <span class="icon"></span>    
                        </div>  

                    </article>                       
                    
                </div>
               
            <%--  adding the div tag only the container is a parsys --%>
            <c:if test="${componentContext.parent.component.name == 'parsys'}">
                </div>
            </c:if>
                   
            <!-- END: <mix_1arts_08> -->     
                
        </c:when>
        <c:otherwise>
        
            <%-- show message only if this is in author mode --%>
            <c:if test = "${authorMode}"> 
                <div class="mix_1arts_08">     
                    <div>
                        <article class="articleMedium">          
                            <h3 class="cq-texthint-placeholder" id="cq-gen96"><c:out value="${placeholdercomponentmessage} "/> <c:out value="${componentname}"/></h3>
                            <img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="" id="cq-gen97" style="width:99%; height: 80%;">  
                        </article>                       
                    
                    </div> 
                </div>
            </c:if>
           
        </c:otherwise>
    </c:choose>
    
    
    <%--  adding a new div to move the parsys  --%>
    <c:if test = "${authorMode}"> 
        <div style="clear:both;"></div>
    </c:if>  
    
    <%--  remove note object in pageContext when add two or more primaryTeaser in a container --%>
    <%pageContext.removeAttribute("note");%>