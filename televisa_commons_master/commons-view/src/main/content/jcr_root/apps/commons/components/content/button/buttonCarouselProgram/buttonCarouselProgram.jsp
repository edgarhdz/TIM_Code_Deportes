<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Generic Program Carousel Button.
 *
 *  Displays a generic carousel program article button.
 *  
 *  component id = btn_6prog_02
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 12/02/2013  | Gerardo Escobar        | Initial Creation.
 * 1.0     | 20/02/2013  | Gerardo Escobar        | Updated the component edit configuration and deny image upload.
 * 1.0     | 18/03/2013  | Juan Jose Pol.         | HTML updated and place holder added
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@page import="com.day.cq.wcm.foundation.List"%>
<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>



<fmt:message key="commons.carouselprogram.message.configure" var="messageConfigure"/>
<fmt:message key="commons.carouselprogram.message.invalid" var="messageInvalid"/>
<fmt:message key="commons.carouselprogram.message.left" var="messageLeft"/>
<fmt:message key="commons.carouselprogram.message.right" var="messageRight"/>
<fmt:message key="commons.singletitlebox.title" var="componentname"/>
<fmt:message key="commons.component.message.titleboxes.placeholder" var="placeholder"/>

<cq:include script="init.jsp"/>
<c:set var="configure" value="<%= ((List) request.getAttribute("list")).isEmpty() %>"/>




    <%-- Adding validation for the content of the text --%>
    <c:set var="text" value="${properties.linkText}"/>
    <c:if test="${empty text}">
    	<c:if test = "${authorMode}"> 
        	<c:set var="text" value="${placeholder} ${componentname}"/>
    	</c:if>
    </c:if>

    <%-- link validation for internal or external--%>
    <c:choose>  
        <c:when test="${properties.linkType == 'internal'}">
            <c:if test = "${not empty properties.internalLink}">
                <c:set var="url" value="href = '${properties.internalLink}.html'"/>
            </c:if>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty properties.externalLink}">
                <c:set var="url" value="href = '${properties.externalLink}'"/>
            </c:if>
        </c:otherwise>  
    </c:choose> 

    <%-- target validation --%>
    <c:if test="${not empty properties.linkTarget && properties.linkTarget != 'select'}">
        <c:set var="target" value="target= '${properties.linkTarget}'"/>
    </c:if>
    

    <!-- BEGIN: btn_progm_02 -->
    <div class="btn_6prog_02" style="clear: both;">
    
        <div class="type1a" paddinglis="original">
        
        
            <div class="title">
                <h3>
                   
                    <a ${url} ${target} ><c:out value="${text}"/><span class="icon"></span></a>
                </h3>
                <ul class="flechas">
                    <li><a href="#left" title="${messageLeft}" class="left sprite carouselArrowLeft" > &laquo;</a></li>
                    <li><a href="#right" title="${messageRight}" class="right sprite carouselArrowRight"> &raquo;</a></li>
                </ul>
            </div>
      
                        
            <div class="carrusel">
                                
                <c:choose>
                    <c:when test="${configure}">
                        <c:if test="${authorMode}"> 
                            <h2 class="cq-texthint-placeholder" id="cq-gen96"><c:out value="${messageConfigure}"/></h2><br>
                            <ul style="width: 1610px !important;" >
                                <li><a><img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="" id="cq-gen97"></a></li>
                                <li><a><img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="" id="cq-gen97"></a></li> 
                                <li><a><img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="" id="cq-gen97"></a></li>  
                                <li><a><img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="" id="cq-gen97"></a></li>
                                <li><a><img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="" id="cq-gen97"></a></li>
                                
                            </ul>
                        </c:if>
                    </c:when>
                    
                    
                    <c:otherwise> 
                       <%-- <ul  style="width: 1610px !important;"> --%>
                       <ul>
                            <c:forEach var="item" items="${list.pages}">
                
                                <tg:noteProvider path="${item.path}"/>
                                <c:choose>
                
                                    <c:when test="${empty note}">
                                        <c:if test="${authorMode}"> 
                                            <li>
                                                <a>    
                                                    <img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="" id="cq-gen97">
                                                </a>    
                                                <h2 class="cq-texthint-placeholder" id="cq-gen96"><c:out value="${messageNotfound}"/></h2>       
                                            </li>    
                                        </c:if>
                                    </c:when>

                                    <c:when test="${!note.program}">
                                        ${note.program}
                                        <c:if test="${authorMode}"> 
                                            <li>
                                                <a>    
                                                    <img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="" id="cq-gen97">
                                                </a>    
                                                <h2 class="cq-texthint-placeholder" id="cq-gen96"><c:out value="${messageInvalid}"/></h2>       
                                            </li>    
                                        </c:if>
                                    </c:when>
                
                                    <c:otherwise>
                
                                        <c:set var="url" value="href='${note.url}.html'"/>
                                        <c:set var="title" value="title='${note.title}'"/>
                
                                        <c:set var="rendition">
                                            <tg:programRenditionSelector width="136" height="102"/>
                                        </c:set>
                                        
                                        <c:set var="alt" value="alt='${note.programImage.description}'"/>
                                        
                                        <li>
                                            <a ${url} ${title}  ><img src="${rendition}" ${alt}></a>
                                            <h2><a ${url} ${title} > ${note.programName} </a></h2>
                                            <p>${note.programSchedule}</p>
                                        </li>
                                    </c:otherwise>
                
                                </c:choose>
                
                            </c:forEach>
                        </ul>
                    </c:otherwise>
                </c:choose>

            </div>
                    
        </div>
        
            
        <div class="bullets">
            <ul>
                <li class="selected"></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
        </div>
        
    </div>
    
        <%--  adding a new div to move the parsys --%>
    <c:if test = "${authorMode}"> 
        <div style="clear:both;"></div>
    </c:if>    
    
    <%--  remove note object in pageContext when add two or more primaryTeaser in a container --%>
    <%pageContext.removeAttribute("note");%>
