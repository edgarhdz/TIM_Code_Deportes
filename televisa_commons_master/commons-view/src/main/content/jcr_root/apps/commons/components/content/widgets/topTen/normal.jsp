<%--

  generics.topTen.title component.

  generics.topTen.description

--%>
<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="commons.singletitlebox.title" var="componentname"/>
<fmt:message key="commons.component.message.titleboxes.placeholder" var="placeholder"/>

<%
     /* adding validation to show the arrows buttons when we have more od 5 items  */
    Resource ressss = resourceResolver.getResource(currentNode.getPath());
    Node node = ressss.adaptTo(Node.class);

    if(node.hasProperty("numberofitems") ){
       pageContext.setAttribute("numberofitems" , node.getProperty("numberofitems").getValue().getString());
    }else{
        pageContext.setAttribute("numberofitems" , null);
    }
%>

    <%-- Adding validation for the content of the text --%>
    <c:set var="text" value="${properties.linkText}"/>
    <c:if test="${empty text}">
        <c:if test = "${authorMode}"> 
            <c:set var="text" value="${placeholder} ${componentname}"/>
        </c:if>
    </c:if>

    <c:choose>
        <c:when test="${properties.linkType == 'internal'}">
            <c:if test="${not empty properties.internalLink}">
                <c:set var="link" value="href='${properties.internalLink}.html'" />  
            </c:if>    
        </c:when>
        <c:otherwise>
            <c:if test="${not empty properties.externalLink}">
                <c:set var="link" value="href='${properties.externalLink}'" />
            </c:if>        
        </c:otherwise>
    </c:choose>    
    
    <%-- adding target propety to the link --%>
    <c:if test="${not empty properties.linkText}">
        <c:set var="target" value="target= '${properties.linkTarget}'"/>
    </c:if>
    <%-- adding title propety to the link --%>
    <c:if test="${not empty properties.linkTitle}">
        <c:set var="title" value="title= '${properties.linkTitle}'" />
    </c:if>

<!-- Add styles to devices -->
<div class="hide-tablet">
    <!-- BEGIN: wdg_top10_01-->
    <div class="wdg_top10_01" data-enhance="false">
         <div class="wdg_carousa showArrows">
         
                <div class="wdg_top10_01_title">
                    <h3>
                        <a ${link} ${target} ${title}> ${text} <span class="icon mobile"></span></a>
                    </h3>

                    <%-- adding validation to show the arrows buttons when we have more od 5 items--%>                    
                    <c:if test="${numberofitems != 'null' && numberofitems > 5 }">
                        <ul class="wt10_arrows">
                            <li>
                                <a href="#left" class="wdg_carousa_left2 carouselArrowLeft">
                                    <i class="tvsa-double-caret-left inactive"></i>
                                </a>
                            </li>
                            <li class="dotted-left">
                                <a href="#right" class="wdg_carousa_right2 carouselArrowRight">
                                    <i class="tvsa-double-caret-right"></i>
                                </a>
                            </li>
                        </ul>
                    </c:if>
                </div>
                           
                <c:if test="${numberofitems == null || numberofitems == 0 }">
                
                    <%--  show message only if this is author mode --%>
                    <c:if test = "${authorMode}">                                        
                        <div class="wdg_top10_01_carousel">
                            <%-- <ul style="width: 0px !important;"> --%>
                            <ul style="width: 0px !important;">
                               <c:forEach var="i" begin="1" end="5" step="1" varStatus ="status">
    
                                   <li style="margin: auto;">
                                        <div>
                                            <a>
                                               <img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="" id="cq-gen97">  
                                                <h6 class="cq-texthint-placeholder" id="cq-gen96"> Lorem Ipsum</h6>
                                                <p><h6 class="cq-texthint-placeholder" id="cq-gen96">Neque porro quisquam est qui dolorem ipsum quia dolor sit amet...</h6></p>
                                                <span class="icon"></span>
                                            </a>
                                        </div>
                                            
                                   </li>
                                    
                               </c:forEach>       
                            </ul>
                        </div>           
                    </c:if>
                </c:if>
                        
                        
                <c:if test="${numberofitems != null && numberofitems > 0 }">
                
                    <div class="wdg_top10_01_carousel">
                    
                       <%-- <ul style="width: 626px !important;"> --%>
                       <ul>
                            <c:set var="index" value="${0}" />
                            <c:forEach var="item" items ="${properties.path}" varStatus="loop">
                            
                                <c:choose>
                                        <c:when test="${properties.numberofitems gt 1}"> 
    
                                           <c:if test="${index==0 || index==5 || index==10}">
                                               <%-- <li class="showArrows"> --%>
                                               <li>
                                           </c:if>
                                           
                                           <%-- adding NoteManagerService service --%>  
                                            <c:if test="${not empty properties.path[index]}">
                                                <tg:noteProvider path="${properties.path[index]}"/> 
                                                
                                                <c:set var="rendition">
                                                    <tg:renditionSelector image="${note.noteImageAsset}" width="84" height="63"/>
                                                </c:set>
                                            </c:if>
                                            
                                            
                                            <div>
                                                <a href="${note.url}.html" title="${note.title}" ${target}>
                                                    <img src="${rendition}" alt="${note.title}"/>
                                                    <h3><c:out value="${note.topic}"/></h3>
                                                    <p><c:out value="${note.title}"/></p>
                                                    <span class="icon"></span>
                                                </a>
                                            </div>
                                            
                                           
                                           <c:if test="${index==4 || index==9 || index==14}">
                                               </li>
                                           </c:if>
                                           
                                            
                                         </c:when>
                                         <c:otherwise>
                                            
                                            <li class="showArrows">
                                            
                                                <%-- adding NoteManagerService service --%>  
                                                <c:if test="${not empty properties.path}">
                                                    <tg:noteProvider path="${properties.path}"/> 
                                                    
                                                    <c:set var="rendition">
                                                        <tg:renditionSelector image="${note.noteImageAsset}" width="84" height="63"/>
                                                    </c:set>
                                                </c:if>
                                                
                                                <div>
                                                    <a href="${note.url}.html" title="${note.title}" ${target}>
                                                        <img src="${rendition}" alt="${note.title}"/>
                                                        <h3><c:out value="${note.topic}"/></h3>
                                                        <p><c:out value="${note.title}"/></p>
                                                        <span class="icon"></span>
                                                    </a>
                                                </div>
                                                
                                            </li>
                                         </c:otherwise>
                                </c:choose>                                                
                                
                                <c:set var="index" value="${index + 1}" />
                                
                            </c:forEach>
                            
                            <c:if test="${index!=5 || index!=10 || index!=15}">
                            </li>
                            </c:if>
                            
                        </ul>
                    </div>
                </c:if>                       
      
            </div>
        <div class="bullets">
            <div class="background-color1"></div>
            <div></div>
        </div>
    </div>

    <!-- END: wdg_top10_01 -->

<!-- close div of styles to devices -->
</div>
