<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Short Teaser component....
 *  
 * 
 *
 *  component id = mix_1arts_07
 *  
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 24/01/2013  | Julio Esquivel         | Initial Creation.
 * 1.1     | 28/02/2013  | Juan Jose Pol.         | Adding validation to show main image, image context and topic.
 * 2.0     | 02/08/2013  | Antonio Oliva          | Added automatic mode.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>

<%@include file="/apps/deportes/generics/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="commons.editcomponent.placeholder" var="placeholdercomponentmessage"/>
<fmt:message key="commons.shortteaser.title" var="componentname"/>



    <%-- adding NoteManagerService service --%>  
    <c:choose>
        <c:when test="${properties.mode == 'manual'}">
			<c:if test="${not empty properties.pathshortarticle}">
    			<tg:noteProvider path="${properties.pathshortarticle}"/>
			</c:if>
        </c:when>
        <c:when test="${properties.mode == 'automatic'}">
            <c:choose>
                <c:when test="${not empty properties.parentPath && not empty properties.topic}">
                    <tg:getNotesFromPathAndTags name="note" path="${properties.parentPath}" limit="2" noteType="genericNote"  index="${properties.noteIndex}" tag="${properties.topic[0]}" asList="false"/>
                </c:when>
				<c:when test="${empty properties.parentPath && not empty properties.topic}">
					<tg:getNotesFromTags name="note" tags="${properties.topic}" limit="2" index="${properties.noteIndex}" asList="false"/>
                </c:when>
				<c:when test="${not empty properties.parentPath && empty properties.topic}">
                    <tg:getLatestNotes path="${properties.parentPath}"  name="note" limit="2" noteType="genericNote" index="${properties.noteIndex}" asList="false"/>
                </c:when>
                <c:otherwise>
                    <tg:getLatestNotes path="${properties.parentPath}"  name="note" limit="2" noteType="genericNote" index="${properties.noteIndex}" asList="false"/>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <tg:noteProvider path="${properties.pathshortarticle}"/>
        </c:otherwise>
    </c:choose>


    <%-- adding validation to show the image context in the main image depending of the template --%>
    <c:if test="${note != null}">
        <c:set var="rendition">
            <tg:renditionSelector image="${note.noteImageAsset}" width="84" height="63"/>
        </c:set>
        <c:if test="${note.photo == true }">  
            <c:set var="imageContext" value="sprite photo-corner" />  
        </c:if>    
        <c:if test="${note.video == true}">
            <c:set var="imageContext" value="sprite video-corner" />  
        </c:if>    
    </c:if>


    <%-- Merge HTML and CSS for the mix_1arts_07 Component --%>
    <c:choose>
        <c:when test="${note != null}">
        
            <!-- BEGIN: mix_1arts_07 -->
            
            <%--  adding the div tag only the container is a parsys --%>
            <c:if test="${componentContext.parent.component.name == 'parsys'}">
                <div class="mix_1arts_07">
            </c:if>  

                    <ul class="articles"> 
                        <li> 
                            <a href="${note.url}.html" title="${note.title}" target="${properties.linkTarget}" class="link" > 
                            
                                <%-- validation to show the main image, context image, and topic --%>
                                <c:if test="${properties.showimage == 'true' }">                            

                                    <img src="${rendition}" alt="${note.title}"/>
                                    
                                    <c:if test="${properties.showcontext == 'true' }">
                                        <span class="${imageContext}"></span>
                                    </c:if> 
                                       
                                </c:if>
                                   
                                <c:if test="${properties.showtopic == 'true' }">                            
                                    <h3 class="textcolor-title2"><c:out value="${note.topic}"/></h3>
                                </c:if>    
                                    
                                <p><c:out value="${note.title}"/></p>
                                <span class="icon"></span> 
                            </a> 
                        </li>
                    </ul>
                    
            <%--  adding the div tag only the container is a parsys --%>
            <c:if test="${componentContext.parent.component.name == 'parsys'}">
                </div>
            </c:if> 
            
            <!-- END: mix_1arts_07 -->
            
        </c:when>
        <c:otherwise>
        
            <c:if test = "${authorMode}">
                <div class="mix_1arts_07">
                    <ul class="articles">
                        <h3 class="cq-texthint-placeholder" id="cq-gen96"><c:out value="${placeholdercomponentmessage} "/> <c:out value="${componentname}"/></h3>
                        <img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="" id="cq-gen97"> 
                    </ul>    
                 </div>
            </c:if>
         
        </c:otherwise>
    </c:choose>
    

    <c:if test = "${authorMode}"> 
        <div style="clear:both;"></div>
    </c:if>  


    <%pageContext.removeAttribute("note");%>