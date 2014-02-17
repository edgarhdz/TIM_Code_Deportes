
<%--
 * DESCRIPTION
 *  art_3arti_01(threeArticlesTeaser) component for Noticieros Televisa
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 13/03/2013  | jdiaz@xumak.com        | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%><%
%><%@include file="//apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="commons.editcomponent.placeholder" var="placeholdermaintitle" />
<fmt:message key="commons.editcomponent.placeholder" var="placeholdertitle" />
<fmt:message key="commons.editcomponent.placeholder" var="placeholderdescription" />

<c:set var="mode" value="<%=WCMMode.fromRequest(request)%>" />
<c:set var="modeEdit" value="<%=WCMMode.EDIT%>" />

	<c:if test="${not empty properties.patharticle1}">
        <tg:noteProvider path="${properties.patharticle1}"/> 
    </c:if>

<!-- BEGIN: <art_3arti_01> -->

<div class="art_3arti_01">
    <div class="container-items"> 
        <ul>
            <li>
                <div class="titulos">
                        <c:if test="${empty note.topic}">
                            <h2></h2>                        
                        </c:if>
                        <c:if test="${not empty note.topic}">
                            <a href="${note.url}.html" target="${properties.linkTarget1}">
                            	<h2> ${note.topic} </h2>
                            </a>
                        </c:if>

                        <c:if test="${empty note.title}">
                            <p>${placeholdertitle}</p>                    
                        </c:if>

                        <c:if test="${not empty note.title}">
                            <a href="${note.url}.html" target="${properties.linkTarget1}">
                            	<p>${note.title}</p>
                            </a>    
                        </c:if>    
                        <span class="icon"></span>
                </div>
                <div class="info-note">
                    <c:if test="${empty note.description}">
                         <p></p>                    
                    </c:if>
                    <c:if test="${not empty note.description}">
                        <a href="${note.url}.html" target="${properties.linkTarget1}">	
                        	<p>${note.description}</p>
                        </a>    
                    </c:if>
                </div>
            </li>
             <%pageContext.removeAttribute("note");%>
        <c:if test="${not empty properties.patharticle2}">
            <tg:noteProvider path="${properties.patharticle2}"/> 
        </c:if>
            <li>
               <div class="titulos">
                        <c:if test="${empty note.topic}">
                            <h2></h2>                        
                        </c:if>
                        <c:if test="${not empty note.topic}">
                            <a href="${note.url}.html" target="${properties.linkTarget2}">
                            	<h2> ${note.topic} </h2>
                            </a>
                        </c:if>

                        <c:if test="${empty note.title}">
                            <p>${placeholdertitle}</p>                    
                        </c:if>
                    
                        <c:if test="${not empty note.title}">
                            <a href="${note.url}.html" target="${properties.linkTarget2}">
                            	<p>${note.title}</p>
                            </a>    
                        </c:if>    
                        <span class="icon"></span>
                </div>
                <div class="info-note">
                    <c:if test="${empty note.description}">
                         <p></p>                    
                    </c:if>
                    <c:if test="${not empty note.description}">
                        <a href="${note.url}.html" target="${properties.linkTarget2}">
                        	<p>${note.description}</p>
                        </a>    
                    </c:if>
                </div>
            </li>
             <%pageContext.removeAttribute("note");%>
            <c:if test="${not empty properties.patharticle3}">
            <tg:noteProvider path="${properties.patharticle3}"/> 
        </c:if>
            <li>
                 <div class="titulos">
                        <c:if test="${empty note.topic}">
                            <h2></h2>                        
                        </c:if>
                        <c:if test="${not empty note.topic}">
                            <a href="${note.url}.html" target="${properties.linkTarget3}">
                            	<h2> ${note.topic} </h2>
                            </a>    
                        </c:if>

                        <c:if test="${empty note.title}">
                            <p>${placeholdertitle}</p>                    
                        </c:if>

                        <c:if test="${not empty note.title}">
                            <a href="${note.url}.html" target="${properties.linkTarget3}">
                            	<p>${note.title}</p>
                            </a>     
                        </c:if>    
                        <span class="icon"></span>
                </div>
                <div class="info-note">
                    <c:if test="${empty note.description}">
                         <p></p>                    
                    </c:if>
                    <c:if test="${not empty note.description}">
                        <a href="${note.url}.html" target="${properties.linkTarget3}">
                        	<p>${note.description}</p>
                        </a>    
                    </c:if>
                </div>
            </li>
        </ul>
    </div>
</div>
 <%pageContext.removeAttribute("note");%>
<c:if
    test="${mode==modeEdit && ( empty properties.rights || empty properties.trademark || properties.numberofitems==0 ) }">
</c:if>

<!-- END: <art_3arti_01> -->



<%--  adding a new div to move the parsys  --%>
<c:if test = "${authorMode}"> 
    <div style="clear:both;"></div>
</c:if> 