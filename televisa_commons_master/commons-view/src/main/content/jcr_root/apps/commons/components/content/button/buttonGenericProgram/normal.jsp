<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Generic Program Button.
 *
 *  Displays a generic program article button.
 *  
 *  component id = btn_progr_01
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 07/02/2013  | Gerardo Escobar        | Initial Creation.
 * 1.1     | 11/02/2013  | Gerardo Escobar        | Update the dialog to show the image and updated the properties.
 * 1.2     | 20/02/2013  | Gerardo Escobar        | Updated the component edit configuration and deny image upload.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>

<fmt:message key="commons.buttonprogram.message.configure" var="messageConfigure"/>
<fmt:message key="commons.buttonprogram.message.invalid" var="messageInvalid"/>
<fmt:message key="commons.buttonprogram.message.notfound" var="messageNotfound"/>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>

    <!-- BEGIN: LIVENOW -->
    <div class="btn_progr_01">
    
        <%-- adding single title box component --%>    
        <!-- BEGIN: str_pleca_01 -->
        <cq:include path="singletitlebox" resourceType="/apps/commons/components/content/structure/singleTitleBox" />
        <!-- END: str_pleca_01 -->   
    
        
        <c:choose>
        
            <%-- show message if not exist an article path  --%>    
            <c:when test="${empty properties.pathProgram}">
                <c:if test="${authorMode}">  
                    <div class="envivo">
                         <div class="envivo_content" style="position: initial; padding: initial;">
                            <div  class="channelImg">              
                                 <img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="" id="cq-gen97" style="height: 109px; width: 92%; background-color: #666;">
                                 <h3 class="cq-texthint-placeholder" id="cq-gen96"><c:out value="${messageConfigure}"/></h3>  
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:when>

            <c:otherwise>
                        
                <%-- adding NoteManagerService service --%>      
                <tg:noteProvider path="${properties.pathProgram}"/>
                
                <c:choose>
                    <c:when test="${empty note}">
                        <c:if test="${authorMode}">
                             <div class="envivo">
                                 <div class="envivo_content" style="position: initial; padding: initial;">
                                    <div  class="channelImg">              
                                         <img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="" id="cq-gen97" style="height: 109px; width: 92%; background-color: #666;">
                                         <h3 class="cq-texthint-placeholder" id="cq-gen96"><c:out value="${messageNotfound}"/></h3>  
                                    </div>
                                </div>
                            </div>

                        </c:if>
                    </c:when>

                    <c:when test="${!note.program}">
                        <c:if test="${authorMode}">
                            <div class="envivo">   
                                 <div class="envivo_content" style="position: initial; padding: initial;">
                                    <div  class="channelImg">              
                                         <img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="" id="cq-gen97" style="height: 109px; width: 92%; background-color: #666;">
                                         <h3 class="cq-texthint-placeholder" id="cq-gen96"><c:out value="${messageInvalid}"/></h3>  
                                    </div>
                                </div>
                            </div>

                        </c:if>
                    </c:when>


                    <c:otherwise>
                        <div class="envivo">
                            <%-- adding rendition in page context--%>
                            <c:set var="rendition">
                                <tg:programRenditionSelector width="300" height="168"/>
                            </c:set>
                            
                            <img src="${rendition}" alt="${note.programImage.description}" />
                            <div class="back_overlay"></div>

                            <div class="envivo_content">
                                <c:set var="url" value="${note.url}.html"/>
                                <c:set var="title" value="${note.title}"/>
    
                                <a  href="${url}" title="${title}" target="${properties.linkTarget}" >
                                    <h3>${note.programName}</h3>
                                    <div class="line"></div>
                                    <p>${properties.teaserText}</p>
                                    
                                    <c:if test= "${not empty properties.fileReference}">
                                        <div  class="channelImg">
                                            <img src="${properties.fileReference}" alt="${note.programChannel}" class="channelImg">
                                        </div>
                                        <i class="tvsa-double-caret-right"></i>
                                    </c:if>
                                </a>
                            </div>
                            
                        </div>    
                                            
                    </c:otherwise>

                </c:choose>
            </c:otherwise>

        </c:choose>
          
    </div>
    
    <!-- END: LIVENOW -->
        
<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>


    <%--  remove the NoteManagerService in page context --%>
    <%pageContext.removeAttribute("note");%>