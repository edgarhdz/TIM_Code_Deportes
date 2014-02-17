<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Single iFrame Box component.
 *  This component is used to display an iFrame customized using the dialog of this component
 *  also, you can add an internal or external link to the iFrame.
 *  
 *
 *  component id = mix_iframe_01
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 31/07/2013  | Omar Carpinteyro       | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>


<%@include file="/apps/commons/components/global.jsp"%>

<%-- adding target propety to the height --%>
<c:if test="${not empty properties.iframeHeight}">
    <c:set var="iframeHeight" value="${properties.iframeHeight}"/>
</c:if>

<%-- adding target propety to the scrolling --%>
<c:if test="${not empty properties.iframeScrolling}">
    <c:set var="iframeScrolling" value="${properties.iframeScrolling}"/>
</c:if>

<%-- adding target propety to the show in tablet --%>
<c:choose>
    <c:when test="${properties.iframeShowInTablet == 'No'}">
        <c:set var="classShowInTablet" value="hide-tablet " />
    </c:when>
    <c:otherwise>
        <c:set var="classShowInTablet" value="" />      
    </c:otherwise>
</c:choose>

<%-- adding target propety to the show in mobile --%>
<c:choose>
    <c:when test="${properties.iframeShowInMobile == 'No'}">
        <c:set var="classShowInMobile" value="hide-mobile" />
    </c:when>
    <c:otherwise>
        <c:set var="classShowInMobile" value="" />      
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${properties.linkType == 'internal'}">
        <c:if test="${not empty properties.internalLink}">
            <c:set var="link" value="${properties.internalLink}.html" />  
        </c:if>    
    </c:when>
    <c:otherwise>
        <c:if test="${not empty properties.externalLink}">
            <c:set var="link" value="${properties.externalLink}" />
        </c:if>        
    </c:otherwise>
</c:choose>

<c:set var="titlePleca" value="${properties.titlePleca}"/>

<!-- BEGIN: mix_iframe_01 -->  
<div class="mix_iframe_01 ${classShowInTablet}${classShowInMobile}">

    <c:choose>

        <c:when test="${empty titlePleca}">
            <c:if test="${authorMode}">
                <div class="mix_iframe_01_title">       
                    <h3>Configura el t&iacute;tulo del iFrame</h3>
                </div>
            </c:if>
        </c:when>

        <c:otherwise>
           
            <div class="mix_iframe_01_title">
                <c:choose>
                    <c:when test="${not empty properties.urlTitlePleca}">
                         <h3><a href="${properties.urlTitlePleca}" title="${titlePleca}">${titlePleca}</a></h3>
                    </c:when>
                    <c:otherwise>
                        <h3>${titlePleca}</h3>
                    </c:otherwise>
                </c:choose>
            </div>

        </c:otherwise>
    </c:choose>

    <c:choose>

        <c:when test="${empty link}">
            <c:if test="${authorMode}">
                <h3>Configura la URL del iFrame</h3>
            </c:if>
        </c:when>

        <c:otherwise>
            <iframe src="${link}" frameborder="0" scrolling="${iframeScrolling}" height="${iframeHeight}"></iframe>
        </c:otherwise>

    </c:choose>

</div>
<!-- END: mix_iframe_01 -->
