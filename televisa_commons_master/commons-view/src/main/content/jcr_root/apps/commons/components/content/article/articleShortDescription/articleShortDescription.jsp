<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Article Short Description Component
 *  This component show a short description for an article, using the content 
 *  added in the widget of the dialog of this component added by the author.
 *  
 *  component id = art_bullet_01
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 06/02/2013  | Juan Jose Pol.         | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>


<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="commons.message.placeholder" var="placeholdercomponentmessage"/>
<fmt:message key="commons.artbullet01.title" var="componentname"/>
<fmt:message key="commons.message.placeholdercomponent" var="placeholdercomponent"/>

    
    <%-- validation link --%>
    <c:if test="${empty link}">
        <c:set var="link" value=""/>
    </c:if>    
    
    <c:choose>
        <c:when test="${properties.linkType == 'internal'}">
            <c:if test="${not empty properties.internalLink}">
                <c:set var="link" value="${properties.internalLink}.html" />  
            </c:if>    
        </c:when>
        <c:otherwise>
            <c:set var="link" value="${properties.externalLink}" />    
        </c:otherwise>
    </c:choose>

    <%-- Validation Target Attribute  --%>
    <c:if test="${not empty properties.linkTarget && properties.linkTarget != 'select'}">
        <c:set var="target" value="${properties.linkTarget}"/>
    </c:if>
    
     <%-- Validation Title Attribute  --%>
    <c:if test="${not empty properties.linkTitle}">
        <c:set var="title" value="title = '${properties.linkTitle}'" />  
        
    </c:if>
    
    
    
    <%-- Merge hmtl and css for the art_bullet_01 Component--%>
      
    <!-- BEGIN:art_bullet_01 -->
  
    <div class="art_bullet_01">
        <div class="articles">
            <ul>
                <li>     
                    <c:if test="${not empty link}">                                       
                        <a href="${link}" ${title} target="${target}">
                    </c:if>

                    <c:choose>
                        <c:when test="${not empty properties.description}">                            
                            <c:out value="${properties.description}"/>                            
                        </c:when>
                        <c:otherwise>
                            <c:if test = "${authorMode}">                            
                                <h3 class="cq-texthint-placeholder" ><c:out value="${placeholdercomponentmessage}"/> <c:out value="${placeholdercomponent}"/> <c:out value="${componentname}"/></h3>
                            </c:if>    
                        </c:otherwise>
                    </c:choose>
                                                          
                    <c:if test="${not empty link}">                                       
                        </a>                     
                    </c:if>                                       
                </li>
            </ul>
        </div>
    </div>
    
    <!-- END:art_bullet_01 -->
    
    <%--  adding a new div to move the parsys   --%>
    <c:if test = "${authorMode}"> 
        <div style="clear:both;"></div>
    </c:if>         
