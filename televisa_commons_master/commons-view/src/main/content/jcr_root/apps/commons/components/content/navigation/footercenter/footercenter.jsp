<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Footer Center component for Noticieros Televisa
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 24/01/2013  | jdiaz@xumak.com        | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%
%><%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<%@ page import="com.day.cq.wcm.api.WCMMode"%>
<fmt:message key="commons.editcomponent.placeholder" var="placeholdercenter"/>

<c:set var="mode" value="<%= WCMMode.fromRequest(request) %>"/>
<c:set var="modeEdit" value="<%= WCMMode.EDIT %>"/>


<c:if
    test="${mode==modeEdit && ( empty properties.textMain || properties.numberofitems==0 )}">
   <p style="color:#FFF">  ${placeholdercenter} </p>
</c:if>


<c:choose>
    <c:when test="${properties.linkTypeMain=='internal'}">
        <c:if test="${not empty properties.pathMain}">
            <c:set var="linkMain" value="${properties.pathMain}.html" />
        </c:if>
    </c:when>
    <c:when test="${properties.linkTypeMain=='external'}">
        <c:set var="linkMain" value="${properties.externalLinkMain}" />
        <c:if test="${not empty linkMain}">
            <c:choose>
                <c:when test="${fn:contains(link, 'http://')}">
                </c:when>
                <c:otherwise>
                    <c:set var="linkMain" value="http://${linkMain}" />
                </c:otherwise>
            </c:choose>
       </c:if>     
    </c:when>
</c:choose>



    <div>
    
     
    
        <a href="${linkMain}" target="${properties.targetMain}"
            title="${properties.titleMain}"> ${properties.textMain}
        </a>
    </div>

    <ul class="blueSections-list">
        <c:if test="${properties.numberofitems>0}">
            <c:choose>
                <c:when test="${properties.numberofitems==1}">
                    <c:choose>
                        <c:when test="${properties.linkType=='internal'}">
                            <c:if test="${not empty properties.path}">
                                <c:set var="linkOne" value="${properties.path}.html" />
                            </c:if>    
                        </c:when>
                        <c:when test="${properties.linkType=='external'}">
                            <c:set var="linkOne" value="${properties.externalLink}" />
                            
                            <c:if test="${not empty linkOne}">
                                <c:choose>
                                    <c:when test="${fn:contains(linkOne, 'http://')}">
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="linkOne" value="http://${linkOne}" />
                                    </c:otherwise>
                                </c:choose>        
                            </c:if>  
                            
                        </c:when>
                    </c:choose>                                 

                    <li><a href="${linkOne}" target="${properties.target}"
                        title="${properties.title}"> ${properties.text} </a></li>
                </c:when>
                <c:otherwise>
                    <c:forEach var="i" begin="0" end="${properties.numberofitems-1}">
                        <c:choose>
                            <c:when test="${properties.linkType[i]=='internal'}">
                                <c:if test="${not empty properties.path[i]}">
                                    <c:set var="link" value="${properties.path[i]}.html" />
                                </c:if>    
                            </c:when>
                            <c:when test="${properties.linkType[i]=='external'}">
                                <c:set var="link" value="${properties.externalLink[i]}" />
                                
                                <c:if test="${not empty link}">
                                    <c:choose>
                                        <c:when test="${fn:contains(link, 'http://')}">
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="link" value="http://${link}" />
                                        </c:otherwise>
                                    </c:choose>
                               </c:if>     
                            </c:when>
                        </c:choose>
                        <li><a href="${link}" target="${properties.target[i]}"
                            title="${properties.title[i]}"> ${properties.text[i]} </a></li>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </c:if>
    </ul>
