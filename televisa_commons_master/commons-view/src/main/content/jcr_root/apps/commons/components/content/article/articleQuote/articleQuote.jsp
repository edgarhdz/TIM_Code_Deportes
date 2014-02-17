<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Article Quote Component
 *  This component is used to add the following components art_quote_01 / art_quote_02  / art_quote_03
 *  where the user can select a of these components in the dialog, for the art_quote_01 component
 *  the user only can add a content and a link to the content, the art_quote_03 component it is similar
 *  like the art_quote_01 component, in contrast the art_quote_02 the author can add an image also he
 *  can add  the link for the content and an author.
 *  
 *  component id = art_quote_01 / art_quote_02  / art_quote_03
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 07/02/2013  | Juan Jose Pol.         | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>

<%@include file="/apps/deportes/generics/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="commons.message.placeholder" var="placeholdercomponentmessage"/>
<fmt:message key="commons.artquote01.title" var="componentname"/>

    <%-- adding image rendition of 192 * 108--%>
    <c:set var="rendition194_109">
      <tg:renditionFromImageAsset name="fileReference" width="194" height="109"/>
    </c:set>
    <%-- adding image rendition of 210 * 120--%>
    <c:set var="rendition210_120">
      <tg:renditionFromImageAsset name="fileReference" width="210" height="120"/>
    </c:set>

    <%--  Quote Validation to add the css correct --%>
    <c:choose>
        <c:when test="${properties.quote == 'art_quote_01'}">
            <c:set var="art_quote" value="art_quote_01 left" />          
        </c:when>
        <c:when test="${properties.quote == 'art_quote_02'}">
            <c:set var="art_quote" value="art_quote_02 left" />          
        </c:when>
        <c:when test="${properties.quote == 'art_quote_03'}">
            <c:set var="art_quote" value="art_quote_03" />          
        </c:when>
        <c:when test="${properties.quote == 'art_quote_04'}">
            <c:set var="art_quote" value="art_quote_04" />          
        </c:when>
        <c:otherwise>
            <c:set var="art_quote" value="" />    
        </c:otherwise>
    </c:choose>
    
     <%-- validation to add the css for the author --%>

    <c:if test="${properties.quote == 'art_quote_02' || properties.quote == 'art_quote_04' && not empty properties.author}">        
        <c:set var="classAuthor" value="class ='autor'" />
        <c:set var="valueAuthor" value="Foto: ${properties.author}" />
    </c:if>

    <%-- Add Additional Class for art_quote_04 --%>
    <c:if test="${properties.quote == 'art_quote_04' && not empty properties.author}">
    	<c:set var="classAuthor" value="class ='autor textcolor-title4'" />
    </c:if>

    
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
        <c:set var="titleAttribute" value="${properties.linkTitle}" />
    </c:if>
    
    
     <%-- Validation Alt Attribute for the Image  --%>
    <c:if test="${not empty properties.alt}">
        <c:set var="alt" value="alt= '${properties.alt}'" />
    </c:if>
    


    <%-- Merge hmtl and css for the  art_quote_01 / art_quote_02 / art_quote_03  Component--%>
    <!-- BEGIN: art_quote_01 / art_quote_02 / art_quote_03 -->
    <c:choose>
    
        <c:when test="${not empty properties.title || not empty rendition194_109}">        

            <div class="${art_quote}">

            <c:if test="${properties.quote == 'art_quote_04'}">
            	<div>
        			<article class="articleMedium">
            </c:if>

                    <c:if test="${not empty link}">
                        <a href="${link}" title="${titleAttribute}" target="${target}">
                        </c:if>

                                <%-- adding main image for qoute 02 --%>
                                <c:if test="${properties.quote == 'art_quote_02' &&  rendition194_109 != null}">

                                    <c:choose>
                                        <c:when test="${not empty rendition194_109}">
                                            <img src="${rendition194_109}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <img class="cq-dd-image cq-image-placeholder"  src="/etc/designs/default/0.gif" >
                                        </c:otherwise>
                                    </c:choose>

                                </c:if>

                                <%-- adding main image for qoute 04--%>
                                <c:if test="${properties.quote == 'art_quote_04' &&  rendition210_120 != null}">
				
                                    <c:choose>
                                        <c:when test="${not empty rendition210_120}">
                                            <img src="${rendition210_120}"/>
                                            <div>
                                        </c:when>
                                        <c:otherwise>
                                            <img class="cq-dd-image cq-image-placeholder"  src="/etc/designs/default/0.gif" >
                                            <div>
                                        </c:otherwise>
                                    </c:choose>

                                </c:if>

                                <h3>
                                    <c:out value="${properties.title}"/>
                                    <!-- To separate texts (Issue NA-AT) -->
									&nbsp;
                                    <%-- adding author for the photo--%>
                                    <c:if test="${properties.quote == 'art_quote_02' || properties.quote == 'art_quote_04'}">
                                        <span ${classAuthor}>${valueAuthor}</span>
                                    </c:if>

                                </h3>

                        <c:if test="${not empty link}">
                            </a>
                        </c:if>
                    </div>
                
                    </c:when>
                    <c:otherwise>
                        <c:if test = "${authorMode}">
                            <h3 class="cq-texthint-placeholder" id="cq-gen96"><c:out value="${placeholdercomponentmessage}"/> <c:out value="${componentname}"/></h3>
                            <img src="/libs/cq/ui/resources/0.gif" class="cq-image-placeholder" alt="" id="cq-gen97">                
                        </c:if>
                    </c:otherwise>
                </c:choose>
            <c:if test="${properties.quote == 'art_quote_04'}">
                        </div>
                    </article>
                </div>
            </c:if>
    <!-- END: art_quote_01 / art_quote_02 / art_quote_03  -->
    

    <%--  adding a new div to move the parsys   --%>
    <c:if test = "${authorMode}"> 
        <div style="clear:both;"></div>
    </c:if> 
