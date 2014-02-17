



<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Index By Tags component.
 *  This component is used for define how 'n' numbers of twitterFeed components is going to manage the component

 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer               | Changes
 * 1.0     | 28/01/2013  | Luis Jose Sztul         | Initial Creation.
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

<%@ page import="java.util.List" %>
<%@ page import="com.day.cq.wcm.api.WCMMode" %>

<fmt:message key="commons.indexByTags.placeholder" var="placeholder"/>
<fmt:message key="commons.indexByTags.noResults.placeholder" var="noResultsPlaceholder"/>

<c:set var="mode" value="<%= WCMMode.fromRequest(request) %>"/>
<c:set var="modeEdit" value="<%= WCMMode.EDIT %>"/>

<c:if test="${mode == modeEdit}">
    <style type="text/css">
        .mix_5x3artspag_01{
            min-height: 1250px !important;
        }
        
        #CQ .dummy-input {
            background: #FFFFFF url(default/images/form/text-bg.gif) repeat-x scroll 0 0;
            border: 1px solid #B5B8C8;
            padding: 1px;
            padding-top: 3px;
            float: left;
            width: 380px;
        }
    </style>
</c:if>

<%
    String type = properties.get("type", "automatic");

    String path = properties.get("firstPage", "");
    String[] tags = properties.get("cq:tags", String[].class);
    
    InfoPage infoPage = null;    
    List<Note> notes = null;
    boolean results = false;
    Integer pageNumber = 1;

    if(type.equals("automatic")){
        if(!path.trim().equals("") && (tags != null && tags.length > 0) && pageNumber > 0){
            InfoPageManagerService service = (InfoPageManagerService) sling.getService(InfoPageManagerService.class);
            FilterIndexByTagsImpl filter = new FilterIndexByTagsImpl(path, tags, true, true, true, false, 6, 1, 1 );
            infoPage = service.getNotesByPathAndTags(slingRequest, filter);
            if(infoPage == null || infoPage.getNotes() == null || infoPage.getNotes().size() <= 0){
                //none;
                pageContext.setAttribute("notes", notes);
            }else{
                notes = infoPage.getNotes();
                pageContext.setAttribute("notes", notes);
                results = true;          
                if( notes.size() >= 1 )
                {
                    %>
                        <c:set var="firstNote" value="<%= notes.get( 0 ) %>"/>
                        <c:set var="firstImage_624_468">
                            <tg:renditionSelector image="${firstNote.noteImageAsset}" width="624" height="468"/>
                        </c:set>
                        
                        <c:set var="firstImage_63_63">
                            <tg:renditionSelector image="${firstNote.noteImageAsset}" width="63" height="63"/>
                        </c:set>
                        
                        <%-- adding validation to show the image context in the image depending of the template --%>
                        <c:if test="${firstNote != null}">
                        
                            <c:if test="${firstNote.photo == true }">  
                                <c:set var="firstNoteImageContext" value="photo-corner" />  
                            </c:if>    
                            <c:if test="${firstNote.video == true}">
                                <c:set var="firstNoteImageContext" value="video-corner" />  
                            </c:if>    
                        </c:if>
                    <%
                }                                  
                if( notes.size() >= 2 )
                {
                    %>   
                        <c:set var="secondNote" value="<%= notes.get( 1 ) %>"/>
                        <c:set var="secondImage_624_468">
                            <tg:renditionSelector image="${secondNote.noteImageAsset}" width="624" height="468"/>
                        </c:set>
                        
                        <c:set var="secondImage_63_63">
                            <tg:renditionSelector image="${secondNote.noteImageAsset}" width="63" height="63"/>
                        </c:set>
                        
                        <%-- adding validation to show the image context in the image depending of the template --%>
                        <c:if test="${secondNote != null}">
                            <c:if test="${secondNote.photo == true }">  
                                <c:set var="secondNoteImageContext" value="photo-corner" />  
                            </c:if>    
                            <c:if test="${secondNote.video == true}">
                                <c:set var="secondNoteImageContext" value="video-corner" />  
                            </c:if>    
                        </c:if>
                    <%
                }
                if( notes.size() >= 3 )
                {
                    %>   
                        <c:set var="thirdNote" value="<%= notes.get( 2 ) %>"/>
                        <c:set var="thirdImage_624_468">
                            <tg:renditionSelector image="${thirdNote.noteImageAsset}" width="624" height="468"/>
                        </c:set>
                        
                        <c:set var="thirdImage_63_63">
                            <tg:renditionSelector image="${thirdNote.noteImageAsset}" width="63" height="63"/>
                        </c:set>
                        
                        <%-- adding validation to show the image context in the image depending of the template --%>
                        <c:if test="${thirdNote != null}">
                            <c:if test="${thirdNote.photo == true }">  
                                <c:set var="thirdNoteImageContext" value="photo-corner" />  
                            </c:if>    
                            <c:if test="${thirdNote.video == true}">
                                <c:set var="thirdNoteImageContext" value="video-corner" />  
                            </c:if>    
                        </c:if>
                    <%
                }
                if( notes.size() >= 4 )
                {
                    %>   
                        <c:set var="fourthNote" value="<%= notes.get( 3 ) %>"/>
                         <c:set var="fourthImage_624_468">
                            <tg:renditionSelector image="${fourthNote.noteImageAsset}" width="624" height="468"/>
                        </c:set>
                        
                        <c:set var="fourthImage_63_63">
                            <tg:renditionSelector image="${fourthNote.noteImageAsset}" width="63" height="63"/>
                        </c:set>
                        
                        <%-- adding validation to show the image context in the image depending of the template --%>
                        <c:if test="${fourthNote != null}">
                            <c:if test="${fourthNote.photo == true }">  
                                <c:set var="fourthNoteImageContext" value="photo-corner" />  
                            </c:if>    
                            <c:if test="${fourthNote.video == true}">
                                <c:set var="fourthNoteImageContext" value="video-corner" />  
                            </c:if>    
                        </c:if>
                    <%
                }
                if( notes.size() >= 5 )
                {
                    %>   
                        <c:set var="fifthNote" value="<%= notes.get( 4 ) %>"/>
                        <c:set var="fifthImage_624_468">
                            <tg:renditionSelector image="${fifthNote.noteImageAsset}" width="624" height="468"/>
                        </c:set>
                        
                        <c:set var="fifthImage_63_63">
                            <tg:renditionSelector image="${fifthNote.noteImageAsset}" width="63" height="63"/>
                        </c:set>
                        
                        <%-- adding validation to show the image context in the image depending of the template --%>
                        <c:if test="${fifthNote != null}">
                            <c:if test="${fifthNote.photo == true }">  
                                <c:set var="fifthNoteImageContext" value="photo-corner" />  
                            </c:if>    
                            <c:if test="${fifthNote.video == true}">
                                <c:set var="fifthNoteImageContext" value="video-corner" />  
                            </c:if>    
                        </c:if>
                    <%
                }
                if( notes.size() >= 6 )
                {
                    %>   
                        <c:set var="sixthNote" value="<%= notes.get( 5 ) %>"/>
                        <c:set var="sixthImage_624_468">
                            <tg:renditionSelector image="${sixthNote.noteImageAsset}" width="624" height="468"/>
                        </c:set>
                        
                        <c:set var="sixthImage_63_63">
                            <tg:renditionSelector image="${sixthNote.noteImageAsset}" width="63" height="63"/>
                        </c:set>
                            
                        <%-- adding validation to show the image context in the image depending of the template --%>
                        <c:if test="${sixthNote != null}">
                            <c:if test="${sixthNote.photo == true }">  
                                <c:set var="sixthNoteImageContext" value="photo-corner" />  
                            </c:if>    
                            <c:if test="${sixthNote.video == true}">
                                <c:set var="sixthNoteImageContext" value="video-corner" />  
                            </c:if>    
                        </c:if>
                        
                    <%
                }
            }
        } 
    } else {
    %>
    <%-- adding NoteManagerService service to the first article--%>  
    <c:if test="${not empty properties.firstPage}">
        <tg:namedNoteProvider name="firstNote" path="${properties.firstPage}"/> 

        <c:set var="firstImage_624_468">
            <tg:renditionSelector image="${firstNote.noteImageAsset}" width="624" height="468"/>
        </c:set>
        
        <c:set var="firstImage_63_63">
            <tg:renditionSelector image="${firstNote.noteImageAsset}" width="63" height="63"/>
        </c:set>
        
        <%-- adding validation to show the image context in the image depending of the template --%>
        <c:if test="${firstNote != null}">
        
            <c:if test="${firstNote.photo == true }">  
                <c:set var="firstNoteImageContext" value="photo-corner" />  
            </c:if>    
            <c:if test="${firstNote.video == true}">
                <c:set var="firstNoteImageContext" value="video-corner" />  
            </c:if>    
        </c:if>
        
    </c:if>

    <%-- adding NoteManagerService service to the second article--%>  
    <c:if test="${not empty properties.secondPage}">

        <tg:namedNoteProvider name="secondNote" path="${properties.secondPage}"/> 
                        
        <c:set var="secondImage_624_468">
            <tg:renditionSelector image="${secondNote.noteImageAsset}" width="624" height="468"/>
        </c:set>
        
        <c:set var="secondImage_63_63">
            <tg:renditionSelector image="${secondNote.noteImageAsset}" width="63" height="63"/>
        </c:set>
        
        <%-- adding validation to show the image context in the image depending of the template --%>
        <c:if test="${secondNote != null}">
            <c:if test="${secondNote.photo == true }">  
                <c:set var="secondNoteImageContext" value="photo-corner" />  
            </c:if>    
            <c:if test="${secondNote.video == true}">
                <c:set var="secondNoteImageContext" value="video-corner" />  
            </c:if>    
        </c:if>
                
    </c:if>

    <%-- adding NoteManagerService service to the  third article--%>  
    <c:if test="${not empty properties.thirdPage}">
        <tg:namedNoteProvider name="thirdNote" path="${properties.thirdPage}"/> 
                        
        <c:set var="thirdImage_624_468">
            <tg:renditionSelector image="${thirdNote.noteImageAsset}" width="624" height="468"/>
        </c:set>
        
        <c:set var="thirdImage_63_63">
            <tg:renditionSelector image="${thirdNote.noteImageAsset}" width="63" height="63"/>
        </c:set>
        
        <%-- adding validation to show the image context in the image depending of the template --%>
        <c:if test="${thirdNote != null}">
            <c:if test="${thirdNote.photo == true }">  
                <c:set var="thirdNoteImageContext" value="photo-corner" />  
            </c:if>    
            <c:if test="${thirdNote.video == true}">
                <c:set var="thirdNoteImageContext" value="video-corner" />  
            </c:if>    
        </c:if>
        
    </c:if>


    <%-- adding NoteManagerService service to the  fourth article--%>  
    <c:if test="${not empty properties.fourthPage}">
        <tg:namedNoteProvider name="fourthNote" path="${properties.fourthPage}"/> 
                        
        <c:set var="fourthImage_624_468">
            <tg:renditionSelector image="${fourthNote.noteImageAsset}" width="624" height="468"/>
        </c:set>
        
        <c:set var="fourthImage_63_63">
            <tg:renditionSelector image="${fourthNote.noteImageAsset}" width="63" height="63"/>
        </c:set>
        
        <%-- adding validation to show the image context in the image depending of the template --%>
        <c:if test="${fourthNote != null}">
            <c:if test="${fourthNote.photo == true }">  
                <c:set var="fourthNoteImageContext" value="photo-corner" />  
            </c:if>    
            <c:if test="${fourthNote.video == true}">
                <c:set var="fourthNoteImageContext" value="video-corner" />  
            </c:if>    
        </c:if>
        
    </c:if>


    <%-- adding NoteManagerService service to the  fifth article--%>  
    <c:if test="${not empty properties.fifthPage}">
        <tg:namedNoteProvider name="fifthNote" path="${properties.fifthPage}"/> 
                        
        <c:set var="fifthImage_624_468">
            <tg:renditionSelector image="${fifthNote.noteImageAsset}" width="624" height="468"/>
        </c:set>
        
        <c:set var="fifthImage_63_63">
            <tg:renditionSelector image="${fifthNote.noteImageAsset}" width="63" height="63"/>
        </c:set>
        
        <%-- adding validation to show the image context in the image depending of the template --%>
        <c:if test="${fifthNote != null}">
            <c:if test="${fifthNote.photo == true }">  
                <c:set var="fifthNoteImageContext" value="photo-corner" />  
            </c:if>    
            <c:if test="${fifthNote.video == true}">
                <c:set var="fifthNoteImageContext" value="video-corner" />  
            </c:if>    
        </c:if>
        
    </c:if>


    <%-- adding NoteManagerService service to the  sixth article--%>  
    <c:if test="${not empty properties.sixthPage}">
        <tg:namedNoteProvider name="sixthNote" path="${properties.sixthPage}"/> 
                        
        <c:set var="sixthImage_624_468">
            <tg:renditionSelector image="${sixthNote.noteImageAsset}" width="624" height="468"/>
        </c:set>
        
        <c:set var="sixthImage_63_63">
            <tg:renditionSelector image="${sixthNote.noteImageAsset}" width="63" height="63"/>
        </c:set>
            
        <%-- adding validation to show the image context in the image depending of the template --%>
        <c:if test="${sixthNote != null}">
            <c:if test="${sixthNote.photo == true }">  
                <c:set var="sixthNoteImageContext" value="photo-corner" />  
            </c:if>    
            <c:if test="${sixthNote.video == true}">
                <c:set var="sixthNoteImageContext" value="video-corner" />  
            </c:if>    
        </c:if>
        
        
    </c:if>
    
    <%
    }
    boolean hasTag = ((type.equalsIgnoreCase("automatic")) ? true : false);
    pageContext.setAttribute("infoPage", infoPage);
    pageContext.setAttribute("results", results);    
%>

  
    
    <c:if test="${firstNote != null }">
        <c:set var="active" value="class='active'"/>
    </c:if>
    
    
    <c:if test="${not empty properties.linkTarget && properties.linkTarget != 'select'}">
        <c:set var="target" value="target= '${properties.linkTarget}'"/>
    </c:if>


    <%-- Merge HTML and CSS for the mix_carru_01 Component  --%>
    <c:choose>        
        <c:when test="${firstNote != null}">        
    
            <!-- BEGIN: Carrusel -->
            <div class="mix_carru_01">
                
                <div class="type2b">
                    <article class="articleBigModule">
            
                        <div class="galleryImages">
                            <ul>
                                <li ${active}>
                                    <div id="bigImgA">
                    
                                        <img src="${firstImage_624_468}" alt="${firstNote.title}" id="bigImg"/>                    
                                        <c:if test="${properties.showcontext == true}">
                                            <span id="icon_type" class="${firstNoteImageContext}"></span>
                                        </c:if>
                                         
                                    </div>
                                </li>
                            </ul>
                        </div>
            
                        <div class="galleryCaption">
                        
                            <h2 class="itemSection">
                                <c:if test="${properties.showtopic == true}">                                    
                                    <a href="${firstNote.url}.html" title="${firstNote.title}" ${target} >
                                        ${firstNote.topic}
                                    </a>
                                </c:if>     
                            </h2>
            
                            <h4 class="itemCaption">
                                <c:if test="${properties.showtitle == true}"> 
                                    <a href="${firstNote.url}.html" title="${firstNote.title}" ${target} >
                                        ${firstNote.title}
                                    </a>
                                </c:if>
                            </h4>
                           
                        </div>
                        
                        <ul class="galleryThumbs">
                            <li ${active} image ="${firstImage_624_468}" seccion="${firstNote.topic}" urlSeccion="${firstNote.categoryUrl}.html" title="${firstNote.title}" urlTitle="${firstNote.url}.html" ${target}  <c:if test="${properties.showcontext == true}"> type="${firstNoteImageContext}" </c:if>>
                                <img src="${firstImage_63_63}" alt=""/>
                            </li>
                            <li  image ="${secondImage_624_468}" seccion="${secondNote.topic}" urlSeccion="${secondNote.categoryUrl}.html" title="${secondNote.title}" urlTitle="${secondNote.url}.html"  <c:if test="${properties.showcontext == true}">type="${secondNoteImageContext}"</c:if>>
                                <img src="${secondImage_63_63}" alt=""/>
                            </li>
                            <li image ="${thirdImage_624_468}" seccion="${thirdNote.topic}" urlSeccion="${thirdNote.categoryUrl}.html" title="${thirdNote.title}" urlTitle="${thirdNote.url}.html" <c:if test="${properties.showcontext == true}">type="${thirdNoteImageContext}"</c:if>>
                                <img src="${thirdImage_63_63}" alt=""/>
                            </li>
                            <li image ="${fourthImage_624_468}" seccion="${fourthNote.topic}" urlSeccion="${fourthNote.categoryUrl}.html" title="${fourthNote.title}" urlTitle="${fourthNote.url}.html" <c:if test="${properties.showcontext == true}">type="${fourthNoteImageContext}"</c:if>>
                                <img src="${fourthImage_63_63}" alt=""/>
                            </li>
                            <li image ="${fifthImage_624_468}" seccion="${fifthNote.topic}" urlSeccion="${fifthNote.categoryUrl}.html" title="${fifthNote.title}" urlTitle="${fifthNote.url}.html" <c:if test="${properties.showcontext == true}">type="${fifthNoteImageContext}"</c:if>>
                                <img src="${fifthImage_63_63}" alt=""/>
                            </li>
                            <li image ="${sixthImage_624_468}" seccion="${sixthNote.topic}" urlSeccion="${sixthNote.categoryUrl}.html" title="${sixthNote.title}" urlTitle="${sixthNote.url}.html" <c:if test="${properties.showcontext == true}">type="${sixthNoteImageContext}"</c:if>>                                                                
                                <img src="${sixthImage_63_63}" alt=""/>
                            </li>
                        </ul>
                                             
                    </article>
                </div>
            </div>
            <!-- END: Carrusel -->
      
    </c:when>
        <c:otherwise>
        
            <%--   show message only if this is in author mode --%>
            <c:if test = "${authorMode}"> 
               
                <h3 class="cq-texthint-placeholder" id="cq-gen96"><c:out value="${placeholdercomponentmessage}"/> <c:out value="${componentname}"/></h3>
                <img src="/libs/cq/ui/resources/0.gif" class="cq-carousel-placeholder" alt="" id="cq-gen97">  
                
            </c:if>
           
        </c:otherwise>
    </c:choose>
        
    <%--  adding a new div to move the parsys  --%>
    <c:if test = "${authorMode}"> 
        <div style="clear:both;"></div>
    </c:if>  
    
     <%--  remove note objects in pageContext  --%>
    <%pageContext.removeAttribute("firstNote");%>
    <%pageContext.removeAttribute("secondNote");%>
    <%pageContext.removeAttribute("thirdNote");%>
    <%pageContext.removeAttribute("fourthNote");%>
    <%pageContext.removeAttribute("fifthNote");%>
    <%pageContext.removeAttribute("sixthNote");%>
