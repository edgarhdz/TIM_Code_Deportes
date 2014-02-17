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

<%@include file="/apps/deportes/generics/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="commons.editcomponent.placeholder" var="placeholdercomponentmessage"/>
<fmt:message key="commons.primaryteaser.title" var="componentname"/>
<tg:noteRemover name="note"/>

    <%-- adding NoteManagerService service --%>  
    <c:choose>
        <c:when test="${properties.mode == 'manual'}">
			<c:if test="${not empty properties.patharticle}">
    			<tg:noteProvider path="${properties.patharticle}"/>
			</c:if>
        </c:when>
        <c:when test="${properties.mode == 'automatic'}">
            <c:choose>
                <c:when test="${not empty properties.parentPath && not empty properties.topic}">
                    <tg:getNotesFromPathAndTags name="note" path="${properties.parentPath}" noteType="genericNote" limit="1" index="${properties.noteIndex}" tag="${properties.topic[0]}" asList="false"/>
                </c:when>
				<c:when test="${empty properties.parentPath && not empty properties.topic}">
					<tg:getNotesFromTags name="note" tags="${properties.topic}" limit="1" index="${properties.noteIndex}" asList="false"/>
                </c:when>
				<c:when test="${not empty properties.parentPath && empty properties.topic}">
                    <tg:getLatestNotes path="${properties.parentPath}"  name="note" noteType="genericNote" limit="1" index="${properties.noteIndex}" asList="false"/>
                </c:when>
                <c:otherwise>
                    <tg:getLatestNotes path="${properties.parentPath}"  name="note" noteType="genericNote"  limit="1" index="${properties.noteIndex}" asList="false"/>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <tg:noteProvider path="${properties.patharticle}"/>
        </c:otherwise>
    </c:choose>

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
            <c:choose>
              <c:when test="${properties.renderSize == 'large'}">
                <c:set var="rendition">
                    <tg:renditionSelector image="${note.noteImageAsset}" width="300" height="225"/>
                </c:set>

                <c:choose>
                  <c:when test="${fn:contains(rendition, '225')}">
                      <c:set var="renditionAlpha" value="${rendition}" />
                  </c:when>
                  <c:otherwise>
                    <c:set var="renditionAlpha">
                       <tg:renditionSelector image="${note.noteImageAsset}" width="300" height="225"/>
                    </c:set>
                  </c:otherwise>
                </c:choose>
              </c:when>
              <c:otherwise>
                <c:set var="rendition">
                    <tg:renditionSelector image="${note.noteImageAsset}" width="300" height="169"/>
                </c:set>

                <c:choose>
                  <c:when test="${fn:contains(rendition, '169')}">
                      <c:set var="renditionAlpha" value="${rendition}" />  
                  </c:when>
                  <c:otherwise>
                    <c:set var="renditionAlpha">
                       <tg:renditionSelector image="${note.noteImageAsset}" width="300" height="169"/>
                    </c:set>
                  </c:otherwise>
                </c:choose>
              </c:otherwise>
            </c:choose>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>

    		<!-- BEGIN: str_pleca_01 -->
                <cq:include path="strPleca01" resourceType="/apps/commons/components/content/structure/singleTitleBox" />
            <!-- END: str_pleca_01 -->

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
                            <span><a href="${note.url}.html" title="${note.title}" target="${properties.linkTarget}" class="textcolor-title2"><c:out value="${note.topic}"/></a></span>
                            <h2><a href="${note.url}.html" title="${note.title}"  target="${properties.linkTarget}"><c:out value="${note.title}"/></a></h2>
                            <span class="icon"></span>
                        </div>
                    </article>
                </div>
            <%--  adding the div tag only the container is a parsys --%>
            <c:if test="${componentContext.parent.component.name == 'parsys'}">
                </div>
            </c:if>
            <!-- END: <mix_1arts_08> -->

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>                
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
