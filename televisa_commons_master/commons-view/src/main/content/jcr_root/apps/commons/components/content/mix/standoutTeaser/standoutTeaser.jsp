<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  standout Teaser component.
 *  
 *  component id = mix_1arts_03
 *  
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 24/01/2013  | Julio Esquivel         | Initial Creation.
 * 1.0     | 28/02/2013  | Juan Jose Pol          | adding NoteManagerService service with jstl
 * 1.0     | 04/03/2013  | Juan Jose Pol          | HTML updated and validation to show image context added
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>

<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="commons.editcomponent.placeholder" var="placeholdercomponentmessage"/>
<fmt:message key="commons.standoutTeaser.title" var="componentname"/>

    <%-- adding NoteManagerService service  to the different articles selected--%>  
    
    <c:if test="${not empty properties.patharticle}">
        <tg:namedNoteProvider name="patharticleNote" path="${properties.patharticle}"/> 
        <c:set var="rendition">
            <tg:renditionSelector image="${patharticleNote.noteImageAsset}" width="408" height="306"/>
        </c:set>
    </c:if>
    
    <c:if test="${not empty properties.patharticlerelated}">
        <tg:namedNoteProvider name="patharticleNoteRelated" path="${properties.patharticlerelated}"/> 
    </c:if>
    
    <c:if test="${not empty properties.patharticlerelatedvideo}">
        <tg:namedNoteProvider name="patharticleNoteVideo" path="${properties.patharticlerelatedvideo}"/> 
    </c:if>
    
    <%-- adding validation to show the image context in the main image depending of the template --%>
    <c:if test="${patharticleNote != null}">
        <c:if test="${patharticleNote.photo == true }">  
            <c:set var="imageContext" value="sprite photo-corner" />  
        </c:if>    
        <c:if test="${patharticleNote.video == true}">
            <c:set var="imageContext" value="sprite video-corner" />  
        </c:if>    
    </c:if>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>
    <cq:include path="strPleca01" resourceType="/apps/commons/components/content/structure/singleTitleBox" />
    <c:choose>
        <c:when test="${patharticleNote != null}">
            <div class="mix_1arts_03" data-enhance="false">
                <div class="type2b">        
        
                    <article class="articleBigModule">
                    
                        <a href="${patharticleNote.url}.html" target="${properties.linkTarget}">
                            <div class="resize">
                               <img src="${rendition}" alt="${patharticleNote.title}">
                                
                                <%-- addign validation to show image context--%>    
                                <c:if test="${properties.showcontext == 'true' }">
                                    <span class="${imageContext}"></span>                       
                                </c:if>
                        </a>
                        
                        <div class="floatRight">                            
            
                            <a class="category" href="${patharticleNote.url}.html" target="${properties.linkTarget}" ><c:out value="${patharticleNote.topic}"/></a>
                            
                            <a href="${patharticleNote.url}.html" target="${properties.linkTarget}" >
                                <h3>  ${patharticleNote.title} </h3>
                                <p class="desc"> ${patharticleNote.description} </p>
                            </a>
                            <ul>
                                <c:choose>
                                    <c:when test="${patharticleNoteRelated != null}">
                                        <li><a href="${patharticleNoteRelated.url}.html"  target="${properties.linkTarget2}" ><span class="iconArticle"><i class="tvsa-double-caret-right tvsa-1x "></i></span><p>${patharticleNoteRelated.title}</p></a></li>
                                    </c:when>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${patharticleNoteVideo != null}">
                                        <li><a href="${patharticleNoteVideo.url}.html" target="${properties.linkTarget3}" ><span class="iconVideo"><i class=" tvsa-videocamera tvsa-1x "></i></span><p>${patharticleNoteVideo.title}</p></a></li>
                                    </c:when>
                                </c:choose>
                            </ul>
                        </div>
                    </article>
        
                </div>
            </div>
        </c:when>
        <c:otherwise>
                        
            <%-- show message only if this is in author mode --%>
            <c:if test = "${authorMode}"> 
                <h3 class="cq-texthint-placeholder" id="cq-gen96"><c:out value="${placeholdercomponentmessage}"/>&nbsp;<c:out value="${componentname}"/></h3>
                <img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="" id="cq-gen97">  
            </c:if>           
        
        </c:otherwise>
    </c:choose>

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>

    <%--  adding a new div to move the parsys  --%>
    <c:if test = "${authorMode}"> 
        <div style="clear:both;"></div>
    </c:if>
    
    
    <%--  remove note object in page context --%>
    <%pageContext.removeAttribute("patharticleNote");%>
    <%pageContext.removeAttribute("patharticleNoteRelated");%>
    <%pageContext.removeAttribute("patharticleNoteVideo");%>